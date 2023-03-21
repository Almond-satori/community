package com.community.utils;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESGenerator {

    private static final String AES = "AES";

    /**
     * 由id生成对应的密钥
     * @param id 用户id
     * @return
     */
    public static String generateKey(Integer id){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecureRandom seed = new SecureRandom();

        seed.setSeed(id); //密钥
        keyGenerator.init(seed);
        SecretKey secretKey = keyGenerator.generateKey();
        //采用base64将密钥编码为字符串
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 生成密文
     * @param content 待加密字符串
     * @param keyStr 密钥字符串
     * @return
     */
    public static String generateCypherText(String content,String keyStr){
        String cypherText = null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            //keyStr转换为secretKey
            byte[] decode = Base64.getDecoder().decode(keyStr);
            SecretKey secretKey = new SecretKeySpec(decode, 0, decode.length, AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            cipher.update(content.getBytes());
            cypherText = Base64.getEncoder().encodeToString(cipher.doFinal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cypherText;
    }

    /**
     * 解密为字符串
     * @param cypherText 密文
     * @param keyStr 密钥字符串
     * @return
     */
    public static String decode(String cypherText,String keyStr){
        byte[] decodeRes = null;
        try {
            Cipher cipher = Cipher.getInstance(AES);
            //keyStr转换为secretKey
            byte[] decode = Base64.getDecoder().decode(keyStr);
            SecretKey secretKey = new SecretKeySpec(decode, 0, decode.length, AES);
            //解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decodeRes = cipher.doFinal(Base64.getDecoder().decode(cypherText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decodeRes);
    }
}
