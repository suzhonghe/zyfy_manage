$(function(){function e(e,a,t){o=null,o=paging({url:"../../repaymentQuery",data:{pageNo:e,pageSize:a,params:t}}),o.results&&$("#dueRepayTab").tableFill({tableData:o.results,repayUrl:"../../repay",advlUrl:"../../repayByPlatform"})}function a(a,t,l){if($("#duePage").html(""),o){if(0==o.totalPage)return;page({id:"duePage",nowNum:o.pageNo,allNum:o.totalPage,callBack:function(n){a=n,t=$(".selectBox p").html(),o=null,e(a,t,l)}})}}var t=1,l=10,n=(new Date).toLocaleDateString(),u=(new Date).toLocaleDateString(),i={startTime:n,endTime:u,duedate:"",status:"TODAYDUE"},o=new Object;e(t,l,i),a(t,l,i),$(".function .selectBox ul li").click(function(){l=$(this).html(),e(t,l,i),a(t,l,i)})});