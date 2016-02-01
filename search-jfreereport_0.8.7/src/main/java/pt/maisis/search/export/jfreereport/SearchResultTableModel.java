package pt.maisis.search.export.jfreereport;

import pt.maisis.search.Result;
import pt.maisis.search.SearchResultElement;
import pt.maisis.search.format.Formatter;

import javax.swing.table.AbstractTableModel;

import java.util.Locale;

public class SearchResultTableModel extends AbstractTableModel {
    private final Result result;
    private final boolean formatValues;
    private final Locale locale;


    public SearchResultTableModel(final Result result,
                                  final boolean formatValues,
                                  final Locale locale) {
        this.result = result;
        this.formatValues = formatValues;
        this.locale = locale;
    }

    public int getRowCount() {
        return this.result.getSearchResult().getResultCount();
    }

    public int getColumnCount() {
        return this.result.getSearchResult().getElement(0).length();
    }

    public String getColumnName(final int column) {
        try {
            return this.result.getSearchResult().getName(column);

        } catch (Exception e) {
            return null;
        }
    }

    public Object getValueAt(final int row, final int column) {
        SearchResultElement sre = this.result.getSearchResult().getElement(row);
        Object value = sre.getValue(column);
        
        if (this.formatValues) {
            Formatter formatter = this.result.getResultMetaData(column).getFormatter();
            return formatter.format(value, locale);
        }
        return value;
    }
}
