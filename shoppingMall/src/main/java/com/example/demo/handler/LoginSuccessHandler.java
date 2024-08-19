package com.example.demo.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.example.demo.service.member.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		super.clearAuthenticationAttributes(request);


		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String url = savedRequest.getRedirectUrl();
			log.info(url);
			if (url == null || url.equals("")) {
				url = "/index";
			} else if (url.contains("/join")) {
				url = "/index";
			}
			requestCache.removeRequest(request, response);
			getRedirectStrategy().sendRedirect(request, response, url);
		}
		else if(savedRequest==null&&MemberService.backPage.equals("http://localhost:8080/login/login")){
			String url = "http://localhost:8080/index";
			requestCache.removeRequest(request, response);
			getRedirectStrategy().sendRedirect(request, response, url);
		}
		else {
			String url =MemberService.backPage;
			requestCache.removeRequest(request, response);
			getRedirectStrategy().sendRedirect(request, response, url);
		}

		super.onAuthenticationSuccess(request, response, authentication);
		
		UserHandler.loginSession(request);
		
		// 컨트롤러에 전달
		request.setAttribute("userid", authentication.getName());
	}

}
