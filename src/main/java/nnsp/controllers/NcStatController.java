package nnsp.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nnsp.common.Config;
import nnsp.services.NcConfigService;
import nnsp.services.NcDashService;
import nnsp.services.NcLinkPolicyService;
import nnsp.services.NcLogService;
import nnsp.services.NcMenuService;
import nnsp.services.NcPolicyLinkService;
import nnsp.services.NcStatService;
import nnsp.services.NcUserService;
import nnsp.services.NcWhService;
import nnsp.services.NdrAnalyzeService;
import nnsp.services.NcProductService;
import nnsp.util.MessageUtil;
import nnsp.util.StringUtil;
import nnsp.vo.NcLinkPolicy;
import nnsp.vo.NcLog;
import nnsp.vo.NcProduct;
import nnsp.vo.NcStatNw;
import nnsp.vo.NcStatSys;
import nnsp.vo.NcSysLoad;
import nnsp.vo.NcWhList;
import nnsp.vo.NdrPs;
import nnsp.vo.NcStatMs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class NcStatController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcStatController.class);
	
	@Autowired
	NcStatService ncStatService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcUserService ncUserService;
	@Autowired
	NcLogService ncLogService;
	@Autowired
	NcProductService ncProductService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcDashService ncDashService;
	@Autowired
	NcLinkPolicyService ncLinkPolicyService;
	@Autowired
	NcPolicyLinkService ncPolicyLinkService;
	@Autowired
	NdrAnalyzeService ndrAnalyzeService;
	@Autowired
	NcWhService ncWhService;
	
	private String[] colors = new String []{"#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7"};
	private ArrayList<String[][]> colorset = new ArrayList<String[][]>();
	
	/**
	 * 네트워크 트래픽 통계
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_traffic.do")
	public String stat_traffic(NcStatNw ncStatNw, Locale locale, Model model,
			@RequestParam(value="tx_div", required=false) String tx_div,
			@RequestParam(value="rx_div", required=false) String rx_div) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		String txDiv = "1";
		String rxDiv = "1";  // 양방향에서는 2 이지만 단방향에서는 1로 사용
		if(tx_div!=null && nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv = tx_div;
		}
		if(rx_div!=null && nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv = rx_div;
		}
		model.addAttribute("tx_div", txDiv);
		model.addAttribute("rx_div", rxDiv);
		
			
		
		if(ncStatNw.getDatetab()==null) {
			ncStatNw.setDatetab("1");
		}
		model.addAttribute("ncStatNw", ncStatNw);
		
		return "stat/stat_traffic";
	}
	
	@RequestMapping(value = "/realtime_log.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String realtime_log(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, @RequestParam("ps_inout") String ps_inout, Locale locale, Model model) {
		
		NdrPs searchNdrPs = new NdrPs();
		searchNdrPs.setCnt(10);
		searchNdrPs.setPs_strc_stime(sdate+" "+shour+":"+sminute+":00");
		searchNdrPs.setPs_strc_etime(edate+" "+ehour+":"+eminute+":59");
		searchNdrPs.setSearchPs_inout(ps_inout);
		ArrayList<NdrPs> realtime = ndrAnalyzeService.getICSPacketList(searchNdrPs);
		
		String strReturn = "<table id='log_event"+ps_inout+"' class='foot-wrapper'>";
		strReturn += "<tbody>";
		
		if(realtime.size()>0){
			String clas="";
			for(int i=0; i < realtime.size(); i++){
			//for(int i=0; i < 5; i++){ // 상위 5개만 보이게..다 보일려면 css 스크롤이 되야 하는데..
				clas="";
				if(realtime.get(i).getPs_detect()==0){ 
					clas = " class=danger"; 
				}
				strReturn += "<tr"+clas+">";
				strReturn += "<td class='list-group1'>"+realtime.get(i).getPs_strc_time()+"</td>";
				strReturn += "<td class='list-group3'>"+realtime.get(i).getPs_proto_sub_dec()+"</td>";
				strReturn += "<td class='list-group2'>"+realtime.get(i).getStrPsInOut()+"</td>";
				strReturn += "<td class='list-group2'>"+realtime.get(i).getStrPsDetect()+"</td>";
				strReturn += "<td class='list-group1'>"+realtime.get(i).getPs_detect_desc()+"</td>";
				strReturn += "<td class='list-group1'>"+realtime.get(i).getPs_src_ip()+":"+realtime.get(i).getPs_src_port()+"</td>";
				strReturn += "<td class='list-group1'>"+realtime.get(i).getPs_dst_ip()+":"+realtime.get(i).getPs_dst_port()+"</td>";
				strReturn += "<td class='list-group4'>&nbsp;</td>";
				strReturn += "</tr>";				
				
				if(i==20) break;
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='7'>"+MessageUtil.getMessage("stat.nodata")+"</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
	}
	
	@GetMapping(value = "/prot_log.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public Object prot_log(
		@RequestParam("sdate") String sdate,
		@RequestParam("edate") String edate,
		@RequestParam("tx_div") String tx_div,
		Locale locale, Model model) {

		int limit = 5;
		ArrayList<NcLog> protLogs = ncStatService.prot_log(sdate, edate, tx_div, limit);

		Gson gson = new Gson();

		return gson.toJson(protLogs);
	}
	
	@GetMapping(value = "/sys_load.do")
	@ResponseBody
	public Object getSysLoad(HttpSession session) {
		NcSysLoad ncSysLoad = ncStatService.protection_system_load();
		return ncSysLoad;
	}
	
	/**
	 * 데이터 수집량 차트
	 * @return 데이터 수집량 차트 (퓨전차트)
	 */
	//@RequestMapping(value = "/receive_ct.do", produces="application/json;charset=UTF-8")
	//@ResponseBody
	//public String receive_ct(@RequestParam("dtArray") String[] dtArray, Model model) {
		/*ArrayList<NdStatNw> collect = ncStatService.receive_tb();
		
		HashMap<Integer, ArrayList<NdStatNw>> map = new HashMap<Integer, ArrayList<NdStatNw>>();
		for(int i=0; i<collect.size(); i++){
			map.put(i, ncStatService.receive_ct(collect.get(i).getNsr_pg_fname()));
		}
		
		model.addAttribute("seriesList", collect);
		model.addAttribute("seriesValue", map);
		return "stat/graph_traffic_zoomline";*/

		/*JSONArray jArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		ArrayList<NdStatNw> collect = ncStatService.receive_tb();
		if(collect.size()>0){
			Calendar c1 = Calendar.getInstance();
		
			for(int i=0; i<collect.size(); i++){
				c1.add(Calendar.HOUR, -1); // 한시간 전
				
				ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
				ArrayList<NdStatNw> timesum = ncStatService.receive_ct(collect.get(i).getNsr_pg_fname());
				
				for(int j=0; j<60; j++){
					ArrayList<Object> team = new ArrayList<Object>();
					team.add(Long.parseLong(dtArray[j]));
					int temp = 0;
					for(int k=0; k<timesum.size(); k++){						
						if(timesum.get(k).getStat_date().equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
							team.add(Integer.toString(timesum.get(k).getStat_sum())); // 문자로 넣어야 함
							temp=1;
							break;
						}
					}
					if(temp==0) team.add("0"); // 문자로 넣어야 함. 시간에 데이터가 없으면 0으로 세팅
					list.add(team);
					c1.add(Calendar.MINUTE, 1); // 1분 더하기
				}
				
				jsonObject.put("data", JSONArray.fromObject(list));
				jArray.add(jsonObject);
			}
		}else{
			ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
			for(int i=0; i<60; i++){
				ArrayList<Object> team = new ArrayList<Object>();
				team.add(Long.parseLong(dtArray[i]));
				team.add("0");
				list.add(team);
			}
			//jsonObject.a("data", new Gson().toJson(list));
			jsonObject.put("data", JSONArray.fromObject(list));
			jArray.add(jsonObject);
		}*/
		
		/*return "[{data:[[gd(2012, 1, 1, 11, 30), 17],[gd(2012, 1, 1, 11, 31), 74],[gd(2012, 1, 1, 11, 32), 6],[gd(2012, 1, 1, 11, 33), 39],[gd(2012, 1, 1, 11, 34), 20]]}," +
				"{data:[[gd(2012, 1, 1, 11, 30), 17],[gd(2012, 1, 1, 11, 31), 74],[gd(2012, 1, 1, 11, 32), 6],[gd(2012, 1, 1, 11, 33), 39],[gd(2012, 1, 1, 11, 34), 20]]}," +
				"{data:[[gd(2012, 1, 1, 11, 30), 17],[gd(2012, 1, 1, 11, 31), 74],[gd(2012, 1, 1, 11, 32), 6],[gd(2012, 1, 1, 11, 33), 39],[gd(2012, 1, 1, 11, 34), 20]]}]";*/

//		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
//		ArrayList<Object> team1 = new ArrayList<Object>();
//		ArrayList<Object> team2 = new ArrayList<Object>();
//		ArrayList<Object> team3 = new ArrayList<Object>();
//		//for(int i=0; i<60; i++){
//			team1.add(Long.parseLong(test1[0]));
//			team1.add("10");
//			team2.add(Long.parseLong(test1[1]));
//			team2.add("11");
//			team3.add(Long.parseLong(test1[2]));
//			team3.add("11");
//
//		//}
//		list.add(team1);
//		list.add(team2);
//		list.add(team3);
//		return list;
		
