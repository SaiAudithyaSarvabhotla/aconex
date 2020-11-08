package aconex.actions;

import com.automation.framework.core.FrameworkException;
import com.automation.framework.utils.ActionsHelper;
import com.automation.framework.utils.ExpectedConditions;

import aconex.screens.DocumentRegisterScreen;
import aconex.screens.NewMailScreen;
import aconex.screens.SearchMailScreen;
import aconex.screens.SummaryScreen;
import aconex.screens.UploadDocumentScreen;

public class SummaryActions extends ActionsHelper {

	public static void navigateToBlankMailScreen() {
		try {
			switchToDefault();
			click(SummaryScreen.button_Mail());
			waitForElementToBeVisible(SummaryScreen.button_BlankMail());
			click(SummaryScreen.button_BlankMail());
			switchToFrame(0);
			waitForElementToBeVisible(NewMailScreen.header_NewMail());
			ExpectedConditions.assertWebElementIsDisplayed(NewMailScreen.header_NewMail(), "New Mail");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void navigateToSearchMail() {
		try {
			switchToDefault();
			click(SummaryScreen.button_Mail());
			waitForElementToBeVisible(SummaryScreen.button_AllMails());
			click(SummaryScreen.button_AllMails());
			switchToFrame(0);
			waitForElementToBeVisible(SearchMailScreen.textbox_Search());
			ExpectedConditions.assertWebElementIsDisplayed(SearchMailScreen.header_SearchMail(), "Search Mail");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void navigateToUploadDocumentScreen() {
		try {
			switchToDefault();
			click(SummaryScreen.button_Documents());
			waitForElementToBeVisible(SummaryScreen.button_UploadNewDocument());
			click(SummaryScreen.button_UploadNewDocument());
			switchToFrame(0);
			waitForElementToBeVisible(UploadDocumentScreen.header_UploadDocument());
			ExpectedConditions.assertWebElementIsDisplayed(UploadDocumentScreen.header_UploadDocument(),
					"Upload Document");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void navigateToDocumentRegisterScreen() {
		try {
			switchToDefault();
			click(SummaryScreen.button_Documents());
			waitForElementToBeVisible(SummaryScreen.button_DocumentRegister());
			click(SummaryScreen.button_DocumentRegister());
			switchToFrame(0);
			waitForElementToBeVisible(DocumentRegisterScreen.header_DocumentRegister());
			ExpectedConditions.assertWebElementIsDisplayed(DocumentRegisterScreen.header_DocumentRegister(),
					"Document Register");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}
}
