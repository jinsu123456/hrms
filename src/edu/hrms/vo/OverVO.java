package edu.hrms.vo;

import java.util.List;

public class OverVO {
	private int overTimeNo;
	private int userId;
	private String date;
	private String start;
	private String end;
	private String state;
	private String content;
	private String rdate;
	
	private String name;
	private String dept;
	private String position;
	
	private String mySignState;
	private int mySignOrder;
	private int stateCount;
	private String mySignLineNo;
	
	private List<SignLineVO> signLineVO;

	public int getOverTimeNo() {
		return overTimeNo;
	}

	public void setOverTimeNo(int overTimeNo) {
		this.overTimeNo = overTimeNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
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

	public String getMySignState() {
		return mySignState;
	}

	public void setMySignState(String mySignState) {
		this.mySignState = mySignState;
	}

	public int getMySignOrder() {
		return mySignOrder;
	}

	public void setMySignOrder(int mySignOrder) {
		this.mySignOrder = mySignOrder;
	}

	public int getStateCount() {
		return stateCount;
	}

	public void setStateCount(int stateCount) {
		this.stateCount = stateCount;
	}

	public String getMySignLineNo() {
		return mySignLineNo;
	}

	public void setMySignLineNo(String mySignLineNo) {
		this.mySignLineNo = mySignLineNo;
	}

	public List<SignLineVO> getSignLineVO() {
		return signLineVO;
	}

	public void setSignLineVO(List<SignLineVO> signLineVO) {
		this.signLineVO = signLineVO;
	}
	
}