package edu.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hrms.dao.AlarmDAO;
import edu.hrms.vo.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{
	
	@Autowired
	AlarmDAO alarmDAO;
	
	@Override
	public int insertAlarm(Map<String, Object> map) {
		return alarmDAO.insertAlarm(map);
	}

	@Override
	public List<AlarmVO> selectAlarm(int userid) {
		return alarmDAO.selectAlarm(userid);
	}

	@Override
	public int updateAlarmRead(int alarmNo) {
		return alarmDAO.updateAlarmRead(alarmNo);
	}
}
