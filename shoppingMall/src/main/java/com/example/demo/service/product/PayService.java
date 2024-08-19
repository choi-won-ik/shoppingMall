package com.example.demo.service.product;

import com.example.demo.Entity.Product;
import com.example.demo.dto.kakaoPay.KakaoPayDTO;
import com.example.demo.dto.kakaoPay.ReadyResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface PayService {
	public ReadyResponse ready(KakaoPayDTO kakaoPay);

	public String approve(String pgToken);

	public KakaoPayDTO kakaoDto(Product product);

	

	

}
