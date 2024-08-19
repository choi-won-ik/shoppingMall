package com.example.demo.dto.product.bottomMenu;

import java.util.List;

import com.example.demo.dto.product.MainProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowingList {
	private String user;
	private int productNUM;
	private Long followerNUM;
	private String profileImg;
	private List<MainProduct> productImg;
	private String userid;
	private boolean follow;
}
