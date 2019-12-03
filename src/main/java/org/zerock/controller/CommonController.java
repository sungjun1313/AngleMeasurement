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
import org.zerock.security.domain.CustomUser;
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
	public String postRegister(MemberVO memberVO, @RequestParam("userPw2") String userPw2, Model model, RedirectAttributes rttr) {
		log.info("memverVO: " + memberVO);
		log.info("userPw2: " + userPw2);
		
		if(memberService.registerValidation(memberVO, model, userPw2)) {
			String encPassword = passwordEncoder.encode(memberVO.getUserPw());//비밀번호 암호화
			memberVO.setUserPw(encPassword);
			int result = memberService.register(memberVO);
			if(result == 1) {
				log.info("회원가입 성공");
				rttr.addFlashAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
				return "redirect:/customLogin";
			}
			log.info("회원가입 서버 에러");
			model.addAttribute("message", "서버 에러로 회원가입에 실패하였습니다.");
		}
		return "account/register";
	}
	
	//  /customProfile 프로필 상세 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/customProfile")
	public String getProfile() {
		log.info("custom profile");
		//CustomUser userDetails = (CustomUser)auth.getPrincipal();
		//MemberVO memberVO = userDetails.getMember();
		return "account/profile";
	}
	
	//  /changeProfile 프로필 변경 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/changeProfile")
	public String getChangeProfile() {
		log.info("profile change");
		return "account/changeProfile";
	}
	
	//  /changeProfile 프로필 변경 post 요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/changeProfile")
	public String postChangeProfile(MemberVO memberVO, Authentication auth, Model model, RedirectAttributes rttr) {
		
		if(memberService.changeProfileValidation(memberVO, auth, model)) {
			CustomUser user = (CustomUser)auth.getPrincipal();
			MemberVO member = user.getMember();
			memberVO.setUserId(member.getUserId());
			int result = memberService.changeProfile(memberVO);
			if(result == 1) {
				log.info("회원정보 변경 성공");
				//DB 변경 성공 시 현재 member객체도 수정
				member.setUserName(memberVO.getUserName());
				member.setEmail(memberVO.getEmail());
				member.setUpdateDate(memberVO.getUpdateDate());
				rttr.addFlashAttribute("message", "회원정보 변경이 성공적으로 완료되었습니다.");
				return "redirect:/customProfile";
			}
			log.info("회원 정보 변경 서버에러");
			model.addAttribute("message", "서버 에러로 회원 정보 변경에 실패하였습니다.");
		}
		return "account/changeProfile";
	}
	
	//  /changePassword  비밀번호 변경 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/changePassword")
	public String getChangePassword() {
		log.info("password change");
		return "account/changePassword";
	}
	
	//  /changePassword 비밀번호 변경 post 요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/changePassword")
	public String postChangePassword(@RequestParam("newPassword1") String newPassword1, @RequestParam("newPassword2") String newPassword2, 
			@RequestParam("oldPassword") String oldPassword, Model model, Authentication auth, RedirectAttributes rttr) {
		if(memberService.changePasswordValidation(auth, model, newPassword1, newPassword2, oldPassword, passwordEncoder)) {
			CustomUser user = (CustomUser)auth.getPrincipal();
			MemberVO member = user.getMember();
			MemberVO memberVO = new MemberVO();
			memberVO.setUserId(member.getUserId());
			String encPassword = passwordEncoder.encode(newPassword1);//비밀번호 암호화
			memberVO.setUserPw(encPassword);
			
			int result = memberService.changePassword(memberVO);
			if(result == 1) {
				log.info("비밀번호 변경 성공");
				//DB 변경 성공 시 member 객체도 변경
				member.setUserPw(encPassword);
				member.setUpdateDate(memberVO.getUpdateDate());
				rttr.addFlashAttribute("message", "비밀번호 변경이 성공적으로 완료되었습니다.");
				return "redirect:/customProfile";
			}
			log.info("비밀번호 변경 서버에러");
			model.addAttribute("message", "서버 에러로 비밀번호 변경에 실패하였습니다.");
		}
		return "account/changePassword";
	}
	
}
