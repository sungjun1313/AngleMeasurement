<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav aria-label="Page navigation" class="my-4">
	<ul class="pagination justify-content-center">
	
		<!-- pc -->
		<c:choose>
			<c:when test="${ pageMaker.prev }">
				<li class="d-none d-md-block page-item pageinate_button">
					<a href="1" class="page-link">&laquo;</a>
				</li>
				<li class="d-none d-md-block page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.startPage - pageMaker.BTN_NUM }'/>" class="page-link">&lt;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="d-none d-md-block page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&laquo;</a>
				</li>
				<li class="d-none d-md-block page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&lt;</a>
				</li>
			</c:otherwise>
		</c:choose>
		
		<c:forEach var="num" begin="${ pageMaker.startPage }" end="${ pageMaker.endPage }">
			<li class="d-none d-md-block page-item pageinate_button <c:out value='${ pageMaker.cri.pageNum == num ? "active" : "" }'/>">
				<a href="<c:out value='${ num }'/>" class="page-link">
					<c:out value="${ num }" />
				</a>
			</li>
		</c:forEach>
		
		
		<c:choose>
			<c:when test="${ pageMaker.next }">
				<li class="d-none d-md-block page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.startPage + pageMaker.BTN_NUM }'/>" class="page-link">&gt;</a>
				</li>
				<li class="d-none d-md-block page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.lastPage }'/>" class="page-link">&raquo;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="d-none d-md-block page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&gt;</a>
				</li>
				<li class="d-none d-md-block page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&raquo;</a>
				</li>
			</c:otherwise>
		</c:choose>
		
		<!-- mobile -->
		<c:choose>
			<c:when test="${ pageMaker.cri.pageNum > 1 }">
				<li class="d-block d-md-none page-item pageinate_button">
					<a href="1" class="page-link">&laquo;</a>
				</li>
				<li class="d-block d-md-none page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.cri.pageNum - 1 }'/>" class="page-link">&lt;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="d-block d-md-none page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&laquo;</a>
				</li>
				<li class="d-block d-md-none page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&lt;</a>
				</li>
			</c:otherwise>
		</c:choose>
		
		<li class="d-block d-md-none page-item disabled">
			<a class="page-link" href="#" tabindex="-1" aria-disabled="true">
				<c:out value="${ pageMaker.cri.pageNum }" />
				&sol;
				<c:out value="${ pageMaker.lastPage }" />
			</a>
		</li>
		
		<c:choose>
			<c:when test="${ pageMaker.cri.pageNum < pageMaker.lastPage }">
				<li class="d-block d-md-none page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.cri.pageNum + 1 }'/>" class="page-link">&gt;</a>
				</li>
				<li class="d-block d-md-none page-item pageinate_button">
					<a href="<c:out value='${ pageMaker.lastPage }'/>" class="page-link">&raquo;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="d-block d-md-none page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&gt;</a>
				</li>
				<li class="d-block d-md-none page-item disabled">
					<a href="#" class="page-link" tabindex="-1" aria-disabled="true">&raquo;</a>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
	<!-- 클릭 시 form sumbit -->
	<form id="pagenationForm" action="/angle/list" method="get">
		<input type="hidden" name="pageNum" value="<c:out value='${ pageMaker.cri.pageNum }'/>" />
		<input type="hidden" name="amount" value="<c:out value='${ pageMaker.cri.amount }'/>" />
		<input type="hidden" name="type" value="<c:out value='${ pageMaker.cri.type }'/>" />
		<input type="hidden" name="keyword" value="<c:out value='${ pageMaker.cri.keyword }'/>" />
	</form>
</nav>