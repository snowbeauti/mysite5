package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/api/gbook")
public class ApiGuestbookController {
	
	@Autowired
	private GuestService gservice;
	
	//전체리스트 가져오기
	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	public List<GuestVo> list() {
		System.out.println("[ApiGuestbookController] /list");
		
		return gservice.guestList();
	}
	
	//글작성(ajax)
	@ResponseBody
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public GuestVo write(@ModelAttribute GuestVo gvo) {
		System.out.println("[ApiGuestbookController] /write");
		System.out.println(gvo);
		
		//입력된 vo를 전달하고 저장vo를 받아야함
				
		return gservice.writeResultVo(gvo);
	}
	
	// 글작성(ajax-json)
		@ResponseBody
		@RequestMapping(value = "/write2", method = { RequestMethod.GET, RequestMethod.POST })
		public GuestVo write2(@RequestBody GuestVo gvo) {
			System.out.println("[ApiGuestbookController] /write2");
			System.out.println(gvo);

			// 입력된vo전달하고 저장vo를 받아야함
			return gservice.writeResultVo(gvo);
		}

		
		// 글삭제(ajax)
		@ResponseBody
		@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
		public int remove(@ModelAttribute GuestVo gvo) {
			System.out.println("[ApiGuestbookController] /write");
			System.out.println(gvo);

			return gservice.remove(gvo);

		}
	
}
