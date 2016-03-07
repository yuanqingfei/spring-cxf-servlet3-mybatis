package com.yuanqingfei.order.api.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.DigestUtils;

public class Md5Util {
	public String genSignature(Map<String, Object> map){
		if(map == null || map.size() == 0){
			return "";
		}
		String result = "";
		StringBuilder builder = new StringBuilder();
		List<String> keyList = new ArrayList<String>();
		for(String key : map.keySet()){
			keyList.add(key);
		}
		Collections.sort(keyList);
		for(String key : keyList){
			builder.append("_");
			builder.append(map.get(key).toString());
		}
		result = builder.toString().substring(1);
		result = md5DigestAsHex(result);
		return result;
	}
	public String md5DigestAsHex(String plaintext) {
		return DigestUtils.md5DigestAsHex(plaintext.getBytes());
	}
}
