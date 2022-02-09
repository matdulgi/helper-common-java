package com.dulgi.helper.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
	sturucture
	* core needs to implement several functions of intefaces - changed to other core
	* core class's method could be static because it's doesn't used to client
	* Entity e.g. Properties,DTO extends CommonEntity 
*/
public class Core implements StringConverter{
	Logger logger = LoggerFactory.getLogger(Core.class);

	public Core(){ }


	@Override
	public boolean isCamelStyle(String str) {
		if (!str.contains("_")) {
			logger.warn("args not contain _");
			return false;
		}
		if(str.charAt(0) =='_'){
			logger.warn("first character is _");
			return false;
		} 
		//needs more regex condition
		if(Pattern.matches("^[A-Z]+[a-Z][0-9]*", str)) return true;
		else return false;
	}

	@Override
	public boolean isPropetyStyle(String str) {
		return Pattern.matches("^[a-z]+[_][a-z]", str);
	}

	@Override
	public boolean isSnakeStyle(String str) {
		return Pattern.matches("^[a-z]+[.][a-z]", str);
	}

	@Override
	public String sepWdToCamel(String str, char delimiter) {
		// not snake ... need more condition
		String type = "";
		switch (delimiter){
			case '_' : type = "snake"; break;
			case '.' : type = "proeprty"; break;
		}
		if (!str.contains("_")) {
			System.out.println("not snake case");
			return str;

		} else {
			// 1. check '_' and remove 2. change next letter to uppercase
			int index;
			StringBuffer stringBuffer;
			stringBuffer = new StringBuffer(str);
			while (stringBuffer.indexOf("_") > 0) {
				index = stringBuffer.indexOf("_");
				stringBuffer.deleteCharAt(index);
				stringBuffer.replace(index, index + 1, (char) (stringBuffer.charAt(index) - 32) + "");
			}
			String newString = stringBuffer.toString();

			return stringBuffer.toString();
		}
	}

	@Override
	public String camelToSepWd(String str, char delimiter) {
		
		return null;
	}

	// parse setter, getter methods's name and convert to case
	public String snakeToCamel(String var) {
		// not snake ... need more condition
		if (!var.contains("_")) {
			System.out.println("not snake case");
			return var;

		} else {
			// 1. check '_' and remove 2. change next letter to uppercase
			int index;
			StringBuffer stringBuffer;
			stringBuffer = new StringBuffer(var);
			while (stringBuffer.indexOf("_") > 0) {
				index = stringBuffer.indexOf("_");
				stringBuffer.deleteCharAt(index);
				stringBuffer.replace(index, index + 1, (char) (stringBuffer.charAt(index) - 32) + "");
			}
			String newString = stringBuffer.toString();

			return stringBuffer.toString();
		}
	}

	// interate 했을때와 달리 for문에 정수 i를 사용했기 때문에 반복연산 중간에 index가 변경되어도 계속 연산이 실행되었다.
	public String camelToSnake(String var) {
		StringBuffer stringBuffer;
		if (var.contains("_")) {
			System.out.println("not camel case");
			return var;
		} else {
			int index;
			stringBuffer = new StringBuffer(var);
			boolean hasCap;
			do {
				hasCap = false;
				for (int i = 0; i < stringBuffer.length(); i++) {
					// 65 < upper < 91, 97 < lower < 122
					if ((byte) stringBuffer.charAt(i) < 91 && (byte) stringBuffer.charAt(i) > 65) {
						index = i;
						// 첫번째 인덱스에 대문자가 들어있을 확룰은 희박하나, _를 붙이지 않도록 설정
						if (i == 0) {
							stringBuffer.replace(index, index + 1, "" + (char) (stringBuffer.charAt(index) + ASCII_CASE_DIFF));
						} else {
							stringBuffer.replace(index, index + 1, "_" + (char) (stringBuffer.charAt(index) + ASCII_CASE_DIFF));
						}
						hasCap = true;
					}
				}
			} while (hasCap);
		}
		return stringBuffer.toString();
	}


