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
</head>
<body id="page-top">
	<!-- Begin Page Content -->
	<div class="container-fluid">
	
		<!-- Content Row -->
		<div class="row">
	
			<!-- Border Bottom Utilities -->
			<div class="col-lg-12">
	
				<div class="card mb-4 py-3 border-bottom-primary">
					<div class="card-body">
						<b>초과 근무 신청</b>
					</div>
				</div>
				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-3 d-none d-lg-block"></div>
							<div class="col-lg-6">
								<div class="p-5">
									<form class="user" onsubmit="return submitFn()" method="post">
										<div class="form-group">
											결재
											<div class="row">
												<c:forEach var="item" items="${signLineList }">
													<div class="col-sm-4 mb-4 mb-sm-0">
														<div class="form-control form-control-user sign-line-box">
															<span class="d-inline card bg-info text-white text-center px-2">${item.dept } </span>&nbsp;
															${item.name } &nbsp;
															<span class="small-grade">${item.position }</span>
														</div>
													</div>
												</c:forEach>
											</div>
										</div>
										<div class="form-group">
											기간<br>
											<div class="mb-3 col">
												<input type="text" name="date" id="startDate" class="datepicker inp" placeholder="날짜 선택" readonly="true">
												<i class="fas fa-lg fa-calendar" onclick="iClickFn(startDate)" style="cursor: pointer;"></i>
												&nbsp;&nbsp;&nbsp;
												<select name="start" id="startTime" onchange="itemChange()">
													<option value="NONE">시작 시간</option>
													<option value="12:00">12:00</option>
													<option value="18:00">18:00</option>
													<option value="19:00">19:00</option>
													<option value="20:00">20:00</option>
													<option value="21:00">21:00</option>
													<option value="22:00">22:00</option>
													<option value="23:00">23:00</option>
												</select> ~ 
												<select name="end" id="endTime">
													<option value="NONE">종료 시간</option>
												</select> 
											</div>
										</div>
										<div class="form-group">
											내용
											<textarea class="resize-none form-control" name="content" placeholder="근무 내용을 입력하세요." required></textarea>
										</div>
										<hr>
										<div class="row">
											<div class="col-lg-6">
												<a href="main.do" class="btn btn-secondary btn-user btn-block">목록으로</a> 
											</div>
											<div class="col-lg-6">
												<button class="btn btn-primary btn-user btn-block">초과신청</button> 
											</div>
										</div>
									</form>
									<hr>
								</div>
							</div>
							<div class="col-lg-3 d-none d-lg-block"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax_Const.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/overtime_application.js"></script>
<!-- /.container-fluid -->
<%@ include file="../include/footer.jsp"%>