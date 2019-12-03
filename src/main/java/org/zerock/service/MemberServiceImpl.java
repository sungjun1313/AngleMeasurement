package org.zerock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
import org.zerock.security.domain.CustomUser;
import org.zerock.mapper.AuthMapper;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

	@Setter(onMethod_ = { @Autowired })
	private MemberMapper memberMapper;
	
	@Setter(onMethod_ = { @Autowired })
	private AuthMapper authMapper;
	
	//이메일 정규식
	/*
	 허용 o
	 me@gmail.com me@me.co.uk me@hahoo.com me-100@me.com me.100@me.com me-100@me.net
	 me.100@me.com.au me@1.com me+100@gmail.com me-100@yahoo-test.com
	 허용x
	 me@.com.my me123@.com me123@.com.com me@%*.com me..2002@gmail.com me.@gmail.com
	 me@gmail.com.la
	*/
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	//회원가입 시 유저정보 객체와 권한정보 객체 모두 저장한다.
	@Transactional
	@Override
	public int register(MemberVO memberVO) {
		log.info("register: " + memberVO);
		//controller에서 비밀번호 암호화해서 가져와야 된다.
		//memberVO.setUserPw(passwordEncoder.encode(memberVO.getUserPw()));
		int result = memberMapper.register(memberVO);
		memberVO.getAuthList().forEach(auth -> {
			auth.setUserId(memberVO.getUserId());
			if(auth.getAuth() == null) {
				auth.setAuth("ROLE_MEMBER");
			}
			authMapper.register(auth);
		});
		return result;
	}
	
	//회원가입 시 올바른 정보인 지 체크한다.
	@Override
	public boolean registerValidation(MemberVO memberVO, Model model, String password2) {
		
		String userId = memberVO.getUserId();
		String userPw = memberVO.getUserPw();
		String userName = memberVO.getUserName();
		String email = memberVO.getEmail();
		List<String> userIdResult = new ArrayList<String>();
		List<String> userPwResult = new ArrayList<String>();
		List<String> userNameResult = new ArrayList<String>();
		List<String> emailResult = new ArrayList<String>();
		
		if(userId == null || userId.length() == 0) {
			log.warn("아이디 null");
			userIdResult.add("아이디는 필수항목입니다.");
		}else {
			if(memberMapper.isExistUserId(userId) > 0) {
				log.warn("아이디 중복");
				userIdResult.add("이미 존재하는 아이디입니다.");
			}
		}
		
		if(userPw == null || userPw.length() == 0 || password2 == null || password2.length() == 0) {
			log.warn("비밀번호 null");
			userPwResult.add("비밀번호는 필수항목입니다.");
		}else {
			if(!userPw.equals(password2)) {
				log.warn("비밀번호 안 맞음");
				userPwResult.add("비밀번호가 서로 일치하지 않습니다.");
			}
		}
		
		if(userName == null || userName.length() == 0) {
			log.warn("이름 null");
			userNameResult.add("이름은 필수항목입니다.");
		}
		
		if(email != null && email.length() != 0) {
			if(memberMapper.isExistEmail(email) > 0) {
				log.warn("이메일 중복");
				emailResult.add("이미 존재하는 이메일입니다.");
			}
			
			if(!isValidEmailAddress(email)) {
				log.warn("이메일 형식 안 맞음");
				emailResult.add("이메일 형식을 올바르게 적어주세요.");
			}
		}
		
		if(userIdResult.isEmpty() && userPwResult.isEmpty() && userNameResult.isEmpty() && emailResult.isEmpty()) {
			return true;
		}
		
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("userIdResult", userIdResult);
		map.put("userPwResult", userPwResult);
		map.put("userNameResult", userNameResult);
		map.put("emailResult", emailResult);
		
		HashMap<String, String> prevValue = new HashMap<String, String>();
		prevValue.put("userId", userId);
		prevValue.put("userName", userName);
		prevValue.put("email", email);
		
		model.addAttribute("validation", map);
		model.addAttribute("prevValue", prevValue);
		
		return false;
	}
	
	//회원정보 변경한다.
	@Override
	public int changeProfile(MemberVO memberVO) {
		return memberMapper.changeProfile(memberVO);
	}
	
	//회원정보 변경 시 올바른 정보인지 체크한다.
	@Override
	public boolean changeProfileValidation(MemberVO memberVO, Authentication auth, Model model) {
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		String userName = memberVO.getUserName();
		String email = memberVO.getEmail();
		List<String> userNameResult = new ArrayList<String>();
		List<String> emailResult = new ArrayList<String>();
		
		if(userName == null || userName.length() == 0) {
			log.warn("이름 null");
			userNameResult.add("이름은 필수항목입니다.");
		}
		
		if(email != null && email.length() != 0 && !email.equals(member.getEmail())) {
			if(memberMapper.isExistEmail(email) > 0) {
				log.warn("이메일 중복");
				emailResult.add("이미 존재하는 이메일입니다.");
			}
			
			if(!isValidEmailAddress(email)) {
				log.warn("이메일 형식 안 맞음");
				emailResult.add("이메일 형식을 올바르게 적어주세요.");
			}
		}
		
		if(userNameResult.isEmpty() && emailResult.isEmpty()) {
			return true;
		}
		
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("userNameResult", userNameResult);
		map.put("emailResult", emailResult);
		
		HashMap<String, String> prevValue = new HashMap<String, String>();
		prevValue.put("userName", userName);
		prevValue.put("email", email);
		
		model.addAttribute("validation", map);
		model.addAttribute("prevValue", prevValue);
		
		return false;
	}
	
	//회원 비밀번호를 변경한다.
	@Override
	public int changePassword(MemberVO memberVO) {
		return memberMapper.changePassword(memberVO);
	}
	
	//회원 비밀번호 변경 시 올바른 정보인지 체크한다.
	@Override
	public boolean changePasswordValidation(Authentication auth, Model model, String newPassword1, String newPassword2, String oldPassword, BCryptPasswordEncoder passwordEncoder) {
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		
		List<String> oldUserPwResult = new ArrayList<String>();
		List<String> newUserPwResult = new ArrayList<String>();
		
		if(oldPassword == null || oldPassword.length() == 0) {
			log.warn("oldPassword null");
			oldUserPwResult.add("기존 비밀번호는 필수항목입니다.");
		}
		
		if(newPassword1 == null || newPassword1.length() == 0 || newPassword2 == null || newPassword2.length() == 0) {
			log.warn("newPassword null");
			newUserPwResult.add("새 비밀번호와 새 비밀번호 확인 모두 기입해주세요.");
		}
		
		if(!passwordEncoder.matches(oldPassword, member.getUserPw())) {
			log.warn("인증 실패");
			oldUserPwResult.add("비밀번호 인증에 실패하였습니다.");
		}
		
		if(!newPassword1.equals(newPassword2)) {
			log.warn("비밀번호 불일치");
			newUserPwResult.add("비밀번호가 일치하지 않습니다.");
		}
		
		if(oldUserPwResult.isEmpty() && newUserPwResult.isEmpty()) {
			return true;
		}
		
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("oldUserPwResult", oldUserPwResult);
		map.put("newUserPwResult", newUserPwResult);
		
		model.addAttribute("validation", map);
		
		return false;
	}
}
