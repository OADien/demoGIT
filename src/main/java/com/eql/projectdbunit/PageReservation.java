package com.eql.projectdbunit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageReservation {
	
	@FindBy(id="name")
	private WebElement name;
	
	@FindBy(xpath="//input[@type='submit']")
	private WebElement submit;
	
	public void book(String title) {
		name.clear();
		name.sendKeys(title);
		submit.click();
	}

}
