package edu.hrms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.hrms.vo.EmployeeVO;

@Repository
public class UserDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "edu.hrms.mappers.userMapper";
	
	public Map<String, Object> selectLogin(String username){
		return sqlSession.selectOne(namespace+".selectLogin", username);
	}
	
	public EmployeeVO selectUser(int userid){
		return sqlSession.selectOne(namespace+".selectUser", userid);
	}

	public List<EmployeeVO> selectUserAll(Map<String,Object> map) {
		return sqlSession.selectList(namespace+".selectUserAll", map);
	}

	public int updateUser(EmployeeVO employee) {
		return sqlSession.update(namespace+".updateUser", employee);
	}

	public int insertUser(EmployeeVO employeeVO) {
		return sqlSession.insert(namespace+".insertUser", employeeVO);
	}

	public int selectMaxUserid(String dept) {
		return sqlSession.selectOne(namespace+".selectMaxUserid", dept);
	}

	public int updateFromAdmin(EmployeeVO employeeVO) {
		return sqlSession.update(namespace+".updateFromAdmin", employeeVO);
	}
	
	
}
