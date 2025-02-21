package cucumberFrameworkFeatureFiles;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "C:/RestProject/demoSerializationSpecBuilder/TestNg_UI_Framework/src/test/java/cucumberFrameworkFeatureFiles",
        glue = "cucumberFrameworkStepDefinitions",
        monochrome = true,
        plugin = {"html:target/cucumber.html"}
)
public class CucumberTestNGRunner extends AbstractTestNGCucumberTests {



}
