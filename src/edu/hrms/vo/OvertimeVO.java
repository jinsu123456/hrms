package edu.hrms.vo;

public class OvertimeVO {

	private int overtimeNo;
	private String userid;
	private String date;
	private String start;
	private String end;
	private String state;
	private String content;
	private String total;
	private String rdate;
	private String name;
	private String dayOfWeek;
	
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getOvertimeNo() {
		return overtimeNo;
	}
	public void setOvertimeNo(int overtimeNo) {
		this.overtimeNo = overtimeNo;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		return "OvertimeVO [overtimeNo=" + overtimeNo + ", userid=" + userid + ", date=" + date + ", start=" + start
				+ ", end=" + end + ", state=" + state + ", content=" + content + ", total=" + total + ", rdate=" + rdate
				+ ", name=" + name + ", dayOfWeek=" + dayOfWeek + "]";
	}
	
	
	
}
