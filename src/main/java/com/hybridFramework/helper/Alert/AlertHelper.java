
package com.hybridFramework.helper.Alert;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.hybridFramework.helper.Logger.LoggerHelper;

public class AlertHelper{
	
	private WebDriver driver;
	private Logger oLog = LoggerHelper.getLogger(AlertHelper.class);
	
	public AlertHelper(WebDriver driver) {
		this.driver = driver;
		oLog.debug("AlertHelper : " + this.driver.hashCode());
	}
	
	public Alert getAlert() throws Exception {
		oLog.debug("Switch Control To Alert");
		return driver.switchTo().alert();
	}
	
	public void AcceptAlert() {
		
		oLog.info("Alert Accepted");
		try {
			getAlert().accept();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DismissAlert() throws Exception {
		oLog.info("Alert Dismissed");
		getAlert().dismiss();
	}

	public String getAlertText() throws Exception {
		String text = getAlert().getText();
		oLog.info(text);
		return text;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			oLog.info("true");
			return true;
		} catch (NoAlertPresentException e) {
			// Ignore
			oLog.info("false");
			return false;
		}
	}

	public void AcceptAlertIfPresent() throws Exception {
		if (!isAlertPresent())
			return;
		AcceptAlert();
		oLog.info("");
	}

	public void DismissAlertIfPresent() throws Exception {

		if (!isAlertPresent())
			return;
		DismissAlert();
		oLog.info("");
	}
	
	public void AcceptPrompt(String text) throws Exception {
		
		if (!isAlertPresent())
			return;
		
		Alert alert = getAlert();
		alert.sendKeys(text);
		alert.accept();
		oLog.info(text);
	}
}
