package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class User {
	private String pmo;
		
    public String getPmo() {
		return pmo;
	}

	public void setPmo(String pmo) {
		this.pmo = pmo;
	}

	private String id;

    private String channel;

    private String clientcode;

    private String email;

    private Boolean enabled;

    private Boolean enterprise;

    private String groupid;

    private String idnumber;
    
    private Date birthDate;

    private Date lastlogindate;

    private String lastmodifiedby;

    private String loginname;

    private String mobile;

    private String name;

    private Boolean needchangepassword;

    private String passphrase;

    private Boolean referralrewarded;

    private Date registerdate;

    private Boolean registryrewarded;

    private String salt;

    private String source;

    private String referralId;

    private String referralRealm;

    private String priv;

    private Boolean type;

    private Boolean idauthenticated;

    private Boolean emailauthenticated;

    private Boolean mobileauthenticated;

    private String paymentpassphrase;

    private String paymentsalt;
    
    private String organizationId;
    
    private BigDecimal inviteAmount;

	public BigDecimal getInviteAmount() {
		return inviteAmount;
	}

	public void setInviteAmount(BigDecimal inviteAmount) {
		this.inviteAmount = inviteAmount;
	}

	public String getId() {
        return id;
    }

    public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode == null ? null : clientcode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public Date getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(Date lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby == null ? null : lastmodifiedby.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getNeedchangepassword() {
        return needchangepassword;
    }

    public void setNeedchangepassword(Boolean needchangepassword) {
        this.needchangepassword = needchangepassword;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase == null ? null : passphrase.trim();
    }

    public Boolean getReferralrewarded() {
        return referralrewarded;
    }

    public void setReferralrewarded(Boolean referralrewarded) {
        this.referralrewarded = referralrewarded;
    }

    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    public Boolean getRegistryrewarded() {
        return registryrewarded;
    }

    public void setRegistryrewarded(Boolean registryrewarded) {
        this.registryrewarded = registryrewarded;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId == null ? null : referralId.trim();
    }

    public String getReferralRealm() {
        return referralRealm;
    }

    public void setReferralRealm(String referralRealm) {
        this.referralRealm = referralRealm == null ? null : referralRealm.trim();
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv == null ? null : priv.trim();
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getIdauthenticated() {
        return idauthenticated;
    }

    public void setIdauthenticated(Boolean idauthenticated) {
        this.idauthenticated = idauthenticated;
    }

    public Boolean getEmailauthenticated() {
        return emailauthenticated;
    }

    public void setEmailauthenticated(Boolean emailauthenticated) {
        this.emailauthenticated = emailauthenticated;
    }

    public Boolean getMobileauthenticated() {
        return mobileauthenticated;
    }

    public void setMobileauthenticated(Boolean mobileauthenticated) {
        this.mobileauthenticated = mobileauthenticated;
    }

    public String getPaymentpassphrase() {
        return paymentpassphrase;
    }

    public void setPaymentpassphrase(String paymentpassphrase) {
        this.paymentpassphrase = paymentpassphrase == null ? null : paymentpassphrase.trim();
    }

    public String getPaymentsalt() {
        return paymentsalt;
    }

    public void setPaymentsalt(String paymentsalt) {
        this.paymentsalt = paymentsalt == null ? null : paymentsalt.trim();
    }

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
}