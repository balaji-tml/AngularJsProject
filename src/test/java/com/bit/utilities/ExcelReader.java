package com.bit.utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReader {

    FileInputStream fis;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;
    String path;


    String cellData = null;


    public ExcelReader(String path) {
        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            row = sheet.getRow(0);
            cell = row.getCell(0);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount(String sheetName) {
        int number,index = workbook.getSheetIndex(sheetName);
        if(index == -1)
            return 0;
        else
        {
            sheet = workbook.getSheetAt(index);
            number = sheet.getLastRowNum() + 1;
            return number;
        }
    }

    public int getColumnCount(String sheetName) {
        int number,index = workbook.getSheetIndex(sheetName);
        if(index == -1)
            return 0;
        else
        {
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            number = row.getLastCellNum();
            return number;
        }
    }

    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if(rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);
            int col_num = -1;
            if(index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            cell = row.getCell(colNum);
            switch (cell.getCellType()) {
                case NUMERIC:
                    cellData = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellData = String.valueOf(cell.getBooleanCellValue());
                    break;
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case BLANK:
                    cellData = "";
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellData;

    }


}
