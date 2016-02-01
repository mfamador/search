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
package pt.maisis.search.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.entity.Report;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.web.SearchShareHelper;

public final class SearchShareService {

    private static Log log = LogFactory.getLog(SearchShareService.class);
    private static final SearchShareService me = new SearchShareService();

    private SearchShareService() {
    }

    /**
     * Returns the instance of this service.
     */
    public static SearchShareService getInstance() {
        return me;
    }

    public SearchForm getSearchForm(long reportId) {

        SearchForm form = new SearchForm();

        Report report = (new Report()).find(reportId);
        log.debug("\n\n\n\n\n\n\nreport to execute : " + report + "\n\n\n\n\n\n");

        SearchShareHelper.fillForm(report, form);
        
        return form;
    }
}
