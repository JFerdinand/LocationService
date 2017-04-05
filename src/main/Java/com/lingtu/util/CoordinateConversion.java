package com.lingtu.util;

import java.math.BigDecimal;
import java.util.Map;

public class CoordinateConversion {
	//(lng,lat)
	public static String conversion(String coordinate){
		//百度转wgs
		String before = HttpClientTest.PostURL("http://172.17.8.58:8091/pointconvert/convert/baidu/wgs.do",coordinate);
		Map<String,Object> map = jsonUtil.toMap(before);
		String str1 = map.get("message").toString().replace("\"", "");
		System.out.println(str1);
		//wgs转高德
		String after = HttpClientTest.PostURL("http://172.17.8.58:8091/pointconvert/convert/gaode/amap.do",str1);
		map = jsonUtil.toMap(after);
		String str2 = map.get("message").toString().replace("\"", "");
		return str2;
	} 
	//lng lat
	public static String conversion2(BigDecimal lng,BigDecimal lat){
		String str = lng.toString()+","+lat.toString();
		conversion(str);
		return conversion(str);
	}
	public static void main(String[] args) {
//		String lnglatarray = "[116.232986,39.914753],[116.233101,39.91423],[116.233299,39.913368],[116.233521,39.912361]";
//		String str = lnglatarray.substring(1, lnglatarray.length()-1).replace("],[",";");
//		String s[] =  conversion(str).split(";");
		String s = "116.288208,40.092793";
		System.out.println(conversion(s));
	}
}
