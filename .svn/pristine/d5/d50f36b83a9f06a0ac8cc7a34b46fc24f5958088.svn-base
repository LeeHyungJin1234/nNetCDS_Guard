package nnsp.services;

import nnsp.vo.NcClientUser;
import nnsp.mappers.NcDoorayUserMapper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcDoorayUserService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcUserDetailsService.class);

	@Autowired
	private NcDoorayUserMapper ncDoorayUserMapper;


	public NcClientUser getClientUserbyId(String id) {
		return ncDoorayUserMapper.getClientUserbyId(id);
	}
	public NcClientUser getClientUserbyIdx(int usr_idx) {
		return ncDoorayUserMapper.getClientUserbyIdx(usr_idx);
	}
	public int insert_client_user(NcClientUser ncClientUser) {
		return ncDoorayUserMapper.insert_client_user(ncClientUser);
	}
	public int update_client_user(int usr_idx, String usr_srcip, String usr_url, String usr_dstip) {
		return ncDoorayUserMapper.update_client_user(usr_idx, usr_srcip, usr_url, usr_dstip);
	}
	public int password_update(int usr_idx, String password, String salt) {
		return ncDoorayUserMapper.password_update(usr_idx, password, salt);
	}
	
	public ArrayList<NcClientUser> getClientUser(){
		return ncDoorayUserMapper.getClientUser();
	}

	// 계정사용 여부 변경
	public int updateUsrStatus(int usr_idx, int usr_status) {
		return ncDoorayUserMapper.updateUsrStatus(usr_idx, usr_status);
	}
	// LastTime 갱신
	public int updateUsrLastTime(int usr_idx) {
		return ncDoorayUserMapper.updateUsrLastTime(usr_idx);
	}
	// 사용자 삭제
	public int deleteUserIdx(int usr_idx) {
		return ncDoorayUserMapper.deleteUserIdx(usr_idx);
	}
	
}