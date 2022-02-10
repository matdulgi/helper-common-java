package com.dulgi.helper.common;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;

public class PropertiesEntity extends CommonEntity{
    @Autowired
    Core core;
    /*
        check properties with method's name, if property is exist, invoke setter method
     */
        // properties.contains(method.getName().equals(""));

    public boolean setPropertiesWith(Method[] methods){

        return true;
    }
    
}
