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

import java.io.Serializable;

public class SelectedResultParameterMetaDataOrder
        extends ParameterMetaDataImpl
        implements Serializable {

    /** Identifica o resultado de uma pesquisa para ser ordenado
    por este param de forma ascendente. */
    public static final String ASCENDING_ORDER = "ASC";
    /** Identifica o resultado de uma pesquisa para ser ordenado
    por este param de forma descendente. */
    public static final String DESCENDING_ORDER = "DESC";
    private String orderTypeAsString;

    public SelectedResultParameterMetaDataOrder() {
    }

    public int getOrderType() {
        return this.orderTypeAsString == ASCENDING_ORDER
                ? OrderParameter.ASCENDING
                : OrderParameter.DESCENDING;
    }

    public String getOrderTypeAsString() {
        return this.orderTypeAsString;
    }

    public void setOrderTypeAsString(final String orderTypeAsString) {
        if (orderTypeAsString != null) {
            this.orderTypeAsString = (ASCENDING_ORDER.equalsIgnoreCase(orderTypeAsString))
                    ? ASCENDING_ORDER
                    : DESCENDING_ORDER;
        }
    }
}
