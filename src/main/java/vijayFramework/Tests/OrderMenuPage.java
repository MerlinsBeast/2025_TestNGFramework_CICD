package vijayFramework.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import vijayFramework.AbstractComponents.AbstractComponent;

import java.util.List;

public class OrderMenuPage extends AbstractComponent {

    WebDriver driver;
    public OrderMenuPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(xpath="//table//tbody//tr//td[2]")
    List<WebElement> listOfItemsAvailableOnOrderMenu;

    public void checkOrderAvailability(String productName){
       Boolean isItemAvailable=listOfItemsAvailableOnOrderMenu.stream().anyMatch(eachItem->eachItem.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(isItemAvailable);
    }



}
