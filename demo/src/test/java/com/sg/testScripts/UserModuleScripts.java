package com.sg.testScripts;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.sg.driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/****************************************
	 * testScripr name		: TS_loginLogout()
	 * test case ID			: SRS_101
	 * 
	 ****************************************/
	public boolean TS_loginLogout()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		try {
			objData = datatable.getExcelData("testData", "SRS_101");
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed to navigate the URL");
			Assert.assertTrue(appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password")), "Failed to login to the actiTime application");
			Assert.assertTrue(appDep.logoutFromApp(oBrowser), "Failed tologout from the actiTime appilcation");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception while executing 'TS_loginLogout()' test script. " + e);
			return false;
		}
		finally {
			oBrowser.close();
			objData = null;
			oBrowser = null;
		}
	}
	
	
	
	
	/****************************************
	 * testScripr name		: TS_CreateAndDeleteUser()
	 * test case ID			: SRS_102
	 * 
	 ****************************************/
	public boolean TS_CreateAndDeleteUser()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String userName = null;
		try {
			objData = datatable.getExcelData("testData", "SRS_102");
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed to navigate the URL");
			Assert.assertTrue(appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password")), "Failed to login to the actiTime application");
			userName = appDep.createUser(oBrowser, objData);
			Assert.assertTrue(appDep.deleteUser(oBrowser, userName), "Failed to delete the user");
			Assert.assertTrue(appDep.logoutFromApp(oBrowser), "Failed tologout from the actiTime appilcation");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception while executing 'TS_CreateAndDeleteUser()' test script. " + e);
			return false;
		}
		finally {
			oBrowser.close();
			objData = null;
			oBrowser = null;
		}
	}
}
