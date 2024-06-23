<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../include/navigator.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/pagination.css" rel="stylesheet">
<!DOCTYPE html>
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
						<span class="menubar">공지사항</span> 
					</h6>
				</div>
				<!-- Card Body -->
				<div class="card-body">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a href="write.do" class="mb-3 d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm small float-right">
							<i class="fas fa-fw fa-bullhorn"></i> 공지 작성
						</a>
					</sec:authorize>
					<!-- 공지사항 -->
					<div class="table-responsive">
						<table class="dataTables_wraper table text-secondary text-center px-3 py-1 border-secondary" >
							<thead>
								<tr class="shadow mb-1 text-gray">
									<th>작성자</th>
									<th>제목</th>
									<th>작성일</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="vo">
								<tr>
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
									<td><a href="view.do?noticeNo=${vo.noticeNo}">${vo.title}</a></td>
									<td class="small">${fn:substring(vo.rdate,0,10) }</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
									
						<!------- 페이징 ------->
						<div class="page_wrap">
  							<div class="page_nation">
  								<c:if test="${pagingVO.startPage > pagingVO.cntPage }">
									<a class="arrow prev" href="main.do?pnum=${pagingVO.startPage-1}"></a>
								</c:if>
								<c:forEach var="p" begin="${pagingVO.startPage }" end="${pagingVO.endPage }">
									<c:choose>
										<c:when test="${p eq pagingVO.nowPage }">
											<a class="active">${p }</a>
										</c:when>
										<c:when test="${p ne pagingVO.nowPage }">
											<a href="main.do?pnum=${p }">${p }</a>
										</c:when>
									</c:choose>
								</c:forEach>
								<c:if test="${pagingVO.endPage < pagingVO.lastPage }">
									<a class="arrow next" href="main.do?pnum=${pagingVO.endPage+1}"></a>
								</c:if>
							</div>
						</div>			
						
					</div>
					
				</div>
			</div>
		</div>

	</div>

</div>

<!-- End of Main Content -->
	
<%@ include file="../include/footer.jsp"%>