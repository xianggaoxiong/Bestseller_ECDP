package com.bestseller.poi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.poi.entity.CsStaff;
import com.bestseller.poi.entity.TrafficSource;
import com.bestseller.poi.mapper.CsStaffMapper;
import com.bestseller.poi.mapper.TrafficSourceMapper;

/** 
* @author  xgx@bestseller.com.cn 
* @date 创建时间：2016年3月8日 下午2:45:27 
* @version 1.0   
*/
@Service
public class CTService {
	@Autowired
	private TrafficSourceMapper tsMapper;
	@Autowired
	private CsStaffMapper csStaffMapper;
	@Transactional
	public boolean upload(File file) {
		InputStream is=null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Workbook wb=null;
		try {
			wb = WorkbookFactory.create(is);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 2. 得到 Sheet 对象
		Sheet sheet = null;
		sheet = wb.getSheet("Traffic Source");
		if(sheet!=null){
			// 3. 解析数据: 解析每一行, 每一个单元格.
			// 把一行转为一个 TrafficSource 对象, 把整个 Excel 文档解析为一个 TrafficSource 的集合
			List<TrafficSource> tsess = new ArrayList<>();
			parseToTSList(sheet, tsess);
			// 4. 批量插入
			TrafficSource ts=null;
			for(int i=0;i<tsess.size();i++){
				ts=tsess.get(i);
				if(tsMapper.getById(ts).size()>0){
					tsMapper.deleteAll(ts);
					continue;
				}
			}
			
			if(tsess.size()>500){
				List<TrafficSource> tses=new LinkedList<TrafficSource>();
				for(int i=0;i<tsess.size();i++){
					tses.add(tsess.get(i));
					if(i%500==0){
						tsMapper.batchSave(tses);
						tses.clear();
					}
					if(i==tsess.size()-1){
						tsMapper.batchSave(tses);
					}
				}
			}else{
				tsMapper.batchSave(tsess);
			}
			return true;
		}
		
		
		
		sheet=wb.getSheet("CS Staff Performance");
		if(sheet!=null){
			// 3. 解析数据: 解析每一行, 每一个单元格.
			// 把一行转为一个 CsStaff 对象, 把整个 Excel 文档解析为一个 CsStaff 的集合
			List<CsStaff> csStaffess = new ArrayList<>();
			parseToCsStaffList(sheet, csStaffess);
			// 4. 批量插入
			CsStaff csStaff=null;
			for(int i=0;i<csStaffess.size();i++){
				csStaff=csStaffess.get(i);
				if(csStaffMapper.getById(csStaff).size()>0){
					csStaffMapper.deleteAll(csStaff);
					continue;
				}
			}
			
			if(csStaffess.size()>500){
				List<CsStaff> csStaffes = new LinkedList<CsStaff>();
				for(int i=0;i<csStaffess.size();i++){
					csStaffes.add(csStaffess.get(i));
					if(i%500==0){
						csStaffMapper.batchSave(csStaffes);
						csStaffes.clear();
					}
					if(i==csStaffess.size()-1){
						csStaffMapper.batchSave(csStaffes);
					}
				}
			}else{
				csStaffMapper.batchSave(csStaffess);
			}
			return true;
		}
		
		return false;
	}

	
	//表CS Staff Performance的解析
	private void parseToCsStaffList(Sheet sheet, List<CsStaff> csStaffes) {
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			CsStaff csStaff = parseToCsStaff(row);
			if (csStaff != null&&csStaff.getShopCode()!=null) {
				csStaffes.add(csStaff);
			}
		}
	}
	private CsStaff parseToCsStaff(Row row) {
		CsStaff csStaff=null;
		Cell cell = row.getCell(0);
		String dayId=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dayId = getCellValue(cell);
		}else{
			throw new NumberFormatException("日期为空!");
		}
		
		cell = row.getCell(1);
		String shopCode=null;
		if(cell!=null&&getCellValue(cell)!=null){
			shopCode = getCellValue(cell);
		}else{
			throw new NumberFormatException("店铺代码为空!");
		}
		
		cell = row.getCell(2);
		String preAfter=null;
		if(cell!=null&&getCellValue(cell)!=null){
			preAfter = getCellValue(cell);
		}
		
		cell = row.getCell(3);
		String wwNo=null;
		if(cell!=null&&getCellValue(cell)!=null){
			wwNo = getCellValue(cell);
		}
		
		cell = row.getCell(4);
		String staffName=null;
		if(cell!=null&&getCellValue(cell)!=null){
			staffName = getCellValue(cell);
		}
		
