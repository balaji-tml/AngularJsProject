package com.bit.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


import com.bit.utilities.ExcelReader;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

//NgWebdriver
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class TestBase {

	public static WebDriver driver;
	public static FileInputStream fis;
	protected static Properties config;
	public static Properties or;
	public static ExcelReader excel;

	//NgWebdriver
	private Server webServer;
	public NgWebDriver ngWebDriver;

	@BeforeSuite
	public void setUp() throws Exception {
		String userDir = System.getProperty("user.dir");
		String browser, appUrl;
		config = new Properties();
		excel = new ExcelReader(userDir + "/src/test/resources/com/bit/excel/Testdata.xlsx");
		or = new Properties();
		if (driver == null) {
			try {

				fis = new FileInputStream(userDir + "/src/test/resources/com/bit/properties/Config.properties");
				config.load(fis);
				fis = new FileInputStream(userDir + "/src/test/resources/com/bit/properties/OR.properties");
				or.load(fis);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			browser = config.getProperty("browser");
			System.out.println("browser: "+browser);
			appUrl = config.getProperty("appurl");
			System.out.println("appUrl: "+appUrl);
			if(browser.equals("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", userDir + "/src/test/resources/com/bit/executables/chromedriver.exe");
				driver = new ChromeDriver();
			}
			else if(browser.equals("edge"))
			{
				System.setProperty("webdriver.edge.driver", userDir + "/src/test/resources/com/bit/executables/msedgedriver.exe");
				driver = new EdgeDriver();
			}
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt("implicit.wait")));

			//NgWebdriver
			ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
			// Launch Protractor's own test app on http://localhost:8080
			((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
			webServer = new Server(new QueuedThreadPool(6));
			ServerConnector connector = new ServerConnector(webServer, new HttpConnectionFactory());
			connector.setPort(8080);
			webServer.addConnector(connector);
			ResourceHandler resource_handler = new ResourceHandler();
			resource_handler.setDirectoriesListed(true);
			resource_handler.setWelcomeFiles(new String[]{"index.html"});
			resource_handler.setResourceBase("src/test/webapp");
			HandlerList handlers = new HandlerList();
			MovedContextHandler effective_symlink = new MovedContextHandler(webServer, "/lib/angular", "/lib/angular_v1.2.9");
			handlers.setHandlers(new Handler[] { effective_symlink, resource_handler, new DefaultHandler() });
			webServer.setHandler(handlers);
			webServer.start();
		}

	}
	
	
	
	@AfterSuite
	public void tearDown()
	{
		if(!(driver == null))
		{
//			driver.quit();
		}
	}

}
