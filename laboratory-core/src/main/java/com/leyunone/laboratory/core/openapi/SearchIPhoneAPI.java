package com.leyunone.laboratory.core.openapi;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 手机号遍历 查询 
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-09
 */
public class SearchIPhoneAPI {

    public static void main(String[] args0) throws IOException {

        FileWriter fileWriter = new FileWriter("/Users/huang/test/test1.txt");
        String httpUrl = "http://apis.baidu.com/chazhao/mobilesearch/phonesearch";
        String httpArg = "";
        for (int i = 0; i <= 9999; i++) {
            if (i >= 1000) {
                httpArg = "phone=178" + String.valueOf(i) + "0077";
            } else if (i >= 100) {
                httpArg = "phone=1780" + String.valueOf(i) + "0077";
            } else if (i >= 10) {
                httpArg = "phone=17800" + String.valueOf(i) + "0077";
            } else {
                httpArg = "phone=178000" + String.valueOf(i) + "0077";
            }
            String jsonResult = request(httpUrl, httpArg);
            if (jsonResult.contains("上海")) {
                fileWriter.write(httpArg + "\n\t");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {

        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");// 填入apikey到HTTP headerconnection.setRequestProperty("apikey", "您自己的apikey");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
