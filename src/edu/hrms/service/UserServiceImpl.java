package edu.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hrms.dao.UserDAO;
import edu.hrms.vo.EmployeeVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public Map<String, Object> selectLogin(String username) {
		return userDAO.selectLogin(username);
	}
	
	@Override
	public EmployeeVO selectUser(int userid) {
		return userDAO.selectUser(userid);
	}

	@Override
	public List<EmployeeVO> selectUserAll(Map<String,Object> map) {
		return userDAO.selectUserAll(map);
	}

	@Override
	public int updateUser(EmployeeVO employee) {
		return userDAO.updateUser(employee);
	}

	@Override
	public int insertUser(EmployeeVO employeeVO) {
		return userDAO.insertUser(employeeVO);
	}

	@Override
	public int selectMaxUserid(String dept) {
		return userDAO.selectMaxUserid(dept);
	}

	@Override
	public int updateFromAdmin(EmployeeVO employeeVO) {
		return userDAO.updateFromAdmin(employeeVO);
	}
	
}
