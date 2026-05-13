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
		    <input type="hidden" name="Cd" value="${subject.getCd()}">
    		<div class="mx-3 my-4">
    		<p>
    		「${subject.getName()}(${subject.getCd()}」
    		</p>
				<div class="mb-3 container">
					<div class="row">

						<div class="col d-flex justify-content-end">
							<a type="submit" href="SubjectDeleteExecute.action?cd=${subject.getCd()}" class="btn btn-danger" role="button" style="max-width:60px">
								削除
							</a>
						</div>
						
					</div>
				</div>
				<div><a href="SubjectList.action">戻る</a></div>
			</div>
			</form>
			
		</section>
	</c:param>
</c:import>