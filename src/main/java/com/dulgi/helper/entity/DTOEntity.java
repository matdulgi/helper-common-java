package com.dulgi.helper.entity;

import com.dulgi.helper.annotation.NeedToChange;
import com.dulgi.helper.common.Core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class DTOEntity<T> extends CommonEntity {
    private Core core = new Core();

    // properties is created by setters or getters
    private Map<String, Object> props;

    //unfinished
    @NeedToChange("find way to get type without class prarmeter")
    public DTOEntity(Class<?> clazz) {
        super(clazz);
    }

//    public DTOEntity(Object dto) {
//        super(dto);
//    }

    public DTOEntity(Class<? extends T> clazz, Properties properties) {
        super(clazz, properties);
    }

    /*
		verify DTO
        requires 
		 - must have property
		 - setters, getters those name derived from properties
    */
    public boolean isDto(Class<?> clazz) {
        boolean isDto = false;
        clazz.getMethods();


        return isDto;
    }

    public Map<String, Object> getProps() {
        return this.props;

    }

    public void printDtoInfo(Object dto) {
        Class clazz = dto.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        String methodName;
        System.out.print("dto 정보 : ");
        try {
            for (Method method : methods) {
                methodName = method.getName();
                if (methodName.contains("get"))
                    System.out.print(methodName + " : " + method.invoke(dto) + " ");
            }
            System.out.println("");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public T initEntity(Map<String, Object> map) {
        Method[] methods = getSetters();

        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext()) {
            String prop = (String) itr.next();
            Object propVal = map.get(prop);

            //methods, map
            for (Method method : methods) {
                /** condition to invoke setter
                 *  - property name must be matched to map's key
                 *  -
                 */
                if (core.setterToProp(method.getName()).equals(prop) && method.getParameterTypes()[0].equals(map.get(prop).getClass())) {
                    try {
                        setField(method, propVal);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return (T) entity;
    }

    public void setField(Method setter, Object value) throws InvocationTargetException, IllegalAccessException {
        if (value instanceof String){
            setter.invoke(entity, (String)value);
        }
        else if(value instanceof Integer){
            setter.invoke(entity, (Integer)value);
        }
        else if(value instanceof Double){
            setter.invoke(entity, (Double)value);
        }
        else if(value instanceof Boolean){
            setter.invoke(entity, (Boolean)value);
        }
        else if(value instanceof Timestamp){
            setter.invoke(entity, (Timestamp)value);
        }
    }
}