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
package pt.maisis.search.format;

import java.util.Locale;
import java.text.MessageFormat;

public class NullMessageFormatter extends AbstractFormatter {

    public NullMessageFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        if (obj == null) {
            return super.nullValue;
        }
        if (!(obj instanceof Object[])) {
            obj = new Object[]{obj.toString()};
        }

        Object[] values = preFormat(obj);
        if (values == null) {
            return null;
        }

        return MessageFormat.format(super.pattern, values);
    }

    protected Object[] preFormat(Object obj) {
        Object[] objs = (Object[]) obj;
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                return null;
            }
        }
        return objs;
    }
}
