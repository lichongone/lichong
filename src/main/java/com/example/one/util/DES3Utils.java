package com.example.one.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;


public class DES3Utils {
    private final static String encoding = "UTF-8";

    /**
     * 方法描述：3DES加密
     * @author guoxk
     * @createTime 2017年5月23日 上午9:03:44
     *
     * @param plainText  明文
     * @param secretKey  密钥
     * @param iv         加密向量
     * @return String    密文
     * @throws Exception
     */
    public static String encode(String plainText, String secretKey, String iv)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encodeBase64String(encryptData);
    }

    /**
     * 方法描述： 3DES解密
     * @author guoxk
     * @createTime 2017年5月23日 上午9:04:37
     *
     * @param encryptText 密文
     * @param secretKey   密钥
     * @param iv          加密向量
     * @return String     明文
     * @throws Exception
     */
    public static String decode(String encryptText, String secretKey, String iv)
            throws Exception {
        if (StringUtils.isBlank(encryptText) || StringUtils.isBlank(secretKey)) {
            return "";
        }
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decodeBase64(encryptText));
        return new String(decryptData, encoding).trim();
    }
    /**
     * 方法描述：测试方法
     * @author guoxk
     * @createTime 2017年5月23日 上午9:12:24
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String key = "4SF6BJ3D8TDOT8NOCZ8T7P1K";
            String iv = "13002542";//IV length must be 8 bytes long
            //加密
//            String encryptStr = DES3.encode("明文", key, iv);
//            System.out.println(encryptStr);
            //解密
//            String decryptStr = DES3.decode(encryptStr, key, iv);
//            System.out.println(decryptStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

