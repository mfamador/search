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
package pt.maisis.search.util;

import pt.maisis.search.config.SearchConfig;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ...
 */
public class ApplicationResources {

    private static Log log = LogFactory.getLog(ApplicationResources.class);
    private static final String DEFAULT_RESOURCES = "search";
    private MessageResources messages;

    private ApplicationResources(Locale locale, String resources) {
        this.messages =
                (resources == null)
                ? MessageResources.getInstance(locale, DEFAULT_RESOURCES)
                : MessageResources.getInstance(locale, resources);
    }

    public static ApplicationResources getInstance(final Locale locale) {
        String resources = SearchConfig.getInstance().getMessageResources();
        return new ApplicationResources(locale, resources);
    }

    public String getMessage(final String key) {
        return this.messages.getMessage(key);
    }

    public String getMessage(final String key,
            final Object arg1) {
        return this.messages.getMessage(key, arg1);
    }

    public String getMessage(final String key,
            final Object arg1,
            final Object arg2) {
        return this.messages.getMessage(key, arg1, arg2);
    }

    public String getMessage(final String key,
            final Object arg1,
            final Object arg2,
            final Object arg3) {
        return this.messages.getMessage(key, arg1, arg2, arg3);
    }

    public String getMessage(final String key,
            final Object[] args) {
        return this.messages.getMessage(key, args);
    }

    public String getKey(final String search,
            final String container,
            final String key) {

        String newKey = new StringBuffer().append(search).append('.').append(container).append('.').append(key).toString();

        if (this.messages.contains(newKey)) {
            return newKey;
        }

        newKey = newKey.substring(search.length() + 1);
        if (messages.contains(newKey)) {
            return newKey;
        }

        return key;
    }
}
