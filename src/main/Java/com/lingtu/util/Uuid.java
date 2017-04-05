package com.lingtu.util;

import java.util.UUID;

public class Uuid {
	public static String uuid(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString().replace("-", "");
		return str;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        System.out.println(uuid());    
	}

}
