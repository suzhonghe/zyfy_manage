package com.zhongyang.java.pojo;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matthew on 2016/1/11.
 */
public class RepayQuery {
        private String id;
        private String loanId;
        private String title;
        private String userId;
        private String name;
        private int currentperiod;
        private BigDecimal money;
        private BigDecimal available_amount;
        private BigDecimal amountTender;
        private BigDecimal amountInterest;
        private BigDecimal amountPrincipal;
        private String status;
        private Date duedate;
        private String sourceId;
        private BigDecimal amount;
        private Date repayDate;
        private String date;




    public String getDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(duedate);
		}

	public BigDecimal getAmount() {
			return amountInterest.add(amountPrincipal);
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

	public String getSourceId() {
			return sourceId;
		}

		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}

	public Date getRepayDate() {
			return repayDate;
		}

		public void setRepayDate(Date repayDate) {
			this.repayDate = repayDate;
		}

	public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

	public String getLoanId() {
			return loanId;
		}

		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}

		public BigDecimal getAmountTender() {
			return amountTender;
		}

		public void setAmountTender(BigDecimal amountTender) {
			this.amountTender = amountTender;
		}

		public BigDecimal getAmountInterest() {
			return amountInterest;
		}

		public void setAmountInterest(BigDecimal amountInterest) {
			this.amountInterest = amountInterest;
		}

		public BigDecimal getAmountPrincipal() {
			return amountPrincipal;
		}

		public void setAmountPrincipal(BigDecimal amountPrincipal) {
			this.amountPrincipal = amountPrincipal;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentperiod() {
        return currentperiod;
    }

    public void setCurrentperiod(int currentperiod) {
        this.currentperiod = currentperiod;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(BigDecimal available_amount) {
        this.available_amount = available_amount;
    }



    public Date getDuedate() {
    
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public RepayQuery( ) {

    }

    public RepayQuery(String id, String title, String name, int currentperiod, BigDecimal money, BigDecimal available_amount, BigDecimal amount, Date duedate) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.currentperiod = currentperiod;
        this.money = money;
        this.available_amount = available_amount;
        this.amountTender = amountTender;
        this.duedate = duedate;
    }

    @Override
    public String toString() {
        return "RepayQuery{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", currentperiod=" + currentperiod +
                ", money=" + money +
                ", available_amount=" + available_amount +
                ", amount=" + amountTender +
                '}';
    }
}
