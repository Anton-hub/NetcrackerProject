package com.vkgroupstat.TEST.exprort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TryToExport {
	
	public static void foo() {
	    try {
			File file = new File("./src/main/resources/exportTemplate.xlsx");
			// Read XSL file
			FileInputStream inputStream = new FileInputStream(file);
			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFCell cell = sheet.getRow(0).getCell(0);			
			if (cell == null)
				cell = sheet.getRow(0).createCell(0);
			cell.setCellValue("hello");
			inputStream.close();
			// Write File
			File file1 = new File("./src/main/resources/1.xlsx");
			FileOutputStream out = new FileOutputStream(file1);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
