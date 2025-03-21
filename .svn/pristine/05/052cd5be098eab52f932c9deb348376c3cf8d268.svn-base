//package nnsp.controllers;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import nnsp.common.PageInfo;
//import nnsp.services.NcAuditService;
//import nnsp.services.NcMenuService;
//import nnsp.services.NcWhService;
//import nnsp.services.NdrAnalyzeService;
//import nnsp.vo.NcWhList;
//import nnsp.vo.NdrPs;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import net.sf.json.JSONObject;
//
//
//@Controller
//public class NdrAnalyzeController {
//
//	@Autowired
//	NcAuditService ncAuditService;
//	@Autowired
//	NdrAnalyzeService ndrAnalyzeService;
//	@Autowired
//	NcWhService ncWhService;
//	@Autowired
//	NcMenuService ncMenuService;
//	
//	@SuppressWarnings("unused")
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	/**
//	 * ICS Protocols List
//	 * @param locale
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/ics_anomaly_list.do")
//	public String ics_anomaly_list(HttpSession session
//			, @RequestParam(value="date", required=false) String date
//			, @RequestParam(value="page", required=false) String page
//			, Model model) {
//
//		NdrPs ndrPs = new NdrPs();
//		
//		if(date==null || !nnsp.util.StringUtil.checkDate2(date)) {
//			date = nnsp.util.StringUtil.getStrDay(0);
//		}
//		
//		ndrPs.setPs_strc_stime(date+" 00:00:00");
//		ndrPs.setPs_strc_etime(date+" 23:59:59");
//		if(page!=null && nnsp.util.StringUtil.checkNumber(page)) {
//			ndrPs.setPage(Integer.parseInt(page));
//		}
//
//		model.addAttribute("date", date);
//		model.addAttribute("page", ndrPs.getPage());
//		
//
//		//   packets List
//		int cpage=1; 
//		int total = ndrAnalyzeService.getICSPacketCnt(ndrPs);
//		if( ndrPs.getPage() > 0) {
//			cpage = ndrPs.getPage();
//		}
//		
//		PageInfo<NdrPs> pginfo = new PageInfo<NdrPs>(total, cpage);
//		pginfo.setResult(ndrAnalyzeService.getICSPacketList(pginfo.getCurrentPage(), pginfo.getPerArticle(), ndrPs));
//		
//		model.addAttribute("pginfo", pginfo);
//		model.addAttribute("scriptName", "loadList");
//		model.addAttribute("totalCnt", total);
//				
//		return "analyze/ics_anomaly_list";
//	}
//	
//	/**
//	 * 패킷 정보로 화이트 리스트 등록
//	 * @return wnEdnpoint
//	 */
//	@RequestMapping(value = "/regWhitePs_grid.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public Object regWhitePs_grid(HttpSession session,
//			@RequestParam(value="arrayAddWhiteICS") String[] arrayAddWhiteICS,
//			@RequestParam(value="mps_strc_etime") String mps_strc_etime){
//		int regCnt = 0;
//		for(int i=0;i<arrayAddWhiteICS.length; i++){
//			
//			NdrPs ndrPs = ndrAnalyzeService.getPsById(mps_strc_etime, arrayAddWhiteICS[i]);
//			if(ndrPs!=null&&ndrPs.getPs_id()>0) {
//				
//				if(ncWhService.getWhRuleCnt(arrayAddWhiteICS[i], mps_strc_etime)>0) {
//					ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//							," 이미 White List에 등록된 ICS 패킷 정보입니다."
//							,"F","I"); // 정책로그 저장
//				}
//				else {				
//					int result = ncWhService.insertWhProcInfo(arrayAddWhiteICS[i], mps_strc_etime);
//					
//					if (result > 0) {
//						regCnt += result;
//						ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//								,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//									+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//									+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//								,"S","I"); // 정책로그 저장
//					} else {
//						ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//								,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//									+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//									+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//								,"F","I"); // 정책로그 저장
//					}
//				}
//			}
//			else {
//				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//						," ICS 패킷 정보를 조회하지 못했습니다."
//						,"F","I"); // 정책로그 저장
//			}
//		}
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("regCnt", regCnt);
//		
//		return jsonResult;
//	}
//	
//	/**
//	 * 패킷 정보로 화이트 리스트 등록
//	 * @return wnEdnpoint
//	 */
//	@RequestMapping(value = "/regWhitePs.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public Object regWhitePs(HttpServletRequest req, HttpSession session){
//		
//		String strPs_id[] = req.getParameterValues("ps_id[]");
//		String mps_strc_etime = req.getParameter("mps_strc_etime");
//		int regCnt = 0;
//		for(int i=0;i<strPs_id.length; i++){
//			
//			NdrPs ndrPs = ndrAnalyzeService.getPsById(mps_strc_etime, strPs_id[i]);
//			if(ndrPs!=null&&ndrPs.getPs_id()>0) {
//				
//				if(ncWhService.getWhRuleCnt(strPs_id[i], mps_strc_etime)>0) {
//					ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//							," 이미 White List에 등록된 ICS 패킷 정보입니다."
//							,"F","I"); // 정책로그 저장
//				}
//				else {				
//					int result = ncWhService.insertWhProcInfo(strPs_id[i], mps_strc_etime);
//					
//					if (result > 0) {
//						regCnt += result;
//						ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//								,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//									+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//									+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//								,"S","I"); // 정책로그 저장
//					} else {
//						ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//								,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//									+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//									+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//								,"F","I"); // 정책로그 저장
//					}
//				}
//			}
//			else {
//				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 등록"
//						," ICS 패킷 정보를 조회하지 못했습니다."
//						,"F","I"); // 정책로그 저장
//			}
//		}
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("regCnt", regCnt);
//		
//		return jsonResult;
//	}
//	
//	/**
//	 * 패킷 정보로 화이트 리스트 삭제
//	 * @return wnEdnpoint
//	 */
//	@RequestMapping(value = "/delWhitePs.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public Object delWhitePs(HttpSession session
//			, @RequestParam(value="ps_id", required=false) String ps_id
//			, @RequestParam(value="date", required=false) String date){
//		
//		int regCnt = 0;
//		NdrPs ndrPs = ndrAnalyzeService.getPsById(date+" 00:00:00", ps_id);
//		if(ndrPs!=null&&ndrPs.getPs_id()>0 && ndrPs.getPs_white_rule()!=null && !ndrPs.getPs_white_rule().equals("")) {
//			regCnt = ncWhService.delWhProcInfo(ndrPs.getPs_white_rule());
//			if (regCnt > 0) {
//				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 삭제"
//						,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//							+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//							+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//						,"S","I"); // 정책로그 저장
//			} else {
//				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 삭제"
//						,"Protocol = "+ndrPs.getPs_proto_sub_dec()
//							+",출발지 = "+((ndrPs.getPs_src_ip()!=null&&!ndrPs.getPs_src_ip().equals(""))?ndrPs.getPs_src_ip():ndrPs.getPs_src_mac())
//							+",도착지 = "+((ndrPs.getPs_dst_ip()!=null&&!ndrPs.getPs_dst_ip().equals(""))?ndrPs.getPs_dst_ip():ndrPs.getPs_dst_mac())
//						,"F","I"); // 정책로그 저장
//			}
//		}
//		else {
//			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 삭제"
//					," ICS 패킷 정보를 조회하지 못했습니다."
//					,"F","I"); // 정책로그 저장
//		}
//				
//					
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("regCnt", regCnt);
//		
//		return jsonResult;
//	}
//	
//	/**
//	 * 수신 서비스 정책 등록 호출
//	 * 
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/white_list.do")
//	public String white_list(Model model 
//			, @RequestParam(value="page",required=false) String page) {
//
//		NcWhList ncWhList = new NcWhList();
//		
//		model.addAttribute("page", page);
//		
//		int cpage=1; 
//		int total = ncWhService.getWhCnt(ncWhList);
//		if( page != null && page.length() > 0) {
//			try{
//				cpage = Integer.parseInt(page);
//			}catch(NumberFormatException e){
//				cpage = 1;
//			}
//		}
//		
//		PageInfo<NcWhList> pginfo = new PageInfo<NcWhList>(total, cpage);
//		pginfo.setResult(ncWhService.getWhList(pginfo.getCurrentPage(), pginfo.getPerArticle(), ncWhList));
//		model.addAttribute("pginfo", pginfo);
//		model.addAttribute("scriptName", "loadWhiteList");
//		return "analyze/white_list";			
//	}
//	
//	/**
//	 * white list 상세 팝업
//	 * 
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/white_popup.do", method = RequestMethod.POST)
//	public String white_popup(HttpSession session, Model model, @RequestParam(value="wl_id",required=false) String wl_id) {
//		long lingWl_id=0;
//		if(wl_id!=null && nnsp.util.StringUtil.checkNumber(wl_id)) {
//			lingWl_id = Long.parseLong(wl_id);
//		}
//		NcWhList ncWhList = ncWhService.getWhListId(lingWl_id);
//		model.addAttribute("ncWhList", ncWhList);
//		return "analyze/white_popup";
//	}
//
//	/**
//	 * 패킷 정보로 화이트 리스트 등록
//	 * @return wnEdnpoint
//	 */
//	@RequestMapping(value = "/deleteWhite.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public Object deleteWhite(HttpServletRequest req, HttpSession session){
//		
//		String strWl_id[] = req.getParameterValues("wl_id[]");
//		int delCnt = 0;
//		
//		for(int i=0;i<strWl_id.length; i++){
//			if(strWl_id[i]!=null && nnsp.util.StringUtil.checkNumber(strWl_id[i])){
//				int wl_id = Integer.parseInt(strWl_id[i]);
//				if(wl_id > 0){
//					NcWhList tmpWl = ncWhService.getWhListId(wl_id);
//					if(tmpWl!=null&&tmpWl.getWl_id()>0) {
//						int result = ncWhService.delWhId(wl_id);
//						
//						if (result > 0) {
//							ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 삭제"
//									,"Whilte List ID = "+strWl_id[i]+",프로토콜 = "+tmpWl.getWl_proto_sub_dec()
//										+ ",출발지 IP = "+tmpWl.getWl_src_ip()+",도착지 IP"+tmpWl.getWl_dst_ip()
//									,"S","I"); // 정책로그 저장
//						} else {
//							ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 삭제"
//									,"Whilte List ID = "+strWl_id[i]+",프로토콜 = "+tmpWl.getWl_proto_sub_dec()
//										+ ",출발지 IP = "+tmpWl.getWl_src_ip()+",도착지 IP"+tmpWl.getWl_dst_ip()
//									,"F","I"); // 정책로그 저장
//						}
//						delCnt += result;
//					}
//					else {
//						ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 삭제",
//								"Whilte List ID = "+strWl_id[i]+", 내용 = 해당 ID의 White List 정보가 존재 하지 않음.","F","I"); // 정책로그 저장
//					}
//				}
//			}
//		}
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("delCnt", delCnt);
//		
//		return jsonResult;
//	}
//	
//	
//
//	/**
//	 * White List 사용여부 수정
//	 * @return String
//	 */
//	@RequestMapping(value = "/white_use_change.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public int white_use_change(HttpSession session, @RequestParam(value="wl_id",required=false) String wl_id, 
//			@RequestParam(value="wl_use_yn",required=false) String wl_use_yn){
//		
//		int result = 0;
//		if(wl_id!=null && nnsp.util.StringUtil.checkNumber(wl_id)){
//			int intWl_id = Integer.parseInt(wl_id);
//			if(intWl_id > 0){
//				NcWhList tmpWl = ncWhService.getWhListId(intWl_id);
//				if(tmpWl!=null&&tmpWl.getWl_id()>0) {
//					result = ncWhService.updateWhiteUseYN(((wl_use_yn!=null && wl_use_yn.equals("1"))?1:0), intWl_id);
//				}
//			}
//		}
//		
//		if(result>0) {
//			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 사용여부 변경"
//					,"Whilte List ID = "+wl_id+", 내용 = 사용여부를 "+((wl_use_yn!=null && wl_use_yn.equals("1"))?"사용함":"사용안함")+"으로 변경"
//					,"S","I"); // 정책로그 저장
//		} else {
//			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 사용여부 변경"
//					,"Whilte List ID = "+wl_id+", 내용 = 사용여부를 "+((wl_use_yn!=null && wl_use_yn.equals("1"))?"사용함":"사용안함")+"으로 변경"
//					,"F","I"); // 정책로그 저장
//		}
//		
//		return result;
//	}
//	
//	/**
//	 * 화이트 리스트 등록
//	 * @return wnEdnpoint
//	 */
//	@RequestMapping(value = "/insert_white.do", method=RequestMethod.POST, produces = "application/json; charset=utf8")
//	@ResponseBody
//	public String insert_white(HttpServletRequest req, HttpSession session, NcWhList ncWhList){
//		int result = ncWhService.insertWhiteList(ncWhList);
//		
//		if (result > 0) {
//			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 등록"
//					,"프로토콜 = "+ncWhList.getWl_proto_sub_dec()
//						+ ",출발지 IP = "+ncWhList.getWl_src_ip()+",출발지 Port ="+ncWhList.getWl_src_port()
//						+ ",도착지 IP = "+ncWhList.getWl_src_ip()+",도착지 Port ="+ncWhList.getWl_dst_port()
//						+ ",Function Code = "+ncWhList.getWl_func1()+",Object ="+ncWhList.getWl_func2()
//						+ ",Variation = "+ncWhList.getWl_func3()+",Qualifier ="+ncWhList.getWl_func4()
//						+ ",사용여부 ="+((ncWhList.getWl_use_yn()==1)?"사용함":"사용안함")
//					,"S","I"); // 정책로그 저장
//			return "true";
//		} else {
//			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"White List 정보 등록"
//					,"프로토콜 = "+ncWhList.getWl_proto_sub_dec()
//						+ ",출발지 IP = "+ncWhList.getWl_src_ip()+",출발지 Port ="+ncWhList.getWl_src_port()
//						+ ",도착지 IP = "+ncWhList.getWl_src_ip()+",도착지 Port ="+ncWhList.getWl_dst_port()
//						+ ",Function Code = "+ncWhList.getWl_func1()+",Object ="+ncWhList.getWl_func2()
//						+ ",Variation = "+ncWhList.getWl_func3()+",Qualifier ="+ncWhList.getWl_func4()
//						+ ",사용여부 ="+((ncWhList.getWl_use_yn()==1)?"사용함":"사용안함")
//					,"F","I"); // 정책로그 저장
//			return "DB Error";
//		}
//	}
//}