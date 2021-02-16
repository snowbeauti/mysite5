package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao galdao;
	
	//리스트 불러오기
	public List<GalleryVo> galleryList(){
		System.out.println("galservice galleryList()");
		
		return galdao.galleryList();
	}
		
	//이미지 업로드
	public GalleryVo restore(MultipartFile file, GalleryVo gvo) {
		System.out.println("galservice.restore()");
		System.out.println(file.getOriginalFilename());
		
		//db 저장할 정보 수집
		String saveDir = "C:\\javaStudy\\upload";
		
		
		//오리지널 파일이름
		String orgName = file.getOriginalFilename();
		
		//확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		
		//서버 저장 파일 이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		
		//서버 파일 패스 --> 저장경로
		String filePath = saveDir + "\\" + saveName;
		
		//파일 사이즈
		long fileSize = file.getSize();
		
		//gvo에 저장
		gvo.setFilePath(filePath);
		gvo.setOrgName(orgName);
		gvo.setSaveName(saveName);
		gvo.setFileSize(fileSize);
		
		
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
		System.out.println("galservice gvo : "+gvo);
		
		galdao.insert(gvo);
		
		return gvo;

	}

	
	
}
