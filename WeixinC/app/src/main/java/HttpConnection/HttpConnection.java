package HttpConnection;

/**
 * Created by 14063 on 2017/12/6.
 */

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import Bean.StorageApplication;

public class HttpConnection {
    private static StorageApplication storageApplication;
    private static String URI_S = "http://192.168.0.107:8080//v1/";///192.168.0.103
     static ArrayList<HashMap<String, Object>> list;
    private static String message;

    //封装新闻的假数据到list中返回
    public static ArrayList<HashMap<String, Object>> getConnection(Context context, String path, HashMap<String, String> params) {

        list = new ArrayList<>();
        try {
            //初始化Url
            URL url = new URL(getURLWithParams(URI_S, path, params));
            System.err.println("url——path:" + url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1000 * 5);
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream is = httpURLConnection.getInputStream();

                String reult = getInputStream.getJsonByInternet(is);
                is.close();

                if (path.equals("user/login")) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("message", login(context, reult));
                    list.add(map);
                } else if (path.equals("user/register")) {

                } else if (path.equals("user/load")) {
                    list=load(context, reult, list);
                } else if (path.equals("user/add")) {

                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("地址错误，请改正当前Url:", "" + url);
                    list.add(map);
                }
            }else{ HashMap<String, Object> map = new HashMap<>();
                map.put("地址错误，请改正当前Url:", "" + url);
                list.add(map);}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @param topAddress
     */

    //组装出带参数的完整URL
    public static String getURLWithParams(String topAddress, String path, HashMap<String, String> params) throws UnsupportedEncodingException {
        //设置编码
        final String encode = "UTF-8";
        String address = topAddress + path;
        StringBuilder url = new StringBuilder(address);
        url.append("?");
        //将map中的key，value构造进入URL中
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

    /*  private   int id;
        private  String userName;
        private  String userPassWord;
        private  String sex;
        private  String email;
        private  String userPhone;
        private  String userAddress;
        private  int userStatus;
        private  int userCode;
        private  int informationCode;
        private String userCreatTime;
        */
    public static String  login(Context context, String reult) {
        String sex = "";
        storageApplication = (StorageApplication) context.getApplicationContext();
        try {
            JSONObject jsonObject = new JSONObject(reult);
            System.err.println("1ddd11:" + jsonObject);
            JSONObject jsonObjectData = jsonObject.getJSONObject("item");
              message= jsonObject.getString("message");
            System.err.println("array11:" + jsonObjectData);
            JSONArray array = jsonObjectData.getJSONArray("data");
            System.err.println("array:" + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);
                try{
                storageApplication.setId(jsonObject1.getInt("id"));
                storageApplication.setUserName(jsonObject1.getString("username"));
                storageApplication.setUserPassWord(jsonObject1.getString("userpassword"));
                switch (jsonObject1.getInt("sex")) {
                    case 0:
                        sex = "女";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    default:
                        sex = "";
                }
                storageApplication.setSex(sex);
                storageApplication.setEmail(jsonObject1.getString("email"));
                storageApplication.setUserPhone(jsonObject1.getString("userPhone"));
                storageApplication.setUserAddress(jsonObject1.getString("userAddress"));
                storageApplication.setUserStatus(jsonObject1.getInt("userStatus"));

                storageApplication.setInformationCode(jsonObject1.getInt("informationCode"));
                storageApplication.setUserCreatTime(jsonObject1.getString("userCreatTime"));
                }catch (Exception e) {
                    e.printStackTrace();
                }
                storageApplication.setUserCode(jsonObject1.getInt("usercode"));
            }

            System.out.println("message2"+message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    public static void register(Context context, String reult, ArrayList<HashMap<String, Object>> list) {
        String sex = "";
        try {
            JSONObject jsonObject = new JSONObject(reult);
            System.err.println("1ddd11:" + jsonObject);
            JSONObject jsonObjectData = jsonObject.getJSONObject("item");
            System.err.println("array11:" + jsonObjectData);
            JSONArray array = jsonObjectData.getJSONArray("data");
            System.err.println("array:" + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", jsonObject1.getInt("id"));
                map.put("userName", jsonObject1.getString("userName"));
                map.put("userPassWord", jsonObject1.getString("userPassWord"));
                switch (jsonObject1.getInt("sex")) {
                    case 0:
                        sex = "女";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    default:
                        sex = "";
                }
                map.put("sex", sex);
                map.put("email", jsonObject1.getString("email"));
                map.put("userPhone", jsonObject1.getString("userPhone"));
                map.put("userAddress", jsonObject1.getString("userAddress"));
                map.put("userStatus", jsonObject1.getInt("userStatus"));
                map.put("userCode", jsonObject1.getInt("userCode"));
                map.put("informationCode", jsonObject1.getInt("informationCode"));
                map.put("userCreatTime", jsonObject1.getString("userCreatTime"));
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  ArrayList<HashMap<String, Object>> load(Context context, String reult, ArrayList<HashMap<String, Object>> list) {
        String sex = "";
        try {
            JSONObject jsonObject = new JSONObject(reult);
            System.err.println("1ddd11:" + jsonObject);
            JSONObject jsonObjectData = jsonObject.getJSONObject("item");
            System.err.println("array11:" + jsonObjectData);
            JSONArray array = jsonObjectData.getJSONArray("data");
            System.err.println("array:" + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                try{
                map.put("id", jsonObject1.getInt("id"));
                map.put("userName", jsonObject1.getString("username"));
                map.put("userPassWord", jsonObject1.getString("userpassword"));
                switch (jsonObject1.getInt("sex")) {
                    case 0:
                        sex = "女";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    default:
                        sex = "";
                }
                map.put("sex", sex);
                map.put("email", jsonObject1.getString("email"));
                map.put("userPhone", jsonObject1.getString("userPhone"));
                map.put("userAddress", jsonObject1.getString("userAddress"));
                map.put("userStatus", jsonObject1.getInt("userStatus"));

                map.put("informationCode", jsonObject1.getInt("informationCode"));
                map.put("userCreatTime", jsonObject1.getString("userCreatTime"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("userCode", jsonObject1.getInt("usercode"));
                map.put("Flags", 0+"");
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  list;
    }
    public static void add(Context context, String reult, ArrayList<HashMap<String, Object>> list) {
        String sex = "";
        try {
            JSONObject jsonObject = new JSONObject(reult);
            System.err.println("1ddd11:" + jsonObject);
            JSONObject jsonObjectData = jsonObject.getJSONObject("item");
            System.err.println("array11:" + jsonObjectData);
            JSONArray array = jsonObjectData.getJSONArray("data");
            System.err.println("array:" + array);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                try{
                map.put("id", jsonObject1.getInt("id"));
                map.put("userName", jsonObject1.getString("userName"));
                map.put("userPassWord", jsonObject1.getString("userPassWord"));
                switch (jsonObject1.getInt("sex")) {
                    case 0:
                        sex = "女";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    default:
                        sex = "";
                }
                map.put("sex", sex);
                map.put("email", jsonObject1.getString("email"));
                map.put("userPhone", jsonObject1.getString("userPhone"));
                map.put("userAddress", jsonObject1.getString("userAddress"));
                map.put("userStatus", jsonObject1.getInt("userStatus"));
                map.put("userCode", jsonObject1.getInt("userCode"));
                map.put("informationCode", jsonObject1.getInt("informationCode"));
                map.put("userCreatTime", jsonObject1.getString("userCreatTime"));
                list.add(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


