package org.zerock.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.MemberVO;
import org.zerock.service.MemberService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CommonController {
	
	@Setter(onMethod_ = { @Autowired })
	private BCryptPasswordEncoder passwordEncoder;
	
	@Setter(onMethod_ = { @Autowired })
	private MemberService memberService;

	//  /test?age=1  테스트용
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/test")
	public String test(@RequestParam("age") int age) {
		log.info("age:" + age);
		return "account/login";
	}
	/*
	@GetMapping(value="/testRest", produces="text/plain; charset=UTF-8")
	@ResponseBody
	public String testRest() {
		return "안녕하세요";
	}
	*/
	
	//  /accessError 권한 거부 페이지
	@GetMapping("/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("access Denied: " + auth);
		model.addAttribute("exception", "Access Denied");
		return "errors/access_denied";
	}
	
	
	//  /customLogin 로그인페이지
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
	
	//  /customLogout 로그아웃페이지
	@GetMapping("/customLogout")
	public String logoutGet(Authentication auth) {
		log.info("custom logout");
		if(auth == null) {
			return "redirect:/board/list";
		}
		return "account/logout";
	}
	
	//  /customLogout 로그아웃 post요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post custom logout");
	}
	
	//  /register 회원가입페이지
	@GetMapping("/register")
	public String getRegister(Authentication auth) {
		if(auth != null) {
			return "redirect:/customProfile";
		}
		return "account/register";
	}
	
	//  /register 회원가입 post 요청 처리
	@PostMapping("/register")
	public String postRegister(MemberVO memberVO, @RequestParam("userPw2") String userPw2, RedirectAttributes rttr) {
		log.info("memverVO: " + memberVO);
		log.info("userPw2: " + userPw2);
		String prevPassword = memberVO.getUserPw();
		String encPassword = passwordEncoder.encode(prevPassword);//비밀번호 암호화
		memberVO.setUserPw(encPassword);
		HashMap<String, List<String>> map = memberService.registerValidation(memberVO, prevPassword, userPw2);
		if(map.get("userIdResult").isEmpty() && map.get("userPwResult").isEmpty() && map.get("userNameResult").isEmpty() && map.get("emailResult").isEmpty()) {
			int result = memberService.register(memberVO);
			if(result == 1) {
				log.info("회원가입 성공");
				rttr.addFlashAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
				return "redirect:/customLogin";
			}
			log.info("회원가입 서버 에러");
			rttr.addFlashAttribute("message", "서버 에러로 회원가입에 실패하였습니다.");
			return "redirect:/register";
		}
		log.info("회원가입 validation 실패");
		log.info(map.toString());
		HashMap<String, String> prevValue = new HashMap<String, String>();
		prevValue.put("userId", memberVO.getUserId());
		prevValue.put("userName", memberVO.getUserName());
		prevValue.put("email", memberVO.getEmail());
		rttr.addFlashAttribute("validation", map);
		rttr.addFlashAttribute("prevValue", prevValue);
		return "redirect:/register";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/customProfile")
	public String getProfile() {
		log.info("custom profile");
		return "account/profile";
	}
	
}
