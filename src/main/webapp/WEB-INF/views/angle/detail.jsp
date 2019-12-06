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
			
			<div>
				<div class="card">
					<div class="card-header text-center">
						<c:out value="${ board.bno }" />.
						<c:out value="${ board.title }" />
					</div>
					<div class="card-body">
						<c:out value="${ board.content }" />
					</div>
					<div class="card-footer text-right">
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ board.regDate }" />
					</div>
				</div>
				
				<div class="card my-3">
					<div class="card-header text-center">
						각도 측정
					</div>
					<div class="card-body">
						<c:forEach items="${angleList }" var="angle">
							<p>
								<c:out value="${ angle.angle }" />
							</p>
						</c:forEach>
					</div>
				</div>
				
				<div class="my-4 text-center">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${ pinfo.member.userId eq board.writer }">
							<a href="/angle/modify/<c:out value='${ board.bno }' />" id="modifyBtn" class="btn btn-primary mr-2 mt-2">Modify</a>
							<a href="/angle/measure/create/<c:out value='${ board.bno }' />" id="measureCreateBtn" class="btn btn-success mr-2 mt-2">Angle Create</a>
							<button id="deleteBtn" class="btn btn-danger mr-2 mt-2">Remove</button>
						</c:if>
					</sec:authorize>
					<a href="#" id="listBtn" class="btn btn-info mt-2">목록</a>
				</div>
			
				<form id="deleteForm" action="/angle/delete" method="post">
					<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				</form>
			
				<form id="listForm" action="/angle/list" method="get">
					<input type="hidden" name="pageNum" value="<c:out value='${ cri.pageNum }'/>"/>
					<input type="hidden" name="amount" value="<c:out value='${ cri.amount }'/>"/>
					<input type="hidden" name="type" value="<c:out value='${ cri.type }'/>"/>
					<input type="hidden" name="keyword" value="<c:out value='${ cri.keyword }'/>"/>
				</form>
			</div>
		</div>
	</layout:put>
	<layout:put block="js">
		<script>
			$(document).ready(function(){
				var listForm = $("#listForm");
				
				$("#listBtn").on("click",function(e){
					e.preventDefault();
					listForm.submit();
				});
				
				$("#modifyBtn, #measureCreateBtn").on("click", function(e){
					e.preventDefault();
					listForm.attr("action", $(this).attr("href"));
					listForm.submit();
				});
			});
		</script>
	</layout:put>
</layout:extends>