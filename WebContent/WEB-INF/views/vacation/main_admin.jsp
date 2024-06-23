<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<head>
	<!-- 데이트피커 { -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
	<link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<!-- } -->
	
	<!-- 캘린더(데이트피커) 스타일 (jquery-ui.css 파일호출 보다 아래에 있어야 합니다.) { -->
	<link href="${pageContext.request.contextPath}/resources/css/sign.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/pagination.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet">
</head>
	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="d-sm-flex align-items-center justify-content-between mb-4">
			<h1 class="h3 mb-0 text-gray-800">연차</h1>
		</div>

		<!-- Content Row -->
		<div class="row">

			<!-- Area Chart -->
			<div class="col-xl-12 col-lg-12">
				<div class="card shadow mb-4">
					<!-- Card Header - Dropdown -->
					<div
						class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
						<h6 class="m-0 font-weight-bold text-primary">
							<span class="menubar" id="menubar1">연차 조회</span> 
						</h6>
					</div>
					<!-- Card Body -->
					<div class="card-body">
					
						<!-- 연차 조회(관리자) -->
						<div id="menu1">
							<div class="mb-3 col">
								<div id="searchDiv" class="float-left mb-3">
									<select class="inp" name="category_dept">
										<option value="">전체</option>
										<option value="D">개발부</option>
										<option value="S">영업부</option>
										<option value="P">기획부</option>
										<option value="H">인사부</option>
									</select>
									<select class="inp" name="category_position">
										<option value="">전체</option>
										<option value="D">부장</option>
										<option value="L">팀장</option>
										<option value="E">사원</option>
									</select>
									<input type="text" id="searchVal" class="inp">
									<div class="d-inline px-2 py-2 bg-secondary" onclick="reloadListFn(1)" style="cursor: pointer; border-radius: 5px;">
										<i class="fas fa-lg fa-search text-white"></i>
									</div>
								</div>
								<div class="float-right mb-2"></div>
							</div>
							<table>
								<thead>
									<tr>
										<th>부서</th>
										<th>직급</th>
										<th>이름</th>
										<th>총 연차</th>
										<th>사용 연차</th>
										<th>잔여 연차</th>
										<th>내역</th>
										<th>연차 부여</th>
									</tr>
								</thead>
								<tbody class="outputBody">
									<c:forEach var="i" items="${list}">
										<tr>
											<td>${i.dept }</td>
											<td>${i.position }</td>
											<td>${i.name }</td>
											<td>
												<span class="text-bold">
													<fmt:formatNumber type="number" maxFractionDigits="0" value="${(i.keepVaca + i.useVaca) / 8}" />일
												</span>
												<span class="text-sm text-blue">(${i.keepVaca + i.useVaca }시간)</span>
											</td>
											<td>
												<span class="text-bold">${i.useVaca_day }일</span>
												<span class="text-sm text-red">(${i.useVaca }시간)</span>
											</td>
											<td>
												<span class="text-bold">${i.keepVaca_day }일</span>
												<span class="text-sm text-green">(${i.keepVaca }시간)</span>
											</td>
											<td><a class="modal-open" href="#" onclick="viewFn('${i.userid}', '${i.name }')"><i class="far fa-id-card fa-lg"></i></a></td>
											<td><a href="#" onclick="giveVacaFn('${i.userid}', '${i.dept }', '${i.position }', '${i.name }')"><i class="fas fa-fw fa-plane fa-lg"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<!--==================== 페이징 =================-->
	<div class="page_wrap">
		<div class="page_nation">
			<c:if test="${pagingVO.startPage > pagingVO.cntPage }">
				<a class="arrow prev" onclick="reloadListFn(${pagingVO.startPage-1})"></a>
			</c:if>
			<c:forEach var="p" begin="${pagingVO.startPage }" end="${pagingVO.endPage }">
				<c:choose>
					<c:when test="${p eq pagingVO.nowPage }">
						<a class="active" onclick="reloadListFn(${p}); pagingFn(this)">${p }</a>
					</c:when>
					<c:when test="${p ne pagingVO.nowPage }">
						<a onclick="reloadListFn(${p}); pagingFn(this)">${p }</a>
					</c:when>
				</c:choose>
			</c:forEach>
			<c:if test="${pagingVO.endPage < pagingVO.lastPage }">
				<a class="arrow next" onclick="reloadListFn(${pagingVO.endPage+1})"></a>
			</c:if>
		</div>
	</div>

	<!--==================== 모달창 =================-->
	<div class="popup-wrap" id="popup">
		<div class="popup">
			<div class="popup-head">
				<span class="head-title"></span>
			</div>
			<div class="popup-body">
				<div class="body-content">
					<div class="body-contentbox">
					</div>
				</div>
			</div>
			<div class="popup-foot">
				<span class="pop-btn close float-right close-custom" id="close">창 닫기</span>
			</div>
		</div>
	</div>
	
	<!-- End of Main Content -->

<script src="${pageContext.request.contextPath}/resources/js/vaca_main_admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/modal.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax.js"></script>
<%-- <script src="${pageContext.request.contextPath}/resources/js/sign_main.js"></script> --%>
<%@ include file="../include/footer.jsp"%>