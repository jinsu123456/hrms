package edu.hrms.vo;

public class NoticeFileVO {

	private int noticeFileNo;
	private int noticeNo;
	private String realNm;
	private String originNm;
	
	public NoticeFileVO() {}
	
	public NoticeFileVO(int noticeFileNo, int noticeNo, String realNm, String originNm) {
		this.noticeFileNo = noticeFileNo;
		this.noticeNo = noticeNo;
		this.realNm = realNm;
		this.originNm = originNm;
	}
	
	public int getNoticeFileNo() {
		return noticeFileNo;
	}
	public void setNoticeFileNo(int noticeFileNo) {
		this.noticeFileNo = noticeFileNo;
	}
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
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
		return "NoticeFileVO [noticeFileNo=" + noticeFileNo + ", noticeNo=" + noticeNo + ", realNm=" + realNm
				+ ", originNm=" + originNm + "]";
	}
	
}
