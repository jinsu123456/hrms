package edu.hrms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.hrms.vo.AlarmVO;

@Repository
public class AlarmDAO {

	private final String namespace = "edu.hrms.mappers.alarmMapper";
	
	@Autowired
	SqlSession sqlSession;
	
	public int insertAlarm(Map<String, Object> map) {
		return sqlSession.insert(namespace+".insertAlarm", map);
	}
	
	public List<AlarmVO> selectAlarm(int userid){
		return sqlSession.selectList(namespace+".selectAlarm", userid);
	}

	public int updateAlarmRead(int alarmNo) {
		return sqlSession.update(namespace+".updateAlarmRead", alarmNo);
	}
	
}
