package com.example.demo.handler;

import java.util.Base64;

public class EncodeToBase64 {
	public static String encodeToBase64(byte[] imageBytes) {
		return Base64.getEncoder().encodeToString(imageBytes);
	}
}
