package com.demo.uploads.demo.utils;

public class RedisUtils {

    public static final String USER_KEY = "user:";

    public static String getUserKey(String id) {

        return  USER_KEY + id;
    }
}