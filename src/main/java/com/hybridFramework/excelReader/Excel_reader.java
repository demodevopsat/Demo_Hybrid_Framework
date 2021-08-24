package com.hybridFramework.excelReader;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_reader {
	
	public static final Logger logger  = Logger.getLogger(Excel_reader.class.getName());
	public String path;
	FileInputStream fis;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	public Excel_reader(String path) {
		this.path = path;
		try {
			logger.info("Creating excel object:-"+path);
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
//			XSSFRow row = sheet.getRow(0);
			XSSFRow row = sheet.getRow(rowNum - 1);
			XSSFCell cell = row.getCell(colNum);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public int getIntCellValue(String sheetName, int colnum, int rowNum) {
		
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		
		row = sheet.getRow(rowNum-1);
		XSSFCell cell = row.getCell(colnum);
		
		cell.setCellType(CellType.NUMERIC);
		
		return (int) cell.getNumericCellValue();
		
	}

	public int getRowCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheetAt(index);
				int number = sheet.getLastRowNum() + 1;
				return number;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getColumnCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(0);
				return row.getLastCellNum();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	public String getFormulatedCellData(String sheetName,int colNum,int rowNum) {
		
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);		
		row = sheet.getRow(rowNum-1);
		 cell = row.getCell(colNum);
		 String cellValue = null;
		 
		 if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//		        System.out.println("Formula is " + cell.getCellFormula());
		        switch(cell.getCachedFormulaResultType()) {
//		            case Cell.CELL_TYPE_NUMERIC:
//		                System.out.println("Last evaluated as: " + cell.getNumericCellValue());
//		                break;
		            case Cell.CELL_TYPE_STRING:
		            	cellValue = cell.getRichStringCellValue().getString();
		                break;
		        }
		     }
		 
		 return cellValue;
	}
}
