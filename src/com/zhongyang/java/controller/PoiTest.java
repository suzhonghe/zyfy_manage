package com.zhongyang.java.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年10月20日 下午2:24:47
* 类说明
*/
public class PoiTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			OutputStream out = new FileOutputStream("D:\\test.xls");
			List<List<String>> data = new ArrayList<List<String>>();
			for (int i = 1; i < 5; i++) {
				List rowData = new ArrayList();
				rowData.add(String.valueOf(i));
				rowData.add("东霖柏鸿");
				data.add(rowData);
			}
			String[] headers = { "ID", "用户名" };
			ExportExcelUtils eeu = new ExportExcelUtils();
			HSSFWorkbook workbook = new HSSFWorkbook();
			eeu.exportExcel(workbook, 0, "上海", headers, data,baos);
			eeu.exportExcel(workbook, 1, "深圳", headers, data,baos);
			eeu.exportExcel(workbook, 2, "广州", headers, data,baos);
			//原理就是将所有的数据一起写入，然后再关闭输入流。
			workbook.write(baos);
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}