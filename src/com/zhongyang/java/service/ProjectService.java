package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ProjectVo;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectService {
	/**
	 * 
	* @Title: addOneProject 
	* @Description:添加一条项目记录 
	* @return void    返回类型 
	* @throws
	 */
	public void addOneProject(Project project)throws Exception;
	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据id查询项目 
	* @return Project    返回类型 
	* @throws
	 */
	public Project queryProjectById(String id)throws Exception;
	/**
	 * 
	* @Title: modifyProject 
	* @Description:根据id修改项目 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyProject(Project project)throws Exception;
	/**
	 * 
	* @Title: deleteProjectById 
	* @Description:根据Id删除项目
	* @return void    返回类型 
	* @throws
	 */
	public void deleteProjectById(String id)throws Exception;
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description:查询所有项目
	* @return List<Project>    返回类型 
	* @throws
	 */
	public List<Project> queryAllProjects(Page<ProjectVo> projectVo)throws Exception;
	/**
	 * 
	* @Title: queryCount 
	* @Description:根据条件查询结果记录数
	* @return int    返回类型 
	* @throws
	 */
	public int queryCount(Map<String,Object>params)throws Exception;

}
