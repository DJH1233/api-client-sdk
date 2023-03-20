package client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import entity.User;
import lombok.Data;
import utils.SignUtil;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
public class MyClient {
    private String accessKey;
    private String secretKey;
    public MyClient(){

    }
    public MyClient(String accessKey,String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String getNameByGet(String name){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        String result = HttpUtil.get("http://localhost:1234/name/get", map);
        return result;
    }

    public String getNameByPost(String name){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        String result = HttpUtil.post("http://localhost:1234/name/post", map);
        return result;
    }

    public String getNameByPostJson(User user) throws UnsupportedEncodingException {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post("http://localhost:1234/name/jsonPost")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        System.out.println(response.getStatus());
        return response.body();
    }

    public Map<String ,String> getHeaders(String body) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey",accessKey);
        map.put("sign", SignUtil.getSign(body,secretKey));
        map.put("body", URLEncoder.encode(body, StandardCharsets.UTF_8.name()));
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("timestamp",String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        User user = new User();
        user.setUserName("df");
        String nameByPostJson = new MyClient().getNameByPostJson(user);
        System.out.println(nameByPostJson);
    }


}
