package org.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//aop를 추가하려면 org.zerock.aop 패키지와 안에 클래스 생성 뒤 아래 코드를 어노테이션으로 추가
//@ComponentScan(basePackages = "org.zerock.aop")
//task를 추가하려면 org.zerock.task 패키지와 안에 클래스 생성 뒤 아래 코드를 어노테이션으로 추가
//@ComponentScan(basePackages = "org.zerock.task")


@Configuration
@ComponentScan(basePackages = {"org.zerock.service"})
@ComponentScan(basePackages = "org.zerock.task")
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = {"org.zerock.mapper"})
public class RootConfig {

	//DB 연결 설정
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl");
		hikariConfig.setUsername("angle_ex");
		hikariConfig.setPassword("angle_ex");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}
	
	//SqlSessionFactory 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	
	//transaction 설정
	@Bean
	public DataSourceTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
