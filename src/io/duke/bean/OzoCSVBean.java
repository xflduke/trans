package io.duke.bean;

public class OzoCSVBean {

    private String syain_no = null;
    private String nowDate = null;
    private String startTime = null;
    private String endTime = null;
    private String reason = null;


	public OzoCSVBean(String syain_no, String nowDate, String startTime, String endTime, String reason) {
		super();
		this.syain_no = syain_no;
		this.nowDate = nowDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reason = reason;
	}

	public OzoCSVBean() {
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSyain_no() {
		return syain_no;
	}
	public void setSyain_no(String syain_no) {
		this.syain_no = syain_no;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "OzoCSVBean [syain_no=" + syain_no + ", nowDate=" + nowDate + ", startTime=" + startTime + ", endTime="
				+ endTime + ", reason=" + reason + "]";
	}
}
