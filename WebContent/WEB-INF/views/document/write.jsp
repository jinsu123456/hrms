<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<head>
	<link href="${pageContext.request.contextPath}/resources/css/fileUpload.css" rel="stylesheet">
</head>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Content Row -->
	<div class="row">

		<!-- Border Bottom Utilities -->
		<div class="col-lg-12">

			<div class="card mb-4 py-3 border-bottom-primary">
				<div class="card-body">
					<b>기안 작성</b>
				</div>
			</div>
			<div class="card o-hidden border-0 shadow-lg my-5">
				<div class="card-body p-0">
					<!-- Nested Row within Card Body -->
					<div class="row">
						<div class="col-lg-3 d-none d-lg-block"></div>
						<div class="col-lg-6">
							<div class="p-5">
								<form class="user" id="form" method="post" onsubmit="return false;" enctype="multipart/form-data">
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
										제목 <input name="title" class="resize-none form-control" required></input>
									</div>
									<div class="form-group">
										내용 <textarea name="content" class="resize-none form-control" required></textarea>
									</div>
									<div class="form-group insert">
										첨부파일 
										<input type="file" onchange="addFile(this);" multiple />
										<div class="file-list"></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-lg-6">
											<a href="main.do" class="btn btn-secondary btn-user btn-block">목록으로</a> 
										</div>
										<div class="col-lg-6">
											<button type="button" onclick="submitForm()"class="btn btn-primary btn-user btn-block">작성완료</button> 
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
<!-- /.container-fluid -->
<script src="${pageContext.request.contextPath }/resources/js/doc_write.js"></script>
<%@ include file="../include/footer.jsp"%>