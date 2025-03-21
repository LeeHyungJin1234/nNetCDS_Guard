package nnsp.services;

import java.util.ArrayList;

import nnsp.vo.NcUser;
import nnsp.mappers.NcUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcUserService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcUserDetailsService.class);

	@Autowired
	private NcUserMapper ncUserMapper;

	// 현재 접속된 유저의 접속정보 가져오기
	public NcUser getUserbyId(String id) {
		return ncUserMapper.getSelectById(id);
	}
	
	// 유저 정보 가져오기
	public NcUser getUserbySeq(int nsu_seq) {
		return ncUserMapper.getSelectBySeq(nsu_seq);
	}

	// 패스워드 수정
	public int password_update(int seq, String pw, String salt) {
		//String encodePassword = pw; //passwordEncoder.encodePassword(pw, salt);
		return ncUserMapper.password_update(seq, pw , salt);
	}

	// 최초 로그인 시 수정
	public int firstlogin_update(int seq, String pw, String salt, String id) {
		//String encodePassword = pw; //passwordEncoder.encodePassword(pw, salt);
		return ncUserMapper.firstlogin_update(seq, pw, salt, id);
	}
		
	// 접속 허용 ip 목록 가져오기(사용 중인 IP)
	public ArrayList<NcUser> getUseAccessIp() {
		return ncUserMapper.getUseAccessIp();
	}

	// 마지막 로그인 일시 변경
	public int last_login_update(int nsu_seq) {
		return ncUserMapper.last_login_update(nsu_seq);
	}

	// 계정 잠금 설정
	public int lock_status_update(String lock_yn, int lock_date, int login_failcnt, int seq) {
		return ncUserMapper.lock_status_update(lock_yn, lock_date, login_failcnt, seq);
	}

	// 관리자 수정 정보 업데이트
	public int update(NcUser ncUser) {
		return ncUserMapper.update(ncUser);
	}

	// 사용가능한 유저정보 목록 가져오기
	public ArrayList<NcUser> getSystemUser() {
		return ncUserMapper.getSystemUser();
	}

	// 계정사용 여부 변경 - 미사용
	public int yn_status_update(int nsu_seq) {
		return ncUserMapper.yn_status_update(nsu_seq);
	}

	// 접속 허용 ip 목록 가져오기(전체)
	public ArrayList<NcUser> getAccessIp() {
		return ncUserMapper.getAccessIp();
	}
		
	// 접속 허용 IP 추가
	public int insert_access_ip(String nai_ip, String nai_name) {
		return ncUserMapper.insert_access_ip(nai_ip, nai_name);
	}

	// 접속 허용 IP 삭제
	public int edit_access_ip(String nai_ip, int use_yn) {
		return ncUserMapper.edit_access_ip(nai_ip, use_yn);
	}
	
	// 접속 허용 계정 추가
	public int insert_system_user(NcUser ncuser){
		return ncUserMapper.insert_system_user(ncuser);
	}
	
	// 접속 허용 IP 조회
	public NcUser getByAccessIp(String nai_ip) {
		return ncUserMapper.getByAccessIp(nai_ip);
	}
	
	// 접속 허용 IP 정보 변경
	public int edit_ip_info(String nai_ip, String nai_name,  String org_nai_ip) {
		return ncUserMapper.edit_ip_info(nai_ip, nai_name, org_nai_ip);
	}
	
	// 보안 등급 조회
	public ArrayList<NcUser> getNsuSecuLevel(){
		return ncUserMapper.getNsuSecuLevel();
	}
	
	public int select_notuse_cnt(String nai_ip) {
		return ncUserMapper.select_notuse_cnt(nai_ip);
	}
	
	// 접속 허용 IP 삭제
	public int delete_access_ip(String nai_ip) {
		return ncUserMapper.delete_access_ip(nai_ip);
	}
	
	// 계정사용 여부 변경
	public int updateUserUseYN(int nsu_seq, int nsu_use_yn) {
		return ncUserMapper.updateUserUseYN(nsu_seq, nsu_use_yn);
	}
}