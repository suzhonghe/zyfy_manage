package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.DictBiz;
import com.zhongyang.java.pojo.Dict;

@Controller
public class DictController extends BaseController {
	@Autowired
	private DictBiz dictBiz;
	
	@RequestMapping("/getAllDict")
	@ResponseBody
	public List<Dict> getAllDict(){
		List<Dict> list = dictBiz.getAllDict();
		return list;		
	}
	@RequestMapping("/addDict")
	@ResponseBody
	public void addDict(Dict dict){
		dictBiz.addDict(dict);
	}
}
