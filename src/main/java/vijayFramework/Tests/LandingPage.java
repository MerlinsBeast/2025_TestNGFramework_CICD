package vijayFramework.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vijayFramework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

    WebDriver driver;
    public LandingPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(css="#userEmail")
    WebElement userName;
    @FindBy(css="#userPassword")
    WebElement passWord;
    @FindBy(css="#login")
    WebElement loginButton;
    @FindBy(css="[class*=flyInOut]")
    WebElement loginErrorToast;


    public ProductsPage login(String username, String password){
        userName.sendKeys(username);
        passWord.sendKeys(password);
        loginButton.click();
        return new ProductsPage(driver);
    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client");
    }
    public void goToSpecificURL(String url){
        driver.get(url);
    }

    public String getErrorMessage(){
        waitForElementToAppear(loginErrorToast);
        return loginErrorToast.getText();
    }
}
