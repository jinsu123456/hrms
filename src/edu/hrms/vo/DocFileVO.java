package edu.hrms.vo;

public class DocFileVO {

	private int fileNo;
	private int docNo;
	private String realNm;
	private String originNm;
	
	
	public DocFileVO() {};
	public DocFileVO(int docNo, String originNm, String realNm) {
		this.docNo = docNo;
		this.originNm = originNm;
		this.realNm = realNm;
	}
	
	
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getDocNo() {
		return docNo;
	}
	public void setDocNo(int docNo) {
		this.docNo = docNo;
	}
	public String getRealNm() {
		return realNm;
	}
	public void setRealNm(String realNm) {
		this.realNm = realNm;
	}
	public String getOriginNm() {
		return originNm;
	}
	public void setOriginNm(String originNm) {
		this.originNm = originNm;
	}
	
	
	@Override
	public String toString() {
		return "DocFileVO [fileNo=" + fileNo + ", docNo=" + docNo + ", realNm=" + realNm + ", originNm=" + originNm
				+ "]";
	}
	
	
	
	
}
