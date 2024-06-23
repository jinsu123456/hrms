package edu.hrms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.hrms.vo.OvertimeSignVO;
import edu.hrms.vo.OvertimeVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.WorkVO;

@Repository
public class WorkDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "edu.hrms.mappers.workMapper";
	
	public WorkVO selectMyWork(Map<String, String> map) {
		return sqlSession.selectOne(namespace+".selectMyWork", map);
	}
	
	public String selectMyThisWeekWorkTime(Map<String, String> map) {
		return sqlSession.selectOne(namespace+".selectMyThisWeekWorkTime", map);
	}
	
	public String selectMyThisWeekOvertimeTime(Map<String, String> map) {
		return sqlSession.selectOne(namespace+".selectMyThisWeekOvertimeTime", map);
	}
	
	public String selectMyThisWeekTotalWorkTime(Map<String, String> map) {
		return sqlSession.selectOne(namespace+".selectMyThisWeekTotalWorkTime", map);
	}
	
	public int insert(Map<String, String> map) {
		return sqlSession.insert(namespace+".insert", map);
	}
	
	public int update(Map<String, String> map) {
		return sqlSession.update(namespace+".update", map);
	}
	
	public int updateOvertime(Map<String, Object> map) {
		return sqlSession.update(namespace+".updateOvertime", map);
	}
	
	public int insertOvertime(Map<String, String> map) {
		return sqlSession.insert(namespace+".insertOvertime", map);
	}
	
	public List<SignLineVO> getSignLineList(Map<String, Object> map) {
		return sqlSession.selectList(namespace+".getSignLineList", map);
	}
	
	public int getMaxNoByUserId(String userid) {
		return sqlSession.selectOne(namespace+".getMaxNoByUserId", userid);
	}
	
	public int insertOvertimeSign(List<OvertimeSignVO> list) {
		return sqlSession.insert(namespace+".insertOvertimeSign", list);
	}
	
	public OvertimeVO selectOvertime(int overtimeNo) {
		return sqlSession.selectOne(namespace+".selectOvertime", overtimeNo);
	}
	
	public List<OvertimeSignVO> getOvertimeSignList(int overtimeNo){
		return sqlSession.selectList(namespace+".getOvertimeSignList", overtimeNo);
	}
	
	public int withdrawal(int overtimeNo) {
		return sqlSession.update(namespace+".withdrawal", overtimeNo);
	}
	
	public int overtimesignDelete(int overtimeNo) {
		return sqlSession.delete(namespace+".overtimesignDelete", overtimeNo);
	}
	
	public List<OvertimeVO> overtimeApplicationToday(Map<String, String> map) {
		return sqlSession.selectList(namespace+".overtimeApplicationToday", map);
	}
	
	
	public List<WorkVO> selectAllMyWork(Map<String, Object> map){
		return sqlSession.selectList(namespace+".selectAllMyWork", map);
	}
	
	public List<OvertimeVO> selectAllMyOvertime(Map<String, Object> map){
		return sqlSession.selectList(namespace+".selectAllMyOvertime", map);
	}
	
	public List<WorkVO> selectAllWork(Map<String, Object> map){
		return sqlSession.selectList(namespace+".selectAllWork", map);
	}
	
	public int getCountOfAllWorkList(Map<String, Object> map) {
		return sqlSession.selectOne(namespace+".getCountOfAllWorkList", map);
	}
	
	
	public int updateWork_admin(Map<String, String> map) {
		return sqlSession.update(namespace+".updateWork_admin", map);
	}
	
}
