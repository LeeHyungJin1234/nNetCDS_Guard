package nnsp.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nnsp.services.NcDateService;

@Component
public class PacketStatScheduler {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PacketStatScheduler.class);
	
	@Autowired
	private NcDateService ncDateService;
	
	@Scheduled(cron="0 0 23 * * *") // 초 분 시 일 월 요일
	public void nextDayTblChk(){ //23시 마다 동작
		String tblDate = nnsp.util.StringUtil.getStrNextDay(1); // 다음날 날짜 테이블 명 지정

		ncDateService.createPacketTable(tblDate);  // 테이블 존재 유무 체크 후 생성
	}
}