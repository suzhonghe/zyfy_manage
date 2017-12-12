$(function(){
    //数据初始参数设置
	
    var curretPage = 1;//当前页
    var currentSize = 10;//当前条数
    var dataStar = new Date().toLocaleDateString();//起始时间
    var dataEnd = new Date().toLocaleDateString();//结束时间
   
    var times = {
        "startTime": dataStar,
        "endTime": dataEnd,
        "duedate": "",
        "status": "TODAYDUE"
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


});