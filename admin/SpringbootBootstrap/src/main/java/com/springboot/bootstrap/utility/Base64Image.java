package com.springboot.bootstrap.utility;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Image {
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
