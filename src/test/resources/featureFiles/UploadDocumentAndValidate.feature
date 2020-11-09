
Feature: Upload a new Document 
  I want to upload a document so that I can save search and view

  Scenario: upload a document
    Given I Login into an aconex application with new Credentails
    When I fill Mandatory fields in Upload Document tab 
    And click on Upload
    Then Document should uploaded successfully
    
    Scenario: search uploaded document and save search
    When I Navigate to Document Register Screen
    And Search with uploaded Document Reference
    Then I can Save search results
    And logout From Application