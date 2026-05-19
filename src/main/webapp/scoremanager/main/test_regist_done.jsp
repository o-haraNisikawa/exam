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

            <!-- 完了メッセージ -->
            <div class="mx-4 mt-4 mb-4 p-3 border rounded bg-success bg-opacity-25 text-center">
                <p class="fs-5 mb-0 fw-bold">成績の登録が完了しました。</p>
            </div>

   
            <div class="row mt-5 px-4 text-center">

                <!-- 成績登録画面へ戻る -->
                <div class="col">
                    <a href="/exam/scoremanager/main/TestRegist.action"
                       class="btn btn-outline-secondary w-75">
                        成績登録画面へ戻る
                    </a>
                </div>

                <!-- 成績参照検索へ -->
                <div class="col">
                    <a href="/exam/scoremanager/main/TestList.action"
                       class="btn btn-outline-secondary w-75">
                        成績参照
                    </a>
                </div>

            </div>

        </section>

    </c:param>
</c:import>

