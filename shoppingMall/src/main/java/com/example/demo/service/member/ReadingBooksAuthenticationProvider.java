package com.example.demo.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.LoginCheckFailException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadingBooksAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userid = authentication.getName();
        UserDetails memberDetails = myUserDetailsService.loadUserByUsername(userid);
		
        String rawPassword = authentication.getCredentials().toString();
        String hashPassword = memberDetails.getPassword();
        
        checkPassword(rawPassword, hashPassword);

        return new UsernamePasswordAuthenticationToken(userid, rawPassword, memberDetails.getAuthorities());
	}
	
    private void checkPassword(String rawPassword, String hashPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(rawPassword, hashPassword);
        if(isPasswordMatch == false){
            throw new LoginCheckFailException();
        }
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}