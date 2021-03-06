package com.zhongyang.java.vo;

import java.io.Serializable;
import java.util.Date;

public class EmployeeVo implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.ClientCode
     *
     * @mbggenerated
     */
    private String clientcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.EMP_ID
     *
     * @mbggenerated
     */
    private String empId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.ENABLED
     *
     * @mbggenerated
     */
    private Boolean enabled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.IDNUMBER
     *
     * @mbggenerated
     */
    private String idnumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.LASTLOGINDATE
     *
     * @mbggenerated
     */
    private Date lastlogindate;
    

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.LOGIN_NAME
     *
     * @mbggenerated
     */
    private String loginName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.MOBILE
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.NAME
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.NEEDCHANGEPASSWORD
     *
     * @mbggenerated
     */
    private Boolean needchangepassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.PASSPHRASE
     *
     * @mbggenerated
     */
    private String passphrase;
    
    private String oldpasswd;

    public String getOldpasswd() {
		return oldpasswd;
	}

	public void setOldpasswd(String oldpasswd) {
		this.oldpasswd = oldpasswd;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.REGISTERDATE
     *
     * @mbggenerated
     */
    private Date registerdate;
    
  
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.SALT
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_EMPLOYEE.BRANCH_ID
     *
     * @mbggenerated
     */
    private String branchId;

    
    private String regDate;
    private String lastDate;
    
    public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.ID
     *
     * @return the value of TB_EMPLOYEE.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.ID
     *
     * @param id the value for TB_EMPLOYEE.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.ClientCode
     *
     * @return the value of TB_EMPLOYEE.ClientCode
     *
     * @mbggenerated
     */
    public String getClientcode() {
        return clientcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.ClientCode
     *
     * @param clientcode the value for TB_EMPLOYEE.ClientCode
     *
     * @mbggenerated
     */
    public void setClientcode(String clientcode) {
        this.clientcode = clientcode == null ? null : clientcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.EMP_ID
     *
     * @return the value of TB_EMPLOYEE.EMP_ID
     *
     * @mbggenerated
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.EMP_ID
     *
     * @param empId the value for TB_EMPLOYEE.EMP_ID
     *
     * @mbggenerated
     */
    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.ENABLED
     *
     * @return the value of TB_EMPLOYEE.ENABLED
     *
     * @mbggenerated
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.ENABLED
     *
     * @param enabled the value for TB_EMPLOYEE.ENABLED
     *
     * @mbggenerated
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.IDNUMBER
     *
     * @return the value of TB_EMPLOYEE.IDNUMBER
     *
     * @mbggenerated
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.IDNUMBER
     *
     * @param idnumber the value for TB_EMPLOYEE.IDNUMBER
     *
     * @mbggenerated
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.LASTLOGINDATE
     *
     * @return the value of TB_EMPLOYEE.LASTLOGINDATE
     *
     * @mbggenerated
     */
    public Date getLastlogindate() {
        return lastlogindate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.LASTLOGINDATE
     *
     * @param lastlogindate the value for TB_EMPLOYEE.LASTLOGINDATE
     *
     * @mbggenerated
     */
    public void setLastlogindate(Date lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.LOGIN_NAME
     *
     * @return the value of TB_EMPLOYEE.LOGIN_NAME
     *
     * @mbggenerated
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.LOGIN_NAME
     *
     * @param loginName the value for TB_EMPLOYEE.LOGIN_NAME
     *
     * @mbggenerated
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.MOBILE
     *
     * @return the value of TB_EMPLOYEE.MOBILE
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.MOBILE
     *
     * @param mobile the value for TB_EMPLOYEE.MOBILE
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.NAME
     *
     * @return the value of TB_EMPLOYEE.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.NAME
     *
     * @param name the value for TB_EMPLOYEE.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.NEEDCHANGEPASSWORD
     *
     * @return the value of TB_EMPLOYEE.NEEDCHANGEPASSWORD
     *
     * @mbggenerated
     */
    public Boolean getNeedchangepassword() {
        return needchangepassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.NEEDCHANGEPASSWORD
     *
     * @param needchangepassword the value for TB_EMPLOYEE.NEEDCHANGEPASSWORD
     *
     * @mbggenerated
     */
    public void setNeedchangepassword(Boolean needchangepassword) {
        this.needchangepassword = needchangepassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.PASSPHRASE
     *
     * @return the value of TB_EMPLOYEE.PASSPHRASE
     *
     * @mbggenerated
     */
    public String getPassphrase() {
        return passphrase;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.PASSPHRASE
     *
     * @param passphrase the value for TB_EMPLOYEE.PASSPHRASE
     *
     * @mbggenerated
     */
    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase == null ? null : passphrase.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.REGISTERDATE
     *
     * @return the value of TB_EMPLOYEE.REGISTERDATE
     *
     * @mbggenerated
     */
    public Date getRegisterdate() {
        return registerdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.REGISTERDATE
     *
     * @param registerdate the value for TB_EMPLOYEE.REGISTERDATE
     *
     * @mbggenerated
     */
    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.SALT
     *
     * @return the value of TB_EMPLOYEE.SALT
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.SALT
     *
     * @param salt the value for TB_EMPLOYEE.SALT
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_EMPLOYEE.BRANCH_ID
     *
     * @return the value of TB_EMPLOYEE.BRANCH_ID
     *
     * @mbggenerated
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_EMPLOYEE.BRANCH_ID
     *
     * @param branchId the value for TB_EMPLOYEE.BRANCH_ID
     *
     * @mbggenerated
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }
}