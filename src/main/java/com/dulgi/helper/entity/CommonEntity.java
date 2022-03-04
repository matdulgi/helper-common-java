package com.dulgi.helper.entity;

import com.dulgi.helper.common.Core;
import com.dulgi.helper.regex.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/* entity wrapper
    this entity means the Object has setter, getter, capsuled properties
*/
public abstract class CommonEntity<T> {
    Core core = new Core();
    Logger logger = LoggerFactory.getLogger(CommonEntity.class);
//    String classPath;
    T entity;

    //regex
    private String setterRegex = Regex.SETTER.getRegex();
    private String getterRegex = Regex.GETTER.getRegex();

    CommonEntity(){ }

    CommonEntity(T entity){
        this.entity = entity;
    }

    CommonEntity(Class<T> clazz){
        try {
            this.entity = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // create empty entity
    CommonEntity(Class<? extends T> clazz, Properties props) {
//    CommonEntity(T t, Properties props) {
        try {
            entity = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T getEntity() {
        return entity;
    }

    public Method[] getSetters() {
        return core.getMethods(entity.getClass(), Pattern.compile(setterRegex));
    }
    // public Method[] getGetters(String name){
    //     return getters;
    // }

    public boolean isSetter(Method method) {
        return Pattern.matches(setterRegex, method.getName());
    }

    public boolean isGetter(Method method) {
        return Pattern.matches(getterRegex, method.getName());
    }

    public String setterToProperty(Method setter) {
        char[] methodNameArray = null;
        if (isSetter(setter)) {
            methodNameArray = setter.getName().substring(3).toCharArray();
            methodNameArray[0] = (char) (methodNameArray[0] + Core.ASCII_CASE_DIFF);
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

    public void entityInfo() {
        entity.toString();
//		Enumeration propEnum = props.propertyNames();
//		while(propEnum.hasMoreElements()){
//		}
    }

    //tmp
    public T newInstance() {
        T instance = null;
        try {
            instance = (T) entity.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}












