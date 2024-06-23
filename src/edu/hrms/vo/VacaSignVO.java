package edu.hrms.vo;

public class VacaSignVO extends SuperSignVO {

	private int vacaSignNo;
	private int vacaNo;
	private int signLineNo;
	private int state;
	private String name;
	private String dept;
	private String position;
	private int signOrder;
	private int prev_state;
	
	public int getVacaSignNo() {
		return vacaSignNo;
	}
	public void setVacaSignNo(int vacaSignNo) {
		this.vacaSignNo = vacaSignNo;
	} 
	public int getVacaNo() {
		return vacaNo;
	}
	public void setVacaNo(int vacaNo) {
		this.vacaNo = vacaNo;
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
	public int getPrev_state() {
		return prev_state;
	}
	public void setPrev_state(int prev_state) {
		this.prev_state = prev_state;
	}
	
	
	@Override
	public String toString() {
		return "VacaSignVO [vacaSignNo=" + vacaSignNo + ", vacaNo=" + vacaNo + ", signLineNo=" + signLineNo + ", state="
				+ state + ", name=" + name + ", dept=" + dept + ", position=" + position + ", signOrder=" + signOrder
				+ ", prev_state=" + prev_state + "]";
	}
	
	
}
