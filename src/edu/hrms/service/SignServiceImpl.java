package edu.hrms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hrms.dao.SignDAO;
import edu.hrms.vo.DocFileVO;
import edu.hrms.vo.DocVO;
import edu.hrms.vo.OverVO;
import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.VacaVO;

@Service
public class SignServiceImpl implements SignService{
	
	@Autowired
	SignDAO signDAO;
	
	@Override
	public List<DocVO> selectAllDoc(int userId) {
		return signDAO.selectAllDoc(userId);
	}
	
	@Override
	public List<VacaVO> selectAllVaca(int userId) {
		return signDAO.selectAllVaca(userId);
	}
	
	@Override
	public List<OverVO> selectAllOver(int userId) {
		return signDAO.selectAllOver(userId);
	}
	
	@Override
	public List<SignLineVO> selectSignLineFromDocNo(int docNo) {
		return signDAO.selectSignLineFromDocNo(docNo);
	}
	
	@Override
	public List<SignLineVO> selectSignLineFromVacaNo(int vacaNo) {
		return signDAO.selectSignLineFromVacaNo(vacaNo);
	}
	
	@Override
	public List<SignLineVO> selectSignLineFromOverTimeNo(int overTimeNo) {
		return signDAO.selectSignLineFromOverTimeNo(overTimeNo);
	}
	
	@Override
	public DocVO selectDocFromDocNo(Map<String,Integer> map) {
		return signDAO.selectDocFromDocNo(map);
	}
	
	@Override
	public VacaVO selectVacaFromVacaNo(Map<String,Integer> map) {
		return signDAO.selectVacaFromVacaNo(map);
	}
	
	@Override
	public OverVO selectOverFromOverTimeNo(Map<String,Integer> map) {
		return signDAO.selectOverFromOverTimeNo(map);
	}

	@Override
	public int updateApprovedDoc(DocVO docVO) {
		return signDAO.updateApprovedDoc(docVO);
	}

	@Override
	public int updateRejectedDoc(DocVO docVO) {
		return signDAO.updateRejectedDoc(docVO);
	}

	@Override
	public int updateApprovedVaca(VacaVO vacaVO) {
		return signDAO.updateApprovedVaca(vacaVO);
	}

	@Override
	public int updateRejectedVaca(VacaVO vacaVO) {
		return signDAO.updateRejectedVaca(vacaVO);
	}

	@Override
	public int updateApprovedOver(OverVO overVO) {
		return signDAO.updateApprovedOver(overVO);
	}

	@Override
	public int updateRejectedOver(OverVO overVO) {
		return signDAO.updateRejectedOver(overVO);
	}

	@Override
	public int updateDocState(int docNo) {
		return signDAO.updateDocState(docNo);
	}

	@Override
	public int updateVacaState(int vacaNo) {
		return signDAO.updateVacaState(vacaNo);
	}

	@Override
	public int updateOverState(int overTimeNo) {
		return signDAO.updateOverState(overTimeNo);
	}
	
	@Override
	public List<DocVO> selectDocSearch(HashMap<String, String> map) {
		return signDAO.selectDocSearch(map);
	}

	@Override
	public List<VacaVO> selectVacaSearch(HashMap<String, String> map) {
		return signDAO.selectVacaSearch(map);
	}

	@Override
	public List<OverVO> selectOverSearch(HashMap<String, String> map) {
		return signDAO.selectOverSearch(map);
	}

	@Override
	public List<DocFileVO> selectDocFile(int docNo) {
		return signDAO.selectDocFile(docNo);
	}

	@Override
	public int updateDocAlarm(Map<String, Object> map) {
		return signDAO.updateDocAlarm(map);
	}

	@Override
	public int updateVacaAlarm(Map<String, Object> map) {
		return signDAO.updateVacaAlarm(map);
	}

	@Override
	public int updateOverAlarm(Map<String, Object> map) {
		return signDAO.updateOverAlarm(map);
	}

	@Override
	public int selectDocSignCount(int userId) {
		return signDAO.selectDocSignCount(userId);
	}

	@Override
	public int selectVacaSignCount(int userId) {
		return signDAO.selectVacaSignCount(userId);
	}

	@Override
	public int selectOverSignCount(int userId) {
		return signDAO.selectOverSignCount(userId);
	}
}
