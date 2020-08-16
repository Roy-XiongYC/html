package com.person.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUrlRequest {

	

	/**
	 * 功能：发送http请求
	 * 
	 * @param url
	 *            请求地址
	 */
	public static String callHttp(String url) throws Exception {
		return callHttp(url, "", null, null, null);
	}

	/**
	 * 功能：发送http请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            发送内容信息
	 */
	public static String callHttp(String url, String content) throws Exception {
		return callHttp(url, content, null, null, null);
	}

	/**
	 * 功能：发送http请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            发送内容信息
	 * @param sendEncode
	 *            发送信息编码,默认UTF-8
	 * @param receiveEncode
	 *            接收信息编码,默认UTF-8
	 */
	public static String callHttp(String url, String content,
			String sendEncode, String receiveEncode) throws Exception {
		return callHttp(url, content, sendEncode, receiveEncode, null);
	}

	/**
	 * 功能：发送http请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            发送内容信息
	 * @param sendEncode
	 *            发送信息编码,默认UTF-8
	 * @param receiveEncode
	 *            接收信息编码,默认UTF-8
	 * @param propertys
	 *            其他属性Map,没有就放null
	 * @return
	 * @throws Exception
	 */
	public static String callHttp(String url, String content,
			String sendEncode, String receiveEncode,
			Map<String, String> propertys) throws Exception {
		if (content == null) {
			throw new Exception("请求内容为空");
		}
		if (url == null) {
			throw new Exception("请求地址为空");
		}
		if (StringUtils.isBlank(sendEncode)) {
			sendEncode = "UTF-8";// 默认编码uft-8
		}
		if (StringUtils.isBlank(receiveEncode)) {
			receiveEncode = "UTF-8";// 默认编码uft-8
		}

		URL u0 = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u0.openConnection();

		byte[] contentbyte = content.getBytes();

		conn.setRequestMethod("POST");
		if (propertys != null) {
			for (Map.Entry<String, String> obj : propertys.entrySet()) {
				if (!StringUtils.isBlank(obj.getKey())
						&& !StringUtils.isBlank(obj.getValue())) {
					conn.setRequestProperty(obj.getKey(), obj.getValue());
				}
			}
		}
		conn.setRequestProperty("Content-Type", "text/xml; charset="
				+ sendEncode);
		conn.setRequestProperty("Content-Length",
				Integer.toString(contentbyte.length));
		// 链接不使用缓存
		conn.setUseCaches(false);
		// URL链接允许输入,默认为true,此处可不设置
		conn.setDoInput(true);
		// URL链接允许输出,默认为true
		conn.setDoOutput(true);

		OutputStream out = conn.getOutputStream();
		OutputStreamWriter outputwriter = new OutputStreamWriter(out,
				sendEncode);

		outputwriter.write(content);
		outputwriter.flush();
		outputwriter.close();

		InputStream in = conn.getInputStream();
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		while (i != -1) {
			i = in.read();
			if (i != -1) {
				buffer.append((char) i);
			}
		}
		in.close();

		if (conn.getResponseCode() != 200) {
			return null;
		}
		String result = "";// 返回信息字符串
		result = new String(buffer.toString().getBytes("iso-8859-1"),
				receiveEncode);
		return result;
	}
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */

	public static String sendGet(String url, int timeout)throws Exception{
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			//conn.setRequestProperty("Content-Type","text/html;charset=GBK");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(180*1000);
			// 建立实际的连接
			conn.connect();	
			// 获取所有响应头字段
//			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result +=line;
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	/**
	 * post 请求
	 *
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> map)
			throws IOException {
		HttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		if (url.startsWith("https://")) {
			sslClient(httpClient);
		}
		String html = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : map.keySet()) {
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		try {
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				html = EntityUtils.toString(httpEntity);
			}
			httppost.abort();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return html;
	}

	private static void sslClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] xcs, String str) {

				}
				public void checkServerTrusted(X509Certificate[] xcs, String str) {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(HttpUrlRequest.callHttp("http://www.51kdd.com/api/json/result/?com=ems&kw=EC248695015CS&valite=&key=FREE-KEY-FGJ794GN"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
