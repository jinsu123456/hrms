package edu.hrms.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.hrms.service.VacaService;
import edu.hrms.util.CalcCalendar;
import edu.hrms.vo.VacaVO;

@Component("VacaScheduler2")
public class VacaScheduler {
	
	@Autowired
	VacaService vacaService;
	
	@Autowired
	CalcCalendar calcCalendar;
	
	@Scheduled(cron = "0 00 12 * * ?")
	public void updateVacaStateToUse() {
		
		// ���� ���Ϸ�� ���� ����
		// 1. ������Ʈ �� ���� ����Ʈ�� ��´�.
		List<VacaVO> list = vacaService.selectVacaListToUpdate(calcCalendar.getTodayDate());
		System.out.println("����Ʈ ������: "+list.size());
		
		if(list.size()!=0) {
			// 2. 1�� ���� ���� ���� �� ��� ���� �ð��� ������Ʈ�Ѵ�.
			int updateTimeCnt = vacaService.minusUserVaca(list);
			
			// 3. 1�� ���� ���¸� ���Ϸ�� �����Ѵ�.
			int updateStateCnt = vacaService.updateVacaStateToUse(list);
			
			System.out.println("user ������Ʈ ����: "+updateTimeCnt);
			System.out.println("vaca ������Ʈ ����: "+updateStateCnt);
			
		}else {
			System.out.println("������Ʈ �� �׸� ����");
		}
		
	}
	
}
