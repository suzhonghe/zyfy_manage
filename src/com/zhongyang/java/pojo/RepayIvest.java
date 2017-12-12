package com.zhongyang.java.pojo;

/**
 * Created by Matthew on 2015/12/29.
 */
public class RepayIvest {

        private String loanID;
        private int currentPeriod;
        private String source;
        

    public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

	public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }


    public RepayIvest(String loanID, int currentPeriod) {
        this.loanID = loanID;
        this.currentPeriod = currentPeriod;
    }

    public int getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public RepayIvest() {
    }

    @Override
    public String toString() {
        return "RepayIvest{" +
                "loanID='" + loanID + '\'' +
                ", currentprtiod=" + currentPeriod +
                '}';
    }
}
