package com.zhongyang.java.pojo;

import java.io.Serializable;

public class SysAuthorization implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_SYS_AUTHORIZATION.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_SYS_AUTHORIZATION.role_id
     *
     * @mbggenerated
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_SYS_AUTHORIZATION.func_id
     *
     * @mbggenerated
     */
    private Integer funcId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_SYS_AUTHORIZATION.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_SYS_AUTHORIZATION.is_del
     *
     * @mbggenerated
     */
    private Integer isDel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_AUTHORIZATION.id
     *
     * @return the value of TB_SYS_AUTHORIZATION.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_AUTHORIZATION.id
     *
     * @param id the value for TB_SYS_AUTHORIZATION.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_AUTHORIZATION.role_id
     *
     * @return the value of TB_SYS_AUTHORIZATION.role_id
     *
     * @mbggenerated
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_AUTHORIZATION.role_id
     *
     * @param roleId the value for TB_SYS_AUTHORIZATION.role_id
     *
     * @mbggenerated
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_AUTHORIZATION.func_id
     *
     * @return the value of TB_SYS_AUTHORIZATION.func_id
     *
     * @mbggenerated
     */
    public Integer getFuncId() {
        return funcId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_AUTHORIZATION.func_id
     *
     * @param funcId the value for TB_SYS_AUTHORIZATION.func_id
     *
     * @mbggenerated
     */
    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_AUTHORIZATION.remark
     *
     * @return the value of TB_SYS_AUTHORIZATION.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_AUTHORIZATION.remark
     *
     * @param remark the value for TB_SYS_AUTHORIZATION.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_AUTHORIZATION.is_del
     *
     * @return the value of TB_SYS_AUTHORIZATION.is_del
     *
     * @mbggenerated
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_AUTHORIZATION.is_del
     *
     * @param isDel the value for TB_SYS_AUTHORIZATION.is_del
     *
     * @mbggenerated
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}