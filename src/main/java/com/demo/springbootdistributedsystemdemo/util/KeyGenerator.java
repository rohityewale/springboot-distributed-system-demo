package com.demo.springbootdistributedsystemdemo.util;

import org.springframework.stereotype.Component;

public final class KeyGenerator {

    public static String getProductKey(String id) {
        return "Product:"+ id;
    }
}
