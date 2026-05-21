<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績参照
            </h2>

            <!-- ▼ 科目検索フォーム -->
            <form method="get" action="/exam/scoremanager/main/TestList.action">
                <div class="row border mx-3 mb-3 py-3 rounded">

                    <div class="col-12 mb-3">
                        <h5 class="fw-bold">科目情報</h5>
                    </div>

                    <!-- 入学年度 -->
                    <div class="col-2">
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="y" items="${ent_year_set}">
                                <option value="${y}" <c:if test="${y == f1}">selected</c:if>>
                                    ${y}
                                </option>
                            </c:forEach>
                        </select>
								<c:if test="${error_subject and btn == '31'}">
								    <span class="text-warning ms-2 d-inline-block" 
								          style="font-size: 0.9rem; white-space: nowrap;">
								        入学年度とクラスと科目を選択してください
								    </span>
								</c:if>
								
	                    </div>

                    <!-- クラス -->
                    <div class="col-2">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="cnum" items="${class_num_set}">
                                <option value="${cnum}" <c:if test="${cnum == f2}">selected</c:if>>
                                    ${cnum}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 科目 -->
                    <div class="col-4">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="sub" items="${subject_set}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>
                                    ${sub.cd}：${sub.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 科目検索ボタン -->
                    <div class="col-3 mt-4 text-center">
                        <button class="btn btn-secondary w-75"
                                name="btn"
                                value="31">
                            検索
                        </button>
                    </div>

                </div>
            </form>

            <!-- ▼ 学生検索フォーム -->
            <form method="get" action="/exam/scoremanager/main/TestList.action">
                <div class="row border mx-3 mb-3 py-3 rounded">

                    <div class="col-12 mb-2">
                        <h5 class="fw-bold">学生情報</h5>
                    </div>

                    <!-- 学生番号 -->
                    <div class="col-4">
                        <label class="form-label">学生番号</label>
                      	<input type="text"
						       class="form-control"
						       name="student_no"
						       value="${student_no}"
						       placeholder="学生番号を入力してください"
						       required>
                      	
                      	
                    </div>

                    <!-- 学生検索ボタン -->
                    <div class="col-3 mt-4 text-center">
                        <button class="btn btn-secondary w-75"
                                name="btn"
                                value="32">
                            検索
                        </button>
                    </div>

                    <c:if test="${error_student}">
                        <div class="text-warning mt-2" style="font-size: 0.9rem;">
                            学生番号を入力してください
                        </div>
                    </c:if>

                </div>
            </form>

            <div class="col-12 mb-3 text-primary" style="font-size: 0.9rem;">
                科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
            </div>

        </section>

    </c:param>
</c:import>
