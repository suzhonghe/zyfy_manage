function ajaxRequest(t){var e=t.url||"",a=t.data||null,n=null;return $.ajax({url:e,type:"post",data:a,async:!1,success:function(t){if(t){if(t.code&&("UNKNOW_EXCEPTION"==t.code||"UNKNOW_NAME"==t.code||"USETTLE_FAIL"==t.code||"NO_UUSERINVEST"==t.code||"NO_INFORMATIONLOAN"==t.code||"PREPARE_DEPOSIT_FAILED"==t.code||"FILE_READ_WRITE_EXCEPTION"==t.code||"EN_CODE_MD5_EXCEPTION"==t.code||"SERVER_REFUSED"==t.code||"LOGIN_ERROR"==t.code||"USER_NOT_EXISTS"==t.code||"USER_PASSWORD_VAILD_FAILURE"==t.code||"NOREGISTERED_UMPACCOUNT"==t.code||"USER_NOLOGIN"==t.code||"LOGIN_SUCCESS"==t.code))return void alert(t.message);n=t}else n="加载超时"},error:function(){n="加载失败"}}),n}function paging(t){var e=new Object;return e.url=t.url,e.data=new Object,e.data=t.data,ajaxRequest(e)}function page(t){if(!t.id)return!1;var e=document.getElementById(t.id),a=t.nowNum||1,n=t.allNum||5,l=t.callBack||function(){};if(a>=2){var i=document.createElement("a");i.href="#"+(a-1),i.innerHTML="上一页",e.appendChild(i)}if(a>=4&&n>=6){var i=document.createElement("a");if(i.href="#1",i.innerHTML="1",e.appendChild(i),a>=5){var d=document.createElement("span");d.innerHTML="...",e.appendChild(d)}}if(5>=n)for(var r=1;n>=r;r++){var i=document.createElement("a");i.href="#"+r,i.innerHTML=r,e.appendChild(i)}else for(var r=1;5>=r;r++){var i=document.createElement("a");1==a||2==a?(i.href="#"+r,i.innerHTML=r):n-a==0||n-a==1?(i.href="#"+(n-5+r),i.innerHTML=n-5+r):(i.href="#"+(a-3+r),i.innerHTML=a-3+r),e.appendChild(i)}if(n-a>=3&&n>=6){if(n>=7&&n-a>=4){var d=document.createElement("span");d.innerHTML="...",e.appendChild(d)}var i=document.createElement("a");i.href="#"+n,i.innerHTML=n,e.appendChild(i)}if(n-a>=1){var i=document.createElement("a");i.href="#"+(a+1),i.innerHTML="下一页",e.appendChild(i)}for(var s=e.getElementsByTagName("a"),r=0;r<s.length;r++)s[r].innerHTML==a&&(s[r].className="on"),s[r].onclick=function(){var a=parseInt(this.getAttribute("href").substring(1));return e.innerHTML="",page({id:t.id,nowNum:a,allNum:n,callBack:t.callBack}),l(a,n),!1}}!function(){$.fn.selectBox=function(){$(this).click(function(t){$(".selectBox").find("ul").css({zIndex:"99"}).hide(),$(this).find("ul").css({zIndex:"999"}).show(),t.stopPropagation(),$(this).find("ul li").click(function(t){var e=$(this).html(),a=$(this).attr("proId");$(this).parent().parent().find("p").html(e).attr("name",a),$(this).parent().hide(),t.stopPropagation()})}),$(document).click(function(){$(".selectBox").find("ul").hide()})},$(".selectBox").selectBox()}(),$.fn.selectBoxCont=function(t){if(t){var e=$(this);$.ajax({type:"post",url:t,success:function(t){"clear guaranteeRealm"==e.attr("class")?$.each(t,function(t,a){e.find("ul").append($("<li></li>").html(a.shortname).attr("proId",a.userId))}):$.each(t,function(t,a){e.find("ul").append($("<li></li>").html(a.name).attr("proId",a.id))})}})}},function(){$.fn.shadow=function(){$(this).find("a").click(function(){$(".shade").hide()}),$(this).find("input").click(function(){$(".shade").hide()}),$(this).find("i").click(function(){$(".shade").hide(),$(".shade").find("a").unbind("click")})},$(".shade").shadow()}(),$.fn.tableFill=function(t){if(t){var e=t.tableData.data||t.tableData||null,a=t.approvalUrl||null,n=t.cancelUrl||null,l=t.repayUrl||null,i=t.advlUrl||null;$(this).find("tbody").html("");for(var d=0;d<e.length;d++){var r=null,s=null,c=null,o=null,u=null,h=null,m=null,p=null,f=null,v=null,b=null,w=null,I=null,E=null,N=null,x=null,T=null,B=null,q=null,y=null,R=null,g=null,L=null,k=null,_=null,O=null,S=null,U=null,C=null,A=null,D=null,M=null,P=null;sourceId=null,e[d].title&&(r=e[d].title),e[d].sourceId&&(sourceId=e[d].sourceId),e[d].repayDate&&(M=new Date(parseInt(e[d].repayDate)).toLocaleString().substr(0)),e[d].id&&(s=e[d].id),null!=e[d].amountTender&&(D=e[d].amountTender),e[d].name&&(c=e[d].name),e[d].amountInterest&&(o=e[d].amountInterest),null!=e[d].amountPrincipal&&(u=e[d].amountPrincipal),e[d].amountInterest&&(h=Number(o)+Number(u)),e[d].duedate&&(m=e[d].date),e[d].currentperiod&&(p=e[d].currentperiod),e[d].available_amount&&(f=e[d].available_amount),e[d].amount&&(v=e[d].amount),e[d].account&&(b=e[d].account),e[d].operationEmplyee&&(w=e[d].operationEmplyee),(e[d].telephone||e[d].mobile)&&(I=e[d].telephone||e[d].mobile),E=e[d].userName?e[d].userName:"",e[d].tradeAmount&&(N=e[d].tradeAmount),e[d].status&&(x=e[d].status),e[d].description&&(T=e[d].description),(e[d].createTime||e[d].timerecorded)&&(B=e[d].createTime||e[d].timerecorded),e[d].operation&&(q=e[d].operation,q="OUT"==q?"转出":"转入"),e[d].type&&(g=e[d].type),e[d].orderid&&(P=e[d].orderid),e[d].loginname&&(R=e[d].loginname),e[d].referralId&&(y=e[d].referralId),e[d].lastlogindate&&(k=e[d].lastlogindate),e[d].registerdate&&(L=e[d].registerdate),e[d].timerecorded&&(_=new Date(parseInt(e[d].timerecorded)).toLocaleString().substr(0)),e[d].rate&&(O=e[d].rate),e[d].months&&(S=e[d].months),e[d].months&&(U=e[d].submittime),e[d].method&&(C=e[d].method),A=0==e[d].enabled?!1:!0;var j=$("<tr></tr>");j.attr("id",s),j.attr("amountInterest",o),j.attr("amountPrincipal",u),$(this).find("thead th").each(function(){var t=$("<td class="+$(this).attr("class")+"></td>");j.append(t)});var H=j.attr("id");j.children("td[class=phoneNumber]").html("<a>"+I+"</a>").find("a").attr("href","userDetail.html#"+H),j.children("td[class=submittime]").html(U),j.children("td[class=term]").html(S),j.children("td[class=rate]").html(O+"%"),j.children("td[class=method]").html(C),j.children("td[class=time]").html(_),j.children("td[class=inviter]").html("<a>"+y+"</a>").find("a").attr("href","userDetail.html#"+e[d].referralRealm),j.children("td[class=loginName]").html(R),j.children("td[class=regTime]").html(L),j.children("td[class=lastTime]").html(k),j.children("td[class=controls]").html(0==A?"<a class='btn edit'>编辑</a><a class='btn abled'>启用</a>":"<a class='btn edit'>编辑</a><a class='btn disabled'>禁用</a>"),j.find("a.edit").click(function(){var t=$(this).parents("tr").attr("id");$(".shadowLayer").show(),$(".editBox").show(),$(".editBox input").eq(0).val(""),$(".editBox input").eq(1).val(""),$(".editBox input").eq(2).val("");var e=ajaxRequest({url:"../../getUserDetaile",data:{userId:t}});$(".editBox input").eq(0).val(e.mobile),$(".editBox input").eq(1).val(e.name),$(".editBox input").eq(2).val(e.idnumber),$(".editBox input[type=button]").click(function(){alert("保存");var e=$(".editBox input").eq(0).val(),a=$(".editBox input").eq(2).val(),n=$(".editBox input").eq(1).val(),l=/^[1][34578][0-9]{9}$/;if(!l.test(e))return void alert("请输入正确的手机号码");ajaxRequest({url:"../../updateUserInfo",data:{mobile:e,id:t,idnumber:a,name:n}});window.location.reload()})}),j.find("a.disabled").click(function(){var t=$(this).parents("tr").attr("id");$(".shadowLayer").show(),$(".disableBox").show(),$(".disableBox i").html($(this).parents("tr").find(".phoneNumber").html()),$(".disableBox input[type=button]").click(function(){ajaxRequest({url:"../../updateUserStatus",data:{id:t,enabled:!1}});window.location.reload()})}),j.find("a.abled").click(function(){var t=$(this).parents("tr").attr("id");$(".shadowLayer").show(),$(".ableBox").show(),$(".ableBox input[type=button]").click(function(){ajaxRequest({url:"../../updateUserStatus",data:{id:t,enabled:!0}});window.location.reload()})}),j.children("td[class=repayDate]").html(M),j.children("td[class=loanName]").html(r).attr("code",e[d].loanId).css("cursor","pointer").click(function(){$(window.parent.document).find(".navList p strong").html("<span>标的管理</span>").append("<span>标的详情</span>"),window.location.href="../targetAdministration/bidDetail.html#"+$(this).attr("code")}),j.children("td[class=realName]").html(c),j.children("td[class=periods]").html(p),j.children("td[class=stillAmount]").html(h),j.children("td[class=availableAmount]").html(f),j.children("td[class=bidAcount]").html(D),j.children("td[class=amount]").html(v),j.children("td[class=maturity]").html(m),j.children("td[class=repay]").html("<a class='repayBtn'>还款</a>"),j.children("td[class=advance]").html("<a class='advanceBtn'>垫付</a>"),j.children("td[class=sourceId]").html(sourceId),j.children("td[class=account]").html(b),j.children("td[class=operationEmplyee]").html(w),j.children("td[class=telephone]").html(I),j.children("td[class=userName]").html(E),j.children("td[class=tradeAmount]").html(N),j.children("td[class=status]").html(x),j.children("td[class=description]").html(T),j.children("td[class=createTime]").html(B),j.children("td[class=control]").html("<a class='btn approval'>批准</a><a class='btn cancel'>取消</a>"),j.children("td[class=type]").html(g),j.children("td[class=orderId]").html(P),j.children("td[class=opreation]").html(q),j.children("td[class=platAmount]").html(v),j.children("td[class=SerialNo]").html(d+1),j.find("a.repayBtn").click(function(){{var t=$(this).parent().parent().attr("id"),e=$(this).parent().parent().attr("amountInterest"),a=$(this).parent().parent().attr("amountPrincipal"),n=Number(e)+Number(a);$(this).parent().parent()}$(".shade").find("a").removeClass("advance").addClass("repay").html("确认还款").attr("repayStatus",!0).click(function(){$(".alertBox",window.parent.document).show();ajaxRequest({url:l,data:{id:t}});$(".shade").hide(),$(this).unbind("click"),setTimeout(function(){$(".alertBox",window.parent.document).hide(),window.location.reload()},18e3)}),$(".shade .tip li span").eq(1).html(""),$(".shade .tip li span").eq(3).html(""),$(".shade .tip li span").eq(5).html(""),$(".shade .tip li span").eq(1).html(a+"元"),$(".shade .tip li span").eq(3).html(e+"元"),$(".shade .tip li span").eq(5).html(n+"元"),$(".shade").show()}),j.find("a.advanceBtn").click(function(){{var t=$(this).parent().parent().attr("id"),e=$(this).parent().parent().attr("amountInterest"),a=$(this).parent().parent().attr("amountPrincipal"),n=Number(e)+Number(a);$(this).parent().parent()}$(".shade").find("a").removeClass("repay").addClass("advance").html("确认垫付").click(function(){$(".alertBox",window.parent.document).show();ajaxRequest({url:i,data:{repaymentId:t}});$(".shade").hide(),setTimeout(function(){$(".alertBox",window.parent.document).hide(),window.location.reload()},8e3)}),$(".shade .tip li span").eq(1).html(""),$(".shade .tip li span").eq(3).html(""),$(".shade .tip li span").eq(5).html(""),$(".shade .tip li span").eq(1).html(a+"元"),$(".shade .tip li span").eq(3).html(e+"元"),$(".shade .tip li span").eq(5).html(n+"元"),$(".shade").show()}),j.find("a.approval").click(function(){{var t=$(this).parent().parent().attr("id");$(this).parent().parent()}$(".shade").find("a").html("确定批准").click(function(){var e=ajaxRequest({url:a,data:{appIdli:t}});(1074==e.code||1075==e.code||1076==e.code)&&alert(e.message),window.location.reload()}),$(".shade").show()}),j.find("a.cancel").click(function(){{var t=$(this).parent().parent().attr("id");$(this).parent().parent()}$(".shade").find("a").html("确定取消").click(function(){ajaxRequest({url:n,data:{id:t}});window.location.reload()}),$(".shade").show()}),$(this).find("tbody").append(j)}}};