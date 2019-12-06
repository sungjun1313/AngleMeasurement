<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">게시글 추가</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="my-4 text-center text-info">게시글 추가</h3>
			
			<c:forEach var="error" items="${ validation.bnoResult }">
				<p class="text-danger my-2 warning"><c:out value="${ error }" /></p>
			</c:forEach>
			
			<c:forEach var="error" items="${ validation.writerResult }">
				<p class="text-danger my-2 warning"><c:out value="${ error }" /></p>
			</c:forEach>
			
			<form id="createForm" action="/angle/create" method="post" class="col-12 col-lg-8 mx-auto">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<div class="form-group">
					<label for="id_title" class="text-primary">
						제목*
					</label>
					<input type="text" name="title" id="id_title" class="form-control" required value="<c:out value='${ prevValue.title }'/>" />
					<c:forEach var="error" items="${ validation.titleResult }">
						<p class="text-danger warning"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group">
					<label for="id_content" class="text-primary">
						내용*
					</label>
					<textarea name="content" id="id_content" class="form-control" required><c:out value="${ prevValue.content }" /></textarea>
					<c:forEach var="error" items="${ validation.contentResult }">
						<p class="text-danger warning"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group text-center">
					<input type="submit" class="btn btn-info" value="저장" />
				</div>
			</form>
		</div>
	</layout:put>
	<layout:put block="js">
		<script src="/resources/js/utils.js"></script>
		<script>
			$(document).ready(function(){
				$("#createForm").submit(function(e){
					var titleVal = $("#id_title").val();
					var contentVal = $("#id_content").val();
					
					if(!utils.isSpaceCheck(titleVal, titleVal.trim(), "제목")){
						return false;
					}
					
					if(!utils.isSpaceCheck(contentVal, contentVal.trim(), "내용")){
						return false;
					}
					
					if(!utils.isEmptyCheck(titleVal.trim(), "제목")){
						return false;
					}
					
					if(!utils.isEmptyCheck(contentVal.trim(), "내용")){
						return false;
					}
					
				});
				
				utils.reSubmitPrevent();
			});
		</script>
	</layout:put>
</layout:extends>