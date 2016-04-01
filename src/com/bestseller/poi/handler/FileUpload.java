package com.bestseller.poi.handler;

import java.io.File;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bestseller.poi.service.CTService;
import com.bestseller.poi.service.DinamondService;

/** 
* @author  xgx@bestseller.com.cn 
* @date 创建时间：2016年3月8日 下午2:46:13 
* @version 1.0  
*/
@Controller
public class FileUpload {
	@Autowired
	private DinamondService dinamondService;
	@Autowired
	private CTService cTService;
	private static final Logger LOGGER=LoggerFactory.getLogger(FileUpload.class);
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(@RequestParam(value="file") MultipartFile file,@RequestParam(value="p4pName",required=false) String p4pName
			,@RequestParam(value="diamondName",required=false) String diamondName,Map<String,Object> map) throws Exception{
		LOGGER.info("导入ODS_sample_p4p表Excel文件工作表名sheet={},及 marketing_DIAMOND_STARSHOP表Excel文件工作表名sheet={}",p4pName, diamondName);
		CommonsMultipartFile cf= (CommonsMultipartFile)file; 
	    DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	    File f = fi.getStoreLocation();
	    boolean upload =false;
	    if(!p4pName.trim().equals("")||!diamondName.trim().equals("")){
	    	upload = dinamondService.upload(f,p4pName,diamondName);
	    	if(!upload){
				map.put("error","上传失败，请确保sheet工作名正确和存在！");
			}
	    	return "success";
	    }
	    if(!upload){
			boolean b=cTService.upload(f);
			if(!b){
				map.put("error","上传失败，请确保sheet工作名正确和存在！");
			}
			return "success";
	    }
		return null;
	}
	
	
}
