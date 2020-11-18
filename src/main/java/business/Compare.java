package business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.Filter;
import exception.DataNoFoundException;
import interf.Request;
import org.apache.http.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.MyAssert;
import utils.ExcelUtils;
import utils.TestLog;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compare {
    ExcelUtils excel;

    JSONObject empRealAward;
    JSONObject entpRealAward;
    JSONObject adviseRealAward;
    JSONObject consultantRealAward;
    TestLog logger = new TestLog(this.getClass());


    @DataProvider(name = "dataProvider"/*, parallel = true*/)
    public Object[][] DataProvider() {
        Request request = new Request();
        request.login();
        excel = new ExcelUtils("F:\\数据\\1117新园区\\莱西\\莱西1月.xlsx", "Sheet2");
        excel.getSheetData();


        List<Map<String, String>> list = excel.getMapData();
        Sheet sheet = excel.getSheet();

        int rowNum = sheet.getLastRowNum();
        Object[][] data = new Object[rowNum][0];
        for (int i = 0; i < list.size(); i++) {
            data[i] = new Object[]{list.get(i)};
        }


        //读取每一行
      /*  for (int i = 0; i < rowNum; i++) {
            data[i] = new Object[]{sheet.getRow(i)};
        }*/


        return data;
    }


    @Test(dataProvider = "dataProvider", threadPoolSize = 1)
    public void compare(HashMap<String, String> expectedAward) {
        Filter filter = this.setfilterConditon(expectedAward);
        List<JSONObject> parkAward = this.getParkAwardReal(filter);
        MyAssert.begin();

        //员工、企业、渠道、产业顾问校验
        // this.assertFourAward();

        //园区奖励结算
        this.assertParkAward(expectedAward, parkAward, filter);
        MyAssert.end();

    }


    public static Double formatDouble(String o, int ratio) {
        if (o == null) {
            return 0.00;
        } else if (o.isEmpty()) {
            return 0.00;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return Double.valueOf(df.format(Double.valueOf(o) * ratio));

        //  return String.format("%.2f", Double.valueOf(o) * ratio);
    }


    public Filter setfilterConditon(HashMap<String, String> expectedAward) {
        Filter filter = new Filter();
        filter.setEntpName(expectedAward.get("企业"));
        filter.setTaxMonth(expectedAward.get("税款缴纳月"));
        filter.setTaxTypeName(expectedAward.get("奖励税种"));
        filter.setTaxTypeId();
        Double taxAmt = Double.valueOf(expectedAward.get("完税额"));
        filter.setTaxAmt(String.format("%.2f", taxAmt));
        this.setEntpId(filter);
        return filter;
    }

    public void setEntpId(Filter filter) {
        Request request = new Request();
        String result = request.getEntpId(filter.getEntpName());

        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String entpId = JSON.parseObject(s.toString()).get("id").toString();
                filter.setEntpId(entpId);
            }
        } else {
            try {
                logger.error("ERROR:" + filter + "\"" + "企业不存在");
                throw new DataNoFoundException(filter + "企业不存在");
            } catch (DataNoFoundException e) {
                e.printStackTrace();
            }
        }


    }


    public void getEmpReal(String name, Filter filter) {
        Request request = new Request();
        String result = request.getEmpBonus(null, filter.getEntpId(), filter.getTaxType(), this.dateFormate(filter.getTaxMonth()), name);
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String RealTaxAmt = JSON.parseObject(s.toString()).get("taxAmt").toString();
                if (RealTaxAmt.equals(filter.getTaxAmt())) {
                    empRealAward = JSON.parseObject(s.toString());
                }
            }
        }

    }


    public void getEntpReal(Filter filter) {
        Request request = new Request();
        String result = request.getEntpBonus(null, filter.getEntpId(), filter.getTaxType(), this.dateFormate(filter.getTaxMonth()));
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String RealTaxAmt = JSON.parseObject(s.toString()).get("taxAmt").toString();
                if (RealTaxAmt.equals(filter.getTaxAmt())) {
                    entpRealAward = JSON.parseObject(s.toString());
                }
            }
        }

    }

    public void getAdviseReal(Filter filter) {
        Request request = new Request();
        String result = request.getAdviseBonus(null, filter.getEntpId(), filter.getTaxType(), this.dateFormate(filter.getTaxMonth()));
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String RealTaxAmt = JSON.parseObject(s.toString()).get("taxAmt").toString();
                if (RealTaxAmt.equals(filter.getTaxAmt())) {
                    adviseRealAward = JSON.parseObject(s.toString());
                }
            }
        }

    }

    public void getConsultantReal(Filter filter) {
        Request request = new Request();
        String result = request.getConsultantBonus(null, filter.getEntpId(), filter.getTaxType(), this.dateFormate(filter.getTaxMonth()));
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String RealTaxAmt = JSON.parseObject(s.toString()).get("taxAmt").toString();
                if (RealTaxAmt.equals(filter.getTaxAmt())) {
                    consultantRealAward = JSON.parseObject(s.toString());
                }
            }
        }

    }


    public List<JSONObject> getParkAwardReal(Filter filter) {
        Request request = new Request();
        String result = request.getParkAward(filter.getEntpId(), filter.getTaxType(), this.dateFormate(filter.getTaxMonth()));
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        // JSONObject parkAward = null;
        List<JSONObject> parkAwards = new ArrayList<>();
        if (!records.isEmpty()) {
            for (Object s : records
            ) {
                String realTaxAmt = JSON.parseObject(s.toString()).get("taxAmt").toString();
                if (realTaxAmt.equals(filter.getTaxAmt())) {
                    //  parkAward = JSON.parseObject(s.toString());
                    parkAwards.add(JSON.parseObject(s.toString()));
                }
            }
        } else {
            try {
                throw new DataNoFoundException(filter.getEntpName() + filter.getTaxType() + filter.getTaxMonth() + "不存在");
            } catch (Exception e) {
                logger.error("ERROR:" + filter.getEntpName() + filter.getTaxType() + filter.getTaxMonth() + "不存在");
            }
        }
        return parkAwards;

    }


    public String dateFormate(String month) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        DateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        String taxMonth = null;
        try {
            taxMonth = dateFormat.format(Format.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return taxMonth;

    }


    /*public void getExpected() {
        excel = new ExcelUtils("F:\\数据\\山东昌乐.xlsx", "Sheet1");

    }*/


    public void assertFourAward(HashMap<String, String> expectedAward, Filter filter) {
        if (!expectedAward.get("招商人员").isEmpty() && expectedAward.get("招商人员") != "0") {
            this.getEmpReal(expectedAward.get("招商人员"), filter);

            MyAssert.verifyEquals(
                    formatDouble(empRealAward.getString("bonusAmt"), 1),
                    formatDouble(expectedAward.get("招商人员提成金额"), 1),
                    "招商人员提成金额");
        }
        if (!expectedAward.get("招商科长").isEmpty() && expectedAward.get("招商科长") != "0") {
            this.getEmpReal(expectedAward.get("招商科长"), filter);

            MyAssert.verifyEquals(
                    formatDouble(empRealAward.getString("bonusAmt"), 1),
                    formatDouble(expectedAward.get("招商科长提成金额"), 1),
                    "招商科长提成金额");
        }
        if (!expectedAward.get("关联招商人员").isEmpty() && expectedAward.get("关联招商人员") != "0") {
            this.getEmpReal(expectedAward.get("关联招商人员"), filter);

            MyAssert.verifyEquals(
                    formatDouble(empRealAward.getString("bonusAmt"), 1),
                    formatDouble(expectedAward.get("关联招商人员提成金额"), 1),
                    "关联招商人员提成金额");
        }
        if (!expectedAward.get("关联招商科长").isEmpty() && expectedAward.get("关联招商科长") != "0") {
            this.getEmpReal(expectedAward.get("关联招商科长"), filter);
            MyAssert.verifyEquals(
                    formatDouble(empRealAward.getString("bonusAmt"), 1),
                    formatDouble(expectedAward.get("关联招商科长提成金额"), 1),
                    "关联招商科长提成金额");
        }


        if (!expectedAward.get("企业返税金额").isEmpty() && expectedAward.get("企业返税金额") != "0.00" && expectedAward.get("企业返税金额") != "-") {
            this.getEntpReal(filter);

            MyAssert.verifyEquals(
                    formatDouble(entpRealAward.getString("rewardAmt"), 1),
                    formatDouble(expectedAward.get("企业返税金额"), 1),
                    "企业返税金额");


        }

        //String award = expectedAward.get("渠道奖励比例");

        if (!expectedAward.get("渠道奖励金额").isEmpty() && expectedAward.get("渠道奖励金额") != "0.00" && expectedAward.get("渠道奖励金额") != "-") {
            this.getAdviseReal(filter);
            MyAssert.verifyEquals(formatDouble(adviseRealAward.getString("rewardAmt"), 1),
                    formatDouble(expectedAward.get("渠道奖励金额"), 1),
                    "渠道奖励金额");
        }

        if (!expectedAward.get("顾问奖励金额").isEmpty() && expectedAward.get("顾问奖励金额") != "0.00" && expectedAward.get("顾问奖励金额") != "-") {
            this.getConsultantReal(filter);
            MyAssert.verifyEquals(
                    formatDouble(consultantRealAward.getString("rewardAmt"), 1),
                    formatDouble(expectedAward.get("顾问奖励金额"), 1),
                    "顾问奖励金额");
        }

    }

    public void assertParkAward(HashMap<String, String> expectedAward, List<JSONObject> parkAwards, Filter filter) {


       /* assertion.verifyEquals(
                formatDouble(ParkAward.getString("localAmt"), 1),
                formatDouble(expectedAward.get("地方留存金额"), 1),
                "地方留存金额");*/

        /*assertion.verifyEquals(
                formatDouble(ParkAward.getString("parkAmt"), 1),
                formatDouble(expectedAward.get("园区留存金额"), 1),
                "园区留存金额");*/

        Double parkAwardAmt = 0.00;
        Double entpAwardAmt = 0.00;
        Double investorAwardAmt = 0.00;
        Double parkConsultantAwardAmt = 0.00;

        for (JSONObject parkAward : parkAwards
        ) {
            parkAwardAmt += formatDouble(parkAward.getString("parkAwardAmt"), 1);
            entpAwardAmt += formatDouble(parkAward.getString("entpAwardAmt"), 1);
            investorAwardAmt += formatDouble(parkAward.getString("investorAwardAmt"), 1);
            parkConsultantAwardAmt += formatDouble(parkAward.getString("parkConsultantAwardAmt"), 1);
        }


        MyAssert.verifyEquals(
                parkAwardAmt,
                formatDouble(expectedAward.get("园区应返税金额"), 1),
                filter + "园区应返税金额");

        MyAssert.verifyEquals(
                entpAwardAmt,
                formatDouble(expectedAward.get("企业返税金额"), 1),
                filter + "企业返税金额");


        MyAssert.verifyEquals(
                investorAwardAmt,
                formatDouble(expectedAward.get("渠道奖励金额"), 1),
                filter + "渠道奖励金额");


        MyAssert.verifyEquals(
                parkConsultantAwardAmt,
                formatDouble(expectedAward.get("顾问奖励金额"), 1),
                filter + "顾问奖励金额");

/*

        assertion.verifyEquals(
                formatDouble(parkAward.getString("grossIncomeAmt"), 1),
                formatDouble(expectedAward.get("集团毛收入"), 1),
                "集团毛收入");

        assertion.verifyEquals(
                formatDouble(parkAward.getString("governmentAwardAmt"), 1),
                formatDouble(expectedAward.get("政务毛利"), 1),
                "政务毛利");


        assertion.verifyEquals(
                formatDouble(parkAward.getString("customerSourceAmt"), 1),
                formatDouble(expectedAward.get("客户来源金额"), 1),
                "客户来源金额");


        assertion.verifyEquals(
                formatDouble(parkAward.getString("marketingOrgAmt"), 1),
                formatDouble(expectedAward.get("引进分成金额"), 1),
                "引进分成金额");

        assertion.verifyEquals(
                formatDouble(parkAward.getString("serviceOrgAmt"), 1),
                formatDouble(expectedAward.get("企服公司分成金额"), 1),
                "企服公司分成金额");

        assertion.verifyEquals(
                formatDouble(parkAward.getString("assistMarketingOrgAmt"), 1),
                formatDouble(expectedAward.get("关联引进分成金额"), 1),
                "关联引进分成金额");


        assertion.verifyEquals(
                formatDouble(parkAward.getString("marketingEmpAmt"), 1),
                formatDouble(expectedAward.get("招商人员提成金额"), 1),
                "招商人员提成金额");

        assertion.verifyEquals(
                formatDouble(parkAward.getString("marketingEmpManagerAmt"), 1),
                formatDouble(expectedAward.get("招商科长提成金额"), 1),
                "招商科长提成金额");


        assertion.verifyEquals(
                formatDouble(parkAward.getString("assistMarketingEmpAmt"), 1),
                formatDouble(expectedAward.get("关联招商人员提成金额"), 1),
                "关联招商人员提成金额");


        assertion.verifyEquals(
                formatDouble(parkAward.getString("assistMarketingEmpManagerAmt"), 1),
                formatDouble(expectedAward.get("关联招商科长提成金额"), 1),
                "关联招商科长提成金额");

*/

    }
}