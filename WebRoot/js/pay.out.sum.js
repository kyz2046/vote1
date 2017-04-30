$(function(){
	loadData("","");
})



function seachPayOutSum(){
	var startTime=$("#startTime").val();//获取开始时间
	var endTime=$("#endTime").val();
	loadData(startTime,endTime);
}




// 根据输入的时间  动态获取消费类型汇总数据
function loadData(startTime,endTime){
	//var startTime=$("#startTime").val();//获取开始时间
	//var endTime=$("#endTime").val();
	// 非空校验
	if(null==startTime||""==startTime||"undefined"==startTime){
		startTime="";
	}
	
	if(null==endTime||""==endTime||"undefined"==endTime){
		endTime="";
	}

	var data1=[];// 存放类型名称
	var data2=[];
	$.ajax({
		type:"post",
		url:"payOutSum",
		data:"startTime="+startTime+"&endTime="+endTime,
		dataType:"json",
		success:function(data){
			if(null!=data&&data.length>0){
				for(var i=0;i<data.length;i++){
					data1[i]=data[i].name;
					var s={};// 声明js 对象
					s.name=data[i].name;// 声明对象成员属性 并赋值 name value
					s.value=data[i].total;
					data2[i]=s;	
				}
							
			}else{
				alert("暂无数据!");
			}
			
			var myChart = echarts.init(document.getElementById('main'));
			option = {
					title : {
						text: '旺财个人支出类型数据汇总',
						subtext: '来至旺财',
						x:'center'
					},
					tooltip : {
						trigger: 'item',
						formatter: "{a} <br/>{b} : {c} ({d}%)"
					},
					legend: {
						orient: 'vertical',
						left: 'left',
						data: data1
					},
					series : [
					          {
					        	  name: '旺财系统',
					        	  type: 'pie',
					        	  radius : '55%',
					        	  center: ['50%', '60%'],
					        	  data:data2,
			        	          itemStyle: {
			        	        	emphasis: {
			        	        		shadowBlur: 10,
			        	        		shadowOffsetX: 0,
			        	        		shadowColor: 'rgba(0, 0, 0, 0.5)'
			        	        	}
			        	        }
					          }
					          ]
			};
			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
			
			
			
		}
	})
	
	
	
	
	
	
	
}
