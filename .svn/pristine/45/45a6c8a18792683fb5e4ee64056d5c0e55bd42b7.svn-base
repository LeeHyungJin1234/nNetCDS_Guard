package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcStatNw;
import nnsp.vo.NcStatSys;
import nnsp.vo.NcSysLoad;
import nnsp.vo.NcStatMs;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface NcStatMapper {
	// 추이차트 날짜 조회
	final String SELECT_CURRENTTIME = "SELECT date_format(NSN_REG_DATE,'%Y-%m-%d %H:%i') NSN_REG_DATE " +
			"FROM NNC_STAT_NETFLOW " +
			//"WHERE NSN_REG_DATE > date_add(NOW(), interval -1 hour) " +
			"GROUP BY date_format(NSN_REG_DATE,'%Y-%m-%d %H:%i') " +
			"ORDER BY NSN_REG_DATE";
	
	// 시스템 동작 상태
	final String SELECT_ACTIVE_STATE = "SELECT NAS_IN_LINE1, NAS_IN_LINE2, NAS_IN_LINE3, NAS_TR_LINE1, NAS_TR_LINE2 " +
			"FROM NNC_ACTIVE_STATE";
	
	// 시스템 동작현황 대시보드 내부망 데이터 수집
	/*final String SELECT_COLLECT_INFO = "SELECT NCP.NCP_NAME, MAX_BYTE, NOW_BYTE FROM ( " +
			"SELECT NSR_PG_FNAME, MAX(NSR_BYTE) MAX_BYTE, (SELECT NSR_BYTE FROM NNC_STAT_RECV WHERE NSR_PG_FNAME=a.NSR_PG_FNAME ORDER BY NSR_DATE DESC LIMIT 1) NOW_BYTE " +
			"FROM NNC_STAT_RECV a " +
			"WHERE NSR_DATE > (NOW() - interval 1 hour) " +
			"GROUP BY NSR_PG_FNAME " +
			"ORDER BY MAX_BYTE DESC " +
			"LIMIT 5 " +
			") NSR, NNC_CONF_PROGRAM NCP WHERE NSR.NSR_PG_FNAME = NCP.NCP_FILE_NAME ORDER BY MAX_BYTE DESC";*/
	final String SELECT_COLLECT_INFO = "SELECT NCP.NCP_NAME, NSR_PG_FNAME, NSR.NSR_BYTE " +
			"FROM ( " +
				"SELECT NSR_PG_FNAME, SUM(NSR_BYTE) NSR_BYTE " +
				"FROM NNC_STAT_RECV " +
				"WHERE NSR_DATE > (NOW() - interval 10 minute) " +
				"GROUP BY NSR_PG_FNAME " +
				"ORDER BY NSR_BYTE DESC " +
				"LIMIT 10 " +
			") NSR, NNC_CONF_PROGRAM NCP " +
			"WHERE NSR.NSR_PG_FNAME = NCP.NCP_FILE_NAME " +
			"ORDER BY NSR_BYTE DESC";
	
	// 시스템 동작현황 대시보드 일방향 데이터 송신
	/*final String SELECT_SEND_INFO = "SELECT NCP.NCP_NAME, MAX_BYTE, NOW_BYTE FROM ( " +
			"SELECT NSS_PG_FNAME, MAX(NSS_BYTE) MAX_BYTE, (SELECT NSS_BYTE FROM NNC_STAT_SEND WHERE NSS_PG_FNAME=a.NSS_PG_FNAME ORDER BY NSS_DATE DESC LIMIT 1) NOW_BYTE " +
			"FROM NNC_STAT_SEND a " +
			"WHERE NSS_DATE > (NOW() - interval 1 hour) " +
			"GROUP BY NSS_PG_FNAME " +
			"ORDER BY MAX_BYTE DESC " +
			"LIMIT 5 " +
			") NSS, NNC_CONF_PROGRAM NCP WHERE NSS.NSS_PG_FNAME = NCP.NCP_FILE_NAME ORDER BY MAX_BYTE DESC";*/
	final String SELECT_SEND_INFO = "SELECT NCP.NCP_NAME, NSS_PG_FNAME, NSS.NSS_BYTE " +
			"FROM ( " +
				"SELECT NSS_PG_FNAME, SUM(NSS_BYTE) NSS_BYTE " +
				"FROM NNC_STAT_SEND " +
				"WHERE NSS_DATE > (NOW() - interval 10 minute) " +
				"GROUP BY NSS_PG_FNAME " +
				"ORDER BY NSS_BYTE DESC " +
				"LIMIT 10 " +
			") NSS, NNC_CONF_PROGRAM NCP " +
			"WHERE NSS.NSS_PG_FNAME = NCP.NCP_FILE_NAME " +
			"ORDER BY NSS_BYTE DESC";
	
	// 시스템 동작현황 tx 시스템 상태 모니터
	final String SELECT_SYS_MONTOR = "SELECT NSM_CPU, NSM_RAM, NSM_HDD FROM NNC_SYSTEM_MONITOR";
	// 시스템 동작현황 master, slave 큐 사이즈
	final String SELECT_QUEUE_SIZE = "SELECT NCS_MASTER_QUEUE, NCS_SLAVE_QUEUE FROM NNC_CONF_SYSTEM WHERE NCS_DIV=1";
	// 시스템 동작현황 master, slave 절체 상태
	final String SELECT_OUT_STATUS = "SELECT NCS_MASTER_OUT, NCS_SLAVE_OUT FROM NNC_CONF_SYSTEM WHERE NCS_DIV=1";
	// 시스템 동작현황 전체 전송량
	final String SELECT_TOTAL_SENDBYTE = "SELECT IFNULL(SUM(NSS_BYTE),0) NSS_BYTE FROM NNC_STAT_SEND WHERE NSS_DATE > (NOW() - interval 10 minute)";
	// 제품별 최대 전송량
	final String SELECT_MAX_TRAFFIC = "SELECT NCV_MAX_TRAFFIC FROM NNC_CONF_VERSION WHERE NCV_DIV=1";
	
	//MASTER/SLAVE 전송상태 QUEUE 상태
	final String SELECT_SEND_MONITOR = "SELECT NSMN_MASTER_OUT, NSMN_SLAVE_OUT, NSMN_QUEUE FROM NNC_SEND_MONITOR LIMIT 1 ";
	
	@Select(SELECT_CURRENTTIME)
	ArrayList<NcStatSys> getCurrenttime();
	
	@Select(SELECT_ACTIVE_STATE)
	NcStatNw getActiveState();
	
	@Select(SELECT_COLLECT_INFO)
	ArrayList<NcStatNw> collect_info();
	
	@Select(SELECT_SEND_INFO)
	ArrayList<NcStatNw> send_info();
	
	@Select(SELECT_SYS_MONTOR)
	NcStatNw getSysMonitor();
	
	@Select(SELECT_QUEUE_SIZE)
	NcStatNw getQueuesize();
	
	@Select(SELECT_OUT_STATUS)
	NcStatNw getOutStatus();
	
	@Select(SELECT_TOTAL_SENDBYTE)
	int getTotalSendbyte();
	
	@Select(SELECT_MAX_TRAFFIC)
	int getMaxTraffic();
	
	@Select(SELECT_SEND_MONITOR)
	NcStatMs getSendMonitor();
	
	final String PROTECTION_SYSTEM_LOAD =
			"SELECT NSM_CPU, NSM_RAM, NSM_DISK " +
			" FROM NNC_SYSTEM_MONITOR " +
			" WHERE 1=1 " +
			" AND NSM_DATETIME IN (SELECT MAX(NSM_DATETIME) FROM NNC_SYSTEM_MONITOR) " ;
		
	@Select(PROTECTION_SYSTEM_LOAD)
	@Results(value = {
		@Result(property = "cpuLoad", column = "NSM_CPU"),
		@Result(property = "ramLoad", column = "NSM_RAM"),
		@Result(property = "diskLoad", column = "NSM_DISK")
	})
	NcSysLoad protection_system_load();
}