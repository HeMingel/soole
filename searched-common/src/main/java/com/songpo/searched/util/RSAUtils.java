package com.songpo.searched.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.springframework.util.Base64Utils;

public class RSAUtils {
	
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * RSA最大加密明文大小
	 */
	 private static final int MAX_ENCRYPT_BLOCK = 117;
	 	 
	 /**
	  * 签名算法
	  */
	 public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	 
	 public static final String DATA_ENCODING = "UTF-8";
		
	/**
	 * 使用公钥加密
	 * @param content
	 * @param publicKey
	 * @return
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey){
		byte[] keyBytes  = null;
		KeyFactory keyFactory = null;
		Key publicK = null;
		Cipher cipher = null;
		int inputLen = 0;
		ByteArrayOutputStream out = null;
		X509EncodedKeySpec x509KeySpec = null;
		byte[] encryptedData = null;
		try{
			keyBytes = Base64Utils.decode(publicKey.getBytes());
			x509KeySpec = new X509EncodedKeySpec(keyBytes);
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			publicK = keyFactory.generatePublic(x509KeySpec);
			// 对数据加密
			cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			inputLen = data.length;
			out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}		
		return encryptedData;
	}
	
    

    
	/**
	 * 公钥加密随机串
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public static String encryptByPublicKey(String data,String publicKey){
		byte[] dataNoteStr = data.getBytes();
		byte[] encodedDataNoteStr = encryptByPublicKey(dataNoteStr, publicKey);
		String encodedNoteStr = AESUtils.parseByte2HexStr(encodedDataNoteStr);
		return encodedNoteStr;
	}
	
	public static void main(String[] args){
	}
}
