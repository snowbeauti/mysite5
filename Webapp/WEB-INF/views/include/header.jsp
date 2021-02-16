<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<div id="header">
			<h1><a href="${pageContext.request.contextPath}/">MySite</a></h1>
		
		<c:choose>
			<c:when test="${sessionScope.authUser == null}">

			<ul>
				<li><a href="${pageContext.request.contextPath}/user/loginform">로그인</a></li>
				<li><a href="${pageContext.request.contextPath}/user/joinform">회원가입</a></li>
			</ul>

			
			</c:when>
			
			<c:otherwise>
			<ul>
				<li>${sessionScope.authUser.name} 님 안녕하세요^^</li>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/user/mform">회원정보수정</a></li>
			</ul>
			</c:otherwise>
			
		</c:choose>
			
		</div>
		<!-- //header -->
		
		
		<div id="nav">
			<ul>
				<li><a href="${pageContext.request.contextPath}/gbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/gallery/list">갤러리</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list3">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->
