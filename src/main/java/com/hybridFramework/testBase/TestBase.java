package com.hybridFramework.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.hybridFramework.PageObject.LoginPage;
import com.hybridFramework.excelReader.Excel_reader;
import com.hybridFramework.helper.Alert.AlertHelper;
import com.hybridFramework.helper.Browser.BrowserHelper;
import com.hybridFramework.helper.Javascript.JavaScriptHelper;
import com.hybridFramework.helper.Wait.WaitHelper;
import com.hybridFramework.helper.genericHelper.GenericHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	public static Excel_reader excelreader = new Excel_reader("./src/main/java/com/hybridFramework/data/TestData.xlsx");
	public static WebDriver driver;
	public static Properties OR;
	public static Config config;
	public static JavaScriptHelper jsh;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static WaitHelper waithelper;
	public static AlertHelper ah;
	public static LoginPage lp;
	public static GenericHelper gh;
	public static BrowserHelper bh;
	public static SoftAssert sa = new SoftAssert();
	public static String partialReportName;

	public ITestResult result;
	public File f1;
	public FileInputStream file;

	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		partialReportName = formater.format(calendar.getTime());
		extent = new ExtentReports(System.getProperty("user.dir")
				+ "/src/main/java/com/hybridFramework/report/PYD_DEVOPS_" + partialReportName + ".html", false);
	}

	@BeforeTest
	public void launchBrowser() throws Exception {
		try {
			loadPropertiesFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Config config = new Config(OR);
			getBrowser(config.getBrowser());
			WaitHelper waitHelper = new WaitHelper(driver);
			waitHelper.setImplicitWait(config.getImplicitWait(), TimeUnit.SECONDS);
			waitHelper.setPageLoadTimeout(config.getPageLoadTimeOut(), TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("No Browser Launched");
			e.printStackTrace();
		}
	}

	public void getBrowser(String browser) {
		if (System.getProperty("os.name").contains("Window")) {
			if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\MozillaDriver\\geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			} else if (browser.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.setAcceptInsecureCerts(true);
				options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
				options.addArguments("incognito");
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("ie")) {
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.ignoreZoomSettings();
				options.introduceFlakinessByIgnoringSecurityDomains();
				options.destructivelyEnsureCleanSession();
				options.requireWindowFocus();
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver(options);
				driver.manage().window().maximize();
			}
		}
	}

	@BeforeClass
	public void initializeObject() {
		config = new Config(OR);
		waithelper = new WaitHelper(driver);
		ah = new AlertHelper(driver);
		lp = new LoginPage(driver);
		gh = new GenericHelper();
		bh = new BrowserHelper(driver);
		jsh = new JavaScriptHelper(driver);
	}

	public void loadPropertiesFile() throws IOException {
		String log4jConfPath = System.getProperty("user.dir") + "\\src\\main\\resources\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		OR = new Properties();
		System.out.println(System.getProperty("user.dir"));

		f1 = new File(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\hybridFramework\\config\\config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		logger.info("loading config.properties");

	}

	public String getScreenShot(String imageName) throws IOException {

		if (imageName.equals("")) {
			imageName = "PYD_DEVOPS_";
		}
		File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imagelocation = System.getProperty("user.dir") + "/src/main/java/com/hybridFramework/screenshot/";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imagelocation + imageName + "_" + formater.format(calendar.getTime()) + ".png";
		File destFile = new File(actualImageName);
		FileUtils.copyFile(image, destFile);
		return actualImageName;
	}

	public WebElement waitForElement(WebDriver driver, long time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	@SuppressWarnings("deprecation")
	public WebElement waitForElementWithPollingInterval(WebDriver driver, long time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void impliciteWait(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void getresult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {

			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getName() + " test is failed" + result.getThrowable());
			String screen = getScreenShot("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
		driver.get(config.getWebsite());
	}

	public void clickOnElement(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			waithelper.waitForElement(driver, 5000, element);
			element.click();
			e.printStackTrace();
			System.out.println("Click on " + element + "could not be done!");
		}
	}

}
