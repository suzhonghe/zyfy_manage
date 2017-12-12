$(function(){
	
	$("input#loanOut").click(function(){	
		
		var loanNum = $(this).parent().children().eq(0).val();
		var loanNotes = $(this).parent().children().eq(1).val();
		var loanAmount = $(this).parent().children().eq(2).val();
		var reDate = ajaxRequest({
			"url": "../../loanAmountTobusinessAccount",
			"data": {
				"loanId": loanNum,
				"description": loanNotes,
				"amount": loanAmount
			}
		});
		if(reDate.code == 0000){
			alert("转账成功");
		}else{
			alert("转账失败");
		}
	});
	$("input#loanIn").click(function(){		
		var loanNum = $(this).parent().children().eq(0).val();
		var loanNotes = $(this).parent().children().eq(1).val();
		var loanAmount = $(this).parent().children().eq(2).val();
		ajaxRequest({
			"url": "../../businessAmountToloanAccount",
			"data": {
				"loanId": loanNum,
				"description": loanNotes,
				"amount": loanAmount
			}
		});
		if(reDate.code == 0000){
			alert("转账成功");
		}else{
			alert("转账失败");
		}
	});
});