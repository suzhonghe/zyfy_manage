package com.zhongyang.java.system.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.zhongyang.java.system.Util;

import net.sf.json.JSONObject;

/**
 * 
 * http接口
 * 
 * @author Matthew
 *
 */
public class ZyHttpClient {
	
	private static Logger logger=Logger.getLogger(ZyHttpClient.class);

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static Object getHttp(String url, Map<String, String> requestMap, String encoding) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);
			httpost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			JSONObject jsonObject = JSONObject.fromObject(requestMap);
			StringEntity se = new StringEntity(jsonObject.toString());
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			se.setContentEncoding(encoding);
			se.setContentType("application/json");
			httpost.setEntity(se);
			HttpResponse resp = httpClient.execute(httpost);
			InputStream content = resp.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content, "UTF-8"));
			String inputLine;
			StringBuffer bankXmlBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				bankXmlBuffer.append(inputLine);
			}
			in.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String requestByGetMethod(String url) {
		// 创建默认的httpClient实例
		String returnString=null;
		CloseableHttpClient httpClient = getHttpClient();
		try {
			// 用get方法发送http请求
			url = url.replaceAll(" ", "%20");
			HttpGet get = new HttpGet(url);
			HttpPost post = new HttpPost(url);
			System.out.println("执行get请求:...." + get.getURI());
			logger.info("执行get请求:...." + get.getURI());
			CloseableHttpResponse httpResponse = null;
			// 发送get请求
			httpResponse = httpClient.execute(post);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					returnString = EntityUtils.toString(entity);
					System.out.println("响应状态码:" + httpResponse.getStatusLine());
					System.out.println("响应内容:" + returnString);
					logger.info("响应状态码:" + httpResponse.getStatusLine());
					logger.info("响应内容:" + returnString);
				}
			} finally {

				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnString;

	}

	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	}
}