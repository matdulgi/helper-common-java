package com.dulgi.helper.common;

import com.dulgi.helper.annotation.NeedToChange;
import com.sun.org.apache.bcel.internal.generic.FSUB;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;

public class PropertiesParser {
    /**
     * it needs possible to parse Integrated properties file including properties of multi subject
     *
     * # several way to use part of properties file by subject, and requirement according to it
     * ### set base subject string (many program use this way)
     *  - must include the base subject string on start of each line (^)
     *  -
     * ### check subString
     *  -
     *
     * each property can include own base option, or not
     *
     * # example
     * ### real properties for base1
     *  - property1
     *  - property2
     *
     * ### Integrated properties file
     *  - base1.property1
     *  - base1.property2
     *  - base2.property1
     *  - base2.property2
     *
     * it needs to remove base part
     * prevent the duplication
     *
     * # expected functions
     * load a Properties File
     * evaluate the properties
     *
     * (1. set base)
     * (2. call parse method with subject argument)
     */
    private String subject;
    @NeedToChange("base is temp name, means the base name of each property")

    public PropertiesParser(){ }
    public PropertiesParser(String subject){
        this.subject = subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public Properties parseProps(String filePath) {
        if (subject == null || subject.equals("") ){

        }

        File file = null;
        try {
            file = new File(ClassLoader.getSystemResource(filePath).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(file)){
            props.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private String getRegex(String subject){
        return "";
    }

}
