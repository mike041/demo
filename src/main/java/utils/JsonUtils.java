package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class JsonUtils {
    String json;

    public JsonUtils(String json) {
        this.json = json;
    }

    public void getFastJson() {
        JSONArray j = JSON.parseObject(json).getJSONObject("data").getJSONArray("records");

    }

}
