//下拉选项框
(function(){
    $.fn.selectBox = function(){

        $(this).click(function(e){

            $(".selectBox").find("ul").css({
                "zIndex": "99"
            }).hide();
            $(this).find("ul").css({
                "zIndex": "999"
            }).show();
            e.stopPropagation();
            $(this).find("ul li").click(function(e){
                var _html = $(this).html();
                var _name = $(this).attr("proId");
                $(this).parent().parent().find("p").html(_html).attr("name",_name);
                $(this).parent().hide();
                e.stopPropagation();
            });
        });
        $(document).click(function(){
            $(".selectBox").find("ul").hide();
        });
    };
    $(".selectBox").selectBox();
})();

//下拉选项框选项内容导入
$.fn.selectBoxCont = function(Url){
    if(!Url){
        return;
    }
    var oParent = $(this);
    $.ajax({
        type: "post",
        url: Url,
        success: function(json){
        	if(oParent.attr("class") == "clear guaranteeRealm"){
        		$.each(json,function(index,item){
                    oParent.find("ul").append($("<li></li>").html(item.shortname).attr("proId",item.userId));
                });
        	}else{
        		 $.each(json,function(index,item){
                     oParent.find("ul").append($("<li></li>").html(item.name).attr("proId",item.id));
                 });
        	}
           
        }
    })
};

//遮罩窗
//<div class="shade">
//<div class="tip">
//<a></a>
//<i></i>
//</div>
//</div>
(function(){
    $.fn.shadow = function(){
        $(this).find("a").click(function(){
            $(".shade").hide();
        });
        $(this).find("input").click(function(){
            $(".shade").hide();
        });
        $(this).find("i").click(function(){
            $(".shade").hide();
            $(".shade").find("a").unbind("click");
        });
    };
    $(".shade").shadow();
})();

//发送ajax请求，返回后台json
function ajaxRequest(ajaxJson){
    //必须给url
    var url = ajaxJson.url || "";
    var data = ajaxJson.data || null;

    var result = null;
    $.ajax({
        url: url,
        type: "post",
        data: data,
        async: false,
        success: function(ajaxStr){
            if(ajaxStr){
                if(ajaxStr.code){
                    if(ajaxStr.code == "UNKNOW_EXCEPTION" || ajaxStr.code == "UNKNOW_NAME" || ajaxStr.code == "USETTLE_FAIL" || ajaxStr.code == "NO_UUSERINVEST" || ajaxStr.code == "NO_INFORMATIONLOAN" || ajaxStr.code == "PREPARE_DEPOSIT_FAILED" || ajaxStr.code == "FILE_READ_WRITE_EXCEPTION" || ajaxStr.code == "EN_CODE_MD5_EXCEPTION" || ajaxStr.code == "SERVER_REFUSED" || ajaxStr.code == "LOGIN_ERROR" || ajaxStr.code == "USER_NOT_EXISTS" || ajaxStr.code == "USER_PASSWORD_VAILD_FAILURE" || ajaxStr.code == "NOREGISTERED_UMPACCOUNT" || ajaxStr.code == "USER_NOLOGIN" || ajaxStr.code == "LOGIN_SUCCESS" ){
                        alert(ajaxStr.message);
                        return;
                    };
                };
                result = ajaxStr;
            }else {
                result = "加载超时";
            }
        },
        //timeout: 0.1,
        error: function(){
            result = "加载失败";
        }
    });
    return result;
}
//分页请求数据
function paging(pageJson){
    var ajaxJson = new Object();
    ajaxJson.url = pageJson.url;
    ajaxJson.data = new Object();
    ajaxJson.data = pageJson.data;
    //    start:"",第几页
    //    length: "",每页条数
    //    startTime: "",开始时间
    //    endTime: ""结束时间
    return ajaxRequest(ajaxJson);
}
// 分页
// 用法：当前页className="on"
/*page({
 id:'div1',
 nowNum:1,
 allNum:10,
 callBack:function(now,all){
 alert('当前页:'+ now + ',总共页:'+ all);
 }
 });*/
