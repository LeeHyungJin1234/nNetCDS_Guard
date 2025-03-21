package nnsp.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nnsp.services.NcMenuService;
import nnsp.services.NcDashService;
import nnsp.services.NcConfigService;
import nnsp.vo.NcLog;
import nnsp.vo.NcStatNw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NcDashController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcDashController.class);
	
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcDashService ncDashService;
	
	
	@SuppressWarnings("unused")
	private String[] colors = new String []{"#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7","#828C38","#C5DE00","#874340","#DBB0A0","#0D477D","#8EBBDD","#A87000","#D8C679","#515151"};
	
	/**
	 * 대시보드
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_sndrcv.do")
	public String dashboard(NcStatNw ncStatNw, Locale locale, Model model,
			@RequestParam(value="tx_div", required=false) String tx_div,
			@RequestParam(value="rx_div", required=false) String rx_div) {
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(100002));
		model.addAttribute("title", ncMenuService.getNowTitle(100002));
		model.addAttribute("menu_id", 100002);
		// 메뉴 만들기 끝

		model.addAttribute("ncStatNw", ncStatNw);
		
		String txDiv = "1";
		String rxDiv = "2";
		if(tx_div!=null && nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv = tx_div;
		}
		if(rx_div!=null && nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv = rx_div;
		}
		model.addAttribute("tx_div", txDiv);
		model.addAttribute("rx_div", rxDiv);
		
		String txTxt = "내";
		String rxTxt = "외";
		if(tx_div!=null && tx_div.equals("3")) {
			txTxt = "외";
			rxTxt = "내";
		}
		model.addAttribute("txTxt", txTxt);
		model.addAttribute("rxTxt", rxTxt);		
				
		return "stat/stat_sndrcv";
	}
	
	/**
	 * 대시보드 이벤트 로그
	 * @return retVal
	 * @throws ParseException 
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/initDashLog.do", method = RequestMethod.POST)
	@ResponseBody
	public Object initDashLog(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, @RequestParam("tx_div") String tx_div, @RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {
		
		Map<String, Object> retVal = new HashMap<String, Object>();

		String tb_head = "<table><colgroup><col width='150' /><col width='80' /><col width='200' /><col width='' /></colgroup><tbody>";
		String tb_foot = "</tbody></table>";
		String tb_nodata = "<tr><td width='100%'>조회된 데이터가 없습니다.</td></tr>";
		
		String txEventLogTxt = tb_head;//tx 이벤트 로그 내용
		String rxEventLogTxt = tb_head;//tx 이벤트 로그 내용
		
		int txcnt = 0;
		int rxcnt = 0;
		
		ArrayList<NcLog> eventLog = ncDashService.getDashEventList2(sdate, edate, shour, ehour, sminute, eminute, tx_div, rx_div); // 이벤트 로그 목록
		if(eventLog!=null && eventLog.size() > 0){
			for(int i=0; i<eventLog.size(); i++){
				NcLog ncLog = (NcLog)eventLog.get(i);
				
				if("1".equals(ncLog.getNcp_div()) || "3".equals(ncLog.getNcp_div())){
					txEventLogTxt = txEventLogTxt + "<tr>";
					
					txEventLogTxt = txEventLogTxt 
						+"<td>"+ncLog.getNle_create_date()+"</td>"
						+"<td>"+ncLog.getNle_risk_level()+"</td>"
						+"<td class='tLeft'>"+ncLog.getNcp_name()+"</td>"
						+"<td class='tLeft'>"+ncLog.getNle_message()+"</td>";
					
					txEventLogTxt = txEventLogTxt + "</tr>";
					txcnt++;
				}else{
					rxEventLogTxt = rxEventLogTxt + "<tr>";
					
					rxEventLogTxt = rxEventLogTxt 
						+"<td>"+ncLog.getNle_create_date()+"</td>"
						+"<td>"+ncLog.getNle_risk_level()+"</td>"
						+"<td class='tLeft'>"+ncLog.getNcp_name()+"</td>"
						+"<td class='tLeft'>"+ncLog.getNle_message()+"</td>";
					
					rxEventLogTxt = rxEventLogTxt + "</tr>";
					rxcnt++;
				}
			}
			
			if(txcnt<1) txEventLogTxt = txEventLogTxt + tb_nodata;
			if(rxcnt<1) rxEventLogTxt = rxEventLogTxt + tb_nodata;
			
		}else{
			txEventLogTxt = txEventLogTxt + tb_nodata; 
			rxEventLogTxt = rxEventLogTxt + tb_nodata; 
		}
		
		txEventLogTxt = txEventLogTxt + tb_foot;
		rxEventLogTxt = rxEventLogTxt + tb_foot;
		
		//마무리
		retVal.put("txEventLogTxt", txEventLogTxt);
		retVal.put("rxEventLogTxt", rxEventLogTxt);

		retVal.put("code", "OK");
	    
		return retVal;
	}
	
	/**
	 * 대시보드 차트 데이터  10초단위로 차트 데이터를 가져오기
	 * @return retVal
	 * @throws ParseException 
	 */
