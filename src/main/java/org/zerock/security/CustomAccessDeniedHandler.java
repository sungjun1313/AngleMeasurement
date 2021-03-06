package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	//권한 거부 시 /accessError로 리다이렉트한다.
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessExeption) throws IOException, ServletException {
		log.error("Access Denied Handler");
		response.sendRedirect("/accessError");
	}
}
