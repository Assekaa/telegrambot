package com.AITU;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelWorker {


    private static XSSFSheet sheet;
    private static List<String> rowNames;
    private static StringBuilder stringBuilder = new StringBuilder();

    public static String chooseGroupToDisplay(String kurs) throws IOException {
        Integer kursi = Integer.valueOf(kurs);
        Integer actKurs = 0;
        stringBuilder = new StringBuilder();
        if (kursi>=1 && kursi<=2) {

            switch (kurs) {
                case "1":
                    actKurs = 0;
                    break;
            }

            File myFile = new File("schedule_4g_exams.xlsx");
            FileInputStream fis = new FileInputStream(myFile);
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            // Return first sheet from the XLSX workbook
            sheet = myWorkBook.getSheetAt(actKurs);


            rowNames = getGroupNames(sheet);

            if (rowNames!=null){

                String rowsRequest = rowNames.stream()
                        .map(x -> '/' + x + '\n')
                        .map(x-> x.replace('-','_'))
                        .collect(Collectors.joining());

                return rowsRequest;
            }

        }   else return "Choose course 1-2";

        return stringBuilder.toString();
    }

    public static List<String> getGroupNames(XSSFSheet sheet){
        List<String> response = new ArrayList<String>();
        XSSFRow row = sheet.getRow(4);
        Iterator<Cell> iterator = row.iterator();
        while (iterator.hasNext()){
            Cell cell = iterator.next();
            if (!cell.getStringCellValue().isEmpty()){
                response.add(cell.getStringCellValue());
            }
        }
        return response;
    }


    public static void getRowValues() throws IOException {
        File myFile = new File("schedule_4g_exams.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

        }

    }

    public static String getRowsByGroupName(String group) {


        int colIndex = rowNames.indexOf(group);


        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                if (cell.getColumnIndex() == colIndex + 1 || cell.getColumnIndex() == 0) {


                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            if (!cell.getStringCellValue().isEmpty())
                                stringBuilder.append(cell.getStringCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            stringBuilder.append(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            stringBuilder.append(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                    }

                }

            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}