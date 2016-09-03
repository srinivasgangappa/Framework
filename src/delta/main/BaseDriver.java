package delta.main;

import org.testng.annotations.DataProvider;

import generic.Excel;

public class BaseDriver
{
	
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
			String controllerPath="./scripts/scenarios.xlsx";
			String suiteSheet="Condition";
			
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

}
