package com.herokuapp.theinternet;

// import static java.lang.System.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.checkerframework.checker.units.qual.s;
//maybe just import all of the classes from the selenium library
import org.openqa.selenium.*;

//in the console you can use the $x("pasted the xpath") you should be able to see only one unique element. This will indicate your xpath only returns one unique element on the webpage.

//building XPATH locators: //tag[@attribute='value']
// the shorter your xpath is, the better as long as you know it is not possible that similar xpath locators will be created

public class PositiveTests {

    WebDriver driver = new ChromeDriver();

    String homepageURL = "https://the-internet.herokuapp.com/";

    // test annotations are necessary to ensure that the tests are executed properly
    @Test
    public void loginTest() throws Exception {
        System.out.println("loginTest started");
        System.out.println("Starting app");
        // create instance of chrome driver for selenium
        driver.get(homepageURL + "/login");
        driver.manage().window().maximize();

        // try using a wait for the page to load. Not a good way to automate testing and
        // waits.
        sleep(5);

        // System.out.println("Page is opened at " + homepageURL);
        // find the username input field
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

        // find the password input field
        WebElement pword = driver.findElement(By.id("password"));
        pword.sendKeys("SuperSecretPassword!");

        // // find the login button
        // if ID is available, it is the best locator
        WebElement loginbutton = driver.findElement(By.tagName("button"));
        loginbutton.click();

        // String expectedUrl = "http://the-internet.herokuapp.com/secure";
        // String actualUrl = driver.getCurrentUrl();
        // // create an assertion
        // Assert.assertEquals(actualUrl, expectedUrl, "Actual page URL is not equal to
        // the current URL");

        // write some verifications
        WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));
        String expectedString = "You logged into a secure area!";
        String actualMsg = successMessage.getText().toString();
        // Assert.assertEquals(actualMsg, expectedString, "Actual message is not equal
        // to expected");
        Assert.assertTrue(actualMsg.contains(expectedString), "Actual message does not contain expected  message");

        sleep(1);

        WebElement logoutButton = driver.findElement(By.xpath("//div[@id='content']//a[@href='/logout']"));
        // Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not
        // displayed");

        logoutButton.click();

        // success message
        // if there are multiple occurrences of a classname, the driver will pick the
        // first instance of the classname

        // make sure to close the driver after the test is finished. This does not exit
        // the entire web driver instance and instead only the current active tab or the
        // browser if only one tab opened.

        driver.close();
        System.out.println("Test Completed");

    }

    // sleep method to use multiple times
    // stop execution for a gien a mount of time
    // @param sec
    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}