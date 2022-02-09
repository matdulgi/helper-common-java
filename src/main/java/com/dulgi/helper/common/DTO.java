package com.dulgi.helper.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

public class DTO extends CommonEntity{
	@Autowired
	public Core core;

	// properties is created by setters or getters
	private String[] properties;
	private Method[] setters;
	private Method[] getters;

    private DTO(){ }

	//unfinished
    public DTO(Class<?> clazz){
		Method[] methods = clazz.getMethods();
    }
    public DTO(Object dto){
		Method[] methods = dto.getClass().getMethods();
    }

	public DTO(Properties properties){

	}

    /*
		verify DTO
        requires 
		 - must have property
		 - setters, getters those name derived from properties
    */
    public boolean isDto(Class<?> clazz){
        boolean isDto = false;
		clazz.getMethods();


        return isDto;
    }

    public void getProperties(){

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
}