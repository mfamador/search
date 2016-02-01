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

import pt.maisis.search.dao.SearchEngineDaoFactory;
import pt.maisis.search.dao.SearchEngineDao;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * Container de todas as pesquisas configuradas.
 *
 * @version 1.0
 */
public class SearchSetMetaData implements Serializable {

    /** Índice das pesquisas. */
    private final Map metaData = new HashMap();
    /** Implementacões do motor de pesquisa, por pesquisa. */
    private final Map implementations = new HashMap();

    public SearchSetMetaData() {
    }

    public void add(SearchMetaData metaData) {
        this.metaData.put(metaData.getName(), metaData);

        this.implementations.put(metaData.getName(),
                SearchEngineDaoFactory.create(metaData.getSearchEngineClass()));
    }

    public int size() {
        return this.metaData.size();
    }

    public SearchMetaData getMetaData(String name) {
        return (SearchMetaData) this.metaData.get(name);
    }

    public SearchEngineDao getImplementation(String name) {
        return (SearchEngineDao) this.implementations.get(name);
    }

    public SearchMetaData[] getAll() {
        SearchMetaData[] result = new SearchMetaData[this.metaData.size()];
        this.metaData.values().toArray(result);
        return result;
    }

    public String toString() {
        return new StringBuffer("SearchSetMetaData[").append("metaData=").append(metaData.values()).append("]").toString();
    }
}
