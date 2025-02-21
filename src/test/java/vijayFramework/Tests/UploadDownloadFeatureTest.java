package vijayFramework.Tests;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import vijayFramework.TestComponents.BaseTest;
import vijayFramework.data.ExcelDataDriven;

import java.io.IOException;
import java.time.Duration;

public class UploadDownloadFeatureTest extends BaseTest {
    @Test
    public void checkUploadDownLoadFeature() throws IOException, InterruptedException {
        Integer amountToUpdate=1001;
        launchSpecificApplication("UploadDownLoadFeatureUrl");
        String filepath="C:/Users/Vijay_Yadav/Downloads/download.xlsx";
        String sheetName="Sheet1";
        String expToastMessage="Updated Excel Data Successfully.";
        //downloading the file
        UploadDownLoadPage uploadDownLoadPage= new UploadDownLoadPage(driver);
        uploadDownLoadPage.downLoadExcelFile();

        uploadDownLoadPage.editDownloadedExcelAndUploadFile(filepath,sheetName,amountToUpdate);
        uploadDownLoadPage.verifyToastMessageAvailabilityAndMessage(expToastMessage);
        uploadDownLoadPage.checkValueForAppleItemsIsAsExpected(amountToUpdate);
    }
}
