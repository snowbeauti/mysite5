<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add List</title>

<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css"	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css"	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"	rel="stylesheet" type="text/css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>


</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li><a href="${pageContext.request.contextPath}/gbook">일반방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/gbook/ajaxlist">ajax방명록</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<!-- 	<form action="${pageContext.request.contextPath}/api/gbook/write" method="get"> -->
				<table id="guestAdd">
					<colgroup>
						<col style="width: 70px;">
						<col>
						<col style="width: 70px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th><label class="form-text" for="input-uname">이름</label></th>
							<td><input id="input-uname" type="text" name="name"></td>
							<th><label class="form-text" for="input-pass">패스워드</label></th>
							<td><input id="input-pass" type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
						</tr>
					</tbody>

				</table>
				<!-- //guestWrite -->

				
				<!-- 	</form>  -->

				<div id=guestbookListArea>
					<!-- 방명록 글 리스트 출력 -->
				</div>

			</div>
			<!-- //guestbook -->
			
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

<!-- 모달창 영역 -->
	<div class="modal fade" id="delModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">방명록 삭제</h4>
	      </div>
	      <div class="modal-body">
	      
	      	<label>비밀번호</label>
	      	<input id="modalPassword" type="text" name="passowrd" value=""><br>
	      	
	      	<!-- no 히든처리 -->
	      	<input id="modalNo" type="hidden" name="no" value="">
	      
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	        <button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->


</body>

<script type="text/javascript">
	//DOM이 생성되면 
	$("document").ready(function() {
		console.log("ready");
		//리스트 출력
		fetchList();
	});
	
	//모달창 삭제버튼 클릭할때 
	$("#modalBtnDel").on("click", function(){
		console.log("모달창 삭제 버튼 클릭");
				
		//모달창 비밀번호, no 수집
		var guestbookVo = {
			password: $("#modalPassword").val(),
			no: $("#modalNo").val()
		};
		
		console.log(guestbookVo);
		
		$.ajax({
			
			url : "${pageContext.request.contextPath }/api/gbook/remove",		
			type : "post",
			//contentType : "application/json",
			data : guestbookVo,
			dataType : "json",
			success : function(count){
				/*성공시 처리해야될 코드 작성*/
				console.log(count);
				
				if(count == 1){
					$("#delModal").modal("hide");  //모달창닫기
					
					//no 테이블(글) 화면에서 안보이도록 처리 
					$("#t-"+ guestbookVo.no).remove();
					
				}else{
					//모달창 닫기
					alert("비밀번호를 잘못 입력했습니다.");
					$("#modalPassword").val("");
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
	});
	
	
	//삭제버튼 클릭할때 --> 비밀번호 입력창 호출
	$("#guestbookListArea").on("click", "a", function() {
		event.preventDefault();
		console.log("모달 창 호출");
		
		//비밀번호 필드 초기화
		$("#modalPassword").val("");
		
		//게시글의 no 값
		var no = $(this).data("no");
		$("#modalNo").val(no);
		
		//모달창 호출
		$("#delModal").modal();
	});
	
	
	//방명록 등록버튼 클릭할때
	$("#btnsubmit").on("click", function() {
		console.log("방명록 등록 버튼 클릭");
		
		//방명록 데이터 수집
		
		var guestbooVo = {
			name: $("[name='name']").val(),
			password: $("[name='pass']").val(),
			content: $("[name='content']").val()
		};
		
		console.log(guestbookVo);
		
		
		//ajax방식으로 요청(저장)
		$.ajax({
			
			/*
			url : "${pageContext.request.contextPath }/api/gbook/write",
			type : "post",
			//contentType : "application/json",
			data : guestbookVo,
			*/
			
			 //json 
			url : "${pageContext.request.contextPath }/api/gbook/write2",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(guestbookVo), 
			
			
			dataType : "json",
			success : function(guestbookVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbooVo);
				render(guestbookVo, "up"); //게스트북 정보 출력
				
				
				/* 입력폼 비우기 */
				$("[name='name']").val("");
				$("[name='pass']").val("");
				$("[name='content']").val("");
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	//방명록 글 정보 + html 조합하여 화면에 출력
	function render(guestbookVo, updown) {
		var str = "";
		str += '<table id="t-'+ guestbookVo.no +'" class="guestRead">';
		str += '	<colgroup>';
		str += '		<col style="width: 10%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 10%;">';
		str += '	</colgroup>';
		str += '	<tr>';
		str += '		<td>' + guestbookVo.no + '</td>';
		str += '		<td>' + guestbookVo.name + '</td>';
		str += '		<td>' + guestbookVo.reg_date + '</td>';
		str += '		<td><a href="" data-no="'+ guestbookVo.no +'" >[삭제]</a></td>';
		str += '	</tr>';
		str += '	<tr>';
		str += '		<td colspan=4 class="text-left">' + guestbookVo.content
				+ '</td>';
		str += '	</tr>';
		str += '</table>';
		if (updown == "down") {
			$("#guestbookListArea").append(str);
		} else if (updown == "up") {
			$("#guestbookListArea").prepend(str);
		} else {
			console.log("방향 미지정");
		}
	}
	
	
	//전체리스트 출력
	function fetchList() {
		
		$.ajax({
			
			url : "${pageContext.request.contextPath}/api/gbook",
			type : "post",
			//contentType : "application/json",
			//data : {name: "홍길동"},
			dataType : "json",
			success : function(guestList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestList);
				for (var i = 0; i < guestList.length; i++) {
					render(guestList[i], "up");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	}
</script>
</html>