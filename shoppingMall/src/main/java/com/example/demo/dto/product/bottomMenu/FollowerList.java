package com.example.demo.dto.product.bottomMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowerList {
	private String img;
	private String user;
	private int productNUM;
	private String userid;
	private boolean follow;
}
