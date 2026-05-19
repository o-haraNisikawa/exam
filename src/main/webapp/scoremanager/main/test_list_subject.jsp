<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（科目）
            </h2>

            <!-- ▼ 成績参照検索フォーム（test_list.jsp と同じ） -->
            <form method="get" action="/exam/scoremanager/main/TestList.action">
                <div class="row border mx-3 mb-3 py-3 rounded">

                    <!-- 科目情報 -->
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
                    <div class="col-3">
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

                    <!-- 科目別検索ボタン -->
                    <div class="col-3 mt-4 text-center">
                        <button class="btn btn-secondary w-75" name="btn" value="31">
                            検索
                        </button>
                    </div>

                    <!-- 区切り線 -->
                    <div class="col-12 mt-4 mb-3 border-top"></div>

                   

                    <!-- 学生情報 -->
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
                               placeholder="学生番号を入力してください">
                    </div>

                    <!-- 学生別検索ボタン -->
                    <div class="col-3 mt-4 text-center">
                        <button class="btn btn-secondary w-75" name="btn" value="32">
                            検索
                        </button>
                    </div>

                </div>
            </form>

            <!-- ▼ 成績一覧（ここから下は元のまま） -->
            <c:choose>
                <c:when test="${not empty list}">

                    <div class="mx-4 mb-3 fw-bold">科目：${subject_name}</div>
                    

                    <table class="table table-hover mx-3">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>

                                <c:forEach var="n" items="${num_set}">
                                    <th>${n}回目</th>
                                </c:forEach>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="row" items="${list}">
                                <tr>
                                    <td>${row.entYear}</td>
                                    <td>${row.classNum}</td>
                                    <td>${row.studentNo}</td>
                                    <td>${row.studentName}</td>

                                    <c:forEach var="n" items="${num_set}">
                                        <td>${row.points[n]}</td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>

                </c:when>

                <c:otherwise>
                    <div class="mx-3 text-danger">
                        成績情報が存在しませんでした。
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="mx-3 mt-4">
                <a href="/exam/scoremanager/main/TestList.action" class="btn btn-outline-secondary">戻る</a>
            </div>

        </section>

    </c:param>
</c:import>