function page(opt){
    if(!opt.id){return false}
    var obj = document.getElementById(opt.id);
    var nowNum = opt.nowNum || 1;
    var allNum = opt.allNum || 5;
    var callBack = opt.callBack || function(){};
    if(nowNum>=2){
        var oA = document.createElement('a');
        oA.href = '#' + (nowNum - 1);
        oA.innerHTML = '上一页';
        obj.appendChild(oA);
    }
    if(nowNum>=4 && allNum>=6){
        var oA = document.createElement('a');
        oA.href = '#1';
        oA.innerHTML = '1';
        obj.appendChild(oA);
        if(nowNum>=5){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        }
    }
    if(allNum<=5){
        for(var i=1;i<=allNum;i++){
            var oA = document.createElement('a');
            oA.href = '#' + i;
            oA.innerHTML = i;
            obj.appendChild(oA);
        }
    }else{
        for(var i=1;i<=5;i++){
            var oA = document.createElement('a');
            if(nowNum == 1 || nowNum == 2){
                oA.href = '#' + i;
                oA.innerHTML = i;
            }else if((allNum - nowNum) == 0 || (allNum - nowNum) == 1){
                oA.href = '#' + (allNum - 5 + i);
                oA.innerHTML = (allNum - 5 + i);
            }else{
                oA.href = '#' + (nowNum - 3 + i);
                oA.innerHTML = (nowNum - 3 + i);
            }
            obj.appendChild(oA);
        }
    }
    if((allNum - nowNum) >= 3 && allNum >= 6){
        if(allNum >= 7 && (allNum - nowNum) >= 4){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        }
        var oA = document.createElement('a');
        oA.href = '#' + allNum;
        oA.innerHTML = allNum;
        obj.appendChild(oA);
    }
    if((allNum - nowNum) >= 1){
        var oA = document.createElement('a');
        oA.href = '#' + (nowNum + 1);
        oA.innerHTML = '下一页';
        obj.appendChild(oA);
    }
    var aA = obj.getElementsByTagName('a');
    for(var i=0;i<aA.length;i++){
        if(aA[i].innerHTML == nowNum){
            aA[i].className = "on";
        }
        aA[i].onclick = function(){
            var nowNum = parseInt(this.getAttribute('href').substring(1));
            obj.innerHTML = '';
            page({
                id:opt.id,
                nowNum:nowNum,
                allNum:allNum,
                callBack:opt.callBack
            });
            callBack(nowNum,allNum);
            return false;
        }
    }
}
//table数据填充
/*
用法
$(obj).tableFill({
    "tableDate": "请求到的数据",
    "approvalUrl": "",(选填)
    "repayUrl": "",(选填)
    "advlUrl": ""(选填)
})
*/
$.fn.tableFill = function(ajaxJson){
    if(!ajaxJson){
        return;
    }
    var tableDatas = ajaxJson.tableData.data || ajaxJson.tableData || null;//传入的数据

    var approvalUrl = ajaxJson.approvalUrl || null;//批准接口
    var cancelUrl = ajaxJson.cancelUrl || null;//取消接口
    var repayUrl = ajaxJson.repayUrl || null;//还款接口
    var advlUrl = ajaxJson.advlUrl || null;//垫付接口
    $(this).find("tbody").html("");//先清空
    for(var i = 0; i < tableDatas.length; i++){
        var loanName = null,
            loanId = null,
            realName = null,
            amountInterest = null,
            amountPrincipal = null,
            stillAmount = null,
            maturity = null,
            periods = null,
            availableAmount = null,
            bidAmount = null,
            account = null,
            operationEmplyee = null,
            telephone = null,
            userName =null,
            tradeAmount = null,
            status = null,
            description = null,
            createTime = null,
            opreation = null,
            inviter = null,
        	loginName = null,
        	type = null,
        	regTime = null,
        	lastTime = null,
        	time = null,
        	rate = null,
        	term = null,
        	submittime = null,
        	method = null,
        	isAble = null,
            bidAcount = null,
            repayDate = null,
        	orderId = null;
            sourceId = null;
        //还款管理表格------------------------------------------------------------------    
        if(tableDatas[i].title){
            //标的名称
            loanName = tableDatas[i].title;
        }
        if(tableDatas[i].sourceId){
            //还款方式
            sourceId = tableDatas[i].sourceId;
        }
        if(tableDatas[i].repayDate){
            //还款时间
            repayDate = new Date(parseInt(tableDatas[i].repayDate)).toLocaleString().substr(0);
        }
        
        if(tableDatas[i].id){
            //id
            loanId = tableDatas[i].id;
        }
      
        if(tableDatas[i].amountTender != null){
            //标的账户
            bidAcount = tableDatas[i].amountTender;
        }
        
        if(tableDatas[i].name){
            //真实姓名
            realName = tableDatas[i].name;
        }
        if(tableDatas[i].amountInterest){
            //待还利息
            amountInterest = tableDatas[i].amountInterest
        }
        if(tableDatas[i].amountPrincipal != null){
            //待还本金
        	amountPrincipal = tableDatas[i].amountPrincipal
        }
     
        if(tableDatas[i].amountInterest){
            //待还金额
            stillAmount = Number(amountInterest) + Number(amountPrincipal);
        }
        if(tableDatas[i].duedate){
            //到期日
            maturity =  tableDatas[i].date;
        }
        if(tableDatas[i].currentperiod){
            //期数
            periods = tableDatas[i].currentperiod;
        }
        if(tableDatas[i].available_amount){
            //账户余额
            availableAmount = tableDatas[i].available_amount;
        }
        if(tableDatas[i].amount){
            //金额
            bidAmount = tableDatas[i].amount;
        }
        //资金管理表格------------------------------------------------------------------
        if(tableDatas[i].account){
            //转出账户
            account = tableDatas[i].account;
        }
        if(tableDatas[i].operationEmplyee){
            //申请员工
            operationEmplyee = tableDatas[i].operationEmplyee;
        }
        if(tableDatas[i].telephone || tableDatas[i].mobile){
            //手机号
            telephone = tableDatas[i].telephone || tableDatas[i].mobile;
        }
        if(tableDatas[i].userName){
            //用户姓名
            userName = tableDatas[i].userName;
        }else{
        	userName = '';
        }
        if(tableDatas[i].tradeAmount){
            //转账金额
            tradeAmount = tableDatas[i].tradeAmount;
        }
        if(tableDatas[i].status){
            //状态
            status = tableDatas[i].status;
        }
        if(tableDatas[i].description){
            //备注
            description = tableDatas[i].description;
        }
        if(tableDatas[i].createTime || tableDatas[i].timerecorded){
            //申请时间
            createTime = tableDatas[i].createTime || tableDatas[i].timerecorded;
        }
        if(tableDatas[i].operation){
            //操作
            opreation = tableDatas[i].operation;
            if(opreation == "OUT"){
                opreation = "转出";
            }else {
                opreation = "转入";
            }
        }
        if(tableDatas[i].type){
            //类型
            type = tableDatas[i].type;
        }
        if(tableDatas[i].orderid){
            //订单号
            orderId = tableDatas[i].orderid;
        }
        //用户列表
        if(tableDatas[i].loginname){
            //登录名
        	loginName = tableDatas[i].loginname;
        }
        if(tableDatas[i].referralId){
            //邀请人
        	inviter = tableDatas[i].referralId;
        }
        if(tableDatas[i].lastlogindate){
            //登录名
        	lastTime = tableDatas[i].lastlogindate;
        }
        if(tableDatas[i].registerdate){
            //注册时间
        	regTime = tableDatas[i].registerdate;
        }
        if(tableDatas[i].timerecorded){
            //时间
            time =  new Date(parseInt(tableDatas[i].timerecorded)).toLocaleString().substr(0);
        }
        if(tableDatas[i].rate){
            //年化率
        	rate =  tableDatas[i].rate;
        }
        if(tableDatas[i].months){
            //期限
        	term =  tableDatas[i].months;
        }
        if(tableDatas[i].months){
            //投资时间
        	submittime =  tableDatas[i].submittime;
        }
        if(tableDatas[i].method){
            //还款方式
        	method =  tableDatas[i].method;
        }
        if(tableDatas[i].enabled == false){
            //是否启用
        	isAble =  false;
        }else {
        	isAble =  true;
        }
        var tr = $("<tr></tr>");
        tr.attr("id",loanId);
        tr.attr("amountInterest",amountInterest);
        tr.attr("amountPrincipal",amountPrincipal);
        $(this).find("thead th").each(function(){
            var td = $("<td class="+$(this).attr("class")+"></td>");
            tr.append(td);
        });
        //用户列表
       
        var userHash = tr.attr("id");
        tr.children("td[class=phoneNumber]").html("<a>"+telephone+"</a>").find("a").attr("href","userDetail.html#"+userHash);
        tr.children("td[class=submittime]").html(submittime);
        tr.children("td[class=term]").html(term);
        tr.children("td[class=rate]").html(rate+"%");
        tr.children("td[class=method]").html(method);
        tr.children("td[class=time]").html(time);
        tr.children("td[class=inviter]").html("<a>"+inviter+"</a>").find("a").attr("href","userDetail.html#"+tableDatas[i].referralRealm);
        tr.children("td[class=loginName]").html(loginName);
        tr.children("td[class=regTime]").html(regTime);
        tr.children("td[class=lastTime]").html(lastTime);
        
        if(isAble == false){
        	tr.children("td[class=controls]").html("<a class='btn edit'>编辑</a><a class='btn abled'>启用</a>");
        }else {
        	tr.children("td[class=controls]").html("<a class='btn edit'>编辑</a><a class='btn disabled'>禁用</a>");
        }
      //用户编辑
        tr.find("a.edit").click(function(){
        	var editId = $(this).parents("tr").attr("id");
            $(".shadowLayer").show();
            $(".editBox").show();
            $(".editBox input").eq(0).val("");
            $(".editBox input").eq(1).val("");
            $(".editBox input").eq(2).val("");
            var thisPerson = ajaxRequest({
                "url":'../../getUserDetaile',
                data: {
                    "userId": editId
                }
            });
           
            $(".editBox input").eq(0).val(thisPerson.mobile);
            $(".editBox input").eq(1).val(thisPerson.name);
            $(".editBox input").eq(2).val(thisPerson.idnumber);
            
            $(".editBox input[type=button]").click(function(){
                alert("保存");
                var mob = $(".editBox input").eq(0).val();
                var idNum = $(".editBox input").eq(2).val();
                var useName = $(".editBox input").eq(1).val();
                var regu =/^[1][34578][0-9]{9}$/;
            	if(!regu.test(mob)){
            		alert("请输入正确的手机号码");
            		return;
            	}
                
                var newThisPerson = ajaxRequest({
                    "url":'../../updateUserInfo',
                    "data": {
                        "mobile": mob,
                        "id": editId,
                        "idnumber": idNum,
                        "name": useName
                    }
                });
                window.location.reload();
            });
            //$(this).unbind("click");
        });
        //用户禁用
        tr.find("a.disabled").click(function(){
        	var thisDisId = $(this).parents("tr").attr("id");
            $(".shadowLayer").show();
            $(".disableBox").show();
            $(".disableBox i").html($(this).parents("tr").find(".phoneNumber").html());
            $(".disableBox input[type=button]").click(function(){
               
               
                var disabDate = ajaxRequest({
                	"url": "../../updateUserStatus",
                	"data": {
                		"id": thisDisId,
                		"enabled": false
                	}
                });
               window.location.reload();
            });
            //$(this).unbind("click");
        });
      //用户启用
        tr.find("a.abled").click(function(){
        	var thisAbId = $(this).parents("tr").attr("id");
            $(".shadowLayer").show();
            $(".ableBox").show();
            $(".ableBox input[type=button]").click(function(){
               
                var abDate = ajaxRequest({
                	"url": "../../updateUserStatus",
                	"data": {
                		"id": thisAbId,
                		"enabled": true
                	}
                });
                window.location.reload();
            });
           // $(this).unbind("click");
        });
        //还款管理表格------------------------------------------------------------------
        tr.children("td[class=repayDate]").html(repayDate);
        tr.children("td[class=loanName]").html(loanName).attr("code",tableDatas[i].loanId).css("cursor","pointer").click(function(){
            $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>标的详情</span>");
            window.location.href = "../targetAdministration/bidDetail.html#"+$(this).attr("code");
        });
        tr.children("td[class=realName]").html(realName);
        tr.children("td[class=periods]").html(periods);
        tr.children("td[class=stillAmount]").html(stillAmount);
        tr.children("td[class=availableAmount]").html(availableAmount);
        tr.children("td[class=bidAcount]").html(bidAcount);
        tr.children("td[class=amount]").html(bidAmount);
        tr.children("td[class=maturity]").html(maturity);
        tr.children("td[class=repay]").html("<a class='repayBtn'>还款</a>");
        tr.children("td[class=advance]").html("<a class='advanceBtn'>垫付</a>");
        tr.children("td[class=sourceId]").html(sourceId);
        //资金管理表格------------------------------------------------------------------
        tr.children("td[class=account]").html(account);
        tr.children("td[class=operationEmplyee]").html(operationEmplyee);
        tr.children("td[class=telephone]").html(telephone);
        tr.children("td[class=userName]").html(userName);
        tr.children("td[class=tradeAmount]").html(tradeAmount);
        tr.children("td[class=status]").html(status);
        tr.children("td[class=description]").html(description);
        tr.children("td[class=createTime]").html(createTime);
        tr.children("td[class=control]").html("<a class='btn approval'>批准</a><a class='btn cancel'>取消</a>");
        tr.children("td[class=type]").html(type);
        tr.children("td[class=orderId]").html(orderId);
        tr.children("td[class=opreation]").html(opreation);
        tr.children("td[class=platAmount]").html(bidAmount);
        tr.children("td[class=SerialNo]").html(i+1);

        //还款按钮
        tr.find("a.repayBtn").click(function(){
            var sendId = $(this).parent().parent().attr("id");
            var interest = $(this).parent().parent().attr("amountInterest");
            var principal = $(this).parent().parent().attr("amountPrincipal");
            var total = Number(interest) + Number(principal);
            var currentLine = $(this).parent().parent();

            $(".shade").find("a").removeClass("advance").addClass("repay").html("确认还款").attr("repayStatus",true).click(function(){
                $(".alertBox", window.parent.document).show();
                var sendStatus = ajaxRequest({
                    "url": repayUrl,
                    "data": {"id": sendId}
                });
                $(".shade").hide();
                $(this).unbind("click");
                setTimeout(function(){
                    $(".alertBox", window.parent.document).hide();
                    window.location.reload();
                },18000);
            });
            $(".shade .tip li span").eq(1).html("");
            $(".shade .tip li span").eq(3).html("");
            $(".shade .tip li span").eq(5).html("");
            $(".shade .tip li span").eq(1).html(principal+"元");
            $(".shade .tip li span").eq(3).html(interest+"元");
            $(".shade .tip li span").eq(5).html(total+"元");
            $(".shade").show();
        });
        //垫付按钮
        tr.find("a.advanceBtn").click(function(){
            var advId = $(this).parent().parent().attr("id");
            var advInterest = $(this).parent().parent().attr("amountInterest");
            var advPrincipal = $(this).parent().parent().attr("amountPrincipal");
            var advTotal = Number(advInterest) + Number(advPrincipal);
            var advLine = $(this).parent().parent();
            $(".shade").find("a").removeClass("repay").addClass("advance").html("确认垫付").click(function(){
                $(".alertBox", window.parent.document).show();
                var advStatus = ajaxRequest({
                    "url": advlUrl,
                    "data": {"repaymentId": advId}
                });
                $(".shade").hide();
                //window.location.reload();
                //$(this).unbind("click");
                setTimeout(function(){
                    $(".alertBox", window.parent.document).hide();
                    window.location.reload();
                },8000);
            });
            $(".shade .tip li span").eq(1).html("");
            $(".shade .tip li span").eq(3).html("");
            $(".shade .tip li span").eq(5).html("");
            $(".shade .tip li span").eq(1).html(advPrincipal+"元");
            $(".shade .tip li span").eq(3).html(advInterest+"元");
            $(".shade .tip li span").eq(5).html(advTotal+"元");
            $(".shade").show();
        });
        //批准按钮
        tr.find("a.approval").click(function(){
            var sendId = $(this).parent().parent().attr("id");
            var currentLine = $(this).parent().parent();
            $(".shade").find("a").html("确定批准").click(function(){

                var sendStatus = ajaxRequest({
                    "url": approvalUrl,
                    "data": {"appIdli": sendId}
                });
                if(sendStatus.code == 1074 || sendStatus.code == 1075 || sendStatus.code == 1076){
                    alert(sendStatus.message);
                };
                window.location.reload();
            });
            $(".shade").show();
        });
        //取消按钮
        tr.find("a.cancel").click(function(){
            var sendId = $(this).parent().parent().attr("id");
            var currentLine = $(this).parent().parent();
            $(".shade").find("a").html("确定取消").click(function(){
                var sendStatus = ajaxRequest({
                    "url": cancelUrl,
                    "data": {"id": sendId}
                });
                window.location.reload();
            });
            $(".shade").show();
        });
        $(this).find("tbody").append(tr);
    }

};








