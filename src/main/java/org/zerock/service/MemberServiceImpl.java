package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
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
	
	
	@Transactional
	@Override
	public int register(MemberVO memberVO) {
		log.info("register: " + memberVO);
		//controller에서 비밀번호 암호화해서 가져와야 된다.
		//memberVO.setUserPw(passwordEncoder.encode(memberVO.getUserPw()));
		int result = memberMapper.register(memberVO);
		memberVO.getAuthList().forEach(auth -> {
			auth.setUserId(memberVO.getUserId());
			authMapper.register(auth);
		});
		return result;
	}
}
