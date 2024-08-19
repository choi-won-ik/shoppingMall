package com.example.demo.service.member;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Repository.member.MemberRepository;
import com.example.demo.dto.MyInformation;
import com.example.demo.handler.UserHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	// 로그인 후 이전 페이지로 돌아기기 위함
	public static String backPage = null;
	// 내 아이디 저장
	public static String myname = null;

	// 아이디 중복 확인
	public Optional<Member> overlap(String userid) {
		return memberRepository.findByUserid(userid);
	}

	// 이전 페이지 저장
	public void backPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("previousPageUrl", request.getHeader("Referer"));
		backPage = (String) session.getAttribute("previousPageUrl");
	}

	// 새션에 내 아이디 저장
	public MyInformation idSession(String str, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member me = memberRepository.findByUserid(str).get();
		session.setAttribute("me", me);

		MyInformation myInformation = MyInformation.builder().id(me.getId()).userid(me.getUserid()).name(me.getName())
				.build();
		return myInformation;
	}

	// 내 소개글 저장
	@Transactional
	public Member introduction(String introduction, String meID) {
		Member member = memberRepository.findByUserid(meID).get();
		log.info(member);
		member.setIntroduction(introduction);
		return memberRepository.save(member);
	}

	// 내 소개글 찾기
	public Member introductionFind(String meID) {
		Optional<Member> mem = memberRepository.findByUserid(meID);
		Member member = mem.get();

		return member;
	}

	@Transactional
	public Member uploadProfile(byte[] img, String userID) {
		Member member = memberRepository.findByUserid(userID).get();

		member.setProfile(img);
		return memberRepository.save(member);
	}

	public StringBuilder userProfile(byte[] profile) {
		StringBuilder imageHtml = new StringBuilder();

		if (profile != null) {
			String base64Image = "";
			try {
				String encodeToBase64 = Base64.getEncoder().encodeToString(profile);
				base64Image = "data:image/png;base64," + encodeToBase64;

			} catch (IllegalArgumentException e) {
				log.error("Error encoding profile image: " + e.getMessage());
			}

			return imageHtml.append("<img src=\"").append(base64Image).append("\" alt='Profile Image' />");
		} else {
			return imageHtml.append("<img src='/img/user.png' alt='Profile Image'>");
		}
	}

}
