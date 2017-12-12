package com.zhongyang.java.biz;

import java.security.GeneralSecurityException;

import com.zhongyang.java.system.DESTextCipher;

public class Test01 {
	public static void main(String[] args) throws GeneralSecurityException {
		 DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		 System.out.println(cipher.encrypt("zycf123456")+"------------------");
	}
}
