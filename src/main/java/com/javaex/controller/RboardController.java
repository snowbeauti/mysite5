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
import com.javaex.service.RboardService;
import com.javaex.vo.RboardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/rboard")
public class RboardController {
	
	@Autowired
	private RboardService rservice;
	
	//리스트
	@RequestMapping(value = "")
	public String list(Model model) {
		System.out.println("/board");
		
		List<RboardVo> rList = rservice.rboardlist();
		System.out.println(rList.toString());
		
		model.addAttribute("rList", rList);
		
		return "rboard/List";
		
	}
	
	//게시글 읽기
	@RequestMapping(value = "/read",method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@ModelAttribute RboardVo rboardvo, Model model) {
		System.out.println("/rboard/read");
	
		RboardVo rvo = rservice.read(rboardvo.getNo());
		System.out.println("read 보내기" + rboardvo.getNo());
	
		model.addAttribute("rvo", rvo);
				
		return "rboard/Read";
	}
	
	//게시글 수정폼
	@RequestMapping(value = "/modifyform", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyform(@RequestParam("no") int no, Model model) {
		System.out.println("/board/modifyform");
		
		RboardVo rvo = rservice.read(no);
		model.addAttribute("rvo", rvo);
		
		return "rboard/ModifyForm";
	}
	
	//게시글 수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute RboardVo rvo) {
		System.out.println("/rboard/modify");
		
		rservice.modify(rvo);
		System.out.println("수정 " + rvo);
		return "redirect:/rboard/read?no=" + rvo.getNo();
	}
	
	//게시글 삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute RboardVo rvo){
		System.out.println("/rboard/delete");
		rservice.delete(rvo);
		
		if(rservice.delete(rvo).equals("delete")) {
		System.out.println("삭제 " + rvo);
		
		} else {
			return "redirect:/rboard";
		}
		return "redirect:/rboard";
	}
	
	//게시글 작성폼
	@RequestMapping(value = "/writeform")
	public String writeform() {
		System.out.println("/rboard/writeform");
		return"rboard/WriteForm";
	}
	
	//게시글 작성
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute RboardVo rvo, HttpSession session) {
		System.out.println("/rboard/write");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		rvo.setUser_no(authUser.getNo());
		
		System.out.println(rvo);
		rservice.write(rvo);
		
		return"redirect:/rboard";
	}

	//댓글 작성폼
	@RequestMapping(value = "/rewriteform")
	public String rewrite(@ModelAttribute RboardVo rvo, Model model) {
		System.out.println("/rboard/rewriteform");
		
		model.addAttribute("rvo", rvo);
		
		return"rboard/ReWriteForm";
	}

	//댓글 작성
	@RequestMapping(value = "/rewrite", method = { RequestMethod.GET, RequestMethod.POST })
	public String rewrite(@ModelAttribute RboardVo rvo, HttpSession session) {
		System.out.println("/rboard/write");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		rvo.setUser_no(authUser.getNo());
		
		System.out.println("controller: " + rvo);
		rservice.rewrite(rvo);
		
		return"redirect:/rboard";
	}
}
