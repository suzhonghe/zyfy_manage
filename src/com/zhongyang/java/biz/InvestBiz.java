package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.vo.InvestDetail;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:33:34
* 类说明:投标信息
*/
public interface InvestBiz {

  public List<Invest> getInvestByLoanId(Invest invest);
  
  public List<InvestDetail>queryInvestByLoanId(Invest invest);

  public PagerVO<InvestVo> getUserInvestList(PagerVO<Invest> pager);
}
