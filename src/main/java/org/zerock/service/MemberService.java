package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.MemberVO;

public interface MemberService {

	public int register(MemberVO memberVO);
	
	public HashMap<String, List<String>> registerValidation(MemberVO memberVO, String password1 ,String password2);
}
