package edu.hrms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.hrms.service.UserService;
import edu.hrms.util.RegEx;
import edu.hrms.vo.EmployeeVO;
import edu.hrms.vo.UserVO;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder encoder;
	
	@RequestMapping(value = "/main.do" , method = RequestMethod.GET)
	public String main(Authentication authentication, Model model, String selected, String dept, String position, String searchName) {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		if(dept != null && (dept.equals("all") || dept.equals(""))) {
			dept = null;
		}
		if(position != null && (position.equals("all") || position.equals(""))) {
			position = null;
		}
		if(searchName != null && searchName.equals("")) {
			searchName = null;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dept", dept);
		map.put("position", position);
		map.put("searchName", searchName);
		List<EmployeeVO> list = userService.selectUserAll(map);
		
		if(selected != null) {
			model.addAttribute("selected", selected);
		}
		model.addAttribute("list", list);
		model.addAttribute("loginUser", userVO);
		model.addAttribute("dept", dept);
		model.addAttribute("position", position);
		model.addAttribute("searchName", searchName);
		return "/user/main";
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(Authentication authentication, Model model) {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		model.addAttribute("loginUser", userVO);
		return "/user/modify";
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public void modify(Authentication authentication, EmployeeVO employee, 
			String newPassword, HttpServletResponse res) throws IOException {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		employee.setUserid(userVO.getUserid());
        String pw = userVO.getPassword();
	        if(encoder.matches(employee.getPassword(), pw)) {
	        	System.out.println("���� ��й�ȣ ���� �Ϸ�");
	        	if(encoder.matches(newPassword, pw)) {
	        		System.out.println("�� ��й�ȣ ���� �Ϸ�");
	        		res.getWriter().append("<script>alert('������ ����ߴ� ��й�ȣ�� �����ϴ�. \\n���ο� ��й�ȣ�� �Է��� �ּ���.');location.href='modify.do'</script>");
	        	}else {
		        	RegEx regEx = new RegEx();
		    		String pwChk = regEx.checkRegEx(RegEx.MPW_REGEX, newPassword);
		    		String phoneChk = regEx.checkRegEx(RegEx.MPHONE_REGEX, employee.getPhone());
		    		String emailChk = regEx.checkRegEx(RegEx.MEMAIL_REGEX, employee.getEmail());
		    		if(pwChk!=null && phoneChk!=null && emailChk!=null) {
		    			String encodedPassword = encoder.encode(pwChk);
		    			employee.setPassword(encodedPassword);
		    			String phone = employee.getPhone().replaceAll("-", "");
		    			employee.setPhone(phone);
		    			int result = userService.updateUser(employee);
		    			
		    			if(result>0) {
			        		userVO.setPassword(encodedPassword);
			    			res.getWriter().append("<script>alert('�� ���� ������ �Ϸ� �Ǿ����ϴ�.');location.href='main.do'</script>");
			    		}else {
			    			res.getWriter().append("<script>alert('�� ���� ������ �Ϸ� �����ʾҽ��ϴ�. \\n�����ڿ��� �����ϼ���.');location.href='main.do'</script>");
			    		}
		    		}else {
		    			res.getWriter().append("<script>alert('�� ���� ������ �Ϸ� �����ʾҽ��ϴ�. \\n�Է������� Ȯ���� �ּ���.');location.href='main.do'</script>");
		    		}
	        	}
	        }else {
	        	System.out.println("���� ��й�ȣ ���� ����");
	        	res.getWriter().append("<script>alert('���� ��й�ȣ�� �ٽ� Ȯ���� �ּ���.');location.href='modify.do'</script>");
	        }
	        res.getWriter().flush();
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/regist.do", method = RequestMethod.GET)
	public String regist() {
		return "/user/regist";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public void regist(EmployeeVO employeeVO, HttpServletResponse res) throws IOException {
		int state = 0;
		state = employeeVO.getState();
		if(employeeVO.getPassword()==null || employeeVO.getPassword().equals("") || employeeVO.getName()==null 
				|| employeeVO.getName().equals("")  || employeeVO.getDept()==null || employeeVO.getDept().equals("") 
				|| employeeVO.getJoinDate()==null || employeeVO.getJoinDate().equals("") 
				|| employeeVO.getPosition()==null || employeeVO.getPosition().equals("")  || state == 0) {
			res.getWriter().append("<script>alert('�ʼ� �Է��׸��� ��� �Է��� �ּ���.');location.href='main.do'</script>");
			res.getWriter().flush();
		}else {
			System.out.println(employeeVO.toString());
			int userid = 0;
			String dept = employeeVO.getDept();
			if(dept.equals("P") || dept.equals("H") || dept.equals("D") || dept.equals("S") || dept.equals("M")) {
				int result = userService.selectMaxUserid(dept);
				userid = result + 1;
				employeeVO.setUserid(Integer.toString(userid));
				String encodedPassword = encoder.encode(employeeVO.getPassword());
				employeeVO.setPassword(encodedPassword);
				String phone = employeeVO.getPhone().replaceAll("-", "");
				employeeVO.setPhone(phone);
				int result2 = userService.insertUser(employeeVO);
				if(result2>0) {
					res.getWriter().append("<script>alert('����� ��� �Ǿ����ϴ�. \\n�����ȣ�� "+employeeVO.getUserid()+" �Դϴ�.');location.href='main.do'</script>");
				}
			}else {
				res.getWriter().append("<script>alert('����� ��� ���� �ʾҽ��ϴ�.');location.href='main.do'</script>");
			}
			res.getWriter().flush();
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/modifyAdmin.do", method = RequestMethod.POST)
	public String modifyAdmin(Model model, int userid) {
		EmployeeVO employeeVO = userService.selectUser(userid);
		model.addAttribute("user", employeeVO);
		return "/user/modifyAdmin";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/modifyAdminOk.do", method = RequestMethod.POST)
	public void modifyAdminOk(EmployeeVO employeeVO, HttpServletResponse res) throws IOException {
		String phone = employeeVO.getPhone().replaceAll("-", "");
		employeeVO.setPhone(phone);
		String encodedPassword = encoder.encode(employeeVO.getPassword());
		employeeVO.setPassword(encodedPassword);
		
		int result = userService.updateFromAdmin(employeeVO);
		
		if(result>0) {
			res.getWriter().append("<script>alert('��� ������ ���� �Ǿ����ϴ�.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('��� ���� ������ �����߽��ϴ�.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
}
