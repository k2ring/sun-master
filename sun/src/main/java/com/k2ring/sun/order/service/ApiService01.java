package com.k2ring.sun.order.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service("apiService01")
public class ApiService01 {

	public String encrypt(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());

		return bytesToHex(md.digest());
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	public Map<String, Object> restApi(Map<String, String> map, String url) throws Exception {

		String param = "";

		ObjectMapper mapper = new ObjectMapper();
		param = mapper.writeValueAsString(map);

		// ����� ���� ��
		// OkHttp ���
		// OkHttp = REST API , HTTP ����� �����ϰ� ����� �� �ֵ��� ������� ���̺귯��
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json"); // application/json �̰� �߿�
		RequestBody body = RequestBody.create(mediaType, param);
		Request request = new Request.Builder().url(url).post(body).addHeader("cache-control", "no-cache").build();
		// ����� �ޱ�
		Response response = client.newCall(request).execute();
		String result = response.body().string();

		ObjectMapper resultMapper = new ObjectMapper();
		Map<String, Object> resultMap = resultMapper.readValue(result, Map.class);

		return resultMap;
	}
}
