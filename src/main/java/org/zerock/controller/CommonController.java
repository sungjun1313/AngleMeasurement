package org.zerock.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.MemberVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CommonController {
	
	@Setter(onMethod_ = { @Autowired })
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/test")
	public String test(@RequestParam("age") int age) {
		log.info("age:" + age);
		return "account/login";
	}
	
	@GetMapping(value="/testRest", produces="text/plain; charset=UTF-8")
	@ResponseBody
	public String testRest() {
		return "안녕하세요";
	}
	
	@GetMapping("/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("access Denied: " + auth);
		model.addAttribute("msg", "Access Denied");
		return "errors/access_denied";
	}
	
	@GetMapping("/customLogin")
	public String loginInput(String error, String logout, HttpServletRequest request ,Model model, Authentication auth) {
		if(auth != null) {
			return "redirect:/customProfile";
		}
		
		if(error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		
		if(logout != null) {
			model.addAttribute("logout", "Logout Complete");
		}
		
		if(error == null && logout == null) {
			log.info("error: " + error +" , logout: " +logout );
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("prevPage", referer);
		}
			
		
		return "account/login";
	}
	
	@GetMapping("/customLogout")
	public String logoutGet(Authentication auth) {
		log.info("custom logout");
		if(auth == null) {
			return "redirect:/board/list";
		}
		return "account/logout";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post custom logout");
	}
	
	@PostMapping("/register")
	public String postRegister(MemberVO memverVO) {
		String encPassword = passwordEncoder.encode(memverVO.getUserPw());//비밀번호 암호화
		memverVO.setUserPw(encPassword);
		//memberService.register(memberVO);
		return null;
	}
}
