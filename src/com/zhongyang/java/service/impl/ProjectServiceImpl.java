package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProjectDao;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.service.ProjectService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ProjectVo;

/**
 * 
* @Title: LoanRequestServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 项目管理实现类 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:03:27 
* @version V1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectDao projectDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.ProjectService#addOneProject(com.zhongyang.java.pojo.Project)
	 */
	@Override
	public void addOneProject(Project project) throws Exception {
		projectDao.insertOneProject(project);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.ProjectService#queryProjectById(java.lang.String)
	 */
	@Override
	public Project queryProjectById(String id) throws Exception {
		return projectDao.selectProjectById(id);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.ProjectService#modifyProject(com.zhongyang.java.pojo.Project)
	 */
	@Override
	public void modifyProject(Project project) throws Exception {
		projectDao.updateProject(project);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.ProjectService#deleteProjectById(java.lang.String)
	 */
	@Override
	public void deleteProjectById(String id) throws Exception {
		projectDao.deleteProjectById(id);
	}
	@Override
	public List<Project> queryAllProjects(Page<ProjectVo> projectVo) throws Exception {
		return projectDao.selectAllProjects(projectVo);
	}
	@Override
	public int queryCount(Map<String,Object>params) throws Exception {
		return projectDao.selectCount(params);
	}

}
