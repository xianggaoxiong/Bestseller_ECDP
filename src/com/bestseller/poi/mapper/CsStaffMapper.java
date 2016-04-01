package com.bestseller.poi.mapper;

import java.util.List;

import com.bestseller.poi.entity.CsStaff;

public interface CsStaffMapper {
	void batchSave(List<CsStaff> csStaffes);
	
	List<CsStaff> getById(CsStaff csStaff);
	
	void deleteAll(CsStaff csStaff);
}
