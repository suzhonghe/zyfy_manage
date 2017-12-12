$(function(){

        //转账账户信息
         var mBalance = ajaxRequest({"url":"../../businessToUserTransfer"});
         $("#transferTable").tableFill({
         	"tableData": mBalance.applications,
         	"approvalUrl": "../../businessToUserTransferUmp",
       	     "cancelUrl": "../../deleteApplication"
         });

         //账户id
         //可用余额
         $("#availableAmount").html(mBalance.availableAmount);
         //账户余额
         $("#balanceAmount").html(mBalance.balanceAmount);
         //冻结余额
         $("#frozenAmount").html(mBalance.frozenAmount);
        //=====================================
        //用户查询
        $("input[name=projectUserId]").next().click(function() {
           
            $(".choseId").show();
            $(".query").click(function(){
                if(!$(".choseId input[name=loginName]").val()){
                   return; 
                }
                $.ajax({
                    type: "post",
                    url: "../../getUserTransfer",
                    data: {
                        "mobile": $(".choseId input[name=loginName]").val()
                    },
                    success: function (str) {
                        //借款人信息详情
      
                        if (str.authentication == true) {
                            str.authentication = "是";
                        } else {
                            str.authentication = "否";
                        }
                        if (str.noRepaymentaGreement == true) {
                            str.noRepaymentaGreement = "已开通";
                        } else {
                            str.noRepaymentaGreement = "未开通";
                        }
                        $("input[name=loginName]").attr("loanId", str.id);
                        $(".choseId li").eq(1).find("span").eq(1).html(str.name);
                        $(".choseId li").eq(2).find("span").eq(1).html(str.idnumber);
                        $(".choseId li").eq(3).find("span").eq(1).html(str.mobile);
                        $(".choseId li").eq(4).find("span").eq(1).html(str.authentication);
                        $(".choseId li").eq(5).find("span").eq(1).html(str.noRepaymentaGreement);

                    }
                });
            });
            $(".determine span").click(function () {
                $(".choseId").hide();
                $("input[name=projectUserId]").val($(".choseId li").eq(1).find("span").eq(1).html()).attr("userId",$("input[name=loginName]").attr("loanId"));
            });
        });
        //提交申请
        $("#subApp").click(function(){
            if($("input[name=projectUserId]").val() && $("input[name=transAmount]").val()){
            	if( parseFloat($("input[name=transAmount]").val())>parseFloat(mBalance.availableAmount) ){
                    alert("转入金额不能大于可用余额");
                    return;
                };
            	 var appJson = {
                     	"userId": $("input[name=projectUserId]").attr("userId"),
                         "userName": $("input[name=projectUserId]").val(),
                         "transAmount": $("input[name=transAmount]").val(),
                         "description": $("textarea[id=description]").val()
                     }
                     var tabData = ajaxRequest({
                         "url": "../../createApplication",
                         "data": appJson
                     });
            	 	$(".shade").show();
            	 	
            	 	if(tabData.code != 0){
	            	   $(".shade .tip p:eq(0)").html("申请失败");
            	 	}else {
	            	   $(".shade .tip p:eq(0)").html("申请成功");
            	 	}
	                $(".shade").find("a").click(function(){
	                   
	                    window.location.reload();
	                   // $("#transferTable").tableFill(tabData);
	                });

            }
        });
    });
