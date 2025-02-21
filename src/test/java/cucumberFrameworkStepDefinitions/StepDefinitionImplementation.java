package cucumberFrameworkStepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import vijayFramework.TestComponents.BaseTest;
import vijayFramework.Tests.*;

import java.io.IOException;

public class StepDefinitionImplementation extends BaseTest {

    public LandingPage landingPage;
    public ProductsPage productsPage;
    public ViewCartPage viewCartPage;
    public CheckOutPage checkOutPage;
    public OrderConfirmationPage orderConfirmationPage;

    @Given("I landed on Ecommerce Page.")
    public void i_landed_on_ecommerce_page() throws IOException {
        landingPage=launchApplication();
    }

    @Given("^I Loggedin with username (.+) and password (.+)$")
    public void i_loggedin_with_username_and_password(String username, String password) {
        productsPage= landingPage.login(username,password);
    }
    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productname) {
        productsPage.addProductToCart(productname);
    }
    @And("^checkout (.+) and submit the order$")
    public void checkout_and_submit_the_order(String productname) {
        viewCartPage=productsPage.clickOnViewCartButton();

        viewCartPage.checkProductAvailableOnCart(productname);
        checkOutPage=viewCartPage.clickOnCheckOutButton();

        String countryName="india";
        orderConfirmationPage= checkOutPage.selectCountryOriginAndProceedWithCheckOut(countryName);
    }
    @Then("^\"([^\"]*)\" message is displayed on confirmation page$")
    public void something_message_is_displayed_on_confirmation_page(String expMessage) {

        String expectedMessageToBeDisplayed="Thankyou for the order.";
        orderConfirmationPage.validateOrderConfirmationMessageDisplayed(expMessage);
        driver.close();

    }}
