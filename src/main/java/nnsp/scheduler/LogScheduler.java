package nnsp.scheduler;

import java.text.ParseException;
import java.util.Calendar;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nnsp.services.NcDateService;

@Component
public class LogScheduler {
	
	//private static final Logger log = LoggerFactory.getLogger(LogScheduler.class);
		
	@Autowired
	private NcDateService ncDateService;
	
	@Scheduled(cron="0 0 23 28-31 * ?") // 초 분 시 일 월 요일
	public void nextDayTblChk() throws ParseException{ //23시 마다 동작
		final Calendar c = Calendar.getInstance();
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
			//It's the last day
		String tblDate = nnsp.util.StringUtil.getStrThisMonth(1); // 다음날 날짜 테이블 명 지정
		
		ncDateService.createAceessTable(tblDate);  // 테이블 존재 유무 체크 후 생성
		}
	}
	
}