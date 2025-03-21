package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcAlarm;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcAlarmMapper {
	
	// 발생할 알람 타입
	final String SELECT_alarm_TYPE = "SELECT NOA_TYPE FROM NNC_OCCUR_alarm WHERE NOA_OCCUR_YN=1 GROUP BY NOA_TYPE";
	// 무결성 알람 상세
	final String SELECT_INTE_DETAIL = "SELECT NOA_DETAIL FROM NNC_OCCUR_alarm WHERE NOA_OCCUR_YN=1 AND NOA_TYPE = 'I' GROUP BY NOA_DETAIL";
	// 로그 알람 상세
	final String SELECT_LOG_DETAIL = "SELECT NOA_DETAIL, date_format(NOA_REG_DATE,'%Y-%m-%d %H:%i:%s') NOA_REG_DATE " +
			"FROM NNC_OCCUR_alarm WHERE NOA_OCCUR_YN=1 AND NOA_TYPE = 'L' " +
			"GROUP BY NOA_DETAIL, NOA_REG_DATE ORDER BY NOA_REG_DATE DESC";
	// 알람 삭제
	final String DELETE_alarm = "UPDATE NNC_OCCUR_alarm SET NOA_OCCUR_YN=0 WHERE NOA_TYPE=#{noa_type} AND NOA_OCCUR_YN=1";
	
	// 로그인 실패 알람
	final String INSERT_LOGIN ="INSERT INTO NNC_OCCUR_alarm (NOA_TYPE, NOA_DETAIL , NOA_OCCUR_YN, NOA_REG_DATE) VALUES ( #{noa_type}, #{noa_detail}, 1 , now() ) ";
	
	final String SELECT_LOGIN_DETAIL = "SELECT NOA_DETAIL, date_format(NOA_REG_DATE,'%Y-%m-%d %H:%i:%s')NOA_REG_DATE FROM NNC_OCCUR_alarm WHERE NOA_OCCUR_YN=1 AND NOA_TYPE = 'R' GROUP BY NOA_DETAIL";
	
	final String INSERT_EMAIL_NOTIFICATION =
	        "INSERT INTO NNC_NOTI_QUEUE " +
	            "(NNQ_DIV, NNQ_ID, NNQ_CREATE_DATE, NNQ_MESSAGE, NNQ_FLAG) " +
	            "VALUES (2, #{NNQ_ID}, now(), #{NNQ_MESSAGE}, 8 ) ";
	          
	
	@Select(SELECT_alarm_TYPE)
	ArrayList<NcAlarm> getAlarmCnt();

	@Select(SELECT_INTE_DETAIL)
	ArrayList<NcAlarm> getInteDetail();
	
	@Select(SELECT_LOG_DETAIL)
	ArrayList<NcAlarm> getLogDetail();
	
	@Update(DELETE_alarm)
	int alarm_delete(String noa_type);
	
	@Insert(INSERT_LOGIN)
	int alarm_login(@Param("noa_type") String noa_type, @Param("noa_detail") String noa_detail );

	@Select(SELECT_LOGIN_DETAIL)
	ArrayList<NcAlarm> getLoginDetail();
	
	@Insert(INSERT_EMAIL_NOTIFICATION)
    int insertEmailNotification(
        @Param("NNQ_ID") String nnqId, @Param("NNQ_MESSAGE") String message);
 
	//Object alarm_login(String type, String detail, int i);
}