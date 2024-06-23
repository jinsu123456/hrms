package edu.hrms.util;

public class RegEx {

	public static final String MPW_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$";
	public static final String MNAME_REGEX = "^[가-힣]*$";
	public static final String MEMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
	public static final String MPHONE_REGEX = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

	public String checkRegEx(String regEx, String val) {
		
		String result = null;
		if(val.matches(regEx)) {
			result = val;
		}
		return result;
	}
	
}
