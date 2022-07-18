package com.udacity.jwdnd.course1.cloudstorage.PageObjectModel;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePage {
    @LocalServerPort
    private int port;
    private JavascriptExecutor js;
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 50);
    }

    @FindBy(css = "div[data-test-id='current-id']")
    @CacheLookup
    private WebElement txtUserId;

    @FindBy(id = "add-credential-button")
    @CacheLookup
    private WebElement btnAddNewCredential;

    @FindBy(id = "add-note-button")
    @CacheLookup
    private WebElement btnAddNewNote;

    @FindBy(id = "edit-note-button")
    @CacheLookup
    private WebElement btnEditNote;

    @FindBy(id = "delete-note-button")
    @CacheLookup
    private WebElement btnDeleteNote;

    @FindBy(css = "a[id='delete-note-button']")
    @CacheLookup
    private List<WebElement> btnListNoteDelete;

    @FindBy(id = "close-note-button")
    @CacheLookup
    private WebElement btnCloseNote;

    @FindBy(id = "show-note-title")
    @CacheLookup
    private WebElement tableNoteTitle;
    @FindBy(id = "show-note-description")
    @CacheLookup
    private WebElement tableNoteDescription;
    @FindBy(id = "nav-credentials-tab")
    @CacheLookup
    private WebElement tabNavCredentials;

    @FindBy(id = "show-credential-url")
    @CacheLookup
    private WebElement tableCredentialUrl;

    @FindBy(id = "show-credential-username")
    @CacheLookup
    private WebElement tableCredentialUsername;

    @FindBy(id = "show-credential-password")
    @CacheLookup
    private WebElement tableCredentialPassword;
    @FindBy(id = "delete-credential-button")
    @CacheLookup
    private WebElement btnDeleteCredentials;

    @FindBy(css = "a[id='delete-credential-button']")
    @CacheLookup
    private List<WebElement> btnListDeleteCredentials;

    @FindBy(id = "note-description")
    @CacheLookup
    private WebElement txtNoteDescription;

    @FindBy(id = "edit-credential-button")
    @CacheLookup
    private WebElement btnEditCredential;

    @FindBy(id = "nav-files-tab")
    @CacheLookup
    private WebElement tabNavFiles;

    @FindBy(id = "logout-button")
    @CacheLookup
    private WebElement btnLogout;

    @FindBy(id = "nav-notes-tab")
    @CacheLookup
    private WebElement tabNavNotes;

    @FindBy(id = "credential-password")
    @CacheLookup
    private WebElement txtCredentialPassword;

    @FindBy(id = "save-note-button")
    @CacheLookup
    private WebElement btnSaveNote;

    @FindBy(id = "credential-save-button")
    @CacheLookup
    private WebElement btnSaveCredential;

    @FindBy(id = "note-title")
    @CacheLookup
    private WebElement txtNoteTitle;

    @FindBy(id = "btnFileUpload")
    @CacheLookup
    private WebElement btnFileUpload;

    @FindBy(id = "delete-file-button")
    @CacheLookup
    private WebElement btnFileDelete;

    @FindBy(css = "a[data-test-id='download-file-button']")
    @CacheLookup
    private List<WebElement> btnListFileDownload;

    @FindBy(id = "newFile")
    @CacheLookup
    private WebElement btnNewFile;

    @FindBy(id = "show-file-name")
    @CacheLookup
    private WebElement tableFileName;

    @FindBy(id = "show-file-type")
    @CacheLookup
    private WebElement tableFileType;

    @FindBy(id = "show-file-size")
    @CacheLookup
    private WebElement tableFileSize;
    @FindBy(id = "credential-url")
    @CacheLookup
    private WebElement txtCredentialUrl;

    @FindBy(id = "show-file-id")
    @CacheLookup
    private WebElement tableFileId;

    @FindBy(id = "show-credential-id")
    @CacheLookup
    private WebElement tableCredentialId;

    @FindBy(id = "show-note-id")
    @CacheLookup
    private WebElement tableNoteId;
    @FindBy(id = "credential-username")
    @CacheLookup
    private WebElement txtCredentialUsername;

    public void logout() {
        js.executeScript("arguments[0].click();", btnLogout);
    }

    public void editNote() {
        js.executeScript("arguments[0].click();", btnEditNote);
    }

    public void editCredential() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", btnDeleteNote);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", btnDeleteCredentials);
    }

    public void setUploadFile(CharSequence filePath){
        wait.until(ExpectedConditions.visibilityOf(btnNewFile));
        btnNewFile.sendKeys(filePath);
    }
    public void uploadFile() {
        js.executeScript("arguments[0].click();", btnFileUpload);
    }

    public void deleteFile() {
        js.executeScript("arguments[0].click();", btnFileDelete);
    }

    public void downloadFirstFile() {
        js.executeScript("arguments[0].click();", btnListFileDownload.get(0));
    }

    public int getUploadedFilesCount(){
        return btnListFileDownload != null ? btnListFileDownload.size() : 0;
    }

    public int getUploadedNotesCount(){
        return btnListNoteDelete != null ? btnListNoteDelete.size() : 0;
    }
    public void addNewNote() {
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", btnAddNewCredential);
    }
    public int getSavedCredentialsCount(){
        return btnListDeleteCredentials != null ? btnListDeleteCredentials.size() : 0;
    }
    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public String getCredentialUsername() {
        return wait.until(ExpectedConditions.visibilityOf(txtCredentialUsername)).getAttribute("value");
    }

    public String getCredentialPassword() {
        return wait.until(ExpectedConditions.visibilityOf(txtCredentialPassword)).getAttribute("value");
    }
    public int getCredentialId(){
        return Integer.valueOf(tableCredentialId.getAttribute("textContent"));
    }

    public String getCredentialUrl() {
        return txtCredentialUrl.getAttribute("value");
    }
    public void openNotesTab() {
        js.executeScript("arguments[0].click();", tabNavNotes);
    }

    public void openCredentialTab() {
        js.executeScript("arguments[0].click();", tabNavCredentials);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='" + noteDescription + "';", txtNoteDescription);
    }

    public void saveNoteChanges() {
        js.executeScript("arguments[0].click();", btnSaveNote);
    }

    public void saveCredentialChanges() {
        js.executeScript("arguments[0].click();", btnSaveCredential);
    }

    public boolean notesListEmpty(WebDriver driver) {
        return !isElementPresent(By.name("show-note-title"), driver) && !isElementPresent(By.name("show-note-description"), driver);
    }

    public boolean credentialsListEmpty(WebDriver driver) {
        return !isElementPresent(By.id("show-credential-url"), driver) &&
               !isElementPresent(By.id("show-credential-username"), driver) &&
               !isElementPresent(By.id("show-credential-password"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();
        Integer noteId = Integer.valueOf(tableNoteId.getAttribute("textContent"));
        Integer userId = Integer.valueOf(txtUserId.getAttribute("textContent"));
        return new Note(noteId, title, description, userId);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUrl)).getText();
        String username = tableCredentialUsername.getText();
        String password = tableCredentialPassword.getText();
        Integer credentialId = Integer.valueOf(tableCredentialId.getAttribute("textContent"));
        Integer userId = Integer.valueOf(txtUserId.getAttribute("textContent"));
        return new Credential(credentialId, url, username, "saltKey", password, userId);
    }

    public File getFirstFile() {
        String fileName = wait.until(ExpectedConditions.elementToBeClickable(tableFileName)).getText();
        String fileType = tableFileType.getText();
        String fileSize = tableFileSize.getText();
        String test = tableFileId.getAttribute("textContent");
        System.out.println(test);
        Integer fileId = Integer.valueOf(tableFileId.getAttribute("textContent"));
        Integer userId = Integer.valueOf(txtUserId.getAttribute("textContent"));
        return new File(fileId, fileName, fileType, fileSize,userId);
    }
}
