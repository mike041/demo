package entity;


import controller.Assembly;
import excelEntity.ExcelBody;
import excelEntity.ExcelRequisition;
import excelEntity.ExcelUrl;
import excelEntity.Parameter;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;

import java.util.List;


/**
 * @author mikezhou
 * @description 请求的所有数据
 */

@Data
public class Requisition {
    private String type;
    private String url;
    private String body;
    List<Header> headerList;
    private Response response;

    public Requisition(ExcelRequisition excelRequisition) {
        this.setType(excelRequisition);
        this.setUrl(excelRequisition.getExcelUrl());
        this.setBody(excelRequisition.getExcelBody());
    }

    public void setType(ExcelRequisition excelRequisition) {
        this.type = excelRequisition.getType();
    }

    public void setUrl(ExcelUrl excelUrl) {
        String path = excelUrl.getPath();
        List<Parameter> parameters = excelUrl.getParameters();
        if (!parameters.isEmpty()) {
            for (Parameter p : parameters
            ) {
                String replacement = Assembly.replace(p.getClassName(), p.getArgs());
                path.replaceFirst("(\\$)([\\w]+)(.*?)(\\$)", replacement);
            }
        }
        this.url = path;
    }

    public void setBody(ExcelBody excelBody) {
        String body = excelBody.getBody();
        List<Parameter> parameters = excelBody.getParameters();
        if (!parameters.isEmpty()) {
            for (Parameter p : parameters
            ) {
                String replacement = Assembly.replace(p.getClassName(), p.getArgs());
                body.replaceFirst("(\\$)([\\w]+)(.*?)(\\$)", replacement);
            }
        }
        this.body = body;
    }
}
