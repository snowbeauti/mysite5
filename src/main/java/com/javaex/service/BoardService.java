package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao bdao;
	
	//리스트
	public List<BoardVo> boardlist(){
		System.out.println("bservice boardList");
		
		return bdao.boardList();
	}
	
	//리스트+검색
	public List<BoardVo> boardList2(String keyword){
		System.out.println("bservic boardList2() keyword: " + keyword);
		
		List<BoardVo> boardList = bdao.selectList2(keyword);
		return boardList;
	}
	
	//리스트+검색+페이징
	public Map<String, Object> boardList3(String keyword, int crtPage){
		System.out.println("bservic boardList3() keyword: " + keyword);
		
		//crtPage -->시작번호, 끝번호 1--> 1, 10       2--> 11, 20         3-->21, 30
		/////////////////////////////
		//리스트구하기
		
		//페이지당 글갯수
		int listCnt = 10;
		
		//현재 페이지
		//crtPage
		crtPage = (crtPage>0) ? crtPage : (crtPage = 1);

		//시작글 번호 startRNum
		int startRNum = (crtPage * listCnt) -9; 	
		
		//끝글번호 endRNum
		int endRNum = crtPage * listCnt;

		
		List<BoardVo> boardList = bdao.selectList3(keyword, startRNum, endRNum);
		////////////////////////////////
		//페이징 계산
		
		//페이지당 버튼 갯수
		int pageBtnCount = 5;
		
		
		//전체 글갯수 구하기
		int totalCount = bdao.selectTotalCnt(keyword);
		
		//1 -> 1-5
		//2 -> 1-5
		//3 -> 1-5
		//4 -> 2-6
		//5 -> 3-7
		
		
			
		
		//마지막 버튼 번호
		
		int endPageBtnNo;
		if(crtPage<3) {
			endPageBtnNo = 5;
		} else {
			endPageBtnNo = crtPage + 2;
		}
		  	
		
		 //고정할 시 
		 //int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount) * pageBtnCount;
		 
		 
		 
		//시작 버튼 번호
			
			int startPageBtnNo;
			
			if(crtPage<3) {
				startPageBtnNo = 1;
			} else {
				startPageBtnNo = crtPage - 2;
			}
			
			
			// 고정할 시 
			// int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);
			 
			  		
		
		
		//다음버튼 boolean
		boolean next;
		
		if(endPageBtnNo * listCnt < totalCount) {
			next = true;
		} else {
			next = false;
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);
		}
		
		//이전버튼 boolean
		boolean prev;
		
		if(startPageBtnNo != 1) {
			prev = true;
		} else {
			prev = false;
		}
		
		//boardList, prev, startPageBtnNo, endPageBtnNo, next   --> jsp map 전달
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("prev", prev);
		pMap.put("next", next);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("startPageBtnNo", startPageBtnNo);
		
		return pMap;
	} 
	

	
	//게시글 가져오기 + 조회수
	public BoardVo read(int no) {
		System.out.println("bservice read");
		
		bdao.updateHit(no);		
		return bdao.selectOne(no);
				
	}

	
	//게시글 수정
	public int modify(BoardVo bvo) {
		System.out.println("bservice modify");
		return bdao.update(bvo);
	}
	
	//게시글 삭제
	public int delete(int no) {
		System.out.println("bservice delete");
		return bdao.delete(no);
	}
	
	//게시글 작성
	public int write(BoardVo bvo) {
		System.out.println("bservice write");
		return bdao.insert(bvo);
	}

}
