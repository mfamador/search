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

import java.util.List;
import java.util.ArrayList;

/**
 * ResultParameterMetaData composite.
 *
 * @version 1.0
 */
public class CompositeResultParameterMetaData
        extends AbstractResultParameterMetaData {

    private FieldMetaData field = new FieldMetaData();
    private int width;
    private boolean widthSet = false;
    private int height;
    private final List children = new ArrayList();

    public CompositeResultParameterMetaData() {
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int width) {
        this.width = width;
        this.widthSet = true;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public FieldMetaData getFieldMetaData() {
        return this.field;
    }

    public void addResultParameter(final ResultParameterMetaData rpmd) {
        this.field.addFieldMetaData(rpmd.getFieldMetaData());
        this.children.add(rpmd);

        if (!widthSet) {
            this.width += rpmd.getWidth();
        }
    }

    public Formatter getFormatter() {
        return DEFAULT_FORMATTER;
    }

    public boolean isComposite() {
        return true;
    }

    public List getChildren() {
        return this.children;
    }

    public String toString() {
        return new StringBuffer("CompositeResultParameterMetaData").append(this.children).toString();
    }

    public Formatter getExporterFormatter() {
        return null;
    }
}
