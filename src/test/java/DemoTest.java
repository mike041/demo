import com.jayway.jsonpath.JsonPath;
import controller.Assembly;
import com.esotericsoftware.yamlbeans.YamlException;
import entity.Response;
import excelEntity.*;
import entity.Requisition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import request.Request;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelUtils;
import utils.TestBaseCase;
import utils.YamlUtils;

import java.io.*;
import java.util.*;

import static io.qameta.allure.SeverityLevel.TRIVIAL;

public class DemoTest extends TestBaseCase {
    String className = this.getClass().getName();
    HashMap<String, Object> aClass = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        String path = "src\\main\\resources\\" + className + ".yaml";
        try {
            aClass = YamlUtils.getClass(path, className);
            testCaseMap.putAll(YamlUtils.getSetUp(aClass, "classSetUp"));
        } catch (FileNotFoundException | YamlException e) {
            e.printStackTrace();
        }
        System.out.println("beforeClass完成");

    }

    @BeforeMethod
    public void beforeMethod() {
        try {
            testCaseMap.putAll(YamlUtils.getSetUp(aClass, "methodSetUp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("beforeMethod完成");
    }

    @Test(groups = "第一组", dataProvider = "data")
    public void test(ExcelRequisition excelRequisition) {
        System.out.println("开始测试" + excelRequisition.getDescription());


        Requisition requisition = new Requisition(excelRequisition);
        SaveResult saveResult = excelRequisition.getSaveResult();
        //发起请求
        Request request = new Request();
        String result = request.sendRequest(requisition.getType(), requisition.getUrl(), requisition.getBody());
        Response response = Assembly.setResponse(result);

        List<ActualAssert> actualAssertList = Assembly.setAllAssertion(excelRequisition.getExcelCheckRule(), excelRequisition.getExcelExpect(), response);

        //校验
        if (null == excelRequisition.getExcelCheckRule()) {
            Assert.assertEquals(response.getCode(), "10000", "默认校验code不通过：" +requisition+ response.getJsonResponse()+"\n");
        } else {
            for (ActualAssert a : actualAssertList
            ) {
                Assert.assertEquals(a.getActulResult().toString(), a.getExpectResult().toString(),requisition+ response.getJsonResponse()+"\n");
            }
        }


        //保存需保存响应
        if (null == saveResult) {
        } else {
            for (Result r : saveResult.getResults()
            ) {
                testCaseMap.put(r.getKey(), JsonPath.read(response.getJsonResponse(), r.getJsonPath()));
            }
        }


    }

    @Test(groups = "第二组")
    @Epic("输入点啥")
    @Story("Story啊")
    @Feature("啥啊")
    @Severity(TRIVIAL)
    public void test2() {
        System.out.println("test2");
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
            System.out.println("开始初始化数据:" + i);
            if (null == sheet.getRow(i)) {
                continue;
            }
            ExcelRequisition excelRequisition = new ExcelRequisition(sheet.getRow(i));
            if (excelRequisition.getIsExecute().equals("false")) {
                continue;
            }
            item.add(excelRequisition);
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
