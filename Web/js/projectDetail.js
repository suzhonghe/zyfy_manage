function getProjectDetailsData(t){$.ajax({type:"post",dataType:"json",data:{projectId:id,status:t?t:""},url:"../../queryProjectDetailById",success:function(t){$(".bidDetailPicList").html(""),$(".bidDetailRepayment tbody").html(""),$(".bidTotal span").html(t.totalAmount);var e,a,o;if(e=0==t.projectDetail.project.groupId?"全部":"新用户",a=t.projectDetail.project.whetherTransfer?"可转":"不可转","MonthlyInterest"==t.projectDetail.project.method?o="按月付息到期还本":"EqualInstallment"==t.projectDetail.project.method?o="按月等额本息":"EqualPrincipal"==t.projectDetail.project.method?o="按月等额本金":"BulletRepayment"==t.projectDetail.project.method?o="一次性还本付息":"EqualInterest"==t.projectDetail.project.method&&(o="月平息"),$(".bidDetailHead .title").html(t.projectDetail.project.title),$(".bidDetailHead .loanId").html(t.projectDetail.project.id),$(".bidDetailHead .productType").html(t.projectDetail.project.productName),$(".bidDataList .amount111").text(t.projectDetail.project.amount),$(".bidDataList .loanUserId").text(t.projectDetail.project.loanUserId),$(".bidDataList .guaranteeRealm").text(t.projectDetail.project.guaranteeRealm),$(".bidDataList .months").text(t.projectDetail.project.months),$(".bidDataList .rate").text(t.projectDetail.project.rate+"%"),$(".bidDataList .method").text(o),$(".bidDataList .groupId").text(e),$(".bidDataList .whetherTransfer").text(a),$(".bidDataList .mortgagedType").text(t.projectDetail.project.mortgagedType),$(".bidDataList .minAmount").text(t.projectDetail.project.minAmount),$(".bidDataList .maxAmount").text(t.projectDetail.project.maxAmount),$(".bidDataList .stepAmount").text(t.projectDetail.project.stepAmount),$(".bidDataList .loanGuaranteeFee").text(t.projectDetail.project.loanGuaranteeFee+"%"),$(".bidDataList .loanServiceFee").text(t.projectDetail.project.loanServiceFee+"%"),$(".bidDataList .loanRiskFee").text(t.projectDetail.project.loanRiskFee+"%"),$(".bidDataList .LoanManageFee").text(t.projectDetail.project.loanManageFee+"%"),$(".bidDataList .loanInterestFee").text(t.projectDetail.project.loanInterestFee+"%"),$(".bidDataList .investInterestFee").text(t.projectDetail.project.investInterestFee+"%"),$(".firmInfo span").html(t.projectDetail.project.firmInfo),$(".operationRange span").html(t.projectDetail.project.operationRange),$(".description span").html(t.projectDetail.project.description),$(".repaySource span").html(t.projectDetail.project.repaySource),$(".riskInfo span").html(t.projectDetail.project.riskInfo),t.projectDetail.legalPersonPhotoUrl)for(var r=t.projectDetail.legalPersonPhotoUrl.split(","),i=0;i<r.length;i++){var l=$("<img style='width: 200px;' src="+r[i]+" />");l.appendTo($(".bidDetailPicList"))}if(t.projectDetail.enterpriseInfoPhotoUrl)for(var p=t.projectDetail.enterpriseInfoPhotoUrl.split(","),i=0;i<p.length;i++){var l=$("<img style='width: 200px;' src="+p[i]+" />");l.appendTo($(".bidDetailPicList"))}if(t.projectDetail.assetsPhotoUrl)for(var c=t.projectDetail.assetsPhotoUrl.split(","),i=0;i<c.length;i++){var l=$("<img style='width: 200px;' src="+c[i]+" />");l.appendTo($(".bidDetailPicList"))}if(t.projectDetail.contractPhotoUrl)for(var n=t.projectDetail.contractPhotoUrl.split(","),i=0;i<n.length;i++){var l=$("<img style='width: 200px;' src="+n[i]+" />");l.appendTo($(".bidDetailPicList"))}if(t.projectDetail.othersPhotoUrl)for(var s=t.projectDetail.othersPhotoUrl.split(","),i=0;i<s.length;i++){var l=$("<img style='width: 200px;' src="+s[i]+" />");l.appendTo($(".bidDetailPicList"))}if(t.loans&&t.loans.length&&0!=t.loans.length)for(var i=0;i<t.loans.length;i++){var d=$("<tr></tr>");d.html("<td>"+t.loans[i].title+"</td><td>"+t.loans[i].serial+"</td><td>"+t.loans[i].amount+"</td><td>"+t.loans[i].bidAmount+"</td><td>"+t.loans[i].months+"</td><td>"+t.loans[i].method+"</td><td>"+t.loans[i].status+"</td><td>"+(t.loans[i].timeOpen?new Date(parseInt(t.loans[i].timeOpen,10)).toLocaleDateString():"未开标")+"</td><td>"+(t.loans[i].timeSettled?new Date(parseInt(t.loans[i].timeSettled,10)).toLocaleDateString():"未结算")+"</td>"),d.appendTo($(".bidDetailRepayment tbody"))}else $(".bidDetailRepayment tbody").html("<td colspan='9'>暂无数据</td>");for(var i=0,D=t.projectDetail.productVos.length;D>i;i++)t.projectDetail.project.productId==t.projectDetail.productVos[i].id&&$(".productType").html(t.projectDetail.productVos[i].productName)}})}var id=window.location.hash.substring(1);$(".querySelector p").click(function(){return $(".querySelector ul").toggle(),$(document).click(function(){$(".querySelector ul").hide()}),!1}),$(".querySelector li").click(function(){$(".querySelector p").attr("code",$(this).attr("code")).html($(this).html()),$(".querySelector ul").hide(),getProjectDetailsData($(this).attr("code"))}),getProjectDetailsData();