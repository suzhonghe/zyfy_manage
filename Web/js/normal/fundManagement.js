;$.ajax({
	type:"POST",
	url:"../../getClientFundRecordInfo",
	data:{},
	success:function(str){
        if(str.availableAmount){
            $("#avaBalance").html(str.availableAmount+"元");
        }else{
            $("#avaBalance").html("0元");
        };
        if(str.balanceAmount){
            $("#accBalance").html(str.balanceAmount+"元");
        }else{
            $("#accBalance").html("0元");
        };
		if(str.frozenAmount){
            $("#froBalance").html(str.frozenAmount+"元");
        }else{
            $("#froBalance").html("0元");
        };
	}
});

$(".selectBox").click(function(){
	$(".selectBox ul").toggle();
	$(document).click(function(){
		$(".selectBox ul").hide();
	});
	return false;
});
$(".selectBox li").click(function(){
	$("#selCap").attr("name",$(this).attr("proId")).html($(this).html());
	$(".selectBox ul").hide();
	return false;
});

zycfH.listAjax({
    page:1,
    num:10,
    field:"mobile,type",
    value:"xxx,xxx",
    url:"../../getClientFundRecord",
    callBack:function(obj){
    	funCallBack(obj)
    }
});

$("#searchTime").click(function(){
    var start,end,mobile;
    if($("#dataStar").val() == ""){
    	start = new Date("1970/1/01").getTime();
    }else{
    	start = new Date($("#dataStar").val()).getTime();
    };
    if($("#dataEnd").val() == ""){
    	end = new Date("2130/1/01").getTime();
    }else{
    	end = new Date($("#dataEnd").val()).getTime();
    };
    if($("#userMobile").val() == ""){
    	mobile = "xxx";
    }else{
    	mobile = $("#userMobile").val();
    }
    if(end<start){
    	alert("结束时间必须大于开始时间")
    }else{
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"mobile,type",
            value:mobile+","+$("#selCap").attr("name"),
            sort:"desc",
            startTime:start,
            endTime:end,
            url:"../../getClientFundRecord",
            callBack:function(obj){
            	funCallBack(obj)
            }
        });
    };
});

function funCallBack(obj){     // 项目管理页的数据
	$("#movies").html("");
    $("#paging").html("");
    if(!obj.data || !obj.data.length || obj.data.length<1){
    	$("#movies").html("<td colspan='11'>暂无数据</td>");
    }else{
    	var len = obj.data.length;
        for(var i=0;i<len;i++){
        	var num = (obj.start-1)*obj.length+i+1;
            var oTr = $("<tr></tr>");
            var userName="";
            if(obj.data[i].userName){
                userName = obj.data[i].userName;
            };
            oTr.html("<td>"+num+"</td><td>"+obj.data[i].orderid+"</td><td>"+obj.data[i].account+"</td><td>"+obj.data[i].mobile+"</td><td>"+userName+"</td><td>"+obj.data[i].types+"</td><td>"+obj.data[i].operations+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].statuss+"</td><td>"+obj.data[i].timerecorded+"</td><td>"+obj.data[i].description+"</td>");
            oTr.appendTo($("#movies"));
        };
    };
};

$("#export").click(function(){
    var start,end,mobile;
    if($("#dataStar").val() == ""){
        start = new Date("1970/1/01").getTime();
    }else{
        start = new Date($("#dataStar").val()).getTime();
    };
    if($("#dataEnd").val() == ""){
        end = new Date("2130/1/01").getTime();
    }else{
        end = new Date($("#dataEnd").val()).getTime();
    };
    if($("#userMobile").val() == ""){
        mobile = "xxx";
    }else{
        mobile = $("#userMobile").val();
    };
    if(end<start){
        alert("结束时间必须大于开始时间");
        return;
    }else if( (start+24*60*60*1000*31)<=end ){
        alert("只能导出一个月数据");
        return;
    };
    window.open("../../clientFundRecordOutExcle?startTime="+start+"&endTime="+end+"&sort=desc&field=mobile,type&value="+mobile+","+$("#selCap").attr("name"));
});
