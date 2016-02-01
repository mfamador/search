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

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.net.URL;

/**
 * Esta classe é uma implementacão do <i>Service Locator pattern</i>
 * e é utilizada para fazer o lookup de recursos como EJBHomes,
 * DataSources, etc.
 *
 * Esta implementacão é um "singleton" (garante que exista uma única
 * instância por aplicacão/class loader) que vai cachando todos os
 * objectos que encontra, assegurando que um JNDI lookup ocorra uma
 * única vez por cada nome.
 */
public class ServiceLocator {

    private InitialContext ic;
    private Map cache; // cache
    private static ServiceLocator me;

    static {
        try {
            me = new ServiceLocator();
        } catch (ServiceLocatorException e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        }
    }

    private ServiceLocator() throws ServiceLocatorException {
        try {
            ic = new InitialContext();
            cache = Collections.synchronizedMap(new HashMap());
        } catch (NamingException e) {
            throw new ServiceLocatorException(e);
        }
    }

    static public ServiceLocator getInstance() {
        return me;
    }

    /**
     * @return a DataSource correspondente ao param especificado
     */
    public DataSource getDataSource(String dataSourceName) throws ServiceLocatorException {
        DataSource dataSource = null;
        try {
            if (cache.containsKey(dataSourceName)) {
                dataSource = (DataSource) cache.get(dataSourceName);
            } else {
                dataSource = (DataSource) ic.lookup(dataSourceName);
                cache.put(dataSourceName, dataSource);
            }
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
        return dataSource;
    }

    /**
     * @return o valor da String identificada por envName
     */
    public String getString(String envName) throws ServiceLocatorException {
        String envEntry = null;
        try {
            envEntry = (String) ic.lookup(envName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
        return envEntry;
    }

    public URL getUrl(String envName) throws ServiceLocatorException {
        URL url = null;
        try {
            url = (URL) ic.lookup(envName);
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
        return url;
    }
}
