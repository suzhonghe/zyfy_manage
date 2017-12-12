package com.zhongyang.java.dao;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ProjectVo;


/**
 * 
* @Title: LoanRequestDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 项目管理dao接口
* @author 苏忠贺   
* @date 2015年12月1日 下午3:27:25 
* @version V1.0
 */
public interface ProjectDao {
	/**
	 * 
	* @Title: insertOneProject 
	* @Description:插入一条项目记录 
	* @return void    返回类型 
	* @throws
	 */
	public void insertOneProject(Project project)throws Exception;
	/**
	 * 
	* @Title: selectProjectById 
	* @Description:根据id查询项目 
	* @return Project    返回类型 
	* @throws
	 */
	public Project selectProjectById(String id)throws Exception;
	/**
	 * 
	* @Title: updateProject 
	* @Description: 根据id修改项目 
	* @return void    返回类型 
	* @throws
	 */
	public void updateProject(Project project)throws Exception;

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
	* @Title: selectAllProject 
	* @Description:查询所有项目
	* @return List<Project>    返回类型 
	* @throws
	 */
	public List<Project> selectAllProjects(Page<ProjectVo> projectVo)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果记录数 
	* @return int    返回类型 
	* @throws
	 */
	public int selectCount(Map<String,Object>params)throws Exception;
}
