package vijayFramework.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNg {

    public static  ExtentReports getReportObject(){
        String path = System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter= new ExtentSparkReporter(path);
        reporter.config().setReportName("Web TestNG Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports extent= new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Project Name", "Vijay Automation Solutions");
        return extent;

    }
}
