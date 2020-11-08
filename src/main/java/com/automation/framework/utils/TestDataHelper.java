package com.automation.framework.utils;

import java.util.HashMap;
import com.automation.framework.core.FrameworkException;

public class TestDataHelper {

	public static HashMap<String, String> dataMap = null;

	public static void getTestData(String scriptName) {
		ExcelHelper excelReader = null;
		excelReader = new ExcelHelper(ApplicationConstants.testDataPath, ApplicationConstants.excelSheetName);
		dataMap = excelReader.getData(scriptName);
	}

	public final static String testData(String columnName) {
		try {
			return dataMap.get(columnName.toString());
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

}
