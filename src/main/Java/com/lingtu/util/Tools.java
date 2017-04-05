package com.lingtu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools
{
	static int IncValue = 0;
	/**
	 * 产生一个唯一的整数数值
	 * @return
	 */
	public static long getUniqueInteger()
	{
		long ival = System.currentTimeMillis();
		IncValue++;
		return ival + IncValue;
	}
	
	/**
	 * 工具类， 将date 转成string 字符
	 * 
	 * @param d
	 * @return
	 */
	public static String DateToString(Date d)
	{
		String sret = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			sret = df.format(d);

		} catch (Exception se)
		{
		}
		return sret;
	}

	/**
	 * 将 string 解析为date
	 * 
	 * @param strdate
	 * @return
	 */
	public static Date StringtoDate(String strdate)
	{
		Date dret = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dret = df.parse(strdate);

		} catch (Exception se)
		{
		}
		return dret;
	}
	

	
	/**
	 * 将boolean 转换为字符
	 * @param b
	 * @return
	 */
	public static String BooleanToString(boolean b)
	{
		return Boolean.toString(b);
		
	}
	/**
	 * 将字符串转化为boolean 
	 * @param s
	 * @return
	 */
	public static boolean StringtoBoolean(String s)
	{
		
		Boolean ob = new Boolean(s);
		return ob.booleanValue();
	}
	/**
	 * 字符串之间插入一个冒号
	 * @param src
	 * @param dec
	 * @param position
	 * @return
	 */
	public static String insertPosition(String src, String dec, int position){
	    StringBuffer stringBuffer = new StringBuffer(src);

	    return stringBuffer.insert(position, dec).toString();

	}
	
	
	public static void main(String args [] )
	{
		for(int i=0;i<100;i++)
		System.out.println (Tools.getUniqueInteger());
		
	}
}
