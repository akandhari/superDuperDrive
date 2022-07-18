package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.ErrorPage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.ResultPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileTests extends CloudStorageApplicationTestBase {

    @Test
    public void Test_Login_And_Upload_New_File_Feature(){
        HomePage homePage = signUpAndLogin("test","upload","fUpload","fileTest");
        String fileName = "README.md";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newFile")));
        WebElement fileSelectButton = driver.findElement(By.id("newFile"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("File saved Successfully!"));
        resultPage.redirectToHome();
        com.udacity.jwdnd.course1.cloudstorage.model.File file = homePage.getFirstFile();
        Assertions.assertEquals(fileName,file.getFilename());
    }

    @Test
    public void Test_Login_Upload_And_Delete_File_Feature(){
        HomePage homePage = signUpAndLogin("test","delete","fDelete","delTest");
        String fileName = "pom.xml";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newFile")));
        WebElement fileSelectButton = driver.findElement(By.id("newFile"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("fDelete","delTest");
        com.udacity.jwdnd.course1.cloudstorage.model.File file = homePage.getFirstFile();
        Assertions.assertEquals(fileName,file.getFilename());
        homePage.deleteFile();
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("file Deleted Successfully"));
    }

    @Test
    public void Test_Login_And_Download_Existing_File_Feature(){
        HomePage homePage = signUpAndLogin("test","FileDownload","fDownload","downloadTest");
        String fileName = "TestFileDownload.pptx";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newFile")));
        WebElement fileSelectButton = driver.findElement(By.id("newFile"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("fDownload","downloadTest");
        homePage.downloadFirstFile();
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void Test_Prevent_Duplicate_File_Upload_For_Same_User_Feature(){

        HomePage homePage = signUpAndLogin("test","duplicateFileUpload","duplicateUpload","fileTest");
        String fileName = "README.md";
        homePage.setUploadFile(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("duplicateUpload","fileTest");
        homePage.setUploadFile(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("File already exists!"));
    }

    @Test
    public void Test_Prevent_File_Delete_By_Unauthorized_User(){
        HomePage homePage = signUpAndLogin("firstUser","GoodUser","authorized","fileTest");
        String fileName = "README.md";
        homePage.setUploadFile(new File(fileName).getAbsolutePath());
        homePage.uploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        com.udacity.jwdnd.course1.cloudstorage.model.File firstUserUploadedFile = homePage.getFirstFile();
        homePage.logout();
        homePage = signUpAndLogin("secondUser","BadUser","unauthorized","fileDelete");
        driver.get("http://localhost:" + this.port + "/file/delete-file/"+firstUserUploadedFile.getFileId());
        ErrorPage errorPage = new ErrorPage(driver);
        Assertions.assertTrue(errorPage.getErrorText().toLowerCase().contains(errorPageMessage.toLowerCase()));
    }
}
