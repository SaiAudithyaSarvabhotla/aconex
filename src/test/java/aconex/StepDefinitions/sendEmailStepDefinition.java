package aconex.StepDefinitions;

import com.automation.framework.utils.TestDataHelper;

import aconex.actions.LoginActions;
import aconex.actions.MailActions;
import aconex.actions.SummaryActions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class sendEmailStepDefinition {
	@Given("^I Login into an aconex application$")
	public void iLoginIntoAconexApp() throws Throwable {
		TestDataHelper.getTestData("SendAMailAndValidate");
		LoginActions.loginIntoAconex();
	}

	@When("^I fill Mandatory fields in New Mail tab$")
	public void iFillMadatoryFieldsInNewMail() throws Throwable {
		SummaryActions.navigateToBlankMailScreen();
		MailActions.selectToReceipientFromGlobalDirectory();
		MailActions.fillMandatoryFields();
	}

	@When("^click on Send$")
	public void clickOnSend() throws Throwable {
		MailActions.sendMail();
	}

	@Then("^Mail should send successfully$")
	public void validateSentMail() throws Throwable {
		MailActions.validateSuccessMail();
	}

	@When("^I Navigate to All Mails Screen$")
	public void iSearchWithMailRefInAllMails() throws Throwable {
		SummaryActions.navigateToSearchMail();
	}

	@When("^Search with Email Reference$")
	public void clickOnSearch() throws Throwable {
		MailActions.searchEmailWithRef();
	}

	@Then("^Mail should displayed in Search Results successfully$")
	public void mailShouldDisplayedinSearchResults() throws Throwable {
		MailActions.validateEmailDetails();
		LoginActions.logout();
	}

}
