/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.validator;

import java.io.Serializable;
import pt.maisis.search.util.ArrayUtils;

/**
 * Classe que representa um erro de validacão.
 *
 * @version 1.0
 *
 * @see ValidationErrors
 */
public class ValidationError implements Serializable {

    private final String key;
    private final Object[] values;

    public ValidationError(final String key) {
        this(key, null);
    }

    public ValidationError(final String key,
            final Object value) {
        this(key, new Object[]{value});
    }

    public ValidationError(final String key,
            final Object value1,
            final Object value2) {
        this(key, new Object[]{value1, value2});
    }

    public ValidationError(final String key,
            final Object value1,
            final Object value2,
            final Object value3) {
        this(key, new Object[]{value1, value2, value3});
    }

    public ValidationError(final String key,
            final Object[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return (this.key);
    }

    public Object[] getValues() {
        return (this.values);
    }

    public String toString() {
        return new StringBuffer("ValidationError[").append("key=").append(this.key).append(", value=").append(ArrayUtils.asList(this.values)).append("]").toString();
    }
}
