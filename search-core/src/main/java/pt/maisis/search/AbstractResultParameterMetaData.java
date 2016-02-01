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
package pt.maisis.search;

import pt.maisis.search.format.Formatter;
import pt.maisis.search.format.NullFormatter;

/**
 * Class that implements some methods of the 
 * {@link ResultParameterMetaData} interface common to
 * {@link SimpleResultParameterMetaData} and
 * {@link CompositeResultParameterMetaData} classes.
 *
 * @version 1.0
 */
public abstract class AbstractResultParameterMetaData
        extends DynamicParameterMetaDataImpl
        implements ResultParameterMetaData {

    /** Formatador a utilizar por defeito. */
    protected static final Formatter DEFAULT_FORMATTER = new NullFormatter();
    private String searchLabel;
    private String resultLabel;
    private String align;
    private boolean selectable = true;
    private String headerStyle;
    private String valueStyle;

    public AbstractResultParameterMetaData() {
    }

    public String getSearchLabel() {
        return this.searchLabel;
    }

    public void setSearchLabel(final String searchLabel) {
        this.searchLabel = searchLabel;
    }

    public String getResultLabel() {
        return (this.resultLabel == null)
                ? this.searchLabel
                : this.resultLabel;
    }

    public void setResultLabel(final String resultLabel) {
        this.resultLabel = resultLabel;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(final String align) {
        this.align = align;
    }

    public void setSelectable(final boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public String getHeaderStyle() {
        return this.headerStyle;
    }

    public void setHeaderStyle(final String headerStyle) {
        this.headerStyle = headerStyle;
    }

    public String getValueStyle() {
        return this.valueStyle;
    }

    public void setValueStyle(final String valueStyle) {
        this.valueStyle = valueStyle;
    }
}
