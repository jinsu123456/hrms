package edu.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.hrms.service.NoticeService;
import edu.hrms.service.SignService;
import edu.hrms.service.VacaService;
import edu.hrms.service.WorkService;
import edu.hrms.util.CalcCalendar;
import edu.hrms.vo.NoticeVO;
import edu.hrms.vo.PagingVO;
import edu.hrms.vo.UserVO;
import edu.hrms.vo.VacaVO;
import edu.hrms.vo.WorkVO;

@Controller
public class HomeController {
	
	@Autowired
	WorkService workService;
	
	@Autowired
	VacaService vacaService;
	
	@Autowired
	CalcCalendar calcCalendar;
	
	@Autowired
	SignService signService;
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping(value = "/error.do", method = RequestMethod.GET)
	public void error() {
		throw new NullPointerException();
	}
	
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		if(authentication!=null) {
			response.sendRedirect(request.getContextPath());
			
		}else {
			Cookie[] cookies = request.getCookies();
			String rememberedId = null;
			if(cookies!=null) {
				for(Cookie c : cookies) {
					String name = c.getName();
					if (name.equals("rememberedId")) {
						rememberedId = c.getValue(); break;
					}
				}
			}
			model.addAttribute("rememberedId", rememberedId);
		}
		return "login";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		
		// 근무 //////////////////////////////////////////////
		Map<String, String> map = new HashMap<>();
		String userid = login.getUserid();
		String today = calcCalendar.getTodayDate();
		map.put("userid", userid);
		map.put("today", today);
		
		WorkVO vo = workService.selectMyWork(map);
		if(vo!=null) {
			model.addAttribute("start", vo.getStart());
			model.addAttribute("end", vo.getEnd());
		}
		Map<String, String> workTimeMap = workService.getWorkTimeMap(userid);
		String myThisWeekTotalWorkTime
			= workService.selectMyThisWeekTotalWorkTime(workService.selectMyThisWeekWorkTime(workTimeMap)
														, workService.selectMyThisWeekOvertimeTime(workTimeMap));
		model.addAttribute("today", today);
		model.addAttribute("myThisWeekTotalWorkTime", myThisWeekTotalWorkTime);
		
		/////////////////// 연차 ///////////////////
		// 로그인 한 회원
		Map<String, Integer> user = vacaService.myRemainVaca(userid);
		model.addAttribute("user", user);
		
		// 달력에 표시할 연차자 리스트
		Map<String, Object> listMap = Map.of("array", new int[] {2,7});
		List<VacaVO> list = vacaService.selectAllVacaList(listMap);
		
		// 오늘 연차자 리스트
		List<VacaVO> todayList = new ArrayList<>();
		for(VacaVO data : list) {
			boolean b = calcCalendar.isParam1BetweenParam2AndParam3_date
						(today, data.getStartDate(), data.getEndDate());
			if(b) {
				todayList.add(data);
			}
		}
		
		//결재 대기 중인 문서 개수
		int userId = Integer.parseInt(userid);
		int docSignCount = signService.selectDocSignCount(userId);
		int vacaSignCount = signService.selectVacaSignCount(userId);
		int overSignCount = signService.selectOverSignCount(userId);
		
		//공지사항
		int count = noticeService.getNoticeCount();
		PagingVO pagingVO = new PagingVO(1, count, 5);
		Map<String, Object> noticeMap = new HashMap<>();
		noticeMap.put("pagingVO", pagingVO);
		List<NoticeVO> noticeList = noticeService.selectNotice(noticeMap);
		
		model.addAttribute("list", list);
		model.addAttribute("todayList", todayList);
		model.addAttribute("docSignCount", docSignCount);
		model.addAttribute("vacaSignCount", vacaSignCount);
		model.addAttribute("overSignCount", overSignCount);
		model.addAttribute("noticeList", noticeList);
		
		return "home";
	}
	
	@RequestMapping(value = "/logoutOk.do", method = RequestMethod.GET)
	public void logout(HttpServletResponse response, Model model) throws IOException {
		
		response.getWriter().append("<script>alert('로그아웃 되었습니다.');location.href='login.do'</script>");
		response.getWriter().flush();
	}
	
}
