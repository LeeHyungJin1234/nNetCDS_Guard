package nnsp.xmlmappers;

import java.util.ArrayList;
import java.util.Map;

import nnsp.vo.NcWhList;
import nnsp.vo.NdrPs;

public interface NcWhXmlMapper {
//화이트 리스트
	ArrayList<NdrPs> getPacketList_grid(Map<String, Object> param);// 조건에 의한 ICS 패킷 목록
	ArrayList<NdrPs> getPacketStatList(Map<String, Object> param);
	int getPacketStatTotalCount(Map<String, Object> param);
	ArrayList<NcWhList> getWhList_grid();
	ArrayList<NcWhList> getWhList(Map<String, Object> param);
	NcWhList getWhListId(Map<String, Object> param);
	int getWhCnt_grid();
	int getWhCnt(Map<String, Object> param);
	int getWhRuleCnt(Map<String, Object> param);
	int insertWhProcInfo(Map<String, Object> param);
	int delWhProcInfo(Map<String, Object> param);
	int delWhId(Map<String, Object> param);
	int updateWhiteUseYN(Map<String, Object> param);
	int insertWhiteList(Map<String, Object> param);
	int registerGroupList(Map<String, Object> param);
	int registerWhiteList(Map<String, Object> param);
	ArrayList<NcWhList> getWhiteList();
	int getWhiteListCount();
	int updateWhiteList(Map<String, Object> param);
	int updateWlUseYn(Map<String, Object> param);
	ArrayList<NcWhList> selectProtocolList();
	ArrayList<NcWhList> selectPolicyLevelList();
//	int selectGroupListCnt(Map<String, Object> param);
	ArrayList<NcWhList> selectGroupList(Map<String, Object> param);
	NcWhList selectGroupInfo(Map<String, Object> param);
	ArrayList<NcWhList> selectLastGroupList();
	ArrayList<NcWhList> selectGroupWhiteList(Map<String, Object> param);
	int getGroupWhiteListCount(Map<String, Object> param);
	int selectIsDuplicateWhiteList(Map<String, Object> param);
	NdrPs selectPacketStatByPsId(Map<String, Object> param);
	NcWhList selectWhiteListByGroupName(Map<String, Object> param);
	int deleteWhiteList(Map<String, Object> param);
	int deleteGroup(Map<String, Object> param);
	NcWhList selectCheckedWhiteList(Map<String, Object> param);
	int deleteCheckedWhiteList(Map<String, Object> param);
	ArrayList<NcWhList> selectWhiteLisyByNglId(Map<String, Object> param);
	ArrayList<NcWhList> selectHostType(Map<String, Object> param);
}