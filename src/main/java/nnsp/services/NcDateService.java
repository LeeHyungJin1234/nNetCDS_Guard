package nnsp.services;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nnsp.common.Config;
import nnsp.mappers.NcLogDateMapper;

@Component
public class NcDateService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcDateService.class);

	@Autowired
	private NcCommonService ncCommonService; // 동적 SQL은 XMLMAPPER로 한다.
	@Autowired
	private NcLogDateMapper ncLogDateMapper; // 동적 SQL은 XMLMAPPER로 한다.

	// 테이블 생성 위협탐지
	private int createTblLog(String dbName, String tblName, int tblDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table_name", tblDate);//조회 테이블 명 설정

		if(ncCommonService.getTableCheck(dbName, tblName)<1) {
			return this.ncLogDateMapper.createTblLog(map);
		}
		else {
			return 0;
		}
	}
	
	//  날짜별 테이블 생성 처리
	public void createAceessTable(String yyyymm) {
		String dbName = "nnet_cds2";
		
		int tblDate = Integer.parseInt(yyyymm.substring( 0, 6));
		String tblNameAccess = "nnc_log_access_" + tblDate;

		this.createTblLog(dbName, tblNameAccess, tblDate);
	}
	
	
	
	// 테이블 생성 패킷 분석
	private int createTblPacket(String dbName, String tblName, int tblDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("table_name", tblDate); //  조회 테이블 명 날짜만

		int cntTbl = ncCommonService.getTableCheck(dbName, tblName);
		if(cntTbl<1) { // 테이블 유무 판단 mariadb
			return ncLogDateMapper.createTblPacket(map);
		}
		else {
			return 0;
		}
	}
	//  날짜별 테이블 생성 처리
	public void createPacketTable(String yyyymmdd) {
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.analyze");
		
		int tblDate = Integer.parseInt(yyyymmdd.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;

		this.createTblPacket(dbName, tblName, tblDate);
	}
}