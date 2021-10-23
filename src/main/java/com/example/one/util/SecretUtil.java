package com.example.one.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class SecretUtil {
	private static final String iv = "hr#@sso!";
	private static final String key = "s00s9o0ijpswyd76d4ss2dre";
	private static Log log = LogFactory.getLog(SecretUtil.class);
	// 定义 加密算法,可用 DES,DESede,Blowfish
	private static final String Algorithm = "DESede";
	private static final String CipherAlgorithm = "DESede/CBC/PKCS5Padding";

	public static String encryptMD5(String data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(data.getBytes());
		return (new BASE64Encoder()).encode(digest.digest());
	}

	public static String encrypt3DES0(String src) {
		return encrypt3DES(key, iv, src);
	}

	public static String decrypt3DES(String src) {
		return decrypt3DES(key, iv, src);
	}

	public static String encrypt3DES(String key, String src) {
		try {
			byte[] bKey = key.getBytes("UTF-8");
			byte[] bIV = key.substring(0, 8).getBytes("UTF-8");
			byte[] bSrc = src.getBytes("UTF-8");
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(bKey, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(CipherAlgorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(bIV));
			return new BASE64Encoder().encode(c1.doFinal(bSrc));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			log.error(e2.getMessage());
		} catch (Exception e3) {
			e3.printStackTrace();
			log.error(e3.getMessage());
		}
		return null;
	}

	public static String encrypt3DES(String key, String iv, String src) {
		try {
			byte[] bKey = key.getBytes("UTF-8");
			byte[] bIV = iv.substring(0, 8).getBytes("UTF-8");
			byte[] bSrc = src.getBytes("UTF-8");
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(bKey, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(CipherAlgorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(bIV));
			return new BASE64Encoder().encode(c1.doFinal(bSrc));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			log.error(e2.getMessage());
		} catch (Exception e3) {
			e3.printStackTrace();
			log.error(e3.getMessage());
		}
		return null;
	}

	public static String decrypt3DES(String key, String src) {
		try {
			byte[] bKey = key.getBytes("UTF-8");
			byte[] bIV = key.substring(0, 8).getBytes("UTF-8");
			byte[] bSrc = new BASE64Decoder().decodeBuffer(src);
			Cipher c1 = Cipher.getInstance(CipherAlgorithm);
			SecretKey deskey = new SecretKeySpec(bKey, Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(bIV));
			return new String(c1.doFinal(bSrc), "UTF-8");
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			log.error(e2.getMessage());
		} catch (Exception e3) {
			e3.printStackTrace();
			log.error(e3.getMessage());
		}
		return null;
	}

	public static String decrypt3DES(String key, String iv, String src) {
		try {
			byte[] bKey = key.getBytes("UTF-8");
			byte[] bIV = iv.substring(0, 8).getBytes("UTF-8");
			byte[] bSrc = new BASE64Decoder().decodeBuffer(src);
			Cipher c1 = Cipher.getInstance(CipherAlgorithm);
			SecretKey deskey = new SecretKeySpec(bKey, Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(bIV));
			return new String(c1.doFinal(bSrc), "UTF-8");
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			log.error(e2.getMessage());
		} catch (Exception e3) {
			e3.printStackTrace();
			log.error(e3.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		// 你的用户名
		String e = SecretUtil.encrypt3DES0("admin");
		// 你的密码
		String e1 = SecretUtil.encrypt3DES0("123456");
		String d = SecretUtil.decrypt3DES(e);
		String d1 = SecretUtil.decrypt3DES(e1);
		// 使用加密的用户名密码登录
		System.out.println("用户名加密" + e);
		System.out.println("密码加密" + e1);
		System.out.println("用户名解密" + d);
		System.out.println("密码解密" + d1);
	}
}