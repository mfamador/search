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

import pt.maisis.search.FieldMetaData;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.format.Formatter;
import pt.maisis.search.format.NumberFormatter;
import pt.maisis.search.format.AbstractDateFormatter;
import pt.maisis.search.format.MessageFormatter;

import java.util.List;
import java.util.Map;


public class ResultParameterMetaDataAdapter implements ResultParameterMetaData {
    private ResultParameterMetaData adaptee;

    public ResultParameterMetaDataAdapter(final ResultParameterMetaData adaptee) {
        this.adaptee = adaptee;
    }

    public String getFieldType() {
        Formatter formatter = getFormatter();
        if (formatter instanceof NumberFormatter) {
            return "number-field";
        }
        if (formatter instanceof AbstractDateFormatter) {
            return "date-field";
        }
        if (formatter instanceof MessageFormatter) {
            return "message-field";
        }
        return "string-field";
    }

    public String getFormatPattern() {
        return getFormatter().getPattern();
    }

    public boolean isFormatPatternAvailable() {
        return getFormatter().getPattern() != null;
    }


    
    public int getWidth() {
        return this.adaptee.getWidth();
    }

    public int getHeight() {
        return this.adaptee.getHeight();
    }

    public FieldMetaData getFieldMetaData() {
        return this.adaptee.getFieldMetaData();
    }

    public Formatter getFormatter() {
        return this.adaptee.getFormatter();
    }

    public Formatter getExporterFormatter() {
        return this.adaptee.getExporterFormatter();
    }

    public boolean isComposite() {
        return this.adaptee.isComposite();
    }

    public List getChildren() {
        return this.adaptee.getChildren();
    }

    public String getName() { 
        return this.adaptee.getName();
    }

    public String getSearchLabel() { 
        return this.adaptee.getSearchLabel();
    }

    public String getResultLabel() { 
        return this.adaptee.getResultLabel();
    }

    public String getAlign() {
        return this.adaptee.getAlign();
    }

    public String getProperty(final String name) {
        return this.adaptee.getProperty(name);
    }

    public Map getProperties() {
        return this.adaptee.getProperties();
    }

    public boolean isSelectable() {
        return this.adaptee.isSelectable();
    }

    public String getHeaderStyle() {
        return this.adaptee.getHeaderStyle();
    }

    public String getValueStyle() {
        return this.adaptee.getValueStyle();
    }
}
