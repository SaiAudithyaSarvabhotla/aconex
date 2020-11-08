package aconex.actions;

import com.automation.framework.core.FrameworkException;
import com.automation.framework.utils.ActionsHelper;
import com.automation.framework.utils.ExpectedConditions;
import com.automation.framework.utils.TestDataHelper;

import aconex.screens.LoginScreen;
import aconex.screens.SummaryScreen;

public class LoginActions extends ActionsHelper {

	public static void loginIntoAconex() {
		waitForElementToBeVisible(LoginScreen.textbox_LoginName());
		setText(LoginScreen.textbox_LoginName(), TestDataHelper.testData("UserName"));
		setText(LoginScreen.textbox_Password(), TestDataHelper.testData("Password"));
		click(LoginScreen.button_Login());
		waitForElementToBeVisible(SummaryScreen.logo_OracleAconex());
		ExpectedConditions.assertWebElementIsDisplayed(SummaryScreen.logo_OracleAconex(), "Oracle Aconex Logo");
		switchToFrame(0);
		waitForElementToBeVisible(SummaryScreen.header_MyTasks());
	}

	public static void logout() {
		try {
			switchToDefault();
			click(SummaryScreen.button_UserName());
			click(SummaryScreen.link_Logout());
			waitForElementToBeVisible(LoginScreen.button_Login());
			ExpectedConditions.assertWebElementIsDisplayed(LoginScreen.button_Login(), "Log in");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

}
