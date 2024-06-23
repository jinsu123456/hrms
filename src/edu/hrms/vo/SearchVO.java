package edu.hrms.vo;

public class SearchVO {

	private String startDate;
	private String endDate;
	private String listType;
	private String position;
	private String searchVal;
	
	private String category_dept;
	private String category_position;
	
	
	public String getCategory_dept() {
		return category_dept;
	}
	public void setCategory_dept(String category_dept) {
		this.category_dept = category_dept;
	}
	public String getCategory_position() {
		return category_position;
	}
	public void setCategory_position(String category_position) {
		this.category_position = category_position;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		if(startDate.equals("")) {
			this.startDate = null;
		}else {
			this.startDate = startDate;
		}
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		if(endDate.equals("")) {
			this.endDate = null;
		}else {
			this.endDate = endDate;
		}
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	
	@Override
	public String toString() {
		return "SearchVO [startDate=" + startDate + ", endDate=" + endDate + ", listType=" + listType + ", position="
				+ position + ", searchVal=" + searchVal + ", category_dept=" + category_dept
				+ ", category_position=" + category_position + "]";
	}
	
	
	
	
}
