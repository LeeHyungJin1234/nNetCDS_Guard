package nnsp.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import nnsp.vo.NcConfig;
import nnsp.vo.NcLinkPolicy;
import nnsp.vo.NcProduct;
import nnsp.vo.NcSysLoad;
import nnsp.common.Config;
import nnsp.mappers.NcConfigMapper;
import nnsp.security.NcSecurityUtil;
import nnsp.util.MessageUtil;

import org.springframework.beans.factory.annotation.Autowired;

import static nnsp.security.Constants.*;

@Component
public class NcConfigService {

	@Autowired
	private NcConfigMapper ncConfigMapper;
    @Autowired
    private NcAuditService ncAuditService;
	
	private static NcConfigService ncConfigService;
	public static NcConfigService getInstance() {
		if (NcConfigService.ncConfigService == null) {
			ncConfigService = new NcConfigService();
		}
		return ncConfigService;
	}

	// 로그 관리 정보 업데이트
	public int config_log_update(int ncl_cycle){
		return  ncConfigMapper.config_log_update(ncl_cycle);
	}
	
	// 로그 관리 정보 조회
	public NcConfig getConfigLog(){
		return  ncConfigMapper.getConfigLog();
	}
	
	// 로그인 설정 업데이트
	public int config_login_update(int ncli_lock_failcnt, int ncli_lock_date){
		return ncConfigMapper.config_login_update(ncli_lock_failcnt, ncli_lock_date);
	}
	
	// 로그인 설정 조회
	public NcConfig getConfigLogIn(){
		return ncConfigMapper.getConfigLogIn();
	}

	// 이메일 설정 업데이트
	public int config_email_update(NcConfig ncConfig) {
		return ncConfigMapper.config_email_update(ncConfig);
	}
	
	// 이메일 사용여부 업데이트
	public int mail_use_yn(int nce_use_yn) {
		return ncConfigMapper.mail_use_yn(nce_use_yn);
	}


	// 이메일 설정 조회
	public NcConfig getConfigeEmail() {
		return ncConfigMapper.getConfigEmail();
	}
    public String getConfigEMailPw() {
        return ncConfigMapper.getConfigEMailPw();
    }
	
	// 송/수신 네트워크 설정 조회
	public NcConfig getConfigSystem(int ncs_div){
		return ncConfigMapper.getConfigSystem(ncs_div);
	}
	
	// 송/수신 네트워크 설정 수정
	public int config_system_update(NcConfig ncConfig){
		return ncConfigMapper.config_system_update(ncConfig);
	}
	
	// 송/수신 네트워크 설정 등록
	public int config_system_insert(NcConfig ncConfig){
		return ncConfigMapper.config_system_insert(ncConfig);
	}
	
	// 연계 네트워크 설정 조회
	public ArrayList<NcConfig> getConfigLink(int nclk_div){
		return ncConfigMapper.getConfigLink(nclk_div);
	}
	
	// 연계네트워크 설정 수정
	public int config_link_update(NcConfig ncConfig){
		return ncConfigMapper.config_link_update(ncConfig);
	}
	
	// 연계 네트워크 설정 등록
	public int config_link_insert(NcConfig ncConfig){
		return ncConfigMapper.config_link_insert(ncConfig);
	}
	
	// 송/수신 네트워크 설정 삭제
	public int config_system_delete(int ncs_seq){
		return ncConfigMapper.config_system_delete(ncs_seq);
	}
	
	public int config_deamon_update(int ncd_seq, String ncd_name, int ncd_div, int ncd_status, String ncd_path){
		return ncConfigMapper.config_deamon_update(ncd_seq, ncd_name, ncd_div, ncd_status, ncd_path);
	}
	
	public int config_deamon_insert(String ncd_name, int ncd_div, int ncd_status, String ncd_path){
		return ncConfigMapper.config_deamon_insert(ncd_name, ncd_div, ncd_status, ncd_path);
	}
	
