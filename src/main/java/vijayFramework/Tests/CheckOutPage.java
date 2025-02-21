package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vijayFramework.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {

    WebDriver driver;
    public CheckOutPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(css="[placeholder='Select Country']")
    WebElement countryDropDown;
    @FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
    WebElement countryOption;
    @FindBy(css=".action__submit")
    WebElement placeOrderButton;
    By optionDisplayed= By.cssSelector(".ta-results");

    public OrderConfirmationPage selectCountryOriginAndProceedWithCheckOut(String countryToSelect){
        Actions a = new Actions(driver);
        a.sendKeys(countryDropDown,countryToSelect).build().perform();
        waitForElementToAppear(optionDisplayed);
        countryOption.click();
        placeOrderButton.click();
        return new OrderConfirmationPage(driver);
    }

}
