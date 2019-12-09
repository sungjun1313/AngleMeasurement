package org.zerock.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.AngleVO;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.AngleMapper;
import org.zerock.mapper.BoardMapper;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Service
public class AngleServiceImpl implements AngleService {

	@Setter(onMethod_ = { @Autowired })
	private AngleMapper angleMapper;
	
	@Setter(onMethod_ = { @Autowired })
	private BoardMapper boardMapper;
	
	private boolean checkImageType(File file) {

		try {
			
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}
	
	//각도 측정 객체를 모두 가져온다.
	@Override
	public List<AngleVO> getList(){
		return angleMapper.getList();
	}
	
	//게시판과 연결된 각도 측정 객체를 페이징해서 가져온다.
	@Override
	public List<AngleVO> getListWithPaging(Criteria cri, Long bno){
		log.warn(""+cri);
		return angleMapper.getListWithPaging(cri, bno);
	}
	
	//각도 측정 객체에 올바른 값이 들어가는 지 체크한다.
	@Override
	public boolean angleValidation(AngleVO angleVO, Model model, String type) {
		String uuid = angleVO.getUuid();
		Long bno = angleVO.getBno();
		String angle = angleVO.getAngle();
		String uploadPath = angleVO.getUploadPath();
		String fileName = angleVO.getFileName();
		List<String> uuidResult = new ArrayList<String>();
		List<String> bnoResult = new ArrayList<String>();
		List<String> angleResult = new ArrayList<String>();
		List<String> uploadPathResult = new ArrayList<String>();
		List<String> fileNameResult = new ArrayList<String>();
		
		if(uuid == null || uuid.length() == 0) {
			log.warn("uuid null");
			uuidResult.add("uuid 발급에 실패하였습니다.");
		}else {
			if(uuid.length() > 100) {
				log.warn("uuid length");
				uuidResult.add("잘못된 uuid를 발급하였습니다.");
			}
		}
		
		if(bno == null) {
			log.warn("bno null");
			bnoResult.add("해당 게시글을 다시 확인해주세요.");
		}else {
			if(boardMapper.isExistBoard(bno) != 1) {
				bnoResult.add("게시글이 존재하지 않습니다.");
			}
		}
		
		if(angle == null) {
			log.warn("angle null");
		}
		
		if(uploadPath == null || uploadPath.length() == 0) {
			log.warn("uploadPath null");
			uploadPathResult.add("파일 경로가 설정되지 않았습니다.");
		}else {
			if(uploadPath.length() > 200) {
				log.warn("uploadPath length");
				uploadPathResult.add("파일 경로는 200 글자 아래로 설정해주세요.");
			}
		}
		
		if(fileName == null || fileName.length() == 0) {
			log.warn("fileName null");
			fileNameResult.add("파일이름은 필수항목입니다.");
		}else {
			if(fileName.length() > 100) {
				log.warn("fileName length");
				fileNameResult.add("파일 이름은 100 글자 이내로 해주세요.");
			}
		}
		
		if(uuidResult.isEmpty() && bnoResult.isEmpty() && angleResult.isEmpty() && uploadPathResult.isEmpty() && fileNameResult.isEmpty()) {
			return true;
		}
		
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("uuidResult", uuidResult);
		map.put("bnoResult", bnoResult);
		map.put("angleResult", angleResult);
		map.put("uploadPathResult", uploadPathResult);
		map.put("fileNameResult", fileNameResult);
		
		HashMap<String, String> prevValue = new HashMap<String, String>();
		prevValue.put("bno", Long.toString(bno));
		prevValue.put("uuid", uuid);
		prevValue.put("uploadPath", uploadPath);
		prevValue.put("fileName", fileName);
		
		model.addAttribute("validation", map);
		model.addAttribute("prevValue", prevValue);
		
		return false;
	}
	
	//각도 측정 개체를 추가한다
	@Override
	public int register(AngleVO angleVO) {
		return angleMapper.insert(angleVO);
	}
	
	//파일은 전송한다.
	@Override
	public HashMap<String, String> fileUpload(MultipartFile multipartFile, String existUuid){
		HashMap<String, String> map = new HashMap();
		if(multipartFile.isEmpty()) {
			map.put("success", "fail");
			return map;
		}
		String uploadFolderPath = getFolder();
		File uploadPath = new File(AngleService.UPLOADPATH, uploadFolderPath);
		
		// make yyyy/MM/dd folder
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		String uploadFileName = multipartFile.getOriginalFilename();
		// IE has file path
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
		log.warn("only file name: " + uploadFileName);
		map.put("fileName", uploadFileName);
		
		String uuid = existUuid == null ? UUID.randomUUID().toString() : existUuid;

		uploadFileName = uuid + "_" + uploadFileName;
		
		try {
			File saveFile = new File(uploadPath, uploadFileName);
			if(checkImageType(saveFile)) {
				multipartFile.transferTo(saveFile);
				map.put("uuid", uuid);
				map.put("uploadPath", uploadFolderPath);
				
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

				Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

				thumbnail.close();
				
				map.put("success", "success");
			}else {
				map.put("success", "fail");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", "fail");
		}
		
		return map;
	}
	
	//파일 객체를 가져온다.
	@Override
	public File readFile(String fileName) {
		return new File(AngleService.UPLOADPATH + fileName);
	}
	
	//게시판과 해당 각도 측정 객체를 가져온다.
	@Override
	public BoardVO readAngleWithBoard(String uuid) {
		return angleMapper.readAngleWithBoard(uuid);
	}
	
	//각도 측정 객체를 수정한다,
	@Override
	public int modify(AngleVO angleVO) {
		return angleMapper.modify(angleVO);
	}
	
	//각도 측정 객체를 수정한다.
	@Transactional
	@Override
	public int modify(AngleVO angleVO, AngleVO newAngleVO) {
		angleMapper.remove(angleVO.getUuid());
		int result = angleMapper.insert(newAngleVO);
		return result;
	}
	
	//파일을 삭제한다.
	@Override
	public void deleteFile(String uploadPath, String uuid, String fileName) {
		File file = new File(AngleService.UPLOADPATH + uploadPath + "//s_" + uuid + "_" + fileName);
		file.delete();
		String largeFile = file.getAbsolutePath().replace("s_", "");
		file = new File(largeFile);
		file.delete();
	}
	
	//각도 측정 객체를 삭제한다.
	@Override
	public int remove(String uuid) {
		return angleMapper.remove(uuid);
	}
	
	//게시판 관련 각도 측정 객체를 모두 가져온다.
	@Override
	public List<AngleVO> getListByBno(Long bno){
		return angleMapper.getListByBno(bno);
	}
	
	//전날 각도 측정 객체를 가져온다.
	@Override
	public List<AngleVO> getOldFiles(){
		return angleMapper.getOldFiles();
	}
}
