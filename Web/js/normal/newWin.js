function PopUpBox(){
	this.t = null;
	this.oParent = null;
	this.oDiv = null;
	this.oTDiv = null;
	this.settings = {   // 默认参数
		w:600,          // 弹窗宽
		h:300,          // 弹窗高
		mark : true,    // 是否有遮罩，默认有
		opacity:0.5,    // 遮罩默认透明度
		drag:true,      // 是否可拖动，默认可以拖到
		dir:'center',   // 弹窗位置，默认居中
		time:null,      // 自动关闭窗口的时间 为空或者'undefined'则不关闭
		tBar:true,      // 是否有标题条，默认有标题条
		title : '',     // 标题名称
		close:true,     // 是否有关闭按钮，默认有关闭按钮
		cBar:true,      // 是否有内容，默认有内容
		content:'',     // 内容
		makesure:true,  // 是否有确定按钮，默认有确定按钮
		cancel:true,    // 是否有取消按钮，默认有取消按钮
		callback:null   //弹窗显示后回调函数
	};
};
PopUpBox.prototype = {
	constructor:PopUpBox,
	json:{},
	init:function(opt){
		this.extend(this.settings,opt);
		if( this.json[opt.iNow] == undefined ){
			this.json[opt.iNow] = true;
		};
		if( this.json[opt.iNow] ){
			this.createFrame();
			this.json[opt.iNow] = false;
		};
	},
	createFrame:function(){    // 创建大外框
		this.oParent = document.createElement('div');
		this.oDiv = document.createElement('div');
		this.oDiv.style.cssText = "width:"+this.settings.w+"px"+";height:"+this.settings.h+"px"+";position:absolute;border:1px solid #666;z-index:2;background-color:#fff;"
        this.oDiv.style.left = (this.viewWidth() - this.settings.w)/2 + "px";
        this.oDiv.style.top = (this.viewHeight() - this.settings.h)/2 + "px";
		this.oParent.appendChild(this.oDiv);
		document.body.appendChild(this.oParent);
		if(this.settings.mark){
			this.createMark();
		};
		if(this.settings.tBar){
			this.createTitle();
		};
		if(this.settings.cBar){
			this.createContent();
		};
		if(this.settings.makesure || this.settings.cancel){
			this.createBtn();
		};
		if(this.settings.time && this.settings.time != 0){
			var This = this;
			this.t && clearTimeout(this.t);
			this.t = setTimeout(function(){
				document.body.removeChild(This.oParent);
				This.json[This.settings.iNow] = true;
			},This.settings.time);
		}
	},
	createTitle:function(){     // 创建标题
		this.oTDiv = document.createElement('div');
		this.oTDiv.style.cssText = "height:45px;background-color:#d7dee4;overflow:hidden;line-height:45px;font-size:14px;";
        this.oTDiv.innerHTML = "<span style='float:left;padding-left:20px;'>"+this.settings.title+"</span><span style='float:right;margin-right:10px;cursor:pointer;font-size:16px;'></span>";
        if(this.settings.close){
        	this.createClose();
        };
        if(this.settings.drag){
        	this.drag(this.oTDiv,this.oDiv);
        }
        this.oDiv.insertBefore(this.oTDiv,this.oDiv.children[0]);
	},
	createContent:function(){    // 内容
		this.oCDiv = document.createElement('div');
		this.oCDiv.style.cssText = "margin-bottom:50px;padding:10px 20px;";
        this.oCDiv.innerHTML = this.settings.content;
        this.oDiv.appendChild(this.oCDiv);
	},
	createBtn:function(){    // 确定和取消按钮
		var This = this;
		this.oBDiv = document.createElement('div');
        this.oBDiv.style.cssText = "height:50px;background-color:#f6f6f6;bottom:0;position:absolute;width:90%;text-align:right;padding:0 5%;line-height:50px;";
        if(this.settings.makesure){
        	this.makesureBtn = document.createElement('span');
        	this.makesureBtn.style.cssText = "display:inline-block;height:34px;width:60px;color:#fff;background-color:#4eb6e4;vertical-align: middle;margin:8px;line-height:36px;border-radius:4px;text-align:center;cursor:pointer;";
        	this.makesureBtn.innerHTML = "确定";
        	this.oBDiv.appendChild(this.makesureBtn);
        	this.makesureBtn.onclick = function(){
        		This.settings.callback && This.settings.callback();
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        if(this.settings.cancel){
        	this.cancelBtn = document.createElement('span');
        	this.cancelBtn.style.cssText = "display:inline-block;height:34px;width:60px;color:#fff;background-color:#4eb6e4;vertical-align: middle;margin:8px;line-height:36px;border-radius:4px;text-align:center;cursor:pointer;";
        	this.cancelBtn.innerHTML = "取消";
        	this.oBDiv.appendChild(this.cancelBtn);
        	this.cancelBtn.onclick = function(){
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        this.oDiv.appendChild(this.oBDiv);
	},
	createClose:function(){    // 右上角关闭按钮
		var This = this;
		this.oSpan = document.createElement('span');
    	this.oSpan.id = "close";
    	this.oSpan.style.cssText = "float:right;margin-right:10px;cursor:pointer;font-size:16px;";
    	this.oSpan.innerHTML = "X";
    	this.oTDiv.appendChild(this.oSpan);
    	this.oSpan.onclick = function(){
			document.body.removeChild(This.oParent);
			This.json[This.settings.iNow] = true;
		};
	},
	createMark:function(){    // 创建遮罩层
		this.oMark = document.createElement('div');
		this.oMark.style.cssText = "width:"+document.documentElement.offsetWidth+"px"+";height:"+document.documentElement.offsetHeight+"px"+";opacity:"+this.settings.opacity+";filter:alpha(opacity="+this.settings.opacity*100+");background-color:#000;position:absolute;left:0;top:0;z-index:1;";
		this.oParent.appendChild(this.oMark);
	},
	viewWidth:function(){    // 可视区宽
		return document.documentElement.clientWidth;
	},
	viewHeight:function(){   // 可视区高
		return document.documentElement.clientHeight;
	},
	extend:function(obj1,obj2){
		for(var i in obj2){
			obj1[i]=obj2[i];
		};
	},
	drag:function(obj,obj1){  // 拖拽
		obj.onmousedown = function(ev) {
			var ev = ev || window.event;
			var disX = ev.clientX - obj1.offsetLeft;
			var disY = ev.clientY - obj1.offsetTop;
			if ( obj.setCapture ) {
				obj.setCapture();
			};
			document.onmousemove = function(ev) {
				var ev = ev || window.event;
				obj1.style.left = ev.clientX - disX + 'px';
				obj1.style.top = ev.clientY - disY + 'px';
			};
			document.onmouseup = function() {
				document.onmousemove = document.onmouseup = null;
				if ( obj.releaseCapture ) {
					obj.releaseCapture();
				};
			};
			return false;
		};
	}
};
/*var addStaff = new PopUpBox();
addStaff.init({
	w:400,
	h:600,
	iNow:0,          // 确保一个对象只创建一次
	title : '我是标题!!!',
	opacity:0.7,
	mark:false,
	tBar:true,
	content:login,
	callback:function(){alert(1)}
});*/

function page(opt){
    if(!opt.id){return false};
    var obj = document.getElementById(opt.id);
    var nowNum = Number(opt.nowNum) || 1;
    var allNum = Number(opt.allNum) || 5;
    if(nowNum>allNum){
        nowNum = allNum
    };
    var callBack = opt.callBack || function(){};
    if(nowNum>=2){
        var oA = document.createElement('a');
        oA.href = '#' + (nowNum - 1);
        oA.innerHTML = '上一页';
        obj.appendChild(oA);
    };
    if(nowNum>=4 && allNum>=6){
        var oA = document.createElement('a');
        oA.href = '#1';
        oA.innerHTML = '1';
        obj.appendChild(oA);
        if(nowNum>=5){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        };
    };
    if(allNum<=5){
        for(var i=1;i<=allNum;i++){
            var oA = document.createElement('a');
            oA.href = '#' + i;
            oA.innerHTML = i;
            obj.appendChild(oA);
        };
    }else{
        for(var i=1;i<=5;i++){
            var oA = document.createElement('a');
            if(nowNum == 1 || nowNum == 2){
                oA.href = '#' + i;
                oA.innerHTML = i;
            }else if((allNum - nowNum) == 0 || (allNum - nowNum) == 1){
                oA.href = '#' + (allNum - 5 + i);
                oA.innerHTML = (allNum - 5 + i);
            }else{
                oA.href = '#' + (nowNum - 3 + i);
                oA.innerHTML = (nowNum - 3 + i);
            };
            obj.appendChild(oA);
        };
    };
    if((allNum - nowNum) >= 3 && allNum >= 6){
        if(allNum >= 7 && (allNum - nowNum) >= 4){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        };
        var oA = document.createElement('a');
        oA.href = '#' + allNum;
        oA.innerHTML = allNum;
        obj.appendChild(oA);
    };
    if((allNum - nowNum) >= 1){
        var oA = document.createElement('a');
        oA.href = '#' + (nowNum + 1);
        oA.innerHTML = '下一页';
        obj.appendChild(oA);
    };
    var aA = obj.getElementsByTagName('a');
    for(var i=0;i<aA.length;i++){
        if(aA[i].innerHTML == nowNum){
            aA[i].className = "on";
        }
        aA[i].onclick = function(){
            var nowNum = parseInt(this.getAttribute('href').substring(1));
            obj.innerHTML = '';
            page({
                id:opt.id,
                nowNum:nowNum,
                allNum:allNum,
                callBack:opt.callBack
            });
            callBack(nowNum,allNum);
            return false;
        };
    };
};


function setCookie(name, value, opts){
    //opts maxAge, path, domain, secure
    if(name && value){
        var cookie = escape(name) + '=' + escape(value);
        //可选参数
        if(opts){
            if(opts.maxAge){
                cookie += '; max-age=' + opts.maxAge; 
            }
            if(opts.path){
                cookie += '; path=' + opts.path;
            }
            if(opts.domain){
                cookie += '; domain=' + opts.domain;
            }
            if(opts.secure){
                cookie += '; secure';
            }
        }
        document.cookie = cookie;
        return cookie;
    }else{
        return '';
   }
}

function getCookie(c_name){
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){ 
		    c_start=c_start + c_name.length+1 ;
		    c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1) c_end=document.cookie.length
		    return unescape(document.cookie.substring(c_start,c_end));
		} ;
	};
	return "";
};

function removeCookie(key) {
	setCookie(key, '', -1);
};

//判断手机号
function checkMobile(s){
	var regu =/^[1][34578][0-9]{9}$/;
	return regu.test(s);
};

//判断身份证号码
function checkCard(s) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
    return reg.test(s);
};

function drag(obj,obj1){  // 拖拽
    obj.onmousedown = function(ev) {
        var ev = ev || window.event;
        var disX = ev.clientX - obj1.offsetLeft;
        var disY = ev.clientY - obj1.offsetTop;
        if ( obj.setCapture ) {
            obj.setCapture();
        };
        document.onmousemove = function(ev) {
            var ev = ev || window.event;
            obj1.style.left = ev.clientX - disX + 'px';
            obj1.style.top = ev.clientY - disY + 'px';
        };
        document.onmouseup = function() {
            document.onmousemove = document.onmouseup = null;
            if ( obj.releaseCapture ) {
                obj.releaseCapture();
            };
        };
        return false;
    };
};
