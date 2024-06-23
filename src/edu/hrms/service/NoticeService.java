package edu.hrms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.NoticeFileVO;
import edu.hrms.vo.NoticeVO;

public interface NoticeService {
	//selectAllNotice
	List<NoticeVO> selectNotice(Map<String,Object>map);
	//���� �ϳ���
	int getNoticeCount();
	//selectNoticeOne
	NoticeVO selectNoticeByNoticeNo(int noticeNo);
	//selectNoticeFileList
	List <NoticeFileVO> selectNoticeFileByNoticeNo(int noticeNo);
	//update
	int updateNotice(NoticeVO noticeVO);
	//delete
	int deleteNoticeFiles(Map<String, Object> map);
	//insert
	int insertNotice(NoticeVO noticeVO);
	//�ֽű�
	int getMaxNoByUserId(int userId);
	//���ϰ��
	String getPath(HttpServletRequest request);
	//���� ���ε�
	List<NoticeFileVO> createFiles(List<MultipartFile> files, String path, int noticeNo);
	
	List<MultipartFile> getMultipartFileList(HttpServletRequest request, List<DocFileVO> list);
	
	int insertNoticeFile(List<NoticeFileVO> vo);
	
	//delete
	int delyn(int noticeNo);
	
	
}
