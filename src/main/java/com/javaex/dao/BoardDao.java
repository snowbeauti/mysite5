package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	//리스트 조회
	public List<BoardVo> boardList(){
		System.out.println("bdao boardList");
		
		return sqlSession.selectList("board.selectList");
	}
	
	//글 전체 가져오기 (키워드)
	public List<BoardVo> selectList2(String keyword){
		System.out.println("bdao boardList2 keyword: " + keyword);
		List<BoardVo> boardList = sqlSession.selectList("board.selectList2", keyword);
		System.out.println("boardList: " + boardList);
		
		return boardList;
	}
	
	//글 전체 가져오기 (키워드+페이징)
	public List<BoardVo> selectList3(String keyword, int startRNum, int endRNum){
		System.out.println("bdao boardList3 keyword: " + keyword);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startRNum", startRNum);
		map.put("endRNum", endRNum);
		System.out.println("map: " + map);
		
		return sqlSession.selectList("board.selectList3", map);
	}
	
	//전체 글 개수조회
	public int selectTotalCnt(String keyword) {
		
		return sqlSession.selectOne("board.selectTotalCnt", keyword);
	}
		
		
	//게시글 조회
	public BoardVo selectOne(int no) {
		System.out.println("bdao selectOne");
		return sqlSession.selectOne("board.selectOne", no);
	}
	
	//조회수 증가
	public int updateHit(int no) {
		System.out.println("bdao updateHit");
		return sqlSession.update("board.updateHit", no);
	}
	
	//게시글 수정
	public int update(BoardVo bvo) {
		System.out.println("bdao update");
		return sqlSession.update("board.update", bvo);
	}
	
	//게시글 삭제
	public int delete(int no) {
		System.out.println("bdao delete");
		return sqlSession.delete("board.delete", no);
	}
	
	//게시글 작성
	public int insert(BoardVo bvo) {
		System.out.println("bdao insert");
		return sqlSession.insert("board.insert", bvo);
	}

}
