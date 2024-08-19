package com.example.demo.controller.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.handler.UserHandler;
import com.example.demo.service.follw.FollowService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FollowController {
	@Autowired
	private FollowService followService;

	@PostMapping("/follow")
	public String follow(@RequestParam("userID") String userID, HttpServletRequest request) {
		String followingID = UserHandler.loginSession(request).get().getUserid();
		followService.following(userID, followingID);

		return "redirect:person/mypage/" + userID;
	}

	@PostMapping("/unfollow")
	public String unfollow(@RequestParam("userID") String userID, HttpServletRequest request) {
		String followingID = UserHandler.loginSession(request).get().getUserid();
		followService.unfollow(userID, followingID);

		return "redirect:person/mypage/" + userID;
	}

	@PostMapping("/product/follow")
	public String productFollow(@RequestParam(name = "userID", defaultValue = "0") String userID,
			@RequestParam(name = "productId", defaultValue = "0") int productId, HttpServletRequest request) {
		String followingID = UserHandler.loginSession(request).get().getUserid();
		followService.following(userID, followingID);

		return "redirect:/product/product/" + productId;
	}

	@PostMapping("/product/unfollow")
	public String productUnfollow(@RequestParam(name = "userID", defaultValue = "0") String userID,
			@RequestParam(name = "productId", defaultValue = "0") int productId, HttpServletRequest request) {
		String followingID = UserHandler.loginSession(request).get().getUserid();
		followService.unfollow(userID, followingID);

		return "redirect:/product/product/" + productId;
	}

}