package edu.hrms.vo;

public class EmployeeVO {
	private String userid;
	private String password;
	private String name;
	private String authority;
	private String email;
	private String dept;
	private String position;
	private String joinDate;
	private String retireDate;
	private int state;
	private String phone;
	private String phoneNumber;
	private String addr;
	private int keepVaca;
	private int useVaca;
	private String deptCase;
	private String positionCase;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getRetireDate() {
		return retireDate;
	}
	public void setRetireDate(String retireDate) {
		this.retireDate = retireDate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getKeepVaca() {
		return keepVaca;
	}
	public void setKeepVaca(int keepVaca) {
		this.keepVaca = keepVaca;
	}
	public int getUseVaca() {
		return useVaca;
	}
	public void setUseVaca(int useVaca) {
		this.useVaca = useVaca;
	}
	public String getDeptCase() {
		return deptCase;
	}
	public void setDeptCase(String deptCase) {
		this.deptCase = deptCase;
	}
	public String getPositionCase() {
		return positionCase;
	}
	public void setPositionCase(String positionCase) {
		this.positionCase = positionCase;
	}
	
	@Override
	public String toString() {
		return "EmployeeVO [userid=" + userid + ", password=" + password + ", name=" + name + ", authority=" + authority
				+ ", email=" + email + ", dept=" + dept + ", position=" + position + ", joinDate=" + joinDate
				+ ", retireDate=" + retireDate + ", state=" + state + ", phone=" + phone + ", phoneNumber="
				+ phoneNumber + ", addr=" + addr + ", keepVaca=" + keepVaca + ", useVaca=" + useVaca + ", deptCase="
				+ deptCase + ", positionCase=" + positionCase + "]";
	}
	
}
