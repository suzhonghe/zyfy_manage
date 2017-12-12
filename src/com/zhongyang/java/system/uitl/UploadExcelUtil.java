package com.zhongyang.java.system.uitl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zhongyang.java.controller.ExportExcelUtils;
import com.zhongyang.java.system.uitl.ExcelUtil.ExportSetInfo;

public class UploadExcelUtil<T> {
	
	public static ResponseEntity<byte[]> upload(HttpServletRequest request,List<String[]> headNames,List<String[]> fieldNames,List file,String name) throws IllegalArgumentException, IllegalAccessException, IOException {
		ResponseEntity<byte[]> responseEntity=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		ExportSetInfo setInfo = new ExportSetInfo();
		LinkedHashMap<String, List> lhm=new LinkedHashMap<String, List>();
		lhm.put(name, file);
		setInfo.setObjsMap(lhm);
		setInfo.setFieldNames(fieldNames);
		setInfo.setTitles(new String[] {name});
		setInfo.setHeadNames(headNames);
		setInfo.setOut(baos);
		// 将需要导出的数据输出
		ExcelUtil.export2Excel(setInfo);
		HttpHeaders headers = new HttpHeaders();   
		String fileName=name+".xls";
		String transferFileName = DownloadFileName.encodeChineseDownloadFileName(request, fileName);//为了解决中文名称乱码问题  
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transferFileName);
		//headers.setContentDispositionFormData("attachment", transferFileName);  
		responseEntity = new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.OK);
		return responseEntity;
	}
	
	

	public static ResponseEntity<byte[]> uploadExcle(HttpServletRequest request,List<List<String>> headRegisterData,List<List<String>> headTypesData,List<List<String>> headRedsData,List<List<String>> headIntervalData) throws IllegalArgumentException, IllegalAccessException, IOException {
		ResponseEntity<byte[]> responseEntity=null;
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			String[] headRegister = { "注册人数", "已认证","未认证","投资人数"};
			String[] headTypes = { "类型", "规模(元)","年划(元)", "投资人数" };
			String[] headReds = {"红包类别", "发放数量/使用(个)","兑现金额(元)"};
			String[] headInterval = {"≤1000(个人)", "1001-5000(个人)","5001-10000(个人)", "10001-50000(个人)","5万以上(个人)"};
			ExportExcelUtils eeu = new ExportExcelUtils();
			HSSFWorkbook workbook = new HSSFWorkbook();
			eeu.exportExcel(workbook, 0, "注册信息", headRegister, headRegisterData,baos);
			eeu.exportExcel(workbook, 1, "标的投资信息", headTypes, headTypesData,baos);
			eeu.exportExcel(workbook, 2, "红包使用信息", headReds, headRedsData,baos);
			eeu.exportExcel(workbook, 3, "投资区间信息", headInterval, headIntervalData,baos);
			//原理就是将所有的数据一起写入，然后再关闭输入流。
			workbook.write(baos);
			baos.close();
			HttpHeaders headers = new HttpHeaders();   
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fileName=sdf.format(new Date())+"活动用户综合信息.xls";
			String transferFileName = DownloadFileName.encodeChineseDownloadFileName(request, fileName);//为了解决中文名称乱码问题  
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transferFileName);
			headers.setContentDispositionFormData("attachment", transferFileName);  
			responseEntity = new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return responseEntity;
	}

}
