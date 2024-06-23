package edu.hrms.service;

import java.util.List;
import java.util.Map;

import edu.hrms.vo.AlarmVO;

public interface AlarmService {
	
	int insertAlarm(Map<String, Object> map);
	List<AlarmVO> selectAlarm(int userid);
	int updateAlarmRead(int alarmNo);
}
