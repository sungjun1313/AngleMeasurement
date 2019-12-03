package org.zerock.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.zerock.domain.MemberVO;

public interface MemberService {

	public int register(MemberVO memberVO);
	
	public boolean registerValidation(MemberVO memberVO, Model model, String password2);
	
	public int changeProfile(MemberVO memberVO);
	
	public boolean changeProfileValidation(MemberVO memberVO, Authentication auth, Model model);
	
	public int changePassword(MemberVO memberVO);
	
	public boolean changePasswordValidation(Authentication auth, Model model, String newPassword1, String newPassword2, String oldPassword, BCryptPasswordEncoder passwordEncoder);
}
