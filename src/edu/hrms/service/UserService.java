package edu.hrms.service;

import java.util.List;
import java.util.Map;

import edu.hrms.vo.EmployeeVO;

public interface UserService {

	public Map<String, Object> selectLogin(String username);

	public EmployeeVO selectUser(int userid);
	
	public List<EmployeeVO> selectUserAll(Map<String,Object> map);

	public int updateUser(EmployeeVO employee);

	public int insertUser(EmployeeVO employeeVO);

	public int selectMaxUserid(String dept);

	public int updateFromAdmin(EmployeeVO employeeVO);
}
