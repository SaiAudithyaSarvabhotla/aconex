package runnerFiles;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.automation.framework.core.FrameworkScript;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/featureFiles", glue = "aconex.StepDefinitions")
public class RunTestNGTest extends AbstractTestNGCucumberTests {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static String scenarioName = null;
	
	@BeforeTest(alwaysRun = true)
	public void setUpCucumber(ITestContext context) {
		FrameworkScript.onInitilise(context);
	}

	@AfterTest(alwaysRun = true)
	public void tearDownClass() throws Exception {
		FrameworkScript.onCompleted();
		testNGCucumberRunner.finish();
	}
}
