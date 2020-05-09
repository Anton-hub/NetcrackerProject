package com.vkgroupstat.export.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.stat.StatItem;

@Component
public class ExcelCollector {
	
	private static final Logger LOG = LogManager.getLogger(ExcelCollector.class);
	
	private final ExcelFileHandler fileHandler;	
	@Autowired
	public ExcelCollector(ExcelFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	private XSSFSheet sheet; 
	
	public File collect(Group group) {	
		
		XSSFWorkbook book = fileHandler.createNewBook();
		
		sheet = book.getSheetAt(0);
		setValue(0, 0, group.getStringName());
		setValue(1, 0, group.getUrlName());
		setValue(2, 0, group.getCreateDate().toString());
		
		setList(35, 1, group.getGroupStat().getSexStat());
		setList(35, 4, group.getGroupStat().getAgeStat());
		setList(35, 7, group.getGroupStat().getCityStat());
		setList(35, 13, group.getGroupStat().getActivityStat());
		
		setValue(35, 10, "Бан");
		setValue(36, 10, "Живые");
		setValue(35, 11, group.getGroupStat().getBannedCount());
		setValue(36, 11, group.getGroupStat().getMemberCount());
		
		File file = fileHandler.WorkbookToFile(book, group.getUrlName() + "_report");
		
		return file;
	}
	
	public void setList(Integer row, Integer column, LinkedList<StatItem> list) {
		for (StatItem item : list) {
			setValue(row, column, item.getName());
			setValue(row, ++column, item.getCount());
			column--;
			row++;
		}
	}
	
	public void setValue(Integer row, Integer column, Object value) {
		
		XSSFCell cell = sheet.getRow(row).getCell(column);
		
		if (cell == null)
			cell = sheet.getRow(row).createCell(column);
		
		if (value instanceof String)
			cell.setCellValue((String)value);
		if (value instanceof Integer)
			cell.setCellValue((Integer)value);
	}
}
