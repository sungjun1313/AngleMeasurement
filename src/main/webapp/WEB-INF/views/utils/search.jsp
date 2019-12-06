<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="searchForm" class="my-3" action="/angle/list" method="get">
	<div class="row justify-content-center">
	<select name="type" style="height:38px;" class="mt-2">
		<option value="" <c:out value="${ pageMaker.cri.type == null ? 'selected' : '' }"/> >--</option>
		<option value="T" <c:out value="${ pageMaker.cri.type eq 'T' ? 'selected' : '' }"/>>제목</option>
		<option value="C" <c:out value="${ pageMaker.cri.type eq 'C' ? 'selected' : '' }"/>>내용</option>
		<option value="W" <c:out value="${ pageMaker.cri.type eq 'W' ? 'selected' : '' }"/>>작성자</option>
		<option value="TC" <c:out value="${ pageMaker.cri.type eq 'TC' ? 'selected' : '' }"/>>제목 or 내용</option>
		<option value="TW" <c:out value="${ pageMaker.cri.type eq 'TW' ? 'selected' : '' }"/>>제목 or 작성자</option>
		<option value="TWC" <c:out value="${ pageMaker.cri.type eq 'TWC' ? 'selected' : '' }"/>>제목 or 내용 or 작성자</option>
	</select>
	<input type="text" name="keyword" class="mx-2 mt-2" style="height:38px;" value="<c:out value="${ pageMaker.cri.keyword }"/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="amount" value="${ pageMaker.cri.amount }" />
	<button class="btn btn-info mt-2" style="height:38px;">Search</button>
	</div>
</form>