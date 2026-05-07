<%-- 学生情報登録	JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			<form method="get" action="SubjectCreateExecute.action">
				<div class="mb-3">
						<label for="input1" class="form-label">科目コード</label>
						<input class="form-control" name="no" type="text" id="input1"
						placeholder="科目コードを入力してください" value="${cd}"
						maxlength="10" required />
						<p class="mt2 text-warning">${errors.get("f1")}</p>
				</div>
				<div class="mb-3">
						<label class="form-label" for="input2">科目名</label>
						<input class="form-control" name="name" type="text" id="input2"
						placeholder="科目名を入力してください" value="${name}"
						maxlength="30" required />
				</div>
				<div class="mb-3">
					<button class="btn btn-secondary" id="filter-button">登録して終了</button>
				</div>
				<div><a href="StudentList.action">戻る</a></div>
			</form>
		</section>
	</c:param>
</c:import>