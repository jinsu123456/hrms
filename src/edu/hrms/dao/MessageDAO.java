package edu.hrms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.hrms.vo.MsgReceiveVO;
import edu.hrms.vo.MsgVO;

@Repository
public class MessageDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "edu.hrms.mapper.messageMapper";
	
	public List<MsgReceiveVO> selectReceiverAll(){
		return sqlSession.selectList(namespace+".selectReceiverAll");
	}
	
	public List<Map<String, String>> selectDeptCount(){
		return sqlSession.selectList(namespace+".selectDeptCount");
	}
	
	public int insertMsg(Map<String,Object> map) {
		return sqlSession.insert(namespace+".insertMsg", map);
	}
	
	public int insertMsgReceive(Map<String,Object> map) {
		return sqlSession.insert(namespace+".insertMsgReceive", map);
	}
	
	public List<MsgVO> selectMsgAll(Map<String,Object> map){
		return sqlSession.selectList(namespace+".selectMsgAll", map);
	}
	
	public List<MsgVO> selectMsgReceiveAll(Map<String,Object> map){
		return sqlSession.selectList(namespace+".selectMsgReceiveAll", map);
	}

	public int updateMsgRead(int msgRNo) {
		return sqlSession.update(namespace+".updateMsgRead", msgRNo);
	}

	public int deleteMsgReceive(int msgRNo) {
		return sqlSession.delete(namespace+".deleteMsgReceive", msgRNo);
	}
	
	public List<MsgVO> selectMsgAllNav(int userId){
		return sqlSession.selectList(namespace+".selectMsgAllNav", userId);
	}
	
}
