package entity;

import lombok.Getter;
import lombok.Setter;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mikezhou
 * @description 从excel中获取的数据
 */

@Setter
@Getter
public class ExcelRequisition {
    private String description;
    private String type;
    private String url;
    private String urlParameters;
    private String body;
    private String bodyParameters;
    private String response;
    private ExcelUtils excelUtils;


    public enum anEnum {

        DESCRIPTION(1),
        TYPE(2),
        URL(3),
        URLPARAMETERS(4),
        BODY(5),
        BODYPARAMETERS(6),
        RESPONSE(7);

        public int row;

        anEnum(int row) {
            this.row = row;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", urlParameters='" + urlParameters + '\'' +
                ", body='" + body + '\'' +
                ", bodyParameters='" + bodyParameters + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public List<ExcelRequisition> getExcelRequisitions() {
        excelUtils.getSheetData();
        List<ExcelRequisition> excelRequisitions = new ArrayList<>();
        for (int i = 1; i < excelUtils.getSheet().getLastRowNum() + 1; i++) {
            this.setDescription(excelUtils.getCellData(i, anEnum.DESCRIPTION.row));
            this.setType(excelUtils.getCellData(i, anEnum.TYPE.row));
            this.setUrl(excelUtils.getCellData(i, anEnum.URL.row));
            this.setUrlParameters(excelUtils.getCellData(i, anEnum.URLPARAMETERS.row));
            this.setBody(excelUtils.getCellData(i, anEnum.BODY.row));
            this.setBodyParameters(excelUtils.getCellData(i, anEnum.BODYPARAMETERS.row));
            this.setResponse(excelUtils.getCellData(i, anEnum.RESPONSE.row));
            excelRequisitions.add(this);
        }
        return excelRequisitions;

    }




}
