package aconex.StepDefinitions;

import com.automation.framework.utils.TestDataHelper;

import aconex.actions.LoginActions;
import aconex.actions.SummaryActions;
import aconex.actions.UploadDocumentActions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class uploadDocumentStepDefinition {
	@When("^I fill Mandatory fields in Upload Document tab$")
	public void iFillMadatoryFieldsInUploadDocument() throws Throwable {
		TestDataHelper.getTestData("UploadDocumentAndValidate");
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
