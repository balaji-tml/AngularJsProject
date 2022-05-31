package com.bit.testcases;

import com.bit.utilities.TestUtil;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.bit.base.TestBase;

import java.util.Hashtable;

public class LoginTest extends TestBase{
	
	@Test(dataProviderClass = TestUtil.class,dataProvider = "dp")
	public void loginTest(Hashtable<String,String> data)
	{

		String appUrl=config.getProperty("appurl");
		String username = data.get("username");

		String password = data.get("password");
		driver.get(appUrl);
		if(driver.getTitle().equals("ServiceNow"))
		{
//			driver.switchTo().frame("loginPage");
			driver.switchTo().frame(0);
			driver.findElement(By.id("user_name")).sendKeys(username);
			driver.findElement(By.id("user_password")).sendKeys(password);
			driver.findElement(By.id("sysverb_login")).click();			
		}
		if(driver.getTitle().contains("ServiceNow"))
		{
			System.out.println("Login Test is successfull!");
		}
		else
		{
			System.out.println("Login Test is not successfull!");
		}
	}

}
