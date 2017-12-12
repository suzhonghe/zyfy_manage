package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProofPhotoDao;
import com.zhongyang.java.pojo.ProofPhoto;
import com.zhongyang.java.service.ProofPhotoService;

/**
 * 
* @Title: ProofPhotoServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:证明材料业务实现接口 
* @author 苏忠贺   
* @date 2015年12月24日 上午10:43:35 
* @version V1.0
 */
@Service
public class ProofPhotoServiceImpl implements ProofPhotoService{
	
	@Autowired
	private ProofPhotoDao proofPhotoDao;
	
	@Override
	public void addProofPhoto(List<ProofPhoto> proofPhotos) throws Exception {
		proofPhotoDao.insertProofPhoto(proofPhotos);
	}

	@Override
	public List<ProofPhoto> queryByParams(ProofPhoto proofPhoto) throws Exception {
		return proofPhotoDao.selectByParams(proofPhoto);
	}

	@Override
	public void deleteByParams(ProofPhoto proofPhoto) throws Exception {
		proofPhotoDao.deleteByParams(proofPhoto);
	}

	@Override
	public void deleteByUrl(String url) throws Exception {
		proofPhotoDao.deleteByUrl(url);
	}

}
