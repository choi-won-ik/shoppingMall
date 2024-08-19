package com.example.demo.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.Entity.Member;
import com.example.demo.Repository.member.MemberRepository;
import com.example.demo.dto.MyProfile;
import com.example.demo.exception.LoginCheckFailException;
import com.example.demo.handler.UserHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepository memberRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member me = memberRepository.findByUserid(username).orElseThrow(() -> new LoginCheckFailException());

		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		HttpSession session = request.getSession();
		
		// 세션에 아이디 저장
		session.setAttribute("me", me);

		return User.builder().username(me.getUserid()).password(me.getPw()).build();
		
	}
}