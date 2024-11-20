package com.sg.methods;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.sg.driver.DriverScript;

public class Datatable extends DriverScript{
	/**************************************
	 * Method Name	: getExcelData
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: Map<String, String>
	 * Param		: sheetName, logicalName
	 **************************************/
	public Map<String, String> getExcelData(String sheetName, String logicalName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Map<String, String> objData = null;
		String strKey = null;
		String strValue = null;
		int rowNum = 0;
		int colNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\TestData.xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeReport(null, "Fail", "The sheet '"+sheetName+"' was not found in the excel test data file");
				return null;
			}
			
			//Find the rowNum based on the logicalName provided
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			if(rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				colNum = row1.getPhysicalNumberOfCells();
				for(int c=0; c<colNum; c++) {
					cell1 = row1.getCell(c);
					strKey = cell1.getStringCellValue();
					
					
					cell2 = row2.getCell(c);
					
					if(cell2==null || cell2.getCellType()==CellType.BLANK) {
						strValue = "";
					}else if(cell2.getCellType()==CellType.BOOLEAN) {
						strValue = String.valueOf(cell2.getBooleanCellValue());
					}else if(cell2.getCellType()==CellType.STRING) {
						strValue = cell2.getStringCellValue();
					}else if(cell2.getCellType()==CellType.NUMERIC) {
						if(DateUtil.isCellDateFormatted(cell2)) {
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//If day is <10, then prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							//If month is <10, then prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							sYear = String.valueOf(cal.get(Calendar.YEAR));
							strValue = sDay + "/" + sMonth + "/" + sYear;
						}else {
							strValue = String.valueOf(cell2.getNumericCellValue());
						}
					}
					
					objData.put(strKey, strValue);
				}
				
				return objData;
			}else {
				reports.writeReport(null, "Fail", "Failed to find the logicalName '"+logicalName+"' in the testData excel");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'getExcelData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception while executing 'getExcelData()' method. " + e);
			}
		}
	}
}
