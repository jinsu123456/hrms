package edu.hrms.vo;

public class MsgReceiveVO {
	private int msgRNo;
	private int msgNo;
	private String state;
	private int userid;
	private String name;
	private String dept;
	private String position;
	private String deptCase;
	
	public int getMsgRNo() {
		return msgRNo;
	}
	public void setMsgRNo(int msgRNo) {
		this.msgRNo = msgRNo;
	}
	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDeptCase() {
		return deptCase;
	}
	public void setDeptCase(String deptCase) {
		this.deptCase = deptCase;
	}
	
}