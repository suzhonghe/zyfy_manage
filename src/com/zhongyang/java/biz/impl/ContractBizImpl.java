package com.zhongyang.java.biz.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhongyang.java.biz.ContractBiz;
import com.zhongyang.java.dao.ContractDao;
import com.zhongyang.java.dao.InvestContractInfoMapper;
import com.zhongyang.java.pojo.ContractPojo;
import com.zhongyang.java.pojo.InvestContractInfo;
import com.zhongyang.java.pojo.InvestRepayment;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.NumberToCN;

@Service
public class ContractBizImpl implements ContractBiz {
	private static Logger logger = Logger.getLogger(AdjustAccountBizImpl.class);
	@Autowired
	ContractDao contractDao;
	
	
	
	@Autowired
	InvestContractInfoMapper investContractInfoMapper;
	
	@Override
	public Message generateContracts(HttpServletRequest request,String loanId) {
		// TODO Auto-generated method stub
		  List<ContractPojo> list = contractDao.getContractInfo(loanId);
		  DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		  
		  Map map = SystemPro.getProperties();
		  String conPath = (String) map.get("CONTRACT_HOME");
		  String src="";
		  if(list.size()<0)
			  return new Message(2,"标的无投资");
		  else
		  {
			  ContractPojo con=list.get(0);
			  if(con.getGuaranteeCorporationName().contains("大别山"))
				  src= conPath+"dbsdb.pdf";
			  else if(con.getGuaranteeCorporationName().contains("瑞盈"))
				  src= conPath+ "rydb.pdf";
		  }
		//  String rootPath = request.getSession().getServletContext().getRealPath("/");
		  int sortNum=0;
		for(ContractPojo con : list){
			
			sortNum++;
			DateTime dt = new DateTime(con.getSettleDate());
			
		    String s =	dt.toString("YYYY年MM月dd日");
			
			con.setCxrDate(s);
			con.setSignDate(s);
			try{
			con.setCJRIdNumber(cipher.decrypt(con.getCJRIdNumber()));
			con.setJKRIdNumber(cipher.decrypt(con.getJKRIdNumber()));
			}catch(Exception e){
				
			}
			InvestRepayment investRepayment =  new InvestRepayment();
			investRepayment.setInvestId(con.getInvestId());
			
			List<InvestRepayment> repayments =  contractDao.getInvestRepaymets(investRepayment);
			
			if(repayments == null || repayments.size() == 0)
				return new Message(5,"数据异常");
			
			investRepayment = repayments.get(0);
			DateTime dt2 = new DateTime(investRepayment.getDuedate());
			
			con.setEndDate(dt2.toString("YYYY年MM月dd日"));
			
			//合同编号
			con.setSerial(con.getSerial()+"-"+sortNum);
			NumberToCN numberTocn =  new NumberToCN();
			con.setAmountUpper(numberTocn.number2CNMontrayUnit(con.getAmount()));

			String dest = conPath+con.getInvestId()+".pdf";
			String path = "/contracts/"+con.getInvestId()+".pdf";
			System.out.println(con.getInvestId()+".pdf");
			con.setRepaymentNo(repayments.size()+"");
			
		try{
			
			String contractUrl = manipulatePdf(src,dest,con,repayments,path,conPath);
			String oidpath = conPath+con.getInvestId()+".pdf";
			String[] files = { oidpath, conPath+"tempContracts.pdf"};//前面的合同是已经生成的么有划款计划的，后面的是单独的生产还款计划的文件，是一个模板，但是没都会被从新全部生成
			contractUrl = conPath+"contracts.pdf";//合并之后的文件(不是最终的，此文档会在最后修改名字为没有还款记的文档名字)
	    	mergePdfFiles(files, contractUrl);
			this.deleteOldcontractFile(contractUrl,oidpath);
			InvestContractInfo record = new InvestContractInfo();
			record.setContractno(con.getSerial());
			record.setCreateDate(new Date());
			record.setExpiredDate(dt2.toDate());
			record.setInvestid(con.getInvestId());
			record.setPath(path);
			record.setIsdel(true);
			System.out.println(contractUrl);
			investContractInfoMapper.insert(record);
			
		}catch (Exception e){
			logger.info(e+"生成合同失败");
			e.printStackTrace();
			return new Message(0,"生成合同失败");
		}
			
		}
		return new Message(1,"生成合同成功");
	}
	private void deleteOldcontractFile(String contractUrl,String path) {
    	File file = new File(path);
    	File files = new File(contractUrl);
    	try {
			FileInputStream fileInputStream = new FileInputStream(file); 
			System.out.println(fileInputStream);
			BufferedInputStream tempStream =new BufferedInputStream(fileInputStream );
			System.out.println(tempStream);
			fileInputStream.close();
			if(file.isFile() && file.exists()){
	    		 file.delete();
	    		 System.out.println("删除合同成功");
	    		 files.renameTo(file);
	    		 System.out.println("修改和合名称同成功");
	    	 }
		} catch (FileNotFoundException e) {
			logger.info(e+"文档为找到");
			System.out.println();
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(e+"流异常");
			e.printStackTrace();
		}
		
	}
	static String[][] normalTableContent=new String[13][5];
    public String manipulatePdf(String src, String dest,ContractPojo contract,List<InvestRepayment> repayments,String path,String conPath) throws DocumentException, IOException {
    	OutputStream pdfFileOutputStream = null;
    	 Document pdfDocument = new Document();
    	 String savepath = null;
    	 try {
    		
    		 PdfReader reader = new PdfReader(src);
             PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
             AcroFields form = stamper.getAcroFields();
             Map<String,Item> map = form.getFields();
	        Set<Entry<String,Item>> set = map.entrySet();
	        for(Entry entry:set){
	        	System.out.println(entry.getKey()+":"+entry.getValue());
	        }
	        form.setField("CJRIdNumber", contract.getCJRIdNumber());
	        form.setField("CJRName", contract.getCJRName());
	        form.setField("serial", contract.getSerial());
	        form.setField("CJRloginName", contract.getCJRloginName());
	        form.setField("cxrDate", contract.getCxrDate());
	        form.setField("endDate", contract.getEndDate());
	        form.setField("guaranteeCorporationAddress", contract.getGuaranteeCorporationAddress());
	        form.setField("guaranteeCorporationName",contract.getGuaranteeCorporationName());
	        form.setField("guaranteeCorporationUserName", contract.getGuaranteeCorporationUserName());
	        form.setField("JKRIdNumber", contract.getJKRIdNumber());
	        form.setField("JKRloginName", contract.getJKRloginName());
	        form.setField("JKRName", contract.getJKRName());
	        //form.setField("loanPurpose", contract.getLoanPurpose());
	        //form.setField("loanPurpose","短期周转");
	        form.setField("signDate", contract.getSignDate());
	        form.setField("amount",contract.getAmount().toPlainString());
	        form.setField("loanRate", contract.getLoanRate().toPlainString()+"%");
	        form.setField("amountUpper", contract.getAmountUpper());
	        form.setField("repaymentNo",contract.getRepaymentNo());
	        stamper.close();
    		System.out.println("生成没有投资列表的合同成功");
	        /*回款计划详情生成start----------------------------------------*/
    		String mb =conPath+"tempContracts.pdf";
    		savepath=mb;
	        pdfFileOutputStream = new FileOutputStream(new File(mb));//加载模板
 	        PdfWriter.getInstance(pdfDocument,pdfFileOutputStream); 
	        pdfDocument.open();
	        Collections.reverse(repayments);
	        for(int i=0;i<repayments.size()+1;i++){
		   		 if(i == 0){
		   			 normalTableContent[i][0] = "期数";
		   			 normalTableContent[i][1] = "还款日期";
		   			 normalTableContent[i][2] = "还款金额";
		   			 normalTableContent[i][3] = "本金";
		  			 normalTableContent[i][4] = "利息";
		   		 }else{
		   			 normalTableContent[i][0] = repayments.get(i-1).getCurrentperiod().toString();
		   			 normalTableContent[i][1] = repayments.get(i-1).getDuedate();//还款日期;//还款金额
					  if(i !=repayments.size()){//还款金额，如果不是最后一个就为当期利息，如果是最后一期就为本金
						normalTableContent[i][2]=repayments.get(i-1).getAmountinterest().toString();
				      }else{
				    	  BigDecimal toAmount =  repayments.get(i-1).getAmountprincipal().add(repayments.get(i-1).getAmountinterest());
				    	  normalTableContent[i][2]=toAmount.toString();
				      }
		   			 normalTableContent[i][3] = repayments.get(i-1).getAmountprincipal().toString();//本金
		   			 normalTableContent[i][4] = repayments.get(i-1).getAmountinterest().toString();//利息 
		   		 }
	   	     }
	         for(int x = 0; x<normalTableContent.length; x++){//定位行  
	          for(int y = 0; y<normalTableContent[x].length; y++){//定位每行的元素个数  
	              System.out.print(normalTableContent[x][y]);
	          }
	          System.out.println("/n");  
	         }
	         //设置中文字体和字体样式  
	         BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	         Font f8 = new Font(bfChinese, 12, Font.NORMAL);
	         // 标题居中  
	         Paragraph title = new Paragraph();  
	         Paragraph title1 = new Paragraph();
	         // 设置页面格式  
	         title.setSpacingBefore(8);  
	         title.setSpacingAfter(2);  
	         title.setAlignment(1); 
	         title1.setSpacingBefore(8);  
	         title1.setSpacingAfter(2);  
	         title1.setAlignment(1);
	         // 设置PDF标题  
	         
	         title1.add(new Chunk("",new Font(bfChinese, 16,Font.NORMAL)));
	         title.add(new Chunk("还款计划详情表",new Font(bfChinese, 16,Font.BOLD)));
	         pdfDocument.add(title);
	         pdfDocument.add(title1);
	         
	         //创建一个N列的表格控件  
	         PdfPTable pdfTable = new PdfPTable(normalTableContent[0].length);  
	         pdfTable.setHeaderRows(1);
	         //设置表格占PDF文档100%宽度  
	         pdfTable.setWidthPercentage(100);  
	         //水平方向表格控件左对齐  
	         pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT); 
	         //创建一个表格的表头单元格  
	         PdfPCell pdfTableHeaderCell = new PdfPCell();
	         //设置表格的表头单元格颜色   
	         pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
	         for(String tableHeaderInfo : normalTableContent[0]){
	             pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));  
	             pdfTable.addCell(pdfTableHeaderCell);
	         }
	         //创建一个表格的正文内容单元格  
	         PdfPCell pdfTableContentCell = new PdfPCell();  
	         pdfTableContentCell.setMinimumHeight(15);
	         pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
	         pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
	         //表格内容行数的填充  
	         int c= repayments.size();
	         for(int i = 1;i < c+1;i++){
                 for(String tableContentInfo : normalTableContent[i]){
                     pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f8));
                     pdfTable.addCell(pdfTableContentCell);
                 }
             }  
	         pdfDocument.add(pdfTable);
	         System.out.println("生成有投资列表的合同成功");
	         /*回款计划详情生成end----------------------------------------*/
	        }catch(FileNotFoundException de) {  
	            de.printStackTrace();  
	            System.err.println("pdf file: " + de.getMessage());    
	        }catch(DocumentException de) {  
	            de.printStackTrace();  
	            System.err.println("document: " + de.getMessage());  
	 
	        }catch(IOException de) {  
	            de.printStackTrace();  
	            System.err.println("pdf font: " + de.getMessage());  
	
	        }finally{  
	            if(pdfDocument!=null){  
	            	pdfDocument.close();
	            	pdfFileOutputStream.close();
	            }  
	        }
		return savepath;         
    }
    public void mergePdfFiles(String[] files, String newfile) {
        boolean retValue = false;   
       
        FileOutputStream fileOutputStream = null;
        Document document = null;   
        PdfCopy copy = null;
		try {
			fileOutputStream = new FileOutputStream(newfile) ;
            PdfReader pdfReader = new PdfReader(files[0]);
			document = new Document(pdfReader.getPageSize(1));   
            copy = new PdfCopy(document, fileOutputStream); 
            copy.setEncryption(true, null, null, PdfWriter.ALLOW_PRINTING);
            document.open();   
            for (int i = 0; i < files.length; i++) {   
                PdfReader reader = new PdfReader(files[i]);   
                int n = reader.getNumberOfPages();   
                for (int j = 1; j <= n; j++) {   
                    document.newPage();   
                    PdfImportedPage page = copy.getImportedPage(reader, j);   
                    copy.addPage(page);   
                } 
                reader.close();
            }
            pdfReader.close();
            System.out.println("合并合同成功");
        } catch (Exception e) {   
        	logger.info(e+"文件合并出错");
            e.printStackTrace();   
        }  finally {
        	copy.close();
            document.close();
            try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("关流异常");
			}
        } 
    }   
	@Override
	public Page<Loan> uncontractLoans(Page<Loan> page) {
		// TODO Auto-generated method stub
		List<Loan> list = contractDao.uncontractLoans(page);
		page.setResults(list);
		return page;
	}
	

}
