package nnsp.services;

import java.util.ArrayList;
import java.util.HashMap;

import nnsp.common.Config;
import nnsp.mappers.NdrAnalyzeMapper;
import nnsp.vo.NdrPs;
import nnsp.xmlmappers.NdrAnalyzeXmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Component
public class NdrAnalyzeService {
	//private static final Logger log = LoggerFactory.getLogger(PacketSummaryScheduler.class);

	@Autowired
	private NcCommonService ncCommonService;
	@Autowired
	private NdrAnalyzeMapper ndrAnalyzeMapper;
	@Autowired
	private NdrAnalyzeXmlMapper ndrAnalyzeXmlMapper; // 동적 SQL은 XMLMAPPER로 한다.
	
	

	// 조건에 따른 protocol 종류  //검색용
	public ArrayList<NdrPs> getIcsProtocolList(NdrPs ndrPs) {
		
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");

		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		String ps_proto_ics = ndrPs.getPs_proto_ics();
		String ps_proto_ics_type = ndrPs.getPs_proto_ics_type();
		String ps_proto_ics_cmd_type = ndrPs.getPs_proto_ics_cmd_type();
		
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만

		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		
		if(ps_proto_ics!=null && !ps_proto_ics.equals("")) {map.put("ps_proto_ics", ps_proto_ics);}
		if(ps_proto_ics_type!=null && !ps_proto_ics_type.equals("")) {map.put("ps_proto_ics_type", ps_proto_ics_type);}
		if(ps_proto_ics_cmd_type!=null && !ps_proto_ics_cmd_type.equals("")) {map.put("ps_proto_ics_cmd_type", ps_proto_ics_cmd_type);}

		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return new ArrayList<NdrPs>();
		}
		return this.ndrAnalyzeXmlMapper.getIcsProtocolList(map);
	}
	
	
	// 조건에 따른 ICS 패킷 목록
	public ArrayList<NdrPs> getICSPacketList(NdrPs ndrPs){
		return this.getICSPacketList(0, 0, ndrPs);
	}
	public ArrayList<NdrPs> getICSPacketList(int nStart, int list_num, NdrPs ndrPs) {
		
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");

		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만

		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		if(ndrPs.getSearchPs_inout()!=null && !ndrPs.getSearchPs_inout().equals("") ) map.put("ps_inout", ndrPs.getSearchPs_inout());
		
		if(ndrPs.getRowNumber() > 0) map.put("rowNumber", ndrPs.getRowNumber());

		//  페이징 처리
		if(nStart>0 && list_num > 0) {
			map.put("page", (nStart-1)*list_num);
			map.put("rowsPerPage", list_num);
		}
		else if(ndrPs.getCnt()>0){//  가져올 수
				map.put("cnt", ndrPs.getCnt());
		}
		else if(ndrPs.getCnt()==0){ // 한번에 불러올 수
			map.put("cnt",Config.getInstance().getPerPage());
		}
		
		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return new ArrayList<NdrPs>();
		}
		
		return this.ndrAnalyzeXmlMapper.getICSPacketList(map);
	}
	// 조건에 따른 ICS 패킷 수
	public int getICSPacketCnt(NdrPs ndrPs) {
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");

		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만

		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
						
		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return 0;
		}
		return this.ndrAnalyzeXmlMapper.getICSPacketCnt(map);
	}
	
	// ICS_시그니쳐 통신 목록
	public ArrayList<NdrPs> getPWRList(NdrPs ndrPs) {
		
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");

		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		long ps_id = ndrPs.getPs_id();
		
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		String ps_white_rule = this.getPWRStr(ps_id, dbName, tblName, tblDate); //   ICS 시그니처
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만

		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		
		map.put("ps_white_rule", ps_white_rule);

		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return new ArrayList<NdrPs>();
		}
		return this.ndrAnalyzeXmlMapper.getPWRList(map);
	}
	public ArrayList<NdrPs> getWhiteRulePacketList(NdrPs ndrPs) {
		
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");

		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		long ps_id = ndrPs.getPs_id();
		
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		String ps_white_rule = this.getPWRStr(ps_id, dbName, tblName, tblDate); //   ICS 시그니처
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만

		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		
		map.put("ps_white_rule", ps_white_rule);

		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return new ArrayList<NdrPs>();
		}
		return this.ndrAnalyzeXmlMapper.getWhiteRulePacketList(map);
	}
	//  ps_id 로 시그니쳐 가져오기
	private String getPWRStr(long ps_id, String dbName, String tblName, int tblDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만
		map.put("ps_id", ps_id);//  ps_id
		
		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return null;
		}
		return this.ndrAnalyzeXmlMapper.getPWRStr(map);
	}
	
	public NdrPs getPsById(String strPs_time, String ps_id) {
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");
		
		String tblName = null;
		int tblTimeLenth = Integer.parseInt(config.getProperty("tbl.packet.statistics.no"));
		int tblDate = 0;
		if(strPs_time!=null && strPs_time.length() >= tblTimeLenth) {
			tblDate = Integer.parseInt(nnsp.util.StringUtil.getStrDate(strPs_time).substring( 0, tblTimeLenth));
			tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		}
		if(this.ncCommonService.getTableCheck(dbName, tblName)>0){
			return this.ndrAnalyzeMapper.getPsById(tblDate, Long.parseLong(ps_id));
		}
		else {
			return null;
		}
	}
	
	
}