
package com.bestseller.poi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.poi.entity.DiamondStarShop;
import com.bestseller.poi.entity.P4P;
import com.bestseller.poi.mapper.DiamondStarShopMapper;
import com.bestseller.poi.mapper.P4PMapper;


/** 
* @author  xgx@bestseller.com.cn 
* @date 创建时间：2016年3月8日 下午2:44:49 
* @version 1.0  
*/
@Service
public class DinamondService {
	@Autowired
	private P4PMapper p4pMapper;
	@Autowired
	private DiamondStarShopMapper dssMapper;
	@Transactional
	public boolean upload(File file,String p4pName,String dianmondName) {
		Map<String,Object> map=new HashMap<>();
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
		sheet = wb.getSheet(p4pName);
		
		Sheet sheet2=null;
		sheet2=wb.getSheet(dianmondName);
		
		
		
		if(sheet!=null&&sheet2==null){
			// 3. 解析数据: 解析每一行, 每一个单元格.
			// 把一行转为一个 p4p 对象, 把整个 Excel 文档解析为一个 p4p 的集合
			// 若出现错误, 则把错误返回
			List<P4P> p4ps = new ArrayList<>();
			parseToP4PList(sheet, p4ps);
			// 4. 批量插入
			map.put("sheetName", p4pName);
			P4P p4p=null;
			for(int i=0;i<p4ps.size();i++){
				p4p=p4ps.get(i);
				map.put("p4p", p4p);
				if(p4pMapper.getById(map).size()>0){
					p4pMapper.deleteAll(map);
					continue;
				}
				else{
					map.remove("p4p");
				}
			}
			
			List<P4P> p4pes=new ArrayList<>();
			if(p4ps.size()>500){
				for(int i=0;i<p4ps.size();i++){
					p4pes.add(p4ps.get(i));
					if(i%500==0){
						map.put("p4ps", p4pes);
						p4pMapper.batchSave(map);
						p4pes.clear();
					}
					if(i==p4ps.size()-1){
						map.put("p4ps", p4pes);
						p4pMapper.batchSave(map);
					}
				}
			}else{
				map.put("p4ps", p4ps);
				p4pMapper.batchSave(map);
			}
			return true;
		}
		
		
		else if(sheet==null&&sheet2!=null){
			List<DiamondStarShop> dsses=new ArrayList<>();
			parseToDSSList(sheet2,dsses);
			map.put("sheetName2", dianmondName);
			DiamondStarShop dss=null;
			for(int i=0;i<dsses.size();i++){
				dss=dsses.get(i);
				map.put("dss", dss);
				if(dssMapper.getById(map).size()>0){
					dssMapper.deleteAll(map);
					continue;
				}
				else{
					map.remove("dss");
				}
			}
			if(dsses.size()>500){
				List<DiamondStarShop> dsss=new ArrayList<>();
				for(int i=0;i<dsses.size();i++){
					dsss.add(dsses.get(i));
					if(i%500==0){
						map.put("dsses", dsss);
						dssMapper.batchSave(map);
						dsss.clear();
					}
					if(i==dsses.size()-1){
						map.put("dsses", dsss);
						dssMapper.batchSave(map);
					}
				}
			}else{
				map.put("dsses", dsses);
				dssMapper.batchSave(map);
			}
			return true;
		}
		
		else{
			return false;
		}
		
	}
	
