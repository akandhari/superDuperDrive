package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTestBase {

	@LocalServerPort
	protected int port;
	protected WebDriver driver;

	protected final String errorPageMessage = "Error occurred! - File not Found";
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		ChromeOptions option = new ChromeOptions();
		HashMap<String,Object> options = new HashMap<>();
		options.put("download.prompt_for_download",false);
		option.setExperimentalOption("prefs",options);
		this.driver = new ChromeDriver(option);
		this.driver.manage().window().maximize();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	protected HomePage signUpAndLogin(String fName,String lName, String uName,String pwd) {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName(fName);
		signupPage.setLastName(lName);
		signupPage.setUserName(uName);
		signupPage.setPassword(pwd);
		signupPage.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(uName);
		loginPage.setPassword(pwd);
		loginPage.login();
		return new HomePage(driver);
	}

	protected HomePage existingUserLogin(String uName,String pwd){
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(uName);
		loginPage.setPassword(pwd);
		loginPage.login();
		return new HomePage(driver);
	}
}
