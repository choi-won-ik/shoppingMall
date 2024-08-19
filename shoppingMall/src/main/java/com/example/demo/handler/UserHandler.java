package com.example.demo.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.Entity.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class UserHandler {

	public static Optional<Member> me = Optional.empty();

	public static Optional<Member> loginSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			Object member = session.getAttribute("me");
			me = Optional.of((Member) member);
		}

		return me;
	}
}
