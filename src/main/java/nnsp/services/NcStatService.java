package nnsp.services;

import java.util.ArrayList;
import java.util.HashMap;

import nnsp.mappers.NcStatMapper;
import nnsp.vo.NcLog;
import nnsp.vo.NcStatNw;
import nnsp.vo.NcStatSys;
import nnsp.vo.NcSysLoad;
import nnsp.vo.NcStatMs;
import nnsp.xmlmappers.NcStatXmlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class NcStatService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcStatService.class);
	@Autowired
	private NcStatMapper ncStatMapper;
	@Autowired
	private NcStatXmlMapper ncStatXmlMapper;
	
	// 시스템별 트래픽 테이블
	public ArrayList<NcStatSys> system_ct(String view_type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("view_type", view_type);
		return this.ncStatXmlMapper.system_ct(map);
	}
	
	// 시스템별 트래픽 테이블 상세
	public ArrayList<NcStatSys> system_detail(String group_id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", group_id);
		return this.ncStatXmlMapper.system_detail(map);
	}
	
	// 서비스 별 트래픽 추이차트
	public ArrayList<NcStatSys> progress_ct(String view_type, int protocol, int port) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("view_type", view_type);
		map.put("protocol", protocol);
		map.put("port", port);
		return this.ncStatXmlMapper.progress_ct(map);
	}
		
	// 서비스별 트래픽 테이블
	public ArrayList<NcStatSys> service_tb(String view_type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("view_type", view_type);
		return this.ncStatXmlMapper.service_tb(map);
	}
	
	// 서비스별 트래픽 테이블 상세
	public ArrayList<NcStatSys> service_detail(String port, String protocol, String view_type){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("view_type", view_type);
		map.put("protocol", protocol);
		map.put("port", port);
		return this.ncStatXmlMapper.service_detail(map);
	}
	
	// 추이차트 날짜 조회
	public ArrayList<NcStatSys> getCurrenttime() {
		return this.ncStatMapper.getCurrenttime();
	}
	
	// 네트워크 트래픽 연결 그래프
	public String[][] network_traffic_grp(String view_type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("view_type", view_type);
		ArrayList<NcStatSys> Traffic = ncStatXmlMapper.getNetworkTraffic(map);
		
		String[][] tmp1 = new String[Traffic.size()][5];
		
		for(int i=0; i < Traffic.size(); i++){
			tmp1[i][0]= Traffic.get(i).getNsn_src_ip();
			tmp1[i][1]= Traffic.get(i).getNsn_dst_ip();
			tmp1[i][2]= String.valueOf(Traffic.get(i).getNsn_packet_cnt());
		}
		
		String[][] tmp2 = network_traffic_sort(tmp1); // 배열 중복 정리
		
		return tmp2;
	}
	
	// 네트워크 트래픽 연결 그래프 데이터 정렬
	public String[][] network_traffic_sort(String[][] data){

		int cnt=0;
		for(int i=0; i < data.length-1; i++){
			if(data[i][0]==null) {
				cnt++;
				continue;
			}

			for(int j=i+1; j < data.length; j++){
				if(data[j][0] != null && (data[i][0].equals(data[j][0]) && data[i][1].equals(data[j][1]))){ // 출발지와 목적지가 모두 같다면
//						System.out.println("i==>"+i);
//						System.out.println("j==>"+j);
//						System.out.println("0==>"+data[i][0]+"_"+data[j][0]);
//						System.out.println("1==>"+data[i][1]+"_"+data[j][1]);
					data[i][2] = String.valueOf(Integer.parseInt(data[i][2])+Integer.parseInt(data[j][2])); // 패킷수 합하기
					data[j][0] = null;
				}
			}
		}
		if(data.length>0 && data[data.length-1][0]==null) cnt++; // i가 마지막까지 안가니까 cnt 체크 필요
		
		//System.out.println("cnt==>"+cnt);
		
		String[][] tmp1 = new String[data.length-cnt][5];
		int cnt2=0;
		for(int i=0; i < data.length; i++){
			//System.out.println("data[i]==>"+data[i][0]);
			if(data[i][0]!=null){
				tmp1[cnt2]=data[i];
				cnt2++;
			}
		}
		
		return tmp1;
	}
		
	// 접근 현황 차트
	public ArrayList<NcLog> login_ct(String sdate, String edate, String nsu_id, String nlm_result) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlm_create_sdate", sdate);
		map.put("nlm_create_edate", edate);
		map.put("nsu_id", nsu_id);
		map.put("nlm_result", nlm_result);
		return this.ncStatXmlMapper.login_ct(map);
	}
	// 접근 현황 테이블
	public ArrayList<NcLog> login_tb(String sdate, String edate, String nsu_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlm_create_sdate", sdate);
		map.put("nlm_create_edate", edate);
		map.put("nsu_id", nsu_id);
		return this.ncStatXmlMapper.login_tb(map);
	}
	
	// 운영환경 변경 차트
	public ArrayList<NcLog> config_ct(String sdate, String edate, String nsu_id, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlm_create_sdate", sdate);
		map.put("nlm_create_edate", edate);
		map.put("nsu_id", nsu_id);
		map.put("type", type);
		return this.ncStatXmlMapper.config_ct(map);
	}
	// 운영환경 변경 테이블
	public NcLog config_tb(String sdate, String edate, String nsu_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlm_create_sdate", sdate);
		map.put("nlm_create_edate", edate);
		map.put("nsu_id", nsu_id);
		return this.ncStatXmlMapper.config_tb(map);
	}
	
	// 정책 변경 차트
	public ArrayList<NcLog> policy_ct(String sdate, String edate, String nsu_id, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlp_create_sdate", sdate);
		map.put("nlp_create_edate", edate);
		map.put("nsu_id", nsu_id);
		map.put("type", type);
		return this.ncStatXmlMapper.policy_ct(map);
	}
	// 정책 변경 테이블
	public NcLog policy_tb(String sdate, String edate, String nsu_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlp_create_sdate", sdate);
		map.put("nlp_create_edate", edate);
		map.put("nsu_id", nsu_id);
		return this.ncStatXmlMapper.policy_tb(map);
	}
	
	// 데이터 수집량 테이블
	public ArrayList<NcStatNw> receive_tb(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsr_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsr_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.receive_tb(map);
	}
	
	// 데이터 수집량 차트
	public ArrayList<NcStatNw> receive_ct(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String nsr_pg_fname) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsr_pg_fname", nsr_pg_fname);
		map.put("nsr_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsr_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.receive_ct(map);
	}
		
	// 데이터 전송 손실 테이블
	public ArrayList<NcStatNw> repe_loss_tb(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsrp_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsrp_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.repe_loss_tb(map);
	}
	
	// 데이터 전송 손실 차트
	public ArrayList<NcStatNw> repe_loss_ct(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String nsrp_pg_fname) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsrp_pg_fname", nsrp_pg_fname);
		map.put("nsrp_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsrp_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.repe_loss_ct(map);
	}
		
	// 일방향 송신량 테이블
	public ArrayList<NcStatNw> send_tb(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nss_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nss_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.send_tb(map);
	}
	
	// 일방향 송신량 차트
	public ArrayList<NcStatNw> send_ct(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String nss_pg_fname) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nss_pg_fname", nss_pg_fname);
		map.put("nss_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nss_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.send_ct(map);
	}
	
	// 데이터 재전송 테이블
	public ArrayList<NcStatNw> repe_byte_tb(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsrp_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsrp_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.repe_byte_tb(map);
	}
	
	// 데이터 재전송 차트
	public ArrayList<NcStatNw> repe_byte_ct(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String nsrp_pg_fname) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nsrp_pg_fname", nsrp_pg_fname);
		map.put("nsrp_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nsrp_edate", edate+" "+ehour+":"+eminute+":59");
		return this.ncStatXmlMapper.repe_byte_ct(map);
	}
	
	// 추이차트 날짜 조회
	public NcStatNw getActiveState() {
		return this.ncStatMapper.getActiveState();
	}
	
	// 내부망 데이터 현재 수집량 
	public ArrayList<NcStatNw> collect_info() {
		return this.ncStatMapper.collect_info();
	}
	
	// 일방향 데이터 현재 송집량
	public ArrayList<NcStatNw> send_info() {
		return this.ncStatMapper.send_info();
	}
	
	// tx 시스템 상태 모니터
	public NcStatNw getSysMonitor() {
		return this.ncStatMapper.getSysMonitor();
	}
	
	// 시스템 동작현황 master, slave 큐 사이즈
	public NcStatNw getQueuesize() {
		return this.ncStatMapper.getQueuesize();
	}
	
	// 시스템 동작현황 master, slave 절체 상태
	public NcStatNw getOutStatus() {
		return this.ncStatMapper.getOutStatus();
	}
	
	// 시스템 동작현황 master, slave 절체 상태
	public int getTotalSendbyte() {
		return this.ncStatMapper.getTotalSendbyte();
	}
	
	// 제품별 최대 전송량
	public int getMaxTraffic() {
		return this.ncStatMapper.getMaxTraffic();
	}
	
	//MASTER/SLAVE 전송상태 QUEUE 상태
	public NcStatMs getSendMonitor() {
		return this.ncStatMapper.getSendMonitor();
	}
	
	// v3.0 실시간 로그
	public ArrayList<NcLog> realtime_log(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String tx_div) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nle_create_sdate", sdate+" "+shour+":"+sminute+":00");
		map.put("nle_create_edate", edate+" "+ehour+":"+eminute+":59");
		map.put("nle_div", tx_div);
		return this.ncStatXmlMapper.realtime_log(map);
	}
	
	// ChartData JSON 변환
	@SuppressWarnings("unused")
	public Object getChartDataJSON(ArrayList<NcStatNw> chartDataList){
		JSONArray rtnJArray = new JSONArray();
		
		JSONObject jsonArrObject = null;
		String nowDate = "";
		long packets = 0;
		ArrayList<String> whiteList = new ArrayList<String>();
		for(int i=0; i<chartDataList.size(); i++){
			NcStatNw chartData = chartDataList.get(i);
			
			if(!nowDate.equals(chartData.getPs_strc_time())){
				jsonArrObject = new JSONObject();
				jsonArrObject.put("dateTime", chartData.getPs_strc_time().substring(0,16));
				nowDate = chartData.getPs_strc_time();
			}

			jsonArrObject.put(chartData.getWl_name(), chartData.getPs_packets());
			if(whiteList.size()>0) {
				boolean bNewProto = true;
				for(int j=0; j < whiteList.size();j++) { 
					if(whiteList.get(j).equals(chartData.getWl_name())) {
						bNewProto=false;
						break;
					}
				}
				if(bNewProto) {
					whiteList.add(chartData.getWl_name());
				}
			}
			else {
				whiteList.add(chartData.getWl_name());
			}
			
			
			if(i == chartDataList.size()-1 || !nowDate.equals(chartDataList.get(i+1).getPs_strc_time())){
				rtnJArray.add(jsonArrObject);//Chart Data 한줄 입력
			}
		}
		JSONObject rtnJObject = new JSONObject();
		rtnJObject.put("whiteList", whiteList);
		rtnJObject.put("chartData", rtnJArray);
		return rtnJObject;
	}
	
	// ChartData JSON 변환
	@SuppressWarnings("unused")
	public Object getThreatChartDataJSON(ArrayList<NcStatNw> chartDataList){
		JSONArray rtnJArray = new JSONArray();
		
		JSONObject jsonArrObject = null;
		String nowDate = "";
		long packets = 0;
		ArrayList<String> protoList = new ArrayList<String>();
		for(int i=0; i<chartDataList.size(); i++){
			NcStatNw chartData = chartDataList.get(i);
			
			if(!nowDate.equals(chartData.getPs_strc_time())){
				jsonArrObject = new JSONObject();
				jsonArrObject.put("dateTime", chartData.getPs_strc_time().substring(0,16));
				nowDate = chartData.getPs_strc_time();
			}
			
			jsonArrObject.put(chartData.getStrPsDetect(), chartData.getPs_packets());
			if(protoList.size()>0) {
				boolean bNewProto = true;
				for(int j=0; j < protoList.size();j++) { 
					if(protoList.get(j).equals(chartData.getStrPsDetect())) {
						bNewProto=false;
						break;
					}
				}
				if(bNewProto) {
					protoList.add(chartData.getStrPsDetect());
				}
			}
			else {
				protoList.add(chartData.getStrPsDetect());
			}
			
			
			if(i == chartDataList.size()-1 || !nowDate.equals(chartDataList.get(i+1).getPs_strc_time())){
				rtnJArray.add(jsonArrObject);//Chart Data 한줄 입력
			}
		}
		JSONObject rtnJObject = new JSONObject();
		rtnJObject.put("protoList", protoList);
		rtnJObject.put("chartData", rtnJArray);
		return rtnJObject;
	}
		
	//자체 시험 로그
	public ArrayList<NcLog> prot_log(
		String sdate, String edate, String tx_div, int limit
	) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nlpt_create_sdate", sdate+" "+"00"+":"+"00"+":00");
		map.put("nlpt_create_edate", edate+" "+"23"+":"+"59"+":59");
		map.put("nlpt_div", tx_div);
		map.put("limit", limit);

		return this.ncStatXmlMapper.prot_log(map);
	}
	
	// 송신측 자체 시험 결과 중 CPU, RAM, Disk 사용량 정보
	public NcSysLoad protection_system_load() {
		return ncStatMapper.protection_system_load();
	}
}