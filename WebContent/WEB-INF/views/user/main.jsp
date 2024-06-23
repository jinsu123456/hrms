<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<!-- 데이트피커 { -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<!-- } -->

<link href="${pageContext.request.contextPath}/resources/css/user.css" rel="stylesheet" type="text/css">
<style>
	.myInfoDiv{
		border:3px solid #B7CFF7;
		border-radius:10px;
	}
	.myInfoContent{
		width:100px;
		margin-left:30px;
	}
</style>
</head>

<body id="page-top">

	<!-- Begin Page Content -->
	<div class="container-fluid">
		<!-- Page Heading -->
		<h1 class="h3 mb-4 text-gray-800">사원</h1>

		<!-- DataTales Example -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<div
					class="font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline"
					id="userInfo" onclick="selectFn(this)">사원 조회</div>
				<div class="font-weight-bold text-secondary py-1 d-inline" id="myInfo"
					onclick="selectFn(this)">내 정보</div>
			</div>
			<div class="card-body">
				<div class="row userInfoHeader">
					<div class="mb-3 col align-self-center">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="regist.do" class="btn btn-primary btn-icon-split">
								<span class="text"><i class="fas fa-user-edit"></i> 사원 등록</span>
							</a>
						</sec:authorize>
						<sec:authorize access="!hasRole('ROLE_ADMIN')">
							<a href="modify.do" class="btn btn-primary btn-icon-split">
								<span class="text"><i class="fas fa-user-edit"></i> 내 정보 수정</span>
							</a>
						</sec:authorize>
							
					</div>
					<form action="main.do" name="frmSearch">
					<div class="mb-3 col-auto">
						<select class="inp" name="dept">
							<option value="all">전체</option>
							<option value="D" <c:if test="${dept eq 'D'}">selected</c:if>>개발부</option>
							<option value="P" <c:if test="${dept eq 'P'}">selected</c:if>>기획부</option>
							<option value="H" <c:if test="${dept eq 'H'}">selected</c:if>>인사부</option>
							<option value="S" <c:if test="${dept eq 'S'}">selected</c:if>>영업부</option>
							<option value="M" <c:if test="${dept eq 'M'}">selected</c:if>>관리부</option>
						</select>
						<select class="inp" name="position">
							<option value="all">전체</option>
							<option value="E" <c:if test="${position eq 'E'}">selected</c:if>>사원</option>
							<option value="L" <c:if test="${position eq 'L'}">selected</c:if>>팀장</option>
							<option value="D" <c:if test="${position eq 'D'}">selected</c:if>>부장</option>
							<option value="C" <c:if test="${position eq 'C'}">selected</c:if>>대표</option>
							<option value="A" <c:if test="${position eq 'A'}">selected</c:if>>관리자</option>
						</select> <input type="text" name="searchName" value="${searchName}" id="search" class="inp"
							placeholder="이름을 입력하세요.">
						<div class="d-inline px-2 py-2 bg-secondary" onclick="javascript:frmSearch.submit()"
							style="cursor: pointer; border-radius: 5px;">
							<i class="fas fa-lg fa-search text-white"></i>
						</div>
					</div>
					</form>
				</div>

				<div class="table-responsive">
					<table class="table table-bordered text-center userInfo"
						id="dataTable" width="100%" cellspacing="0">
						<thead>
							<tr>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<th width="20.1%">이름</th>
									<th width="20%">연락처</th>
									<th width="20%">이메일</th>
									<th width="10.3%">쪽지</th>
									<th width="10.3%">조회</th>
									<th width="10.3%">수정</th>
								</sec:authorize>
								<sec:authorize access="!hasRole('ROLE_ADMIN')">
									<th width="25%">이름</th>
									<th width="25%">연락처</th>
									<th width="25%">이메일</th>
									<th width="12.5%">쪽지</th>
									<th width="12.5%">조회</th>
								</sec:authorize>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="vo">
								<tr>
									<td>
										<div
											class="d-inline card text-primary text-center px-1 mr-1 border-primary font-weight-bold">${vo.deptCase}</div>
										<span class="text-dark font-weight-bold">${vo.name}</span> <span
										class="text-xs font-weight-bold">${vo.positionCase}</span>
									</td>
									<td>
										<div class="text-dark">${vo.phoneNumber}</div> 
									</td>
									<td>
										<div class="text-dark">${vo.email}</div>
									</td>
									<td>
										<form action="${pageContext.request.contextPath}/message/reply.do" method="post">
											<input type="hidden" name="sendUserId" value="${vo.userid}">
										<div class="text-dark"><a href="#" onclick="messageFn(this,${vo.userid})"><i class="fas fa-envelope fa-lg"></i></a></div> 
										</form>
									</td>
									<td>
										<div class="text-dark"><a href="#" data-toggle="modal" data-target="#modal${vo.userid}"><i class="fas fa-address-card fa-lg" style="color:#36b9cc"></i></a></div> 
									</td>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
									<td>
										<form name="frm" action="modifyAdmin.do" method="post">
											<input type="hidden" name="userid" value="${vo.userid}">
											<div class="text-dark"><a onclick="adminModifyFn(this)" href="#"><i class="fas fa-user-cog" style="color:red"></i></a></div>
										</form>
									</td>
									</sec:authorize>
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="myInfo d-none">
					<div class="row">
						<div class="col-lg-2"></div>
						<div class="col-lg-8 text-center my-5">
							<div class="row m-3 p-4 myInfoDiv">
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">사원번호</span>
									</div>
									<span class="text-dark">${loginUser.userid}</span>
								</div>
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">이름</span>
									</div>
									<span class="text-dark">${loginUser.name}</span>
								</div>
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">부서</span>
									</div>
									<span class="text-dark">${loginUser.deptCase}</span>
								</div>
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">직급</span>
									</div>
									<span class="text-dark">${loginUser.positionCase}</span>
								</div>
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">입사일</span>
									</div>
									<span class="text-dark">${loginUser.joinDate}</span>
								</div>
								<div class="col-6 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">상태</span>
									</div>
									<c:choose>
										<c:when test="${loginUser.state eq 1}">
											<span class="text-dark">재직</span>
										</c:when>
										<c:when test="${loginUser.state eq 0}">
											<span class="text-dark">퇴사</span>
										</c:when>
									</c:choose>
								</div>
								<div class="col-12 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">연락처</span>
									</div>
									<span class="text-dark">${loginUser.phoneNumber}</span>
								</div>
								<div class="col-12 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">이메일</span>
									</div>
									<span class="text-dark">${loginUser.email}</span>
								</div>
								<div class="col-12 p-4 text-left row">
									<div class="myInfoContent">
										<span class="font-weight-bold text-primary">주소</span>
									</div>
									<span class="text-dark">${loginUser.addr}</span>
								</div>
							</div>
							<button class="btn btn-primary shadow-sm mt-3" onclick="location.href='modify.do'">정보 수정</button>
						</div>
						<div class="col-lg-2"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<c:forEach items="${list}" var="vo">
		<div class="modal fade" id="modal${vo.userid}" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header bg-primary text-white">
						<h5 class="modal-title" id="exampleModalLabel">사원 정보</h5>
						<button class="close" type="button" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true" class="text-white">x</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary align-self-center">이름</div>
									<div class="col-9 text-dark align-self-center">${vo.name}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">부서</div>
									<div class="col-9 text-dark">${vo.deptCase}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">직급</div>
									<div class="col-9 text-dark">${vo.positionCase}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">상태</div>
									<c:choose>
										<c:when test="${vo.state eq 1}">
											<div class="col-9 text-dark">재직</div>
										</c:when>
										<c:when test="${vo.state eq 2}">
											<div class="col-9 text-dark">휴직</div>
										</c:when>
										<c:when test="${vo.state eq 9}">
											<div class="col-9 text-dark">퇴직</div>
										</c:when>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">입사일</div>
									<div class="col-9 text-dark">${vo.joinDate}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">연락처</div>
									<div class="col-9 text-dark">${vo.phoneNumber}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">이메일</div>
									<div class="col-9 text-dark">${vo.email}</div>
								</div>
							</div>
						</div>
						<div class="row m-2">
							<div class="col-12 card bg-gray-100 p-3 text-dark font-weight-bold userInfoContent text-left">
								<div class="row">
									<div class="col-3 font-weight-bold text-primary">주소</div>
									<div class="col-9 text-dark">${vo.addr}</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	
	<!-- 캘린더 옵션 { -->
	<script src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/user_main.js"></script>
	
	<script>
	$(document).ready(function(){
		let selected = `${selected}`;
		if(selected == 'myInfo'){
			selectFn(document.getElementById('myInfo'));
		}
	})
	
	function messageFn(obj, userid){
		if(userid == ${loginUser.userid}){
			alert("자신에게는 쪽지를 보낼 수 없습니다.");
		}else{
			if(confirm("해당 사원에게 쪽지를 작성 하시겠습니까?")){
				$(obj).closest('form').submit();
			}
		}	
	}
	
	function adminModifyFn(obj){
		$(obj).closest('form').submit();
	}
	</script>
	
	<%@ include file="../include/footer.jsp"%>