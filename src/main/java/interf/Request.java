package interf;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import request.MyGet;
import utils.PropertiesUtils;
import utils.TestLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Request {
    TestLog logger = new TestLog();
    public String result;
    static String path = new PropertiesUtils().getProperties("path");
    RequestConfig config;
    public static String token = new PropertiesUtils().getProperties("token");

    public Request() {
        config = RequestConfig.custom().
                setConnectTimeout(20000).
                setSocketTimeout(20000)
                .build();

    }

    public void setRequestConfig(RequestConfig config) {
        this.config = config;
    }

    public String post(String url, String json, String token) {
        CloseableHttpClient httpClient;
        HttpPost post = null;
        //HttpHost proxy = new HttpHost("127.0.0.1", 8888);

        try {
            httpClient = HttpClients.createDefault();
            post = new HttpPost(url);
            post.setConfig(config);
            post.addHeader("Authorization", "Bearer " + token);
            post.addHeader("Content-Type", "application/json;charset=UTF-8");

            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            //logger.info("请求：" + EntityUtils.toString(stringEntity));
            post.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(/*proxy,*/ post);
            HttpEntity httpEntity = response.getEntity();//获取响应正文对象
            String myResult = EntityUtils.toString(httpEntity, "UTF-8");
            int code = (int) JsonPath.read(myResult, "$.code");
            Assert.assertEquals(code, 10000, myResult);

            //logger.info("返回：" + myResult);
            return myResult;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String get(String url, String json, String token) {

        CloseableHttpClient httpClient;
        MyGet get = null;
        //HttpHost proxy = new HttpHost("127.0.0.1", 8888);

        try {
            httpClient = HttpClients.createDefault();
            get = new MyGet(url);
            get.setConfig(config);
            get.addHeader("Authorization", "Bearer " + token);
            get.addHeader("Content-Type", "application/json;charset=UTF-8");

            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            //logger.info("请求：" + EntityUtils.toString(stringEntity));
            get.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(/*proxy,*/ get);
            HttpEntity httpEntity = response.getEntity();//获取响应正文对象
            String myResult = EntityUtils.toString(httpEntity, "UTF-8");
            //logger.info("返回：" + myResult);
            return myResult;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String url) {

        CloseableHttpClient httpClient;
        HttpGet get = null;
        //HttpHost proxy = new HttpHost("127.0.0.1", 8888);

        try {
            httpClient = HttpClients.createDefault();
            get = new HttpGet(url);
            get.setConfig(config);
            get.addHeader("Authorization", "Bearer " + token);
            get.addHeader("Content-Type", "application/json;charset=UTF-8");
            //logger.info("请求：" + EntityUtils.toString(stringEntity));
            CloseableHttpResponse response = httpClient.execute(/*proxy,*/ get);
            HttpEntity httpEntity = response.getEntity();//获取响应正文对象
            String myResult = EntityUtils.toString(httpEntity, "UTF-8");
            //logger.info("返回：" + myResult);
            return myResult;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String addVoucher(String voucherBillId) {
        String url = new PropertiesUtils().getProperties("addVoucher");
        String body = new PropertiesUtils().getProperties("addVoucherBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, voucherBillId);
        return this.post(uri, body, token);
    }

    public String getParkAward(String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("parkAward");
        String body = new PropertiesUtils().getProperties("parkAwardBody");
        String uri = path + url;
        body = String.format(body, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body, token);
    }

    public String getEmpBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth, String name) {
        String url = new PropertiesUtils().getProperties("empBonus");
        String body = new PropertiesUtils().getProperties("empBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth, name);
        return this.post(uri, body, token);
    }

    public String getEntpBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("entpBonus");
        String body = new PropertiesUtils().getProperties("entpBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);

        return this.post(uri, body, token);
    }

    public String getAdviseBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("adviseBonus");
        String body = new PropertiesUtils().getProperties("adviseBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body, token);
    }

    public String getConsultantBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("consultantBonus");
        String body = new PropertiesUtils().getProperties("consultantBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body, token);
    }

    public String getEntpId(String entpCode) {
        String url = new PropertiesUtils().getProperties("entpUrl");
        String body = new PropertiesUtils().getProperties("entpBody");
        String uri = path + url;
        body = String.format(body, entpCode);
        return this.post(uri, body, token);
    }

    public HashMap<String, String> getAllEntpMap(String parkName) {
        String url = new PropertiesUtils().getProperties("entpUrl");
        String body = new PropertiesUtils().getProperties("entpBodyFull");
        body = String.format(body, this.getPark(parkName));
        String uri = path + url;
        String result = this.post(uri, body, token);
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        HashMap<String, String> AllEntpMap = new HashMap<>();
        for (Object s : records
        ) {
            String entpId = JSON.parseObject(s.toString()).get("id").toString();
            String entpName = JSON.parseObject(s.toString()).getJSONArray("names").get(0).toString();
            String entpCode = JSON.parseObject(s.toString()).getString("code");
            AllEntpMap.put(entpCode, entpId);
        }
        return AllEntpMap;
    }

    public String getPark(String parkName) {
        String url = new PropertiesUtils().getProperties("parkUrl");
        String body = new PropertiesUtils().getProperties("parkBody");
        body = String.format(body, parkName);
        String uri = path + url;
        String result = this.post(uri, body, token);
        List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
        String parkId = "";
        for (Object s : records
        ) {
            parkId = JSON.parseObject(s.toString()).get("id").toString();
        }
        return parkId;
    }


    public void login() {
        String url = new PropertiesUtils().getProperties("url");
        String name = new PropertiesUtils().getProperties("name");
        String password = new PropertiesUtils().getProperties("password");
        String uri = path + url;
        String body = "{\"loginType\":1,\"platformNo\":1,\"clientId\":\"resico\",\"clientSecret\":\"resico888\",\"username\":%s,\"password\":%s}";
        body = String.format(body, name, password);
        String result = this.post(uri, body, "");
        Request.token = JSON.parseObject(result).getJSONObject("data").get("accessToken").toString().replaceAll("\"", "");
    }


    //确认待发放

  /*  public void getConfirmBeIssued() {
        //this.login();
        String url = "/finance/park/receivable/page";

        String uri = path + url;
        int count = 0;
        String parkId = new PropertiesUtils().getProperties("parkId");
        for (int i = 3; i < 30; i++) {
            String body = "{\"parkId\":%s,\"current\":%d,\"size\":200}";
            body = String.format(body,parkId, i);
            String result = this.post(uri, body, "");
            List records = JSON.parseObject(result).getJSONObject("data").getJSONArray("records");
            int total = Integer.valueOf(JSON.parseObject(result).getJSONObject("data").getString("total"));

            for (Object s : records
            ) {
                if (((JSONObject) s).getJSONObject("incomeConfirmFlag").getString("label").equals("否")) {
                    String id = ((JSONObject) s).getString("id");
                    this.confirmBeIssued(id);
                    System.out.println("执行了" + count++);
                }

            }
            if ((total - 200 * (i - 1)) / 200 == 0) {
                break;
            }
        }

    }*/


    public void confirmBeIssued(String id) {
        String url = "/finance/park/receivable/%s/income-confirm";
        String body = "";
        String uri = path + String.format(url, id);
        this.post(uri, body, token);
    }


    //新增私有库
    public void privateSave(String body) {

        String url = new PropertiesUtils().getProperties("privateSave");
        String uri = path + url;
        String result = this.post(uri, body, token);
    }

    public String privatePage() {

        String url = new PropertiesUtils().getProperties("privatePage");
        String body = new PropertiesUtils().getProperties("privatePageBody");
        String uri = path + url;
        return this.post(uri, body, token);
    }


}
