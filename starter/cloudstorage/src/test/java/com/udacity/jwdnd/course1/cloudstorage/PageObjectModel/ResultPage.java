package com.udacity.jwdnd.course1.cloudstorage.PageObjectModel;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    private final JavascriptExecutor js;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "show-home-page")
    private WebElement btnRedirectToHome;

    @FindBy(id = "message")
    private WebElement displayMessage;

    public void redirectToHome() {
        js.executeScript("arguments[0].click();", btnRedirectToHome);
    }
    public String getMessageDisplayed(){
        return displayMessage.getText();
    }
}
