package nnsp.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nnsp.services.NcAuditService;
import nnsp.services.NcMenuService;
import nnsp.services.NcWhService;
import nnsp.vo.NcWhList;
import nnsp.vo.NdrPs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
public class NcWhController {
		
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcWhService ncWhService;
	@Autowired
	NcMenuService ncMenuService;
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * 접근제어정책 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/white_ics.do")
	public String white_ics(Model model, NcWhList ncWhList 
			, @RequestParam(value="wl_name", required=false) String wl_name) {
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		
		ArrayList<NcWhList> protocolList = ncWhService.selectProtocolList();
		model.addAttribute("protocolList", protocolList);
		model.addAttribute("protocolListCnt", protocolList.size());
		
		ArrayList<NcWhList> policyLevelList = ncWhService.selectPolicyLevelList();
		model.addAttribute("policyLevelList", policyLevelList);
		model.addAttribute("policyLevelListCnt", policyLevelList.size());
//		ArrayList<NcWhList> groupList = ncWhService.selectGroupList(ncWhList);
//		model.addAttribute("groupList", groupList);
		
		ArrayList<NcWhList> hostTypeList = ncWhService.selectHostType();
		model.addAttribute("hostTypeList", hostTypeList);
		model.addAttribute("hostTypeListCnt", hostTypeList.size());
	
		model.addAttribute("wl_name", wl_name);
		return "analyze/white_ics";			
	}
	
	/**
	 * 
	 * 그룹별 whiteList 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/group_list_grid.do")
	public String group_list_grid(Model model, NcWhList ncWhList
			, @RequestParam(value="wl_name", required=false) String wl_name) {

		model.addAttribute("wl_name", wl_name);
		model.addAttribute("scriptName", "loadWhiteList");
		return "analyze/group_list_grid";			
	}
	
	/**
	 *  WhiteList JsonData
	 * @return Object JSON
	 */
	@RequestMapping(value = "/getWhiteList.do")
	@ResponseBody
	public Object getWhiteList(HttpSession session) {
	    //조회 날짜 설정
		ArrayList<NcWhList> arrWhiteList = ncWhService.getWhiteList();
		int totalCount = ncWhService.getWhiteListCount(); // 전체 항목 수 가져오기

		JSONArray jarrWhiteList = JSONArray.fromObject(arrWhiteList);
		
		JSONObject rtnJSONObject = new JSONObject();
		rtnJSONObject.put("data", jarrWhiteList);
		rtnJSONObject.put("totalCount", totalCount);

		return rtnJSONObject;
	}
	
	/**
	 * 화이트 리스트 등록
	 * @return wnEdnpoint
	 */
	@RequestMapping(value = "/regst_white_list.do")
	@ResponseBody
	public String regst_white_list(HttpSession session, NcWhList ncWhList){
		String strResult = "";
		if(ncWhService.selectIsDuplicateWhiteList(ncWhList) == 0) {	//White List 중복체크 : 중복 X
			int result = ncWhService.registerWhiteList(ncWhList);
			
			if (result > 0) {
				ncAuditService.policy_log_insert(
						(String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근 제어 정책 등록"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
						,"S","I"); // 정책로그 저장
				strResult = "success";
			} 
			else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근 제어 정책 등록"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
						,"F","I"); // 정책로그 저장
				strResult = "fail";
			}
		}
		else {	//중복된 White Lsit
			strResult = "smae_policy";
			return strResult;
		}
		
