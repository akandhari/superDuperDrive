package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests extends CloudStorageApplicationTestBase {
    @Test
    public void Test_Login_Create_New_Credentials_And_Verify_At_HomePage() {
        HomePage homePage = signUpAndLogin("test", "createCredential", "newCred", "credTest");
        String credentialUrl = "https://review.udacity.com/#!/rubrics/2724/view";
        String credUsername = "testCredentials";
        String credPassword = "password123";
        createUpdateCredential(credentialUrl, credUsername,credPassword, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Credentials Created Successfully!"));
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openCredentialTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(credentialUrl,credential.getUrl());
        Assertions.assertEquals(credUsername, credential.getUsername());
    }
    @Test
    public void Test_Login_Update_Credential_And_Verify_Updated_Credential() {
        HomePage homePage = signUpAndLogin("test", "updateCredential", "updateCred", "credTest");
        String credentialUrl = "https://review.udacity.com/#!/rubrics/2724/view";
        String credUsername = "testCreateCredentials";
        String credPassword = "password123";
        createUpdateCredential(credentialUrl, credUsername,credPassword, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("updateCred","credTest");
        homePage.openCredentialTab();
        String updatedCredentialUrl = "https://review.udacity.com/rubrics/2724";
        String updatedCredUsername = "testUpdateCredentials";
        String updatedCredPassword = "updatedPassword123";
        createUpdateCredential(updatedCredentialUrl, updatedCredUsername,updatedCredPassword, homePage, false);
        resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Credentials Updated Successfully!"));
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("updateCred","credTest");
        homePage.openCredentialTab();
        homePage.editCredential();
        String url = homePage.getCredentialUrl();
        String uName = homePage.getCredentialUsername();
        String pwd = homePage.getCredentialPassword();
        Assertions.assertTrue(updatedCredentialUrl.equalsIgnoreCase(url));
        Assertions.assertTrue(updatedCredUsername.equalsIgnoreCase(uName));
        Assertions.assertTrue(updatedCredPassword.equalsIgnoreCase(pwd));
    }
    @Test
    public void Test_Delete_Existing_Credentials_And_Verify_Its_Removed() {
        HomePage homePage = signUpAndLogin("test", "deleteCredential", "deleteCred", "credTest");
        String credentialUrl = "https://review.udacity.com/#!/rubrics/2724/view";
        String credUsername = "testCredentials";
        String credPassword = "password123";
        createUpdateCredential(credentialUrl, credUsername,credPassword, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Credentials Created Successfully!"));
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openCredentialTab();
        Assertions.assertEquals(1,homePage.getSavedCredentialsCount());
        homePage.logout();
        homePage = existingUserLogin("deleteCred","credTest");
        homePage.openCredentialTab();
        homePage.deleteCredential();
        resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Credentials Deleted Successfully!"));
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openCredentialTab();
        Assertions.assertEquals(0,homePage.getSavedCredentialsCount());
    }
    @Test
    public void Test_Prevent_Credentials_Delete_By_Unauthorized_User() {
        HomePage homePage = signUpAndLogin("firstUser", "GoodUser", "authorizedCred", "credTest");
        String credentialUrl = "https://review.udacity.com/#!/rubrics/2724/view";
        String credUsername = "testCredentials";
        String credPassword = "password123";
        createUpdateCredential(credentialUrl, credUsername,credPassword, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Credentials Created Successfully!"));
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openCredentialTab();
        Credential firstUserCredential = homePage.getFirstCredential();
        homePage.logout();
        homePage = signUpAndLogin("secondUser", "BadUser", "unauthorizedCred", "credTest");
        homePage.openCredentialTab();
        driver.get("http://localhost:" + this.port + "/credential/delete/"+firstUserCredential.getCredentialId());
        resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Resource not found!"));
    }
    private void createUpdateCredential(String url, String username,String password, HomePage homePage, boolean createCred) {
        homePage.openCredentialTab();
        if (createCred) {
            homePage.addNewCredential();
        }else {
            homePage.editCredential();
        }
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
        homePage.saveCredentialChanges();
    }
}
