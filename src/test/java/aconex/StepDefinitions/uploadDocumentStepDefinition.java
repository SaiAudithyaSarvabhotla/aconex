package aconex.StepDefinitions;

import com.automation.framework.utils.ActionsHelper;
import com.automation.framework.utils.TestDataHelper;

import aconex.actions.LoginActions;
import aconex.actions.SummaryActions;
import aconex.actions.UploadDocumentActions;
import aconex.screens.LoginScreen;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class uploadDocumentStepDefinition {
	@Given("^I Login into an aconex application with new Credentails$")
	public void iLoginIntoAconexApp() throws Throwable {
		TestDataHelper.getTestData("UploadDocumentAndValidate");
		ActionsHelper.click(LoginScreen.button_Login());
		ActionsHelper.waitForElementToBeVisible(LoginScreen.textbox_LoginName());
		LoginActions.loginIntoAconex();
	}

	@When("^I fill Mandatory fields in Upload Document tab$")
	public void iFillMadatoryFieldsInUploadDocument() throws Throwable {
		SummaryActions.navigateToUploadDocumentScreen();
		UploadDocumentActions.fillMandatoryFields();
		UploadDocumentActions.uploadFile();
	}

	@When("^click on Upload$")
	public void clickOnUpload() throws Throwable {
		UploadDocumentActions.submitDocument();
	}

	@Then("^Document should uploaded successfully$")
	public void validateDocument() throws Throwable {
		UploadDocumentActions.captureDocumentRefAndValidate();
	}

	@When("^I Navigate to Document Register Screen$")
	public void navigateToDocumentRegisterScreen() throws Throwable {
		SummaryActions.navigateToDocumentRegisterScreen();
	}

	@When("^Search with uploaded Document Reference$")
	public void searchUploadedDocument() throws Throwable {
		UploadDocumentActions.searchUploadedDocument();
	}

	@Then("^I can Save search results$")
	public void saveSearchResults() throws Throwable {
		UploadDocumentActions.saveSearchResults();
	}

	@Then("^logout From Application$")
	public void logoutApplication() throws Throwable {
		LoginActions.logout();
	}

}
