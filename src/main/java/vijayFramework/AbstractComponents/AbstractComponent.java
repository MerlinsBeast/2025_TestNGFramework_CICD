package vijayFramework.AbstractComponents;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class AbstractComponent {

    WebDriver driver;
    DataFormatter formatter= new DataFormatter();
    public AbstractComponent(WebDriver driver) {
        this.driver=driver;
    }

    public void waitForElementToAppear(By findBy){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
    }
    public void waitForElementToBeClickable(WebElement findBy){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
    }

    public void waitForElementToAppear(WebElement findBy){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }
    public void waitForElementToDisappear(WebElement findBy){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(findBy));
    }

    public ArrayList<String> editDataForSpecificFieldInExcel(String filePath, String testCaseName, int amountToUpdate) throws IOException {
        ArrayList<String> details= new ArrayList<>();
        FileInputStream file= new FileInputStream(filePath);
        XSSFWorkbook workbook= new XSSFWorkbook(file);
        int sheetCount=workbook.getNumberOfSheets();
        for(int i=0;i<sheetCount;i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(testCaseName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                int rowCount=sheet.getLastRowNum();
                int colCount= sheet.getRow(0).getLastCellNum();
                System.out.println("Total number of rows is "+ rowCount);
                for(int j=1;j<=rowCount;j++){
                    for(int k=1;k<colCount;k++){
                        String expItemName=formatter.formatCellValue(sheet.getRow(j).getCell(k));
                        if(k==1 && expItemName.equals("Apple")){
                            // excel write code should go here
                            sheet.getRow(j).getCell(k+2).setCellValue(amountToUpdate);
                            FileOutputStream fout= new FileOutputStream(filePath);
                            workbook.write(fout);
                            String da=formatter.formatCellValue(sheet.getRow(j).getCell(k+2));
                            details.add(da);
                            workbook.close();
                            break;
                        }
                        else {

                        }
                    }

                }
            }

        }
        System.out.println(details);
        return details;
    }
}
