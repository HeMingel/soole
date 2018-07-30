package com.songpo.searched.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;

public class MD5SignUtils {
	
	public static final String CHARSET_NAME_DEFAULT = "UTF-8";
	
	/**
	 * 创建签名
	 * @param packageParams
	 * @return
	 */
	public static String createMD5Sign(SortedMap<String, String> packageParams, String charsetName) {
		StringBuffer sb = new StringBuffer();
		String sign = "";
		try{
			Set es = packageParams.entrySet();
			Iterator it = es.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String k = (String) entry.getKey();
				String v = (String) entry.getValue();
				if(!StringUtils.isEmpty(k) && !StringUtils.isEmpty(v)){
//					System.out.println("k[" + k + "]");
//					System.out.println("v[" + v + "]");				
					sb.append(k + "=" + v + "&");
				}
			}
			String strMsg = sb.toString();
			System.out.println("SignSrcMsg[" + strMsg + "]");
			sign = MD5Util.md5encode(strMsg, charsetName);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return sign;
	}
	
	/**
	 * 验证签名
	 * @param packageParams
	 * @return
	 */
	public static boolean validateMD5Sign(SortedMap<String, String> packageParams, String charsetName, String strSrcMsg) {
		boolean validate = false;
		try{
			String strMD5Sign = createMD5Sign(packageParams, charsetName);
			if(StringUtils.equals(strMD5Sign, strSrcMsg)){
				validate = true;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return validate;
	}
	public static void main(String[] args){
		String charsetName = "UTF-8";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("uid", "11111");
		String strMsg = MD5SignUtils.createMD5Sign(packageParams, charsetName);
		boolean flag = MD5SignUtils.validateMD5Sign(packageParams, charsetName, strMsg);
		System.out.println(strMsg);
		System.out.println(flag);
		
	}
}
