//package com.vkgroupstat.view;
//
//import com.vkgroupstat.model.Group;
//import com.vkgroupstat.vkconnection.vkentity.Subscription;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.web.servlet.view.document.AbstractXlsView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.LinkedList;
//import java.util.Map;
//
//public class ExcelView extends AbstractXlsView {
//
//    @Override
//    protected void buildExcelDocument(Map<String, Object> model,
//                                      Workbook workbook,
//                                      HttpServletRequest request,
//                                      HttpServletResponse response) throws Exception {
//
//        // change the file name
//        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
//
////        @SuppressWarnings("unchecked")
////        List<User> users = (List<User>) model.get("users");
//        Group group = (Group)model.get("group");
//        LinkedList<Subscription> groupsList = group.getRangeList();
//        // create excel xls sheet
//        Sheet sheet = workbook.createSheet("Range List");
//        sheet.setDefaultColumnWidth(30);
//
//        // create style for header cells
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setFontName("Arial");
//        style.setFillForegroundColor(HSSFColor.BLUE.index);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        font.setBold(true);
//        font.setColor(HSSFColor.WHITE.index);
//        style.setFont(font);
//
//
//        // create header row
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("vk id");
//        header.getCell(0).setCellStyle(style);
//        header.createCell(1).setCellValue("Название");
//        header.getCell(1).setCellStyle(style);
//        header.createCell(2).setCellValue("Общие подписчики");
//        header.getCell(2).setCellStyle(style);
//        header.createCell(3).setCellValue("Всего подписчиков");
//        header.getCell(3).setCellStyle(style);
//        header.createCell(4).setCellValue("Женщин");
//        header.getCell(4).setCellStyle(style);
//        header.createCell(5).setCellValue("Мужчин");
//        header.getCell(5).setCellStyle(style);
//        header.createCell(6).setCellValue("> 1 лайка");
//        header.getCell(6).setCellStyle(style);
//        header.createCell(7).setCellValue("> 1 лайка и 1 комментария");
//        header.getCell(7).setCellStyle(style);
////        header.createCell(8).setCellValue("Phone Number");
////        header.getCell(8).setCellStyle(style);
//
//
//
//        int rowCount = 1;
//
//        for(Subscription current : groupsList){
//            Row userRow =  sheet.createRow(rowCount++);
//            userRow.createCell(0).setCellValue(current.getId());
//            userRow.createCell(1).setCellValue(current.getStringName());
//            userRow.createCell(2).setCellValue(current.getTargetSubsCount());
//            userRow.createCell(3).setCellValue(current.getThisGroupSubsCount());
//            userRow.createCell(4).setCellValue(current.getStatistics().getSexStat().get(0).toString());
//            userRow.createCell(5).setCellValue(current.getStatistics().getSexStat().get(1).toString());
//            userRow.createCell(6).setCellValue(current.getStatistics().getActivityStat().get(0).toString());
//            userRow.createCell(7).setCellValue(current.getStatistics().getActivityStat().get(1).toString());
////            userRow.createCell(8).setCellValue(user.getPhoneNumber());
//
//            }
//
//    }
//
//}
