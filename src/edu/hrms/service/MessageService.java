package edu.hrms.service;

import java.util.List;
import java.util.Map;

import edu.hrms.vo.MsgReceiveVO;
import edu.hrms.vo.MsgVO;

public interface MessageService {
	List<MsgReceiveVO> selectReceiverAll();
	List<Map<String, String>> selectDeptCount();
	int insertMsg(Map<String,Object> map);
	int insertMsgReceive(Map<String, Object> map);
	List<MsgVO> selectMsgAll(Map<String,Object> map);
	List<MsgVO> selectMsgReceiveAll(Map<String,Object> map);
	int updateMsgRead(int msgRNo);
	int deleteMsgReceive(int msgRNo);
	List<MsgVO> selectMsgAllNav(int userId);
	
}
