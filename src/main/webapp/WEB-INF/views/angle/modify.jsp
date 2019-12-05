<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">게시글 수정</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="my-4 text-center text-info">게시글 수정</h3>
			
			<form action="/angle/modify" method="post" class="col-12 col-lg-8 mx-auto">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<input type="hidden" name="bno" value='<c:out value="${ board.bno }" />' >
				<input type="hidden" name="writer" value='<c:out value="${ board.writer }" />' >
				<input type="hidden" name="pageNum" value="<c:out value='${ cri.pageNum }' />" />
				<input type="hidden" name="amount" value="<c:out value='${ cri.amount }' />" />
				<input type="hidden" name="type" value="<c:out value='${ cri.type }' />" />
				<input type="hidden" name="keyword" value="<c:out value='${ cri.keyword }' />" />
						
				<div class="form-group">
					<label for="id_title" class="text-primary">
						제목*
					</label>
					<input type="text" name="title" id="id_title" required class="form-control" value="<c:out value='${ board.title }'/>" />
				</div>
				<div class="form-group">
					<label for="id_content" class="text-primary">
						내용*
					</label>
					<textarea name="content" id="id_content" class="form-control" required><c:out value="${ board.content }"/></textarea>
				</div>
				<div class="form-group text-center">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${ pinfo.member.userId eq board.writer }">
							<input type="submit" class="btn btn-info" value="수정" />
						</c:if>
					</sec:authorize>
				</div>
			</form>
		</div>
	</layout:put>
</layout:extends>