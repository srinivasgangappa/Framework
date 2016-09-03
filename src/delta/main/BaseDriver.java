package delta.main;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import generic.Excel;

public class BaseDriver implements CommonConstants
{
	public WebDriver driver;
	public ExtentReports eReport;
	public ExtentTest testReport;
	
	//This Dataprovider is for commented code in test method
	@DataProvider
	public static String[][] getScenarios()
	{
		String[][] data = new String[2][1];
		data[0][0]="ScenarioSheet1";
		data[1][0]="ScenarioSheet2";
		return data;
	}
	
	@DataProvider
	public static String[][] getScenarioConditon()
	{	

			
			int setpCount = Excel.getRowCount(controllerPath, suiteSheet);
			String[][] data1 = new String[setpCount][2];
			for(int i=1; i<setpCount; i++)
			{		
			
			String scenarioName=Excel.getCellValue(controllerPath, suiteSheet, i, 0);
			String executionStatus=Excel.getCellValue(controllerPath, suiteSheet, i, 1);
			data1[i-1][0]=scenarioName;
			data1[i-1][1]=executionStatus;
			}
			return data1;
	}

	@BeforeSuite
	public void initFramework()
	{
		//Initiate the Report
		eReport = new ExtentReports(reportFilePath);
	}
	@AfterSuite
	public void endFrameWork()
	{
		eReport.flush();
	}
	
}
