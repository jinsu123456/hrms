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
	<!-- Begin Page Content -->
	<div class="container-fluid">
	
		<!-- Page Heading -->
		<div class="d-sm-flex align-items-center justify-content-between mb-4">
			<h1 class="h3 mb-0 text-gray-800">근무</h1>
		</div>
	
		<!-- Content Row -->
		<div class="row">
	
			<!-- Earnings (Monthly) Card Example -->
			<div class="col-xl-4 col-md-6 mb-4">
				<div class="card border-left-primary shadow h-200 py-2">
					<div class="card-body">
						<div class="row no-gutters align-items-center">
							<div class="col mr-2">
								<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">오늘 근무 시간</div>
								<div class="h5 mb-0 font-weight-bold text-gray-800">
									<span id="goToWorkClock"></span> ~
									<span id="leaveWorkClock"></span>
								</div>
								<button id="goToWorkButton" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" onclick="workFn('GO')">
									출근하기
								</button>
								<button id="leaveWorkButton" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" onclick="workFn('LEAVE')">
									퇴근하기
								</button>
							</div>
							<div class="col-auto">
								<i class="fas fa-calendar fa-2x text-gray-300"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
	
			<!-- Earnings (Monthly) Card Example -->
			<div class="col-xl-4 col-md-6 mb-4">
				<div class="card border-left-info shadow h-100 py-2">
					<div class="card-body">
						<div class="row no-gutters align-items-center">
							<div class="col mr-2">
								<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
									금주 누적 근무
								</div>
								<div class="row no-gutters align-items-center">
									<div class="col-auto">
										<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${myThisWeekTotalWorkTime }</div>
									</div>
									<div class="col">
										<div class="progress progress-sm mr-2">
											<div id="workBar" class="progress-bar bg-info" role="progressbar"
												style="" aria-valuenow="50" aria-valuemin="0"
												aria-valuemax="100"></div>
										</div>
									</div>
								</div>
								<span class="text-lightgray"> / 52:00:00</span>
							</div>
							<div class="col-auto">
								<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
	
			<!-- Earnings (Monthly) Card Example -->
			<div class="col-xl-4 col-md-6 mb-4">
				<div class="card border-left-success shadow h-100 py-2">
					<div class="card-body">
						<div class="row no-gutters align-items-center">
							<div class="col mr-2">
								<div class="text-xs font-weight-bold text-success text-uppercase mb-1">
									금주 누적 초과근무
								</div>
								<div class="h5 mb-0 font-weight-bold text-gray-800">${myThisWeekOvertimeTime }</div>
							</div>
							<div class="col-auto">
								<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
	
		</div>
	
		<!-- Content Row -->
		<div class="row">
	
			<!-- Area Chart -->
			<div class="col-xl-12 col-lg-12">
				<div class="card shadow mb-4">
					<!-- Card Header - Dropdown -->
					<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
						<h6 class="m-0 font-weight-bold text-primary">
							<span id="myWork" class="menubar" onclick="displayFn(1)">내 근무</span> 
							<span id="myOvertime" class="menubar" onclick="displayFn(2)">내 초과근무</span> 
						<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CEO', 'ROLE_DIRECTOR', 'ROLE_LEADER')">
							<span id="allWork" class="menubar" onclick="displayFn(3)">근무 조회</span> 
						</sec:authorize>
						</h6>
					</div>
					<!-- Card Body -->
					<div class="card-body">
					
						<!-- 내 근무 -->
						<div id="menu1">
							<div class="mb-3 col">
								<input type="text" name="" value="" id="startDate1" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(1)">
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate1)" style="cursor: pointer;"></i> ~ 
								<input type="text" name="" value="${today }" id="endDate1" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(1)"> 
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate1)" style="cursor: pointer;"></i>
								<div class="float-right">
									<a onclick="overtime_aplicationFn()" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm small float-right">
										<i class="fas fa-fw fa-briefcase"></i> 초과근무 신청
									</a>
								</div>
							</div>
							<table>
								<thead>
									<tr>
										<th>날짜</th>
										<th>출근</th>
										<th>퇴근</th>
										<th>초과근무</th>
										<th>총 근무</th>
									</tr>
								</thead>
								<tbody class="outputBody1">
									<c:forEach var="item" items="${workList}">
										<tr>
											<td>${item.date } ${item.dayOfWeek }</td>
											<td>${item.start }</td>
											<td>${item.end }</td>
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
							</table>
						</div>
						
						<!-- 내 초과근무 -->
						<div id="menu2" class="display-none">
							<div class="mb-3 col">
								<input type="text" name="" value="" id="startDate2" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(2)">
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate2)" style="cursor: pointer;"></i> ~ 
								<input type="text" name="" value="${today }" id="endDate2" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(2)"> 
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate2)" style="cursor: pointer;"></i>
								<div class="float-right">
									<a onclick="overtime_aplicationFn()" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm small float-right">
										<i class="fas fa-fw fa-briefcase"></i> 초과근무 신청
									</a>
								</div>
							</div>
							<table>
								<thead>
									<tr>
										<th>날짜</th>
										<th>시작</th>
										<th>종료</th>
										<th>근무시간</th>
										<th>사유</th>
										<th>결재 상태</th>
										<th>조회</th>
									</tr>
								</thead>
								<tbody class="outputBody2">
									<c:forEach var="item" items="${overtimeList}">
										<tr>
											<td>${item.date } ${item.dayOfWeek }</td>
											<td>${item.start }</td>
											<td>${item.end }</td>
											<td>${item.total }</td>
											<td>${item.content }</td>
											<td>
												<c:choose>
													<c:when test="${item.state == 0}">
														<span class="btn btn-light btn-gradient2 mini">대기</span>
													</c:when>
													<c:when test="${item.state == 1 }">
														<span class="btn-gradient red mini">진행중</span>
													</c:when>
													<c:when test="${item.state == 2 }">
														<span class="btn-gradient green mini">승인</span>
													</c:when>
													<c:when test="${item.state == 3 }">
														<span class="btn-gradient mini btn-secondary">반려</span>
													</c:when>
													<c:when test="${item.state == 9 }">
														<span class="btn-gradient mini btn-withdrawal">철회</span>
													</c:when>
												</c:choose>
											</td>
											<td>
												<a href="overtime_view.do?no=${item.overtimeNo }">
													<i class="fas fa-clipboard-list fa-lg"></i>
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						
						<!-- 근무 조회 -->
						<div id="menu3" class="display-none">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CEO', 'ROLE_DIRECTOR', 'ROLE_LEADER')">
							<div class="mb-3 col">
								<input type="text" name="" value="" id="startDate3" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(3,1)">
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate3)" style="cursor: pointer;"></i> ~ 
								<input type="text" name="" value="${today }" id="endDate3" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(3,1)"> 
								<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate3)" style="cursor: pointer;"></i>
								<div id="searchDiv" class="float-right">
									<input type="text" id="searchVal" class="inp" placeholder="이름을 입력하세요">
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
								<tbody class="outputBody3">
									<c:forEach var="item" items="${allWorkList }">
										<tr>
											<td>${item.date }  ${item.dayOfWeek }</td>
											<td>
												<span class="d-inline card bg-info text-white text-center px-2">${item.dept }</span>
												<span>${item.name }</span>
												<span class="text-xs font-weight-bold text-primary text-uppercase mb-1">${item.position }</span>
											</td>
											<td>${item.start }</td>
											<td>${item.end }</td>
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
						</sec:authorize>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- End of Main Content -->
<script>
	let start = '${start}';
	let end = '${end}';
	const today = '${today}';
	let myThisWeekTotalWorkTime = '${myThisWeekTotalWorkTime}';
	const path = '${pageContext.request.contextPath}';
	let ovoAppArr = '${ovoAppArr}';
	const cls = "d-none d-sm-inline-block btn btn-sm btn-success shadow-sm";
</script>
<script src="${pageContext.request.contextPath}/resources/js/workButton.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/work_menu.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/workInsert.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax_Const.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/work_date.js"></script>
<%@ include file="../include/footer.jsp"%>