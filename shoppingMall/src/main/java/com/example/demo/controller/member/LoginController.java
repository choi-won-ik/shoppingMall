package com.example.demo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/login")
@Log4j2
@RequiredArgsConstructor
public class LoginController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/login")
	public String login(@RequestParam(name = "hasMessage", defaultValue = "false") boolean hasMessage,
			@RequestParam(name = "message", defaultValue = "") String message, Model model,
			HttpServletRequest request) {
		model.addAttribute("hasMessage", hasMessage);
		model.addAttribute("message", message);

		memberService.backPage(request);
		
		return "login/login";
	}
}