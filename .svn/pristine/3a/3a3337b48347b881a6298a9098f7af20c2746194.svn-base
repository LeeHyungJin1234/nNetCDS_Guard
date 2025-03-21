package nnsp.services;


import nnsp.mappers.NcProductMapper;
import nnsp.vo.NcProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcProductService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcProductService.class);
	@Autowired
	private NcProductMapper ncProductMapper;
	
	// 장비소개 정보 조회
	public NcProduct getNcProduct() {
		return ncProductMapper.getNcProduct();
    }
	
	// 장비소개 정보 수정
	public int ncProduct_update(NcProduct ncProduct) {
		return ncProductMapper.ncProduct_update(ncProduct);
    }
	// 초기 장비소개 정보 수정
	public int ncProduct_update2(NcProduct ncProduct) {
		return ncProductMapper.ncProduct_update2(ncProduct);
	}
	
	// 장비소개 정보 등록
	public int ncProduct_insert(NcProduct ncProduct) {
		return ncProductMapper.ncProduct_insert(ncProduct);
    }
}