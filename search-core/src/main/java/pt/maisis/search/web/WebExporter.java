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

import pt.maisis.search.export.SearchResultExporter;

public class WebExporter {

    private String name;
    private String htmlHeader;
    private String htmlFooter;
    private SearchResultExporter exporter;

    public WebExporter() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setExporter(final String clazz,
            final String template,
            final String useVelocityTemplate) {
        if (clazz != null) {
            try {
                this.exporter =
                        (SearchResultExporter) Class.forName(clazz).newInstance();

                this.exporter.setTemplate(template);

                this.exporter.useVelocityTemplate(Boolean.valueOf(useVelocityTemplate).booleanValue());

                // this.exporter.setSearchMetaData(this);

            } catch (Exception e) {
                e.printStackTrace(); // alertar
            }
        }
    }

    public SearchResultExporter getExporter() {
        return this.exporter;
    }

    public String getHtmlHeader() {
        return this.htmlHeader;
    }

    public void setHtmlHeader(final String htmlHeader) {
        this.htmlHeader = htmlHeader;
    }

    public String getHtmlFooter() {
        return this.htmlFooter;
    }

    public void setHtmlFooter(final String htmlFooter) {
        this.htmlFooter = htmlFooter;
    }
}
