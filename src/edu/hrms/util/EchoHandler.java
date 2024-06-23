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
	//로그인 한 인원 전체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {//클라이언트와 서버가 연결
		/* logger.info("Socket 연결"); */
		sessions.add(session);
		/* logger.info(currentUserName(session)); *///현재 접속한 사람
		String senderId = currentUserName(session);
		userSessionsMap.put(senderId,session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {// 메시지
		logger.info("ssesion"+currentUserName(session));
		String msg = message.getPayload();//자바스크립트에서 넘어온 Msg
		logger.info("msg="+msg);
		
		if(!StringUtils.isEmpty(msg)) {
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 3) {
				String type = strs[0];
				String receiveUserid = strs[1];
				String sendUserid = strs[2];

				logger.info("length 성공?"+type);
				System.out.println(receiveUserid+""+sendUserid);
				WebSocketSession receiveSession = userSessionsMap.get(receiveUserid);
				WebSocketSession sendSession = userSessionsMap.get(sendUserid);
				logger.info("receiveSession="+userSessionsMap.get(receiveUserid));
				logger.info("receiveSession"+receiveSession);
				
				if(receiveSession != null && type.equals("approvedDoc")) {
					TextMessage tmpMsg = new TextMessage("새로운 기안 승인 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedDoc")) {
					TextMessage tmpMsg = new TextMessage("새로운 기안 반려 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("approvedVaca")) {
					TextMessage tmpMsg = new TextMessage("새로운 연차 승인 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedVaca")) {
					TextMessage tmpMsg = new TextMessage("새로운 연차 반려 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("approvedOver")) {
					TextMessage tmpMsg = new TextMessage("새로운 초과근무 승인 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("rejectedOver")) {
					TextMessage tmpMsg = new TextMessage("새로운 초과근무 반려 알림 도착");
					receiveSession.sendMessage(tmpMsg);
				}else if(receiveSession != null && type.equals("sendMessage")) {
					TextMessage tmpMsg = new TextMessage("새로운 메시지 도착");
					receiveSession.sendMessage(tmpMsg);
				}
				
			}else if(strs != null && strs.length > 3){
				logger.info("length > 3 성공?"+strs.length);
				String type = strs[0];
				String sendUserid = strs[strs.length-1];
				WebSocketSession sendSession = userSessionsMap.get(sendUserid);
				for(int i=1; i<strs.length-1; i++) {
					String receiveUserid = strs[i];
					WebSocketSession receiveSession = userSessionsMap.get(receiveUserid);
					if(receiveSession != null && type.equals("sendMessage")) {
						TextMessage tmpMsg = new TextMessage("새로운 메시지 도착");
						receiveSession.sendMessage(tmpMsg);
					}
				}
			}
			
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {//연결 해제
		/* logger.info("Socket 끊음"); */
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