/*
 * ExlFileManager.java
 *
 * Created on July 22, 2008, 11:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package chs103.physical;

import chs103.bl.Batch;
import chs103.bl.BatchFormat;
import chs103.exception.ReadFileExeption;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Title: ExlFileManager <br> Decription: <br> Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class ExlFileManager {

    /**
     * Creates a new instance of ExlFileManager
     */
    public ExlFileManager() {
    }

    /**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb the workbook
     * @param row the row to create the cell in
     * @param value the value
     * @param column the column number to create the cell in
     * @param align the alignment for the cell.
     * @param type the value type
     */
    private static HSSFCell createCell(HSSFWorkbook wb, HSSFRow row, Object value, short column, short align, int type) {
        HSSFCell cell = row.createCell(column);
        HSSFCellStyle cellStyle = null;


        //for futeare use
        switch (type) {
            case HSSFCell.CELL_TYPE_STRING:
                cell.setCellValue((String) value);
                cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(align);
                cell.setCellStyle(cellStyle);
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                cell.setCellValue((Double) value);
                cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(align);
                cell.setCellStyle(cellStyle);
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cell.setCellValue((Boolean) value);
                cellStyle = wb.createCellStyle();
                cell.setCellStyle(cellStyle);
                break;
            default:
        }
        return cell;
    }

    /**
     * The readFromFile method loads the value of a string from a *.CSV file .Static.
     *
     * @param filename the filename
     */
    public static void readFromFile(String filename) throws ReadFileExeption {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = 0; r < rows; r++) {
                HSSFRow row = sheet.getRow(r);
                int cells = row.getPhysicalNumberOfCells();
                System.out.println("ROW " + row.getRowNum());
                for (short c = 0; c < cells; c++) {
                    HSSFCell cell = row.getCell(c);
                    String value = null;
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_FORMULA:
                            value = "FORMULA ";
                            break;

                        case HSSFCell.CELL_TYPE_NUMERIC:
                            value = "NUMERIC value=" + cell.getNumericCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            value = "STRING value=" + cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value = "STRING value=" + cell.getBooleanCellValue();
                            break;

                        default:
                    }
                    System.out.println("CELL col=" + cell.getCellNum() + " VALUE=" + value);
                }
            }
        } catch (Exception e) {
            throw new ReadFileExeption("Error while reading excel file");
        }
    }

    /**
     * The writeToFile method write Batch data to *.CSV file .
     *
     * @param batchs the array of batch
     * @param filename the file name
     */
    public static boolean writeToFile(String filename, Batch[] batchs) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("CH103_1");
        HSSFCellStyle cellStyleForTitle = getCellStyleForTitle(wb);
        short col = 0;
        short rowCounter = 0;

        // init column titles
        HSSFRow row = sheet.createRow((short) rowCounter++);

        col = 0;
        HSSFCell cell = createCell(wb, row, "Batch #", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        cell = createCell(wb, row, "Birds", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        cell = createCell(wb, row, "Average", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        cell = createCell(wb, row, "Standard Deviation", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        cell = createCell(wb, row, "CV", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        cell = createCell(wb, row, "Percent", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyleForTitle);
        // init batch data
        for (int i = 0; i < batchs.length; i++) {
            row = sheet.createRow((short) rowCounter++);
            col = 0;
            createCell(wb, row, (double) batchs[i].getBatchNumber(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getNumOfBirds(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getAvgWeight(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getStdDev(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getCv(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].calcNumOfBirdPercent(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
        }

        row = sheet.createRow((short) rowCounter++);
        col = 0;
        short colFrom = (short) (col);
        short colTo = (short) (col + 1);
        Region region = new Region(row.getRowNum(), colFrom, row.getRowNum(), colTo);

        for (int i = 0; i < batchs.length; i++) {
            cell = createCell(wb, row, "Batch " + batchs[i].getBatchNumber(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyleForTitle);
            cell = createCell(wb, row, " ", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyleForTitle);
            sheet.addMergedRegion(region);
            colFrom += 2;
            colTo += 2;
            region.setColumnFrom(colFrom);
            region.setColumnTo(colTo);
        }
        sheet.addMergedRegion(region);
        region.setColumnFrom(colFrom);
        region.setColumnTo(colTo);

        row = sheet.createRow((short) rowCounter++);
        col = 0;
        for (int i = 0; i < batchs.length; i++) {
            cell = createCell(wb, row, "weight", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyleForTitle);
            cell = createCell(wb, row, " birds ", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyleForTitle);
        }
        sheet.addMergedRegion(region);
        region.setColumnFrom(colFrom);
        region.setColumnTo(colTo);

        for (int k = 0; k < BatchFormat.HISTOGRM_NUMS; k++) {
            row = sheet.createRow((short) rowCounter++);
            col = 0;
            //createCell(wb, row, " - " + k + " - ", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
            createCell(wb, row, (double) batchs[0].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[0].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[1].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[1].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[2].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[2].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[3].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[3].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[4].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[4].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[5].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[5].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[6].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[6].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[7].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[7].getHistogramma()[k], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[8].calcStartWeight(k), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[8].getHistogramma()[k], col, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
        }

        // Write the output to a file
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    boolean isCellEmpty(int[] arr, int i) {
        if (arr[i] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static HSSFCellStyle getCellStyleForTitle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderTop((short) 1); // single line border
        cellStyle.setBorderBottom((short) 1); // single line border
        cellStyle.setBorderLeft((short) 1); // single line border
        cellStyle.setBorderRight((short) 1); // single line border
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// text alignemnt
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    public static boolean writeHistogramToFile(String filename, Batch[] batchs) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("CH103_1");
        short col = 0;

        // init column titles
        HSSFRow row = sheet.createRow((short) 0);
        col = 0;
        createCell(wb, row, "Batch #", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        createCell(wb, row, "Birds", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        createCell(wb, row, "Average", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        createCell(wb, row, "Standard Deviation", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        createCell(wb, row, "CV", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        createCell(wb, row, "Percent", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        for (int j = 0; j < 80; j++) {
            createCell(wb, row, "- " + j + " -", col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_STRING);
        }

        // init batch data
        for (int i = 0; i < batchs.length; i++) {
            row = sheet.createRow((short) i + 1);
            col = 0;
            createCell(wb, row, (double) batchs[i].getBatchNumber(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getNumOfBirds(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getAvgWeight(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getStdDev(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].getCv(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            createCell(wb, row, (double) batchs[i].calcNumOfBirdPercent(), col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            int[] array = batchs[i].getHistogramma();
            for (int j = 0; j < array.length; j++) {
                createCell(wb, row, (double) array[j], col++, HSSFCellStyle.ALIGN_CENTER, HSSFCell.CELL_TYPE_NUMERIC);
            }
        }
        // Write the output to a file
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
