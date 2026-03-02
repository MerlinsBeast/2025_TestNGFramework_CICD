@tag
Feature: Purchase the order from Ecommerce Website
  I want to use this template for my feature file

  Background:
    Given I landed on Ecommerce Page.
  @tag1
  Scenario Outline: Positive Test of Submitting the order
    Given I Loggedin with username <name> and password <password>
    When I add product <productName> to Cart
    And checkout <productName> and submit the order
    Then "Thankyou for the order." message is displayed on confirmation page

    Examples:

      | name | password | productName |

      | yd276vijay@gmail.com | Merlin@01Magic | ZARA COAT 3 |