package com.example.demo.controller.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Member;
import com.example.demo.service.join.JoinService;
import com.example.demo.service.member.MemberService;

@Controller
@RequestMapping("/join")
public class JoinController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private JoinService joinService;
	
	@GetMapping("/joinPage1")
	public String join1() {
		return "/join/joinPage1";
	}
	
	@GetMapping("/joinPage2")
	public String join2() {
		return "join/joinPage2";
	}
	
	// 아이디 중복 확인
	@PostMapping("/overlap")
	@ResponseBody
	public boolean overlap(@RequestParam("userid") String userid) {
		boolean result = false;
		if(memberService.overlap(userid).isEmpty()) {
			System.out.println("없음");
		}else {
			result = true;
		}
		return result;
	}
	
	@GetMapping("/joinPage3")
	public String join3() {
		return "join/joinPage3";
	}
	
	// 회원가입
	@PostMapping("/join-success")
	public String join3(@RequestParam(name="name",defaultValue = "0") String name
			,@RequestParam(name="userid",defaultValue = "0") String userid
			,@RequestParam(name="pw",defaultValue = "0") String pw
			,@RequestParam(name="phone",defaultValue = "0") int phone
			,@RequestParam(name="birthday",defaultValue = "0") int birthday) {
		try {
			joinService.join(name,userid,pw,phone,birthday);
			
			return "redirect:/join/joinPage3";
		} catch (Exception e) {
			return "redirect:/join/joinPage2";
		}		
	}
}
