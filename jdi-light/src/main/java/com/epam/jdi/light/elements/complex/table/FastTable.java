package com.epam.jdi.light.elements.complex.table;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.pageobjects.annotations.objects.JTable;
import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.light.common.Exceptions.exception;
import static com.epam.jdi.light.driver.WebDriverByUtils.fillByMsgTemplate;
import static com.epam.jdi.light.driver.WebDriverByUtils.fillByTemplate;
import static com.epam.jdi.light.elements.init.UIFactory.$;
import static com.epam.jdi.light.elements.init.UIFactory.$$;
import static com.epam.jdi.light.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.light.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;
import static com.epam.jdi.tools.LinqUtils.listEquals;
import static com.epam.jdi.tools.LinqUtils.select;

public class FastTable extends Table {
    protected By cellLocator;
    protected By allCells;
    protected MapArray<String, List<WebElement>> rows = new MapArray<>();
    protected MapArray<String, List<WebElement>> columns = new MapArray<>();
    protected MapArray<String, MapArray<String, WebElement>> cells = new MapArray<>();

    public FastTable() {
        rowsLocator = By.xpath("//tr[%s]/td");
        columnsLocator = By.xpath("//tr/td[%s]");
        cellLocator = By.xpath("//tr[{1}]/td[{0}]");
        allCells = By.cssSelector("td");
        count = new CacheValue<>(() -> webColumn(1).size());
    }
    private Boolean headerIsRow = null;
    private int getRowIndex(int rowNum) {
        if (headerIsRow == null) {
            List<String> firstRow = $$(fillByTemplate(rowsLocator, 1), this).values();
            headerIsRow = firstRow.isEmpty() || listEquals(headers.get(), firstRow);
        }
        return headerIsRow ? rowNum + 1 : rowNum;
    }
    private WebList getRow(int rowNum) {
        return $$(fillByTemplate(rowsLocator, getRowIndex(rowNum), this));
    }
    private WebList getColumn(int colNum) {
        return $$(fillByTemplate(columnsLocator, colNum), this);
    }
    private WebElement getCell(int colNum, int rowNum) {
        return $(fillByMsgTemplate(cellLocator, colNum, getRowIndex(rowNum), this));
    }

    public List<WebElement> webRow(int rowNum) {
        if (!rows.has(rowNum+"")) {
            if (gotTable)
                return select(cells, c -> c.value.get(rowNum+""));
            rows.add(rowNum+"", getRow(rowNum));
        }
        return rows.get(rowNum+"");
    }
    public List<WebElement> webColumn(int colNum) {
        if (!columns.has(colNum+"")) {
            if (gotTable)
                return cells.get(colNum + "").values();
            columns.add(colNum + "", getColumn(colNum));
        }
        return columns.get(colNum+"");
    }
    public WebElement webCell(int colNum, int rowNum) {
        if (!gotTable) {
            if (rows.has(rowNum + ""))
                return rows.get(rowNum + "").get(colNum - 1);
            if (columns.has(colNum + ""))
                return columns.get(colNum + "").get(rowNum - 1);
            if (!cells.has(colNum + ""))
                cells.add(colNum + "", new MapArray<>(rowNum + "", getCell(colNum, rowNum)));
            else if (!cells.get(colNum + "").has(rowNum + ""))
                cells.get(colNum + "").add(rowNum + "", getCell(colNum, rowNum));
        }
        return cells.get(colNum+"").get(rowNum+"");
    }
    @Override
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JTable.class, Table.class))
            return;
        super.setup(field);
        JTable j = field.getAnnotation(JTable.class);
        By cell = findByToBy(j.cell());
        if (cell != null)
            this.cellLocator = cell;
    }
    private boolean gotTable = false;
    public FastTable getTable() {
        if (!gotTable) {
            try {
                List<WebElement> listOfCells = $$(allCells, parent).getAll();
                cells = new MapArray<>();
                int k = 0;
                int j = 1;
                for (int i = 1; i <= size.get(); i++)
                    cells.add(i+"", new MapArray<>());
                while (k < listOfCells.size()) {
                    for (int i = 1; i <= size.get(); i++)
                        cells.get(i+"").add(j+"", listOfCells.get(k++));
                    j++;
                }
                gotTable = true;
            } catch (Exception ex) {throw exception("Can't get all cells. " + ex.getMessage()); }
        }
        return this;
    }

    @Override
    public String getValue() {
        getTable();
        return super.getValue();
    }

}
