package org.zerock.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;
import org.zerock.config.RootConfig;
import org.zerock.config.SecurityConfig;
import org.zerock.domain.AuthVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j
public class MemberServiceTests {

	@Setter(onMethod_ = { @Autowired })
	private MemberService memberService;
	
	@Setter(onMethod_ = { @Autowired })
	BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		log.info("test");
	}
	
	/*
	@Test
	public void testRegister() {
		log.info("MemberServiceTests testRegister============================");
		MemberVO memberVO = new MemberVO();
		AuthVO authVO = new AuthVO();
		List<AuthVO> authList = new ArrayList<>();
		
		authVO.setAuth("ROLE_MEMBER");
		authList.add(authVO);
		
		memberVO.setUserId("user0");
		memberVO.setUserPw(passwordEncoder.encode("pw0"));
		memberVO.setUserName("성준");
		memberVO.setEmail("sj@naver.com");
		memberVO.setAuthList(authList);
		
		int result = memberService.register(memberVO);
		log.info("회원가입 결과: " + result);
	}
	*/
	
}
