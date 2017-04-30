<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="statics/sweetAlert/css/sweetalert2.min.css" rel="stylesheet">
<script src="statics/sweetAlert/js/sweetalert2.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>云记类别</title>

    
	

  </head>
  
  <body>
    <div class="data_list">
    
    
        <div class="data_list_title">
            <span class="glyphicon glyphicon-list"></span>&nbsp;用户列表 <span
                class="noteType_add">
                <button class="btn btn-sm btn-success" type="button" id="addBtn">新增投票</button>
            </span>
        </div>
        <div>
        <c:choose>
           <c:when test="${empty pageInfo.currentDatas}">
             <h3>暂无用户记录 <h3>
           </c:when>
           <c:otherwise>
            <table class="table table-hover table-striped ">
                    <tr>
                        <th>编号</th>
                        <th>用户名称</th>
                        <th>用户类型</th>
                        <th>权限次数</th>
                        <th>投票间隔时间</th>
                        <th>操作</th>
                        
                    </tr>
                    
                    <c:forEach items="${pageInfo.currentDatas }" var="user">
                     <tr id="tr${user.id}">
                        <td>${user.id }</td>
                        <td>${user.name }</td>
                       <td> <c:choose> 
                        	<c:when test="${user.manId }">管理员</c:when>
                        	<c:otherwise>普通用户</c:otherwise>
                        </c:choose> </td>
                        <td>${user.addRight }</td>
                        <td>${user.voteTime }</td>
                        <td>
                            <button class="btn btn-primary update"  type="button">修改间隔时间</button>&nbsp;
                            <button class="btn btn-danger del" type="button" >设为管理员</button>
                        </td>
                    </tr>
                    </c:forEach>
            </table>
           </c:otherwise>
           
        </c:choose>
        <nav style="text-align: center">
			<ul class="pagination">
			   <c:if test="${pageInfo.hasPrePage}">
			    <li><a href="user?act=queryUserByParams&pageNum=${pageInfo.prePage}&typeName=${typeName}&time=${time}&key=${key}">&laquo;</a></li>
			   </c:if>
			    <c:forEach var="p" begin="${pageInfo.startNavPage }" end="${pageInfo.endNavPage}">
			     <li><a href="user?act=queryUserByParams&pageNum=${p}&typeName=${typeName}&time=${time}&key=${key}">${p}</a></li>
			    </c:forEach>
			   <c:if test="${pageInfo.hasNextPage }">
			     <li><a href="user?act=queryUserByParams&pageNum=${pageInfo.nextPage}&typeName=${typeName}&time=${time}&key=${key}">&raquo;</a></li>
			   </c:if>
			</ul>
			</nav>
        </div>
    </div>
    
    
    <!-- 添加类型模态框 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
    aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="id" id="id" /> <label for="typename">间隔时间</label>
                    <input type="text" name="typeName" class="form-control"
                        id="typeName" placeholder="两次投票间隔时间">
                </div>
                <div>
                    <span style="color:red" id="msg"></span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>关闭
                </button>
                <button type="button" id="btn_submit" class="btn btn-primary">
                    保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 添加类型模态框 -->
    <div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
    aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">新增</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="id" id="id2" />
                     <label for="typename">频道</label>
                    <select id="channelss" >
                    	<%-- <c:forEach items="${ channels}" var="channel">
                    		<option value="${channel.channelId }">${channel.channelName } </option>
                    	
                    	</c:forEach> --%>
                    </select></br>
                        
                         <label for="typename">投票标题</label>
                    <input type="text" name="voteName" class="form-control"
                        id="voteName" placeholder="投票标题">
                         <label for="typename">投票到期时间</label>
                    <input type="text" name="endTime" class="form-control"
                        id="endTime" placeholder="投票到期时间" onClick="WdatePicker()">
                        <!-- <input id="d11" type="text" onClick="WdatePicker()"/> -->
                         <label for="typename">投票选项</label>
                    <input type="text" name="voteOption01" class="form-control"
                        id="voteOption01" placeholder="投票选项">
                         <label for="typename">投票选项</label>
                    <input type="text" name="voteOption02" class="form-control"
                        id="voteOption02" placeholder="投票选项">
                        <label for="typename">投票选项</label>
                    <input type="text" name="voteOption03" class="form-control"
                        id="voteOption03" placeholder="投票选项">
                </div>
                <div>
                    <span style="color:red" id="msg"></span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>关闭
                </button>
                <button type="button" id="btn_submit2" class="btn btn-primary">
                    保存</button>
            </div>
        </div>
    </div>
