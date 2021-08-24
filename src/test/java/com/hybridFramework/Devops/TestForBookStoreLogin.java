package com.hybridFramework.Devops;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.hybridFramework.testBase.TestBase;

public class TestForBookStoreLogin extends TestBase {

	@Test
	public void TestForRegisterAUserAndLogin() throws Exception {
		String exp_Username = lp.newUserRegistration();
		lp.loginInToDemoBlaze(exp_Username);
		String act_LoggedUserName = lp.fetchLoggedUserName();
		Assert.assertTrue(act_LoggedUserName.equalsIgnoreCase(exp_Username),
				"User is Successfully Logged into the Application");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		try {
			getresult(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		extent.endTest(test);
		extent.flush();
		driver.quit();
	}

}
