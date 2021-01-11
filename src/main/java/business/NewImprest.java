package business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import interf.Request;
import utils.ExcelUtils;

import java.util.List;
import java.util.Random;

public class NewImprest {
    static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50SWQiOiIxMzI4NTE5Mzk0NDkxOTY5NTM4IiwicGxhdGZvcm1ObyI6MSwiY2xpZW50SWQiOiJyZXNpY28iLCJleHAiOjE2MDc2NTYyNjQsInVzZXJJZCI6IjEzMjg1MTkzOTM5Mzk0NjQxOTMiLCJqdGkiOiI5ZjhmODk5Zi0wYzM1LTRmOTgtODRjMS1hMWEwZWMxNDExMWUifQ.E5CekVS1itsr1EkkHyf-K4Akkw61ojmnSstZDQL_GLM";


    public static void main(String[] args) {
        Request request = new Request();
        /*String url1 = "http://api.uat.ustax.com.cn/finance/governmentReserve/apply";
        String body1 = "{\n" +
                "\t\"reserveType\": 1001,\n" +
                "\t\"applyPayDate\": \"2020-12-08\",\n" +
                "\t\"fileId\": \"1336208520881156098\",\n" +
                "\t\"parkId\": \"1299555607250026546\",\n" +
                "\t\"companyBodyId\": \"1303214721429970946\",\n" +
                "\t\"receiveMan\": \"管理员\",\n" +
                "\t\"accountBank\": \"重庆支行\",\n" +
                "\t\"accountNo\": \"55555555\",\n" +
                "\t\"reserveAmount\": 1000,\n" +
                "\t\"remark\": \"测试自动生成\"\n" +
                "}";

        request.post(url1, body1, token);


        for (int i = 0; i < 15; i++) {
            String url2 = "http://api.uat.ustax.com.cn/bpm/instance-flow-run/page-todo/704";
            String body2 = "{\"current\":1,\"size\":20}";
            String response = request.post(url2, body2, token);
            JSONArray result = JSON.parseObject(response).getJSONObject("data").getJSONArray("records");
            String id = result.getJSONObject(0).getString("id");
            String url3 = "http://api.uat.ustax.com.cn/bpm/instance-flow-run/%s/operation";
            url3 = String.format(url3, id);
            String body3 = "{\"id\":\"%s\",\"operationType\":2}";
            body3 = String.format(body3, id);
            request.post(url3, body3, token);
            System.out.println("执行了第"+i+"次");

        }*/


        ExcelUtils utils = new ExcelUtils("F:\\UITest\\备用金.xlsx");
        utils.getSheetData();
        List<List<String>> lists = utils.getListData();
        for (List<String> list : lists
        ) {
            Random random = new Random();
            int index = random.nextInt() * 10;
            String parkName = list.get(0);
            String parkUrl = "/government-affair/park/cooperations";
            String parkBody = String.format("{\"keywords\":\"%s\",\"current\":1,\"size\":20}", parkName);

            String parkId = JSON.parseObject(request.post(parkUrl, parkBody)).getJSONObject("data").getJSONArray("records").get(0).toString();

            String url2 = "/government-affair/enterprises";
            String body2 = String.format("entpBodyFull={\"parkId\":\"%s\",\"current\":1,\"size\":2}\n", parkId);

            JSONArray entpIds = JSON.parseObject(request.post(url2, body2)).getJSONObject("data").getJSONArray("records");
            String entpId;
            if (index < entpIds.size() - 1) {
                entpId = entpIds.get(index).toString();
            } else {
                entpId = entpIds.get(0).toString();

            }
            String url1 = "http://api.uat.ustax.com.cn/finance/handleFee/add";
            String body = "{\n" +
                    "\t\"enterpriseId\": \"%s\",\n" +
                    "\t\"feeId\": \"1294199817345105921\",\n" +
                    "\t\"money\": 200,\n" +
                    "\t\"payMethod\": 1002,\n" +
                    "\t\"remark\": \"测试数据\",\n" +
                    "\t\"settleType\": 2001\n" +
                    "}";


            String enterpriseId = entpId;

            String actul = String.format(body,
                    enterpriseId);
            String response = request.post(url1, actul);
            System.out.println(response);

        }


    }


}
