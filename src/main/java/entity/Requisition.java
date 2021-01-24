package entity;


import controller.Assembly;
import excelEntity.ExcelBody;
import excelEntity.ExcelRequisition;
import excelEntity.ExcelUrl;
import excelEntity.Parameter;
import lombok.Data;
import org.apache.http.Header;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
                String replacement = null;
                try {
                    replacement = Assembly.replace(p.getClassName(), p.getArgs());
                    path = path.replaceFirst("(\\$)([\\w]+)(.*?)(\\$)", replacement);
                    Class clazz = Class.forName("DemoTest");
                    Field field = clazz.getField("testCaseMap");
                    if (null == p.getKey()) {
                        continue;
                    }
                    ((HashMap<String, Object>) field.get(clazz)).put(p.getKey(), replacement);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
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
                String replacement = null;
                try {
                    replacement = Assembly.replace(p.getClassName(), p.getArgs());
                    body = body.replaceFirst("(\\$)([\\w]+)(.*?)(\\$)", replacement);
                    Class clazz = Class.forName("DemoTest");
                    Field field = clazz.getField("testCaseMap");
                    if (null == p.getKey()) {
                        continue;
                    }
                    ((HashMap<String, Object>) field.get(clazz)).put(p.getKey(), replacement);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        this.body = body;
    }

    @Override
    public String toString() {
        return "Requisition{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", headerList=" + headerList +
                ", response=" + response +
                '}';
    }
}
