package edu.hrms.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.hrms.service.AlarmService;
import edu.hrms.service.MessageService;
import edu.hrms.vo.AlarmVO;
import edu.hrms.vo.MsgVO;
import edu.hrms.vo.UserVO;

@Component
public class HomeInterceptor implements HandlerInterceptor {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	AlarmService alarmService;
	/*
	 * 1. dispatcher servlet과 controller 사이에서 동작한다.
	 * 2. 때문에 dispatcher servlet의 bean에 모두 접근이 가능하다.
	 * 3. 인증/인가 등에 관련된 공통 작업 위치
	 * 4. controller로 넘겨주는 정보의 가공 처리 위치
	 * 5. api 호출에 대한 로그 또는 감사 처리 위치
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1.지정된 컨트롤러 동작 이전에 호출되어 동작되는 메소드 (인가/인증 처리)
		//2.handle 객체는 지정된 컨트롤러 파악 또는 지정 메소드 실행 가능
		//3.리턴 타입 boolean은 true이면 이후 작업 실행 false이면 이후 작업 중단
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//1. 지정된 컨트롤러 동작 이후에 처리하며, dispatcherservlet이 화면을 처리하기 전에 동작
		//2. modelAndView를 사용하면 컨트롤러에서 담은 model객체의 데이터를 제어할 수있다.
		String command = request.getRequestURI().substring(request.getContextPath().length()+1);
		String[] uris = command.split("/");

		if(modelAndView!=null) {
			String navSelected = "";
			if(uris[0].equals("")) {
				navSelected = "home";
			}else if(uris[0].equals("work")) {
				navSelected = "work";
			}else if(uris[0].equals("vaca")) {
				navSelected = "vaca";
			}else if(uris[0].equals("docu")) {
				navSelected = "docu";
			}else if(uris[0].equals("sign")) {
				navSelected = "sign";
			}else if(uris[0].equals("work")) {
				navSelected = "work";
			}else if(uris[0].equals("user")) {
				navSelected = "user";
			}else if(uris[0].equals("message")) {
				navSelected = "message";
			}else if(uris[0].equals("notice")) {
				navSelected = "notice";
			}
			modelAndView.addObject("navSelected", navSelected);
			
			if(!uris[0].contains("login")) {
				UserVO user = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				List<MsgVO> msgList = messageService.selectMsgAllNav(Integer.parseInt(user.getUserid()));
				List<AlarmVO> alarmList = alarmService.selectAlarm(Integer.parseInt(user.getUserid()));
				modelAndView.addObject("msgList", msgList);
				modelAndView.addObject("alarmList", alarmList);
				modelAndView.addObject("login", user);
			}
		}
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//1.dispatcher servlet이 화면 처리를 한 이후 에 동작
	}

}
