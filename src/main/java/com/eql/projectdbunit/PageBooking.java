package com.eql.projectdbunit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PageBooking {
	
	
	@FindBy(xpath="//div[contains(@class, 'scheduler_default_cell')][1]")
	private WebElement firstCell;
	
	@FindBy(xpath="//div[contains(@class, 'scheduler_default_cell')][6]")
	private WebElement followingCell;
	
	public PageReservation clickCell(WebDriver driver) throws InterruptedException {
		firstCell.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.switchTo().frame(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal_default_main']/iframe"))));
		return PageFactory.initElements(driver,  PageReservation.class);
	}
	
	public void moveBooking(WebDriver driver) {
		Actions actions = new Actions(driver);
		WebElement res = driver.findElement( By.xpath("//div[contains(@class, 'scheduler_default_event_inner')][1]"));
		actions.clickAndHold(res).moveToElement(followingCell).release(followingCell).build().perform();
	}
	
	
	public void deleteBooking(WebDriver driver) throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions actions = new Actions(driver);
		WebElement res = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'scheduler_default_event_inner')][1]")));
		actions.moveToElement(res).build().perform();
		
		Thread.sleep(500);
		Actions actions2 = new Actions(driver);
		actions2.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'scheduler_default_event_delete')][1]")))).click().build().perform();
	}

}
