package com.lingtu.util;

public class sendCode {
	
	/**
	 * 产生验证码
	 * @return	验证码
	 */
	public static String produceCode(){
		String code = "";   //定义验证码   
	    int codeLength = 6;//验证码的长度     
	    int[] random = {0,1,2,3,4,5,6,7,8,9};//随机数  
	    for(int i = 0; i < codeLength; i++) {//循环操作  
	       int index = (int)(Math.random()*10);//取得随机数的索引（0~10）  
	       code += random[index];//根据索引取得随机数加到code上  
	   }
		return code;
	}
	public static void main(String[] args) {
		String code = produceCode();
		System.out.println(code);
	}
}
