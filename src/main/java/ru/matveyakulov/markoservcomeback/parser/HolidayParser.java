package ru.matveyakulov.markoservcomeback.parser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.matveyakulov.markoservcomeback.factory.DriverFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HolidayParser {

    public static List<String> getHolidaysByMouthAndDay(String mouth, Integer day){
        WebDriver driver = DriverFactory.getDriver();
        String uri = String.format("https://kakoysegodnyaprazdnik.ru/baza/%s/%s", mouth, day);
        try {
            driver.get(uri);
            return driver.findElements(By.cssSelector("body > div.wrap > div > div > div > div > div > span"))
                    .stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception e){
            return Collections.emptyList();
        } finally {
            driver.quit();
        }
    }
}
