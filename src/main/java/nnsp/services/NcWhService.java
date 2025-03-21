package nnsp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nnsp.common.Config;
import nnsp.vo.NcWhList;
import nnsp.vo.NdrPs;
import nnsp.xmlmappers.NcWhXmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NcWhService {

	@Autowired
	private NcCommonService ncCommonService;
	@Autowired
	private NcWhXmlMapper ncWhXmlMapper;
	
	
	public ArrayList<NdrPs> getPacketList_grid(NdrPs ndrPs){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Config config = Config.getInstance();
		String dbName = config.getProperty("db.ics");
		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		String yyyyMMddHHmmss = nnsp.util.StringUtil.getStrDate(ps_stime);
		int tblDate = Integer.parseInt(yyyyMMddHHmmss.substring( 0, Integer.parseInt(config.getProperty("tbl.packet.statistics.no"))));
		String tblName = config.getProperty("tbl.packet.statistics")+tblDate;
		
		map.put("ps_table", tblDate); //  조회 테이블 명 날짜만
		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		
		if(this.ncCommonService.getTableCheck(dbName, tblName)<1){ // 테이블 유무 판단 maria
			return new ArrayList<NdrPs>();
		}
		
		return this.ncWhXmlMapper.getPacketList_grid(map);
	}
	
	public ArrayList<NdrPs> getPacketStatList(NdrPs ndrPs, Map<String, Object> loadOptions){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ps_stime = ndrPs.getPs_strc_stime();
		String ps_etime = ndrPs.getPs_strc_etime();
		
		map.put("ps_stime", ps_stime); //  조회 시작시간
		map.put("ps_etime", ps_etime); //  조회 끝시간
		
		 // 페이징 옵션 추가
	    map.put("skip", loadOptions.get("skip")); // 시작 위치
	    map.put("take", loadOptions.get("take")); // 조회 데이터 수
	   
	    // 정렬 옵션 변환 및 추가
	    if (loadOptions.containsKey("sort") && loadOptions.get("sort") != null) {
	        String sortSql = parseSortOptions(loadOptions.get("sort"));
	        
	        if (!sortSql.isEmpty()) {
	            map.put("sort", sortSql);
	        }
	    }
	    
	    // 필터 옵션 변환 및 추가
	    if (loadOptions.containsKey("filter") && loadOptions.get("filter") != null) {
	        String filterSql = parseFilterOptions(loadOptions.get("filter"), new Object());

	        if (!filterSql.isEmpty()) {
	            map.put("filter", filterSql);
	        }
	    }
		
		return this.ncWhXmlMapper.getPacketStatList(map);
	}
	
	public int getPacketStatTotalCount(NdrPs ndrPs, Map<String, Object> loadOptions) {
		// 요청 데이터를 담을 Map 생성
	    HashMap<String, Object> map = new HashMap<>();
	    String ps_stime = ndrPs.getPs_strc_stime();	 // 시작 시간
	    String ps_etime = ndrPs.getPs_strc_etime(); // 종료 시간

	    map.put("ps_stime", ps_stime); // 조회 시작 시간
	    map.put("ps_etime", ps_etime); // 조회 종료 시간

	    // 필터 옵션 변환 및 추가
	    if (loadOptions.containsKey("filter") && loadOptions.get("filter") != null) {
	        String filterSql = parseFilterOptions(loadOptions.get("filter"), new Object());

	        if (!filterSql.isEmpty()) {
	            map.put("filter", filterSql);
	        }
	    }
	    
	    return ncWhXmlMapper.getPacketStatTotalCount(map);
	}
	
	//SelectGroupList
	public ArrayList<NcWhList> selectGroupWhiteList(Map<String, Object> loadOptions) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 페이징 옵션 추가
	    map.put("skip", loadOptions.get("skip")); // 시작 위치
	    map.put("take", loadOptions.get("take")); // 조회 데이터 수

	    // 정렬 옵션 변환 및 추가
	    if (loadOptions.containsKey("sort") && loadOptions.get("sort") != null) {
	        String sortSql = parseSortOptions(loadOptions.get("sort"));
	        
	        if (!sortSql.isEmpty()) {
	            map.put("sort", sortSql);
	        }
	    }
	    
	    // 필터 옵션 변환 및 추가
	    if (loadOptions.containsKey("filter") && loadOptions.get("filter") != null) {
	    	String filterSql = parseFilterOptions(loadOptions.get("filter"), loadOptions.get("groupByField"));
	        
	        if (!filterSql.isEmpty()) {
	            map.put("filter", filterSql);
	        }
	    }

		ArrayList<NcWhList> resultGroupWhiteList = new ArrayList<NcWhList>();
		ArrayList<NcWhList> whiteList = this.ncWhXmlMapper.selectGroupWhiteList(map);
		
		for(int i = 0; i < whiteList.size(); i++) {
			NcWhList white = whiteList.get(i);
			
			if (white.getWl_src_ip() != null && white.getWl_src_eip() != null) {
	            white.setWl_src_ip(white.getWl_src_ip() + " ~ " + white.getWl_src_eip());
	        }
			
			if (white.getWl_dst_ip() != null && white.getWl_dst_eip() != null) {
	            white.setWl_dst_ip(white.getWl_dst_ip() + " ~ " + white.getWl_dst_eip());
	        }
			
			resultGroupWhiteList.add(white);
		}
		
		if(resultGroupWhiteList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return resultGroupWhiteList;
	}
	
	public int getGroupWhiteListCount(Map<String, Object> loadOptions) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 필터 옵션 변환 및 추가
	    if (loadOptions.containsKey("filter") && loadOptions.get("filter") != null) {
	        String filterSql = parseFilterOptions(loadOptions.get("filter"), new Object());

	        if (!filterSql.isEmpty()) {
	            map.put("filter", filterSql);
	        }
	    }
	    
	    if (loadOptions.containsKey("nglGroupName") && loadOptions.get("nglGroupName") != null) {
	    	map.put("nglGroupName", loadOptions.get("nglGroupName"));
	    }
		
		int totalCount = this.ncWhXmlMapper.getGroupWhiteListCount(map);
		
		if(totalCount < 1){
			totalCount = 0;
		}
		
		return totalCount;
	}
	
	/**
	 * JSON 형태의 정렬 옵션을 SQL `ORDER BY` 구문으로 변환
	 */
	private String parseSortOptions(Object sortOptions) {
	    if (sortOptions == null || sortOptions.toString().isEmpty()) {
	        return ""; // 정렬 옵션이 없을 경우 빈 문자열 반환
	    }

	    try {
	        // Sort JSON 문자열을 안전하게 처리
	        String sortJson = sortOptions.toString();

	        // HTML 인코딩 제거
	        sortJson = sortJson.replace("&quot;", "\"");

	        // JSON 배열 파싱
	        List<Map<String, Object>> sortList = new ObjectMapper().readValue(sortJson, new TypeReference<List<Map<String, Object>>>() {});
	        StringBuilder sortSql = new StringBuilder();

	        for (Map<String, Object> sort : sortList) {
	            String selector = (String) sort.get("selector");
	            boolean isDesc = (Boolean) sort.getOrDefault("desc", false);

	            if (selector != null && !selector.isEmpty()) {
	            	if (selector.contains("strWlId")) {
	                    selector = "WL_ID"; // 완전히 변환
	                }
	            	if (selector.contains("strPsDetect")) {
	                    selector = "PS_DETECT"; // 완전히 변환
	                }
	            	
	                if (sortSql.length() > 0) {
	                    sortSql.append(", "); // 다중 정렬 구문에 쉼표 추가
	                }
	                sortSql.append(selector).append(isDesc ? " DESC" : " ASC");
	            }
	        }

	        return sortSql.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ""; // 파싱 실패 시 빈 문자열 반환
	    }
	}

	/**
	 * JSON 형태의 필터 옵션을 SQL `WHERE` 조건으로 변환
	 */
	private String parseFilterOptions(Object filterOptions, Object groupByField) {
	    if (filterOptions == null || filterOptions.toString().isEmpty()) {
	        return ""; // 필터 옵션이 없을 경우 빈 문자열 반환
	    }
	    
	    try {
	        // 필터 JSON 출력
	        String filterJson = filterOptions.toString();
	        String groupFieldJson = groupByField != null ? groupByField.toString() : "[]";

	        // HTML 인코딩 제거
	        filterJson = filterJson.replace("&quot;", "\"");
	        groupFieldJson = groupFieldJson.replace("&quot;", "\"");
	        
	        // JSON 배열 파싱
	        List<Object> filterList = new ObjectMapper().readValue(filterJson, new TypeReference<List<Object>>() {});
	        String groupField = null;
	        if (groupFieldJson.startsWith("[") && groupFieldJson.endsWith("]")) {
	            List<String> groupFieldList = new ObjectMapper().readValue(groupFieldJson, new TypeReference<List<String>>() {});
	            groupField = groupFieldList.isEmpty() ? null : groupFieldList.get(0); // 첫 번째 값 추출
	        } else {
	            groupField = groupByField.toString(); // JSON 형식이 아닌 경우 그대로 사용
	        }
	        // SQL 변환
	        if (filterList.get(0) instanceof String && filterList.size() == 3) {
	            // 단일 필터 형식이면 buildSingleFilterSql로 처리
	            return buildSingleFilterSql(filterList);
	        } else {
	            // 부정 조건이나 다중 필터는 buildMultiFilterSql로 처리
	            return buildMultiFilterSql(filterList, groupField);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ""; // 파싱 실패 시 빈 문자열 반환
	    }
	}

	// 단일 필터 처리 메서드
	private String buildSingleFilterSql(List<Object> filter) {
	    if (filter.size() != 3) {
	        throw new IllegalArgumentException("Invalid single filter format: " + filter);
	    }

	    String field = filter.get(0).toString();
	    String operator = filter.get(1).toString();
	    Object value = filter.get(2);

	    if (value == null) {
	        return ""; // 값이 없으면 빈 문자열 반환
	    }

	    StringBuilder filterSql = new StringBuilder();
	    if ("contains".equalsIgnoreCase(operator)) {
	        filterSql.append(field)
	                 .append(" LIKE '%")
	                 .append(value.toString().replace("'", "''"))
	                 .append("%'");
	    } else {
	        filterSql.append(field).append(" ").append(operator).append(" ");
	        if (value instanceof String) {
	            filterSql.append("'").append(value).append("'");
	        } else {
	            filterSql.append(value);
	        }
	    }

	    System.out.println("********** Single filter SQL: " + filterSql.toString().trim());
	    return filterSql.toString().trim();
	}

	// 다중 필터 처리 메서드
	@SuppressWarnings("unchecked")
	private String buildMultiFilterSql(List<Object> filterList, String groupField) {
	    StringBuilder filterSql = new StringBuilder();

	    try {
	        System.out.println("****** filterList : " + filterList);
	        for (int i = 0; i < filterList.size(); i++) {
	            Object element = filterList.get(i);
	            System.out.println("********** Processing element " + i + ": " + element);

	            // 부정 조건 처리
	            if ("!".equals(element)) {
	                System.out.println("********** Negation (!) detected");

	                // 다음 요소가 존재하지 않거나 유효하지 않다면 예외 처리
	                if (i + 1 >= filterList.size()) {
	                    throw new IllegalArgumentException("Negation operator '!' must be followed by a valid filter: " + filterList);
	                }

	                Object negatedFilter = filterList.get(++i); // 다음 요소 가져오기

	                if (negatedFilter instanceof List) {
	                    // 내부 조건 처리
	                    List<Object> negatedFilterList = (List<Object>) negatedFilter;

	                    String nestedSql;
	                    if (negatedFilterList.size() == 3 && negatedFilterList.get(0) instanceof String) {
	                        // 단일 필터로 처리
	                        nestedSql = buildSingleFilterSql(negatedFilterList);
	                    } else {
	                        // 다중 필터로 처리
	                        nestedSql = buildMultiFilterSql(negatedFilterList, groupField);
	                    }

	                    System.out.println("********** Nested SQL for !: " + nestedSql);

	                    if (!nestedSql.isEmpty()) {
	                        if (filterSql.length() > 0) {
	                            filterSql.append(" AND ");
	                        }
	                        filterSql.append("NOT (").append(nestedSql).append(")");
	                    }
	                } else {
	                    throw new IllegalArgumentException("Invalid negated filter format: " + negatedFilter);
	                }

	                // 다음 요소로 진행
	                continue;
	            }

	            // 리스트 처리
	            if (element instanceof List) {
	                List<Object> filterItem = (List<Object>) element;
	                System.out.println("********** Processing as List: " + filterItem);

	                if (filterItem.size() == 3 && filterItem.get(0) instanceof String) {
	                    // 단일 필터 처리
	                    String singleFilterSql = buildSingleFilterSql(filterItem);
	                    System.out.println("********** Single filter SQL: " + singleFilterSql);

	                    if (!singleFilterSql.isEmpty()) {
	                        if (filterSql.length() > 0 && !filterSql.toString().endsWith("AND ") && !filterSql.toString().endsWith("OR ")) {
	                            filterSql.append(" AND ");
	                        }
	                        filterSql.append(singleFilterSql);
	                    }
	                } else {
	                    // 중첩된 다중 필터 처리
	                    String nestedSql = buildMultiFilterSql(filterItem, groupField);
	                    System.out.println("********** Nested filter SQL: " + nestedSql);

	                    if (!nestedSql.isEmpty()) {
	                        if (filterSql.length() > 0 && !filterSql.toString().endsWith("AND ") && !filterSql.toString().endsWith("OR ")) {
	                            filterSql.append(" AND ");
	                        }
	                        filterSql.append("(").append(nestedSql).append(")");
	                    }
	                }
	            } else if (element instanceof String) {
	                // 논리 연산자 처리 (AND, OR)
	                String logicOperator = element.toString().toUpperCase();
	                if ("AND".equals(logicOperator) || "OR".equals(logicOperator)) {
	                    System.out.println("********** Logical operator detected: " + logicOperator);
	                    filterSql.append(" ").append(logicOperator).append(" ");
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }

	    System.out.println("********** Final filterSql: " + filterSql.toString().trim());
	    return filterSql.toString().trim();
	}
	
	public int registerGroupList(NcWhList ncWhList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ngl_group_name", ncWhList.getNgl_group_name());
		map.put("ngl_group_desc", ncWhList.getNgl_group_desc());
		
		return this.ncWhXmlMapper.registerGroupList(map);
	}
	
	//화이트 리스트 등록
	public int registerWhiteList(NcWhList ncWhList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("wl_name", ncWhList.getWl_name());
		map.put("wl_npl_id", ncWhList.getWl_npl_id());
		map.put("wl_npl_name", ncWhList.getWl_npl_name());
		map.put("wl_nplv_id", ncWhList.getWl_nplv_id());
		map.put("wl_nplv_name", ncWhList.getWl_nplv_name());
		
//		map.put("wl_src_host_type", ncWhList.getWl_src_host_type());
		map.put("wl_src_ip", ncWhList.getWl_src_ip());
//		if(ncWhList.getWl_src_eip() != null && !ncWhList.getWl_src_eip().equals("")) {
//			map.put("wl_src_eip", ncWhList.getWl_src_eip());
//		}
		map.put("wl_src_port_type", ncWhList.getWl_src_port_type());
		map.put("wl_src_port", ncWhList.getWl_src_port());
		
//		map.put("wl_dst_host_type", ncWhList.getWl_dst_host_type());
		map.put("wl_dst_ip", ncWhList.getWl_dst_ip());
//		if(ncWhList.getWl_dst_eip() != null && !ncWhList.getWl_dst_eip().equals("")) {
//			map.put("wl_dst_eip", ncWhList.getWl_dst_eip());
//		}
		map.put("wl_dst_port_type", ncWhList.getWl_dst_port_type());
		map.put("wl_dst_port", ncWhList.getWl_dst_port());
		
		return this.ncWhXmlMapper.registerWhiteList(map);
	}
	
	//SelectProtocolList
	public ArrayList<NcWhList> getWhiteList() {
		ArrayList<NcWhList> resultGroupWhiteList = new ArrayList<NcWhList>();
		ArrayList<NcWhList> whiteList = this.ncWhXmlMapper.getWhiteList();
		
		for(int i = 0; i < whiteList.size(); i++) {
			NcWhList white = whiteList.get(i);
			
//			if (white.getWl_src_ip() != null && white.getWl_src_eip() != null) {
//	            white.setWl_src_ip(white.getWl_src_ip() + " ~ " + white.getWl_src_eip());
//	        }
//			
//			if (white.getWl_dst_ip() != null && white.getWl_dst_eip() != null) {
//	            white.setWl_dst_ip(white.getWl_dst_ip() + " ~ " + white.getWl_dst_eip());
//	        }
			
			resultGroupWhiteList.add(white);
		}
		
		if(resultGroupWhiteList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return resultGroupWhiteList;
	}
	
	public int getWhiteListCount() {
		return ncWhXmlMapper.getWhiteListCount();
	}
	
	public int updateWhiteList(NcWhList ncWhList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("wl_id", ncWhList.getWl_id());
		map.put("wl_name", ncWhList.getWl_name());
		map.put("wl_npl_id", ncWhList.getWl_npl_id());
		map.put("wl_npl_name", ncWhList.getWl_npl_name());
		map.put("wl_nplv_id", ncWhList.getWl_nplv_id());
		map.put("wl_nplv_name", ncWhList.getWl_nplv_name());
		
//		map.put("wl_src_host_type", ncWhList.getWl_src_host_type());
		map.put("wl_src_ip", ncWhList.getWl_src_ip());
//		if(ncWhList.getWl_src_eip() != null && !ncWhList.getWl_src_eip().equals("")) {
//			map.put("wl_src_eip", ncWhList.getWl_src_eip());
//		}
		map.put("wl_src_port_type", ncWhList.getWl_src_port_type());
		map.put("wl_src_port", ncWhList.getWl_src_port());
		
//		map.put("wl_dst_host_type", ncWhList.getWl_dst_host_type());
		map.put("wl_dst_ip", ncWhList.getWl_dst_ip());
//		if(ncWhList.getWl_dst_eip() != null && !ncWhList.getWl_dst_eip().equals("")) {
//			map.put("wl_dst_eip", ncWhList.getWl_dst_eip());
//		}
		map.put("wl_dst_port_type", ncWhList.getWl_dst_port_type());
		map.put("wl_dst_port", ncWhList.getWl_dst_port());
		
		return this.ncWhXmlMapper.updateWhiteList(map);
	}
	
	public int updateWlUseYn(long wl_id, int wl_use_yn) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("wl_id", wl_id);
		map.put("wl_use_yn", wl_use_yn);
		
		return this.ncWhXmlMapper.updateWlUseYn(map);
	}
	
	//SelectProtocolList
	public ArrayList<NcWhList> selectProtocolList() {
		ArrayList<NcWhList> protocolList = this.ncWhXmlMapper.selectProtocolList();
		
		if(protocolList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return protocolList;
	}
	
	//SelectPolicyLevelList
	public ArrayList<NcWhList> selectPolicyLevelList() {
		ArrayList<NcWhList> policyLevelList = this.ncWhXmlMapper.selectPolicyLevelList();
		
		if(policyLevelList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return policyLevelList;
	}
	
	//SelectGroupList
	public ArrayList<NcWhList> selectGroupList(NcWhList ncWhList) {
		int ngl_id = ncWhList.getNgl_id();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ngl_id > 0) {
			map.put("ngl_id", ngl_id);
		}
		
		ArrayList<NcWhList> groupList = this.ncWhXmlMapper.selectGroupList(map);
		
		if(groupList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return groupList;
	}
	
	//SelectGroupList
	public NcWhList selectGroupInfo(int ngl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ngl_id > 0) {
			map.put("ngl_id", ngl_id);
		}
		
		NcWhList groupInfo = this.ncWhXmlMapper.selectGroupInfo(map);
		
		return groupInfo;
	}
	
	//SelectGroupList
	public ArrayList<NcWhList> selectLastGroupList() {
		ArrayList<NcWhList> lastGroupList = this.ncWhXmlMapper.selectLastGroupList();
		
		if(lastGroupList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return lastGroupList;
	}
	
	public int selectIsDuplicateWhiteList(NcWhList ncWhList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("wl_name", ncWhList.getWl_name());
		map.put("wl_npl_id", ncWhList.getWl_npl_id());
		map.put("wl_nplv_id", ncWhList.getWl_nplv_id());
		
//		map.put("wl_src_host_type", ncWhList.getWl_src_host_type());
		map.put("wl_src_ip", ncWhList.getWl_src_ip());
//		if(("2").equals(ncWhList.getWl_src_host_type())) {
//			map.put("wl_src_eip", ncWhList.getWl_src_eip());
//		}
		map.put("wl_src_port_type", ncWhList.getWl_src_port_type());
		map.put("wl_src_port", ncWhList.getWl_src_port());
		
//		map.put("wl_dst_host_type", ncWhList.getWl_dst_host_type());
		map.put("wl_dst_ip", ncWhList.getWl_dst_ip());
//		if(("2").equals(ncWhList.getWl_dst_host_type())) {
//			map.put("wl_dst_eip", ncWhList.getWl_dst_eip());
//		}
		map.put("wl_dst_port_type", ncWhList.getWl_dst_port_type());
		map.put("wl_dst_port", ncWhList.getWl_dst_port());
		
		return this.ncWhXmlMapper.selectIsDuplicateWhiteList(map);
	}
	
	public NdrPs selectPacketStatByPsId(NdrPs ndrPs) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ps_id", ndrPs.getPs_id());
		map.put("tblDate", ndrPs.getTblDate());
		
		return this.ncWhXmlMapper.selectPacketStatByPsId(map);
	}
	
	public NcWhList selectWhiteListByGroupName(String groupName) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(groupName != null && !groupName.equals(""))
			{map.put("ngl_group_name", groupName);}

		return this.ncWhXmlMapper.selectWhiteListByGroupName(map);
	}
	
	public int deleteWhiteList(int wl_ngl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(wl_ngl_id > 0) {
			map.put("wl_ngl_id", wl_ngl_id);
		}
		else {return 0;}
		
		return this.ncWhXmlMapper.deleteWhiteList(map);
	}
	
	public int deleteGroup(int ngl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ngl_id > 0) {
			map.put("ngl_id", ngl_id);
		}
		else {return 0;}
		
		return this.ncWhXmlMapper.deleteGroup(map);
	}
	
	public NcWhList selectCheckedWhiteList(long wl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(wl_id > 0)
			{map.put("wl_id", wl_id);}

		return this.ncWhXmlMapper.selectCheckedWhiteList(map);
	}
	
	public int deleteCheckedWhiteList(long wl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(wl_id > 0) {
			map.put("wl_id", wl_id);
		}
		else {return 0;}
		
		return this.ncWhXmlMapper.deleteCheckedWhiteList(map);
	}
	
	public ArrayList<NcWhList> selectWhiteLisyByNglId(int ngl_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(ngl_id > 0) {
			map.put("ngl_id", ngl_id);
		}
		
		ArrayList<NcWhList> groupList = this.ncWhXmlMapper.selectWhiteLisyByNglId(map);
		
		if(groupList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return groupList;
	}
	
	public ArrayList<NcWhList> selectHostType() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<NcWhList> hostTypeList = this.ncWhXmlMapper.selectHostType(map);
		
		if(hostTypeList.size() < 1){
			return new ArrayList<NcWhList>();
		}
		
		return hostTypeList;
	}
}