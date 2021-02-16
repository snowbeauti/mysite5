package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galservice;
	
	//전체리스트 출력
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("GalleryCnt.list()");
		
		List<GalleryVo> galleryList = galservice.galleryList();
		System.out.println(galleryList.toString());
		
		
		model.addAttribute("galleryList", galleryList);
		
		return "/gallery/list";
	}


	
	//파일 업로드
		@RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
		public String upload(@RequestParam("file") MultipartFile file, 
				             @ModelAttribute GalleryVo gvo,
				             Model model,
				             HttpSession session) {
			System.out.println("Gallery-cnt.upload()");
			System.out.println(gvo);
			
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			gvo.setUser_no(authUser.getNo());
			
			galservice.restore(file, gvo);
			
			return "redirect:/gallery/list"; 
		}
		

}
