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
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomDateFormatter extends DateFormatter {

    private static Log log = LogFactory.getLog(CustomDateFormatter.class);
    protected String pattern;

    public CustomDateFormatter() {
    }

    public void setPattern(final String pattern) {
        super.setPattern(pattern);
        this.pattern = pattern;
    }

    public Object format(Object obj, Locale locale) {

        try {
            Date date = toDate(obj);

            if (date == null) {
                return super.nullValue;
            }

            if (this.pattern == null) {
                return super.format(obj, locale);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(this.pattern);

            return dateFormat.format(date);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "";
    }
}
