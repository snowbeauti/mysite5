package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//이미지 리스트 조회
	public List<GalleryVo> galleryList(){
		System.out.println("GalleryDao.galleryList()");
		
		return sqlSession.selectList("gallery.selectList");
	}
	
	//이미지 저장
	public int insert(GalleryVo gvo) {
		System.out.println("GalleryDao.insert()");
		
		return sqlSession.insert("gallery.insert", gvo);
	}

}
