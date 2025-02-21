package vijayFramework.Tests;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vijayFramework.TestComponents.BaseTest;
import vijayFramework.TestComponents.RetryfailedTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class StandAloneTestModified extends BaseTest {
    String product1="ZARA COAT 3";
    @Test(dataProvider = "getData")
    public void ZaddProduct(HashMap<String,String> details) throws IOException {
        ProductsPage productsPage= landingPage.login(details.get("username"),details.get("password"));

        productsPage.addProductToCart(product1);
        ViewCartPage viewCartPage=productsPage.clickOnViewCartButton();

        viewCartPage.checkProductAvailableOnCart(product1);
        CheckOutPage checkOutPage=viewCartPage.clickOnCheckOutButton();

        String countryName="india";
        OrderConfirmationPage orderConfirmationPage= checkOutPage.selectCountryOriginAndProceedWithCheckOut(countryName);

        String expectedMessageToBeDisplayed="Thankyou for the order.";
        orderConfirmationPage.validateOrderConfirmationMessageDisplayed(expectedMessageToBeDisplayed);
    }

    @Test(dependsOnMethods = "ZaddProduct", retryAnalyzer = RetryfailedTest.class)
    public void OrderConfirmation() throws IOException {
        ProductsPage productsPage= landingPage.login("yd276vijay@gmail.com","Merlin@01Magic");
        OrderMenuPage orderMenuPage=productsPage.clickOnOrderTab();
        orderMenuPage.checkOrderAvailability(product1);

    }

    @DataProvider
    public Object[][] getData() throws IOException {
       List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/vijayFramework/data/PurchaseOrder.json");
        return new Object[][]{{data.get(0)},{data.get(1)}};
    }

}
