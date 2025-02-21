package vijayFramework.Tests;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vijayFramework.AbstractComponents.AbstractComponent;

import java.util.List;

public class ViewCartPage extends AbstractComponent {

    WebDriver driver;
    public ViewCartPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(css=".cartSection h3")
    List<WebElement> listOfItemsOnViewCartPage;
    @FindBy(css=".totalRow button")
    WebElement checkButton;

    public void checkProductAvailableOnCart(String productName){
        Boolean flag=listOfItemsOnViewCartPage.stream().anyMatch(cardProduct->cardProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(flag);
    }

    public CheckOutPage clickOnCheckOutButton(){
        checkButton.click();
        return new CheckOutPage(driver);
    }


}
