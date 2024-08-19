package com.example.demo.dto.product;

import com.example.demo.Entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformation {
	private int productNo;
	private Member user;

	private String productName;

	private String productDescription;

	private int productPrice;

//	private Category category;

	private int quantity;
}
