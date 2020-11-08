package com.automation.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.framework.core.FrameworkException;
import com.relevantcodes.extentreports.LogStatus;

public class ObjectHelper {

	private static Sheet sheet = null;
	private static Workbook workbook = null;
	private static WebElement element = null;
	public static String screenName = null;
	public static String logicalName = null;
	public static String[] className = null;
	public static long maxTimeOut;

	static {
		try {
			if (ApplicationConstants.orFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(ApplicationConstants.orPath));
				sheet = workbook.getSheet(ApplicationConstants.excelSheetName);
			} else if (ApplicationConstants.orFileName.endsWith(".xlsx")) {
				FileInputStream file = new FileInputStream(new File(ApplicationConstants.orPath));
				workbook = new XSSFWorkbook(file);
				sheet = workbook.getSheet(ApplicationConstants.excelSheetName);
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static WebElement getWebElement() {
		try {
			element = findObject(getObject());
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return element;

	}

	private static WebElement findObject(String xPath) {
		WebDriver driver = null;
		driver = DriverHelper.getWebDriver();
		WebElement webelement = null;
		WebDriverWait dynamicWait = null;
		long startTime;
		try {
			waitForpageToLoad();
			maxTimeOut = ApplicationConstants.dynamicWaitTime * 1000;
			startTime = System.currentTimeMillis();
			List<WebElement> webelements = driver.findElements(By.xpath(xPath));
			while ((webelements.size() == 0) & (System.currentTimeMillis() - startTime < maxTimeOut)) {
				webelements = driver.findElements(By.xpath(xPath));
			}
			if (webelements.size() > 0) {
				webelement = driver.findElement(By.xpath(xPath));
				dynamicWait = new WebDriverWait(driver, maxTimeOut);
				dynamicWait.until(ExpectedConditions.visibilityOf(webelement));
				dynamicWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
				Reporter.reportEventWithoutScreenshot(LogStatus.PASS, getClassMethodName(),
						"WebElement " + logicalName + " is Present");
			} else {
				Reporter.reportEventWithScreenshot(LogStatus.FAIL, getClassMethodName(),
						"WebElement " + logicalName + " is not Present");
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}

		return webelement;
	}

	public static String getObject() {
		try {
			className = Thread.currentThread().getStackTrace()[3].getClassName().replace(".", "/").split("/");
			screenName = className[className.length - 1];
			logicalName = Thread.currentThread().getStackTrace()[3].getMethodName();
			return getXPath(screenName, logicalName);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

	public static String getXPath(String screenName, String logicalName) {
		String xpath = null;
		int noofRows;
		int objectRowNumber = -1;
		try {
			noofRows = getNumberOfRows();
			for (int index = 0; index < noofRows; index++) {
				if (screenName.equalsIgnoreCase(getCellData(index, 0).toString())) {
					objectRowNumber = index;
					if (logicalName.equalsIgnoreCase(getCellData(objectRowNumber, 1).toString())) {
						return getCellData(objectRowNumber, 2);
					}
				}
			}
			return xpath;
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

	public static int getNumberOfRows() {
		return sheet.getPhysicalNumberOfRows();
	}

	public static String getCellData(int rowNumber, int columnNumber) {
		Row row = null;
		Cell col = null;
		try {
			row = sheet.getRow(rowNumber);
			if (row != null) {
				col = row.getCell(columnNumber);
				if (col != null) {
					return col.toString().trim();
				}
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

	public static void waitForpageToLoad() {
		JavascriptExecutor js = null;
		String status = null;
		long startTime;
		try {
			maxTimeOut = ApplicationConstants.dynamicWaitTime * 1000;
			js = (JavascriptExecutor) DriverHelper.getWebDriver();
			startTime = System.currentTimeMillis();
			status = js.executeScript("return document.readyState").toString();
			while (((!status.equalsIgnoreCase("complete")) & System.currentTimeMillis() - startTime < maxTimeOut)) {
				status = js.executeScript("return document.readyState").toString();
			}
			if (!(status.equalsIgnoreCase("complete"))) {
				System.out.println("Page is not loaded fully even after 60 secs. Contact Dev to improve performance");
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	private synchronized static String getClassMethodName() {
		String className, classSimpleName, methodName, classMethodName;
		className = Thread.currentThread().getStackTrace()[3].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length - 1];
		methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		classMethodName = "[" + classSimpleName + ":" + methodName + "]";
		return classMethodName;
	}

}
