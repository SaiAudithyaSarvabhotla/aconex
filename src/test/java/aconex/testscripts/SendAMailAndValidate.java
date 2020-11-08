package aconex.testscripts;

import org.testng.annotations.Test;

import com.automation.framework.core.FrameworkScript;

import aconex.actions.LoginActions;
import aconex.actions.MailActions;
import aconex.actions.SummaryActions;

public class SendAMailAndValidate extends FrameworkScript {

	@Test(priority=1)
	public static void sendAMailToGlobalGroup() {
		LoginActions.loginIntoAconex();
		SummaryActions.navigateToBlankMailScreen();
		MailActions.sendAMailAndValidate();
	}

	@Test(priority=2)
	public static void searchEmailAndValidate() {
		SummaryActions.navigateToSearchMail();
		MailActions.searchAndViewSentEmail();
		LoginActions.logout();
	}

}
