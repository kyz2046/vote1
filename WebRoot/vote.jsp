<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="js/echarts.common.min.js"></script>
<link href="statics/sweetAlert/css/sweetalert2.min.css" rel="stylesheet">
<script src="statics/sweetAlert/js/sweetalert2.min.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
     <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 900px;height:600px;"></div>
    
    
     <button class="btn btn-primary update" id="addBtn"  type="button">添加选项</button>&nbsp;&nbsp;&nbsp;&nbsp;
     <button class="btn btn-primary update" id="delBtn"  type="button">删除投票</button>&nbsp;
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
                    <input type="hidden" name="id" id="id" /> <label for="typename">新增选项名称</label>
                    <input type="text" name="typeName" class="form-control"
                        id="typeName" placeholder="选项名称">
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
     
    <script type="text/javascript">
    $(function(){
    	$("#delBtn").click(function(){
   		  if(${userInfo.result.manId}==1){
    		$.ajax({
     		   type:"post",
      		   url:"vote",
      		   data:"act=delectVoteResult&voteId="+${voteResultInfo.vote.voteId},
      		   dataType:"json",
      		   success:function(data){
      			 if(data.resultCode==200){
						swal("删除投票", "执行成功!", "success");
						window.location.href="user?act=queryUserByParams";
					}else{
						swal("删除投票", data.msg, "error");
					}
      		   }
    		})}else{
    			swal("管理操作", "您不是管理员，不能操作", "error");
    		}
  		   
  	   }) ;
 	   
 	   //swal("Here's a message!");
 	   
 	   $("#addBtn").click(function(){
 		  
 		   $("#myModal").modal("show");
 		  
 		   
 	   }) ;
 	   $("#btn_submit").click(function(){
    		var typeName=$("#typeName").val();
    		var id=${voteResultInfo.vote.voteId};//更新时值存在
    		$.ajax({
    		   type:"post",
     		   url:"voteOption",
     		   data:"act=saveOrUpdateVoteOption&voteOptionName="+typeName+"&voteId="+${voteResultInfo.vote.voteId},
     		   dataType:"json",
     		   success:function(data){
     			 	 if(data.resultCode==200){
     			 		swal("添加操作",data.msg,"success");
						window.location.href="user?act=showVoteResult&voteId="+${voteResultInfo.vote.voteId};
					 }else{
						 swal("添加操作",data.msg,"error");
					 }
     			   
     		   }	
    		})
    	})
    	
    });
    
    
    
    var myChart = echarts.init(document.getElementById('main'));
    
    var dataAxis = ${categories};
    var data = ${values};
    var yMax = ${yMax};
    var dataShadow = [];

    for (var i = 0; i < data.length; i++) {
        dataShadow.push(yMax);
    }

    option = {
        title: {
            text: '${voteResultInfo.vote.voteName}',
            subtext: ''
        },
        xAxis: {
            data: dataAxis,
            axisLabel: {
                inside: true,
                textStyle: {
                    color: '#040308'
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            { // For shadow
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.05)'}
                },
                barGap:'-100%',
                barCategoryGap:'40%',
                data: dataShadow,
                animation: false
            },
            {
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#83bff6'},
                                {offset: 0.5, color: '#188df0'},
                                {offset: 1, color: '#188df0'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
                data: data
            }
        ]
    };

    // Enable data zoom when user click bar.
    var zoomSize = 6;
    myChart.on('click', function (params) {
    	console.log(params.name);
    	$.ajax({
    		type:"post",
    		url:"user",
    		data:"act=userVote&voteOptionName="+params.name,
    		dataType:"json",
    		success:function(data){
    			if(data.resultCode==200){
    				swal("投票操作",data.msg,"success");
    				window.location.href="user?act=showVoteResult&voteId="+${voteResultInfo.vote.voteId};
    			}else{
    				 swal("投票操作",data.msg,"error");
    			}
    		}
    	})
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
        myChart.dispatchAction({
            type: 'dataZoom',
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
            endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        });
    });
    myChart.setOption(option);
    </script>
  </body>
</html>
