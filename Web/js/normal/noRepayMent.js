$(function(){
    //数据初始设置
    var curretPage = 1;
    var currentSize = 10;
    //var dataStar = new Date().setHours(0,0,0,0);
    var dataStar = new Date(new Date().getTime()+24*60*60*1000).toLocaleDateString();   // 当前时间的后一天
    var dataEnd = new Date("2038/01/01").toLocaleDateString();
    var times = {
        "startTime": dataStar,
        "endTime": dataEnd,
        "name":"",
        "duedate": "",
        "status": "UNDUE"
    };
    var initDataJson = new Object();
     function strToDate(str){
        if(!str){
            return;
        }
        if(str == ''){
            return 0;
        }
        var str = str;
        var year = str.substring("0","4");
        var month = parseInt(str.substring("5","7"));
        var date = parseInt(str.substring("8","10"));
        var oDate = new Date().setFullYear(year,month,date);
        return oDate;
    }
   
    tablePage(curretPage,currentSize,times);
    //数据初始化第一页10条
    //分页数据请求
    function tablePage(){
         initDataJson = null;   
         initDataJson = paging({
                        "url": "../../repaymentQuery",
                        "data": {
                        	"pageNo": curretPage,
                            "pageSize": currentSize,
                            "params": times
                        }
                    });
        if(initDataJson.results){
            $("#noRepayTab").tableFill({
                "tableData": initDataJson.results,
                "repayUrl": "../../repayInAdvance"
            });
            //分页显示
            $("#duePage").html("");
            if(initDataJson.totalPage == 0){
            	return;
            }
            page({
                "id": "duePage",
                "nowNum":initDataJson.pageNo,
                "allNum":initDataJson.totalPage,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    currentSize = currentSize;
                    dataStar = $("#dataStar").val() || dataStar;
                    dataEnd = $("#dataEnd").val() || dataEnd;
                    times.startTime = dataStar;
                    times.endTime = dataEnd;
                    times.name = $(".queryName").val();
                    initDataJson = null;

                    initDataJson = paging({
                        "url": "../../repaymentQuery",
                        "data": {
                        	"pageNo": curretPage,
                            "pageSize": currentSize,
                            "params": times
                        }
                    });
                   
                    if(initDataJson.results){
                        $("#noRepayTab").tableFill({
                            "tableData": initDataJson.results,
                            "repayUrl": "../../repayInAdvance"
                        });
                    }
                    $(".repayBtn").html("提前还款");
                }
            });
            $(".repayBtn").html("提前还款");
        }
    }
    //数据填充
    $(".repayBtn").html("提前还款");
    /*$(".function .selectBox ul li").click(function(){
        currentSize = $(this).html();
        tablePage();
    });*/
    //查询
    $("#searchTime").click(function(){
        var indexDate = new Date(dataStar).getTime();
        if( $("#dataStar").val() != "" ){
            var minDate = new Date($("#dataStar").val()).getTime();
            if(minDate<indexDate){
                alert("尚未到期还款查询的开始时间不能小于明天");
                return;
            }
            times.startTime = $("#dataStar").val();
        }else{
            times.startTime = dataStar;
        }
        if( $("#dataEnd").val() != "" && $("#dataStar").val() != "" ){
            var minDate = new Date($("#dataStar").val()).getTime();
            var maxDate = new Date($("#dataEnd").val()).getTime();
            if(maxDate<minDate){
                alert("结束时间不能小于开始时间")
                return;
            }
            times.endTime = $("#dataEnd").val();
        }else if( $("#dataEnd").val() != "" && $("#dataStar").val() == "" ){
            var maxDate = new Date($("#dataEnd").val()).getTime();
            if(maxDate<indexDate){
                alert("结束时间不能小于明天")
                return;
            }
            times.endTime = $("#dataEnd").val();
        }else{
            times.endTime = dataEnd;
        };
        times.name = $(".queryName").val();
        tablePage();
        $(".repayBtn").html("提前还款");
    });

    $("#export").click(function(){
        window.open("../../uploadRepayQueryExcel?startTime="+$("#dataStar").val()+"&endTime="+$("#dataEnd").val()+"&name="+$(".queryName").val()+"&status=UNDUE",'_blank');
    });
});