package edu.hrms.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserVO extends User {
	
	private String userid;
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
	private String password;
	
	public UserVO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,String authority,String name) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		// security 외 필요한 유저 정보를 추가한다.
		this.userid =username;
		this.authority = authority;
		this.name=name;
	}
	
	public UserVO(String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities, String authority, 
					String name, String email, String dept, String position, String joinDate, String retireDate, int state,
					String phone, String addr, int keepVaca, int useVaca, String deptCase, String positionCase, String phoneNumber) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		// security 외 필요한 유저 정보를 추가한다.
		this.userid = username;
		this.authority = authority;
		this.name = name;
		this.email = email;
		this.dept = dept;
		this.position = position;
		this.joinDate = joinDate;
		this.retireDate = retireDate;
		this.state = state;
		this.phone = phone;
		this.addr = addr;
		this.keepVaca = keepVaca;
		this.useVaca = useVaca;
		this.deptCase = deptCase;
		this.positionCase = positionCase;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
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


	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", name=" + name + ", authority=" + authority + ", email=" + email
				+ ", dept=" + dept + ", position=" + position + ", joinDate=" + joinDate + ", retireDate=" + retireDate
				+ ", state=" + state + ", phone=" + phone + ", addr=" + addr + ", keepVaca=" + keepVaca + ", useVaca="
				+ useVaca + "]";
	}


	

	
}
