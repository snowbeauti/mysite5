package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.dao.UserDao;
import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	// 필드
	@Autowired
	private UserDao udao;
	
	@Autowired
	private UserService uservice;

	// 회원가입폼
	@RequestMapping(value = "/joinform", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("/user/joinform");

		return "user/joinForm";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo uvo) {
		System.out.println("/user/join");
		System.out.println("controller.uvo" + uvo.toString());

		int count = uservice.join(uvo);

		return "user/joinOk";
	}

	// 로그인폼
	@RequestMapping(value = "/loginform", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("/user/loginForm");

		return "user/loginForm"; 
	}

	// 로그인
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo uvo, HttpSession session) {
		System.out.println("/user/login");
		System.out.println("controller.uvo" + uvo.toString());

		UserVo authUser = uservice.login(uvo);
		
		if (authUser == null) {
			System.out.println("로그인실패");

			return "redirect:/user/loginform?result=fail";
		} else {
			System.out.println("로그인성공");
			session.setAttribute("authUser", authUser);

			return "redirect:/";
		}

	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {
		System.out.println("로그아웃");
		
		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/";
	}
	
	//회원정보수정폼
	@RequestMapping(value = "/mform")
	public String mform(Model model, HttpSession session) {
		System.out.println("mform");
		
		//세션에 있는 no가져오기
		int no = ((UserVo) session.getAttribute("authUser")).getNo();
		
		
		//회원정보가져오기
		UserVo uvo = uservice.mform(no);
		
		//jsp에 데이터 보내기
		model.addAttribute("uvo", uvo);
		
		return "user/modifyForm";
	}

	//회원정보수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute UserVo uvo, HttpSession session) {
		System.out.println("modify");
			
			//세션에 사용자정보 가져오기
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//세션에서 no값 가져오기
			int no = authUser.getNo();
			
			//vo에 no정보 추가
			uvo.setNo(no);
			
			//회원정보 수정
			int count = uservice.modify(uvo);
			
			//세션에 이름변경
			authUser.setName(uvo.getName());			
			
			return "redirect:/";
	}
	
	//회원가입 - 아이디체크
	@ResponseBody
	@RequestMapping(value = "/idcheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String idcheck(@RequestParam("id") String id ) {
		System.out.println("user/idcheck");
		System.out.println("checkid = " + id );		
		
		String result = uservice.idcheck(id);		
		System.out.println(result);
		
		return result; //jsp를 찾는 문법 (@ResponseBody -->response의 Body영역에 데이터만 보낸다._return값)
		
	}
}
