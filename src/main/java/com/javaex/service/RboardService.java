package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javaex.dao.RboardDao;
import com.javaex.vo.RboardVo;

@Service
public class RboardService {

	@Autowired
	private RboardDao rdao;

	// 리스트
	public List<RboardVo> rboardlist() {
		System.out.println("rservice boardList");

		return rdao.rboardList();
	}

	// 게시글 가져오기 + 조회수
	public RboardVo read(int no) {
		System.out.println("rservice read");

		rdao.updateHit(no);
		return rdao.selectOne(no);
	}

	// 조회수
	public int hit(int no) {
		System.out.println("rservice hit");
		return rdao.updateHit(no);
	}

	// 게시글 수정
	public int modify(RboardVo rvo) {
		System.out.println("rservice modify");
		return rdao.update(rvo);
	}

	// 게시글 삭제
	public String delete(RboardVo rvo) {
		System.out.println("rservice delete");
		
		List<RboardVo> rList= rdao.rList(rvo);
		System.out.println("filltervo: " + rList);
		
		if (rList == null) {
			System.out.println("service 삭제");
			rdao.delete(rvo);
			return "delete";
		} else {
			System.out.println("service 삭제불가");
			return "deletefail";
		}
	}

	// 게시글 작성
	public int write(RboardVo rvo) {
		System.out.println("rservice write");
		return rdao.insert(rvo);
	}

	// 댓글 작성
	public int rewrite(RboardVo rvo) {
		System.out.println("rservice rewrite " + rvo);
		rdao.reupdate(rvo);

		return rdao.reinsert(rvo);
	}

}
