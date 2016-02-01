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

/**
 * Classe que representa um parâmetro de pesquisa.
 *
 * @version 1.0
 */
public class SearchParameter extends Parameter {

    private final String operator;
    private final Object value;
    private final String field;
    private final String type;

    public SearchParameter(final String name,
            final String field,
            final String operator,
            final String type,
            final Object value) {
        super(name);
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.type = type;
    }

    public SearchParameter(final String name,
            final String field,
            final String operator,
            final Object value) {
        super(name);
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.type = "";
    }

    public String getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }

    public String getField() {
        return this.field;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return new StringBuffer("SearchParameter[").append("name=").append(getName()).append(", field=").append(this.field).append(", operator=").append(this.operator).append(", value=").append(this.value).append(", type=").append(this.type).append("]").toString();
    }
}
