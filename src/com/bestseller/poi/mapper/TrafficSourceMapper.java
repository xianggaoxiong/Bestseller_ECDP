package com.bestseller.poi.mapper;

import java.util.List;

import com.bestseller.poi.entity.TrafficSource;

public interface TrafficSourceMapper {
	void batchSave(List<TrafficSource> tses);
	
	List<TrafficSource> getById(TrafficSource ts);
	
	void deleteAll(TrafficSource ts);
}
