package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.CmsColumnBiz;
import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.service.CmsColumnService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.CmsColumnVO;
@Service
public class CmsColumnBizImpl implements CmsColumnBiz {
	
	@Autowired
	private CmsColumnService cmsColumnService;

	@Override
	@Transactional
	public Message addCmsColumn(CmsColumn cmsColumn) {
		Message message=new Message();
		try {
			cmsColumn.setId(UUID.randomUUID().toString().toUpperCase());
			cmsColumn.setTime(new Date());
			cmsColumnService.addCmsColumn(cmsColumn);
			message.setCode(1);
			message.setMessage("添加栏目成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加栏目失败");
		}
		return message;
	}

	@Override
	@Transactional
	public Message modifyByParams(CmsColumn cmsColumn) {
		Message message=new Message();
		try {
			if(cmsColumn.getId()==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得需要修改的栏目ID");
			}
			int count = cmsColumnService.modifyByParams(cmsColumn);
			if(count>0){
				message.setCode(1);
				message.setMessage("修改栏目成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改栏目失败");
		}
		return message;
	}

	@Override
	@Transactional
	public Message deleteByParams(CmsColumn cmsColumn) {
		Message message=new Message();
		try {
			if(cmsColumn.getId()!=null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收失败");
			}
			int count = cmsColumnService.deleteByParams(cmsColumn);
			if(count>0){
				message.setCode(1);
				message.setMessage("删除栏目成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "删除栏目失败");
		}
		return message;
	}

	@Override
	public List<CmsColumnVO> queryByParams(CmsColumn cmsColumn) {
		List<CmsColumnVO> list=new ArrayList<CmsColumnVO>();
		try {
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<CmsColumn> results = cmsColumnService.queryByParams(cmsColumn);
			for (CmsColumn ccl : results) {
				CmsColumnVO ccv=new CmsColumnVO();
				ccv.setId(ccl.getId());
				ccv.setName(ccl.getName());
				ccv.setTime(sdf.format(ccl.getTime()));
				list.add(ccv);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询栏目失败");
		}
		return list;
	}

}