//		System.out.println("11111111111111111111111111111111111111111");
//		System.out.println(jArray.toString());
//		System.out.println("1111111111111111111111111111111111111111");
		//return jArray.toString();
	//}
	
	/**
	 * 데이터 수집량 차트
	 * @return 데이터 수집량 차트
	 */
	@RequestMapping(value = "/receive_ct.do")
	@ResponseBody
	public Object receive_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, 
			@RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		//String return_value="";
		JSONArray jArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		boolean bigsize=false;
		
		ArrayList<NcStatNw> collect = ncStatService.receive_tb(sdate, edate, shour, ehour, sminute, eminute);
		if(collect.size()==0){
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				jsonObject.put("value1", "0");
				jArray.add(jsonObject);
				
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}else{
			ArrayList<ArrayList<NcStatNw>> team = new ArrayList<ArrayList<NcStatNw>>();
			for(int i=0; i<collect.size(); i++){
				team.add(ncStatService.receive_ct(sdate, edate, shour, ehour, sminute, eminute, collect.get(i).getNsr_pg_fname()));
			}
			
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				
				// 숫자 크기 체크 시작
				loop:
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_sum() > 10000){
							bigsize=true;
							break loop;
						}
					}
				}
				// 숫자 크기 체크 끝
				
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					int temp2 = 0;
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_date().equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
							if(bigsize){
								jsonObject.put("value"+(i+1), (timesum.get(k).getStat_sum()/1000));
							}else{
								jsonObject.put("value"+(i+1), timesum.get(k).getStat_sum());
							}
							temp2=1;
							break;
						}
					}
					if(temp2==0) jsonObject.put("value"+(i+1), "0");
				}
				
				jArray.add(jsonObject);
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}
		
		/*return "Date,프로그램1,프로그램2,프로그램3,프로그램4,프로그램5,프로그램6,프로그램7,프로그램8,프로그램9,프로그램10\n" +
				"2009/07/12 01:00:00,2354587,3544877,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 02:00:00,2355487,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 03:00:00,2555487,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 04:00:00,2364487,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 05:00:00,2354487,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 06:00:00,2354587,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877\n" +
				"2009/07/12 07:00:00,2354587,1354487,3000877,1234877,7544877,5544877,7044877,4444877,6044877,6544877";*/
		//return return_value;
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		
		JSONArray jArray2 = new JSONArray();
		String unit=" Byte";
		if(bigsize) unit = " KB";
		for(int j=1; j<=10; j++){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("balloonText", "[[value]]"+unit);
			jsonObject2.put("bullet", "round");
			jsonObject2.put("bulletBorderAlpha", 1);
			jsonObject2.put("bulletBorderColor", "#FFFFFF");
			jsonObject2.put("hideBulletsCount", 50);
			jsonObject2.put("lineThickness", 2);
			jsonObject2.put("lineColor", "#0D477D");
			jsonObject2.put("valueField", "value"+j);
			jsonObject2.put("id", "id"+j);
			jArray2.add(jsonObject2);
		}
		
		return_map.put("graphs_value", jArray2);
		return return_map;
	}
	
	/**
	 * 데이터 수집량 테이블
	 * @return 데이터 수집량 테이블 내용
	 */
	@RequestMapping(value = "/receive_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String receive_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Locale locale, Model model) {
		ArrayList<NcStatNw> collect = ncStatService.receive_tb(sdate, edate, shour, ehour, sminute, eminute);
		
		String strReturn = "<table class='data_table'>";
		strReturn += "<tbody>";
		
		if(collect.size()>0){
			String temp[][] = new String[collect.size()][2];
			for(int i=0; i < collect.size(); i++){
				strReturn += "<tr class='toggle-graph1' data-graph-index='"+i+"' onclick='javascript:clickTrEvent(this)'>";
				strReturn += "<td class='title'><div style='float:left;border-radius:5px;background-color:"+colors[i]+";width:13px;height:13px;margin:5px 5px;'>&nbsp;</div>"
						+ "<div style='float:left;border-radius:5px;background-color:#d5d5d5;width:13px;height:13px;margin:5px 5px;display:none'>&nbsp;</div>"+collect.get(i).getNcp_name()+"</td>";
				strReturn += "<td class='number'>"+StringUtil.toNumFormat(collect.get(i).getNsr_byte())+"</td>";
				strReturn += "</tr>";
				
				temp[i][0] = collect.get(i).getNsr_pg_fname();
				temp[i][1] = colors[i];
			}
			colorset.add(temp);
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='2'>"+MessageUtil.getMessage("stat.nodata")+"</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
		
		/*String strReturn = "";
		ArrayList<NdStatNw> collect = ncStatService.receive_tb();
		if(collect.size()>0){
			int total_byte = 0;
			for(int i=0; i < collect.size(); i++){
				total_byte += collect.get(i).getNsr_byte();
			}
			long percent = 0;
			for(int i=0; i < collect.size(); i++){
				percent = Math.round((double)collect.get(i).getNsr_byte() / (double)total_byte *100.0);
				strReturn += "<div class='widget_summary'>" +
					"<div class='w_left w_25'>" +
					"	<span>"+collect.get(i).getNcp_name()+"</span>" +
					"</div>" +
					"<div class='w_center w_55'>" +
					"	<div class='progress'>" +
					"		<div class='progress-bar' role='progressbar' data-transitiongoal='"+percent+"' style='background-color:"+colors[i]+";width:"+percent+"%'></div>" +
					"	</div>" +
					"</div>" +
					"<div class='w_right w_20'><span>"+StringUtil.toNumFormat(collect.get(i).getNsr_byte())+"</span></div>" +
					"<div class='clearfix'></div>" +
					"</div>";
			}
		}else{
			strReturn += "1시간 이내 데이터가 없습니다.";
		}
		
		return strReturn;*/
	}
	
	/**
	 * 데이터 전송 손실 차트
	 * @return 데이터 전송 손실 차트 내용(퓨전차트)
	 */
	@RequestMapping(value = "/repe_loss_ct.do")
	@ResponseBody
	public Object repe_loss_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, 
			@RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {
		
		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
//		String return_value="";
		JSONArray jArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		//boolean bigsize=false;
		
		ArrayList<NcStatNw> loss = ncStatService.repe_loss_tb(sdate, edate, shour, ehour, sminute, eminute);
		if(loss.size()==0){
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				jsonObject.put("value1", "0");
				jsonObject.put("lcolor1", "#0D477D");
				jArray.add(jsonObject);
				
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}else{
			ArrayList<ArrayList<NcStatNw>> team = new ArrayList<ArrayList<NcStatNw>>();
			for(int i=0; i<loss.size(); i++){
				team.add(ncStatService.repe_loss_ct(sdate, edate, shour, ehour, sminute, eminute, loss.get(i).getNsrp_pg_fname()));
			}
			
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				
				// 숫자 크기 체크 시작
//				loop:
//				for(int i=0; i<team.size(); i++){
//					ArrayList<NdStatNw> timesum = team.get(i);
//					for(int k=0; k<timesum.size(); k++){
//						if(timesum.get(k).getStat_sum() > 10000){
//							bigsize=true;
//							break loop;
//						}
//					}
//				}
				// 숫자 크기 체크 끝
				
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					int temp2 = 0;
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_date().equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
							//if(bigsize){
								//jsonObject.put("value"+(i+1), (timesum.get(k).getStat_sum()/1000));
							//}else{
								jsonObject.put("value"+(i+1), timesum.get(k).getStat_sum());
							//}
							temp2=1;
							break;
						}
					}
					if(temp2==0) jsonObject.put("value"+(i+1), "0");
					// 라인색 지정 시작
					String temp3[][] = new String [colorset.size()][2];
					String colortemp="";
					for(int k=0; k<colorset.size(); k++){
						temp3 = colorset.get(k);
						if(temp3[k][0].equals(timesum.get(i).getNsrp_pg_fname())){
							colortemp = temp3[k][1];
							break;
						}
					}
					jsonObject.put("lcolor"+(i+1), colortemp);
					// 라인색 지정 끝
				}
				
				jArray.add(jsonObject);
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		
		JSONArray jArray2 = new JSONArray();
		//String unit="";
		//if(bigsize) unit = " KB";
		for(int j=1; j<=10; j++){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("balloonText", "[[value]]");
			jsonObject2.put("bullet", "round");
			jsonObject2.put("bulletBorderAlpha", 1);
			jsonObject2.put("bulletBorderColor", "#FFFFFF");
			jsonObject2.put("hideBulletsCount", 50);
			jsonObject2.put("lineThickness", 2);
			jsonObject2.put("lineColor", "#0D477D");
			jsonObject2.put("valueField", "value"+j);
			jsonObject2.put("id", "id"+j);
			jArray2.add(jsonObject2);
		}
		
		return_map.put("graphs_value", jArray2);
		return return_map;
	}
	
	/**
	 * 데이터 전송 손실 테이블
	 * @return 데이터 전송 손실 테이블 내용
	 */
	@RequestMapping(value = "/repe_loss_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String repe_loss_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Locale locale, Model model) {
		ArrayList<NcStatNw> loss = ncStatService.repe_loss_tb(sdate, edate, shour, ehour, sminute, eminute);
		
		String strReturn = "<table class='data_table'>";
		strReturn += "<tbody>";
		
		if(loss.size()>0){
			String temp[][] = new String [colorset.size()][2];
			String colortemp = "";
			for(int i=0; i < loss.size(); i++){
				
				for(int j=0; j<colorset.size(); j++){
					temp = colorset.get(j);
					if(temp[j][0].equals(loss.get(i).getNsrp_pg_fname())){
						colortemp = temp[j][1];
						break;
					}
				}
				
				strReturn += "<tr class='toggle-graph2' data-graph-index='"+i+"' onclick='javascript:clickTrEvent(this)'>";
				strReturn += "<td class='title'><div style='float:left;border-radius:5px;background-color:"+colortemp+";width:13px;height:13px;margin:5px 5px;'>&nbsp;</div>"
						+ "<div style='float:left;border-radius:5px;background-color:#d5d5d5;width:13px;height:13px;margin:5px 5px;display:none'>&nbsp;</div>"+loss.get(i).getNcp_name()+"</td>";
				strReturn += "<td class='number'>"+StringUtil.toNumFormat(loss.get(i).getNsrp_loss_yn())+"</td>";
				strReturn += "</tr>";
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='2'>"+MessageUtil.getMessage("stat.nodata")+"</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
		
		/*String strReturn = "";
		ArrayList<NdStatNw> loss = ncStatService.repe_loss_tb();
		if(loss.size()>0){
			int total_byte = 0;
			for(int i=0; i < loss.size(); i++){
				total_byte += loss.get(i).getNsrp_loss_yn();
			}
			long percent = 0;
			for(int i=0; i < loss.size(); i++){
				percent = Math.round((double)loss.get(i).getNsrp_loss_yn() / (double)total_byte *100.0);
				strReturn += "<div class='widget_summary'>" +
					"<div class='w_left w_25'>" +
					"	<span>"+loss.get(i).getNcp_name()+"</span>" +
					"</div>" +
					"<div class='w_center w_55'>" +
					"	<div class='progress'>" +
					"		<div class='progress-bar' role='progressbar' data-transitiongoal='"+percent+"' style='background-color:"+colors[i]+";width:"+percent+"%'></div>" +
					"	</div>" +
					"</div>" +
					"<div class='w_right w_20'><span>"+StringUtil.toNumFormat(loss.get(i).getNsrp_loss_yn())+"</span></div>" +
					"<div class='clearfix'></div>" +
					"</div>";
			}
		}else{
			strReturn += "1시간 이내 데이터가 없습니다.";
		}

		return strReturn;*/
	}
	
	/**
	 * 일방향 송신량 차트
	 * @return 일방향 송신량 차트 내용(퓨전차트)
	 */
	@RequestMapping(value = "/send_ct.do")
	@ResponseBody
	public Object send_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, 
			@RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {
		
		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		JSONArray jArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		boolean bigsize=false;
		
		ArrayList<NcStatNw> send = ncStatService.send_tb(sdate, edate, shour, ehour, sminute, eminute);
		if(send.size()==0){
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				jsonObject.put("value1", "0");
				jsonObject.put("lcolor1", "#0D477D");
				jArray.add(jsonObject);
				
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}    
		}else{
			ArrayList<ArrayList<NcStatNw>> team = new ArrayList<ArrayList<NcStatNw>>();
			for(int i=0; i<send.size(); i++){
				team.add(ncStatService.send_ct(sdate, edate, shour, ehour, sminute, eminute, send.get(i).getNss_pg_fname()));
			}
			
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				
				// 숫자 크기 체크 시작
				loop:
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_sum() > 10000){
							bigsize=true;
							break loop;
						}
					}
				}
				// 숫자 크기 체크 끝
				
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					int temp2 = 0;
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_date().equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
							if(bigsize){
								jsonObject.put("value"+(i+1), (timesum.get(k).getStat_sum()/1000));
							}else{
								jsonObject.put("value"+(i+1), timesum.get(k).getStat_sum());
							}
							temp2=1;
							break;
						}
					}
					if(temp2==0) jsonObject.put("value"+(i+1), "0");
					// 라인색 지정 시작
					String temp3[][] = new String [colorset.size()][2];
					String colortemp="";
					for(int k=0; k<colorset.size(); k++){
						temp3 = colorset.get(k);
						
						if(temp3[k][0].equals(timesum.get(i).getNss_pg_fname())){
							colortemp = temp3[k][1];
							break;
						}
					}
					jsonObject.put("lcolor"+(i+1), colortemp);
					// 라인색 지정 끝
				}
				
				jArray.add(jsonObject);
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		
		JSONArray jArray2 = new JSONArray();
		String unit=" Byte";
		if(bigsize) unit = " KB";
		for(int j=1; j<=10; j++){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("balloonText", "[[value]]"+unit);
			jsonObject2.put("bullet", "round");
			jsonObject2.put("bulletBorderAlpha", 1);
			jsonObject2.put("bulletBorderColor", "#FFFFFF");
			jsonObject2.put("hideBulletsCount", 50);
			jsonObject2.put("lineThickness", 2);
			jsonObject2.put("lineColor", "#0D477D");
			jsonObject2.put("valueField", "value"+j);
			jsonObject2.put("id", "id"+j);
			jArray2.add(jsonObject2);
		}
		
		return_map.put("graphs_value", jArray2);
		return return_map;
	}
	
	/**
	 * 일방향 송신량 테이블
	 * @return 일방향 송신량 테이블 내용
	 */
	@RequestMapping(value = "/send_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String send_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Locale locale, Model model) {
		ArrayList<NcStatNw> send = ncStatService.send_tb(sdate, edate, shour, ehour, sminute, eminute);
		
		String strReturn = "<table class='data_table'>";
		strReturn += "<tbody>";
		
		if(send.size()>0){
			String temp[][] = new String [colorset.size()][2];
			String colortemp = "";
			for(int i=0; i < send.size(); i++){
				
				for(int j=0; j<colorset.size(); j++){
					temp = colorset.get(j);
					if(temp[j][0].equals(send.get(i).getNss_pg_fname())){
						colortemp = temp[j][1];
						break;
					}
				}
				
				strReturn += "<tr class='toggle-graph3' data-graph-index='"+i+"' onclick='javascript:clickTrEvent(this)'>";
				strReturn += "<td class='title'><div style='float:left;border-radius:5px;background-color:"+colortemp+";width:13px;height:13px;margin:5px 5px;'>&nbsp;</div>"
						+ "<div style='float:left;border-radius:5px;background-color:#d5d5d5;width:13px;height:13px;margin:5px 5px;display:none'>&nbsp;</div>"+send.get(i).getNcp_name()+"</td>";
				strReturn += "<td class='number'>"+StringUtil.toNumFormat(send.get(i).getNss_byte())+"</td>";
				strReturn += "</tr>";
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='2'>"+MessageUtil.getMessage("stat.nodata")+"</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
		
		/*String strReturn = "";
		ArrayList<NdStatNw> send = ncStatService.send_tb();
		if(send.size()>0){
			int total_byte = 0;
			for(int i=0; i < send.size(); i++){
				total_byte += send.get(i).getNss_byte();
			}
			long percent = 0;
			for(int i=0; i < send.size(); i++){
				percent = Math.round((double)send.get(i).getNss_byte() / (double)total_byte *100.0);
				strReturn += "<div class='widget_summary'>" +
					"<div class='w_left w_25'>" +
					"	<span>"+send.get(i).getNcp_name()+"</span>" +
					"</div>" +
					"<div class='w_center w_55'>" +
					"	<div class='progress'>" +
					"		<div class='progress-bar' role='progressbar' data-transitiongoal='"+percent+"' style='background-color:"+colors[i]+";width:"+percent+"%'></div>" +
					"	</div>" +
					"</div>" +
					"<div class='w_right w_20'><span>"+StringUtil.toNumFormat(send.get(i).getNss_byte())+"</span></div>" +
					"<div class='clearfix'></div>" +
					"</div>";
			}
		}else{
			strReturn += "1시간 이내 데이터가 없습니다.";
		}

		return strReturn;*/
	}
	
	/**
	 * 데이터 재전송 차트
	 * @return 데이터 재전송 차트 내용(퓨전차트)
	 */
	@RequestMapping(value = "/repe_byte_ct.do")
	@ResponseBody
	public Object repe_byte_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, 
			@RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		JSONArray jArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		boolean bigsize=false;
		
		ArrayList<NcStatNw> repeat = ncStatService.repe_byte_tb(sdate, edate, shour, ehour, sminute, eminute);
		if(repeat.size()==0){
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				jsonObject.put("value1", "0");
				jsonObject.put("lcolor1", "#0D477D");
				jArray.add(jsonObject);
				
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}else{
			ArrayList<ArrayList<NcStatNw>> team = new ArrayList<ArrayList<NcStatNw>>();
			for(int i=0; i<repeat.size(); i++){
				team.add(ncStatService.repe_byte_ct(sdate, edate, shour, ehour, sminute, eminute, repeat.get(i).getNsrp_pg_fname()));
			}
			
			for(int j=0; j<=cnt; j++){
				jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime()));
				
				// 숫자 크기 체크 시작
				loop:
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_sum() > 10000){
							bigsize=true;
							break loop;
						}
					}
				}
				// 숫자 크기 체크 끝
				
				for(int i=0; i<team.size(); i++){
					ArrayList<NcStatNw> timesum = team.get(i);
					int temp2 = 0;
					for(int k=0; k<timesum.size(); k++){
						if(timesum.get(k).getStat_date().equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
							if(bigsize){
								jsonObject.put("value"+(i+1), (timesum.get(k).getStat_sum()/1000));
							}else{
								jsonObject.put("value"+(i+1), timesum.get(k).getStat_sum());
							}
							temp2=1;
							break;
						}
					}
					if(temp2==0) jsonObject.put("value"+(i+1), "0");
					// 라인색 지정 시작
					String temp3[][] = new String [colorset.size()][2];
					String colortemp="";
					for(int k=0; k<colorset.size(); k++){
						temp3 = colorset.get(k);
						if(temp3[k][0].equals(timesum.get(i).getNsrp_pg_fname())){
							colortemp = temp3[k][1];
							break;
						}
					}
					jsonObject.put("lcolor"+(i+1), colortemp);
					// 라인색 지정 끝
				}
				
				jArray.add(jsonObject);
				c1.add(Calendar.MINUTE, 1); // 1분 더하기
			}
		}
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		
		JSONArray jArray2 = new JSONArray();
		String unit=" Byte";
		if(bigsize) unit = " KB";
		for(int j=1; j<=10; j++){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("balloonText", "[[value]]"+unit);
			jsonObject2.put("bullet", "round");
			jsonObject2.put("bulletBorderAlpha", 1);
			jsonObject2.put("bulletBorderColor", "#FFFFFF");
			jsonObject2.put("hideBulletsCount", 50);
			jsonObject2.put("lineThickness", 2);
			jsonObject2.put("lineColor", "#0D477D");
			jsonObject2.put("valueField", "value"+j);
			jsonObject2.put("id", "id"+j);
			jArray2.add(jsonObject2);
		}
		
		return_map.put("graphs_value", jArray2);
		return return_map;
	}
	
	/**
	 * 데이터 재전송 테이블
	 * @return 데이터 재전송 테이블  내용
	 */
	@RequestMapping(value = "/repe_byte_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String repe_byte_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour, @RequestParam("ehour") String ehour, 
			@RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Locale locale, Model model) {
		ArrayList<NcStatNw> repeat = ncStatService.repe_byte_tb(sdate, edate, shour, ehour, sminute, eminute);
		
		String strReturn = "<table class='data_table'>";
		strReturn += "<tbody>";
		
		if(repeat.size()>0){
			String temp[][] = new String [colorset.size()][2];
			String colortemp = "";
			for(int i=0; i < repeat.size(); i++){
				
				for(int j=0; j<colorset.size(); j++){
					temp = colorset.get(j);
					if(temp[j][0].equals(repeat.get(i).getNsrp_pg_fname())){
						colortemp = temp[j][1];
						break;
					}
				}
				
				strReturn += "<tr class='toggle-graph4' data-graph-index='"+i+"' onclick='javascript:clickTrEvent(this)'>";
				strReturn += "<td class='title'><div style='float:left;border-radius:5px;background-color:"+colortemp+";width:13px;height:13px;margin:5px 5px;'>&nbsp;</div>"
						+ "<div style='float:left;border-radius:5px;background-color:#d5d5d5;width:13px;height:13px;margin:5px 5px;display:none'>&nbsp;</div>"+repeat.get(i).getNcp_name()+"</td>";
				strReturn += "<td class='number'>"+StringUtil.toNumFormat(repeat.get(i).getNsrp_byte())+"</td>";
				strReturn += "</tr>";
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='2'>"+MessageUtil.getMessage("stat.nodata")+"</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
		
		/*String strReturn = "";
		ArrayList<NdStatNw> repeat = ncStatService.repe_byte_tb();
		if(repeat.size()>0){
			int total_byte = 0;
			for(int i=0; i < repeat.size(); i++){
				total_byte += repeat.get(i).getNsrp_byte();
			}
			long percent = 0;
			for(int i=0; i < repeat.size(); i++){
				percent = Math.round((double)repeat.get(i).getNsrp_byte() / (double)total_byte *100.0);
				strReturn += "<div class='widget_summary'>" +
					"<div class='w_left w_25'>" +
					"	<span>"+repeat.get(i).getNcp_name()+"</span>" +
					"</div>" +
					"<div class='w_center w_55'>" +
					"	<div class='progress'>" +
					"		<div class='progress-bar' role='progressbar' data-transitiongoal='"+percent+"' style='background-color:"+colors[i]+";width:"+percent+"%'></div>" +
					"	</div>" +
					"</div>" +
					"<div class='w_right w_20'><span>"+StringUtil.toNumFormat(repeat.get(i).getNsrp_byte())+"</span></div>" +
					"<div class='clearfix'></div>" +
					"</div>";
			}
		}else{
			strReturn += "1시간 이내 데이터가 없습니다.";
		}

		return strReturn;*/
	}
	
///////////////////////////////// 아래는 현재 사용안함
	
	/**
	 * 시스템 통계(네트워크 트래픽)
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_system.do", method = RequestMethod.GET)
	public String stat_system(Locale locale, Model model) {
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		return "stat/stat_system";
	}
	
	/**
	 * 시스템별 트래픽 차트
	 * @return 시스템별 트래픽 차트 내용(퓨전차트)
	 */
	@RequestMapping(value = "/system_ct.do", method = RequestMethod.GET)
	public String system_ct(@RequestParam("view_type") String view_type, @RequestParam("show") String show, Locale locale, Model model) {
		ArrayList<NcStatSys> system =  ncStatService.system_ct(view_type);
		model.addAttribute("systemList", system);
		model.addAttribute("show", show);
		return "stat/graph_sys_system";
	}
	
	/**
	 * 시스템별 트래픽 테이블
	 * @return 시스템별 트래픽 차트 내용
	 */
	@RequestMapping(value = "/system_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String system_tb(@RequestParam("view_type") String view_type, Locale locale, Model model) {
		ArrayList<NcStatSys> system = ncStatService.system_ct(view_type);
		
		String strReturn = "<table cellspacing='0px' cellpadding='2px' style='table-layout:fixed;'>";
		strReturn += "<colgroup>"; 
		strReturn += "<col width='5%' /><col width='5%' /><col width='' /><col width='' /><col width='15%' /><col width='15%' /><col width='15%' /><col width='15%' />";
		strReturn += "</colgroup>";
		
		for(int i=0; i < system.size(); i++){
			strReturn += "<tr id='system_"+system.get(i).getNoi_gcode()+"'>" +
					"<td style='text-align:center;'><a onClick='system_detail(this,"+system.get(i).getNoi_gcode()+");' style='cursor:pointer;'><img src='/images/tree_menu/close.png'></td>" +
					"<td style='text-align:center;'><div style='border-radius:5px;background-color:"+colors[i]+";width:15px;'>&nbsp;</div></td>" +
					"<td style='text-align:center;'>"+system.get(i).getNoi_obj_nm()+"</td>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'>"+system.get(i).getInsize()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getOutsize()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getIncnt()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getOutcnt()+"</td>" +
				"</tr>";
		}
		
		strReturn += "</table>";

		return strReturn;
	}
	
	/**
	 * 시스템별 트래픽 테이블 상세
	 * @return 시스템별 트래픽 테이블 시스템 상세
	 */
	@RequestMapping(value = "/system_detail.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String system_detail(@RequestParam("group_id") String group_id, Locale locale, Model model) {
		ArrayList<NcStatSys> system = ncStatService.system_detail(group_id);
		
		String strReturn = "";
		for(int i=0; i < system.size(); i++){
			strReturn += "<tr name='sys_dtTR_"+group_id+"'>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'>"+system.get(i).getNoi_ip()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getNoi_ip2()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getInsize()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getOutsize()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getIncnt()+"</td>" +
					"<td style='text-align:center;'>"+system.get(i).getOutcnt()+"</td>" +
				"</tr>";
		}

		return strReturn;
	}
	
	/**
	 * 서비스별 트래픽 차트
	 * @return 서비스별 트래픽 차트 내용(퓨전차트)
	 */
	@RequestMapping(value = "/service_ct.do", method = RequestMethod.GET)
	public String service_ct(@RequestParam("view_type") String view_type, @RequestParam("show") String show, Locale locale, Model model) {
		ArrayList<NcStatSys> date = ncStatService.getCurrenttime();
		
		ArrayList<NcStatSys> service_tb =  ncStatService.service_tb(view_type);
		HashMap<Integer, ArrayList<NcStatSys>> map = new HashMap<Integer, ArrayList<NcStatSys>>();
		
		for(int i=0; i<service_tb.size(); i++){
			map.put(i, ncStatService.progress_ct(view_type, service_tb.get(i).getNsn_protocol(), service_tb.get(i).getPort()));
		}
		  
		model.addAttribute("seriesList", service_tb);
		model.addAttribute("seriesValue", map);
		model.addAttribute("dateList", date);
		model.addAttribute("show", show);
		return "stat/graph_sys_zoomline";
	}
	
	/**
	 * 서비스별 트래픽 테이블
	 * @return 서비스별 트래픽 차트 내용
	 */
	@RequestMapping(value = "/service_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String service_tb(@RequestParam("view_type") String view_type, Locale locale, Model model) {
		ArrayList<NcStatSys> service =  ncStatService.service_tb(view_type);
		
		String strReturn = "<table cellspacing='0px' cellpadding='2px' style='table-layout:fixed;'>";
		strReturn += "<colgroup>"; 
		strReturn += "<col width='5%' /><col width='5%' /><col width='' /><col width='' /><col width='15%' /><col width='15%' /><col width='15%' /><col width='15%' />";
		strReturn += "</colgroup>";
		
		for(int i=0; i < service.size(); i++){
			strReturn += "<tr id='service_"+service.get(i).getPort()+"_"+service.get(i).getNsn_protocol()+"_"+view_type+"'>" +
					"<td style='text-align:center;'><a onClick=\"service_detail(this,'"+service.get(i).getPort()+"','"+service.get(i).getNsn_protocol()+"','"+view_type+"');\" style='cursor:pointer;'><img src='/images/tree_menu/close.png'></td>" +
					"<td style='text-align:center;'><div style='border-radius:5px;background-color:"+colors[i]+";width:15px;'>&nbsp;</div></td>" +
					"<td style='text-align:center;'>"+service.get(i).getService_name()+"#"+service.get(i).getPort()+"</td>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'>"+service.get(i).getInsize()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getOutsize()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getIncnt()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getOutcnt()+"</td>" +
				"</tr>";
		}
		
		strReturn += "</table>";
		return strReturn;
	}
	
	/**
	 * 서비스별 트래픽 테이블 상세
	 * @return 서비스별 트래픽 테이블 시스템 상세
	 */
	@RequestMapping(value = "/service_detail.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String service_detail(@RequestParam("port") String port, @RequestParam("protocol") String protocol, @RequestParam("view_type") String view_type, Locale locale, Model model) {
		ArrayList<NcStatSys> service =  ncStatService.service_detail(port, protocol, view_type);
		
		String strReturn = "";
		for(int i=0; i < service.size(); i++){
			strReturn += "<tr name='srv_dtTR_"+port+"_"+protocol+"_"+view_type+"'>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'></td>" +
					"<td style='text-align:center;'>"+service.get(i).getNsn_src_ip()+"</td>"+
					"<td style='text-align:center;'>"+service.get(i).getNsn_dst_ip()+"</td>"+
					"<td style='text-align:center;'>"+service.get(i).getInsize()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getOutsize()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getIncnt()+"</td>" +
					"<td style='text-align:center;'>"+service.get(i).getOutcnt()+"</td>" +
				"</tr>";
		}

		return strReturn;
	}
	
	/**
	 * 네트워크 트래픽 연결 차트
	 * @return 네트워크 트래픽 연결 차트
	 */
	@RequestMapping(value = "/network_traffic.do", method = RequestMethod.GET)
	public String network_traffic(@RequestParam("view_type") String view_type, Locale locale, Model model) {
		String[][] tmp1 =  ncStatService.network_traffic_grp(view_type);
		/*for(int i=0; i<tmp1.length; i++){
			System.out.println("1111111111111111111111111111111");
			for(int j=0; j<tmp1[i].length; j++){
				System.out.println(tmp1[i][j]);
			}
			System.out.println("1111111111111111111111111111111");
		}*/
		model.addAttribute("data", tmp1);
		return "stat/network_traffic";
	}
	
	/**
	 * 네트워크 트래픽 연결 차트
	 * @return 네트워크 트래픽 연결 차트
	 */
	@RequestMapping(value = "/network_traffic1.do", method = RequestMethod.GET)
	public String network_traffic1(@RequestParam("view_type") String view_type, Locale locale, Model model) {
		String[][] tmp1 =  ncStatService.network_traffic_grp(view_type);
		model.addAttribute("data", tmp1);
		return "stat/network_traffic";
	}
	
	
///////////////////////////////////////////	관리자 통계 메뉴
	/**
	 * 관리자 통계
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_manager.do")
	public String stat_manager(NcLog ncMngData, Locale locale, Model model) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(ncMngData.getNlm_create_edate()==null) {
			String edate = sdf.format(cal.getTime());
			ncMngData.setNlm_create_edate(edate);
		}
		if(ncMngData.getNlm_create_sdate()==null) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-7); // 7일전 날짜 Set
			String sdate = sdf.format(cal.getTime());
			ncMngData.setNlm_create_sdate(sdate);
		}
		if(ncMngData.getDatetab()==null) {
			ncMngData.setDatetab("7");
		}
		model.addAttribute("ncMngData", ncMngData);
		model.addAttribute("userList", ncUserService.getNsuSecuLevel());
		return "stat/stat_manager";
	}
	
	/**
	 * 접근 현황 차트
	 * @return 접근 현황 차트 내용(퓨전차트)
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/login_ct.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) throws ParseException {
		ArrayList<NcLog> sList =  ncStatService.login_ct(sdate, edate, nsu_id, "S");
		ArrayList<NcLog> fList =  ncStatService.login_ct(sdate, edate, nsu_id, "F");
//		model.addAttribute("sList", sList);
//		model.addAttribute("fList", fList);
//		model.addAttribute("sdate", sdate);
//		model.addAttribute("edate", edate);
		
		//Date타입으로 변경
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = df.parse( sdate );
		Date d2 = df.parse( edate );
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		//Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
		c1.setTime( d1 );
		c2.setTime( d2 );
		
		JSONArray jArray = new JSONArray();
		
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
			int temp=0;
			for(int i=0; i<sList.size(); i++){
				if((sList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("success", sList.get(i).getCnt());
					temp=1;
					break;				
				}
			}
			if(temp==0) jsonObject.put("success", 0);
			
			temp=0; // 초기화
			for(int i=0; i<fList.size(); i++){
				if((fList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("fail", fList.get(i).getCnt());
					temp=1;
					break;				
				}
			}
			if(temp==0) jsonObject.put("fail", 0);
			jArray.add(jsonObject);
			c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
		}	
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		
		JSONArray jArray2 = new JSONArray();
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("balloonText", "[[value]]");
		jsonObject2.put("bullet", "round");
		jsonObject2.put("bulletBorderAlpha", 1);
		jsonObject2.put("bulletBorderColor", "#FFFFFF");
		jsonObject2.put("hideBulletsCount", 50);
		jsonObject2.put("lineThickness", 2);
		jsonObject2.put("lineColor", "#1b72cc");
		jsonObject2.put("valueField", "success");
		jsonObject2.put("id", "id1");
		jArray2.add(jsonObject2);
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("balloonText", "[[value]]");
		jsonObject3.put("bullet", "round");
		jsonObject3.put("bulletBorderAlpha", 1);
		jsonObject3.put("bulletBorderColor", "#FFFFFF");
		jsonObject3.put("hideBulletsCount", 50);
		jsonObject3.put("lineThickness", 2);
		jsonObject3.put("lineColor", "#cc1f1b");
		jsonObject3.put("valueField", "fail");
		jsonObject3.put("id", "id2");
		jArray2.add(jsonObject3);
		return_map.put("graphs_value", jArray2);
		
		return return_map;
	}
	
	/**
	 * 접근 현황 테이블
	 * @return 접근 현황 테이블 내용
	 */
	@RequestMapping(value = "/login_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String login_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) {
		ArrayList<NcLog> login = ncStatService.login_tb(sdate, edate, nsu_id);
		
		String strReturn = "<table>";
		
		strReturn += "<tbody class='scroll_container' style='display:block; overflow-y:auto; height:165px; ' >";
		
		for(int i=0; i < login.size(); i++){
			strReturn += "<tr>" +
					"<td class='list-group1'>"+login.get(i).getNsu_id()+"</td>" +
					"<td class='list-group2'>"+login.get(i).getCnt()+"</td>" +
					"<td class='list-group3'>"+login.get(i).getScnt()+"</td>" +
					"<td class='list-group4'>"+(login.get(i).getCnt()-login.get(i).getScnt())+"</td>" +
				"</tr>";
		}
		
		strReturn += "</tbody></table>";
		return strReturn;
	}
	
	/**
	 * 운영환경 변경 차트
	 * @return 운영환경 변경 차트 내용(퓨전차트)
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/config_ct.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object config_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) throws ParseException {
		ArrayList<NcLog> mList =  ncStatService.config_ct(sdate, edate, nsu_id, "MNG");
		ArrayList<NcLog> iList =  ncStatService.config_ct(sdate, edate, nsu_id, "INT");
		ArrayList<NcLog> xList =  ncStatService.config_ct(sdate, edate, nsu_id, "EXT");
		ArrayList<NcLog> eList =  ncStatService.config_ct(sdate, edate, nsu_id, "ETC");
//		model.addAttribute("mList", mList);
//		model.addAttribute("sList", sList);
//		model.addAttribute("eList", eList);
//		model.addAttribute("sdate", sdate);
//		model.addAttribute("edate", edate);
		//Date타입으로 변경
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = df.parse( sdate );
		Date d2 = df.parse( edate );
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		//Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
		c1.setTime( d1 );
		c2.setTime( d2 );
		
		JSONArray jArray = new JSONArray();
		
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
			int temp=0;
			for(int i=0; i<mList.size(); i++){
				if((mList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("관리자", mList.get(i).getCnt());
					temp=1;
					break;				
				}
			}
			if(temp==0) jsonObject.put("관리자", 0);
			
			temp=0; // 초기화
			for(int i=0; i<iList.size(); i++){
				if((iList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("정방향", iList.get(i).getCnt());
					temp=1;
					break;
				}
			}
			if(temp==0) jsonObject.put("정방향", 0);
		if("dual".equals(Config.getInstance().getModel())){	
			temp=0; // 초기화
			for(int i=0; i<xList.size(); i++){
				if((xList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("역방향", xList.get(i).getCnt());
					temp=1;
					break;
				}
			}
			if(temp==0) jsonObject.put("역방향", 0);
		}
			temp=0; // 초기화
			for(int i=0; i<eList.size(); i++){
				if((eList.get(i).getNlm_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("기타", eList.get(i).getCnt());
					temp=1;
					break;
				}
			}
			if(temp==0) jsonObject.put("기타", 0);
			jArray.add(jsonObject);
			c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
		}	
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		return return_map;
	}
	
	/**
	 * 운영환경 변경 테이블
	 * @return 운영환경 변경 테이블 내용
	 */
	@RequestMapping(value = "/config_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String config_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) {
		NcLog config = ncStatService.config_tb(sdate, edate, nsu_id);
		
		String strReturn = "<div id='tab01' class='tabcontent'>"+
	        "<ul>"+
	            "<li><span>관리자 등록 설정</span><span>"+config.getAcc()+"</span></li>"+
	            "<li><span>관리자 접속 설정</span><span>"+config.getAccip()+"</span></li>"+
	            "<li><span>로그인 설정</span><span>"+config.getLogin()+"</span></li>"+
	        "</ul>"+
	    "</div>"+
	    "<div id='tab02' class='tabcontent'>"+
	        "<ul>"+
	            "<li><span>내부 송신 시스템 설정</span><span>"+config.getInts()+"</span></li>"+
	            "<li><span>외부 수신 시스템 설정</span><span>"+config.getExtr()+"</span></li>"+
	            "<li><span>프로그램 등록</span><span>"+config.getFwdpg()+"</span></li>"+
	            "<li><span>자체 보호</span><span>"+config.getFwdig()+"</span></li>"+
	        "</ul>"+
	    "</div>";
	if("dual".equals(Config.getInstance().getModel())){	
		strReturn += "<div id='tab03' class='tabcontent'>"+
	        "<ul>"+
	            "<li><span>외부 송신 시스템 설정</span><span>"+config.getExts()+"</span></li>"+
	            "<li><span>내부 수신 시스템 설정</span><span>"+config.getIntr()+"</span></li>"+
	            "<li><span>프로그램 등록</span><span>"+config.getRvspg()+"</span></li>"+
	            "<li><span>자체 보호</span><span>"+config.getRvsig()+"</span></li>"+
	        "</ul>"+
	    "</div>";
	}
		strReturn += "<div id='tab04' class='tabcontent'>"+
	        "<ul>"+
	            "<li><span>로그 관리</span><span>"+config.getLog()+"</span></li>"+
	            "<li><span>메일 설정</span><span>"+config.getMail()+"</span></li>"+
	            "<li><span>장비소개 관리</span><span>"+config.getProdt()+"</span></li>"+
	            "<li><span>콘텐츠 관리</span><span>"+config.getConts()+"</span></li>"+
	        "</ul>"+
	    "</div>";

		return strReturn;
	}
	
	/**
	 * 정책 변경 차트
	 * @return 정책 변경 차트 내용(퓨전차트)
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/policy_ct.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object policy_ct(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) throws ParseException {
		ArrayList<NcLog> sList =  ncStatService.policy_ct(sdate, edate, nsu_id, "S");
		ArrayList<NcLog> rList =  ncStatService.policy_ct(sdate, edate, nsu_id, "R");
//		model.addAttribute("sList", sList);
//		model.addAttribute("rList", rList);
//		model.addAttribute("sdate", sdate);
//		model.addAttribute("edate", edate);
		//Date타입으로 변경
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = df.parse( sdate );
		Date d2 = df.parse( edate );
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		//Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
		c1.setTime( d1 );
		c2.setTime( d2 );
		
		JSONArray jArray = new JSONArray();
		
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
			int temp=0;
			for(int i=0; i<sList.size(); i++){
				if((sList.get(i).getNlp_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("정방향", sList.get(i).getCnt());
					temp=1;
					break;				
				}
			}
			if(temp==0) jsonObject.put("정방향", 0);
		if("dual".equals(Config.getInstance().getModel())){
			temp=0; // 초기화
			for(int i=0; i<rList.size(); i++){
				if((rList.get(i).getNlp_create_date()).equals(new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()))){
					jsonObject.put("역방향", rList.get(i).getCnt());
					temp=1;
					break;
				}
			}
			if(temp==0) jsonObject.put("역방향", 0);
		}
			jArray.add(jsonObject);
			c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
		}	
		
		HashMap<String, Object> return_map = new HashMap<String, Object>();
		return_map.put("data_value", jArray);
		return return_map;
	}
	
	/**
	 * 정책 변경 테이블
	 * @return 정책 변경 테이블 내용
	 */
	@RequestMapping(value = "/policy_tb.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String policy_tb(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("nsu_id") String nsu_id, Locale locale, Model model) {
		NcLog policy = ncStatService.policy_tb(sdate, edate, nsu_id);
		
		String strReturn = "<table>"+
				"<tbody>"+
				"<tr>" +
					"<td class='list-group1'><div class='board-circle content3-board-circle1'></div>정방향</td>" +
					"<td class='list-group2'>"+policy.getInternal()+"</td>" +
				"</tr>";
//		if("dual".equals(Config.getInstance().getModel())){
//			strReturn += "<tr>" +
//					"<td class='list-group1'><div class='board-circle content3-board-circle2'></div>역방향</td>" +
//					"<td class='list-group2'>"+policy.getExternal()+"</td>" +
//				"</tr>";
//		}
		strReturn += "</tbody>"+
				"</table>";
		
		return strReturn;
	}
	
	/**
	 * 보안접속 통계
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_access.do", method = RequestMethod.GET)
	public String stat_access(Locale locale, Model model) {
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		return "stat/stat_access";
	}
	
	/**
	 * 시스템 동작 현황
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_operate.do", method = RequestMethod.GET)
	public String stat_operate(Locale locale, Model model) {
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		return "stat/stat_operate";
	}
	
	/**
	 * 실시간 이벤트 로그
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/real_event.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String real_event() throws ParseException {
		/*String strReturn = "<table class='table table-striped jambo_table bulk_action' style='font-size:10pt'>";
		strReturn += "<colgroup><col width='15%' /><col width='10%' /><col width='7%' /><col width='16%' /><col width='' /></colgroup>";
		strReturn += "<tbody>";
		
		ArrayList<NdLog> evt =  ncLogService.getRealEvent();
		for(int i=0; i<evt.size(); i++){
			strReturn += "<tr>";
			strReturn += "<td>"+evt.get(i).getNle_create_date()+"</td>";
			strReturn += "<td style='word-break:break-all;'>"+evt.get(i).getNle_host_name()+"</td>";
			strReturn += "<td>"+evt.get(i).getNle_risk_level()+"</td>";
			strReturn += "<td style='word-break:break-all;'>"+(null==evt.get(i).getNcp_name()?"":evt.get(i).getNcp_name())+"</td>";
			strReturn += "<td style='word-break:break-all;text-align:left'>"+evt.get(i).getNle_message()+"</td>";
			strReturn += "</tr>";
		}
		strReturn += "</tbody></table>";

		return strReturn;*/
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<NcLog> evt =  ncLogService.getRealEvent();
		String strReturn = "<table class='log_body'>";
		strReturn += "<colgroup><col width='20%' /><col width='10%' /><col width='20%' /><col width='50%' /></colgroup>";
		String temp="";
		for(int i=0; i<evt.size(); i++){
			temp="";
			if("위험".equals(evt.get(i).getNle_risk_level())) {
				temp="style='color:red'";
			}else if("경고".equals(evt.get(i).getNle_risk_level())) {
				temp="style='color:#f0ad4e'";
			}
			strReturn += "<tr "+temp+">"+
					"<td>"+dt.format(dt.parse(evt.get(i).getNle_create_date()))+"</td>"+
					"<td>"+evt.get(i).getNle_risk_level()+"</td>"+
					"<td>"+(null==evt.get(i).getNcp_name()?"":evt.get(i).getNcp_name())+"</td>"+
					"<td class='none' style='text-align:left;word-break:break-all'>"+evt.get(i).getNle_message()+"</td>"+
				"</tr>";
		}
		strReturn += "</table>";
		return strReturn;
	}
	
	/**
	 * 실시간 접속 통제 로그
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/real_access.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String real_access() throws ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<NcLog> acs =  ncLogService.getRealAccess();
		String strReturn = "<table class='log_body'>";
		strReturn += "<col width='16%' /><col width='10%' /><col width='11%' /><col width='11%' /><col width='15%' /><col width='11%' /><col width='15%' /><col width='11%' />";
		String temp="";
		for(int i=0; i<acs.size(); i++){
			temp="";
			if("경고".equals(acs.get(i).getNla_risk_level())) {
				temp="style='color:red'";
			}
			strReturn += "<tr "+temp+">"+
					"<td>"+dt.format(dt.parse(acs.get(i).getNla_create_date()))+"</td>"+
					"<td>"+acs.get(i).getNla_risk_level()+"</td>"+
					"<td>"+acs.get(i).getNla_access_type()+"</td>"+
					"<td>"+acs.get(i).getNla_div()+"</td>"+
					"<td>"+acs.get(i).getNla_src_ip()+"</td>"+
					"<td>"+acs.get(i).getNla_src_port()+"</td>"+
					"<td>"+acs.get(i).getNla_dst_ip()+"</td>"+
					"<td class='none'>"+acs.get(i).getNla_dst_port()+"</td>"+
				"</tr>";
		}
		strReturn += "</table>";
		return strReturn;
	}
	
	/**
	 * 실시간 관리자 로그
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/real_mng.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String real_mng() throws ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<NcLog> mng =  ncLogService.getRealMng();
		String strReturn = "<table class='log_body'>";
		strReturn += "<col width='12%' /><col width='15%' /><col width='12%' /><col width='16%' /><col width='9%' /><col width='9%' /><col width='' />";
		String temp="";
		for(int i=0; i<mng.size(); i++){
			temp="";
			if("경고".equals(mng.get(i).getNlm_risk_level())) {
				temp="style='color:red'";
			}
			strReturn += "<tr "+temp+">"+
					"<td>"+mng.get(i).getNsu_id()+"</td>"+
					"<td>"+dt.format(dt.parse(mng.get(i).getNlm_create_date()))+"</td>"+
					"<td>"+mng.get(i).getNai_ip()+"</td>"+
					"<td>"+mng.get(i).getNlm_page()+"</td>"+
					"<td>"+mng.get(i).getNlm_result()+"</td>"+
					"<td>"+mng.get(i).getNlm_risk_level()+"</td>"+
					"<td class='none' style='text-align:left'>"+(null==mng.get(i).getNlm_param()?"":mng.get(i).getNlm_param())+"</td>"+
				"</tr>";
		}
		strReturn += "</table>";
		return strReturn;
	}
	
	/**
	 * 시스템 동작 현황
	 * @return
	 */
	@RequestMapping(value = "/active_state.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String active_state() {
		NcStatNw activeState = ncStatService.getActiveState();
		
		String strReturn = "", strReturn2 = "";
		
		if(activeState.getNas_in_line1()==0){
			strReturn += "<div class='tx_server_line gray_line'></div>";
		}else if(activeState.getNas_in_line1()==1){
			strReturn += "<div class='tx_server_line blue_line'></div>";
		}else{
			strReturn += "<div class='tx_server_line red_line'></div>";
		}
		
		if(activeState.getNas_in_line2()==0){
			strReturn += "<div class='tx_server_line gray_line'></div>";
		}else if(activeState.getNas_in_line2()==1){
			strReturn += "<div class='tx_server_line blue_line'></div>";
		}else{
			strReturn += "<div class='tx_server_line red_line'></div>";
		}
		
		if(activeState.getNas_in_line3()==0){
			strReturn += "<div class='tx_server_line gray_line'></div>";
		}else if(activeState.getNas_in_line3()==1){
			strReturn += "<div class='tx_server_line blue_line'></div>";
		}else{
			strReturn += "<div class='tx_server_line red_line'></div>";
		}
		
		if(activeState.getNas_tr_line1()==1){
			strReturn2 += "<div class='tx_rx_line b_arrow'></div>";
		}else{
			strReturn2 += "<div class='rx_tx_line r_arrow'></div>";
		}
		if(activeState.getNas_tr_line2()==1){
			strReturn2 += "<div class='tx_rx_line b_arrow'></div>";
		}else{
			strReturn2 += "<div class='rx_tx_line r_arrow'></div>";
		}
		

		return strReturn+"|"+strReturn2;		
	}
	
	
	/**
	 * 시스템 동작 현황
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stat_action.do", method = RequestMethod.GET)
	public String stat_action(Locale locale, Model model) {
		//장비소개 정보 
		NcProduct getNcProduct = ncProductService.getNcProduct();
		if(getNcProduct!=null){
			if(getNcProduct.getNcpd_desc()!=null){
				getNcProduct.setNcpd_desc(getNcProduct.getNcpd_desc().replaceAll("\n", "</br>"));
			}
		}
		model.addAttribute("getNcProduct", getNcProduct);
		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		return "stat/stat_action";
	}
	
	/**
	 * 내부망 데이터 현재 수집량 
	 * @return 내부망 데이터 최대/현재 수집량 
	 */
	@RequestMapping(value = "/collect_info.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String collect_info(Locale locale, Model model) {
		ArrayList<NcStatNw> collect = ncStatService.collect_info();
		
		String strReturn = "<table style='width:100%;table-layout:fixed;'>";
		strReturn += "<colgroup>"; 
		strReturn += "<col width='3%' /><col width='' /><col width='35%' />";
		strReturn += "</colgroup><tbody>";
		
		if(collect.size()>0){
			for(int i=0; i < collect.size(); i++){
				strReturn += "<tr>";
				strReturn += "<td></td>";
				strReturn += "<td style='text-align:left;'>"+collect.get(i).getNcp_name()+"</td>";
				strReturn += "<td>"+StringUtil.toNumFormat(collect.get(i).getNsr_byte())+"</td>";
				strReturn += "</tr>";
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='3'>현재 데이터를 수집 중인<br>서비스가 없습니다.</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		
		return strReturn;
	}
	
	/**
	 * 일방향 데이터 현재 송신량
	 * @return  일방향 데이터 최대/현재 송신량
	 */
	@RequestMapping(value = "/send_info.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String send_info(Locale locale, Model model) {
		ArrayList<NcStatNw> send = ncStatService.send_info();
		
		String strReturn = "<table style='width:100%;table-layout:fixed;'>";
		strReturn += "<colgroup>"; 
		strReturn += "<col width='3%' /><col width='' /><col width='35%' />";
		strReturn += "</colgroup><tbody>";
		
		if(send.size()>0){
			for(int i=0; i < send.size(); i++){
				strReturn += "<tr>";
				strReturn += "<td></td>";
				strReturn += "<td style='text-align:left;'>"+send.get(i).getNcp_name()+"</td>";
				strReturn += "<td>"+StringUtil.toNumFormat(send.get(i).getNss_byte())+"</td>";
				strReturn += "</tr>";
			}
		}else{
			strReturn += "<tr>";
			strReturn += "<td colspan='3'>현재 데이터를 송신 중인<br>서비스가 없습니다.</td>";
			strReturn += "</tr>";
		}
		
		strReturn += "</tbody></table>";
		
		return strReturn;
	}
	
	/**
	 * master, slave 전송량
	 * @return  master, slave 전송량
	 */
	@RequestMapping(value = "/system_traffic.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String system_traffic(Locale locale, Model model) {
		NcStatMs status = ncStatService.getSendMonitor();
		
		int size = ncStatService.getTotalSendbyte(); // 현재 전송량
		int max_size = ncStatService.getMaxTraffic(); // 최대 전송량
		
		String master_byte="", slave_byte="", temp;
		String master_size="", slave_size="";
		
		// byte를 KB 또는 MB로 변환
		int BASE = 1024, KB = BASE, MB = KB*BASE;
		if(size >= MB) {
            temp = StringUtil.toNumFormat(size/MB) + " Mbyte";
        }else if(size >= KB) {
        	temp = StringUtil.toNumFormat(size/KB) + " Kbyte";
        }else{
        	temp = size + " byte";
        }
        
		if(status.getNsmn_master_out()==0){ // master가 사용중 일때
			master_byte = temp;
			master_size = (size/max_size*100)+"";
		}else if(status.getNsmn_master_out()==1){ // master가 절체중 일때
			master_byte = "<font color='red'>절체</font>";
			master_size = "0";
		}else{ // master가 대기 중일대
			master_byte = "<font color='#f0ad4e'>대기중</font>";
			master_size = "0";
		}
		
		if(status.getNsmn_slave_out()==0){ // slave가 사용중 일때
			slave_byte = temp;
			slave_size = (size/max_size*100)+"";
		}else if(status.getNsmn_slave_out()==1){ // slave가 절체중 일때
			slave_byte = "<font color='red'>절체</font>";
			slave_size = "0";
		}else{ // slave가 대기 중일대
			slave_byte = "<font color='#f0ad4e'>대기중</font>";
			slave_size = "0";
		}
		
		return master_size+"|"+slave_size+"|"+master_byte+"|"+slave_byte;
	}
	
	/**
	 * master, slave 큐 사이즈
	 * @return  master, slave 큐 사이즈
	 */
	@RequestMapping(value = "/queue_size.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String queue_size(Locale locale, Model model) {
		NcStatMs status = ncStatService.getSendMonitor();
		int master_queue = 0;
		int slave_queue = 0;
		
		if (status.getNsmn_master_out()==0) {
			master_queue = status.getNsmn_queue();
		}
		else{
			slave_queue = status.getNsmn_queue();
		}
		
		return master_queue+"|"+slave_queue;
	}
	
	/**
	 * cpu, ram, hdd 사용량 표시
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/system_monitor.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String system_monitor(Locale locale, Model model) {
		NcStatNw system = ncStatService.getSysMonitor();
		return system.getNsm_cpu()+"|"+system.getNsm_ram()+"|"+system.getNsm_hdd();
	}
	
	/**
	 * 대시보드 차트 데이터  1분단위로 차트 데이터를 가져오기
	 * @return retVal
	 * @throws ParseException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/trafficChart.do", method = RequestMethod.POST)
	@ResponseBody
	public Object trafficChart(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("tx_div") String tx_div, @RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {

		Map<String, Object> retVal = new HashMap<String, Object>();

		int txDiv = 1;
//		int rxDiv = 2;
		if(nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv=Integer.parseInt(tx_div);
		}
/*		
		if(nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv=Integer.parseInt(rx_div);
		}
*/
		long lInMaxPackets = 0; // 최대값 IN
		long lOutMaxPackets = 0; // 최대값 OUT
		
		//차트 정보 가져오기 시작
		JSONArray inRecvCtData = new JSONArray();
		JSONArray inSendCtData = new JSONArray();
		JSONArray inDetectData = new JSONArray();
		JSONArray outRecvCtData = new JSONArray();
		JSONArray outSendCtData = new JSONArray();
		JSONArray outDetectData = new JSONArray();

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 00);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 59);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		ArrayList<NcStatNw> recvInDataList = ncDashService.chart_data_recv_in(sdate, edate, shour, ehour, sminute, eminute);
		ArrayList<NcStatNw> sendInDataList = ncDashService.chart_data_send_in(sdate, edate, shour, ehour, sminute, eminute);

		ArrayList<NcStatNw> recvOutDataList = ncDashService.chart_data_recv_out(sdate, edate, shour, ehour, sminute, eminute);
		ArrayList<NcStatNw> sendOutDataList = ncDashService.chart_data_send_out(sdate, edate, shour, ehour, sminute, eminute);
		
		ArrayList<NcStatNw> DetectInDataList = ncDashService.detect_dash_in(sdate, edate, shour, ehour, sminute, eminute);
		ArrayList<NcStatNw> DetectOutDataList = ncDashService.detect_dash_out(sdate, edate, shour, ehour, sminute, eminute);
		
		
		Date now_date = new Date();
		String now_date_str = "";
		String now_time_str = "";
		boolean chkData = false;
		for(int j=0; j<=cnt; j++){
			now_date = c1.getTime();
			now_date_str = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(now_date);
			now_time_str = new java.text.SimpleDateFormat("HH:mm").format(now_date);
			
			
			
			JSONObject inRecv = new JSONObject();
			inRecv.put("category", now_time_str);
			for(int i=0; i<recvInDataList.size(); i++){
				chkData = false;
				if(recvInDataList.get(i).getPs_strc_time().equals(now_date_str)){
					inRecv.put("column", recvInDataList.get(i).getPs_packets());
					chkData = true;
					
					if(lInMaxPackets<recvInDataList.get(i).getPs_packets()) {
						lInMaxPackets = recvInDataList.get(i).getPs_packets();
					}
					break;
				}
			}
			if(!chkData){
				inRecv.put("column",0);
			}
			inRecv.put("color", "#3f81d7");
			inRecvCtData.add(inRecv);

			JSONObject inSend = new JSONObject();
			inSend.put("category", now_time_str);
			for(int i=0; i<sendInDataList.size(); i++){
				chkData = false;
				if(sendInDataList.get(i).getPs_strc_time().equals(now_date_str)){
					inSend.put("column", sendInDataList.get(i).getPs_packets());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				inSend.put("column", 0);
			}
			inSend.put("color", "#3e9180");
			inSendCtData.add(inSend);

			
			
			
			chkData = false;
			JSONObject outRecv = new JSONObject();
			outRecv.put("category", now_time_str);
			for(int i=0; i<recvOutDataList.size(); i++){
				chkData = false;
				if(recvOutDataList.get(i).getPs_strc_time().equals(now_date_str)){
					outRecv.put("column", recvOutDataList.get(i).getPs_packets());
					chkData = true;

					if(lOutMaxPackets<recvOutDataList.get(i).getPs_packets()) {
						lOutMaxPackets = recvOutDataList.get(i).getPs_packets();
					}
					break;
				}
			}
			if(!chkData){
				outRecv.put("column", 0);
			}
			outRecv.put("color", "#3f81d7");
			outRecvCtData.add(outRecv);

			JSONObject outSend = new JSONObject();
			outSend.put("category", now_time_str);
			for(int i=0; i<sendOutDataList.size(); i++){
				chkData = false;
				if(sendOutDataList.get(i).getPs_strc_time().equals(now_date_str)){
					outSend.put("column", sendOutDataList.get(i).getPs_packets());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				outSend.put("column", 0);
			}
			outSend.put("color", "#3e9180");
			outSendCtData.add(outSend);
			
		
			
			
			chkData = false;
			JSONObject inDetect = new JSONObject();
			inDetect.put("category", now_time_str);
			for(int i=0; i<DetectInDataList.size(); i++){
				chkData = false;
				if(DetectInDataList.get(i).getPs_strc_time().equals(now_date_str)){
					inDetect.put("column", DetectInDataList.get(i).getPs_packets());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				inDetect.put("column",0);
			}
			inDetect.put("color", "#cc1f1b");
			inDetectData.add(inDetect);
			
			chkData = false;
			JSONObject outDetect = new JSONObject();
			outDetect.put("category", now_time_str);
			for(int i=0; i<DetectOutDataList.size(); i++){
				chkData = false;
				if(DetectOutDataList.get(i).getPs_strc_time().equals(now_date_str)){
					outDetect.put("column", DetectOutDataList.get(i).getPs_packets());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				outDetect.put("column",0);
			}
			outDetect.put("color", "#cc1f1b");
			outDetectData.add(outDetect);
			
			c1.add(Calendar.SECOND, 60); // 1분 더하기
		}
		//차트 정보 가져오기 끝
		
		//마무리
		retVal.put("inRecvCtData", inRecvCtData);//차트 데이터
		retVal.put("outRecvCtData", outRecvCtData);//차트 데이터
		retVal.put("inSendCtData", inSendCtData);//차트 데이터
		retVal.put("outSendCtData", outSendCtData);//차트 데이터
		retVal.put("inDetectData", inDetectData);//차트 데이터
		retVal.put("outDetectData", outDetectData);//차트 데이터
		retVal.put("inMaxPackets", lInMaxPackets);// In 수집 최대값
		retVal.put("outMaxPackets", lOutMaxPackets);// Out 수집 최대값		

	    return retVal;
	}
	
	/**
	 * 대시보드 차트 데이터  1분단위로 차트 데이터를 가져오기
	 * @return retVal
	 * @throws ParseException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/center_info.do", method = RequestMethod.POST)
	@ResponseBody
	public Object center_info(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("tx_div") String tx_div, @RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {
		
		Map<String, Object> retVal = new HashMap<String, Object>();
		int txDiv = 1;
		int rxDiv = 1;// 양방향에서는 2이지만 현재 단방향이라 1로 변경
		if(nnsp.util.StringUtil.checkNumber(tx_div)){
			txDiv=Integer.parseInt(tx_div);
		}
		if(nnsp.util.StringUtil.checkNumber(rx_div)){
			rxDiv=Integer.parseInt(rx_div);
		}
		
//		NcConfig arrowInfo = ncDashService.arrow_info(txDiv);
//		String arrow = "<img class='arrow_in' src='/img/master_in.png'><img class='product_cds' src='/img/CDS_prod_img.gif'><img class='arrow_out' src='/img/master_out.png'>";
//		String arrow = "";
//		if(arrowInfo.getNcs_arrow()==0){ // 둘다 절체
//			arrow = "<img src='/img/master02.png'><img src='/img/slave02.png'>";
//		}else if(arrowInfo.getNcs_arrow()==1){ // master가 절체
//			arrow = "<img src='/img/master02.png'><img src='/img/slave01.png'>";
//		}else if(arrowInfo.getNcs_arrow()==2){ // slave가 절체
//			arrow = "<img src='/img/master01.png'><img src='/img/slave02.png'>";
//		}else if(arrowInfo.getNcs_arrow()==3){ // 둘다 절체 아님. master가 기본
//			arrow = "<img src='/img/master01.png'><img src='/img/slave02.png'>";
//		}
		
//		long txbyte = ncDashService.tx_byte_info(sdate, edate, shour, ehour, sminute, eminute, txDiv);
//		long rxbyte = ncDashService.rx_byte_info(sdate, edate, shour, ehour, sminute, eminute, rxDiv);
		
		ArrayList<NcWhList> whiteList = ncWhService.getWhiteList();
		
		long inPackets = 0;
		long outPackets = 0;
		
		for(int i=0; i<whiteList.size(); i++) {
			long wl_id = whiteList.get(i).getWl_id();
			inPackets += ncDashService.getCenterChartData(sdate, edate, shour, ehour, sminute, eminute, wl_id, 0, 1).getTotal_packets();
			outPackets += ncDashService.getCenterChartData(sdate, edate, shour, ehour, sminute, eminute, wl_id, 1, 1).getTotal_packets();
		}
		
		String inpacket = StringUtil.numCalculation((double)inPackets);
		String outpacket = StringUtil.numCalculation((double)outPackets);
		
//		int txcnt = ncDashService.tx_svc_cnt(txDiv);
		
				
//		retVal.put("mainarrow", arrow);//절체상태에 따른 이미지표시
		
//		retVal.put("txbyte", txByteNunit[0]);// tx 전송량
//		retVal.put("tx_byte_unit", txByteNunit[1]);// tx 용량 단위
//		retVal.put("txcnt", txcnt);//정책 갯수
//		retVal.put("rxbyte", rxByteNunit[0]);//rx 수신량
//		retVal.put("rx_byte_unit", rxByteNunit[1]);// tx 용량 단위
//		retVal.put("rxcnt", txcnt);//정책 갯수
		
			retVal.put("txbyte", inpacket);// tx 전송량
			retVal.put("tx_byte_unit", "Packets");// tx 용량 단위
			retVal.put("txcnt", whiteList.size());//정책 갯수
			retVal.put("rxbyte", outpacket);//rx 수신량
			retVal.put("rx_byte_unit", "Packets");// tx 용량 단위
			retVal.put("rxcnt", whiteList.size());//정책 갯수
			
		return retVal;
	}
	
	/**
	 * 대시보드 차트 데이터  1분단위로 차트 데이터를 가져오기
	 * @return retVal
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/tx_proc.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public Object tx_proc(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("tx_div") String tx_div, HttpSession session) throws ParseException {

		String icon="";
		String nodeColor="";
		String status="";

		String statusBeginFormatStr = "<span class=\'%s\'>";
		String imgSrcFormatStr = "<img src=\'/img/main_icon/%s\' alt=\'%s\'>";
		String serviceNameFormatStr = "<span class=\'service-name\'>%s</span>";
		String bytesFormatStr = "<span class=\'bytes\'>%s%s</span>";
		String byteUnitFormatStr = "<span class=\'byte-unit\'>%s</span>";
		String hiddenFormatStr = "<input type=\'hidden\' value=%d>";
		String StatusEndStr = "</span>";
		String nodeColorFormatStr = "%s";

		JSONArray ja = new JSONArray();
//		ArrayList<NcPolicyLink> policyList = ncPolicyLinkService.getPolicyService(); //정책 리스트
		
		ArrayList<NcWhList> whiteList = ncWhService.getWhiteList();

		// gradation end color 지정
		// just for color, as properties are taken from data object where the name is first mentioned.
		JSONObject jo = new JSONObject();
		jo.put("from", "Z");
//		jo.put("color", "rgba(31, 1, 79, 0.4)");
		jo.put("color", "#5d6165");
		ja.add(jo);
		
		for (int i = 0;i < whiteList.size() && i < 10;i++) {
			jo = new JSONObject();
			
			NcWhList wl = whiteList.get(i);
			String wl_name = wl.getWl_name();
			long wl_id = wl.getWl_id();
			int policyStatus = wl.getWl_use_yn();
			
			NcStatNw ncStatNw = ncDashService.getCenterChartData(sdate, edate, shour, ehour, sminute, eminute, wl_id, 0, 0);
			long txSSum = ncStatNw.getTotal_packets();

			String tBytes = StringUtil.numCalculation((double)txSSum);
			String[] byteNunit = null;
			if(txSSum > 1000) {
				byteNunit = tBytes.split(" ");
			}
			
			icon = (policyStatus == 1) ? "main_syslog11_b.png" : "main_syslog13_g.png";
			status = (policyStatus == 1) ? "normal" : "inactive";
			nodeColor = (policyStatus == 1) ? "rgba(90, 176, 214, 1.0)" : "#5d6165";
			
			if(txSSum > 1000) {
				jo.put("from", String.format(statusBeginFormatStr, status) +
						String.format(imgSrcFormatStr, icon, wl_name) +
						String.format(serviceNameFormatStr, wl_name) +
						(String.format(bytesFormatStr, byteNunit[0],
						String.format(byteUnitFormatStr, byteNunit[1]))) +
						String.format(hiddenFormatStr, (i + 1)) +
						StatusEndStr);
			}
			else {
				jo.put("from", String.format(statusBeginFormatStr, status) +
						String.format(imgSrcFormatStr, icon, wl_name) +
						String.format(serviceNameFormatStr, wl_name) +
						(String.format(bytesFormatStr, tBytes,
						String.format(byteUnitFormatStr, ""))) +
						String.format(hiddenFormatStr, (i + 1)) +
						StatusEndStr);
			}
			
			
			
			
			
			
			
//			NcPolicyLink pl = policyList.get(i);
//			String npl_name = pl.getNpl_name();
//			String nts_name = pl.getNpl_tx_nts_name();
//			int useYN = pl.getNpl_use_yn();
//			int failStatus = pl.getNpl_stat();
//			int nplNo = pl.getNpl_no();

//			switch (nts_name) {
//				case "DBMS":
//					if (failStatus != 1) { icon = "main_database12_r.png"; break; }
//					icon = (useYN == 0) ? "main_database13_g.png" : "main_database11_b.png";
//					break;
//				case "OPC":
//					if (failStatus != 1) { icon = "main_opc12_r.png"; break; }
//					icon = (useYN == 0) ? "main_opc13_g.png" : "main_opc11_b.png";
//					break;
//				case "Ftp/Sftp":
//					if (failStatus != 1) { icon = "main_ftp12_r.png"; break; }
//					icon = (useYN == 0) ? "main_ftp13_g.png" : "main_ftp11_b.png";
//					break;
//				default:
//					if (failStatus != 1) { icon = "main_syslog12_r.png"; break; }
//					icon = (useYN == 0) ? "main_syslog13_g.png" : "main_syslog11_b.png";
//			}
			
//			long txSSum = ncDashService.nss_byte_sum(sdate, edate, shour, ehour, sminute, eminute, "00", "59", nplNo);
//			String tBytes = StringUtil.byteCalculation((double)txSSum);
//			String[] byteNunit = tBytes.split(" ");

//			if (failStatus != 1) {
//				status = "failure";
//				nodeColor = "#ef3a20";
//			}
//			else {
//				status = (useYN == 1) ? "normal" : "inactive";
//				nodeColor = (useYN == 1) ? "rgba(90, 176, 214, 1.0)" : "#5d6165";
//			}

//			jo.put("from", String.format(statusBeginFormatStr, status) +
//					String.format(imgSrcFormatStr, icon, nts_name) +
//					String.format(serviceNameFormatStr, ngl_group_name) +
//					(String.format(bytesFormatStr, byteNunit[0],
//					String.format(byteUnitFormatStr, byteNunit[1]))) +
//					String.format(hiddenFormatStr, (i + 1)) +
//					StatusEndStr);
			jo.put("to", "Z");
			jo.put("value", 0);
			jo.put("wl_name", wl_name);
			jo.put("wl_id", wl_id);
			jo.put("color", String.format(nodeColorFormatStr, nodeColor));

			ja.add(jo);
		}

		return ja.toString();
	}
	
	/**
	 * 대시보드 차트 데이터  1분단위로 차트 데이터를 가져오기
	 * @return retVal
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/rx_proc.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public Object rx_proc(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("rx_div") String rx_div, HttpSession session) throws ParseException {

		String icon="";
		String nodeColor="";
		String status="";

		String statusBeginFormatStr = "<span class=\'%s\'>";
		String serviceNameFormatStr = "<span class=\'service-name\'>%s</span>";
		String bytesFormatStr = "<span class=\'bytes\'>%s%s</span>";
		String byteUnitFormatStr = "<span class=\'byte-unit\'>%s</span>";
		String imgSrcFormatStr = "<img src=\'/img/main_icon/%s\' alt=\'%s\'>";
		String hiddenFormatStr = "<input type=\'hidden\' value=%d>";
		String StatusEndStr = "</span>";
		String nodeColorFormatStr = "%s";

		JSONArray ja = new JSONArray();
//		ArrayList<NcPolicyLink> policyList = ncPolicyLinkService.getPolicyService(); //정책 리스트
		
		ArrayList<NcWhList> whiteList = ncWhService.getWhiteList();


		// gradation end color 지정
		// just for color, as properties are taken from data object where the name is first mentioned.
		JSONObject jo = new JSONObject();
		jo.put("from", "Z");
//		jo.put("color", "rgba(1, 60, 79, 0.4)");
		jo.put("color", "#5d6165");
		ja.add(jo);

		for(int i = 0;i < whiteList.size() && i < 10;i++){
			jo = new JSONObject();
			
			NcWhList wl = whiteList.get(i);
			String wl_name = wl.getWl_name();
			long wl_id = wl.getWl_id();
			int policyStatus = wl.getWl_use_yn();
			
			NcStatNw ncStatNw = ncDashService.getCenterChartData(sdate, edate, shour, ehour, sminute, eminute, wl_id, 1, 0);
			long txSSum = ncStatNw.getTotal_packets();
			
			String tBytes = StringUtil.numCalculation((double)txSSum);
			String[] byteNunit = null;
			if(txSSum > 1000) {
				byteNunit = tBytes.split(" ");
			}
			
			icon = (policyStatus == 1) ? "main_syslog11_g.png" : "main_syslog13_g.png";
			status = (policyStatus == 1) ? "normal" : "inactive";
			nodeColor = (policyStatus == 1) ? "rgba(110, 177, 176, 1.0)" : "#5d6165";
			if(txSSum > 1000) {
				jo.put("from", "Z");
				jo.put("to", String.format(statusBeginFormatStr, status) +
						String.format(imgSrcFormatStr, icon, wl_name) +
						String.format(serviceNameFormatStr, wl_name) +
						(String.format(bytesFormatStr, byteNunit[0],
						String.format(byteUnitFormatStr, byteNunit[1]))) +
						String.format(hiddenFormatStr, (i + 1)) +
						StatusEndStr);
			}
			else {
				jo.put("from", "Z");
				jo.put("to", String.format(statusBeginFormatStr, status) +
						String.format(imgSrcFormatStr, icon, wl_name) +
						String.format(serviceNameFormatStr, wl_name) +
						(String.format(bytesFormatStr, tBytes,
						String.format(byteUnitFormatStr, ""))) +
						String.format(hiddenFormatStr, (i + 1)) +
						StatusEndStr);
			}

//			NcPolicyLink pl = policyList.get(i);
//			String npl_name = pl.getNpl_name();
//			String nts_name = pl.getNpl_rx_nts_name();
//			int useYN = pl.getNpl_use_yn();
//			int failStatus = pl.getNpl_stat();
//			int nplNo = pl.getNpl_no();
//
//			switch (nts_name) {
//				case "DBMS":
//					if (failStatus != 1) { icon = "main_database12_r.png"; break; }
//					icon = (useYN == 0) ? "main_database13_g.png" : "main_database11_b.png";
//					break;
//				case "OPC":
//					if (failStatus != 1) { icon = "main_opc12_r.png"; break; }
//					icon = (useYN == 0) ? "main_opc13_g.png" : "main_opc11_b.png";
//					break;
//				case "Ftp/Sftp":
//					if (failStatus != 1) { icon = "main_ftp12_r.png"; break; }
//					icon = (useYN == 0) ? "main_ftp13_g.png" : "main_ftp11_b.png";
//					break;
//				default:
//					if (failStatus != 1) { icon = "main_syslog12_r.png"; break; }
//					icon = (useYN == 0) ? "main_syslog13_g.png" : "main_syslog11_g.png";
//			}
//			
//			long rxRSum = ncDashService.nsr_byte_sum(sdate, edate, shour, ehour, sminute, eminute, "00", "59", nplNo);
//			String tBytes = StringUtil.byteCalculation((double)rxRSum);
//			String[] byteNunit = tBytes.split(" ");
//
//			if (failStatus != 1) {
//				status = "failure";
//				nodeColor = "#ef3a20";
//			}
//			else {
//				status = (useYN == 1) ? "normal" : "inactive";
//				nodeColor = (useYN == 1) ? "rgba(110, 177, 176, 1.0)" : "#5d6165";
//			}
//
//			jo.put("from", "Z");
//			jo.put("to", String.format(statusBeginFormatStr, status) +
//					String.format(serviceNameFormatStr, npl_name) +
//					(String.format(bytesFormatStr, byteNunit[0],
//					String.format(byteUnitFormatStr, byteNunit[1]))) +
//					String.format(imgSrcFormatStr, icon, nts_name) +
//					String.format(hiddenFormatStr, (i + 1)) +
//					StatusEndStr);
			jo.put("value", 0);
			jo.put("wl_name", wl_name);
			jo.put("wl_id", wl_id);
			jo.put("color", String.format(nodeColorFormatStr, nodeColor));

			ja.add(jo);
		}
		
		return ja.toString();
	}
	
	/**
	 * 대시보드에서 정책이미지 클릭하면 나오는 팝업
	 * @param nlp_seq - 정책 seq
	 * @return
	 */
	@RequestMapping(value = "/service_txpopup.do")
	public String service_txpopup(@RequestParam("nlp_seq") int nlp_seq, @RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		ArrayList<NcLinkPolicy> routeList = ncLinkPolicyService.getPolicyRoute(ncPolicy.getNlp_seq());
		ncPolicy.setNpr_source_ip(routeList.get(0).getNpr_source_ip());
		ncPolicy.setNpr_destination_ip(routeList.get(0).getNpr_destination_ip());
		ncPolicy.setNpr_destination_port(routeList.get(0).getNpr_destination_port());
		model.addAttribute("ncPolicy", ncPolicy);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("shour", shour);
		model.addAttribute("ehour", ehour);
		model.addAttribute("sminute", sminute);
		model.addAttribute("eminute", eminute);
		return "stat/service_txpopup";
	}
	
	/**
	 * 대시보드에서 정책이미지 클릭하면 나오는 팝업
	 * @param nlp_seq - 정책 seq
	 * @return
	 */
	@RequestMapping(value = "/service_rxpopup.do")
	public String service_rxpopup(@RequestParam("nlp_seq") int nlp_seq, @RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute, Model model) {
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		ArrayList<NcLinkPolicy> routeList = ncLinkPolicyService.getPolicyRoute(ncPolicy.getNlp_seq());
		ncPolicy.setNpr_source_ip(routeList.get(0).getNpr_source_ip());
		ncPolicy.setNpr_destination_ip(routeList.get(0).getNpr_destination_ip());
		ncPolicy.setNpr_destination_port(routeList.get(0).getNpr_destination_port());
		model.addAttribute("ncPolicy", ncPolicy);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("shour", shour);
		model.addAttribute("ehour", ehour);
		model.addAttribute("sminute", sminute);
		model.addAttribute("eminute", eminute);
		return "stat/service_rxpopup";
	}
	
	/**
	 * 팝업에서 통계
	 * @return retVal
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/trafficPopTx.do", method = RequestMethod.POST)
	@ResponseBody
	public Object trafficPopTx(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("ncp_file_name") String ncp_file_name, HttpSession session) throws ParseException {

		Map<String, Object> retVal = new HashMap<String, Object>();

		//차트 정보 가져오기 시작
		JSONArray recvCtData = new JSONArray();
		JSONArray sendCtData = new JSONArray();
		JSONArray repeBtCtData = new JSONArray();
		JSONArray repeYnCtData = new JSONArray();

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 00);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 59);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		ArrayList<NcStatNw> recvTxDataList = ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, "00", "59", ncp_file_name);
		ArrayList<NcStatNw> sendTxDataList = ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, "00", "59", ncp_file_name);

		ArrayList<NcStatNw> repeDataList = ncDashService.chart_data_repe(sdate, edate, shour, ehour, sminute, eminute, "00", "59", ncp_file_name);
		
		Date now_date = new Date();
		String now_date_str = "";
		String now_time_str = "";
		boolean chkData = false;
		for(int j=0; j<=cnt; j++){
			now_date = c1.getTime();
			now_date_str = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(now_date);
			now_time_str = new java.text.SimpleDateFormat("HH:mm").format(now_date);
			
			JSONObject recv = new JSONObject();
			recv.put("category", now_time_str);
			for(int i=0; i<recvTxDataList.size(); i++){
				chkData = false;
				if(recvTxDataList.get(i).getNsr_date().equals(now_date_str)){
					recv.put("column", recvTxDataList.get(i).getNsr_byte());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				recv.put("column",0);
			}
			recv.put("color", "#3f81d7");
			recvCtData.add(recv);

			JSONObject send = new JSONObject();
			send.put("category", now_time_str);
			for(int i=0; i<sendTxDataList.size(); i++){
				chkData = false;
				if(sendTxDataList.get(i).getNss_date().equals(now_date_str)){
					send.put("column", sendTxDataList.get(i).getNss_byte());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				send.put("column", 0);
			}
			send.put("color", "#3e9180");
			sendCtData.add(send);

			JSONObject repeByte = new JSONObject();
			JSONObject repeLoss = new JSONObject();
			repeByte.put("category", now_time_str);
			repeLoss.put("category", now_time_str);
			for(int i=0; i<repeDataList.size(); i++){
				chkData = false;
				if(repeDataList.get(i).getNsrp_date().equals(now_date_str)){
					repeByte.put("column", repeDataList.get(i).getNsrp_byte());
					repeLoss.put("column", repeDataList.get(i).getNsrp_loss_yn());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				repeByte.put("column",0);
				repeLoss.put("column",0);
			}
			repeByte.put("color", "#cd5a1b");
			repeLoss.put("color", "#cc1f1b");
			repeBtCtData.add(repeByte);
			repeYnCtData.add(repeLoss);
			
			c1.add(Calendar.SECOND, 60); // 1분 더하기
		}
		//차트 정보 가져오기 끝
		
		//마무리
		retVal.put("recvCtData", recvCtData);//차트 데이터
		retVal.put("sendCtData", sendCtData);//차트 데이터
		retVal.put("repeBtCtData", repeBtCtData);//차트 데이터
		retVal.put("repeYnCtData", repeYnCtData);//차트 데이터
	    
	    return retVal;
	}
	
	/**
	 * 팝업에서 통계
	 * @return retVal
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/trafficPopRx.do", method = RequestMethod.POST)
	@ResponseBody
	public Object trafficPopRx(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("ncp_file_name") String ncp_file_name, HttpSession session) throws ParseException {

		Map<String, Object> retVal = new HashMap<String, Object>();

		//차트 정보 가져오기 시작
		JSONArray recvCtData = new JSONArray();
		JSONArray sendCtData = new JSONArray();

		Calendar c1 = Calendar.getInstance();
		String temp[] = sdate.split("-");
		c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1, Integer.parseInt(temp[2]), Integer.parseInt(shour), Integer.parseInt(sminute), 00);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		String temp1[] = edate.split("-");
		c2.set(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])-1, Integer.parseInt(temp1[2]), Integer.parseInt(ehour), Integer.parseInt(eminute), 59);
		c2.set(Calendar.MILLISECOND, 0);
		
		long difference = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long cnt = difference/60;
		
		ArrayList<NcStatNw> recvTxDataList = ncDashService.chart_data_recv(sdate, edate, shour, ehour, sminute, eminute, "00", "59", ncp_file_name);
		ArrayList<NcStatNw> sendTxDataList = ncDashService.chart_data_send(sdate, edate, shour, ehour, sminute, eminute, "00", "59", ncp_file_name);
		
		Date now_date = new Date();
		String now_date_str = "";
		String now_time_str = "";
		boolean chkData = false;
		for(int j=0; j<=cnt; j++){
			now_date = c1.getTime();
			now_date_str = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(now_date);
			now_time_str = new java.text.SimpleDateFormat("HH:mm").format(now_date);
			
			JSONObject recv = new JSONObject();
			recv.put("category", now_time_str);
			for(int i=0; i<recvTxDataList.size(); i++){
				chkData = false;
				if(recvTxDataList.get(i).getNsr_date().equals(now_date_str)){
					recv.put("column", recvTxDataList.get(i).getNsr_byte());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				recv.put("column",0);
			}
			recv.put("color", "#3f81d7");
			recvCtData.add(recv);

			JSONObject send = new JSONObject();
			send.put("category", now_time_str);
			for(int i=0; i<sendTxDataList.size(); i++){
				chkData = false;
				if(sendTxDataList.get(i).getNss_date().equals(now_date_str)){
					send.put("column", sendTxDataList.get(i).getNss_byte());
					chkData = true;
					break;
				}
			}
			if(!chkData){
				send.put("column", 0);
			}
			send.put("color", "#3e9180");
			sendCtData.add(send);
			
			c1.add(Calendar.SECOND, 60); // 1분 더하기
		}
		//차트 정보 가져오기 끝
		
		//마무리
		retVal.put("recvCtData", recvCtData);//차트 데이터
		retVal.put("sendCtData", sendCtData);//차트 데이터
	    
	    return retVal;
	}
	
	/**
	 * 검색기간 분단위 차트 가져오기 Ajax
	 * @return  Object
	 */
	@RequestMapping(value = "/getChartDataJSON.do", method = RequestMethod.POST)
	@ResponseBody
	public Object getChartDataJSON(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("inout") String inout, HttpSession session) {
		
		int iInout=0;
		if(inout!=null && nnsp.util.StringUtil.checkNumber(inout)) {
			iInout = Integer.parseInt(inout);
		}
		ArrayList<NcStatNw> svcInDataList = ncDashService.chart_data_svc_in(sdate, edate, shour, ehour, sminute, eminute, iInout);
		
		return ncStatService.getChartDataJSON(svcInDataList);
	}
	
	/**
	 * 검색기간 분단위 차트 가져오기 Ajax
	 * @return  Object
	 */
	@RequestMapping(value = "/getThreatChartDataJSON.do", method = RequestMethod.POST)
	@ResponseBody
	public Object getThreatChartDataJSON(@RequestParam("sdate") String sdate, @RequestParam("edate") String edate, @RequestParam("shour") String shour,
			@RequestParam("ehour") String ehour, @RequestParam("sminute") String sminute, @RequestParam("eminute") String eminute,
			@RequestParam("inout") String inout, HttpSession session) {
		
		int iInout=0;
		if(inout!=null && nnsp.util.StringUtil.checkNumber(inout)) {
			iInout = Integer.parseInt(inout);
		}
		ArrayList<NcStatNw> threatDataList = ncDashService.getThreatChartData(sdate, edate, shour, ehour, sminute, eminute, iInout);
		
		return ncStatService.getThreatChartDataJSON(threatDataList);
	}
}