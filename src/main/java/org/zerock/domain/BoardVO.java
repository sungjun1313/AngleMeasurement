package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	//게시판 객체  -> 번호, 제목, 내용, 작성자, 작성일, 수정일, 각도측청 개체 리스트
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	
	private List<AngleVO> angleList;
}
