package nnsp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import nnsp.vo.NcLog;
import nnsp.vo.NcLog.LOG_TYPE;
import nnsp.common.PageInfo;
import nnsp.services.NcAuditService;
import nnsp.services.NcLogService;
import nnsp.services.NcMenuService;
import nnsp.util.MessageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
public class NcLogController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcLogController.class);

	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcLogService ncLogService;
	@Autowired
	NcMenuService ncMenuService;

	/**
	 * 관리자 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_manager.do", method = RequestMethod.GET)
	public String log_manager(NcLog ncMngData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

        Integer days = 7;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncMngData.setNlm_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter));    // 일주일 전
		ncMngData.setNlm_create_edate(LocalDate.now().format(formatter));                               // 현재 날짜
		ncMngData.setDatetab(days.toString());

		int cpage = 1;
		int total = ncLogService.getMngLogTotalCnt(ncMngData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getMngLogList(pginfo.getOffset(), pginfo.getPerArticle(), ncMngData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncMngData", ncMngData);
		model.addAttribute("base_url", "log_manager.do?page=");

		return "log/log_manager";
	}
	
	/**
	 * 관리자 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_manager_grid.do", method = RequestMethod.GET)
	public String log_manager_grid(NcLog ncMngData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

		ncMngData.setLogType(LOG_TYPE.MANAGER);
		setDefaultSearchPeriod(ncMngData, 1);

		int cpage = 1;
		int total = ncLogService.getMngLogTotalCnt_grid(ncMngData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getMngLogList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncMngData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncMngData", ncMngData);
		model.addAttribute("base_url", "log_manager_grid.do?page=");

		return "log/log_manager_grid";
	}

	/**
	 * 관리자 로그 검색
	 * 
	 * @param ncMngData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/manager_grid_search.do")
	public String manager_grid_search(NcLog ncMngData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_manager_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_manager_grid.do"));
		// 메뉴 만들기 끝

		int cpage = 1;
		int total = ncLogService.getMngSearchCnt_grid(ncMngData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}
		
		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getMngSearchList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncMngData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncMngData", ncMngData);
		model.addAttribute("base_url",
			"manager_grid_search.do?&nlm_create_sdate=" + ncMngData.getNlm_create_sdate() 
				+ "&nlm_create_edate=" + ncMngData.getNlm_create_edate()
				+ "&page=");

		return "log/log_manager_grid";
	}

	/**
	 * 정책관리 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_policy.do", method = RequestMethod.GET)
	public String log_policy(NcLog ncPolicyData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

        Integer days = 7;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncPolicyData.setNlp_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter)); // 일주일 전
		ncPolicyData.setNlp_create_edate(LocalDate.now().format(formatter));                            // 현재 날짜
		ncPolicyData.setDatetab(days.toString());

		int cpage = 1;
		int total = ncLogService.getPolicyTotalCnt(ncPolicyData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getPolicyList(pginfo.getOffset(), pginfo.getPerArticle(), ncPolicyData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncPolicyData", ncPolicyData);
		model.addAttribute("base_url", "log_policy.do?page=");

		return "log/log_policy";
	}

	/**
	 * 정책관리 로그 검색
	 * 
	 * @param ncPolicyData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/policy_search.do")
	public String policy_search(NcLog ncPolicyData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_policy.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_policy.do"));
		// 메뉴 만들기 끝

		int cpage = 1;
		int total = ncLogService.getPolicySearchCnt(ncPolicyData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getPolicySearchList(pginfo.getOffset(), pginfo.getPerArticle(), ncPolicyData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncPolicyData", ncPolicyData);
		model.addAttribute("base_url",
				"policy_search.do?nsu_id=" + ncPolicyData.getNsu_id() + "&nlm_create_sdate="
						+ ncPolicyData.getNlp_create_sdate() + "&nlm_create_edate=" + ncPolicyData.getNlp_create_edate()
						+ "&nai_ip=" + ncPolicyData.getNai_ip() + "&datetab=" + ncPolicyData.getDatetab() + "&page=");

		return "log/log_policy";
	}
	
	/**
	 * 관리자 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_policy_grid.do", method = RequestMethod.GET)
	public String log_policy_grid(NcLog ncPolicyData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

		ncPolicyData.setLogType(LOG_TYPE.POLICY);
		setDefaultSearchPeriod(ncPolicyData, 1);

		int cpage = 1;
		int total = ncLogService.getPolicyLogTotalCnt_grid(ncPolicyData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getPolicyLogList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncPolicyData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncPolicyData", ncPolicyData);
		model.addAttribute("base_url", "log_policy_grid.do?page=");

		return "log/log_policy_grid";
	}

	/**
	 * 관리자 로그 검색
	 * 
	 * @param ncMngData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/policy_grid_search.do")
	public String policy_grid_search(NcLog ncPolicyData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_policy_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_policy_grid.do"));
		// 메뉴 만들기 끝

		int cpage = 1;
		int total = ncLogService.getPolicySearchCnt_grid(ncPolicyData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}
		
		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getPolicySearchList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncPolicyData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncPolicyData", ncPolicyData);
		model.addAttribute("base_url",
			"policy_grid_search.do?&nlp_create_sdate=" + ncPolicyData.getNlp_create_sdate() 
				+ "&nlp_create_edate=" + ncPolicyData.getNlp_create_edate()
				+ "&page=");

		return "log/log_policy_grid";
	}

	/**
	 * 이벤트 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_event.do", method = RequestMethod.GET)
	public String log_event(NcLog ncEvtData, Model model, @RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

        Integer days = 7;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncEvtData.setNle_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter));    // 일주일 전
		ncEvtData.setNle_create_edate(LocalDate.now().format(formatter));                               // 현재 날짜
		ncEvtData.setDatetab(days.toString());

		int cpage = 1;
		int total = ncLogService.getEventTotalCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getEventList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_event.do?page=");

		return "log/log_event";
	}

	/**
	 * 이벤트 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/event_search.do")
	public String event_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_event.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_event.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNle_div() == 0)
			ncEvtData.setNle_div(1);

		int cpage = 1;
		int total = ncLogService.getEventSearchCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getEventSearchList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url",
				"event_search.do?nle_create_sdate=" + ncEvtData.getNle_create_sdate() + "&nle_create_edate="
						+ ncEvtData.getNle_create_edate() + "&npl_name=" + ncEvtData.getNpl_name() + "&nle_div="
						+ ncEvtData.getNle_div() + "&nle_risk_level=" + ncEvtData.getNle_risk_level() + "&datetab="
						+ ncEvtData.getDatetab() + "&page=");

		return "log/log_event";
	}
	
	/**
	 * 관리자 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_event_grid.do", method = RequestMethod.GET)
	public String log_event_grid(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

		ncEvtData.setLogType(LOG_TYPE.EVENT);
		setDefaultSearchPeriod(ncEvtData, 1);

		int cpage = 1;
		int total = ncLogService.getEventLogTotalCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getEventLogList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_event_grid.do?page=");

		return "log/log_event_grid";
	}

	/**
	 * 관리자 로그 검색
	 * 
	 * @param ncMngData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/event_grid_search.do")
	public String event_grid_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_event_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_event_grid.do"));
		// 메뉴 만들기 끝

		int cpage = 1;
		int total = ncLogService.getEventSearchCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}
		
		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getEventSearchList_grid(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url",
			"event_grid_search.do?&nle_create_sdate=" + ncEvtData.getNle_create_sdate() 
				+ "&nle_create_edate=" + ncEvtData.getNle_create_edate()
				+ "&page=");

		return "log/log_event_grid";
	}

	/**
	 * 접속 통제 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_access2.do", method = RequestMethod.GET)
	public String log_access2(NcLog ncAcsData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

        Integer days = 1;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncAcsData.setNla_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter));    // 1일 전
		ncAcsData.setNla_create_edate(LocalDate.now().format(formatter));                               // 현재 날짜
		ncAcsData.setDatetab(days.toString());

		int cpage = 1;
        int tblDate = Integer.parseInt(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMM")));
		int total = ncLogService.getAccessSearchCnt2(ncAcsData, tblDate);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(
				ncLogService.getAccessSearchList2(pginfo.getOffset(), pginfo.getPerArticle(), ncAcsData, tblDate));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncAcsData", ncAcsData);
		model.addAttribute("base_url", "log_access2.do?page=");

		return "log/log_access2";
	}

	@RequestMapping(value = "/access_search2.do")
	public String access_search2(NcLog ncAcsData, Model model,
			@RequestParam(value = "page", required = false) String page) throws UnsupportedEncodingException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_access2.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_access2.do"));
		// 메뉴 만들기 끝
		SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMM", Locale.KOREA);

		Date date = null;
		long lDate = 0;

		try {
			date = iFormatter.parse(ncAcsData.getNla_create_sdate());
			lDate = date.getTime();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String table_name = oFormatter.format(lDate);
		int tblDate = Integer.parseInt(table_name.substring(0, 6));

		if (ncAcsData.getNla_div() == 0)
			ncAcsData.setNla_div(1);

		int cpage = 1;
		int total = ncLogService.getAccessSearchCnt2(ncAcsData, tblDate);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}
		model.addAttribute("total", total);

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(
				ncLogService.getAccessSearchList2(pginfo.getOffset(), pginfo.getPerArticle(), ncAcsData, tblDate));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncAcsData", ncAcsData);
		/*
		 * model.addAttribute("base_url",
		 * "access_search.do?nla_create_sdate="+ncAcsData.getNla_create_sdate()+
		 * "&nla_create_edate="+ncAcsData.getNla_create_edate()
		 * +"&nla_risk_level="+URLEncoder.encode(ncAcsData.getNla_risk_level(),"UTF-8")+
		 * "&nla_access_type="+ncAcsData.getNla_access_type()
		 * +"&nla_div="+ncAcsData.getNla_div()+"&nla_div2="+URLEncoder.encode(ncAcsData.
		 * getNla_div2(),"UTF-8")
		 * +"&nla_src_ip="+ncAcsData.getNla_src_ip()+"&nla_src_port="+ncAcsData.
		 * getNla_src_port()+"&nla_dst_ip="+ncAcsData.getNla_dst_ip()+"&nla_dst_port="+
		 * ncAcsData.getNla_dst_port()+"&page=");
		 */
		model.addAttribute("base_url",
				"access_search2.do?nla_create_sdate=" + ncAcsData.getNla_create_sdate() + "&nla_create_edate="
						+ ncAcsData.getNla_create_edate() + "&nla_access_type=" + ncAcsData.getNla_access_type()
						+ "&nla_div=" + ncAcsData.getNla_div() + "&nla_div2="
						+ URLEncoder.encode(ncAcsData.getNla_div2(), "UTF-8") + "&nla_risk_level="
						+ ncAcsData.getNla_risk_level() + "&datetab=" + ncAcsData.getDatetab() + "&page=");

		return "log/log_access2";
	}
	
	/**
	 * 관리자 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_access_grid.do", method = RequestMethod.GET)
	public String log_access_grid(NcLog ncAcsData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

		ncAcsData.setLogType(LOG_TYPE.ACCESS);
		setDefaultSearchPeriod(ncAcsData, 1);
		
		int cpage = 1;
		int total = ncLogService.getAccessSearchCnt_grid(ncAcsData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getAccessSearchList_grid(ncAcsData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncAcsData", ncAcsData);
		model.addAttribute("base_url", "log_access_grid.do?page=");

		return "log/log_access_grid";
	}

	/**
	 * 관리자 로그 검색
	 * 
	 * @param ncMngData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/access_grid_search.do")
	public String access_grid_search(NcLog ncAcsData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_access_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_access_grid.do"));
		// 메뉴 만들기 끝

		int cpage = 1;
		int total = ncLogService.getAccessSearchCnt_grid(ncAcsData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}
		
		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getAccessSearchList_grid(ncAcsData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncAcsData", ncAcsData);
		model.addAttribute("base_url",
			"access_grid_search.do?&nla_create_sdate=" + ncAcsData.getNla_create_sdate() 
				+ "&nla_create_edate=" + ncAcsData.getNla_create_edate()
				+ "&page=");

		return "log/log_access_grid";
	}
	
	/*
		 * @RequestMapping(value = "/log_access.do", method = RequestMethod.GET) public
		 * String log_access(NdLog ncAcsData, Model
		 * model, @RequestParam(value="page",required=false) String page) { // 메뉴 만들기 시작
		 * model.addAttribute("top_menu", ncMenuService.getTopMenu());
		 * model.addAttribute("left_menu", ncMenuService.getLeftMenu()); // 메뉴 만들기 끝
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 * ncAcsData.setNla_create_sdate(sdf.format(new
		 * Date(System.currentTimeMillis()))); // 현재 날짜
		 * ncAcsData.setNla_create_edate(sdf.format(new
		 * Date(System.currentTimeMillis())));
		 * 
		 * int cpage=1; int total = ncLogService.getAccessTotalCnt(ncAcsData); if( page
		 * != null && page.length() > 0) { try{ cpage = Integer.parseInt(page);
		 * }catch(NumberFormatException e){
		 * 
		 * } }
		 * 
		 * PageInfo<NdLog> pginfo = new PageInfo<NdLog>(total, cpage);
		 * pginfo.setResult(ncLogService.getAccessList(pginfo.getOffset(),
		 * pginfo.getPerArticle(), ncAcsData)); model.addAttribute("pginfo", pginfo);
		 * model.addAttribute("ncAcsData", ncAcsData); model.addAttribute("base_url",
		 * "log_access.do?page=");
		 * 
		 * return "log/log_access"; }
		 */

	/**
	 * 접속 통제 검색
	 * 
	 * @param ncAcsData
	 * @param model
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/access_search.do")
	public String access_search(NcLog ncAcsData, Model model,
			@RequestParam(value = "page", required = false) String page) throws UnsupportedEncodingException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_access.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_access.do"));
		// 메뉴 만들기 끝

		if (ncAcsData.getNla_div() == 0)
			ncAcsData.setNla_div(1);

		int cpage = 1;
		int total = ncLogService.getAccessSearchCnt(ncAcsData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getAccessSearchList(pginfo.getOffset(), pginfo.getPerArticle(), ncAcsData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncAcsData", ncAcsData);
		/*
		 * model.addAttribute("base_url",
		 * "access_search.do?nla_create_sdate="+ncAcsData.getNla_create_sdate()+
		 * "&nla_create_edate="+ncAcsData.getNla_create_edate()
		 * +"&nla_risk_level="+URLEncoder.encode(ncAcsData.getNla_risk_level(),"UTF-8")+
		 * "&nla_access_type="+ncAcsData.getNla_access_type()
		 * +"&nla_div="+ncAcsData.getNla_div()+"&nla_div2="+URLEncoder.encode(ncAcsData.
		 * getNla_div2(),"UTF-8")
		 * +"&nla_src_ip="+ncAcsData.getNla_src_ip()+"&nla_src_port="+ncAcsData.
		 * getNla_src_port()+"&nla_dst_ip="+ncAcsData.getNla_dst_ip()+"&nla_dst_port="+
		 * ncAcsData.getNla_dst_port()+"&page=");
		 */
		model.addAttribute("base_url",
				"access_search.do?nla_create_sdate=" + ncAcsData.getNla_create_sdate() + "&nla_create_edate="
						+ ncAcsData.getNla_create_edate() + "&nla_access_type=" + ncAcsData.getNla_access_type()
						+ "&nla_div=" + ncAcsData.getNla_div() + "&nla_div2="
						+ URLEncoder.encode(ncAcsData.getNla_div2(), "UTF-8") + "&nla_risk_level="
						+ ncAcsData.getNla_risk_level() + "&datetab=" + ncAcsData.getDatetab() + "&page=");

		return "log/log_access";
	}

	/**
	 * 무결성 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_integrity.do", method = RequestMethod.GET)
	public String log_integrity(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝

        Integer days = 7;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncEvtData.setNli_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter));    // 일주일 전
		ncEvtData.setNli_create_edate(LocalDate.now().format(formatter));                               // 현재 날짜
        ncEvtData.setDatetab(days.toString());

		int cpage = 1;
		int total = ncLogService.getIntegrityTotalCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getIntegrityList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_integrity.do?page=");

		return "log/log_integrity";
	}

	/**
	 * 무결성 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/integrity_search.do")
	public String integrity_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) throws UnsupportedEncodingException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_integrity.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_integrity.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNli_div() == 0)
			ncEvtData.setNli_div(1);

		int cpage = 1;
		int total = ncLogService.getIntegritySearchCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getIntegritySearchList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		/*
		 * model.addAttribute("base_url",
		 * "integrity_search.do?nli_create_sdate="+ncEvtData.getNli_create_sdate()+
		 * "&nli_create_edate="+ncEvtData.getNli_create_edate()+"&nli_risk_level="+
		 * ncEvtData.getNli_risk_level()
		 * +"&ncp_name="+ncEvtData.getNcp_name()+"&nli_message="+ncEvtData.
		 * getNli_message()+"&nli_div="+ncEvtData.getNli_div()+"&page=");
		 */
		model.addAttribute("base_url",
				"integrity_search.do?nli_create_sdate=" + ncEvtData.getNli_create_sdate() + "&nli_create_edate="
						+ ncEvtData.getNli_create_edate() + "&npl_name=" + ncEvtData.getNpl_name() + "&nli_div="
						+ ncEvtData.getNli_div() + "&nli_risk_level="
						+ URLEncoder.encode(ncEvtData.getNli_risk_level(), "UTF-8") + "&datetab="
						+ ncEvtData.getDatetab() + "&page=");

		return "log/log_integrity";
	}
	
	/**
	 * 무결성 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_protection_grid.do")
	public String log_protection_grid(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝

		ncEvtData.setLogType(LOG_TYPE.PROTECTION);
		setDefaultSearchPeriod(ncEvtData, 1);

		int cpage = 1;
		int total = ncLogService.getProtectionTotalCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getProtectionList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_protection_grid.do?page=");

		return "log/log_protection_grid";
	}

	/**
	 * 무결성 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/protection_grid_search.do")
	public String protection_grid_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) throws UnsupportedEncodingException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_protection_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_protection_grid.do"));
		model.addAttribute("tab_menu", ncMenuService.getTabMenu("/log_protection_grid.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNlpt_div() == 0)
			ncEvtData.setNlpt_div(1);

		int cpage = 1;
		int total = ncLogService.getProtectionSearchCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getProtectionSearchList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url",
				"protection_grid_search.do?"
						+ "nlpt_create_sdate=" + ncEvtData.getNlpt_create_sdate() 
						+ "&nlpt_create_edate=" + ncEvtData.getNlpt_create_edate() 
						+ "&page=");

		return "log/log_protection_grid";
	}
	
	/**
	 * 무결성 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_integrity_grid.do")
	public String log_integrity_grid(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝

		ncEvtData.setLogType(LOG_TYPE.INTEGRITY);
		setDefaultSearchPeriod(ncEvtData, 1);

		int cpage = 1;
		int total = ncLogService.getIntegrityTotalCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getIntegrityList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_integrity_grid.do?page=");

		return "log/log_integrity_grid";
	}

	/**
	 * 무결성 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/integrity_grid_search.do")
	public String integrity_grid_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) throws UnsupportedEncodingException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_integrity_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_integrity_grid.do"));
		model.addAttribute("tab_menu", ncMenuService.getTabMenu("/log_integrity_grid.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNli_div() == 0)
			ncEvtData.setNli_div(1);

		int cpage = 1;
		int total = ncLogService.getIntegritySearchCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getIntegritySearchList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url",
				"integrity_grid_search.do?"
						+ "nli_create_sdate=" + ncEvtData.getNli_create_sdate() 
						+ "&nli_create_edate=" + ncEvtData.getNli_create_edate() 
						+ "&page=");

		return "log/log_integrity_grid";
	}

	/**
	 * 오류 및 버퍼링 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_error.do", method = RequestMethod.GET)
	public String log_error(NcLog ncEvtData, Model model, @RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		Integer days = 1;       // 1일, 7일, 15일
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ncEvtData.setNler_create_sdate(LocalDate.now().minusDays(days.intValue()).format(formatter));    // 일주일 전
		ncEvtData.setNler_create_edate(LocalDate.now().format(formatter));                               // 현재 날짜
        ncEvtData.setDatetab(days.toString());
        
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -2);
//		// ncEvtData.setNle_create_sdate(sdf.format(cal.getTime())); // 일주일 전
//		ncEvtData.setNler_create_sdate(sdf.format(new Date(System.currentTimeMillis())));
//		ncEvtData.setNler_create_edate(sdf.format(new Date(System.currentTimeMillis()))); // 현재 날짜

		int cpage = 1;
		int total = ncLogService.getErrorTotalCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getErrorList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_error.do?page=");

		return "log/log_error";
	}

	/**
	 * 오류 및 버퍼링 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/error_search.do")
	public String error_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_error.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_error.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNler_div() == 0)
			ncEvtData.setNler_div(1);

		int cpage = 1;
		int total = ncLogService.getErrorSearchCnt(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		pginfo.setResult(ncLogService.getErrorSearchList(pginfo.getOffset(), pginfo.getPerArticle(), ncEvtData));
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		/*
		 * model.addAttribute("base_url",
		 * "error_search.do?nler_create_sdate="+ncEvtData.getNler_create_sdate()+
		 * "&nler_create_edate="+ncEvtData.getNler_create_edate()+"&nler_risk_level="+
		 * ncEvtData.getNler_risk_level()
		 * +"&ncp_name="+ncEvtData.getNcp_name()+"&nler_message="+ncEvtData.
		 * getNler_message()+"&nler_div="+ncEvtData.getNler_div()+"&page=");
		 */
		model.addAttribute("base_url",
				"error_search.do?nler_create_sdate=" + ncEvtData.getNler_create_sdate() + "&nler_create_edate="
						+ ncEvtData.getNler_create_edate() + "&ncp_name=" + ncEvtData.getNcp_name() + "&nler_div="
						+ ncEvtData.getNler_div() + "&nler_risk_level=" + ncEvtData.getNler_risk_level() + "&datetab="
						+ ncEvtData.getDatetab() + "&page=");

		return "log/log_error";
	}
	
	/**
	 * 오류 및 버퍼링 로그
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/log_error_grid.do")
	public String log_error_grid(NcLog ncEvtData, Model model, @RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		ncEvtData.setLogType(LOG_TYPE.ERROR);
		setDefaultSearchPeriod(ncEvtData, 1);

		int cpage = 1;
		int total = ncLogService.getErrorTotalCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getErrorList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url", "log_error_grid.do?page=");
		return "log/log_error_grid";
	}

	/**
	 * 오류 및 버퍼링 로그 검색
	 * 
	 * @param ncEvtData
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/error_grid_search.do")
	public String error_grid_search(NcLog ncEvtData, Model model,
			@RequestParam(value = "page", required = false) String page) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu("/log_error_grid.do"));
		model.addAttribute("left_menu", ncMenuService.getLeftMenu("/log_error_grid.do"));
		// 메뉴 만들기 끝

		if (ncEvtData.getNler_div() == 0)
			ncEvtData.setNler_div(1);

		int cpage = 1;
		int total = ncLogService.getErrorSearchCnt_grid(ncEvtData);
		if (page != null && page.length() > 0) {
			try {
				cpage = Integer.parseInt(page);
			} catch (NumberFormatException e) {

			}
		}

		PageInfo<NcLog> pginfo = new PageInfo<NcLog>(total, cpage);
		ArrayList<NcLog> arr = ncLogService.getErrorSearchList_grid(ncEvtData);
		Gson gson = new Gson();
		String json = gson.toJson(arr);
		
		model.addAttribute("jsondata", json);
		model.addAttribute("pginfo", pginfo);
		model.addAttribute("ncEvtData", ncEvtData);
		model.addAttribute("base_url",
				"error_grid_search.do?"
				+ "nler_create_sdate=" + ncEvtData.getNler_create_sdate() 
				+ "&nler_create_edate=" + ncEvtData.getNler_create_edate());

		return "log/log_error_grid";
	}
	
	/**
	 * 로그 Csv 다운로드
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/NcLogCsv.do")
	public String NcLogCsv(HttpSession session, NcLog ncLogData, Model model) {
		ArrayList<NcLog> searchList = new ArrayList<NcLog>();
		
		String audit_param = "";
		if (ncLogData.getLog_type().equals("manager")) {
			audit_param = MessageUtil.getMessage("log.dlcsvadmin");
			searchList = ncLogService.getMngCsv(ncLogData);
		} else if (ncLogData.getLog_type().equals("system")) {
			searchList = ncLogService.getMngCsv(ncLogData);
		} else if (ncLogData.getLog_type().equals("policy")) {
			audit_param = MessageUtil.getMessage("log.dlcsvmgnt");
			searchList = ncLogService.getPolicyCsv(ncLogData);
		}else if (ncLogData.getLog_type().equals("event")) {
			audit_param = MessageUtil.getMessage("log.dlcsvaudit");
			searchList = ncLogService.getEventCsv(ncLogData);
		} else if (ncLogData.getLog_type().equals("protection")) {
			audit_param = MessageUtil.getMessage("log.dlcsvprotection");
			searchList = ncLogService.getIntegrityCsv(ncLogData);
		} else if (ncLogData.getLog_type().equals("integrity")) {
			audit_param = MessageUtil.getMessage("log.dlcsvintegrity");
			searchList = ncLogService.getIntegrityCsv(ncLogData);
		}else if (ncLogData.getLog_type().equals("error")) {
			audit_param = MessageUtil.getMessage("log.dlcsverror");
			searchList = ncLogService.getErrorCsv(ncLogData);
		}else if (ncLogData.getLog_type().equals("access2")) {
			audit_param = MessageUtil.getMessage("log.dlcsvaccessctrl");
			searchList = ncLogService.getAccessCsv2(ncLogData);
		}

		model.addAttribute("searchList", searchList);
		model.addAttribute("mode", ncLogData.getLog_type());

		ncAuditService.mng_log_insert(
			(String) session.getAttribute("loginUserId"),
			(String) session.getAttribute("clientIp"),
			MessageUtil.getMessage("log.dlcsvmessage"),
			audit_param, "S", "I"); // 감사로그 저장

		return "NcLogCsv";
	}
	
	private boolean setDefaultSearchPeriod(NcLog ncLog, int days) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		switch (ncLog.getLogType()) {
			case MANAGER:
				ncLog.setNlm_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNlm_create_edate(LocalDate.now().format(formatter));
				break;
			case POLICY:
				ncLog.setNlp_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNlp_create_edate(LocalDate.now().format(formatter));
				break;
			case EVENT:
				ncLog.setNle_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNle_create_edate(LocalDate.now().format(formatter));
				break;
			case ACCESS:
				ncLog.setNla_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNla_create_edate(LocalDate.now().format(formatter));
				break;
			case PROTECTION:
				ncLog.setNlpt_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNlpt_create_edate(LocalDate.now().format(formatter));
				break;
			case INTEGRITY:
				ncLog.setNli_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNli_create_edate(LocalDate.now().format(formatter));
				break;
			case ERROR:
				ncLog.setNler_create_sdate(LocalDate.now().minusDays(days).format(formatter));
				ncLog.setNler_create_edate(LocalDate.now().format(formatter));
				break;
		}

		ncLog.setDatetab(Integer.toString(days));

		return true;
	}
}