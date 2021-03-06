package com.dulgi.helper.common;

import com.dulgi.helper.annotation.Moved;
import com.dulgi.helper.annotation.NeedToChange;
import com.dulgi.helper.regex.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

/*
	sturucture
	* core needs to implement several functions of intefaces - changed to other core
	* core class's method could be static because it's doesn't used to client
	* Entity e.g. Properties,DTO extends CommonEntity 
*/
public class Core {
    Logger logger = LoggerFactory.getLogger(Core.class);

    @NeedToChange("this values can be moved to String converter")
    public final static int ASCII_CASE_DIFF = 32;
    public final static int ASCII_MIN_LO_CASE = 97;
    public final static int ASCII_MAX_LO_CASE = 122;
    public final static int ASCII_MIN_UP_CASE = 65;
    public final static int ASCII_MAX_UP_CASE = 91;

    public Core() {
    }


    public boolean isCamelStyle(String str) {
        if (!str.contains("_")) {
            logger.warn("args not contain _");
            return false;
        }
        if (str.charAt(0) == '_') {
            logger.warn("first character is _");
            return false;
        }
        //needs more regex condition
        if (Pattern.matches(Regex.CAMEL.getRegex(), str)) return true;
        else return false;
    }

    public boolean isPropertyStyle(String str) {
        return Pattern.matches(Regex.PROPERTY.getRegex(), str);
    }

    public boolean isSnakeStyle(String str) {
        return Pattern.matches(Regex.SNAKE.getRegex(), str);
    }

    public String sepStrToCamel(String str, char delimiter) {
        // not snake ... need more condition
        String type = "";
        switch (delimiter) {
            case '_':
                type = "snake";
                break;
            case '.':
                type = "proeprty";
                break;
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

    // interate ???????????? ?????? for?????? ?????? i??? ???????????? ????????? ???????????? ????????? index??? ??????????????? ?????? ????????? ???????????????.
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
                        // ????????? ???????????? ???????????? ???????????? ????????? ????????????, _??? ????????? ????????? ??????
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

	@Moved("PropertiesParser")
    public static Properties loadProps(String filePath) {
        Properties props = new Properties();
        try {
            File file = new File(ClassLoader.getSystemResource(filePath).toURI());
            props.load(new FileInputStream(file));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String setterToProp(String methodName) {
        char[] methodNameArray = methodName.substring(3).toCharArray();
        methodNameArray[0] = (char) (methodNameArray[0] + 32);
        return new String(methodNameArray);
    }


    /* get Setters by substr set
     * basically get Declared Method. (considered to add option)
     * */
    public Method[] getMethods(Class<?> clazz, String subStr) {
        Set<Method> namedMethodSet = new HashSet();
        for (Method method : clazz.getMethods()) {
            if (method.getName().contains(subStr))
                namedMethodSet.add(method);
        }
        return namedMethodSet.stream().toArray(Method[]::new);
    }


    public Method[] getMethods(Class<?> clazz, Pattern regex) {
        Set<Method> methodSet = new HashSet<>();
        for (Method method : clazz.getMethods()) {
            if (regex.matcher(method.getName()).matches())
                methodSet.add(method);
        }
        return methodSet.stream().toArray(Method[]::new);
    }

    public Method[] methodFilter(Method[] methods, String substr) {
        Set<Method> namedMethodSet = new HashSet();
        for (Method method : methods) {
            if (method.getName().contains(substr))
                namedMethodSet.add(method);
        }
        return namedMethodSet.stream().toArray(Method[]::new);
    }


}
