package edu.hrms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import edu.hrms.vo.UserVO;

@EnableWebSocket
@Configuration
public class EchoHandler extends TextWebSocketHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	//�α��� �� �ο� ��ü
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1�� �� ���
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {//Ŭ���̾�Ʈ�� ������ ����
		/* logger.info("Socket ����"); */
		sessions.add(session);
		/* logger.info(currentUserName(session)); *///���� ������ ���
		String senderId = currentUserName(session);
		userSessionsMap.put(senderId,session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {// �޽���
		logger.info("ssesion"+currentUserName(session));
		String msg = message.getPayload();//�ڹٽ�ũ��Ʈ���� �Ѿ�� Msg
		logger.info("msg="+msg);
		
		if(!StringUtils.isEmpty(msg)) {
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 3) {
				String type = strs[0];
				String receiveUserid = strs[1];
				String sendUserid = strs[2];

				logger.info("length ����?"+type);
				System.out.println(receiveUserid+""+sendUserid);
				WebSocketSession receiveSession = userSessionsMap.get(receiveUserid);
				WebSocketSession sendSession = userSessionsMap.get(sendUserid);
				logger.info("receiveSession="+userSessionsMap.get(receiveUserid));
				logger.info("receiveSession"+receiveSession);
				
				if(receiveSession != null && type.equals("approvedDoc")) {
					TextMessage tmpMsg = new TextMessage("���ο� ��� ���� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedDoc")) {
					TextMessage tmpMsg = new TextMessage("���ο� ��� �ݷ� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("approvedVaca")) {
					TextMessage tmpMsg = new TextMessage("���ο� ���� ���� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedVaca")) {
					TextMessage tmpMsg = new TextMessage("���ο� ���� �ݷ� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("approvedOver")) {
					TextMessage tmpMsg = new TextMessage("���ο� �ʰ��ٹ� ���� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedOver")) {
					TextMessage tmpMsg = new TextMessage("���ο� �ʰ��ٹ� �ݷ� �˸� ����");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("sendMessage")) {
					TextMessage tmpMsg = new TextMessage("���ο� �޽��� ����");
					receiveSession.sendMessage(tmpMsg);
				}
				
			}else if(strs != null && strs.length > 3){
				logger.info("length > 3 ����?"+strs.length);
				String type = strs[0];
				String sendUserid = strs[strs.length-1];
				WebSocketSession sendSession = userSessionsMap.get(sendUserid);
				for(int i=1; i<strs.length-1; i++) {
					String receiveUserid = strs[i];
					WebSocketSession receiveSession = userSessionsMap.get(receiveUserid);
					if(receiveSession != null && type.equals("sendMessage")) {
						TextMessage tmpMsg = new TextMessage("���ο� �޽��� ����");
						receiveSession.sendMessage(tmpMsg);
					}
				}
			}
			
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {//���� ����
		/* logger.info("Socket ����"); */
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session),session);
	}

	
	private String currentUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		UserVO loginUser = (UserVO)httpSession.get("login");
		
		if(loginUser == null) {
			String mid = session.getId();
			return mid;
		}
		
		String mid = loginUser.getUserid();
		return mid;
		
	}
	
}