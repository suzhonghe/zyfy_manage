var id=window.location.hash.substring(1);
$(".querySelector p").click(function(){
    $(".querySelector ul").toggle();
    $(document).click(function(){
        $(".querySelector ul").hide();
    });
    return false;
});
$(".querySelector li").click(function(){
    $(".querySelector p").attr("code",$(this).attr("code")).html($(this).html());
    $(".querySelector ul").hide();
    getProjectDetailsData( $(this).attr("code") );
});
getProjectDetailsData();
function getProjectDetailsData(st){
  $.ajax({
    type: "post",
    dataType :"json",
    data:{"projectId":id,"status":(st?st:"")},
    url: "../../queryProjectDetailById",
    success: function(str){
            $(".bidDetailPicList").html("");
            $(".bidDetailRepayment tbody").html("");
            $(".bidTotal span").html(str.totalAmount);
            var groupId,whetherTransfer,method;
            if(str.projectDetail.project.groupId == 0){
              groupId = "全部";
            }else{
              groupId = "新用户";
            };
            if(!str.projectDetail.project.whetherTransfer){
              whetherTransfer = "不可转";
            }else{
              whetherTransfer = "可转";
            };
            if(str.projectDetail.project.method=='MonthlyInterest'){
                method = '按月付息到期还本';
            }else if(str.projectDetail.project.method=='EqualInstallment'){
                method = '按月等额本息';
            }else if(str.projectDetail.project.method=='EqualPrincipal'){
                method = '按月等额本金';
            }else if(str.projectDetail.project.method=='BulletRepayment'){
                method = '一次性还本付息';
            }else if(str.projectDetail.project.method=='EqualInterest'){
                method = '月平息';
            };
            $(".bidDetailHead .title").html(str.projectDetail.project.title);
            $(".bidDetailHead .loanId").html(str.projectDetail.project.id);
            $(".bidDetailHead .productType").html(str.projectDetail.project.productName);

            $(".bidDataList .amount111").text(str.projectDetail.project.amount);
            $(".bidDataList .loanUserId").text(str.projectDetail.project.loanUserId);
            $(".bidDataList .guaranteeRealm").text(str.projectDetail.project.guaranteeRealm);
            $(".bidDataList .months").text(str.projectDetail.project.months);
            $(".bidDataList .rate").text(str.projectDetail.project.rate+"%");
            $(".bidDataList .method").text(method);
            $(".bidDataList .groupId").text(groupId);
            $(".bidDataList .whetherTransfer").text(whetherTransfer);
            $(".bidDataList .mortgagedType").text(str.projectDetail.project.mortgagedType);
            $(".bidDataList .minAmount").text(str.projectDetail.project.minAmount);
            $(".bidDataList .maxAmount").text(str.projectDetail.project.maxAmount);
            $(".bidDataList .stepAmount").text(str.projectDetail.project.stepAmount);
            $(".bidDataList .loanGuaranteeFee").text(str.projectDetail.project.loanGuaranteeFee+"%");
            $(".bidDataList .loanServiceFee").text(str.projectDetail.project.loanServiceFee+"%");
            $(".bidDataList .loanRiskFee").text(str.projectDetail.project.loanRiskFee+"%");
            $(".bidDataList .LoanManageFee").text(str.projectDetail.project.loanManageFee+"%");
            $(".bidDataList .loanInterestFee").text(str.projectDetail.project.loanInterestFee+"%");
            $(".bidDataList .investInterestFee").text(str.projectDetail.project.investInterestFee+"%");

            $(".firmInfo span").html(str.projectDetail.project.firmInfo);
            $(".operationRange span").html(str.projectDetail.project.operationRange);
            $(".description span").html(str.projectDetail.project.description);
            $(".repaySource span").html(str.projectDetail.project.repaySource);
            $(".riskInfo span").html(str.projectDetail.project.riskInfo);

            if(str.projectDetail.legalPersonPhotoUrl){
                  var LPho = str.projectDetail.legalPersonPhotoUrl.split(",");
                  for(var i=0;i<LPho.length;i++){
                      var oImg = $("<img style='width: 200px;' src="+LPho[i]+" />");
                      oImg.appendTo($(".bidDetailPicList"));
                  };
              };
              if(str.projectDetail.enterpriseInfoPhotoUrl){
                  var EPho = str.projectDetail.enterpriseInfoPhotoUrl.split(",");
                  for(var i=0;i<EPho.length;i++){
                      var oImg = $("<img style='width: 200px;' src="+EPho[i]+" />");
                      oImg.appendTo($(".bidDetailPicList"));
                  };
              };
              if(str.projectDetail.assetsPhotoUrl){
                  var APho = str.projectDetail.assetsPhotoUrl.split(",");
                  for(var i=0;i<APho.length;i++){
                      var oImg = $("<img style='width: 200px;' src="+APho[i]+" />");
                      oImg.appendTo($(".bidDetailPicList"));
                  };
              };
              if(str.projectDetail.contractPhotoUrl){
                  var CPho = str.projectDetail.contractPhotoUrl.split(",");
                  for(var i=0;i<CPho.length;i++){
                      var oImg = $("<img style='width: 200px;' src="+CPho[i]+" />");
                      oImg.appendTo($(".bidDetailPicList"));
                  };
              };
              if(str.projectDetail.othersPhotoUrl){
                  var OPho = str.projectDetail.othersPhotoUrl.split(",");
                  for(var i=0;i<OPho.length;i++){
                      var oImg = $("<img style='width: 200px;' src="+OPho[i]+" />");
                      oImg.appendTo($(".bidDetailPicList"));
                  };
              };
              if(!str.loans || !str.loans.length || str.loans.length == 0){
                  $(".bidDetailRepayment tbody").html("<td colspan='9'>暂无数据</td>");
              }else{
                for(var i=0;i<str.loans.length;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td>"+str.loans[i].title+"</td><td>"+str.loans[i].serial+"</td><td>"+str.loans[i].amount+"</td><td>"+str.loans[i].bidAmount+"</td><td>"+str.loans[i].months+"</td><td>"+str.loans[i].method+"</td><td>"+str.loans[i].status+"</td><td>"+(str.loans[i].timeOpen?new Date(parseInt(str.loans[i].timeOpen,10)).toLocaleDateString():"未开标")+"</td><td>"+(str.loans[i].timeSettled?new Date(parseInt(str.loans[i].timeSettled,10)).toLocaleDateString():"未结算")+"</td>");
                  oTr.appendTo($(".bidDetailRepayment tbody"));
                };
              };

              for(var i=0,len=str.projectDetail.productVos.length;i<len;i++){
                  if(str.projectDetail.project.productId == str.projectDetail.productVos[i].id){
                      $(".productType").html(str.projectDetail.productVos[i].productName);
                  }
              };
    }
  })
}
