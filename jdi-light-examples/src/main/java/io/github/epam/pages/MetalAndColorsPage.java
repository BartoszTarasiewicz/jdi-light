package io.github.epam.pages;

import com.epam.jdi.light.elements.complex.Dropdown;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.light.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.light.elements.pageobjects.annotations.simple.ByText;
import com.epam.jdi.light.elements.pageobjects.annotations.simple.Css;
import io.github.epam.sections.LogSidebar;
import org.openqa.selenium.WebElement;

public class MetalAndColorsPage extends WebPage {
	@Css(".uui-side-bar[name='log-sidebar']") public LogSidebar logSidebar;
	@JDropdown(root = @FindBy(css ="div[ui=dropdown]"),
			value = @FindBy(css =".filter-option"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret")
	) public Dropdown colors;
	@JDropdown(root = @FindBy(css ="div[ui=combobox]"),
			value = @FindBy(css ="input"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret")
	) public Dropdown metals;
	/*@JDropdown(root = @FindBy(css ="div[ui=droplist]"),
			value = @FindBy(css ="button"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret"),
			isselected = @FindBy(xpath ="././/input")
	) public Dropdown vegetables;*/
	@ByText("Calculate") public WebElement calculate;
	@ByText("Submit") public WebElement submit;

}