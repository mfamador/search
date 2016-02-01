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

import pt.maisis.search.SearchParameter;
import pt.maisis.search.OrderParameter;
import pt.maisis.search.Parameter;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que disponibiliza alguns métodos úteis para utilizar
 * no contexto das templates SQL das pesquisas.
 *
 * @version 1.0
 */
public class SearchSqlTool {

    private static Log log = LogFactory.getLog(SearchSqlTool.class);
    private static final int IN_SIZE = 256;
    private static final char COMMA = ',';
    private static final char SPACE = ' ';
    private static final char QUESTION_MARK = '?';
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    private static final String OR = "or";
    private static final String AND = "and";
    private static final String QUOTE = "'";

    public SearchSqlTool() {
    }

    /**
     * Retorna um ponto de interrogacão (<i>question mark</i>) por
     * cada valor do search param.
     */
    public String getQuestionMark(final SearchParameter param) {
        Object value = param.getValue();
        if (value == null) {
            return "?";
        }

        if ("between".equalsIgnoreCase(param.getOperator())) {
            return "? and ?";
        }

        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;

            StringBuffer sb = new StringBuffer();
            if (values.length > 0) {
                sb.append("(?");
                for (int i = 1; i < values.length; i++) {
                    sb.append(",?");
                }
                sb.append(")");
            }
            return sb.toString();
        }

        return "?";
    }

    /**
     * Retorna uma CSV com os nomes dos campos (<i>fields</i>)
     * da dada lista de params.
     */
    public String getCsv(final List params) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        Iterator it = params.iterator();
        StringBuffer sb = new StringBuffer();
        Parameter param = (Parameter) it.next();

        if (param instanceof OrderParameter) {
            do {
                OrderParameter o = (OrderParameter) param;
                Iterator fields = o.getFields().iterator();

                while (fields.hasNext()) {
                    String field = (String) fields.next();
                    sb.append(field).append(' ').append(getOrder(o));

                    if (fields.hasNext() || it.hasNext()) {
                        sb.append(',');
                    }
                }

            } while (it.hasNext() && ( param = (Parameter) it.next() ) != null);
        } else {
            sb.append(param.getField());
            while (it.hasNext()) {
                param = (Parameter) it.next();
                sb.append(',').append(param.getField());
            }
        }

        return sb.toString();
    }

    private String getOrder(OrderParameter o) {
        return ( o.getOrder() == 0 ? "asc" : "desc" );
    }

    /**
     * Retorna um critério SQL tendo em conta o dado parâmetro
     * de pesquisa.
     */
    public String getCriteria(final SearchParameter param) {

        StringBuffer sb = new StringBuffer();

        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(param.getOperator()).append(" (");
            sb.append(param.getField()).append(",");
        } else {
            sb.append(param.getField());
            sb.append(' ').append(param.getOperator());
        }

        sb.append(' ').append(getQuestionMark(param));
        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(")");
        }

        return sb.toString();
    }

    /**
     * Retorna uma string com todos os critérios de pesquisa
     * a incluir na cláusula where de um statement sql.
     */
    public String getCriterias(final List params) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        Iterator it = params.iterator();
        StringBuffer sb = new StringBuffer();
        SearchParameter param = (SearchParameter) it.next();
        sb.append(getCriteria(param));
        while (it.hasNext()) {
            param = (SearchParameter) it.next();
            sb.append(" and ").append(getCriteria(param));
        }

        return sb.toString();
    }

    /**
     * Retorna uma string com todos os critérios de pesquisa
     * a incluir na cláusula <i>where</i> de um statement sql, excepto
     * os nomes dos critérios passados na lista <code>excludes</code>.
     */
    public String getCriterias(final List params, final List excludes) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        if (excludes == null || excludes.isEmpty()) {
            return getCriterias(params);
        }

        Set set = new HashSet();
        set.addAll(excludes);

        Iterator it = params.iterator();
        StringBuffer sb = new StringBuffer();
        SearchParameter param;
        while (it.hasNext()) {
            param = (SearchParameter) it.next();
            if (!set.contains(param.getName())) {
                if (sb.length() > 0) {
                    sb.append(" and ");
                }
                sb.append(getCriteria(param));
            }
        }

        return sb.toString();
    }

    /**
     * Retorna uma string com todos os critérios de pesquisa
     * a incluir na cláusula <i>where</i> de um statement sql, excepto
     * o nome do critério do parâmetro <code>excludes</code>.
     */
    public String getCriterias(final List params, final String excludes) {
        List names = new ArrayList();
        names.add(excludes);
        return getCriterias(params, excludes);
    }

    /**
     * Retorna uma string representativa do dado <code>param</code>
     * a utilizar na cláusula <i>where</i> do statement sql.
     * 
     * @param param        instância de {@link SearchParameter}.
     * @param insertQuotes permite indicar se se deve colocar o 
     *                     valor do dado param entre plicas.
     */
    public String getValue(final SearchParameter param,
            final boolean insertQuotes) {
        return getValue(param.getField(),
                param.getOperator(),
                param.getValue(),
                insertQuotes);
    }

    /**
     * O mesmo que {@link #getValue(SearchParameter, boolean)}.
     * No entanto, neste caso não insere plicas.
     * 
     * @param param        instância de {@link SearchParameter}.
     */
    public String getValue(final SearchParameter param) {
        return getValue(param.getField(),
                param.getOperator(),
                param.getValue(),
                false);
    }

    /**
     * O mesmo que {@link #getValue(String, String, Object, boolean)}.
     * No entanto, neste caso não insere plicas.
     * 
     * @param param        instância de {@link SearchParameter}.
     */
    public String getValue(final String field,
            final String operator,
            final Object value) {
        return getValue(field, operator, value, false);
    }

    /**
     * Retorna uma string a utilizar na cláusula <i>where</i> 
     * do statement sql.<br/>
     * Exemplo: nome<i>(field)</i> =<i>(operator)</i> 'x'<i>(value)</i>
     * 
     * @param field        nome da coluna.
     * @param operator     operador a utilizar.
     * @param value        valor.
     * @param insertQuotes permite indicar se se deve colocar o 
     *                     valor do dado param entre plicas.
     */
    public String getValue(final String field,
            final String operator,
            final Object value,
            final boolean insertQuotes) {

        if (value == null) {
            return null;
        }

        if (value.getClass().isArray()) {
            Object[] values = (Object[]) value;
            StringBuffer sb = new StringBuffer();
            int size = values.length;
            if (size > 0) {
                sb.append(OPEN_BRACKET);
                for (int i = 0; i < size;) {
                    sb.append(field).append(SPACE);
                    sb.append(operator).append(SPACE);
                    sb.append(OPEN_BRACKET).append(values[i]);
                    ++i;
                    for (int j = 1; j < IN_SIZE && i < size; j++, i++) {
                        sb.append(COMMA);
                        if (insertQuotes) {
                            sb.append(QUOTE).append(values[i]).append(QUOTE);
                        } else {
                            sb.append(values[i]);
                        }
                    }
                    sb.append(CLOSE_BRACKET);

                    if (i < size) {
                        sb.append(SPACE).append(OR).append(SPACE);
                    }
                }
            }
            sb.append(CLOSE_BRACKET);
            return sb.toString();

        } else {
            StringBuffer sb = new StringBuffer(field);
            sb.append(SPACE);
            sb.append(operator);
            sb.append(SPACE);

            if (insertQuotes) {
                sb.append(QUOTE).append(value).append(QUOTE);
            } else {
                sb.append(value);
            }

            return sb.toString();
        }
    }
}
