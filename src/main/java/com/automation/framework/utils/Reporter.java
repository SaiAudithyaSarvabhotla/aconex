package com.automation.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.automation.framework.core.FrameworkException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter {

	public static Map<Long, String> threadToExtentReportMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	public static ExtentReports extent = null;

	public static ExtentTest getTest(String testName, String description) {
		if (!nameToTestMap.containsKey(testName)) {
			Long threadId = Thread.currentThread().getId();
			ExtentTest test = getReport().startTest(testName, description);
			nameToTestMap.put(testName, test);
			threadToExtentReportMap.put(threadId, testName);
		}
		return nameToTestMap.get(testName);
	}

	public static ExtentReports getReport() {
		try {
			if (extent == null) {
				extent = new ExtentReports(ApplicationConstants.reportPath, true);
				extent.addSystemInfo("Host Name", "").addSystemInfo("Environment", "");
			}
			return extent;
		} catch (Exception exception) {
			return null;
		}
	}

	public static void startScript(String scriptName) {
		getTest(scriptName).log(LogStatus.INFO, "[" + scriptName + "]", "[" + scriptName + "]");
	}

	public static void endScript() {
		ExtentTest test = null;
		test = getTest();
		test.log(LogStatus.INFO, "[/" + test.getTest().getName() + "]", "[/" + test.getTest().getName() + "]");
		if (test != null) {
			getReport().endTest(test);
			extent.flush();
		}
	}

	public static void startTest(String testName) {
		getTest().log(LogStatus.INFO, "[" + testName + "]", "[" + testName + "]");
	}

	public static void endTest(String testName) {
		getTest().log(LogStatus.INFO, "[" + testName + "]", "[" + testName + "]");
	}

	public static void startMethod(String testName) {
		String classMethodName;
		classMethodName = "[" + getSenderName() + "]";
		getTest().log(LogStatus.INFO, classMethodName, classMethodName);
	}

	public static void endMethod() {
		String classMethodName;
		classMethodName = "[/" + getSenderName() + "]";
		getTest().log(LogStatus.INFO, classMethodName, classMethodName);
	}

	public static void reportEventWithScreenshot(LogStatus status, String stepName, String description) {
		String screenShotPath = null;
		SoftAssert softAssert = null;
		String newStepName = stepName.replace("<", "").replace(">", "");
		WebDriver driver = null;
		driver = DriverHelper.getWebDriver();
		if ((driver != null) & (!("null".equals(driver)))) {
			if ((status.equals(LogStatus.PASS)) | (status.equals(LogStatus.FAIL)) | (status.equals(LogStatus.ERROR))
					| (status.equals(LogStatus.WARNING))) {
				screenShotPath = addScreenshot();
				getTest().log(status, newStepName, description + getTest().addScreenCapture(screenShotPath));
				if ((status.equals(LogStatus.FAIL)) | (status.equals(LogStatus.ERROR))) {
					if (ApplicationConstants.isContinue) {
						softAssert = new SoftAssert();
						softAssert.fail(description);
					} else {
						Assert.fail(description);
					}
				}
			} else {
				getTest().log(status, newStepName, description);
			}
		}
	}
	
	public static void reportEventWithoutScreenshot(LogStatus status, String stepName, String description) {
		SoftAssert softAssert = null;
		String newStepName = stepName.replace("<", "").replace(">", "");
		WebDriver driver = null;
		driver = DriverHelper.getWebDriver();
		if ((driver != null) & (!("null".equals(driver)))) {
			if ((status.equals(LogStatus.PASS)) | (status.equals(LogStatus.FAIL)) | (status.equals(LogStatus.ERROR))
					| (status.equals(LogStatus.WARNING))) {
				getTest().log(status, newStepName, description);
				if ((status.equals(LogStatus.FAIL)) | (status.equals(LogStatus.ERROR))) {
					if (ApplicationConstants.isContinue) {
						softAssert = new SoftAssert();
						softAssert.fail(description);
					} else {
						Assert.fail(description);
					}
				}
			} else {
				getTest().log(status, newStepName, description);
			}
		}
	}

	public static ExtentTest getTest() {
		Long threadId;
		threadId = Thread.currentThread().getId();
		if (threadToExtentReportMap.containsKey(threadId)) {
			String testName = threadToExtentReportMap.get(threadId);
			return nameToTestMap.get(testName);
		}
		return null;
	}

	protected synchronized static String addScreenshot() {
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			WebDriver webDriver = DriverHelper.getWebDriver();
			File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			fileInputStreamReader = new FileInputStream(srcFile);
			byte[] bytes = new byte[(int) srcFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encode(bytes));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return "data:image/png;base64," + encodedBase64;
	}

	public static ExtentTest getTest(String scriptName) {
		return getTest(scriptName, "");
	}

	private synchronized static String getSenderName() {
		String className, classSimpleName, methodName, classMethodName;
		className = Thread.currentThread().getStackTrace()[3].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length - 1];
		methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		classMethodName = classSimpleName + ":" + methodName;
		return classMethodName;
	}

}
