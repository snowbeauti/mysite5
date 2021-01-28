package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao udao;
	
	//회원가입
	public int join(UserVo uvo) {
		System.out.println("uservise join()");
		
		return udao.insert(uvo);
		
	}
	
	//로그인
	public UserVo login(UserVo uvo) {
		System.out.println("uservice login()");
		
		return udao.selectUser(uvo);
	}

	
	//회원정보 수정폼
	public UserVo mform(int no) {
		System.out.println("uservice mform()");
		
		return udao.select(no);
	}
	
	//회원정보 수정
	public int modify(UserVo uvo) {
		System.out.println("uservice modify()");
		
		return udao.update(uvo);
		
	}
}
