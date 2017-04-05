package com.lingtu.test;

/**
 * Created by jiangmingjun on 17-3-14.
 */
import com.lingtu.util.BCrypt;
import com.lingtu.util.jsonUtil;

import java.security.SecureRandom;
public class Test {

    public static void main(String[] args) {
        /*
        SHA256
         */
//        SecureRandom random = new SecureRandom();
//        byte[] aesKey = new byte[20];
//        random.nextBytes(aesKey);
//        StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i <aesKey.length ; i++) {
//            String hex = Integer.toHexString(0xff & aesKey[i]);
//            if(hex.length() == 1){
//                hexString.append('0');
//            }
//            hexString.append(hex);
//        }
//        System.out.println(hexString);
        /*
        BCrypt
         */
        String pass = "qwe32144.56";

        String hashed = BCrypt.hashpw(pass,BCrypt.gensalt());
        System.out.println(hashed);
        System.out.println(BCrypt.checkpw("qwe32144.56","$2a$10$SjHb6aRB.yTZ5XmdJDb3quQDL9zyHjlHCJIrml8RrUW4ws.m0wBoC"));
    }
}
