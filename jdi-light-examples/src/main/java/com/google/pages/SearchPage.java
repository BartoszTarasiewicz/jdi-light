package com.google.pages;

import com.epam.jdi.light.elements.complex.UIList;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.light.elements.pageobjects.annotations.simple.WithText;
import com.google.custom.SearchResult;
import org.openqa.selenium.WebElement;

public class SearchPage extends WebPage {
    @Css(".srg>.g") public UIList<SearchResult> jobsE;
    @WithText("test Framework for UI") public WebElement gitHubJdi;

}
