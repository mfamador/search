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
package pt.maisis.search.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

public class LocaleConfig {

    public static final String LOCALE = "pt.maisis.search.Locale";

    private LocaleConfig() {
    }

    public static void setLocale(final HttpSession session,
            final Locale locale) {
        session.setAttribute(LOCALE, locale);
    }

    public static void setLocale(final HttpServletRequest request,
            final Locale locale) {
        request.setAttribute(LOCALE, locale);
    }

    public static Locale getLocale(final HttpSession session) {
        return (Locale) session.getAttribute(LOCALE);
    }

    public static Locale getLocale(final HttpServletRequest request) {
        Locale locale = (Locale) request.getAttribute(LOCALE);

        if (locale == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                locale = getLocale(session);
            }
            if (locale == null) {
                return request.getLocale();
            }
        }
        return locale;
    }
}
