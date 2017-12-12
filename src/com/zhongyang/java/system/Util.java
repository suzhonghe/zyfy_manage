package com.zhongyang.java.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.zhongyang.java.vo.VerificationCode;

public class  Util{

	public static VerificationCode getVerificationCode() {
		int width = 90;// 定义图片的width
		int height = 20;// 定义图片的height
		int codeCount = 4;// 定义图片上显示验证码的个数
		int xx = 15;
		int fontHeight = 18;
		int codeY = 16;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		 Graphics gd = buffImg.getGraphics();
	     Random random = new Random();
	        // 将图像填充为白色
	        gd.setColor(Color.WHITE);
	        gd.fillRect(0, 0, width, height);
	 
	        // 创建字体，字体的大小应该根据图片的高度来定。
	        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
	        // 设置字体。
	        gd.setFont(font);
	 
	        // 画边框。
	        gd.setColor(Color.BLACK);
	        gd.drawRect(0, 0, width - 1, height - 1);
	 
	        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
	        gd.setColor(Color.BLUE);
	        for (int i = 0; i < 5; i++) {
	            int x = random.nextInt(width);
	            int y = random.nextInt(height);
	            int xl = random.nextInt(4);
	            int yl = random.nextInt(4);
	            gd.drawLine(x, y, x + xl, y + yl);
	        }
	 
	        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
	        StringBuffer randomCode = new StringBuffer();
	        int red = 0, green = 0, blue = 0;
	 
	        // 随机产生codeCount数字的验证码。
	        for (int i = 0; i < codeCount; i++) {
	            // 得到随机产生的验证码数字。
	            String code = String.valueOf(codeSequence[random.nextInt(36)]);
	            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
	            red = random.nextInt(255);
	            green = random.nextInt(255);
	            blue = random.nextInt(255);
	 
	            // 用随机产生的颜色将验证码绘制到图像中。
	            gd.setColor(new Color(red, green, blue));
	            gd.drawString(code, (i + 1) * xx, codeY);
	 
	            // 将产生的四个随机数组合在一起。
	            randomCode.append(code);
	        }
	        
	        VerificationCode vc=new VerificationCode();
	        vc.setCode(randomCode.toString());
	        vc.setBuffImg(buffImg);
	        return vc;
	}
	
	public final static String zycf32MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Map<String,Object> getPageProFileValue(String field,String value,String sort){
		Map<String,Object> map=new HashMap<String,Object>();
		String fields[] = null;
		String values[] = null;
		if(field!=null&&value!=null){
			fields=field.split("\\,");
			values=field.split("\\,");
            int i=0;
            for(String string:fields){
                map.put(string, values[i++]);
            }
            if (sort!=null&&"".equals(sort)) {
                map.put("sort", sort);
            }else{
                map.put("sort", "desc");
            }
		}
		return map;
	}
}
