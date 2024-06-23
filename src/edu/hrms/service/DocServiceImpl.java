package edu.hrms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.hrms.dao.DocDAO;
import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.DocSignVO;
import edu.hrms.vo.DocVO;
import edu.hrms.vo.SignLineVO;

@Service
public class DocServiceImpl implements DocService {

	@Autowired
	DocDAO docDAO;
	
	@Override
	public List<DocVO> selectList(Map<String, Object> map) {
		return docDAO.selectList(map);
	}
	
	@Override
	public int insert(DocVO vo) {
		return docDAO.insert(vo);
	}
	
	@Override
	public int getMaxNoByUserId(int userId) {
		return docDAO.getMaxNoByUserId(userId);
	}
	
	@Override
	public String getPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
//		String path = "C:/Users/MYCOM/git/hrms/hrms/src/main/webapp/resources/upload";
//		String path = request.getContextPath()+"/resources/upload";
		return path;
	}
	
	@Override
	public List<DocFileVO> createFiles(List<MultipartFile> files, String path, int docNo) {

		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		System.out.println("path: "+path);
		
		List<DocFileVO> list = new ArrayList<>();
		for(MultipartFile data : files) {
			try {
				String originNm = data.getOriginalFilename();
				String[] fileNmArr = originNm.split("\\.");
				String ext = fileNmArr[fileNmArr.length-1].toLowerCase();
				
				String realNm = null;
				int i = 1;
				while(true) {
					realNm = fileNmArr[0] + "_" + i + "." + ext;
					File file = new File(path, realNm);
					if(!file.exists()) {
						data.transferTo(file);
						list.add(new DocFileVO(docNo, originNm, realNm));
						break;
					}
					i++;
				}
				
			}catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public int insertDocFile(List<DocFileVO> list) {
		return docDAO.insertDocFile(list);
	}
	
	@Override
	public int updateDoc(DocVO vo) {
		return docDAO.updateDoc(vo);
	}
	
	@Override
	public int deleteDocFiles(Map<String, Object> map) {
		return docDAO.deleteDocFiles(map);
	}
	
	@Override
	public int updateDocSign(int docNo) {
		return docDAO.updateDocSign(docNo);
	}
	
	@Override
	// insert 할 때 사용할 signVO 리스트 생성 메소드
	public List<DocSignVO> getDocSignList(List<SignLineVO> signLineList, int docNo) {
		List<DocSignVO> docSignList = new ArrayList<>();
		for(SignLineVO vo : signLineList) {
			DocSignVO dsvo = new DocSignVO();
			dsvo.setDocNo(docNo);
			dsvo.setSignLineNo(vo.getSignLineNo());
			docSignList.add(dsvo);
		}
		return docSignList;
	}
	
	@Override
	public int insertDocSign(List<DocSignVO> list) {
		return docDAO.insertDocSign(list);
	}
	
	@Override
	public DocVO selectDocByDocNo(int docNo) {
		return docDAO.selectDocByDocNo(docNo);
	}
	
	@Override
	// db에서 signVO 리스트 가져올 때 사용하는 메소드
	public List<DocSignVO> getDocSignList(int docNo) {
		return docDAO.getDocSignList(docNo);
	}
	
	@Override
	public List<DocFileVO> selectDocFilesByDocNo(int docNo) {
		return docDAO.selectDocFilesByDocNo(docNo);
	}
	
	@Override
	public int withdrawl(int docNo) {
		return docDAO.withdrawl(docNo);
	}
	
	@Override
	public int deleteDocSign(int docNo) {
		return docDAO.deleteDocSign(docNo);
	}
	
	@Override
	public List<MultipartFile> getMultipartFileList(HttpServletRequest request, List<DocFileVO> list) {
		
		List<MultipartFile> mfList = new ArrayList<>();
		String path = getPath(request);
		for(int i=0; i<list.size(); i++) {
			try {
				File f = new File(path, list.get(i).getRealNm());
				FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(f.toPath()), false, f.getName(), (int) f.length(), f.getParentFile());
				InputStream input = new FileInputStream(f);
				OutputStream os = fileItem.getOutputStream();
				IOUtils.copy(input, os);
				MultipartFile mf = new CommonsMultipartFile(fileItem);
				mfList.add(mf);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return mfList;
    }
	
	
}
