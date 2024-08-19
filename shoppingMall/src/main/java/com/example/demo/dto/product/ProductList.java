package com.example.demo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductList {
	private String id;
	private String productName;
	private int price;
	private String productStreetaddress;
	private String img;
	private int productNo;
}
