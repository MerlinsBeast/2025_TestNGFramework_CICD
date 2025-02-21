package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vijayFramework.AbstractComponents.AbstractComponent;

import java.util.List;

public class ProductsPage extends AbstractComponent {

    WebDriver driver;
    public ProductsPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(css="[routerlink*='cart']")
    WebElement cartButton;
    @FindBy(xpath="//*[contains(@class,'col-lg-4')]")
    List<WebElement> listOfProductsOnPage;
    @FindBy(xpath="//*[contains(@class,'col-lg-4')]//div[@class='card-body']//h5//b")
    WebElement productNameLabel;
    @FindBy(xpath="//div[contains(@class,'success ng-animating')]")
    WebElement toastSection;
    @FindBy(css="[routerlink*='cart']")
    WebElement cartIcon;
    @FindBy(xpath="//i[@class='fa fa-handshake-o']//parent::button")
            WebElement orderMenu;

        By product= By.cssSelector("[routerlink*='cart']");
    By addToCartButton= By.xpath("//parent::h5//parent::div//button[contains(text(),' Add To Cart')]");
    By toastMessageSection= By.xpath("//div[contains(@class,'success ng-animating')]");

    public List<WebElement> getProductsList(){
        waitForElementToAppear(product);
        return listOfProductsOnPage;
    }

    public WebElement getProductByName(String productName){
        return getProductsList().stream().filter(s->s.findElement(By.xpath("//*[contains(@class,'col-lg-4')]//div[@class='card-body']//h5//b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
    }

    public void addProductToCart(String productName){
        WebElement elementRetrieved=getProductByName(productName);
        elementRetrieved.findElement(addToCartButton).click();
        waitForElementToAppear(toastMessageSection);
        waitForElementToDisappear(toastSection);
    }
    public ViewCartPage clickOnViewCartButton(){
        cartIcon.click();
        return new ViewCartPage(driver);
    }

    public OrderMenuPage clickOnOrderTab(){
        waitForElementToBeClickable(orderMenu);
        orderMenu.click();
        return new OrderMenuPage(driver);
    }

}
