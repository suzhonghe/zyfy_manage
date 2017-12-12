package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.CorporationUserVo;
/**
 * 
* @Title: CorporationUserBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:企业信息业务处理接口 
* @author 苏忠贺   
* @date 2016年1月25日 下午5:40:07 
* @version V1.0
 */
public interface CorporationUserBiz {
	/**
	 * 
	* @Title: addCorporationUser 
	* @Description:增加一条企业信息
	* @return Message    返回类型 
	* @throws
	 */
    public Message addCorporationUser(CorporationUser corporationUser);
    /**
     * 
    * @Title: queryAllCorporationUser 
    * @Description:根据企业类型查询企业名字
    * @return List<CorporationUser>    返回类型 
    * @throws
     */
    public List<CorporationUserVo> queryAllCorporationUser(CorporationUser corporationUser);
}
