package nnsp.xmlmappers;

import java.util.ArrayList;
import java.util.Map;

import nnsp.vo.NdrPs;

public interface NdrAnalyzeXmlMapper {

	ArrayList<NdrPs> getIcsProtocolList(Map<String, Object> param);// 조건에 의한 프로토콜 목록
	ArrayList<NdrPs> getICSPacketList(Map<String, Object> param);// 조건에 의한 ICS 패킷 목록
	int getICSPacketCnt(Map<String, Object> param);// 조건에 의한 ICS 패킷 
	String getPWRStr(Map<String, Object> param);// ICS 시그니쳐 값 가져오기
	ArrayList<NdrPs> getPWRList(Map<String, Object> param);// 시그니쳐 통신 목록
	ArrayList<NdrPs> getWhiteRulePacketList(Map<String, Object> param); //  시그니쳐 통신량 목록

	int createTblPacket(Map<String, Object> param);
}