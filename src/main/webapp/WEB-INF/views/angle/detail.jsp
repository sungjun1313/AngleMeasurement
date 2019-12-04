<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">각도 측정</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="my-4 text-center text-info">각도 측정</h3>
			
			<c:out value="${ board.bno }" />
			
			<form action="/angle/delete" method="post">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
			</form>
			
			<form action="/angle/list" method="get">
				<input type="hidden" name="pageNum" value="<c:out value='${ cri.pageNum }'/>"/>
				<input type="hidden" name="amount" value="<c:out value='${ cri.amount }'/>"/>
				<input type="hidden" name="type" value="<c:out value='${ cri.type }'/>"/>
				<input type="hidden" name="keyword" value="<c:out value='${ cri.keyword }'/>"/>
				<input type="submit" value="목록" class="btn btn-info" />
			</form>
		</div>
	</layout:put>
</layout:extends>