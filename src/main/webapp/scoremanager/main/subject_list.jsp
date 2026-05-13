<%-- 科目一覧JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目一覧</h2>
			<div class="my-2 text-end px-4">
					<a href="SubjectCreate.action">新規登録</a>
			</div>
			<form method="get" action="SubjectList.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-lavel" for="subject-f1-select">科目番号</label>
						<select class="form-select" id="subject-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="cd" items="${subject_cd_set}">
								<%-- 現在のcdと選択されていたf1が一致した場合selectedを追記 --%>
								<option value="${cd}" <c:if test="${cd == f1}"> selected </c:if>>${cd}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
			            <label class="form-label" for="subject-f2-select">科目名</label>
				            <select class="form-select" id="subject-f2-select" name="f2">
			                <option value="0">--------</option>
			                <c:forEach var="name" items="${subject_name_set}">
			                    <option value="${name}" <c:if test="${name == f2}">selected</c:if>>
		                        ${name}
			                    </option>
			                </c:forEach>
			            </select>
			        </div>
					
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞り込み</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>			
			<c:choose>
			    <c:when test="${subjects.size() > 0}">
			        <div>検索結果: ${subjects.size()} 件</div>
			
			        <table class="table table-hover">
			            <tr>
			                <th>科目番号</th>
			                <th>科目名</th>
			                <th></th>
			                <th></th>
			            </tr>
			
			            <c:forEach var="subject" items="${subjects}">
			                <tr>
			                    <td>${subject.cd}</td>
			                    <td>${subject.name}</td>
			                    <td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td>
			                    <td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a></td>
			                    
			                </tr>
			            </c:forEach>
			        </table>
			    </c:when>
			
			    <c:otherwise>
			        <div>科目情報が存在しませんでした。</div>
			    </c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>