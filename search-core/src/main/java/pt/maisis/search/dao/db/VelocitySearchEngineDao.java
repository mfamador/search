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
package pt.maisis.search.dao.db;

import pt.maisis.search.SearchQuery;
import pt.maisis.search.SearchParameter;
import pt.maisis.search.util.SearchSqlTool;
import pt.maisis.search.template.VelocityTemplate;

import org.apache.velocity.VelocityContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Iterator;

/**
 * Implementacão de {@link pt.maisis.search.dao.SearchEngineDao} que 
 * permite efectuar pesquisas a uma base de dados, tendo em conta 
 * templates SQL definidas nos ficheiros de configuracão das 
 * pesquisas.<p/>
 * 
 * Esta implementacão é útil em situacões em que a geracão dinâmica
 * de statements SQL não é suficientemente flexível.<p/>
 * 
 * As templates SQL são manipuladas utilizando a framework Velocity
 * (http://velocity.apache.org/).<p/>
 * 
 * No contexto Velocity são colocadas várias variáveis:
 * <ul>
 *   <li>
 *     <b>queryType</b>, permite identificar o tipo de query
 *      a executar (count ou get).
 *   </li>
 *   <li>
 *     <b>resultParams</b>, campos de retorno a retornar no resultado
 *     da pesquisa.
 *   </li>
 *   <li>
 *     <b>searchParams</b>, critérios de pesquisa.
 *   </li>
 *   <li>
 *     <b>orderParams</b>, campos a ordenar.
 *   </li>
 * </ul>
 *
 * A template sql, definida no descriptor XML é, geralmente, 
 * <i>evaluated</i> duas vezes. Uma para a capturar o número total
 * de resultados ({@link #COUNT}) e outra para capturar os resultados
 * propriamente ditos ({@link #GET}).<p/>
 * 
 * ...
 *
 * @version 1.0
 */
public class VelocitySearchEngineDao extends SearchEngineDaoImpl {

    private static Log log = LogFactory.getLog(VelocitySearchEngineDao.class);
    /** 
     * Atributo colocado no contexto do velocity que identifica
     * o tipo de query ({@link #COUNT} ou {@link #GET}). 
     */
    public static final String QUERY_TYPE = "queryType";
    /** Constante que identifica uma query do tipo 'count'. */
    public static final String COUNT = "count";
    /** Constante que identifica uma query do tipo 'get'. */
    public static final String GET = "get";
    //    public static final String PARAMS_INDEX  = "paramsIndex";
    /** Result params. */
    public static final String RESULT_PARAMS = "resultParams";
    /** Search params. */
    public static final String SEARCH_PARAMS = "searchParams";
    /** Order params. */
    public static final String ORDER_PARAMS = "orderParams";
    /** 
     * SQL tool ({@link SearchSqlTool}) que facilita a criacão das 
     * queries.
     */
    public static final String SQL_TOOL = "sql";
    private final SearchSqlTool sqlTool = new SearchSqlTool();

    public VelocitySearchEngineDao() {
    }

    @Override
    protected String buildCountStatement(final SearchQuery query) {
        VelocityContext context = new VelocityContext();
        context.put(QUERY_TYPE, COUNT);
        context.put(RESULT_PARAMS, query.getResultParams());

        List sp = query.getSearchParams();
        if (!sp.isEmpty()) {
            context.put(SEARCH_PARAMS, sp);
        }

        context.put(SQL_TOOL, sqlTool);
        setAllSearchParams(context, query.getSearchParams());

        VelocityTemplate velocity = VelocityTemplate.getInstance();
        return velocity.evaluate(context, query.getSqlTemplate());
    }

    @Override
    protected String buildStatement(final SearchQuery query) {
        VelocityContext context = new VelocityContext();
        context.put(QUERY_TYPE, GET);
        context.put(RESULT_PARAMS, query.getResultParams());

        List sp = query.getSearchParams();
        if (!sp.isEmpty()) {
            context.put(SEARCH_PARAMS, sp);
        }

        List op = query.getOrderParams();
        if (!op.isEmpty()) {
            context.put(ORDER_PARAMS, op);
        }

        // context.put(PARAMS_INDEX, buildIndex(query));
        context.put(SQL_TOOL, sqlTool);

        setAllSearchParams(context, query.getSearchParams());

        VelocityTemplate velocity = VelocityTemplate.getInstance();
        return velocity.evaluate(context, query.getSqlTemplate());
    }

    private void setAllSearchParams(final VelocityContext context,
            final List searchParams) {
        for (Iterator it = searchParams.iterator(); it.hasNext();) {
            SearchParameter sp = (SearchParameter) it.next();
            context.put(sp.getName(), sp.getValue());
        }
    }
}
