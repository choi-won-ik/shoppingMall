package com.example.demo.controller.person;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Member;
import com.example.demo.dto.product.ProductList;
import com.example.demo.handler.UserHandler;
import com.example.demo.service.follw.FollowService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.product.LikeyService;
import com.example.demo.service.product.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private FollowService followService;
	@Autowired
	private ProductService productService;
	@Autowired
	private LikeyService likeyService;

	@GetMapping("/mypage/{userID}")
	public String mypage(@PathVariable("userID") String userID, Model model, HttpServletRequest request) {
		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> loginID = Optional.of(authentication.getName());

		// 페이지 주인
		model.addAttribute("userID", userID);
		// 페이지 주인 정보
		Member member = memberService.introductionFind(userID);

		model.addAttribute("owner", member.getName());
		model.addAttribute("time", member.getTime());

		byte[] imgFile = member.getProfile();

		model.addAttribute("profile", memberService.userProfile(imgFile));

		// 상품종류와 갯수
		model.addAttribute("productNUM", productService.productNUM(member.getId()));
		model.addAttribute("productList", productService.productList(member.getId()));

		// 팔로우 수
		model.addAttribute("followerNUM", followService.followNUM(member));
		model.addAttribute("followers", followService.followers(member,loginID));

		// 팔로잉 수
		model.addAttribute("followingNUM", followService.followingNUM(member));
		model.addAttribute("followings", followService.followings(member,loginID));
		
		
		
		// 좋아요 누른 상품
		List<ProductList> likeyList = likeyService.userLikeyList(member);
		model.addAttribute("likeyList",likeyList);
		model.addAttribute("likeyListNUM",likeyList.size());
		

		// 상대 페이지
		if (!loginID.equals(Optional.of(userID))) {

			Boolean follow = false;

			
			
			// 로그인 되어있을 경우
			if (!loginID.equals(Optional.of("anonymousUser"))) {
				model.addAttribute("meID", loginID.get());
				
				follow = followService.isFollowing(member.getId(), loginID.get());
				model.addAttribute("loginID",loginID.get());
			}

			
			
			
			model.addAttribute("follow", follow ? "true" : "false");
			model.addAttribute("introductionFind", member.getIntroduction());

			return "person/userpage";
		}

		// 내 페이지
		else {
			model.addAttribute("introductionFind", member.getIntroduction());
			model.addAttribute("meID", userID);

			return "person/mypage";
		}
	}

	@PostMapping("/introduction")
	@ResponseBody
	public Member introduction(@RequestParam("introduction") String introduction, @RequestParam("meID") String meID) {

		return memberService.introduction(introduction, meID);
	}

	@PostMapping("/uploadProfile")
	public ResponseEntity<String> uploadProfile(@RequestParam("file") MultipartFile file,
			@RequestParam("userID") String userID) throws IOException {
		byte[] img = file.getBytes();
		try {
			memberService.uploadProfile(img, userID);

			return ResponseEntity.ok("profile update success");
		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}