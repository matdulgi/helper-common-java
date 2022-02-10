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
public class CommonEntity {
    @Autowired
    Core core;
    Logger logger = LoggerFactory.getLogger(CommonEntity.class);
    String type;
    String classPath;
    Object entity;
    Method[] setters; 
    Method[] getters;
	Properties props;

    //regex
    final String setterRegex = Regex.SETTER.getRegex();
    final String getterRegex = Regex.GETTER.getRegex();
    

    public CommonEntity(){ }

    public CommonEntity(Object entity){
        this.entity = entity;
        setters = core.getMethods(entity.getClass(), Pattern.compile(setterRegex) );

    }

    // create empty new entity wity type 
    public CommonEntity(String type){
    }

    // create empty entity
    public CommonEntity(Class<?> clazz){
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
		String prop = "";
		Object propVal = "";
//		Properties props = new Properties();

		Class clazz = entity.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println(entity.getClass().getSimpleName() + "의 프로퍼티 목록");
		for (Method method : methods) {
			if (isGetter(method)) {
				prop = setterToProperty(method);
				try {
					propVal = method.invoke(entity);
					System.out.print("프로퍼티 : " + prop + " 값 : " + propVal);
					props.put(prop, propVal);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}

    public void setEntityWithSetters(){
    }
}
