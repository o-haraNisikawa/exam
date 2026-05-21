<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
        
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<label class="w-100 mb-5">
				<p class="bg-success px-4 text-center bg-opacity-50 mb-5">登録が完了しました</p>
			</label>
			

		    <div class="row mt-5">
			
			    <div class="col-3">
			        <a href="TestRegist.action">
			            戻る
			        </a>
			    </div>
			
			    <div class="col-3">
			        <a href="TestList.action" >
			            成績参照
			        </a>
			    </div>
			
			</div>
					    
	
			
			

        </section>
    </c:param>
</c:import>
