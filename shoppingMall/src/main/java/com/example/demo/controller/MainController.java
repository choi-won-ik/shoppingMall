package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Member;
import com.example.demo.dto.MyInformation;
import com.example.demo.dto.ProductSearch;
import com.example.demo.dto.product.MainProduct;
import com.example.demo.handler.UserHandler;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.product.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

	@Autowired
	private ProductService productService;
	@Autowired
	private MemberService memberService;

	@GetMapping("/index")
	public String main(Model model, HttpServletRequest request) {
		List<MainProduct> list = productService.allProduct();

		model.addAttribute("products", list);

		return "index";
	}

	@PostMapping("/myname")
	@ResponseBody
	public MyInformation myname(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> op = Optional.of(authentication.getName());

		Optional<Member> me = UserHandler.me;
		// 로그인 되어 있을 때
		if (!me.isEmpty()) {
			MyInformation myInformation = MyInformation.builder().id(me.get().getId()).userid(me.get().getUserid())
					.name(me.get().getName()).build();

			return myInformation;
		}
		// 시큐리티에 로그인만 되어 있을 때(페이지 재 가동 시)
		else if (me.isEmpty() && !op.isEmpty()) {
			String userid = op.get();
			memberService.idSession(userid, request);

			return memberService.idSession(userid, request);
		}
		// 로그인 되어있지 않을 때
		else {
			return null;
		}
	}

	@PostMapping("/productSearch")
	@ResponseBody
	public List<ProductSearch> productSearch(@RequestParam("productName") String str) {
		List<ProductSearch> list = productService.productSearch(str);

		for (ProductSearch productSearch : list) {
			System.out.println(productSearch);
		}
		return list;
	}

//	@GetMapping("")
//	public String main1(Model model,HttpServletRequest request) {
//		return "index";
//	}
}