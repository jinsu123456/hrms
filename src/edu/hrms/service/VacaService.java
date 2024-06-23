package edu.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;

import edu.hrms.vo.SignLineVO;
import edu.hrms.vo.VacaSignVO;
import edu.hrms.vo.VacaVO;

public interface VacaService {
	
	List<VacaVO> selectMyVacaList(VacaVO vo);
	
	VacaVO myRecentVacaApplication(String userid);
	
	Map<String, Integer> myRemainVaca(String userid);
	
	int checkVacaAppCnt(VacaVO vo);
	
	int insertVaca(VacaVO vo);
	
	int getMaxNoByUserId(String userid);
	
	int insertVacaSign(List<VacaSignVO> list);
	
	VacaVO selectVacaByVacaNo(int vacaNo);
	
	List<VacaSignVO> getVacaSignList(List<SignLineVO> signLineList, int vacaNo);
	
	List<VacaSignVO> getVacaSignList(int vacaNo);
	
	int withdrawal(int vacaNo);
	
	int vacaSignDelete(int vacaNo);
	
	List<VacaVO> selectVacaListToUpdate(String today);
	
	int minusUserVaca(List<VacaVO> list);
	
	int updateVacaStateToUse(List<VacaVO> list);
	
	
	// °ü¸®ÀÚ
	List<VacaVO> selectAllVacaList(Map<String, Object> map);
	
	List<Map<String, Object>> selectAllUserList(Map<String, Object> map);
	
	int getCountOfAllUserList(Map<String, Object> map);
	
	@Secured("ROLE_ADMIN")
	int giveVaca(Map<String, Object> map);
	
	
}
