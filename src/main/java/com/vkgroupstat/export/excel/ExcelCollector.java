package com.vkgroupstat.export.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.Subscription;
import com.vkgroupstat.vkconnection.vkentity.stat.StatItem;

@Component
public class ExcelCollector {
	
	private static final Logger LOG = LogManager.getLogger(ExcelCollector.class);
	
	private final ExcelFileHandler fileHandler;	
	@Autowired
	public ExcelCollector(ExcelFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	public byte[] collect(Group group) {	
		
		XSSFWorkbook book = fileHandler.createNewBook();
		FormulaEvaluator evaluator = book.getCreationHelper()
				.createFormulaEvaluator();
		
		XSSFSheet sheet = book.getSheetAt(0);
		
		setValue(sheet, 0, 0, group.getStringName());
		setValue(sheet, 1, 2, group.getUrlName());
		setValue(sheet, 2, 2, group.getCreateDate().toString());
		setRangeList(sheet, 47, group.getRangeList());
		
		sheet = book.getSheetAt(1);
		
		setList(sheet, 2, 1, group.getGroupStat().getSexStat());
		setList(sheet, 2, 4, group.getGroupStat().getAgeStat());
		setList(sheet, 2, 7, group.getGroupStat().getCityStat());
		setList(sheet, 2, 13, group.getGroupStat().getActivityStat());
		setValue(sheet, 1, 15, group.getGroupStat().getMemberCount());
		evaluator.evaluateFormulaCell(sheet.getRow(2).getCell(15));
		evaluator.evaluateFormulaCell(sheet.getRow(3).getCell(15));
		
		setValue(sheet, 2, 10, "Забаненные");
		setValue(sheet, 3, 10, "Активные");
		setValue(sheet, 2, 11, group.getGroupStat().getBannedCount());
		setValue(sheet, 3, 11, group.getGroupStat().getMemberCount());
		
		byte[] bytes = fileHandler.WorkbookToByte(book);
		
		return bytes;
	}
	
	public void setList(XSSFSheet sheet, Integer row, Integer column, LinkedList<StatItem> list) {
		for (StatItem item : list) {
			setValue(sheet, row, column, item.getName());
			setValue(sheet, row, column + 1, item.getCount());
			row++;
		}
	}
	
	public void setValue(XSSFSheet sheet, Integer row, Integer column, Object value) {
		
		XSSFCell cell = sheet.getRow(row).getCell(column);
		
		if (cell == null)
			cell = sheet.getRow(row).createCell(column);
		
		if (value instanceof String)
			cell.setCellValue((String)value);
		if (value instanceof Integer)
			cell.setCellValue((Integer)value);
	}
	
	public void setRangeList(XSSFSheet sheet, Integer startRow, LinkedList<Subscription> list) {
		Integer row = startRow;
		Integer column = 1;
		for (Subscription item : list) {
			setValue(sheet, row, column, item.getStringName());
			setValue(sheet, row, column+8, item.getThisGroupSubsCount());
			setValue(sheet, row, column+10, item.getTargetSubsCount());
			row++;
		}
	}
	
	public XSSFWorkbook experementalCollect(Group group) {	

		XSSFWorkbook book = fileHandler.createNewBook();
		FormulaEvaluator evaluator = book.getCreationHelper()
				.createFormulaEvaluator();

		XSSFSheet sheet = book.getSheetAt(0);

		setValue(sheet, 0, 0, group.getStringName());
		setValue(sheet, 1, 2, group.getUrlName());
		setValue(sheet, 2, 2, group.getCreateDate().toString());
		setRangeList(sheet, 47, group.getRangeList());

		sheet = book.getSheetAt(1);

		setList(sheet, 2, 1, group.getGroupStat().getSexStat());
		setList(sheet, 2, 4, group.getGroupStat().getAgeStat());
		setList(sheet, 2, 7, group.getGroupStat().getCityStat());
		setList(sheet, 2, 13, group.getGroupStat().getActivityStat());
		setValue(sheet, 1, 15, group.getGroupStat().getMemberCount());
		evaluator.evaluateFormulaCell(sheet.getRow(2).getCell(15));
		evaluator.evaluateFormulaCell(sheet.getRow(3).getCell(15));

		setValue(sheet, 2, 10, "Забаненные");
		setValue(sheet, 3, 10, "Активные");
		setValue(sheet, 2, 11, group.getGroupStat().getBannedCount());
		setValue(sheet, 3, 11, group.getGroupStat().getMemberCount());

		return book;
	}
}
