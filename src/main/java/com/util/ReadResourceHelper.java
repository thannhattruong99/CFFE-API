package com.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReadResourceHelper {
    public static List<String> loadResource(String path){
        List<String> result = new ArrayList<>();
        File file = null;
        Scanner myReader = null;
        try {
            file = ResourceUtils.getFile("classpath:" + path);
            myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                result.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(myReader != null){
                myReader.close();
            }
        }
        return result;
    }

    public static List<String> getResourceList(String path) {
        List<String> list = new ArrayList<>();
        ResourceBundle rb = ResourceBundle.getBundle(path);
        Enumeration<String> key = rb.getKeys();
        while (key.hasMoreElements()) {
            list.add(key.nextElement());
        }
        return list;
    }
}
