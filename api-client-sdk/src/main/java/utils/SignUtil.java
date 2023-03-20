package utils;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtil {
    public static String getSign(String body,String secretKey){
        return DigestUtil.md5Hex(body + "." + secretKey);
    }
}
