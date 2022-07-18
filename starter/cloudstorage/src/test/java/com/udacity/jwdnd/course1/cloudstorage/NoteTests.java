package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjectModel.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests extends CloudStorageApplicationTestBase {

    @Test
    public void Test_Login_Create_New_Note_And_Verify_Contents() {
        HomePage homePage = signUpAndLogin("test", "createNote", "newNote", "noteTest");
        String noteTitle = "Note Create Test";
        String noteDescription = "Note Create Test Description";
        createUpdateNote(noteTitle, noteDescription, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openNotesTab();
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(note.getNoteTitle(), noteTitle);
        Assertions.assertEquals(note.getNotedescription(), noteDescription);
    }

    @Test
    public void Test_Login_Update_Note_Verify_Updated_Contents() {
        HomePage homePage = signUpAndLogin("test", "updateNote", "updateNote", "noteTest");
        String noteTitle = "Note Create Test";
        String noteDescription = "Note Create Test Description";
        createUpdateNote(noteTitle, noteDescription, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openNotesTab();
        String updatedNoteTitle = "Note Update Test";
        String updatedNoteDescription = "Note Update Test Description";
        createUpdateNote(updatedNoteTitle, updatedNoteDescription, homePage, false);
        resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openNotesTab();
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(note.getNoteTitle(), updatedNoteTitle);
        Assertions.assertEquals(note.getNotedescription(), updatedNoteDescription);
    }

    @Test
    public void Test_Delete_Existing_Note_And_Verify_Its_Removed() {
        HomePage homePage = signUpAndLogin("test", "deleteNote", "deleteNote", "noteTest");
        String noteTitle = "Note Create Test";
        String noteDescription = "Note Create Test Description";
        createUpdateNote(noteTitle, noteDescription, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage.logout();
        homePage = existingUserLogin("deleteNote","noteTest");
        homePage.openNotesTab();
        homePage.deleteNote();
        resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Note Deleted Successfully!"));
        resultPage.redirectToHome();
        Assertions.assertEquals(0,homePage.getUploadedNotesCount());
    }

    @Test
    public void Test_Prevent_Note_Delete_By_Unauthorized_User() {
        HomePage homePage = signUpAndLogin("firstUser", "goodUser", "authorizedNote", "noteTest");
        String noteTitle = "Note Create Test";
        String noteDescription = "Note Create Test Description";
        createUpdateNote(noteTitle, noteDescription, homePage, true);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.redirectToHome();
        homePage = new HomePage(driver);
        homePage.openNotesTab();
        Note firstUserNote = homePage.getFirstNote();
        homePage.logout();
        homePage = signUpAndLogin("secondUser", "badUser", "unauthorizedNote", "noteTest");
        homePage.openNotesTab();
        driver.get("http://localhost:" + this.port + "/note/delete-note/"+firstUserNote.getNoteId());
        resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.getMessageDisplayed().contains("Resource not found!"));
    }
    private void createUpdateNote(String noteTitle, String noteDescription, HomePage homePage, boolean createNote) {
        homePage.openNotesTab();
        if (createNote) {
            homePage.addNewNote();
        }else {
            homePage.editNote();
        }
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();
    }
}
