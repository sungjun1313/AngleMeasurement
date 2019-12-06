package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AngleVO;
import org.zerock.domain.Criteria;

public interface AngleService {
	
	public static final String UPLOADPATH = "C:\\Users\\JLPartner\\Desktop\\spring3\\upload\\tmp\\";	

	public List<AngleVO> getList();
	
	public List<AngleVO> getListWithPaging(Criteria cri, Long bno);
	
	public boolean angleValidation(AngleVO angleVO, Model model, String type);
	
	public int register(AngleVO angleVO);
	
	public HashMap<String, String> fileUpload(MultipartFile multipartFile);
}
