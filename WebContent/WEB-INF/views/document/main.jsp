<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<span class="menubar" id="menubar1" onclick="displayFn(1)">내 기안</span> 
						<span class="menubar" id="menubar2" onclick="displayFn(2)">부서 기안</span>
					</h6>
				</div>
				<!-- Card Body -->
				<div class="card-body">
				
					<!-- 내 기안 -->
					<div id="menu1">
						<div class="mb-3 col">
							<input type="text" name="" value="" id="startDate1" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(1)">
							<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate1)" style="cursor: pointer;"></i> ~ 
							<input type="text" name="" value="${today }" id="endDate1" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(1)"> 
							<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate1)" style="cursor: pointer;"></i>
							<div class="float-right">
								<a href="write.do" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm small float-right">
									<i class="fas fa-fw fa-briefcase"></i> 기안 작성
								</a>
							</div>
						</div>
						<table>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성일</th>
									<th>결재</th>
								</tr>
							</thead>
							<tbody id="outputBody1">
								<c:forEach var="i" items="${list_my}">
									<tr>
										<td>${i.rownum }</td>
										<td><a href="javascript:viewFn('${i.docNo }')">${i.title }</a></td>
										<td>${i.date }</td>
										<c:choose>
											<c:when test="${i.state eq '대기'}">
												<c:set var="className" value="btn btn-light btn-gradient2 mini" />
											</c:when>
											<c:when test="${i.state eq '진행중'}">
												<c:set var="className" value="btn-gradient red mini" />
											</c:when>
											<c:when test="${i.state eq '승인'}">
												<c:set var="className" value="btn-gradient green mini" />
											</c:when>
											<c:when test="${i.state eq '반려'}">
												<c:set var="className" value="btn-gradient mini btn-secondary" />
											</c:when>
											<c:when test="${i.state eq '철회'}">
												<c:set var="className" value="btn-gradient mini btn-withdrawal" />
											</c:when>
										</c:choose>
										<td><span class="${className}">${i.state }</span></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<!-- 부서 기안 -->
					<div id="menu2" style="display:none;">
						<div class="mb-3 col">
							<input type="text" name="" value="" id="startDate2" class="datepicker inp" placeholder="시작일 선택" readonly="true" onchange="reloadListFn(2)">
							<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate2)" style="cursor: pointer;"></i> ~ 
							<input type="text" name="" value="${today }" id="endDate2" class="datepicker inp" placeholder="종료일 선택" readonly="true" onchange="reloadListFn(2)"> 
							<i class="fas fa-lg fa-calendar" onclick="iClickFn(endDate2)" style="cursor: pointer;"></i>
							<div class="float-right">
								<select class="inp" name="category">
									<option value="">전체</option>
									<option value="D">부장</option>
									<option value="L">팀장</option>
									<option value="E">사원</option>
								</select>
								<input type="text" name="searchVal" class="inp" placeholder="이름을 입력해주세요.">
								<div class="d-inline px-2 py-2 bg-secondary" onclick="reloadListFn(2)" style="cursor: pointer; border-radius: 5px;">
									<i class="fas fa-lg fa-search text-white"></i>
								</div>
							</div>
						</div>
						<table>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>작성일</th>
								</tr>
							</thead>
							<tbody id="outputBody2">
								<c:forEach var="i" items="${list_dept }">
									<tr>
										<td>${i.rownum }</td>
										<td><a href="javascript:viewFn('${i.docNo }')">${i.title }</a></td>
										<td>
											<span class="d-inline card bg-info text-white text-center px-2">${i.dept }</span>
											<span>${i.name }</span>
											<span class="text-xs font-weight-bold text-primary text-uppercase mb-1">${i.position }</span>
										</td>
										<td>${i.date }</td>
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
<!-- End of Main Content -->
<script src="${pageContext.request.contextPath}/resources/js/doc_main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax_Const.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>
<%@ include file="../include/footer.jsp"%>