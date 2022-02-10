package com.dulgi.helper.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/* entity wrapper
    this entity means the Object has setter, getter, capsuled properties
*/
public class CommonEntity<T> {
    Core core = new Core();
    Logger logger = LoggerFactory.getLogger(CommonEntity.class);
    String type;
    String classPath;
    T entity;
    Method[] setters; 
    Method[] getters;
	Properties props;

    //regex
    final String setterRegex = Regex.SETTER.getRegex();
    final String getterRegex = Regex.GETTER.getRegex();


    public CommonEntity(){ }


    // create empty new entity wity type
    public CommonEntity(String type){
    }

    // create empty entity
    public CommonEntity(Class<? extends T> clazz){
		try {
			entity = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public T getEntity(){
		return entity;
	}

	public CommonEntity(T entity){
		this.entity = entity;
		setters = core.getMethods(entity.getClass(), Pattern.compile(setterRegex) );
	}

	public Method[] getSetters(String name){
        return setters;
    }
    // public Method[] getGetters(String name){
    //     return getters;
    // }

    private boolean isSetter(Method method){
        return Pattern.matches(setterRegex, method.getName());
	}
	
	private boolean isGetter(Method method){
        return Pattern.matches(getterRegex, method.getName());
	}

    public String setterToProperty(Method setter) {
        char[] methodNameArray = null;
        if (isSetter(setter)){
            methodNameArray = setter.getName().substring(3).toCharArray();
            methodNameArray[0] = (char) (methodNameArray[0] + core.ASCII_CASE_DIFF);
        }
		return new String(methodNameArray);
	}

	// return all entity's properties set
	public Set<String> getPropsNames() {
		Set<String> propSet = new HashSet<>();
		Class clazz = entity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String prop;

		for (Method method : methods) {
			prop = setterToProperty(method);
			propSet.add(prop);
		}
		return propSet;
	}

	// return Entity's properties with passed dto's getters
	// it uses entity's getters
	public Properties getProps() {
		if (props != null){
			return props;
		}
		String prop, propVal = "";

		Class clazz = entity.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			if (isGetter(method)) {
				prop = setterToProperty(method);
				try {
					propVal = (String)method.invoke(entity);
					props.put(prop, propVal);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}

    public void setEntityWithProp(Properties props){
		String prop, propVal, mprop;
		Method[] methods = core.getMethods(entity.getClass(), Pattern.compile(setterRegex));

		Enumeration propEnum = props.propertyNames();
		while(propEnum.hasMoreElements()) {
			prop = (String) propEnum.nextElement();
			propVal = props.getProperty(prop);
			
			for (Method method : methods){
//				mprop = core.setterToProp(method.getName()); accessed just one time
				if(core.setterToProp(method.getName()).equals(prop)){
					try {
						method.invoke(entity, propVal);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
    }

	public void entityInfo(){
		entity.toString();
//		Enumeration propEnum = props.propertyNames();
//		while(propEnum.hasMoreElements()){
//		}
	}
}












