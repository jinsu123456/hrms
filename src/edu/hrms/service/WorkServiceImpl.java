package edu.hrms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hrms.dao.WorkDAO;
import edu.hrms.util.CalcCalendar;
import edu.hrms.vo.OvertimeSignVO;
import edu.hrms.vo.OvertimeVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.SuperSignVO;
import edu.hrms.vo.WorkVO;

@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	WorkDAO workDAO;
	
	@Autowired
	CalcCalendar calcCalendar;
	
	@Override
	public WorkVO selectMyWork(Map<String, String> map) {
		return workDAO.selectMyWork(map);
	}
	
	@Override
	public Map<String, String> getWorkTimeMap(String userid){
		Map<String, String> workTimeMap = calcCalendar.getFirstLastDays(calcCalendar.getTodayDate());
		workTimeMap.put("userid",userid);
		return workTimeMap;
	}
	
	@Override
	public String selectMyThisWeekWorkTime(Map<String, String> map) {
		return workDAO.selectMyThisWeekWorkTime(map);
	}
	
	@Override
	public String selectMyThisWeekOvertimeTime(Map<String, String> map) {
		return workDAO.selectMyThisWeekOvertimeTime(map);
	}
	
	@Override
	public String selectMyThisWeekTotalWorkTime(String myThisWeekWorkTime, String myThisWeekOvertimeTime) {
		Map<String, String> myTotalWorkTimeMap = new HashMap<>();
		myTotalWorkTimeMap.put("workTime", myThisWeekWorkTime);
		myTotalWorkTimeMap.put("overtimeTime", myThisWeekOvertimeTime);
		
		return workDAO.selectMyThisWeekTotalWorkTime(myTotalWorkTimeMap);
	}
	
	@Override
	public int insert(Map<String, String> map) {
		return workDAO.insert(map);
	}
	
	@Override
	public int update(Map<String, String> map) {
		return workDAO.update(map);
	}
	
	@Override
	public int updateOvertime(Map<String, Object> map) {
		return workDAO.updateOvertime(map);
	}
	
	@Override
	public int insertOvertime(Map<String, String> map) {
		return workDAO.insertOvertime(map);
	}
	
	@Override
	public int getMaxNoByUserId(String userid) {
		return workDAO.getMaxNoByUserId(userid);
	}
	
	@Override
	public int insertOvertimeSign(List<OvertimeSignVO> list) {
		return workDAO.insertOvertimeSign(list);
	}
	
	@Override
	public List<WorkVO> selectAllMyWork(Map<String, Object> map) {
		return workDAO.selectAllMyWork(map);
	}
	
	@Override
	public List<OvertimeVO> selectAllMyOvertime(Map<String, Object> map) {
		return workDAO.selectAllMyOvertime(map);
	}
	
	@Override
	public OvertimeVO selectOvertime(int overtimeNo) {
		return workDAO.selectOvertime(overtimeNo);
	}
	
	@Override
	public List<SignLineVO> getSignLineList(String userid, String position, String type) {
		if(position.equals("E")) {
			position = "C,D,L";
		}else if(position.equals("L")) {
			position = "C,D";
		}else if(position.equals("D")) {
			position = "C";
		}
		String[] positionArr = position.split(",");
		Map<String, Object> signLineMap = new HashMap<>();
		signLineMap.put("userid", userid);
		signLineMap.put("positionArr", positionArr);
		signLineMap.put("type", type);
		return workDAO.getSignLineList(signLineMap);
	}
	
	@Override
	// insert 할 때 사용할 signVO 리스트 생성 메소드
	public List<OvertimeSignVO> getOvertimeSignList(List<SignLineVO> signLineList, int overtimeNo) {
		List<OvertimeSignVO> overtimeSignList = new ArrayList<>();
		for(SignLineVO vo : signLineList) {
			OvertimeSignVO ovo = new OvertimeSignVO();
			ovo.setOvertimeNo(overtimeNo);
			ovo.setSignLineNo(vo.getSignLineNo());
			overtimeSignList.add(ovo);
		}
		return overtimeSignList;
	}
	
	@Override
	// db에서 signVO 리스트 가져올 때 사용하는 메소드
	public List<OvertimeSignVO> getOvertimeSignList(int overtimeNo) {
		return workDAO.getOvertimeSignList(overtimeNo);
	}
	
	@Override
	public int withdrawal(int overtimeNo) {
		return workDAO.withdrawal(overtimeNo);
	}
	
	@Override
	public int overtimesignDelete(int overtimeNo) {
		return workDAO.overtimesignDelete(overtimeNo);
	}
	
	@Override
	public OvertimeVO[] overtimeApplicationToday(Map<String, String> map) {
		List<OvertimeVO> ovoAppList = workDAO.overtimeApplicationToday(map);
		OvertimeVO[] arr = new OvertimeVO[2];
		if(ovoAppList.size()!=0) {
			for(OvertimeVO data : ovoAppList) {
				if(data.getStart().equals("12:00:00")) {
					arr[0] = data;
				}else {
					arr[1] = data;
				}
			}
		}
		return arr;
	}
	
	
	@Override
	public String[] getDeptArr(String dept) {
		String[] deptArr = null;
		if(dept.equals("M")) {
			deptArr = new String[] {"M","D","S","P","H"};
		}else {
			deptArr = new String[] {dept};
		}
		return deptArr;
	}
	
	@Override
	public List<WorkVO> selectAllWork(Map<String, Object> map) {
		return workDAO.selectAllWork(map);
	}
	
	@Override
	public int getCountOfAllWorkList(Map<String, Object> map) {
		return workDAO.getCountOfAllWorkList(map);
	}
	
	@Override
	public List<? extends SuperSignVO> processList(List<? extends SuperSignVO> list){
		boolean returningFlag = false;
		for(SuperSignVO data : list) {
			if(data.getPrev_state()==2 && data.getState()==0) {
				data.setState(1);
			}
			if(returningFlag) {
				data.setState(9);
			}
			if(data.getPrev_state()==3) {
				data.setState(9);
				returningFlag = true;
			}
		}
		return list;
	}
	
	// 테스트
	public <T extends SuperSignVO> List<T> test(List<T> list){
		boolean returningFlag = false;
		for(T data : list) {
			if(data.getPrev_state()==2 && data.getState()==0) {
				data.setState(1);
			}
			if(returningFlag) {
				data.setState(9);
			}
			if(data.getPrev_state()==3) {
				data.setState(9);
				returningFlag = true;
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getCountNowstate(List<? extends SuperSignVO> list, String state){
		Map<String, Object> map = new HashMap<>();
		int count = 0;
		String nowState = "대기";
		for(SuperSignVO vo : list) {
			if(vo.getState()==2) {
				count++;
				nowState = "진행";
			}else if(vo.getState()==3) {
				nowState = "반려";
				break;
			}
		}
		if(state.equals("9")) {
			nowState = "철회";
		}else if(state.equals("2") || state.equals("7")) {
			nowState = "승인";
		}
		
		map.put("count", count);
		map.put("nowState", nowState);
		return map;
	}
	
	
	@Override
	public int updateWork_admin(Map<String, String> map) {
		return workDAO.updateWork_admin(map);
	}
	
}
