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
package pt.maisis.search.value;

import pt.maisis.search.util.ServiceLocator;
import pt.maisis.search.AjaxParameterMetaData;

import java.sql.Connection;
import javax.sql.DataSource;

public class AjaxValue {

    private String datasource;
    private String statement;
    private String source;
    private AjaxParameterMetaData parameter;

    public AjaxValue() {
    }

    public void setDataSource(final String datasource) {
        this.datasource = datasource;
    }

    public String getDataSource() {
        return this.datasource;
    }

    public void setStatement(final String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return this.statement;
    }

    public String getDatasource() {
        return this.datasource;
    }

    public void setDatasource(final String datasource) {
        this.datasource = datasource;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public void setParameterMetaData(final AjaxParameterMetaData parameter) {
        this.parameter = parameter;
    }

    public AjaxParameterMetaData getParameterMetaData() {
        return parameter;
    }

    private Connection getConnection() {
        try {
            ServiceLocator sl = ServiceLocator.getInstance();
            DataSource ds = (DataSource) sl.getDataSource(this.datasource);
            return ds.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
