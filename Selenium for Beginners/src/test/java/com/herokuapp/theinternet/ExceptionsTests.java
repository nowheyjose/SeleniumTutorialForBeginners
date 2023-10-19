package com.herokuapp.theinternet;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class ExceptionsTests {
    private WebDriver driver;

    private String homepageURL = "https://practicetestautomation.com/practice-test-exceptions/";

    // @BeforeMethod(alwaysRun = true)
    @Test(enabled = false)
    public void noSuchElementTest() throws Exception {
        // Test case 1: NoSuchElementException
        // Open page
        // Click Add button
        // Verify Row 2 input field is displayed
        // create driver
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(homepageURL);
        WebElement addbutton = driver.findElement(By.id("add_btn"));
        addbutton.click();
        // if no id found and need xpath for row true try using
        // By.xpath("//input[@class='input-field'])[2]"); where input is tag name, class
        // is input-field and index is [2] for row 2
        // using index is not the best idea since indexes can change if devs add changes
        // to the page or use xpath "//div[@id='row2']/input"

        // Use an implicit wait to allow the row to appear. IMPLICIT tells driver to
        // wait for a specific amount of time. If the element appears sooner than the
        // duration, it will continue running, if not it will fail the test

        /* ************** */
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        /* ************** */
        // implicits are not ideal. it is better to use an explicit wait. Explicit waits
        // allow you to wait for a condition to occur and are good for synchorinising
        // the state bwteeen the browser and its DOM
        // use an explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("row2")));
        Assert.assertTrue(row2.isDisplayed(), "row 2 is not displayed");

    }

    @Test(enabled = false)
    public void elementNotInteractableTest() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(homepageURL);
        WebElement addbutton = driver.findElement(By.id("add_btn"));
        addbutton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='rows']/div[3]/div[@class='row']/input[@type='text']")));
        row2.sendKeys("Sample send text");
        // driver.findElement(By.name("save"));
        WebElement saveBtn = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveBtn.click();

        WebElement confirm = driver.findElement(By.id("confirmation"));
        String msgTxt = confirm.getText();
        Assert.assertEquals(msgTxt, "Row 2 was saved", "Confirmation message text is not expected");
    }

    @Test(enabled = false)
    public void invalidElementStateExceptionTest() throws Exception {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(homepageURL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='row1']/input")));
        // if the user cannot interact, then selenium will not be able to either ideally
        WebElement editBtn = driver.findElement(By.id("edit_btn"));
        // wait.until(ExpectedConditions.elementToBeClickable(editBtn));
        editBtn.click();
        // add a wait until row 1 input is clickable
        wait.until(ExpectedConditions.elementToBeClickable(row));
        row.clear();
        row.sendKeys("Sample send text");
        WebElement saveBtn = driver.findElement(By.id("save_btn"));
        saveBtn.click();

        String value = row.getAttribute("value");
        Assert.assertEquals(value, "Sample send text", "Input text was not expected");
        // driver.findElement(By.name("save"));

        // Verify text was saved
        WebElement confirm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("confirmation")));
        String msgTxt = confirm.getText();
        Assert.assertEquals(msgTxt, "Row 1 was saved", "Confirmation message text is not expected");
    }

    @Test(enabled = false)
    public void staleElementReferenceExceptionTest() throws Exception {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(homepageURL);

        // instructions is set to disappear and you want to verify they clear out. Use
        // an explicit wait method to ensure that the element LOCATOR disappears

        WebElement addbutton = driver.findElement(By.id("add_btn"));
        addbutton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))),
                "Instructions are still displayed");
        // Assert false is used because we expect the result to return false
        // Assert.assertFalse(instructions.isDisplayed(), "Instructions still present");

    }

    @Test
    public void timeoutExceptionTest() throws Exception {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(homepageURL);

        WebElement addbutton = driver.findElement(By.id("add_btn"));
        addbutton.click();
        int secondsToWait = 6;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='row2']/input")));

        Assert.assertTrue(row2.isDisplayed(), "Input field is not yet displayed");

    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    private void tearDown() throws InterruptedException {
        driver.quit();
    }
}
