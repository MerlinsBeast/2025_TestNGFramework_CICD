package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) {
        WebDriver driver= new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String product1="ZARA COAT 3";
        String product2="ADIDAS ORIGINAL";
        LandingPage landingPage= new LandingPage(driver);

        driver.findElement(By.cssSelector("#userEmail")).sendKeys("yd276vijay@gmail.com");
        driver.findElement(By.cssSelector("#userPassword")).sendKeys("Merlin@01Magic");
        driver.findElement(By.cssSelector("#login")).click();
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("[routerlink*='cart']"))));
        List<WebElement> items= driver.findElements(By.xpath("//*[contains(@class,'col-lg-4')]"));
        WebElement elementRetrieved=items.stream().filter(s->s.findElement(By.xpath("//*[contains(@class,'col-lg-4')]//div[@class='card-body']//h5//b")).getText().equalsIgnoreCase(product1)).findFirst().orElse(null);
        elementRetrieved.findElement(By.xpath("//parent::h5//parent::div//button[contains(text(),' Add To Cart')]")).click();


        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class,'success ng-animating')]"))));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[contains(@class,'success ng-animating')]"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cardProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean flag=cardProducts.stream().anyMatch(cardProduct->cardProduct.getText().equalsIgnoreCase(product1));
        Assert.assertTrue(flag);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"india").build().perform();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        String actMessage=driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(actMessage.equalsIgnoreCase("Thankyou for the order."));

        driver.close();

    }
}
