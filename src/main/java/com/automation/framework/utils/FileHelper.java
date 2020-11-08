package com.automation.framework.utils;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;

import com.automation.framework.core.FrameworkException;

public class FileHelper extends ActionsHelper {

	public static void uploadFile(String filePath, String xpath) {
		try {
			String fileUploadPath = new File(filePath).getAbsolutePath();
			LocalFileDetector detector = new LocalFileDetector();
			RemoteWebElement element = (RemoteWebElement) DriverHelper.getWebDriver().findElement(By.xpath(xpath));
			element.setFileDetector(detector);
			element.sendKeys(fileUploadPath);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

}
