package org.mytests.tests;

import com.epam.jdi.light.driver.WebDriverFactory;
import org.mytests.uiobjects.example.site.SiteJdi;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.driver.get.DriverData.CAPABILITIES_FOR_CHROME;
import static com.epam.jdi.light.driver.get.RemoteDriver.DRIVER_REMOTE_URL;
import static com.epam.jdi.light.logger.LogLevels.STEP;
import static com.epam.jdi.light.settings.WebSettings.logger;
import static com.epam.jdi.light.ui.html.PageFactory.initElements;
import static org.mytests.uiobjects.example.site.SiteJdi.homePage;

public class TestsInit {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() {
        logger.setLogLevel(STEP);

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", "RedVarVar");
        sauceOpts.setCapability("accessKey", "774d79f4-9b78-4a8b-982c-fd5d829b0eaf");
        sauceOpts.setCapability("seleniumVersion", "4.0.0-alpha2");
        sauceOpts.setCapability("name", "Jdi test");
        sauceOpts.setCapability("maxDuration", 6000);
        sauceOpts.setCapability("commandTimeout", 600);
        sauceOpts.setCapability("build", "Build of test run for JDI");
        CAPABILITIES_FOR_CHROME.forEach(sauceOpts::setCapability);

        initElements(SiteJdi.class);
        homePage.open();
        logger.info("Run Tests");
    }

    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
    }

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        killAllSeleniumDrivers();
    }
}
