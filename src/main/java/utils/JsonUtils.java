package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.io.IOException;
import java.util.*;
import java.util.Map.*;


public class JsonUtils {
    private Map<String, Object> map = new HashMap<>();

    public void jsonLeaf(JsonNode node) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                Entry<String, JsonNode> entry = it.next();
                JsonNode nextNode = entry.getValue();
                if (nextNode.isValueNode()) {
                    if (!map.containsKey(entry.getKey())) {
                        map.put(entry.getKey(), nextNode.asText().replaceAll("\"", ""));
                    }

                } else if (nextNode.isArray()) {
                    Iterator<JsonNode> itArray = nextNode.iterator();
                    while (itArray.hasNext()) {
                        JsonNode jsonNode = itArray.next();
                        if (jsonNode.isValueNode()) {
                            if (!map.containsKey(entry.getKey())) {
                                map.put(entry.getKey(), jsonNode.asText().replaceAll("\"", ""));
                            }
                        } else {
                            jsonLeaf(jsonNode);
                        }
                    }
                } else {
                    jsonLeaf(entry.getValue());
                }
            }
        }

       /* if (node.isArray()) {
            Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()) {
                JsonNode jsonNode = it.next();
                jsonLeaf(jsonNode);
            }
        }*/
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Map<String, Object> getMap(JsonNode node) {
        this.jsonLeaf(node);
        return map;
    }

    public Map<String, Object> getMap(String json) {
        ObjectMapper jackson = new ObjectMapper();
        JsonNode node = null;
        try {
            node = jackson.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jsonLeaf(node);
        return map;
    }


    public Object getValue(String key, JsonNode node) {
        this.getMap(node);
        return map.get(key);
    }

    public Object getValue(String key, String json) {
        JsonNode node = toJsonNode(json);
        this.getMap(node);
        return map.get(key);
    }

    public JsonNode toJsonNode(String json) {
        ObjectMapper jackson = new ObjectMapper();
        try {
            return jackson.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getJsonKey(String head, JSONObject jsonObj) {
        ArrayList<String> keys = new ArrayList<>();
        Set<Entry<String, Object>> set = jsonObj.entrySet();
        Iterator<Entry<String, Object>> iter = set.iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();

            keys.add(head == null ? key : head + key);
            //判断value类型
            if (value instanceof JSONObject) {
                ArrayList<String> list = getJsonKey(head == null ? key + "-" : head + key + "-", (JSONObject) value);
                keys.addAll(list);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                if (jsonArray.size() != 0) {
                    Object obj = jsonArray.get(0);
                    //对array中的类型进行判断
                    if (obj instanceof JSONObject) {
                        ArrayList<String> list = getJsonKey(head == null ? key + "-" : head + key + "-", (JSONObject) jsonArray.get(0));
                        keys.addAll(list);
                    } else {
                        keys.add(head == null ? key : head + key);
                    }
                }
            }
        }
        return keys;
    }

    public Object getValue(String key) {
        return map.get(key);
    }

    public static void main(String[] args) throws IOException {
        String sourceJson = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"ok\",\n" +
                "    \"time\": \"2016-10-13 16:13:48\",\n" +
                "    \"data\": {\n" +
                "        \"count\": 1,\n" +
                "        \"list\": [\n" +
                "            {\n" +
                "                \"pid\": \"商品id\",\n" +
                "                \"title\": \"商品名称\",\n" +
                "                \"price\": \"价格\",\n" +
                "                \"desc\": \"商品描述\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        String targetJson = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"ok\",\n" +
                "    \"time\": \"2016-10-13 16:13:48\",\n" +
                "    \"data\": {\n" +
                "        \"count\": 1,\n" +
                "        \"list\": [\n" +
                "            {\n" +
                "                \"pid\": \"edb1eb3e82964efba7117fa7feed2f53\",\n" +
                "                \"title\": \"牛B辣条\",\n" +
                "                \"price\": \"12.00\",\n" +
                "                \"desc\": \"炒鸡好吃的辣条，辣条里面的领导者。\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        JsonUtils jsonUtils = new JsonUtils();
        JSONObject source = JSONObject.parseObject(sourceJson);
        JSONObject target = JSONObject.parseObject(targetJson);

        // System.out.println(jsonUtils.getMap(json));\
        ArrayList<String> sourceList = jsonUtils.getJsonKey(null, source);
        ArrayList<String> targetList = jsonUtils.getJsonKey(null, target);
        Assert.assertEquals(sourceList,targetList);


    }

}


