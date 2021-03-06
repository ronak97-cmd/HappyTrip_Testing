package com.skillassure.happytrip.page;

import java.io.IOException;
import java.util.List;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.skillassure.happytrip.base.BaseClass;
import com.skillassure.happytrip.utils.ReadExcel;

@SuppressWarnings("deprecation")
public class ShceduleFlight extends BaseClass {

	// Logger
	private static Logger log = LogManager.getLogger(ShceduleFlight.class.getName());

	private String filePath = ("resources/TestData.xlsx");
	private String sheetName = "Sheet1";

	@Test(description = "This Test Phase will ensure Valid login of Admin", dataProvider = "AdminLogin")
	public void login(String sUsername, String sPassword) throws Throwable {

		// Navigate to Sign in link
		// Using Partial link
		driver.findElement(By.partialLinkText("Sign")).click();
		log.info("Clicking on Sign in Link");

		driver.findElement(By.id("username")).sendKeys(sUsername);
		log.info("Entering Username");

		driver.findElement(By.id("password")).sendKeys(sPassword);
		log.info("Entering Password");

		// Click sigin
		driver.findElement(By.id("signInButton")).click();
		log.info("Logged into HappyTrip");

		// Entent Report
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./reports/HappyTrip_Login.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);

		// Login Report
		ExtentTest logger = extent.createTest("LoginTest");

		logger.log(Status.INFO, "Browser started");
		logger.log(Status.INFO, "Application is up and running");
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Insert title here"));
		logger.log(Status.PASS, "Login Verified");
		extent.flush();

	}

	@Test(description = "This Test Phase will ensure Scheduling the flight")
	public void scheduleFlight() {
		// Navigate to Schedule flight link
		driver.findElement(By.linkText("Schedule Flight")).click();
		log.info("Clicking on Schedule flight Link");

		// Choose the flight from the drop down
		Select flight = new Select(driver.findElement(By.id("flight")));
		flight.selectByVisibleText("spic01");
		log.info("selecting the flight");

		// Choose the route from the drop down
		Select route = new Select(driver.findElement(By.id("route")));
		route.selectByIndex(4);
		log.info("selecting the route");

		// Enter the distance between From and To place
		// Distance
		driver.findElement(By.id("distance")).sendKeys("750");
		log.info("Enter the Distance");

		// Enter the Departure & Arrival Date also the time

		// Departure Date
		// Open the Date picker
		driver.findElement(By.cssSelector("dd:nth-child(12) > .ui-datepicker-trigger")).click();
		log.info("Select the Departure date");
		// Select the date from date picker
		WebElement departdate = driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td[6]/a"));
		List<WebElement> Dday = departdate.findElements(By.tagName("td"));
		// condition to select
		for (WebElement cell : Dday) {
			if (cell.getText().equals("4")) {
				cell.click();
				break;
			}
		}

		// Set the departure time
		Select dTime = new Select(driver.findElement(By.id("departureTime")));
		dTime.selectByIndex(0);
		log.info("Select the Departure time");

		// Arrival Date
		// Open the Date picker
		driver.findElement(By.cssSelector("dd:nth-child(12) > .ui-datepicker-trigger")).click();
		log.info("Selecting the Arrival date");

		// Select the date from date picker
		WebElement arrivalDate = driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td[6]/a"));
		List<WebElement> Aday = arrivalDate.findElements(By.tagName("td"));
		// condition to select
		for (WebElement cell : Aday) {
			if (cell.getText().equals("5")) {
				cell.click();
				break;
			}
		}
		// Set the arrival time
		Select aTime = new Select(driver.findElement(By.id("arrivalTime")));
		aTime.selectByIndex(0);
		log.info("Selecting the Arrival time");

		// Update the cost of Economy class
		driver.findElement(By.id("classEconomy")).sendKeys("1500");
		log.info("Update the Class information");

		// Click Add to update the changes
		driver.findElement(By.xpath("//form[@id='AddSchedule']/dl/dd[10]/button")).click();
		log.info("Confirming the changes");

		// Entent Report
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./reports/HappyTrip_ScheduleFlight.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);

		// Login Report
		ExtentTest logger = extent.createTest("LoginTest");

		logger.log(Status.INFO, "Browser started");
		logger.log(Status.INFO, "Application is up and running");
		String URL = driver.getCurrentUrl();
		Assert.assertTrue(URL.contains("http://43.254.161.195:8085/happytripcrclean1/admin/scheduleFlight.html"));
		logger.log(Status.PASS, "Schedule Verified");
		extent.flush();
	}

	@DataProvider(name = "AdminLogin")
	public Object[][] readExcel() throws InvalidFormatException, IOException {
		return ReadExcel.readExcel(filePath, sheetName);
	}

}
