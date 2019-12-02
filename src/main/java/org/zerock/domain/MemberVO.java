package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	//유저 정보 -> 아이디, 비밀번호, 이름, 이메일, 사용가능여부, 등록일, 수정일, 마지막 로그인, 권한 객체 리스트
	private String userId;
	private String userPw;
	private String userName;
	private String email;
	private boolean enabled;
	
	private Date regDate;
	private Date updateDate;
	private Date lastLogin;
	private List<AuthVO> authList;
}
