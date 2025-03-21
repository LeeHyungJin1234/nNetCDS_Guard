package nnsp.services;


import nnsp.mappers.NcPolicyLinkMapper;
import nnsp.vo.NcOpcTag;
import nnsp.vo.NcPolicyLink;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NcPolicyLinkService {

	@Autowired
	private NcPolicyLinkMapper ncPolicyLinkMapper;

	public NcPolicyLink getContsTest(int ntc_seq, int nts_seq) {
		return ncPolicyLinkMapper.getContsTest(ntc_seq, nts_seq);
	}
	
	//NND_POLICY_LINK 번호 찾기
	public int last_policy_seq() {
		return ncPolicyLinkMapper.last_policy_seq();
	}
	
	public int insert_policylink(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.insert_policylink(policyInsert);
	}

	public int pl_route_insert(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.pl_route_insert(policyInsert);
	}

	public int pl_route_insert2(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.pl_route_insert2(policyInsert);
		
	}

	public ArrayList<NcPolicyLink> getPolicyService() {
		return ncPolicyLinkMapper.getPolicyService();
	}

	public ArrayList<NcPolicyLink> getPolicyRoute(int npl_seq) {
		return ncPolicyLinkMapper.getPolicyRoute(npl_seq);
	}

	public ArrayList<NcPolicyLink> getPolicyRoute2(int npl_seq) {
		return ncPolicyLinkMapper.getPolicyRoute2(npl_seq);
	}

	public ArrayList<NcPolicyLink> getProgramTxName() {
		return ncPolicyLinkMapper.getProgramTxName();
	}

	public ArrayList<NcPolicyLink> getProgramRxName() {
		return ncPolicyLinkMapper.getProgramRxName();
	}

	public ArrayList<NcPolicyLink> getstName(int ntc_seq) {
		return ncPolicyLinkMapper.getstName(ntc_seq);
	}

	public ArrayList<NcPolicyLink> getctName() {
		return ncPolicyLinkMapper.getctName();
	}

	public ArrayList<NcPolicyLink> getDetailConts(int ntc_seq) {
		return ncPolicyLinkMapper.getDetailConts(ntc_seq);
	}

	public int delete_policy_link(int npl_seq) {
		return ncPolicyLinkMapper.delete_policy_link(npl_seq);
	}

	public int delete_policy_route(int npl_seq) {
		return ncPolicyLinkMapper.delete_policy_route(npl_seq);
	}

	public NcPolicyLink getPolicyOne(int npl_seq) {
		return ncPolicyLinkMapper.getPolicyOne(npl_seq);
	}

	public NcPolicyLink getsrcRoute(int npl_seq) {
		return ncPolicyLinkMapper.getsrcRoute(npl_seq);
	}

	public NcPolicyLink getdstRoute(int npl_seq) {
		return ncPolicyLinkMapper.getdstRoute(npl_seq);
	}

	public int insert_policylink2(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.insert_policylink2(policyInsert);
	}

	public int insert_srcIp(NcPolicyLink policyInsert) {
		policyInsert.setNplr_query(StringEscapeUtils.unescapeHtml(policyInsert.getNplr_query()));
		return ncPolicyLinkMapper.insert_srcIp(policyInsert);
	}

	public int insert_dstIp(NcPolicyLink policyInsert) {
		policyInsert.setNplr_rx_query(StringEscapeUtils.unescapeHtml(policyInsert.getNplr_rx_query()));
		return ncPolicyLinkMapper.insert_dstIp(policyInsert);
	}

	public int policy_service_delete(int npl_seq) {
		return ncPolicyLinkMapper.policy_service_delete(npl_seq);
	}

	public int policy_route_delete(int npl_seq) {
		return ncPolicyLinkMapper.policy_route_delete(npl_seq);
	}

	public int update_policylink(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.update_policylink(policyInsert);
	}

	public int update_srcIp(NcPolicyLink policyInsert) {
		policyInsert.setNplr_query(StringEscapeUtils.unescapeHtml(policyInsert.getNplr_query()));
		return ncPolicyLinkMapper.update_srcIp(policyInsert);
	}

	public int update_dstIp(NcPolicyLink policyInsert) {
		policyInsert.setNplr_rx_query(StringEscapeUtils.unescapeHtml(policyInsert.getNplr_rx_query()));
		return ncPolicyLinkMapper.update_dstIp(policyInsert);
	}

	public int insert_fileSend(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.insert_fileSend(policyInsert);
	}

	public int insert_fileExcept(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.insert_fileExcept(policyInsert);
	}

	public int insert_fileTx(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.insert_fileTx(policyInsert);
	}

	public int insert_fileRx(NcPolicyLink policyInsert) {
		// TODO Auto-generated method stub
		return ncPolicyLinkMapper.insert_fileRx(policyInsert);
	}

	public int delete_fileSend(int npl_seq) {
		return ncPolicyLinkMapper.delete_fileSend(npl_seq);
	}

	public int delete_fileExcept(int npl_seq) {
		return ncPolicyLinkMapper.delete_fileExcept(npl_seq);
	}

	public int delete_fileTx(int npl_seq) {
		return ncPolicyLinkMapper.delete_fileTx(npl_seq);
	}

	public int delete_fileRx(int npl_seq) {
		// TODO Auto-generated method stub
		return ncPolicyLinkMapper.delete_fileRx(npl_seq);
	}

	public NcPolicyLink getfileTx(int npl_seq) {
		return ncPolicyLinkMapper.getfileTx(npl_seq);
	}

	public NcPolicyLink getfileRx(int npl_seq) {
		return ncPolicyLinkMapper.getfileRx(npl_seq);
	}

	public NcPolicyLink getfileSend(int npl_seq) {
		return ncPolicyLinkMapper.getfileSend(npl_seq);
	}

	public NcPolicyLink getfileExcept(int npl_seq) {
		return ncPolicyLinkMapper.getfileExcept(npl_seq);
	}

	public int update_fileSend(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.update_fileSend(policyInsert);
	}

	public int update_fileExcept(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.update_fileExcept(policyInsert);
	}

	public int update_fileTx(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.update_fileTx(policyInsert);
	}

	public int update_fileRx(NcPolicyLink policyInsert) {
		return ncPolicyLinkMapper.update_fileRx(policyInsert);
	}

	public int policy_use_change(int npl_use_yn, int npl_seq) {
		return ncPolicyLinkMapper.policy_use_change(npl_use_yn, npl_seq);
	}

	public NcPolicyLink getctName(int npl_tx_nts_seq) {
		return ncPolicyLinkMapper.getctName2(npl_tx_nts_seq);
	}

	public NcPolicyLink getstName2(int npl_rx_nts_seq) {
		return ncPolicyLinkMapper.getstName2(npl_rx_nts_seq);
	}

	public NcPolicyLink getctName2(int npl_rx_nts_seq) {
		return ncPolicyLinkMapper.getctName2(npl_rx_nts_seq);
	}

	public NcPolicyLink getPolicyServiceBySeq(int nlp_seq) {
		return ncPolicyLinkMapper.getPolicyServiceBySeq(nlp_seq);
	}

	public NcPolicyLink getPolicyr1(int npl_seq) {
		return ncPolicyLinkMapper.getPolicyr1(npl_seq);
	}

	public NcPolicyLink getPolicyr2(int npl_seq) {
		return ncPolicyLinkMapper.getPolicyr2(npl_seq);
	}

	public int tx_password_update(int seq, String pw, String salt, int div) {
		String encodePassword = pw; //passwordEncoder.encodePassword(pw, salt);
		return ncPolicyLinkMapper.tx_password_update(seq,encodePassword,salt,div);
	}

	public NcPolicyLink getPolicySalt(int npl_seq) {
		return ncPolicyLinkMapper.getPolicySalt(npl_seq);
	}

	public int rx_password_update(int seq, String pw, String salt, int div) {
		String encodePassword = pw; // passwordEncoder.encodePassword(pw, salt);
		return ncPolicyLinkMapper.rx_password_update(seq,encodePassword,salt,div);
	}

	public NcPolicyLink getPolicySalt1(int npl_seq) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<NcPolicyLink> getstName3() {
		return ncPolicyLinkMapper.getstName3();
	}

	// OPC 태그 입력
	public int insertOpcTag(int npl_no, String not_tx_tag, String not_rx_tag, int not_type) {
		return ncPolicyLinkMapper.insertOpcTag(npl_no, not_tx_tag, not_rx_tag, not_type);
	}
	public int deleteOpcTag(int npl_no) {
		return ncPolicyLinkMapper.deleteOpcTag(npl_no);
	}

	public int updateOpcTag(int npl_no,String not_tx_tag, String not_rx_tag, int not_type) {
		return ncPolicyLinkMapper.updateOpcTag(npl_no, not_tx_tag, not_rx_tag, not_type);
		
	}

	public ArrayList<NcOpcTag> getopcTagList(int npl_no) {
		// TODO Auto-generated method stub
		return ncPolicyLinkMapper.getopcTagList(npl_no);
	}
}