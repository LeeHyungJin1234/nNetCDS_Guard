package nnsp.mappers;


import nnsp.vo.NcProduct;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcProductMapper {
	
	// 장비소개 정보 조회
	final String SELETE_PRODUCT = 
			" SELECT NCPD_NAME, NCPD_VERSION, NCPD_HW_MODEL, NCPD_MODEL, NCPD_SN, " + 
			" 	NCPD_TEL, NCPD_DATE, NCPD_DESC, NCPD_REG, NCPD_DIV, NCPD_KEY, NCPD_SETUP " + 
			" FROM NNC_CONF_PRODUCT ";
	// 장비소개 정보 수정
	final String UPDATE_PRODUCT = "UPDATE NNC_CONF_PRODUCT " 
			+ " SET NCPD_SN=#{ncpd_sn}, NCPD_SETUP=#{ncpd_setup} ";
	final String UPDATE_PRODUCT2 =
			"UPDATE NNC_CONF_PRODUCT " +
			"SET NCPD_TEL=#{ncpd_tel}, NCPD_DATE=#{ncpd_date}, NCPD_DESC=#{ncpd_desc}, NCPD_REG=now() ";
	// 장비소개 정보 등록
	final String INSERT_PRODUCT = 
			" INSERT INTO NNC_CONF_PRODUCT( " + 
			" 	NCPD_NAME, NCPD_VERSION, NCPD_MODEL, NCPD_SN, " + 
			" 	NCPD_TEL, NCPD_DATE, NCPD_DESC, NCPD_REG) " +  
			" VALUES( " + 
			" 	#{ncpd_name}, #{ncpd_version}, #{ncpd_model}, #{ncpd_sn}, " + 
			" 	#{ncpd_tel}, #{ncpd_date}, #{ncpd_desc}, now()) ";
	
	
	@Select(SELETE_PRODUCT)
    @Results(value = {
		@Result(property="ncpd_name", column="NCPD_NAME"),
		@Result(property="ncpd_version", column="NCPD_VERSION"),
		@Result(property="ncpd_hw_model", column="NCPD_HW_MODEL"),
		@Result(property="ncpd_model", column="NCPD_MODEL"),
		@Result(property="ncpd_sn", column="NCPD_SN"),
		@Result(property="ncpd_tel", column="NCPD_TEL"),
		@Result(property="ncpd_date", column="NCPD_DATE"),
		@Result(property="ncpd_desc", column="NCPD_DESC"),
		@Result(property="ncpd_reg", column="NCPD_REG"),
		@Result(property="ncpd_div", column="NCPD_DIV"),
		@Result(property="ncpd_key", column="NCPD_KEY"),
		@Result(property="ncpd_setup", column="NCPD_SETUP")
    })
    NcProduct getNcProduct();
		
	@Update(UPDATE_PRODUCT)
	int ncProduct_update(NcProduct ncProduct);

	@Update(UPDATE_PRODUCT2)
	int ncProduct_update2(NcProduct ncProduct);
	
	@Update(INSERT_PRODUCT)
	int ncProduct_insert(NcProduct ncProduct);
}