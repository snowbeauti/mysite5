package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {

	@Autowired
	private GuestDao gdao;

	// 리스트
	public List<GuestVo> guestList() {
		System.out.println("gservice GList");

		return gdao.guestList();
	}

	// 등록
	public int add(GuestVo gvo) {
		System.out.println("gservice add");

		return gdao.insert(gvo);
	}

	// 삭제
	public String delete(GuestVo gvo) {
		System.out.println("gservice getguest");
		int no = gvo.getNo();

		GuestVo guestvo = gdao.getGuest(no);
		System.out.println("gvo" + gvo.getPassword() + ", guestvo" + guestvo.getPassword());
		
		if (gvo.getPassword().equals(guestvo.getPassword())) {
			System.out.println("gservice delete");
			gdao.delete(guestvo.getNo());
			return "success";
		} else {
			System.out.println("delete fail");
			return "fail";
		}
	}
	
	/* 글 삭제 */
	public int remove(GuestVo gvo){
		System.out.println("[gservice] remove()");
		
		return gdao.delete(gvo);
	}

	//ajax 글 저장
	public GuestVo writeResultVo(GuestVo gvo) {
		//글저장
		gdao.insertSelectkey(gvo);
		
		//no값을 알 수 있다.
		int no  = gvo.getNo();
		
		//글 1개 조회
		return gdao.selectOne(no);
		
	}
}
