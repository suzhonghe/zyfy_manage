$(function(){
    //选项框内容初始化
    $(".selectBox").selectBox();
    $(".productId").selectBoxCont("../../queryProductByStatus?status=0");
    $(".guaranteeRealm").selectBoxCont("../../queryAllCorporationUserByCondition?type=GUARANTEE");
    //项目信息初始化
    var id = window.location.search.substring(1);
 
  
    $.ajax({
        type: "post",
        dataType :"json",
        data:{"id":id},
        url: "../../queryProjectById",
        success: function(strJson){

            console.log(strJson);
            if(strJson){
                var project = strJson.project;
                if(strJson.assetsPhotoUrl){
                    var assetsPhotoUrl = strJson.assetsPhotoUrl;
                    addImg(assetsPhotoUrl,"contractPhoto");
                }
                if(strJson.contractPhotoUrl){
                    var contractPhotoUrl = strJson.contractPhotoUrl;
                    addImg(contractPhotoUrl,"assetsPhoto");
                }
                if(strJson.enterpriseInfoPhotoUrl){
                    var enterpriseInfoPhotoUrl = strJson.enterpriseInfoPhotoUrl;
                    addImg(enterpriseInfoPhotoUrl,"enterpriseInfoPhoto");
                }
                if(strJson.legalPersonPhotoUrl){
                    var legalPersonPhotoUrl = strJson.legalPersonPhotoUrl;
                    addImg(legalPersonPhotoUrl,"legalPersonPhoto");
                }
                if(strJson.othersPhotoUrl){
                    var othersPhotoUrl = strJson.othersPhotoUrl;
                    addImg(othersPhotoUrl,"othersPhoto");
                }
                
                
                
                
                if(project.mortgaged == 0){
                    $("#noMor").attr("checked",true);
                }else {
                    $("Mor").attr("checked",true);
                    $(".mortgage").show();
                }
                //图片
                //assetsPhotoUrl
                //contractPhotoUrl
                //enterpriseInfoPhotoUrl
                //legalPersonPhotoUrl
                //othersPhotoUrl
                function addImg(imgDate,obj ){
                    var imgArr = imgDate.split(",");
                    
                    for(var j=0; j<imgArr.length; j++){
                        var img = $("<img src="+imgArr[j]+" />");
                        var li = $("<li class='left'></li>");
                        var i = $("<i>×</i>");
                        li.append(img);
                        li.append(i);
                       
                        $("#"+obj).find("ul").append(li);
                        
                    }
                    $("#"+obj).find("ul").find("i").on("click",function(){
                    	var _this = $(this);
                    	var imgSrc = $(this).parent().find('img').attr('src');
                    	$.ajax({
                    		url: '../../deletePhoto',
                    		type: 'post',
                    		data: {
                    			"path": imgSrc,"type":"upload"
                    		},
                    		success: function(msg){
                    			if(msg.code == 1){
                    				_this.parent().remove();
                    			}
                    		}
                    	});
                    });
                }
                var productName;
                for(var i=0;i<strJson.productVos.length;i++){
                	if(project.productId==strJson.productVos[i].id){
                		productName=strJson.productVos[i].productName
                	}
                }
                $("input[name=projectTitle]").val(project.title);
                $("input[name=projectSerial]").val(project.serial);
                $("input[name=projectUserId]").val(project.loanUserId);
                $(".guaranteeRealm p").html(project.guaranteeRealm);
                $(".productId p").attr("name",project.productId);
                $(".productId p").html(productName);
                $("input[name=projectLegalPerson]").val(project.legalPerson);
                $("input[name=projectAgentPerson]").val(project.agentPerson);

                $("input[name=projectAmount]").val(project.amount);
                $("#year").val(project.years);
                $("#month").val(project.months);
                $("#day").val(project.days);
                if(project.method=="MonthlyInterest"){
                    $(".method p").html("按月付息到期还本")
                }else{
                    $(".method p").html("一次性还本付息")
                };
                $("input[name=projectRate]").val(project.rate);
                $(".method p").attr("name",project.method);
                $("input[name=projectLoanGuaranteeFee]").val(project.loanGuaranteeFee);
                $("input[name=projectLoanServiceFee]").val(project.loanServiceFee);
                $("input[name=projectLoanRiskFee]").val(project.loanRiskFee);
                $("input[name=projectLoanManageFee]").val(project.loanManageFee);
                $("input[name=projectLoanInterestFee]").val(project.loanInterestFee);
                $("input[name=projectInvestInterestFee]").val(project.investInterestFee);
                $("textarea[name=projectFirmInfo]").val(project.firmInfo);
                $("textarea[name=projectOperationRange]").val(project.operationRange);
                $("textarea[name=projectDescription]").val(project.description);
                $("textarea[name=projectRepaySource]").val(project.repaySource);
                $("textarea[name=projectRiskInfo]").val(project.riskInfo);

            }
        },
        error: function(){

        }
    })
    //抵押品选项
    $(".mortgageBox").click(function(){
        if($("#mor").attr("checked")){
            $(".mortgage").show();
            $(".mortgage li").click(function(){
                //$(".mortgage li").removeClass("act");
                //$(this).addClass("act");
                //$(".mortgage").hide();
            });
        }else {
            $(".mortgage").hide();
        }
    });
    function imgSrc(id){
        if(!id){
            return;
        }
        var oBox = document.getElementById(id);
        var aImg = oBox.getElementsByTagName("img");
        var imgArr = [];
        for(var i=0;i<aImg.length;i++){
            imgArr.push(aImg[i].getAttribute("src"));
        }
        return imgArr;
    };

    $("input[name=projectLoanGuaranteeFee]").val("0");
    $("input[name=projectLoanServiceFee]").val("0");
    $("input[name=projectLoanRiskFee]").val("0");
    $("input[name=projectLoanManageFee]").val("0");
    $("input[name=projectLoanInterestFee]").val("0");
    $("input[name=projectInvestInterestFee]").val("0");

    $(".subAll").click(function(){
        //是否有抵押
        var mortgagedValue = 0;
        var mortgageGoods = null;
        if($("#noMor").is(":checked")){
            mortgagedValue = 0;
            //无抵押品
            mortgageGoods = "无";
        }else if($("#mor").is(":checked")){
            mortgagedValue = 1;
            //有抵押品
            var mortgages = [];
            $(".mortgage li").each(function(){
                if($(this).find("input").is(":checked")){
                    mortgages.push($(this).find("input").next().html());
                }
            });
            mortgageGoods = mortgages.join(",");

        }
        var project=new Object();
        project["id"] = id;
        project["title"] = $("input[name=projectTitle]").val();
        project["serial"] = $("input[name=projectSerial]").val();
        project["loanUserId"] = $("input[name=projectUserId]").val();
        project["legalPerson"] = $("input[name=projectLegalPerson]").val();
        project["agentPerson"] = $("input[name=projectAgentPerson]").val();
        project["guaranteeRealm"] = $(".guaranteeRealm p").html();
        project["productId"] = $(".productId p").attr("name");
        project["amount"] = $("input[name=projectAmount]").val();
        project["years"] = $("#year").val();
        project["months"] = $("#month").val();
        project["days"] = $("#day").val();
        project["rate"] = $("input[name=projectRate]").val();
        project["method"] = $(".method p").attr("name");
        project["mortgaged"] = mortgagedValue;
        project["mortgagedType"] = mortgageGoods;
        project["loanGuaranteeFee"]= $("input[name=projectLoanGuaranteeFee]").val();
        project["loanServiceFee"]=$("input[name=projectLoanServiceFee]").val();
        project["loanRiskFee"] =$("input[name=projectLoanRiskFee]").val();
        project["loanManageFee"]=$("input[name=projectLoanManageFee]").val();
        project["loanInterestFee"]= $("input[name=projectLoanInterestFee]").val();
        project["investInterestFee"]= $("input[name=projectInvestInterestFee]").val();
        project["firmInfo"]=$("textarea[name=projectFirmInfo]").val();
        project["operationRange"]=$("textarea[name=projectOperationRange]").val();
        project["description"]=$("textarea[name=projectDescription]").val();
        project["repaySource"]=$("textarea[name=projectRepaySource]").val();
        project["riskInfo"]=$("textarea[name=projectRiskInfo]").val();
        var legalPersonPhoto= imgSrc("legalPersonPhoto");
        var enterpriseInfoPhoto = imgSrc("enterpriseInfoPhoto");
        var contractPhoto = imgSrc("contractPhoto");
        var assetsPhoto =imgSrc("assetsPhoto");
        var othersPhoto = imgSrc("othersPhoto");
        var prom = new Object();
        prom["project"]=project;
        prom["legalPersonPhoto"]=legalPersonPhoto;
        prom["enterpriseInfoPhoto"]=enterpriseInfoPhoto;
        prom["contractPhoto"]=contractPhoto;
        prom["assetsPhoto"]=assetsPhoto;
        prom["othersPhoto"]=othersPhoto;
        console.log(prom);
        if(project["title"]==""){
        	alert("项目名称不能为空");
        	return
        }
        if(project["serial"] == ""){
        	alert("项目唯一号不能为空");
        	return
        }
        if(project["serial"].split('-').length!=3){
        	alert("项目唯一号格式不正确");
        	return
        }
        if(project["loanUserId"] == ""){
        	alert("借款人ID不能为空");
        	return
        }
        
        if(project["legalPerson"] == ""&&project["agentPerson"] == ""){
        	alert("借款单位法人及代理人不能同时为空");
        	return
        }
        if(project["guaranteeRealm"]==""){
        	alert("关联企业不能为空");
        	return
        }
        if($(".productId p").attr("name")==""||$(".productId p").attr("name")==null){
        	alert("产品类型不能为空");
        	return
        }
        if(project["amount"] == ""){
        	alert("项目金额不能为空");
        	return
        }
        if(project["years"] == "" ||project["months"] == "" ||project["days"] == ""){
        	alert("借款期限不能为空");
        	return
        }
        if(project["rate"] == ""){
        	alert("年化利率不能为空");
        	return
        }
        if(project["method"] == ""){
        	alert("还款方式不能为空");
        	return
        }
        if(project["mortgaged"]==1&&project["mortgagedType"] == ""){
        	alert("抵押品不能为空");
        	return
        }
       
        $.ajax({
            type: "post",
            dataType :"json",
            url: "../../modifyProject",
            contentType:"application/json",
            data: JSON.stringify(prom),
            success: function(strJson){
                if(strJson.code == 1){
                    window.location.href = "projectManagement.html";

                }else {
                    alert("提交失败"+strJson.message);
                }

            },
            error: function(){

            }
        })




    });
    $(".resAll").click(function(){
        $(".resetBox").show();
        $(".resY").click(function(){
            $(".resetBox").hide();
            $("input").val("");
            $("textarea").val("");
        });
        $(".resN").click(function(){
            $(".resetBox").hide();
        });
    });
    //借款用户选择
    $("input[name=projectUserId]").next().click(function(){
        $(".choseId").show();
        $(".query").click(function(){
            $.ajax({
                type: "post",
                url: "../../queryLender",
                data: {
                    "loginName": $(".choseId input[name=loginName]").val()
                },
                success: function(str){
                    //借款人信息详情
                    if(str.idauthenticated == true){
                        str.idauthenticated = "是";
                    }else {
                        str.idauthenticated = "否";
                    }


                    $("input[name=loginName]").attr("loanId",str.id);
                    $(".choseId li").eq(1).find("span").eq(1).html(str.name);
                    $(".choseId li").eq(2).find("span").eq(1).html(str.idnumber);
                    $(".choseId li").eq(3).find("span").eq(1).html(str.mobile);
                    $(".choseId li").eq(4).find("span").eq(1).html(str.idauthenticated);

                }
            });
        });
        $(".determine span").click(function(){
            $(".choseId").hide();
            $("input[name=projectUserId]").val($("input[name=loginName]").attr("loanId"));
        });
    });
    //配置图片上传
    /*function upload(id,photoId){
        if(!id){
            return;
        }
        $("#"+id).uploadify({
            auto: true,
            swf: '../../uploadify.swf',
            uploader: '../../uploadFile',//后台处理的请求
            queueID : 'fileQueue',//与下面的id对应
            buttonClass :'btn',
            formData: {
                recid:"${rec.id}"
            },
            multi: true,
            fileTypeDesc: '请选择图片，仅支持格式JPG,BMP,PNG,GIF，最大4M',
            fileTypeExts: '*.JPG;*.GIF;*.PNG;*.BMP;',
            buttonText: '上传图片',

            fileObjName    : 'file',//服务端File对应的名称。
            fileSizeLimit    : '4MB',//文件上传的大小限制，如果是字符串单位可以是B KB MB GB，默认是0，表示无限制
            onUploadSuccess: function(file, data, response) {//上传完成时触发（每个文件触发一次
                if (response == true) {//如果服务器返回200表示成功
                    if (data.indexOf("#Err") == -1) {//判断是否有手动抛出错误信息
                        data = eval("(" + data + ")");
                        $("#faximage").attr("src",data.message);//重新生成缩略图
                        $("#showtrue").attr("href",
                            data + "?" + Math.random());
                        $("#pic").val(data);//保存数据
                        $("#nowpic").val("2");//表示重新上传了图片
                        var img = $("<img src='../../"+data.message+"'/>");
                        var li = $("<li class='left'></li>");
                        var i = $("<i>×</i>");
                        li.append(img);
                        li.append(i);
                        $("#"+photoId).find("ul").append(li);
                        $("#"+photoId).find("ul li i").click(function(){
                            $(this).parent().remove();
                        });
                    } else {
                        //否则输出错误信息
                        data = eval("(" + data + ")");
                        alert(data.message, 'e');
                    }
                } else {
                    alert('上传失败', 'e');
                }
            },
            onUploadError : function(file, data, response) {
                alert('上传失败', 'e');
            }
        });
    }
    upload("legalPerson","legalPersonPhoto");
    upload("enterpriseInfo","enterpriseInfoPhoto");
    upload("contract","contractPhoto");
    upload("assets","assetsPhoto");
    upload("others","othersPhoto");*/

    function fnSubmit(id){
        $("#"+id+" input").on("change",function(){
            $("#"+id+" form").submit()
        })
    };
    fnSubmit("legalPerson");
    fnSubmit("enterpriseInfo");
    fnSubmit("contract");
    fnSubmit("assets");
    fnSubmit("others");

});

function callback(obj,cName){
    var iArr = ["legalPersonPhoto","enterpriseInfoPhoto","contractPhoto","assetsPhoto","othersPhoto"];
    var num = parseInt(cName);
    var li = $("<li></li>");
    var img = $("<img src='"+obj+"' />");
    var i = $("<i>×</i>");
    i.appendTo(li);
    img.appendTo(li);
    li.appendTo( $("#"+iArr[num-1]+" ul") );
    $("#"+iArr[num-1]).find("ul li i").each(function(index,item){
        bindClick($(this));
        function bindClick(obj){
            obj.get(0).onclick = function(){
                var This = this;
                $.ajax({
                    type:"POST",
                    url:"../../deletePhoto",
                    data:{"path":$(This).siblings().attr("src"),"type":"upload"},
                    success:function(str){
                        alert(str.message);
                        if(str.code == 1){
                            $(This).parent().remove();
                        }
                    }
                });
            };
        };
    });
};
function uploadError(){
    alert("上传失败")
};

