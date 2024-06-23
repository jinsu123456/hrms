package edu.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;

import edu.hrms.vo.OvertimeSignVO;
import edu.hrms.vo.OvertimeVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.SuperSignVO;
import edu.hrms.vo.WorkVO;

public interface WorkService {

	WorkVO selectMyWork(Map<String, String> map);
	
	public Map<String, String> getWorkTimeMap(String userid);
	
	String selectMyThisWeekWorkTime(Map<String, String> map);
	
	String selectMyThisWeekOvertimeTime(Map<String, String> map);
	
	String selectMyThisWeekTotalWorkTime(String myThisWeekWorkTime, String myThisWeekOvertimeTime);
	
	int insert(Map<String, String> map);

	int update(Map<String, String> map);
	
	int updateOvertime(Map<String, Object> map);
	
	int insertOvertime(Map<String, String> map);
	
	List<SignLineVO> getSignLineList(String userid, String position, String type);
	
	int getMaxNoByUserId(String userid);
	
	int insertOvertimeSign(List<OvertimeSignVO> list);
	
	int withdrawal(int overtimeNo);
	
	int overtimesignDelete(int overtimeNo);
	
	OvertimeVO[] overtimeApplicationToday(Map<String, String> map);

	List<WorkVO> selectAllMyWork(Map<String, Object> map);
	
	List<OvertimeVO> selectAllMyOvertime(Map<String, Object> map);
	
	OvertimeVO selectOvertime(int overtimeNo);
	
	List<OvertimeSignVO> getOvertimeSignList(List<SignLineVO> signLineList, int overtimeNo);
	
	List<OvertimeSignVO> getOvertimeSignList(int overtimeNo);
	
	String[] getDeptArr(String dept);
	
	List<WorkVO> selectAllWork(Map<String, Object> map);
	
	int getCountOfAllWorkList(Map<String, Object> map);
	
	List<? extends SuperSignVO> processList(List<? extends SuperSignVO> list);
	
	Map<String, Object> getCountNowstate(List<? extends SuperSignVO> list, String state);
	
	@Secured("ROLE_ADMIN")
	int updateWork_admin(Map<String, String> map);
}
