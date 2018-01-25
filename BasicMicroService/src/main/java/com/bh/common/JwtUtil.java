package com.bh.common;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.json.JSONObject;

public class JwtUtil {

	/**
	 * 将请求头里的令牌解析成jsonobject(可以改成一个公用的方法）
	 * @author lilei
	 * @date   2017年12月15日 下午4:42:28 
	 * @param httpServletRequest
	 * @return
	 */
	public JSONObject getJwtjsonByRequire(HttpServletRequest httpServletRequest){
		try {
			//获得头信息
			String jwtstr = httpServletRequest.getHeader("Authorization");
			jwtstr = jwtstr.replace("Bearer ", "");//去掉"Bearer "
			//解析令牌
			JwtConsumer jwtConsumer = new JwtConsumerBuilder().setSkipAllValidators().setDisableRequireSignature()
					.setSkipSignatureVerification().build();
			JwtContext jwtContext = jwtConsumer.process(jwtstr);
			//转成JSONObject
			String flatJson = JsonUtil.toJson(jwtContext.getJwtClaims().getClaimsMap());
			JSONObject jsonObject = new JSONObject(flatJson);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
