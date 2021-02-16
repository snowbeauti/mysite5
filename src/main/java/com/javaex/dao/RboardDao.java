package com.javaex.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.javaex.vo.RboardVo;

@Repository
public class RboardDao {

	@Autowired
	private SqlSession sqlSession;
	
	//리스트 조회
	public List<RboardVo> rboardList(){
		System.out.println("rdao boardList");
		
		return sqlSession.selectList("rboard.selectList");
	}
	
	//게시글 조회
	public RboardVo selectOne(int no) {
		System.out.println("rdao selectOne");
		return sqlSession.selectOne("rboard.selectOne", no);
	}
	
	//조회수 증가
	public int updateHit(int no) {
		System.out.println("rdao updateHit");
		return sqlSession.update("rboard.updateHit", no);
	}
	
	//게시글 수정
	public int update(RboardVo rvo) {
		System.out.println("rdao update");
		return sqlSession.update("rboard.update", rvo);
	}
	
	//게시글 삭제 여부 확인
	public List<RboardVo> rList(RboardVo rvo) {
		System.out.println("rdao deletefillter");
		return sqlSession.selectList("rboard.deletefillter", rvo);
	}
	
	//게시글 삭제
	public int delete(RboardVo rvo) {
		System.out.println("rdao delete");
		return sqlSession.delete("rboard.delete", rvo);
	}
	

	//게시글 작성
	public int insert(RboardVo rvo) {
		System.out.println("rdao insert" + rvo);
		return sqlSession.insert("rboard.insert", rvo);
	}
	//기존댓글 order_no증가
	public int reupdate(RboardVo rvo) {
		System.out.println("rdao reupdate" + rvo);
		RboardVo rboardvo = sqlSession.selectOne("rboard.selectOne", rvo.getNo());
		System.out.println("order증가" + rboardvo);
		
		return sqlSession.update("rboard.reupdate", rvo);
	}
	
	//댓글 작성
	public int reinsert(RboardVo rvo) {
		System.out.println("rdao reinsert" + rvo);
		return sqlSession.insert("rboard.reinsert", rvo);
	}

}
