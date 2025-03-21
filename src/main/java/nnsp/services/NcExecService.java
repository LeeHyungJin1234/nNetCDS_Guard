package nnsp.services;

import java.util.ArrayList;
import java.util.HashMap;

import nnsp.xmlmappers.NcExecXmlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcExecService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcExecService.class);
	@Autowired
	private NcExecXmlMapper ncExecXmlMapper;
	
	public ArrayList<HashMap<String, Object>> select_exec(String param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sql_exec", param);
		return ncExecXmlMapper.select_exec(map);
    }
	public int insert_exec(String param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sql_exec", param);
		return ncExecXmlMapper.insert_exec(map);
    }
	public int update_exec(String param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sql_exec", param);
		return ncExecXmlMapper.update_exec(map);
    }
}