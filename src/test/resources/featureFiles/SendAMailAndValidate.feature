
Feature: SendAMailAndValidate
  I want to send a mail to group and validate the mail sent successfully

	@SendAMailAndValidate
  Scenario: send email to group
    Given I Login into an aconex application
    When I fill Mandatory fields in New Mail tab 
    And click on Send
    Then Mail should send successfully
    
    Scenario: validate email in All Mails
    When I Navigate to All Mails Screen
    And Search with Email Reference
    Then Mail should displayed in Search Results successfully