<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/navigator.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<head>
	<link href="${pageContext.request.contextPath}/resources/css/etc.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/calendar.css" rel="stylesheet">
</head>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">홈</h1>
	</div>

	<sec:authorize access="!hasRole('ROLE_ADMIN')">
	<!-- Content Row -->
	<div class="row">
		
		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-primary shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-lg font-weight-bold text-primary text-uppercase mb-1">
								현재 시간
							</div>
							<div class="h5 mb-2 font-weight-bold text-gray-800">
								<span id="goToWorkClock"></span> ~
								<span id="leaveWorkClock"></span>
							</div>
						</div>
						<div class="col-auto">
							<a class="btn btn-primary btn-icon-split"> 
								<span id="goToWorkButton" onclick="workFn('GO')" class="text">출근</span>
							</a> 
							<a class="btn btn-danger btn-icon-split"> 
								<span id="leaveWorkButton" onclick="workFn('LEAVE')" class="text">퇴근</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-success shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-lg font-weight-bold text-success text-uppercase mb-1">
								결재 문서
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								${docSignCount+vacaSignCount+overSignCount}건
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-file-signature fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Earnings (Monthly) Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-info shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-lg font-weight-bold text-info text-uppercase mb-1">
								금주 누적 근무
							</div>
							<div class="row no-gutters align-items-center">
								<div class="col-auto">
									<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${myThisWeekTotalWorkTime }</div>
								</div>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-briefcase fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Pending Requests Card Example -->
		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-warning shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-lg font-weight-bold text-warning text-uppercase mb-1">
								금년 잔여 연차
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								${user.keepVaca_day }일
								<span class="text-md">(${user.keepVaca }시간)</span>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-plane fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</sec:authorize>

	<!-- Content Row -->
	<div class="row">

		<!-- Area Chart -->
		<div class="col-xl-8 col-lg-7">
			<div class="card shadow mb-4">
				<!-- Card Header - Dropdown -->
				<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">월별 연차현황</h6>
				</div>
				<!-- Card Body -->
				<div class="card-body">
					<div class="row">
						<div class="calendar col">
							<div class="header mb-3">
								<div class="year-month"></div>
								<div class="nav">
									<button class="nav-btn go-prev" onclick="prevMonth()">&lt;</button>
									<button class="nav-btn go-today" onclick="goToday()">Today</button>
									<button class="nav-btn go-next" onclick="nextMonth()">&gt;</button>
								</div>
							</div>
							<div class="main">
								<div class="days">
									<div class="day">일</div>
									<div class="day">월</div>
									<div class="day">화</div>
									<div class="day">수</div>
									<div class="day">목</div>
									<div class="day">금</div>
									<div class="day">토</div>
								</div>
								<div class="dates"></div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>

		<!-- Pie Chart -->
		<div class="col-xl-4 col-lg-5">
			<div class="card shadow mb-4">
				<!-- Card Header - Dropdown -->
				<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">공지사항</h6>
				</div>
				<!-- Card Body -->
				<div class="card-body">
					<div class="h5 mb-3 ml-2 font-weight-bold text-gray-800 today-date">안내드립니다.</div>
					<c:forEach items="${noticeList}" var="vo" end="1">
						<div class="card mt-3 bg-light text-black">
							<div class="card-body">
								<div class="row">
									<div class="col-3 pr-0">
										<div class="d-inline card bg-danger text-white text-center px-2">공지</div>
										<c:choose>
											<c:when test="${vo.userId eq '99000'}">
												<td>대표</td>
											</c:when>
											<c:when test="${vo.userId eq '99001'}">
												<td>관리자</td>
											</c:when>
											<c:otherwise>
												<td>오류</td>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-6 d-inline-block text-truncate">
										<a href="${pageContext.request.contextPath}/notice/view.do?noticeNo=${vo.noticeNo}">${vo.title}</a>
									</div>
									<div class="col-3 text-right">${fn:substring(vo.rdate,0,10) }</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			
			<div class="card shadow mb-4">
				<!-- Card Header - Dropdown -->
				<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">연차 사원</h6>
				</div>
				
				<!-- Card Body -->
				<!-- 연차자 명단 출력 div -->
				<div id="vaca-list" class="card-body">
					<div id="vaca-date" class="h4 mb-3 ml-2 font-weight-bold text-gray-800 today-date">${today}</div>
					<c:if test="${!empty todayList }">
						<c:forEach var="i" items="${todayList }">
							<div class="card mt-3 bg-light text-black">
								<div class="card-body">
									<div id="vaca-dept" class="d-inline card bg-info text-white text-center px-2">${i.dept}</div>
										<span id="vaca-name">${i.name}</span>
										<span class="text-xs font-weight-bold text-primary text-uppercase mb-1" id="vaca-position">${i.position}</span>
									<!-- <div id="vaca-state" class="d-inline ml-5">${i.state}</div> -->
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty todayList }">
						<div class="h4 mb-3 ml-2 font-weight-bold text-lightgray">연차자 없음 <i class="fas fa-user-alt-slash"></i></div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

</div>
<script>
	let list = '${list}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/calendar_home.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
	<script>
		let start = '${start}';
		let end = '${end}';
		const today = '${today}';
		let myThisWeekTotalWorkTime = '${myThisWeekTotalWorkTime}';
		const path = '${pageContext.request.contextPath}';
		let ovoAppArr = '${ovoAppArr}'
		const cls = "btn btn-success btn-icon-split";
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/workButton.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/workInsert.js"></script>
</sec:authorize>
<!-- /.container-fluid -->
<%@ include file="include/footer.jsp" %>