package com.example.demo.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
	private String userid;
	private String name;
	private StringBuilder profile;
	private List<ProductList> product;
	private int productNUM;
	private long followNUM;
}
