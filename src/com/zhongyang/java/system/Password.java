package com.zhongyang.java.system;


import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
public class Password {

	
	/**
	 * 
	 * 
	 * N+4zfpTfT/b9e1ROL/f7cR+lrDkxeku3
	 * 37132819810608401682245646
	 * 
	 */
	
    private static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Password p = new Password();
		String epassword = "ff6b6db0b2f4506c074343f20805a39ca856bf3a";
		String passText = "123456";
		String id = "371328198106084016";
		String salt = p.getSalt(id);
		String sp = p.getPassphrase(salt, passText);
		System.out.println("salt : +" + salt +" passphrase: "+sp);
		System.out.println(p.matchPassphrase(sp, salt, passText) + "--------------- for real :");
		salt ="SjZ1MmwxIDIzMiw2IDEyOTA5MTE1MSAxNDE6ODE2NjEgM1A4TQ==";
		sp = "11895b3270e3fd9038252d4fd45400b6fa30de4f";
		System.out.println(p.matchPassphrase(sp, salt, "123456"));
	}

	
    /**
     * Salt is Base64(now + identity)
     *
     * Identity can be null, will generate a random alphanumeric instead.
     *
     * @param identity can be null
     * @return
     */
    public static String getSalt(String identity) {
        String now = df.format(new Date());
        String identityString = StringUtils.isBlank(identity)
                                ? RandomStringUtils.randomAlphanumeric(20)
                                : identity.trim();
        return Base64.encodeBase64String(blend(now.getBytes(), identityString.getBytes()));
    }

    /**
     * Stored passphrase
     *
     * @param salt
     * @param userPassword
     * @return
     */
    public static String getPassphrase(String salt, String userPassword) {
        return DigestUtils.sha1Hex(blend(salt.getBytes(), userPassword.getBytes()));
    }

    public static boolean matchPassphrase(final String passphrase, final String salt, final String userPassword) {
        boolean result = passphrase.equalsIgnoreCase(getPassphrase(salt, userPassword));
        if (!result) {
           System.out.println("密码不对！");
        }
        return result;
    }

    private static byte[] blend(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        int ai = 0;
        int bi = 0;
        for (int i = 0; i < result.length; i++) {
            if (ai == a.length || bi < ai && bi < b.length) {
                result[i] = b[bi++];
                continue;
            }
            if (bi == b.length || ai <= bi) {
                result[i] = a[ai++];
                continue;
            }
        }
        return result;
    }
}
