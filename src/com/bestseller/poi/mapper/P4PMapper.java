package com.bestseller.poi.mapper;

import java.util.List;
import java.util.Map;

import com.bestseller.poi.entity.P4P;


public interface P4PMapper {
	void batchSave(Map<String,Object> map);
	
	List<P4P> getById(Map<String,Object> map);
	
	void update(Map<String,Object> map);
	
	void deleteAll(Map<String,Object> map);
}
