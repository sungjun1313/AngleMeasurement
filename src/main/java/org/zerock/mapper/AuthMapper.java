package org.zerock.mapper;

import org.zerock.domain.AuthVO;

public interface AuthMapper {

	//회원가입할 때 권한 등록
	public int register(AuthVO authVO);
}
