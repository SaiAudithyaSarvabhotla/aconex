package com.automation.framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import com.automation.framework.utils.ApplicationConstants;
import com.automation.framework.utils.DriverHelper;
import com.automation.framework.utils.Reporter;
import com.automation.framework.utils.TestDataHelper;

public class FrameworkScript extends TestDataHelper {

	public static String browser = "";
	public static String suiteName = "";
	public static String scriptName = "";
	protected static WebDriver driver;

	@BeforeClass
	public static void onInitilise(ITestContext context) {
		try {
			browser = context.getCurrentXmlTest().getParameter("browser").toUpperCase();
			suiteName = context.getSuite().getName().toString();
			scriptName = context.getName();
			Reporter.startMethod(scriptName);
			if (suiteName.contains("Hybrid")) {
				getTestData(scriptName);
			}
			launchBrowser();
			launchApplication();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void launchBrowser() {
		try {
			switch (browser) {
			case "CHROME":
				System.setProperty("webdriver.chrome.driver", ApplicationConstants.chromerDriverPath);
				driver = new ChromeDriver();
				DriverHelper.setWebDriver(driver);
				break;

			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", ApplicationConstants.fireFoxDriverPath);
				driver = new FirefoxDriver();
				DriverHelper.setWebDriver(driver);
				break;

			case "IE":
				System.setProperty("webdriver.ie.driver", ApplicationConstants.ieDriverPath);
				driver = new InternetExplorerDriver();
				DriverHelper.setWebDriver(driver);
				break;
			}
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void launchApplication() {
		driver.get(ApplicationConstants.aconexApplicationUrl);
	}

	@AfterClass
	public static void onCompleted() {
		DriverHelper.quitWebDriver();
	}

	@AfterSuite
	public final void afterSuite(ITestContext context) {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		Reporter.endMethod();
	}
}
