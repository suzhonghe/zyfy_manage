package com.zhongyang.java.sys.uitl;

public enum Authorities {

	PRDADD("新建产品",1),
	PRDLIST("产品管理",2),
	PRJLIST("项目管理",3),
	EMPADD("新增员工",20),
	EMPUPD("编辑",21),
	EMPBAN("禁用",22),
	EMPLIST("员工列表",23),
	ROLEADD("新建角色",24),
	ROLEAUTH("查询角色",25),
	ROLEAUTHINIT("角色权限",26),
	ROLEAUTHUPD("角色权限更新",27),
	EMPPASSUPD("修改密码",28),
	EMPROLEDISPATCH("角色分配",29),
    
	//用户管理
	USERLIST("用户列表",140),
	USERINFODETAILE("用户信息明细",141),
	USERINFOEDIT("用户信息编辑",142),
	USERISTATUSEDIT("用户账户状态",143),
	ORGMANAGE("机构管理",144),
	ORGADD("添加机构",145),
	ORGMODIFY("编辑机构",146),
	CHANGEINVITE("推荐人变更",147),
	
	//产品管理
	PRDUPD("产品编辑",30),
	PRDBAN("产品禁用",31),
	
	//项目管理
	PRJADD("项目新增",40),
	PRJUPD("项目编辑",41),
	PRJFRZ("项目冻结",42),
	BIDAPPLY("标的申请",43),
	PRJUPLOAD("项目导出",44),
	PRJQUERY("项目查询",45),
	PRJBYID("查看项目",46),
	
	//标的管理
	BIDUPD("标的编辑",50),
	BIDCANCEL("标的取消",51),
	BIDRELEASE("标的发布",52),
	BIDLIST("标的列表",53),
	ARBUPD("调度标的编辑",54),
	ARBCANCEL("调度标的取消",55),
	ARBRELEASE("调度标的取消",56),
	ARBLIST("调度标的列表",57),
	INBUPD("进行中标的编辑",58),
	INBCANCEL("流标",59),
	INBLIST("进行中列表",60),
	ACCAUDITLIST("满标结算",61),
	ACCAUDIT("结算审核",62),
	CONTRACTGEN("生成合同",63),
	ACCLIST("审核列表",64),
	BIDCANCELLIST("标的取消列表",65),
	BIDFAILEDLIST("流标列表",66),
	BIDRELEASEDLIST("已发布标的列表",67),
	BIDAPPLYLIST("申请发标列表",68),
	BIDUNARBLIST("需调度发标列表",69),
	BIDLISTUPLOAD("标的列表导出",160),
	BIDLIQUERY("标的列表查询",161),
	BIDTIME("定时发标",162),
	BIDCAT("查看标的详情",163),
	
	//还款管理
	REPAYMENT("还款",70),
	REPAYMENTDETAILS("还款明细",71),
	SUBREPAY("垫付",72),
	REPAYLIST("还款列表",73),
	TOREPAYLIST("待还款列表",74),
	SETTLELIST("已还清列表",75),
	UNDUEREPAYLIST("逾期未还列表",76),
	PREPAY("提前还款",77),
	BREACHLIST("违约列表",78),
	
	//资金管理
	ENTERPRECHARGE("企业充值",80),
	ENTERPREWITHDRAW("企业提现",81),
	ENTERPTRANSFER("企业转账",82),
	B2CTRANSAPPLY("企业向个人转账申请",83),
	B2CTRANSAGGREE("个人转账批准",84),
	B2CTRANSCANCEL("个人转账取消",85),
	PLATCAPITALRECORDS("平台资金记录",86),
	B2CADJ("标的转出",87),
	P2BADJ("标的转入",88),
	
	//对账管理
	CHECKRECHARGE("充值对账",100),
	CHECKWITHDRAWL("提现对账",101),
	CHECKTRANSFER("转账对账",102),
	CHECKBID("标的对账",103),
	CHECKFILEMANAGER("对账文件管理",104),
	DOWNLOADCHECKFILE("对账文件下载",105),
	DERIVEFILE("导出对账文件",106),
	MAINTAINQUERY("运维查询",107),
	MAINTAINADJ("运维调账",108),
	CHECKPLATFORMRECORD("商户对账",109),
	
	
	//CMS管理
	COLUMNADD("栏目新建",110),
	COLUMNUPD("栏目更新",111),
	COLUMNDEL("栏目删除",112),
	COLUMNLIST("栏目列表",113),
	
	ARTICLEADD("文章新增",114),
	ARTICLEUPD("文章更新",115),
	ARTICLEDEL("文章删除",116),
	ARTICLEDETAIL("文章详情",117),
	ARTICLELIST("文章列表",118),
	BANNERUPLOAD("图片上传",210),
	BANNERMANAGE("图片",211),
	//查询统计
	USERSTATIC("用户统计",120),
	CAPSTATIC("资金统计",121),
	MARKSTATIC("邀请统计",122),
	
	//消息管理
	NEWMSG("新建消息",130),
	EDITMSG("消息编辑",131),
	MSGDEL("删除消息",132),
	GROUPMSG("群发消息",133),
	SERVICESTIC("客服统计",134),
	
	//系统设置
	SYSSETTING("系统设置",150),
	CHANGEPWD("修改密码",151),
	
	//红包返利
	BONUSLIST("红包列表",200),
	EXPBIDLIST("体验标列表",201),
	FRESHADD("创建红包/体验金",202),
	FRESHUPD("修改红包/体验金",203),
	VIRTUALADD("体验标新增",204),
	VIRTUALUPD("体验标修改",205),
	VIRREPAY("体验标收益重新还款",206),
	
	
	;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	private String name;
	private int index;
	private Authorities(String name, int index){
		this.index=index;
		this.name=name;
	}

}
