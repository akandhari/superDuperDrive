package com.udacity.jwdnd.course1.cloudstorage.PageObjectModel;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorPage {

    @FindBy(id = "error")
    @CacheLookup
    private WebElement errorSection;

    @FindBy(css = "a[class='back']")
    @CacheLookup
    private WebElement btnRedirectToHome;

    private WebDriverWait wait;
    private final JavascriptExecutor js;

    public ErrorPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 5);
    }

    public String getErrorText(){
        wait.until(ExpectedConditions.elementToBeClickable(errorSection));
        return errorSection.getText();
    }
    public void redirectToHomePage() {
        js.executeScript("arguments[0].click();", btnRedirectToHome);
    }
}
