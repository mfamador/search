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

import java.util.List;

public class SearchQueryMetaData {

    private final SearchQuery query;
    private final SearchMetaData smd;
    private final List rpmds;
    private final List rrpmds;

    public SearchQueryMetaData(final SearchQuery query,
            final SearchMetaData smd,
            final List rpmds,
            final List rrpmds) {
        this.query = query;
        this.smd = smd;
        this.rpmds = rpmds;
        this.rrpmds = rrpmds;
    }

    public SearchQuery getQuery() {
        return this.query;
    }

    public SearchMetaData getSearchMetaData() {
        return this.smd;
    }

    public List getResultMetaData() {
        return this.rpmds;
    }

    public List getRequiredResultMetaData() {
        return this.rrpmds;
    }

    public void setResultRecordLimit(final int resultRecordLimit) {
        if (query != null) {
            query.setResultRecordLimit(resultRecordLimit);
        }
    }

    public void setQueryTimeout(final int queryTimeout) {
        if (query != null) {
            query.setQueryTimeout(queryTimeout);
        }
    }
}
