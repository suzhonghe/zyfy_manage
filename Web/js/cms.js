var cms={exception:function(e){return!e.code||"UNKNOW_EXCEPTION"!=e.code&&"UNKNOW_NAME"!=e.code&&"USETTLE_FAIL"!=e.code&&"NO_UUSERINVEST"!=e.code&&"NO_INFORMATIONLOAN"!=e.code&&"PREPARE_DEPOSIT_FAILED"!=e.code&&"FILE_READ_WRITE_EXCEPTION"!=e.code&&"EN_CODE_MD5_EXCEPTION"!=e.code&&"SERVER_REFUSED"!=e.code&&"LOGIN_ERROR"!=e.code&&"USER_NOT_EXISTS"!=e.code&&"USER_PASSWORD_VAILD_FAILURE"!=e.code&&"NOREGISTERED_UMPACCOUNT"!=e.code&&"USER_NOLOGIN"!=e.code&&"LOGIN_SUCCESS"!=e.code?void 0:void alert(e.message)},getColumn:function(e){$.ajax({type:"POST",url:"../../queryCmsColumnByParams",data:{},success:function(t){cms.exception(t),$(".bidSearchT ul").html(""),$(".bidSearchT .selectedList").html("").attr("code","");var a=t.length;if(t.length&&a>0){$(".bidSearchT .selectedList").html(t[0].name).attr("code",t[0].id);for(var i=0;a>i;i++){var n=$("<li code="+t[i].id+" >"+t[i].name+"</li>");n.appendTo($(".bidSearchT ul"))}e&&e()}$(".bidSearchT li").click(function(){$(".bidSearchT .selectedList").html($(this).html()).attr("code",$(this).attr("code"))})},error:function(e){alert(e.message?e.message:"服务器连接出错，请尝试刷新")}}),$(".bidSearchT .selectedList").click(function(){return $(".bidSearchT ul").toggle(),$(document).click(function(){$(".bidSearchT ul").hide()}),!1})},column:function(){function e(){var e=/[@#\$%\^&\*]+/g;e.test($("input[name='newColumnName']").val())?alert("不能输入非法字符"):$.ajax({type:"POST",url:"../../addCmsColumn",data:{name:$("input[name='newColumnName']").val()},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.location.reload()},error:function(e){alert(e.message?e.message:"服务器连接出错，请尝试刷新")}})}$.ajax({type:"POST",url:"../../queryCmsColumnByParams",data:{},success:function(e){cms.exception(e),$(".tableList tbody").html("");var t=e.length;if(!e.length||1>t)$(".tableList tbody").html("<td colspan='3'>暂无数据</td>");else for(var a=0;t>a;a++){var i=$("<tr></tr>");i.html("<td>"+e[a].name+"</td><td>"+e[a].time+"</td><td class='operation'><a code="+e[a].id+" onclick='cms.editColumn(this)' href='javascript:void(0);'>编辑</a><a code="+e[a].id+" onclick='cms.delColumn(this)' href='javascript:void(0);'>删除</a></td>"),i.appendTo($(".tableList tbody"))}},error:function(e){alert(e.message?e.message:"服务器连接出错，请尝试刷新")}}),$(".addColumn").click(function(){var t="<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'><span>栏目名称：</span><input name='newColumnName' type='text' /></p>",a=new PopUpBox;a.init({w:340,h:240,iNow:1,title:"创建栏目",opacity:.7,content:t,callback:function(){e()}})})},editColumn:function(e){function t(e){var t=/[@#\$%\^&\*]+/g;t.test($("input[name='editColumnName']").val())?alert("不能输入非法字符"):$.ajax({type:"POST",url:"../../modifyCmsColumnByParams",data:{name:$("input[name='editColumnName']").val(),id:$(e).attr("code")},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.location.reload()},error:function(e){alert(e.message?e.message:"服务器连接出错，请尝试刷新")}})}var a="<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'><span>栏目名称：</span><input name='editColumnName' type='text' value="+$(e).parent().siblings().eq(0).text()+" /></p>",i=new PopUpBox;i.init({w:340,h:240,iNow:2,title:"编辑栏目",opacity:.7,content:a,callback:function(){t(e)}})},delColumn:function(e){$.ajax({type:"POST",url:"../../deleteCmsColumnByParams",data:{id:$(e).attr("code")},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.location.reload()},error:function(e){alert(e.message?e.message:"服务器连接出错，请尝试刷新")}})},writingArticles:function(){function e(e){$.ajax({url:"../../queryBannerPhotos",type:"post",data:{type:i[e]},async:!1,success:function(e){if(cms.exception(e),$(".picList").html(""),!e.length||e.length<1){var t=$("<li class='left'>暂无数据</li>");t.appendTo($(".picList"))}else for(var a=0;a<e.length;a++){var t=$("<li class='left'></li>");t.html("<img src="+e[a].pathAddress+" alt='文章图片'><p>"+e[a].photoName+"</p><input type='text' value="+e[a].pathAddress+">"),t.appendTo($(".picList"))}}})}function t(e){var t=e?e:"",a=e?"../../modifyArticleByParams":"../../addArticle";$.ajax({type:"POST",url:a,data:{id:t,columnId:$(".bidSearchT .selectedList").attr("code"),columnName:$(".bidSearchT .selectedList").html(),title:$(".title input").val(),author:$(".author input").val(),source:$(".source input").val(),sourceLink:$(".sourceLink input").val(),content:UE.getEditor("editor").getContent()},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.history.go(-1)}})}if(window.location.search){var a=window.location.search.substring(1);$.ajax({type:"POST",url:"../../editArticle",data:{id:a},success:function(e){console.log(e.content),cms.exception(e),$(".selectedList").html(e.columnName).attr("code",e.columnId),$(".title input").val(e.title),$(".author input").val(e.author),$(".source input").val(e.source),$(".sourceLink input").val(e.sourceLink),setTimeout(function(){document.getElementById("ueditor_0").contentWindow.document.body.innerHTML=e.content},100)}})}var i=["pc_banner","app_banner","advertisement","news","article"];e(0),$(".articleImg input").click(function(){$(".articleImg input").removeClass("active"),$(this).addClass("active");var t=$(this).index();e(t)}),$(".subBtn input").click(function(){""==$(".bidSearchT .selectedList").attr("code")||""==$(".title .right input").val()?alert("所属栏目或文章标题不能为空"):t(a)})},article:function(){cms.maListAjax({page:1,num:20,id:"paging",params:{columnId:$(".bidSearchT .selectedList").attr("code"),title:$("#querySearch").val()},url:"../../queryArticleByParams",callBack:function(e){cms.articleData(e)}})},articlePage:function(){$("#gitPos").click(function(){var e=[];e.length=0;$(".tableList tbody tr").length;$(".tableList tbody tr").each(function(){e.push($(this).children().eq(0).attr("code"))}),$.ajax({type:"POST",url:"../../posDisplace",data:{str:e},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.location.reload()}})})},articleData:function(e){if($("#paging").html(""),$(".tableList tbody").html(""),!e.results||!e.results.length||e.results.length<1)$(".tableList tbody").html("<td colspan='5'>暂无数据</td>");else for(var t=0;t<e.results.length;t++){var a=$("<tr></tr>"),i=(e.pageNo-1)*e.pageSize+t+1;a.html("<td code="+e.results[t].id+">"+i+"</td><td>"+e.results[t].title+"</td><td>"+e.results[t].columnName+"</td><td>"+e.results[t].submitTime+"</td><td class='operation'><a href='writingArticles.html?"+e.results[t].id+"' >编辑</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,3,e)'>置顶</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,1)'>上移</a><a href='javasctipt:void(0);' onclick='cms.movePos(this,2)'>下移</a><a href='javasctipt:void(0);' onclick='cms.delArticle(this)'>删除</a></td>"),a.appendTo($(".tableList tbody"))}},movePos:function(e,t,a){var i=$(".tableList tbody tr").length;if(1==t){var n=$(e).parent().parent();0!=n.index()&&n.prev().before(n)}else if(2==t){var n=$(e).parent().parent();n.index()!=i-1&&n.next().after(n)}else if(3==t){var n=$(e).parent().parent();0!=n.index()&&$(".tableList tbody").prepend(n)}return a&&a.preventDefault?a.preventDefault():window.event.returnValue=!1,!1},delArticle:function(e){$.ajax({type:"POST",url:"../../deleteArticleByParams",data:{id:$(e).parent().siblings().eq(0).attr("code")},success:function(e){cms.exception(e),alert(e.message),1==e.code&&window.location.reload()}})},maListAjax:function(e){var t={page:1,num:10,params:{},url:"",id:"",callBack:function(){}};$.extend(t,e),$.ajax({type:"POST",url:t.url,data:{pageNo:t.page,pageSize:t.num,params:t.params},dataType:"json",success:function(e){cms.exception(e),t.callBack(e),e.totalPage>1&&page({id:t.id,nowNum:t.page,allNum:e.totalPage,callBack:function(e){cms.maListAjax({page:e,num:10,params:t.params,id:t.id,url:t.url,callBack:t.callBack})}})}})}};