package boot.service;

import boot.mongo.dto.ReportDTO;
import boot.mongo.model.StatBin;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

    @Autowired
    private AppService appService;

    public Workbook getExcel(List<Long> periodKindListId, String teCode, List<String> statusCode){
        int i = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Лист1");
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C1:C3"));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        Row row = sheet.createRow(i);
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);

        row.createCell(0).setCellValue("№");
        row.createCell(1).setCellValue("Наименование формы");
        row.createCell(2).setCellValue("Количество по каталогу");
        row.createCell(3).setCellValue("Количество отчитавшихся");

        i++;
        Row row1 = sheet.createRow(i);
        row1.createCell(3).setCellValue("Всего");
        row1.createCell(4).setCellValue("Из них");

        i++;
        Row row2 = sheet.createRow(i);
        row2.createCell(4).setCellValue("Переотчет");
        row2.createCell(5).setCellValue("Дозапись");

        List<StatBin> formsAndPeriod = new ArrayList<>();

        StatBin bin = new StatBin();


        List<ReportDTO> reports = appService.getReports(periodKindListId, teCode, statusCode);

        for (ReportDTO dto: reports){
            Long formId = dto.getFormId();
            Long periodKindList = dto.getPeriodKindListId();
            Long cntCatalog = dto.getCntCatalog();
            Long otchitavwisya = dto.getOtchitavwisya();
            Long pereotchet = dto.getPereotchet();
            Long dozapis = dto.getDozapis();

            i ++;

            Row rowData = sheet.createRow(i);
            rowData.createCell(2).setCellValue(formId);
            rowData.createCell(6).setCellValue(periodKindList);
            rowData.createCell(1).setCellValue(cntCatalog);

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


}
