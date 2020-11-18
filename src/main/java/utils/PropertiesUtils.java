package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    public String getProperties(String key) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\main\\resources\\config"));
            return properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
