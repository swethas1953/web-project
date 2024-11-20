package com.sg.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.sg.driver.DriverScript;
import com.sg.locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/**************************************
	 * Method Name	: navigateURL
	 * Purpose		: To navigate the URL
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver, URL
	 **************************************/
	public boolean navigateURL(WebDriver oDriver, String URL)
	{
		try {
			oDriver.navigate().to(URL);
			appInd.waitForTheWebElement(oDriver, obj_Login_Btn, "Clickable", "", 10);
			
			String strTitle = oDriver.getTitle();
			if(appInd.compareValues(oDriver, strTitle, "actiTIME - Login")) return true;
			else return false;
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'navigateURL()' method. " + e);
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name	: loginToApp
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver, userName, password
	 **************************************/
	public boolean loginToApp(WebDriver oDriver, String userType, String userName, String password) {
		try {
			appInd.setObject(oDriver, obj_UserName_Edit, userName);
			appInd.setObject(oDriver, obj_Password_Edit, password);
			appInd.clickObject(oDriver, obj_Login_Btn);
			
			if(userType.equalsIgnoreCase("New")) {
				appInd.waitForTheWebElement(oDriver, obj_StartExploringActitime_Btn, "Clickable", "", 20);
				appInd.clickObject(oDriver, obj_StartExploringActitime_Btn);
			}
			
			appInd.waitForTheWebElement(oDriver, obj_LoginLogo_image, "Clickable", "", 10);
			if(appInd.verifyElelementPresent(oDriver, obj_LoginLogo_image)) {
				reports.writeReport(oDriver, "Pass", "Login to actiTime is successful");
				
				appInd.waitForTheWebElement(oDriver, obj_Shortcut_Close_Btn, "Clickable", "", 10);
				boolean blnRes = appInd.verifyOptionalElement(oDriver, obj_Shortcut_Window);
				if(blnRes)
					appInd.clickObject(oDriver, obj_Shortcut_Close_Btn);
				return true;
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to login to actiTime");
				return false;
			}
			
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'loginToApp()' method. " + e);
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name	: createUser
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver
	 **************************************/
	public String createUser(WebDriver oDriver, Map<String, String> objData) {
		String userName = null;
		try {
			appInd.clickObject(oDriver, obj_USERS_Menu);
			appInd.waitForTheWebElement(oDriver, obj_AddUser_Btn, "Clickable", "", 10);
			appInd.verifyText(oDriver, obj_UserList_PageHeader, "Text", "User List");
				
			
			//click on "add Users" button and validate
			appInd.clickObject(oDriver, obj_AddUser_Btn);
			appInd.waitForTheWebElement(oDriver, obj_Users_ReypePassword_Edit, "Clickable", "", 10);
			appInd.verifyElelementPresent(oDriver, obj_AddUser_Window);
			
			
			
			//6. Enter all the details to create the user & validate
			String FN = objData.get("FirstName");
			String LN = objData.get("LastName");
			appInd.setObject(oDriver, obj_Users_FirstName_Edit, FN);
			appInd.setObject(oDriver, obj_Users_LastName_Edit, LN);
			appInd.setObject(oDriver, obj_Users_Email_Edit, objData.get("email"));
			appInd.setObject(oDriver, obj_Users_UserName_Edit, objData.get("User_UN"));
			appInd.setObject(oDriver, obj_Users_Password_Edit, objData.get("User_PWD"));
			appInd.setObject(oDriver, obj_Users_ReypePassword_Edit, objData.get("User_RetypePwd"));
			
			userName = LN+", "+FN;
			appInd.clickObject(oDriver, obj_CreateUser_Btn);
			appInd.waitForTheWebElement(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"), "Text", userName, 10);
			boolean blnRes = appInd.verifyElelementPresent(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"));
			
			if(blnRes) {
				reports.writeReport(oDriver, "Pass", "The user was created successful");
				return userName;
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to create the user");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'createUser()' method. " + e);
			return null;
		}
	}
	
	
	
	/**************************************
	 * Method Name	: deleteUser
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver, userName
	 **************************************/
	public boolean deleteUser(WebDriver oDriver, String userName) {
		try {
			appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"));
			appInd.waitForTheWebElement(oDriver, obj_DeleteUser_Btn, "clickable", "", 10);
			appInd.clickObject(oDriver, obj_DeleteUser_Btn);
			Thread.sleep(2000);
			oDriver.switchTo().alert().accept();
			Thread.sleep(2000);
			
			if(appInd.verifyElelementNotPresent(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"))) {
				reports.writeReport(oDriver, "Pass", "User was deleted successful");
				return true;
				
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to delete the user");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'deleteUser()' method. " + e);
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name	: logoutFromApp
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver
	 **************************************/
	public boolean logoutFromApp(WebDriver oDriver) {
		try {
			appInd.clickObject(oDriver, obj_Logout_Link);
			appInd.waitForTheWebElement(oDriver, obj_Login_Header, "Text", "Please identify yourself", 10);
				
			if(appInd.verifyText(oDriver, obj_Login_Header, "Text", "Please identify yourself")) {
				reports.writeReport(oDriver, "Pass", "Logout from the actiTime was successful");
				return true;
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to logout from the actiTime");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'logoutFromApp()' method. " + e);
			return false;
		}
	}
}

