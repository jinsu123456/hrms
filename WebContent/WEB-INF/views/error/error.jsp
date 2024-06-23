<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- 404 Error Text -->
	<div class="text-center">
		<div class="error mx-auto" data-text="error">error</div>
		<p class="lead text-gray-800 mb-5">오류가 발생했습니다.</p>
		<p class="text-gray-500 mb-0">
			<span id="sec" class="text-orange"></span>초 후 홈 화면으로 이동합니다.
		</p>
	</div>

</div>
<script>
	const path = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/error.js"></script>
<!-- /.container-fluid -->
<%@ include file="../include/footer.jsp"%>