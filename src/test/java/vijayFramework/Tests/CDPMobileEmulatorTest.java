package vijayFramework.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v132.network.Network;
import org.openqa.selenium.devtools.v132.network.model.Request;
import org.openqa.selenium.devtools.v132.network.model.Response;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.openqa.selenium.devtools.v132.network.Network.requestWillBeSent;
import static org.openqa.selenium.devtools.v132.network.Network.responseReceived;

public class CDPMobileEmulatorTest {

    @Test
    public void SetDeviceMetricsToMobileView() throws InterruptedException {
        ChromeDriver driver= new ChromeDriver();
        driver.manage().window().maximize();
        Map deviceMetrics= new HashMap();
        deviceMetrics.put("width",600);
        deviceMetrics.put("height",1000);
        deviceMetrics.put("deviceScaleFactor",50);
        deviceMetrics.put("mobile",true);

        DevTools devTools= driver.getDevTools();
        devTools.createSession();
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride",deviceMetrics);
// you can use the below one liner custom method which is already created by selenium for us but incase if you want to create you own custom method you can do that using above way creating a hasmap and passing those values for paramters in the executeCDPCommands method
//        devTools.send(Emulation.setDeviceMetricsOverride(600,1000,50,true, Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()));

        driver.get("https://www.rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector(".navbar-toggler")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("Library")).click();



    }

    @Test(enabled = true)
    public void checkNetworkValidation() throws InterruptedException {
        // not executing this method as chrome is asking for is Human validation which is impossible for us to bypass through automation
        ChromeDriver driver= new ChromeDriver();
        driver.manage().window().maximize();
        DevTools devTools= driver.getDevTools();
        devTools.createSession();
        // this will enabled network tracking
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        devTools.addListener(requestWillBeSent(), request->{
            Request req= request.getRequest();
            System.out.println(req.getUrl());
            System.out.println(req.getHeaders());
            System.out.println("Request details above");
        });

        devTools.addListener(responseReceived(), response->{
            Response response1 =response.getResponse();
            if(response1.getStatus().toString().startsWith("4")){
                System.out.println(response1.getUrl());
                System.out.println(response1.getStatus());
            }
        });

        driver.get("https://www.rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();

    }
    @Test(enabled = false)
    public void setGeoLocationValidation() throws InterruptedException {
        // not executing this method as chrome is asking for is Human validation which is impossible for us to bypass through automation
        Map coordinates= new HashMap();
        coordinates.put("latitude",40);
        coordinates.put("longitude",3);
        coordinates.put("accuracy",1);
        ChromeDriver driver= new ChromeDriver();
        driver.manage().window().maximize();
        DevTools devTools= driver.getDevTools();
        devTools.createSession();
        driver.executeCdpCommand("Emulation.setGeolocationOverride",coordinates);
        driver.get("https://www.google.com");
        driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
        driver.findElement(By.xpath("//*[contains(text(),'m not a robot')]")).click();
        driver.findElements(By.cssSelector(".LC20lb")).get(0).click();
        String movieTitle=driver.findElement(By.cssSelector(".our-story-card-title")).getText();
        System.out.println(movieTitle);
    }
}
