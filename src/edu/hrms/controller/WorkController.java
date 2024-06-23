package edu.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.hrms.service.WorkService;
import edu.hrms.util.CalcCalendar;
import edu.hrms.vo.OvertimeSignVO;
import edu.hrms.vo.OvertimeVO;
import edu.hrms.vo.PagingVO;
import edu.hrms.vo.SearchVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.UserVO;
import edu.hrms.vo.WorkVO;

@Controller
@RequestMapping(value = "/work")
public class WorkController {

	@Autowired
	WorkService workService;
	
	@Autowired
	CalcCalendar calcCalendar;
	
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main(Model model, Authentication authentication, HttpServletResponse response) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		
		// 로그인 한 계정이 관리자일 경우 관리자 근무 페이지로 이동
		if(login.getAuthority().equals("ROLE_ADMIN")) {
			try {
				response.sendRedirect("main_admin.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			String userid = login.getUserid();
			String today = calcCalendar.getTodayDate();
			model.addAttribute("today", today);
			
			Map<String, String> map = new HashMap<>();
			map.put("userid", userid);
			map.put("today", today);
			
			WorkVO vo = workService.selectMyWork(map); // 오늘 출퇴근
			if(vo!=null) {
				model.addAttribute("start", vo.getStart());
				model.addAttribute("end", vo.getEnd());
			}
			
			// 로그인 한 사원 금주 근무 시간
			Map<String, String> workTimeMap = workService.getWorkTimeMap(userid);
			String myThisWeekOvertimeTime = workService.selectMyThisWeekOvertimeTime(workTimeMap);
			String myThisWeekTotalWorkTime = workService.selectMyThisWeekTotalWorkTime(workService.selectMyThisWeekWorkTime(workTimeMap), myThisWeekOvertimeTime);
			
			model.addAttribute("myThisWeekOvertimeTime", myThisWeekOvertimeTime);
			model.addAttribute("myThisWeekTotalWorkTime", myThisWeekTotalWorkTime);
			
			Map<String, Object> listMap = new HashMap<>();
			listMap.put("userid", userid);
			listMap.put("startDate", null);
			listMap.put("endDate", today);
			
			// 로그인 한 사원 근무 리스트
			List<WorkVO> workList = workService.selectAllMyWork(listMap);
			model.addAttribute("workList", workList);
			List<OvertimeVO> overtimeList = workService.selectAllMyOvertime(listMap);
			model.addAttribute("overtimeList", overtimeList);
			
			// 로그인 한 사원 금일 초과근무 신청 여부 : [0]=점심 초과근무, [1]=저녁 초과근무
			OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
			if(ovoAppArr[0]!=null && ovoAppArr[1]!=null) {
				model.addAttribute("ovoAppArr", ovoAppArr);
			}
			
			// 팀장 이상 사원만 조회 가능한 부서 근무리스트
			if(!login.getAuthority().equals("ROLE_EMPLOYEE")) {
				Map<String, Object> allWorkListMap = new HashMap<>();
				String[] deptArr = workService.getDeptArr(login.getDept());
				allWorkListMap.put("deptArr", deptArr);
				allWorkListMap.put("startDate", null);
				allWorkListMap.put("endDate", today);
				int cnt = workService.getCountOfAllWorkList(allWorkListMap);
				
				PagingVO pagingVO = new PagingVO(1, cnt, 10);
				allWorkListMap.put("pagingVO",pagingVO);
				
				List<WorkVO> allWorkList = workService.selectAllWork(allWorkListMap);
				model.addAttribute("pagingVO", pagingVO);
				model.addAttribute("allWorkList", allWorkList);
			}
		}
		return "/work/main";
	}
	
	
	@RequestMapping(value = "/workInsert.do", method = RequestMethod.POST)
	@ResponseBody
	public Object workInsert(String date, String time, String goOrLeave, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		
		Map<String, String> map = new HashMap<>();
		map.put("userid", login.getUserid());
		map.put("date", date);
		map.put("time", time);
		
		// 출근인 경우
		if(goOrLeave.equals("GO")) {
			workService.insert(map);
			
		// 퇴근인 경우
		}else if(goOrLeave.equals("LEAVE")) {
			map.put("today", calcCalendar.getTodayDate());
			
			// 오늘 초과근무 신청 여부 : [0]=점심 초과근무, [1]=저녁 초과근무
			OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
			System.out.println(Arrays.toString(ovoAppArr));
			
			// 초과근무 신청이 없으면 퇴근처리한다.
			if(ovoAppArr[0]==null && ovoAppArr[1]==null) {
				workService.update(map);
				
			// 초과근무 신청이 있을 경우 퇴근시간과 초과근무 시간을 대조한다.
			}else {
				OvertimeVO ovoAfternoon = ovoAppArr[0];
				OvertimeVO ovoEvening = ovoAppArr[1];
				
				// 퇴근시간이 초과근무 신청 시간보다 전인지 체크
				String lastEndTime = null;
				if(ovoEvening!=null) {
					lastEndTime = ovoEvening.getDate() + " " + ovoEvening.getEnd();
				}else {
					lastEndTime = ovoAfternoon.getDate() + " " + ovoAfternoon.getEnd();
				}
				String leaveTime = date + " " + time;
				boolean isLeaveBeforeEndtime = calcCalendar.isParam1_beforeOrAfter_param2(leaveTime, lastEndTime, "before");
				
				// 초과근무시간 끝시간보다 먼저 퇴근하는경우
				if(isLeaveBeforeEndtime) return ovoAppArr;
			}
		}
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/updateOvertime.do", method = RequestMethod.POST)
	@ResponseBody
	public void updateOvertime(String date, String time, Authentication authentication) {
		UserVO login = (UserVO)authentication.getPrincipal();
		
		Map<String, String> map = new HashMap<>();
		map.put("userid", login.getUserid());
		map.put("time", time);
		map.put("today", date);
		
		// 퇴근시간 업데이트
		workService.update(map);
		
		OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
		String now = date + " " + time;
		
		/*
		 *  1. 점심 초과근무 신청: 시작 전 퇴근 : 철회
		 *  2. 점심 초과근무 신청: 진행 중 퇴근 : 끝나는 시간 업데이트
		 *  3. 점심 초과근무 신청: 13시 지나고 퇴근: 처리 x
		 *  4. 저녁 초과근무 신청: 시작 전 퇴근: 철회
		 *  5. 저녁 초과근무 신청: 진행 중 퇴근: 끝나는 시간 업데이트
		 *  6. 저녁 초과근무 신청: 끝나고 퇴근: 처리 x
		 *  
		 *  7. 점심, 저녁 초과근무 신청: 점심 시작, 저녁 시작 전 퇴근 : 둘 다 철회
		 *  8. 점심, 저녁 초과근무 신청: 점심 도중, 저녁 시작 전 퇴근 : 점심 업데이트, 저녁 철회
		 *  9. 점심, 저녁 초과근무 신청: 점심 끝, 저녁 시작 전 퇴근 : 저녁 철회
		 *  10. 점심, 저녁 초과근무 신청: 점심 끝, 저녁 도중 퇴근 : 저녁 업데이트
		 *  11. 점심, 저녁 초과근무 신청: 점심 끝, 저녁 끝 퇴근 : 처리 x
		 */
		
		for(int i=0; i<ovoAppArr.length; i++) {
			if(ovoAppArr[i]!=null) {
				String startTime = date + " " + ovoAppArr[i].getStart();
				String endTime = date + " " + ovoAppArr[i].getEnd();
				boolean isNowBeforeStartTime= calcCalendar.isParam1_beforeOrAfter_param2(now, startTime, "before");
				boolean isNowAfterEndTime= calcCalendar.isParam1_beforeOrAfter_param2(now, endTime, "after");
				// 시작시간 전 퇴근 : 철회
				if(isNowBeforeStartTime) {
					workService.withdrawal(ovoAppArr[i].getOvertimeNo());
					workService.overtimesignDelete(ovoAppArr[i].getOvertimeNo());
					
				// 시작시간 후 퇴근
				}else {
					// 끝시간 후 퇴근이 아니면 초과근무 끝시간 업데이트
					if(!isNowAfterEndTime) {
						workService.updateOvertime(Map.of("overtimeNo", ovoAppArr[i].getOvertimeNo(), "end", time));
					}
				}
			}
		}
	}
	
	@RequestMapping(value = "/overtime_application.do", method = RequestMethod.GET)
	public String overtimeApplication(Model model, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		List<SignLineVO> signLineList = workService.getSignLineList(login.getUserid(), login.getPosition(), "O");
		model.addAttribute("signLineList", signLineList);
		
		return "/work/overtime_application";
	}
	
	@RequestMapping(value = "/overtime_application.do", method = RequestMethod.POST)
	public void overtimeApplication(HttpServletResponse response, String date, String start, String end, String content, Authentication authentication) throws IOException {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		String userid = login.getUserid();
		
		Map<String, String> map = Map.of(
				"userid", userid, "today", date, "start", start,
				"end", end, "content", content);
		
		// 오늘 초과근무 신청 여부 : [0]=점심 초과근무, [1]=저녁 초과근무
		OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
		boolean flag = true;
		if(ovoAppArr!=null) {
			if(start.equals("12:00")) {
				if(ovoAppArr[0]!=null) {
					response.getWriter().append("<script>alert('오늘 이미 결재 대기중인 점심 초과근무가 있습니다.');location.href='main.do';</script>");
					flag = false;
				}
			}else {
				if(ovoAppArr[1]!=null) {
					response.getWriter().append("<script>alert('오늘 이미 결재 대기중인 저녁 초과근무가 있습니다.');location.href='main.do';</script>");
					flag = false;
				}
			}
		}
		if(flag) {
			workService.insertOvertime(map);
			List<SignLineVO> signLineList = workService.getSignLineList(userid, login.getPosition(), "O");
			List<OvertimeSignVO> overtimeSignList = workService.getOvertimeSignList(signLineList, workService.getMaxNoByUserId(userid));
			workService.insertOvertimeSign(overtimeSignList);
			response.getWriter().append("<script>alert('초과근무 신청이 완료되었습니다.');location.href='main.do';</script>");
		}
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/overtime_view.do")
	public String overtimeView(Model model, @RequestParam("no")int overtimeNo) {
		
		OvertimeVO ovo = workService.selectOvertime(overtimeNo);
		model.addAttribute("ovo", ovo);
		
		// 1. db에서 sign 리스트 얻어온다
		List<OvertimeSignVO> osList = workService.getOvertimeSignList(overtimeNo);
		
		// 2. 1에서 얻은 리스트 가공한다
		osList = (List<OvertimeSignVO>) workService.processList(osList);
		model.addAttribute("osList", osList);
		
		// 3. 2에서 얻은 리스트로 결재 현황 카운트, 진행상황  구한다
		Map<String, Object> map = workService.getCountNowstate(osList, ovo.getState());
		
		model.addAttribute("count", map.get("count"));
		model.addAttribute("nowState", map.get("nowState"));
		
		return "/work/overtime_view";
	}
	
	@RequestMapping(value = "/withdrawal.do", method = RequestMethod.POST)
	public void withdrawal(HttpServletResponse response, int overtimeNo) throws IOException {
		
		workService.withdrawal(overtimeNo);
		workService.overtimesignDelete(overtimeNo);
		response.getWriter().append("<script>alert('초과근무 신청이 철회되었습니다.');location.href='main.do';</script>");
		response.getWriter().flush();
	}
	
	
	@RequestMapping(value = "/reloadList.do")
	@ResponseBody
	public Object reloadList(String obj, SearchVO searchVO, String pNum, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		
		Map<String, Object> listMap = new HashMap<>();
		listMap.put("userid", login.getUserid());
		listMap.put("startDate", searchVO.getStartDate());
		listMap.put("endDate", searchVO.getEndDate());
		
		List<?> list = new ArrayList<>();
		if(obj.equals("1")) {
			list = workService.selectAllMyWork(listMap);
			
		}else if(obj.equals("2")) {
			list = workService.selectAllMyOvertime(listMap);
			
		}else if(obj.equals("3")) {
			listMap.put("deptArr", workService.getDeptArr(login.getDept()));
			listMap.put("category_dept", searchVO.getCategory_dept());
			listMap.put("category_position", searchVO.getCategory_position());
			listMap.put("searchVal", searchVO.getSearchVal());
			
			int cnt = workService.getCountOfAllWorkList(listMap);
			
			PagingVO pagingVO = new PagingVO(Integer.parseInt(pNum), cnt, 10);
			listMap.put("pagingVO", pagingVO);
			
			list = workService.selectAllWork(listMap);
			Map<String, Object> map = new HashMap<>();
		
			map.put("list", list);
			map.put("pagingVO", pagingVO);
			return map;
		}
		
		return list;
	}

	
	/////////////////////////// 관리자 ///////////////////////////
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/main_admin.do", method = RequestMethod.GET)
	public String main_admin(Model model, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		model.addAttribute("today", calcCalendar.getTodayDate());
		
		Map<String, Object> allWorkListMap = new HashMap<>();
		String[] deptArr = workService.getDeptArr(login.getDept());
		allWorkListMap.put("deptArr", deptArr);
		allWorkListMap.put("startDate", null);
		allWorkListMap.put("endDate", calcCalendar.getTodayDate());
		
		int cnt = workService.getCountOfAllWorkList(allWorkListMap);
		PagingVO pagingVO = new PagingVO(1, cnt, 10);
		allWorkListMap.put("pagingVO",pagingVO);
		
		List<WorkVO> allWorkList = workService.selectAllWork(allWorkListMap);
		
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("allWorkList", allWorkList);
		
		return "/work/main_admin";
	}
	
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/workInsert_admin.do", method = RequestMethod.POST)
	public int workInsert(String wNo, String end) {
		Map<String, String> map = Map.of("wNo", wNo, "end", end);
		return workService.updateWork_admin(map);
	}
	
	
}
