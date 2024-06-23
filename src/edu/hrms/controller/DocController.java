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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.hrms.service.AlarmService;
import edu.hrms.service.DocService;
import edu.hrms.service.SignService;
import edu.hrms.service.WorkService;
import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.DocSignVO;
import edu.hrms.vo.DocVO;
import edu.hrms.vo.SearchVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.UserVO;

@Controller
@RequestMapping(value = "/docu")
public class DocController {
	
	@Autowired
	WorkService workService;
	
	@Autowired
	DocService docService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(Model model, Authentication authentication) {
		UserVO login = (UserVO) authentication.getPrincipal();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userid", login.getUserid());
		map.put("dept", login.getDept());
		List<DocVO> list_my = docService.selectList(map);
		
		map.remove("userid");
		map.put("state", "2");
		List<DocVO> list_dept = docService.selectList(map);
		model.addAttribute("list_my", list_my);
		model.addAttribute("list_dept", list_dept);
		
		return "document/main";
	}
	
	@RequestMapping(value = "/write.do", method = RequestMethod.GET)
	public String write(Model model, Authentication authentication) {
		UserVO login = (UserVO) authentication.getPrincipal();
		
		List<SignLineVO> signLineList = workService.getSignLineList(login.getUserid(), login.getPosition(), "D");
		model.addAttribute("signLineList", signLineList);
		
		return "document/write";
	}
	
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	@ResponseBody
	public String write(Authentication authentication, HttpServletRequest request, 
			String title, String content, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IllegalStateException, IOException {
		
		UserVO login = (UserVO) authentication.getPrincipal();
		int userid = Integer.parseInt(login.getUserid());
		String path = docService.getPath(request);
//		String path = request.getSession().getServletContext().getRealPath("/resources/upload"); // ����
		
		// ��� �ۼ� ���� ����
		// 1. doc ���̺� insert �Ѵ�
		DocVO vo = new DocVO(Integer.toString(userid), title, content);
		int docInsert = docService.insert(vo);
		int docSignInsert = 0; 
		
		if(docInsert>0) {
			// 2. insert�� docNo ���´�
			int docNo = docService.getMaxNoByUserId(userid);
			
			if(files.size()>0) {
				// 3. ���� ���� �� ����vo����Ʈ �����Ѵ�.
				List<DocFileVO> fileList = docService.createFiles(files, path, docNo);
				
				// 4. docfile ���̺� insert �Ѵ�
				docService.insertDocFile(fileList);
			}
			
			// 5. ������� ����Ʈ �����Ѵ�.
			List<SignLineVO> signLineList = workService.getSignLineList(Integer.toString(userid), login.getPosition(), "D");
			
			// 6. docSign ����Ʈ �����Ѵ�.
			List<DocSignVO> docSignList = docService.getDocSignList(signLineList, docNo);
			
			// 7. docsign ���̺� insert �Ѵ�.
			docSignInsert = docService.insertDocSign(docSignList);
		}
		
		return Integer.toString(docSignInsert);
	}
	
	@RequestMapping(value = "/reloadList.do")
	@ResponseBody
	public List<DocVO> reloadList(SearchVO searchVO, Authentication authentication){
		
		UserVO login = (UserVO)authentication.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("dept", login.getDept());
		map.put("searchVO", searchVO);
		
		if(searchVO.getListType().equals("my")) {
			map.put("userid", login.getUserid());
		}else {
			map.put("state", "2");
		}
	
		return docService.selectList(map);
	}
	
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Model model, int docNo) {
		
		DocVO vo = docService.selectDocByDocNo(docNo);
		model.addAttribute("vo", vo);
		
		// 1. db���� sign ����Ʈ ���´�
		List<DocSignVO> list = docService.getDocSignList(docNo);
		
		// 2. 1���� ���� ����Ʈ �����Ѵ�
		list = (List<DocSignVO>) workService.processList(list);
		model.addAttribute("list", list);
		
		// 3. 2���� ���� ����Ʈ�� ���� ��Ȳ ī��Ʈ, �����Ȳ  ���Ѵ�
		Map<String, Object> map = workService.getCountNowstate(list, vo.getState());
		
		model.addAttribute("count", map.get("count"));
		model.addAttribute("nowState", map.get("nowState"));
		model.addAttribute("dfList", docService.selectDocFilesByDocNo(docNo));
		
		return "document/view";
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(Model model, int docNo, Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		UserVO login = (UserVO) authentication.getPrincipal();
		DocVO vo = docService.selectDocByDocNo(docNo);
		
		if(vo.getUserid().equals(login.getUserid())){
			model.addAttribute("vo", vo);
			
			List<SignLineVO> signLineList = workService.getSignLineList(login.getUserid(), login.getPosition(), "D");
			model.addAttribute("signLineList", signLineList);
			
			List<DocFileVO> dfList = docService.selectDocFilesByDocNo(docNo);
			model.addAttribute("dfList", dfList);
			
		}else {
			response.getWriter().append("<script>alert('�߸��� �����Դϴ�.');location.href='../docu/main.do';</script>");
			response.getWriter().flush();
		}
		
		return "document/modify";
	}
	
	@ResponseBody
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public int modify(HttpServletRequest request, @RequestParam(value = "files", required = false) List<MultipartFile> files
			, DocVO vo, String[] deletedFiles) {
		
		String path = docService.getPath(request);
//		String path = request.getSession().getServletContext().getRealPath("/resources/upload"); // ����
		
		int docNo = vo.getDocNo();
		
		// ��� ���� ����
		// 1. doc update �Ѵ�.
		docService.updateDoc(vo);
		
		if(deletedFiles.length>0) {
			// 2. ������Ų ���� docfile ���̺��� �����Ѵ�.
			docService.deleteDocFiles(Map.of("docNo", docNo, "array", deletedFiles));
		}
		
		if(files.size()>0) {
			// 3. ���� ���� �� ���� vo����Ʈ �����Ѵ�.
			List<DocFileVO> fileList = docService.createFiles(files, path, docNo);
			
			// 4. docfile ���̺� insert �Ѵ�.
			docService.insertDocFile(fileList);
		}
			
		// 5. docSign update �Ѵ�.
		int docSignUpdate = docService.updateDocSign(docNo);
		Map<String, Object> map = new HashMap<String, Object>();
		
		return docSignUpdate;
	}
	
	@RequestMapping(value = "/withdrawal.do", method = RequestMethod.POST)
	public void withdrawal(HttpServletResponse response, int docNo) throws IOException {
		docService.withdrawl(docNo);
		int result = docService.deleteDocSign(docNo);
		
		if(result>0) {
			response.getWriter().append("<script>alert('����� öȸ�Ǿ����ϴ�.');location.href='../docu/main.do';</script>");
		}else {
			response.getWriter().append("<script>alert('������ �߻��߽��ϴ�.');location.href='../docu/main.do';</script>");
		}
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	public void download(HttpServletResponse response, HttpServletRequest request, DocFileVO vo) throws IOException {
		
		File f = new File(docService.getPath(request), vo.getRealNm());
        // file �ٿ�ε� ����
        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        
        String fileName = URLEncoder.encode(vo.getOriginNm(), "UTF-8");
        fileName = fileName.replaceAll("\\+", "%20");
        
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
        // response ��ü�� ���ؼ� �����κ��� ���� �ٿ�ε�
        OutputStream os = response.getOutputStream();
        // ���� �Է� ��ü ����
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
		
	}
	
	
}
