package com.lingtu.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToStringUtil
{

	/**
	 * 把一个对象（bean 对象）， 转化为字符串， 一般用于调试程序， 查看对象中得数据
	 * 
	 * @param oo
	 * @return
	 */
	public static String BeanToString(Object oo)
	{

		try
		{
			return ToStringBuilder.reflectionToString(oo);

		} catch (Exception se)
		{
		}
		return "";

	}
	/**
	 * 验证码:产生一个随机的6位数字
	 * @return
	 */
	public static String Identiy(){
		String[] beforeShuffle = new String[] {"0","1","2", "3", "4", "5", "6", "7",
				"8", "9" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);// 用于随机排列随机使用一个默认的源指定的列表
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(2, 8);
		return result;
	}
}
