package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.system.page.Page;
/**
 * 
* @Title: CorporationUserDao.java 
* @Package com.zhongyang.java.dao 
* @Description:企业单位dao
* @author 苏忠贺   
* @date 2016年1月25日 下午5:20:23 
* @version V1.0
 */
public interface CorporationUserDao {
	/**
	 * 
	* @Title: insertCorporationUser 
	* @Description:插入一条企业信息 
	* @return int    返回类型 
	* @throws
	 */
    public int insertCorporationUser(CorporationUser corporationUser)throws Exception;
    /**
     * 
    * @Title: selectAllCorporationUser 
    * @Description:查询所有担保实体
    * @return List<CorporationUser>    返回类型 
    * @throws
     */
    public List<CorporationUser> selectAllCorporationUser(CorporationUser corporationUser)throws Exception;
}