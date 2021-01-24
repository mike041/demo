package request;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import utils.PropertiesUtils;
import utils.TestLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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


    public String sendRequest(String type, String url, String json) {
        String response = "";
        if ("post".equalsIgnoreCase(type)) {
            response = this.post(url, json);
        } else {
            response = this.get(url, json);
        }
        return response;
    }


    public String post(String url, String json) {
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

            //logger.info("返回：" + myResult);
            return myResult;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String get(String url, String json) {

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


    @Test
    public void test() {
        String url = "https://api.test.ustax.tech/file/upload";
        String localFileName = "F:\\流程图.rar";
        try {
            String result = this.upload(url, localFileName);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String upload(String url, String localFileName) throws IOException {
        Map<String, ContentBody> reqParam = new HashMap<>();
        reqParam.put("name", new StringBody("file", ContentType.MULTIPART_FORM_DATA));
        reqParam.put("filename", new StringBody(localFileName, ContentType.MULTIPART_FORM_DATA));
        reqParam.put("upfile", new FileBody(new File(localFileName)));
        return Request.postFileMultiPart(url, reqParam);

    }


    public static String postFileMultiPart(String url, Map<String, ContentBody> reqParam) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Entry<String, ContentBody> param : reqParam.entrySet()) {
                multipartEntityBuilder.addPart(param.getKey(), param.getValue());
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);
            System.out.println(reqEntity.getContentType());
            // 执行post请求.
            CloseableHttpResponse response = httpclient.execute(httppost);
            System.out.println("got response");
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                //System.out.println("--------------------------------------");
                // 打印响应状态
                //System.out.println(response.getStatusLine());
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
                //System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String addVoucher(String voucherBillId) {
        String url = new PropertiesUtils().getProperties("addVoucher");
        String body = new PropertiesUtils().getProperties("addVoucherBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, voucherBillId);
        return this.post(uri, body);
    }

    public String getParkAward(String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("parkAward");
        String body = new PropertiesUtils().getProperties("parkAwardBody");
        String uri = path + url;
        body = String.format(body, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body);
    }

    public String getEmpBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth, String name) {
        String url = new PropertiesUtils().getProperties("empBonus");
        String body = new PropertiesUtils().getProperties("empBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth, name);
        return this.post(uri, body);
    }

    public String getEntpBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("entpBonus");
        String body = new PropertiesUtils().getProperties("entpBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);

        return this.post(uri, body);
    }

    public String getAdviseBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("adviseBonus");
        String body = new PropertiesUtils().getProperties("adviseBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body);
    }

    public String getConsultantBonus(String voucherBillId, String entpId, int taxTypes, String TaxMonth) {
        String url = new PropertiesUtils().getProperties("consultantBonus");
        String body = new PropertiesUtils().getProperties("consultantBonusBody");
        String uri = path + url;
        body = String.format(body, voucherBillId, entpId, taxTypes, TaxMonth, TaxMonth);
        return this.post(uri, body);
    }

    public String getEntpId(String entpCode) {
        String url = new PropertiesUtils().getProperties("entpUrl");
        String body = new PropertiesUtils().getProperties("entpBody");
        String uri = path + url;
        body = String.format(body, entpCode);
        return this.post(uri, body);
    }

    public HashMap<String, String> getAllEntpMap(String parkName) {
        String url = new PropertiesUtils().getProperties("entpUrl");
        String body = new PropertiesUtils().getProperties("entpBodyFull");
        body = String.format(body, this.getPark(parkName));
        String uri = path + url;
        String result = this.post(uri, body);
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
        String result = this.post(uri, body);
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
        String result = this.post(uri, body);
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
        this.post(uri, body);
    }


    //新增私有库
    public void privateSave(String body) {

        String url = new PropertiesUtils().getProperties("privateSave");
        String uri = path + url;
        String result = this.post(uri, body);
    }

    public String privatePage() {

        String url = new PropertiesUtils().getProperties("privatePage");
        String body = new PropertiesUtils().getProperties("privatePageBody");
        String uri = path + url;
        return this.post(uri, body);
    }


}
