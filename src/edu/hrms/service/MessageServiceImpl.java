package edu.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hrms.dao.MessageDAO;
import edu.hrms.vo.MsgReceiveVO;
import edu.hrms.vo.MsgVO;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageDAO messageDAO;
	
	@Override
	public List<MsgReceiveVO> selectReceiverAll() {
		return messageDAO.selectReceiverAll();
	}

	@Override
	public List<Map<String, String>> selectDeptCount() {
		return messageDAO.selectDeptCount();
	}

	@Override
	public int insertMsg(Map<String, Object> map) {
		return messageDAO.insertMsg(map);
	}

	@Override
	public int insertMsgReceive(Map<String, Object> map) {
		return messageDAO.insertMsgReceive(map);
	}

	@Override
	public List<MsgVO> selectMsgAll(Map<String,Object> map) {
		return messageDAO.selectMsgAll(map);
	}

	@Override
	public List<MsgVO> selectMsgReceiveAll(Map<String,Object> map) {
		return messageDAO.selectMsgReceiveAll(map);
	}

	@Override
	public int updateMsgRead(int msgRNo) {
		return messageDAO.updateMsgRead(msgRNo);
	}

	@Override
	public int deleteMsgReceive(int msgRNo) {
		return messageDAO.deleteMsgReceive(msgRNo);
	}

	@Override
	public List<MsgVO> selectMsgAllNav(int userId) {
		return messageDAO.selectMsgAllNav(userId);
	}

}
