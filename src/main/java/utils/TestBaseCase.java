package utils;


import interf.Request;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


public class TestBaseCase {
    //方法描述
    Request request = new Request();
    public static String description;
    public TestLog log = new TestLog(this.getClass().getSuperclass());

    @BeforeSuite
    public void setup() {
        log.info("------------------判断是否需要登录---------------");

        if (!this.isTokenEffective()) {
            request.login();
        }

        log.info("------------------生成数据---------------");
        this.GenerateData();

        log.info("------------------开始执行测试---------------");
    }

    @AfterSuite
    public void tearDown() {
        log.info("关闭server");
        log.info("-------------结束测试，并关闭退出driver及自动化 server-------------");
    }

    public boolean isTokenEffective() {
        String result = request.get("https://api.test.ustax.tech/ums/employee/resources?systemType=1");
        String code = new JsonUtils().getValue("code", result).toString();
        if (code != "10000") {
            return false;
        }
        return true;
    }

    private void GenerateData() {
        String tels = RandomUtils.getNumber(11);
        String customerName = ChineseChar.getRandomString(5);
        String companyName = ChineseChar.getRandomString(5);

        String sql1 = "SELECT count(1) FROM  crm_customer WHERE `name`=?;";
        String sql2 = "SELECT count(1) FROM  ems_enterprise WHERE `names`=?;";
        TestJDBC.load();
        try {
            while (TestJDBC.query(sql1, customerName).getRow() > 0) customerName = ChineseChar.getRandomString(5);
            while (TestJDBC.query(sql2, companyName).getRow() > 0) companyName = ChineseChar.getRandomString(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("classes:\n" +
                "  - class:\n" +
                "      className: \"DemoTest\"\n" +
                "      description: \"测试入驻企业\"\n" +
                "      classSetUp: {contactName: \"%s\", qq: \"%s\", tels: \"%s\", weChat: \"%s\", customerName: \"%s\", companyName: \"%s\",parkId: \"1316213784702005249\" }\n" +
                "      methodSetUp:  {}");
        String yaml = buffer.toString();
        String sb = String.format(yaml, tels, tels, tels, tels, customerName, companyName);

        File pageObjectFile = new File("src\\main\\resources\\DemoTest.yaml");
        if (pageObjectFile.exists()) {
            pageObjectFile.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(pageObjectFile, false);
            BufferedWriter output = new BufferedWriter(fileWriter);
            output.write(sb);
            output.flush();
            output.close();
        } catch (IOException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        System.out.println(sb);
        TestLog log = new TestLog(this.getClass());
        log.info("自动生成测试内容成功");
    }
}
