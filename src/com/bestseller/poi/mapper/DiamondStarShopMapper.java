package com.bestseller.poi.mapper;

import java.util.List;
import java.util.Map;

import com.bestseller.poi.entity.DiamondStarShop;

public interface DiamondStarShopMapper {
	void batchSave(Map<String,Object> map);
	
	List<DiamondStarShop> getById(Map<String,Object> map);
	
	void update(Map<String,Object> map);
	
	void deleteAll(Map<String,Object> map);
}
