package boot.service;

import boot.mongo.dto.EcpDTO;
import boot.mongo.dto.ReportDTO;
import boot.mongo.klazz.KlazzService;
import boot.mongo.klazz.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class Export {
    private static final String FILE_NAME = "/home/karakozhin/excel.xlsx";
    private static final String Excel_ECP = "/home/karakozhin/excel_ecp.xlsx";

    @Autowired
    private AppService appService;

    @Autowired
    private KlazzService klazzService;

    public Workbook getExcel(List<Long> periodKindListId, String teCode, List<String> statusCode){
        int i = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Лист1");
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C1:C3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("G1:G3"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("H1:H3"));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        Row row = sheet.createRow(i);
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(6, 5000);

        row.createCell(0).setCellValue("№");
        row.createCell(1).setCellValue("Наименование формы");
        row.createCell(2).setCellValue("Количество по каталогу");
        row.createCell(3).setCellValue("Количество отчитавшихся");
        row.createCell(6).setCellValue("Период");
//        row.createCell(7).setCellValue("Форм Айди");

        i++;
        Row row1 = sheet.createRow(i);
        row1.createCell(3).setCellValue("Всего");
        row1.createCell(4).setCellValue("Из них");

        i++;
        Row row2 = sheet.createRow(i);
        row2.createCell(4).setCellValue("Переотчет");
        row2.createCell(5).setCellValue("Дозапись");


        List<ReportDTO> reports = appService.getReports(periodKindListId, teCode, statusCode);

        for (ReportDTO dto: reports){
            int num = dto.getNum();
            Long formId = dto.getFormId();
            String formName = dto.getFormName();
            String periodName = dto.getPeriodKindlistName();
            Long periodKindList = dto.getPeriodKindListId();
            Long cntCatalog = dto.getCntCatalog();
            Long otchitavwisya = dto.getOtchitavwisya();
            Long pereotchet = dto.getPereotchet();
            Long dozapis = dto.getDozapis();

            i ++;

            Row rowData = sheet.createRow(i);

            rowData.createCell(0).setCellValue(num);
            rowData.createCell(1).setCellValue(formName);
            rowData.createCell(2).setCellValue(cntCatalog);
            rowData.createCell(3).setCellValue(otchitavwisya);
            rowData.createCell(4).setCellValue(pereotchet);
            rowData.createCell(5).setCellValue(dozapis);
            rowData.createCell(6).setCellValue(periodName);
//            rowData.createCell(7).setCellValue(periodKindList);
//            rowData.createCell(8).setCellValue(formId);



        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return workbook;
    }

    public Workbook getExcelRegion(Long periodKindListId){
        int i = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Лист1");

        Row row = sheet.createRow(i);
        row.setHeightInPoints(4*sheet.getDefaultRowHeightInPoints());
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);

        Font hFont = workbook.createFont();
        hFont.setFontName("Arial");
        hFont.setFontHeightInPoints((short)8);
        hFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        Font rFont = workbook.createFont();
        rFont.setFontName("Arial");
        rFont.setFontHeightInPoints((short)8);
        rFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);

        CellStyle hStyle = workbook.createCellStyle();
        hStyle.setFont(hFont);
        hStyle.setAlignment(CellStyle.ALIGN_CENTER);
        hStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        hStyle.setBorderBottom(CellStyle.BORDER_THIN);
        hStyle.setBorderLeft(CellStyle.BORDER_THIN);
        hStyle.setBorderRight(CellStyle.BORDER_THIN);
        hStyle.setBorderTop(CellStyle.BORDER_THIN);

        CellStyle rStyle = workbook.createCellStyle();
        rStyle.setFont(rFont);
        rStyle.setWrapText(true);
        rStyle.setAlignment(CellStyle.ALIGN_CENTER);
        rStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        rStyle.setBorderBottom(CellStyle.BORDER_THIN);
        rStyle.setBorderLeft(CellStyle.BORDER_THIN);
        rStyle.setBorderRight(CellStyle.BORDER_THIN);
        rStyle.setBorderTop(CellStyle.BORDER_THIN);

        row.createCell(0).setCellValue("Регионы");
        row.getCell(0).setCellStyle(hStyle);
        row.createCell(1).setCellValue("Количество \nреспондентов по \nкаталогу согласно \nКРП");
        row.getCell(1).setCellStyle(hStyle);
        row.createCell(2).setCellValue("Количество  \nреспондентов, \nпредставившие стат. \nформы в онлайн режиме");
        row.getCell(2).setCellStyle(hStyle);
        row.createCell(3).setCellValue("Доля респондентов, \nпредставивших  стат. \nформы в онлайн режиме \n(в %)");
        row.getCell(3).setCellStyle(hStyle);
        
        List<EcpDTO> ecpDTOList = appService.getOur(periodKindListId);

        row.getCell(0).setCellStyle(hStyle);
        row.getCell(1).setCellStyle(hStyle);
        row.getCell(2).setCellStyle(hStyle);
        row.getCell(3).setCellStyle(hStyle);


        for (EcpDTO ecpDTO : ecpDTOList){

            String name = ecpDTO.getOblName();
            Long cntCatalog = ecpDTO.getCntCatalog();
            Long ecp = ecpDTO.getKolECP();
            String procent = ecpDTO.getProcent();



            i++;
            Row rowData = sheet.createRow(i);

            rowData.createCell(0).setCellValue(name);
//            Row roww = sheet;


            rowData.createCell(1).setCellValue(cntCatalog);

            rowData.createCell(2).setCellValue(ecp);

            rowData.createCell(3).setCellValue(procent);
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(Excel_ECP);
            workbook.write(outputStream);
            workbook.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  workbook;
    }

}
