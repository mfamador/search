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

/**
 * Classe que representa uma query de pesquisa.
 *
 * @version 1.0
 */
public interface SearchQuery {

    /**
     * Nome da datasource a ser utilizada.
     */
    String getDataSource();

    /**
     * Nome da tabela/view a utilizar na pesquisa.
     */
    String getTableSource();

    /**
     * SQL template.
     */
    String getSqlTemplate();

    /**
     * Critérios de pesquisa a utilizar na pesquisa.
     * Retorna uma lista vazia caso não tenham sido
     * especificados nenhuns critérios de pesquisa.
     * @see SearchParameter
     */
    List getSearchParams();

    SqlFragmentParameter getSqlFragmentParam();

    /**
     * Retorna os campos de retorno a serem devolvidos 
     * no resultado da pesquisa.
     * @see ResultParameter
     */
    List getResultParams();

    /**
     * Campos de ordenacão a utilizar na pesquisa.
     * Retorna uma lista vazia caso não tenham sido
     * especificados nenhuns campos de ordenacão.
     * @see OrderParameter
     */
    List getOrderParams();

    /**
     */
    int getResultRecordLimit();

    /**
     */
    void setResultRecordLimit(int resultRecordLimit);

    /**
     */
    int getQueryTimeout();

    /**
     */
    void setQueryTimeout(int queryTimout);
}
