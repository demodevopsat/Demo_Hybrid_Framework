
package com.hybridFramework.helper.Browser;

import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.hybridFramework.helper.Logger.LoggerHelper;

public class BrowserHelper{

	private WebDriver driver;
	private Logger Log = LoggerHelper.getLogger(BrowserHelper.class);

	public BrowserHelper(WebDriver driver) {
		this.driver = driver;
		Log.debug("BrowserHelper : " + this.driver.hashCode());
	}

	public void goBack() {
		driver.navigate().back();
		Log.info("Navigate Back");
	}

	public void goForward() {
		driver.navigate().forward();
		Log.info("Navigate Forward");
	}

	public void refresh() {
		driver.navigate().refresh();
		Log.info("Page Refreshed");
	}

	public Set<String> getWindowHandles() {
		Log.info("Window Handling");
		return driver.getWindowHandles();
	}

	public void SwitchToWindow(int index) {

		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandles());
		System.out.println(windowsId);
		driver.switchTo().window(windowsId.get(index));
		driver.manage().window().maximize();
		Log.info(index);
		Log.info("Switch to Window is Successfull");
	}

	public void switchToParentWindow() {
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandles());
		driver.switchTo().window(windowsId.get(0));
		Log.info("Switch to Parent Window");
	}

	public void switchToParentWithChildClose() {
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandles());

		for (int i = 1; i < windowsId.size(); i++) {
			Log.info(windowsId.get(i));
			driver.switchTo().window(windowsId.get(i));
			driver.close();
		}
		switchToParentWindow();
	}
		
	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
		Log.info("Frame ID/Name : "+nameOrId);
	}

	public void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
		Log.info("Frame Index : "+index);
	}
	
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		Log.info("Switch to Default Content");
	}
}
