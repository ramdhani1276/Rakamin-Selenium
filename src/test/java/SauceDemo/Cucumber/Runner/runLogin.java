package SauceDemo.Cucumber.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/SauceDemo/Cucumber/Features",
        glue = "SauceDemo.Cucumber.StepDef",
        plugin = {"html:target/HTML_report.html"},
        tags = "@TDD"
)

public class runLogin {
}