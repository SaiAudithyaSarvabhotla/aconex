package aconex.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.automation.framework.core.FrameworkException;
import com.automation.framework.utils.ActionsHelper;
import com.automation.framework.utils.DriverHelper;
import com.automation.framework.utils.ExpectedConditions;
import com.automation.framework.utils.TestDataHelper;

import aconex.screens.NewMailScreen;
import aconex.screens.SearchMailScreen;
import aconex.screens.SelectAttributesScreen;
import aconex.screens.ToSearchDirectoryScreen;
import aconex.screens.ViewMailScreen;

public class MailActions extends ActionsHelper {

	public static String mailRefNumber = null;

	public static void sendAMailAndValidate() {
		try {
			selectToReceipientFromGlobalDirectory();
			fillMandatoryFields();
			sendMail();
			validateSuccessMail();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void searchAndViewSentEmail() {
		try {
			searchEmailWithRef();
			validateEmailDetails();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	@SuppressWarnings("unused")
	public static void selectToReceipientFromGlobalDirectory() {
		try {
			String groupName = TestDataHelper.testData("GroupName");
			String familyName = TestDataHelper.testData("FamilyName");
			click(NewMailScreen.button_ToDirectory());
			waitForElementToBeVisible(ToSearchDirectoryScreen.button_Ok());
			ExpectedConditions.assertWebElementIsDisplayed(ToSearchDirectoryScreen.header_SearchDirectory(),
					"Search Directory");
			click(ToSearchDirectoryScreen.button_Global());
			setText(ToSearchDirectoryScreen.textbox_GroupName(), groupName);
			setText(ToSearchDirectoryScreen.textbox_FamilyName(), familyName);
			click(ToSearchDirectoryScreen.button_Search());
			waitForElementToBeVisible(ToSearchDirectoryScreen.table_searchResults());
			List<WebElement> results = DriverHelper.getWebDriver().findElements(By.xpath("//tr[@class='dataRow']"));
			for (int i = 1; i <= results.size(); i++) {
				WebElement GroupName = DriverHelper.getWebDriver()
						.findElement(By.xpath("//tr[@class='dataRow'][" + i + "]/td[2]"));
				if (GroupName.getText().contains(groupName + " " + familyName)) {
					WebElement selectGroupName = DriverHelper.getWebDriver()
							.findElement(By.xpath("//tr[@class='dataRow'][" + i + "]/td[1]/input[@type='checkbox']"));
					click(selectGroupName);
				}
				break;
			}
			click(ToSearchDirectoryScreen.button_ToReceipient());
			click(ToSearchDirectoryScreen.button_Ok());
			WebElement toReceipient = DriverHelper.getWebDriver().findElement(By.xpath("//tr[@class='contentcell ']"));
			waitForElementToBeVisible(toReceipient);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void fillMandatoryFields() {
		try {
			selectByText(NewMailScreen.dropdown_Type(), TestDataHelper.testData("Type"));
			NewMailScreen.textbox_Subject().sendKeys(TestDataHelper.testData("Subject"));
			selectAttributes1And2();
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void sendMail() {
		try {
			click(NewMailScreen.button_Send());
			Thread.sleep(2000);
			switchToDefault();
			switchToFrame(0);
			waitForElementToBeVisible(ViewMailScreen.header_ViewMail());

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void validateSuccessMail() {
		try {
			ExpectedConditions.assertTextWithScreenshot(TestDataHelper.testData("Subject"));
			WebElement mailRefElement = DriverHelper.getWebDriver()
					.findElement(By.xpath("//div[@data-automation-id='mailHeader-refNumber-value']"));
			mailRefNumber = mailRefElement.getText();
			System.out.println(mailRefNumber);
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void selectAttributes1And2() {
		try {

			click(NewMailScreen.textbox_Attribute1());
			switchToParentFrame();
			switchToFrame(0);
			Thread.sleep(2000);
			// waitForLoadingSignDisappear("//div[@class='uiLoadingBlocker']");
			waitForElementToBeVisible(SelectAttributesScreen.header_SelectAttributes());
			selectAttributes(1, TestDataHelper.testData("Attribute1"));
			click(SelectAttributesScreen.button_AddItemlist1());
			selectAttributes(2, TestDataHelper.testData("Attribute2"));
			click(SelectAttributesScreen.button_AddItemlist2());
			click(SelectAttributesScreen.button_Ok());
			switchToDefault();
			switchToFrame(0);
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
					id = "attributeBidi_PRIMARY_ATTRIBUTE";
					break;
				case 2:
					id = "attributeBidi_SECONDARY_ATTRIBUTE";
					break;
				case 3:
					id = "attributeBidi_THIRD_ATTRIBUTE";
					break;
				case 4:
					id = "attributeBidi_FOURTH_ATTRIBUTE";
					break;
				}

				WebElement attribute = DriverHelper.getWebDriver().findElement(
						By.xpath("//div[@id='" + id + "']//option[contains(text(),'" + attrNames[i] + "')]"));
				waitForElementToBeVisible(attribute);
				click(attribute);
			}
		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void searchEmailWithRef() {
		try {
			setText(SearchMailScreen.textbox_Search(), mailRefNumber);
			click(SearchMailScreen.button_Search());
			waitForElementToBeVisible(SearchMailScreen.table_searchResults());
			ExpectedConditions.assertWebElementIsDisplayed(SearchMailScreen.table_searchResults(),
					"Search Mail Results");
			List<WebElement> searchResults = DriverHelper.getWebDriver()
					.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
			for (int i = 0; i < searchResults.size(); i++) {
				WebElement searchResult = DriverHelper.getWebDriver()
						.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[3]"));
				ExpectedConditions.assertEquals(searchResult.getText().toString().trim(), mailRefNumber);
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

	public static void validateEmailDetails() {
		try {
			List<WebElement> searchResults = DriverHelper.getWebDriver()
					.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
			for (int i = 0; i < searchResults.size(); i++) {
				WebElement searchResult = DriverHelper.getWebDriver()
						.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[3]"));
				ExpectedConditions.assertEquals(searchResult.getText().toString().trim(), mailRefNumber);
			}

		} catch (Exception exception) {
			new FrameworkException(exception);
		}
	}

}
