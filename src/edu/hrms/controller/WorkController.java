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
		
		// �α��� �� ������ �������� ��� ������ �ٹ� �������� �̵�
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
			
			WorkVO vo = workService.selectMyWork(map); // ���� �����
			if(vo!=null) {
				model.addAttribute("start", vo.getStart());
				model.addAttribute("end", vo.getEnd());
			}
			
			// �α��� �� ��� ���� �ٹ� �ð�
			Map<String, String> workTimeMap = workService.getWorkTimeMap(userid);
			String myThisWeekOvertimeTime = workService.selectMyThisWeekOvertimeTime(workTimeMap);
			String myThisWeekTotalWorkTime = workService.selectMyThisWeekTotalWorkTime(workService.selectMyThisWeekWorkTime(workTimeMap), myThisWeekOvertimeTime);
			
			model.addAttribute("myThisWeekOvertimeTime", myThisWeekOvertimeTime);
			model.addAttribute("myThisWeekTotalWorkTime", myThisWeekTotalWorkTime);
			
			Map<String, Object> listMap = new HashMap<>();
			listMap.put("userid", userid);
			listMap.put("startDate", null);
			listMap.put("endDate", today);
			
			// �α��� �� ��� �ٹ� ����Ʈ
			List<WorkVO> workList = workService.selectAllMyWork(listMap);
			model.addAttribute("workList", workList);
			List<OvertimeVO> overtimeList = workService.selectAllMyOvertime(listMap);
			model.addAttribute("overtimeList", overtimeList);
			
			// �α��� �� ��� ���� �ʰ��ٹ� ��û ���� : [0]=���� �ʰ��ٹ�, [1]=���� �ʰ��ٹ�
			OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
			if(ovoAppArr[0]!=null && ovoAppArr[1]!=null) {
				model.addAttribute("ovoAppArr", ovoAppArr);
			}
			
			// ���� �̻� ����� ��ȸ ������ �μ� �ٹ�����Ʈ
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
		
		// ����� ���
		if(goOrLeave.equals("GO")) {
			workService.insert(map);
			
		// ����� ���
		}else if(goOrLeave.equals("LEAVE")) {
			map.put("today", calcCalendar.getTodayDate());
			
			// ���� �ʰ��ٹ� ��û ���� : [0]=���� �ʰ��ٹ�, [1]=���� �ʰ��ٹ�
			OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
			System.out.println(Arrays.toString(ovoAppArr));
			
			// �ʰ��ٹ� ��û�� ������ ���ó���Ѵ�.
			if(ovoAppArr[0]==null && ovoAppArr[1]==null) {
				workService.update(map);
				
			// �ʰ��ٹ� ��û�� ���� ��� ��ٽð��� �ʰ��ٹ� �ð��� �����Ѵ�.
			}else {
				OvertimeVO ovoAfternoon = ovoAppArr[0];
				OvertimeVO ovoEvening = ovoAppArr[1];
				
				// ��ٽð��� �ʰ��ٹ� ��û �ð����� ������ üũ
				String lastEndTime = null;
				if(ovoEvening!=null) {
					lastEndTime = ovoEvening.getDate() + " " + ovoEvening.getEnd();
				}else {
					lastEndTime = ovoAfternoon.getDate() + " " + ovoAfternoon.getEnd();
				}
				String leaveTime = date + " " + time;
				boolean isLeaveBeforeEndtime = calcCalendar.isParam1_beforeOrAfter_param2(leaveTime, lastEndTime, "before");
				
				// �ʰ��ٹ��ð� ���ð����� ���� ����ϴ°��
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
		
		// ��ٽð� ������Ʈ
		workService.update(map);
		
		OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
		String now = date + " " + time;
		
		/*
		 *  1. ���� �ʰ��ٹ� ��û: ���� �� ��� : öȸ
		 *  2. ���� �ʰ��ٹ� ��û: ���� �� ��� : ������ �ð� ������Ʈ
		 *  3. ���� �ʰ��ٹ� ��û: 13�� ������ ���: ó�� x
		 *  4. ���� �ʰ��ٹ� ��û: ���� �� ���: öȸ
		 *  5. ���� �ʰ��ٹ� ��û: ���� �� ���: ������ �ð� ������Ʈ
		 *  6. ���� �ʰ��ٹ� ��û: ������ ���: ó�� x
		 *  
		 *  7. ����, ���� �ʰ��ٹ� ��û: ���� ����, ���� ���� �� ��� : �� �� öȸ
		 *  8. ����, ���� �ʰ��ٹ� ��û: ���� ����, ���� ���� �� ��� : ���� ������Ʈ, ���� öȸ
		 *  9. ����, ���� �ʰ��ٹ� ��û: ���� ��, ���� ���� �� ��� : ���� öȸ
		 *  10. ����, ���� �ʰ��ٹ� ��û: ���� ��, ���� ���� ��� : ���� ������Ʈ
		 *  11. ����, ���� �ʰ��ٹ� ��û: ���� ��, ���� �� ��� : ó�� x
		 */
		
		for(int i=0; i<ovoAppArr.length; i++) {
			if(ovoAppArr[i]!=null) {
				String startTime = date + " " + ovoAppArr[i].getStart();
				String endTime = date + " " + ovoAppArr[i].getEnd();
				boolean isNowBeforeStartTime= calcCalendar.isParam1_beforeOrAfter_param2(now, startTime, "before");
				boolean isNowAfterEndTime= calcCalendar.isParam1_beforeOrAfter_param2(now, endTime, "after");
				// ���۽ð� �� ��� : öȸ
				if(isNowBeforeStartTime) {
					workService.withdrawal(ovoAppArr[i].getOvertimeNo());
					workService.overtimesignDelete(ovoAppArr[i].getOvertimeNo());
					
				// ���۽ð� �� ���
				}else {
					// ���ð� �� ����� �ƴϸ� �ʰ��ٹ� ���ð� ������Ʈ
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
		
		// ���� �ʰ��ٹ� ��û ���� : [0]=���� �ʰ��ٹ�, [1]=���� �ʰ��ٹ�
		OvertimeVO[] ovoAppArr = workService.overtimeApplicationToday(map);
		boolean flag = true;
		if(ovoAppArr!=null) {
			if(start.equals("12:00")) {
				if(ovoAppArr[0]!=null) {
					response.getWriter().append("<script>alert('���� �̹� ���� ������� ���� �ʰ��ٹ��� �ֽ��ϴ�.');location.href='main.do';</script>");
					flag = false;
				}
			}else {
				if(ovoAppArr[1]!=null) {
					response.getWriter().append("<script>alert('���� �̹� ���� ������� ���� �ʰ��ٹ��� �ֽ��ϴ�.');location.href='main.do';</script>");
					flag = false;
				}
			}
		}
		if(flag) {
			workService.insertOvertime(map);
			List<SignLineVO> signLineList = workService.getSignLineList(userid, login.getPosition(), "O");
			List<OvertimeSignVO> overtimeSignList = workService.getOvertimeSignList(signLineList, workService.getMaxNoByUserId(userid));
			workService.insertOvertimeSign(overtimeSignList);
			response.getWriter().append("<script>alert('�ʰ��ٹ� ��û�� �Ϸ�Ǿ����ϴ�.');location.href='main.do';</script>");
		}
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/overtime_view.do")
	public String overtimeView(Model model, @RequestParam("no")int overtimeNo) {
		
		OvertimeVO ovo = workService.selectOvertime(overtimeNo);
		model.addAttribute("ovo", ovo);
		
		// 1. db���� sign ����Ʈ ���´�
		List<OvertimeSignVO> osList = workService.getOvertimeSignList(overtimeNo);
		
		// 2. 1���� ���� ����Ʈ �����Ѵ�
		osList = (List<OvertimeSignVO>) workService.processList(osList);
		model.addAttribute("osList", osList);
		
		// 3. 2���� ���� ����Ʈ�� ���� ��Ȳ ī��Ʈ, �����Ȳ  ���Ѵ�
		Map<String, Object> map = workService.getCountNowstate(osList, ovo.getState());
		
		model.addAttribute("count", map.get("count"));
		model.addAttribute("nowState", map.get("nowState"));
		
		return "/work/overtime_view";
	}
	
	@RequestMapping(value = "/withdrawal.do", method = RequestMethod.POST)
	public void withdrawal(HttpServletResponse response, int overtimeNo) throws IOException {
		
		workService.withdrawal(overtimeNo);
		workService.overtimesignDelete(overtimeNo);
		response.getWriter().append("<script>alert('�ʰ��ٹ� ��û�� öȸ�Ǿ����ϴ�.');location.href='main.do';</script>");
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

	
	/////////////////////////// ������ ///////////////////////////
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
