package nnsp.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

//@Resource(name = "sqlSessionFactoryMariaDB")
public interface NcCommonMapper {
	
	
	String SELECT_TABLE_CHECK = "SELECT COUNT(*) FROM Information_schema.tables "
		+ "WHERE table_schema = #{db_name} "
		+ "AND table_name = #{table_name}";

	@Select(SELECT_TABLE_CHECK)
	int getTableCheck(Map<String, Object> param);
}