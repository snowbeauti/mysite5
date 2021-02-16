package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService bservice;
	
	//리스트
	@RequestMapping(value = "")
	public String list(Model model) {
		System.out.println("/board");
		
		List<BoardVo> bList = bservice.boardlist();
		System.out.println(bList.toString());
		
		model.addAttribute("bList", bList);
		
		return "board/List";
		
	}
	
	//리스트 + 검색기능
	@RequestMapping(value = "/list2", method = {RequestMethod.GET, RequestMethod.POST})
	public String list2(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {
		System.out.println("boardControll.list2() keyword: " + keyword);
		
		List<BoardVo> boardList = bservice.boardList2(keyword);
		
		model.addAttribute("bList", boardList);
		
		return "board/List2";
		
	}
	
	//리스트 + 검색기능 + 페이징
	@RequestMapping(value = "/list3", method = {RequestMethod.GET, RequestMethod.POST})
	public String list3(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
						@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
						Model model) {
		System.out.println("boardControll.list3() keyword: " + keyword + "crtPage: " + crtPage);
		
		Map<String, Object> pMap = bservice.boardList3(keyword, crtPage);
		System.out.println(pMap);
		model.addAttribute("pMap", pMap);
		
		return "board/List3";
		
	}
	//게시글 읽기
	@RequestMapping(value = "/read",method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@ModelAttribute BoardVo boardvo, Model model) {
		System.out.println("/board/read");

		BoardVo bvo = bservice.read(boardvo.getNo());	
		System.out.println("read 보내기" + boardvo.getNo());
	
		model.addAttribute("bvo", bvo);
				
		return "board/Read";
	}
	
	//게시글 수정폼
	@RequestMapping(value = "/modifyform", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyform(@RequestParam("no") int no, Model model) {
		System.out.println("/board/modifyform");
		
		BoardVo bvo = bservice.read(no);
		model.addAttribute("bvo", bvo);
		System.out.println(bvo); 
		
		return "board/ModifyForm";
	}
	
	//게시글 수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute BoardVo bvo) {
		System.out.println("/board/modify");
		
		bservice.modify(bvo);
		System.out.println("수정 " + bvo);
		return "redirect:/board/read?no=" + bvo.getNo();
	}
	
	//게시글 삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@RequestParam("no") int no){
		System.out.println("/board/delete");
		bservice.delete(no);
		System.out.println("삭제 " + no);
		return "redirect:/board";
	}
	
	//게시글 작성폼
	@RequestMapping(value = "/writeform")
	public String writeform() {
		System.out.println("/board/writeform");
		return"board/WriteForm";
	}
	
	//게시글 작성
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute BoardVo bvo, HttpSession session) {
		System.out.println("/board/write");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		bvo.setUser_no(authUser.getNo());
		
		System.out.println(bvo);
		bservice.write(bvo);
		
		return"redirect:/board";
	}

}
