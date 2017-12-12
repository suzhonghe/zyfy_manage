package com.zhongyang.java.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.VirtualLoanBiz;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.service.VirtualLoanService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
@Service
public class VirtualLoanBizImpl implements VirtualLoanBiz {
	@Autowired
	private VirtualLoanService virtualLoanService;
	/**
	 * 保存体验标
	 */
	@Override
	@Transactional
	public Message saveVirtualLoan(VirtualLoan virtualLoan) {
		try{
			Message message = new Message();
			virtualLoan.setId(UUID.randomUUID().toString().toUpperCase());
			virtualLoan.setCreateTime(new Date());
			virtualLoan.setStatus(0);
			int count = virtualLoanService.saveVirtualLoan(virtualLoan);
			if(count>0){
				message.setCode(1);
				message.setMessage("创建体验标成功！");
			}else{
				message.setCode(0);
				message.setMessage("创建体验标失败！");
			}
			
			return message;
		}catch(Exception e){
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "创建体验标失败");			
		}
	}
	/**
	 *更新体验标相关信息
	 */
	@Override
	@Transactional
	public Message updateVirtualLoan(VirtualLoan virtualLoan) {
		try{
			Message message = new Message();
			if(virtualLoan.getStatus()==1){
				VirtualLoan loan=new VirtualLoan();
				loan.setStatus(virtualLoan.getStatus());
				VirtualLoan result = virtualLoanService.queryVirtualLoanByParams(loan);
				if(result!=null){
					message.setCode(0);
					message.setMessage("已有体验标处于进行中状态！");
					return message;
				}
			}
			
			int count=virtualLoanService.updateVirtualLoan(virtualLoan);
			if(count>0){
				message.setCode(1);
				message.setMessage("更新体验标成功！");
			}else{
				message.setCode(0);
				message.setMessage("更新体验标失败！");
			}
			return message;
		}catch(Exception e){
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新体验标失败");			
		}
	}
	/**
	 * 查询所有体验标信息
	 */
	@Override
	public Page<VirtualLoan> getAllVirtualLoan(Page<VirtualLoan> page) {
		try{
			if(page==null){
				page = new Page<VirtualLoan>();
				page.setPageNo(1);
				page.setPageSize(10);
			}
			List<VirtualLoan> list = new ArrayList<VirtualLoan>();
			list = virtualLoanService.getAllVirtualLoan(page);
			page.setResults(list);
			return page;
		}catch(Exception e){
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询体验标列表失败");			
		}
	}
	/**
	 * 根据id查询体验表信息
	 */
	@Override
	public VirtualLoan getVirtualLoanById(String id) {
		try{
			VirtualLoan virtualLoan=virtualLoanService.getVirtualLoanById(id);
			return virtualLoan;
		}catch(Exception e){
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询id="+id+"体验标查询失败");	
		}
	}

}
