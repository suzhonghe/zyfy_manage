package com.zhongyang.java.biz.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.biz.BannerBiz;
import com.zhongyang.java.pojo.BannerPhoto;
import com.zhongyang.java.service.BannerPhotoService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.BannerVO;
@Service
public class BannerBizImpl implements BannerBiz{
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ip=(String) sysMap.get("ZYCFMANAGER_IP");
		pc_banner=(String) sysMap.get("ZYCFMANAGE_PC_BANNER");
		app_banner=(String) sysMap.get("ZYCFMANAGE_APP_BANNER");
		advertisement=(String) sysMap.get("ZYCFMANAGE_ADVERTISEMENT");
		news=(String) sysMap.get("ZYCFMANAGE_NEWS");
		article=(String) sysMap.get("ZYCFMANAGE_ARTICLE");
	}
	
	private static Logger logger = Logger.getLogger(BannerBizImpl.class);
	
	private static String ip;
	
	private static String pc_banner;
	
	private static String app_banner;
	
	private static String advertisement;
	
	private static String news;
	
	private static String article;
	
	@Autowired
	private BannerPhotoService bannerPhotoService;
	
	@Override
	@Transactional
	public void uploadBannerPhoto(HttpServletRequest request, MultipartFile file,String type,HttpServletResponse response) {
		Message message=new Message();
		byte[] bytes;
		try {
			bytes = file.getBytes();
			//获得绝对路径
			String realPath = request.getRealPath("/");
			String uploadDir = realPath + "tempFile";
			String sep = System.getProperty("file.separator");
			String fileName=file.getOriginalFilename();
			String fileNames[]=fileName.split("\\.");
			String name= System.currentTimeMillis()+"."+fileNames[1];
			String path=uploadDir + sep + name;
			File uploadedFile = new File(path);
			FileCopyUtils.copy(bytes, uploadedFile);
			String accessAddress = BannerPhotoSave(path,type);
			message.setMessage(ip+accessAddress);
			PrintWriter writer = response.getWriter();
			writer.print(ip+accessAddress);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "banner图上传失败");
		}
	}
	@Transactional
	public String BannerPhotoSave(String path, String type) throws Exception{
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		String photoFile=null;
		if("pc_banner".equals(type)){
			photoFile =pc_banner;
		}
		if("app_banner".equals(type)){
			photoFile =app_banner;
		}
		if("advertisement".equals(type)){
			photoFile = advertisement;
		}
		if("news".equals(type)){
			photoFile = news;
		}
		if("article".equals(type)){
			photoFile = article;
		}
		
		// path源路径
		String tempFile =path;
		// fileCopeToPath 目标路径
		String fileCopeToPath;
		if(path.contains("\\")){
			fileCopeToPath = photoFile+path.substring(path.lastIndexOf("\\"));
		}
		else{
			fileCopeToPath = photoFile+path.substring(path.lastIndexOf("/"));
		}
		
		File file = new File(tempFile);
		File copyfile = new File(fileCopeToPath);
		if(!file.exists())throw new UException(SystemEnum.UNKNOW_EXCEPTION, "图片不存在");
		FileInputStream fis = new FileInputStream(file);
		if(!copyfile.exists()){
			copyfile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(copyfile);
		bis=new BufferedInputStream(fis);
		bos=new BufferedOutputStream(fos);
		byte[] b = new byte[1024];
		int len = 0;
		while (len!= -1) {
			bos.write(b, 0, len);
			len = bis.read(b);
		}
		bos.close();
		bis.close();
		BannerPhoto bannerPhoto=new BannerPhoto();
		bannerPhoto.setId(UUID.randomUUID().toString().toUpperCase());
		//图片存储路径
		if(path.contains("\\"))
			bannerPhoto.setPathAddress("/"+type+path.substring(path.lastIndexOf("\\")).replace("\\", "/"));
		else 
			bannerPhoto.setPathAddress("/"+type+path.substring(path.lastIndexOf("/")));
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		bannerPhoto.setTime(timeStamp);
		
		if(path.contains("\\"))
			bannerPhoto.setPhotoName(path.substring(path.lastIndexOf("\\")).substring(1));
		else 
			bannerPhoto.setPhotoName(path.substring(path.lastIndexOf("/")).substring(1));

		bannerPhoto.setType(type);
		bannerPhoto.setWhetherShow(false);
		bannerPhoto.setSerialNumber(Integer.MAX_VALUE);
		bannerPhotoService.addPhoto(bannerPhoto);
		return bannerPhoto.getPathAddress();
	}
	@Override
	@Transactional
	public Message deleteBannerPhoto(String pathAddress) {
		Message message=new Message();
		try {
			String path=null;
			String url=null;
			if(pathAddress.contains("pc_banner")){
				path =pc_banner;
				url="/pc_banner";
			}
			if(pathAddress.contains("app_banner")){
				path=app_banner;
				url="/app_banner";
			}
			if(pathAddress.contains("news")){
				path=news;
				url="/news";
			}
			if(pathAddress.contains("advertisement")){
				path=advertisement;
				url="/advertisement";
			}
			if(pathAddress.contains("article")){
				path=article;
				url="/article";
			}
			String filePath=path+pathAddress.substring(pathAddress.lastIndexOf("/"));
			File file=new File(filePath);
			if(file.exists()){
				file.delete();
			}
			BannerPhoto bannerPhoto=new BannerPhoto();
			bannerPhoto.setPathAddress(url+pathAddress.substring(pathAddress.lastIndexOf("/")));
			bannerPhotoService.deleteByParams(bannerPhoto);
			message.setCode(1);
			message.setMessage("删除成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除banner出现异常");
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "banner删除失败");
		}
	}
	@Override
	public List<BannerPhoto> queryBannerPhotos(String type) {
		try {
			List<BannerPhoto> bannerPhotos=new ArrayList<BannerPhoto>();
			BannerPhoto bannerPhoto=new BannerPhoto();
			bannerPhoto.setType(type);
			List<BannerPhoto> queryAllPhotos = bannerPhotoService.queryByParams(bannerPhoto);
			for (BannerPhoto photo : queryAllPhotos) {
				photo.setPathAddress(ip+photo.getPathAddress());
				bannerPhotos.add(photo);
			}
			return bannerPhotos;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("banner查询失败");
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "banner查询失败");
		}
	}
	
	@Override
	@Transactional
	public Message saveBannerPhoto(BannerVO bannerVo) {
		Message message=new Message();
		try {
			for (BannerPhoto photo : bannerVo.getBannerPhotos()) {
				if(photo==null)continue;
				photo.setWhetherShow(true);
				String pathAddress = photo.getPathAddress().substring(photo.getPathAddress().lastIndexOf("/"));
				photo.setPathAddress("/"+bannerVo.getBannerPhotoVo().getType()+pathAddress);
			}
			BannerPhoto bannerPhoto=new BannerPhoto();
			bannerPhoto.setWhetherShow(true);
			bannerPhoto.setType(bannerVo.getBannerPhotoVo().getType());
			List<BannerPhoto> queryBannerPhotos = bannerPhotoService.queryByParams(bannerPhoto);
			
			if(queryBannerPhotos.size()!=0){
				for (BannerPhoto photo : queryBannerPhotos) {
					photo.setWhetherShow(false);
					photo.setSerialNumber(Integer.MAX_VALUE);
					
				}
				bannerPhotoService.batchUpdateBannerPhotos(queryBannerPhotos);
			}
			
			bannerPhotoService.batchUpdateBannerPhotos(bannerVo.getBannerPhotos());
			message.setCode(1);
			message.setMessage("banner轮播修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("banner轮播图修改失败");
			logger.info(e,e.fillInStackTrace());
			message.setCode(0);
			message.setMessage("banner轮播修改失败");
		}
		return message;
	}
	@Transactional
	public Message modifyBannerPhoto(BannerPhoto bannerPhoto,String type) {
		Message message=new Message();
		try {
			if(bannerPhoto.getPathAddress()!=null){
				bannerPhoto.setPathAddress("/"+type+bannerPhoto.getPathAddress().substring(bannerPhoto.getPathAddress().lastIndexOf("/")));
			}
			bannerPhotoService.modifyByParams(bannerPhoto);
			message.setCode(1);
			message.setMessage("图片修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("banner图修改失败");
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "图片修改失败");
		}
		return message;
	}
}
