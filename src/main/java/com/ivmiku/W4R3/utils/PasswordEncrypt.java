package com.ivmiku.W4R3.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Random;

public class PasswordEncrypt {
    public static String getSalt(int n){
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            //Random().nextInt()返回值为[0,n)
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static String encrypt(String password, String salt) {
        SimpleHash sh = new SimpleHash("MD5", password, salt, 5);
        return sh.toHex();
    }
}
