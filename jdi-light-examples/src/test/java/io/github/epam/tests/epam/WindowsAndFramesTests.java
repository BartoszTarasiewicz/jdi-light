package io.github.epam.tests.epam;

import io.github.epam.SimpleTestsInit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.jdi.light.elements.base.WindowsManager.*;
import static io.github.epam.EpamGithubSite.*;
import static io.github.epam.steps.Preconditions.shouldBeLoggedIn;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.assertEquals;

public class WindowsAndFramesTests extends SimpleTestsInit {

    @BeforeMethod
    public void before() {
        shouldBeLoggedIn();
    }
    @Test
    public void windowsTest() {
        homePage.shouldBeOpened();
        homePage.githubLink.click();
        System.out.println("New window is opened: " + newWindowIsOpened());
        System.out.println("Windows count: " + windowsCount());
        originalWindow(); // open original (first) window
        switchToWindow(2); // open second window
        assertEquals(githubPage.repoDescription.getText(),
        "JDI is the test Framework for UI test automation");
        setWindowName("Github");
        switchToWindow(1); // open first (original) window

        homePage.jdiText.is().text(
            containsString("QUIS NOSTRUD EXERCITATION"));
        switchToWindow("Github");
        assertEquals(githubPage.repoDescription.getText(),
                "JDI is the test Framework for UI test automation");
    }

    @Test
    public void frameTest() {
        iframe.userIcon.click();
    }
}
