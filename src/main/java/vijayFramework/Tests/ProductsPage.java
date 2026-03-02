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
    // use a relative XPath; when used via element.findElement the leading dot
    // ensures the search is scoped inside the product element rather than the
    // entire document.  Also trim whitespace in the text check.
    By addToCartButton = By.xpath(".//button[contains(text(),'Add To Cart')]");
    By toastMessageSection= By.xpath("//div[contains(@class,'success ng-animating')]");

    public List<WebElement> getProductsList(){
        waitForElementToAppear(product);
        return listOfProductsOnPage;
    }

    public WebElement getProductByName(String productName){
        /*
         * Original locator was absolute and returned the first matching <b> element
         * on the entire page for every product.  That meant the filter compared the
         * same text repeatedly and never found a match for products other than the
         * first one (or returned null).  To fix this we scope the lookup to the
         * individual `s` element by using a relative XPath (note the leading dot).
         */
        return getProductsList().stream()
                .filter(s -> {
                    WebElement nameLabel = s.findElement(By.xpath(".//div[@class='card-body']//h5//b"));
                    return nameLabel.getText().equalsIgnoreCase(productName);
                })
                .findFirst()
                .orElse(null);
    }

    public void addProductToCart(String productName){
        WebElement elementRetrieved = getProductByName(productName);
        if (elementRetrieved == null) {
            throw new RuntimeException("Product '" + productName + "' not found on the page");
        }
        // addToCartButton is already relative to the product element, but ensure
        // we use dot syntax when building complex XPath if we ever change it.
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