</div>




 
    
    <script type="text/javascript">
      
    
    $(function(){
    		$("#myModal").modal("hide");   			    	
	    	// 清除模态框内容
	    	$("#msg").html("");
	    	$("#typeName").val("");
	    	$("#id").val("");//清空id 值
	    	
	    	$("#myModal2").modal("hide");   			    	
		    	// 清除模态框内容
		    	$("#msg").html("");
		    	$("input").val("");
		    	$("#id2").val("");//清空id 值
    		
    	   //swal("Here's a message!");
    	   
    	   $("#addBtn").click(function(){
    		  /*  $("#myModalLabel").html("新增");
           	$("#btn_submit").html("保存"); */
    		   $("#myModal2").modal("show");
    		   $("#id2").val("");
    		   $("#typeName2").val("");
    		   $.ajax({
   				type:"post",
   				url:"main",
   				data:"act=queryChannelInfo",
   				dataType:"json",
   				success:function(data){
   				 for (var int = 0; int < data.length; int++) {
   					 /* console.log(int); */
   					 $("#channelss").append(
   						"<option name='channelId' value='"+data[int].channelId+"'>"+data[int].channelName+"</option>"
   					 );
   				 };
   				}
    		   })
    	   }) 
    	   
    	  
    	   
    	   
    	   
    	  
    	$("#btn_submit").click(function(){
    		var typeName=$("#typeName").val();
    		var id=$("#id").val();//更新时值存在
    		$.ajax({
				type:"post",
				url:"manager",
				data:"act=setUserVoteTime&voteTime="+typeName+"&id="+id,
				dataType:"json",
				success:function(data){
					if(data.resultCode==200){
						swal("创建投票", "执行成功!", "success");
						window.location.href="user?act=queryUserByParams&id="+id;
					}else{
						swal("创建投票", data.msg, "error");
					}
					$("#myModal").modal("hide");   			    	
 			    	// 清除模态框内容
 			    	$("#msg").html("");
 			    	$("#typeName").val("");
 			    	$("#id").val("");//清空id 值
     		   		}	
    		})
    	})
    	
    	$("#btn_submit2").click(function(){
    		var channelId=$("#channelss").children('option:selected').val(); 
    		var voteName=$("#voteName").val();
    		var endTime=$("#endTime").val();
    		var voteOption01=$("#voteOption01").val();
    		var voteOption02=$("#voteOption02").val();
    		var voteOption03=$("#voteOption03").val();
    		$.ajax({
				type:"post",
				url:"vote",
				data:"act=addVoteResult&voteName="+voteName+"&endTime="+endTime+"&voteOption01="+voteOption01+"&voteOption02="+voteOption02+"&voteOption03="+voteOption03+"&channelId="+channelId,
				dataType:"json",
				success:function(data){
					if(data.resultCode==200){
						swal("设置间隔时间", "执行成功!", "success");
						window.location.href="main?act=queryVoteByParams&voteName="+voteName;
					}else{
						swal("设置间隔时间", data.msg, "error");
					}
					$("#myModal2").modal("hide");   			    	
 			    	// 清除模态框内容
 			    	/* $("#msg").html("");
 			    	$("input").val("");
 			    	$("#id2").val(""); *///清空id 值
     		   		}	
    		})
    	})
    	
    	
    	// 页面加载完毕 绑定更新事件
    	attachUpdate();
    	   // 绑定删除事件
    	   attachDel();
    	
       })
       
       
      $("#typeName").focus(function(){
    	  $("#msg").html("");
		   $("#btn_submit").attr("disabled",false);
    	  
      })
       
       
       
       // 执行唯一性校验
       function checkNoteType(type){
    	   // ajax 请求后台
    	   $.ajax({
    		   type:"post",
    		   url:"noteType",
    		   data:"act=checkNoteType&typeName="+type,
    		   dataType:"json",
    		   success:function(data){
    			   // 成功  按钮可用  置空错误信息  失败  提示错误信息 按钮不可用
    			   if(data.resultCode==300){
    				   $("#msg").html(data.msg);
    				   $("#btn_submit").attr("disabled",true);
    			   }else{
    				   $("#msg").html("");
    				   $("#btn_submit").attr("disabled",false);
    			   }
    		   }
    	   }) 
       }
    
       
       
       function addTr(id,typeName){
    	   /**
    	   
    	      <tr>
                        <td>${noteType.id }</td>
                        <td>${noteType.typeName }</td>
                        <td>
                            <button class="btn btn-primary" type="button">修改</button>&nbsp;
                            <button class="btn btn-danger del" type="button">删除</button>
                        </td>
                </tr>
    	   **/
    	   var table=$(".table.table-hover.table-striped");//获取父节点
    	   //alert(table.length);
    	   var tr="<tr id='tr"+id+"'>";//子节点
    	   tr=tr+"<td >"+id+"</td>";
    	   tr=tr+"<td>"+typeName+"</td>";
    	   tr=tr+"<td><button class='btn btn-primary update' type='button'>修改</button>&nbsp;";
    	   tr=tr+"<button class='btn btn-danger del' type='button'>删除</button></td>";
    	   if(table.length==0){
    		   /**
    		      <table class="table table-hover table-striped ">
                    <tr>
                        <th>编号</th>
                        <th>类型</th>
                        <th>操作</th>
                    </tr>
                    </table>
    		   **/
    		   var tab="<table class='table table-hover table-striped '>";
    		   tab=tab+"<tr> <th>编号</th><th>类型</th><th>操作</th></tr>";
    		   tab=tab+tr+"</table>";
    		  var div= $("h3").parent();
    		  $("h3").remove();
    		  div.append(tab);
    	   }else{
    		   table.append(tr);
    	   }
    	   
    	   
    	  /*  
    	   table.append(tr);  */
    	   // 绑定更新事件
    	   attachUpdate();
    	   
    	   // 
    	   attachDel();
    	   
       }
       
       
       
       
       function attachUpdate(){
    	// 修改点击事件	
           $(".btn.btn-primary.update").click(function(){
           	//alert("11");
           	var tr= $(this).parent().parent();
           	var id=tr.children("td").eq(0).text();
           	var typeName=tr.children("td").eq(1).text();
           	
           	// 填充模态框内容
           	$("#id").val(id);
           	$("#typeName").val();
           	
           	// 
           	$("#myModalLabel").html("设置两次投票间隔时间");
           	$("#btn_submit").html("更新");
           	// 打开模态框
           	$("#myModal").modal("show");
           
           })	   
       }
       
       
       function attachDel(){
    	   $(".btn.btn-danger.del").click(function(){
    		// 获取删除记录id
    		   var tr= $(this).parent().parent();
				var id= tr.children("td").eq(0).text();
    		   swal({
    			   title: "设置管理员",
    			   text: "该用户将添加为管理员",
    			   type: "warning",
    			   showCancelButton: true,
    			   confirmButtonColor: "#DD6B55",
    			   confirmButtonText: "确认",
    			   cancelButtonText: "取消",
    			   closeOnConfirm: false,
    			   closeOnCancel: false
    			 }).then(function(isConfirm){
    				 if(isConfirm){
    					// 执行ajax 请求 删除记录
    					$.ajax({
    						type:"post",
    						url:"user",
    						data:"act=setAdmin&id="+id,
    						dataType:"json",
    						success:function(data){
    							if(data.resultCode==200){
    								swal("设置管理员", "执行成功!", "success");
    								window.location.href="user?act=queryUserByParams&id="+id;
    								
    								
    							}else{
    								swal("设置管理员", data.msg, "error");
    							}
    						}
    					}) 
    				 } 
    			 })  
    	   })   
       }
    
    </script>
    
    
    
    
    
    
  </body>
</html>