	public int edit_deamon_useyn(int ncd_seq, int ncd_use_yn, int ncd_status){
		return ncConfigMapper.edit_deamon_useyn(ncd_seq, ncd_use_yn, ncd_status);
	}
	
	// 송/수신 서비스 프로그램 조회
	public ArrayList<NcConfig> getConfigProgram(int ncp_div){
		return ncConfigMapper.getConfigProgram(ncp_div);			
	}
	
	// 송/수신 서비스 데몬 조회 - 서비스 정책에서 사용
	public ArrayList<NcConfig> getServiceProgram(int ncp_div){
		return ncConfigMapper.getServiceProgram(ncp_div);			
	}
	
	// 송/수신 서비스 데몬 조회 - 서비스 정책에서 사용
	public NcConfig serviceProgramBySeq(int ncp_seq){
		return ncConfigMapper.serviceProgramBySeq(ncp_seq);			
	}
	
	// 메인 송/수신 서비스 프로그램 조회
	public NcConfig getServiceStatus(){
		return ncConfigMapper.getServiceStatus();			
	}
	
	// 메인 서비스 프로그램 시작/중지
	public int service_status_update(int ncp_status){
		return ncConfigMapper.service_status_update(ncp_status);
	}
	
	// 무결성 점검 주기 조회
	public NcConfig getInspectCycle(int nci_div){
		return ncConfigMapper.getInspectCycle(nci_div);			
	}

	// 무결성 점검 주기 수정
	public int inspect_cycle_update(NcConfig ncConfig){
		return ncConfigMapper.inspect_cycle_update(ncConfig);
	}
	
	// 검사 플래그 수정
	public int inspect_flag_update(int ncp_div, int ncp_inspect_flag){
		if(ncp_div==0){
			return ncConfigMapper.inspect_flag_allupdate(ncp_inspect_flag);
		}else{
			return ncConfigMapper.inspect_flag_update(ncp_inspect_flag, ncp_div);
		}
	}
	
	// 검사 중 프로그램 확인
	public int inspect_ing_count(){
		return ncConfigMapper.inspect_ing_count();
	}
	
