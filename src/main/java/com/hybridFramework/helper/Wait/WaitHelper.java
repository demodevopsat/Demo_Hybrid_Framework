package com.hybridFramework.helper.Wait;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.hybridFramework.helper.Logger.LoggerHelper;
import com.hybridFramework.testBase.TestBase;

public class WaitHelper extends TestBase {
	
	WebDriver driver;
	private Logger Log = LoggerHelper.getLogger(WaitHelper.class);

	
	public WaitHelper(WebDriver driver) {
		this.driver=driver;
		Log.debug("WaitHelper : " + TestBase.driver.hashCode());
	}
	
	public void setStaticWait(long miliSeconds) {
		
		try {
			Thread.sleep(miliSeconds);
		} catch (InterruptedException e) {
			System.out.println("waiting for "+miliSeconds+" miliSeconds.");
		}
	}
	
	public void setImplicitWait(long timeout, TimeUnit unit) {
		Log.info(timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit == null ? TimeUnit.SECONDS : unit);
	}
	
	public void setPageLoadTimeout(long timeout, TimeUnit unit) {
		Log.info(timeout);
		driver.manage().timeouts().pageLoadTimeout(timeout, unit == null ? TimeUnit.SECONDS : unit);
	}
	
	@SuppressWarnings("deprecation")
	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.debug("");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(pollingEveryInMiliSec, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}
	
	public void waitForElementVisible(WebElement locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.info(locator);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(locator));
	}
	
	public void waitForElementToBeVisible(WebDriver driver, long timeout, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
		Reporter.log("element found..."+element.getText());
	}
	
	public boolean waitForElementToBeClickable(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
	}
	
	public void waitForAlertToBePresent(WebDriver driver,long timeout){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		 wait.until(ExpectedConditions.alertIsPresent());
	}
	

}
