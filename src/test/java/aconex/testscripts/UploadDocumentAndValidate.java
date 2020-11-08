package aconex.testscripts;

import org.testng.annotations.Test;

import com.automation.framework.core.FrameworkScript;

import aconex.actions.LoginActions;
import aconex.actions.SummaryActions;
import aconex.actions.UploadDocumentActions;

public class UploadDocumentAndValidate extends FrameworkScript {

	@Test (priority = 1)
	public static void uploadDocument() {
		LoginActions.loginIntoAconex();
		SummaryActions.navigateToUploadDocumentScreen();
		UploadDocumentActions.uploadNewDocumentAndValidate();
	}
	
	@Test (priority = 2)
	public static void searchForUploadedDocument() {
		SummaryActions.navigateToDocumentRegisterScreen();
		UploadDocumentActions.searchAndSaveSearch();
		LoginActions.logout();
	}
}
