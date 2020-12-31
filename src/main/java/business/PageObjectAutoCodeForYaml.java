package business;

import com.esotericsoftware.yamlbeans.YamlReader;

import utils.TestLog;

import java.io.*;
import java.util.*;

//通过解析xml文件自动生成对象库代码
public class PageObjectAutoCodeForYaml {
    TestLog log = new TestLog(this.getClass());
    static String path = "src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.yaml";

    public static void autoCode() throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Can't find " + path);
        }
        YamlReader yamlReader = new YamlReader(new FileReader(file));
        Object yamlObject = yamlReader.read();
        Map yamlMap = (Map) yamlObject;
        ArrayList<HashMap<String, Object>> pages = (ArrayList<HashMap<String, Object>>) yamlMap.get("pages");//page列表


    }


    public static void main(String[] args) throws Exception {
        // TODO 自动生成的方法存根
    }

}
