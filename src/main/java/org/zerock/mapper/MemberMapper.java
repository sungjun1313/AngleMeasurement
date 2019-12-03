package org.zerock.mapper;

import org.zerock.domain.MemberVO;

public interface MemberMapper {

	//userId에 해당하는 유저 객체를 가져온다.
	public MemberVO read(String userId);
	
	//회원가입할 때 회원정보를 등록한다.
	public int register(MemberVO memberVO);
	
	//아이디가 존재하는 지 확인한다
	public int isExistUserId(String userId);
	
	//이메일이 존재하는 지 확인한다.
	public int isExistEmail(String email);
	
	//유저정보를 변경한다.
	public int changeProfile(MemberVO memberVO);
	
	//비밀번호를 변경한다.
	public int changePassword(MemberVO memberVO);
}
