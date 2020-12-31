package business;

import com.jayway.jsonpath.JsonPath;
import entity.ExcelRequisition;
import entity.Requisition;
import interf.Request;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequisitionControl {
    public Map<String, Object> parameterMap = new HashMap<>();

    /**
     * 从excel获取整个请求list
     */

    public List<ExcelRequisition> getExcelRequisitions(String path) {
        List<ExcelRequisition> excelRequisitions = new ArrayList<>();
        ExcelUtils excelUtils = new ExcelUtils(path);
        excelUtils.getSheetData();
        for (int i = 2; i <= excelUtils.getSheet().getLastRowNum() + 1; i++) {
            ExcelRequisition excelRequisition = new ExcelRequisition();
            excelRequisition.setDescription(excelUtils.getCellData(i, ExcelRequisition.anEnum.DESCRIPTION.row));
            excelRequisition.setType(excelUtils.getCellData(i, ExcelRequisition.anEnum.TYPE.row));
            excelRequisition.setUrl(excelUtils.getCellData(i, ExcelRequisition.anEnum.URL.row));
            excelRequisition.setUrlParameters(excelUtils.getCellData(i, ExcelRequisition.anEnum.URLPARAMETERS.row));
            excelRequisition.setBody(excelUtils.getCellData(i, ExcelRequisition.anEnum.BODY.row));
            excelRequisition.setBodyParameters(excelUtils.getCellData(i, ExcelRequisition.anEnum.BODYPARAMETERS.row));
            excelRequisition.setResponse(excelUtils.getCellData(i, ExcelRequisition.anEnum.RESPONSE.row));
            excelRequisitions.add(excelRequisition);
        }
        return excelRequisitions;
    }

    /**
     * 设置请求对象
     */

    public Requisition setRequisition(ExcelRequisition e) {
        Requisition requisition = new Requisition();
        String url = e.getUrl();
        String urlParameters = e.getUrlParameters();
        String body = e.getBody();
        String bodyParameters = e.getBodyParameters();
        requisition.setType(e.getType());
        requisition.setUrl(this.setParameter(url, urlParameters));
        requisition.setBody(this.setParameter(body, bodyParameters));
        return requisition;
    }


    /**
     * 将参数组合为最终结果
     */
    public String setParameter(String value, String parameters) {
        while (value.contains("%s")) {
            if (parameters.contains(",")) {
                String[] aParameters = parameters.split(",");
                for (String parameter : aParameters
                ) {
                    if (parameter.contains("\"")) {
                        value = value.replaceFirst("%s", parameter.replaceAll("\"", ""));
                    } else {
                        value = value.replaceFirst("%s", parameterMap.get(parameter).toString());
                    }
                }
            } else if (parameters.contains("\"")) {
                value = value.replaceFirst("%s", parameters.replaceAll("\"", ""));
            } else {
                value = value.replaceFirst("%s", parameterMap.get(parameters).toString());
            }
        }
        return value;
    }

    /**
     * 将需保存的响应结果的值保存到map
     */

    public void addParameterMap(String response, String responseData) {
        if (null != responseData && !responseData.equals("0") && !responseData.isEmpty()) {
            if (responseData.contains(",")) {
                String[] aUrlParameters = responseData.split(",");
                for (String urlParameter : aUrlParameters
                ) {
                    String[] bUrlParameters = urlParameter.split(":");
                    String value = JsonPath.read(response, bUrlParameters[1]).toString();
                    this.parameterMap.put(bUrlParameters[0], value);
                }
            } else {
                String[] aUrlParameters = responseData.split(":");
                Object value = JsonPath.read(response, aUrlParameters[1]);
                this.parameterMap.put(aUrlParameters[0], value);
            }
        }

    }

    public static void main(String[] args) {
        String path = "F:\\UITest\\新增入驻.xlsx";
        RequisitionControl requisitionControl = new RequisitionControl();
        List<ExcelRequisition> excelRequisitions = requisitionControl.getExcelRequisitions(path);
        for (ExcelRequisition e : excelRequisitions
        ) {
            System.out.println(e.getDescription() + "开始");
            Request request = new Request();

            Requisition requisition = requisitionControl.setRequisition(e);
            String type = requisition.getType();
            String url = requisition.getUrl();
            String body = requisition.getBody();

            String response = "";
            if ("post".equalsIgnoreCase(type)) {
                response = request.post(url, body, request.token);
            } else {
                response = request.get(url, body, request.token);

            }
            requisition.setResponse(response);

            String responseData = e.getResponse();

            requisitionControl.addParameterMap(response, responseData);
            System.out.println(e.getDescription() + "结束");

        }

    }

}
