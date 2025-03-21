package nnsp.xmlmappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface NcExecXmlMapper {

	ArrayList<HashMap<String, Object>> select_exec(Map<String, Object> param);
	
	int insert_exec(Map<String, Object> param);
	
	int update_exec(Map<String, Object> param);
}
