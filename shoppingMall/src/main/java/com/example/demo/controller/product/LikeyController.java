package com.example.demo.controller.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.product.LikeyService;
import com.example.demo.service.product.ProductService;

@Controller
public class LikeyController {

	@Autowired
	private LikeyService likeyService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ProductService productService; 

	@PostMapping("/likey")
	@ResponseBody
	public boolean likey(@RequestParam("productCode") int productCode) {
		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userid = authentication.getName();
		
		Optional<Member> member= memberService.overlap(userid);
		Optional<Product> product=productService.findByProductNo(productCode);
		
		boolean result = likeyService.add(product, member);
		System.out.println("====================");
		System.out.println("====================");
		System.out.println(result);
		System.out.println("====================");
		System.out.println("====================");
		return result;
	}
	
	@PostMapping("/person/likey")
	public String personLikey(@RequestParam("productCode") int productCode,@RequestParam("userid") String userid) {
		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginid = authentication.getName();
		
		Optional<Member> member= memberService.overlap(loginid);
		Optional<Product> product=productService.findByProductNo(productCode);
		likeyService.add(product, member);
		return "redirect:/person/mypage/" + userid;
	}
}
