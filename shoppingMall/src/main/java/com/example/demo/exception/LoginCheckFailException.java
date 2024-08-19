package com.example.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginCheckFailException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	
	public LoginCheckFailException() {
        super("존재하지 않는 아이디 혹은 비밀번호가 다릅니다.");
    }
}
