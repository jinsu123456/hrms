package edu.hrms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.hrms.service.AlarmService;
import edu.hrms.service.SignService;
import edu.hrms.vo.AlarmVO;
import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.DocVO;
import edu.hrms.vo.OverVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.SignVO;
import edu.hrms.vo.UserVO;
import edu.hrms.vo.VacaVO;

@Controller
@RequestMapping(value = "/sign")
public class SignController {
	
	private int userId = 0;
	
	@Autowired
	SignService signService;
	
	@Autowired
	AlarmService alarmService;
	
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main(Authentication authentication, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
		UserVO loginUser = (UserVO)authentication.getPrincipal();
		userId = Integer.parseInt(loginUser.getUserid());
		
		List<DocVO> docVOList = signService.selectAllDoc(userId);
		List<VacaVO> vacaVOList = signService.selectAllVaca(userId);
		List<OverVO> overVOList = signService.selectAllOver(userId);
		
		int docSignCount = signService.selectDocSignCount(userId);
		int vacaSignCount = signService.selectVacaSignCount(userId);
		int overSignCount = signService.selectOverSignCount(userId);
		
		SignVO vo = new SignVO(
				docVOList,
				vacaVOList,
				overVOList
		);
		model.addAttribute("list", vo);
		model.addAttribute("docSignCount", docSignCount);
		model.addAttribute("vacaSignCount", vacaSignCount);
		model.addAttribute("overSignCount", overSignCount);
		
		return "/sign/main";
	}
	
	@RequestMapping(value = "/docView.do", method = RequestMethod.GET)
	public String docView(int docNo, Model model, Authentication authentication) {
		UserVO loginUser = (UserVO)authentication.getPrincipal();
		userId = Integer.parseInt(loginUser.getUserid());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("docNo", docNo);
		map.put("userId", userId);
		DocVO docVO = signService.selectDocFromDocNo(map);
		List<DocFileVO> fileList = signService.selectDocFile(docNo);
		List<SignLineVO> signLineVO = signService.selectSignLineFromDocNo(docVO.getDocNo());
		
		int count = signLineVO.size();
		for(int j=0; j<signLineVO.size(); j++) {
			if(signLineVO.get(j).getUserId() == userId) {
				docVO.setMySignOrder(signLineVO.get(j).getSignOrder());
			}
			if(signLineVO.get(j).getState().equals("0")) {
				count--;
			}
		}
		docVO.setStateCount(count);
		docVO.setSignLineVO(signLineVO);
		docVO.setDocFileVO(fileList);
		
		model.addAttribute("vo", docVO);
		
		return "/sign/docView";
	}
	
	@RequestMapping(value = "/vacaView.do", method = RequestMethod.GET)
	public String vacaView(int vacaNo, Model model, Authentication authentication) {
		UserVO loginUser = (UserVO)authentication.getPrincipal();
		userId = Integer.parseInt(loginUser.getUserid());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("vacaNo", vacaNo);
		map.put("userId", userId);
		VacaVO vacaVO = signService.selectVacaFromVacaNo(map);
		List<SignLineVO> signLineVO = signService.selectSignLineFromVacaNo(vacaVO.getVacaNo());
		
		int count = signLineVO.size();
		for(int j=0; j<signLineVO.size(); j++) {
			if(signLineVO.get(j).getUserId() == userId) {
				vacaVO.setMySignOrder(signLineVO.get(j).getSignOrder());
			}
			if(signLineVO.get(j).getState().equals("0")) {
				count--;
			}
		}
		vacaVO.setStateCount(count);
		vacaVO.setSignLineVO(signLineVO);
		model.addAttribute("vo", vacaVO);
		
		return "/sign/vacaView";
	}
	
	@RequestMapping(value = "/overView.do", method = RequestMethod.GET)
	public String OverView(int overTimeNo, Model model, Authentication authentication) {
		UserVO loginUser = (UserVO)authentication.getPrincipal();
		userId = Integer.parseInt(loginUser.getUserid());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("overTimeNo", overTimeNo);
		map.put("userId", userId);
		OverVO overVO = signService.selectOverFromOverTimeNo(map);
		List<SignLineVO> signLineVO = signService.selectSignLineFromOverTimeNo(overVO.getOverTimeNo());
		
		int count = signLineVO.size();
		for(int j=0; j<signLineVO.size(); j++) {
			if(signLineVO.get(j).getUserId() == userId) {
				overVO.setMySignOrder(signLineVO.get(j).getSignOrder());
			}
			if(signLineVO.get(j).getState().equals("0")) {
				count--;
			}
		}
		overVO.setStateCount(count);
		overVO.setSignLineVO(signLineVO);
		
		model.addAttribute("vo", overVO);
		
		return "/sign/overView";
	}
	
