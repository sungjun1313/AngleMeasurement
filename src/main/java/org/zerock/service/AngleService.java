package org.zerock.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.config.FileConfig;
import org.zerock.domain.AngleVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface AngleService {
	
	public static final String UPLOADPATH = FileConfig.PATH;	

	public List<AngleVO> getList();
	
	public List<AngleVO> getListWithPaging(Criteria cri, Long bno);
	
	public boolean angleValidation(AngleVO angleVO, Model model, String type);
	
	public int register(AngleVO angleVO);
	
	public HashMap<String, String> fileUpload(MultipartFile multipartFile, String existUuid);
	
	public File readFile(String fileName);
	
	public BoardVO readAngleWithBoard(String uuid);
	
	public int modify(AngleVO angleVO);
	
	public int modify(AngleVO angleVO, AngleVO newAngleVO);
	
	public void deleteFile(String uploadPath, String uuid, String fileName);
	
	public int remove(String uuid);
	
	public List<AngleVO> getListByBno(Long bno);
	
	public List<AngleVO> getOldFiles();
	
	public int getTotalCount(Long bno);
	
}
