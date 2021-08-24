package com.hybridFramework.helper.Javascript;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hybridFramework.helper.Logger.LoggerHelper;
import com.hybridFramework.helper.Wait.WaitHelper;

public class JavaScriptHelper {

	private static WebDriver driver;
	private Logger Log = LoggerHelper.getLogger(JavaScriptHelper.class);
	WaitHelper waitHelper = new WaitHelper(driver);

	public JavaScriptHelper(WebDriver driver) {
		JavaScriptHelper.driver = driver;
		Log.debug("JavaScriptHelper : " + JavaScriptHelper.driver.hashCode());
	}

	public Object executeScript(String script) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		Log.info(script);
		return exe.executeScript(script);
	}

	public Object executeScript(String script, Object... args) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		Log.info(script);
		return exe.executeScript(script, args);
	}

	public void scrollToElemet(WebElement element) {
		executeScript("window.scrollTo(arguments[0],arguments[1])",
				element.getLocation().x, element.getLocation().y);
		Log.info(element);
	}

	public void scrollToElemetAndClick(WebElement element) {
		scrollToElemet(element);
		element.click();
		Log.info(element);
	}

	public void scrollIntoView(WebElement element) {
		executeScript("arguments[0].scrollIntoView()", element);
		Log.info(element);
	}

	public void scrollIntoViewAndClick(WebElement element) {
		scrollIntoView(element);
		element.click();
		Log.info(element);
	}

	public void scrollDownVertically() {
		executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollUpVertically() {
		executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void scrollDownByPixel() {
		executeScript("window.scrollBy(0,1500)");
	}
	
	public void scrollLeftByPixel() {
		executeScript("window.scrollBy(-1500, 0)");
	}

	public void scrollUpByPixel() {
		executeScript("window.scrollBy(0,-800)");
	}
	
	public void scrollByPixel(int pixel) {
		executeScript("window.scrollBy(0,"+pixel+")");
	}
	
	public void scrollRightByPixel() {
		executeScript("window.scrollBy(1500, 0)");
	}

	public void ZoomInBypercentage() {
		executeScript("document.body.style.zoom='40%'");
	}

	public void ZoomBy100percentage() {
		executeScript("document.body.style.zoom='100%'");
	}
		
}


