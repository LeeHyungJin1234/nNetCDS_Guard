package nnsp.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import nnsp.vo.NdrPs;

public interface NdrAnalyzeMapper {
	//  ICS 패킷 정보 ID
	final String SELECT_PS_ID =
		" SELECT PS_ID "+
		"	,PS_PROTO_SUB_DEC "+
		"	,PS_PROTO_ICS "+
		"	,PS_PROTO_ICS_CMD_TYPE "+
		"	,PS_SRC_MAC "+
		"	,PS_SRC_MAC_VENDOR "+
		"	,PS_SRC_ICS_VENDOR "+
		"	,PS_SRC_ICS_Layer "+
		"	,PS_DST_MAC "+
		"	,PS_DST_MAC_VENDOR "+
		"	,PS_DST_ICS_VENDOR "+
		"	,PS_DST_ICS_Layer "+
		"	,PS_SRC_IP "+
		"	,PS_DST_IP "+
		"	,PS_SRC_PORT "+
		"	,PS_DST_PORT "+
		"	,PS_WHITE_RULE "+
		" FROM nnet_cds2.nnc_packet_stat_#{table_name} "+
		" WHERE PS_ID=#{ps_id} ";
	
	@Select(SELECT_PS_ID)
    @Results(value = {
    		@Result(property="ps_id", column="PS_ID"),
    		@Result(property="ps_proto_sub_dec", column="PS_PROTO_SUB_DEC"),
    		@Result(property="ps_proto_ics", column="PS_PROTO_ICS"),
    		@Result(property="ps_proto_ics_cmd_type", column="PS_PROTO_ICS_CMD_TYPE"),
    		@Result(property="ps_src_mac", column="PS_SRC_MAC"),
    		@Result(property="ps_src_mac_vendor", column="PS_SRC_MAC_VENDOR"),
    		@Result(property="ps_src_ics_vendor", column="PS_SRC_ICS_VENDOR"),
    		@Result(property="ps_src_ics_layer", column="PS_SRC_ICS_Layer"),
    		@Result(property="ps_dst_mac", column="PS_DST_MAC"),
    		@Result(property="ps_dst_mac_vendor", column="PS_DST_MAC_VENDOR"),
    		@Result(property="ps_dst_ics_vendor", column="PS_DST_ICS_VENDOR"),
    		@Result(property="ps_dst_ics_layer", column="PS_DST_ICS_Layer"),
    		@Result(property="ps_src_ip", column="PS_SRC_IP"),
    		@Result(property="ps_dst_ip", column="PS_DST_IP"),
    		@Result(property="ps_src_port", column="PS_SRC_PORT"),
    		@Result(property="ps_dst_port", column="PS_DST_PORT"),
    		@Result(property="ps_white_rule", column="PS_WHITE_RULE")
    })
	NdrPs getPsById(@Param("table_name") int table_name, @Param("ps_id") long ps_id);
}
