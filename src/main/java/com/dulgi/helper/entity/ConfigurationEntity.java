package com.dulgi.helper.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.Enumeration;
import java.util.Properties;

/* entity wrapper
    this entity means the Object has setter, getter, capsuled properties
*/
public class ConfigurationEntity<T> extends CommonEntity {
    Logger logger = LoggerFactory.getLogger(ConfigurationEntity.class);
//    T entity;
    Properties props;


    private Class<? extends AbstractCollection> clazzC;


    // create empty entity
    // it could to enforce to pass properties of configuration type class parameter
//    public ConfigurationEntity(Class<? extends T> clazz) {
//        super(clazz);
//    }

    /** final field requires initialization
     * constructor with one parameter (clazz) cannot initialize directly
     *
     * how to initialize parent's final field with metadata
     * condition
     *  - configuration class(metadata) can be changed (Polymorphism)
     *  - the class's must not have any constructor that does not initialize own final field
     *      do not support to reflection creation instance
     *  -
     *
     * case 1. declare all parameter type of configuration classes
     *  -> can increase many of unused constructor or method
     *
     */
    public ConfigurationEntity(Object dto){
        super(dto);
    }

	public ConfigurationEntity(Class<? extends T> clazz, Properties props) {
        super(clazz, props);
    }

//    public ConfigurationEntity(T entity) {
//        this.entity = entity;
//        setters = core.getMethods(entity.getClass(), Pattern.compile(setterRegex));
//    }

    // return Entity's properties with passed dto's getters
    // it uses entity's getters
    public Properties getProps() {
        if (props != null) {
            return props;
        }
        String prop, propVal = "";

        Class clazz = entity.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (isGetter(method)) {
                prop = setterToProperty(method);
                try {
                    propVal = (String) method.invoke(entity);
                    props.put(prop, propVal);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

//    public T initEntity(Properties props) {
//        return initEntity(((T)super.entity).getClass(), props);
//    }

//    private T initEntity(T entity){
//        Properties props =
//        먄약 하나 더 추가하면,, 무한루프?
//        return null;
//    }


//    private T initEntity(Class<? extends T> clazz, Properties props){
    public void initEntity(Properties props){
        if (entity == (T) super.entity)
            entity = (T) super.entity;

        String prop, propVal;
        Method[] methods = getSetters();

        Enumeration propEnum = props.propertyNames();
        while (propEnum.hasMoreElements()) {
            prop = (String) propEnum.nextElement();
            propVal = props.getProperty(prop);

            for (Method method : methods) {
//				mprop = core.setterToProp(method.getName()); accessed just one time
                if (core.setterToProp(method.getName()).equals(prop)) {
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

    public T getNewEntity(Class<? extends T> clazz, Properties props){
        try {
            T entity = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T)super.entity;
    }

}












