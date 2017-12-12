$(function(){
	
    function merRequest(obj,url,id,name){
        //id为获取input值对应的id,name为后台接收名字
        obj.click(function(){
            var ajaxJson = new Object();
            ajaxJson.url = url;
            ajaxJson.data = new Object();
            var oData = $("#"+id).val();
            ajaxJson.data[name] = oData;
            var tipText = ajaxRequest(ajaxJson);
           
            if(id == "merchantRecharge"){
            	var tempWindow = window.open("");
            	tempWindow.location = tipText.prepareUmp;
            }
            $(".shade").show();
            $(".shade").find("h4").html("正在"+$(this).html());
            $(".shade").find(".sucBtn").html($(this).html()+"成功");
            $(".shade").find(".questBtn").html("查看"+$(this).html()+"相关问题");
        });
    }
    
    //充值
    merRequest($("#rechargeBtn"),"../../businessRecharge","merchantRecharge","busrechargeAt")
//	$("#rechargeBtn").click(function(){
//		if(!$("#merchantRecharge").val()){
//			return;
//		}
//		$.ajax({
//	        url: "../../businessRecharge",
//	        type: "post",
//	        data: {"busrechargeAt": $("#merchantRecharge").val()},
//	        success: function(ajaxStr){
//	            if(ajaxStr){
//	            	var temp = window.open("");
//	            	temp.location = ajaxStr.prepareUmp;
//	            }else {
//	                alert("加载超时");
//	            }
//	        },
//	        error: function(){
//	            alert("加载失败");
//	        }
//	    });
//	});
    //提现
	$("#withdrawBtn").click(function(){
		
		if(!$("#merchantWithdraw").val()){
			return;
		}
		$.ajax({
	        url: "../../businessWithdraw",
	        type: "post",
	        data: {"merchantWithdraw": $("#merchantWithdraw").val()},
	        success: function(ajaxStr){
	            if(ajaxStr){
	            	alert("提现申请成功");
	            }else {
	                alert("加载超时");
	            }
	        },
	        error: function(){
	            alert("加载失败");
	        }
	    });
	});


//	//转账查询
//	$("#search").click(function(){
//		if(!$("#outAccount").val()){
//			return;
//		}
//		var msg = ajaxRequest({
//					"url": "../../getUserTransfer",
//					"data": {
//						"mobile": $("#outAccount").val()
//					}
//				});
//
//			if(msg){
//				$(".msg").find("span").eq(1).html();
//				$(".msg").find("span").eq(3).html();
//				$(".msg").find("span").eq(5).html();
//			}		
//	});
//    //转账
//    $("#transferBtn").click(function(){
//    	if(!$("#merchantTransfer").val()){
//			return;
//		}
//    	$.ajax({
//	        url: "../../businessWithdraw",
//	        type: "post",
//	        data: {"merchantTransfer": $("#merchantTransfer").val()},
//	        success: function(ajaxStr){
//	            if(ajaxStr){
//	            	alert("转账成功");
//	            }else {
//	                alert("加载超时");
//	            }
//	        },
//	        error: function(){
//	            alert("加载失败");
//	        }
//	    });
//    });
 
});

