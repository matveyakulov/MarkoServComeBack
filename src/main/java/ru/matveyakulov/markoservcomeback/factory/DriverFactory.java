package ru.matveyakulov.markoservcomeback.factory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class DriverFactory {

    public static ChromeDriver getDriver() {
        String path = new File(".\\src\\main\\resources\\utils\\chromedriver.exe").getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        return new ChromeDriver(options);
    }
}
