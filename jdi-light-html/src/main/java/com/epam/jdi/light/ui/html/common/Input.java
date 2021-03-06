package com.epam.jdi.light.ui.html.common;

import com.epam.jdi.light.elements.base.BaseElement;
import com.epam.jdi.light.elements.base.UIElement;

public interface Input extends BaseElement {
    String getText();
    void sendKeys(CharSequence... value);
    UIElement setText(String value);
    void clear();
    default void input(String value) {
        clear();
        sendKeys(value);
    }
    default void focus(){ sendKeys(""); }
}
