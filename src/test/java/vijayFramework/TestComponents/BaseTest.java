package vijayFramework.TestComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import vijayFramework.Tests.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    DataFormatter formatter= new DataFormatter();
    Properties prop = new Properties();
    public WebDriver initializeDriver() throws IOException {


        FileInputStream fileInputStream= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/globalData.properties");
        prop.load(fileInputStream);
        String browserName=System.getProperty("browser")!=null ?System.getProperty("browser"):prop.getProperty("browserName");

        if(browserName.contains("chrome")){
            ChromeOptions options = new ChromeOptions();
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            driver= new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));
        } else if (browserName.contains("edge")) {
            EdgeOptions options= new EdgeOptions();
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            driver= new EdgeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    public LandingPage launchSpecificApplication(String urlName) throws IOException {
        driver=initializeDriver();
        landingPage= new LandingPage(driver);
        landingPage.goToSpecificURL(prop.getProperty(urlName));
        return landingPage;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        driver=initializeDriver();
        landingPage= new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }
    @AfterMethod
    public void tearDown(){
        driver.close();
    }

    @AfterTest
    public void tearDownRemainingInstance(){
        driver.quit();
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //read json to String
        String jsonContent= FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        //String to Hash Map we need Jackson Data bind dependency
        ObjectMapper mapper= new ObjectMapper();
        List<HashMap<String,String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });

        return data;
    }
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }


    public String[][] getDataFromExcel() throws IOException {
        FileInputStream fileInputStream= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/globalData.properties");
        prop.load(fileInputStream);
        String sheetName=System.getProperty("SheetName")!=null ?System.getProperty("SheetName"):prop.getProperty("SheetName");
        ArrayList<String> details= new ArrayList<>();
        String data[][] = new String[0][];
        FileInputStream file= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/excelData.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(file);
        int sheetCount=workbook.getNumberOfSheets();
        for(int i=0;i<sheetCount;i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                int rowCount=sheet.getLastRowNum();
                int colCount= sheet.getRow(0).getLastCellNum();
                System.out.println("Total number of rows is "+ rowCount);
                data= new String[rowCount][colCount-1];
                for(int j=1;j<=rowCount;j++){
                    for(int k=0;k<colCount;k++){
                        if(k!=0){
                            String da=formatter.formatCellValue(sheet.getRow(j).getCell(k));
                            details.add(da);
                            data[j-1][k-1]= da;
                            System.out.println((j-1)+" row  "+ (k-1)+" column    " + data[j-1][k-1]);
                        }
                    }

                }
            }

        }
        System.out.println("data"+ data);

        return data;
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
                            FileOutputStream fout= new FileOutputStream("C:/Users/Vijay_Yadav/Downloads/download.xlsx");
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
