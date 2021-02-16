package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	//회원가입-->회원정보저장
	
	public int insert(UserVo uvo) {
		
		System.out.println("insert: " + uvo.toString());
		
		return sqlSession.insert("user.insert", uvo);
		
	
	}
	
	//로그인 -->회원정보 가져오기
	public UserVo selectUser(UserVo uvo) {
		System.out.println("selectUser: " + uvo.toString());
		
		return sqlSession.selectOne("user.selectUser", uvo);
	}
	
	//회원정보수정1 -->회원정보 가져오기
	public UserVo select(int no) {
		System.out.println("select(no):" + no);
		
		return sqlSession.selectOne("user.select", no);
	}
	
	//회원정보수정2 -->회원정보 변경하기
	public int update(UserVo uvo) {
		System.out.println("update:" + uvo.toString());
		
		return sqlSession.update("user.update", uvo);
	}
	
	//회원가입 id체크
	public UserVo selectOne(String id) {
		System.out.println("user dao selectOne" + id);
		return  sqlSession.selectOne("user.selectBuId", id);
	}
}
