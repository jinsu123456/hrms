package edu.hrms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.hrms.vo.VacaVO;

@Repository
public class CalcCalendar {

	SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
	SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	LocalDate todayDate = LocalDate.now();
	LocalDateTime todayDateTime = LocalDateTime.now();
	
	public Map<String, String> getFirstLastDays(String date) {
		Calendar calendar = Calendar.getInstance();
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);    
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);
		
		Map<String, String> map = new HashMap<>();
		calendar.set(year, month - 1, day);
		map.put("weekOfYear", Integer.toString(calendar.get(Calendar.WEEK_OF_YEAR)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		map.put("startDay", sdf_date.format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		map.put("endDay", sdf_date.format(calendar.getTime()));
		
		return map;
	}
	
	public String getTodayDate() {
		return todayDate.toString();
	}
	
	public String getNowTime() {
		return todayDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public boolean isParam1_beforeOrAfter_param2(String param1, String param2, String before_or_after) {
		Date time1;
		Date time2;
		boolean result = false;
		
		try{
			time1 = sdf_datetime.parse(param1);
			time2 = sdf_datetime.parse(param2);
			
			if(before_or_after.equals("before")) {
				result = time1.before(time2);
			}else if(before_or_after.equals("after")) {
				result = time1.after(time2);
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public int getVacationDays(String startDate, String endDate) {
		Date dateStartDate = null;
		Date dateEndDate = null;
	    try {
	        dateStartDate = sdf_date.parse(startDate);
	        dateEndDate = sdf_date.parse(endDate);
	    } catch (ParseException parseException) {
	        System.out.println(parseException.getStackTrace());
	    }

	    Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(dateStartDate);
	    Calendar cal2 = Calendar.getInstance();
	    cal2.setTime(dateEndDate);
	    int vacationDays = 1;
//	    int weekendDays = 0;

	    while (cal1.before(cal2)) {
	        if (Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK) && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
	        	vacationDays++;
	        }else {
//	        	weekendDays++;
	        }
	        cal1.add(Calendar.DATE, 1);
	        startDate = sdf_date.format(cal1.getTime());
	    }
//	    System.out.println("vacationDays : " + vacationDays);
//	    System.out.println("weekendDays : " + weekendDays);
	    
	    return vacationDays;
	}
	
	public int getUseHours(String date, String time, String start_or_end) {
		
		// simpleDateFormat.parse(String) = String�� Date Ÿ������ ����ȯ
		// calendar.setTime(Date) => Date�� Calendar Ÿ������ ����ȯ
		// calendar.getTime() => Date Ÿ������ calendar ��ü�� �ð� ����
		
		String startTime = null;
		String endTime = null;
		
		if(start_or_end.equals("start")) {
			startTime = time;
			endTime = "18:00:00";
		}else if(start_or_end.equals("end")) {
			startTime = "09:00:00";
			endTime = time;
		}else {
			startTime = time;
			endTime = start_or_end;
		}
		int hours = 0;
		try {
			Date startTime_date = sdf_datetime.parse(date+" "+startTime);
			Date endTime_date = sdf_datetime.parse(date+" "+endTime);
			Date time12_00_01PM_date = sdf_datetime.parse(date+" "+"12:00:01");
			
			Calendar startTime_cal = Calendar.getInstance();
			Calendar endTime_cal = Calendar.getInstance();
			
			startTime_cal.setTime(startTime_date);
			endTime_cal.setTime(endTime_date);
			
			long startDatetime = startTime_cal.getTimeInMillis();
			long endDatetime = endTime_cal.getTimeInMillis();
			
			long hoursLong = (endDatetime-startDatetime)/(1000*60*60);
			hours = Long.valueOf(hoursLong).intValue();
			
//			System.out.println("���ɽð� ��� ��: "+hours);
			
			if(startTime_date.before(time12_00_01PM_date) && endTime_date.after(time12_00_01PM_date)) {
				hours -= 1;
			}
//			System.out.println("���ɽð� ��� ��: "+hours);
			
		} catch(ParseException e) { e.printStackTrace(); }
		
		return hours;
	}
	
	public int getTotalUseHour(VacaVO vo) {
		
		String startDate = vo.getStartDate();
		String endDate = vo.getEndDate();
		String startTime = vo.getStartTime();
		String endTime = vo.getEndTime();
		
		// ���� ����ϼ� 1���� ��� => ���ð����� ���۽ð� ���� ��(���ɽð� ��� �ʿ�)
		
		// ���� ����ϼ� 2���� ���
		// 1. ������ 18�ÿ��� ������ ���۽ð� ����(���ɽð� ��� �ʿ�)
		// 2. ���� ���ð����� ���� 09�� ����(���ɽð� ��� �ʿ�)
		// 3. 1+2 �Ѵ�
		
		// ���� ����ϼ� 3�� �̻��� ���
		// 1. ������ 18�ÿ��� ������ ���۽ð� ����(���ɽð� ��� �ʿ�)
		// 2. ���� ���ð����� ���� 09�� ����(���ɽð� ��� �ʿ�)
		// 3. 1+2�� ������ �ϼ�*8�ð� ���Ѵ�
		
		int totalHours = 0;
		// �����ð� ��� ����
		// 1. ���� �ָ� ������ ���� �ϼ� ���Ѵ�
		int days = getVacationDays(startDate, endDate);
		
		// 2. �ϼ�(1��, 2��, 3�� �̻�)�� ���� �б� ó���Ѵ�
		if(days==1) {
			totalHours = getUseHours(startDate, startTime, endTime);
		}
		if(days>=2) {
			totalHours = getUseHours(startDate, startTime, "start");
			totalHours += getUseHours(endDate, endTime, "end");
		}
		if(days>=3) {
			totalHours += (days-2)*8;
		}
		
		return totalHours;
	}
	
	public boolean isParam1BetweenParam2AndParam3_date(String param1, String param2, String param3) {
		
		boolean result = false;
		try {
			Date date1 = sdf_date.parse(param1);
			Date date2 = sdf_date.parse(param2);
			Date date3 = sdf_date.parse(param3);
			
			if(date1.compareTo(date2)==0 || date1.after(date2)) {
				if(date1.compareTo(date3)==0 || date1.before(date3)) {
					result = true;
				}
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	


	

}
