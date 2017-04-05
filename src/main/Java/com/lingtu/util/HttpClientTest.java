package com.lingtu.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.JsonArray;


public class HttpClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String str = HttpClientTest.PostUrl("http://localhost:8080/webspring/httpclient/test.do");
//		Map<String,Object> map = jsonUtil.toMap(str);
//		System.out.println(map.get("code")+"==="+map.get("message")+"==="+map.get("score"));

		

//		String str1 = HttpClientTest.PostURL("http://localhost:8080/busmanage/main.do");
//		Map<String,Object> map = jsonUtil.toMap(str);
//		System.out.println(map.get("message"));
//		String str1 = jsonUtil.toJson(map.get("result"));
//		Map<String,Object> map1 = jsonUtil.toMap(str1);
//		String str2 = jsonUtil.toJson(map1.get("routes"));
//		List list = jsonUtil.toList(str2);
//		String str3 = jsonUtil.toJson(list.get(1));
//		Map<String,Object> map2 = jsonUtil.toMap(str3);
//		String str1 = jsonUtil.parseJson(str).getAsJsonObject("result").toString();
//		JsonArray arry = jsonUtil.parseJson(str1).getAsJsonArray("routes");
//		String s = jsonUtil.parseJson(arry.get(0).toString()).getAsJsonPrimitive("price").toString();
//		System.out.println(str);
		
	}
	public String toUrlEncoding(String src) throws UnsupportedEncodingException{
		String str = java.net.URLEncoder.encode(src,"utf-8");
		return str;
	}
	public static String GetUrl(String strurl){
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(strurl);
		try
		{
			int code = client.executeMethod(get);
			System.out.println(code+"=======");
			byte [] date = get.getResponseBody();
			String bdate = new String(date,"utf-8");
			return bdate;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
//	public static String PostUrl(String strurl){
//		HttpClient client = new HttpClient();
//		try
//		{
//			PostMethod post = new PostMethod(strurl);
//			String userid = "5";
//			String userscore = "10000";
//			String transid = "1111226";
//			post.addParameter("userid", userid);
//			post.addParameter("userscore", userscore);
//			post.addParameter("transid",transid);
//			String s = MD5.MD5Encode(userid+userscore+transid);
//			post.addParameter("sign",s);
//			int code = client.executeMethod(post);
//			byte [] date = post.getResponseBody();
//			String bdate = new String(date,"utf-8");
//	        return bdate;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
	public static String PostURL(String strurl,String str){
		String sret = null;
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(strurl);
			post.addParameter("coordsys", str);
			int ncode = client.executeMethod(post);
			byte[] bdata =post.getResponseBody();
			String strdata =  new String(bdata,"utf-8");
			return strdata;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sret;
	}
 

}
