package org.zerock.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AngleVO {

	//각도 측정 객체 -> 게시판 번호, 각도, 파일이름, 파일경로 
	private String uuid;
	private Long bno;
	private String angle;
	private String uploadPath;
	private String fileName;
	
	private MultipartFile angleFile;
}
