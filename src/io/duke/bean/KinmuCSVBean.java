package io.duke.bean;


public class KinmuCSVBean {

    private String syain_no = null;
    private String nowDate = null;
    private String startTime = null;
    private String endTime = null;

    /**
     *
     * @return
     * @generated
     */
    public String getSyain_no() {

        return syain_no;
    }

    /**
     *
     * @param
     * @generated
     */
    public void setSyain_no(String syain_no) {

        this.syain_no = syain_no;
    }

    /**
     *
     * @return
     * @generated
     */
    public String getNowDate() {

        return nowDate;
    }

    /**
     *
     * @param
     * @generated
     */
    public void setNowDate(String nowDate) {

        this.nowDate = nowDate;
    }

    /**
     *
     * @return
     * @generated
     */
    public String getStartTime() {

        return startTime;
    }

    /**
     *
     * @param
     * @generated
     */
    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    /**
     *
     * @return
     * @generated
     */
    public String getEndTime() {

        return endTime;
    }

    /**
     *
     * @param
     * @generated
     */
    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }

	@Override
	public String toString() {
		return "KinmuCSVBean [syain_no=" + syain_no + ", nowDate=" + nowDate + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

}
