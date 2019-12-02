package org.zerock.domain;

import lombok.Data;

@Data
public class AuthVO {

	//권한에 대한 객체 -> 아이디, 권한
	private String userId;
	private String auth;
}
