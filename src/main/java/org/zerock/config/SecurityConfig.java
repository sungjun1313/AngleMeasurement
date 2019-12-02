package org.zerock.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.security.CustomUserDetailsService;
import org.zerock.security.CustomLoginSuccessHandler;
import org.zerock.security.CustomAccessDeniedHandler;
import org.zerock.security.CustomHttp403ForbiddenEntryPoint;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Setter(onMethod_ = { @Autowired })
	private DataSource dataSource;
	
	//로그인 성공 시 리다이렉트를 정하는 핸들러 클래스 리턴
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	//패스워드 암호화 클래스 리턴
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//유저정보 가져오는 서비스 리턴
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}
	
	//rememberMe에서 사용되는 저장소 리턴
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	//권한 거부되었을 때 리다이렉트를 정하는 클래스 리턴
	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	/*
	@Bean
	public AuthenticationEntryPoint customHttp403ForbiddenEntryPoint() {
		return new CustomHttp403ForbiddenEntryPoint();
	}
	*/

	//세팅 시작
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//xml에서 url-intercept 역할
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/test").access("isAuthenticated()");
		
		//loginPage는 login url, loginProcessingUrl는  login 처리할  url
		http.formLogin()
			.loginPage("/customLogin")
			.loginProcessingUrl("/login")
			.successHandler(loginSuccessHandler());
		
		//로그인 성공 시 다음에 자동 로그인
		http.rememberMe()
			.key("zerock")
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(604800);
		
		//로그아웃 세션 쿠기 삭제
		http.logout()
			.logoutUrl("/customLogout")
			.invalidateHttpSession(true)
			.deleteCookies("remember-me", "JESSSION_ID");
		
		//권한 거부 되었을 때 액션
		http.exceptionHandling()
			.accessDeniedHandler(this.customAccessDeniedHandler());
		
	}
	
	//유저정보를 가져올 서비스와 패스워드 암호화 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService())
		.passwordEncoder(passwordEncoder());
	}
}
