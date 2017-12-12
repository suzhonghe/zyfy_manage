package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ProjectBiz;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectDetail;
import com.zhongyang.java.vo.ProjectDetailVo;
import com.zhongyang.java.vo.ProjectVo;
import com.zhongyang.java.vo.loan.LoanStatus;

/**
 * 
* @Title: LoanRequestController.java 
* @Package com.zhongyang.java.controller 
* @Description: 项目管理控制器 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:15:47 
* @version V1.0
 */
@Controller
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectBiz projectBiz;
	
	/**
	 * 
	* @Title: projectApply 
	* @Description: 项目申请 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJADD)
	@RequestMapping(value="/projectApply", method = RequestMethod.POST)
	public @ResponseBody Message projectApply(@RequestBody LoanVo loanVo) throws Exception{
		return 	projectBiz.addOneProject(loanVo);
	}
	/**
	 * 
	* @Title: queryLender 
	* @Description:查询用户 
	* @return User    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/queryLender", method = RequestMethod.POST)
	public @ResponseBody User queryLender(String loginName){
		
		return projectBiz.queryUserByLoginName(loginName);
	}
	/**
	 * 
	* @Title: queryProjectById 
	* @Description:根据ID查询项目
	* @return Project    返回类型 
	* @throws
	 */
	//@FireAuthority(authorities=Authorities.PRJBYID)
	@RequestMapping(value="/queryProjectById", method = RequestMethod.POST)
	public @ResponseBody ProjectDetail queryProjectById(String id){
		return projectBiz.queryProjectById(id);
	}
	@RequestMapping(value="/queryProjectDetailById", method = RequestMethod.POST)
	public @ResponseBody ProjectDetailVo queryProjectDetailById(String projectId,LoanStatus status){
		return projectBiz.queryProjectDetailById(projectId,status);
	}
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description: 查询所有项目 
	* @return List<Project>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJLIST)
	@RequestMapping(value="/queryAllProjects", method = RequestMethod.POST)
	public @ResponseBody PagerVO<ProjectVo> queryAllProjects(PagerVO<ProjectVo>pager){
		return projectBiz.queryAllProjects(pager);
	}
	/**
	 * 
	* @Title: modifyStatus 
	* @Description:修改项目状态
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJFRZ)
	@RequestMapping(value="/modifyProjectStatus", method = RequestMethod.POST)
	public @ResponseBody Message modifyProjectStatus(Project project){
		return projectBiz.modifyProjectStatus(project);
	}
	/**
	 * 
	* @Title: modifyProject 
	* @Description:编辑项目
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJUPD)
	@RequestMapping(value="/modifyProject", method = RequestMethod.POST)
	public @ResponseBody Message modifyProject(@RequestBody LoanVo loanVo){
		return projectBiz.modifyProject(loanVo);
	}
}
