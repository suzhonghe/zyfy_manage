package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CmsColumnDao;
import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.service.CmsColumnService;
@Service
public class CmsColumnServiceImpl implements CmsColumnService {
	
	@Autowired
	private CmsColumnDao cmsColumnDao;

	@Override
	public int addCmsColumn(CmsColumn cmsColumn) throws Exception {
		return cmsColumnDao.insertCmsColumn(cmsColumn);
	}

	@Override
	public int modifyByParams(CmsColumn cmsColumn) throws Exception {
		return cmsColumnDao.updateByParams(cmsColumn);
	}

	@Override
	public int deleteByParams(CmsColumn cmsColumn) throws Exception {
		return cmsColumnDao.deleteByParams(cmsColumn);
	}

	@Override
	public List<CmsColumn> queryByParams(CmsColumn cmsColumn) throws Exception {
		return cmsColumnDao.selectByParams(cmsColumn);
	}

}
