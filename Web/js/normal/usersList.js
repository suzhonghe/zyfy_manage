$(function(){
    var curretPage = 1;
    var currentSize = 10;
    var initDataJson = new Object();
    var totalPages = 1;
    function tablePage(){
        initDataJson = null;
        initDataJson = paging({
            "url": "../../getUserList",
            "data": {
                "start": curretPage,
                "length": currentSize,
                "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
//                "params":
            }
        });
        if(initDataJson.data){
            $("#usersTab").tableFill({
                "tableData": initDataJson.data
            });
            
            totalPages = Math.ceil(initDataJson.recordsTotal/currentSize);
            //分页显示
            $("#duePage").html("");
            page({
                "id": "duePage",
                "nowNum":initDataJson.pageNo,
                "allNum":totalPages,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    initDataJson = null;
                    initDataJson = paging({
			            "url": "../../getUserList",
			            "data": {
			                "start": curretPage,
			                "length": currentSize,
                            "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                            "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
			            }
                 });
                    
                    if(initDataJson.data){
                        $("#usersTab").tableFill({
                            "tableData": initDataJson.data
                        });
                    }     
                }
            });
        }
    }
    tablePage();

    $(".searchBox input[type=button]").on("click",function(){
        var searchCont = $(this).parent().find("#mobileSearch").val();
        var regNum = /^[1][34578][0-9]{9}$/;
        var regWord = /^[\u4e00-\u9fa5]+$/; 
        /*if(!searchCont){
            return;
        }*/
        if( ($("#dataStar").val() || $("#dataEnd").val()) && searchCont ){
            alert("不能同时查询时间或姓名或电话，一次查询一个条件");
            return;
        }
        if(regNum.test(searchCont) || searchCont == "" ){
            initDataJson = null;
            $("#duePage").html("");
            initDataJson = paging({
                "url": "../../getUserList",
                "data": {
                    "start": curretPage,
                    "length": currentSize,
                    "field": "searchCont",
                    "value": searchCont,
                    "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                    "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
                }
            });
            if(initDataJson.data){
                $("#usersTab").tableFill({
                    "tableData": initDataJson.data
                });
                    totalPages = Math.ceil(initDataJson.recordsTotal/currentSize);
                    page({
                        "id": "duePage",
                        "nowNum":initDataJson.pageNo,
                        "allNum":totalPages,
                        callBack:function(now,all){
                            //alert('当前页:'+ now + ',总共页:'+ all);
                            curretPage = now;
                            initDataJson = null;
                            initDataJson = paging({
                                "url": "../../getUserList",
                                "data": {
                                    "start": curretPage,
                                    "length": currentSize,
                                    "params": searchCont,
                                    "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                                    "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
                                }
                         });
                            
                            if(initDataJson.data){
                                $("#usersTab").tableFill({
                                    "tableData": initDataJson.data
                                });
                            }     
                        }
                    });  
            }else{
                $("#usersTab tbody").html("<td colspan='7'>暂无数据</td>");
                $("#duePage").html("");
            };
            
           
        }
        if(regWord.test(searchCont) || searchCont == "" ){
            initDataJson = null;
            $("#duePage").html("");
            initDataJson = paging({
                "url": "../../getUserList",
                "data": {
                    "start": curretPage,
                    "length": currentSize,
                    "field": "searchCont",
                    "value": searchCont,
                    "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                    "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
                }
            });
            if(initDataJson.data){
                $("#usersTab").tableFill({
                    "tableData": initDataJson.data
                });
                totalPages = Math.ceil(initDataJson.recordsTotal/currentSize);
                page({
                    "id": "duePage",
                    "nowNum":initDataJson.pageNo,
                    "allNum":totalPages,
                    callBack:function(now,all){
                        //alert('当前页:'+ now + ',总共页:'+ all);
                        curretPage = now;
                        initDataJson = null;
                        initDataJson = paging({
                            "url": "../../getUserList",
                            "data": {
                                "start": curretPage,
                                "length": currentSize,
                                "params": searchCont,
                                "startTime":$("#dataStar").val()?new Date($("#dataStar").val()).getTime():new Date("1970/1/01").getTime(),
                                "endTime":$("#dataEnd").val()?new Date($("#dataEnd").val()).getTime():new Date("2130/1/01").getTime()
                            }
                     });
                        
                        if(initDataJson.data){
                            $("#usersTab").tableFill({
                                "tableData": initDataJson.data
                            });
                        }     
                    }
                });  
            }else{
                $("#usersTab tbody").html("<td colspan='7'>暂无数据</td>");
                $("#duePage").html("");
            };
        }
    });
    $(".shadowLayer .closeBtn").click(function(){
        $(".shadowLayer").hide();
    });
});