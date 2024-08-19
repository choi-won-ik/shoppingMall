package com.example.demo.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.kakaoPay.KakaoPayDTO;
import com.example.demo.dto.kakaoPay.ReadyResponse;
import com.example.demo.service.product.PayService;
import com.example.demo.service.product.PayServiceImpl;

@Controller
public class KakaoPayController {

	@Autowired
	private PayService payService;
	
	private static String result=""; 

	@PostMapping("kakaoPay")
	@ResponseBody
	public ReadyResponse kakaoPay(Model model,@RequestBody KakaoPayDTO product) {
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println(product);
		System.out.println("=====================");
		System.out.println("=====================");
		
		ReadyResponse readyResponse = payService.ready(product);
		result="";
		return readyResponse;
	}

	@GetMapping("/approve")
	public String approve(@RequestParam("pg_token") String pgToken, Model model) {
		payService.approve(pgToken);
		result="approve";
		return "product/kakao/approve";
	}

	@GetMapping("/cancel")
	public String cancel() {
		// 주문건이 진짜 취소되었는지 확인 후 취소 처리
		// 결제내역조회(/v1/payment/status) api에서 status를 확인한다.
		// To prevent the unwanted request cancellation caused by attack,
		// the “show payment status” API is called and then check if the status is
		// QUIT_PAYMENT before suspending the payment
		return "product/kakao/cancel";
	}

	@GetMapping("/fail")
	public String fail() {
		// 주문건이 진짜 실패되었는지 확인 후 실패 처리
		// 결제내역조회(/v1/payment/status) api에서 status를 확인한다.
		// To prevent the unwanted request cancellation caused by attack,
		// the “show payment status” API is called and then check if the status is
		// FAIL_PAYMENT before suspending the payment
		return "product/kakao/fail";
	}
	
	@GetMapping("/complete")
	public String success() {
		
		if(result.equals("approve")) {
			return "product/complete";
		}else {
			return "product/failed";
		}
	}
}
