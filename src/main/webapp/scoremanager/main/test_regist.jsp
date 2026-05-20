<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績管理
            </h2>

            <!-- ▼ 検索条件 ▼ -->
            <form method="get" action="/exam/scoremanager/main/TestRegist.action">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded">

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
                            <c:forEach var="c" items="${class_num_set}">
                                <option value="${c}" <c:if test="${c == f2}">selected</c:if>>
                                    ${c}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 科目 -->
                    <div class="col-3">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="s" items="${subject_set}">
                                <option value="${s.cd}" <c:if test="${s.cd == f3}">selected</c:if>>
                                    ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 回数 -->
                    <div class="col-2">
                        <label class="form-label">回数</label>
                        <select class="form-select" name="f4">
                            <option value="0">--------</option>
                            <c:forEach var="n" items="${num_set}">
                                <option value="${n}" <c:if test="${n == f4}">selected</c:if>>
                                    ${n}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 検索ボタン -->
                    <div class="col-3 mt-4 text-center">
                        <button class="btn btn-secondary w-75">検索</button>
                    </div>

                </div>
            </form>

            <!-- ▼ 学生情報なしエラー -->
            <c:if test="${error_student_notfound}">
                <div class="text-warning mt-2 ms-4" style="font-size: 0.9rem;">
                    学生情報が存在しません。
                </div>
            </c:if>

            <!-- ▼ 検索結果（学生一覧） ▼ -->
            <c:if test="${not empty student_list}">
                <!-- ▼ 科目名と回数の表示 -->
                <div class="px-4 mb-2 fw-bold">科目：${subject_name}（${f4}回目）</div>

                <form method="post" action="/exam/scoremanager/main/TestRegist.action">

                    <input type="hidden" name="f1" value="${f1}">
                    <input type="hidden" name="f2" value="${f2}">
                    <input type="hidden" name="f3" value="${f3}">
                    <input type="hidden" name="f4" value="${f4}">
                    <input type="hidden" name="btn" value="save">

                    <table class="table table-hover mt-3">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="st" items="${student_list}" varStatus="status">
                                <tr>
                                    <td>${st.entYear}</td>
                                    <td>${st.classNum}</td>
                                    <td>${st.no}</td>
                                    <td>${st.name}</td>

                                    <td>
                                        <input type="hidden" name="student_no" value="${st.no}">
                                        <input type="number"
                                               class="form-control"
                                               name="point"
                                               min="0" max="100"
                                               placeholder="0～100">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="text-end px-4">
                        <button class="btn btn-primary">登録して終了</button>
                    </div>
                </form>
            </c:if>

        </section>

    </c:param>
</c:import>
