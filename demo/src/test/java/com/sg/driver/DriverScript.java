package com.sg.driver;

import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.sg.methods.AppDependentMethods;
import com.sg.methods.AppIndependentMethods;
import com.sg.methods.Datatable;
import com.sg.methods.UserModuleMethods;
import com.sg.reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static ReportUtils reports = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static String screenshotLocation = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	
	
	@BeforeSuite
	public void loadClassFile() {
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			reports = new ReportUtils();
			datatable = new Datatable();
			userMethods = new UserModuleMethods();
			
		}catch(Exception e) {
			System.out.println("Exception in 'loadClassFile()' method. " + e);
		}
	}
	
	
	
	
}
