package nnsp.mappers;

import nnsp.vo.NcClientUser;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcDoorayUserMapper {
	
	final String SELECT_BY_CLINET_ID = "SELECT USR_IDX, USR_ACCOUNT, USR_PWD, USR_PREV_PWD, USR_SALT, USR_PREV_SALT, USR_STATUS, USR_SRCIP, " +
			" USR_URL, USR_DSTIP, " +
			" DATE_FORMAT(USR_REGDATE,'%Y-%m-%d %H:%i:%s') USR_REGDATE, DATE_FORMAT(USR_LASTTIME,'%Y-%m-%d %H:%i:%s') USR_LASTTIME " +
			" FROM NNC_CLIENTUSER WHERE binary(USR_ACCOUNT)=#{id} ";
	
	@Select(SELECT_BY_CLINET_ID)
	@Results(value = {
        @Result(property="usr_idx", column="USR_IDX"),
        @Result(property="usr_account", column="USR_ACCOUNT"),
        @Result(property="usr_pwd", column="USR_PWD"),
        @Result(property="usr_prev_pwd", column="USR_PREV_PWD"),
        @Result(property="usr_salt", column="USR_SALT"),
        @Result(property="usr_prev_salt", column="USR_PREV_SALT"),
        @Result(property="usr_status", column="USR_STATUS"),
        @Result(property="usr_regdate", column="USR_REGDATE"),
        @Result(property="usr_lasttime", column="USR_LASTTIME"),
        @Result(property="usr_srcip", column="USR_SRCIP"),
        @Result(property="usr_url", column="USR_URL"),
        @Result(property="usr_dstip", column="USR_DSTIP")
    })
	NcClientUser getClientUserbyId(@Param("id") String id);
	

	final String SELECT_BY_CLINET_IDX = "SELECT USR_IDX, USR_ACCOUNT, USR_PWD, USR_PREV_PWD, USR_SALT, USR_PREV_SALT, USR_STATUS, USR_SRCIP, " +
			" USR_URL, USR_DSTIP, " +
			" DATE_FORMAT(USR_REGDATE,'%Y-%m-%d %H:%i:%s') USR_REGDATE, DATE_FORMAT(USR_LASTTIME,'%Y-%m-%d %H:%i:%s') USR_LASTTIME " +
			" FROM NNC_CLIENTUSER WHERE USR_IDX=#{usr_idx} ";
	
	@Select(SELECT_BY_CLINET_IDX)
	@Results(value = {
        @Result(property="usr_idx", column="USR_IDX"),
        @Result(property="usr_account", column="USR_ACCOUNT"),
        @Result(property="usr_pwd", column="USR_PWD"),
        @Result(property="usr_prev_pwd", column="USR_PREV_PWD"),
        @Result(property="usr_salt", column="USR_SALT"),
        @Result(property="usr_prev_salt", column="USR_PREV_SALT"),
        @Result(property="usr_status", column="USR_STATUS"),
        @Result(property="usr_regdate", column="USR_REGDATE"),
        @Result(property="usr_lasttime", column="USR_LASTTIME"),
        @Result(property="usr_srcip", column="USR_SRCIP"),
        @Result(property="usr_url", column="USR_URL"),
        @Result(property="usr_dstip", column="USR_DSTIP")
    })
	NcClientUser getClientUserbyIdx(@Param("usr_idx") int usr_idx);
	
	// 사용자 계정 정보 추가
	final String INSERT_CLIENT_USER = "INSERT INTO NNC_CLIENTUSER "
			+ " (USR_ACCOUNT, USR_STATUS, USR_SRCIP, USR_URL, USR_DSTIP, USR_REGDATE, USR_LASTTIME) "
			+ " VALUES "
			+ " (#{usr_account}, 0, #{usr_srcip}, #{usr_url}, #{usr_dstip}, now(), now())";
	@Insert(INSERT_CLIENT_USER)
	int insert_client_user(NcClientUser ncClientUser);	
	
	// 사용자 계정 정보 추가
	final String UPDATE_CLIENT_USER_IDX = "UPDATE NNC_CLIENTUSER SET "
			+ " USR_SRCIP=#{usr_srcip}, USR_URL=#{usr_url}, USR_DSTIP=#{usr_dstip} "
			+ " WHERE USR_IDX=#{usr_idx} ";
	@Update(UPDATE_CLIENT_USER_IDX)
	int update_client_user(@Param("usr_idx") int usr_idx, @Param("usr_srcip") String usr_srcip, @Param("usr_url") String usr_url, @Param("usr_dstip") String usr_dstip);
	
	// 패스워드 변경
	final String PASSWORD_UPDATE = "UPDATE NNC_CLIENTUSER SET "
			+ " usr_prev_pwd=usr_pwd, usr_pwd=#{password}, "
            + " usr_regdate=now(), "
            + " usr_prev_salt=usr_salt, "
            + " usr_salt=#{salt} "
			+ " WHERE usr_idx=#{usr_idx}";
	@Update(PASSWORD_UPDATE)
	int password_update(@Param("usr_idx") int usr_idx, @Param("password") String password,  @Param("salt") String salt);
	
	// 사용 가능한 계정 정보 조회
	final String SELECT_CLIENT_USER = "SELECT USR_IDX, USR_ACCOUNT, USR_PWD, USR_PREV_PWD, USR_SALT, USR_PREV_SALT, USR_STATUS, USR_SRCIP, " +
			" USR_URL, USR_DSTIP, " +
			" DATE_FORMAT(USR_REGDATE,'%Y-%m-%d %H:%i:%s') USR_REGDATE, DATE_FORMAT(USR_LASTTIME,'%Y-%m-%d %H:%i:%s') USR_LASTTIME " +
			" FROM NNC_CLIENTUSER "; 

	@Select(SELECT_CLIENT_USER)
	@Results(value = {
	        @Result(property="usr_idx", column="USR_IDX"),
	        @Result(property="usr_account", column="USR_ACCOUNT"),
	        @Result(property="usr_pwd", column="USR_PWD"),
	        @Result(property="usr_prev_pwd", column="USR_PREV_PWD"),
	        @Result(property="usr_salt", column="USR_SALT"),
	        @Result(property="usr_prev_salt", column="USR_PREV_SALT"),
	        @Result(property="usr_status", column="USR_STATUS"),
	        @Result(property="usr_regdate", column="USR_REGDATE"),
	        @Result(property="usr_lasttime", column="USR_LASTTIME"),
	        @Result(property="usr_srcip", column="USR_SRCIP"),
	        @Result(property="usr_url", column="USR_URL"),
	        @Result(property="usr_dstip", column="USR_DSTIP")
	})
	ArrayList<NcClientUser> getClientUser();
	
	// 계정 사용여부 설정
 	final String UPDATE_USER_STATUS =
 		"UPDATE NNC_CLIENTUSER " +
 		"   SET USR_STATUS=#{usr_status} " +
 		" WHERE USR_IDX=#{usr_idx}";
 	
    @Update(UPDATE_USER_STATUS)
	int updateUsrStatus(@Param("usr_idx") int usr_idx, @Param("usr_status") int usr_status);
    

	// 로그인 시간 갱신
 	final String UPDATE_USER_LASTTIME =
 		"UPDATE NNC_CLIENTUSER " +
 		"   SET USR_LASTTIME=now() " +
 		" WHERE USR_IDX=#{usr_idx}";
 	
    @Update(UPDATE_USER_LASTTIME)
	int updateUsrLastTime(@Param("usr_idx") int usr_idx);

    // 계정 미사용 설정
 	final String DELETE_USER_IDX = "DELETE FROM NNC_CLIENTUSER WHERE USR_IDX=#{usr_idx}";
    @Delete(DELETE_USER_IDX)
	int deleteUserIdx(@Param("usr_idx") int usr_idx);
}