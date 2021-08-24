package com.hybridFramework.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hybridFramework.testBase.Config;
import com.hybridFramework.testBase.TestBase;

public class LoginPage extends TestBase {

	WebDriver driver;
	public static String userName, signUpMsg;
	public static String[] loggedUserName;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void loginInToDemoBlaze(String username) {
		Config config = new Config(OR);

		if (new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(btnLogin)).isEnabled()) {
			bh.refresh();
			btnLogin.click();
			txtLoginUsername.sendKeys(username);
			txtLoginPassword.sendKeys(config.getPassword());
			logInModalBtn.click();
			waithelper.setStaticWait(5000);
		}
	}

	public String newUserRegistration() throws Exception {
		Config config = new Config(OR);
		userName = config.getUserName() + Math.random() * 100 + 1;
		if (new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(btnSignUp)).isDisplayed()) {
			btnSignUp.click();
			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(signUpModalBtn));
			txtUsername.sendKeys(userName);
			txtPassword.sendKeys(config.getPassword());
			signUpModalBtn.click();
			waithelper.setStaticWait(5000);
			driver.switchTo().alert().accept();
			waithelper.setStaticWait(2000);
		}
		return userName;
	}

	public String fetchLoggedUserName() {
		if (txtLoggedUserName.isDisplayed()) {
			loggedUserName = gh.getDisplayText(txtLoggedUserName).split(" ");
		}
		return loggedUserName[1].trim();
	}

	// Page Web Elements

	@FindBy(xpath = "//div[@id='navbarExample']//a[@id='signin2']")
	private WebElement btnSignUp;

	@FindBy(xpath = "//div[@id='signInModal']//input[@id='sign-username']")
	private WebElement txtUsername;

	@FindBy(xpath = "//div[@id='signInModal']//input[@id='sign-password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//div[@id='logInModal']//input[@id='loginusername']")
	private WebElement txtLoginUsername;

	@FindBy(xpath = "//div[@id='logInModal']//input[@id='loginpassword']")
	private WebElement txtLoginPassword;

	@FindBy(xpath = "//div[@id='signInModal']//button[contains(@class,'btn-primary')]")
	private WebElement signUpModalBtn;

	@FindBy(xpath = "//div[@id='logInModal']//button[contains(@class,'btn-primary')]")
	private WebElement logInModalBtn;

	@FindBy(xpath = "//div[@id='navbarExample']//a[@id='login2']")
	private WebElement btnLogin;

	@FindBy(xpath = "//div[@id='navbarExample']//a[@id='nameofuser']")
	private WebElement txtLoggedUserName;

}
