package com.zhongyang.java.sys.uitl;

import java.util.List;

import com.zhongyang.java.pojo.Role;

public class AuthorityHelper {
	/**
	 * 鉴权
	 * @param key
	 * @param auth
	 * @return
	 */

	public static boolean checkAuthority(int key,String auth){
		
		if(auth == null || "".equals(auth))
			return false;
		
		char value = auth.charAt(key);
		if(value == '1')
			return true;
		return false;
	}
	

	/**
	 * 创建权限字符串
	 * @param akeys 有权限的项,比如 1,3,6,11,20
	 * @return 权限字符串, 比如0101001001000000000
	 */
	public static String makeAuthority(String akeys){
		StringBuilder sb = new StringBuilder(_RAW);
		String []akeys_s = akeys.split(",");
		for(String akey: akeys_s){
			if(null == akey || "".equals(akey)){
				continue;
			}
			int ak = Integer.parseInt(akey);
			sb.setCharAt(ak, '1');
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 如果用户有多个角色，合并不同角色的权限
	 * @param list
	 * @return
	 */
		
	public static String unifyAuthorities(List<Role> list){
		StringBuilder sb = new StringBuilder(_RAW);
		
		for(Role role: list)
		{
			String s = role.getPriviliges();
			if(s!=null && s!="")
				for(int i=0;i<s.length();i++)
					if(s.charAt(i)=='1')
						sb.setCharAt(i, '1');
		}
		
		return sb.toString();
	}
	
	final public static String _RAW = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	
	final public static String _RAW_ON = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

	
}
