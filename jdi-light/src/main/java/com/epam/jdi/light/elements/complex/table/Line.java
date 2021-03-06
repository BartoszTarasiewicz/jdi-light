package com.epam.jdi.light.elements.complex.table;

import com.epam.jdi.light.elements.complex.IList;
import com.epam.jdi.tools.LinqUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.light.common.Exceptions.exception;
import static com.epam.jdi.tools.PrintUtils.print;

public class Line implements IList<String> {
    private List<String> elements;

    public Line(List<WebElement> elements) {
        this.elements = LinqUtils.select(elements, WebElement::getText);
    }

    public List<String> elements() {
        return elements;
    }

    public String getValue() {
        return print(elements);
    }
    public void clear() { elements.clear(); }
    public <T> T asData(Class<T> cl) {
        T instance;
        try { instance = cl.newInstance(); }
        catch (Exception ex) { throw exception("Can't convert row to Data (%s)", cl.getSimpleName()); }
        int i = 0;
        for (Field field : cl.getFields()) {
            try {
                field.set(instance, elements.get(i));
            } catch (Exception ex) {
                throw exception("Can't set table value '%s' to field '%s'", elements.get(i), field.getName());
            }
            i++;
        }
        return instance;
    }

}
