package com.eql.projectdbunit;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestHotel {

	private WebDriver driver;
	private String url = "http://localhost/TutorialHtml5HotelPhp/";

	@Before
	public void init() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		try {
			DatabaseUtils.deleteAllData("src/main/resources/dbunit/clear.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @After
	public void quit() {
		driver.quit();
	}

	// @Test
	public void testBook() throws InterruptedException {
		// clear the table
		driver.get(url);
		PageBooking pageBooking = PageFactory.initElements(driver, PageBooking.class);
		PageReservation pageRes = pageBooking.clickCell(driver);
		pageRes.book("resa 1");
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement event = wait.until(ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//div[contains(@class, 'scheduler_default_event_inner')][1]"))));
		Assert.assertTrue(event.getText().contains("resa 1"));

		// check in the datatabase that there is a row with this data
		try {
			DatabaseUtils.compareData("reservations", "src/main/resources/dbunit/book.xml", "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testMoveRes() throws InterruptedException {
		try {
			DatabaseUtils.insertData("src/main/resources/dbunit/book.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.get(url);
		PageBooking pageBooking = PageFactory.initElements(driver, PageBooking.class);
		pageBooking.moveBooking(driver);
		Thread.sleep(500);
		Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Update successful']")).isDisplayed());
		Thread.sleep(6500);
		Assert.assertFalse(driver.findElement(By.xpath("//*[text()='Update successful']")).isDisplayed());
		try {
			DatabaseUtils.compareData("reservations", "src/main/resources/dbunit/movebooking.xml", "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() throws InterruptedException {
		try {
			DatabaseUtils.insertData("src/main/resources/dbunit/book.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.get(url);
		PageBooking pageBooking = PageFactory.initElements(driver, PageBooking.class);
		pageBooking.deleteBooking(driver);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Assert.assertTrue(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='Deleted.']"))))
						.isDisplayed());
		Thread.sleep(6500);
		Assert.assertFalse(driver.findElement(By.xpath("//*[text()='Deleted.']")).isDisplayed());
		try {
			DatabaseUtils.compareData("reservations", "src/main/resources/dbunit/clear.xml", "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
