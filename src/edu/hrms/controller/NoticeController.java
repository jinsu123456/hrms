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
	
	//리스트
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
	
	//글보기
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(int noticeNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNoticeByNoticeNo(noticeNo);
		List <NoticeFileVO> list = noticeService.selectNoticeFileByNoticeNo(noticeNo);
		model.addAttribute("vo", noticeVO);
		model.addAttribute("list", list);		
		return "/notice/view";
	}
	//글쓰기페이지로
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/write.do", method = RequestMethod.GET)
	public String write() {	
		return "/notice/write";
	}
	
	//수정페이지로이동
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(int noticeNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNoticeByNoticeNo(noticeNo);
		model.addAttribute("vo", noticeVO);
		List<NoticeFileVO> nfList = noticeService.selectNoticeFileByNoticeNo(noticeNo);
		model.addAttribute("nfList", nfList);
		return "/notice/modify";
	}
	//수정글 업데이트
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
			System.out.println("if문 실행");
		}
		if(files.size()>0) {
			// 3. 파일 생성 및 파일 vo리스트 리턴한다.
			List<NoticeFileVO> fileList = noticeService.createFiles(files, path, noticeNo);
			
			// 4. noticefile 테이블에 insert 한다.
			noticeService.insertNoticeFile(fileList);
		}
		
		return result;
	}
	
	//delyn 업데이트
	@RequestMapping(value = "/delyn.do", method = RequestMethod.POST)
	public void delyn(HttpServletResponse response, int noticeNo) throws IOException {
			
			int result = noticeService.delyn(noticeNo);
			if(result>0) {
				response.getWriter().append("<script>alert('공지사항이 삭제되었습니다.');location.href='main.do';</script>");
				response.getWriter().flush();
			}else {				
				response.getWriter().append("<script>alert('공지사항이 삭제되지 않았습니다.');location.href='main.do';</script>");
				response.getWriter().flush();
			}
		}
	//글쓰기 인서트
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	@ResponseBody
	public String write(Authentication authentication, HttpServletRequest request, String title,
			 String content, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IllegalStateException, IOException {
		
		UserVO login = (UserVO) authentication.getPrincipal();
		int userid = Integer.parseInt(login.getUserid());
		String path = noticeService.getPath(request);
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs(); // 존재하지 않는 모든 상위 폴더 생성
		}
		
		// 공지 작성 로직 순서
		// 1. 공지 테이블에 insert 한다
		NoticeVO vo = new NoticeVO(userid, title, content);
		int noticeInsert = noticeService.insertNotice(vo);
		
		int result = 0;
		if(noticeInsert>0) {
			// 2. insert된 noticeNo 얻어온다
			int noticeNo = noticeService.getMaxNoByUserId(userid);
		
			if(files.size()>0) {
				// 3. 파일 생성 및 파일vo리스트 리턴한다.
				List<NoticeFileVO> fileList = noticeService.createFiles(files, path, noticeNo);
				
				// 4. noticefile 테이블에 insert 한다
				result = noticeService.insertNoticeFile(fileList);	
			}
			
		}
		
		/*
		 * if(files != null && files.size()>0) { // 3. 파일 생성 및 파일vo리스트 리턴한다.
		 * List<NoticeFileVO> fileList = noticeService.createFiles(files, path,
		 * noticeNo);
		 * 
		 * // 4. 공지파일 테이블에 insert 한다 noticeService.insertNoticeFile(fileList); }
		 */
		return Integer.toString(result);
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	public void download(HttpServletResponse response, HttpServletRequest request, NoticeFileVO vo) throws IOException {
		
		File f = new File(noticeService.getPath(request), vo.getRealNm());
        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        String fileName = URLEncoder.encode(vo.getOriginNm(), "UTF-8");
        fileName = fileName.replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
		
	}

}