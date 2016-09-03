package delta.main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import generic.Excel;
import generic.Propertie;
import generic.Utility;

public class DeltaDrive extends BaseDriver
{
	public String configPptPath="./config/config.properties";
	public String scenarioPath="./scripts/scenarios.xlsx";
	public String reportFilePath="./Report/results.html";
	public String imageFolderPath="./ScreenShot";
	public WebDriver driver;
	public ExtentReports eReport;
	public ExtentTest testReport;
	
	@BeforeSuite
	public void initFramework()
	{
		//Initiate the Report
		eReport = new ExtentReports(reportFilePath);
	}
		
	@BeforeMethod
	public void launchApp()
	{
		//loading the property values
		String appURL=Propertie.getPropertieValue(configPptPath, "URL");
		String timeOut=Propertie.getPropertieValue(configPptPath, "TimeOut");
		
		driver = new FirefoxDriver();
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(timeOut), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	/*@Test(dataProvider="getScenarios")
	public void testScenarios(String ScenarioSheet)
	{
		
		testReport = eReport.startTest(ScenarioSheet);
		
		int setpCount = Excel.getRowCount(scenarioPath, ScenarioSheet);
		
		for(int i=1; i<setpCount; i++)
		{		
			String description=Excel.getCellValue(scenarioPath, ScenarioSheet, i, 0);
			String action=Excel.getCellValue(scenarioPath, ScenarioSheet, i, 1);
			String input1=Excel.getCellValue(scenarioPath, ScenarioSheet, i, 2);
			String input2=Excel.getCellValue(scenarioPath, ScenarioSheet, i, 3);
		
			testReport.log(LogStatus.INFO, "description:"+description+" action: "+action+" input1: "+input1+" input2: "+input2);
			KeyWord.executeKeyword(driver, action, input1, input2);
			Assert.fail();
		}
	}*/
	
	@Test(dataProvider="getScenarioConditon")
	public void testScenarios(String scenarioName, String executionStatus)
	{
		
		testReport = eReport.startTest(scenarioName);
		
		if(executionStatus.equalsIgnoreCase("yes"))
		{
		
		int setpCount = Excel.getRowCount(scenarioPath, scenarioName);
		
		for(int i=1; i<setpCount; i++)
		{		
			String description=Excel.getCellValue(scenarioPath, scenarioName, i, 0);
			String action=Excel.getCellValue(scenarioPath, scenarioName, i, 1);
			String input1=Excel.getCellValue(scenarioPath, scenarioName, i, 2);
			String input2=Excel.getCellValue(scenarioPath, scenarioName, i, 3);
		
			testReport.log(LogStatus.INFO, "description:"+description+" action: "+action+" input1: "+input1+" input2: "+input2);
			KeyWord.executeKeyword(driver, action, input1, input2);
			Assert.fail();
		}
		}
		else
		{
			testReport.log(LogStatus.SKIP,"Execution status is 'NO'");
			throw new SkipException("Skiping the exception");
		}
	}

	@AfterMethod
	public void quitApp(ITestResult test) throws InterruptedException
	{
		if(test.getStatus()==2)
		{
			String pImage = Utility.getPageScreenShot(driver, imageFolderPath);
			String p = testReport.addScreenCapture("."+pImage);
			testReport.log(LogStatus.FAIL, "page screen shot:"+p);
			
			
		}
		driver.close();
		eReport.endTest(testReport);
	}
	
	@AfterSuite
	public void endFrameWork()
	{
		eReport.flush();
	}
}
