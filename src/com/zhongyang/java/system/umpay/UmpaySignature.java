package com.zhongyang.java.system.umpay;

import java.util.Map;

import org.apache.log4j.Logger;

import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.http.ZyHttpClient;

public class UmpaySignature {
	
	private static Logger logger=Logger.getLogger(UmpaySignature.class);
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		mer_id = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		sign_type = (String) sysMap.get("ZYCF_UMP_SIGN_TYPE");
		charset = (String) sysMap.get("ZYCF_UMP_CHARSET");
		res_format =(String) sysMap.get("ZYCF_UMP_RES_FORMAT");
		version = (String) sysMap.get("ZYCF_UMP_VERSION");
	}
	private String service;
	private static String sign_type;
	private static String charset;
	private static String res_format;
	private static String mer_id;
	private static String version;
	private Map<String, String> para;

	public UmpaySignature(String service, Map<String, String> para) {
		this.service = service;
		this.para = para;
	}

    public UmpaySignature(Map<String, String> para) {
        this.para = para;
    }

	public Map<String,String> getSignature() throws ReqDataException, RetDataException {
		para.put("service", service);
		para.put("charset", charset);
		para.put("res_format", res_format);
		para.put("mer_id", mer_id);
		para.put("version", version);
		para.put("sign_type", sign_type);
		ReqData reqData= Mer2Plat_v40.makeReqDataByGet(para);
		String hhtpEntity= ZyHttpClient.requestByGetMethod(reqData.getUrl());
		return Plat2Mer_v40.getResData(hhtpEntity.trim());	
	}
	

	public String getSignatureStrng() throws ReqDataException{
		para.put("service", service);
		para.put("charset", charset);
		para.put("res_format", res_format);
		para.put("mer_id", mer_id);
		para.put("version", version);
		para.put("sign_type", sign_type);
		ReqData reqData= Mer2Plat_v40.makeReqDataByGet(para);
		logger.info("跳转路径："+reqData.getUrl());
		return reqData.getUrl();	
	}

    public String callBackSignature() throws ReqDataException, RetDataException {
        para.put("mer_id", mer_id);
        para.put("version", version);
        para.put("sign_type", sign_type);
        String s= Mer2Plat_v40.merNotifyResData(para);
        StringBuffer sb=new StringBuffer();
        sb.append("<META NAME=\"MobilePayPlatform\" CONTENT=\"").append(s).append("\"/>");
        logger.info("响应："+sb.toString());
        return sb.toString();
    }
}
