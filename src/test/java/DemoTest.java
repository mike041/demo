import controller.Assembly;
import controller.RequisitionControl;
import com.esotericsoftware.yamlbeans.YamlException;
import entity.Response;
import excelEntity.*;
import entity.Requisition;
import interf.Request;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelUtils;
import utils.TestBaseCase;
import utils.YamlUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DemoTest extends TestBaseCase {
    RequisitionControl requisitionControl = new RequisitionControl();
    String className = this.getClass().getName();
    HashMap<String, Object> aClass = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        String path = "src\\main\\resources\\" + className + ".yaml";
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


    @Test(groups = "第二组", priority = 1)
    public void test2() {
        System.out.println("第二组执行");
    }

    @Test(groups = "第一组", dataProvider = "data")
    public void test3(Requisition requisition, List<ActualAssert> actualAssertList, SaveResult saveResult) {

        Request request = new Request();
        String result = request.sendRequest(requisition.getType(), requisition.getUrl(), requisition.getBody());
        Response response = RequisitionControl.setResponse(result);

        for (ActualAssert a:actualAssertList
             ) {
            a.
        }


    }

    @DataProvider(name = "data")
    public Iterator<Object[]> dataProvider() {
        String path = "F:\\UITest\\新格式.xlsx";
        ExcelUtils utils = new ExcelUtils(path);
        Sheet sheet = utils.getSheet();
        List<Object> item = new ArrayList<>();
        List<Object[]> testCase = new ArrayList<>();

        utils.getSheetData();
        for (int i = 1; i <= sheet.getLastRowNum() + 1; i++) {
            if (utils.isRowEmpty(sheet.getRow(i))) {
                continue;
            }
            ExcelRequisition excelRequisition = new ExcelRequisition(sheet.getRow(i));
            if (excelRequisition.getIsExecute().equals("false")) {
                continue;
            }
            Requisition requisition = new Requisition(excelRequisition);
            SaveResult result = excelRequisition.getSaveResult();

            List<ActualAssert> actualAssertList = Assembly.setAllAssertion(excelRequisition.getExcelCheckRule(), excelRequisition.getExcelExpect(), requisition.getResponse());
            item.add(requisition);
            item.add(actualAssertList);
            item.add(result);
        }
        for (Object u : item) {
            //做一个形式转换
            testCase.add(new Object[]{u});
        }
        return testCase.iterator();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod完成");
    }

}