/*	@RequestMapping(value = "/initChart.do", method = RequestMethod.POST)
	@ResponseBody
	public Object initChart(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("ssecond") String ssecond, @RequestParam("esecond") String esecond, @RequestParam("tx_div") String tx_div,
			@RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {

		*//*if(nnsp.util.StringUtil.checkNumber(ssecond)){
			ssecond = Integer.toString((Integer.parseInt(ssecond)/10)*10);
		}
		else{
			ssecond = "00";
		}
		if(nnsp.util.StringUtil.checkNumber(esecond)){
			esecond = Integer.toString(((Integer.parseInt(esecond)/10)*10)+9);
		}
		else{
			esecond = "59";
		}*//*
		ssecond = "00";
		esecond = "59";
	
		Map<String, Object> retVal = new HashMap<String, Object>();

		int txDiv = 1;
		int rxDiv = 2;
		if(nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv=Integer.parseInt(tx_div);
		}
		if(nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv=Integer.parseInt(rx_div);
		}
		
		ArrayList<NdConfig> ndConfigTx = ncConfigService.program_list(1,txDiv);
		ArrayList<NdConfig> ndConfigRx = ncConfigService.program_list(1,rxDiv);
			
		//테이블 정보 가져오기 시작
		String tb_head = "<table class='data_table'><tbody>";
		String tb_foot = "</tbody></table>";
		String tb_nodata = "<tr><td>조회된 데이터가 없습니다.</td></tr>";
				
		int maxTxRSumCnt = 0;
		long maxTxRSum = 0;
		int maxTxSSumCnt = 0;
		long maxTxSSum = 0;

		String txRecvTbCont = tb_head;//tx r 데이터 정보 테이블 내용
		String txSendTbCont = tb_head;//tx s 데이터 정보 테이블 내용
		if(ndConfigTx!=null && ndConfigTx.size() > 0){
			for(int i=0; i<ndConfigTx.size(); i++){
				NdConfig ndConfig = (NdConfig)ndConfigTx.get(i);
				
				long txRSum = ncDashService.nsr_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
				if(maxTxRSum < txRSum){
					maxTxRSum = txRSum;
					maxTxRSumCnt = i;
				}
				txRecvTbCont = txRecvTbCont + "<tr>";
				txRecvTbCont = txRecvTbCont 
					+"<td class='toggle-graphTxRecv title' data-graph-index='"+i+"'>"
						+"<div id='txRecvLine"+i+"' class='serviceName' style='background-color:"+colors[(i%10)]+";'>&nbsp;</div>"
						+ndConfig.getNcp_name()
					+"</td>"
					+"<td class='number'>"+ncDashService.sizeCalculation(txRSum)+"</td><td>&nbsp;</td>";
				txRecvTbCont = txRecvTbCont + "</tr>";
				
				long txSSum = ncDashService.nss_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
				if(maxTxSSum < txSSum){
					maxTxSSum = txSSum;
					maxTxSSumCnt = i;
				}
				txSendTbCont = txSendTbCont + "<tr>";
				txSendTbCont = txSendTbCont 
					+"<td class='toggle-graphTxSend title' data-graph-index='"+i+"'>"
						+"<div id='txSendLine"+i+"' class='serviceName' style='background-color:"+colors[(i%10)]+";'>&nbsp;</div>"
						+ndConfig.getNcp_name()
					+"</td>"
					+"<td class='number'>"+ncDashService.sizeCalculation(txSSum)+"</td><td>&nbsp;</td>";
				txSendTbCont = txSendTbCont + "</tr>";
			}
		}
		else {
			txRecvTbCont = txRecvTbCont + tb_nodata;
			txSendTbCont = txSendTbCont + tb_nodata;
		}
		txRecvTbCont = txRecvTbCont + tb_foot;
		txSendTbCont = txSendTbCont + tb_foot;
		
		int maxRxRSumCnt = 0;
		long maxRxRSum = 0;
		int maxRxSSumCnt = 0;
		long maxRxSSum = 0;
		
		String rxRecvTbCont = tb_head;//rx r 데이터 정보 테이블 내용
		String rxSendTbCont = tb_head;//rx s 데이터 정보 테이블 내용
		if(ndConfigRx!=null && ndConfigRx.size() > 0){
			for(int i=0; i<ndConfigRx.size(); i++){
				NdConfig ndConfig = (NdConfig)ndConfigRx.get(i);
				
				long rxRSum = ncDashService.nsr_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
				if(maxRxRSum < rxRSum){
					maxRxRSum = rxRSum;
					maxRxRSumCnt = i;
				}
				rxRecvTbCont = rxRecvTbCont + "<tr>";
				rxRecvTbCont = rxRecvTbCont 
					+"<td class='toggle-graphRxRecv title' data-graph-index='"+i+"'>"
						+"<div id='rxRecvLine"+i+"' class='serviceName' style='background-color:"+colors[(i%10)]+";'>&nbsp;</div>"
						+ndConfig.getNcp_name()
					+"</td>"
					+"<td class='number'>"+ncDashService.sizeCalculation(rxRSum)+"</td><td>&nbsp;</td>";
				rxRecvTbCont = rxRecvTbCont + "</tr>";

				long rxSSum = ncDashService.nss_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
				if(maxRxSSum < rxSSum){
					maxRxSSum = rxSSum;
					maxRxSSumCnt = i;
				}
				rxSendTbCont = rxSendTbCont + "<tr>";				
				rxSendTbCont = rxSendTbCont 
					+"<td class='toggle-graphRxSend title' data-graph-index='"+i+"'>"
						+"<div id='rxSendLine"+i+"' class='serviceName' style='background-color:"+colors[(i%10)]+";'>&nbsp;</div>"
						+ndConfig.getNcp_name()
					+"</td>"
					+"<td class='number'>"+ncDashService.sizeCalculation(rxSSum)+"</td><td>&nbsp;</td>";
				rxSendTbCont = rxSendTbCont + "</tr>";
			}
		}
		else {
			rxRecvTbCont = rxRecvTbCont + tb_nodata; 
			rxSendTbCont = rxSendTbCont + tb_nodata; 
		}
		rxRecvTbCont = rxRecvTbCont + tb_foot;
		rxSendTbCont = rxSendTbCont + tb_foot;
		//테이블 정보 가져오기 끝
		
		//그래프 설정 정보 생성 시작
		JSONArray txRecvGraphs = new JSONArray();
		JSONArray txSendGraphs = new JSONArray();
		if(ndConfigTx.size() > 0){
			for(int i=0; i<ndConfigTx.size(); i++){
				NdConfig ndConfig = (NdConfig)ndConfigTx.get(i);
				
				JSONObject txRecvGraph = new JSONObject();
				txRecvGraph.put("id", "txRG"+i);
				txRecvGraph.put("balloonText", "[[title]]:[[value]] byte");
				txRecvGraph.put("lineColor", colors[i]);
				txRecvGraph.put("lineThickness", 2);
				txRecvGraph.put("bullet", "none");
				txRecvGraph.put("title", ndConfig.getNcp_name());
				txRecvGraph.put("valueField", ndConfig.getNcp_file_name());
				txRecvGraph.put("useLineColorForBulletBorder", true);
				txRecvGraphs.add(txRecvGraph);
				
				JSONObject txSendGraph = new JSONObject();
				txSendGraph.put("id", "txSG"+i);
				txSendGraph.put("balloonText", "[[title]]:[[value]] byte");
				txSendGraph.put("lineColor", colors[i]);
				txSendGraph.put("lineThickness", 2);
				txSendGraph.put("bullet", "none");
				txSendGraph.put("title", ndConfig.getNcp_name());
				txSendGraph.put("valueField", ndConfig.getNcp_file_name());
				txSendGraph.put("useLineColorForBulletBorder", true);
				txSendGraphs.add(txSendGraph);
			}
		}
		else{
			JSONObject txRecvGraph = new JSONObject();
			txRecvGraph.put("id", "txRG0");
			txRecvGraph.put("balloonText", "[[title]]:[[value]] byte");
			txRecvGraph.put("lineColor", colors[0]);
			txRecvGraph.put("lineThickness", 2);
			txRecvGraph.put("bullet", "none");
			txRecvGraph.put("title", "조회된 데이터가 없습니다.");
			txRecvGraph.put("valueField", "nodataTxR0");
			txRecvGraph.put("useLineColorForBulletBorder", true);
			txRecvGraphs.add(txRecvGraph);
			
			JSONObject txSendGraph = new JSONObject();
			txSendGraph.put("id", "txSG0");
			txSendGraph.put("balloonText", "[[title]]:[[value]] byte");
			txSendGraph.put("lineColor", colors[0]);
			txSendGraph.put("lineThickness", 2);
			txSendGraph.put("bullet", "none");
			txSendGraph.put("title", "조회된 데이터가 없습니다.");
			txSendGraph.put("valueField", "nodataTxS0");
			txSendGraph.put("useLineColorForBulletBorder", true);
			txSendGraphs.add(txSendGraph);
		}

		JSONArray rxRecvGraphs = new JSONArray();
		JSONArray rxSendGraphs = new JSONArray();
		if(ndConfigRx.size() > 0){
			for(int i=0; i<ndConfigRx.size(); i++){
				NdConfig ndConfig = (NdConfig)ndConfigRx.get(i);
				
				JSONObject rxRecvGraph = new JSONObject();
				rxRecvGraph.put("id", "rxRG"+i);
				rxRecvGraph.put("balloonText", "[[title]]:[[value]] byte");
				rxRecvGraph.put("lineColor", colors[i]);
				rxRecvGraph.put("lineThickness", 2);
				rxRecvGraph.put("bullet", "none");
				rxRecvGraph.put("title", ndConfig.getNcp_name());
				rxRecvGraph.put("valueField", ndConfig.getNcp_file_name());
				rxRecvGraph.put("useLineColorForBulletBorder", true);
				rxRecvGraphs.add(rxRecvGraph);
	
				JSONObject rxSendGraph = new JSONObject();
				rxSendGraph.put("id", "rxSG"+i);
				rxSendGraph.put("balloonText", "[[title]]:[[value]] byte");
				rxSendGraph.put("lineColor", colors[i]);
				rxSendGraph.put("lineThickness", 2);
				rxSendGraph.put("bullet", "none");
				rxSendGraph.put("title", ndConfig.getNcp_name());
				rxSendGraph.put("valueField", ndConfig.getNcp_file_name());
				rxSendGraph.put("useLineColorForBulletBorder", true);
				rxSendGraphs.add(rxSendGraph);
			}
		}
		else{
			JSONObject rxRecvGraph = new JSONObject();
			rxRecvGraph.put("id", "rxRG0");
			rxRecvGraph.put("balloonText", "[[title]]:[[value]] byte");
			rxRecvGraph.put("lineColor", colors[0]);
			rxRecvGraph.put("lineThickness", 2);
			rxRecvGraph.put("bullet", "none");
			rxRecvGraph.put("title", "조회된 데이터가 없습니다.");
			rxRecvGraph.put("valueField", "nodataRxR0");
			rxRecvGraph.put("useLineColorForBulletBorder", true);
			rxRecvGraphs.add(rxRecvGraph);
			
			JSONObject rxSendGraph = new JSONObject();
			rxSendGraph.put("id", "rxSG0");
			rxSendGraph.put("balloonText", "[[title]]:[[value]] byte");
			rxSendGraph.put("lineColor", colors[0]);
			rxSendGraph.put("lineThickness", 2);
			rxSendGraph.put("bullet", "none");
			rxSendGraph.put("title", "조회된 데이터가 없습니다.");
			rxSendGraph.put("valueField", "nodataRxS0");
			rxSendGraph.put("useLineColorForBulletBorder", true);
			rxSendGraphs.add(rxSendGraph);
		}
		//그래프 설정 정보 생성 끝
		
		//차트 정보 가져오기 시작
		JSONArray txRecvCtData = new JSONArray();
		JSONArray txSendCtData = new JSONArray();
		JSONArray rxRecvCtData = new JSONArray();
		JSONArray rxSendCtData = new JSONArray();

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), Integer.parseInt(ssecond));
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), Integer.parseInt(esecond));
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		ArrayList<ArrayList<NdStatNw>> recvTxDataList = new ArrayList<ArrayList<NdStatNw>>();
		ArrayList<ArrayList<NdStatNw>> sendTxDataList = new ArrayList<ArrayList<NdStatNw>>();
		for(int i=0; i<ndConfigTx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigTx.get(i);
			recvTxDataList.add(ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
			sendTxDataList.add(ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
		}
		
		ArrayList<ArrayList<NdStatNw>> recvRxDataList = new ArrayList<ArrayList<NdStatNw>>();
		ArrayList<ArrayList<NdStatNw>> sendRxDataList = new ArrayList<ArrayList<NdStatNw>>();
		for(int i=0; i<ndConfigRx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigRx.get(i);
			recvRxDataList.add(ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
			sendRxDataList.add(ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
		}
		
		for(int j=0; j<=cnt; j++){
			Date now_date = c1.getTime();
			String now_date_str = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now_date);
			
			JSONObject txRecv = new JSONObject();
			txRecv.put("date", now_date.getTime());
			for(int i=0; i<recvTxDataList.size(); i++){
				ArrayList<NdStatNw> recvTxSum = recvTxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<recvTxSum.size(); k++){
					NdStatNw recvTxData = (NdStatNw)recvTxSum.get(k);
					if(recvTxData.getNsr_date().equals(now_date_str)){
						txRecv.put(recvTxData.getNsr_pg_fname(), recvTxData.getNsr_byte());
						recvTxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					txRecv.put(ndConfigTx.get(i).getNcp_file_name(), 0);
				}
			}
			txRecvCtData.add(txRecv);

			JSONObject txSend = new JSONObject();
			txSend.put("date", now_date.getTime());
			for(int i=0; i<sendTxDataList.size(); i++){
				ArrayList<NdStatNw> sendTxSum = sendTxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<sendTxSum.size(); k++){
					NdStatNw sendTxData = (NdStatNw)sendTxSum.get(k);
					if(sendTxData.getNss_date().equals(now_date_str)){
						txSend.put(sendTxData.getNss_pg_fname(), sendTxData.getNss_byte());
						sendTxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					txSend.put(ndConfigTx.get(i).getNcp_file_name(), 0);
				}
			}
			txSendCtData.add(txSend);

			JSONObject rxRecv = new JSONObject();
			rxRecv.put("date", now_date.getTime());
			for(int i=0; i<recvRxDataList.size(); i++){
				ArrayList<NdStatNw> recvRxSum = recvRxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<recvRxSum.size(); k++){
					NdStatNw recvRxData = (NdStatNw)recvRxSum.get(k);
					if(recvRxData.getNsr_date().equals(now_date_str)){
						rxRecv.put(recvRxData.getNsr_pg_fname(), recvRxData.getNsr_byte());
						recvRxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					rxRecv.put(ndConfigRx.get(i).getNcp_file_name(), 0);
				}
			}
			rxRecvCtData.add(rxRecv);

			JSONObject rxSend = new JSONObject();
			rxSend.put("date", now_date.getTime());
			for(int i=0; i<sendRxDataList.size(); i++){
				ArrayList<NdStatNw> sendRxSum = sendRxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<sendRxSum.size(); k++){
					NdStatNw sendRxData = (NdStatNw)sendRxSum.get(k);
					if(sendRxData.getNss_date().equals(now_date_str)){
						rxSend.put(sendRxData.getNss_pg_fname(), sendRxData.getNss_byte());
						sendRxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					rxSend.put(ndConfigRx.get(i).getNcp_file_name(), 0);
				}
			}
			rxSendCtData.add(rxSend);
			
			//c1.add(Calendar.SECOND, 10); // 10초 더하기
			c1.add(Calendar.SECOND, 60); // 1분 더하기
		}
		//차트 정보 가져오기 끝
		
		//마무리
		retVal.put("txRecvTbCont", txRecvTbCont);//송신 r 데이터 정보 테이블
		retVal.put("txSendTbCont", txSendTbCont);//송신 s 데이터 정보 테이블
		retVal.put("rxRecvTbCont", rxRecvTbCont);//수신 r 데이터 정보 테이블
		retVal.put("rxSendTbCont", rxSendTbCont);//수신 s 데이터 정보 테이블
		
		retVal.put("txRecvCtData", txRecvCtData);//차트 데이터
		retVal.put("txSendCtData", txSendCtData);//차트 데이터
		retVal.put("rxRecvCtData", rxRecvCtData);//차트 데이터
		retVal.put("rxSendCtData", rxSendCtData);//차트 데이터

		retVal.put("txRecvGraphs", txRecvGraphs);//내부망데이터 수집 그래프설정
		retVal.put("txSendGraphs", txSendGraphs);//일방향 데이터 송신 그래프설정
		retVal.put("rxRecvGraphs", rxRecvGraphs);//일방향데이터 수신 그래프설정
		retVal.put("rxSendGraphs", rxSendGraphs);//외부망 데이터 송신 그래프설정

		retVal.put("maxTxRSumCnt", maxTxRSumCnt);//최대 내부망데이터 수집량 서비스 번호
		retVal.put("maxTxSSumCnt", maxTxSSumCnt);//최대 내부망데이터 송신량 서비스 번호
		retVal.put("maxRxRSumCnt", maxRxRSumCnt);//최대 외부망데이터 수신량 서비스 번호
		retVal.put("maxRxSSumCnt", maxRxSSumCnt);//최대 외부망데이터 송신량 서비스 번호
		
		retVal.put("code", "OK");
	    
	    return retVal;
	}*/
	
	/**
	 * 대시보드 차트 데이터  10초단위로 차트 데이터를 가져오기(차트 전송량 갱신)
	 * @return retVal
	 * @throws ParseException 
	 */
	/*@RequestMapping(value = "/updateChart.do", method = RequestMethod.POST)
	@ResponseBody
	public Object updateChart(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("ssecond") String ssecond, @RequestParam("esecond") String esecond, @RequestParam("tx_div") String tx_div,
			@RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {

		*//*if(nnsp.util.StringUtil.checkNumber(ssecond)){
			ssecond = Integer.toString((Integer.parseInt(ssecond)/10)*10);
		}
		else{
			ssecond = "00";
		}
		if(nnsp.util.StringUtil.checkNumber(esecond)){
			esecond = Integer.toString(((Integer.parseInt(esecond)/10)*10)+9);
		}
		else{
			esecond = "59";
		}*//*
		ssecond = "00";
		esecond = "59";
	
		Map<String, Object> retVal = new HashMap<String, Object>();

		int txDiv = 1;
		int rxDiv = 2;
		if(nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv=Integer.parseInt(tx_div);
		}
		if(nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv=Integer.parseInt(rx_div);
		}
		
		ArrayList<NdConfig> ndConfigTx = ncConfigService.program_list(1,txDiv);
		ArrayList<NdConfig> ndConfigRx = ncConfigService.program_list(1,rxDiv);
			
		//테이블 정보 가져오기 시작
		int maxTxRSumCnt = 0;
		long maxTxRSum = 0;
		int maxTxSSumCnt = 0;
		long maxTxSSum = 0;
		
		ArrayList<String> nsrByteSumTx = new ArrayList<String>();
		ArrayList<String> nssByteSumTx = new ArrayList<String>();
		for(int i=0; i<ndConfigTx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigTx.get(i);

			long txRSum = ncDashService.nsr_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
			if(maxTxRSum < txRSum){
				maxTxRSum = txRSum;
				maxTxRSumCnt = i;
			}
			nsrByteSumTx.add(ncDashService.sizeCalculation(txRSum));
			
			long txSSum = ncDashService.nss_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
			if(maxTxSSum < txSSum){
				maxTxSSum = txSSum;
				maxTxSSumCnt = i;
			}
			nssByteSumTx.add(ncDashService.sizeCalculation(txSSum));
		}
		
		int maxRxRSumCnt = 0;
		long maxRxRSum = 0;
		int maxRxSSumCnt = 0;
		long maxRxSSum = 0;
		
		ArrayList<String> nsrByteSumRx = new ArrayList<String>();
		ArrayList<String> nssByteSumRx = new ArrayList<String>();
		for(int i=0; i<ndConfigRx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigRx.get(i);

			long rxRSum = ncDashService.nsr_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
			if(maxRxRSum < rxRSum){
				maxRxRSum = rxRSum;
				maxRxRSumCnt = i;
			}
			nsrByteSumRx.add(ncDashService.sizeCalculation(rxRSum));
			
			long rxSSum = ncDashService.nss_byte_sum(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()); 
			if(maxRxSSum < rxSSum){
				maxRxSSum = rxSSum;
				maxRxSSumCnt = i;
			}
			nssByteSumRx.add(ncDashService.sizeCalculation(rxSSum));
		}		
		//테이블 정보 가져오기 끝
		
		//차트 정보 가져오기 시작
		JSONArray txRecvCtData = new JSONArray();
		JSONArray txSendCtData = new JSONArray();
		JSONArray rxRecvCtData = new JSONArray();
		JSONArray rxSendCtData = new JSONArray();
		
		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), Integer.parseInt(ssecond));
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), Integer.parseInt(esecond));
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		ArrayList<ArrayList<NdStatNw>> recvTxDataList = new ArrayList<ArrayList<NdStatNw>>();
		ArrayList<ArrayList<NdStatNw>> sendTxDataList = new ArrayList<ArrayList<NdStatNw>>();
		for(int i=0; i<ndConfigTx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigTx.get(i);
			recvTxDataList.add(ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
			sendTxDataList.add(ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
		}
		
		ArrayList<ArrayList<NdStatNw>> recvRxDataList = new ArrayList<ArrayList<NdStatNw>>();
		ArrayList<ArrayList<NdStatNw>> sendRxDataList = new ArrayList<ArrayList<NdStatNw>>();
		for(int i=0; i<ndConfigRx.size(); i++){
			NdConfig ndConfig = (NdConfig)ndConfigRx.get(i);
			recvRxDataList.add(ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
			sendRxDataList.add(ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, ssecond, esecond, ndConfig.getNcp_file_name()));
		}		
		
		for(int j=0; j<=cnt; j++){
			Date now_date = c1.getTime();
			String now_date_str = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now_date);
			
			JSONObject txRecv = new JSONObject();
			txRecv.put("date", now_date.getTime());
			for(int i=0; i<recvTxDataList.size(); i++){
				ArrayList<NdStatNw> recvTxSum = recvTxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<recvTxSum.size(); k++){
					NdStatNw recvTxData = (NdStatNw)recvTxSum.get(k);
					if(recvTxData.getNsr_date().equals(now_date_str)){
						txRecv.put(recvTxData.getNsr_pg_fname(), recvTxData.getNsr_byte());
						recvTxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					txRecv.put(ndConfigTx.get(i).getNcp_file_name(), 0);
				}
			}
			txRecvCtData.add(txRecv);

			JSONObject txSend = new JSONObject();
			txSend.put("date", now_date.getTime());
			for(int i=0; i<sendTxDataList.size(); i++){
				ArrayList<NdStatNw> sendTxSum = sendTxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<sendTxSum.size(); k++){
					NdStatNw sendTxData = (NdStatNw)sendTxSum.get(k);
					if(sendTxData.getNss_date().equals(now_date_str)){
						txSend.put(sendTxData.getNss_pg_fname(), sendTxData.getNss_byte());
						sendTxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					txSend.put(ndConfigTx.get(i).getNcp_file_name(), 0);
				}
			}
			txSendCtData.add(txSend);

			JSONObject rxRecv = new JSONObject();
			rxRecv.put("date", now_date.getTime());
			for(int i=0; i<recvRxDataList.size(); i++){
				ArrayList<NdStatNw> recvRxSum = recvRxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<recvRxSum.size(); k++){
					NdStatNw recvRxData = (NdStatNw)recvRxSum.get(k);
					if(recvRxData.getNsr_date().equals(now_date_str)){
						rxRecv.put(recvRxData.getNsr_pg_fname(), recvRxData.getNsr_byte());
						recvRxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					rxRecv.put(ndConfigRx.get(i).getNcp_file_name(), 0);
				}
			}
			rxRecvCtData.add(rxRecv);

			JSONObject rxSend = new JSONObject();
			rxSend.put("date", now_date.getTime());
			for(int i=0; i<sendRxDataList.size(); i++){
				ArrayList<NdStatNw> sendRxSum = sendRxDataList.get(i);
				boolean chkData = false;
				for(int k=0; k<sendRxSum.size(); k++){
					NdStatNw sendRxData = (NdStatNw)sendRxSum.get(k);
					if(sendRxData.getNss_date().equals(now_date_str)){
						rxSend.put(sendRxData.getNss_pg_fname(), sendRxData.getNss_byte());
						sendRxSum.remove(k);
						chkData = true;
						break;
					}
				}
				if(!chkData){
					rxSend.put(ndConfigRx.get(i).getNcp_file_name(), 0);
				}
			}
			rxSendCtData.add(rxSend);
			
			//c1.add(Calendar.SECOND, 10); // 10초 더하기
			c1.add(Calendar.SECOND, 60); // 1분 더하기
		}
		//차트 정보 가져오기 끝
		
		//마무리
		retVal.put("nsrByteSumTx", nsrByteSumTx);//송신 r 데이터 정보 테이블
		retVal.put("nssByteSumTx", nssByteSumTx);//송신 s 데이터 정보 테이블
		retVal.put("nsrByteSumRx", nsrByteSumRx);//수신 r 데이터 정보 테이블
		retVal.put("nssByteSumRx", nssByteSumRx);//수신 s 데이터 정보 테이블
		
		retVal.put("txRecvCtData", txRecvCtData);//차트 데이터
		retVal.put("txSendCtData", txSendCtData);//차트 데이터
		retVal.put("rxRecvCtData", rxRecvCtData);//차트 데이터
		retVal.put("rxSendCtData", rxSendCtData);//차트 데이터

		retVal.put("maxTxRSumCnt", maxTxRSumCnt);//최대 내부망데이터 수집량 서비스 번호
		retVal.put("maxTxSSumCnt", maxTxSSumCnt);//최대 내부망데이터 송신량 서비스 번호
		retVal.put("maxRxRSumCnt", maxRxRSumCnt);//최대 외부망데이터 수신량 서비스 번호
		retVal.put("maxRxSSumCnt", maxRxSSumCnt);//최대 외부망데이터 송신량 서비스 번호
		
		retVal.put("code", "OK");
	    
	    return retVal;
	}*/
}