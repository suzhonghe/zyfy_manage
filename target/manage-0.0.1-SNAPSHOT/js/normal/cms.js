var cms = {
	exception:function(obj){
        if(obj.code){
            if(obj.code == "UNKNOW_EXCEPTION" || obj.code == "UNKNOW_NAME" || obj.code == "USETTLE_FAIL" || obj.code == "NO_UUSERINVEST" || obj.code == "NO_INFORMATIONLOAN" || obj.code == "PREPARE_DEPOSIT_FAILED" || obj.code == "FILE_READ_WRITE_EXCEPTION" || obj.code == "EN_CODE_MD5_EXCEPTION" || obj.code == "SERVER_REFUSED" || obj.code == "LOGIN_ERROR" || obj.code == "USER_NOT_EXISTS" || obj.code == "USER_PASSWORD_VAILD_FAILURE" || obj.code == "NOREGISTERED_UMPACCOUNT" || obj.code == "USER_NOLOGIN" || obj.code == "LOGIN_SUCCESS" ){
                alert(obj.message);
                return;
            }
        }
    },
	getColumn:function(fn){    //获取栏目信息
		$.ajax({
			type:"POST",
			url:"../../queryCmsColumnByParams",
			data:{},
			success:function(str){
				cms.exception(str);
				$(".bidSearchT ul").html("");
				$(".bidSearchT .selectedList").html("").attr("code","");
				var len = str.length;
				if(str.length && len>0){
					$(".bidSearchT .selectedList").html(str[0].name).attr("code",str[0].id);
					for(var i=0;i<len;i++){
						var oLi = $("<li code="+str[i].id+" >"+str[i].name+"</li>");
			            oLi.appendTo($(".bidSearchT ul"));
					};
					fn && fn();
				};
				$(".bidSearchT li").click(function(){
					$(".bidSearchT .selectedList").html($(this).html()).attr("code",$(this).attr("code"));
				});
			},
			error:function(str){
				if(str.message){
                    alert(str.message);
                }else{
                    alert("服务器连接出错，请尝试刷新");
                }
			}
		});
		$(".bidSearchT .selectedList").click(function(){
			$(".bidSearchT ul").toggle();
			$(document).click(function(){
				$(".bidSearchT ul").hide();
			});
			return false;
		});
	},
	column:function(){   //栏目页面
		$.ajax({
			type:"POST",
			url:"../../queryCmsColumnByParams",
			data:{},
			success:function(str){
				cms.exception(str);
				$(".tableList tbody").html("");
				var len = str.length;
				if(!str.length || len<1){
					$(".tableList tbody").html("<td colspan='3'>暂无数据</td>");
				}else{
					for(var i=0;i<len;i++){
						var oTr = $("<tr></tr>");
			            oTr.html("<td>"+str[i].name+"</td><td>"+str[i].time+"</td><td class='operation'><a code="+str[i].id+" onclick='cms.editColumn(this)' href='javascript:void(0);'>编辑</a><a code="+str[i].id+" onclick='cms.delColumn(this)' href='javascript:void(0);'>删除</a></td>");
			            oTr.appendTo($(".tableList tbody"));
					}
				}
			},
			error:function(str){
				if(str.message){
                    alert(str.message);
                }else{
                    alert("服务器连接出错，请尝试刷新");
                }
			}
		});
		$(".addColumn").click(function(){
			var content = "<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'><span>栏目名称：</span><input name='newColumnName' type='text' /></p>";
	        var addStaff = new PopUpBox();
	        addStaff.init({
	            w:340,
	            h:240,
	            iNow:1,          // 确保一个对象只创建一次
	            title : '创建栏目',
	            opacity:0.7,
	            content:content,
	            callback:function(){creatColumn()}
	        });
		});
		function creatColumn(){
			var re = /[@#\$%\^&\*]+/g;
			if( re.test($("input[name='newColumnName']").val()) ){
				alert("不能输入非法字符")
			}else{
				$.ajax({
					type:"POST",
					url:"../../addCmsColumn",
					data:{"name":$("input[name='newColumnName']").val()},
					success:function(str){
						cms.exception(str);
						alert(str.message)
						if(str.code == 1){
							window.location.reload();
						}
					},
					error:function(str){
						if(str.message){
		                    alert(str.message);
		                }else{
		                    alert("服务器连接出错，请尝试刷新");
		                }
					}
				});
			}
		};
	},
	editColumn:function(This){   // 栏目页面的编辑
		var content = "<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'><span>栏目名称：</span><input name='editColumnName' type='text' value="+$(This).parent().siblings().eq(0).text()+" /></p>";
        var addStaff = new PopUpBox();
        addStaff.init({
            w:340,
            h:240,
            iNow:2,          // 确保一个对象只创建一次
            title : '编辑栏目',
            opacity:0.7,
            content:content,
            callback:function(){edit(This)}
        });
        function edit(This){
        	var re = /[@#\$%\^&\*]+/g;
			if( re.test($("input[name='editColumnName']").val()) ){
				alert("不能输入非法字符")
			}else{
				$.ajax({
					type:"POST",
					url:"../../modifyCmsColumnByParams",
					data:{"name":$("input[name='editColumnName']").val(),"id":$(This).attr("code")},
					success:function(str){
						cms.exception(str);
						alert(str.message)
						if(str.code == 1){
							window.location.reload();
						}
					},
					error:function(str){
						if(str.message){
		                    alert(str.message);
		                }else{
		                    alert("服务器连接出错，请尝试刷新");
		                }
					}
				});
			}
        };
	},
	delColumn:function(This){   // 栏目页面的删除
		$.ajax({
			type:"POST",
			url:"../../deleteCmsColumnByParams",
			data:{"id":$(This).attr("code")},
			success:function(str){
				cms.exception(str);
				alert(str.message)
				if(str.code == 1){
					window.location.reload();
				}
			},
			error:function(str){
				if(str.message){
                    alert(str.message);
                }else{
                    alert("服务器连接出错，请尝试刷新");
                }
			}
		});
	},
	writingArticles:function(){  // 新建文章页面
		if(window.location.search){
			var id = window.location.search.substring(1);
			$.ajax({
				type:"POST",
				url:"../../editArticle",
				data:{"id":id},
				success:function(str){
					console.log(str.content)
					cms.exception(str);
					$(".selectedList").html(str.columnName).attr("code",str.columnId);
					$(".title input").val(str.title);
					$(".author input").val(str.author);
					$(".source input").val(str.source);
					$(".sourceLink input").val(str.sourceLink);
					setTimeout(function(){
						document.getElementById('ueditor_0').contentWindow.document.body.innerHTML = str.content;
					},100);
				}
			});
		}
		var typeArr = ["pc_banner","app_banner","advertisement","news","article"];
		getPic(0);
		$(".articleImg input").click(function(){
			$(".articleImg input").removeClass("active");
			$(this).addClass("active");
			var typeIndex = $(this).index();
			getPic(typeIndex);
		});
		function getPic(index){
			$.ajax({
	            url: '../../queryBannerPhotos',
	            type: 'post',
	            data:{"type":typeArr[index]},
	            async: false,
	            success: function(str){
	            	cms.exception(str);
	                $(".picList").html("");
	                if(!str.length || str.length<1){
	                	var oLi = $("<li class='left'>暂无数据</li>");
	                	oLi.appendTo( $(".picList") );
	                }else{
	                	for(var i=0;i<str.length;i++){
	                		var oLi = $("<li class='left'></li>");
	                		oLi.html("<img src="+str[i].pathAddress+" alt='文章图片'><p>"+str[i].photoName+"</p><input type='text' value="+str[i].pathAddress+">")
	                	    oLi.appendTo( $(".picList") );
	                	}
	                }
	            }
	        });
		};
		$(".subBtn input").click(function(){
			if( $(".bidSearchT .selectedList").attr("code")=="" || $(".title .right input").val()=="" ){
				alert("所属栏目或文章标题不能为空")
			}else{
				publishArticles(id);
			};
	    });
	    function publishArticles(id){
	    	var articleID = id?id:"";
	    	var saveUrl = id?"../../modifyArticleByParams":"../../addArticle";
	    	$.ajax({
				type:"POST",
				url:saveUrl,
				data:{"id":articleID,"columnId":$(".bidSearchT .selectedList").attr("code"),"columnName":$(".bidSearchT .selectedList").html(),"title":$(".title input").val(),"author":$(".author input").val(),"source":$(".source input").val(),"sourceLink":$(".sourceLink input").val(),"content":UE.getEditor('editor').getContent()},
				success:function(str){
					cms.exception(str);
					alert(str.message);
					if(str.code == 1){
						window.history.go(-1);
					}
				}
			});
	    }
	},
	article:function(){
		cms.maListAjax({
            page:1,
            num:20,
            id:"paging",
            params:{"columnId":$(".bidSearchT .selectedList").attr("code"),"title":$("#querySearch").val()},
            url:"../../queryArticleByParams",
            callBack:function(obj){
                cms.articleData(obj);
            }
        });
	},
	articlePage:function(){
		$("#gitPos").click(function(){
			var arr = [];
			arr.length = 0;
			var len = $(".tableList tbody tr").length;
			$(".tableList tbody tr").each(function(){
				arr.push( $(this).children().eq(0).attr("code") )
			});
			$.ajax({
				type:"POST",
				url:"../../posDisplace",
				data:{"str":arr},
				success:function(str){
					cms.exception(str);
					alert(str.message);
					if(str.code == 1){
		                window.location.reload();
					}
				}
			});
		});
	},
	articleData:function(obj){
		$("#paging").html("");
		$(".tableList tbody").html("");
		if(!obj.results || !obj.results.length || obj.results.length<1){
        	$(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
        }else{
        	for(var i=0;i<obj.results.length;i++){
        		var oTr = $("<tr></tr>");
        		var num = (obj.pageNo-1)*obj.pageSize+i+1;
		    	oTr.html("<td code="+obj.results[i].id+">"+num+"</td><td>"+obj.results[i].title+"</td><td>"+obj.results[i].columnName+"</td><td>"+obj.results[i].submitTime+"</td><td class='operation'><a href='writingArticles.html?"+obj.results[i].id+"' >编辑</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,3,e)'>置顶</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,1)'>上移</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,2)'>下移</a><a href='javasctipt:void(0);' onclick='cms.delArticle(this)'>删除</a></td>");
				oTr.appendTo($(".tableList tbody"));
        	};
        };
	},
	movePos:function(This,dir,e){
		var len = $(".tableList tbody tr").length;
		if(dir == 1){    //上移
			var indexTr = $(This).parent().parent();
			if( indexTr.index() != 0 ) {
			    indexTr.prev().before(indexTr);
		    }
		}else if(dir == 2){  //下移
			var indexTr = $(This).parent().parent();
			if( indexTr.index() != (len-1) ) {
			    indexTr.next().after(indexTr);
		    }
		}else if(dir == 3){  //置顶
			var indexTr = $(This).parent().parent();
			if( indexTr.index() != 0 ) {
			    $(".tableList tbody").prepend( indexTr )
		    }
		};
		if ( e && e.preventDefault ){
			//阻止默认浏览器动作(W3C) 
		    e.preventDefault(); 
		}else{
			//IE中阻止函数器默认动作的方式 
		    window.event.returnValue = false; 
		};
		return false;
	},
	delArticle:function(This){
		$.ajax({
			type:"POST",
			url:"../../deleteArticleByParams",
			data:{"id":$(This).parent().siblings().eq(0).attr("code")},
			success:function(str){
				cms.exception(str);
				alert(str.message);
				if(str.code == 1){
	                window.location.reload();
				}
			}
		});
	},
	maListAjax:function(opt){
    	var settings = {
  			page:1,
  			num:10,
  			params:{},
  			url:"",
  			id:"",
  			callBack:function(){}
  		};
  		$.extend(settings,opt);
      	$.ajax({
            type:"POST",
              url:settings.url,
              data:{"pageNo":settings.page, 
  			    "pageSize":settings.num,
  			    "params":settings.params
  			    },
              dataType:"json",
              success:function(str){
              	cms.exception(str);
              	settings.callBack(str);
              	if(str.totalPage>1){
      			    	page({
      						id:settings.id,
      						nowNum:settings.page,
      						allNum:str.totalPage,
      						callBack:function(now,all){
      							cms.maListAjax({
      								page:now,
      							    num:10,
      							    params:settings.params,
      							    id:settings.id,
      							    url:settings.url,
      			                    callBack:settings.callBack
      							});
  						  }
  					  });
  			    };
  			  }
      });
    }
}