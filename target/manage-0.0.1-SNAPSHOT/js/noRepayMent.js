$(function(){function a(){if(i=null,i=paging({url:"../../repaymentQuery",data:{pageNo:e,pageSize:t,params:r}}),i.results){if($("#noRepayTab").tableFill({tableData:i.results,repayUrl:"../../repayInAdvance"}),$("#duePage").html(""),0==i.totalPage)return;page({id:"duePage",nowNum:i.pageNo,allNum:i.totalPage,callBack:function(a){e=a,t=t,l=$("#dataStar").val()||l,n=$("#dataEnd").val()||n,r.startTime=l,r.endTime=n,r.name=$(".queryName").val(),i=null,i=paging({url:"../../repaymentQuery",data:{pageNo:e,pageSize:t,params:r}}),i.results&&$("#noRepayTab").tableFill({tableData:i.results,repayUrl:"../../repayInAdvance"}),$(".repayBtn").html("提前还款")}}),$(".repayBtn").html("提前还款")}}var e=1,t=10,l=new Date((new Date).getTime()+864e5).toLocaleDateString(),n=new Date("2038/01/01").toLocaleDateString(),r={startTime:l,endTime:n,name:"",duedate:"",status:"UNDUE"},i=new Object;a(e,t,r),$(".repayBtn").html("提前还款"),$("#searchTime").click(function(){var e=new Date(l).getTime();if(""!=$("#dataStar").val()){var t=new Date($("#dataStar").val()).getTime();if(e>t)return void alert("尚未到期还款查询的开始时间不能小于明天");r.startTime=$("#dataStar").val()}else r.startTime=l;if(""!=$("#dataEnd").val()&&""!=$("#dataStar").val()){var t=new Date($("#dataStar").val()).getTime(),i=new Date($("#dataEnd").val()).getTime();if(t>i)return void alert("结束时间不能小于开始时间");r.endTime=$("#dataEnd").val()}else if(""!=$("#dataEnd").val()&&""==$("#dataStar").val()){var i=new Date($("#dataEnd").val()).getTime();if(e>i)return void alert("结束时间不能小于明天");r.endTime=$("#dataEnd").val()}else r.endTime=n;r.name=$(".queryName").val(),a(),$(".repayBtn").html("提前还款")}),$("#export").click(function(){window.open("../../uploadRepayQueryExcel?startTime="+$("#dataStar").val()+"&endTime="+$("#dataEnd").val()+"&name="+$(".queryName").val()+"&status=UNDUE","_blank")})});