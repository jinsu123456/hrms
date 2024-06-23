package edu.hrms.vo;

public class SignLineVO {
	private int userId;
	private String name;
	private String dept;
	private String position;
	private int signOrder;
	private String state;
	private int signLineNo;
	private String prevState;
	private String nextState;
	
	public SignLineVO() {}
	
	public SignLineVO(String name, String dept, String position, int signOrder, String state, int userId, String prevState, String nextState) {
		this.userId = userId;
		this.name = name;
		this.dept = dept;
		this.position = position;
		this.signOrder = signOrder;
		this.state = state;
		this.prevState = prevState;
		this.nextState = nextState;
	}
	
	public int getSignLineNo() {
		return signLineNo;
	}
	public void setSignLineNo(int signLineNo) {
		this.signLineNo = signLineNo;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public int getSignOrder() {
		return signOrder;
	}
	public void setSignOrder(int signOrder) {
		this.signOrder = signOrder;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getPrevState() {
		return prevState;
	}

	public void setPrevState(String prevState) {
		this.prevState = prevState;
	}

	public String getNextState() {
		return nextState;
	}

	public void setNextState(String nextState) {
		this.nextState = nextState;
	}

	@Override
	public String toString() {
		return "SignLineVO [userId=" + userId + ", name=" + name + ", dept=" + dept + ", position=" + position
				+ ", signOrder=" + signOrder + ", state=" + state + ", signLineNo=" + signLineNo + ", prevState="
				+ prevState + ", nextState=" + nextState + "]";
	}
	
	
	
}