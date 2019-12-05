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
			
			<form action="/angle/create" method="post" class="col-12 col-lg-8 mx-auto">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<div class="form-group">
					<label for="id_title" class="text-primary">
						제목*
					</label>
					<input type="text" name="title" id="id_title" required class="form-control" />
				</div>
				<div class="form-group">
					<label for="id_content" class="text-primary">
						내용*
					</label>
					<textarea name="content" id="id_content" class="form-control" required></textarea>
				</div>
				<div class="form-group text-center">
					<input type="submit" class="btn btn-info" value="저장" />
				</div>
			</form>
		</div>
	</layout:put>
</layout:extends>