		return strResult;
	}
	
	@RequestMapping(value = "/updateWlUseYn.do")
	@ResponseBody
	public int updateWlUseYn(HttpSession session, 
			@RequestParam(value = "wl_name", required = false) String wl_name,
			@RequestParam(value = "wl_id", required = false) long wl_id, 
			@RequestParam(value = "wl_use_yn", required = false) int wl_use_yn) {
		int result = 0;
		
		String str_wl_use_yn = wl_use_yn == 1 ? "사용" : "미사용";
		
		result = ncWhService.updateWlUseYn(wl_id, wl_use_yn);
		
		if (result > 0) {
        	ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
					, "접근제어정책 수정"
					,"정책명 = "+wl_name 
					+ ", 사용여부 = "+str_wl_use_yn
					,"S","I"); // 정책로그 저장
        } else {
        	ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
        			, "접근제어정책 수정"
					,"정책명 = "+wl_name 
					+ ", 사용여부 = "+str_wl_use_yn
					,"F","I"); // 정책로그 저장
        }
		
		return result;
	}
	
	@RequestMapping(value = "/modifyWhiteList.do")
	@ResponseBody
	public String modifyWhiteList(HttpSession session, NcWhList ncWhList,
			@RequestParam(value = "wl_id", required = false) long wl_id) {
	    String strResult = "fail";
	    
		if(ncWhService.selectIsDuplicateWhiteList(ncWhList) == 0) {
	        // 새로운 데이터 삽입
	        int updateResult = ncWhService.updateWhiteList(ncWhList);
	        
	        if (updateResult > 0) {
	        	ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근 제어 정책 수정"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
						,"S","I"); // 정책로그 저장
				strResult = "success";
	        } else {
	        	ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근 제어 정책 수정"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
						,"F","I"); // 정책로그 저장
				strResult = "update_fail";
	        }
		}
		else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
					, "접근 제어 정책 수정"
					,"정책명 = "+ncWhList.getWl_name() 
					+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
					+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//					+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
					+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//					+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
					+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
					+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//					+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
					+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//					+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
					+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
					+ ", 도착지 Port ="+ncWhList.getWl_dst_port() 
					,"F","I"); // 정책로그 저장
			strResult = "same_policy";
		}
		
	    return strResult;
	}
	
	/**
	 * 체크된 정책 삭제
	 * @return wnEdnpoint
	 */
	@RequestMapping(value = "/delete_checked_white_list.do")
	@ResponseBody
	public Object delete_checked_white_list(HttpSession session, 
			@RequestParam(value="selectedRowWlId") String[] selectedRowWlId){
		int delWhiteListCnt=0;
		
		for(int i=0; i<selectedRowWlId.length; i++) {
			NcWhList ncWhList = ncWhService.selectCheckedWhiteList(Long.parseLong(selectedRowWlId[i]));
//			NcWhList groupinfo = ncWhService.selectGroupInfo(ncWhList.getWl_ngl_id());
//			ncWhList.setNgl_group_name(groupinfo.getNgl_group_name());
			int result = ncWhService.deleteCheckedWhiteList(Long.parseLong(selectedRowWlId[i]));
			
			if (result > 0) {
				ncAuditService.policy_log_insert(
						(String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근제어 정책 삭제"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port() 
						,"S","I"); // 정책로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "접근제어 정책 삭제"
						,"정책명 = "+ncWhList.getWl_name() 
						+ ", 프로토콜 = "+ncWhList.getWl_npl_name()
						+ ", 정책등급 = "+ncWhList.getWl_nplv_name()
//						+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
						+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
//						+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip() 
						+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
						+ ", 출발지 Port ="+ncWhList.getWl_src_port()
//						+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
						+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
//						+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
						+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
						+ ", 도착지 Port ="+ncWhList.getWl_dst_port() 
						,"F","I"); // 정책로그 저장
			}
			delWhiteListCnt += result;
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("delWhiteListCnt", delWhiteListCnt);
		
		return jsonResult;
	}
	
	/**
	 *  그룹별 WhiteList JsonData
	 * @return Object JSON
	 */
	@RequestMapping(value = "/getGroupWhiteList.do")
	@ResponseBody
	public Object getGroupWhiteList(HttpSession session
	        , @RequestParam(value = "skip", required = false, defaultValue = "0") int skip
	        , @RequestParam(value = "take", required = false, defaultValue = "30") int take
	        , @RequestParam(value = "sort", required = false) String sort
	        , @RequestParam(value = "filter", required = false) String filter
	        , @RequestParam(value = "groupByField", required = false) String groupByField) {
		Map<String, Object> loadOptions = new HashMap<>();
	    loadOptions.put("skip", skip);
	    loadOptions.put("take", take);
	    loadOptions.put("sort", sort);
	    loadOptions.put("filter", filter);
	    loadOptions.put("groupByField", groupByField);
	    
	    //조회 날짜 설정
		ArrayList<NcWhList> arrGroupWhiteList = ncWhService.selectGroupWhiteList(loadOptions);
		int totalCount = ncWhService.getGroupWhiteListCount(loadOptions); // 전체 항목 수 가져오기

		JSONArray jarrGroupWhiteList = JSONArray.fromObject(arrGroupWhiteList);
		
		JSONObject rtnJSONObject = new JSONObject();
		rtnJSONObject.put("data", jarrGroupWhiteList);
		rtnJSONObject.put("totalCount", totalCount);

		return rtnJSONObject;
	}
	

	/**
	 * 그룹 등록
	 * @return wnEdnpoint
	 */
	@RequestMapping(value = "/regst_group.do")
	@ResponseBody
	public String regst_group(HttpSession session, NcWhList ncWhList){
		ArrayList<NcWhList> groupList = ncWhService.selectGroupList(ncWhList);
		String strResult = "";
		
		for(int i = 0; i < groupList.size(); i++) {
			if(groupList.get(i).getNgl_group_name().equals(ncWhList.getNgl_group_name())){
				strResult = "same_group";
				return strResult;
			}
		}
		
		int result = ncWhService.registerGroupList(ncWhList);
		
		if (result > 0) {
			ncAuditService.policy_log_insert(
					(String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
					, "그룹 정보 등록"
					, "그룹 명 = "+ncWhList.getNgl_group_name() 
					+ ", 설명 = "+ncWhList.getNgl_group_desc() 
					,"S","I"); // 정책로그 저장
			strResult = "success";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
					, "그룹 정보 등록"
					, "그룹 명 = "+ncWhList.getNgl_group_name() 
					+ ", 설명 = "+ncWhList.getNgl_group_desc() 
					,"F","I"); // 정책로그 저장
			strResult = "fail";
		}
		
		return strResult;
	}
	
	
	/**
	 * 통신현황 페이지
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ics_anomaly_list_grid.do")
	public String ics_anomaly_list_grid(HttpSession session
			, @RequestParam(value="sdateTime", required=false) String sdateTime
			, @RequestParam(value="edateTime", required=false) String edateTime
			, @RequestParam(value="dateTab", required=false) String dateTab
			, @RequestParam(value="stat_ps_inout", required=false) String stat_ps_inout
			, Model model) throws ParseException {
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		
		NcWhList ncWhList = new NcWhList();
		ArrayList<NcWhList> groupList = ncWhService.selectGroupList(ncWhList);
		
		model.addAttribute("groupList", groupList);
		
		ArrayList<NcWhList> hostTypeList = ncWhService.selectHostType();
		model.addAttribute("hostTypeList", hostTypeList);
		
		if(sdateTime == null && edateTime == null) {
			sdateTime = nnsp.util.StringUtil.getStrDayOneHourBefore(0);
			edateTime = nnsp.util.StringUtil.getStrDayWithTime(0);
		}
		
		if(dateTab == null ) {
			dateTab = "1";
		}
		
		model.addAttribute("sdateTime", sdateTime);
		model.addAttribute("edateTime", edateTime);
		model.addAttribute("dateTab", dateTab);
		model.addAttribute("stat_ps_inout", stat_ps_inout);
				
		return "analyze/ics_anomaly_list_grid";
	}
	
	@RequestMapping(value = "/ics_anomaly_list_data.do")
	public void getAnomalyListData(
	        @RequestParam(value = "sdateTime", required = false) String sdateTime,
	        @RequestParam(value = "edateTime", required = false) String edateTime,
	        @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
	        @RequestParam(value = "take", required = false, defaultValue = "20") int take,
	        @RequestParam(value = "sort", required = false) String sort,
	        @RequestParam(value = "filter", required = false) String filter,
	        HttpServletResponse response) throws IOException {
	    // AJAX 요청에 대한 데이터 조회
	    NdrPs ndrPs = new NdrPs();
	    ndrPs.setPs_strc_stime(sdateTime + ":00");
	    ndrPs.setPs_strc_etime(edateTime + ":59");

	    Map<String, Object> loadOptions = new HashMap<>();
	    loadOptions.put("skip", skip);
	    loadOptions.put("take", take);
	    loadOptions.put("sort", sort);
	    loadOptions.put("filter", filter);

	    List<NdrPs> data = ncWhService.getPacketStatList(ndrPs, loadOptions);
	    int totalCount = ncWhService.getPacketStatTotalCount(ndrPs, loadOptions);

	    // JSON 데이터 반환
	    Map<String, Object> result = new HashMap<>();
	    result.put("data", data);
	    result.put("totalCount", totalCount);

	    // 응답 직렬화
	    Gson gson = new Gson();
	    String jsonResponse = gson.toJson(result);
	    response.setContentType("application/json; charset=UTF-8");
	    response.getWriter().write(jsonResponse);
	}
	
	/**
	 * 체크된 정책 지정된 그룹에 등록
	 * @return wnEdnpoint
	 */
	@RequestMapping(value = "/regst_checked_white_list.do")
	@ResponseBody
	public Object regst_checked_white_list(HttpSession session, 
			@RequestParam(value="selectedRowPsId") String[] selectedRowPsId,
			@RequestParam(value="ngl_id") int ngl_id,
			@RequestParam(value="date") String date){
		int regCnt = 0;
		int duplicateCnt = 0;
		NcWhList groupinfo = ncWhService.selectGroupInfo(ngl_id);
		
		for(int i=0; i<selectedRowPsId.length; i++) {
			NdrPs ndrPs = new NdrPs();
			ndrPs.setPs_id(Long.parseLong(selectedRowPsId[i]));
			ndrPs.setTblDate(Integer.parseInt(nnsp.util.StringUtil.getStrDateYMD(date)));
			
			NdrPs selectPacketByPsId = ncWhService.selectPacketStatByPsId(ndrPs);
			
			NcWhList ncWhList = new NcWhList();
//			ncWhList.setWl_ngl_id(ngl_id);
			ncWhList.setNgl_group_name(groupinfo.getNgl_group_name());
//			ncWhList.setWl_proto_sub_dec(selectPacketByPsId.getPs_proto_sub_dec());
//			ncWhList.setPs_inout(selectPacketByPsId.getPs_inout());
			ncWhList.setWl_src_ip(selectPacketByPsId.getPs_src_ip());
			ncWhList.setWl_src_port(selectPacketByPsId.getPs_src_port());
			ncWhList.setWl_dst_ip(selectPacketByPsId.getPs_dst_ip());
			ncWhList.setWl_dst_port(selectPacketByPsId.getPs_dst_port());
			
			if(ncWhService.selectIsDuplicateWhiteList(ncWhList) == 0) {	//White List 중복체크 : 중복 X
				int result = ncWhService.registerWhiteList(ncWhList);
				
				if (result > 0) {
					ncAuditService.policy_log_insert(
							(String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
							, "White List 정보 등록"
							, "그룹 명 = "+ncWhList.getNgl_group_name() 
//							+ ", 그룹 ID = "+ncWhList.getWl_ngl_id() 
//							+ ", 구분 = "+ncWhList.getStrPsInOut()
							+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
							+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
							+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip()
							+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
							+ ", 출발지 Port ="+ncWhList.getWl_src_port()
							+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
							+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
							+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
							+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
							+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
							,"S","I"); // 정책로그 저장
					regCnt += result;
				} 
				else {
					ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
							, "White List 정보 등록"
							, "그룹 명 = "+ncWhList.getNgl_group_name() 
//							+ ", 그룹 ID = "+ncWhList.getWl_ngl_id() 
//							+ ", 구분 = "+ncWhList.getStrPsInOut()
							+ ", 출발지 Host Type = "+ncWhList.getWl_src_host_type()
							+ ", 출발지 Host = "+ncWhList.getWl_src_ip() 
							+ ", 출발지 끝 IP = "+ncWhList.getWl_src_eip()
							+ ", 출발지 Port Type = "+ncWhList.getWl_src_port_type()
							+ ", 출발지 Port ="+ncWhList.getWl_src_port()
							+ ", 도착지 Host Type = "+ncWhList.getWl_dst_host_type()
							+ ", 도착지 Host = "+ncWhList.getWl_dst_ip() 
							+ ", 도착지 끝 IP = "+ncWhList.getWl_dst_eip() 
							+ ", 도착지 Port Type = "+ncWhList.getWl_dst_port_type()
							+ ", 도착지 Port ="+ncWhList.getWl_dst_port()
							,"F","I"); // 정책로그 저장
				}
			}
			else {	//중복된 White Lsit
				duplicateCnt++;
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("regCnt", regCnt);
		jsonResult.put("duplicateCnt", duplicateCnt);
		
		return jsonResult;
	}
	
	/**
	 * 그룹 삭제
	 * @return wnEdnpoint
	 */
	@RequestMapping(value = "/delete_group.do")
	@ResponseBody
	public Object delete_group(HttpSession session
			, @RequestParam(value="groupName", required=false) String groupName){
		int delGroupCnt=0;
		int delWhiteListCnt=0;
		NcWhList groupList = ncWhService.selectWhiteListByGroupName(groupName);
		String groupname = groupName;
		
		if(groupList != null) {
			delWhiteListCnt += ncWhService.deleteWhiteList(groupList.getNgl_id());
			delGroupCnt += ncWhService.deleteGroup(groupList.getNgl_id());
			
			if (delGroupCnt > 0) {
				ncAuditService.policy_log_insert(
						(String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "그룹 및 정책 삭제"
						, "그룹 명 = "+groupName  
						+ ", 접근제어 정책 "+delWhiteListCnt+"개 삭제 "
						,"S","I"); // 정책로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp")
						, "그룹 및 하위 정책 삭제"
						, "그룹 명 = "+groupName
						,"F","I"); // 정책로그 저장
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("delGroupCnt", delGroupCnt);
		jsonResult.put("delWhiteListCnt", delWhiteListCnt);
		jsonResult.put("groupname", groupname);
		
		return jsonResult;
	}
}