		cell = row.getCell(5);
		Double vovaConsulation=null;
		if(cell!=null&&getCellValue(cell)!=null){
			vovaConsulation = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(6);
		Long consulation=null;
		if(cell!=null&&getCellValue(cell)!=null){
			consulation = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(7);
		Long reception=null;
		if(cell!=null&&getCellValue(cell)!=null){
			reception = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(8);
		Double salesValue=null;
		if(cell!=null&&getCellValue(cell)!=null){
			salesValue = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(9);
		Double avgResponse=null;
		if(cell!=null&&getCellValue(cell)!=null){
			avgResponse = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(10);
		Double basketValue=null;
		if(cell!=null&&getCellValue(cell)!=null){
			basketValue = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(11);
		Long basketSize=null;
		if(cell!=null&&getCellValue(cell)!=null){
			basketSize = (long) Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(12);
		Double satisfaction=null;
		if(cell!=null&&getCellValue(cell)!=null){
			satisfaction = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(13);
		Double sroEvaluation=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sroEvaluation = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(14);
		String arTime=null;
		if(cell!=null&&getCellValue(cell)!=null){
			arTime = getCellValue(cell);
		}
		
		cell = row.getCell(15);
		Double artSecond=null;
		if(cell!=null&&getCellValue(cell)!=null){
			artSecond = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(16);
		Double vopRate=null;
		if(cell!=null&&getCellValue(cell)!=null){
			vopRate = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(17);
		Double returnValue=null;
		if(cell!=null&&getCellValue(cell)!=null){
			returnValue = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(18);
		String onlineTime=null;
		if(cell!=null&&getCellValue(cell)!=null){
			onlineTime = getCellValue(cell);
		}
		
		cell = row.getCell(19);
		Double otSecond=null;
		if(cell!=null&&getCellValue(cell)!=null){
			otSecond = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(20);
		Double qaRate=null;
		if(cell!=null&&getCellValue(cell)!=null){
			qaRate = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(21);
		Double wwrr=null;
		if(cell!=null&&getCellValue(cell)!=null){
			wwrr = Double.parseDouble(getCellValue(cell)+0);
		}
		
		cell = row.getCell(22);
		Long notReply=null;
		if(cell!=null&&getCellValue(cell)!=null){
			notReply = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(23);
		Double remarkA=null;
		if(cell!=null&&getCellValue(cell)!=null){
			remarkA = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(24);
		Double remarkB=null;
		if(cell!=null&&getCellValue(cell)!=null){
			remarkB = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(25);
		String remarkC=null;
		if(cell!=null&&getCellValue(cell)!=null){
			remarkC = getCellValue(cell);
		}
		csStaff=new CsStaff(dayId, shopCode, preAfter, wwNo, staffName, vovaConsulation, consulation, reception, salesValue, avgResponse, basketValue, basketSize, satisfaction, sroEvaluation, arTime, artSecond, vopRate, returnValue, onlineTime, otSecond, qaRate, wwrr, notReply, remarkA, remarkB, remarkC);
		return csStaff;
	}


	//表Traffic Source的解析
	private void parseToTSList(Sheet sheet, List<TrafficSource> tses) {
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			TrafficSource ts = parseToTSS(row);
			if (ts != null&&ts.getShopCode()!=null) {
				tses.add(ts);
			}
		}
	}
	
	private TrafficSource parseToTSS(Row row) {
		TrafficSource ts=null;
		Cell cell = row.getCell(0);
		String shopCode=null;
		if(cell!=null&&getCellValue(cell)!=null){
			shopCode = getCellValue(cell);
		}else{
			throw new NumberFormatException("店铺代码为空!");
		}
		
		cell = row.getCell(1);
		String dayId=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dayId = getCellValue(cell);
		}else{
			throw new NumberFormatException("日期为空!");
		}
		
		cell = row.getCell(2);
		String itsdNum=null;
		if(cell!=null&&getCellValue(cell)!=null){
			itsdNum = getCellValue(cell);
		}
		
		cell = row.getCell(3);
		String itsd=null;
		if(cell!=null&&getCellValue(cell)!=null){
			itsd = getCellValue(cell);
		}
		
		cell = row.getCell(4);
		String trafficSourse=null;
		if(cell!=null&&getCellValue(cell)!=null){
			trafficSourse = getCellValue(cell);
		}
		
		
		cell = row.getCell(5);
		String tsDetail=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tsDetail = getCellValue(cell);
		}
		
		
		cell = row.getCell(6);
		Long uv=null;
		if(cell!=null&&getCellValue(cell)!=null){
			uv = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(7);
		Long pv=null;
		if(cell!=null&&getCellValue(cell)!=null){
			pv = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(8);
		Integer bookMark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			bookMark =(int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(9);
		Integer sCart=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sCart =(int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(10);
		Double lossRate=null;
		if(cell!=null&&getCellValue(cell)!=null){
			lossRate =Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(11);
		Double coValue=null;
		if(cell!=null&&getCellValue(cell)!=null){
			coValue =Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(12);
		Long ccQty=null;
		if(cell!=null&&getCellValue(cell)!=null){
			ccQty = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(13);
		Double salesValue=null;
		if(cell!=null&&getCellValue(cell)!=null){
			salesValue =Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(14);
		Integer pcQty=null;
		if(cell!=null&&getCellValue(cell)!=null){
			pcQty =(int) Double.parseDouble(getCellValue(cell));
		}
		
		ts=new TrafficSource(shopCode, dayId, itsdNum, itsd, trafficSourse, tsDetail,uv, pv, bookMark, sCart, lossRate, coValue, ccQty, salesValue, pcQty);
		return ts;
	}
	
	
	
	//解析单元格 的类型
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				return dateFormat.format(date)+"";
			} else {
				return cell.getNumericCellValue() + "";
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula() + "";
		}
		return null;
	}
}