	// tmp
	public Object dtoToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class clazz = obj.getClass();

		return map;
	}

	public String methodNameToProperty(String methodName) {
		char[] methodNameArray = methodName.substring(3).toCharArray();
		methodNameArray[0] = (char) (methodNameArray[0] + 32);
		return new String(methodNameArray);
	}


	public Set<?> getDtoSetFromParamMap(Map<String, String[]> paramMap, Class<? extends Object> clazz,
			int index) {

		String param;
		String paramValue;
		String prop;
		String propType;
//		Set<String> settedPropSet = new HashSet<>();
		Set<String> paramSet = paramMap.keySet();

		Method[] methods = clazz.getDeclaredMethods();

		Set<Object> dtoSet = new HashSet<>();

		// 일단 총 갯수가 2개 이상인 파라미터에 대해서만 작동하도록 돌려막기....
		for (int i = 0; i < index; i++) {
			try {
				Object dto = clazz.newInstance();
				Iterator<String> paramItr = paramSet.iterator();
				while (paramItr.hasNext()) {
					param = paramItr.next();
					if (paramMap.get(param).length < index)
						continue;
					paramValue = paramMap.get(param)[i];
//					param = adjustParamName(param);
//					System.out.println("요청 파라미터 : " + param + "  값 : " + paramValue);

					// dto에서 프로퍼티 가져오기
					for (Method method : methods) {
						prop = methodNameToProperty(method.getName());
						if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {
							try {

								// 이걸 여기서가 아니라 처음부터 받아와서 해야돼.
								System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName()
										+ "실행 - " + paramValue + " => " + prop);
//								paramType = paramValue.getClass().getSimpleName();
								propType = method.getParameters()[0].getType().getSimpleName();
//								System.out.println("파라미터 타입 : " + paramType + " 프로퍼티 타입 : " + propType);

//								if (paramType == propType)
//									method.invoke(dto, paramValue);
//								else {
								System.out.println("invoke : " + paramValue.getClass().getSimpleName() + " "
										+ paramValue + " => " + prop);
								switch (propType) {
								case "String":
									method.invoke(dto, paramValue);
									break;
								case "int":
									method.invoke(dto, Integer.parseInt(paramValue));
									break;
								case "Double":
									method.invoke(dto, Double.parseDouble(paramValue));
									break;
								case "Boolean":
									method.invoke(dto, Boolean.parseBoolean(paramValue));
									break;
								case "Date":
									method.invoke(dto, java.sql.Date.valueOf(paramValue));
									break;
								}
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
				System.out.println("dtoSet <= Dto : " + dto);
				dtoSet.add(dto);
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		// 확인용
		System.out.println("---- 쎗팅된 dtoSet");
		dtoSet.stream().forEach((d) -> System.out.println(d));
		System.out.println("뭬야");
		return dtoSet;
	}

	public List<?> getDtoListFromParamMap(Map<String, String[]> paramMap, Class<? extends Object> clazz,
			int index) {

		String param;
		String paramValue;
		String prop;
		String propType;
//		Set<String> settedPropSet = new HashSet<>();
		Set<String> paramSet = paramMap.keySet();

		Method[] methods = clazz.getDeclaredMethods();

		List<Object> dtoList = new ArrayList<>();

		// 일단 총 갯수가 2개 이상인 파라미터에 대해서만 작동하도록 돌려막기....
		for (int i = 0; i < index; i++) {
			try {
				Object dto = clazz.newInstance();
				Iterator<String> paramItr = paramSet.iterator();
				while (paramItr.hasNext()) {
					param = paramItr.next();
					if (paramMap.get(param).length < index)
						continue;
					paramValue = paramMap.get(param)[i];
//					param = adjustParamName(param);
//					System.out.println("요청 파라미터 : " + param + "  값 : " + paramValue);

					// dto에서 프로퍼티 가져오기
					for (Method method : methods) {
						prop = methodNameToProperty(method.getName());
						if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {
							try {

								// 이걸 여기서가 아니라 처음부터 받아와서 해야돼.
								System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName()
										+ "실행 - " + paramValue + " => " + prop);
//								paramType = paramValue.getClass().getSimpleName();
								propType = method.getParameters()[0].getType().getSimpleName();
//								System.out.println("파라미터 타입 : " + paramType + " 프로퍼티 타입 : " + propType);

//								if (paramType == propType)
//									method.invoke(dto, paramValue);
//								else {
								System.out.println("invoke : " + paramValue.getClass().getSimpleName() + " "
										+ paramValue + " => " + prop);
								switch (propType) {
								case "String":
									method.invoke(dto, paramValue);
									break;
								case "int":
									method.invoke(dto, Integer.parseInt(paramValue));
									break;
								case "Double":
									method.invoke(dto, Double.parseDouble(paramValue));
									break;
								case "Boolean":
									method.invoke(dto, Boolean.parseBoolean(paramValue));
									break;
								case "Date":
									method.invoke(dto, java.sql.Date.valueOf(paramValue));
									break;
								}
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
				System.out.println("dtoSet <= Dto : " + dto);
				dtoList.add(dto);
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		// 확인용
		System.out.println("---- 쎗팅된 dtoSet");
		dtoList.stream().forEach((d) -> System.out.println(d));
		System.out.println("뭬야");
		return dtoList;
	}

	// 보류 : get으로 대체
	// 요청 파라미터로 Dto 초기화(insert 하기 전 dto에 데이터 담을 때 사용)
	// 요청 파라미터의 name 과 프로퍼티 이름은 같아야 한다
	// set한 프로퍼티의 목록을 Set으로 리턴하면 좋을것같다
	// 팀프로젝트만 끝나면 삭제하자..
	public Set<String> setDtoFromParamMap(Map<String, String[]> paramMap, Object dto) {

		String param;
		String[] paramValue;
		String prop;
		Set<String> settedPropSet = new HashSet<>();
		Set<String> paramSet = paramMap.keySet();
		Iterator<String> paramItr = paramSet.iterator();

		String paramType;
		String propType;

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		// 파라미터 이름,값 가져오기
		// 하나의 키에 대해 값이 여러개일 수 있다
		// 1. 하나인지 여러개인지 체크
		// 2. 각 값에 대해서 다 대입하도록? > 타입이 컬렉션이어야 한다
		while (paramItr.hasNext()) {
			param = paramItr.next();
			paramValue = paramMap.get(param);
//			param = adjustParamName(param);
//			System.out.println("요청 파라미터 : " + param + "  값 : " + Arrays.toString(paramValue));

			// dto에서 프로퍼티 가져오기
			for (Method method : methods) {
				prop = methodNameToProperty(method.getName());
				if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {
					try {
						for (int i = 0; i < paramValue.length; i++) {
							System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName() + "실행 - "
									+ paramValue[i] + " => " + prop);
							paramType = paramValue[i].getClass().getSimpleName();
							propType = method.getParameters()[0].getType().getSimpleName();
							System.out.println("파라미터 타입 : " + paramType + " 프로퍼티 타입 : " + propType);

							if (paramType == propType)
								method.invoke(dto, paramValue[i]);
							else {
								System.out.println("paramvalue" + " " + paramValue.getClass().getSimpleName() + " "
										+ paramValue[i]);
								switch (propType) {
								case "String":
									method.invoke(dto, paramValue[0]);
									break;
								case "int":
									method.invoke(dto, Integer.parseInt(paramValue[0]));
									break;
								case "Double":
									method.invoke(dto, Double.parseDouble(paramValue[0]));
									break;
								case "Boolean":
									method.invoke(dto, Boolean.parseBoolean(paramValue[0]));
									break;
								case "Date":
									method.invoke(dto, java.sql.Date.valueOf(paramValue[0]));
									break;
								}
//								method.invoke(dto, castParamType(paramValue, propType));
							}
						}
//						if (paramValue.length == 1) {
//							method.invoke(dto, paramValue[0]);
//							settedPropSet.add(prop);
//						} else if (paramValue.length > 1) {
						// 파라미터 값이 여러개일 경우의 처리 필요
						// 여러개이면 아마 콜렉션에 추가하는 식으로
						// 이럴 경우엔 setter가 add가 될 것이다
//						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return settedPropSet;
	}


    /* get Setters by substr set */
    public Method[] getMethods(Class<?> clazz, String subStr){
        Set<Method> namedMethodSet = new HashSet();
		for (Method method : clazz.getMethods()){
			if (method.getName().contains(subStr))
				namedMethodSet.add(method);
		}
		return (Method[]) namedMethodSet.toArray();
    }

	public Method[] getMethods(Class<?> clazz, Pattern regex){
        Set<Method> namedMethodSet = new HashSet<>();
		for (Method method : clazz.getMethods()){
			if (regex.matcher(method.getName()).matches())
				namedMethodSet.add(method);
		}
		return (Method[]) namedMethodSet.toArray();
    }

	public Object getDtoFromParamMap(Map<String, String[]> paramMap, Class<?> clazz) {
//		Enumeration<String> parameterEnum = request.getParameterNames();

		String param;
		String[] paramValue;
		String prop;
		Set<String> settedPropSet = new HashSet<>();
		Set<String> paramSet = paramMap.keySet();
		Iterator<String> paramItr = paramSet.iterator();

		String paramType;
		String propType;

		Object dto = null;

		try {
			dto = clazz.newInstance();

			Method[] methods = clazz.getDeclaredMethods();
			// 파라미터 이름,값 가져오기
			// 하나의 키에 대해 값이 여러개일 수 있다
			// 1. 하나인지 여러개인지 체크
			// 2. 각 값에 대해서 다 대입하도록? > 타입이 컬렉션이어야 한다
			while (paramItr.hasNext()) {
				param = paramItr.next();
				paramValue = paramMap.get(param);
				param = snakeToCamel(param);

				System.out.println("요청 파라미터 : " + param + "  값 : " + Arrays.toString(paramValue));

				// dto에서 프로퍼티 가져오기
				for (Method method : methods) {
					prop = methodNameToProperty(method.getName());
					if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {

						for (int i = 0; i < paramValue.length; i++) {
							System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName() + "실행 - "
									+ paramValue[i] + " => " + prop);
							paramType = paramValue[i].getClass().getSimpleName();
							propType = method.getParameters()[0].getType().getSimpleName();
							System.out.println("파라미터 타입 : " + paramType + " 프로퍼티 타입 : " + propType);

							if (paramType == propType)
								method.invoke(dto, paramValue[i]);
							else {
								System.out.println("paramvalue" + " " + paramValue.getClass().getSimpleName() + " "
										+ paramValue[i]);
								switch (propType) {
								case "String":
									method.invoke(dto, paramValue[0]);
									break;
								case "int":
									method.invoke(dto, Integer.parseInt(paramValue[0]));
									break;
								case "Double":
									method.invoke(dto, Double.parseDouble(paramValue[0]));
									break;
								case "Boolean":
									method.invoke(dto, Boolean.parseBoolean(paramValue[0]));
									break;
								case "Date":
									method.invoke(dto, java.sql.Date.valueOf(paramValue[0]));
									break;
								}
//								method.invoke(dto, castParamType(paramValue, propType));
							}
						}
					}
//						if (paramValue.length == 1) {
//							method.invoke(dto, paramValue[0]);
//							settedPropSet.add(prop);
//						} else if (paramValue.length > 1) {
					// 파라미터 값이 여러개일 경우의 처리 필요
					// 여러개이면 아마 콜렉션에 추가하는 식으로
					// 이럴 경우엔 setter가 add가 될 것이다
//						}

				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException e) {
			e.printStackTrace();
		}

		return dto;
	}


	public Method[] methodFilter(Method[] methods, String substr){
		Set<Method> namedMethodSet = new HashSet();
		for (Method method : methods){
			if (method.getName().contains(substr))
				namedMethodSet.add(method);
		}
		return (Method[]) namedMethodSet.toArray();
	}


}
