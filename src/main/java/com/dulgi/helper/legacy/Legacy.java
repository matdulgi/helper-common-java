package com.dulgi.helper.legacy;

import com.dulgi.helper.common.Core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Legacy {
    Core core;

    //Jdbc
    // db조회 결과 ResultSet로 dto를 초기화
    // public void setDtoWithResultSetRow(Object dto, ResultSet rs) {
    // 	String column;
    // 	String columnType;// 확인용
    // 	Object columnValue;
    // 	try {
    // 		int columnCount = rs.getMetaData().getColumnCount();

    // 		// resultSet의 다음 값을 불러와 dto에 저장
    // 		// !주의 : getObject로 NUMBER을 가져오면 int가 아닌 BicDemical로 전달된다
    // 		for (int i = 1; i <= columnCount; i++) {
    // 			column = rs.getMetaData().getColumnName(i);
    // 			columnType = rs.getMetaData().getColumnTypeName(i);
    // 			switch (columnType) {
    // 			case "NUMBER":
    // 				columnValue = rs.getInt(i);
    // 				break;
    // 			case "VARCHAR2":
    // 				columnValue = rs.getString(i);
    // 				break;
    // 			default:
    // 				columnValue = rs.getObject(i);
    // 				break;
    // 			}
    // 			System.out.println(i + "번째 칼럼 : " + column + " 타입 : " + columnType + " 값 : " + columnValue);
    // 			setPropertyWithColumn(dto, column, columnValue);
    // 		}
    // 	} catch (SQLException e) {
    // 		e.printStackTrace();
    // 	}
    // }


    // web

    //	// 보류 : clss를 받도록 개선
    //	public Object getDtoFromParamMap(Map<String, String[]> paramMap, Object dto) {
    ////	Enumeration<String> parameterEnum = request.getParameterNames();
    //
    //		String param;
    //		String[] paramValue;
    //		String prop;
    //		Set<String> settedPropSet = new HashSet<>();
    //		Set<String> paramSet = paramMap.keySet();
    //		Iterator<String> paramItr = paramSet.iterator();
    //
    //		String paramType;
    //		String propType;
    //
    //		Class clazz = dto.getClass();
    //		Method[] methods = clazz.getDeclaredMethods();
    //		// 파라미터 이름,값 가져오기
    //		// 하나의 키에 대해 값이 여러개일 수 있다
    //		// 1. 하나인지 여러개인지 체크
    //		// 2. 각 값에 대해서 다 대입하도록? > 타입이 컬렉션이어야 한다
    //		while (paramItr.hasNext()) {
    //			param = paramItr.next();
    //			paramValue = paramMap.get(param);
    //			param = snakeToKamel(param);
    //
    //			System.out.println("요청 파라미터 : " + param + "  값 : " + Arrays.toString(paramValue));
    //
    //			// dto에서 프로퍼티 가져오기
    //			for (Method method : methods) {
    //				prop = methodNameToProperty(method.getName());
    //				if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {
    //					try {
    //						for (int i = 0; i < paramValue.length; i++) {
    //							System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName() + "실행 - "
    //									+ paramValue[i] + " => " + prop);
    //							paramType = paramValue[i].getClass().getSimpleName();
    //							propType = method.getParameters()[0].getType().getSimpleName();
    //							System.out.println("파라미터 타입 : " + paramType + " 프로퍼티 타입 : " + propType);
    //
    //							if (paramType == propType)
    //								method.invoke(dto, paramValue[i]);
    //							else {
    //								System.out.println("paramvalue" + " " + paramValue.getClass().getSimpleName() + " "
    //										+ paramValue[i]);
    //								switch (propType) {
    //								case "String":
    //									method.invoke(dto, paramValue[0]);
    //									break;
    //								case "int":
    //									method.invoke(dto, Integer.parseInt(paramValue[0]));
    //									break;
    //								case "Double":
    //									method.invoke(dto, Double.parseDouble(paramValue[0]));
    //									break;
    //								case "Boolean":
    //									method.invoke(dto, Boolean.parseBoolean(paramValue[0]));
    //									break;
    //								case "Date":
    //									method.invoke(dto, java.sql.Date.valueOf(paramValue[0]));
    //									break;
    //								}
    ////							method.invoke(dto, castParamType(paramValue, propType));
    //							}
    //						}
    ////					if (paramValue.length == 1) {
    ////						method.invoke(dto, paramValue[0]);
    ////						settedPropSet.add(prop);
    ////					} else if (paramValue.length > 1) {
    //						// 파라미터 값이 여러개일 경우의 처리 필요
    //						// 여러개이면 아마 콜렉션에 추가하는 식으로
    //						// 이럴 경우엔 setter가 add가 될 것이다
    ////					}
    //					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
    //						e.printStackTrace();
    //					}
    //				}
    //			}
    //		}
    //		// 확인용
    //		checkSettedProperties(dto);
    //		return dto;
    //	}


    //etc
    //no
    // 값이 있는 프로퍼티만 Map 으로 반환
    // public Map<String, Object> getValidPropertyMap(Object dto) {
    // 	String property;
    // 	Object propertyValue;
    // 	Map<String, Object> propertieMap = new HashMap<>();

    // 	Class clazz = dto.getClass();
    // 	Method[] methods = clazz.getDeclaredMethods();

    // 	System.out.println(dto.getClass().getSimpleName() + "의 프로퍼티 목록");
    // 	for (Method method : methods) {
    // 		// Class에는 다른 get메서드가 존재한다
    // 		if (method.getName().contains("get")) {
    // 			property = methodNameToProperty(method.getName());

    // 			try {
    // 				propertyValue = method.invoke(dto);
    // 				if (propertyValue != null && propertyValue != "") {
    // 					System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
    // 					propertieMap.put(property, method.invoke(dto));
    // 				}
    // 			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
    // 				e.printStackTrace();
    // 			}
    // 		}
    // 	}
    // 	return propertieMap;
    // }

    //no
    // PreparedStatement에 순서를 맞추기 위해 List 사용
    // 프로퍼티 값이 존재하는지 미리 확인.. 근데 이러면 자원 낭비가 너무 많이 된다
    // public List<String> getValidPropertyList(Object dto) {
    // 	String property;
    // 	Object propertyValue;
    // 	List<String> propertyList = new ArrayList<>();

    // 	Class clazz = dto.getClass();
    // 	Method[] methods = clazz.getDeclaredMethods();

    // 	System.out.println("memberDto의 프로퍼티 목록");
    // 	for (Method method : methods) {
    // 		if (method.getName().contains("get")) {
    // 			try {
    // 				property = methodNameToProperty(method.getName());
    // 				propertyValue = method.invoke(dto);
    // 				if (propertyValue != null && propertyValue != "") {
    // 					System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
    // 					propertyList.add(property);
    // 				}
    // 			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
    // 				e.printStackTrace();
    // 			}
    // 		}
    // 	}
    // 	return propertyList;
    // }

    // public List<Object> getValidPropertyValueList(Object dto) {
    // 	String property;
    // 	Object propertyValue;
    // 	List<Object> propertyValueList = new ArrayList<>();

    // 	Class clazz = dto.getClass();
    // 	Method[] methods = clazz.getDeclaredMethods();

    // 	System.out.println("memberDto의 프로퍼티 목록");
    // 	for (Method method : methods) {
    // 		if (method.getName().contains("get")) {
    // 			try {
    // 				property = methodNameToProperty(method.getName());
    // 				propertyValue = method.invoke(dto);
    // 				if (propertyValue != null && propertyValue != "") {
    // 					System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
    // 					propertyValueList.add(propertyValue);
    // 				}
    // 			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
    // 				e.printStackTrace();
    // 			}
    // 		}
    // 	}
    // 확인
    // 	List<String> propertyList = getValidPropertyList(dto);
    // 	Iterator<String> propertyItr = propertyList.iterator();
    // 	Iterator<Object> propertyValueItr = propertyValueList.iterator();
    // 	System.out.println("ㅇList에 저장된 프로퍼티 값 ");
    // 	while (propertyValueItr.hasNext() && propertyItr.hasNext()) {
    // 		System.out.println("프로퍼티 : " + propertyItr.next() + " 값 : " + propertyValueItr.next());
    // 	}
    // 	return propertyValueList;
    // }


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
                param = core.snakeToCamel(param);

                System.out.println("요청 파라미터 : " + param + "  값 : " + Arrays.toString(paramValue));

                // dto에서 프로퍼티 가져오기
                for (Method method : methods) {
                    prop = core.setterToProp(method.getName());
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

            // get properties from Dto
            for (Method method : methods) {
                prop = core.setterToProp(method.getName());
                if (method.getName().contains("set") && prop.equals(param) && !paramValue.equals("")) {
                    try {
                        for (int i = 0; i < paramValue.length; i++) {
                            System.out.println(" - " + dto.getClass().getSimpleName() + " " + method.getName() + "실행 - "
                                    + paramValue[i] + " => " + prop);
                            paramType = paramValue[i].getClass().getSimpleName();
                            propType = method.getParameters()[0].getType().getSimpleName();
                            System.out.println("parameter type : " + paramType + " property type : " + propType);

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
                        prop = core.setterToProp(method.getName());
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
                        prop = core.setterToProp(method.getName());
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


}
