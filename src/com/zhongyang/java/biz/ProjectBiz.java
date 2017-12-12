package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectDetail;
import com.zhongyang.java.vo.ProjectDetailVo;
import com.zhongyang.java.vo.ProjectVo;
import com.zhongyang.java.vo.loan.LoanStatus;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务处理接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectBiz {
	/**
	 * 
	* @Title: addOneProject 
	* @Description: 添加一条项目记录 
	* @return Message    返回类型 
	* @throws
	 */
	public Message addOneProject(LoanVo loanVo);
	
	/**
	 * 
	* @Title: queryUserByLoginName 
	* @Description: 根据登录名查询用户 
	* @return User    返回类型 
	* @throws
	 */
	public User queryUserByLoginName(String loginName);
	
	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据Id查询项目
	* @return Project    返回类型 
	* @throws
	 */
	public ProjectDetail queryProjectById(String id);
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description:查询所有项目
	* @return List<Project>    返回类型 
	* @throws
	 */
	public PagerVO<ProjectVo> queryAllProjects(PagerVO<ProjectVo>pager);
	/**
	 * 
	* @Title: modifyProjectStatus 
	* @Description:修改项目状态 
	* @return Message    返回类型 
	* @throws
	 */
	public Message modifyProjectStatus(Project project);
	/**
	 * 
	* @Title: modifyProject 
	* @Description:编辑项目
	* @return Message    返回类型 
	* @throws
	 */
	public Message modifyProject(LoanVo loanVo);
	
	public ProjectDetailVo queryProjectDetailById(String projectId,LoanStatus status);

}
