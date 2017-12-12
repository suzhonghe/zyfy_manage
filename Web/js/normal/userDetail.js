$(function(){
    var userId = window.location.hash.substring(1);
    //$(".selectBox")
    //初始化基本信息
    var curretPage = 1;
    var currentSize = 10;
    var totalPages = 1;
    $(".nav li").eq(0).addClass("act");
    $(".detailBox").eq(0).show();

    var userDatailData = ajaxRequest({
    	url: '../../getUserDetaile',
    	data: {
    		"userId":userId
    	}
    });
    if(userDatailData){
        $(".basic .userName").html(userDatailData.name);
        $(".basic .idNumber").html(userDatailData.idnumber);
        $(".basic .phoneNumber").html(userDatailData.mobile);
        $(".basic .email").html(userDatailData.email);
        $(".basic .loginName").html(userDatailData.loginname);
        $(".basic .bankCard").html(userDatailData.account);
        $(".basic .inviter").html(userDatailData.referralId);   
        $(".basic .lastTime").html(userDatailData.lastlogindate);
        $(".basic .regTime").html(userDatailData.registerdate);
        if(userDatailData.enterprise == false){
            userDatailData.enterprise = "否"
        }else {
            userDatailData.enterprise = "是"
        }
        $(".basic .isStaff").html(userDatailData.enterprise);
        if(userDatailData.idnumber){
            $(".basic ul li:last-child i").css({
                "marginLeft": "50px"
            });
            $(".basic ul li:last-child i").addClass("open").removeClass("noOpen").html("已开户").click(function(){
                $("userUmpNone").show();
                
                    var userIfno = ajaxRequest({
                        url: '../../getUserUmpInfo',
                        data: {
                            "userId":userId
                        }
                     });
                     if(userIfno){
                        $("userUmpNone").hide();
                        switch(parseInt(userIfno.accountState)){
                        case 1:
                            userIfno.accountState = "正常";
                            break;
                        case 2:
                            userIfno.accountState = "挂失";
                            break;
                        case 3:
                            userIfno.accountState = "冻结";
                            break;
                        case 4:
                            userIfno.accountState = "锁定";
                            break;  
                        default:
                            userIfno.accountState = "销户";
                        }
                        $(".userUmpInfo li").eq(0).find("span").eq(1).html(userIfno.custName); 
                        $(".userUmpInfo li").eq(1).find("span").eq(1).html(userIfno.contactMobile); 
                        $(".userUmpInfo li").eq(2).find("span").eq(1).html(userIfno.accountName); 
                        $(".userUmpInfo li").eq(3).find("span").eq(1).html(userIfno.accountState); 
                        $(".userUmpInfo li").eq(4).find("span").eq(1).html(userIfno.availableAmount); 
                        $(".userUmpInfo li").eq(5).find("span").eq(1).html(userIfno.cardId); 
                        $(".userUmpInfo li").eq(6).find("span").eq(1).html(userIfno.gateId); 
                        $(".userUmpInfo li").eq(7).find("span").eq(1).html(userIfno.accountId);
                    }
                
                
                $(".userUmpInfo").show();
               
                $(".userUmpInfo input[type=button]").click(function(){
                     $(".userUmpInfo").hide();
                });
            });
        }else{
            $(".basic ul li:last-child i").css({
                "marginLeft": "50px"
            });
            $(".basic ul li:last-child i").addClass("noOpen").removeClass("open").html("未开户");
        }
        
    }
    
    
    //点击切换
    $(".nav li").click(function(){
        $(".detailBox").hide();
        $(".detailBox").eq($(this).index()).show();
        $(".nav li").removeClass("act");
        $(this).addClass("act");
    });
    //资金明细
    $(".nav li").eq(1).click(function(){
    	var fundData = ajaxRequest({
        	url: '../../getUserFundAccount',
        	data: {
        		"userId":userId,
        	}
        });
        if(fundData){
            $(".frequency p span").eq(1).html(fundData.investNumber);
            $(".frequency p span").eq(3).html(fundData.loanNumber);
            $(".allAmount p span").html("￥"+fundData.totalAssets);
            $(".avaAmount").html(fundData.availableAmount);
            $(".dueInAmount").html(fundData.dueInAmount);
            $(".frozenAmount").html(fundData.frozenAmount);
            $(".dueOutAmount").html(fundData.dueOutAmount);
        }
    	
    	var fundTabData = ajaxRequest({
        	url: '../../getUserFundDetaileList',
        	data: {
        		"field": "userId",
        		"value": userId,
                "start": curretPage,
                "length": currentSize
        	}
        });
    	if(fundTabData){
            $(".amountDetail table").tableFill({
                "tableData": fundTabData
            });
            $("#amountDetailPage").html("");
            totalPages = Math.ceil(fundTabData.totalPage/currentSize) || 1;
            
            page({
                "id": "amountDetailPage",
                "nowNum":fundTabData.pageNo,
                "allNum":totalPages,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    fundTabData = null;
                     fundTabData = paging({
                        "url": "../../getUserFundDetaileList",
                        "data": {
                            "field": "userId",
                            "value": userId,
                            "start": curretPage,
                            "length": currentSize
                        }
                 });
                    
                    if(fundTabData.data){
                        $(".amountDetail table").tableFill({
                            "tableData": fundTabData
                        });
                    }     
                }
            });
        }
    	
    	
    });
    $("#searchTime").on("click",function(){
    	var amountType = $(".amountDetail .amountType p").attr("name");
    	var statusV =  $(".amountDetail .status p").attr("name");
        var dataStar = new Date($("#dataStar").val().replace(/-/g,'/')).setHours(0,0,0);
        var dataEnd =  new Date($("#dataEnd").val().replace(/-/g,'/')).setHours(23,59,59);
    	var typeAndStatusField='';
		var typeAndStatusValue =  '';
    	if(amountType == '' && statusV ==''){
    		typeAndStatusField='';
    		typeAndStatusValue =  ''
    	}else if(amountType != '' && statusV == ''){
    		typeAndStatusValue = amountType;
    		typeAndStatusField = ",type";
    	}else if(statusV !='' && amountType ==''){
    		typeAndStatusValue = statusV;
    		typeAndStatusField = ",status";
    	}else{
    		typeAndStatusValue = amountType+','+statusV;
    		typeAndStatusField = ",type,status";
    	}
    	if($("#dataStar").val() == ''){

    		dataStar = new Date(0).getTime();
            dataEnd = new Date(0).getTime();
    	}else{
    		dataStar = new Date($("#dataStar").val().replace(/-/g,'/')).setHours(0,0,0);
    		dataEnd = new Date($("#dataEnd").val().replace(/-/g,'/')).setHours(23,59,59);
    	}

    	fundTabData = null;
    	
    	fundTabData = ajaxRequest({
        	url: '../../getUserFundDetaileList',
        	data: {
        		"field": "userId"+typeAndStatusField,
        		"value": userId+','+typeAndStatusValue,
                "start": curretPage,
                "length": currentSize,
                "startTime": dataStar,
                "endTime": dataEnd
        	}
        });
    	
        if(fundTabData){
            $(".amountDetail table").tableFill({
            "tableData": fundTabData
            });
            $("#amountDetailPage").html("");
            totalPages = Math.ceil(fundTabData.totalPage/currentSize) || 1;
            page({
                    "id": "amountDetailPage",
                    "nowNum":fundTabData.pageNo,
                    "allNum":totalPages,
                    callBack:function(now,all){
                        //alert('当前页:'+ now + ',总共页:'+ all);
                        curretPage = now;
                        fundTabData = null;
                        fundTabData = ajaxRequest({
                            url: '../../getUserFundDetaileList',
                            data: {
                                "field": "userId"+typeAndStatusField,
                                "value": userId+','+typeAndStatusValue,
                                "start": curretPage,
                                "length": currentSize,
                                "startTime": dataStar,
                                "endTime": dataEnd
                            }
                        });
                        
                        if(fundTabData.data){
                            $(".amountDetail table").tableFill({
                                "tableData": fundTabData
                            });
                        }     
                    }
            });
    	}else {
    		 $(".amountDetail table tbody").html("<tr style='height:30px;'><td colspan='7'>暂无数据</td></tr>");
    	}
    	


        

    });
    //借出历史
    $(".nav li").eq(2).click(function(){
    	var lendTabData = ajaxRequest({
        	url: '../../getUserInvestList',
        	data: {
        		"field": "userId",
        		"value": userId,
                "start": curretPage,
                "length": currentSize
        	}
        });

        if(lendTabData.data){
            $(".lend table").tableFill({
                "tableData": lendTabData
            });
            $("#lendPage").html("");
            totalPages = Math.ceil(lendTabData.recordsTotal/currentSize) || 1;

            page({
                "id": "lendPage",
                "nowNum":lendTabData.pageNo,
                "allNum":totalPages,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    lendTabData = null;
                    lendTabData = paging({
                        "url": "../../getUserInvestList",
                        "data": {
                            "field": "userId",
                            "value": userId,
                            "start": curretPage,
                            "length": currentSize
                        }
                    });
                    
                    if(lendTabData.data){
                    	 $(".lend table").tableFill({
                             "tableData": lendTabData
                         });
                    }     
                }
            });
        }else{
        	 $(".lend table tbody").html("<tr style='height:30px;'><td colspan='7'>暂无数据</td></tr>");
        }
       
    });
    //借入历史
    $(".nav li").eq(3).click(function(){
    	var borrowTabData = ajaxRequest({
        	url: '../../getUserLoanList',
        	data: {
        		"field": "userId",
        		"value": userId,
                "start": curretPage,
                "length": currentSize
        	}
        });
        
    	if(borrowTabData.data){
            $(".borrow table").tableFill({
                "tableData": borrowTabData
            });
            $("#borrowPage").html("");
            totalPages = Math.ceil(borrowTabData.recordsTotal/currentSize) || 1;

            page({
                "id": "borrowPage",
                "nowNum":borrowTabData.pageNo,
                "allNum":totalPages,
                callBack:function(now,all){
                    //alert('当前页:'+ now + ',总共页:'+ all);
                    curretPage = now;
                    borrowTabData = null;
                    borrowTabData = paging({
                        "url": "../../getUserLoanList",
                        "data": {
                            "field": "userId",
                            "value": userId,
                            "start": curretPage,
                            "length": currentSize
                        }
                    });
                    if(borrowTabData.data){
                    	 $(".borrow table").tableFill({
                             "tableData": borrowTabData
                         });
                    }     
                }
            });
        }else {
        	 $(".borrow table tbody").html("<tr style='height:30px;'><td colspan='7'>暂无数据</td></tr>");
        }
    	 
    });

});