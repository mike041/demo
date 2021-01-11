package controller;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import entity.Response;
import excelEntity.*;
import exception.MyException;
import hook.Reflect;
import utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static excelEntity.CheckRule.*;

public class Assembly {



    public static List<ActualAssert> setAllAssertion(ExcelCheckRule excelCheckRule, ExcelExpect excelExpect, Response response) {
        List<CheckRule> checkRuleList = excelCheckRule.getCheckRuleList();
        List<Expect> excelExpectList = excelExpect.getExpectList();
        if (checkRuleList.size() != excelExpectList.size()) {
            try {
                throw new MyException("校验规则数量与预期数量不匹配：" + "校验:" + checkRuleList.size() + "预期参数：" + excelExpectList.size();
            } catch (MyException e) {
                e.printStackTrace();
            }
        }

        List<ActualAssert> actualAssertList = new ArrayList<>();
        for (int i = 0; i < checkRuleList.size(); i++) {
            ActualAssert actualAssert = Assembly.setActualAssert(checkRuleList.get(i), excelExpectList.get(i), response);
            actualAssertList.add(actualAssert);
        }
        return actualAssertList;
    }


    public static ActualAssert setActualAssert(CheckRule checkRule, Expect except, Response response) {
        Object actulResult;
        Object expectResult;
        switch (checkRule.getType()) {
            case CheckRule.ALL:
                actulResult = response.getJsonResponse().replaceAll("\n", "");
                expectResult = except.getValue();
                break;
            case CODE:
                actulResult = response.getCode();
                expectResult = except.getValue();
                break;
            case JSON:
                JSONObject actualJson = JSONObject.parseObject(response.getJsonResponse());
                actulResult = JsonUtils.getJsonKey(null, actualJson);
                JSONObject actualJson1 = JSONObject.parseObject(except.getValue());
                expectResult = JsonUtils.getJsonKey(null, actualJson1);
                break;
            case VALUE:
                actulResult = JsonPath.read(response.getJsonResponse(), checkRule.getJsonPath());
                expectResult = JsonPath.read(except.getValue(), checkRule.getJsonPath());
                break;
            default:
                actulResult = response.getCode();
                expectResult = 10000;
                break;
        }
        return ActualAssert.builder().actulResult(actulResult).expectResult(expectResult).build();
    }


    public static String replace(String className, Object value) {
        String replacement = null;
        try {
            replacement = Reflect.execute(className, value).toString();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return replacement;
    }


}
