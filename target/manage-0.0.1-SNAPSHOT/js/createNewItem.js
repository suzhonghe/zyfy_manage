function callback(e,t){var a=["legalPersonPhoto","enterpriseInfoPhoto","contractPhoto","assetsPhoto","othersPhoto"],n=parseInt(t),o=$("<li></li>"),r=$("<img src='"+e+"' />"),i=$("<i>×</i>");i.appendTo(o),r.appendTo(o),o.appendTo($("#"+a[n-1]+" ul")),$("#"+a[n-1]).find("ul li i").each(function(){function e(e){e.get(0).onclick=function(){var e=this;$.ajax({type:"POST",url:"../../deletePhoto",data:{path:$(e).siblings().attr("src"),type:"upload"},success:function(t){alert(t.message),1==t.code&&$(e).parent().remove()}})}}e($(this))})}function uploadError(){alert("上传失败")}$(function(){function e(e){if(e){for(var t=document.getElementById(e),a=t.getElementsByTagName("img"),n=[],o=0;o<a.length;o++)n.push(a[o].getAttribute("src"));return n}}function t(e){$("#"+e+" input").on("change",function(){$("#"+e+" form").submit()})}$(".selectBox").selectBox(),$(".productId").selectBoxCont("../../queryProductByStatus?status=0"),$(".guaranteeRealm").selectBoxCont("../../queryAllCorporationUserByCondition?type=GUARANTEE"),$(".mortgageBox").click(function(){$("#mor").attr("checked")?($(".mortgage").show(),$(".mortgage li").click(function(){})):$(".mortgage").hide()}),$("input[name=projectLoanGuaranteeFee]").val("0"),$("input[name=projectLoanServiceFee]").val("0"),$("input[name=projectLoanRiskFee]").val("0"),$("input[name=projectLoanManageFee]").val("0"),$("input[name=projectLoanInterestFee]").val("0"),$("input[name=projectInvestInterestFee]").val("0"),$("#year").val("0"),$("#day").val("0"),$(".subAll").click(function(){var t=0,a=null;if($("#noMor").is(":checked"))t=0,a="无";else if($("#mor").is(":checked")){t=1;var n=[];$(".mortgage li").each(function(){$(this).find("input").is(":checked")&&n.push($(this).find("input").next().html())}),a=n.join(",")}var o=new Object;o.title=$("input[name=projectTitle]").val(),o.serial=$("input[name=projectSerial]").val(),o.loanUserId=$("input[name=projectUserId]").val(),o.legalPerson=$("input[name=projectLegalPerson]").val(),o.agentPerson=$("input[name=projectAgentPerson]").val(),o.guaranteeId=$(".guaranteeRealm p").attr("name"),o.guaranteeRealm=$(".guaranteeRealm p").html(),o.productId=$(".productId p").attr("name"),o.amount=$("input[name=projectAmount]").val(),o.years=$("#year").val(),o.months=$("#month").val(),o.days=$("#day").val(),o.rate=$("input[name=projectRate]").val(),o.method=$(".method p").attr("name"),o.mortgaged=t,o.mortgagedType=a,o.loanGuaranteeFee=$("input[name=projectLoanGuaranteeFee]").val(),o.loanServiceFee=$("input[name=projectLoanServiceFee]").val(),o.loanRiskFee=$("input[name=projectLoanRiskFee]").val(),o.loanManageFee=$("input[name=projectLoanManageFee]").val(),o.loanInterestFee=$("input[name=projectLoanInterestFee]").val(),o.investInterestFee=$("input[name=projectInvestInterestFee]").val(),o.firmInfo=$("textarea[name=projectFirmInfo]").val(),o.operationRange=$("textarea[name=projectOperationRange]").val(),o.description=$("textarea[name=projectDescription]").val(),o.repaySource=$("textarea[name=projectRepaySource]").val(),o.riskInfo=$("textarea[name=projectRiskInfo]").val();var r=e("legalPersonPhoto"),i=e("enterpriseInfoPhoto"),l=e("contractPhoto"),c=e("assetsPhoto"),s=e("othersPhoto"),p=new Object;return p.project=o,p.legalPersonPhoto=r,p.enterpriseInfoPhoto=i,p.contractPhoto=l,p.assetsPhoto=c,p.othersPhoto=s,alert(o.description.length),o.description.length>45?void alert("资金用途描述字数不能大于45字"):""==o.title?void alert("项目名称不能为空"):""==o.serial?void alert("项目唯一号不能为空"):3!=o.serial.split("-").length?void alert("项目唯一号格式不正确"):""==o.loanUserId?void alert("借款人ID不能为空"):""==o.legalPerson&&""==o.agentPerson?void alert("借款单位法人及代理人不能同时为空"):""==o.guaranteeRealm?void alert("关联企业不能为空"):""==$(".productId p").attr("name")||null==$(".productId p").attr("name")?void alert("产品类型不能为空"):""==o.amount?void alert("项目金额不能为空"):""==o.years||""==o.months||""==o.days?void alert("借款期限不能为空"):""==o.rate?void alert("年化利率不能为空"):""==o.method?void alert("还款方式不能为空"):1==o.mortgaged&&""==o.mortgagedType?void alert("抵押品不能为空"):void $.ajax({type:"post",dataType:"json",url:"../../projectApply",contentType:"application/json",data:JSON.stringify(p),success:function(e){1==e.code?window.location.href="projectManagement.html":alert("提交失败"+e.message)},error:function(e){alert("出现异常："+e)}})}),$(".resAll").click(function(){$(".resetBox").show(),$(".resY").click(function(){$(".resetBox").hide(),$("input").val(""),$("textarea").val("")}),$(".resN").click(function(){$(".resetBox").hide()})}),$("input[name=projectUserId]").next().click(function(){$(".choseId").show(),$(".query").click(function(){$.ajax({type:"post",url:"../../queryLender",data:{loginName:$(".choseId input[name=loginName]").val()},success:function(e){return e.message?void alert(e.message):(e.idauthenticated=1==e.idauthenticated?"是":"否",$("input[name=loginName]").attr("loanId",e.id),$(".choseId li").eq(1).find("span").eq(1).html(e.name),$(".choseId li").eq(2).find("span").eq(1).html(e.idnumber),$(".choseId li").eq(3).find("span").eq(1).html(e.mobile),void $(".choseId li").eq(4).find("span").eq(1).html(e.idauthenticated))}})}),$(".determine span").click(function(){$(".choseId").hide(),$("input[name=projectUserId]").val($("input[name=loginName]").attr("loanId"))})}),t("legalPerson"),t("enterpriseInfo"),t("contract"),t("assets"),t("others")});