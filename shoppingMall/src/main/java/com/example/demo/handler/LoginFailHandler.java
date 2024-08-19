package com.example.demo.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        
		String message = getMessage(exception);
        String redirectUrl = "/login/login?hasMessage=true&message=" + message;
        setDefaultFailureUrl(redirectUrl);
        super.onAuthenticationFailure(request, response, exception);
	}
	
	private static String getMessage(AuthenticationException exception) throws UnsupportedEncodingException {
        String message = exception.getMessage();
        String encodeMessage = URLEncoder.encode(message, "UTF-8");
		log.info("실패 핸들러 가동");
        log.info("실패 핸들러 가동");
        log.info("실패 핸들러 가동");
        log.info("실패 핸들러 가동");
        log.info("실패 핸들러 가동");
        return encodeMessage;
    }

}
