package com.zc.gateway;

public class Test {

    public static void main(String[] args) {
        String s = "/config/get";
        String reg = "/config/[^/]+";
//        String reg = "/config/.+";
        System.err.println(s.matches(reg));
    }

}
