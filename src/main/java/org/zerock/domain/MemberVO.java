package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

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
