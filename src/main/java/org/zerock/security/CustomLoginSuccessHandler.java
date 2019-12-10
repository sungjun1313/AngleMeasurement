package org.zerock.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	//prevPage 없을 때 리다이렉트할 default url
	public CustomLoginSuccessHandler() {
		setDefaultTargetUrl("/angle/list");
	}
	
	//prevPage를 체크해서 prevPage가 존재하면 prevPage로 리다이렉트, 없으면 default url로 리다이렉트
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		log.warn("Login success");
		
		HttpSession session = request.getSession();
		if(session != null) {
			String redirectUrl = (String) session.getAttribute("prevPage");
			if(redirectUrl != null) {
				log.warn(redirectUrl);
				session.removeAttribute("prevPage");
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);
			}
		}
		
		log.warn("defaultPage");
		super.onAuthenticationSuccess(request, response, auth);
	}
}
