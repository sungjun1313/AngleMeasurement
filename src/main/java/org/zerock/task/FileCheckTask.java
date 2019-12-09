package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.service.AngleService;
import org.zerock.domain.AngleVO;
import org.zerock.service.AngleService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCheckTask {

	@Setter(onMethod_ = { @Autowired })
	private AngleService angleService;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}

	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles() throws Exception {
		log.warn("File Check Task Run...................");
		log.warn(""+ new Date());
		log.warn("======================================");
		
		//file list in database
		List <AngleVO> fileList = angleService.getOldFiles();
		
		//ready for check file in directory with database file list
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get(angleService.UPLOADPATH , vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		//image file has thumbnail file
		fileList.stream()
		.map(vo -> Paths.get(angleService.UPLOADPATH, vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
		.forEach(p -> fileListPaths.add(p));
		
		log.warn("======================================");
		
		fileListPaths.forEach(p -> log.warn(""+p));
		
		//files in yesterday directory
		
		File targetDir = Paths.get(angleService.UPLOADPATH, getFolderYesterDay()).toFile();
		File[] removeFiles = targetDir.listFiles(file-> fileListPaths.contains(file.toPath()) == false);
		
		if(removeFiles != null) {
			for(File file : removeFiles) {
				log.warn(file.getAbsolutePath());
				file.delete();
			}
		}
		
		
	}
}
