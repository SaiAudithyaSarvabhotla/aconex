package com.automation.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.framework.core.FrameworkException;

public class ExcelHelper {

	private static Sheet sheet = null;
	private Workbook workbook = null;

	public ExcelHelper(String fileName, String sheetName) {
		fileName.toString();
		try {
			if (fileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(fileName));
				sheet = workbook.getSheet(sheetName);
			} else if (fileName.endsWith(".xlsx")) {
				FileInputStream file = new FileInputStream(new File(fileName));
				workbook = new XSSFWorkbook(file);
				sheet = workbook.getSheet(sheetName);
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public HashMap<String, String> getData(String testScriptName) {
		try {
			int numberOfRows;
			int numberOfColumns;
			int scriptRowNumber = -1;
			int headerRowNumber = -1;
			ArrayList<Integer> requiredRowList = new ArrayList<Integer>();
			HashMap<String, String> testData = new HashMap<String, String>();

			numberOfRows = sheet.getPhysicalNumberOfRows();

			for (int index = 0; index < numberOfRows; index++) {
				if (testScriptName.equalsIgnoreCase(getCellData(index, 0).toString())) {
					scriptRowNumber = index;
					requiredRowList.add(scriptRowNumber);
				}
			}

			for (int index = requiredRowList.get(0) - 1; index >= 0; index--) {
				if ("TestScript".equalsIgnoreCase(getCellData(index, 0).toString())) {
					headerRowNumber = index;
					break;
				}
			}

			numberOfColumns = sheet.getRow(headerRowNumber).getPhysicalNumberOfCells();

			for (int columnNumber = 0; columnNumber < numberOfColumns; columnNumber++) {
				testData.put(getCellData(headerRowNumber, columnNumber).toString(),
						getCellData(scriptRowNumber, columnNumber).toString());
			}

			return testData;

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

	public static String getCellData(int rowNumber, int colNumber) {
		try {
			Row row = null;
			Cell cellData = null;
			row = sheet.getRow(rowNumber);
			if (row != null) {
				cellData = row.getCell(colNumber);
				if (cellData != null) {
					return cellData.toString().trim();
				} else {
					return "";
				}
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;

	}
}
