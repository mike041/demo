package controller;

import com.jayway.jsonpath.JsonPath;
import entity.Requisition;
import entity.Response;
import excelEntity.*;
import interf.Request;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import utils.ExcelUtils;

import java.util.*;


public class RequisitionControl {
    ExcelUtils utils;
    Sheet sheet;

    public Map<String, Object> parameterMap = new HashMap<>();


    /**
     * 设置请求对象
     */

    public Requisition setRequisition(ExcelRequisition excelRequisition) {

        String type = excelRequisition.getType();
        String url = Assembly.assembly(excelRequisition.getUrl(), this.parameterMap, this);
        String body = Assembly.assembly(excelRequisition.getBody(), this.parameterMap, this);
        Request request = new Request();
        String rerponse = request.sendRequest(type, url, body);
        Response requisitionResponse = this.setResponse(rerponse);
        Requisition e = Requisition.builder()
                .type(type)
                .url(url)
                .body(body)
                .response(requisitionResponse).build();

        return e;
    }

    /**
     * 发送请求并获取响应
     */

    public static Response setResponse(String rerponse) {
        int code = JsonPath.read(rerponse, "$.code");
        Object data = JsonPath.read(rerponse, "$.data");
        String error = JsonPath.read(rerponse, "$.error");
        String msg = JsonPath.read(rerponse, "$.msg");
        boolean succeed = JsonPath.read(rerponse, "$.succeed");
        Response response = Response.builder()
                .code(code)
                .data(data)
                .error(error)
                .msg(msg)
                .succeed(succeed)
                .jsonResponse(rerponse)
                .build();
        return response;
    }


    public Map<String, String> setToSaveMap(ExcelRequisition excelRequisition, Response response) {

        Map<String, String> toSaveMap = new HashMap<>();
        String toSave = excelRequisition.getToSave();
        {
            if (!toSave.isEmpty() && toSave != null) {
                if (toSave.contains(",")) {
                    String[] saves = toSave.split(",");
                    for (int i = 0; i < saves.length; i++) {
                        String key = saves[i].split(":")[0];
                        String formate = saves[i].split(":")[1];
                        String value = JsonPath.read(response.getResponse(), formate);
                        toSaveMap.put(key, value);
                    }
                } else {
                    String key = toSave.split(":")[0];
                    String formate = toSave.split(":")[1];
                    String value = JsonPath.read(response.getResponse(), formate);
                    toSaveMap.put(key, value);
                }

            }
            return toSaveMap;
        }
    }


}
