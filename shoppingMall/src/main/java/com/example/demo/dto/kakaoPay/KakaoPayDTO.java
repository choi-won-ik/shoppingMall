package com.example.demo.dto.kakaoPay;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayDTO {
	// 주문번호
	private String partnerOrderId;
	// 회원 아이디
	private String partnerUserId;
	
	private String itemName;
	// 상품수령
	private int quantity;
	// 총액
	private int totalAmount;
	// 비과세
	private int taxFreeAmount;
	// 주소
	private String productStreetaddress;
	// 상세주소
	private String productDetailaddress;
}
