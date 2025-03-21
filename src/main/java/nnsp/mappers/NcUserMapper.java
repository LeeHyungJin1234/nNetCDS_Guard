package nnsp.mappers;

import java.util.ArrayList;
import nnsp.vo.NcUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcUserMapper {
	// 사용자 계정 ID로 조회
	final String SELECT_BY_ID = "SELECT NSU_SEQ, NSU_ID, NSU_NAME, NSU_PW, NSU_PREV_PW, NSU_EMAIL, " +
            "NSU_DIVISION, NSU_POSITION, NSU_TEL, NSU_MOBILE, NSU_DESC, NSU_USE_YN, " +
            "DATE_FORMAT(NSU_REG_DATE,'%Y-%m-%d %H:%i:%s') NSU_REG_DATE, " +
            "DATE_FORMAT(NSU_LAST_LOGIN,'%Y-%m-%d %H:%i:%s') NSU_LAST_LOGIN, " +
            "NSU_SECU_LEVEL, NSU_LOCK_YN, NSU_LOCK_DATE, " +
            "IFNULL(NSU_LOGIN_FAILCNT,0) NSU_LOGIN_FAILCNT, NSU_SALT, NSU_PREV_SALT " +
			"FROM NNC_SYSTEM_USER WHERE binary(NSU_ID)=#{id}"; 
	
	// 사용자 계정 seq로 조회
	final String SELECT_BY_SEQ = "SELECT NSU_SEQ, NSU_ID, NSU_NAME, NSU_PW, NSU_PREV_PW, NSU_EMAIL, " +
            "NSU_DIVISION, NSU_POSITION, NSU_TEL, NSU_MOBILE, NSU_DESC, NSU_USE_YN, " +
            "DATE_FORMAT(NSU_REG_DATE,'%Y-%m-%d %H:%i:%s') NSU_REG_DATE, " +
            "DATE_FORMAT(NSU_LAST_LOGIN,'%Y-%m-%d %H:%i:%s') NSU_LAST_LOGIN, " +
            "NSU_SECU_LEVEL, NSU_LOCK_YN, NSU_LOCK_DATE, " +
            "IFNULL(NSU_LOGIN_FAILCNT,0) NSU_LOGIN_FAILCNT, NSU_SALT, NSU_PREV_SALT " +
			"FROM NNC_SYSTEM_USER WHERE NSU_SEQ=#{nsu_seq}"; 
	
	// 사용 가능한 계정 정보 조회
	final String SELECT_SYSTEM_USER = "SELECT NSU_SEQ, NSU_ID, NSU_NAME, NSU_PW, NSU_PREV_PW, NSU_EMAIL, " +
            "NSU_DIVISION, NSU_POSITION, NSU_TEL, NSU_MOBILE, NSU_DESC, NSU_USE_YN, " +
            "DATE_FORMAT(NSU_REG_DATE,'%Y-%m-%d %H:%i:%s') NSU_REG_DATE, " +
            "DATE_FORMAT(NSU_LAST_LOGIN,'%Y-%m-%d %H:%i:%s') NSU_LAST_LOGIN, " +
            "NSU_SECU_LEVEL, NSU_LOCK_YN, NSU_LOCK_DATE, " +
            "IFNULL(NSU_LOGIN_FAILCNT,0) NSU_LOGIN_FAILCNT, NSU_SALT, NSU_PREV_SALT " +
			"FROM NNC_SYSTEM_USER "; 
	
	// 유저별 계정 보안 등급 조회
	final String SELECT_SECU_LEVEL = "SELECT NSU_NAME, NSU_ID, NSU_SECU_LEVEL FROM NNC_SYSTEM_USER "; 
	
	// 사용자 계정 정보 추가
	final String INSERT_SYSTEM_USER = "INSERT INTO NNC_SYSTEM_USER "
			+ " (NSU_ID, NSU_PW, NSU_NAME, NSU_DIVISION, NSU_POSITION, NSU_TEL, NSU_MOBILE, NSU_REG_DATE, NSU_USE_YN, NSU_EMAIL,NSU_SECU_LEVEL, NSU_DESC) "
			+ " VALUES "
			+ " (#{nsu_id},#{nsu_pw}, #{nsu_name}, #{nsu_division}, #{nsu_position}, #{nsu_tel}, #{nsu_mobile}, now(), 0,  #{nsu_email}, #{nsu_secu_level}, #{nsu_desc})";
		
	// 최근 로그인 시간 변경
	final String LAST_LOGIN_UPDATE = "UPDATE NNC_SYSTEM_USER SET NSU_LAST_LOGIN=now() WHERE NSU_SEQ=#{nsu_seq}";
	
	// 패스워드 변경
	final String PASSWORD_UPDATE = "UPDATE NNC_SYSTEM_USER SET "
			+ " NSU_PREV_PW=NSU_PW, NSU_PW=#{password}, "
            + " NSU_REG_DATE=now(), "
            + " NSU_PREV_SALT=NSU_SALT, "
            + " NSU_SALT=#{salt} "
			+ " WHERE NSU_SEQ=#{seq}";
	
	// 최초 로그인 시 변경
	final String FIRSTLOGIN_UPDATE = "UPDATE NNC_SYSTEM_USER SET "
            + " NSU_ID=#{id}, NSU_PREV_PW=NSU_PW, NSU_PW=#{password}, "
            + " NSU_REG_DATE=now(), NSU_PREV_SALT=NSU_SALT, NSU_SALT=#{salt} "
			+ " WHERE NSU_SEQ=#{seq}";
	
	// 계정 잠금 설정
	final String LOCK_STATUS_UPDATE = 
			"UPDATE NNC_SYSTEM_USER "
			+ "SET NSU_LOCK_YN=#{lock_yn}, "
			+ "NSU_LOCK_DATE=date_add(now(), INTERVAL #{lock_date} MINUTE), "
			+ "NSU_LOGIN_FAILCNT=#{login_failcnt} "
			+ "WHERE NSU_SEQ=#{seq}";
	
	// 계정 미사용 설정
	//final String YN_STATUS_UPDATE = "UPDATE NNC_SYSTEM_USER SET NSU_USE_YN=0 WHERE NSU_SEQ=#{nsu_seq}";
	final String YN_STATUS_UPDATE = "DELETE FROM NNC_SYSTEM_USER WHERE NSU_SEQ=#{nsu_seq}";
	
	// 관리자 관리 정보 업데이트
	final String USER_INFO_UPDATE = "UPDATE NNC_SYSTEM_USER SET NSU_NAME=#{nsu_name}, NSU_DIVISION=#{nsu_division}, NSU_POSITION=#{nsu_position}, NSU_TEL=#{nsu_tel}, NSU_MOBILE=#{nsu_mobile}, NSU_EMAIL=#{nsu_email}, NSU_DESC=#{nsu_desc} " +
			"WHERE NSU_SEQ=#{nsu_seq}";
	
	// 마지막 사용자 번호 구하기
	final String LAST_USER_SEQ = "SELECT MAX(NSU_SEQ) NSU_SEQ FROM NNC_SYSTEM_USER";
		
	// 접속 허용 클라이언트 IP 조회(전체)
	final String SELECT_ACCESS_IP = "SELECT NAI_IP, NAI_NAME, NAI_USE_YN, NAI_DIV FROM NNC_ACCESS_IP WHERE NAI_DEL_YN=0 OR NAI_DEL_YN=2 ";
	
	// 접속 허용 클라이언트 IP 조회(사용 중인 IP)
	final String SELECT_USE_ACCESS_IP = "SELECT NAI_IP FROM NNC_ACCESS_IP WHERE NAI_USE_YN=1 AND (NAI_DEL_YN=0 OR NAI_DEL_YN=2)";
	
	// 접속 허용 송수신 망 조회
	final String SELECT_USE_DIV = "SELECT NAI_DIV FROM NNC_ACCESS_IP WHERE NAI_USE_YN=1";
		
	// 접속 허용 IP 추가
	final String INSERT_ACCESS_IP = "INSERT INTO NNC_ACCESS_IP (NAI_IP, NAI_NAME, NAI_USE_YN) VALUES (#{nai_ip}, #{nai_name}, 0)";
	
	// 접속 허용 IP 변경(사용상태)
	final String ACCESS_IP_UPDATE = "UPDATE NNC_ACCESS_IP SET NAI_USE_YN=#{use_yn} , NAI_DEL_YN=2 WHERE NAI_IP=#{nai_ip}";
	
	// 접속 허용 IP 조회
	final String SELECT_BY_ACCESS_IP = "SELECT NAI_IP, NAI_NAME, NAI_USE_YN FROM NNC_ACCESS_IP WHERE NAI_IP=#{nai_ip}";
	
	// 접속 허용 IP 변경
	final String IP_INFO_UPDATE = "UPDATE NNC_ACCESS_IP SET NAI_IP=#{nai_ip}, NAI_NAME=#{nai_name}, NAI_DEL_YN=2 WHERE NAI_IP=#{org_nai_ip}";
	
	final String SELECT_NOTUSE_CNT = "SELECT COUNT(*) AS CNT FROM NNC_ACCESS_IP WHERE NAI_IP != #{nai_ip} AND NAI_USE_YN=1";
	
	final String DELETE_ACCESS_IP = "DELETE FROM NNC_ACCESS_IP WHERE NAI_IP = #{nai_ip}";
		
	// 관리자등록 패스워드
	final String PASSWORD_UPDATE2 =
         "UPDATE NNT_SYSTEM_USER " +
             "   SET NSU_PW=#{password}, " +
             "       NSU_SALT=#{salt} " +
             " WHERE NSU_SEQ=#{seq}";

	// 계정 사용여부 설정
 	final String UPDATE_USER_USEYN =
 		"UPDATE NNC_SYSTEM_USER " +
 		"   SET NSU_USE_YN=#{nsu_use_yn} " +
 		" WHERE NSU_SEQ=#{nsu_seq}";
	
	@Select(SELECT_BY_ID)
	@Results(value = {
	        @Result(property = "nsu_seq", column = "NSU_SEQ"),
	        @Result(property = "nsu_id", column = "NSU_ID"),
	        @Result(property = "nsu_name", column = "NSU_NAME"),
	        @Result(property = "nsu_pw", column = "NSU_PW"),
	        @Result(property = "nsu_prev_pw", column = "NSU_PREV_PW"),
	        @Result(property = "nsu_use_yn", column = "NSU_USE_YN"),
	        @Result(property = "nsu_reg_date", column = "NSU_REG_DATE"),
	        @Result(property = "nsu_last_login", column = "NSU_LAST_LOGIN"),
	        @Result(property = "nsu_secu_level", column = "NSU_SECU_LEVEL"),
	        @Result(property = "nsu_lock_yn", column = "NSU_LOCK_YN"),
	        @Result(property = "nsu_lock_date", column = "NSU_LOCK_DATE"),
	        @Result(property = "nsu_login_failcnt", column = "NSU_LOGIN_FAILCNT"),
	        @Result(property = "nsu_email", column = "NSU_EMAIL"),
	        @Result(property = "nsu_tel", column = "NSU_TEL"),
	        @Result(property = "nsu_mobile", column = "NSU_MOBILE"),
	        @Result(property = "nsu_division", column = "NSU_DIVISION"),
	        @Result(property = "nsu_position", column = "NSU_POSITION"),
	        @Result(property = "nsu_desc", column = "NSU_DESC"),
	        @Result(property = "nsu_salt", column = "NSU_SALT"),
	        @Result(property = "nsu_prev_salt", column = "NSU_PREV_SALT")
    })
	NcUser getSelectById(@Param("id") String id);
	
	@Select(SELECT_BY_SEQ)
	@Results(value = {
	        @Result(property = "nsu_seq", column = "NSU_SEQ"),
	        @Result(property = "nsu_id", column = "NSU_ID"),
	        @Result(property = "nsu_name", column = "NSU_NAME"),
	        @Result(property = "nsu_pw", column = "NSU_PW"),
	        @Result(property = "nsu_use_yn", column = "NSU_USE_YN"),
	        @Result(property = "nsu_reg_date", column = "NSU_REG_DATE"),
	        @Result(property = "nsu_last_login", column = "NSU_LAST_LOGIN"),
	        @Result(property = "nsu_secu_level", column = "NSU_SECU_LEVEL"),
	        @Result(property = "nsu_lock_yn", column = "NSU_LOCK_YN"),
	        @Result(property = "nsu_lock_date", column = "NSU_LOCK_DATE"),
	        @Result(property = "nsu_login_failcnt", column = "NSU_LOGIN_FAILCNT"),
	        @Result(property = "nsu_email", column = "NSU_EMAIL"),
	        @Result(property = "nsu_tel", column = "NSU_TEL"),
	        @Result(property = "nsu_mobile", column = "NSU_MOBILE"),
	        @Result(property = "nsu_division", column = "NSU_DIVISION"),
	        @Result(property = "nsu_position", column = "NSU_POSITION"),
	        @Result(property = "nsu_desc", column = "NSU_DESC")
    })
	NcUser getSelectBySeq(@Param("nsu_seq") int nsu_seq);
	
	@Select(SELECT_SECU_LEVEL)
	@Results(value = {
		@Result(property="nsu_name", column="NSU_NAME"),
		@Result(property="nsu_id", column="NSU_ID"),
        @Result(property="nsu_secu_level", column="NSU_SECU_LEVEL")
    })
	ArrayList<NcUser> getNsuSecuLevel();
	
	@Update(LAST_LOGIN_UPDATE)
	int last_login_update(@Param("nsu_seq") int nsu_seq);
	
	@Update(PASSWORD_UPDATE)
	int password_update(@Param("seq") int seq, @Param("password") String password,  @Param("salt") String salt);
	
	@Update(FIRSTLOGIN_UPDATE)
	int firstlogin_update(@Param("seq") int seq, @Param("password") String password, @Param("salt") String salt, @Param("id") String id);
	
	@Select(SELECT_ACCESS_IP)
	@Results(value = {
        @Result(property="nai_ip", column="NAI_IP"),
        @Result(property="nai_name", column="NAI_NAME"),
        @Result(property="nai_use_yn", column="NAI_USE_YN"),
        
    })
	ArrayList<NcUser> getAccessIp();
	
	@Select(SELECT_USE_ACCESS_IP)
	@Results(value = {
        @Result(property="nai_ip", column="NAI_IP")
    })
	ArrayList<NcUser> getUseAccessIp();
	
	@Select(SELECT_SYSTEM_USER)
	@Results(value = {
			@Result(property="nsu_seq", column="NSU_SEQ"),
	        @Result(property="nsu_id", column="NSU_ID"),
	        @Result(property="nsu_name", column="NSU_NAME"),
	        @Result(property="nsu_pw", column="NSU_PW"),
	        @Result(property="nsu_use_yn", column="NSU_USE_YN"),
	        @Result(property="nsu_reg_date", column="NSU_REG_DATE"),
	        @Result(property="nsu_last_login", column="NSU_LAST_LOGIN"),
	        @Result(property="nsu_secu_level", column="NSU_SECU_LEVEL"),
	        @Result(property="nsu_lock_yn", column="NSU_LOCK_YN"),
	        @Result(property="nsu_lock_date", column="NSU_LOCK_DATE"),
	        @Result(property="nsu_login_failcnt", column="NSU_LOGIN_FAILCNT"),
	        @Result(property="nsu_email", column="NSU_EMAIL"),
	        @Result(property="nsu_tel", column="NSU_TEL"),
	        @Result(property="nsu_mobile", column="NSU_MOBILE"),
	        @Result(property="nsu_division", column="NSU_DIVISION"),
	        @Result(property="nsu_position", column="NSU_POSITION"),
	        @Result(property="nsu_desc", column="NSU_DESC")			
	})
	ArrayList<NcUser> getSystemUser();
	
	@Update(LOCK_STATUS_UPDATE)
	int lock_status_update(@Param("lock_yn") String lock_yn, @Param("lock_date") int lock_date, @Param("login_failcnt") int login_failcnt, @Param("seq") int seq);
	
	@Update(USER_INFO_UPDATE)
	int update(NcUser ncUser);
	
	@Update(YN_STATUS_UPDATE)
	int yn_status_update(@Param("nsu_seq") int nsu_seq);
	
	@Insert(INSERT_ACCESS_IP)
	int insert_access_ip(@Param("nai_ip") String nai_ip, @Param("nai_name") String nai_name);
	
	@Insert(INSERT_SYSTEM_USER)
	int insert_system_user(NcUser ncuser);
	
	@Update(ACCESS_IP_UPDATE)
	int edit_access_ip(@Param("nai_ip") String nai_ip, @Param("use_yn") int use_yn);
	
	@Select(SELECT_BY_ACCESS_IP)
	@Results(value = {
        @Result(property="nai_ip", column="NAI_IP"),
        @Result(property="nai_name", column="NAI_NAME"),
        @Result(property="nai_use_yn", column="NAI_USE_YN")
    })
	NcUser getByAccessIp(@Param("nai_ip") String nai_ip);
	
	@Update(IP_INFO_UPDATE)
	int edit_ip_info(@Param("nai_ip") String nai_ip, @Param("nai_name") String nai_name, @Param("org_nai_ip") String org_nai_ip);
	
	@Select(LAST_USER_SEQ)
	int last_user_seq();
	
	@Select(SELECT_NOTUSE_CNT)
	int select_notuse_cnt(@Param("nai_ip") String nai_ip);
	
	@Update(DELETE_ACCESS_IP)
	int delete_access_ip(@Param("nai_ip") String nai_ip);
	
	@Update(PASSWORD_UPDATE2)
    int password_update2(@Param("seq") int seq, @Param("password") String password, @Param("salt") String salt);

    @Update(UPDATE_USER_USEYN)
	int updateUserUseYN(@Param("nsu_seq") int nsu_seq, @Param("nsu_use_yn") int nsu_use_yn);
}