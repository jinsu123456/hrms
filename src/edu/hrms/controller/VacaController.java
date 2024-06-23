package edu.hrms.controller;

import java.io.IOException;
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

import edu.hrms.service.VacaService;
import edu.hrms.service.WorkService;
import edu.hrms.util.CalcCalendar;
import edu.hrms.vo.PagingVO;
import edu.hrms.vo.SearchVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.UserVO;
import edu.hrms.vo.VacaSignVO;
import edu.hrms.vo.VacaVO;

@Controller
@RequestMapping(value = "/vaca")
public class VacaController {
	
	@Autowired
	VacaService vacaService;
	 
	@Autowired
	WorkService workService;
	
	@Autowired
	CalcCalendar calcCalendar;
	
	@RequestMapping(value = "/main.do")
	public String main(Model model, Authentication authentication, HttpServletResponse response) throws IOException {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		
		// 로그인 한 계정이 관리자일 경우 관리자 연차 페이지로 이동
		if(login.getAuthority().equals("ROLE_ADMIN")) {
			response.sendRedirect("main_admin.do");
			
		}else {
			String userid = login.getUserid();
			model.addAttribute("myVacaList", vacaService.selectMyVacaList(new VacaVO(userid)));
			model.addAttribute("myRecentVacaApplication", vacaService.myRecentVacaApplication(userid));
			model.addAttribute("user", vacaService.myRemainVaca(userid));
		}
		
		return "/vacation/main";
	}
	
	@RequestMapping(value = "/application.do", method = RequestMethod.GET)
	public String application(Model model, Authentication authentication) {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		List<SignLineVO> signLineList = workService.getSignLineList(login.getUserid(), login.getPosition(), "V");
		model.addAttribute("signLineList", signLineList);
		
		return "/vacation/application";
	}
	
	@RequestMapping(value = "/application.do", method = RequestMethod.POST)
	public void application(HttpServletResponse response, VacaVO vo, Authentication authentication) throws IOException {
		
		UserVO login = (UserVO)authentication.getPrincipal();
		String userid = login.getUserid();
		vo.setUserid(userid);
		
		// 해당 날짜 연차 신청 여부 확인
		int cnt = vacaService.checkVacaAppCnt(vo);
		
		if(cnt>0) {
			response.getWriter().append("<script>alert('해당 날짜에 이미 신청한 결재 대기\\(혹은 진행\\)중인 연차 내역이 있습니다.');location.href='main.do';</script>");
		}else {
			vacaService.insertVaca(vo);
			int vacaNo = vacaService.getMaxNoByUserId(userid);
			List<SignLineVO> signLineList = workService.getSignLineList(userid, login.getPosition(), "V");
			List<VacaSignVO> vacaSignList = vacaService.getVacaSignList(signLineList, vacaNo);
			vacaService.insertVacaSign(vacaSignList);
			response.getWriter().append("<script>alert('연차 신청이 완료되었습니다.');location.href='main.do';</script>");
		}
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/view.do")
	public String view(Model model, @RequestParam("no")int vacaNo) {
		
		VacaVO vo = vacaService.selectVacaByVacaNo(vacaNo);
		model.addAttribute("vo", vo);
		
		// 1. db에서 sign 리스트 얻어온다
		List<VacaSignVO> list = vacaService.getVacaSignList(vacaNo);
		
		// 2. 1에서 얻은 리스트 가공한다
		list = (List<VacaSignVO>) workService.processList(list);
		model.addAttribute("list", list);
		
		// 3. 2에서 얻은 리스트로 결재 현황 카운트, 진행상황  구한다
		Map<String, Object> map = workService.getCountNowstate(list, vo.getState());
		
		model.addAttribute("count", map.get("count"));
		model.addAttribute("nowState", map.get("nowState"));
		
		return "/vacation/view";
	}
	
	@RequestMapping(value = "/withdrawal.do", method = RequestMethod.POST)
	public void withdrawal(HttpServletResponse response, int vacaNo) throws IOException {
		
		vacaService.withdrawal(vacaNo);
		vacaService.vacaSignDelete(vacaNo);
		
		response.getWriter().append("<script>alert('연차 신청이 철회되었습니다.');location.href='main.do';</script>");
		response.getWriter().flush();
	}
	
	
	@RequestMapping(value = "/reloadList.do")
	@ResponseBody
	public List<VacaVO> reloadMyVacaList(VacaVO vo, Authentication authentication){
		
		UserVO login = (UserVO)authentication.getPrincipal();
		vo.setUserid(login.getUserid());
		
		List<VacaVO> list = vacaService.selectMyVacaList(vo);
		return list;
	}
	
	
	/////////////// 관리자 ///////////////
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/main_admin.do", method = RequestMethod.GET)
	public String main_admin(Model model) {
		
		int cnt = vacaService.getCountOfAllUserList(new HashMap<String,Object>());
		PagingVO pagingVO = new PagingVO(1, cnt, 10);
		
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("list", vacaService.selectAllUserList(Map.of("pagingVO", pagingVO)));
		
		return "/vacation/main_admin";
	}

	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/view_admin.do", method = RequestMethod.POST)
	public List<VacaVO> view_admin(String userid){
		
		return vacaService.selectAllVacaList(new HashMap<String, Object>(){{
			put("array", new int[] {2,7}); put("userid", userid);
		}});
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/reloadList_admin.do")
	@ResponseBody
	public Map<String, Object> reloadList_admin(SearchVO searchVO, String pNum){
		
		Map<String, Object> map = new HashMap<>();
		map.put("searchVO", searchVO);
		map.put("array", new int[] {2,7}); // 연차 state
		
		int cnt = vacaService.getCountOfAllUserList(map);
		PagingVO pagingVO = new PagingVO(Integer.parseInt(pNum), cnt, 10);
		map.put("pagingVO", pagingVO);
		
		List<Map<String, Object>> list = vacaService.selectAllUserList(map);
		
		return new HashMap<String, Object>(){{
			put("pagingVO", pagingVO); put("list", list);
		}};
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/giveVaca_admin.do")
	@ResponseBody
	public int giveVaca_admin(String userid, int hour) {
		return vacaService.giveVaca(Map.of("userid", userid, "hour", hour));
	}
	
	
}
