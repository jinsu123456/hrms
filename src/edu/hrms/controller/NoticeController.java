package edu.hrms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.hrms.service.DocService;
import edu.hrms.service.NoticeService;
import edu.hrms.vo.NoticeFileVO;
import edu.hrms.vo.NoticeVO;
import edu.hrms.vo.PagingVO;
import edu.hrms.vo.UserVO;


@Controller
@RequestMapping(value ="/notice")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	/*
	 * @Autowired DocService docService;
	 */
	
	//����Ʈ
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String notice(Model model, String pnum) {
		System.out.println(pnum);
		
		int count = noticeService.getNoticeCount();
		
		PagingVO pagingVO = null;
		int pnumInt;
		if(pnum==null) {
			pnumInt = 1;
		}else{
			pnumInt = Integer.parseInt(pnum);
		}
		pagingVO = new PagingVO(pnumInt, count, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagingVO", pagingVO);		
		
		List<NoticeVO> list = noticeService.selectNotice(map);
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("list", list);
		return "/notice/main";
	}
	
	//�ۺ���
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(int noticeNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNoticeByNoticeNo(noticeNo);
		List <NoticeFileVO> list = noticeService.selectNoticeFileByNoticeNo(noticeNo);
		model.addAttribute("vo", noticeVO);
		model.addAttribute("list", list);		
		return "/notice/view";
	}
	//�۾�����������
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/write.do", method = RequestMethod.GET)
	public String write() {	
		return "/notice/write";
	}
	
	//�������������̵�
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(int noticeNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNoticeByNoticeNo(noticeNo);
		model.addAttribute("vo", noticeVO);
		List<NoticeFileVO> nfList = noticeService.selectNoticeFileByNoticeNo(noticeNo);
		model.addAttribute("nfList", nfList);
		return "/notice/modify";
	}
	//������ ������Ʈ
	@ResponseBody
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public int modifyupdate(HttpServletRequest request, @RequestParam(value= "files", required = false) List<MultipartFile> files
			, NoticeVO noticeVO, String[] deletedFiles) {
		
		String path = noticeService.getPath(request);
		
		int noticeNo = noticeVO.getNoticeNo();
		System.out.println(Arrays.toString(deletedFiles));
		
		//notice update
		int result = noticeService.updateNotice(noticeVO);
		if(deletedFiles.length>0) {
			//delete noticeFile in table.
			noticeService.deleteNoticeFiles(Map.of("noticeNo", noticeNo, "array", deletedFiles));
			System.out.println("if�� ����");
		}
		if(files.size()>0) {
			// 3. ���� ���� �� ���� vo����Ʈ �����Ѵ�.
			List<NoticeFileVO> fileList = noticeService.createFiles(files, path, noticeNo);
			
			// 4. noticefile ���̺� insert �Ѵ�.
			noticeService.insertNoticeFile(fileList);
		}
		
		return result;
	}
	
	//delyn ������Ʈ
	@RequestMapping(value = "/delyn.do", method = RequestMethod.POST)
	public void delyn(HttpServletResponse response, int noticeNo) throws IOException {
			
			int result = noticeService.delyn(noticeNo);
			if(result>0) {
				response.getWriter().append("<script>alert('���������� �����Ǿ����ϴ�.');location.href='main.do';</script>");
				response.getWriter().flush();
			}else {				
				response.getWriter().append("<script>alert('���������� �������� �ʾҽ��ϴ�.');location.href='main.do';</script>");
				response.getWriter().flush();
			}
		}
	//�۾��� �μ�Ʈ
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	@ResponseBody
	public String write(Authentication authentication, HttpServletRequest request, String title,
			 String content, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IllegalStateException, IOException {
		
		UserVO login = (UserVO) authentication.getPrincipal();
		int userid = Integer.parseInt(login.getUserid());
		String path = noticeService.getPath(request);
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs(); // �������� �ʴ� ��� ���� ���� ����
		}
		
		// ���� �ۼ� ���� ����
		// 1. ���� ���̺� insert �Ѵ�
		NoticeVO vo = new NoticeVO(userid, title, content);
		int noticeInsert = noticeService.insertNotice(vo);
		
		int result = 0;
		if(noticeInsert>0) {
			// 2. insert�� noticeNo ���´�
			int noticeNo = noticeService.getMaxNoByUserId(userid);
		
			if(files.size()>0) {
				// 3. ���� ���� �� ����vo����Ʈ �����Ѵ�.
				List<NoticeFileVO> fileList = noticeService.createFiles(files, path, noticeNo);
				
				// 4. noticefile ���̺� insert �Ѵ�
				result = noticeService.insertNoticeFile(fileList);	
			}
			
		}
		
		/*
		 * if(files != null && files.size()>0) { // 3. ���� ���� �� ����vo����Ʈ �����Ѵ�.
		 * List<NoticeFileVO> fileList = noticeService.createFiles(files, path,
		 * noticeNo);
		 * 
		 * // 4. �������� ���̺� insert �Ѵ� noticeService.insertNoticeFile(fileList); }
		 */
		return Integer.toString(result);
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	public void download(HttpServletResponse response, HttpServletRequest request, NoticeFileVO vo) throws IOException {
		
		File f = new File(noticeService.getPath(request), vo.getRealNm());
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