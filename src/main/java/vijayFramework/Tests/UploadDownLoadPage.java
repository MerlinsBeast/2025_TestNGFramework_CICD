package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import vijayFramework.AbstractComponents.AbstractComponent;

import java.io.IOException;
import java.util.List;

public class UploadDownLoadPage extends AbstractComponent {

    WebDriver driver;
    public UploadDownLoadPage(WebDriver driver){
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


    //PageFactory
    @FindBy(id="downloadButton")
WebElement downloadButton;
    @FindBy(id="fileinput")
    WebElement uploadButton;
    @FindBy(xpath = "//*[@class='Toastify__toast-body']//div[2]")
    WebElement toastMessage;
    @FindBy(xpath = "//div[contains(text(),'Apple')]//parent::div//following-sibling::div[2]")
    WebElement valueForAppleItemsInTable;


    public void editDownloadedExcelAndUploadFile(String filepath,String SheetName,int amountToUpdate) throws IOException {
        WebElement uploadButton=driver.findElement(By.id("fileinput"));
        editDataForSpecificFieldInExcel(filepath,SheetName,amountToUpdate);
        uploadButton.sendKeys(filepath);
    }

    public void downLoadExcelFile() throws InterruptedException {
        downloadButton.click();
        Thread.sleep(4000);
    }

    public void verifyToastMessageAvailabilityAndMessage(String expMessage) {
        waitForElementToAppear(toastMessage);
        String actToastMessage=toastMessage.getText();
        Assert.assertEquals(actToastMessage,expMessage);
        waitForElementToDisappear(toastMessage);
    }

    public void checkValueForAppleItemsIsAsExpected(Integer expAmount){
        String itemPrice=valueForAppleItemsInTable.getText();
        Assert.assertEquals(expAmount.toString(),itemPrice);
    }


}
