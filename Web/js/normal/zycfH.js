var zycfH = {
    exception:function(obj){
        if(obj.code){
            if(obj.code == "UNKNOW_EXCEPTION" || obj.code == "UNKNOW_NAME" || obj.code == "USETTLE_FAIL" || obj.code == "NO_UUSERINVEST" || obj.code == "NO_INFORMATIONLOAN" || obj.code == "PREPARE_DEPOSIT_FAILED" || obj.code == "FILE_READ_WRITE_EXCEPTION" || obj.code == "EN_CODE_MD5_EXCEPTION" || obj.code == "SERVER_REFUSED" || obj.code == "LOGIN_ERROR" || obj.code == "USER_NOT_EXISTS" || obj.code == "USER_PASSWORD_VAILD_FAILURE" || obj.code == "NOREGISTERED_UMPACCOUNT" || obj.code == "USER_NOLOGIN" || obj.code == "LOGIN_SUCCESS" ){
                alert(obj.message);
                return;
            }
        }
    },
    myData:function(){   // 时间输入框
   	    !function(){
            laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
            laydate({elem: '#dataStar'});//绑定元素
        }();
        !function(){
            laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
            laydate({elem: '#dataEnd'});//绑定元素
        }();
    },
    login:function(){  // 后台页面登录
       $('#loginFrame .userBar input').focus();
       $('#loginFrame .userBar input').focus(function(){
           $('#loginFrame .msgInfo').css('visibility','hidden');
       });
       $('#loginFrame .passwordBar input').focus(function(){
           $('#loginFrame .msgInfo').css('visibility','hidden');
       });
       $('#loginFrame .codeBar input').focus(function(){
           $('#loginFrame .msgInfo').css('visibility','hidden');
       });
       $('#loginFrame .codeBar .Refresh').click(function(){
            $('#loginFrame .codeBar').find('img').attr('src','imgValalidate'+'?'+ Math.random())
        });
       $(document).keydown(function(e){
          if(e.keyCode==13){
             $('#loginFrame .loginBar input').click();
          }
        });
       $('#loginFrame .loginBar input').click(function(){
           if($('#loginFrame .userBar input').val() == ''){
               $('#loginFrame .msgInfo').css('visibility','visible').html('用户名不能为空！');
           }else if($('#loginFrame .passwordBar input').val() == ''){
               $('#loginFrame .msgInfo').css('visibility','visible').html('密码不能为空！');
           }else if($('#loginFrame .codeBar input').val() == ''){
               $('#loginFrame .msgInfo').css('visibility','visible').html('验证码不能为空！');
           }else{
               $.ajax({    
                   url:'codeValalidate',  //请求路径
                   type:'post',  //请求方式  get||post
                   data:{},  //请求数据
                   success:function(str){
                       zycfH.exception(str);
                       if($('#loginFrame .codeBar input')[0].value.toLowerCase() == str.code.toLowerCase()){
                           $.ajax({
                               url: 'emplogin',
                               type: 'post',
                               data: {"loginname": $('#loginFrame .userBar input').val(),
                                      "passphrase": $('#loginFrame .passwordBar input').val()
                               },
                               success: function(str){
                                    zycfH.exception(str);
                                   if(str.code == 1002){
                                       $('#loginFrame .msgInfo').css('visibility','visible').html("用户名不存在");
                                   }else if(str.code == 1001){
                                       $('#loginFrame .msgInfo').css('visibility','visible').html("用户名和密码不匹配");
                                   }else if(str.code == 1003){
                                        $('#loginFrame .msgInfo').css('visibility','visible').html(str.message);
                                   }else {
                                      if (top != self) {
                                          top.window.location.href="index.html";
                                      }else{
                                          window.location.href="index.html";
                                      };
                                   };
                               }/*,
                               error: function(str){
                                   if(str.message){
                                      $('#loginFrame .msgInfo').css('visibility','visible').html(str.message);
                                   }else{
                                      alert("服务器连接出错，请尝试刷新");
                                   }
                                   
                               }*/
                           });
                       }else{
                           $('#loginFrame .msgInfo').css('visibility','visible').html('验证码不正确！');
                       };
                   }/*,
                   error:function(str){
                      if(str.message){
                          alert(str.message);
                      }else{
                          alert("服务器连接出错，请尝试刷新");
                      }
                   }*/
               });
           };
       });
    },
    homePage:function(){    // 后台首页
       var t = document.documentElement.offsetHeight || document.body.offsetHeight;
       $('.sideBarNav').height(t-88);
       $('.content').height(t-88);
       $('#header .showUserName').click(function(){   // 首页右上角弹出框 
           $('#header .msg').toggle();
           document.onclick = function(){
               $('#header .msg').hide();
           }
           return false;
       });
       $('#header .signOut').click(function(){
          $('#header .msg').hide();
          var oLogin = "<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'>你真的要退出登录吗？</p>"
          var addStaff = new PopUpBox();
          addStaff.init({
              w:340,
              h:200,
              iNow:9,          // 确保一个对象只创建一次
              title : '添加员工',
              opacity:0.7,
              mark:true,
              tBar:false,
              content:oLogin,
              callback:function(){loginOut();}
          });
          return false;
       });
       function loginOut(){
          $.ajax({
             data:{},
             url:"emplogout",
             type:"post",
             success:function(str){
                 zycfH.exception(str);
                if(str.code == 0){
                  window.location.reload();
                }else{
                  alert(str.message);
                }
             }
          })
       };
       if(getCookie('sideNav')){
          $('.sideBarNav h3 span').each(function(){
            if($(this).html() == getCookie('sideNav')){
              $('.sideBarNav h3').removeClass('active').siblings().css('display',"none");
              $(this).parent().addClass('active');
              $('#iframe').attr('src',getCookie('URL'));
              $('.navList p strong').html("<span>"+$(this).html()+"</span>");
            }
          });
          $('.sideBarNav li li').each(function(){
            if($(this).html() == getCookie('sideNav')){
              $('.sideBarNav h3').removeClass('active').siblings().css('display',"none");
              $(this).parent().show();
              $('.sideBarNav li li').removeClass('on').eq($(this).addClass('on'));
              $('.navList p strong').html($(this).parent().siblings().html()).append("<span>"+$(this).html()+"</span>");
              $('#iframe').attr('src',getCookie('URL'));
            }
          });
       }
       $('.sideBarNav h3').click(function(){     // 首页管理列表

           $('.sideBarNav h3').removeClass('active').siblings().slideUp();
           if($(this).siblings().css('display') == 'none'){
               $(this).addClass('active').siblings().slideDown();
           }else{
               $(this).removeClass('active').siblings().slideUp();
           };
           if($(this).find('span').html() == '产品管理'){
               $('.sideBarNav li li').removeClass('on');
               $('.navList p strong').html($(this).html());
               $('#iframe').attr('src','html/productAdministration/productList.html');
               setCookie('sideNav', $(this).find('span').html(),{"path":"/back"});
               setCookie('URL', 'html/productAdministration/productList.html',{"path":"/back"});
           };
           if($(this).find('span').html() == '项目管理'){
               $('.sideBarNav li li').removeClass('on');
               $('.navList p strong').html($(this).html());
               $('#iframe').attr('src','html/projectAdministration/projectManagement.html');
               setCookie('sideNav', $(this).find('span').html(),{"path":"/back"});
               setCookie('URL', 'html/projectAdministration/projectManagement.html',{"path":"/back"});
           };
       });
       $('.sideBarNav li li').click(function(){      // 首页右上角弹出框
           $('.sideBarNav li li').removeClass('on').eq($(this).addClass('on'));
           $('.navList p strong').html($(this).parent().siblings().html()).append("<span>"+$(this).html()+"</span>");
 
           /* 标的管理 */
           if($(this).html() == '标的列表' || $(this).html() == '标的统计'){
               $('#iframe').attr('src','html/targetAdministration/bidList.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/bidList.html',{"path":"/back"});
           };
           if($(this).html() == '申请发标列表'){
               $('#iframe').attr('src','html/targetAdministration/apply.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/apply.html',{"path":"/back"});
           };
           if($(this).html() == '需调度发标列表'){
               $('#iframe').attr('src','html/targetAdministration/needDispatch.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/needDispatch.html',{"path":"/back"});
           };
           if($(this).html() == '已调度发标列表'){
               $('#iframe').attr('src','html/targetAdministration/alreadyDispatch.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/alreadyDispatch.html',{"path":"/back"});
           };
           if($(this).html() == '进行中列表'){
               $('#iframe').attr('src','html/targetAdministration/onGoing.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/onGoing.html',{"path":"/back"});
           };
           if($(this).html() == '满标审核'){
               $('#iframe').attr('src','html/targetAdministration/loanManger.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/loanManger.html',{"path":"/back"});
           };
           if($(this).html() == '生成合同'){
               $('#iframe').attr('src','html/targetAdministration/createContract.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/createContract.html',{"path":"/back"});
           };
           if($(this).html() == '已流标列表'){
               $('#iframe').attr('src','html/targetAdministration/Flow.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/Flow.html',{"path":"/back"});
           };
           if($(this).html() == '已取消列表'){
               $('#iframe').attr('src','html/targetAdministration/cancel.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/targetAdministration/cancel.html',{"path":"/back"});
           };
 
           /* 对账管理 */
           if($(this).html() == '充值对账'){
               $('#iframe').attr('src','html/accountChecking/recharge.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/recharge.html',{"path":"/back"});
           };
           if($(this).html() == '提现对账'){
               $('#iframe').attr('src','html/accountChecking/withdrawals.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/withdrawals.html',{"path":"/back"});
           };
           if($(this).html() == '转账对账'){
               $('#iframe').attr('src','html/accountChecking/transferAccounts.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/transferAccounts.html',{"path":"/back"});
           };
           if($(this).html() == '标的对账'){
               $('#iframe').attr('src','html/accountChecking/bid.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/bid.html',{"path":"/back"});
           };
           if($(this).html() == '对账文件管理'){
               $('#iframe').attr('src','html/accountChecking/recManagement.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/recManagement.html',{"path":"/back"});
           };
           if($(this).html() == '对账文件下载'){
               $('#iframe').attr('src','html/accountChecking/accountLoad.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/accountLoad.html',{"path":"/back"});
           };
           if($(this).html() == '运维查询'){
               $('#iframe').attr('src','html/umpreturnMsg/form.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/umpreturnMsg/form.html',{"path":"/back"});
           };
           if($(this).html() == '运维调账'){
               $('#iframe').attr('src','html/umpreturnMsg/adjustData.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/umpreturnMsg/adjustData.html',{"path":"/back"});
           };
           if($(this).html() == '用户余额对账'){
               $('#iframe').attr('src','html/accountChecking/userFund.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/userFund.html',{"path":"/back"});
           };
           if($(this).html() == '个人资金记录'){
               $('#iframe').attr('src','html/accountChecking/PfundRecord.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/accountChecking/PfundRecord.html',{"path":"/back"});
           };
 
           //资金管理
           if($(this).html() == '三方支付'){
               $('#iframe').attr('src','html/fundManagement/threePartyPayment.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/fundManagement/threePartyPayment.html',{"path":"/back"});
           };
           if($(this).html() == '商户向用户转账'){
               $('#iframe').attr('src','html/fundManagement/merchantsToUser.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/fundManagement/merchantsToUser.html',{"path":"/back"});
           };
           if($(this).html() == '平台资金记录'){
               $('#iframe').attr('src','html/fundManagement/PlatformFundRecord.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/fundManagement/PlatformFundRecord.html',{"path":"/back"});
           };
           if($(this).html() == '标的调账'){
               $('#iframe').attr('src','html/fundManagement/loanFund.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/fundManagement/loanFund.html',{"path":"/back"});
           };
           //还款管理
           if($(this).html() == '本日到期还款'){
               $('#iframe').attr('src','html/repayManagement/dueRepay.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/repayManagement/dueRepay.html',{"path":"/back"});
           };
           if($(this).html() == '尚未到期还款'){
               $('#iframe').attr('src','html/repayManagement/noRepaymentDate.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/repayManagement/noRepaymentDate.html',{"path":"/back"});
           };
           if($(this).html() == '已还清列表'){
               $('#iframe').attr('src','html/repayManagement/paidOff.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/repayManagement/paidOff.html',{"path":"/back"});
           };
           if($(this).html() == '逾期未还列表'){
               $('#iframe').attr('src','html/repayManagement/overdue.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/repayManagement/overdue.html',{"path":"/back"});
           };
            if($(this).html() == '违约列表'){
               $('#iframe').attr('src','html/repayManagement/defaultList.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/repayManagement/defaultList.html',{"path":"/back"});
           };
           //员工管理
           if($(this).html() == '角色权限'){
               $('#iframe').attr('src','html/staff/roleSecurity.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/staff/roleSecurity.html',{"path":"/back"});
           };
           if($(this).html() == '员工列表'){
               $('#iframe').attr('src','html/staff/staffList.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/staff/staffList.html',{"path":"/back"});
           };
           //cms管理
           if($(this).html() == '图片'){
               $('#iframe').attr('src','html/cms/pictures.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/cms/pictures.html',{"path":"/back"});
           };
           if($(this).html() == '栏目文章'){
               $('#iframe').attr('src','html/cms/article.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/cms/article.html',{"path":"/back"});
           };
           //用户列表
            if($(this).html() == '用户列表'){
               $('#iframe').attr('src','html/usersList/usersList.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/usersList/usersList.html',{"path":"/back"});
           };
           if($(this).html() == '用户组'){
               $('#iframe').attr('src','html/usersList/userGroup.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/usersList/userGroup.html',{"path":"/back"});
           };
           if($(this).html() == '机构管理'){
               $('#iframe').attr('src','html/usersList/organizationTree.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/usersList/organizationTree.html',{"path":"/back"});
           };
           if($(this).html() == '推荐人变更'){
               $('#iframe').attr('src','html/usersList/changeReferee.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/usersList/changeReferee.html',{"path":"/back"});
           };
           //系统管理
           if($(this).html() == '修改密码'){
               $('#iframe').attr('src','html/system/modifyPassword.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/system/modifyPassword.html',{"path":"/back"});
           };
           //消息管理
           if($(this).html() == '短信管理'){
               $('#iframe').attr('src','html/message/message.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/message/message.html',{"path":"/back"});
           };
           if($(this).html() == '客服统计'){
               $('#iframe').attr('src','html/message/customerService.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/message/customerService.html',{"path":"/back"});
           };
           //奖励管理
           if($(this).html() == '邀请列表'){
               $('#iframe').attr('src','html/reward/inviteList.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/reward/inviteList.html',{"path":"/back"});
           };
           if($(this).html() == '红包管理'){
               $('#iframe').attr('src','html/reward/redMoney.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/reward/redMoney.html',{"path":"/back"});
           };
           if($(this).html() == '体验金'){
               $('#iframe').attr('src','html/reward/testMoney.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/reward/testMoney.html',{"path":"/back"});
           };
           if($(this).html() == '体验标'){
               $('#iframe').attr('src','html/reward/testBid.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/reward/testBid.html',{"path":"/back"});
           };
           //邀请统计
           if($(this).html() == '邀请统计'){
               $('#iframe').attr('src','html/statistics/invitationStatistics.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/statistics/invitationStatistics.html',{"path":"/back"});
           };
           if($(this).html() == '用户统计'){
               $('#iframe').attr('src','html/statistics/regUser.html');
               setCookie('sideNav', $(this).html(),{"path":"/back"});
               setCookie('URL', 'html/statistics/regUser.html',{"path":"/back"});
           };
       });
    },
    newStandard:function(){
       /* 下拉列表 */
       $('.affEnterprise .right span').click(function(){
           $('.affEnterprise .right ul').toggle();
       });
       $('.productType .right span').click(function(){
           $('.productType .right ul').toggle();
       });
       $('.InvestmentGroup .right span').click(function(){
           $('.InvestmentGroup .right ul').toggle();
       });
       $('.InvestmentGroup .right li').click(function(){
           $('.InvestmentGroup .right span').html($(this).html());
           $('.InvestmentGroup .right ul').hide();
       });
       $('.Whether .right span').click(function(){
           $('.Whether .right ul').toggle();
       });
       $('.Whether .right li').click(function(){
           $('.Whether .right span').html($(this).html());
           $('.Whether .right ul').hide();
       });
       $('.RepaymentMethod .right span').click(function(){
           $('.RepaymentMethod .right ul').toggle();
       });
       $('.RepaymentMethod .right li').click(function(){
           $('.RepaymentMethod .right span').html($(this).html());
           $('.RepaymentMethod .right ul').hide();
       });
    },
    listAjax:function(opt){       // 页面拿数据
   	    var settings = {
            page:1,
            num:10,
            startTime:new Date("1970/1/01").getTime(),
            endTime:new Date("2120/1/01").getTime(),
            field:"",
            value:"",
            sort:"",
            url:"",
            callBack:function(){}
        };
        $.extend(settings,opt);
        $.ajax({
            type:"POST",
            url:settings.url,
            data:{"field":settings.field,"value":settings.value,"sort":settings.sort,"start":settings.page,"length":settings.num,"startTime":settings.startTime,"endTime":settings.endTime},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
            	settings.callBack(str);
                if(str.totalPage>1){
                    page({
                        id:'paging',
                        nowNum:settings.page,
                        allNum:str.totalPage,
                        callBack:function(now,all){
                       	    var start,end;
                       	    if($("#dataStar").val() == "" || $("#dataStar").length<=0){
                       	    	start = settings.startTime;
                       	    }else{
                       	    	start = new Date($("#dataStar").val()).getTime();
                       	    };
                       	    if($("#dataEnd").val() == "" || $("#dataEnd").length<=0){
                       	    	end = settings.endTime;
                       	    }else{
                       	    	end = new Date($("#dataEnd").val()).getTime();
                       	    };
                            zycfH.listAjax({
                                page:now,
                                num:10,
                                startTime:start,
                                endTime:end,
                                field:settings.field,
          					            value:settings.value,
          					            sort:settings.sort,
                                url:settings.url,
                                callBack:settings.callBack
                            });
                        }
                    });
                };
            }
        });
    },
    productCallBack:function(obj){    // 产品页数据展示
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
        }else{
        	for(var i=0;i<obj.data.length;i++){
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a onclick='zycfH.productJunp(this)' code="+obj.data[i].id+" href='javascript:void(0);'>"+obj.data[i].name+"</a></td><td>"+obj.data[i].months+"月"+"</td><td>"+obj.data[i].rate+"%"+"</td><td>"+obj.data[i].description+"</td><td class='operation'><a onclick='zycfH.productJunp(this)' code="+obj.data[i].id+" href='javascript:void(0);'>编辑</a><a onclick='zycfH.productDisable(this)' status="+obj.data[i].status+" href='javascript:void(0);'>停用</a></td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    full:function(){   // 满标页
       $.ajax({
             type:"POST",
             url:"../../finishedLoan",
             data:{},
             dataType:"json",
             success:function(str){
                 zycfH.exception(str);
                 if(str.length == 0){
                     var oDiv = $("<div style='font-size:16px;text-align:center;color:#666;margin-top:40px;'>暂无数据</div>");
                     oDiv.appendTo($(document.body));
                 }else{
                     getData(str);
                 };
             }
         });
       function getData(obj){
           var len = obj.length;
           for(var i=0;i<len;i++){
               var t = parseInt(Math.abs(obj[i].timeFinished)/1000);
               var countdown = Math.floor(t/86400)+'天'+Math.floor(t%86400/3600)+'时'+Math.floor(t%86400%3600/60)+'分'+t%60+'秒';
               var full = (obj[i].status='FINISHED')?'已满标':'未满标';
               var aContent = document.createElement('div');
               aContent.className = 'item';
               aContent.innerHTML = "<p class='itemTitle'><span>"+obj[i].title+"</span><span class='examine' code="+obj[i].loanid+" state='1' onclick='zycfH.balance(this)'>审核结算</span><span code="+obj[i].loanid+" onclick='zycfH.flowBtn(this)' class='flow'>流标</span></p><div class='borrowerBar clear'><div><p>借款人："+obj[i].name+"</p><p>已投金额："+obj[i].amount+"元</p></div><div><p>投标数："+obj[i].bidNumber+"</p><p>总金额："+obj[i].bidAmount+"元</p></div><div><p>用时："+countdown+"</p><p>状态："+full+"</p></div></div>";
               var tabContent = obj[i].loanInvestVol;
               var oLen = tabContent.length;
               var oTab = document.createElement('table');
               var oThead = document.createElement('thead');
               var oTbody = document.createElement('tbody');
               var oTr = document.createElement('tr');
               oTr.innerHTML = "<th>投资者</th><th>金额</th><th>投标时间</th>";
               oThead.appendChild(oTr);
               oTab.appendChild(oThead);
               oTab.appendChild(oTbody);
               var oBtn = document.createElement('p');
               oBtn.className = 'slideToggle';
               oBtn.innerHTML = "<span loanId="+obj[i].loanid+">展开查看</span>";
               aContent.appendChild(oTab);
               aContent.appendChild(oBtn);
               document.body.appendChild(aContent);
           };
           $('.item .slideToggle span').click(function(){
               var This = this;
               if($(this).html() == '展开查看'){
                   $(this).parent().siblings('table').show();
                   $(this).html('收起');
                   $.ajax({
                        type:"POST",
                        url:"../../finishedLoanInvestUser",
                        data:{"longId":$(this).attr('loanId')},
                        dataType:"json",
                        success:function(str){
                            zycfH.exception(str);
                            $(This).parent().siblings('table').find('tbody').html('');
                            for(var i=0;i<str.length;i++){
                                var oTr = $("<tr></tr>");
                                oTr.html("<td>"+str[i].investName+"</td><td>"+str[i].investAmount+"</td><td>"+str[i].submittime+"</td>");
                               oTr.appendTo($(This).parent().siblings('table').find('tbody'));
                            };
                        }
                    });
               }else if($(this).html() == '收起'){
                   $(this).parent().siblings('table').hide();
                   $(this).html('展开查看');
               };
           });
       };
    },
    createContractPage:function(){    // 生成合同
        zycfH.maListAjax({
            page:1,
            num:10,
            id:"paging",
            url:"../../getuncontractLoans",
            callBack:function(obj){
              zycfH.createContractData(obj)
            }
        });
    },
    createContractData:function(obj){
      $(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.results.length || obj.results.length<1){
          $(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
        }else{
          var len = obj.results.length;
          for(var i=0;i<len;i++){
              var oTr = $("<tr></tr>");
              oTr.html("<td><a code="+obj.results[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.results[i].title+"</a></td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].status+"</td><td>"+new Date(parseInt(obj.results[i].timeSettled,10)).toLocaleDateString()+"</td><td class='operation'><a code="+obj.results[i].id+" onclick='zycfH.createContract(this)' href='javascript:void(0);'>生成合同</a></td>");
              oTr.appendTo($(".tableList tbody"));
          };
        };
    },
    createContract:function(This){
        $(".alertBox", window.parent.document).show();
        setTimeout(function(){
            $(".alertBox", window.parent.document).hide();
        },3000);
        $.ajax({
            type:"POST",
            url:"../../generateContracts",
            data:{"loanId":$(This).attr('code')},
            dataType:"json",
            success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 1){
                  $(This).parent().parent().remove();
                }
            }
        });
    },
    productList:function(){  // 产品页
        zycfH.listAjax({
            page:1,
            num:100,
            field:"timeCreate",
            value:"TIMECREATE",
            sort:"desc",
            url:"../../queryAllProducts",
            callBack:function(obj){
            	zycfH.productCallBack(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeCreate",
		            value:"TIMECREATE",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../queryAllProducts",
			        callBack:function(obj){
			        	zycfH.productCallBack(obj)
			        }
	            });
       	    };
        });
    },
    productDisable:function(This){    // 产品页启用停用
       var code = $(This).siblings().eq(0).attr('code');
       $.ajax({
           type:"POST",
           url:"../../modifyProductStatus",
           data:{"productId":code},
           dataType:"json",
           success:function(str){
               zycfH.exception(str);
               if(str.code == 1){
               	    alert('修改成功！');
                   if(status == 0){
                       status = 1;
                       $(This).attr('status',status);
                       $(This).html('启用');
                   }else if(status == 1){
                       status = 0;
                       $(This).attr('status',status);
                       $(This).html('停用');
                   };
               }else{
                   alert('修改失败！');
               };
           }
       });
    },
    productJunp:function(This){   // 产品页编辑
       var code = This.getAttribute("code");
       if($(This).html() == '编辑'){
           window.location.href = "addProduct.html?"+code;
       }else{
           window.location.href = "productDetails.html?"+code;
       };
    },
    productDetails:function(){   // 产品页产品详情
       var id = window.location.search.substring(1);
       $.ajax({
           type:"POST",
           url:"../../queryProductById",
           data:{"id":id},
           dataType:"json",
           success:function(str){
               zycfH.exception(str);
               var method,grounp;
               if(str.method=='MonthlyInterest'){
                   method = '按月付息到期还本';
               }else if(str.method=='EqualInstallment'){
                   method = '按月等额本息';
               }else if(str.method=='EqualPrincipal'){
                   method = '按月等额本金';
               }else if(str.method=='BulletRepayment'){
                   method = '一次性还本付息';
               }else if(str.method=='EqualInterest'){
                   method = '月平息';
               };
               if(str.groupId==1){
                   grounp = '新用户';
               }else if(str.groupId==0){
                   grounp = '全部用户';
               };
               $('.productTltle .right span').html(str.name);
               $('.RepaymentMethod .right span').html(method);
               $('.date .right span').html(str.months);
               $('.rate .right span').html(str.rate);
               $('.InvestmentGroup .right span').html(grounp);
               $('.minInvestAmount .right span').html(str.minInvestAmount);
               $('.maxInvestAmount .right span').html(str.maxInvestAmount);
               $('.description .right span').html(str.description);
           }
       });
        $('.subBtn input').click(function(){
            window.history.go(-1);
        });
    },
    addProduct:function(){   // 产品页新建产品
       if(window.location.search){
           var id = window.location.search.substring(1);
           $.ajax({
               type:"POST",
               url:"../../queryProductById",
               data:{"id":id},
               dataType:"json",
               success:function(str){
                   zycfH.exception(str);
                   var method,grounp;
                   if(str.method=='MonthlyInterest'){
                       method = '按月付息到期还本';
                   }else if(str.method=='EqualInstallment'){
                       method = '按月等额本息';
                   }else if(str.method=='EqualPrincipal'){
                       method = '按月等额本金';
                   }else if(str.method=='BulletRepayment'){
                       method = '一次性还本付息';
                   }else if(str.method=='EqualInterest'){
                       method = '月平息';
                   };
                   if(str.groupId==1){
                       grounp = '新用户';
                   }else if(str.groupId==0){
                       grounp = '全部用户';
                   };
                   $('.productTltle .right input').val(str.name);
                   $('.RepaymentMethod .right .select').html(method);
                   $('.date .right input').val(str.months);
                   $('.rate .right input').val(str.rate);
                   $('.InvestmentGroup .right .select').html(grounp);
                   $('.minInvestAmount .right input').val(str.minInvestAmount);
                   $('.maxInvestAmount .right input').val(str.maxInvestAmount);
                   $('.description .right textarea').val(str.description);
               }
           });
       }else{
           $('.productTltle .right input').val('');
           $('.RepaymentMethod .right .select').html('按月付息到期还本');
           $('.date .right input').val('');
           $('.rate .right input').val('');
           $('.InvestmentGroup .right .select').html('全部用户');
           $('.minInvestAmount .right input').val('');
           $('.maxInvestAmount .right input').val('');
           $('.description .right textarea').val('');
       };
       $(".subBtn input").click(function(){
           zycfH.productSave();
       });
        $('.phoTestCon .close').click(function(){
           $('#systemError').hide();
           if(currentAjax){
               currentAjax.abort();
           }else{
               return;
           }
       });
    },
    productSave:function(){   // 新建产品页里的保存按钮
       var groupId,repayMethod,url,flag = false,id;
       if(window.location.search){
           var id = window.location.search.substring(1);
           url = "../../modifyProduct";
       }else{
           url = "../../addProduct";
       };
       if($('.InvestmentGroup .select').html()=='新用户'){
           groupId = 1;
       }else if($('.InvestmentGroup .select').html()=='全部用户'){
           groupId = 0;
       };
       if($('.RepaymentMethod .right span').html()=='按月付息到期还本'){
           repayMethod = 'MonthlyInterest';
       }else if($('.RepaymentMethod .right span').html()=='按月等额本息'){
           repayMethod = 'EqualInstallment';
       }else if($('.RepaymentMethod .right span').html()=='按月等额本金'){
           repayMethod = 'EqualPrincipal';
       }else if($('.RepaymentMethod .right span').html()=='一次性还本付息'){
           repayMethod = 'BulletRepayment';
       }else if($('.RepaymentMethod .right span').html()=='月平息'){
           repayMethod = 'EqualInterest';
       };
       if(($('.productTltle .right input').val() != '') && ($('.date .right .months').val() != '') && ($('.rate .right input').val() != '')){
           flag = true;
           $.ajax({
               type:"POST",
               url:url,
               data:{"id":id,
                   "name":$('.productTltle .right input').val(),
                   "repayMethod":repayMethod,
                   "months":$('.date .right .months').val(),
                   "rate":$('.rate .right input').val(),
                   "groupId":groupId,
                   "minInvestAmount":$('.minInvestAmount .right input').val(),
                   "maxInvestAmount":$('.maxInvestAmount .right input').val(),
                   "description":$('.description .right textarea').val(),},
               dataType:"json",
               success:function(str){
                   zycfH.exception(str);
                   var oTime = null;
                   clearInterval(oTime);
                   if(str.code==1){
                       window.location.href="productList.html";
                   }else if(str.code==0){
                       $('#systemError').show();
                       var oPhoTestCon = $('#phoTest .phoTestCon');
                       var w = (document.documentElement.clientWidth-oPhoTestCon[0].offsetWidth)/2;
                       oPhoTestCon[0].style.left = w+'px';
                       oPhoTestCon[0].style.top = "120px";
                       var n = 3;
                       oTime = setInterval(function(){
                           n--;
                           $('#systemError .back strong').html(n);
                           if(n==0){
                               clearInterval(oTime);
                               $('#systemError').hide();  
                           };
                       },1000);
                       return false;
                   };
               }
           });
       };
       if($('.productTltle .right input').val() == ''){
           flag = false;
           alert('请输入产品名称！');
       }else if($('.date .right .months').val() == ''){
           flag = false;
           alert('请输入借款期限！');
       }else if($('.rate .right input').val() == ''){
           flag = false;
           alert('请输入借款利率！');
       };
    },
    projectList:function(){     // 项目管理页
    	$("#export").click(function(){
    		var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    var openLink = $("<a target='_self'></a>");
			openLink.attr('href', "../../uploadProject?startTime="+start+"&endTime="+end+"&sort=desc&timeSubmit=TIMESUBMIT");
			openLink[0].click();
    	});
        zycfH.listAjax({
            page:1,
            num:10,
            field:"timeSubmit",
            value:"TIMESUBMIT",
            sort:"desc",
            url:"../../queryAllProjects",
            callBack:function(obj){
            	zycfH.projectCallBack(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeSubmit",
			        value:"TIMESUBMIT",
			        sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../queryAllProjects",
		            callBack:function(obj){
		            	zycfH.projectCallBack(obj)
		            }
	            });
       	    };
        });
    },
    projectCallBack:function(obj){     // 项目管理页的数据
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data || !obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
        }else{
        	var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            var method,bidClass,editeClass,bidClick,editeClick,freezeClass,freezeClick;
	            if(obj.data[i].method=='MonthlyInterest'){
	                method = '按月付息到期还本';
	            }else if(obj.data[i].method=='EqualInstallment'){
	                method = '按月等额本息';
	            }else if(obj.data[i].method=='EqualPrincipal'){
	                method = '按月等额本金';
	            }else if(obj.data[i].method=='BulletRepayment'){
	                method = '一次性还本付息';
	            }else if(obj.data[i].method=='EqualInterest'){
	                method = '月平息';
	            };
              if(obj.data[i].amount != obj.data[i].surplusAmount){
                editeClass = "on";
                editeClick = "";
              }else{
                editeClass = "";
                editeClick = "zycfH.itemEdit(this)";
              };
              if(obj.data[i].status == 1 || obj.data[i].surplusAmount == 0){
                bidClass = "on";
                bidClick = "";
              }else{
                bidClass = "";
                bidClick = "zycfH.jump(this)";
              };
              if(obj.data[i].surplusAmount == 0){
                freezeClass = "on";
                freezeClick = "";
              }else{
                freezeClass = "";
                freezeClick = "zycfH.freezeThaw(this)";
              };
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a code="+obj.data[i].id+" href='projectDetail.html#"+obj.data[i].id+"' >"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].surplusAmount+"</td><td>"+obj.data[i].months+"月"+"</td><td>"+method+"</td><td>"+obj.data[i].timeSubmit+"</td><td class='operation'><a class='"+bidClass+"' code='"+obj.data[i].id+"' onclick="+bidClick+" >发标申请</a><a class='"+editeClass+"' onclick='"+editeClick+"' >编辑</a><a class='"+freezeClass+"' onclick='"+freezeClick+"' href='javascript:void(0);' code="+obj.data[i].status+">"+((obj.data[i].status==1)?"解冻":"冻结")+"</a></td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    alignCenter:function(){     // 项目管理页的弹窗居中
        var L = ($(document).width() - $('.phoTestCon').width())/2;
        $('.phoTestCon').css({'left':L,'top':'180px'});
        window.onresize = function(){
            var L = ($(document).width() - $('.phoTestCon').width())/2;
            $('.phoTestCon').css({'left':L,'top':'180px'});
        };
    },
    showProject:function(This){     // 项目管理页的标的详情
    },
    freezeThaw:function(This){      // 项目管理页的冻结解冻
       var status = $(This).attr('code');
       var code = $(This).siblings().eq(0).attr('code');
       $.ajax({
           type:"POST",
           url:"../../modifyProjectStatus",
           data:{"id":code},
           dataType:"json",
           success:function(str){
               zycfH.exception(str);
                alert(str.message);
               if(str.code == 1){
                   if(status == 0){
                       status = 1;
                       $(This).attr('code',status);
                       $(This).html('解冻');
                       $(This).siblings().eq(0).addClass('on').attr('onclick','');
                   }else if(status == 1){
                       status = 0;
                       $(This).attr('code',status);
                       $(This).html('冻结');
                       $(This).siblings().eq(0).removeClass('on').attr('onclick','zycfH.jump(this)');
                   };
               }else{
                   alert('修改失败！');
               };
           }
       });
    },
    balance:function(This){   // 满标审核结算
        var code = This.getAttribute('code');
        if($(This).attr("state") == "1"){
            $(This).attr("state","2");
            $.ajax({
                type:"POST",
                url:"../../settleLoan",
                data:{"loanId":code},
                dataType:"json",
                success:function(str){
                    zycfH.exception(str);
                    alert(str.message);
                    if(str.code == 0){
                        window.location.reload();
                    }else{
                        $(This).attr("state","1");
                    }
                },
                error:function(str){
                    $(This).attr("state","1");
                }
            });
        }else{
            alert("正在处理中，请稍后...");
        };
    },
    jump:function(This){    // 发标申请按钮
       var code = This.getAttribute('code');
       window.location.href = "../targetAdministration/newStandard.html?"+code;
    },
    //项目编辑
    itemEdit: function(obj){
        var code = $(obj).prev().attr('code');
        window.location.href = "../projectAdministration/itemEdit.html?"+code;
    },
    addBid:function(){
        var id = window.location.search.substring(1);
        var projectID;
        $(".personID .uploadImg").html("");
        $(".companyInfo .uploadImg").html("");
        $(".contract .uploadImg").html("");
        $(".assetsInfo .uploadImg").html("");
        $(".other .uploadImg").html("");
        $(".mortgaged .martgageType").html("");
        $(".productId .select").html("");
        $(".productId .right ul").html("");
        $(".guaranteeRealm .right ul").html("");

        if(window.location.hash){
          $(".header").html("编辑标的");
          $(".subBtn input").val("保存修改");
          $(".subBtn a").html("取消");
          $.ajax({
               type:"POST",
               url:"../../queryLoanById",
               data:{"id":id},
               dataType:"json",
               success:function(str){
                   zycfH.exception(str);
                   if(str.projectDtail.legalPersonPhotoUrl){
                       var LPho = str.projectDtail.legalPersonPhotoUrl.split(",");
                       for(var i=0;i<LPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+LPho[i]+" />");
                           oImg.appendTo($(".personID .uploadImg"));
                       };
                   };
                   if(str.projectDtail.enterpriseInfoPhotoUrl){
                       var EPho = str.projectDtail.enterpriseInfoPhotoUrl.split(",");
                       for(var i=0;i<EPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+EPho[i]+" />");
                           oImg.appendTo($(".companyInfo .uploadImg"));
                       };
                   };
                   if(str.projectDtail.assetsPhotoUrl){
                       var APho = str.projectDtail.assetsPhotoUrl.split(",");
                       for(var i=0;i<APho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+APho[i]+" />");
                           oImg.appendTo($(".contract .uploadImg"));
                       };
                   };
                   if(str.projectDtail.contractPhotoUrl){
                       var CPho = str.projectDtail.contractPhotoUrl.split(",");
                       for(var i=0;i<CPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+CPho[i]+" />");
                           oImg.appendTo($(".assetsInfo .uploadImg"));
                       };
                   };
                   if(str.projectDtail.othersPhotoUrl){
                       var OPho = str.projectDtail.othersPhotoUrl.split(",");
                       for(var i=0;i<OPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+OPho[i]+" />");
                           oImg.appendTo($(".other .uploadImg"));
                       };
                   };
                   var cu = str.projectDtail.corporationUserVo;
                   for(var i=0;i<cu.length;i++){
                       if(str.loan.guaranteeId == cu[i].userId){
                           $(".guaranteeRealm .select").html(cu[i].shortname);
                           $(".guaranteeRealm .select").attr('code',cu[i].userId);
                       };
                       var oLi = $("<li onclick='zycfH.corporation(this)' code="+cu[i].userId+">"+cu[i].shortname+"</li>");
                       oLi.appendTo($(".guaranteeRealm .right ul"));
                   };
                   var PT = str.projectDtail.productVos;
                   for(var i=0;i<PT.length;i++){
                       var oLi = $("<li onclick='zycfH.productTpye(this)' code="+PT[i].id+">"+PT[i].productName+"</li>");
                       oLi.appendTo($(".productId .right ul"));
                   };
                   $(".productId .select").html(str.loan.productName);
                   $(".productId .select").attr('code',str.loan.productId);
                   var MT = str.loan.mortgagedType.split(",");
                   for(var i=0;i<MT.length;i++){
                        var oIp = $("<input type='checkbox' checked/><span>"+MT[i]+"</span>");
                        oIp.appendTo($(".mortgaged .martgageType"));
                    };
                   var method;
                   if(str.loan.method=='MonthlyInterest'){
                       method = '按月付息到期还本';
                   }else if(str.loan.method=='EqualInstallment'){
                       method = '按月等额本息';
                   }else if(str.loan.method=='EqualPrincipal'){
                       method = '按月等额本金';
                   }else if(str.loan.method=='BulletRepayment'){
                       method = '一次性还本付息';
                   }else if(str.loan.method=='EqualInterest'){
                       method = '月平息';
                   };
                   if(str.loan.mortgaged==0){
                       $('.mortgaged #mortgage').attr("checked","checked");
                       $('.mortgaged .martgageType').hide();
                   }else if(str.loan.mortgaged==1){
                       $('.mortgaged #mortgage1').attr("checked","checked");
                       $('.mortgaged .martgageType').show();
                   };
                   $('.title .right input').val(str.loan.title);
                   $('.serial .right input').val(str.loan.serial);
                   $('.amount .right input').val(str.loan.amount);
                   $('.loanUserId .right input').val(str.loan.loanUserId);
                   $('.guaranteeRealm .right span').html(str.loan.guaranteeRealm);
                   $('.date .months').val(str.loan.months);
                   $('.rate .right input').val(str.loan.rate);
                   $('.addRate .right input').val(str.loan.addRate);
                   $('.groupId .right span').html(str.groupId);
                   $('.method .right span').html(method);
                   $('.minAmount .right input').val(str.loan.minAmount);
                   $('.maxAmount .right input').val(str.loan.maxAmount);
                   $('.stepAmount .right input').val(str.loan.stepAmount);
                   $('.loanGuaranteeFee .right input').val(str.loan.loanGuaranteeFee);
                   $('.loanServiceFee .right input').val(str.loan.loanServiceFee);
                   $('.loanRiskFee .right input').val(str.loan.loanRiskFee);
                   $('.LoanManageFee .right input').val(str.loan.loanManageFee);
                   $('.loanInterestFee .right input').val(str.loan.loanInterestFee);
                   $('.investInterestFee .right input').val(str.loan.investInterestFee);
                   $('.loanTime .right input').val(str.loan.loanTime);
                   $('.firmInfo .right textarea').val(str.projectDtail.project.firmInfo);
                   $('.operationRange .right textarea').val(str.projectDtail.project.operationRange);
                   $('.description .right textarea').val(str.projectDtail.project.description);
                   $('.repaySource .right textarea').val(str.projectDtail.project.repaySource);
                   $('.riskInfo .right textarea').val(str.projectDtail.project.riskInfo);
               }
           });
            $('.subBtn a').click(function(){
                window.history.go(-1);
            });
        }else{
          $(".header").html("新建标的");
          $(".subBtn input").val("提交申请");
          $(".subBtn a").html("返回项目管理");
           $.ajax({
               type:"POST",
               url:"../../queryProjectById",
               data:{"id":id},
               dataType:"json",
               success:function(str){
                   zycfH.exception(str);
                   projectID = str.project.id;
                   if(str.legalPersonPhotoUrl){
                       var LPho = str.legalPersonPhotoUrl.split(",");
                       for(var i=0;i<LPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+LPho[i]+" />");
                           oImg.appendTo($(".personID .uploadImg"));
                       };
                   };
                   if(str.enterpriseInfoPhotoUrl){
                       var EPho = str.enterpriseInfoPhotoUrl.split(",");
                       for(var i=0;i<EPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+EPho[i]+" />");
                           oImg.appendTo($(".companyInfo .uploadImg"));
                       };
                   };
                   if(str.assetsPhotoUrl){
                       var APho = str.assetsPhotoUrl.split(",");
                       for(var i=0;i<APho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+APho[i]+" />");
                           oImg.appendTo($(".contract .uploadImg"));
                       };
                   };
                   if(str.contractPhotoUrl){
                       var CPho = str.contractPhotoUrl.split(",");
                       for(var i=0;i<CPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+CPho[i]+" />");
                           oImg.appendTo($(".assetsInfo .uploadImg"));
                       };
                   };
                   if(str.othersPhotoUrl){
                       var OPho = str.othersPhotoUrl.split(",");
                       for(var i=0;i<OPho.length;i++){
                           var oImg = $("<img style='width: 200px;' src="+OPho[i]+" />");
                           oImg.appendTo($(".other .uploadImg"));
                       };
                   };
                   if(str.productVos){
                      var PT = str.productVos;
                       for(var i=0;i<PT.length;i++){
                           if(str.project.productId == PT[i].id){
                               $(".productId .select").html(PT[i].productName);
                               $(".productId .select").attr('code',str.project.productId);
                           };
                           var oLi = $("<li onclick='zycfH.productTpye(this)' code="+PT[i].id+">"+PT[i].productName+"</li>");
                           oLi.appendTo($(".productId .right ul"));
                       };
                   };
                   if(str.corporationUserVo){
                      var cu = str.corporationUserVo;
                       for(var i=0;i<cu.length;i++){
                           if(str.project.guaranteeId == cu[i].userId){
                               $(".guaranteeRealm .select").html(cu[i].shortname);
                               $(".guaranteeRealm .select").attr('code',cu[i].userId);
                           };
                           var oLi = $("<li onclick='zycfH.corporation(this)' code="+cu[i].userId+">"+cu[i].shortname+"</li>");
                           oLi.appendTo($(".guaranteeRealm .right ul"));
                       };
                   };
                   if(str.project.mortgagedType){
                      var MT = str.project.mortgagedType.split(",");
                       for(var i=0;i<MT.length;i++){
                            var oIp = $("<input type='checkbox' checked/><span>"+MT[i]+"</span>");
                            oIp.appendTo($(".mortgaged .martgageType"));
                        };
                        if(str.project.mortgaged==0){
                           $('.mortgaged #mortgage').attr("checked","checked");
                           $('.mortgaged .martgageType').hide();
                       }else if(str.project.mortgaged==1){
                           $('.mortgaged #mortgage1').attr("checked","checked");
                           $('.mortgaged .martgageType').show();
                       };
                   };
                   var method;
                   if(str.project.method=='MonthlyInterest'){
                       method = '按月付息到期还本';
                   }else if(str.project.method=='EqualInstallment'){
                       method = '按月等额本息';
                   }else if(str.project.method=='EqualPrincipal'){
                       method = '按月等额本金';
                   }else if(str.project.method=='BulletRepayment'){
                       method = '一次性还本付息';
                   }else if(str.project.method=='EqualInterest'){
                       method = '月平息';
                   };
                   $('.title .right input').val(str.project.title);
                   $('.serial .right input').val(str.project.serial);
                   $('.loanUserId .right input').val(str.project.loanUserId);
                   $('.date .months').val(str.project.months);
                   $('.rate .right input').val(str.project.rate);
                   $('.groupId .right span').html(str.groupId);
                   $('.method .right span').html(method);
                   $('.minAmount .right input').val(str.project.minAmount);
                   $('.maxAmount .right input').val(str.project.maxAmount);
                   $('.stepAmount .right input').val(str.project.stepAmount);
                   $('.loanGuaranteeFee .right input').val(str.project.loanGuaranteeFee);
                   $('.loanServiceFee .right input').val(str.project.loanServiceFee);
                   $('.loanRiskFee .right input').val(str.project.loanRiskFee);
                   $('.LoanManageFee .right input').val(str.project.loanManageFee);
                   $('.loanInterestFee .right input').val(str.project.loanInterestFee);
                   $('.investInterestFee .right input').val(str.project.investInterestFee);
                   $('.firmInfo .right textarea').val(str.project.firmInfo);
                   $('.operationRange .right textarea').val(str.project.operationRange);
                   $('.description .right textarea').val(str.project.description);
                   $('.repaySource .right textarea').val(str.project.repaySource);
                   $('.riskInfo .right textarea').val(str.project.riskInfo);
               }
           });
            $('.subBtn a').click(function(){
                window.location.href = "../projectAdministration/projectManagement.html";
            });
        };
       
        $("input[name='mortgage']").change(function(){
            if($("input[name='mortgage']:checked").val()==0){
               $('.mortgaged .martgageType').hide();
           }else if($("input[name='mortgage']:checked").val()==1){
               $('.mortgaged .martgageType').show();
           };
        });
        $('.subBtn input').click(function(){
            var repayMethod,mortgaged,groupId,Whether,arr=[];
            if($('.method .right span').html()=='按月付息到期还本'){
               repayMethod = 'MonthlyInterest';
           }else if($('.method .right span').html()=='按月等额本息'){
               repayMethod = 'EqualInstallment';
           }else if($('.method .right span').html()=='按月等额本金'){
               repayMethod = 'EqualPrincipal';
           }else if($('.method .right span').html()=='一次性还本付息'){
               repayMethod = 'BulletRepayment';
           }else if($('.method .right span').html()=='月平息'){
               repayMethod = 'EqualInterest';
           };
           if($('.groupId .right span').html() == '全部用户'){
        	   groupId = 0;
           }else if($('.productId .right span').html() == '新用户'){
        	   groupId = 1;
           };
           if($('.whetherTransfer .right span').html() == '是'){
               Whether = 1;
           }else if($('.whetherTransfer .right span').html() == '否'){
               Whether = 0;
           };
           mortgaged = $("input[name='mortgage']:checked").val();
           if(mortgaged == 1){
               $(".mortgaged .martgageType input:checked").each(function(){
                   arr.push($(this).next().html());
               });
           };
           mortgagedType = arr.join(",");
           var onlyNum = $('.serial .right input').val();
           if(window.location.hash){
              $.ajax({
                   type:"POST",
                   url:"../../modifyUnpublishedLoan",
                   data:{"id":id,
                       "title":$('.title .right input').val(),
                        "serial":$('.serial .right input').val(),
                        "amount":$('.amount .right input').val(),
                        "loanUserId":$('.loanUserId .right input').val(),
                        "guaranteeRealm":$('.guaranteeRealm .right span').html(),
                        "guaranteeId":$('.guaranteeRealm .right span').attr("code"),
                        "productId":$('.productId .right span').attr('code'),
                        "months":$('.date .right input').val(),
                        "rate":$('.rate .right input').val(),
                        "addRate":$('.addRate .right input').val(),
                        "groupId":groupId,
                        "whetherTransfer":Whether,
                        "method":repayMethod,
                        "mortgaged":mortgaged,
                        "mortgagedType":mortgagedType,
                        "minAmount":$('.minAmount .right input').val(),
                        "maxAmount":$('.maxAmount .right input').val(),
                        "stepAmount":$('.stepAmount .right input').val(),
                        "loanGuaranteeFee":$('.loanGuaranteeFee .right input').val(),
                        "loanServiceFee":$('.loanServiceFee .right input').val(),
                        "loanRiskFee":$('.loanRiskFee .right input').val(),
                        "LoanManageFee":$('.LoanManageFee .right input').val(),
                        "loanInterestFee":$('.loanInterestFee .right input').val(),
                        "investInterestFee":$('.investInterestFee .right input').val(),
                        "loanTime":$('.loanTime .right input').val(),
                        "firmInfo":$('.firmInfo .right textarea').val(),
                        "operationRange":$('.operationRange .right textarea').val(),
                        "description":$('.description .right textarea').val(),
                        "repaySource":$('.repaySource .right textarea').val(),
                        "riskInfo":$('.riskInfo .right textarea').val()
                        },
                   dataType:"json",
                   success:function(str){
                       zycfH.exception(str);
                       alert(str.message);
                       if(str.code==1){
                           window.history.go(-1);
                       }
                   }
               });
           }else{
              if((onlyNum.split('-').length>3) && ($('.amount .right input').val()!='')){
                   $.ajax({
                       type:"POST",
                       url:"../../bidApply",
                       data:{"project.id":projectID,
                           "loan.title":$('.title .right input').val(),
                            "loan.serial":$('.serial .right input').val(),
                            "loan.amount":$('.amount .right input').val(),
                            "loan.loanUserId":$('.loanUserId .right input').val(),
                            "loan.guaranteeRealm":$('.guaranteeRealm .right span').html(),
                            "loan.guaranteeId":$('.guaranteeRealm .right span').attr("code"),
                            "loan.productId":$('.productId .right span').attr('code'),
                            "loan.months":$('.date .right input').val(),
                            "loan.rate":$('.rate .right input').val(),
                            "loan.addRate":$('.addRate .right input').val(),
                            "loan.groupId":groupId,
                            "loan.whetherTransfer":Whether,
                            "loan.method":repayMethod,
                            "loan.mortgaged":mortgaged,
                            "loan.mortgagedType":mortgagedType,
                            "loan.minAmount":$('.minAmount .right input').val(),
                            "loan.maxAmount":$('.maxAmount .right input').val(),
                            "loan.stepAmount":$('.stepAmount .right input').val(),
                            "loan.loanGuaranteeFee":$('.loanGuaranteeFee .right input').val(),
                            "loan.loanServiceFee":$('.loanServiceFee .right input').val(),
                            "loan.loanRiskFee":$('.loanRiskFee .right input').val(),
                            "loan.LoanManageFee":$('.LoanManageFee .right input').val(),
                            "loan.loanInterestFee":$('.loanInterestFee .right input').val(),
                            "loan.investInterestFee":$('.investInterestFee .right input').val(),
                            "loan.loanTime":$('.loanTime .right input').val(),
                            "loan.firmInfo":$('.firmInfo .right textarea').val(),
                            "loan.operationRange":$('.operationRange .right textarea').val(),
                            "loan.description":$('.description .right textarea').val(),
                            "loan.repaySource":$('.repaySource .right textarea').val(),
                            "loan.riskInfo":$('.riskInfo .right textarea').val()
                            },
                       dataType:"json",
                       success:function(str){
                           zycfH.exception(str);
                           if(str.code==1){
                               alert(str.message);
                               $(window.parent.document).find('.sideBarNav h3').removeClass('active').siblings().slideUp();
                               $(window.parent.document).find('.sideBarNav h3').each(function(){
                                   if($(this).find('span').html() == '标的管理'){
                                       $(this).addClass('active').siblings().show();
                                   };
                               });
                               $(window.parent.document).find('.sideBarNav li li').removeClass('on');
                               $(window.parent.document).find('.sideBarNav li li').each(function(){
                                   if($(this).html() == '申请发标列表'){
                                       $(this).addClass('on');
                                   };
                               });
                               setCookie('sideNav', '申请发标列表',{"path":"/back"});
                               setCookie('URL', 'html/targetAdministration/apply.html',{"path":"/back"});
                               $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>申请发标列表</span>");
                               $(window.parent.document).find("#iframe").attr("src","html/targetAdministration/apply.html");
                           }else if(str.code==0){
                               alert(str.message);
                           };
                       }
                   });
               };
               if(onlyNum.split('-').length<=3){
                   alert("请填写正确的唯一号！");
               }else if($('.amount .right input').val()==''){
                   alert("请填写本次发标金额！");
               };
            };
        });
    },
    corporation:function(This){
        $('.guaranteeRealm .right span').html($(This).html());
        $('.guaranteeRealm .right span').attr('code',$(This).attr('code'));
        $('.guaranteeRealm .right ul').hide();
    },
    productTpye:function(This){
        $('.productType .right span').html($(This).html());
        $('.productType .right span').attr('code',$(This).attr('code'));
        $('.productType .right ul').hide();
    },
    bidApply:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeSubmit,status",
            value:"TIMESUBMIT,INITIATED",
            sort:"desc",
            url:"../../inititedLoans",
            callBack:function(obj){
            	zycfH.applyCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeSubmit,status",
		            value:"TIMESUBMIT,INITIATED",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../inititedLoans",
		            callBack:function(obj){
		            	zycfH.applyCallback(obj)
		            }
	            });
       	    };
        });
    },
    applyCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
        }else{
        	var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a code="+obj.data[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].loanUserId+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].rate+(obj.data[i].addRate?"+"+obj.data[i].addRate:"")+"</td><td>"+obj.data[i].months+"月"+"</td><td>未发布</td><td class='operation'><a code="+obj.data[i].id+" onclick='zycfH.applyRelease(this)' href='javascript:void(0);'>发标</a><a code="+obj.data[i].id+" onclick='zycfH.editBid(this)' href='javascript:void(0);'>编辑</a><a code="+obj.data[i].id+" onclick='zycfH.applyCancel(this)' href='javascript:void(0);'>取消</a></td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    bidDetail:function(This){
        $(window.parent.document).find('.sideBarNav li li').each(function(){
            if($(this).hasClass("on")){
                if($(this).html() == '标的统计'){
                    window.location.href = "../../html/targetAdministration/InvestRecord.html#"+$(This).attr('code');
                }else{
                    window.location.href = "../../html/targetAdministration/bidDetail.html#"+$(This).attr('code');
                }
            }
        });
       
    },
    applyRelease:function(This){
        window.location.href = "bidConfirm.html#"+$(This).attr('code');
    },
    editBid:function(This){
    	window.location.href = "newStandard.html?"+$(This).attr('code')+"#99";
    },
    applyCancel:function(This){
    	var code = $(This).attr("code");
    	var oContent = "<textarea class='reason' rows='8' style='width:300px;margin:0 auto;outline:none;'></textarea>";
    	var addStaff = new PopUpBox();
			addStaff.init({
				w:360,
				h:260,
				iNow:0,          // 确保一个对象只创建一次
				mark:false,
				content:oContent,
				callback:function(){zycfH.cancelBtn(code);}
			});
    },
    cancelBtn:function(code){
    	$.ajax({
            type:"POST",
            url:"../../cancleLoan",
            data:{"id":code,
                "reason":$(".reason").val()},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
            	if(str.code == 0){
            		alert(str.message);
            	}else if(str.code == 1){
            		alert(str.message);
            		$(window.parent.document).find('.sideBarNav li li').removeClass('on');
                    $(window.parent.document).find('.sideBarNav li li').each(function(){
                        if($(this).html() == '已取消列表'){
                            $(this).addClass('on');
                        };
                    });
                    setCookie('sideNav', '已取消列表',{"path":"/back"});
                    setCookie('URL', 'html/targetAdministration/cancel.html',{"path":"/back"});
                    $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>已取消列表</span>");
                    window.location.href = "cancel.html";
            	}
            	
            }
        });
    },
    investRecord:function(){
        var id = window.location.hash.substring(1);
        $.ajax({
            type:"POST",
            url:"../../queryInvestRecord",
            data:{"loanid":id},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
              if(!str.length || str.length == 0){
                $(".tableList tbody").html("<td colspan='7'>暂无数据</td>");
              }else{
                for(var i=0;i<str.length;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td>"+(i+1)+"</td><td>"+str[i].loanName+"</td><td style='cursor:pointer;' code="+str[i].userId+" onclick='zycfH.lookFundRecord(this)' >"+str[i].userName+"</td><td>"+str[i].loginName+"</td><td>"+str[i].amount+"</td><td>"+str[i].time+"</td><td>"+str[i].investStatus+"</td>");
                  oTr.appendTo($(".tableList tbody"));
                };
              };
            }
        });
    },
    bidConfirm:function(){
        var code = window.location.hash.substring(1);
        var method,mortgaged,whetherTransfer,groupId;
        $.ajax({
            type:"POST",
            url:"../../queryLoanById",
            data:{"id":code},
            dataType:"json",
            success:function(str){
                zycfH.exception(str);
                if(str.projectDtail.legalPersonPhotoUrl){
                    var LPho = str.projectDtail.legalPersonPhotoUrl.split(",");
                    for(var i=0;i<LPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+LPho[i]+" />");
                        oImg.appendTo($(".personID .uploadImg"));
                    };
                };
                if(str.projectDtail.enterpriseInfoPhotoUrl){
                    var EPho = str.projectDtail.enterpriseInfoPhotoUrl.split(",");
                    for(var i=0;i<EPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+EPho[i]+" />");
                        oImg.appendTo($(".companyInfo .uploadImg"));
                    };
                };
                if(str.projectDtail.assetsPhotoUrl){
                    var APho = str.projectDtail.assetsPhotoUrl.split(",");
                    for(var i=0;i<APho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+APho[i]+" />");
                        oImg.appendTo($(".contract .uploadImg"));
                    };
                };
                if(str.projectDtail.contractPhotoUrl){
                    var CPho = str.projectDtail.contractPhotoUrl.split(",");
                    for(var i=0;i<CPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+CPho[i]+" />");
                        oImg.appendTo($(".assetsInfo .uploadImg"));
                    };
                };
                if(str.projectDtail.othersPhotoUrl){
                    var OPho = str.projectDtail.othersPhotoUrl.split(",");
                    for(var i=0;i<OPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+OPho[i]+" />");
                        oImg.appendTo($(".other .uploadImg"));
                    };
                };
                if(str.loan.method=='MonthlyInterest'){
                    method = '按月付息到期还本';
                }else if(str.loan.method=='EqualInstallment'){
                    method = '按月等额本息';
                }else if(str.loan.method=='EqualPrincipal'){
                    method = '按月等额本金';
                }else if(str.loan.method=='BulletRepayment'){
                    method = '一次性还本付息';
                }else if(str.loan.method=='EqualInterest'){
                    method = '月平息';
                };
                if(str.loan.mortgaged == 0){
                	mortgagedType='无';
                }else if(str.loan.mortgaged == 1){
                	mortgagedType=str.loan.mortgagedType;
                };
                if(str.loan.whetherTransfer == 0){
                    whetherTransfer='否';
                }else if(str.loan.whetherTransfer == 1){
                    whetherTransfer='是';
                };
                if(str.loan.groupId == 0){
                    groupId='全部用户';
                }else if(str.loan.groupId == 1){
                    groupId='新用户';
                };
                $(".title .right span").html(str.loan.title);
                $(".serial .right span").html(str.loan.serial);
                $(".amount .right span").html(str.loan.amount);
                $(".loanUserId .right span").html(str.loan.loanUserId);
                $(".guaranteeRealm .right span").html(str.loan.guaranteeRealm);
                $(".productId .right span").html(str.loan.productName);
                $(".date .right span").html(str.loan.months+"月");
                $(".rate .right span").html(str.loan.rate);
                $(".addRate .right span").html(str.loan.addRate);
                $(".groupId .right span").html(groupId);
                $(".Whether .right span").html(whetherTransfer);
                $(".method .right span").html(method);
                $(".mortgaged .right span").html(mortgagedType);
                $(".minAmount .right span").html(str.loan.minAmount);
                $(".maxAmount .right span").html(str.loan.maxAmount);
                $(".stepAmount .right span").html(str.loan.stepAmount);
                $(".termOfValidity .right span").html(str.loan.loanTime);
                $(".loanGuaranteeFee .right span").html(str.loan.loanGuaranteeFee);
                $(".loanServiceFee .right span").html(str.loan.loanServiceFee);
                $(".loanRiskFee .right span").html(str.loan.loanRiskFee);
                $(".LoanManageFee .right span").html(str.loan.loanManageFee);
                $(".loanInterestFee .right span").html(str.loan.loanInterestFee);
                $(".investInterestFee .right span").html(str.loan.investInterestFee);
                $(".firmInfo .right span").html(str.projectDtail.project.firmInfo);
                $(".operationRange .right span").html(str.projectDtail.project.operationRange);
                $(".description .right span").html(str.projectDtail.project.description);
                $(".repaySource .right span").html(str.projectDtail.project.repaySource);
                $(".riskInfo .right span").html(str.projectDtail.project.riskInfo);
            }
        });
        $(".subBtn .bidEdit").click(function(){
           window.location.href = "newStandard.html?"+code+"#99";
        });
        var one = true;
        if(one){
            $(".subBtn .atOnce").click(function(){
                one = false;
                $.ajax({
                    type:"POST",
                    url:"../../bidPublished",
                    data:{"loan.id":code},
                    dataType:"json",
                    success:function(str){
                        zycfH.exception(str);
                        one = true;
                        if(str.code == 0){
                           alert(str.message);
                        }else if(str.code == 1){
                            $(window.parent.document).find('.sideBarNav li li').removeClass('on');
                            $(window.parent.document).find('.sideBarNav li li').each(function(){
                                if($(this).html() == '进行中列表'){
                                    $(this).addClass('on');
                                };
                            });
                            setCookie('sideNav', '进行中列表',{"path":"/back"});
                            setCookie('URL', 'html/targetAdministration/onGoing.html',{"path":"/back"});
                            $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>进行中列表</span>");
                            window.location.href = "onGoing.html";
                        };
                    }
                });
            });
        };
        $(".subBtn .adjust").click(function(){
            $.ajax({
                type:"POST",
                url:"../../modifyLoanUnplaned",
                data:{"id":code},
                dataType:"json",
                success:function(str){
                    zycfH.exception(str);
                    if(str.code == 0){
                        alert(str.message);
                    }else if(str.code == 1){
                        $(window.parent.document).find('.sideBarNav li li').removeClass('on');
                        $(window.parent.document).find('.sideBarNav li li').each(function(){
                            if($(this).html() == '需调度发标列表'){
                                $(this).addClass('on');
                            };
                        });
                        setCookie('sideNav', '需调度发标列表',{"path":"/back"});
                        setCookie('URL', 'html/targetAdministration/needDispatch.html',{"path":"/back"});
                        $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>需调度发标列表</span>");
                        window.location.href = "needDispatch.html";
                    };
                }
            });
        });
    },
    needDispatch:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeSubmit,status",
            value:"TIMESUBMIT,UNPLANED",
            sort:"desc",
            url:"../../unplanedLoans",
            callBack:function(obj){
            	zycfH.needDisCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeSubmit,status",
		            value:"TIMESUBMIT,UNPLANED",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../unplanedLoans",
		            callBack:function(obj){
		            	zycfH.needDisCallback(obj)
		            }
	            });
       	    };
        });
    },
    needDisCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
        }else{
	    	var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a code="+obj.data[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].loanUserId+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].rate+(obj.data[i].addRate?"+"+obj.data[i].addRate:"")+"</td><td>"+obj.data[i].months+"月"+"</td><td class='operation'><a code="+obj.data[i].id+" onclick='zycfH.timing(this)' href='javascript:void(0);'>定时发布</a><a code="+obj.data[i].id+" onclick='zycfH.editBid(this)' href='javascript:void(0);'>编辑</a><a code="+obj.data[i].id+" onclick='zycfH.applyCancel(this)' href='javascript:void(0);'>取消</a></td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    timing:function(This){
        var id = $(This).attr('code');
        $('#systemError').show();
        $("#systemError .OK").click(function(){
            if($("#systemError .laydate-icon").val()!=''){
                $.ajax({
                    type:"POST",
                    url:"../../timerBidPublished",
                    data:{"loanId":id,
                        "publishedTime":$("#systemError .laydate-icon").val()},
                    dataType:"json",
                    success:function(str){
                        zycfH.exception(str);
                        var time = null;
                        clearInterval(time);
                        if(str.code == 0){
                            alert(str.message);
                        }else if(str.code == 1){
                            $('#systemError .phoTestCon').html($("<p>操作成功</p>"));
                            var n = 2;
                            time = setInterval(function(){
                                n--;
                                if(n<=0){
                                    clearInterval(time);
                                    $('#systemError').hide();
                                    $(window.parent.document).find('.sideBarNav li li').removeClass('on');
                                    $(window.parent.document).find('.sideBarNav li li').each(function(){
                                        if($(this).html() == '已调度发标列表'){
                                            $(this).addClass('on');
                                        };
                                    });
                                    setCookie('sideNav', '已调度发标列表',{"path":"/back"});
                                    setCookie('URL', 'html/targetAdministration/alreadyDispatch.html',{"path":"/back"});
                                    $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>已调度发标列表</span>");
                                    window.location.href = "alreadyDispatch.html";
                                };
                            },1000);
                        };
                    }
                });
            }else{
                alert(1)
            };
        });
        $("#systemError .cancel").click(function(){
            $('#systemError').hide();
        });
    },
    alreadyDispatch:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeOpen,status",
            value:"TIMEOPEN,SCHEDULED",
            sort:"asc",
            url:"../../scheduledLoans",
            callBack:function(obj){
            	zycfH.alreadyCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeOpen,status",
		            value:"TIMEOPEN,SCHEDULED",
		            sort:"asc",
	                startTime:start,
	                endTime:end,
	                url:"../../scheduledLoans",
		            callBack:function(obj){
		            	zycfH.alreadyCallback(obj)
		            }
	            });
       	    };
        });
    },
    alreadyCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        function countDown(str){
            clearInterval(str.time);
            str.oTr = $("<tr></tr>");
            str.time = null;
            str.t = parseInt(parseInt(str.divTime)/1000);
            if(str.t<=0){
            	str.t = 0;
            };
            str.countDown = Math.floor(str.t/86400)+'天'+Math.floor(str.t%86400/3600)+'时'+Math.floor(str.t%86400%3600/60)+'分'+str.t%60+'秒';
            str.time = setInterval(function(){
    		        str.t-=1;
                if(str.t<=0){
                    str.t=0;
                    clearInterval(str.time);
                };
    		        str.countDown = Math.floor(str.t/86400)+'天'+Math.floor(str.t%86400/3600)+'时'+Math.floor(str.t%86400%3600/60)+'分'+str.t%60+'秒';
    		        str.oTr.children('td').eq(8).html(str.countDown);
    		    },1000);
            str.oTr.html("<td><a code="+str.id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+str.title+"</a></td><td>"+str.guaranteeRealm+"</td><td>"+str.userName+"</td><td>"+str.loanUserId+"</td><td>"+str.amount+"</td><td>"+str.rate+(str.addRate?"+"+str.addRate:"")+"</td><td>"+str.months+"月"+"</td><td>"+str.timeOpen+"</td><td>"+str.countDown+"</td><td class='operation'><a code="+str.id+" onclick='zycfH.editBid(this)' href='javascript:void(0);'>编辑</a><a code="+str.id+" onclick='zycfH.applyCancel(this)' href='javascript:void(0);'>取消</a></td>");
            str.oTr.appendTo($(".tableList tbody"));
        };
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
        }else{
	        var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            countDown(obj.data[i]);
	        };
        };
    },
    onGoing:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeOpen,status",
            value:"TIMEOPEN,OPENED",
            sort:"desc",
            url:"../../opendLoans",
            callBack:function(obj){
            	zycfH.onGoingCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeOpen,status",
		            value:"TIMEOPEN,OPENED",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../opendLoans",
		            callBack:function(obj){
		            	zycfH.onGoingCallback(obj)
		            }
	            });
       	    };
        });
    },
    onGoingCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
        }else{
	        var countDown,status;
	        var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            if(obj.data[i].status == 'OPENED'){
	                status = '进行中';
	            };
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a code="+obj.data[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].loanUserId+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].rate+(obj.data[i].addRate?"+"+obj.data[i].addRate:"")+"</td><td>"+obj.data[i].months+"月"+"</td><td>"+obj.data[i].planing+"%"+"</td><td>"+status+"</td><td class='operation'><a code="+obj.data[i].id+" onclick='zycfH.flowBtn(this)' href='javascript:void(0);'>流标</a></td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    flowBtn:function(This){
        if(confirm("确定要流标吗？")){
            var id = $(This).attr('code');
            var oContent = "<textarea class='reason' rows='8' style='width:300px;margin:0 auto;outline:none;'></textarea>";
    	    	var addStaff = new PopUpBox();
    				addStaff.init({
    					w:360,
    					h:260,
    					iNow:0,          // 确保一个对象只创建一次
    					mark:false,
    					content:oContent,
    					callback:function(){zycfH.failedBid(id);}
    				});
        };
    },
    failedBid:function(obj){
    	$.ajax({
            type:"POST",
            url:"../../failedBid",
            data:{"id":obj,"reason":$(".reason").val()},
            dataType:"json",
            success:function(str){
                zycfH.exception(str);
                if(str.code == 1){
                    alert(str.message);
                    $(window.parent.document).find('.sideBarNav li li').removeClass('on');
                    $(window.parent.document).find('.sideBarNav li li').each(function(){
                        if($(this).html() == '已流标列表'){
                            $(this).addClass('on');
                        };
                    });
                    setCookie('sideNav', '已流标列表',{"path":"/back"});
                    setCookie('URL', 'html/targetAdministration/Flow.html',{"path":"/back"});
                    $(window.parent.document).find('.navList p strong').html("<span>标的管理</span>").append("<span>已流标列表</span>");
                    window.location.href = "Flow.html";
                }else if(str.code == 0){
                    alert(str.message);
                };
            }
        });
    },
    flow:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeFailed,status",
            value:"TIMEFAILED,FAILED",
            sort:"desc",
            url:"../../failedLoans",
            callBack:function(obj){
            	zycfH.flowCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeFailed,status",
		            value:"TIMEFAILED,FAILED",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../failedLoans",
		            callBack:function(obj){
		            	zycfH.flowCallback(obj)
		            }
	            });
       	    };
        });
    },
    flowCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
        }else{
	    	var countDown;
	        var len = obj.data.length;
	        for(var i=0;i<len;i++){
	            var oDate = new Date(parseInt(obj.data[i].nowTime)).toLocaleString().substr(0,19);
	            var oTr = $("<tr></tr>");
	            oTr.html("<td><a code="+obj.data[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].loanUserId+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].rate+(obj.data[i].addRate?"+"+obj.data[i].addRate:"")+"</td><td>"+obj.data[i].months+"月"+"</td><td>"+obj.data[i].planing+"</td><td>"+obj.data[i].timeFailed+"</td><td>"+obj.data[i].reason+"</td>");
	            oTr.appendTo($(".tableList tbody"));
	        };
        };
    },
    cancel:function(){
    	zycfH.listAjax({
            page:1,
            num:10,
            field:"timeCancle,status",
            value:"TIMECANCLE,CANCELED",
            sort:"desc",
            url:"../../cancledLoans",
            callBack:function(obj){
            	zycfH.cancelCallback(obj)
            }
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	zycfH.listAjax({
	                page:1,
	                num:10,
	                field:"timeCancle,status",
		            value:"TIMECANCLE,CANCELED",
		            sort:"desc",
	                startTime:start,
	                endTime:end,
	                url:"../../cancledLoans",
		            callBack:function(obj){
		            	zycfH.cancelCallback(obj)
		            }
	            });
       	    };
        });
    },
    cancelCallback:function(obj){
    	$(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.data.length || obj.data.length<1){
        	$(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
        }else{
	    	var len = obj.data.length;
            var countDown,status;
            for(var i=0;i<len;i++){
                if(obj.data[i].status == 'CANCELED'){
                    status = '已取消';
                };
                var oTr = $("<tr></tr>");
                oTr.html("<td><a code="+obj.data[i].id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+obj.data[i].title+"</a></td><td>"+obj.data[i].guaranteeRealm+"</td><td>"+obj.data[i].userName+"</td><td>"+obj.data[i].loanUserId+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].rate+(obj.data[i].addRate?"+"+obj.data[i].addRate:"")+"</td><td>"+obj.data[i].months+"月"+"</td><td>"+obj.data[i].status+"</td><td>"+obj.data[i].reason+"</td>");
                oTr.appendTo($(".tableList tbody"));
            };
        };
    },
    bidList:function(){
    	var strField = "opened,finished,settled,cleared,timeOpen",value = "OPENED,FINISHED,SETTLED,CLEARED,TIMEOPEN";
    	$.ajax({
            type:"POST",
            url:"../../queryAllProductName",
            data:{},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
            	$('.bidSearchC ul').html("");
            	$('.bidSearch .bidSearchC .selectedList').html("");
            	var oLi1 = $("<li>全部</li>");
          		oLi1.appendTo($('.bidSearchC ul'));
              	for(var i=0;i<str.length;i++){
              		var oLi = $("<li></li>");
              		oLi.html(str[i]);
              		oLi.appendTo($('.bidSearchC ul'));
              	};
              	$('.bidSearch .bidSearchC .selectedList').click(function(){
  		            $('.bidSearchC ul').toggle();
  		        });
  		        $('.bidSearchC li').click(function(){
  		            $('.bidSearch .bidSearchC .selectedList').html($(this).html());
  		            $('.bidSearchC ul').hide();
  		        });
            }
        });
        $.ajax({
            type:"POST",
            url:"../../queryAllCorporationUserByCondition",
            data:{"type":"GUARANTEE"},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
            	$('.bidSearchT ul').html("");
            	$('.bidSearch .bidSearchT .selectedList').html("");
            	var oLi1 = $("<li>全部</li>");
          		oLi1.appendTo($('.bidSearchT ul'));
              	for(var i=0;i<str.length;i++){
              		var oLi = $("<li></li>");
              		oLi.html(str[i].shortname);
              		oLi.appendTo($('.bidSearchT ul'));
              	};
              	$('.bidSearch .bidSearchT .selectedList').click(function(){
  		            $('.bidSearchT ul').toggle();
  		        });
  		        $('.bidSearchT li').click(function(){
  		            $('.bidSearch .bidSearchT .selectedList').html($(this).html());
  		            $('.bidSearchT ul').hide();
  		        });
            }
        });
        $('.bidHead a').click(function(){
            $('.bidHead a').removeClass('active');
            $(this).addClass('active');
            var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	var productName=$('.bidSearch .bidSearchC .selectedList').html();
       	    	var gruaranteeRealm=$('.bidSearch .bidSearchT .selectedList').html();
       	    	var title=$("#bidNameSearch").val();
       	    	if(productName=="" || productName=="全部"){
       	    		productName="xxx";
       	    	}
       	    	if(gruaranteeRealm=="" || gruaranteeRealm=="全部"){
       	    		gruaranteeRealm="xxx";
       	    	}
       	    	if(title=="" || title=="&nbsp;"){
       	    		title="xxx";
       	    	}
       	    	if($(this).html()=="全部"){
       	    		strField = "opened,finished,settled,cleared,timeOpen,productName,guaranteeRealm,title";
       	    		value = "OPENED,FINISHED,SETTLED,CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="进行中"){
       	    		strField = "opened,timeOpen,productName,guaranteeRealm,title";
       	    		value = "OPENED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="满标"){
       	    		strField = "finished,timeOpen,productName,guaranteeRealm,title";
       	    		value = "FINISHED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="还款中"){
       	    		strField = "settled,timeOpen,productName,guaranteeRealm,title";
       	    		value = "SETTLED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="已还清"){
       	    		strField = "cleared,timeOpen,productName,guaranteeRealm,title";
       	    		value = "CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	zycfH.getBidData({
	                page:1,
	                num:10,
	                startTime:start,
	                endTime:end,
	                field:strField,
                    value:value
	            });
       	    };
        });
        $("#searchTime").click(function(){
       	    var start,end;
       	    if($("#dataStar").val() == ""){
       	    	start = new Date("1970/1/01").getTime();
       	    }else{
       	    	start = new Date($("#dataStar").val()).getTime();
       	    };
       	    if($("#dataEnd").val() == ""){
       	    	end = new Date("2130/1/01").getTime();
       	    }else{
       	    	end = new Date($("#dataEnd").val()).getTime();
       	    };
       	    if(end<start){
       	    	alert("结束时间必须大于开始时间")
       	    }else{
       	    	var productName=$('.bidSearch .bidSearchC .selectedList').html();
       	    	var gruaranteeRealm=$('.bidSearch .bidSearchT .selectedList').html();
       	    	var title=$("#bidNameSearch").val();
       	    	if(productName=="" || productName=="全部"){
       	    		productName="xxx";
       	    	}
       	    	if(gruaranteeRealm=="" || gruaranteeRealm=="全部"){
       	    		gruaranteeRealm="xxx";
       	    	}
       	    	if(title=="" || title=="&nbsp;"){
       	    		title="xxx";
       	    	}
       	    	$(".bidHead a").each(function(){
       	    		if($(this).hasClass("active")){
       	    			if($(this).html()=="全部"){
		       	    		strField = "opened,finished,settled,cleared,timeOpen,productName,guaranteeRealm,title";
		       	    		value = "OPENED,FINISHED,SETTLED,CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
		       	    	};
		       	    	if($(this).html()=="进行中"){
		       	    		strField = "opened,timeOpen,productName,guaranteeRealm,title";
		       	    		value = "OPENED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
		       	    	};
		       	    	if($(this).html()=="满标"){
		       	    		strField = "finished,timeOpen,productName,guaranteeRealm,title";
		       	    		value = "FINISHED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
		       	    	};
		       	    	if($(this).html()=="还款中"){
		       	    		strField = "settled,timeOpen,productName,guaranteeRealm,title";
		       	    		value = "SETTLED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
		       	    	};
		       	    	if($(this).html()=="已还清"){
		       	    		strField = "cleared,timeOpen,productName,guaranteeRealm,title";
		       	    		value = "CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
		       	    	};
       	    		};
       	    	});
       	    	zycfH.getBidData({
	                page:1,
	                num:10,
	                startTime:start,
	                endTime:end,
	                field:strField,
                    value:value
	            });
       	    };
        });
        zycfH.getBidData({
            page:1,
            num:10,
            startTime:new Date("1970/1/01").getTime(),
            endTime:new Date("2120/1/01").getTime(),
            field:strField,
            value:value
        });
        $("#export").click(function(){
    		  var start,end,strField,value;
     	    if($("#dataStar").val() == ""){
     	    	start = new Date("1970/1/01").getTime();
     	    }else{
     	    	start = new Date($("#dataStar").val()).getTime();
     	    };
     	    if($("#dataEnd").val() == ""){
     	    	end = new Date("2130/1/01").getTime();
     	    }else{
     	    	end = new Date($("#dataEnd").val()).getTime();
     	    };
       	  var productName=$('.bidSearch .bidSearchC .selectedList').html();
   	    	var gruaranteeRealm=$('.bidSearch .bidSearchT .selectedList').html();
   	    	var title=$("#bidNameSearch").val();
   	    	if(productName=="" || productName=="全部"){
   	    		productName="xxx";
   	    	}
   	    	if(gruaranteeRealm=="" || gruaranteeRealm=="全部"){
   	    		gruaranteeRealm="xxx";
   	    	}
   	    	if(title=="" || title=="&nbsp;"){
   	    		title="xxx";
   	    	};
   	    	$(".bidHead a").each(function(){
   	    		if($(this).hasClass("active")){
   	    			if($(this).html()=="全部"){
       	    		strField = "opened,finished,settled,cleared,timeOpen,productName,guaranteeRealm,title";
       	    		value = "OPENED,FINISHED,SETTLED,CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="进行中"){
       	    		strField = "opened,timeOpen,productName,guaranteeRealm,title";
       	    		value = "OPENED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="满标"){
       	    		strField = "finished,timeOpen,productName,guaranteeRealm,title";
       	    		value = "FINISHED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="还款中"){
       	    		strField = "settled,timeOpen,productName,guaranteeRealm,title";
       	    		value = "SETTLED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
       	    	if($(this).html()=="已还清"){
       	    		strField = "cleared,timeOpen,productName,guaranteeRealm,title";
       	    		value = "CLEARED,TIMEOPEN"+","+productName+","+gruaranteeRealm+","+title;
       	    	};
   	    		};
   	    	});
          window.open("../../uploadPublishedLoans?startTime="+start+"&endTime="+end+"&field="+strField+"&value="+value)
    	});
    },
    getBidData:function(opt){
        var settings = {
            page:1,
            num:10,
            startTime:new Date("1970/1/01").getTime(),
            endTime:new Date("2120/1/01").getTime(),
            field:"opened,finished,settled,cleared,timeOpen",
            value:"OPENED,FINISHED,SETTLED,CLEARED,TIMEOPEN"
        };
        $.extend(settings,opt);
        $.ajax({
            type:"POST",
            url:"../../publishedLoans",
            data:{"start":settings.page,
                "length":10,
                "field":settings.field,
                "value":settings.value,
                "sort":"desc",
                "startTime":settings.startTime,
                "endTime":settings.endTime},
            dataType:"json",
            success:function(str){
                zycfH.exception(str);
                zycfH.publishedData(str);
                if(str.totalPage>1){
                    page({
                        id:'paging',
                        nowNum:settings.page,
                        allNum:str.totalPage,
                        callBack:function(now,all){
                        	var start,end;
                       	    if($("#dataStar").val() == ""){
                       	    	start = settings.startTime;
                       	    }else{
                       	    	start = new Date($("#dataStar").val()).getTime();
                       	    };
                       	    if($("#dataEnd").val() == ""){
                       	    	end = settings.endTime;
                       	    }else{
                       	    	end = new Date($("#dataEnd").val()).getTime();
                       	    };
                            zycfH.getBidData({
                                page:now,
                                num:10,
                                startTime:start,
                                endTime:end,
                                field:settings.field,
                                value:settings.value
                            });
                        }
                    });
                };
            }
        });
    },
    publishedData:function(obj){
    	$("#totalAmount").html(obj.recordsFiltered);
        $("#paging").html("");
        $(".tableList tbody").html("");
        if(obj.data.length == 0){
        	$(".tableList tbody").html("<td colspan='8'>暂无数据</td>");
        }else{
        	for(var i=0;i<obj.data.length;i++){
	            var num = (obj.start-1)*obj.length+i+1;
	            bidData(obj.data[i],num);
	        };
        };
        function bidData(str,num){
        	var oTr = $("<tr></tr>");
	        var status;
	        var timeSettled='未结算';
	        if(str.status=='SCHEDULED'){
	            status = '待发售';
	        }else if(str.status=='OPENED'){
	            status = '投资';
	        }else if(str.status=='FINISHED'){
	            status = '已满标';
	        }else if(str.status=='SETTLED'){
	            status = '还款中';
	            timeSettled=str.timeSettled;
	        }else if(str.status=='CLEARED'){
	            status = '已还清';
	            timeSettled=str.timeSettled;
	        };
	        oTr.html("<td>"+num+"</td><td><a code="+str.id+" onclick='zycfH.bidDetail(this)' href='javascript:void(0);'>"+str.title+"</a></td><td>"+str.productName+"</td><td>"+str.guaranteeRealm+"</td><td>"+str.amount+"</td><td>"+str.zycfAvailableAmount+"</td><td>"+str.umpAvailableAmount+"</td><td>"+status+"</td><td>"+str.timeOpen+"</td><td>"+timeSettled+"</td>");
	        oTr.appendTo($(".tableList tbody"));
        };
        
    },
    bidDetailData:function(){
      var id = window.location.hash.substring(1);
      if(id){
        $.ajax({
            type:"POST",
            url:"../../queryLoanById",
            data:{"id":id},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
              $(".bidDetailPicList").html("");
              var groupId,whetherTransfer,method;
              if(str.loan.groupId == 0){
                groupId = "全部";
              }else{
                groupId = "新用户";
              };
              if(str.loan.whetherTransfer == 0){
                whetherTransfer = "不可转";
              }else{
                whetherTransfer = "可转";
              };
              if(str.loan.method=='MonthlyInterest'){
                  method = '按月付息到期还本';
              }else if(str.loan.method=='EqualInstallment'){
                  method = '按月等额本息';
              }else if(str.loan.method=='EqualPrincipal'){
                  method = '按月等额本金';
              }else if(str.loan.method=='BulletRepayment'){
                  method = '一次性还本付息';
              }else if(str.loan.method=='EqualInterest'){
                  method = '月平息';
              };
              $(".bidDetailHead .title").html(str.loan.title);
              $(".bidDetailHead .loanId").html(str.loan.id);
              $(".bidDetailHead .productType").html(str.loan.productName);

              $(".bidDataList .amount111").text(str.loan.amount);
              $(".bidDataList .loanUserId").css("cursor","pointer").text(str.loan.loanUserId).attr("code",str.loan.loanUserId).click(function(){
                    zycfH.lookFundRecord($(this));
                });;
              $(".bidDataList .guaranteeRealm").text(str.loan.guaranteeRealm);
              $(".bidDataList .months").text(str.loan.months);
              $(".bidDataList .rate").text(str.loan.rate+"%"+(str.loan.addRate?"+"+str.loan.addRate+"%":""));
              $(".bidDataList .method").text(method);
              $(".bidDataList .groupId").text(groupId);
              $(".bidDataList .whetherTransfer").text(whetherTransfer);
              $(".bidDataList .mortgagedType").text(str.loan.mortgagedType);
              $(".bidDataList .minAmount").text(str.loan.minAmount);
              $(".bidDataList .maxAmount").text(str.loan.maxAmount);
              $(".bidDataList .stepAmount").text(str.loan.stepAmount);
              $(".bidDataList .loanGuaranteeFee").text(str.loan.loanGuaranteeFee+"%");
              $(".bidDataList .loanServiceFee").text(str.loan.loanServiceFee+"%");
              $(".bidDataList .loanRiskFee").text(str.loan.loanRiskFee+"%");
              $(".bidDataList .LoanManageFee").text(str.loan.loanManageFee+"%");
              $(".bidDataList .loanInterestFee").text(str.loan.loanInterestFee+"%");
              $(".bidDataList .investInterestFee").text(str.loan.investInterestFee+"%");

              $(".firmInfo span").html(str.projectDtail.project.firmInfo);
              $(".operationRange span").html(str.projectDtail.project.operationRange);
              $(".description span").html(str.projectDtail.project.description);
              $(".repaySource span").html(str.projectDtail.project.repaySource);
              $(".riskInfo span").html(str.projectDtail.project.riskInfo);

              if(str.projectDtail.legalPersonPhotoUrl){
                    var LPho = str.projectDtail.legalPersonPhotoUrl.split(",");
                    for(var i=0;i<LPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+LPho[i]+" />");
                        oImg.appendTo($(".bidDetailPicList"));
                    };
                };
                if(str.projectDtail.enterpriseInfoPhotoUrl){
                    var EPho = str.projectDtail.enterpriseInfoPhotoUrl.split(",");
                    for(var i=0;i<EPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+EPho[i]+" />");
                        oImg.appendTo($(".bidDetailPicList"));
                    };
                };
                if(str.projectDtail.assetsPhotoUrl){
                    var APho = str.projectDtail.assetsPhotoUrl.split(",");
                    for(var i=0;i<APho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+APho[i]+" />");
                        oImg.appendTo($(".bidDetailPicList"));
                    };
                };
                if(str.projectDtail.contractPhotoUrl){
                    var CPho = str.projectDtail.contractPhotoUrl.split(",");
                    for(var i=0;i<CPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+CPho[i]+" />");
                        oImg.appendTo($(".bidDetailPicList"));
                    };
                };
                if(str.projectDtail.othersPhotoUrl){
                    var OPho = str.projectDtail.othersPhotoUrl.split(",");
                    for(var i=0;i<OPho.length;i++){
                        var oImg = $("<img style='width: 200px;' src="+OPho[i]+" />");
                        oImg.appendTo($(".bidDetailPicList"));
                    };
                };
                $(".bidDataList .loanUserId").css("cursor","pointer").click(function(){
                    zycfH.lookFundRecord( this );
                });
            }
        });
      }else{
        alert("未找到当前标的，请重新选择标的");
      };
      $(".bidDetailRepayment tbody").html("");
      $(".bidDetailTabWrap .bidDetailTab li").click(function(){
        $(".bidDetailTabWrap .bidDetailTab li").removeClass("active").eq($(this).addClass("active"));
        if($(this).html() == "还款计划"){
          $(".bidDetailRepayment").show();
          $(".bidDetailInvestment").hide();
          $(".bidDetailRepayment tbody").html("");
          repaymentPlan();
        }else if($(this).html() == "投资记录"){
          $(".bidDetailRepayment").hide();
          $(".bidDetailInvestment").show();
          $(".bidDetailInvestment tbody").html("");
          $.ajax({
              type:"POST",
              url:"../../queryInvestRecord",
              data:{"loanid":id},
              dataType:"json",
              success:function(str){
                zycfH.exception(str);
                if(!str.length || str.length == 0){
                  $(".bidDetailInvestment tbody").html("<td colspan='3'>暂无数据</td>");
                }else{
                  for(var i=0;i<str.length;i++){
                    var oTr = $("<tr></tr>");
                    oTr.html("<td style='cursor:pointer;' code="+str[i].userId+" onclick='zycfH.lookFundRecord(this)' >"+str[i].userName+"</td><td>"+str[i].amount+"</td><td>"+str[i].time+"</td><td>"+str[i].investStatus+"</td>");
                    oTr.appendTo($(".bidDetailInvestment tbody"));
                  };
                };
              }
          });
        };
      });
      repaymentPlan();
      function repaymentPlan(){
        $.ajax({
            type:"POST",
            url:"../../queryByLoanId",
            data:{"loanId":id},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
              if(!str.length || str.length == 0){
                $(".bidDetailRepayment tbody").html("<td colspan='4'>暂无数据</td>");
              }else{
                for(var i=0;i<str.length;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td>"+str[i].currentperiod+"</td><td>"+str[i].duedate+"</td><td>"+str[i].amount+"</td><td>"+str[i].status+"</td>");
                  oTr.appendTo($(".bidDetailRepayment tbody"));
                };
              };
            }
        });
      };
    },
    lookFundRecord:function(This){
      $(window.parent.document).find('.sideBarNav li li').removeClass('on');
      $(window.parent.document).find('.sideBarNav li li').each(function(){
          if($(this).html() == '用户列表'){
              $(this).addClass('on');
          };
      });
      $(window.parent.document).find('.navList p strong').html("<span>用户管理</span>").append("<span>用户列表</span>");
      window.location.href = "../usersList/userDetail.html#"+$(This).attr("code");
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
                zycfH.exception(str);
              	settings.callBack(str);
              	if(str.totalPage>1){
      			    	page({
      						id:settings.id,
      						nowNum:settings.page,
      						allNum:str.totalPage,
      						callBack:function(now,all){
      							zycfH.maListAjax({
      								page:now,
      							    num:settings.num,
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
    },
    staffList:function(){
      $(".searchResult").click(function(){
          $(".list").toggle();
          $(document).click(function(){
             $(".list").hide();
          });
          return false;
      });
      $(".list").click(function(ev){
        var ev = ev || window.event;
        if(ev.stopPropagation) { //W3C阻止冒泡方法  
            ev.stopPropagation();  
        } else {  
            ev.cancelBubble = true; //IE阻止冒泡方法  
        } ; 
      });
      $.ajax({
          type:"POST",
          url:"../../queryAllOrganizations",
          data:{},
          success:function(str){
              creatSelector(str);
          }
      });
      function creatSelector(obj){
          var oLi = $("<li></li>");
          var oH3 = $("<h3 id="+obj.id+">"+obj.name+"</h3>");
          oH3.appendTo(oLi);
          if(!obj.children || !obj.children.length || obj.children.length<1){
            return;
          }else{
            creatChild(obj.children,oLi);
          };
          oLi.appendTo($(".list"));
          var oTime = null;
          $(".list li").on("mouseenter",function(){
            $(this).children("ul").show();
          });
          $(".list li").on("mouseleave",function(){
            $(this).children("ul").hide();
          });
          $(".list h3").click(function(){
            $(".searchResult").html($(this).html());
            $(".list").hide();
            zycfH.maListAjax({
                page:1,
                num:10,
                id:"paging",
                params:{"orgId":$(this).attr("id")},
                url:"../../empList",
                callBack:function(obj){
                  zycfH.staffData(obj)
                }
            });
          });
      };
      function creatChild(obj,oParent){
          var oUl = $("<ul></ul>");
          for(var i=0,len=obj.length;i<len;i++){
            var oLi = $("<li></li>");
            var oH3 = $("<h3 id="+obj[i].id+">"+obj[i].name+"</h3>");
            oH3.appendTo(oLi);
            oLi.appendTo(oUl);
            oUl.appendTo(oParent);
            if( obj[i].children.length || obj[i].children.length>0){
              arguments.callee(obj[i].children,oLi); 
            };
          };
      };
    	var oLogin = "<div id='addStaffFrame'><p><span>登录名：</span><input name='loginName' type='text' /></p><p><span>姓名：</span><input name='name' type='text' /></p><p><span>身份证号：</span><input name='idnumber' type='text' /></p><p><span>手机号码：</span><input name='mobile' type='text' /></p><p><span>唯一号：</span><input name='empId' type='text' /></p><div class='mechanismBar'><span>所属机构：</span><span class='mechanism' code='' ></span><ul class='nav'></ul></div></div>"
    	$(".headBtn").click(function(){
    		var addStaff = new PopUpBox();
  			addStaff.init({
  				w:400,
  				h:360,
  				iNow:0,          // 确保一个对象只创建一次
  				title : '添加员工',
  				opacity:0.7,
  				mark:false,
  				tBar:true,
  				content:oLogin,
  				callback:function(){zycfH.fnAddRole();}
  			});
        $(".mechanism").click(function(){
            $(".nav").toggle();
            $(document).click(function(){
               $(".nav").hide();
            });
            return false;
        });
        $(".nav").click(function(ev){
          var ev = ev || window.event;
          if(ev.stopPropagation) { //W3C阻止冒泡方法  
              ev.stopPropagation();  
          } else {  
              ev.cancelBubble = true; //IE阻止冒泡方法  
          } ; 
        });
        $.ajax({
            type:"POST",
            url:"../../queryAllOrganizations",
            data:{},
            success:function(str){
                creatSelect(str);
            }
        });
        function creatSelect(obj){
            var oLi = $("<li></li>");
            var oH3 = $("<h3 id="+obj.id+">"+obj.name+"</h3>");
            oH3.appendTo(oLi);
            if(!obj.children || !obj.children.length || obj.children.length<1){
              return;
            }else{
              creatChildren(obj.children,oLi);
            };
            oLi.appendTo($(".nav"));
            var oTime = null;
            $(".nav li").on("mouseenter",function(){
              $(this).children("ul").show();
            });
            $(".nav li").on("mouseleave",function(){
              $(this).children("ul").hide();
            });
            $(".nav h3").click(function(){
              $(".mechanism").html($(this).html());
              $(".mechanism").attr("code",$(this).attr("id"));
              $(".nav").hide();
              return false;
            });
        };
        function creatChildren(obj,oParent){
            var oUl = $("<ul></ul>");
            for(var i=0,len=obj.length;i<len;i++){
              var oLi = $("<li></li>");
              var oH3 = $("<h3 id="+obj[i].id+">"+obj[i].name+"</h3>");
              oH3.appendTo(oLi);
              oLi.appendTo(oUl);
              oUl.appendTo(oParent);
              if( obj[i].children.length || obj[i].children.length>0){
                arguments.callee(obj[i].children,oLi); 
              };
            };
        };
  			return false;
    	});
        zycfH.maListAjax({
            page:1,
            num:10,
            id:"paging",
            params:{"orgId":""},
            url:"../../empList",
            callBack:function(obj){
            	zycfH.staffData(obj)
            }
        });
    },
    staffData:function(obj){
    	$(".staffTable tbody").html("");
    	$("#paging").html("");
    	if(obj.results.length == 0){
    		$(".staffTable tbody").html("<td colspan='7'>暂无数据</td>");
    	}else{
    		for(var i=0;i<obj.results.length;i++){
        		var oTr = $("<tr></tr>");
		        oTr.html("<td><a code="+obj.results[i].id+" href='staffDetail.html#"+obj.results[i].loginName+"' >"+obj.results[i].name+"</a></td><td>"+obj.results[i].loginName+"</td><td>"+obj.results[i].empId+"</td><td>"+obj.results[i].mobile+"</td><td>"+obj.results[i].regDate+"</td><td>"+(obj.results[i].lastDate?obj.results[i].lastDate:"")+"</td><td class='operation'><a href='javascript:void(0);' code="+obj.results[i].loginName+" onclick='zycfH.editStaff(this)'>编辑</a><a href='javascript:void(0);' code="+obj.results[i].id+" onclick='zycfH.roleDisabled(this)'>禁用</a></td>");
		        oTr.appendTo($(".staffTable tbody"));
        	};
    	};
    },
    editStaff:function(This){
      window.location.href = "staffDetail.html#"+$(This).attr("code");
    },
    roleDisabled:function(This){
      $.ajax({
          type:"POST",
          url:"../../emplpyeeBan",
          data:{"id":$(This).attr("code")},
          success:function(str){
              alert(str.message);
              if(str.code == 1){
                  $(This).parent().parent().remove();
              };
          }
      });
    },
    fnAddRole:function(){
      if($("input[name='loginName']").val() != "" && $("input[name='name']").val() != "" && checkCard($("input[name='idnumber']").val()) && checkMobile($("input[name='mobile']").val()) ){
          $.ajax({
              type:"POST",
              url:"../../addEmployee",
              data:{"loginName":$("input[name='loginName']").val(),
                  "name":$("input[name='name']").val(),
                  "idnumber":$("input[name='idnumber']").val(),
                  "mobile":$("input[name='mobile']").val(),
                  "empId":$("input[name='empId']").val(),
                  "orgId":$(".mechanism").attr("code")},
              dataType:"json",
              success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 0){
                  window.location.reload();
                };
              }
          });
      };
      if($("input[name='loginName']").val() == ""){
          alert("登录名不能为空");
      }else if($("input[name='name']").val() == ""){
          alert("姓名不能为空");
      }else if(!checkCard($("input[name='idnumber']").val())){
          alert("请输入正确身份证号码");
      }else if(!checkMobile($("input[name='mobile']").val())){
          alert("请输入正确手机号码");
      }
    },
    staffDetail:function(){
      var loginName = window.location.hash.substring(1);
      var staffID = "";
      $.ajax({
          type:"POST",
          url:"../../getUserRoles",
          data:{"loginName":loginName},
          dataType:"json",
          success:function(str){
            zycfH.exception(str);
            staffID = str.employee.id;
            $(".systemRole .selecetList li").html("");
            $(".loginName .fontColor").html(str.employee.loginName);
            $(".empId .fontColor").html(str.employee.empId);
            $(".mobile input").val(str.employee.mobile);
            $(".idnumber .fontColor").html(str.employee.idnumber);
            $(".lastlogindate .fontColor").html(str.employee.lastDate);
            $(".staff.fontColor.mechanism").html(str.employee.orgName);
            $(".staff.fontColor.mechanism").attr("code",str.employee.orgId);
            if(str.userRoles.length>0){
              $(".systemRole .staff").html(str.userRoles[0].name);
              $(".systemRole .staff").attr("code",str.userRoles[0].id);
            }else{
              $(".systemRole .staff").html("&nbsp");
              $(".systemRole .staff").attr("code","");
            };
            for(var i=0;i<str.roles.length;i++){
              var oLi = $("<li code="+str.roles[i].id+">"+str.roles[i].name+"</li>");
              oLi.appendTo($(".systemRole .selecetList"));
            };
            $(".systemRole .selecetList li").click(function(){
              $(".systemRole .staff").attr("code",$(this).attr("code")).html($(this).html());
              $(".systemRole .selecetList").hide();
            });
          }
      });
      $(".systemRole .staff").click(function(){
        $(".systemRole .selecetList").toggle();
        $(document).click(function(){
          $(".systemRole .selecetList").hide();
        });
        return false;
      });
      $(".save").click(function(){
        $.ajax({
            type:"POST",
            url:"../../dispatchEmpRole",
            data:{"empId":staffID,"roleId":$(".systemRole .staff").attr("code"),"orgId":$(".mechanism").attr("code")},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
              alert(str.message);
              if(str.code == 1){
                window.location.reload();
              };
            }
        });
      });

      $(".mechanism").click(function(){
          $(".list").toggle();
          $(document).click(function(){
             $(".list").hide();
          });
          return false;
      });
      $(".list").click(function(ev){
        var ev = ev || window.event;
        if(ev.stopPropagation) { //W3C阻止冒泡方法  
            ev.stopPropagation();  
        } else {  
            ev.cancelBubble = true; //IE阻止冒泡方法  
        } ; 
      });
      $.ajax({
          type:"POST",
          url:"../../queryAllOrganizations",
          data:{},
          success:function(str){
              creatSelector(str);
          }
      });
      function creatSelector(obj){
          var oLi = $("<li></li>");
          var oH3 = $("<h3 id="+obj.id+">"+obj.name+"</h3>");
          oH3.appendTo(oLi);
          if(!obj.children || !obj.children.length || obj.children.length<1){
            return;
          }else{
            creatChild(obj.children,oLi);
          };
          oLi.appendTo($(".list"));
          var oTime = null;
          $(".list li").on("mouseenter",function(){
            $(this).children("ul").show();
          });
          $(".list li").on("mouseleave",function(){
            $(this).children("ul").hide();
          });
          $(".list h3").click(function(){
            $(".mechanism").html($(this).html());
            $(".mechanism").attr("code",$(this).attr("id"));
            $(".list").hide();
          });
      };
      function creatChild(obj,oParent){
          var oUl = $("<ul></ul>");
          for(var i=0,len=obj.length;i<len;i++){
            var oLi = $("<li></li>");
            var oH3 = $("<h3 id="+obj[i].id+">"+obj[i].name+"</h3>");
            oH3.appendTo(oLi);
            oLi.appendTo(oUl);
            oUl.appendTo(oParent);
            if( obj[i].children.length || obj[i].children.length>0){
              arguments.callee(obj[i].children,oLi); 
            };
          };
      };
    },
    roleSecurity:function(){
    	$.ajax({
    		type:"POST",
    		url:"../../authRoleInit",
    		dataType:"json",
    		success:function(str){
          zycfH.exception(str);
    			fnRoleDate(str);   // 角色详情和唯一号列表
    			$(".roleList .roleDetailed").html("");  // 角色列表
    			if(str.roles.length == 0){
    				$(".roleList .roleDetailed").html("<li>暂无数据</li>");
    			}else{
    				for(var i=0;i<str.roles.length;i++){
	    				rolesListData(str.roles[i]);
	    			};
    			};
    			$(".roleDetailed li").each(function(){
    				if(!$(this).hasClass("active")){
    					$(".roleDetailed li").eq(0).addClass("active");
    				};
    			});
    			$(".roleList h3").click(function(){
    				$(this).siblings().toggle();
    			});
    			$(".roleList .roleDetailed li").click(function(){  // 点击角色列表里的子项
		    		$(".roleList .roleDetailed li").removeClass("active");
		    		$(this).addClass("active");
		    		$.ajax({
			    		type:"POST",
			    		url:"../../getRoleAuthsDetails",
			    		dataType:"json",
			    		data:{"id":$(this).attr("code")},
			    		success:function(str){
                zycfH.exception(str);
			    			fnRoleDate(str);
			    		}
			    	});
		    	});
    		}
    	});
      $(".roleDetail .roleSave").click(function(){        // 保存角色权限
          var arr = [],pindex;
          $(".roleDetailData li input:checked").each(function(){
              arr.push($(this).parent().attr("pindex"));
          });
          pindex = arr.join(",");
          $.ajax({
              type:"POST",
              url:"../../updateRoleAuths",
              dataType:"json",
              data:{"role.id":$(".roleDetailed li.active").attr("code"),"priviliges":pindex},
              success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 1){
                  window.location.reload();
                }
              }
          });
      });
      function fnRoleDate(obj){
        	$(".roleDetail .roleDetailData").html("");  // 角色详情
    			if(obj.auths.length == 0){
    				$(".roleDetail .roleDetailData").html("<li>暂无数据</li>");
    			}else{
        			for(var i in obj.auths){
        				roleDetailedData(obj.auths[i]);
        			};
    			};
    			$(".memberList tbody").html("");  // 唯一号列表
    			if(obj.emps.length == 0){
    				$(".memberList tbody").html("<td colspan='3'>暂无数据</td>");
    			}else{
    				for(var i=0;i<obj.emps.length;i++){
        				memberListData(obj.emps[i]);
        			};
    			};
        };
    	function rolesListData(obj){     // 角色列表
    		var oLi = $("<li code="+obj.id+">"+obj.name+"</li>");
    		oLi.appendTo($(".roleList .roleDetailed"));
    	};
    	function memberListData(obj){    // 唯一号列表
    		var oTr = $("<tr></tr>");
	        oTr.html("<td>"+obj.name+"</td><td>"+obj.empId+"</td><td>"+obj.loginName+"</td>");
	        oTr.appendTo($(".memberList tbody"));
    	};
    	function roleDetailedData(obj){   // 角色详情
    		var oLi = $("<li></li>");
    		oLi.html("<h3 class='powersList'>"+obj[0].cato+"</h3>");
    		var oUl = $("<ul class='powersListDetailed'></ul>")
    		if(obj.length == 0){
				oUl.html("<li>暂无数据</li>");
			}else{
				for(var i=0;i<obj.length;i++){
					var mLi = $("<li pindex="+obj[i].pindex+"></li>");
					if(obj[i].flag){
						mLi.html("<input type='checkbox' checked='checked' name='user'/><label for=''>"+obj[i].name+"</label>");
					}else{
						mLi.html("<input type='checkbox' name='user'/><label for=''>"+obj[i].name+"</label>");
					};
					mLi.appendTo(oUl);
				};
			};
			oUl.appendTo(oLi);
			oLi.appendTo($(".roleDetail .roleDetailData"));
    	};
    	$(".newRole").click(function(){
    		window.location.href = "newRole.html"
    	});
    },
    newRole:function(){
    	$.ajax({
    		type:"POST",
    		url:"../../addRoleAuths",
    		dataType:"json",
    		success:function(str){
          zycfH.exception(str);
          $(".newRoleSecurity").html("");  // 角色详情
          for(var i in str){
            var oP = $("<p class='powers'>"+i+"</p>");
            var oDiv = $("<div class='powersCheckBox'></div>");
            for(var j=0;j<str[i].length;j++){
                var mLi = $("<span class='clear' pindex="+str[i][j].pindex+"></span>");
                if(str[i][j].flag){
                  mLi.html("<input type='checkbox' checked='checked' name='user'/><label for=''>"+str[i][j].name+"</label>");
                }else{
                  mLi.html("<input type='checkbox' name='user'/><label for=''>"+str[i][j].name+"</label>");
                };
                mLi.appendTo(oDiv);
            };
            oP.appendTo($(".newRoleSecurity"));
            oDiv.appendTo($(".newRoleSecurity"));
          };
        }
    	});
      $(".headBtn").click(function(){
          if($(".roleName .right input").val() == ""){
              alert("角色名称不能为空");
              return;
          }
          var arr = [],pindex;
          $(".newRoleSecurity input:checked").each(function(){
              arr.push($(this).parent().attr("pindex"));
          });
          pindex = arr.join(",");
          $.ajax({
              type:"POST",
              url:"../../addRole",
              dataType:"json",
              data:{"role.name":$(".roleName .right input").val(),"priviliges":pindex,"role.description":$(".roleDetail .right textarea").val()},
              success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 0){  // 成功
                  window.location.href = "roleSecurity.html";
                }
              }
          });
      });
    },
    // 对账管理
    RechargeRecord:function(URL,fn){          
      zycfH.listAjax({
          page:1,
          num:10,
          field:"ORDERID",
          value:"xxx",
          sort:"desc",
          url:URL,
          callBack:function(obj){
            fn(obj);
          }
      });
      $("#searchTime").click(function(){
          var start,end;
          if($("#dataStar").val() == ""){
            start = new Date("1970/1/01").getTime();
          }else{
            start = new Date($("#dataStar").val()).getTime();
          };
          if($("#dataEnd").val() == ""){
            end = new Date("2130/1/01").getTime();
          }else{
            end = new Date($("#dataEnd").val()).getTime();
          };
          if(end<start){
            alert("结束时间必须大于开始时间")
          }else{
            zycfH.listAjax({
                page:1,
                num:10,
                field:"ORDERID",
                value:$("#fuzzyQuery").val(),
                sort:"desc",
                startTime:start,
                endTime:end,
                url:URL,
              callBack:function(obj){
                fn(obj);
              }
            });
          };
      });
    },
    settleExport:function(settle){     // 对账管理里的导出
      $(".export").click(function(){
        var start,end;
        if($("#dataStar").val() == ""){
          start = new Date("1970/1/01").getTime();
        }else{
          start = new Date($("#dataStar").val()).getTime();
        };
        if($("#dataEnd").val() == ""){
          end = new Date("2130/1/01").getTime();
        }else{
          end = new Date($("#dataEnd").val()).getTime();
        };;
        var openLink = $("<a target='_self'></a>");
        openLink.attr('href', "../../"+settle+"?startTime="+start+"&endTime="+end);
        openLink[0].click();
      });
    },
    rRecordCallback:function(obj){    // 充值对账
      $(".tableList tbody").html("");
      $("#paging").html("");
      if(!obj.data.length || obj.data.length<1){
        $(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
      }else{
        var len = obj.data.length;
        for(var i=0;i<len;i++){
            var pageNum = (obj.start-1)*10+i+1;
            var oTr = $("<tr></tr>");
            oTr.html("<td>"+pageNum+"</td><td>"+obj.data[i].orderid+"</td><td>"+obj.data[i].accountname+"</td><td>"+obj.data[i].mobile+"</td><td>"+obj.data[i].realName+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].transactionid+"</td><td>"+obj.data[i].settledate+"</td><td>"+obj.data[i].settletime+"</td>");
            oTr.appendTo($(".tableList tbody"));
        };
      };
    },
    tRecordCallback:function(obj){       // 标的对账
      $(".tableList tbody").html("");
      $("#paging").html("");
      if(!obj.data.length || obj.data.length<1){
        $(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
      }else{
        var len = obj.data.length;
        for(var i=0;i<len;i++){
            var pageNum = (obj.start-1)*10+i+1;
            var oTr = $("<tr></tr>");
            oTr.html("<td>"+pageNum+"</td><td>"+obj.data[i].orderid+"</td><td>"+obj.data[i].title+"</td><td>"+obj.data[i].outaccountid+"</td><td>"+obj.data[i].inaccountid+"</td><td>"+obj.data[i].action+"</td><td>"+obj.data[i].transfertype+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].transactionid+"</td><td>"+obj.data[i].settledate+"</td>");
            oTr.appendTo($(".tableList tbody"));
        };
      };
    },
    qTransferCallback:function(obj){         // 转账对账
      $(".tableList tbody").html("");
      $("#paging").html("");
      if(!obj.data || !obj.data.length || obj.data.length<1){
        $(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
      }else{
        var len = obj.data.length;
        for(var i=0;i<len;i++){
            var pageNum = (obj.start-1)*10+i+1;
            var oTr = $("<tr></tr>");
            oTr.html("<td>"+pageNum+"</td><td>"+obj.data[i].orderid+"</td><td>"+obj.data[i].outName+"</td><td>"+obj.data[i].outaccountid+"</td><td>"+obj.data[i].inName+"</td><td>"+obj.data[i].inaccountid+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].transactionid+"</td><td>"+obj.data[i].settledate+"</td><td>"+obj.data[i].settletime+"</td>");
            oTr.appendTo($(".tableList tbody"));
        };
      };
    },
    qWithdrawCallback:function(obj){      // 提现对账
      $(".tableList tbody").html("");
      $("#paging").html("");
      if(!obj.data.length || obj.data.length<1){
        $(".tableList tbody").html("<td colspan='9'>暂无数据</td>");
      }else{
        var len = obj.data.length;
        for(var i=0;i<len;i++){
            var pageNum = (obj.start-1)*10+i+1;
            var oTr = $("<tr></tr>");
            oTr.html("<td>"+pageNum+"</td><td>"+obj.data[i].orderid+"</td><td>"+obj.data[i].mobile+"</td><td>"+obj.data[i].realName+"</td><td>"+obj.data[i].amount+"</td><td>"+obj.data[i].fee+"</td><td>"+obj.data[i].state+"</td><td>"+obj.data[i].transactionid+"</td><td>"+obj.data[i].recordDate+"</td>");
            oTr.appendTo($(".tableList tbody"));
        };
      };
    },
    recManageCallback:function(obj){      // 对账文件管理
      $(".tableList tbody").html("");
      $("#paging").html("");
      if(!obj.data.length || obj.data.length<1){
        $(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
      }else{
        var len = obj.data.length;
        for(var i=0;i<len;i++){
            var pageNum = (obj.start-1)*10+i+1;
            var oTr = $("<tr></tr>");
            oTr.html("<td>"+pageNum+"</td><td>"+obj.data[i].settleDate+"</td><td>"+obj.data[i].settleType+"</td><td>"+obj.data[i].status+"</td><td>"+obj.data[i].applyTime+"</td>");
            oTr.appendTo($(".tableList tbody"));
        };
      };
    },
    settleLoad:function(){    // 对账下载
      $(".accountLoad .selectedList").click(function(){
        $(".accountLoad ul").toggle();
        $(document).click(function(){
          $(".accountLoad ul").hide();
        })
        return false;
      });
      $(".accountLoad li").click(function(){
        $(".accountLoad .selectedList").html($(this).html());
        $(".accountLoad .selectedList").attr("code",$(this).attr("code"));
        $(".accountLoad ul").hide();
      });
      $("#searchTime").click(function(){
        var maxInput = new Date($("#dataEnd").val()).getTime();    // 最大值输入框
        var minInput = new Date($("#dataStar").val()).getTime();   // 最小值输入框
        var maxTime = new Date(new Date().getTime()-24*60*60*1000);   // 当前时间的前一天
        var minTime = new Date(new Date($("#dataEnd").val()).getTime()-24*60*60*1000*31);
        if(maxInput<=maxTime && minInput<=maxInput && minInput>=minTime && $("#dataStar").val()!="" && $("#dataEnd").val()!=""){    // 最大值输入框应小于或等于（当前时间的前一天），最小输入框应小于最大输入框，并且大于最大输入框的7天
          $.ajax({
            type:"POST",
            url:"../../downloadSettleFile",
            data:{"number":$(".accountLoad .selectedList").attr("code"),"startDate":$("#dataStar").val(),"endDate":$("#dataEnd").val()},
            dataType:"json",
            success:function(str){
              zycfH.exception(str);
              alert(str.message);
            }
          });
        }else if(maxInput>maxTime){
          alert("最大时间不能大于当前时间的前一天");
        }else if(minInput>maxInput){
          alert("最小时间应小于最大时间");
        }else if(minInput<minTime){
          alert("最小时间与最大时间间隔不能大于31天");
        }else if($("#dataStar").val()==""){
          alert("最小时间不能为空");
        }else if($("#dataEnd").val()==""){
          alert("最大时间不能为空");
        }
      });
    },
    //个人资金记录
    fundRecord:function(){   
      $(".selectWrap .selectedList").click(function(){
          $(".selectWrap ul").toggle();
          $(document).click(function(){
              $(".selectWrap ul").hide();
          });
          return false;
      });  
      $(".selectWrap li").click(function(){
          $(".selectWrap .selectedList").html($(this).html()).attr("proId",$(this).attr("proId"));
          $(".selectWrap ul").hide();
          return false;
      });    
      zycfH.maListAjax({
          page:1,
          num:10,
          id:"paging",
          params:{"start":$("#dataStar").val(),"end":$("#dataEnd").val(),"type":$(".selectWrap .selectedList").attr("proId")},
          url:"../../queryPlatFormRecord",
          callBack:function(obj){
            fundRecordData(obj)
          }
      });
      $("#searchTime").click(function(){
          zycfH.maListAjax({
              page:1,
              num:10,
              id:"paging",
              params:{"start":$("#dataStar").val(),"end":$("#dataEnd").val(),"type":$(".selectWrap .selectedList").attr("proId")},
              url:"../../queryPlatFormRecord",
              callBack:function(obj){
                fundRecordData(obj)
              }
          });
      });
      function fundRecordData(obj){
          $(".tableList tbody").html("");
          $("#paging").html("");
          if(!obj.results || !obj.results.length || obj.results.length<1){
            $(".tableList tbody").html("<td colspan='8'>暂无数据</td>");
          }else{
            var len = obj.results.length;
            for(var i=0;i<len;i++){
                var pageNum = (obj.pageNo-1)*10+i+1;
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+pageNum+"</td><td>"+obj.results[i].orderId+"</td><td>"+obj.results[i].mobile+"</td><td>"+obj.results[i].realName+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].strType+"</td><td>"+obj.results[i].strStatus+"</td><td>"+obj.results[i].strDate+"</td>");
                oTr.appendTo($(".tableList tbody"));
            };
          };
      };
      $(".export").click(function(){
          window.open("../../uploadPlatFormRecord?params[type]="+$(".selectWrap .selectedList").attr("proId")+"&params[start]="+$("#dataStar").val()+"&params[end]="+$("#dataEnd").val());
      });
    },
    userGroup:function(){
      $.ajax({
          type:"POST",
          url:"../../queryByUserGroup",
          dataType:"json",
          data:{},
          success:function(str){
            zycfH.exception(str);
            userGroupData(str);
          }
      });
      function userGroupData(obj){
        $(".tableList tbody").html("");
        for(var i=0;i<obj.length;i++){
          var oTr = $("<tr></tr>");
          oTr.html("<td><a href='userGroupInfo.html#"+obj[i].name+","+obj[i].description+","+obj[i].id+"' >"+obj[i].name+"</a></td><td>"+obj[i].description+"</td><td>"+obj[i].timecreated+"</td><td class='operation'><a onclick='zycfH.editUserGroup(this)' code="+obj[i].id+" href='javascript:void(0);'>编辑</a></td>");
          oTr.appendTo($(".tableList tbody"));
        }
      };
      $(".export").click(function(){
          var oLogin = "<div id='addStaffFrame'><p><span>用户组名称</span><input name='name' type='text' /></p><p><span>描述</span><textarea name='description' rows='3' style='vertical-align:middle;display:inline-block;width:196px;'></textarea></p></div>"
          var addStaff = new PopUpBox();
          addStaff.init({
            w:400,
            h:240,
            iNow:0,          // 确保一个对象只创建一次
            title : '添加用户组',
            mark:false,
            tBar:true,
            content:oLogin,
            callback:function(){zycfH.addUserGroup("addUserGroup");}
          });
          return false;
      });
    },
    addUserGroup:function(str,ID){
      if($("input[name='name']").val() != ""){
        var id = "";
        if(ID){
          id=ID;
        };
        $.ajax({
            type:"POST",
            url:"../../"+str,
            dataType:"json",
            data:{"name":$("input[name='name']").val(),"description":$("textarea[name='description']").val(),"id":id},
            success:function(str){
              zycfH.exception(str);
              alert(str.message)
              if(str.code == 1){
                window.location.reload();
              }
            }
        });
      };
    },
    editUserGroup:function(This){
        var oLogin = "<div id='addStaffFrame'><p><span>用户组名称</span><input name='name' type='text' value="+$(This).parent().siblings().eq(0).find("a").html()+" /></p><p><span>描述</span><textarea name='description' rows='3' style='vertical-align:middle;display:inline-block;width:196px;'>"+$(This).parent().siblings().eq(1).html()+"</textarea></p></div>"
        var addStaff = new PopUpBox();
        addStaff.init({
          w:400,
          h:240,
          iNow:1,          // 确保一个对象只创建一次
          title : '编辑用户组',
          mark:false,
          tBar:true,
          content:oLogin,
          callback:function(){zycfH.addUserGroup("modifyUserGroup",$(This).attr("code"));}
        });
        return false;
    },
    userGroupInfo:function(){
       var arr = window.location.hash.substring(1).split(",");
       var userId = "";
       $(".bidHead strong").html(arr[0]);
       $(".bidHead span").html(arr[1]);
       zycfH.listAjax({
          page:1,
          num:10,
          field:"groupid",
          value:arr[2],
          sort:"desc",
          url:"../../queryByGroupId",
          callBack:function(obj){
            userGroupInfoCall(obj);
          }
      });
      $("#searchTime").click(function(){
            var selectParam = ($("#fuzzyQuery").val()=="")?"xxx":$("#fuzzyQuery").val();
            zycfH.listAjax({
                page:1,
                num:10,
                field:"groupid,name",
                value:arr[2]+","+selectParam,
                sort:"desc",
                url:"../../likeQueryUser",
                callBack:function(obj){
                  userGroupInfoCall(obj);
                }
            });
      });
      function userGroupInfoCall(obj){
          $(".tableList tbody").html("");
          $("#paging").html("");
          if(!obj.data.length || obj.data.length<1){
            $(".tableList tbody").html("<td colspan='6'>暂无数据</td>");
          }else{
            var len = obj.data.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+obj.data[i].mobile+"</td><td>"+obj.data[i].loginname+"</td><td>"+obj.data[i].name+"</td><td>"+obj.data[i].referralMobile+"</td><td>"+obj.data[i].registerdate+"</td><td>"+obj.data[i].lastlogindate+"</td>");
                oTr.appendTo($(".tableList tbody"));
            };
          };
      };
      $("#export").click(function(){
          var oLogin = "<div id='addStaffFrame'><p><input name='name' type='text' value='' /><span id='search' style='text-align:center;cursor:pointer;background-color:#38afff;border-radius:4px;color:#fff;margin-left:6px;'>查询</span></p><p><span style='width:100px'>真实姓名</span><span class='name' style='text-align:left;'></span></p><p><span style='width:100px'>身份证号</span><span class='idnumber' style='text-align:left;'></span></p><p><span style='width:100px'>手机号码</span><span class='mobile' style='text-align:left;'></span></p><p><span style='width:100px'>是否开户</span><span class='orNot' style='text-align:left;'></span></p><p><span style='width:100px'>无密还款协议</span><span class='agreement' style='text-align:left;'></span></p></div>"
          var addStaff = new PopUpBox();
          addStaff.init({
            w:400,
            h:380,
            iNow:1,          // 确保一个对象只创建一次
            mark:false,
            title : '添加用户', 
            content:oLogin,
            callback:function(){giveUserGroup();}
          });
          $("#search").click(function(){
              if($("input[name='name']").val() != ""){
                  $.ajax({
                    type:"POST",
                    url:"../../queryUserByMobile",
                    dataType:"json",
                    data:{"mobile":$("input[name='name']").val()},
                    success:function(str){
                      zycfH.exception(str);
                      if(str.code == "UNKNOW_EXCEPTION"){
                      }else{
                        userId = str.id;
                        $(".name").html(str.name);
                        $(".idnumber").html(str.idnumber);
                        $(".mobile").html(str.mobile);
                        $(".orNot").html(str.authentication);
                        $(".agreement").html(str.name);
                      }
                    }
                  });
              }
          });
      });
      function giveUserGroup(){
          $.ajax({
              type:"POST",
              url:"../../modifyUser",
              dataType:"json",
              data:{"id":userId,"groupid":arr[2]},
              success:function(str){
                  zycfH.exception(str);
                  alert(str.message)
                  if(str.code == 1){
                    window.location.reload();
                  }
              }
          });
      };
    },
    institutions:function(){
      $.ajax({
          type:"POST",
          url:"../../queryOrganizations",
          dataType:"json",
          data:{},
          success:function(str){
            zycfH.exception(str);
            institutionsData(str);
          }
      });
      function institutionsData(obj){
        $(".tableList tbody").html("");
        for(var i=0;i<obj.length;i++){
          var oTr = $("<tr></tr>");
          oTr.html("<td>"+obj[i].name+"</td><td>"+obj[i].description+"</td><td>"+new Date(parseInt(obj[i].createTime,10)).toLocaleDateString()+"</td><td class='operation'><a onclick='zycfH.editinstitutions(this)' code="+obj[i].id+" href='javascript:void(0);'>编辑</a><a  href='institutionsInfo.html#"+obj[i].name+","+obj[i].description+","+obj[i].id+"'>查看</a></td>");
          oTr.appendTo($(".tableList tbody"));
        }
      };
      $(".export").click(function(){
          var oLogin = "<div id='addStaffFrame'><p><span>机构名称</span><input name='name' type='text' /></p><p><span>描述</span><textarea name='description' rows='3' style='vertical-align:middle;display:inline-block;width:196px;'></textarea></p></div>"
          var addStaff = new PopUpBox();
          addStaff.init({
            w:400,
            h:240,
            iNow:0,          // 确保一个对象只创建一次
            title : '添加机构',
            mark:false,
            tBar:true,
            content:oLogin,
            callback:function(){zycfH.addUserGroup("addOrganization");}
          });
          return false;
      });
    },
    editinstitutions:function(This){
        var oLogin = "<div id='addStaffFrame'><p><span>机构名称</span><input name='name' type='text' value="+$(This).parent().siblings().eq(0).html()+" /></p><p><span>描述</span><textarea name='description' rows='3' style='vertical-align:middle;display:inline-block;width:196px;'>"+$(This).parent().siblings().eq(1).html()+"</textarea></p></div>"
        var addStaff = new PopUpBox();
        addStaff.init({
          w:400,
          h:240,
          iNow:1,          // 确保一个对象只创建一次
          title : '编辑机构',
          mark:false,
          tBar:true,
          content:oLogin,
          callback:function(){zycfH.addUserGroup("modifyOrganization",$(This).attr("code"));}
        });
        return false;
    },
    institutionsInfo:function(){
      var arr = window.location.hash.substring(1).split(",");
       var userId = "";
       $(".bidHead strong").html(arr[0]);
       $(".bidHead span").html(arr[1]);
       zycfH.listAjax({
          page:1,
          num:10,
          field:"organizationId",
          value:arr[2],
          sort:"desc",
          url:"../../queryByOrganization",
          callBack:function(obj){
            institutionsInfoCall(obj);
          }
      });
      $("#searchTime").click(function(){
            var start,end;
            if($("#dataStar").val() == ""){
              start = new Date("1970/1/01").getTime();
            }else{
              start = new Date($("#dataStar").val()).getTime();
            };
            if($("#dataEnd").val() == ""){
              end = new Date("2130/1/01").getTime();
            }else{
              end = new Date($("#dataEnd").val()).getTime();
            };
            if(end<start){
              alert("结束时间必须大于开始时间")
            }else{
              zycfH.listAjax({
                  page:1,
                  num:10,
                  field:"organizationId",
                  value:arr[2],
                  sort:"desc",
                  startTime:start,
                  endTime:end,
                  url:"../../queryByOrganization",
                  callBack:function(obj){
                    institutionsInfoCall(obj);
                  }
              });
            };
      });
      function institutionsInfoCall(obj){
          $(".tableList tbody").html("");
          $("#paging").html("");
          if(!obj.data.length || obj.data.length<1){
            $(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
          }else{
            var len = obj.data.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+obj.data[i].name+"</td><td>"+obj.data[i].mobile+"</td><td>"+obj.data[i].totalNumber+"</td><td>"+obj.data[i].inviteAmount+"</td><td class='operation'><a onclick='zycfH.institutionsInfoDel(this)' code="+obj.data[i].id+" href='javascript:void(0);'>删除</a></td>");
                oTr.appendTo($(".tableList tbody"));
            };
          };
      };
      $("#export").click(function(){
          var oLogin = "<div id='addStaffFrame'><p><input name='name' type='text' value='' /><span id='search' style='text-align:center;cursor:pointer;background-color:#38afff;border-radius:4px;color:#fff;margin-left:6px;'>查询</span></p><p><span style='width:100px'>真实姓名</span><span class='name' style='text-align:left;'></span></p><p><span style='width:100px'>身份证号</span><span class='idnumber' style='text-align:left;'></span></p><p><span style='width:100px'>手机号码</span><span class='mobile' style='text-align:left;'></span></p><p><span style='width:100px'>是否开户</span><span class='orNot' style='text-align:left;'></span></p></div>"
          var addStaff = new PopUpBox();
          addStaff.init({
            w:400,
            h:380,
            iNow:1,          // 确保一个对象只创建一次
            mark:false,
            title : '添加成员', 
            content:oLogin,
            callback:function(){giveUserGroup();}
          });
          $("#search").click(function(){
              if($("input[name='name']").val() != ""){
                  $.ajax({
                    type:"POST",
                    url:"../../queryUserByMobile",
                    dataType:"json",
                    data:{"mobile":$("input[name='name']").val()},
                    success:function(str){
                      zycfH.exception(str);
                      if(str.code == "UNKNOW_EXCEPTION"){
                      }else{
                        userId = str.id;
                        $(".name").html(str.name);
                        $(".idnumber").html(str.idnumber);
                        $(".mobile").html(str.mobile);
                        $(".orNot").html(str.authentication);
                      }
                    }
                  });
              }
          });
      });
      function giveUserGroup(){
          $.ajax({
              type:"POST",
              url:"../../modifyUser",
              dataType:"json",
              data:{"id":userId,"organizationId":arr[2]},
              success:function(str){
                  zycfH.exception(str);
                  alert(str.message);
                  if(str.code == 1){
                    window.location.reload();
                  }
              }
          });
      };
    },
    institutionsInfoDel:function(This){
        var oLogin = "<p style='text-align:center;font-size:18px;color:#666;margin-top:40px;'>你确定要删除此项吗？</p>"
        var addStaff = new PopUpBox();
        addStaff.init({
            w:340,
            h:200,
            iNow:9,          // 确保一个对象只创建一次
            title : '删除成员',
            opacity:0.7,
            mark:false,
            tBar:true,
            content:oLogin,
            callback:function(){delMember();}
        });
        return false;
        function delMember(){
            $.ajax({
                type:"POST",
                url:"../../modifyUser",
                dataType:"json",
                data:{"id":$(This).attr("code"),"organizationId":""},
                success:function(str){
                    zycfH.exception(str);
                    alert(str.message);
                    if(str.code == 1){
                      $(This).parent().parent().remove();
                    }
                }
            });
        };
    },
    modifyPassword:function(){    // 修改密码
        $(".passwordFrame .newPssWord input").focus(function(){
          $(".passwordFrame .newPssWord .msg").css("display","none");
          $(".passwordFrame .newPssWord .msgInfo").css("display","none");
        });
        $(".passwordFrame .newPssWord input").blur(function(){
          if($(this).val() == ""){
            $(".passwordFrame .newPssWord .msgInfo").css("display","inline-block");
          };
          if($(this).val().length<6 || $(this).val().length>16){
            $(".passwordFrame .newPssWord .msgInfo").css("display","inline-block");
          }else{
            $(".passwordFrame .newPssWord .msg").css("display","inline-block");
          }
        });
        $(".passwordFrame .newPssWord2 input").focus(function(){
          $(".passwordFrame .newPssWord2 .msg").css("display","none");
          $(".passwordFrame .newPssWord2 .msgInfo").css("display","none");
        });
        $(".passwordFrame .newPssWord2 input").blur(function(){
          if($(this).val() == ""){
            $(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
          };
          if($(".passwordFrame .newPssWord input").val() == $(this).val() && $(this).val() != ""){
            $(".passwordFrame .newPssWord2 .msg").css("display","inline-block");
          }else{
            $(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
          }
        });
        $(".passwordFrame .reviseBtn input").click(function(){
          if($(".passwordFrame .newPssWord input").val().length<=16 && $(".passwordFrame .newPssWord input").val().length>=6 && ($(".passwordFrame .newPssWord input").val() == $(".passwordFrame .newPssWord2 input").val())){
            $.ajax({  
              url:'../../updatePass',  
              type:'POST',  //请求方式  get||post
              data:{"oldpasswd":$(".passwordFrame .oldPassWord input").val(),"passphrase":$(".passwordFrame .newPssWord input").val()},
              success:function(str){
                zycfH.exception(str);
                if(str.code == 0){
                  alert(str.message);
                  window.location.reload();
                }else if(str.code == 1 || str.code == 2 || str.code == 3){
                  alert(str.message);
                }else if(str.code == "UNKNOW_EXCEPTION"){
                  alert("未登录，请先登录后再尝试更改密码");
                };
              }
            });
          };
          if($(".passwordFrame .newPssWord input").val().length>16 || $(".passwordFrame .newPssWord input").val().length<6){
            $(".passwordFrame .newPssWord .msgInfo").css("display","inline-block");
          }else if($(".passwordFrame .newPssWord input").val() != $(".passwordFrame .newPssWord2 input").val()){
            $(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
          };
        });
    },
    inviteList:function(){
        $("#searchTime").click(function(){
            if($("#userID").val() != ""){
                var start,end;
                if($("#dataStar").val() == ""){
                  start = new Date("1970/1/01").getTime();
                }else{
                  start = new Date($("#dataStar").val()).getTime();
                };
                if($("#dataEnd").val() == ""){
                  end = new Date("2130/1/01").getTime();
                }else{
                  end = new Date($("#dataEnd").val()).getTime();
                };
                if(end<start){
                  alert("结束时间必须大于开始时间")
                }else{
                  zycfH.listAjax({
                      page:1,
                      num:10,
                      field:"mobile",
                      value:$("#userID").val(),
                      sort:"desc",
                      startTime:start,
                      endTime:end,
                      url:"../../queryInvestRecordVoByParams",
                      callBack:function(obj){
                        inviteListData(obj)
                      }
                  });
                };
            }else{
                alert("用户id不能为空")
            }
        });
        function inviteListData(obj){
            $(".tableList tbody").html("");
            $("#paging").html("");
            if(!obj.investRecords.length || obj.investRecords.length<1){
              $(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
              $(".inviteInfo").html("<span>推荐<strong></strong>人，共 <strong></strong> 笔，投资金额<strong></strong> 元</span>");
              $(".inviteAmount strong").html("");
            }else{
              $(".inviteInfo").html("<span>推荐<strong>"+obj.userNumber+"</strong>人，共 <strong>"+obj.investNumber+"</strong> 笔，投资金额<strong>"+obj.totalAmount+"</strong> 元</span>");
              $(".inviteAmount strong").html(obj.yearAmount);
              var len = obj.investRecords.length;
              for(var i=0;i<len;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td>"+obj.investRecords[i].userName+"</td><td>"+obj.investRecords[i].title+"</td><td>"+obj.investRecords[i].months+"个月"+"</td><td>"+obj.investRecords[i].amount+"</td><td>"+new Date(parseInt(obj.investRecords[i].date,10)).toLocaleDateString()+"</td>");
                  oTr.appendTo($(".tableList tbody"));
              };
            };
        }
    },
    creatTest:function(opt){
        $(".groupId .select").click(function(){
            $(".groupId ul").toggle();
            $(document).click(function(){
                $(".groupId ul").hide();
            })
            return false;
        });
        $.ajax({
            type:"POST",
            data:{},
            url:"../../getAllDict",
            success:function(str){
                zycfH.exception(str);
                if(str && str.length && str.length>=1){
                    $(".groupId ul").html("");
                    $(".groupId .select").attr("code",str[0].code).html(str[0].name);
                    for(var i=0;i<str.length;i++){
                      var oLi = $("<li code="+str[i].code+">"+str[i].name+"<li>");
                      oLi.appendTo($(".groupId ul"));
                    };
                    $(".groupId li").click(function(){
                       $(".groupId .select").html($(this).html());
                       $(".groupId .select").attr("code",$(this).attr("code"));
                    });
                }
            }
        });
    },
    creatTestBtn:function(opt){
        var settings={
            name:"",
            triggerType:"",
            amount:"",
            effectDay:"",
            startTime:"",
            endTime:"",
            amountLimit:"",
            dateLimit:0,
            type:""
        };
        $.extend(settings,opt);
        if(parseInt(settings.type) == 1){
            if(settings.name!="" && settings.amount!="" && settings.effectDay!="" && settings.startTime!="" && settings.endTime!="" && settings.amountLimit!="" && settings.dateLimit!=""){
                fnCreatAmount();
            }else if(settings.name==""){
                alert("名称不能为空")
            }else if(settings.amount==""){
                alert("金额不能为空")
            }else if(settings.effectDay==""){
                alert("有效期不能为空")
            }else if(settings.amountLimit==""){
                alert("投资下限不能为空")
            }else if(settings.dateLimit==""){
                alert("投资期限下限不能为空")
            }else if(settings.startTime==""){
                alert("活动开始时间不能为空")
            }else if(settings.endTime==""){
                alert("活动结束时间不能为空")
            }
        }else if(parseInt(settings.type) == 2){
            if(settings.name!="" && settings.amount!="" && settings.effectDay!="" && settings.startTime!="" && settings.endTime!=""){
                fnCreatAmount();
            }else if(settings.name==""){
                alert("名称不能为空")
            }else if(settings.amount==""){
                alert("金额不能为空")
            }else if(settings.effectDay==""){
                alert("有效期不能为空")
            }else if(settings.startTime==""){
                alert("活动开始时间不能为空")
            }else if(settings.endTime==""){
                alert("活动结束时间不能为空")
            }
        };
        function fnCreatAmount(){
            $.ajax({
                type:"POST",
                data:{"name":settings.name,"triggerType":settings.triggerType,"amount":settings.amount,"effectDay":settings.effectDay,"startTime":settings.startTime,"endTime":settings.endTime,"amountLimit":settings.amountLimit,"dateLimit":settings.dateLimit,"type":settings.type},
                url:"../../saveFreshAmount",
                success:function(str){
                    zycfH.exception(str);
                    alert(str.message);
                    if(str.code == 1){
                        window.history.go(-1);
                    }
                }
            })
        };
    },
    invite:function(num,fn){
        $.ajax({
            type:"POST",
            data:{"type":num},
            url:"../../getAllFreshAmount.action",
            success:function(str){
                zycfH.exception(str);
                fn(str);
            }
        });
    },
    testMoneyData:function(obj){
        $(".tableList tbody").html("");
        if(!obj.length || obj.length<1){
          $(".tableList tbody").html("<td colspan='7'>暂无数据</td>");
        }else{
          var len = obj.length;
          for(var i=0;i<len;i++){
              var oTr = $("<tr></tr>");
              var status = "";
              if(obj[i].status == 1){
                  status = "未结束"
              }else{
                  status = "已结束"
              }
              oTr.html("<td>"+obj[i].name+"</td><td>"+obj[i].amount+"</td><td>"+obj[i].number+"</td><td>"+obj[i].startTime+"</td><td>"+obj[i].endTime+"</td><td>"+status+"</td>");
              if(obj[i].status == 1){
                  $("<td class='operation'><a onclick='zycfH.endBtn(this)' code="+obj[i].id+" href='javascript:void(0);'>结束</a><a href='TestMoneyList.html?"+obj[i].id+"'>查看列表</a></td>").appendTo(oTr);
              }else{
                  $("<td class='operation'><a href='TestMoneyList.html?"+obj[i].id+"'>查看列表</a></td>").appendTo(oTr);
              }
              oTr.appendTo($(".tableList tbody"));
          };
        };
    },
    endBtn:function(This){
        $.ajax({
            type:"POST",
            url:"../../updateFreshAmount",
            data:{"id":$(This).attr("code")},
            success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 1){
                    window.location.reload();
                }
            }
        });
    },
    testMoneyListCallBack:function(obj){
        $(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.results || !obj.results.length || obj.results.length<1){
            $(".tableList tbody").html("<td colspan='5'>暂无数据</td>");
        }else{
            var len = obj.results.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+obj.results[i].mobile+"</td><td>"+obj.results[i].triggerType+"</td><td>"+obj.results[i].getDate+"</td><td>成功</td><td class='operation'></td>");
                oTr.appendTo($(".tableList tbody"));
            };
        };
    },
    creatTestBid:function(){
        $(".subBtn input").click(function(){
            if($(".title .right input").val()!="" && $(".amount .right input").val()!="" && $(".loanDay .right input").val()!="" && $(".rate .right input").val()!="" && $(".method .right input").val()!=""){
                $.ajax({
                    type:"POST",
                    data:{"title":$(".title .right input").val(),"amount":$(".amount .right input").val(),"loanDay":$(".loanDay .right input").val(),"rate":$(".rate .right input").val(),"method":$(".method .right input").val()},
                    url:"../../saveVirtualLoan",
                    success:function(str){
                        zycfH.exception(str);
                        alert(str.message);
                        if(str.code == 1){
                            window.history.go(-1);
                        }
                    }
                })
            }else if($(".title .right input").val()==""){
                alert("标题不能为空")
            }else if($(".amount .right input").val()==""){
                alert("金额不能为空")
            }else if($(".loanDay .right input").val()==""){
                alert("体验天数不能为空")
            }else if($(".rate .right input").val()==""){
                alert("利率不能为空")
            }else if($(".method .right input").val()==""){
                alert("计息方式不能为空")
            }
        });
    },
    testBidData:function(obj){
        $(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.results || !obj.results.length){
            $(".tableList tbody").html("<td colspan='8'>暂无数据</td>");
        }else{
            var len = obj.results.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                var status = "";
                if(obj.results[i].status == 0){
                    status = "未开始"
                }else if(obj.results[i].status == 1){
                    status = "进行中"
                }else if(obj.results[i].status == 2){
                    status = "已结束"
                }else if(obj.results[i].status == 3){
                    status = "已取消"
                };
                oTr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].loanDay+"</td><td>"+obj.results[i].rate+"</td><td>"+new Date(parseInt(obj.results[i].createTime,10)).toLocaleDateString()+"</td><td>"+obj.results[i].loanDay+"</td><td>"+status+"</td><td>"+obj.results[i].method+"</td>");
                if(obj.results[i].status == 0){
                    var td = $("<td class='operation'><a onclick='zycfH.testBidUpdate(this,1)' code="+obj.results[i].id+" href='javascript:void(0);'>发布</a><a onclick='zycfH.testBidUpdate(this,3)' code="+obj.results[i].id+" href='javascript:void(0);'>取消</a></td>")
                    td.appendTo(oTr);
                }else if(obj.results[i].status == 1){
                    var td = $("<td class='operation'><a onclick='zycfH.testBidUpdate(this,2)'  code="+obj.results[i].id+" href='javascript:void(0);'>结束</a><a href='paymentPlan.html?"+obj.results[i].id+"#"+obj.results[i].title+"'>投资记录</a></td>")
                    td.appendTo(oTr);
                }else if(obj.results[i].status == 2){
                    var td = $("<td class='operation'></td>")
                    td.appendTo(oTr);
                }else if(obj.results[i].status == 3){
                    var td = $("<td class='operation'></td>")
                    td.appendTo(oTr);
                };
                oTr.appendTo($(".tableList tbody"));
            };
        };
    },
    testBidUpdate:function(This,num){
        $.ajax({
            type:"POST",
            url:"../../updateVirtualLoan",
            data:{"id":$(This).attr("code"),"status":num},
            success:function(str){
                zycfH.exception(str);
                alert(str.message);
                if(str.code == 1){
                    window.location.reload();
                }
            }
        });
    },
    paymentPlan:function(){
        var code = window.location.search.substring(1);
        var hash = window.location.hash.substring(1);
        $(".header").html(hash+"投资记录");
        zycfH.maListAjax({
            page:1,
            num:10,
            id:"paging",
            params:{"vloanId":code},
            url:"../../queryVirtualRecordByParams",
            callBack:function(obj){
              zycfH.paymentPlanData(obj)
            }
        });
    },
    paymentPlanData:function(obj){
        $(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.results || !obj.results.length || obj.results.length<1){
            $(".tableList tbody").html("<td colspan='7'>暂无数据</td>");
        }else{
            var len = obj.results.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+obj.results[i].userId+"</td><td>"+obj.results[i].amount+"</td><td>"+new Date(parseInt(obj.results[i].createTime,10)).toLocaleDateString()+"</td><td>"+obj.results[i].status+"</td><td>"+new Date(parseInt(obj.results[i].repayTime,10)).toLocaleDateString()+"</td><td>"+obj.results[i].realEarning+"元"+"</td><td>"+(obj.results[i].flag?"是":"否")+"</td>");
                oTr.appendTo($(".tableList tbody"));
            };
        };
    },
    invitationStatistics:function(){
        $(".selectedList").click(function(){
            $(".list").toggle();
            $(document).click(function(){
               $(".list").hide();
            });
            return false;
        });
        $(".list").click(function(ev){
          var ev = ev || window.event;
          if(ev.stopPropagation) { //W3C阻止冒泡方法  
              ev.stopPropagation();  
          } else {  
              ev.cancelBubble = true; //IE阻止冒泡方法  
          } ; 
        });
        $.ajax({
            type:"POST",
            url:"../../queryAllOrganizations",
            data:{},
            success:function(str){
                creatSelector(str);
            }
        });
        function creatSelector(obj){
            var oLi = $("<li></li>");
            var oH3 = $("<h3 id="+obj.id+">"+obj.name+"</h3>");
            oH3.appendTo(oLi);
            if(!obj.children || !obj.children.length || obj.children.length<1){
              return;
            }else{
              creatChild(obj.children,oLi);
            };
            oLi.appendTo($(".list"));
            var oTime = null;
            $(".list li").on("mouseenter",function(){
              $(this).children("ul").show();
            });
            $(".list li").on("mouseleave",function(){
              $(this).children("ul").hide();
            });
            $(".list h3").click(function(){
              $(".selectedList").html($(this).html());
              $(".selectedList").attr("code",$(this).attr("id"));
              $(".list").hide();
            });
        };
        function creatChild(obj,oParent){
            var oUl = $("<ul></ul>");
            for(var i=0,len=obj.length;i<len;i++){
              var oLi = $("<li></li>");
              var oH3 = $("<h3 id="+obj[i].id+">"+obj[i].name+"</h3>");
              oH3.appendTo(oLi);
              oLi.appendTo(oUl);
              oUl.appendTo(oParent);
              if( obj[i].children.length || obj[i].children.length>0){
                arguments.callee(obj[i].children,oLi); 
              };
            };
        };
        /*zycfH.maListAjax({
            page:1,
            num:10,
            id:"paging",
            params:{"organizationId":"","startTime":"","endTime":""},
            url:"../../queryStatisticByOrg",
            callBack:function(obj){
              invStaData(obj);
            }
        });*/
        $("#searchTime").click(function(){
            zycfH.maListAjax({
                page:1,
                num:10,
                id:"paging",
                params:{"organizationId":$(".selectWrap .selectedList").attr("code"),"startTime":$("#dataStar").val(),"endTime":$("#dataEnd").val()},
                url:"../../queryStatisticByOrg",
                callBack:function(obj){
                  invStaData(obj);
                }
            });
        });
        function invStaData(obj){
            $(".tableList tbody").html("");
            $("#paging").html("");
            if(!obj.results || !obj.results.length || obj.results.length<1){
              $(".tableList tbody").html("<td colspan='10'>暂无数据</td>");
            }else{
              var len = obj.results.length;
              for(var i=0;i<len;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td class='fontColor' type='OWN' startTime='"+obj.params.startTime+"' endTime='"+obj.params.endTime+"'  mobile='"+obj.results[i].mobile+"' onclick='zycfH.viewDetails(this)' >"+obj.results[i].staffName+"</td><td class='fontColor' startTime='"+obj.params.startTime+"' endTime='"+obj.params.endTime+"' type='INVITE' onclick='zycfH.viewDetails(this)' mobile='"+obj.results[i].mobile+"' >"+obj.results[i].mobile+"</td><td>"+obj.results[i].orgName+"</td><td>"+(obj.results[i].totalAmount?parseFloat(obj.results[i].totalAmount).toFixed(2):"")+"</td><td>"+(obj.results[i].yearAmount?parseFloat(obj.results[i].yearAmount).toFixed(2):"")+"</td>");
                  oTr.appendTo($(".tableList tbody"));
              };
            };
        };
        $("#export").click(function(){
            window.open("../../uploadInviteExcel?organizationId="+$(".selectWrap .selectedList").attr("code")+"&startTime="+$("#dataStar").val()+"&endTime="+$("#dataEnd").val());
        });
    },
    viewDetails:function(This){
        $(".exportDetails").unbind(); 
        $(".detailed .close").click(function(){
          $(".detailed").hide();
        });
        $(".detailed").show();
        $(".exportDetails").click(function(){
            window.open("../../uploadUserInviteDetail?mobile="+$(This).attr("mobile")+"&startTime="+$(This).attr("startTime")+"&endTime="+$(This).attr("endTime")+"&type="+$(This).attr("type"));
        });
        zycfH.maListAjax({
            page:1,
            num:10,
            id:"detailedPaging",
            params:{"mobile":$(This).attr("mobile"),"startTime":$(This).attr("startTime"),"endTime":$(This).attr("endTime"),"type":$(This).attr("type")},
            url:"../../queryUserStatisticDetail",
            callBack:function(obj){
              viewDetailsData(obj);
            }
        });
        function viewDetailsData(obj){
          $(".detailed tbody").html("");
          $("#detailedPaging").html("");
          if(!obj.results || !obj.results.length || obj.results.length<1){
            $(".detailed tbody").html("<td colspan='10'>暂无数据</td>");
          }else{
            var len = obj.results.length;
            for(var i=0;i<len;i++){
                var oTr = $("<tr></tr>");
                oTr.html("<td>"+obj.results[i].inviteName+"</td><td>"+obj.results[i].mobile+"</td><td>"+parseFloat(obj.results[i].investAmount).toFixed(2)+"</td><td>"+obj.results[i].title+"</td><td>"+obj.results[i].rate+(obj.results[i].addRate?"+"+obj.results[i].addRate:"")+"%"+"</td><td>"+obj.results[i].months+"</td><td>"+obj.results[i].submitTime+"</td><td>"+obj.results[i].status+"</td><td>"+parseFloat(obj.results[i].yearAmount).toFixed(2)+"</td>");
                oTr.appendTo($(".detailed tbody"));
            };
          };
        };
    },
    userFund:function(){
        var allNum = 1;
      zycfH.maListAjax({
          page:1,
          num:10,
          id:"paging",
          params:{"condition":$("#condition").val()},
          url:"../../queryUserFund",
          callBack:function(obj){
            userFundData(obj);
            allNum = obj.totalPage;
          }
      });
      function userFundData(obj){
        $(".tableList tbody").html("");
        $("#paging").html("");
        if(!obj.results || !obj.results.length || obj.results.length<1){
          $(".tableList tbody").html("<td colspan='8'>暂无数据</td>");
        }else{
          var len = obj.results.length;
          for(var i=0;i<len;i++){
              var oTr = $("<tr></tr>");
              oTr.html("<td>"+obj.results[i].userId+"</td><td>"+obj.results[i].userName+"</td><td>"+obj.results[i].mobile+"</td><td>"+obj.results[i].umpAccountName+"</td><td>"+obj.results[i].umpAccountId+"</td><td>"+obj.results[i].umpAviAmount+"</td><td>"+obj.results[i].aviAmount+"</td><td>"+obj.results[i].frozenAmount+"</td>");
              oTr.appendTo($(".tableList tbody"));
          };
        };
      };
        $("#searchTime").click(function(){
            zycfH.maListAjax({
                page:1,
                num:10,
                id:"paging",
                params:{"condition":$("#condition").val()},
                url:"../../queryUserFund",
                callBack:function(obj){
                    userFundData(obj);
                }
            });
        });
        $(".junp input").change(function(){
            var reg = /^[1-9][0-9]*$/g;
            if(reg.test( $(this).val() )){
                zycfH.maListAjax({
                    page:(parseInt($(this).val())>allNum)?allNum:$(this).val(),
                    num:10,
                    id:"paging",
                    params:{"condition":$("#condition").val()},
                    url:"../../queryUserFund",
                    callBack:function(obj){
                        userFundData(obj);
                    }
                });
            }else{
                alert("输入非0数字");
            };
        });
    },
    customerService:function(){
        $(".userQuery .selectWrap em").click(function(){
            $(".userQuery .selectWrap ul").toggle();
            $(document).click(function(){
                $(".userQuery .selectWrap ul").hide();
            });
            return false;
        });
        $(".userQuery .selectWrap li").click(function(){
            $(".userQuery .selectWrap em").html($(this).html());
            $(".userQuery .selectWrap em").attr("code",$(this).attr("code"));
            $(".userQuery .selectWrap ul").hide();
        });
        $("#searchTime").click(function(){
            if( $("#dataStar").val() && $("#dataEnd").val() && (new Date($("#dataStar").val()).getTime()) > (new Date($("#dataEnd").val()).getTime()) ){
                alert("开始时间不能大于结束时间");
                return;
            };
            if( ($("#dataStar").val() || $("#dataEnd").val()) && !threeMonth( $("#dataStar").val(),$("#dataEnd").val() ) ){
                alert("时间区间不能大于3个月");
                return;
            };
            zycfH.maListAjax({
                page:1,
                num:10,
                id:"paging",
                params:{"type":$(".userQuery .selectWrap em").attr("code"),"queryData":$(".userQuery .selectWrap input").val(),"startTime":$("#dataStar").val(),"endTime":$("#dataEnd").val()},
                url:"../../getUserInvestInfo",
                callBack:function(obj){
                  customerServiceData(obj);
                }
            });

        });
        function customerServiceData(obj){
            $(".tableList tbody").html("");
            $("#paging").html("");
            if(!obj.results || !obj.results.length || obj.results.length<1){
              $(".tableList tbody").html("<td colspan='7'>暂无数据</td>");
            }else{
              var len = obj.results.length;
              for(var i=0;i<len;i++){
                  var oTr = $("<tr></tr>");
                  oTr.html("<td>"+obj.results[i].name+"</td><td>"+obj.results[i].mobile+"</td><td>"+(obj.results[i].amount?obj.results[i].amount:"")+"</td><td>"+(obj.results[i].count?obj.results[i].count:"")+"</td><td>"+(obj.results[i].investTime?obj.results[i].investTime:"")+"</td><td>"+(obj.results[i].lastInvestTime?obj.results[i].lastInvestTime:"")+"</td><td>"+obj.results[i].inviteMobile+"</td>");
                  oTr.appendTo($(".tableList tbody"));
              };
            };
        };
        function threeMonth(month1,month2){
            var maxInput = new Date(month2).getTime();    // 最大值输入框
            var minInput = new Date(month1).getTime();   // 最小值输入框
            if( minInput+24*60*60*1000*90>=maxInput && minInput<maxInput ){
              return true;
            }else{
              return false;
            };
        };
    },
    userStatistics:function(){ 
        $(".selectedList").click(function(){
            $(this).next().toggle();
            var This = this;
            $(document).click(function(){
                $(This).next().hide();
            });
            return false;
        });
        $(".selectWrap .list li").click(function(){
            $(this).parent().prev().html( $(this).html() ).attr("code", $(this).attr("code") );
            $(this).parent().hide();
        });
        var flag = false;
        var onOff = true;
        $("#searchTime").click(function(){
            if($("#dataStar").val() == "" || $("#dataEnd").val() == ""){
                alert("请选择时间段");
                return;
            };
            if(!onOff){
                alert("正在查询数据，请稍后...");
                return;
            }
            onOff = false;
            $.ajax({
                type:"POST",
                url:"../../queryRegisterUser",
                data:{"start":$("#dataStar").val(),"end":$("#dataEnd").val(),"idauthenticated":$(".realName .selectedList").attr("code"),"ifinvest":$(".Investment .selectedList").attr("code")},
                success:function(str){
                    onOff = true;
                    if(str.message.code == 1){
                        flag = true;
                        //查询成功
                        var allCount = str.registerUserInfo.userInvest.length;
                        if(allCount>5){
                            fillingDate(str.registerUserInfo.userInvest, 1, 5);
                            $("#paging").show().html("");
                            page({
                                id:'paging',
                                nowNum:1,
                                allNum:Math.ceil(allCount/5),
                                callBack:function(now,all){
                                    fillingDate( str.registerUserInfo.userInvest, now, (all-now)?5:(allCount-(now-1)*5) );
                                }
                            });
                        }else{
                            fillingDate( str.registerUserInfo.userInvest, 1, allCount );
                            $("#paging").hide();
                        }
                        function fillingDate(obj, startPage, count){
                            $("#userInvest tbody").html("");
                            if(count){
                                for(var i=0; i<count; i++){
                                    var oTr = $("<tr></tr>");
                                    oTr.html("<td><a href='../usersList/userDetail.html#"+obj[(startPage-1)*5+i].id+"'>"+obj[(startPage-1)*5+i].mobile+"</a></td><td>"+(obj[(startPage-1)*5+i].name?obj[(startPage-1)*5+i].name:"")+"</td><td>"+obj[(startPage-1)*5+i].lastLoginTime+"</td>");
                                    oTr.appendTo( $("#userInvest tbody") );
                                };
                            }else{
                                $("#userInvest tbody").html("<td colspan='3'>暂无数据</td>");
                            }
                        };
                        $("#userStatistics tbody td").eq(0).html(str.userStatistics.registerUserCount);
                        $("#userStatistics tbody td").eq(1).html(str.userStatistics.authenUserCount);
                        $("#userStatistics tbody td").eq(2).html(str.userStatistics.noAutherUserCount);
                        $("#userStatistics tbody td").eq(3).html(str.userStatistics.investUserCount);
                        $("#userInvests tbody").html("");
                        if(str.userInvestLoan.userInvests.length){
                            for(var i=0, len=str.userInvestLoan.userInvests.length; i<len; i++){
                                var oTr = $("<tr></tr>");
                                oTr.html("<td>"+str.userInvestLoan.userInvests[i].type+"</td><td>"+str.userInvestLoan.userInvests[i].scaleAmount+"</td><td>"+str.userInvestLoan.userInvests[i].yearAmount+"</td><td>"+str.userInvestLoan.userInvests[i].totalPerson+"</td>");
                                oTr.appendTo( $("#userInvests tbody") );
                            };
                        }else{
                            $("#userInvests tbody").html("<td colspan='4'>暂无数据</td>");
                        }
                        $("#freshInfos tbody").html("");
                        if(str.freshInfoStatistics.freshInfos.length){
                            for(var i=0, len=str.freshInfoStatistics.freshInfos.length; i<len; i++){
                                var oTr = $("<tr></tr>");
                                oTr.html("<td>"+(str.freshInfoStatistics.freshInfos[i].type?str.freshInfoStatistics.freshInfos[i].type:"")+"</td><td>"+str.freshInfoStatistics.freshInfos[i].totalCount+"/"+str.freshInfoStatistics.freshInfos[i].useCount+"</td><td>"+(str.freshInfoStatistics.freshInfos[i].amount?str.freshInfoStatistics.freshInfos[i].amount:"0")+"</td>");
                                oTr.appendTo( $("#freshInfos tbody") );
                            };
                        }else{
                            $("#freshInfos tbody").html("<td colspan='4'>暂无数据</td>");
                        }
                        if(str.investLevel.investLevelcount.length){
                            $("#investLevelcount tbody td").eq(0).html(str.investLevel.investLevelcount[0]?str.investLevel.investLevelcount[0].count:"0");
                            $("#investLevelcount tbody td").eq(1).html(str.investLevel.investLevelcount[1]?str.investLevel.investLevelcount[1].count:"0");
                            $("#investLevelcount tbody td").eq(2).html(str.investLevel.investLevelcount[2]?str.investLevel.investLevelcount[2].count:"0");
                            $("#investLevelcount tbody td").eq(3).html(str.investLevel.investLevelcount[3]?str.investLevel.investLevelcount[3].count:"0");
                            $("#investLevelcount tbody td").eq(4).html(str.investLevel.investLevelcount[4]?str.investLevel.investLevelcount[4].count:"0");
                        }else{
                            $("#investLevelcount tbody td").eq(0).html("0");
                            $("#investLevelcount tbody td").eq(1).html("0");
                            $("#investLevelcount tbody td").eq(2).html("0");
                            $("#investLevelcount tbody td").eq(3).html("0");
                            $("#investLevelcount tbody td").eq(4).html("0");
                        }
                    }else{
                        flag = false;
                        $("#userInvest tbody").html("<td colspan='3'>暂无数据</td>");
                        $("#freshInfos tbody").html("<td colspan='3'>暂无数据</td>");
                        $("#userInvests tbody").html("<td colspan='4'>暂无数据</td>");
                        for(var i=0, len=$("#investLevelcount tbody td").size(); i<len; i++){
                            $("#investLevelcount tbody td").eq(i).html("0");
                        }
                        for(var i=0, len=$("#userStatistics tbody td").size(); i<len; i++){
                            $("#userStatistics tbody td").eq(i).html("0");
                        }
                        alert(str.message.message);
                    }
                },
                error:function(str){
                    onOff = true;
                    flag = false;
                    $("#paging").hide().html("");
                    $("#userInvest tbody").html("<td colspan='3'>暂无数据</td>");
                    $("#freshInfos tbody").html("<td colspan='3'>暂无数据</td>");
                    $("#userInvests tbody").html("<td colspan='4'>暂无数据</td>");
                    for(var i=0, len=$("#investLevelcount tbody td").size(); i<len; i++){
                        $("#investLevelcount tbody td").eq(i).html("0");
                    }
                    for(var i=0, len=$("#userStatistics tbody td").size(); i<len; i++){
                        $("#userStatistics tbody td").eq(i).html("0");
                    }
                    alert("出错了，请重新查询");
                }
            });
        });
        $("#export").click(function(){
            if(flag){
                window.open("../../registerUserExprot?start="+$("#dataStar").val()+"&end="+$("#dataEnd").val()+"&idauthenticated="+$(".realName .selectedList").attr("code")+"&ifinvest="+$(".Investment .selectedList").attr("code"));
            }else{
                alert("暂无数据，请点击查询按钮查询");
            }
        });
    }
};
