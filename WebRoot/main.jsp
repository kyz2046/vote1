<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="statics/css/note.css" rel="stylesheet">
<link href="statics/bootstrap3/css/bootstrap.css" rel="stylesheet">
<script src="statics/js/jquery-1.11.3.js"></script>
<script src="statics/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript" src="statics/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="statics/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="js/main.js"></script>

<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="main?act=queryVoteByParams&channelId=-1" class="navbar-brand" style="font-size:25px" href="">投票管理</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="main?act=queryVoteByParams&channelId=-1"><i
						class="glyphicon glyphicon-cloud"></i>&nbsp;主&nbsp;&nbsp;页</a></li>
						
				<!-- <li><a href="note?act=toAddNotePage"><i class="glyphicon glyphicon-pencil"></i>&nbsp;发表云记</a></li> -->
					<c:choose>
						<c:when test="${userInfo.result.manId}">
							<li ><a id="adm" href="user?act=queryUserByParams"><i class="glyphicon glyphicon-list"></i>&nbsp;管理员</a></li>
						</c:when>
					
					</c:choose>
					
						
					
				
				
				
				
				
				<!-- <li><a href="user?act=userInfo"><i
						class="glyphicon glyphicon-user"></i>&nbsp;个人中心</a></li> -->
						
						
			</ul>
			<form class="navbar-form navbar-right" role="search" action="main">
				<div class="form-group">
					<input type="hidden" name="act" value="queryVoteByParams">
					 <input	type="text" name="voteName" class="form-control" placeholder="搜索投票">
				</div>
				<button type="submit" class="btn btn-default">查询</button>
			</form>
		</div>
	</div>
	</nav>
	<div class="container">
		<div class="row-fluid">
			<div class="col-md-3">
				<div class="data_list">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-user"></span>&nbsp;个人中心&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="user?act=userLoginOut">退出</a>
					</div>
					
					<%-- <div class="userimg"> 
						<img src="user?act=pic&fn=${userInfo.result.pic}">
						<img src="user?act=upImg&img=${userInfo.result.img }">
					</div>
					<div class="nick">${userInfo.result.nickName }</div>
					<div class="mood">${userInfo.result.mood }</div> --%>
					
					
				</div>
				
				<!-- <div class="data_list">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-calendar"> </span>&nbsp;云记存档
					</div>

					<div>
						<ul class="nav nav-pills nav-stacked" id="data1">
							<li value="1">NBA </li>
							<li value="2">CBA </li>	
							<li value="3">fdsfsdf </li>	
						</ul>
					</div>

				</div> -->
				
				
				<div class="data_list">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-list-alt"> </span>&nbsp;投票频道
					</div>

					<div>
						<ul class="nav nav-pills nav-stacked" id="data2" onclick="selectChannel()">
							<li  ><a href="main?act=queryVoteByParams&channelId=-1" >全部</a> </li>
							<li  ><a href="main?act=queryVoteByParams&channelId=1" >NBA</a> </li>
							<li ><a href="main?act=queryVoteByParams&channelId=2" >CBA</a> </li>	
							<li ><a href="main?act=queryVoteByParams&channelId=3" >意甲</a></li>	
							<li ><a href="main?act=queryVoteByParams&channelId=4" >英超</a> </li>
							<li ><a href="main?act=queryVoteByParams&channelId=5" >欧冠</a> </li>	
							<li ><a href="main?act=queryVoteByParams&channelId=6" >LPL</a></li>	

						</ul>
					</div>

				</div>
			</div>
		</div>
		<div class="col-md-9">
			<jsp:include page="${change}"></jsp:include>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	console.log(${voteId}+"11111111111");
	
		function selectChannel(){
			$.ajax({
				type:"post",
				url:"main",
				data:"act=queryVoteByParams&channelId="+$(this).val(),
				dataType:"json",
				success:function(data){
					
				}
				
			})
			
		}
	function checkAdmin(){
		if(${userInfo.result.manId}>0){
			console.log(${userInfo.result.manId});
		}
	}



</script>
</html>