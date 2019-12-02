package org.zerock.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

	//모든 Exception을 처리
	//권한 관련 에러일 경우 -> /errors/access_denied.jsp
	//나머지 에러일 경우 -> /errors/error_page.jsp
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception: " + ex.getMessage());
		model.addAttribute("exception", ex);
		if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            return "/errors/access_denied";
        }
		return "/errors/error_page";
	}
	
	//404 에러 처리
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "/errors/custom404";
	}
}
