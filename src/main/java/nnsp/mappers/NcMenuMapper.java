package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcMenu;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface NcMenuMapper {
	// TOP 메뉴 조회
	final String SELECT_TOP_MENU = "SELECT NTM_ID, NTM_NAME FROM NNC_TOP_MENU ORDER BY NTM_SEQ"; 
	// TOP 메뉴 조회
	final String SELECT_TOP_ENMENU = "SELECT NTM_ID, NTM_ENAME AS NTM_NAME FROM NNC_TOP_MENU ORDER BY NTM_SEQ"; 
	
	// SUB 메뉴 조회
	final String SELECT_SUB_MENU = "SELECT NSM_ID, NSM_NAME, NSM_CONTROL, NSM_DEPTH " +
			"FROM NNC_SUB_MENU A " +
			"WHERE NSM_PR_ID=#{nsm_pr_id} ORDER BY NSM_SEQ"; 
	// SUB 메뉴 조회
	final String SELECT_SUB_ENMENU = "SELECT NSM_ID, NSM_ENAME AS NSM_NAME, NSM_CONTROL, NSM_DEPTH " +
			"FROM NNC_SUB_MENU A " +
			"WHERE NSM_PR_ID=#{nsm_pr_id} ORDER BY NSM_SEQ"; 
	
	// 현재 메뉴 조회
	final String SELECT_NOW_MENU = "SELECT NTM.NTM_ID, NTM_NAME, NSM_NAME, NSM_CONTROL, NSM_DEPTH, (SELECT COUNT(NSM_ID) FROM NNC_SUB_MENU WHERE NSM_PR_ID = NSM.NSM_ID) PR_CNT " +
			"FROM NNC_TOP_MENU NTM, NNC_SUB_MENU NSM, (SELECT NTM_ID FROM NNC_SUB_MENU WHERE NSM_CONTROL=#{now_page}) AA " +
			"WHERE NTM.NTM_ID = NSM.NTM_ID " +
			"AND NTM.NTM_ID = AA.NTM_ID " +
			"ORDER BY NSM_SEQ";
	
	// 현재 메뉴 타이틀
	final String SELECT_NOW_TITLE = "SELECT IF((NSM_PR_ID=400005 || NSM_PR_ID=400018), (SELECT CONCAT(NSM_NAME,' > ',A.NSM_NAME) FROM NNC_SUB_MENU WHERE NSM_ID=A.NSM_PR_ID), NSM_NAME) TITLE " +
			"FROM NNC_SUB_MENU A " +
			"WHERE NSM_CONTROL=#{now_page}";
	// 현재 영문 메뉴 타이틀
	final String SELECT_NOW_ETITLE = "SELECT IF((NSM_PR_ID=400005 || NSM_PR_ID=400018), (SELECT CONCAT(NSM_ENAME,' > ',A.NSM_ENAME) FROM NNC_SUB_MENU WHERE NSM_ID=A.NSM_PR_ID), NSM_ENAME) TITLE " +
			"FROM NNC_SUB_MENU A " +
			"WHERE NSM_CONTROL=#{now_page}";
	
	// 현재 메뉴 설명
	final String SELECT_MENU_DESC = "SELECT NSM_DESC FROM NNC_SUB_MENU WHERE NSM_CONTROL=#{now_page}";
		
	// SUB 메뉴 정보
	final String SUB_MENU_INFO = "SELECT NSM_ID, NSM_NAME, NSM_CONTROL, NSM_DEPTH FROM NNC_SUB_MENU WHERE NSM_ID = #{nsm_id}"; 
	
	// 현재 탑메뉴 정보
	final String SELECT_NOW_TOPMENU = "SELECT distinct NTM_ID FROM NNC_SUB_MENU WHERE NSM_CONTROL=#{now_page}";
	
	// 탬메뉴 상위 아이디 조회
	final String SELECT_PR_ID = "SELECT NSM_PR_ID FROM NNC_SUB_MENU WHERE NSM_CONTROL = #{now_page} AND NSM_DEPTH=2";
	
	// 현재 메뉴 정보
	final String SELECT_NOW_MENUINFO ="SELECT NSM_ID, NSM_PR_ID FROM NNC_SUB_MENU WHERE NSM_CONTROL=#{now_page} LIMIT 1";
	
	@Select(SELECT_TOP_MENU)
	@Results(value = {
        @Result(property="ntm_id", column="NTM_ID"),
        @Result(property="ntm_name", column="NTM_NAME")
    })
	ArrayList<NcMenu> getTopMenu();
	@Select(SELECT_TOP_ENMENU)
	@Results(value = {
        @Result(property="ntm_id", column="NTM_ID"),
        @Result(property="ntm_name", column="NTM_NAME")
    })
	ArrayList<NcMenu> getTopEnMenu();
	
	@Select(SELECT_SUB_MENU)
	@Results(value = {
		@Result(property="nsm_id", column="NSM_ID"),
        @Result(property="nsm_name", column="NSM_NAME"),
        @Result(property="nsm_control", column="NSM_CONTROL"),
        @Result(property="nsm_depth", column="NSM_DEPTH")
    })
	ArrayList<NcMenu> getSubMenu(@Param("nsm_pr_id") int nsm_pr_id);
	@Select(SELECT_SUB_ENMENU)
	@Results(value = {
		@Result(property="nsm_id", column="NSM_ID"),
        @Result(property="nsm_name", column="NSM_NAME"),
        @Result(property="nsm_control", column="NSM_CONTROL"),
        @Result(property="nsm_depth", column="NSM_DEPTH")
    })
	ArrayList<NcMenu> getSubEnMenu(@Param("nsm_pr_id") int nsm_pr_id);
	
	@Select(SELECT_NOW_MENU)
	@Results(value = {
		@Result(property="ntm_id", column="NTM_ID"),
		@Result(property="ntm_name", column="NTM_NAME"),
        @Result(property="nsm_name", column="NSM_NAME"),
        @Result(property="nsm_control", column="NSM_CONTROL"),
        @Result(property="nsm_depth", column="NSM_DEPTH"),
        @Result(property="pr_cnt", column="PR_CNT")
    })
	ArrayList<NcMenu> getNowMenu(@Param("now_page") String now_page);
	
	@Select(SELECT_NOW_TITLE)
	String getNowTitle(@Param("now_page") String now_page);
	@Select(SELECT_NOW_ETITLE)
	String getNowETitle(@Param("now_page") String now_page);
	@Select(SELECT_MENU_DESC)
	String getMenuDesc(@Param("now_page") String now_page);
	@Select(SELECT_NOW_TOPMENU)
	int getNowTopMenu(@Param("now_page") String now_page);
	
	@Select(SUB_MENU_INFO)
	@Results(value = {
		@Result(property="nsm_id", column="NSM_ID"),
        @Result(property="nsm_name", column="NSM_NAME"),
        @Result(property="nsm_control", column="NSM_CONTROL"),
        @Result(property="nsm_depth", column="NSM_DEPTH")
    })
	NcMenu getSubMenuInfo(@Param("nsm_id") int nsm_id);
	
	@Select(SELECT_PR_ID)
	int getPrId(@Param("now_page") String now_page);
	
	@Select(SELECT_NOW_MENUINFO)
	@Results(value = {
        @Result(property="nsm_id", column="NSM_ID"),
        @Result(property="nsm_pr_id", column="NSM_PR_ID")
    })
	NcMenu getNowMenuInfo(@Param("now_page") String now_page);
}