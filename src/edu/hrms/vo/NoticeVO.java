package edu.hrms.vo;

import java.util.List;
//select
public class NoticeVO {
	private int noticeNo;
	private int userid;
	private String title;
	private String content;
	private String rdate;
	private String delyn;
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private List<NoticeFileVO> noticeFileVO;
	
	public NoticeVO() {}
//input data insert
	public NoticeVO(int userid, String title, String content ) {
		super();
		this.userid = userid;
		this.title = title;
		this.content = content;
	}

	public List<NoticeFileVO> getNoticeFileVO() {
		return noticeFileVO;
	}

	public void setNoticeFileVO(List<NoticeFileVO> noticeFileVO) {
		this.noticeFileVO = noticeFileVO;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getDelyn() {
		return delyn;
	}

	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}

	@Override
	public String toString() {
		return "NoticeVO [noticeNo=" + noticeNo + ", userid=" + userid + ", title=" + title + ", content=" + content
				+ ", rdate=" + rdate + ", delyn=" + delyn + ", noticeFileVO=" + noticeFileVO + "]";
	}

}
