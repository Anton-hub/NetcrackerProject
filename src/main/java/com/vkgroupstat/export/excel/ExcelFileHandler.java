package com.vkgroupstat.export.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelFileHandler {
	
	public static final Logger LOG = LogManager.getLogger(ExcelFileHandler.class);
	
	public XSSFWorkbook createNewBook() {
		File templatePath = new File("./src/main/resources/exportTemplate.xlsx");
		XSSFWorkbook book;		
		FileInputStream inputStream;
		
		try {			
			inputStream = new FileInputStream(templatePath);
			book = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			LOG.error("Ошибка чтения шаблона");
			return null;
		}
		
		return book;
	}
	
	public byte[] WorkbookToByte(XSSFWorkbook book) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
		    book.write(bos);
		} catch (IOException e) {
			LOG.error("Ошибка записи книги в поток");
		}
		
		byte[] bytes = bos.toByteArray();		
		
		return bytes;
	}
}
