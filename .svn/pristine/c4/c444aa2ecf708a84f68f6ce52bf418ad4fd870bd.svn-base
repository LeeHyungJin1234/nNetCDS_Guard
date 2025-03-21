package nnsp.services;

import java.util.HashMap;

import nnsp.mappers.NcCommonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcCommonService {

	@Autowired
	private NcCommonMapper ncCommonMapper; // 동적 SQL은 XMLMAPPER로 한다. MariaDB
	//  테이블 유무 체크
	public int getTableCheck(String dbName, String tblName) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("db_name", dbName);//조회 DB 명 설정
		map.put("table_name", tblName);//조회 테이블 명 설정

		return ncCommonMapper.getTableCheck(map);
	}
}