	// 환경설정 파일 만들기
	/*public void create_configFile(NdConfig ncConfig) {
		String line_str="";
		try {
			if(ncConfig.getNcs_div()==1){ // 송신
				
				ArrayList<NdConfig> systemList = ncConfigMapper.getConfigSystem(2);
				if(systemList.size()>0){ // 송신은 수신 외부 IP와 MAC이 필요. 수신 정보가 없으면 만들지 말 것
				
					line_str="## one-way communication system configuration file : transmission" +
							"\r\n\r\n# one-way transmission network configuration" +
							"\r\n[one-way transmission network]" +
							"\r\nIP_EXT = "+ ncConfig.getNcs_ext_ip() +
							"\r\nNM_EXT = "+ ncConfig.getNcs_ext_nm() +
							"\r\nGW_EXT = "+ ncConfig.getNcs_ext_gw() +
							"\r\nIP_INT = "+ ncConfig.getNcs_int_ip() +
							"\r\nNM_INT = "+ ncConfig.getNcs_int_nm() +
							"\r\nIP_MNG = "+ ncConfig.getNcs_mng_ip() +
							"\r\nNM_MNG = "+ ncConfig.getNcs_mng_nm() +
							"\r\nDNS_EXT = "+ ncConfig.getNcs_dns() +
							"\r\n\r\n# one-way receive network configuration" +
							"\r\n[one-way receive local]" +
							"\r\nIP_RINT = "+ systemList.get(0).getNcs_int_ip() +
							"\r\nMAC_RINT = "+ systemList.get(0).getNcs_mac() +
							"\r\n\r\n# one-way transmission DB queue size" +
							"\r\n[DB queue]" +
							"\r\nMAX_QUEUE = 512000";
					
					File f1 = new File(Config.getInstance().getSndPath()+"nds_conf");
					if(f1.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
						f1.delete();
					}
					f1.createNewFile();
					
					BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
					out.write(line_str);
					out.close();
				}
				
			}else{ // 수신
				line_str="## one-way communication system configuration file : receive" +
						"\r\n\r\n# one-way receive network configuration" +
						"\r\n[one-way receive network]" +
						"\r\nIP_EXT = "+ ncConfig.getNcs_ext_ip() +
						"\r\nNM_EXT = "+ ncConfig.getNcs_ext_nm() +
						"\r\nGW_EXT = "+ ncConfig.getNcs_ext_gw() +
						"\r\nIP_INT = "+ ncConfig.getNcs_int_ip() +
						"\r\nNM_INT = "+ ncConfig.getNcs_int_nm() +
						"\r\nIP_MNG = "+ ncConfig.getNcs_mng_ip() +
						"\r\nNM_MNG = "+ ncConfig.getNcs_mng_nm() +
						"\r\nDNS_EXT = "+ ncConfig.getNcs_dns() +
						"\r\n\r\n# one-way transmission DB queue size" +
						"\r\n[DB queue]" +
						"\r\nMAX_QUEUE = 512000";
				
				File f1 = new File(Config.getInstance().getTransPath()+"ndr_conf");
				if(f1.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
					f1.delete();
				}
				f1.createNewFile();
				
				BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
				out.write(line_str);
				out.close();
				
				line_str="## one-way communication system configuration file : receive" +
						"\r\n\r\n[one-way receive elc delay time]" +
						"\r\n# range : 60 ~ 180" +
						"\r\nELC_DELAY = "+ncConfig.getNcs_elc_delay();
				
				File f2 = new File(Config.getInstance().getTransPath()+"ndr_elc_conf");
				if(f2.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
					f2.delete();
				}
				f2.createNewFile();
				
				BufferedWriter out1 = new BufferedWriter(new FileWriter(f2, true));
				out1.write(line_str);
				out1.close();
				
				ArrayList<NdConfig> systemList = ncConfigMapper.getConfigSystem(1);
				if(systemList.size()>0 && (!ncConfig.getNcs_int_ip().equals(ncConfig.getOrg_int_ip()) || !ncConfig.getNcs_mac().equals(ncConfig.getOrg_mac()))){ // 수신 외부 IP나 MAC이 변경되었으면 송신도 다시 만들어 줘야 함
					
					line_str="## one-way communication system configuration file : transmission" +
							"\r\n\r\n# one-way transmission network configuration" +
							"\r\n[one-way transmission network]" +
							"\r\nIP_EXT = "+ systemList.get(0).getNcs_ext_ip() +
							"\r\nNM_EXT = "+ systemList.get(0).getNcs_ext_nm() +
							"\r\nGW_EXT = "+ systemList.get(0).getNcs_ext_gw() +
							"\r\nIP_INT = "+ systemList.get(0).getNcs_int_ip() +
							"\r\nNM_INT = "+ systemList.get(0).getNcs_int_nm() +
							"\r\nIP_MNG = "+ systemList.get(0).getNcs_mng_ip() +
							"\r\nNM_MNG = "+ systemList.get(0).getNcs_mng_nm() +
							"\r\nDNS_EXT = "+ systemList.get(0).getNcs_dns() +
							"\r\n\r\n# one-way receive network configuration" +
							"\r\n[one-way receive local]" +
							"\r\nIP_RINT = "+ ncConfig.getNcs_int_ip() +
							"\r\nMAC_RINT = "+ ncConfig.getNcs_mac() +
							"\r\n\r\n# one-way transmission DB queue size" +
							"\r\n[DB queue]" +
							"\r\nMAX_QUEUE = 512000";
					
					File f3 = new File(Config.getInstance().getSndPath()+"nds_conf");
					if(f3.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
						f3.delete();
					}
					f3.createNewFile();
					
					BufferedWriter out2 = new BufferedWriter(new FileWriter(f3, true));
					out2.write(line_str);
					out2.close();
				}
			}
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}*/
	
