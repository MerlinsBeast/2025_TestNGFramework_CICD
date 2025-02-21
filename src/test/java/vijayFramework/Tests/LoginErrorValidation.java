package vijayFramework.Tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vijayFramework.TestComponents.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginErrorValidation extends BaseTest {
    @Test(dependsOnMethods = "addProductMultipleTestsInSameClass" )
    public void addProduct() throws IOException {
        ProductsPage productsPage = landingPage.login("yd276vijay@gmail.com", "Merl01Magic");
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @Test
    public void addProductMultipleTestsInSameClass() throws IOException {
        String product1="ZARA COAT 3";
        String product2="ADIDAS ORIGINAL";
        ProductsPage productsPage= landingPage.login("yd276vijay@gmail.com","Merlin@01Magic");

        productsPage.addProductToCart(product1);
        ViewCartPage viewCartPage=productsPage.clickOnViewCartButton();

        viewCartPage.checkProductAvailableOnCart(product1);
        CheckOutPage checkOutPage=viewCartPage.clickOnCheckOutButton();

        String countryName="india";
        OrderConfirmationPage orderConfirmationPage= checkOutPage.selectCountryOriginAndProceedWithCheckOut(countryName);

        String expectedMessageToBeDisplayed="Thankyou for the order.";
        orderConfirmationPage.validateOrderConfirmationMessageDisplayed(expectedMessageToBeDisplayed);


    }

    @Test(dataProvider = "getDataThroughExcel")
    public void addProductExcelErrorValidation(String username, String password) throws IOException {
        ProductsPage productsPage = landingPage.login(username, password);
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @DataProvider(name="getDataThroughExcel")
    public String[][] getDataThroughExcel() throws IOException {
        String [][] dataReceived=getDataFromExcel();
        return dataReceived;
    }

}
