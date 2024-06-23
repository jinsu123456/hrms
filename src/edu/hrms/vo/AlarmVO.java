package edu.hrms.vo;

public class AlarmVO {
	private int userid;
	private int alarmNo;
	private String alarmState;
	private String contentNo;
	private String rdate;
	private String state;
	private String type;
	private String content;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getAlarmNo() {
		return alarmNo;
	}
	public void setAlarmNo(int alarmNo) {
		this.alarmNo = alarmNo;
	}
	public String getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}
	public String getContentNo() {
		return contentNo;
	}
	public void setContentNo(String contentNo) {
		this.contentNo = contentNo;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
