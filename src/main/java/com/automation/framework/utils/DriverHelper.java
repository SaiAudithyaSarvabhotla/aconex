package com.automation.framework.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.automation.framework.core.FrameworkException;

public class DriverHelper {
	private static Map<Long, WebDriver> threadDriver = new HashMap<Long, WebDriver>();

	public static void setWebDriver(WebDriver driver) {
		Long threadId;
		try {
			threadId = Thread.currentThread().getId();
			if (!threadDriver.containsKey(threadId)) {
				threadDriver.put(threadId, driver);
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static WebDriver getWebDriver() {
		Long threadId;
		try {
			threadId = Thread.currentThread().getId();
			if (threadDriver.containsKey(threadId)) {
				return threadDriver.get(threadId);
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		return null;
	}

	public static void quitWebDriver() {
		Long threadId;
		try {
			threadId = Thread.currentThread().getId();
			if (threadDriver.containsKey(threadId)) {
				threadDriver.get(threadId).quit();
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
		threadDriver.clear();
	}

}
