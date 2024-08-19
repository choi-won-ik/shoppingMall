package com.example.demo.service.join;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Repository.member.MemberRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JoinService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberRepository memberRepository;
	
	public ResponseEntity<String> join(String name, String userid, String pw, long phone, long birthday) {
		String password = passwordEncoder.encode(pw);
		LocalDateTime jointime = LocalDateTime.now();
		// 날짜를 년/월/일로 변경
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		
		Member member = Member.builder()
				.userid(userid)
				.pw(password)
				.name(name)
				.phone(phone)
				.birthday(birthday)
				.time(jointime.format(formatter))
				.introduction("안녕하세요.")
				.build();
		try {
			memberRepository.save(member);
			
			return ResponseEntity.ok("join success");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
