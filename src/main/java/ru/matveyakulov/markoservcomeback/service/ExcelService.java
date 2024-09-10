package ru.matveyakulov.markoservcomeback.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.matveyakulov.markoservcomeback.domain.Holiday;

import java.io.*;
import java.util.*;

public class ExcelService {

    public static String write(List<Holiday> holidays) {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "holidays.xlsx";

        FileInputStream file ;
        Workbook workbook;
        try {
            file = new FileInputStream(fileLocation);
            workbook = new XSSFWorkbook(file);
        } catch (Exception e) {
            workbook = new XSSFWorkbook();
        }
        try {
            Sheet sheet;
            if(workbook.getNumberOfSheets() > 0) {
                sheet = workbook.getSheetAt(0);
            } else {
                sheet = workbook.createSheet("Holidays");
                sheet.setColumnWidth(0, 4000);
                sheet.setColumnWidth(1, 4000);
                sheet.setColumnWidth(2, 30000);
                sheet.setColumnWidth(3, 4000);
                Row header = sheet.createRow(0);
                List<String> fields = List.of("month", "day", "value", "datetime");
                for (int i = 0; i < fields.size(); i++) {
                    Cell headerCell = header.createCell(i);
                    headerCell.setCellValue(fields.get(i));
                }
            }

            int lastRow = sheet.getLastRowNum();
            for (int i = 1; i < holidays.size() + 1; i++) {
                Row row = sheet.createRow(lastRow + i);
                Holiday holiday = holidays.get(i - 1);
                Cell month = row.createCell(0);
                month.setCellValue(holiday.getMonth());
                Cell day = row.createCell(1);
                day.setCellValue(holiday.getDay());
                Cell value = row.createCell(2);
                value.setCellValue(holiday.getValue());
                Cell value2 = row.createCell(3);
                value2.setCellValue(holiday.getValue());
            }

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            return fileLocation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Holiday> read(String fileLocation) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(fileLocation);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            List<Holiday> holidays = new ArrayList<>();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Holiday holiday = new Holiday(
                        row.getCell(0).getStringCellValue(),
                        (int) row.getCell(1).getNumericCellValue(),
                        row.getCell(2).getStringCellValue());
                holidays.add(holiday);
            }
            return holidays;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
