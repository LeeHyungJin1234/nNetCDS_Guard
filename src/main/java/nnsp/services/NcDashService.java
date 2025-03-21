package nnsp.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import nnsp.mappers.NcDashMapper;
import nnsp.vo.NcConfig;
import nnsp.vo.NcLog;
import nnsp.vo.NcStatNw;
import nnsp.xmlmappers.NcStatXmlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcDashService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcDashService.class);
	@Autowired
	private NcDashMapper ncDashMapper;
	@Autowired
	private NcStatXmlMapper ncStatXmlMapper;
	
	/**
	 * 용량 표시
	 * @param num
	 * @return
	 */
	public String sizeCalculation(long size) {
	    String CalcuSize = null;
	    int i = 0;

	    double calcu = (double) size;
	    while (calcu >= 1024 && i < 5) { // 단위 숫자로 나누고 한번 나눌 때마다 i 증가
	        calcu = calcu / 1024;
	        i++;
	    }
	    DecimalFormat df = new DecimalFormat("##0.0");
	    switch (i) {
	        case 0:
	            CalcuSize = df.format(calcu) + "Byte";
	            break;
	        case 1:
	            CalcuSize = df.format(calcu) + "KB";
	            break;
	        case 2:
	            CalcuSize = df.format(calcu) + "MB";
	            break;
	        case 3:
	            CalcuSize = df.format(calcu) + "GB";
	            break;
	        case 4:
	            CalcuSize = df.format(calcu) + "TB";
	            break;
	        default:
	            CalcuSize="ZZ"; //용량표시 불가

	    }
	    return CalcuSize;
	}
	
	// 대시보드 이벤트 로그
	public ArrayList<NcLog> getDashEventList(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String nle_div) {
		String nle_create_sdate = sdate+" "+shour+":"+sminute;
		String nle_create_edate = edate+" "+ehour+":"+eminute;
		return this.ncDashMapper.getDashEventList(nle_create_sdate, nle_create_edate, nle_div);
	}
	
	// 대시보드 이벤트 로그
	public ArrayList<NcLog> getDashEventList2(String sdate, String edate, String shour, String ehour, String sminute, String eminute, String tx_div, String rx_div) {
		String nle_create_sdate = sdate+" "+shour+":"+sminute;
		String nle_create_edate = edate+" "+ehour+":"+eminute;
		return this.ncDashMapper.getDashEventList2(nle_create_sdate, nle_create_edate, tx_div, rx_div);
	}
		
	// 대시보드용 차트 데이터 송신
	public ArrayList<NcStatNw> chart_data_send(String sdate, String edate, String shour, String ehour, String sminute,
			String eminute, String ssecond, String esecond, String nss_pg_fname) {
		String nss_sdate = sdate+" "+shour+":"+sminute+":"+ssecond;
		String nss_edate = edate+" "+ehour+":"+eminute+":"+esecond;
		
		return this.ncDashMapper.send_ct_dash(nss_pg_fname, nss_sdate, nss_edate);
	}
	// 대시보드용 차트 데이터 수신
	public ArrayList<NcStatNw> chart_data_recv(String sdate, String edate, String shour, String ehour, String sminute,
			String eminute, String ssecond, String esecond, String nsr_pg_fname) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":"+ssecond;
		String nsr_edate = edate+" "+ehour+":"+eminute+":"+esecond;
		return this.ncDashMapper.receive_ct_dash(nsr_pg_fname, nsr_sdate, nsr_edate);
	}
	// 대시보드용 차트 데이터 재전송
	public ArrayList<NcStatNw> chart_data_repe(String sdate, String edate, String shour, String ehour, String sminute,
			String eminute, String ssecond, String esecond, String nsrp_pg_fname) {
		String nsrp_sdate = sdate+" "+shour+":"+sminute+":"+ssecond;
		String nsrp_edate = edate+" "+ehour+":"+eminute+":"+esecond;
		return this.ncDashMapper.repeat_ct_dash(nsrp_pg_fname, nsrp_sdate, nsrp_edate);
	}
	
	// 서비스별 전송량 송신
	public long nss_byte_sum(String sdate, String edate, String shour, String ehour, String sminute,
			String eminute, String ssecond, String esecond, int nss_npl_no) {
		String nss_sdate = sdate+" "+shour+":"+sminute+":"+ssecond;
		String nss_edate = edate+" "+ehour+":"+eminute+":"+esecond;
		
		return this.ncDashMapper.nss_byte_sum(nss_npl_no, nss_sdate, nss_edate);
	}
	// 서비스별 전송량 수신
	public long nsr_byte_sum(String sdate, String edate, String shour, String ehour, String sminute,
			String eminute, String ssecond, String esecond, int nsr_npl_no) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":"+ssecond;
		String nsr_edate = edate+" "+ehour+":"+eminute+":"+esecond;
		
		return this.ncDashMapper.nsr_byte_sum(nsr_npl_no, nsr_sdate, nsr_edate);
	}
	
	// 대시보드용 차트 데이터 송신
	public ArrayList<NcStatNw> chart_data_send_in(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nss_sdate = sdate+" "+shour+":"+sminute+":00";
		String nss_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.send_ct_traffic_in(nss_sdate, nss_edate);
	}
	// 대시보드용 차트 데이터 수신
	public ArrayList<NcStatNw> chart_data_recv_in(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsr_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.receive_ct_traffic_in(nsr_sdate, nsr_edate);
	}
	// 대시보드용 차트 데이터 수신
	public ArrayList<NcStatNw> detect_dash_in(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsr_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.detect_dash_in(nsr_sdate, nsr_edate);
	}
	// 대시보드용 차트 데이터 송신
	public ArrayList<NcStatNw> chart_data_send_out(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nss_sdate = sdate+" "+shour+":"+sminute+":00";
		String nss_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.send_ct_traffic_out(nss_sdate, nss_edate);
	}
	// 대시보드용 차트 데이터 수신
	public ArrayList<NcStatNw> chart_data_recv_out(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsr_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.receive_ct_traffic_out(nsr_sdate, nsr_edate);
	}
	
	// 대시보드용 차트 데이터 수신
	public ArrayList<NcStatNw> detect_dash_out(String sdate, String edate, String shour, String ehour, String sminute, String eminute) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsr_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.detect_dash_out(nsr_sdate, nsr_edate);
	}
	// 대시보드용 차트 데이터 재전송/손실
	public ArrayList<NcStatNw> chart_data_svc_in(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int in_out) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String svc_sdate = sdate+" "+shour+":"+sminute+":00";
		String svc_edate = edate+" "+ehour+":"+eminute+":59";
		
		if(svc_sdate!=null&&!svc_sdate.equals("")){map.put("svc_sdate", svc_sdate);}
		if(svc_edate!=null&&!svc_edate.equals("")){map.put("svc_edate", svc_edate);}
		if(in_out >= 0){map.put("in_out", in_out);}
		
		return this.ncStatXmlMapper.service_ct_traffic_in(map);
	}
	// 대시보드용 차트 데이터 재전송/손실
	public ArrayList<NcStatNw> chart_data_svc_out(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int in_out) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String svc_sdate = sdate+" "+shour+":"+sminute+":00";
		String svc_edate = edate+" "+ehour+":"+eminute+":59";
		
		if(svc_sdate!=null&&!svc_sdate.equals("")){map.put("svc_sdate", svc_sdate);}
		if(svc_edate!=null&&!svc_edate.equals("")){map.put("svc_edate", svc_edate);}
		if(in_out >= 0){map.put("in_out", in_out);}
		
		return this.ncStatXmlMapper.service_ct_traffic_out(map);
	}
	// 대시보드용 차트 데이터 재전송/손실
	public ArrayList<NcStatNw> chart_data_repe(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int tx_div) {
		String nsrp_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsrp_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.repeate_ct_traffic(tx_div, nsrp_sdate, nsrp_edate);
	}
	// tx 바이트 정보
	public long tx_byte_info(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int tx_div) {
		String nss_sdate = sdate+" "+shour+":"+sminute+":00";
		String nss_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.tx_byte_info(tx_div, nss_sdate, nss_edate);
	}
	// rx 바이트 정보
	public long rx_byte_info(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int rx_div) {
		String nsr_sdate = sdate+" "+shour+":"+sminute+":00";
		String nsr_edate = edate+" "+ehour+":"+eminute+":59";
		return this.ncDashMapper.rx_byte_info(rx_div, nsr_sdate, nsr_edate);
	}
	// master/slave 절체 정보
	public NcConfig arrow_info(int tx_div) {
		return this.ncDashMapper.arrow_info(tx_div);
	}
	
	// tx 서비스 갯수
	public int tx_svc_cnt(int txDiv) {
		return this.ncDashMapper.tx_svc_cnt(txDiv);
	}
	
	// 대시보드용 차트 데이터 centerChart
	public NcStatNw getCenterChartData(String sdate, String edate, String shour, String ehour, String sminute, String eminute, long wl_id, int ps_inout, int ps_detect) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ccd_sdate = sdate+" "+shour+":"+sminute+":00";
		String ccd_edate = edate+" "+ehour+":"+eminute+":59";
		
		if(ccd_sdate!=null&&!ccd_sdate.equals("")){map.put("ccd_sdate", ccd_sdate);}
		if(ccd_edate!=null&&!ccd_edate.equals("")){map.put("ccd_edate", ccd_edate);}
		if(wl_id > 0){map.put("wl_id", wl_id);}
		if(ps_inout == 0 || ps_inout == 1){map.put("ps_inout", ps_inout);}
		if(ps_detect == 1){map.put("ps_detect", ps_detect);}
		
		return this.ncStatXmlMapper.getCenterChartData(map);
	}
	
	public ArrayList<NcStatNw> getThreatChartData(String sdate, String edate, String shour, String ehour, String sminute, String eminute, int ps_inout) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String tcd_sdate = sdate+" "+shour+":"+sminute+":00";
		String tcd_edate = edate+" "+ehour+":"+eminute+":59";
		
		if(tcd_sdate!=null&&!tcd_sdate.equals("")){map.put("tcd_sdate", tcd_sdate);}
		if(tcd_edate!=null&&!tcd_edate.equals("")){map.put("tcd_edate", tcd_edate);}
		if(ps_inout >= 0){map.put("ps_inout", ps_inout);}
		
		return this.ncStatXmlMapper.getThreatChartData(map);
	}
	
}