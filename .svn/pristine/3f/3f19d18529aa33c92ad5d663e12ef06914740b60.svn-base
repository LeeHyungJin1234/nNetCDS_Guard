package nnsp.services;

import java.io.*;

import nnsp.common.Config;
import nnsp.mappers.NcConfigMapper;
import nnsp.mappers.NcLinkPolicyMapper;
import nnsp.mappers.NcPolicyMapper;
import nnsp.mappers.NcUserMapper;
import nnsp.vo.NcConfig;
import nnsp.vo.NcLinkPolicy;
import nnsp.vo.NcPolicy;
import nnsp.vo.NcUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcTransferService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcTransferService.class);
	
	@Autowired
	private NcPolicyMapper ncPolicyMapper;
	@Autowired
	private NcLinkPolicyMapper ncLinkPolicyMapper;
	@Autowired
	private NcUserMapper ncUserMapper;
	@Autowired
	private NcConfigMapper ncConfigMapper;
	
	private String transfer_query = Config.getInstance().getTransPath()+"ndr_query";
	
	// 수신 DB에 허용 IP 전달
	public void trans_obj_ip(String action, NcPolicy ncPolicy, int noi_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncPolicyMapper.last_ipaddr_seq();
				line_str = "INSERT INTO NNC_OBJ_IPADDR(NOI_SEQ, NOI_IP_TYPE, NOI_IP, NOI_OBJ_NM, NOI_GCODE, NOI_DIV, NOI_USE_YN) " +
						"VALUES("+seq+",'"+ncPolicy.getNoi_ip_type()+"','"+ncPolicy.getNoi_ip()+"','"+ncPolicy.getNoi_obj_nm()+"','"+ncPolicy.getNoi_gcode()+"','"+ncPolicy.getNoi_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_OBJ_IPADDR " +
						"SET NOI_IP_TYPE='"+ncPolicy.getNoi_ip_type()+"', NOI_IP='"+ncPolicy.getNoi_ip()+"', NOI_OBJ_NM='"+ncPolicy.getNoi_obj_nm()+"', NOI_GCODE='"+ncPolicy.getNoi_gcode()+"', NOI_DIV='"+ncPolicy.getNoi_div()+"' " +
						"WHERE NOI_SEQ="+ncPolicy.getNoi_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_OBJ_IPADDR SET NOI_USE_YN=0 WHERE NOI_SEQ="+noi_seq+";";
			}else if("SYS".equals(action)){ // 환경설정 시스템 설정에서 호출
				line_str = "UPDATE NNC_OBJ_IPADDR SET NOI_IP='"+ncPolicy.getNoi_ip()+"' WHERE NOI_GCODE='"+ncPolicy.getNoi_gcode()+"' AND NOI_DIV='"+ncPolicy.getNoi_div()+"';";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 허용 서비스 전달
	public void trans_obj_service(String action, NcPolicy ncPolicy, int nos_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncPolicyMapper.last_obj_service_seq();
				line_str = "INSERT INTO NNC_OBJ_SERVICE(NOS_SEQ, NOS_PORT, NOS_SERVICE_NM, NOS_OBJ_NM, NOS_GCODE, NOS_DIV, NOS_USE_YN) " +
						"VALUES("+seq+",'"+ncPolicy.getNos_port()+"','"+ncPolicy.getNos_service_nm()+"','"+ncPolicy.getNos_obj_nm()+"','"+ncPolicy.getNos_gcode()+"','"+ncPolicy.getNos_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_OBJ_SERVICE " +
						"SET NOS_PORT='"+ncPolicy.getNos_port()+"', NOS_SERVICE_NM='"+ncPolicy.getNos_service_nm()+"', NOS_OBJ_NM='"+ncPolicy.getNos_obj_nm()+"', NOS_GCODE='"+ncPolicy.getNos_gcode()+"', NOS_DIV='"+ncPolicy.getNos_div()+"' " +
						"WHERE NOS_SEQ="+ncPolicy.getNos_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_OBJ_SERVICE SET NOS_USE_YN=0 WHERE NOS_SEQ="+nos_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 허용 정책 전달
	public void trans_policy_allow(String action, NcPolicy ncPolicy, int npa_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncPolicyMapper.last_allow_seq();
				line_str = "INSERT INTO NNC_POLICY_ALLOW(NPA_SEQ, NPA_SIP_GCODE, NPA_DIP_GCODE, NPA_SSVC_GCODE, NPA_DSVC_GCODE, NPC_NO, NPA_DIV, NPA_USE_YN) " +
						"VALUES("+seq+",'"+ncPolicy.getNpa_sip_gcode()+"','"+ncPolicy.getNpa_dip_gcode()+"','"+ncPolicy.getNpa_ssvc_gcode()+"','"+ncPolicy.getNpa_dsvc_gcode()+"','"+ncPolicy.getNpc_no()+"','"+ncPolicy.getNpa_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_POLICY_ALLOW " +
						"SET NPA_SIP_GCODE='"+ncPolicy.getNpa_sip_gcode()+"', NPA_DIP_GCODE='"+ncPolicy.getNpa_dip_gcode()+"', NPA_SSVC_GCODE='"+ncPolicy.getNpa_ssvc_gcode()+"', NPA_DSVC_GCODE='"+ncPolicy.getNpa_dsvc_gcode()+"', NPC_NO='"+ncPolicy.getNpc_no()+"' "+
						"WHERE NPA_SEQ="+ncPolicy.getNpa_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_POLICY_ALLOW SET NPA_USE_YN=0 WHERE NPA_SEQ="+npa_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 서비스 정책 전달
	public void trans_policy_service(String action, NcPolicy ncPolicy, int nps_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncPolicyMapper.last_service_seq();
				line_str = "INSERT INTO NNC_POLICY_SERVICE(NPS_SEQ, NPC_NO, NCP_SEQ, NPS_TX_INTIP, NPS_TX_INTPORT, NPS_RX_INTIP, NPS_RX_INTPORT, NPS_CONTS_IP, NPS_CONTS_PORT, NPS_ELC_FLAG, NPS_CONF_FILE, NPS_USE_YN) " +
						"VALUES("+seq+",'"+ncPolicy.getNpc_no()+"','"+ncPolicy.getNcp_seq()+"','"+ncPolicy.getNps_tx_intip()+"','"+ncPolicy.getNps_tx_intport()+"','"+ncPolicy.getNps_rx_intip()+"','"+ncPolicy.getNps_rx_intport()+"','"+
						ncPolicy.getNps_conts_ip()+"','"+ncPolicy.getNps_conts_port()+"','"+ncPolicy.getNps_elc_flag()+"','"+ncPolicy.getNps_conf_file()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_POLICY_SERVICE " +
						"SET NCP_SEQ='"+ncPolicy.getNcp_seq()+"', NPS_TX_INTIP='"+ncPolicy.getNps_tx_intip()+"', NPS_TX_INTPORT='"+ncPolicy.getNps_tx_intport()+"', NPS_RX_INTIP='"+ncPolicy.getNps_rx_intip()+"', NPS_RX_INTPORT='"+ncPolicy.getNps_rx_intport()+"', " +
						"NPS_CONTS_IP='"+ncPolicy.getNps_conts_ip()+"', NPS_CONTS_PORT='"+ncPolicy.getNps_conts_port()+"', NPS_ELC_FLAG='"+ncPolicy.getNps_elc_flag()+"', NPC_NO='"+ncPolicy.getNpc_no()+"', NPS_CONF_FILE='"+ncPolicy.getNps_conf_file()+"' " +
						"WHERE NPS_SEQ="+ncPolicy.getNps_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_POLICY_SERVICE SET NPS_USE_YN=0 WHERE NPS_SEQ="+nps_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 접근 IP 전달
	public void trans_access_ip(String action, String nai_ip, String nai_name, int nai_div, int use_yn, String org_nai_ip){
		String line_str="";
		try{
			if("I".equals(action)){
				line_str = "INSERT INTO NNC_ACCESS_IP (NAI_IP, NAI_NAME, NAI_USE_YN, NAI_DIV) VALUES ('"+nai_ip+"','"+nai_name+"',0,"+nai_div+");";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_ACCESS_IP SET NAI_IP='"+nai_ip+"', NAI_NAME='"+nai_name+"', NAI_DIV="+nai_div+" WHERE NAI_IP='"+org_nai_ip+"';";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_ACCESS_IP SET NAI_USE_YN="+use_yn+" WHERE NAI_IP='"+nai_ip+"';";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 로그인 사용자 계정 전달
	public void trans_system_user(String action, NcUser ncUser){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncUserMapper.last_user_seq();
				String encodePassword = ncUser.getNsu_pw(); //passwordEncoder.encodePassword(ncUser.getNsu_pw(), ncUser.getNsu_reg_date());
				line_str = "INSERT INTO NNC_SYSTEM_USER (NSU_SEQ, NSU_ID, NSU_PW, NSU_NAME, NSU_DIVISION, NSU_POSITION, NSU_TEL, NSU_MOBILE, NSU_REG_DATE, NSU_USE_YN, NSU_EMAIL, NSU_SECU_LEVEL, NSU_DESC) " +
						"VALUES ("+seq+",'"+ncUser.getNsu_id()+"','"+encodePassword+"','"+ncUser.getNsu_name()+"','"+ncUser.getNsu_division()+"','"+ncUser.getNsu_position()+"','"+ncUser.getNsu_tel()+"','"+ncUser.getNsu_mobile()+"','"+
						ncUser.getNsu_reg_date()+"',1,'"+ncUser.getNsu_email()+"',"+ncUser.getNsu_secu_level()+",'"+ncUser.getNsu_desc()+"');";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_SYSTEM_USER SET NSU_NAME='"+ncUser.getNsu_name()+"', NSU_DIVISION='"+ncUser.getNsu_division()+"', NSU_POSITION='"+ncUser.getNsu_position()+"', NSU_TEL='"+ncUser.getNsu_tel()+"', " +
						"NSU_MOBILE='"+ncUser.getNsu_mobile()+"', NSU_EMAIL='"+ncUser.getNsu_email()+"', NSU_DESC='"+ncUser.getNsu_desc()+"' " +
						"WHERE NSU_SEQ='"+ncUser.getNsu_seq()+"'";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_SYSTEM_USER SET NSU_USE_YN=0 WHERE NSU_SEQ="+ncUser.getNsu_seq()+";";
			}else if("UID".equals(action)){
				line_str = "UPDATE NNC_SYSTEM_USER SET NSU_ID='"+ncUser.getNsu_id()+"' WHERE NSU_SEQ="+ncUser.getNsu_seq()+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 네트워크 설정 전달
	public void trans_conf_system(String action, NcConfig ncConfig){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncConfigMapper.last_system_seq();
				line_str = "INSERT INTO NNC_CONF_SYSTEM(NCS_SEQ, NCS_INT_IP, NCS_EXT_IP, NCS_MNG_IP, NCS_INT_NM, NCS_EXT_NM, NCS_MNG_NM, NCS_EXT_GW, NCS_INT_GW, NCS_MAC, NCS_DNS, NCS_ELC_DELAY, NCS_DIV, NCS_USE_YN) " +
						"VALUES("+seq+",'"+ncConfig.getNcs_int_ip()+"','"+ncConfig.getNcs_ext_ip()+"','"+ncConfig.getNcs_mng_ip()+"','"+ncConfig.getNcs_int_nm()+"','"+ncConfig.getNcs_ext_nm()+"','"+ncConfig.getNcs_mng_nm()+"'," +
								"'"+ncConfig.getNcs_ext_gw()+"','"+ncConfig.getNcs_int_gw()+"','"+ncConfig.getNcs_mac()+"','"+ncConfig.getNcs_dns()+"','"+ncConfig.getNcs_elc_delay()+"','"+ncConfig.getNcs_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_CONF_SYSTEM SET NCS_INT_IP='"+ncConfig.getNcs_int_ip()+"', NCS_EXT_IP='"+ncConfig.getNcs_ext_ip()+"', NCS_MNG_IP='"+ncConfig.getNcs_mng_ip()+"', NCS_INT_NM='"+ncConfig.getNcs_int_nm()+"', " +
						"NCS_EXT_NM='"+ncConfig.getNcs_ext_nm()+"', NCS_MNG_NM='"+ncConfig.getNcs_mng_nm()+"', NCS_EXT_GW='"+ncConfig.getNcs_ext_gw()+"', NCS_INT_GW='"+ncConfig.getNcs_int_gw()+"', NCS_MAC='"+ncConfig.getNcs_mac()+"', " +
						"NCS_DNS='"+ncConfig.getNcs_dns()+"', NCS_ELC_DELAY='"+ncConfig.getNcs_elc_delay()+"' " +
						"WHERE NCS_SEQ='"+ncConfig.getNcs_seq()+"';";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 로그인 설정 정보 전달
	public void trans_conf_login(int ncli_lock_failcnt, int ncli_lock_date){
		String line_str="UPDATE NNC_CONF_LOGIN SET NCLI_LOCK_FAILCNT='"+ncli_lock_failcnt+"', NCLI_LOCK_DATE='"+ncli_lock_date+"';";
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 로그 설정 정보 전달
	public void trans_conf_log(int ncl_cycle, String ncl_server_ip){
		String line_str="UPDATE NNC_CONF_LOG SET NCL_CYCLE='"+ncl_cycle+"', NCL_SERVER_IP='"+ncl_server_ip+"';";
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 메일 설정 정보 전달
	public void trans_conf_email(NcConfig ncConfig){
		String line_str="UPDATE NNC_CONF_EMAIL " +
				"SET NCE_HOST='"+ncConfig.getNce_host()+"', NCE_PORT="+ncConfig.getNce_port()+", NCE_ID='"+ncConfig.getNce_id()+"', NCE_PW='"+ncConfig.getNce_pw()+"', NCE_CYCLE="+ncConfig.getNce_cycle()+", NCE_FROM_EMAIL='"+ncConfig.getNce_from_email()+"';";
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 무결성 점검 주기 전달
	public void trans_conf_inspect(int nci_cycle){
		String line_str="UPDATE NNC_CONF_INSPECT SET NCI_CYCLE="+nci_cycle+";";
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 무결성 점검 주기 전달
	public void trans_inspect_flag(int ncp_div, int ncp_inspect_flag){
		String line_str="";
		if(ncp_div==0){
			line_str="UPDATE NNC_CONF_PROGRAM SET NCP_INSPECT_FLAG="+ncp_inspect_flag+" WHERE NCP_INSPECT_FLAG=0;";
		}else{
			line_str="UPDATE NNC_CONF_PROGRAM SET NCP_INSPECT_FLAG="+ncp_inspect_flag+" WHERE NCP_DIV="+ncp_div+" AND NCP_INSPECT_FLAG=0;";
		}
		
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 연계서버 허용 IP 전달
	public void trans_link_ip(String action, NcLinkPolicy ncLinkPolicy, int nli_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncLinkPolicyMapper.last_ipaddr_seq();
				line_str = "INSERT INTO NNC_LINK_IPADDR(NLI_SEQ, NLI_IP_TYPE, NLI_IP, NLI_IP_NM, NLI_GCODE, NLI_DIV, NLI_USE_YN) " +
						"VALUES("+seq+",'"+ncLinkPolicy.getNli_ip_type()+"','"+ncLinkPolicy.getNli_ip()+"','"+ncLinkPolicy.getNli_ip_nm()+"','"+ncLinkPolicy.getNli_gcode()+"','"+ncLinkPolicy.getNli_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_LINK_IPADDR " +
						"SET NLI_IP_TYPE='"+ncLinkPolicy.getNli_ip_type()+"', NLI_IP='"+ncLinkPolicy.getNli_ip()+"', NLI_IP_NM='"+ncLinkPolicy.getNli_ip_nm()+"', NLI_GCODE='"+ncLinkPolicy.getNli_gcode()+"', NLI_DIV='"+ncLinkPolicy.getNli_div()+"' " +
						"WHERE NLI_SEQ="+ncLinkPolicy.getNli_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_LINK_IPADDR SET NLI_USE_YN=0 WHERE NLI_SEQ="+nli_seq+";";
			}else if("SYS".equals(action)){ // 환경설정 시스템 설정에서 호출
				line_str = "UPDATE NNC_LINK_IPADDR SET NLI_IP='"+ncLinkPolicy.getNli_ip()+"' WHERE NLI_GCODE='"+ncLinkPolicy.getNli_gcode()+"' AND NLI_DIV='"+ncLinkPolicy.getNli_div()+"';";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 연계 네트워크 설정 전달
	public void trans_conf_link(String action, NcConfig ncConfig){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncConfigMapper.last_link_seq();
				line_str = "INSERT INTO NNC_CONF_LINK(NCLK_SEQ, NCLK_RCV_IP, NCLK_SND_IP, NCLK_RCV_NM, NCLK_SND_NM, NCLK_SND_GW, NCLK_DIV, NCLK_USE_YN) " +
						"VALUES("+seq+",'"+ncConfig.getNclk_rcv_ip()+"','"+ncConfig.getNclk_snd_ip()+"','"+ncConfig.getNclk_rcv_nm()+"','"+ncConfig.getNclk_snd_nm()+"','"+ncConfig.getNclk_snd_gw()+"','"+ncConfig.getNclk_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_CONF_LINK " +
						"SET NCLK_RCV_IP='"+ncConfig.getNclk_rcv_ip()+"', NCLK_SND_IP='"+ncConfig.getNclk_snd_ip()+"', NCLK_RCV_NM='"+ncConfig.getNclk_rcv_nm()+"', NCLK_SND_NM='"+ncConfig.getNclk_snd_nm()+"', NCLK_SND_GW='"+ncConfig.getNclk_snd_gw()+"' " +
						"WHERE NCLK_SEQ='"+ncConfig.getNclk_seq()+"';";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 연계 서버 재시작 전달
	public int trans_link_restart(){
		try{
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write("UPDATE NNC_CONF_LINK SET NCLK_RESTART=1 WHERE NCLK_USE_YN=1;");
			out.newLine();
			out.close();
			return 0;
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
			return 1;
		}
	}
	
	// 수신 DB에 허용 서비스 전달
	public void trans_link_service(String action, NcLinkPolicy ncLinkPolicy, int nls_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncLinkPolicyMapper.last_obj_service_seq();
				line_str = "INSERT INTO NNC_LINK_SERVICE(NLS_SEQ, NLS_PORT, NLS_SERVICE_NM, NLS_GCODE, NLS_DIV, NLS_USE_YN) " +
						"VALUES("+seq+",'"+ncLinkPolicy.getNls_port()+"','"+ncLinkPolicy.getNls_service_nm()+"','"+ncLinkPolicy.getNls_gcode()+"','"+ncLinkPolicy.getNls_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_LINK_SERVICE " +
						"SET NLS_PORT='"+ncLinkPolicy.getNls_port()+"', NLS_SERVICE_NM='"+ncLinkPolicy.getNls_service_nm()+"', NLS_GCODE='"+ncLinkPolicy.getNls_gcode()+"', NLS_DIV='"+ncLinkPolicy.getNls_div()+"' " +
						"WHERE NLS_SEQ="+ncLinkPolicy.getNls_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_LINK_SERVICE SET NLS_USE_YN=0 WHERE NLS_SEQ="+nls_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 허용 정책 전달
	public void trans_link_allow(String action, NcLinkPolicy ncLinkPolicy, int nla_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncLinkPolicyMapper.last_allow_seq();
				line_str = "INSERT INTO NNC_LINK_ALLOW(NLA_SEQ, NLA_NAME, NLA_IP_GCODE, NLA_SVC_GCODE, NLP_SEQ, NLA_DIV, NLA_USE_YN) " +
						"VALUES("+seq+",'"+ncLinkPolicy.getNla_name()+"','"+ncLinkPolicy.getNla_ip_gcode()+"','"+ncLinkPolicy.getNla_svc_gcode()+"','"+ncLinkPolicy.getNlp_seq()+"','"+ncLinkPolicy.getNla_div()+"',1);";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_LINK_ALLOW " +
						"SET NLA_NAME='"+ncLinkPolicy.getNla_name()+"', NLA_IP_GCODE='"+ncLinkPolicy.getNla_ip_gcode()+"', NLA_SVC_GCODE='"+ncLinkPolicy.getNla_svc_gcode()+"', NLP_SEQ='"+ncLinkPolicy.getNlp_seq()+"' "+
						"WHERE NLA_SEQ="+ncLinkPolicy.getNla_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_LINK_ALLOW SET NLA_USE_YN=0 WHERE NLA_SEQ="+nla_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 수신 DB에 연계 정책 전달
	public void trans_link_policy(String action, NcLinkPolicy ncLinkPolicy, int nlp_seq){
		String line_str="";
		try{
			if("I".equals(action)){
				int seq = ncLinkPolicyMapper.last_service_seq();
				line_str = "INSERT INTO NNC_LINK_POLICY(NLP_SEQ, NLP_NAME, NPC_NO, NPCS_SEQ, NLP_NW_IP, NLP_NW_PORT, NLP_OPC_SERVER, NLP_DB_NAME, NLP_RCV_PORT, NLP_ACCOUNT, NLP_PASSWD, NLP_CONTROL_PORT, NLP_USE_YN, NLP_DIV) " +
						"VALUES("+seq+",'"+ncLinkPolicy.getNlp_name()+"','"+ncLinkPolicy.getNpc_no()+"','"+ncLinkPolicy.getNpcs_seq()+"','"+ncLinkPolicy.getNlp_nw_ip()+"','"+ncLinkPolicy.getNlp_nw_port()+"','"+ncLinkPolicy.getNlp_opc_server()+"','"+
						ncLinkPolicy.getNlp_db_name()+"','"+ncLinkPolicy.getNlp_rcv_port()+"','"+ncLinkPolicy.getNlp_account()+"','"+ncLinkPolicy.getNlp_passwd()+"','"+ncLinkPolicy.getNlp_control_port()+"',1,'"+ncLinkPolicy.getNlp_div()+"');";
			}else if("U".equals(action)){
				line_str = "UPDATE NNC_LINK_POLICY " +
						"SET NLP_NAME='"+ncLinkPolicy.getNlp_name()+"', NPC_NO='"+ncLinkPolicy.getNpc_no()+"', NPCS_SEQ='"+ncLinkPolicy.getNpcs_seq()+"', NLP_NW_IP='"+ncLinkPolicy.getNlp_nw_ip()+"', NLP_NW_PORT='"+ncLinkPolicy.getNlp_nw_port()+"', " +
						"NLP_OPC_SERVER='"+ncLinkPolicy.getNlp_opc_server()+"', NLP_DB_NAME='"+ncLinkPolicy.getNlp_db_name()+"', " +
						"NLP_RCV_PORT='"+ncLinkPolicy.getNlp_rcv_port()+"', NLP_ACCOUNT='"+ncLinkPolicy.getNlp_account()+"', NLP_PASSWD='"+ncLinkPolicy.getNlp_passwd()+"', NLP_CONTROL_PORT='"+ncLinkPolicy.getNlp_control_port()+"' " +
						"WHERE NLP_SEQ="+ncLinkPolicy.getNlp_seq()+";";
			}else if("D".equals(action)){
				line_str = "UPDATE NNC_LINK_POLICY SET NLP_USE_YN=0 WHERE NLP_SEQ="+nlp_seq+";";
			}
			
			File f1 = new File(transfer_query);
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}

}