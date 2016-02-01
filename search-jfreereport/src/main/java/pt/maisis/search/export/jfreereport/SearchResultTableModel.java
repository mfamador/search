/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
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
            Formatter formatter = this.result.getResultMetaData(column).getExporterFormatter();
            if(formatter == null)
                formatter = this.result.getResultMetaData(column).getFormatter();
            
            return formatter.format(value, locale);
        }
        return value;
    }
}
