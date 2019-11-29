package org.zerock.domain;

import lombok.Data;

@Data
public class AngleVO {

	private String uuid;
	private Long bno;
	private Long angle;
	private String uploadPath;
	private String fileName;
}
