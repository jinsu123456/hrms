<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/pagination.css" rel="stylesheet">
</head>
<body id="page-top">
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<!-- Begin Page Content -->
	<div class="container-fluid">
	
		<!-- Content Row -->
		<div class="row">
	
			<!-- Area Chart -->
			<div class="col-xl-12 col-lg-12">
				<div class="card shadow mb-4">
					<!-- Card Header - Dropdown -->
					<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
						<h6 class="m-0 font-weight-bold text-primary">
							<span class="menubar">근무 관리</span> 
						</h6>
					</div>
					<!-- Card Body -->
					<div class="card-body">
					
						<div class="mb-3 col">
							<div class="float-left mb-2">
								<input type="text" name="" value="" id="startDate1" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(3,1)">
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate1)" style="cursor: pointer;"></i> ~ 
								<input type="text" name="" value="${today }" id="endDate1" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(3,1)"> 
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate1)" style="cursor: pointer;"></i>
							</div>
							<div id="searchDiv" class="float-right">
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
									<option value="L">대표</option>
									<option value="E">사원</option>
								</select>
								<input type="text" id="searchVal" class="inp">
								<div class="d-inline px-2 py-2 bg-secondary" onclick="reloadListFn(3,1)" style="cursor: pointer; border-radius: 5px;">
									<i class="fas fa-lg fa-search text-white"></i>
								</div>
							</div>
						</div>
						<table>
							<thead>
								<tr>
									<th>날짜</th>
									<th>이름</th>
									<th>출근</th>
									<th>퇴근</th>
									<th>초과근무</th>
									<th>총 근무</th>
								</tr>
							</thead>
							<tbody class="outputBody">
								<c:forEach var="item" items="${allWorkList }">
									<tr>
										<td>${item.date }  ${item.dayOfWeek }</td>
										<td>
											<span class="d-inline card bg-info text-white text-center px-2">${item.dept }</span>
											<span>${item.name }</span>
											<span class="text-xs font-weight-bold text-primary text-uppercase mb-1">${item.position }</span>
										</td>
										<td>${item.start }</td>
										<c:if test="${item.end eq '-' }">
											<td><a href="#"><i class="fas fa-clock fa-lg" onclick="leaveWork_admin('${item.wNo}')"></i></a></td>
										</c:if>
										<c:if test="${item.end ne '-' }">
											<td>${item.end }</td>
										</c:if>
										<td>
											<c:forEach var="i" begin="0" end="1">
												<c:set var="ot" value="${fn:split(item.overtime,',')[i]}" />
												<c:set var="ots" value="${fn:split(item.overtime_state,',')[i]}" />
												<c:choose>
													<c:when test="${ots eq '(결재 대기)'}">
														<c:set var="className" value="text-gray" />
													</c:when>
													<c:when test="${ots eq '(결재 진행중)'}">
														<c:set var="className" value="text-red" />
													</c:when>
													<c:when test="${ots eq '(승인)'}">
														<c:set var="className" value="text-green" />
													</c:when>
													<c:when test="${empty ots}">
														<c:set var="className" value="" />
													</c:when>
												</c:choose>
												<c:if test="${i==1 }"><br></c:if>
												<c:if test="${not empty ot}">
													<span class="${className }">${ot } ${ots}</span> 
												</c:if>
											</c:forEach>
										</td>
										<td>${item.total }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table><br>
						
						<!------- 페이징 ------->
						<div class="page_wrap">
  							<div class="page_nation">
  								<c:if test="${pagingVO.startPage > pagingVO.cntPage }">
									<a class="arrow prev" onclick="reloadListFn(3,${pagingVO.startPage-1})"></a>
								</c:if>
								<c:forEach var="p" begin="${pagingVO.startPage }" end="${pagingVO.endPage }">
									<c:choose>
										<c:when test="${p eq pagingVO.nowPage }">
											<a class="active" onclick="reloadListFn(3,${p}); pagingFn(this)">${p }</a>
										</c:when>
										<c:when test="${p ne pagingVO.nowPage }">
											<a onclick="reloadListFn(3,${p}); pagingFn(this)">${p }</a>
										</c:when>
									</c:choose>
								</c:forEach>
								<c:if test="${pagingVO.endPage < pagingVO.lastPage }">
									<a class="arrow next" onclick="reloadListFn(${pagingVO.endPage+1})"></a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</sec:authorize>
<!-- End of Main Content -->
<%-- <script src="${pageContext.request.contextPath}/resources/js/work_menu.js"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/resources/js/clock.js"></script> --%>
<script>
	const today = '${today}';
	const path = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/work_main_admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax_Const.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/work_date.js"></script>
<%@ include file="../include/footer.jsp"%>