package com.dulgi.helper.entity;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import com.dulgi.helper.common.Core;
import com.dulgi.helper.common.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* entity wrapper
    this entity means the Object has setter, getter, capsuled properties
*/
public abstract class CommonEntity<T> {
    Core core = new Core();
    Logger logger = LoggerFactory.getLogger(CommonEntity.class);
    String type;
    String classPath;
    T entity;
    Method[] setters;
    Method[] getters;

    //regex
    final String setterRegex = Regex.SETTER.getRegex();
    final String getterRegex = Regex.GETTER.getRegex();
    private Class<? extends AbstractCollection> clazzC;

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

    //
    public Set getPropsNames() {
        Set<String> props = new HashSet();
        Class clazz = entity.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            props.add(setterToProperty(method));
        }
        return props;
    }

    public void entityInfo(){
        entity.toString();
//		Enumeration propEnum = props.propertyNames();
//		while(propEnum.hasMoreElements()){
//		}
    }
}












