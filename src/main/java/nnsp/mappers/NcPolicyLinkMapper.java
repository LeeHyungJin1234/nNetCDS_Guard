package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcOpcTag;
import nnsp.vo.NcPolicyLink;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcPolicyLinkMapper {
	
	final String POLICY_TEST_NAME = "SELECT NTS_NAME, NTS_NAME FROM NNC_TYPE_CONTENTS NTC "+
			"LEFT JOIN NNC_TYPE_SERVICE NTS ON (NTS.NSC_SEQ = NTC.NTS_SEQ) "+
			"WHERE NTC_SEQ=#{ntc_seq} AND Ntc.NTS_SEQ=#{nts_seq}";
	
	final String LAST_POLICY_SEQ = "SELECT MAX(NPL_SEQ) NPL_SEQ FROM NNC_POLICY_LINK";
	
	
	final String INSERT_POLICYLINK = "INSERT INTO NNC_POLICY_LINK(NPL_NAME, NTS_SEQ, NTC_SEQ, NPL_SRC_PORT, NCP_SEQ_TX, NCP_SEQ_RX, NPL_USE_YN )" +
									"VALUES(#{npl_name}, #{nts_seq}, #{ntc_seq}, #{npl_src_port}, #{ncp_seq_tx}, #{ncp_seq_rx}, #{npl_use_yn})";

	final String PL_ROUTE_INSERT = "INSERT INTO NNC_POLICY_LINK_ROUTE(NPL_SEQ, NPLR_DIV, NPLR_RANGE, NPLR_IP, NPLR_END_IP, NPLR_DST_PORT)" + 
									"VALUES(#{npl_seq}, 1, #{nplr_range}, #{nplr_ip}, #{nplr_end_ip}, 0)";

	final String PL_ROUTE_INSERT2 = "INSERT INTO NNC_POLICY_LINK_ROUTE(NPL_SEQ, NPLR_DIV, NPLR_RANGE, NPLR_IP, NPLR_DST_PORT)" + 
									"VALUES(#{npl_seq}, 2, 0,#{nplr_dst_ip}, #{nplr_dst_port})";

	final String SELECT_POLICY_SERVICE = "SELECT NPL.NPL_SEQ, NPL_NAME, NPL_TX_NTC_SEQ, NPL_RX_NTC_SEQ, NPL_TX_NTS_SEQ, NPL_RX_NTS_SEQ, " +
										 "NPL_USE_YN, NPL_REGDTTM, NPL_NO, NPL_STAT, NTS1.NTS_NAME AS NPL_TX_NTS_NAME  , NTC1.NTC_NAME AS NPL_TX_NTC_NAME , NTS2.NTS_NAME AS NPL_RX_NTS_NAME , NTC2.NTC_NAME AS NPL_RX_NTC_NAME " +
										"FROM NNC_POLICY_LINK NPL " +
										"LEFT JOIN NNC_TYPE_CONTENTS NTC1 ON (NPL_TX_NTC_SEQ = NTC1.NTC_SEQ) " +
										"LEFT JOIN NNC_TYPE_CONTENTS NTC2 ON (NPL_RX_NTC_SEQ = NTC2.NTC_SEQ) " +
										"LEFT JOIN NNC_TYPE_SERVICE NTS1 ON (NPL_TX_NTS_SEQ = NTS1.NTS_SEQ AND NTS1.NTC_SEQ = NTC1.NTC_SEQ) " +
										"LEFT JOIN NNC_TYPE_SERVICE NTS2 ON (NPL_RX_NTS_SEQ = NTS2.NTS_SEQ AND NTS2.NTC_SEQ = NTC2.NTC_SEQ) " +
										"ORDER BY NPL_SEQ DESC";


	final String SELECT_POLICY_ROUTE = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_IP, NPLR_PORT, NPLR_ID , "+
										"NPLR_PW ,NPLR_SERVER_NAME, NPLR_REGDTTM "+
										"FROM NNC_POLICY_LINK_ROUTE " +
										"WHERE NPLR_DIV = 1 AND NPL_SEQ = #{npl_seq}";

	final String SELECT_POLICY_ROUTE2 = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_IP, NPLR_PORT, NPLR_ID , "+
										"NPLR_PW , NPLR_SERVER_NAME, NPLR_REGDTTM "+
										"FROM NNC_POLICY_LINK_ROUTE " +
										"WHERE NPLR_DIV = 2 AND NPL_SEQ = #{npl_seq}";

	final String SELECT_PROGRAM_TXNAME = "SELECT NCP_SEQ, NCP_NAME FROM NNC_CONF_PROGRAM WHERE NCP_DIV = 1";

	final String SELECT_PROGRAM_RXNAME = "SELECT NCP_SEQ, NCP_NAME FROM NNC_CONF_PROGRAM WHERE NCP_DIV = 2";

	final String SELECT_STYPE_NAME = "SELECT NTS_SEQ , NTS_NAME FROM NNC_TYPE_SERVICE WHERE NTC_SEQ =#{ntc_seq} ";
	
	final String SELECT_CTYPE_NAME =  "SELECT NTC_SEQ , NTC_NAME FROM NNC_TYPE_CONTENTS";

	final String SELETE_DETAIL_CONTS = "SELECT NTS_SEQ, NTS_NAME FROM NNC_TYPE_SERVICE WHERE NTC_SEQ =#{ntc_seq} ORDER BY NTS_SEQ";

	final String DELETE_POLICY_LINK = "DELETE FROM NNC_POLICY_LINK WHERE NPL_SEQ=#{npl_seq}";

	final String DELETE_POLICY_ROUTE = "DELETE FROM NNC_POLICY_LINK_ROUTE WHERE NPL_SEQ=#{npl_seq}";

	final String SELECT_POLICY_ONE ="SELECT NPL.NPL_SEQ, NPL_NAME,  NPL_TX_NTC_SEQ , NPL_RX_NTC_SEQ, NPL_TX_NTS_SEQ, NPL_RX_NTS_SEQ," + 
									 "NPL_USE_YN , NPL_REGDTTM ,NPL_NO, NTS1.NTS_NAME AS NPL_TX_NTS_NAME  , NTC1.NTC_NAME AS NPL_TX_NTC_NAME , NTS2.NTS_NAME AS NPL_RX_NTS_NAME , NTC2.NTC_NAME AS NPL_RX_NTC_NAME " +
									"FROM NNC_POLICY_LINK NPL " +
									"LEFT JOIN NNC_TYPE_CONTENTS NTC1 ON (NPL_TX_NTC_SEQ = NTC1.NTC_SEQ) " +
									"LEFT JOIN NNC_TYPE_CONTENTS NTC2 ON (NPL_RX_NTC_SEQ = NTC2.NTC_SEQ) " +
									"LEFT JOIN NNC_TYPE_SERVICE NTS1 ON (NPL_TX_NTS_SEQ = NTS1.NTS_SEQ AND NTS1.NTC_SEQ = NTC1.NTC_SEQ) " +
									"LEFT JOIN NNC_TYPE_SERVICE NTS2 ON (NPL_RX_NTS_SEQ = NTS2.NTS_SEQ AND NTS2.NTC_SEQ = NTC2.NTC_SEQ) " +
									"WHERE NPL_SEQ = #{npl_seq} ORDER BY NPL_SEQ DESC";

	final String SELECT_SRC_ROUTE = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_IP, NPLR_PORT, NPLR_ID , "
									+ " NPLR_PW, NPLR_SERVER_NAME, NPLR_REGDTTM, "
									+ " NPLR_QUERY, NPLR_WHERE, NPLR_IDX_COLUMN, NPLR_INTERVAL_TYPE, NPLR_INTERVAL_VALUE "
									+ " FROM NNC_POLICY_LINK_ROUTE " 
									+ " WHERE NPLR_DIV = 1 AND NPL_SEQ = #{npl_seq}";

	final String SELECT_DST_ROUTE = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_IP, NPLR_PORT, NPLR_ID , "
									+ " NPLR_PW, NPLR_SERVER_NAME, NPLR_REGDTTM, "
									+ " NPLR_QUERY, NPLR_CNT "
									+ " FROM NNC_POLICY_LINK_ROUTE "
									+ " WHERE NPLR_DIV = 2 AND NPL_SEQ = #{npl_seq}";

	final String INSERT_POLICYLINK2 = "INSERT INTO NNC_POLICY_LINK(NPL_NAME, NPL_TX_NTC_SEQ, NPL_TX_NTS_SEQ, NPL_RX_NTC_SEQ, NPL_RX_NTS_SEQ, NPL_USE_YN , NPL_REGDTTM,NPL_NO)" +
			"VALUES(#{npl_name}, #{npl_tx_ntc_seq}, #{npl_tx_nts_seq}, #{npl_rx_ntc_seq}, #{npl_rx_nts_seq}, 1 , now() ,#{npl_no} )";

	final String INSERT_SRCIP = "INSERT INTO NNC_POLICY_LINK_ROUTE "
			+ " (NPL_SEQ, NPLR_DIV, NPLR_IP, NPLR_PORT, NPLR_ID, NPLR_PW,NPLR_SERVER_NAME,NPLR_REGDTTM, "
			+ " NPLR_QUERY, NPLR_WHERE, NPLR_IDX_COLUMN, NPLR_INTERVAL_TYPE, NPLR_INTERVAL_VALUE ) "
			+ " VALUES(#{npl_seq}, 1, #{nplr_ip}, #{nplr_port},#{npl_tx_id} ,#{npl_tx_pw} ,#{nplr_server_name},now(), "
			+ " #{nplr_query}, #{nplr_where}, #{nplr_idx_column}, #{nplr_interval_type}, #{nplr_interval_value}) ";

	
	final String INSERT_DSTIP = "INSERT INTO NNC_POLICY_LINK_ROUTE(NPL_SEQ, NPLR_DIV, NPLR_IP, NPLR_PORT, NPLR_ID, NPLR_PW,NPLR_SERVER_NAME,NPLR_REGDTTM, "
			+ " NPLR_QUERY, NPLR_CNT ) "
			+ " VALUES(#{npl_seq}, 2, #{nplr_ip2}, #{nplr_port2},#{npl_rx_id} ,#{npl_rx_pw} ,#{nplr_server_name2}, now(), "
			+ " #{nplr_rx_query}, #{nplr_cnt} ) ";

	final String POLICY_SERVICE_DELETE = "DELETE FROM NNC_POLICY_LINK WHERE NPL_SEQ=#{npl_seq}";

	final String POLICY_ROUTE_DELETE = "DELETE FROM NNC_POLICY_LINK_ROUTE WHERE NPL_SEQ=#{npl_seq}";

	final String UPDATE_POLICYLINK = "UPDATE NNC_POLICY_LINK " +
			"SET NPL_NAME=#{npl_name}, NPL_TX_NTC_SEQ=#{npl_tx_ntc_seq}, "+ 
			"NPL_TX_NTS_SEQ=#{npl_tx_nts_seq}, NPL_RX_NTC_SEQ=#{npl_rx_ntc_seq}, "+ 
			"NPL_RX_NTS_SEQ=#{npl_rx_nts_seq}, NPL_USE_YN=#{npl_use_yn},NPL_NO = #{npl_no} "+
			"WHERE NPL_SEQ=#{npl_seq}";

	final String UPDATE_SRCIP = "UPDATE NNC_POLICY_LINK_ROUTE " +
			"SET NPLR_IP=#{nplr_ip}, " +
			"NPLR_PORT=#{nplr_port}, " +
			"NPLR_ID=#{npl_tx_id}, " +
			"NPLR_PW=#{npl_tx_pw}, " +
			"NPLR_SERVER_NAME=#{nplr_server_name}, " +
			"NPLR_QUERY=#{nplr_query}, " +
			"NPLR_WHERE=#{nplr_where}, " +
			"NPLR_IDX_COLUMN=#{nplr_idx_column}, " +
			"NPLR_INTERVAL_TYPE=#{nplr_interval_type}, " +
			"NPLR_INTERVAL_VALUE=#{nplr_interval_value} " +
			"WHERE NPLR_DIV=1 AND NPL_SEQ=#{npl_seq}";

	final String UPDATE_DSTIP = "UPDATE NNC_POLICY_LINK_ROUTE " +
			"SET NPLR_IP=#{nplr_ip2}, " +
			"NPLR_PORT=#{nplr_port2}, " +
			"NPLR_ID=#{npl_rx_id}, " +
			"NPLR_PW=#{npl_rx_pw}, " +
			"NPLR_SERVER_NAME=#{nplr_server_name2}, " +
			"NPLR_QUERY=#{nplr_rx_query}, " +
			"NPLR_CNT=#{nplr_cnt} " +
			"WHERE NPLR_DIV=2 AND NPL_SEQ=#{npl_seq}";

	final String INSERT_FILESEND = "INSERT INTO NNC_POLICY_FILE_SEND(NPL_SEQ,NPFS_TYPE,NPFS_FILTER,NPFS_REGDTTM)" + 
									"VALUES(#{npl_seq},#{npfs_type},#{npfs_filter}, now())";

	final String INSERT_FILEEXCEPT = "INSERT INTO NNC_POLICY_FILE_EXCEPT(NPL_SEQ,NPFE_USE, NPFE_MAX_SIZE, NPFE_FILTER, NPFE_OPTION, NPFE_MOVE_DIR, NPFE_REGDTTM)" + 
			"VALUES(#{npl_seq},#{npfe_use},#{npfe_max_size},#{npfe_filter},#{npfe_option},#{npfe_move_dir}, now())";

	final String INSERT_FILETX = "INSERT INTO NNC_POLICY_FILE_TX(NPL_SEQ,NPFT_SPLIT, NPFT_INTERVAL, NPFT_ENCRYPT, NPFT_ENCODE, NPFT_BUFFER, NPFT_OPEN,NPFT_BACKUP,NPFE_FILE_DIR,NPFE_REGDTTM)" + 
			"VALUES(#{npl_seq},#{npft_split},#{npft_interval},#{npft_encrypt},#{npft_encode},#{npft_buffer},#{npft_open},#{npft_backup},#{npfe_file_dir}, now())";

	final String INSERT_FILERX = "INSERT INTO NNC_POLICY_FILE_RX(NPL_SEQ,NPFR_QUE_SIZE, NPFR_DECODE_SIZE, NPFR_BACKUP, NPFR_BACKUP_DIR,NPFE_REGDTTM)" + 
			"VALUES(#{npl_seq},#{npfr_que_size},#{npfr_decode_size},#{npfr_backup},#{npfr_backup_dir}, now())";

	final String DELETE_FILESEND = "DELETE FROM NNC_POLICY_FILE_SEND WHERE NPL_SEQ=#{npl_seq}";

	final String DELETE_FILEEXCEPT = "DELETE FROM NNC_POLICY_FILE_EXCEPT WHERE NPL_SEQ=#{npl_seq}";

	final String DELETE_FILETX = "DELETE FROM NNC_POLICY_FILE_TX WHERE NPL_SEQ=#{npl_seq}";

	final String DELETE_FILERX = "DELETE FROM NNC_POLICY_FILE_RX WHERE NPL_SEQ=#{npl_seq}";

	final String GET_FILETX = "SELECT NPFT_SEQ,NPL_SEQ,NPFT_SPLIT,NPFT_INTERVAL,NPFT_ENCRYPT,NPFT_ENCODE,NPFT_BUFFER,NPFT_OPEN,NPFT_BACKUP,NPFE_FILE_DIR,NPFE_REGDTTM " +
								"FROM NNC_POLICY_FILE_TX " +
								"WHERE NPL_SEQ = #{npl_seq}";

	final String GET_FILERX = "SELECT NPFR_SEQ,NPL_SEQ,NPFR_QUE_SIZE,NPFR_DECODE_SIZE,NPFR_BACKUP,NPFR_BACKUP_DIR,NPFE_REGDTTM " +
							"FROM NNC_POLICY_FILE_RX " +
							"WHERE NPL_SEQ = #{npl_seq}";

	final String GET_FILESEND = "SELECT NPFS_SEQ,NPL_SEQ,NPFS_TYPE,NPFS_FILTER,NPFS_REGDTTM " +
								"FROM NNC_POLICY_FILE_SEND " +
								"WHERE NPL_SEQ = #{npl_seq}";

	final String GET_FILEEXCEPT = "SELECT NPFE_SEQ,NPL_SEQ,NPFE_USE,NPFE_MAX_SIZE,NPFE_FILTER,NPFE_OPTION,NPFE_MOVE_DIR,NPFE_REGDTTM " + 
								"FROM NNC_POLICY_FILE_EXCEPT " +
								"WHERE NPL_SEQ = #{npl_seq}";

	final String UPDATE_FILESEND = "UPDATE NNC_POLICY_FILE_SEND " +
									"SET NPFS_TYPE = #{npfs_type}, " +
									"NPFS_FILTER = #{npfs_filter} " +
									"WHERE NPL_SEQ = #{npl_seq}";

	final String UPDATE_FILEEXCEPT = "UPDATE NNC_POLICY_FILE_EXCEPT " +
									"SET NPFE_USE = #{npfe_use}, " +
									"NPFE_MAX_SIZE = #{npfe_max_size}, " +
									"NPFE_FILTER = #{npfe_filter}, " +
									"NPFE_OPTION = #{npfe_option}, " +
									"NPFE_MOVE_DIR = #{npfe_move_dir} " +
									"WHERE NPL_SEQ = #{npl_seq}";

	final String UPDATE_FILETX = "UPDATE NNC_POLICY_FILE_TX " +
								"SET NPFT_SPLIT = #{npft_split}," +
								"NPFT_INTERVAL = #{npft_interval}," +
								"NPFT_ENCRYPT = #{npft_encrypt}," +
								"NPFT_ENCODE = #{npft_encode}," +
								"NPFT_BUFFER = #{npft_buffer}," +
								"NPFT_OPEN = #{npft_open}," +
								"NPFT_BACKUP = #{npft_backup}," +
								"NPFT_FILE_DIR = #{npft_file_dir} " +
								"WHERE NPL_SEQ = #{npl_seq}";

	final String UPDATE_FILERX = "UPDATE NNC_POLICY_FILE_RX " +
								"SET NPFR_QUE_SIZE = #{npfr_que_size}, " +
								"NPFR_DECODE_SIZE = #{npfr_decode_size}, " +
								"NPFR_BACKUP = #{npfr_backup}, " +
								"NPFR_BACKUP_DIR = #{npfr_backup_dir} " +
								"WHERE NPL_SEQ = #{npl_seq}";

	final String POLICY_USE_CHANGE = "UPDATE NNC_POLICY_LINK SET NPL_USE_YN = #{npl_use_yn} WHERE NPL_SEQ = #{npl_seq}";

	final String SELECT_STYPE_NAME2 = "SELECT NTS_SEQ , NTS_NAME FROM NNC_TYPE_SERVICE WHERE NTS_SEQ=#{npl_rx_nts_seq}";

	final String SELECT_CTYPE_NAME2 = "SELECT NTS_SEQ , NTS_NAME FROM NNC_TYPE_SERVICE WHERE NTS_SEQ=#{npl_tx_nts_seq}";

	final String POLICY_SERVICE_BYSEQ1 = "SELECT NPL_NAME, NPL_NO FROM NNC_POLICY_LINK" +
										" WHERE NPL_SEQ = #{nlp_seq}";

	final String GET_PR1 = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_RANGE , NPLR_IP, NPLR_END_IP , NPLR_PORT, NPLR_URL, NPLR_ID , "+
							"NPLR_PW , NPLR_FILE_DIR, NPLR_CYCLE , NPLR_REGDTTM "+
							"FROM NNC_POLICY_LINK_ROUTE " +
							"WHERE NPLR_DIV = 1 AND NPL_SEQ = #{npl_seq}";
	final String GET_PR2 = "SELECT NPLR_SEQ , NPL_SEQ , NPLR_DIV , NPLR_RANGE , NPLR_IP, NPLR_END_IP , NPLR_PORT, NPLR_URL, NPLR_ID , "+
							"NPLR_PW , NPLR_FILE_DIR, NPLR_CYCLE , NPLR_REGDTTM "+
							"FROM NNC_POLICY_LINK_ROUTE " +
							"WHERE NPLR_DIV = 2 AND NPL_SEQ = #{npl_seq}";

	final String TX_PASSWORD_UPDATE ="UPDATE NNC_POLICY_LINK_ROUTE SET NPLR_PW=#{password}, NPLR_REGDTTM=#{salt} WHERE NPL_SEQ=#{seq} AND NPLR_DIV = #{div}";

	final String getPolicySalt = "SELECT DATE_FORMAT(NPL_REGDTTM,'%Y-%m-%d %H:%i:%s') NPL_REGDTTM FROM NNC_POLICY_LINK WHERE NPL_SEQ = #{npl_seq}";

	final String RX_PASSWORD_UPDATE ="UPDATE NNC_POLICY_LINK_ROUTE SET NPLR_PW=#{password}, NPLR_REGDTTM=#{salt} WHERE NPL_SEQ=#{seq} AND NPLR_DIV = #{div}";

	final String SELECT_STYPE_NAME3 = "SELECT NTS_SEQ , NTS_NAME FROM NNC_TYPE_SERVICE";
	@Select(POLICY_TEST_NAME)                                                                                                                                            
	@Results(value = {
			@Result(property="ntc_name", column="NTC_NAME"),
	        @Result(property="nts_name", column="NTS_NAME"),
	})
	NcPolicyLink getContsTest(@Param("ntc_seq") int ntc_seq, @Param("nts_seq") int nts_seq);
	
	@Select(LAST_POLICY_SEQ)
	int last_policy_seq();
	
	
	@Insert(INSERT_POLICYLINK)
	int insert_policylink(NcPolicyLink policyInsert);
	
	@Insert(PL_ROUTE_INSERT)
	int pl_route_insert(NcPolicyLink policyInsert);
	
	@Insert(PL_ROUTE_INSERT2)
	int pl_route_insert2(NcPolicyLink policyInsert);
	
	
	@Select(SELECT_POLICY_SERVICE)
	@Results(value = {
    		@Result(property="npl_seq", column="NPL_SEQ"),
	        @Result(property="npl_name", column="NPL_NAME"),
	        @Result(property="npl_tx_ntc_seq", column="NPL_TX_NTC_SEQ"),
	        @Result(property="npl_tx_nts_seq", column="NPL_TX_NTS_SEQ"),
    		@Result(property="npl_rx_ntc_seq", column="NPL_RC_NTC_SEQ"),
    		@Result(property="npl_rx_nts_seq", column="NPL_RX_NTS_SEQ"),
    		@Result(property="npl_regdttm", column="NPL_REGDTTM"),
    		@Result(property="npl_use_yn", column="NPL_USE_YN"),
    		@Result(property="npl_no", column="NPL_NO"),
    		@Result(property="npl_stat", column="NPL_STAT"),
    })
	ArrayList<NcPolicyLink> getPolicyService();
	
	@Select(SELECT_POLICY_ROUTE)
	@Results(value = {
			@Result(property="nplr_seq", column="NPLR_SEQ"),
	        @Result(property="npl_seq", column="npl_seq"),
	        @Result(property="nplr_div", column="NPLR_DIV"),
    		@Result(property="nplr_ip", column="NPLR_IP"),
    		@Result(property="nplr_port", column="NPLR_PORT"),
    		@Result(property="nplr_id", column="NPLR_ID"),
    		@Result(property="nplr_pw", column="NPLR_PW"),
    		@Result(property="nplr_server_name", column="NPLR_SERVER_NAME"),
    		@Result(property="nplr_regdttm", column="NPLR_REGTTM"),
	})
	ArrayList<NcPolicyLink> getPolicyRoute(@Param("npl_seq") int npl_seq);
	@Select(SELECT_POLICY_ROUTE2)
	ArrayList<NcPolicyLink> getPolicyRoute2(@Param("npl_seq") int npl_seq);
	
	@Select(SELECT_PROGRAM_TXNAME)
	@Results(value={
			@Result(property="ncp_seq", column="NCP_SEQ"),
			@Result(property="ncp_name", column="NCP_NAME"),
			@Result(property="ncp_div", column="NCP_DIV"),
			@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
			@Result(property="ncp_path", column="NCP_PATH"),
			@Result(property="ncp_status", column="NCP_STATUS"),
			@Result(property="ncp_service_flag", column="NCP_SERVICE_FLAG"),
			@Result(property="ncp_hash", column="NCP_HASH"),
			@Result(property="ncp_file_size", column="NCP_FILE_SIZE"),
			@Result(property="ncp_hash_date", column="NCP_HASH_DATE"),
			@Result(property="ncp_inspect_flag", column="NCP_inspect_FLAG"),
			@Result(property="ncp_use_yn", column="NCP_USE_YN"),
		})
	ArrayList<NcPolicyLink> getProgramTxName();
	
	@Select(SELECT_PROGRAM_RXNAME)
	ArrayList<NcPolicyLink> getProgramRxName();
	
	@Results(value= {
			@Result(property="nts_seq", column="NTS_SEQ"),
			@Result(property="nts_name", column="NTS_NAME"),
			@Result(property="ntc_seq", column="NTC_SEQ"),
			@Result(property="ntc_NAME", column="NTC_NAME"),
	})
	@Select(SELECT_STYPE_NAME)
	ArrayList<NcPolicyLink> getstName(@Param("ntc_seq") int ntc_seq);
	
	@Select(SELECT_CTYPE_NAME)
	ArrayList<NcPolicyLink> getctName();
	
	@Select(SELETE_DETAIL_CONTS)
	@Results(value= {
			@Result(property="nts_seq", column="NTS_SEQ"),
			@Result(property="nts_name", column="NTS_NAME"),
			@Result(property="ntc_seq", column="NTC_SEQ"),
			@Result(property="ntc_NAME", column="NTC_NAME"),
	})
	ArrayList<NcPolicyLink> getDetailConts(@Param("ntc_seq")int ntc_seq);
	
	@Insert(DELETE_POLICY_LINK)
	int delete_policy_link(@Param("npl_seq") int npl_seq);
	
	@Insert(DELETE_POLICY_ROUTE)
	int delete_policy_route(@Param("npl_seq") int npl_seq);
	
	@Select(SELECT_POLICY_ONE)
	NcPolicyLink getPolicyOne(@Param("npl_seq") int npl_seq);
	
	@Select(SELECT_SRC_ROUTE)
	NcPolicyLink getsrcRoute(@Param("npl_seq") int npl_seq);
	@Select(SELECT_DST_ROUTE)
	NcPolicyLink getdstRoute(@Param("npl_seq") int npl_seq);
	
	@Insert(INSERT_POLICYLINK2)
	int insert_policylink2(NcPolicyLink policyInsert);
	
	@Insert(INSERT_SRCIP)
	int insert_srcIp(NcPolicyLink policyInsert);
	
	@Insert(INSERT_DSTIP)
	int insert_dstIp(NcPolicyLink policyInsert);
	
	@Insert(POLICY_SERVICE_DELETE)
	int policy_service_delete(int npl_seq);
	@Insert(POLICY_ROUTE_DELETE)
	int policy_route_delete(int npl_seq);
	@Insert(UPDATE_POLICYLINK)
	int update_policylink(NcPolicyLink policyInsert);

	@Insert(UPDATE_SRCIP)
	int update_srcIp(NcPolicyLink policyInsert);
	
	@Insert(UPDATE_DSTIP)
	int update_dstIp(NcPolicyLink policyInsert);
	
	@Insert(INSERT_FILESEND)
	int insert_fileSend(NcPolicyLink policyInsert);
	
	@Insert(INSERT_FILEEXCEPT)
	int insert_fileExcept(NcPolicyLink policyInsert);
	
	@Insert(INSERT_FILETX)
	int insert_fileTx(NcPolicyLink policyInsert);
	
	@Insert(INSERT_FILERX)
	int insert_fileRx(NcPolicyLink policyInsert);
	
	@Insert(DELETE_FILESEND)
	int delete_fileSend(int npl_seq);
	
	@Insert(DELETE_FILEEXCEPT)
	int delete_fileExcept(int npl_seq);
	
	@Insert(DELETE_FILETX)
	int delete_fileTx(int npl_seq);
	
	@Insert(DELETE_FILERX)
	int delete_fileRx(int npl_seq);
	
	@Results(value= {
			@Result(property="npft_seq", column="NPFS_SEQ"),
			@Result(property="npl_seq", column="NPL_SEQ"),
			@Result(property="npft_split", column="NPFT_SPLIT"),
			@Result(property="npft_interval", column="NPFT_INTERVAL"),
			@Result(property="npft_encrypt", column="NPFT_ENCRYPT"),
			@Result(property="npft_encode", column="NPFT_ENCODE"),
			@Result(property="npft_buffer", column="NPFT_BUFFER"),
			@Result(property="npft_open", column="NPFT_OPEN"),
			@Result(property="npft_backup", column="NPFT_BACKUP"),
			@Result(property="npfe_file_dir", column="NPFE_FILE_DIR"),
			@Result(property="npfe_regdttm", column="NPFE_REGDTTM"),
	})
	@Select(GET_FILETX)
	NcPolicyLink getfileTx(int npl_seq);
	
	@Results(value= {
			@Result(property="npfr_seq", column="NPFS_SEQ"),
			@Result(property="npl_seq", column="NPL_SEQ"),
			@Result(property="npfr_que_size", column="NPFR_QUE_SIZE"),
			@Result(property="npfr_decode_size", column="NPFR_DECODE_SIZE"),
			@Result(property="npfr_backup", column="NPFR_BACKUP"),
			@Result(property="npfr_backup_dir", column="NPFR_BACKUP_DIR"),
			@Result(property="npfr_regdttm", column="NPFR_REGDTTM"),
	})
	@Select(GET_FILERX)
	NcPolicyLink getfileRx(int npl_seq);
	
	@Results(value= {
			@Result(property="npfs_seq", column="NPFS_SEQ"),
			@Result(property="npl_seq", column="NPL_SEQ"),
			@Result(property="npfs_type", column="NPFS_TYPE"),
			@Result(property="npfs_filter", column="NPFS_FILTER"),
			@Result(property="npfs_regdttm", column="NPFS_REGDTTM"),
	})
	@Select(GET_FILESEND)
	NcPolicyLink getfileSend(int npl_seq);
	
	@Results(value= {
			@Result(property="npfe_seq", column="NPFE_SEQ"),
			@Result(property="npl_seq", column="NPL_SEQ"),
			@Result(property="npfe_use", column="NPFR_QUE_SIZE"),
			@Result(property="npfe_max_size", column="NPFE_MAX_SIZE"),
			@Result(property="npfe_filter", column="NPFE_FILTER"),
			@Result(property="npfe_option", column="NPFE_OPTION"),
			@Result(property="npfe_move_dir", column="NPFE_MOVE_DIR"),
			@Result(property="npfe_regdttm", column="NPFE_REGDTTM"),
	})
	@Select(GET_FILEEXCEPT)
	NcPolicyLink getfileExcept(int npl_seq);
	
	@Insert(UPDATE_FILESEND)
	int update_fileSend(NcPolicyLink policyInsert);
	
	@Insert(UPDATE_FILEEXCEPT)
	int update_fileExcept(NcPolicyLink policyInsert);
	
	@Insert(UPDATE_FILETX)
	int update_fileTx(NcPolicyLink policyInsert);
	
	@Insert(UPDATE_FILERX)
	int update_fileRx(NcPolicyLink policyInsert);
	
	@Update(POLICY_USE_CHANGE)
	int policy_use_change(@Param("npl_use_yn") int npl_use_yn,@Param("npl_seq") int npl_seq);
	@Select(SELECT_CTYPE_NAME2)
	NcPolicyLink getctName2(@Param("npl_tx_nts_seq") int npl_tx_nts_seq);
	@Select(SELECT_STYPE_NAME2)
	NcPolicyLink getstName2(@Param("npl_rx_nts_seq") int npl_rx_nts_seq);
	
	@Select(POLICY_SERVICE_BYSEQ1)
	NcPolicyLink getPolicyServiceBySeq(@Param("nlp_seq") int nlp_seq);
	
	@Select(GET_PR1)
	NcPolicyLink getPolicyr1(@Param("npl_seq") int npl_seq);
	@Select(GET_PR2)
	NcPolicyLink getPolicyr2(@Param("npl_seq") int npl_seq);
	
	@Update(TX_PASSWORD_UPDATE)
	int tx_password_update(@Param("seq") int seq, @Param("password") String password,  @Param("salt") String salt, @Param("div")int div);
	
	@Select(getPolicySalt)
	NcPolicyLink getPolicySalt(@Param("npl_seq") int npl_seq);
	
	@Update(RX_PASSWORD_UPDATE)
	int rx_password_update(@Param("seq") int seq, @Param("password") String password,  @Param("salt") String salt, @Param("div")int div);
	@Results(value= {
			@Result(property="nts_seq", column="NTS_SEQ"),
			@Result(property="nts_name", column="NTS_NAME"),
			@Result(property="ntc_seq", column="NTC_SEQ"),
			@Result(property="ntc_NAME", column="NTC_NAME"),
	})
	@Select(SELECT_STYPE_NAME3)
	ArrayList<NcPolicyLink> getstName3();

	// OPC 태그
	final String INSERT_OPC_TAG = " INSERT INTO nnc_opc_tag " +
			" (NPL_NO, NOT_TX_TAG, NOT_RX_TAG, NOT_TYPE) " +
			" VALUES (#{npl_no}, #{not_tx_tag}, #{not_rx_tag}, #{not_type} ) ";
	@Insert(INSERT_OPC_TAG)
	int insertOpcTag(@Param("npl_no") int npl_no, @Param("not_tx_tag") String not_tx_tag,  @Param("not_rx_tag") String not_rx_tag, @Param("not_type")int not_type);
	final String DEL_OPC_TAG = " DELETE FROM nnc_opc_tag WHERE NPL_NO = #{npl_no} ";

	@Delete(DEL_OPC_TAG)
	int deleteOpcTag(@Param("npl_no") int npl_no);
	
	final String UPDATE_OPC_TAG = "UPDATE nnc_opc_tag " +
			"SET NPL_NO = #{npl_no}," +
			"NOT_TX_TAG = #{not_tx_tag}," +
			"NOT_RX_TAG = #{not_rx_tag}," +
			"NOT_TYPE = #{not_type} " +
			"WHERE NPL_SEQ = #{npl_seq}";

	final String GET_OPC_TAG = "SELECT NOT_SEQ,NPL_NO,NOT_TX_TAG,NOT_RX_TAG,NOT_TYPE FROM NNC_OPC_TAG WHERE NPL_NO =#{npl_no}";
	@Update(UPDATE_OPC_TAG)
	int updateOpcTag(int npl_no, String not_tx_tag, String not_rx_tag, int not_type);
	
	@Results(value= {
			@Result(property="npl_no", column="NPL_NO"),
			@Result(property="not_tx_tag", column="NOT_TX_TAG"),
			@Result(property="not_rx_tag", column="NOT_RX_TAG"),
			@Result(property="not_type", column="NOT_TYPE"),
	})
	@Select(GET_OPC_TAG)
	ArrayList<NcOpcTag> getopcTagList(int npl_no);
	
}