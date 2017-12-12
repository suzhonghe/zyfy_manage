$(function(){
    //数据初始设置
    var curretPage = 1;
    var currentSize = 10;

    var dataStar = new Date("1970/01/01").toLocaleDateString();//起始时间
    var dataEnd = new Date("2038/01/01").toLocaleDateString();


    var times = {
        "startTime": dataStar,
        "endTime": dataEnd,
        "duedate": "",
        "status": "REPAYED','PREPAY"
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
        $("#duePage").html("");
        if(initDataJson.results){
            $("#paidTab").tableFill({
                "tableData": initDataJson.results
            });
            //分页显示
            if(initDataJson.totalPage == 0){
            	return;
            };
            page({
                "id": "duePage",
                "nowNum":initDataJson.pageNo,
                "allNum":initDataJson.totalPage,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    currentSize = $(".selectBox p").html();
                    dataStar = $("#dataStar").val() || dataStar;
                    dataEnd = $("#dataEnd").val() || dataEnd;
                    times.startTime = dataStar;
                    times.endTime = dataEnd;
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
                        $("#paidTab").tableFill({
                            "tableData": initDataJson.results
                        });
                    }
                }
            });
        }
    }
    //数据填充

    $(".function .selectBox ul li").click(function(){
        currentSize = $(this).html();
        tablePage();
    });
    //查询
    $("#searchTime").click(function(){
    	if( $("#dataStar").val() != "" ){
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
            times.endTime = $("#dataEnd").val();
        }else{
            times.endTime = dataEnd;
        };
        tablePage();
    });
});