package util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesReader {

    private static final ResourceBundle resource= ResourceBundle.getBundle("config.config");
    public static Map<String,String> readProperties(){
        Map<String,String> map=new HashMap<>();
        for (String key:resource.keySet()){
            String value=resource.getString(key);
            map.put(key,value);
        }
        return map;
    }
    public static String getKey(String key){
        return resource.getString(key);
    }
}
