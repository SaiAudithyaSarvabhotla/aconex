package com.automation.framework.utils;

import java.io.File;

public class ApplicationConstants {

	public static final String projectPath = System.getProperty("user.dir");

	public static final String testDataFileName = "testData.xls";

	public static final String orFileName = "objectRepository.xlsx";

	public static final String excelSheetName = "Sheet1";

	public static final String testDataPath = projectPath + File.separator + "resources" + File.separator + "testData"
			+ File.separator + testDataFileName;

	public static final String orPath = projectPath + File.separator + "resources" + File.separator + "objectrepository"
			+ File.separator + orFileName;

	public static String chromerDriverPath = projectPath + File.separator + "resources" + File.separator + "drivers"
			+ File.separator + "chromedriver.exe";

	public static String fireFoxDriverPath = projectPath + File.separator + "resources" + File.separator + "drivers"
			+ File.separator + "geckodriver.exe";

	public static String ieDriverPath = projectPath + File.separator + "resources" + File.separator + "drivers"
			+ File.separator + "IEDriverServer.exe";

	public static final String aconexApplicationUrl = "https://qa122.aconex.com/";

	public static final int dynamicWaitTime = 60;
	
	public static final boolean isContinue = true;
	
	public static String reportPath = projectPath + File.separator + "reports" + File.separator + "extentReport"
			+ File.separator + "ExtentReport.html";
	
}
