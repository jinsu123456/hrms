package edu.hrms.vo;

import java.util.List;

public class SignVO {
	List<DocVO> docVO;
	List<VacaVO> vacaVO;
	List<OverVO> overVO;
	
	public SignVO() {}
	
	public SignVO(List<DocVO> docVO, List<VacaVO> vacaVO, List<OverVO> overVO) {
		this.docVO = docVO;
		this.vacaVO = vacaVO;
		this.overVO = overVO;
	}
	
	public List<DocVO> getDocVO() {
		return docVO;
	}
	public void setDocVO(List<DocVO> docVO) {
		this.docVO = docVO;
	}
	public List<VacaVO> getVacaVO() {
		return vacaVO;
	}
	public void setVacaVO(List<VacaVO> vacaVO) {
		this.vacaVO = vacaVO;
	}
	public List<OverVO> getOverVO() {
		return overVO;
	}
	public void setOverVO(List<OverVO> overVO) {
		this.overVO = overVO;
	}
}
