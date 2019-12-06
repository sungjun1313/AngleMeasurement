<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">각도측정 리스트</layout:put>
	<layout:put block="content">
		<div class="container-fluid">
			<div class="row justify-content-lg-center">
				<div class="col-xl-10">
					<h3 class="my-4 text-center text-info">각도 측정 게시판</h3>
				
					<table class="table table-sm table-hover text-center" style="table-layout:fixed;">
						<thead>
							<tr class="table-primary">
								<th style="width:70px;">No.</th>
								<th>제목</th>
								<th style="width:120px;">작성자</th>
								<th style="width:250px;" class="d-none d-md-table-cell">등록일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ list }" var="board">
								<tr class="customPointer" data-href="<c:out value='${ board.bno }'/>">
									<td><c:out value="${ board.bno }"/></td>
									<td class="text-truncate">
										<c:set var="title" value="${ board.title }"/>
										${fn:length(title) > 40 ? fn:substring(title, 0, 40) : title}${fn:length(title) > 40 ? "..." : "" }
									</td>
									<td><c:out value="${ board.writer }"/></td>
									<td class="d-none d-md-table-cell"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value='${ board.regDate }' /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- detail form -->
					<form id="detailForm" action="angle/detail" method="get">
						<input type="hidden" name="pageNum" value="<c:out value='${ pageMaker.cri.pageNum }'/>"/>
						<input type="hidden" name="amount" value="<c:out value='${ pageMaker.cri.amount }'/>"/>
						<input type="hidden" name="type" value="<c:out value='${ pageMaker.cri.type }'/>"/>
						<input type="hidden" name="keyword" value="<c:out value='${ pageMaker.cri.keyword }'/>"/>
					</form>
					
					<sec:authorize access="isAuthenticated()">
						<div class="row justify-content-end pr-3">
							<div>
								<a href="/angle/create" class="btn btn-success">Create</a>
							</div>
						</div>
					</sec:authorize>
					
					<div>
						<!--
							search 조건
							spring -> model key:pageMaker value:PageDTO
						 -->
						 <%@ include file="../utils/search.jsp" %>
					</div>
				
					<div>
						<!-- 
							pagination 조건
							spring -> model key:pageMaker value:PageDTO
							jQuery -> resources/js/pagination.js
						-->
						<%@ include file="../utils/pagination.jsp" %>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal 추가 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">알림창</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">처리가 완료되었습니다.</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		
	</layout:put>
	<layout:put block="js">
		<script src="/resources/js/pagination.js"></script>
		<script>
			$(document).ready(function(){
				var result = '<c:out value="${ result }" />';
				
				checkModal(result);
				
				history.replaceState({}, null, null);
				
				function checkModal(result){
					if(result === '' || history.state){
						return;
					}
					
					if(result != null){
						$(".modal-body").html(result);
					}
					
					$("#myModal").modal("show");
				}
				
				detailForm = $("#detailForm");
				
				$("tr").click(function(e){
					e.preventDefault();
					var href = $(this).data("href");
					if(href){
						var url = "/angle/detail/" + href;
						detailForm.attr("action", url);
						detailForm.submit();
						//window.location = '/board/get?bno='+href;
					}
				});
			});
		</script>
	</layout:put>
</layout:extends>