package nnsp.xmlmappers;

import java.util.ArrayList;
import java.util.Map;

import nnsp.vo.NcLog;
import nnsp.vo.NcStatNw;
import nnsp.vo.NcStatSys;

public interface NcStatXmlMapper {
	
	// 시스템별 트래픽 차트
	ArrayList<NcStatSys> system_ct(Map<String, Object> param);
	
	// 시스템별 트래픽 테이블상세
	ArrayList<NcStatSys> system_detail(Map<String, Object> param);
	
	// 서비스별 트래픽 차트
	ArrayList<NcStatSys> service_tb(Map<String, Object> param);
	
	// 서비스별 트래픽 테이블상세
	ArrayList<NcStatSys> service_detail(Map<String, Object> param);
	
	// 서비스별 트래픽 추이차트
	ArrayList<NcStatSys> progress_ct(Map<String, Object> param);
	
	// 트래픽 연결선 차트 로그 검색
	ArrayList<NcStatSys> getNetworkTraffic(Map<String, Object> param);
	
	// 접근 현황 차트
	ArrayList<NcLog> login_ct(Map<String, Object> param);
	
	// 접근 현황 테이블
	ArrayList<NcLog> login_tb(Map<String, Object> param);
	
	// 운영환경 변경 차트
	ArrayList<NcLog> config_ct(Map<String, Object> param);
	
	// 운영환경 변경 테이블
	NcLog config_tb(Map<String, Object> param);
	
	// 정책 변경 차트
	ArrayList<NcLog> policy_ct(Map<String, Object> param);
	
	// 정책 변경 테이블
	NcLog policy_tb(Map<String, Object> param);
	
	// 데이터 수집량 차트
	ArrayList<NcStatNw> receive_ct(Map<String, Object> param);
	
	// 데이터 수집량 테이블
	ArrayList<NcStatNw> receive_tb(Map<String, Object> param);
	
	// 데이터 전송 손실 테이블
	ArrayList<NcStatNw> repe_loss_ct(Map<String, Object> param);
	
	// 데이터 전송 손실 테이블
	ArrayList<NcStatNw> repe_loss_tb(Map<String, Object> param);
	
	// 일방향 송신량 테이블
	ArrayList<NcStatNw> send_ct(Map<String, Object> param);
	
	// 일방향 송신량 테이블
	ArrayList<NcStatNw> send_tb(Map<String, Object> param);
	
	// 데이터 재전송 테이블
	ArrayList<NcStatNw> repe_byte_ct(Map<String, Object> param);
	
	// 데이터 재전송 테이블
	ArrayList<NcStatNw> repe_byte_tb(Map<String, Object> param);

	// v3.0 실시간 로그
	ArrayList<NcLog> realtime_log(Map<String, Object> param);
	// 실시간 통신현황
	ArrayList<NcLog> realtime_analyze(Map<String, Object> param);
	
	ArrayList<NcStatNw> service_ct_traffic_in(Map<String, Object> param);
	ArrayList<NcStatNw> service_ct_traffic_out(Map<String, Object> param);
	NcStatNw getCenterChartData(Map<String, Object> param);
	ArrayList<NcStatNw> getThreatChartData(Map<String, Object> param);
	
	//자체 시험 로그
	ArrayList<NcLog> prot_log(Map<String, Object> param);
}