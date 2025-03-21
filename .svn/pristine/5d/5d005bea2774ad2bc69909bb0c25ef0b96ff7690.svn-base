package nnsp.controllers;

import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcMenuService;
import nnsp.services.NcProductService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcProduct;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class NcProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(NcProductController.class);

	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcProductService ncProductService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcConfigService ncConfigService;
	
	/**
	 * 장비소개 관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/config_product.do")
	public String config_log(HttpSession session,Model model) {
		session.setMaxInactiveInterval(session.getMaxInactiveInterval());
		NcProduct getNcProduct = ncProductService.getNcProduct();
		model.addAttribute("config_product_data", getNcProduct);
		if(getNcProduct!=null && getNcProduct.getNcpd_tel()!=null){
			String[] arr_np_tel = getNcProduct.getNcpd_tel().split("-");
			if(arr_np_tel.length == 3){
				model.addAttribute("ncpd_tel1", arr_np_tel[0]);
				model.addAttribute("ncpd_tel2", arr_np_tel[1]);
				model.addAttribute("ncpd_tel3", arr_np_tel[2]);
			}
		}
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		return "config/config_product";
	}

	/**
	 * 장비소개 정보 수정
	 * @param ncProduct
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/modify_product.do")
	@ResponseBody
	public String modify_product(NcProduct ncProduct, HttpSession session) {
		ncProduct.setNcpd_div(1);
		ncProduct.setNcpd_setup(true);
		int result = ncProductService.ncProduct_update2(ncProduct);
		ncProduct = ncProductService.getNcProduct();

		String audit_page = MessageUtil.getMessage("log.product.modify");
		String audit_param =
			MessageUtil.getMessage("log.product.technicalsupport") + " = " + ncProduct.getNcpd_tel() + ", " +
			MessageUtil.getMessage("log.product.installationdate") + " = " + ncProduct.getNcpd_date() + ", " +
			MessageUtil.getMessage("log.product.desc") + " = " + ncProduct.getNcpd_desc();
		String audit_result = result == 1 ? "S" : "F";

		ncAuditService.policy_log_insert(
			(String)session.getAttribute("loginUserId"),
			(String)session.getAttribute("clientIp"),
			audit_page, audit_param, audit_result, "I"); // 감사로그 저장

		return result == 1 ? "true" : "DB Error";
	}
	
	/**
	 * 현재 사용 안함
	 * 장비소개 정보 등록
	 * @param ncProduct
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/insert_product.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_product(NcProduct ncProduct, HttpSession session) {
		int result = ncProductService.ncProduct_insert(ncProduct);

		String audit_page = MessageUtil.getMessage("log.product.register");
		String audit_param =
			MessageUtil.getMessage("log.product.name") + " = " +
				ncProduct.getNcpd_name() + "," +
			MessageUtil.getMessage("log.product.model") + " = " +
				ncProduct.getNcpd_model() + "," +
			MessageUtil.getMessage("log.product.sn") + " = " +
				ncProduct.getNcpd_sn() + "," +
			MessageUtil.getMessage("log.product.technicalsupport") + " = " +
				ncProduct.getNcpd_tel() + "," +
			MessageUtil.getMessage("log.product.installationdate") + " = " +
				ncProduct.getNcpd_date() + "," +
			MessageUtil.getMessage("log.product.desc") + " = " +
				ncProduct.getNcpd_desc() + "";
		String audit_result = result == 1 ? "S" : "F";

		ncAuditService.policy_log_insert(
			(String)session.getAttribute("loginUserId"),
			(String)session.getAttribute("clientIp"),
			audit_page, audit_param, audit_result, "I"); // 감사로그 저장

		return result == 1 ? "true" : "DB Error";
	}
	
	/*
	@GetMapping(value="/prod_info.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public NcProduct get_prod_info() {
		return ncProductService.getNcProduct();
	}
	*/
	@PostMapping(value="/prod_info.do")
	@ResponseBody
	public String update_prod_info(NcProduct ncProduct, HttpSession session) {
		String strResult="DB Error";
		
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
		//  초기 설정 전일 때만 실행
		if (ncConfigService.getConfigProd()) {
			return "Error";
		}
		
		String audit_page = MessageUtil.getMessage("product.setting");
		String audit_result="F";
		String audit_risk="W";
		String audit_param = "";

		// 최소 1자 이상 최대 32자 이하인지 체크
		if (ncProduct.getNcpd_sn().length() < 1) {
			return "not_enough_length";
		}
		
		if (ncProduct.getNcpd_sn().length() > 32) {
			return "not_enough_length";
		}

		NcProduct currProdInfo = new NcProduct();

		currProdInfo.setNcpd_sn(ncProduct.getNcpd_sn());
		currProdInfo.setNcpd_setup(true);
		audit_param = MessageUtil.getMessage("product.serial.number")+ ncProduct.getNcpd_sn();
		
		if(ncProductService.ncProduct_update(currProdInfo)>0) {
			audit_result="S";
			audit_risk="I";
			strResult="true";
		}
		
		ncAuditService.mng_log_insert(
				(String)session.getAttribute("loginUserId"),
				(String)session.getAttribute("clientIp"),
				audit_page, audit_param,audit_result,audit_risk); // 감사로그 저장
		
		return strResult;
	}
}