package com.hybridFramework.Generic;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightWebElement {
	
		public static String eleOrignalStyle;
		public static WebElement highlightObject = null;
		static WebDriver driver;
	  
	  public void  highlightObject(WebElement webelement) throws Exception
	  {
	    Thread.sleep(2000L);
	    
	    JavascriptExecutor jse = (JavascriptExecutor)driver;
	    eleOrignalStyle = (String)jse.executeScript("return arguments[0].getAttribute('style')", new Object[] { webelement });
	    String highlightStyle = "; outline: 2px solid rgb(0,255,0);";
	    jse.executeScript("arguments[0].setAttribute('style','" + eleOrignalStyle + highlightStyle + "')", new Object[] { webelement });
	    Thread.sleep(200L);
	    highlightObject = webelement;
	    	    
	  }
	}


