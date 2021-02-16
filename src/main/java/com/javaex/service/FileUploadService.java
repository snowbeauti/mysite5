package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;

@Service
public class FileUploadService {

	@Autowired
	private GalleryDao gdao;
	
	public String restore(MultipartFile file) {
		System.out.println("fileUpService.restore()");
		System.out.println(file.getOriginalFilename());
		
		//db 저장할 정보 수집
		String saveDir = "C:\\javaStudy\\upload";
		
		
		//오리지널 파일이름
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: " + orgName);
		
		//확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName: " + exName);
		
		//서버 저장 파일 이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("saveName" + saveName);
		
		//서버 파일 패스 --> 저장경로
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath: " + filePath);
		
		//파일 사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize: " + fileSize);
		
		//서버하드디스크 파일저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
			
		} catch(IOException e) {
			e.printStackTrace();
			
		}

		return saveName;
	}

	
	
}
