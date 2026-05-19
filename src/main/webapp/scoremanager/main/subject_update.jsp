<%-- 学生情報登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form method="get" action="SubjectUpdateExecute.action">
		    <input type="hidden" name="oldCd" value="${subject.getCd()}">
    		<div class="mx-3 my-4">
				
				<div class="mb-3">
					<label class="form-text-label" for="subjectCd">科目コード</label>
					<p>${subject.getCd()}</p>
					<input type="hidden" name="cd" value="${subject.getCd()}">
				</div>
				<div class="mb-3">
					<label class="form-text-label" for="subjectName">科目名</label>
					<input class="form-control" name="name" type="text" id="subjectName"
						placeholder="科目名を入力してください" value="${subject.getName()}"
						maxlength="30" required />
				<p class="mt2 text-warning">${error}</p>
				</div>
				
				
				<div class="mb-3 container">
					<div class="row">
						<input class="col btn btn-primary" type="submit" id="filter-button" value="変更" style="max-width:60px">
						<div><a href="SubjectList.action">戻る</a></div>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>