	@RequestMapping(value = "/approvedDoc.do", method = RequestMethod.POST)
	public void approvedDoc(DocVO docVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromDocNo(docVO.getDocNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(docVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateApprovedDoc(docVO);
					signService.updateDocState(docVO.getDocNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("docNo",docVO.getDocNo());
					map.get("alarmNo");
					signService.updateDocAlarm(map);
				}else {
					result = -1;
				}
			}
		}
		
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('승인완료 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1) {
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('승인되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value = "/rejectedDoc.do", method = RequestMethod.POST)
	public void rejectedDoc(DocVO docVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromDocNo(docVO.getDocNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(docVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateRejectedDoc(docVO);
					signService.updateDocState(docVO.getDocNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("docNo",docVO.getDocNo());
					map.get("alarmNo");
					signService.updateDocAlarm(map);
				}else {
					result = -1;
				}
			}
		}
		
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('반려 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1){
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('반려 되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value = "/approvedVaca.do", method = RequestMethod.POST)
	public void approvedVaca(VacaVO vacaVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromVacaNo(vacaVO.getVacaNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(vacaVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateApprovedVaca(vacaVO);
					signService.updateVacaState(vacaVO.getVacaNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("vacaNo",vacaVO.getVacaNo());
					map.get("alarmNo");
					signService.updateVacaAlarm(map);
				}else {
					result = -1;
				}
			}
		}
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('승인완료 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1) {
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('승인되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value = "/rejectedVaca.do", method = RequestMethod.POST)
	public void rejectedVaca(VacaVO vacaVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromVacaNo(vacaVO.getVacaNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(vacaVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateRejectedVaca(vacaVO);
					signService.updateVacaState(vacaVO.getVacaNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("vacaNo",vacaVO.getVacaNo());
					map.get("alarmNo");
					signService.updateVacaAlarm(map);
				}else {
					result = -1;
				}
			}
		}
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('반려 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1){
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('반려 되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value = "/approvedOver.do", method = RequestMethod.POST)
	public void approvedOver(OverVO overVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromOverTimeNo(overVO.getOverTimeNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(overVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateApprovedOver(overVO);
					signService.updateOverState(overVO.getOverTimeNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("overTimeNo",overVO.getOverTimeNo());
					map.get("alarmNo");
					signService.updateOverAlarm(map);
				}else {
					result = -1;
				}
			}
		} 
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('승인완료 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1) {
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('승인되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value = "/rejectedOver.do", method = RequestMethod.POST)
	public void rejectedOver(OverVO overVO, HttpServletResponse res) throws IOException {
		int result = 0;
		List<SignLineVO> signLineVO = signService.selectSignLineFromOverTimeNo(overVO.getOverTimeNo());
		for(int i=0; i<signLineVO.size(); i++) {
			if(signLineVO.get(i).getSignLineNo()==Integer.parseInt(overVO.getMySignLineNo())) {
				if(signLineVO.get(i).getNextState() == null || signLineVO.get(i).getNextState().equals("0")) {
					result = signService.updateRejectedOver(overVO);
					signService.updateOverState(overVO.getOverTimeNo());
					Map<String, Object> map = new HashMap<String, Object>();
					alarmService.insertAlarm(map);
					map.put("overTimeNo",overVO.getOverTimeNo());
					map.get("alarmNo");
					signService.updateOverAlarm(map);
				}else {
					result = -1;
				}
			}
		}
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('반려 되었습니다.');location.href='main.do'</script>");
		}else if(result == -1) {
			res.getWriter().append("<script>alert('이미 상위 직급 사원이 결재처리 하여 수정이 불가합니다.');location.href='main.do'</script>");
		}else {
			res.getWriter().append("<script>alert('반려되지 않았습니다.');location.href='main.do'</script>");
		}
		res.getWriter().flush();
	}
	
	@RequestMapping(value="/search.do")
	@ResponseBody
	public HashMap<String, Object> search(String startDate, String endDate, String name, String mySignState, Authentication authentication){
		UserVO loginUser = (UserVO)authentication.getPrincipal();
		userId = Integer.parseInt(loginUser.getUserid());
		if(endDate.equals("")) {
			endDate = null;
		}
		if(startDate.equals("")) {
			startDate = null;
		}
		if(mySignState.equals("all")) {
			mySignState = null;
		}
		if(name.equals("")) {
			name = null;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", Integer.toString(userId));
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("name", name);
		map.put("mySignState", mySignState);
		
		System.out.println("startDate:"+startDate+"endDate:"+endDate+"name:"+name+"state:"+mySignState);
		List<DocVO> docVOList = signService.selectDocSearch(map);
		List<VacaVO> vacaVOList = signService.selectVacaSearch(map);
		List<OverVO> overVOList = signService.selectOverSearch(map);
		
		int docSignCount = signService.selectDocSignCount(userId);
		int vacaSignCount = signService.selectVacaSignCount(userId);
		int overSignCount = signService.selectOverSignCount(userId);
		
		SignVO vo = new SignVO(
				docVOList,
				vacaVOList,
				overVOList
		);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("list", vo);
		resultMap.put("docSignCount", docSignCount);
		resultMap.put("vacaSignCount", vacaSignCount);
		resultMap.put("overSignCount", overSignCount);
		
		return resultMap;
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	public void download(HttpServletResponse response, HttpServletRequest request, DocFileVO vo) throws IOException {
		
		File f = new File(request.getSession().getServletContext().getRealPath("/resources/upload"), vo.getRealNm());
        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        String filename = URLEncoder.encode(vo.getOriginNm(),"UTF-8");
        filename = filename.replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
		
	}
		
	@RequestMapping(value = "/alarmRead.do")
	@ResponseBody
	public int alarmRead(int alarmNo) {
		int result = alarmService.updateAlarmRead(alarmNo);
		return result;
	}
	
	@RequestMapping(value = "/alarmRoad.do")
	@ResponseBody
	public List<AlarmVO> alarmRoad(Authentication authentication) {
		UserVO user = (UserVO)authentication.getPrincipal();
		List<AlarmVO> alarmList = alarmService.selectAlarm(Integer.parseInt(user.getUserid()));
		return alarmList;
	}
	
}

