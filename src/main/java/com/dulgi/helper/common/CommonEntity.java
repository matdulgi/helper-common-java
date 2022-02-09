package com.dulgi.helper.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    	// return all DTO's properties set
	public Set<String> settersToPropsNames() {
		Set<String> propertySet = new HashSet<>();
		Class clazz = entity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String methodName;
		String property;

		for (Method method : methods) {
			property = setterToProperty(method);
			propertySet.add(property);
		}
		return propertySet;
	}

    	// return DTO's properties map with passed dto's setters
	public Map<String, Object> settersToProps(Object dto) {
		String property = "";
		Object propertyValue = "";
		Map<String, Object> propertieMap = new HashMap<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println(dto.getClass().getSimpleName() + "의 프로퍼티 목록");
		for (Method method : methods) {
			// Class에는 다른 get메서드가 존재한다
			if (method.getName().contains("get")) {
				property = setterToProperty(method);
				try {
					propertyValue = method.invoke(dto);
					System.out.print("프로퍼티 : " + property + " 값 : " + propertyValue);
					propertieMap.put(property, propertyValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return propertieMap;
	}

    public void setEntityWithSetters(){
    }
}
