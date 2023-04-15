package com.leyunone.laboratory.core.openapi;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 手机号遍历 查询 
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-09
 */
public class SearchIPhoneAPI {

    public static void main(String[] args0) throws IOException {

        String httpUrl = "http://apis.baidu.com/chazhao/mobilesearch/phonesearch";
        String httpArg = "";
        List<String> ds = new ArrayList<>();
        Set<String> ss = new HashSet<>();
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
//            String jsonResult = request(httpUrl, httpArg);
//            if (jsonResult.contains("上海")) {
//                fileWriter.write(httpArg + "\n\t");
//            }
            ds.add(httpArg);
            ss.add(httpArg);
        }
        System.out.println(ds.size());
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