	//推广单元报表的插入及解析
	private void parseToDSSList(Sheet sheet, List<DiamondStarShop> dsses) {
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			DiamondStarShop dss = parseToDSS(row);
			if (dss != null) {
				dsses.add(dss);
			}
		}
	}
	
	private DiamondStarShop parseToDSS(Row row) {
		DiamondStarShop dss=null;
		Cell cell=row.getCell(0);
		String conditio=null;
		if(cell!=null&&getCellValue(cell)!=null){
			conditio=getCellValue(cell);
		}
		
		cell=row.getCell(1);
		String unit=null;
		if(cell!=null&&getCellValue(cell)!=null){
			unit=getCellValue(cell);
		}
		
		cell=row.getCell(2);
		String project=null;
		if(cell!=null&&getCellValue(cell)!=null){
			project=getCellValue(cell);
		}
		
		cell=row.getCell(3);
		Long impression=null;
		if(cell!=null&&getCellValue(cell)!=null){
			impression=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(4);
		Long click=null;
		if(cell!=null&&getCellValue(cell)!=null){
			click=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(5);
		Double ctr=null;
		if(cell!=null&&getCellValue(cell)!=null){
			ctr=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(6);
		Double cost=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cost=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(7);
		Double cpm=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cpm=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(8);
		Double cpc=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cpc=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(9);
		Double tdRoi=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tdRoi=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(10);
		Double sdRoi=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sdRoi=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(11);
		Double fdRoi=null;
		if(cell!=null&&getCellValue(cell)!=null){
			fdRoi=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(12);
		Long tdOrders=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tdOrders=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(13);
		Long sdOrders=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sdOrders=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(14);
		Long fdOrders=null;
		if(cell!=null&&getCellValue(cell)!=null){
			fdOrders=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(15);
		Integer sBookmark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sBookmark=(int) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(16);
		Integer pBookmark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			pBookmark=(int) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(17);
		Long visits=null;
		if(cell!=null&&getCellValue(cell)!=null){
			visits=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(18);
		Long tdVisits=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tdVisits=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(19);
		Long sdVisits=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sdVisits=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(20);
		Long fdVisits=null;
		if(cell!=null&&getCellValue(cell)!=null){
			fdVisits=(long) Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(21);
		Double tdvImpre=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tdvImpre=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(22);
		Double sdvImpre=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sdvImpre=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(23);
		Double fdvImpre=null;
		if(cell!=null&&getCellValue(cell)!=null){
			fdvImpre=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(24);
		Double sdAmount=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sdAmount=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(25);
		Double fdAmount=null;
		if(cell!=null&&getCellValue(cell)!=null){
			fdAmount=Double.parseDouble(getCellValue(cell));
		}
		
		cell=row.getCell(26);
		String sCode=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sCode=getCellValue(cell);
		}else{
			throw new NumberFormatException("店铺代码为空!");
		}
		
		cell=row.getCell(27);
		String cCategory=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cCategory=getCellValue(cell);
		}
		
		cell=row.getCell(28);
		String dStarShop=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dStarShop=getCellValue(cell);
		}
		
		cell=row.getCell(29);
		String dayId=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dayId=getCellValue(cell);
		}else{
			throw new NumberFormatException("日期为空!");
		}
		
		dss=new DiamondStarShop(conditio, unit, project, impression, click, ctr, cost, cpm, cpc, tdRoi, sdRoi, fdRoi, tdOrders, sdOrders, fdOrders, sBookmark, pBookmark, visits, tdVisits, sdVisits, fdVisits, tdvImpre, sdvImpre, fdvImpre, sdAmount, fdAmount, sCode, cCategory, dStarShop, dayId);
		return dss;
	}

	//p4p数据表的插入及解析
	private void parseToP4PList(Sheet sheet, List<P4P> p4ps) {
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			P4P p4p = parseToP4P(row);
			if (p4p != null) {
				p4ps.add(p4p);
			}
		}
	}

	//解析单元格
	private P4P parseToP4P(Row row) {
		P4P p4p=null;
		Cell cell = row.getCell(0);
		String dayId=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dayId = getCellValue(cell);
		}else{
			throw new NumberFormatException("日期为空!");
		}
		
		
		cell = row.getCell(1);
		String project=null;
		if(cell!=null&&getCellValue(cell)!=null){
			project = getCellValue(cell);
		}
		
		
		cell = row.getCell(2);
		String product=null;
		if(cell!=null&&getCellValue(cell)!=null){
			product = getCellValue(cell);
		}
		
		
		cell = row.getCell(3);
		String searchType=null;
		if(cell!=null&&getCellValue(cell)!=null){
			searchType = getCellValue(cell);
		}
		 
		cell = row.getCell(4);
		String trafficSource=null;
		if(cell!=null&&getCellValue(cell)!=null){
			trafficSource = getCellValue(cell);
		}
		
		
		cell = row.getCell(5);
		Long impression=null;
		if(cell!=null&&getCellValue(cell)!=null){
			impression = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(6);
		Long click=null;
		if(cell!=null&&getCellValue(cell)!=null){
			click = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(7);
		Long cost=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cost = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(8);
		Double ctr=null;
		if(cell!=null&&getCellValue(cell)!=null){
			ctr = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(9);
		Double cpc=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cpc = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(10);
		Double cpm=null;
		if(cell!=null&&getCellValue(cell)!=null){
			cpm = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(11);
		Double rVClick=null;
		if(cell!=null&&getCellValue(cell)!=null){
			
			rVClick = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(12);
		Long dTurnover=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dTurnover = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(13);
		Integer dTransactions=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dTransactions = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(14);
		Long iTurnover=null;
		if(cell!=null&&getCellValue(cell)!=null){
			iTurnover = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(15);
		Integer iTransactions=null;
		if(cell!=null&&getCellValue(cell)!=null){
			iTransactions = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(16);
		Long tAmount=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tAmount = (long) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(17);
		Integer tTransactions=null;
		if(cell!=null&&getCellValue(cell)!=null){
			 tTransactions = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(18);
		Integer pBookmark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			pBookmark = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(19);
		Integer sBookmark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			sBookmark = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(20);
		Integer tBookmark=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tBookmark = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(21);
		Double roi=null;
		if(cell!=null&&getCellValue(cell)!=null){
			roi = Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(22);
		Integer dsCart=null;
		if(cell!=null&&getCellValue(cell)!=null){
			dsCart = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(23);
		Integer isCart=null;
		if(cell!=null&&getCellValue(cell)!=null){
			isCart = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(24);
		Integer tsCart=null;
		if(cell!=null&&getCellValue(cell)!=null){
			tsCart = (int) Double.parseDouble(getCellValue(cell));
		}
		
		cell = row.getCell(25);
		String poductLine=null;
		if(cell!=null&&getCellValue(cell)!=null){
			poductLine = getCellValue(cell);
		}
		
		cell = row.getCell(26);
		String shopCode=null;
		if(cell!=null&&getCellValue(cell)!=null){
			shopCode = getCellValue(cell);
		}else{
			throw new NumberFormatException("店铺代码为空!");
		}
		
		p4p=new P4P(dayId, project, product, searchType, trafficSource, impression, click, cost, ctr, cpc, cpm, rVClick, dTurnover, dTransactions, iTurnover, iTransactions, tAmount, tTransactions, pBookmark, sBookmark, tBookmark, roi, dsCart, isCart, tsCart, poductLine, shopCode);
		return p4p;
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
