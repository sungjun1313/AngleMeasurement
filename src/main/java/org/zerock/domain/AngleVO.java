package org.zerock.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AngleVO {

	//각도 측정 객체 -> 이름 중복 방지용 uuid, 게시판 번호, 각도, 파일경로, 파일이름, request로 받을 파일
	private String uuid;
	private Long bno;
	private String angle;
	private String uploadPath;
	private String fileName;
	
	private MultipartFile angleFile;
}