	// 메일 설정 파일 만들기
	@SuppressWarnings("resource")
	public void create_conf_email(NcConfig ncConfig) {
		try{
			String line_str = "## one-way communication system email configuration file" +
					"\r\n\r\n[one-way system email configuration]" +
					"\r\nEMAIL_HOST = "+ ncConfig.getNce_host() +
					"\r\nEMAIL_PORT = "+ ncConfig.getNce_port() +
					"\r\nEMAIL_ID = "+ new String(Base64.encodeBase64(ncConfig.getNce_id().getBytes())) +
					"\r\nEMAIL_PW = "+ ncConfig.getNce_pw() +
					"\r\nEMAIL_SEND = "+ ncConfig.getNce_from_email();
			
			File f1 = new File(Config.getInstance().getTransPath()+"ndr_email_conf");
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
	
	// 송/수신 시스템 버전정보
	public NcConfig getSysVersion(int ncs_div){
		return ncConfigMapper.getSysVersion(ncs_div);			
	}
	
	// 자체 시험 수동 검사 상태 체크
	public int protection_status(){
		return ncConfigMapper.protection_status();
	}

	// 송신측 자체 시험 수동 검사 상태 변경
	public int protection_start() {
		return ncConfigMapper.start_self_test();
	}

	// 송신측 자체 시험 수동 검사 마지막 검사 시간
	public String protection_last_datetime() {
		return ncConfigMapper.protection_last_datetime();
	}

	// 송신측 자체 시험 결과 중 CPU, RAM, Disk 사용량 정보
	public NcSysLoad protection_system_load() {
		return ncConfigMapper.protection_system_load();
	}
	
	// 수신측 무결성 관련 데몬 실행
	public void trans_inspect_start(int type) {
		try{
			String line_str = "";
			if(type==1){ // 무결성 검사
				line_str="/nnc-rcv/system/bin/ndr_inte_chk";
			}else{ // 무결성 값 업데이트
				line_str="/nnc-rcv/system/bin/ndr_inte_udt";
			}
			
			File f1 = new File(Config.getInstance().getTransPath()+"ndr_cmd");
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
	
	public int inspect_start(int type, int nci_div) {
		/*try {
			String line_str = "";
			if(type==1){ // 무결성 검사
				line_str="/nnd-snd/system/bin/nds_inte_chk";
			}else{ // 무결성 값 업데이트
				line_str="/nnd-snd/system/bin/nds_inte_udt";
			}
			
		    Process process = Runtime.getRuntime().exec(line_str);
		    process.waitFor();
		    
		    int result = process.exitValue();
		    if ( result != 0 ) {
		    	if(type==1){ // 무결성 검사
		    		System.out.println("무결성 검사 데몬 실행 실패");
		    	}else{
		    		System.out.println("무결성 업데이트 데몬 실행 실패");
		    	}
		    }

		    process.destroy();
		    return result;
		} catch(Exception e) {
			System.out.println(e);
			return 1;
		}*/
		if(type==1){ // 무결성 검사
			return ncConfigMapper.start_inspect_passive(nci_div);
		}else{ // 무결성값 업데이트
			return ncConfigMapper.start_inspect_update(nci_div);
		}
	}
	
	// 송신측 무결성 관련 데몬 실행
	public int inspect_start(int type, int nci_div, String nsu_id) {
		/*try {
			String line_str = "";
			if(type==1){ // 무결성 검사
				line_str="/nnd-snd/system/bin/nds_inte_chk";
			}else{ // 무결성 값 업데이트
				line_str="/nnd-snd/system/bin/nds_inte_udt";
			}
			
		    Process process = Runtime.getRuntime().exec(line_str);
		    process.waitFor();
		    
		    int result = process.exitValue();
		    if ( result != 0 ) {
		    	if(type==1){ // 무결성 검사
		    		System.out.println("무결성 검사 데몬 실행 실패");
		    	}else{
		    		System.out.println("무결성 업데이트 데몬 실행 실패");
		    	}
		    }

		    process.destroy();
		    return result;
		} catch(Exception e) {
			System.out.println(e);
			return 1;
		}*/
		if(type==1){ // 무결성 검사
			return ncConfigMapper.start_inspect_passive_id(nci_div, nsu_id);
		}else{ // 무결성값 업데이트
			return 0;//return ncConfigMapper.start_inspect_update(nci_div);
		}
	}
	
	// 실행 중인 무결성 관련 데몬 조회
	public int inspect_ing_deamon(int nci_div){
		return ncConfigMapper.inspect_ing_deamon(nci_div);
	}
	
	// 송신측 데몬 재시작
	public int nds_restart() {
		try {
			Process process = Runtime.getRuntime().exec("/nnc-snd/system/bin/nds_control.sh restart");
		    process.waitFor();
		    
		    int result = process.exitValue();
		    if ( result != 0 ) {
		    	System.out.println("내부전송통제서버 서비스 재시작 실패");
		    }

		    process.destroy();
		    return result;
		} catch(Exception e) {
			System.out.println(e);
			return 1;
		}
	}
	
	// 수신측 데몬 재시작
	public int trans_ndr_restart() {
		try{
			File f1 = new File(Config.getInstance().getTransPath()+"ndr_cmd");
			if(!f1.exists()){
				f1.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write("/nnc-rcv/system/bin/ndr_control.sh restart");
			out.newLine();
			out.close();
			return 0;
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
			return 1;
		}
	}
	
	// 연계 서버 재시작 플래그 변경
	public int link_restart_update(){
		return ncConfigMapper.link_restart_update();
	}
	
	// 시스템 재시작 플래그 변경
	public int system_restart_update(int ncs_div){
		return ncConfigMapper.system_restart_update(ncs_div);
	}
	public int system_restart_update(int ncs_div, String nsu_id){
		return ncConfigMapper.system_restart_update_id(ncs_div, nsu_id);
	}
	
	// DB 정보 가져오기
	public String getConfigDB(){
		return ncConfigMapper.getConfigDB();
	}

	// jdbc.properties 변경
	public int setJdbcProperties(String pw){
		String strDb_pw = null;

        String audit_page = MessageUtil.getMessage("product.setting");
        if (pw != null && !pw.equals("")) {

            String strDek = this.getDek();
            if (strDek != null) {
                strDb_pw = nnsp.security.ARIAUtil.ariaEncrypt(pw, strDek, audit_page);
                if (strDb_pw == null) {
                    return 1;
                }
                try {
                    strDek.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                pw.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Properties prop = new Properties();
        Properties prop1 = new Properties();
        InputStream input = null;
        OutputStream output = null;

        try {
            String filename = "jdbc.properties";
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("jdbc.properties 파일 검색 실패");
                return 1;
            }

            prop.load(input);

            URL url = this.getClass().getClassLoader().getResource(filename);
            output = new FileOutputStream(new File(url.toURI()));

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                if ("jdbc.password".equals(key)) {
                    prop1.setProperty(key, strDb_pw);
                } else {
                    prop1.setProperty(key, value);
                }
            }

            prop1.store(output, null);
            input.close();
            output.close();
            return 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                input.close();
                output.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return 1;
        }
	}
	
	// 평문 db정보 임시저장
	public void setDbConftxt(HttpServletRequest req) {
		try{
			String line_str = "# one-way system db configuration" +
					"\r\n\r\n[DB Info]" +
					"\r\nDB_SVR = localhost" +
					"\r\nDB_USER = nnsp" +
					"\r\nDB_PASS = "+req.getParameter("new_passwd") +
					"\r\nDB_NAME = nnet_cds2" +
					"\r\nDB_PORT = 3306";
			
			File f1 = new File("D:/Tx/Manager/tmp/nnc_db_conf_txt");
			if(f1.exists()){
				f1.delete();
			}
			f1.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// db 초기 패스워드 변경 기록
	public int insert_config_db(){
		return ncConfigMapper.insert_config_db();
	}
	// 초기설정 완료 날짜 기록
	public int update_complete_date(){
		return ncConfigMapper.update_complete_date();
	}
	
	// db설정 스크립트 실행
	@SuppressWarnings("unused")
	public void init_db_start() {
		try {
			Process process = Runtime.getRuntime().exec("/nnc-snd/system/bin/nnc_init_db.sh &");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 프로그램 중복 체크
	public int program_count(NcConfig ncConfig) {
		return ncConfigMapper.program_count(ncConfig);
    }
	
	// 프로그램 등록,수정,삭제
	public int program_insert(NcConfig ncConfig){
		return ncConfigMapper.program_insert(ncConfig);
	}
	public int program_update(NcConfig ncConfig){
		return ncConfigMapper.program_update(ncConfig);
	}
	public int program_delete(int ncp_seq){
		return ncConfigMapper.program_delete(ncp_seq);
	}
	// 프로그램 관리 목록
	public ArrayList<NcConfig> program_list(int ncp_service_flag, int ncp_div){
		if(ncp_div==0){
			return ncConfigMapper.program_list(ncp_service_flag);
		}else{
			return ncConfigMapper.program_listbydiv(ncp_service_flag, ncp_div);
		}
	}
	
	// 프로그램 관리 목록
	public ArrayList<NcConfig> program_rvs_list(int ncp_service_flag, int ncp_div){
		if(ncp_div==0){
			return ncConfigMapper.program_rvs_list(ncp_service_flag);
		}else{
			return ncConfigMapper.program_listbydiv(ncp_service_flag, ncp_div);
		}
	}
		
	// 시스템 원격 터미널 플래그 변경
	public int system_remote_update(int ncs_remote_flag, int ncs_div){
		return ncConfigMapper.system_remote_update(ncs_remote_flag, ncs_div);
	}
	
	// 콘텐츠 목록
	public ArrayList<NcLinkPolicy> getContents(){
		return ncConfigMapper.getContents();
	}
	
	// 콘텐츠 목록
	public NcLinkPolicy getContentsBySeq(int npcs_seq){
		return ncConfigMapper.getContentsBySeq(npcs_seq);
	}
		
	// 프로토콜 목록
	public ArrayList<NcLinkPolicy> getProtocol(){
		return ncConfigMapper.getProtocol();
	}
	
	// 콘텐츠 등록,수정,삭제
	public int contents_insert(NcLinkPolicy ncLinkPolicy){
		return ncConfigMapper.contents_insert(ncLinkPolicy);
	}
	public int contents_update(NcLinkPolicy ncLinkPolicy){
		return ncConfigMapper.contents_update(ncLinkPolicy);
	}
	public int contents_delete(int npcs_seq){
		return ncConfigMapper.contents_delete(npcs_seq);
	}
	
	// 콘텐츠 중복 확인
	public int contents_count(NcLinkPolicy ncLinkPolicy){
		return ncConfigMapper.contents_count(ncLinkPolicy);
	}
	
	// 정책에서 사용중인 콘텐츠인지 확인
	public int contents_use_count(int npcs_seq){
		return ncConfigMapper.contents_use_count(npcs_seq);
	}
	
	// 정책에서 사용중인 콘텐츠인지 확인
	public int program_use_count(int ncp_seq){
		return ncConfigMapper.program_use_count(ncp_seq);
	}

	public int getProtectTotalCnt() {
		// TODO Auto-generated method stub
		return this.ncConfigMapper.getProtectTotalCnt();
	}
	
	public int getServiceTotalCnt() {// 자체시험
		// TODO Auto-generated method stub
		return this.ncConfigMapper.getServiceTotalCnt();
	}
	

	public List<NcConfig> getProtectList(int nStart, int list_num) {
		// TODO Auto-generated method stub
		return this.ncConfigMapper.getProtectList(nStart, list_num);
	}
	
	public ArrayList<NcConfig> getfile_size(int nStart, int list_num) {
		// TODO Auto-generated method stub
		return this.ncConfigMapper.getfile_size(nStart, list_num);
	}

	public ArrayList<NcConfig> getService_size(int nStart, int list_num) {
		// TODO Auto-generated method stub
		return this.ncConfigMapper.getService_size(nStart, list_num);
	}

	public int setConfigProperties(String setKey, String setValue) {
		Properties prop = new Properties();
        Properties prop1 = new Properties();
    	InputStream input = null;
    	OutputStream output = null;
    	
		try {
			String filename = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("config.properties 파일 검색 실패");
				return 1;
			}

			prop.load(input);
			
			URL url = this.getClass().getClassLoader().getResource(filename);
			output = new FileOutputStream(new File(url.toURI()));

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				if(setKey.equals(key)){
					prop1.setProperty(key, setValue);
				}
				else{
					prop1.setProperty(key, value);
				}
			}

			prop1.store(output, null);
			input.close();
			output.close();
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				input.close();
				output.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return 1;
		}
	
		
	}
	
	/* KEK/DEK */

    @SuppressWarnings({ "unused", "rawtypes" })
	public boolean createKey(String pw) {
        boolean bResult = false;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        SecureRandom sr = NcSecurityUtil.getSecureRandom();
		//   DEK 생성
		byte[] dek = new byte[DEK_LENGTH];
		sr.nextBytes(dek);

        String audit_page =  MessageUtil.getMessage("log.passwardkeycreate");

        String strDek = Hex.encodeHexString(dek);

        String strEncDek = null;

        try {
            Class AriaEngine = Class.forName("nnsp.security.ARIAEngine");

            strEncDek = nnsp.security.ARIAUtil.ariaEncrypt(strDek, pw, audit_page);

            int iKekFileLength = pw.length() / 2;
            String strKek_a = pw.substring(0, iKekFileLength);
            String strKek_b = pw.substring(iKekFileLength);
            boolean bResultKekA = this.createFile(strKek_a, KEK_A_DIR);  //   KEK A 파일 생성
            boolean bResultKekB = this.createFile(strKek_b, KEK_B_DIR);  //   KEK B 파일 생성
            boolean bResultDek = this.createFile(strEncDek, DEK_DIR);  //   DEK 파일 생성
            if (bResultKekA && bResultKekB && bResultDek) {
                bResult = true;
            }

        } catch (ClassNotFoundException e) {
            System.err.println(e); // 에러 메시지 출력
            ncAuditService.mng_log_insert(
                "SYSTEM",
                "",
                MessageUtil.getMessage("log.passwardkeycreate"), MessageUtil.getMessage("log.passwardkeycreatingfail"), "F", "W"); // 감사로그 저장
        }

        return bResult;
    }

    @SuppressWarnings("unused")
	public String getKek() {
    	String strResult = null;
    	String strKa = this.readFileLine(KEK_A_DIR);
    	String strKb = this.readFileLine(KEK_B_DIR);

    	if (strKa != null /* && strKa.length() == 32 */ && strKb != null /* && strKb.length() == 32 */) {
    		strResult = strKa + strKb;
    	} else {
    		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    		HttpSession session = attr.getRequest().getSession();
    		ncAuditService.mng_log_insert(
    				"SYSTEM",
    				"",
    				MessageUtil.getMessage("log.passcalcu"), MessageUtil.getMessage("log.missingkek"),
    				"F", "W"); // 감사로그 저장
    	}
    	return strResult;
    }

    @SuppressWarnings("unused")
	public String getDek() {
        String strResult = null;
        String strKek = this.getKek();

        String audit_page = MessageUtil.getMessage("log.passcalcu");

		if (strKek != null /* && strKek.length() == 64 */) {
            String strD = this.readFileLine(DEK_DIR);
			if (strD != null /* && strD.length() == 128 */) {
                strResult = nnsp.security.ARIAUtil.ariaDecrypt(strD, strKek, audit_page);
            } else {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession();
                ncAuditService.mng_log_insert(
                    "SYSTEM",
                    "",
                    audit_page, MessageUtil.getMessage("log.missingkek"),
                    "F", "W"); // 감사로그 저장
            }
            try {
                strKek.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return strResult;
    }

    // key파일 저장
    private boolean createFile(String key, String filename) {
        boolean bResult = false;
        try {
            File f1 = new File(filename);
            if (f1.exists()) {
                f1.delete();
            }
            f1.getParentFile().mkdirs();
            f1.createNewFile();

            BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
            out.write(key);
            // out.newLine();
            out.close();

            bResult = true;
        } catch (IOException e) {
            System.err.println(e); // 에러 메시지 출력
        }

        return bResult;
    }

    // key파일 한줄 읽기
    private String readFileLine(String filename) {
        String strResult = null;
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader(filename)
            );
            strResult = reader.readLine();
            reader.close();

        } catch (IOException e) {
            System.err.println(e); // 에러 메시지 출력
        }

        return strResult;
    }

    // 이메일 DEK 생성 및 저장
    @SuppressWarnings({ "unused", "rawtypes" })
	public boolean createMDek() {
        boolean bResult = false;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        SecureRandom sr = NcSecurityUtil.getSecureRandom();
		//   DEK 생성
		byte[] dek = new byte[DEK_LENGTH];
		sr.nextBytes(dek);

        String audit_page = MessageUtil.getMessage("log.passwardkeycreate");

        String strDek = Hex.encodeHexString(dek);

        String strEncDek = null;

        try {
            Class AriaEngine = Class.forName("nnsp.security.ARIAEngine");

            String strKek = this.getKek();
            if (strKek != null && !strKek.equals("")) {
                strEncDek = nnsp.security.ARIAUtil.ariaEncrypt(strDek, strKek, audit_page);
                strKek.close();

                if (strEncDek != null && !strEncDek.equals("")) {
                    strDek.close();
                    if (ncConfigMapper.config_email_dek_update(strEncDek) > 0) {
                        strEncDek.close();
                        bResult = true;
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println(e); // 에러 메시지 출력
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!bResult) {
            ncAuditService.mng_log_insert(
                "SYSTEM",
                "",
                MessageUtil.getMessage("log.passwardkeycreate"), MessageUtil.getMessage("log.passwardkeycreatingfail"), "F", "W"); // 감사로그 저장
        }
        return bResult;
    }

    @SuppressWarnings({ "rawtypes", "unused" })
	public String getEmailDek() {
        String strDek = null;
        String strEDek = ncConfigMapper.config_email_dek_select();

        if (strEDek == null || strEDek.isEmpty() || strEDek.equals("")) {
            //  메일 관리 dek 가 없으면 생성
            if (this.createMDek()) {
                strEDek = ncConfigMapper.config_email_dek_select();
            } else {//   메일관리 dek 생성에 실패하면 null
                return null;
            }
        }

        String strKek = this.getKek();
        if (strKek != null && !strKek.isEmpty()) {
            try {
                Class AriaEngine = Class.forName("nnsp.security.ARIAEngine");

                strDek = nnsp.security.ARIAUtil.ariaDecrypt(strEDek, strKek, MessageUtil.getMessage("log.keydecrypt"));

            } catch (ClassNotFoundException e) {
                System.err.println(e); // 에러 메시지 출력
            }
            try {
                strKek.close();
                strEDek.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return strDek;
    }
    
    public String getConfigePw() {
		return ncConfigMapper.getConfigePw();
	}
    
 // 제품 초기 정보 설정 여부 조회
 	public boolean getConfigProd(){
 		return ncConfigMapper.getConfigProd();
 	}

 	public int ncProduct_update(NcProduct ncProduct) {
 		return ncConfigMapper.ncProduct_update(ncProduct);
 	}

 	public NcProduct getNcProduct() {
 		return ncConfigMapper.getNcProduct();
 	}
}