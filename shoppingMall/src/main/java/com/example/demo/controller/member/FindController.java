package com.example.demo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/find")
public class FindController {
	
	@GetMapping("/find")
	public String find() {
		return "find/find"; 
	}
	
	@GetMapping("/findID")
	public String findID() {
		return "find/findID"; 
	}
	
	@GetMapping("/findPW")
	public String findPW() {
		return "find/findPW"; 
	}
}
