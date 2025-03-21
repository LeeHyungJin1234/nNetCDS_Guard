package nnsp.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import nnsp.services.NcDateService;

@Service
public class NdService implements ApplicationListener<ContextClosedEvent>, InitializingBean, DisposableBean {

	@Autowired
	private NcDateService ncDateService;
	
	@PostConstruct
    private void init() {  //  웹서버 시작시 실행 (초기화 작업)
		String tblDate = nnsp.util.StringUtil.getStrNextDay(0); // 오늘 날짜 테이블 명 지정
		
		
		//String yyyymmdd = nnsp.util.StringUtil.getStrNextDay(0);
		ncDateService.createAceessTable(tblDate);  // 서비스 시작시 테이블 존재 유무 체크하여 생성 log_access 테이블
		ncDateService.createPacketTable(tblDate);  // 테이블 존재 유무 체크 후 생성 packet_stat 테이블
    }
    public void onApplicationEvent(ContextClosedEvent event) {
        //System.err.println("ApplicationListener<ContextClosedEvent> 인터페이스 구현 메서드 입니다. '애플리케이션'이 죽었을 때 '한 번' 실행됩니다.");
        //System.err.println("이벤트 발생 시간(timestamp) : " + event.getTimestamp());
    }
    public void afterPropertiesSet() throws Exception {
        //System.err.println("InitializingBean 인터페이스 구현 메서드입니다. NdrService 'Bean'이 생성될 때 마다 호출되는 메서드 입니다.");
    }
    public void destroy() throws Exception {
        //System.err.println("DisposableBean 인터페이스 구현 메서드입니다. NdrService 'Bean'이 소멸될 때 마다 호출되는 메서드입니다");
    }
}
