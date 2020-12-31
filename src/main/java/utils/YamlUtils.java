package utils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YamlUtils {
    static TestLog log = new TestLog();

    public static HashMap<String, Object> getSetUp(HashMap<String, Object> aClass, String type) {
        if (aClass.containsKey(type)) {
            return (HashMap<String, Object>) aClass.get(type);
        }
        log.error(aClass + "未配置该字段");
        return new HashMap<>();
    }


    public static ArrayList<HashMap<String, Object>> getClassNodes(String path) throws
            FileNotFoundException, YamlException {
        YamlReader yamlReader = new YamlReader(new FileReader(path));
        Map yamlMap = (Map) yamlReader.read();
        ArrayList<HashMap<String, Object>> classNodes = (ArrayList<HashMap<String, Object>>) yamlMap.get("classes");
        return classNodes;
    }

    public static HashMap<String, Object> getClass(String path, String className) throws
            FileNotFoundException, YamlException {
        HashMap<String, Object> aClass = new HashMap<>();
        YamlReader yamlReader = new YamlReader(new FileReader(path));
        Map yamlMap = (Map) yamlReader.read();
        ArrayList<HashMap<String, Object>> classNodes = (ArrayList<HashMap<String, Object>>) yamlMap.get("classes");
        for (int i = 0; i < classNodes.size(); i++) {
            HashMap<String, Object> classNode = classNodes.get(i);//获取class节点
            aClass = (HashMap<String, Object>) classNode.get("class");
            if (className.equals(aClass.get("className"))) {
                return aClass;
            }
            log.error(className + "未配置该测试类");
        }
        return aClass;
    }


    public static void main(String[] args) {
        HashMap<String, Object> aClass;
        HashMap<String, Object> methodSetUp = new HashMap<>();
        HashMap<String, Object> calssSetUp = new HashMap<>();
        String path = "src\\main\\resources\\ClassSetUp.yaml";
        try {
            aClass = YamlUtils.getClass(path, "DemoTest");
            methodSetUp = YamlUtils.getSetUp(aClass, "methodSetUp");
            calssSetUp = YamlUtils.getSetUp(aClass, "classSetUp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        System.out.println(calssSetUp);
        System.out.println(methodSetUp);

    }
}
