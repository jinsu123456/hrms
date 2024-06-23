package edu.hrms.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.authentication.AccountExpiredException;

public class UserLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		String msg = "";
		
		if(exception instanceof LockedException) {
			msg = "�������� ����� �α����� �� �����ϴ�.";
		}else if(exception instanceof AccountExpiredException) {
			msg = "����� ����Դϴ�.";
		}else {
			msg = "�����ȣ�� ��й�ȣ�� Ȯ�����ּ���.";
		}
		
		response.getWriter().append("<script>alert('"+msg+"'); location.href='login.do';</script>");
		response.getWriter().flush();
	}

}
