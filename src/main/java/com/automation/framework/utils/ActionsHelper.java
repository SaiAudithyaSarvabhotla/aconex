package com.automation.framework.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.framework.core.FrameworkException;

public class ActionsHelper {

	public static String CharList = "abcdefghijklmnopqrstuvwxyz1234567890";

	public static void selectByText(WebElement element, String visibleText) {
		Select select = null;
		try {
			select = new Select(element);
			select.selectByVisibleText(visibleText);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void setText(WebElement element, String text) {
		try {
			element.clear();
			element.click();
			element.sendKeys(text);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void click(WebElement element) {
		try {
			waitUntilClickable(element);
			element.click();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void clearText(WebElement element) {
		try {
			waitUntilClickable(element);
			element.clear();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void waitForElementToBeVisible(WebElement element) {
		WebDriverWait dynamicWait;
		try {
			dynamicWait = new WebDriverWait(DriverHelper.getWebDriver(), ApplicationConstants.dynamicWaitTime);
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(element));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void waitUntilClickable(WebElement element) {
		WebDriverWait dynamicWait;
		try {
			dynamicWait = new WebDriverWait(DriverHelper.getWebDriver(), ApplicationConstants.dynamicWaitTime);
			dynamicWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(element));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void waitUntilPresentOfElement(String xpath) {
		WebDriverWait dynamicWait;
		try {
			dynamicWait = new WebDriverWait(DriverHelper.getWebDriver(), ApplicationConstants.dynamicWaitTime);
			dynamicWait.until(
					org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			dynamicWait
					.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void switchToFrame(int index) {
		try {
			DriverHelper.getWebDriver().switchTo().frame(index);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void switchToFrame(String frameName) {
		try {
			DriverHelper.getWebDriver().switchTo().frame(frameName);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void switchToParentFrame() {
		try {
			DriverHelper.getWebDriver().switchTo().parentFrame();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void switchToDefault() {
		try {
			DriverHelper.getWebDriver().switchTo().defaultContent();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void waitForLoadingSignDisappear(String xpath) {
		WebDriverWait dynamicWait = null;
		try {
			dynamicWait = new WebDriverWait(DriverHelper.getWebDriver(), ApplicationConstants.dynamicWaitTime);
			dynamicWait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(xpath))));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static String generateRandomString() {
		StringBuffer randString = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int number = gerRandomNumber();
			char ch = CharList.charAt(number);
			randString.append(ch);
		}
		return randString.toString();
	}

	public static int gerRandomNumber() {
		int randNum = 0;
		Random random = new Random();
		randNum = random.nextInt(CharList.length());
		if (randNum - 1 == -1) {
			return randNum;
		} else {
			return randNum - 1;
		}
	}

	public static String currentDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return df.format(date);

	}

}
