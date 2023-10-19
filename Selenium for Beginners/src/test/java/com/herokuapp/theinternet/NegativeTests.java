package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;

public class NegativeTests {
    WebDriver driver = new ChromeDriver();

    String homepageURL = "https://the-internet.herokuapp.com/";

    // test annotations are necessary to ensure that the tests are executed properly

    @Parameters({ "username", "password", "expectedMessage" })
    @Test
    public void negativeLoginTest(String user, String pwd, String expErrorMsg) throws Exception {
        System.out.println("Incorrect login test with username " + user + "password " + pwd);
        System.out.println("Starting app");
        // create instance of chrome driver for selenium
        driver.get(homepageURL + "/login");
        driver.manage().window().maximize();

        // try using a wait for the page to load. Not a good way to automate testing and
        // waits.
        sleep(1);

        // System.out.println("Page is opened at " + homepageURL);
        // find the username input field
        WebElement usernameElement = driver.findElement(By.id("username"));
        usernameElement.sendKeys(user);

        // find the password input field
        WebElement pword = driver.findElement(By.id("password"));
        pword.sendKeys(pwd);

        // // find the login button
        // if ID is available, it is the best locator
        WebElement loginbutton = driver.findElement(By.tagName("button"));
        loginbutton.click();

        sleep(3);

        // Verifications
        WebElement errorMsg = driver.findElement(By.id("flash"));
        String actualErrorMsg = errorMsg.getText();

        Assert.assertTrue(actualErrorMsg.contains(expErrorMsg),
                "Actual error does not contain expected error message " + actualErrorMsg);

    }

    // @Test
    // public void incorrectUserName() throws Exception {
    // System.out.println("Incorrect username test");
    // System.out.println("Starting app");
    // // create instance of chrome driver for selenium
    // driver.get(homepageURL + "/login");
    // driver.manage().window().maximize();

    // // try using a wait for the page to load. Not a good way to automate testing
    // and
    // // waits.
    // sleep(1);

    // // System.out.println("Page is opened at " + homepageURL);
    // // find the username input field
    // WebElement username = driver.findElement(By.id("username"));
    // username.sendKeys("incorrectUser");

    // // find the password input field
    // WebElement pword = driver.findElement(By.id("password"));
    // pword.sendKeys("SuperSecretPassword!");

    // // // find the login button
    // // if ID is available, it is the best locator
    // WebElement loginbutton = driver.findElement(By.tagName("button"));
    // loginbutton.click();

    // sleep(3);

    // // Verifications
    // WebElement errorMsg = driver.findElement(By.id("flash"));
    // String expectedError = "Your username is invalid!";
    // String actualErrorMsg = errorMsg.getText();

    // Assert.assertTrue(actualErrorMsg.contains(expectedError),
    // "Actual error does not contain expected error message " + actualErrorMsg);

    // }

    // annotations with parameters.
    // you can also set enabled or disabled the test so you can fix it later and
    // continue running other tests
    // You can group tests by setting parameter groups = {"testGroupName"}
    @Test(priority = 1, enabled = false)
    public void incorrectPwdTest() throws Exception {
        System.out.println("Incorrect username test");
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
        pword.sendKeys("wrongPwd");

        // // find the login button
        // if ID is available, it is the best locator
        WebElement loginbutton = driver.findElement(By.tagName("button"));
        loginbutton.click();

        sleep(3);

        // Verifications
        WebElement errorMsg = driver.findElement(By.id("flash"));
        String expectedError = "Your password is invalid!";
        String actualErrorMsg = errorMsg.getText();

        Assert.assertTrue(actualErrorMsg.contains(expectedError),
                "Actual error does not contain expected error message " + actualErrorMsg);
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.close();
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // you can give your tests with similar functionality parameters using the
    // @Parameters tag. This will allow you to adjust specific features without
    // having to change several test methods that contain the same thing

}
