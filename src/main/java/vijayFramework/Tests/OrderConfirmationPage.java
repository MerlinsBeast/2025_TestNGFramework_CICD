package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import vijayFramework.AbstractComponents.AbstractComponent;

public class OrderConfirmationPage extends AbstractComponent {

    WebDriver driver;
    public OrderConfirmationPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(css=".hero-primary")
    WebElement confirmationMessageLabel;

    By optionDisplayed= By.cssSelector(".ta-results");

    public void validateOrderConfirmationMessageDisplayed(String expectedMsg){
        String actMessage=confirmationMessageLabel.getText();
        Assert.assertTrue(actMessage.equalsIgnoreCase(expectedMsg));
    }

}
