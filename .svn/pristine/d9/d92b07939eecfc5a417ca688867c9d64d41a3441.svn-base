package nnsp.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import nnsp.vo.NdrPs;

public interface NcWhMapper {
	final String SELECT_PACKET_STATS =
        " SELECT PS_ID, PS_STRC_TIME, PS_PROTO_ID, PS_PROTO_SUB_DEC, PS_SRC_IP, PS_DST_IP, PS_SRC_PORT, PS_DST_PORT, " + 
        " PS_INOUT, PS_DETECT, PS_DETECT_DESC, PS_NGL_ID, NGL_GROUP_NAME " + 
        " FROM (";

    final String SELECT_PACKET_STATS_END =
        ") ps " + 
        " ORDER BY PS_STRC_TIME DESC ";

    @Select("<script>" +
        "    ${read_qry} " +
        "</script>")
    @Results(value = {
        @Result(property="ps_id", column="PS_ID"),
        @Result(property="ps_strc_time", column="PS_STRC_TIME"),
        @Result(property="ps_proto_sub_dec", column="PS_PROTO_SUB_DEC"),
        @Result(property="ps_src_ip", column="PS_SRC_IP"),
        @Result(property="ps_dst_ip", column="PS_DST_IP"),
        @Result(property="ps_src_port", column="PS_SRC_PORT"),
        @Result(property="ps_dst_port", column="PS_DST_PORT"),
        @Result(property="ps_inout", column="PS_INOUT"),
        @Result(property="ps_detect", column="PS_DETECT"),
        @Result(property="ps_detect_desc", column="PS_DETECT_DESC"),
        @Result(property="ps_ngl_id", column="PS_NGL_ID"),
        @Result(property="ngl_group_name", column="NGL_GROUP_NAME")
    })
    ArrayList<NdrPs> getPacketStatsByDateRange(@Param("read_qry") String read_qry);
}
