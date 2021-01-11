package utils;

import com.alibaba.fastjson.JSONObject;
import entity.Requisition;
import exception.MyException;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MyAssert {
    static TestLog logger = new TestLog();

    public static boolean flag = true;

    public static void begin() {
        flag = true;
    }

    public static void end() {
        Assert.assertTrue(flag);
    }


    public static void verifyJsonEquals(JSONObject actual, JSONObject expected) {
        ArrayList<String> sourceList = JsonUtils.getJsonKey(null, expected);
        ArrayList<String> targetList = JsonUtils.getJsonKey(null, actual);
        Assert.assertEquals(sourceList, targetList);
    }

    public static void verifyEquals(Object actual, Object expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            flag = false;
        }
    }


    public static void verifyEquals(Object actual, Object expected, Double awardAmt, String
            message) {
        DecimalFormat df = new DecimalFormat("#.00");


        Double expectedTio = (Double) expected / awardAmt;
        Double actualTio = (Double) actual / awardAmt;


        try {
            if ((double) actual - (double) expected == 0 || Math.abs((double) actual - (double) expected) <= 0.03) {
                return;
            }/* else if (x + 0.04 == y || x + 0.05 == y || x + 0.10 == y || x / y == 2.00 || y / x == 2.00) {
                return;
            }*/ else {
                throw new MyException("ERROR:" + message + "实际" + actual + "与" + "预期" + expected + "不等");
            }
        } catch (MyException e) {
            logger.error("ERROR:" + message + "实际" + actual + "与" + "预期" + expected + "不等");
            e.printStackTrace();
            flag = false;
        }
    }

    public static void responseAssertion(List<Object> actualResponses, List<Object> exceptResponse, Requisition requisition) {
        if (null == actualResponses) {
            Assert.assertEquals(requisition.getResponse().getCode(), "10000");
        } else {
            for (int i = 0; i < actualResponses.size(); i++) {
                Assert.assertEquals(actualResponses.get(i), exceptResponse.get(i));
            }
        }
    }

}
