package com.zhongyang.java.system;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.http.ZyHttpClient;

/**
 *
 * @author Matthew
 *
 */
public class ShortMessage {

	private static ShortMessage shortMessage;
    private static String verificationCode;
    private static String url="ssssssssss";
    private static String sn;
    private static String pwd;
    private static String mobile;
    private static String content;
    private static String ext;
    private static String stime;
    private static String rrid;
    private static String msgfmt;
    private static String loanmsg;
    private static String messsge;



    public  String getVerificationCode(List<String> mobiles) {
        String string =String.valueOf(Math.round(Math.random()*899999+100000));
        content=verificationCode.replaceAll("random",string);
        mobile=getMobile(mobiles);
        try {
            ZyHttpClient.requestByGetMethod(new String(getPath().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    public  String getSendToUserMsg(Map<String, Object> map) {
    	String msg = (String) map.get("info");
    	String string = "";
    	String mobiles = (String) map.get("mobiles");
        content = loanmsg.replaceAll("msg",msg);
        mobile = mobiles;
        try {
            ZyHttpClient.requestByGetMethod(new String(getSendMSg().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    public  String sendToUserMsg(Map<String, Object> map) {
    	String msg = (String) map.get("info");
    	String string = "";
    	String mobiles = (String) map.get("mobiles");
        content = "【左右逢园】"+msg;
        mobile = mobiles;
        try {
            ZyHttpClient.requestByGetMethod(new String(getSendMSg().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    public  String getSendMsg(Map<String, Object> map) {
    	String result = null;
    	String msg = (String) map.get("info");
    	String mobiles = (String) map.get("mobiles");
        content = "【左右逢园】"+msg;
        mobile = mobiles;
        if((map.get("stime"))!=null){
        	 stime=map.get("stime").toString();
        }else{
            stime="";
        }
        try {
        	result = ZyHttpClient.requestByGetMethod(new String(getSendMSg().getBytes(),"UTF-8"));
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }

    public String getLoanmsg() {
		return loanmsg;
	}
    
	public static String getMesssge() {
		return messsge;
	}
	public  ShortMessage() {
        fill();
        getSendMSgTuUser();
    }

    public static ShortMessage getShortMessage() {
        if(shortMessage==null){
            shortMessage=new ShortMessage();
        }
        return shortMessage;
    }
   
    private static void fill(){
        String path=ShortMessage.class.getClassLoader().getResource("").getPath();
        Map<String,Object> map=DomParseService.xmlTOmap(path+"shortMessage.xml");
        verificationCode=map.get("verificationCode").toString();
        url=map.get("url").toString();
        sn=map.get("sn").toString();
        String ss = map.get("pwd").toString();
        pwd=map.get("pwd").toString();
        Object ob;
        if((ob=map.get("ext"))!=null){
            ext=map.get("ext").toString();
        }else{
            ext="";
        }
        if((ob=map.get("stime"))!=null){
            stime=map.get("stime").toString();
        }else{
            stime="";
        }
        if((ob=map.get("rrid"))!=null){
            rrid=map.get("rrid").toString();
        }else{
            rrid="";
        }
        if((ob=map.get("msgfmt"))!=null){
            msgfmt=map.get("msgfmt").toString();
        }else{
            msgfmt="";
        }
    }
    
    public static void getSendMSgTuUser(){
        String path=ShortMessage.class.getClassLoader().getResource("").getPath();
        Map<String,Object> map=DomParseService.xmlTOmap(path+"shortMessage.xml");
        loanmsg=map.get("loanmsg").toString();
        messsge=map.get("messsge").toString();
        url=map.get("url").toString();
        sn=map.get("sn").toString();
        pwd=map.get("pwd").toString();
        Object ob;
        if((ob=map.get("ext"))!=null){
            ext=map.get("ext").toString();
        }else{
            ext="";
        }
        if((ob=map.get("stime"))!=null){
            stime=map.get("stime").toString();
        }else{
            stime="";
        }
        if((ob=map.get("rrid"))!=null){
            rrid=map.get("rrid").toString();
        }else{
            rrid="";
        }
        if((ob=map.get("msgfmt"))!=null){
            msgfmt=map.get("msgfmt").toString();
        }else{
            msgfmt="";
        }
    }
    private String getSendMSg(){
        StringBuffer path=new StringBuffer();
        path.append(url).append("?sn=").append(sn).append("&pwd=").append(Util.zycf32MD5(sn+pwd)).append("&mobile=").append(mobile);
        path.append("&content=").append(content).append("&ext=").append(ext).append("&stime=").append(stime).append("&rrid=").append(rrid)
                .append("&msgfmt=").append(msgfmt);
        return path.toString();
    }
    
    private String getPath(){
        StringBuffer path=new StringBuffer();
        path.append(url).append("?sn=").append(sn).append("&pwd=").append(Util.zycf32MD5(sn+pwd)).append("&mobile=").append(mobile);
        path.append("&content=").append(content).append("&ext=").append(ext).append("&stime=").append(stime).append("&rrid=").append(rrid)
                .append("&msgfmt=").append(msgfmt);
        return path.toString();
    }

    private String getMobile(List<String> list){
        StringBuffer sb=new StringBuffer();
        if(list!=null&&list.size()>0){
            sb.append(list.get(0));
            for(int i=1;i<list.size();i++){
                sb.append(",").append(list.get(i));
            }
            return sb.toString();
        }else{
            throw new UException(SystemEnum.UNKNOW_EXCEPTION,"没有手机号");
        }
    }
}
