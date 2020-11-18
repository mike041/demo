package utils;

import business.Compare;
import exception.DataNoFoundException;
import org.testng.Assert;


public class MyAssert {
    static TestLog logger = new TestLog();

    public static boolean flag = true;

    public static void begin() {
        flag = true;
    }

    public static void end() {
        Assert.assertTrue(flag);
    }


    public static void verifyEquals(Object actual, Object expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            flag = false;
        }
    }

    public static void verifyEquals(Object actual, Object expected, String
            message) {
        try {
            double a = (Double) actual - (Double) expected;
            if (a == 0 || Math.abs(a) < 0.02) {
                // logger.info(message + "实际" + actual + "与" + "预期" + expected + "相差" + a);
                return;
            } else {
                throw new DataNoFoundException("ERROR:" + message + "实际" + actual + "与" + "预期" + expected + "不等");
            }
            // Assert.assertEquals(actual, expected, message);
        } catch (DataNoFoundException e) {
            logger.error("ERROR:" + message + "实际" + actual + "与" + "预期" + expected + "不等");
            e.printStackTrace();
            flag = false;
        }
    }

}
