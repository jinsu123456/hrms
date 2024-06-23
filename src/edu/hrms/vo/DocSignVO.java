package edu.hrms.vo;

public class DocSignVO extends SuperSignVO {

	private int docSignNo;
	private int docNo;
	private int signLineNo;
	private int state;
	
	private String userid;
	private String name;
	private String dept;
	private String position;
	
	private int signOrder;
	private int prev_state;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getDocSignNo() {
		return docSignNo;
	}
	public void setDocSignNo(int docSignNo) {
		this.docSignNo = docSignNo;
	}
	public int getDocNo() {
		return docNo;
	}
	public void setDocNo(int docNo) {
		this.docNo = docNo;
	}
	public int getSignLineNo() {
		return signLineNo;
	}
	public void setSignLineNo(int signLineNo) {
		this.signLineNo = signLineNo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getSignOrder() {
		return signOrder;
	}
	public void setSignOrder(int signOrder) {
		this.signOrder = signOrder;
	}
	public int getPrev_state() {
		return prev_state;
	}
	public void setPrev_state(int prev_state) {
		this.prev_state = prev_state;
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
	
	
	@Override
	public String toString() {
		return "DocSignVO [docSignNo=" + docSignNo + ", docNo=" + docNo + ", signLineNo=" + signLineNo + ", state="
				+ state + ", userid=" + userid + ", name=" + name + ", dept=" + dept + ", position=" + position
				+ ", signOrder=" + signOrder + ", prev_state=" + prev_state + "]";
	}
	
	
	
}
