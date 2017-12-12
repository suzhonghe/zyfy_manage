package com.zhongyang.java.biz.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.biz.PhototManageBiz;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;

@Service
public class PhototManageBizImpl implements PhototManageBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		pc_banner=(String) sysMap.get("ZYCFMANAGE_PC_BANNER");
		app_banner=(String) sysMap.get("ZYCFMANAGE_APP_BANNER");
		advertisement=(String) sysMap.get("ZYCFMANAGE_ADVERTISEMENT");
		news=(String) sysMap.get("ZYCFMANAGE_NEWS");
		article=(String) sysMap.get("ZYCFMANAGE_ARTICLE");
		itemPhoto=(String) sysMap.get("ZYCFMANAGE_PHOTO_FILE");
	}
	
	private static Logger logger = Logger.getLogger(BannerBizImpl.class);
	
	
	private static String pc_banner;
	
	private static String app_banner;
	
	private static String advertisement;
	
	private static String news;
	
	private static String article;
	
	private static String itemPhoto;

	@Override
	public String uploadPhoto(MultipartFile items_pic, String type,int name) {
		logger.info("开始上传图片");
		try {
			String pictureFile_name =  items_pic.getOriginalFilename();
			//新文件名称
			String newFileName = System.currentTimeMillis()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));

			//上传图片
			File uploadPic;
			switch (type) {
				case "pc_banner":uploadPic = new java.io.File(pc_banner+newFileName);break;
				case "app_banner":uploadPic = new java.io.File(app_banner+newFileName);break;
				case "advertisement":uploadPic = new java.io.File(advertisement+newFileName);break;
				case "news":uploadPic = new java.io.File(news+newFileName);break;
				case "article":uploadPic = new java.io.File(article+newFileName);break;
				case "upload":uploadPic = new java.io.File(itemPhoto+newFileName);break;
				default:uploadPic=null;break;
			}
			
			if(uploadPic==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "上传文件出错");
			}
			
			if(!uploadPic.exists()){
				uploadPic.mkdirs();
			}
			//向磁盘写文件
			items_pic.transferTo(uploadPic);
			
			String script="<script>parent.callback('/"+type+"/"+newFileName+"','"+name+"')</script>";
			return script;
		} catch (IllegalStateException |IOException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			return "<script>parent.uploadError()</script>";
		} 
	}

	@Override
	public Message deletePhoto(String url, String type) {
		String path;
		Message message=new Message();
		switch (type) {
			case "pc_banner":path = pc_banner;break;
			case "app_banner":path = app_banner;break;
			case "advertisement":path = advertisement;break;
			case "news":path = news;break;
			case "article":path = article;break;
			case "upload":path = itemPhoto;break;
			default:path=null;break;
		}
		if(path==null){
			message.setCode(0);
			message.setMessage("参数值type不正确");
		}
		String filePath=path+url.substring(url.lastIndexOf("/"));
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
			message.setCode(1);
			message.setMessage("删除成功");
		}
		else{
			message.setCode(0);
			message.setMessage("图片不存在");
		}
		return message;
	}
}
