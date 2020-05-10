package com.vkgroupstat.export.excel;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.vkgroupstat.model.Group;

public class ExcelView extends AbstractXlsxView {

	@Autowired
	private ExcelCollector excelCollector;

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									Workbook workbook,
									HttpServletRequest request,
									HttpServletResponse response) throws Exception {

		Group group = (Group)model.get("group");
		workbook = excelCollector.experementalCollect(group);

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + group.getUrlName() + "_report.xlsx");
		response.setContentType("application/octet-stream");  

	}
}