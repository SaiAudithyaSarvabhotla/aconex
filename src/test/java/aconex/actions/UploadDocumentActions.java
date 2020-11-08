package aconex.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.automation.framework.core.FrameworkException;
import com.automation.framework.utils.ActionsHelper;
import com.automation.framework.utils.DriverHelper;
import com.automation.framework.utils.ExpectedConditions;
import com.automation.framework.utils.FileHelper;
import com.automation.framework.utils.TestDataHelper;

import aconex.screens.DocumentRegisterScreen;
import aconex.screens.SelectAttributesScreen;
import aconex.screens.UploadDocumentScreen;

public class UploadDocumentActions extends ActionsHelper {

	public static String uploadedDocNumber = null;

	public static void uploadNewDocumentAndValidate() {
		try {
			fillMandatoryFields();
			uploadFile();
			submitDocument();
			captureDocumentRefAndValidate();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void searchAndSaveSearch() {
		try {
			searchUploadedDocument();
			saveSearchResults();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void fillMandatoryFields() {
		try {
			String documentNumber = generateRandomString();
			setText(UploadDocumentScreen.textbox_DocumentNo(), documentNumber);
			setText(UploadDocumentScreen.textbox_Revision(), TestDataHelper.testData("Revision"));
			setText(UploadDocumentScreen.textbox_Title(), TestDataHelper.testData("Title") + " " + documentNumber);
			selectByText(UploadDocumentScreen.dropdown_Type(), TestDataHelper.testData("Type"));
			selectByText(UploadDocumentScreen.dropdown_Status(), TestDataHelper.testData("Status"));
			selectByText(UploadDocumentScreen.dropdown_Discipline(), TestDataHelper.testData("Discipline"));
			selectByText(UploadDocumentScreen.dropdown_VDRCode(), TestDataHelper.testData("VDRCode"));
			selectByText(UploadDocumentScreen.dropdown_Category(), TestDataHelper.testData("Category"));
			selectAttributesAndGreenStarCategory();
			setText(UploadDocumentScreen.textbox_PrintSize(), TestDataHelper.testData("PrintSize"));
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void uploadFile() {
		try {
			FileHelper.uploadFile(TestDataHelper.testData("FilePath"), "//input[@name='associatedfile_0']");
			ExpectedConditions.assertTextWithScreenshot("Sample.png");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void submitDocument() {
		try {
			click(UploadDocumentScreen.button_Upload());
			Thread.sleep(2000);
			waitForElementToBeVisible(UploadDocumentScreen.header_Success());
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void captureDocumentRefAndValidate() {
		try {
			ExpectedConditions
					.assertTextWithScreenshot("The following document has been uploaded to your organization");
			WebElement uploadedDocId = DriverHelper.getWebDriver()
					.findElement(By.xpath("//li[@class='message success']//h3/b"));
			uploadedDocNumber = uploadedDocId.getText();
			ExpectedConditions.assertTrue(true, "Uploaded Doc ID is :" + uploadedDocNumber);
			System.out.println("Document # is :" + uploadedDocNumber);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void selectAttributesAndGreenStarCategory() {
		try {
			click(UploadDocumentScreen.textbox_Attribute1());
			swithBtwFrames("Parent");
			waitForElementToBeVisible(UploadDocumentScreen.header_Attribute());
			selectAttributes(1, TestDataHelper.testData("Attribute"));
			click(SelectAttributesScreen.button_AddItemlist1());
			click(UploadDocumentScreen.button_AttributeOk());
			swithBtwFrames("Default");
			click(UploadDocumentScreen.textbox_GreenStarCategory());
			swithBtwFrames("Parent");
			selectAttributes(2, TestDataHelper.testData("GreenStarCategory"));
			click(SelectAttributesScreen.button_AddItemlist2());
			click(UploadDocumentScreen.button_GreenStarCategoryOk());
			swithBtwFrames("Default");
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void selectAttributes(int attributeLevel, String attrName) {
		String id = null;
		String[] attrNames = attrName.split(",");
		try {
			for (int i = 0; i < attrNames.length; i++) {
				switch (attributeLevel) {
				case 1:
					id = "attribute1_0_bidi";
					break;
				case 2:
					id = "attribute2_0_bidi";
					break;
				}
				WebElement attribute = DriverHelper.getWebDriver().findElement(
						By.xpath("//div[@id='" + id + "']//option[contains(text(),'" + attrNames[i] + "')]"));
				click(attribute);
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void searchUploadedDocument() {
		try {
			clearText(DocumentRegisterScreen.textbox_Search());
			setText(DocumentRegisterScreen.textbox_Search(), uploadedDocNumber);
			click(DocumentRegisterScreen.button_Search());
			waitForElementToBeVisible(DocumentRegisterScreen.table_searchResults());
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}
	
	public static void saveSearchResults() {
		try {
			String saveSearchName = ActionsHelper.generateRandomString();
			click(DocumentRegisterScreen.button_SaveSearchAs());
			waitForElementToBeVisible(DocumentRegisterScreen.header_SaveSearch());
			setText(DocumentRegisterScreen.textbox_Name(), saveSearchName);
			click(DocumentRegisterScreen.radio_SharedToOrganization());
			click(DocumentRegisterScreen.button_Save());
			ExpectedConditions.assertTrue(true, "Search Saved Successfully and Name is " + saveSearchName);
			System.out.println("Search Saved Name is :" + saveSearchName);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void swithBtwFrames(String frameSelection) throws InterruptedException {
		if (frameSelection.equalsIgnoreCase("Parent")) {
			switchToParentFrame();
			switchToFrame(0);
			Thread.sleep(2000);
		} else if (frameSelection.equalsIgnoreCase("Default")) {
			switchToDefault();
			switchToFrame(0);
		}
	}

}
