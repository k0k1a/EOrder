package com.cdu.lys.graduation.commons.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JWT生成工具类
 * @author liyinsong
 * @date 2022/1/9 19:26
 */
public class JWTUtils {

    /**
     * 生成jwt
     * @param subject   代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
     * @param ttlMillis 过期时间
     * @param claims    创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
     * @param secretKey 秘钥
     * @return jwt
     */
    public static String createJWT(String subject, long ttlMillis, Map<String, Object> claims,String secretKey) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey key = generalKey(secretKey);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(hs256, key);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }

        return builder.compact();
    }

    /**
     * 生成jwt字符串,payload中不带参数
     * @param secretKey 秘钥，不能泄露，泄露后用户就能自由签发jwt
     * @return jwt
     */
    public static String createJWT(String secretKey) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey key = generalKey(secretKey);

        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .signWith(hs256, key);

        return builder.compact();
    }

    /**
     * 生成秘钥key
     * @param secretKey BASE64格式的key，即是秘钥
     * @return key
     */
    private static SecretKey generalKey(String secretKey) {

        //本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(secretKey);

        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
        return key;
    }


    /**
     * 解析jwt字符串
     * @param jwt jwt字符串
     * @return 返回jwt payload
     */
    public static Claims parseJWT(String jwt,String key) {
        SecretKey secretKey = generalKey(key);

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt).getBody();
    }

    //完成最终版本后删除
    /*public static void main(String[] args) {
        System.out.println("========test01========");

        Map<String, Object> map = new HashMap<>();
        map.put("username", "test");
        String key = Base64.encodeBase64String("123".getBytes(StandardCharsets.UTF_8));

        String jwt = JWTUtils.createJWT( "a1", System.currentTimeMillis(), map, key);
        System.out.println(jwt);
        System.out.println("start parse");


        Claims x = parseJWT(jwt,key);
        System.out.println(x);
        System.out.println("audience="+x.getAudience());
        System.out.println("subject="+x.getSubject());
        System.out.println("expire="+x.getExpiration());
        System.out.println("id="+x.getId());
        System.out.println("iss="+x.getIssuedAt());
        System.out.println("issuser="+x.getIssuer());
        System.out.println("notbefor="+x.getNotBefore());
        System.out.println("username="+x.get("username"));

        System.out.println("========test02========");
        String jwt1 = JWTUtils.createJWT(key);
        System.out.println("jwt="+jwt1);
        System.out.println(JWTUtils.parseJWT(jwt1, key));

    }*/

}
