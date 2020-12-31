package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
                        map.put(entry.getKey(), nextNode.asText().replaceAll("\"",""));
                    }

                } else if (nextNode.isArray()) {
                    Iterator<JsonNode> itArray = nextNode.iterator();
                    while (itArray.hasNext()) {
                        JsonNode jsonNode = itArray.next();
                        if (jsonNode.isValueNode()) {
                            if (!map.containsKey(entry.getKey())) {
                                map.put(entry.getKey(), jsonNode.asText().replaceAll("\"",""));
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

    public Object getValue(String key) {
        return map.get(key);
    }

    public static void main(String[] args) throws IOException {
        String json = "{\n" +
                "  \"code\" : 10000,\n" +
                "  \"succeed\" : true,\n" +
                "  \"msg\" : \"成功\",\n" +
                "  \"data\" : {\n" +
                "    \"total\" : 1,\n" +
                "    \"size\" : 20,\n" +
                "    \"current\" : 1,\n" +
                "    \"records\" : [ {\n" +
                "      \"clueId\" : \"1337572295961919489\",\n" +
                "      \"clueType\" : {\n" +
                "        \"value\" : 1001,\n" +
                "        \"label\" : \"线索客户\"\n" +
                "      },\n" +
                "      \"area\" : null,\n" +
                "      \"provinceCode\" : \"110000\",\n" +
                "      \"provinceName\" : \"北京\",\n" +
                "      \"cityCode\" : \"110100\",\n" +
                "      \"cityName\" : \"北京市\",\n" +
                "      \"industryCode\" : \"\",\n" +
                "      \"industryName\" : \"\",\n" +
                "      \"returnPublic\" : {\n" +
                "        \"value\" : 1,\n" +
                "        \"label\" : \"是\"\n" +
                "      },\n" +
                "      \"clueDepot\" : {\n" +
                "        \"value\" : 0,\n" +
                "        \"label\" : \"否\"\n" +
                "      },\n" +
                "      \"flowRecorded\" : null,\n" +
                "      \"received\" : {\n" +
                "        \"value\" : 0,\n" +
                "        \"label\" : \"否\"\n" +
                "      },\n" +
                "      \"receivedName\" : null,\n" +
                "      \"firstSaleName\" : null,\n" +
                "      \"recordText\" : \"很过分的说法是发生大幅度\",\n" +
                "      \"opportunityFlag\" : 0,\n" +
                "      \"recordId\" : \"1337578161546571778\",\n" +
                "      \"lastFollower\" : \"周云腾\",\n" +
                "      \"lastFollowType\" : {\n" +
                "        \"value\" : 1,\n" +
                "        \"label\" : \"电话拜访\"\n" +
                "      },\n" +
                "      \"weChat\" : \"实打实大所多\",\n" +
                "      \"trafficSource\" : \"抖音\",\n" +
                "      \"pageSource\" : \"\",\n" +
                "      \"keyWords\" : \"\",\n" +
                "      \"mainContactName\" : \"联系人\",\n" +
                "      \"mainContactTel\" : [ \"15902379111\" ],\n" +
                "      \"clueDepotTime\" : null,\n" +
                "      \"receiveUserName\" : null,\n" +
                "      \"focusFlag\" : 0,\n" +
                "      \"coopTime\" : \"2020-12-01 00:00:00\",\n" +
                "      \"receiveTime\" : null,\n" +
                "      \"createTime\" : \"2020-12-12 09:37:41\",\n" +
                "      \"customerClassify\" : null,\n" +
                "      \"locationFlag\" : {\n" +
                "        \"value\" : 5,\n" +
                "        \"label\" : \"合作客户\"\n" +
                "      },\n" +
                "      \"depotOverDuration\" : null,\n" +
                "      \"overTime\" : null,\n" +
                "      \"overTimeFlag\" : 0,\n" +
                "      \"authorityType\" : 0,\n" +
                "      \"distribute\" : {\n" +
                "        \"value\" : 0,\n" +
                "        \"label\" : \"否\"\n" +
                "      },\n" +
                "      \"firstFollowType\" : {\n" +
                "        \"value\" : 1,\n" +
                "        \"label\" : \"电话拜访\"\n" +
                "      },\n" +
                "      \"firstReceiveTime\" : null,\n" +
                "      \"firstFollowTime\" : \"2020-12-12 10:00:00\",\n" +
                "      \"lastFollowTime\" : \"2020-12-12 10:00:00\",\n" +
                "      \"businessTime\" : \"2020-12-01 00:00\",\n" +
                "      \"lastReceiveUserName\" : null,\n" +
                "      \"customerTime\" : \"2020-12-12 10:04:16\",\n" +
                "      \"status\" : {\n" +
                "        \"value\" : 1,\n" +
                "        \"label\" : \"有效\"\n" +
                "      },\n" +
                "      \"intention\" : {\n" +
                "        \"value\" : 3,\n" +
                "        \"label\" : \"强烈\"\n" +
                "      },\n" +
                "      \"interviewFlag\" : {\n" +
                "        \"value\" : 0,\n" +
                "        \"label\" : \"否\"\n" +
                "      },\n" +
                "      \"returnPublicTime\" : \"2020-12-12 09:53:33\",\n" +
                "      \"crmPrincipalName\" : \"周云腾\",\n" +
                "      \"crmPrincipalId\" : \"1305320785377931265\",\n" +
                "      \"principalId\" : \"1305320785377931265\",\n" +
                "      \"followFlag\" : {\n" +
                "        \"value\" : 1,\n" +
                "        \"label\" : \"是\"\n" +
                "      },\n" +
                "      \"principalName\" : \"周云腾\",\n" +
                "      \"followType\" : null,\n" +
                "      \"firstSaleOrgName\" : null,\n" +
                "      \"createdBy\" : \"1305320785377931265\",\n" +
                "      \"createdByName\" : \"周云腾\",\n" +
                "      \"followOverFlag\" : 0,\n" +
                "      \"intendedFlag\" : 1,\n" +
                "      \"secondSaleName\" : \"\",\n" +
                "      \"firstVisitor\" : \"周云腾\",\n" +
                "      \"interviewCount\" : 0,\n" +
                "      \"qq\" : \"1234655444\"\n" +
                "    } ]\n" +
                "  },\n" +
                "  \"error\" : null\n" +
                "}";

        JsonUtils jsonUtils = new JsonUtils();
        // System.out.println(jsonUtils.getMap(json));
        System.out.println(jsonUtils.getValue("mainContactTel", json));


    }

}


