package com.google.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.simple.Css;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class HomePage extends WebPage {
    @Css("[name=q]") public WebElement search;

    public void search(String text) {
        search.sendKeys(text + Keys.ENTER);
    }
}
