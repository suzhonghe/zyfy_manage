package com.zhongyang.java.pojo;

import java.io.Serializable;

public class Role implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_ROLE.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_ROLE.ClientCode
     *
     * @mbggenerated
     */
    private String clientcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_ROLE.DESCRIPTION
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ,所属表字段为TB_ROLE.NAME
     *
     * @mbggenerated
     */
    private String name;
    
    private String priviliges;

    public String getPriviliges() {
		return priviliges;
	}

	public void setPriviliges(String priviliges) {
		this.priviliges = priviliges;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_ROLE.ID
     *
     * @return the value of TB_ROLE.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_ROLE.ID
     *
     * @param id the value for TB_ROLE.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_ROLE.ClientCode
     *
     * @return the value of TB_ROLE.ClientCode
     *
     * @mbggenerated
     */
    public String getClientcode() {
        return clientcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_ROLE.ClientCode
     *
     * @param clientcode the value for TB_ROLE.ClientCode
     *
     * @mbggenerated
     */
    public void setClientcode(String clientcode) {
        this.clientcode = clientcode == null ? null : clientcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_ROLE.DESCRIPTION
     *
     * @return the value of TB_ROLE.DESCRIPTION
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_ROLE.DESCRIPTION
     *
     * @param description the value for TB_ROLE.DESCRIPTION
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_ROLE.NAME
     *
     * @return the value of TB_ROLE.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_ROLE.NAME
     *
     * @param name the value for TB_ROLE.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}