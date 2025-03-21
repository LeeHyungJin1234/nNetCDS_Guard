package nnsp.services;

import java.util.ArrayList;

import nnsp.mappers.NcAlarmMapper;
import nnsp.vo.NcAlarm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcAlarmService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcAlarmService.class);
	@Autowired
	private NcAlarmMapper ncAlarmMapper;
	
	// 알람 표시 갯수 조회
	public ArrayList<NcAlarm> getAlarmCnt() {
		return ncAlarmMapper.getAlarmCnt();
    }
	
	// 무결성 알람 내용 조회
	public ArrayList<NcAlarm> getInteDetail() {
		return ncAlarmMapper.getInteDetail();
    }
	
	// 무결성 알람 내용 조회
	public ArrayList<NcAlarm> getLogDetail() {
		return ncAlarmMapper.getLogDetail();
    }
	
	// 알람 삭제
	public int alarm_delete(String noa_type){
		return ncAlarmMapper.alarm_delete(noa_type);
	}

	public int login_alarm_insert(String noa_type, String noa_detail) {
		// TODO Auto-generated method stub
		System.out.println("알람 찍기");
		return ncAlarmMapper.alarm_login(noa_type, noa_detail);
		
	}
	public int insertEmailNotification(String nnq_id, String message) {
		return ncAlarmMapper.insertEmailNotification(nnq_id, message);
	}

	public ArrayList<NcAlarm> getLoginDetail() {
		// TODO Auto-generated method stub
		return ncAlarmMapper.getLoginDetail();
	}
}