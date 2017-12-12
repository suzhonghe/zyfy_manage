$(function(){
    $("#pcPhoto").show();
    var idArr = ["pcPhoto","appPhoto","advPhoto","newsPhoto","articlePhoto"];
    var typeArr = ["pc_banner","app_banner","advertisement","news","article"];
    $(".slideNav li").click(function(){
        $(".slideNav li").removeClass("act");
        $(this).addClass("act");
        $(".photoBox").hide();
        $(".photoBox").eq($(this).index()).show();
        dataInit(idArr[$(this).index()],typeArr[$(this).index()]);
    });
    //数据初始化
    dataInit("pcPhoto","pc_banner");

     function dataInit(id,viewType){
            var data = new Object();
            $.ajax({
                 url: '../../queryBannerPhotos',
                 type: 'post',
                 data:{"type":viewType},
                 async: false,
                 success: function(str){
                     if(str){
                         data = str;
                     }
                 }
            });
          
            $("#"+id+" tbody").html("");
            for(var i = 0; i<data.length; i++){
                var tr = null;
                var imgUrl = null;
                var imgTitle = null;
                var imgLink = null;
                imgUrl = data[i].pathAddress;
                imgTitle = data[i].title;
                imgLink = data[i].jumpAddress;
                tr = $("<tr>" +
                    "<td class='img'>" +
                    "<img src="+imgUrl+" />" +
                    "</td>" +
                    "<td class='tdTitle'>"+imgTitle+"</td>" +
                    "<td class='tdLink'><a href="+imgLink+">"+imgLink+"</a></td>" +
                    "<td class='tdTime'></td>" +
                    "<td class='control'>" +
                    "<p>"+
                    "<input class='upBtn' type='button' value='上移' onclick='upTr(this)'/>" +
                    "<input class='downBtn' type='button' value='下移' onclick='downTr(this)'/>" +
                    "</p>"+
                    "<p>"+
                    "<input class='editBtn' type='button' value='编辑' onclick='editTr(this)'/>" +
                    "<input class='deleteBtn' type='button' value='删除' onclick='deleteTr(this)'/>" +
                    "</p>"+
                    "</td>" +
                    "</tr>");

                $("#"+id+" tbody").append(tr);
            }
     }
    //添加图片按钮
    $(".add").click(function(e){
    
        e.stopPropagation();
    	$(this).parents(".subBox").find(".addBox").toggle();
        $(this).parents(".subBox").find(".addBox").find("img").attr("src","");
        $(this).parents(".subBox").find(".addBox").find(".title").val("");
        $(this).parents(".subBox").find(".addBox").find(".link").val("");
    });

    //配置图片上传
    function upload(id,viewType){
        $("#"+id).uploadify({
            auto: true,
            swf: '../../uploadify.swf',
            uploader: '../../uploadBannerPhoto?type='+viewType,//后台处理的请求
            queueID : 'fileQueue',//与下面的id对应
           // buttonClass	:'btn',
            formData: {
                recid:"${rec.id}"
            },
            multi: true,
            fileTypeDesc: '请选择图片，仅支持格式JPG,BMP,PNG,GIF，最大4M',
            fileTypeExts: '*.JPG;*.GIF;*.PNG;*.BMP;',
            buttonText: '上传',
            width: 60,
            height: 30,
            fileObjName    : 'file',//服务端File对应的名称。
            fileSizeLimit	 : '4MB',//文件上传的大小限制，如果是字符串单位可以是B KB MB GB，默认是0，表示无限制
            onUploadSuccess: function(file, data, response) {//上传完成时触发（每个文件触发一次
                if (response == true) {//如果服务器返回200表示成功
                        $("#"+id).parent().parent().find("img").attr("src",data);
                }
            },
            onUploadError : function(file, data, response) {
            	alert(response)
                alert('上传失败', 'e');
            }
        });
    } 
    //添加图片框，上传按钮
    upload("pcUpBtn","pc_banner");
    upload("pcEditUpBtn","pc_banner");
    upload("appUpBtn","app_banner");
    upload("appEditUpBtn","app_banner");
    upload("advUpBtn","advertisement");
    upload("advEditUpBtn","advertisement");
    upload("newsUpBtn","news");
    upload("newsEditUpBtn","news");
    upload("articleUpBtn","article");
    upload("articleEditPicLink","article");
    //确定图片添加
    $(".addBox .determine").click(function(e){
    	var viewType = null;
    	
    	if($(this).parents(".photoBox").attr("id") == "pcPhoto"){
    		viewType = "pc_banner";
    	}else if($(this).parents(".photoBox").attr("id") == "appPhoto"){
    		viewType = "app_banner";
    	}else if($(this).parents(".photoBox").attr("id") == "advPhoto"){
    		viewType = "advertisement";
    	}else if($(this).parents(".photoBox").attr("id") == "newsPhoto"){
    		viewType = "news";
    	}else{
            viewType = "article";
        }
        e.stopPropagation();
        $(this).parent().hide();
        var tr = null;
        var imgUrl = null;
        var imgTitle = null;
        var imgLink = null;
        imgUrl = $(this).parent().find("img").attr("src");
        imgTitle = $(this).parent().find(".title").val();
        imgLink = $(this).parent().find(".link").val();
        
        if(!imgUrl){
            return;
        }
        $.ajax({
        	url: '../../modifyBannerPhoto',
        	type: 'post',
        	data: {
        		"jumpAddress": imgLink,
        		"pathAddress": imgUrl,
        		"title": imgTitle,
        		"type": viewType
        	}
        });
        tr = $("<tr>" +
            "<td class='img'>" +
                "<img src="+imgUrl+" />" +
            "</td>" +
            "<td class='tdTitle'>"+imgTitle+"</td>" +
            "<td class='tdLink'><a href='+imgLink+'>"+imgLink+"</a></td>" +
            "<td class='tdTime'></td>" +
            "<td class='control'>" +
                "<p>"+
                    "<input class='upBtn' type='button' value='上移' onclick='upTr(this)'/>" +
                    "<input class='downBtn' type='button' value='下移' onclick='downTr(this)'/>" +
                "</p>"+
                "<p>"+
                    "<input class='editBtn' type='button' value='编辑' onclick='editTr(this)'/>" +
                    "<input class='deleteBtn' type='button' value='删除' onclick='deleteTr(this)'/>" +
                "</p>"+
            "</td>" +
            "</tr>");

        $(this).parent().parent().find("tbody").prepend(tr);

    });
    //预览
    preview("pcPhoto","pc_banner");
    function preview(id,viewType){

            $("#"+id+" .release").click(function(){
            $(".reBox").show();
            $(".signOut input").click(function(){
                $(".tabBanner ol").html("");
                $(".tabBanner ul").html("");
                $(".reBox").hide();
            });
        
            var bannerArr = [];
            var photos = [];
            for(var i = 0, len = $("#"+id+" table tbody tr").size(); i < len; i++){
                var bannerJson = {};
                bannerJson.jumpAddress = $("#"+id+" table tbody tr").eq(i).find(".tdLink").find("a").html();
                bannerJson.pathAddress = $("#"+id+" table tbody tr").eq(i).find(".img").find("img").attr("src");
                bannerJson.serialNumber = i;
                bannerJson.title = $("#"+id+" table tbody tr").eq(i).find(".tdTitle").html();
                bannerArr.push(bannerJson);
                photos.push($("#"+id+" table tbody tr").eq(i).find(".img").find("img").attr("src"));
            }
            
            var prom = new Object();
            prom.bannerPhotoVo = new Object();
            prom.bannerPhotoVo.type = viewType;
            prom.bannerPhotos = bannerArr;
           
            
            $.ajax({
                url: '../../saveBannerPhoto',
                type: 'post',
                dataType :"json",
                contentType:"application/json",
                data: JSON.stringify(prom),
                async: false,
                success: function(str){
                    if(str.code == 1){
                        for(var i = 0, len = photos.length; i < len; i++){
                            var oLi = $("<li class='left'></li>");
                            var xLi = $("<li></li>");
                            if(i == 0){
                                oLi.addClass("act");
                                xLi.addClass("act");
                            }
                            xLi.css({
                                "backgroundImage": "url("+photos[i]+")"
                            });
                            oLi.appendTo($(".tabBanner ol"));
                            xLi.appendTo($(".tabBanner ul"));
                        };
                        $(".tabBanner").tab();
                    }else{
                        alert(str.message)
                    };
                }
            });
        });

        $.fn.tab = function(){

            var _this = $(this);
            _this.timer = null;
            _this.iNow = 0;
            $(this).find("ol li").click(function(){
                _this.iNow = $(this).index();
                $(this).parent().find("li").removeClass("act");
                $(this).addClass("act");
                $(this).parent().parent().find("ul li").removeClass("act");
                $(this).parent().parent().find("ul li").eq($(this).index()).addClass("act");
            });

            function timerTab(){
                _this.timer = setInterval(function(){
                    _this.iNow++;
                    if(_this.iNow == _this.find("ol li").length){
                        _this.iNow = 0;
                    }
                    _this.find("ul li").removeClass("act");
                    _this.find("ol li").removeClass("act");
                    _this.find("ul li").eq(_this.iNow).addClass("act");
                    _this.find("ol li").eq(_this.iNow).addClass("act");
                },2000);
            }
            function tabAuto(){

                clearInterval(_this.timer);
                timerTab();
            }
            tabAuto();

            $(this).mouseover(function(){
                clearInterval(_this.timer);
            });
            $(this).mouseout(function(){
                timerTab();
            });
        }
    };
    preview("appPhoto","app_banner");
    preview("advPhoto","advertisement");
    preview("newsPhoto","news");

});
