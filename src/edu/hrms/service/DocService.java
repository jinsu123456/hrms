package edu.hrms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.DocSignVO;
import edu.hrms.vo.DocVO;
import edu.hrms.vo.SignLineVO;

public interface DocService {

	List<DocVO> selectList(Map<String, Object> map);
	
	int insert(DocVO vo);
	
	int getMaxNoByUserId(int userId);
	
	String getPath(HttpServletRequest request);
	
	List<DocFileVO> createFiles(List<MultipartFile> files, String path, int docNo);
	
	int insertDocFile(List<DocFileVO> list);
	
	List<DocSignVO> getDocSignList(List<SignLineVO> signLineList, int docNo);
	
	int insertDocSign(List<DocSignVO> list);
	
	List<DocSignVO> getDocSignList(int docNo);
	
	DocVO selectDocByDocNo(int docNo);
	
	List<DocFileVO> selectDocFilesByDocNo(int docNo);
	
	int updateDoc(DocVO vo);
	
	int deleteDocFiles(Map<String, Object> map);
	
	int updateDocSign(int docNo);
	
	int withdrawl(int docNo);
	
	int deleteDocSign(int docNo);
	
	List<MultipartFile> getMultipartFileList(HttpServletRequest request, List<DocFileVO> list);
}
