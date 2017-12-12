$(function(){

	function fundType(cont){
		if(cont == "INVEST"){
			cont = "投标";
		}
		if(cont == "WITHDRAW"){
			cont = "取现";
		}
		if(cont == "DEPOSIT"){
			cont = "充值";
		}
		if(cont == "LOAN"){
			cont = "放款";
		}
		if(cont == "LOAN_REPAY"){
			cont = "贷款还款";
		}
		if(cont == "LOAN_AMOUNT_REPAYTOB"){
			cont = "标的转到平台";
		}
		if(cont == "FAILED_LOAN_REPAY"){
			cont = "流标返款";
		}
		if(cont == "DISBURSE"){
			cont = "垫付还款";
		}
		if(cont == "INVEST_REPAY"){
			cont = "投资还款";
		}
		if(cont == "CREDIT_ASSIGN"){
			cont = "债权转让";
		}
    	if(cont == "TRANSFER"){
			cont = "转账扣款";
		}
		if(cont == "TRANSFERIN"){
			cont = "平台转入";
		}
		if(cont == "OFFLINE_DEPOSIT"){
			cont = "线下充值";
		}
		if(cont == "REWARD_REGISTER"){
			cont = "注册奖励";
		}
    	if(cont == "REWARD_REFERRAL"){
			cont = "推荐奖励";
		}
		if(cont == "REWARD_INVEST"){
			cont = "投标奖励";
		}
		if(cont == "REWARD_DEPOSIT"){
			cont = "充值奖励";
		}
		if(cont == "COUPON_CASH"){
			cont = "现金券";
		}
		if(cont == "COUPON_INTEREST"){
			cont = "加息券";
		}
		if(cont == "COUPON_PRINCIPAL"){
			cont = "增值券";
		}
		if(cont == "COUPON_REBATE"){
			cont = "返现券";
		}
    	if(cont == "INTEREST_BEARING_INCOME"){
			cont = "余额生息收益";
		}
    	if(cont == "FEE_WITHDRAW"){
			cont = "提现手续费";
		}
		if(cont == "FEE_AUTHENTICATE"){
			cont = "身份验证手续费";
		}
		if(cont == "FEE_INVEST_INTEREST"){
			cont = "投资管理费";
		}
		if(cont == "FEE_LOAN_SERVICE"){
			cont = "借款服务费";
		}
		if(cont == "FEE_LOAN_MANAGE"){
			cont = "借款管理费";
		}
		if(cont == "FEE_LOAN_INTEREST"){
			cont = "还款管理费";
		}
		if(cont == "FEE_LOAN_VISIT"){
			cont = "实地考察费";
		}
		if(cont == "FEE_LOAN_GUARANTEE"){
			cont = "担保费";
		}
    	if(cont == "FEE_LOAN_RISK"){
			cont = "风险管理费";
		}
		if(cont == "FEE_LOAN_OVERDUE"){
			cont = "逾期管理费";
		}
		if(cont == "FEE_LOAN_PENALTY"){
			cont = "逾期罚息(给商户)";
		}
		if(cont == "FEE_LOAN_PENALTY_INVEST"){
			cont = "逾期罚息(给投资人)";
		}
		if(cont == "FEE_DEPOSIT"){
			cont = "充值手续费";
		}
    	if(cont == "FEE_ADVANCE_REPAY"){
			cont = "提前还款违约金(给商户)";
		}
		if(cont == "FEE_ADVANCE_REPAY_INVEST"){
			cont = "提前还款违约金(给投资人)";
		}
		if(cont == "FEE_CREDIT_ASSIGN"){
			cont = "债权转让手续费";
		}
    	if(cont == "FEE_BIND_CARD"){
			cont = "用户绑卡手续费";
		}
		if(cont == "FEE_WEALTHPRODUCT_MANAGE"){
			cont = "产品管理费";
		}
		if(cont == "FEE_WEALTHPRODUCT_CUSTODY"){
			cont = "产品托管费";
		}
		alert(cont);
	}


	function fundStatus(cont){
		if(cont == "INITIALIZED"){
			cont = "初始";
		}
		if(cont == "PROCESSING"){
			cont = "处理中";
		}
		if(cont == "AUDITING"){
			cont = "审核中";
		}
		if(cont == "PAY_PENDING"){
			cont = "支付结果待查";
		}
		if(cont == "CUT_PENDING"){
			cont = "代扣结果待查";
		}
		if(cont == "FAILED"){
			cont = "成功";
		}
		if(cont == "PAY_PENDING"){
			cont = "失败";
		}
		if(cont == "REJECTED"){
			cont = "拒绝";
		}
		if(cont == "CANCELED"){
			cont = "取消";
		}
				
	}

	var tableDatas = ajaxRequest({
		"url": "../../getClientFundRecord"
	});
	//初始化账户数据
	$("#avaBalance").html(tableDatas.availableAmount);
	$("#accBalance").html(tableDatas.balanceAmount);
	$("#froBalance").html(tableDatas.frozenAmount);
	
	if(tableDatas.applications){
		for(var i=0;i<tableDatas.applications.length;i++){
			tableDatas.applications[i].type = fundType(tableDatas.applications[i].type);
			tableDatas.applications[i].status = fundStatus(tableDatas.applications[i].status);
		}
		
		console.log(tableDatas);
		$("#platTab").tableFill({
			"tableData": tableDatas.applications
		});
		$("div.holder").jPages({
			containerID : "movies",
			previous : "上一页",
			next : "下一页",
			perPage : 10,
			delay : 20
		});
	}
	
	//查询按钮
    $(".selectBox").selectBox();
	$("#searchTime").click(function(){
		$("#selAcc").attr("name");
		//资金类型
		$("#selCap").attr("name");
		//时间段
		var startTime = parseInt($("#dataStar").val().replace(/-/g,""));
		var endTime = parseInt($("#dataEnd").val().replace(/-/g,""));
		
		var tab = null;
		//全不填
		if($("#userMobile").val() == "" && $("#dataStar").val() == "" && $("#dataEnd").val() == "" && $("#selCap").attr("name") == "all"){
			return;
		}
		
		if($("#userMobile").val() && $("#selCap").attr("name") == "all" && $("#dataStar").val() == "" && $("#dataEnd").val() == ""){
			//只填手机号
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"mobile": $("#userMobile").val()
			 	}
			});
		}else if($("#selCap").attr("name") != "all" && $("#userMobile").val() == "" && $("#dataStar").val() == "" && $("#dataEnd").val() == ""){
			//只填资金类型
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"type": $("#selCap").attr("name")
			 	}
			});
		}else if($("#dataStar").val() && $("#dataEnd").val() && $("#selCap").attr("name") == "all" && $("#userMobile").val() == ""){
			//只填时间段
			if(endTime < startTime){
				alert("结束时间不能小于开始时间");
				return;
			}
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"startTime": new Date($("#dataStar").val()),
			 		"endTime": new Date($("#dataEnd").val())
			 	}
			});
		}else if($("#userMobile").val() && $("#selCap").attr("name") != "all" && $("#dataStar").val() == "" && $("#dataEnd").val() == ""){
			//没填时间段
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"mobile": $("#userMobile").val(),
			 		"type": $("#selCap").attr("name")
			 	}
			});
		}else if($("#selCap").attr("name") == "all" && $("#userMobile").val() && $("#dataStar").val() && $("#dataEnd").val()){
			//没填资金类型
			if(endTime < startTime){
				alert("结束时间不能小于开始时间");
				return;
			}
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"mobile": $("#userMobile").val(),
			 		"startTime": new Date($("#dataStar").val()),
			 		"endTime": new Date($("#dataEnd").val())
			 	}
			});
		}else if($("#dataStar").val() && $("#dataEnd").val() && $("#selCap").attr("name") != "all" && $("#userMobile").val() == ""){
			//没填手机号
			if(endTime < startTime){
				alert("结束时间不能小于开始时间");
				return;
			}
			tab = ajaxRequest({
			 	"url": "../../getClientFundRecord",
			 	"data": {
			 		"type": $("#selCap").attr("name"),
			 		"startTime": new Date($("#dataStar").val()),
			 		"endTime": new Date($("#dataEnd").val())
			 	}
			});
		}else {
			alert("填写有误，不能单独填写一个时间");
		}
		

		$("#platTab").find("tbody").html("");
		$("#platTab").tableFill({
			"tableData": tab.applications
		});
	
		$("div.holder").jPages({
			containerID : "movies",
			previous : "上一页",
			next : "下一页",
			perPage : 10,
			delay : 20
		});
	});
	

});