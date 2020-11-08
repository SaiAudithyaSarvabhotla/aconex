package com.automation.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

public class ExpectedConditions extends ActionsHelper {

	public static void assertTextWithScreenshot(String message) {
		String xpath = "//*[text()='" + message + "']";
		try {
			WebDriverWait dynamicWait = new WebDriverWait(DriverHelper.getWebDriver(),
					ApplicationConstants.dynamicWaitTime);
			dynamicWait.until(
					org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			dynamicWait
					.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			Reporter.reportEventWithScreenshot(LogStatus.PASS, getClassMethodName(),
					"Expected Text " + message + " is displayed in application");
		} catch (Throwable exception) {
			Reporter.reportEventWithScreenshot(LogStatus.ERROR, getClassMethodName(),
					"Expected Text " + message + " is not displayed in application");
		}
	}

	public static void assertWebElementIsDisplayed(WebElement element, String webElementName) {
		try {
			Assert.assertTrue(element.isDisplayed());
			Reporter.reportEventWithScreenshot(LogStatus.PASS, getClassMethodName(),
					"WebElement " + webElementName + " is displayed in application");
		} catch (Throwable exception) {
			Reporter.reportEventWithScreenshot(LogStatus.ERROR, getClassMethodName(),
					"WebElement " + webElementName + " is not displayed in application");
		}
	}

	public static void assertEquals(String expectedMessage, String actualMessage) {
		try {
			Assert.assertEquals(actualMessage, expectedMessage);
			Reporter.reportEventWithScreenshot(LogStatus.PASS, getClassMethodName(),
					"Expected Text " + expectedMessage + " is same as Actual text " + actualMessage);
		} catch (Throwable exception) {
			Reporter.reportEventWithScreenshot(LogStatus.ERROR, getClassMethodName(),
					"Expected Text " + expectedMessage + " is not same as Actual text " + actualMessage);
		}
	}
	
	public static void assertTrue(boolean truevalue,String message) {
		try {
			Assert.assertTrue(truevalue);
			Reporter.reportEventWithScreenshot(LogStatus.PASS, getClassMethodName(),
					"Expected Text " + message + " is displayed");
		} catch (Throwable exception) {
			Reporter.reportEventWithScreenshot(LogStatus.ERROR, getClassMethodName(),
					"Expected Text " + message + " is not displayed");
		}
	}

	public static String getClassMethodName() {
		String className, classSimpleName, methodName, classMethodName;
		className = Thread.currentThread().getStackTrace()[1].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length - 1];
		methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		classMethodName = "[" + classSimpleName + ":" + methodName + "]";
		return classMethodName;
	}

}
