$(function(){
    //数据初始参数设置
	
    var curretPage = 1;//当前页
    var currentSize = 10;//当前条数
    var dataStar = new Date("1970/01/01").toLocaleDateString();//起始时间
    var dataEnd = new Date(new Date().getTime()-24*60*60*1000).toLocaleDateString();//结束时间
   
    var times = {
        "startTime": dataStar,
        "endTime": dataEnd,
        "duedate": "",
        "status": "OVERDUE"
    };
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
   
    var initDataJson = new Object();//用于填充的数据
    requestAndFill(curretPage,currentSize,times);
    pageShow(curretPage,currentSize,times);
       function requestAndFill(curretPage,currentSize,times){
        
    	   	
            initDataJson = null;
            initDataJson = paging({
                        "url": "../../repaymentQuery",
                        "data": {
                            "pageNo": curretPage,
                            "pageSize": currentSize,
                            "params": times
                        }
                    });
           
            //数据填充 
            if(initDataJson.results){
                $("#dueRepayTab").tableFill({
                    "tableData": initDataJson.results,
                    "repayUrl": "../../repay",
                    "advlUrl": "../../repayByPlatform"
                });
               
            }
       }
       function pageShow(curretPage,currentSize,times){
            $("#duePage").html("");
            //分页显示
            if(initDataJson){
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
                        currentSize = $(".selectBox p").html();
                        dataStar = $("#dataStar").val() || dataStar;
                        dataEnd = $("#dataEnd").val() || dataEnd;
                        times.startTime = dataStar;
                        times.endTime = dataEnd;
                        initDataJson = null;
                        
                        requestAndFill(curretPage,currentSize,times);
                    }
                });
            }
           
            
            
           
       }

    $(".function .selectBox ul li").click(function(){
        currentSize = $(this).html();
        
        requestAndFill(curretPage,currentSize,times);
        pageShow(curretPage,currentSize,times);
    });

    $("#searchTime").click(function(){
        var indexDate = new Date(dataEnd).getTime();
        if( $("#dataStar").val() != "" ){
            var minDate = new Date($("#dataStar").val()).getTime();
            if(minDate>indexDate){
                alert("逾期未还查询的开始时间不能大于昨天");
                return;
            }
            times.startTime = $("#dataStar").val();
        }else{
            times.startTime = dataStar;
        };
        if( $("#dataEnd").val() != "" && $("#dataStar").val() != "" ){
            var minDate = new Date($("#dataStar").val()).getTime();
            var maxDate = new Date($("#dataEnd").val()).getTime();
            if(maxDate<minDate){
                alert("结束时间不能小于开始时间")
                return;
            }else if(maxDate>indexDate){
                alert("结束时间不能大于昨天")
                return;
            }
            times.endTime = $("#dataEnd").val();
        }else if( $("#dataEnd").val() != "" && $("#dataStar").val() == "" ){
            var maxDate = new Date($("#dataEnd").val()).getTime();
            if(maxDate>indexDate){
                alert("结束时间不能大于昨天")
                return;
            }
            times.endTime = $("#dataEnd").val();
        }else{
            times.endTime = dataEnd;
        };
        currentSize = $(".selectBox p").html();
        requestAndFill(curretPage,currentSize,times);
        pageShow(curretPage,currentSize,times);
    });

});