import business.RequisitionControl;
import com.esotericsoftware.yamlbeans.YamlException;
import entity.ExcelRequisition;
import entity.Requisition;
import interf.Request;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.TestBaseCase;
import utils.YamlUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class DemoTest extends TestBaseCase {
    RequisitionControl requisitionControl = new RequisitionControl();
    String className = this.getClass().getName();
    HashMap<String, Object> aClass = new HashMap<>();

    @BeforeClass
    public void beforeClass() {

        String path = "src\\main\\resources\\DemoTest.yaml";
        try {
            aClass = YamlUtils.getClass(path, className);
            requisitionControl.parameterMap.putAll(YamlUtils.getSetUp(aClass, "classSetUp"));
        } catch (FileNotFoundException | YamlException e) {
            e.printStackTrace();
        }
        System.out.println("beforeMethod完成");

    }

    @BeforeMethod
    public void beforeMethod() {
        try {
            requisitionControl.parameterMap.putAll(YamlUtils.getSetUp(aClass, "methodSetUp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("beforeMethod完成");

    }


    @Test(groups = "第一组")
    public void test1() {
        String path = "F:\\UITest\\新增入驻.xlsx";

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
                String code = new JsonUtils().getValue("code", response).toString();
                Assert.assertTrue(code.equals("10000"), response);
            } else {
                response = request.get(url, body, request.token);
                String code = new JsonUtils().getValue("code", response).toString();
                Assert.assertTrue(code.equals("10000"), response);

            }
            requisition.setResponse(response);

            String responseData = e.getResponse();

            requisitionControl.addParameterMap(response, responseData);
            System.out.println(e.getDescription() + "结束");

        }
    }


    @Test(groups = "第二组", priority = 1)
    public void test2() {
        System.out.println("第二组执行");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod完成");

    }

}
