<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite02/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite02/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">
	
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		

		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="read">
					<form action="#" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${boardVo.uName }</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${boardVo.hit }</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${boardVo.regDate }</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${boardVo.title }</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >
								${boardVo.content }
								
							</span>
						</div>
						<c:if test="${boardVo.userNo == authUser.no }">
							<a id="btn_modify" href="/mysite02/board?action=modifyForm&bNo=${param.no}">수정</a>
						</c:if>
						
						<a id="btn_modify" href="/mysite02/board?action=list">목록</a>
						
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
			
	</div>
	<!-- //wrap -->

</body>

</html>
