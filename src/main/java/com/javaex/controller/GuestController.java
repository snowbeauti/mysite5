package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/gbook")
public class GuestController {
	
	@Autowired
	private GuestService gservice;

	// 리스트
	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	public String addlist(Model model) {
		System.out.println("/gbook");

		List<GuestVo> gList = gservice.guestList();
		System.out.println(gList.toString());
		
		model.addAttribute("gList", gList);

		return "guestbook/AddList";
	}

	// 등록
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestVo gvo) {
		System.out.println("/gbook/add");

		gservice.add(gvo);

		return "redirect:/gbook";
	}
	
	
	// 삭제폼
	@RequestMapping(value = "/dform", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteform() {
		System.out.println("/gbook/deleteForm");

		return "guestbook/DeleteForm";
	}
	
	// 삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestVo gvo) {
		System.out.println("/gbook/delete");
		
		String result = gservice.delete(gvo);
		
		//System.out.println("service delete return: " + gservice.delete(gvo));
	    System.out.println("service delete return: " + result);
		
		
		if (result.equals("success")) {
			System.out.println("삭제완료");
			return "redirect:/gbook";

		} else {
			System.out.println("삭제실패");
			return "redirect:/gbook/dform?no=" + gvo.getNo() + "&result=fail";
		}

	}
	
	//ajaxList
	@RequestMapping(value = "ajaxlist", method = { RequestMethod.GET, RequestMethod.POST })
	public String ajaxList() {
		System.out.println("[guestbookContolloer] /ajaxList");
		
		return "guestbook/AjaxList";
	}

}