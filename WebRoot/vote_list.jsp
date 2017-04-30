<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    
  </head>
  
  <body>
    <div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;投票列表
		</div>
		<div class="note_datas">
			<ul>
				  <c:forEach items="${pageInfo.currentDatas }" var="vote">
				    <li>
				       <a href="user?act=showVoteResult&voteId=${vote.voteId}"> ${vote.voteName}</a>
				    </li>
				  </c:forEach>
			</ul>		
			<nav style="text-align: center">
			<ul class="pagination">
			   <c:if test="${pageInfo.hasPrePage}">
			    <li><a href="main?act=queryVoteByParams&pageNum=${pageInfo.prePage}&typeName=${typeName}&time=${time}&key=${key}">&laquo;</a></li>
			   </c:if>
			    <c:forEach var="p" begin="${pageInfo.startNavPage }" end="${pageInfo.endNavPage}">
			     <li><a href="main?act=queryVoteByParams&pageNum=${p}&typeName=${typeName}&time=${time}&key=${key}">${p}</a></li>
			    </c:forEach>
			   <c:if test="${pageInfo.hasNextPage }">
			     <li><a href="main?act=queryVoteByParams&pageNum=${pageInfo.nextPage}&typeName=${typeName}&time=${time}&key=${key}">&raquo;</a></li>
			   </c:if>
			</ul>
			</nav>
		</div>
	</div>
  </body>
</html>

