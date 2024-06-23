package edu.hrms.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.hrms.vo.UserVO;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		UserVO login = (UserVO) authentication.getPrincipal();
		request.getSession().setAttribute("login", login);
		
		// 사원번호 기억하기 쿠키
		String checkRemember =  (String) request.getParameter("checkRemember");
		if(checkRemember!=null) {
			Cookie[] cookies = request.getCookies();
			String rememberedId = null;
			for(Cookie c : cookies) {
				String name = c.getName();
	            if (name.equals("rememberedId")) {
	            	rememberedId = c.getValue(); break;
	            }
			}
			
			boolean flag = false;
			// 쿠키 새로 만들어야 하는 경우
			// 1. 쿠키가 없는 경우
			if(rememberedId==null) flag = true;
			
			// 2. 쿠키가 있지만 값이 기존 아이디와 같지 않은 경우
			if(rememberedId!=null && !rememberedId.equals(login.getUserid())) flag = true;
			
			if(flag) {
				Cookie cookie = new Cookie("rememberedId", login.getUserid()); 
				cookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(cookie);
			}
			
		// 체크 해제한 경우 쿠키 삭제
		}else {
			Cookie cookie = new Cookie("rememberedId", null);
		    cookie.setMaxAge(0);
		    response.addCookie(cookie);
		}
		
		
		String name = login.getName();
		String dept = login.getDept();
		switch(dept) {
			case "M" : dept = ""; break;
			case "D" : dept = "개발부"; break;
			case "S" : dept = "영업부"; break;
			case "P" : dept = "기획부"; break;
			case "H" : dept = "인사부"; break;
		}
		String position = login.getPosition();
		switch(position) {
			case "C" : position = "대표"; break;
			case "D" : position = "부장"; break;
			case "L" : position = "팀장"; break;
			case "E" : position = "사원"; break;
			case "A" : position = "관리자"; break;
		}
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append("<script>alert('"+dept+" "+name+" "+position+"님 환영합니다.');location.href='" + request.getContextPath() + "'</script>");
		response.getWriter().flush();

	}

}
