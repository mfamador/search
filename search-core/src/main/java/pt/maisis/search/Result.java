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
 * Classe que contem o resultado de uma pesquisa bem
 * como a metadata associada a esse resultado.
 *
 * @version 1.0
 */
public interface Result {

    /**
     * Retorna o resultado, propriamente dito, da pesquisa.
     */
    SearchResult getSearchResult();

    /**
     * Indica se o resultado da pesquisa é {@link Pageable}.
     */
    boolean isSearchResultPageable();

    SearchMetaData getSearchMetaData();

    /**
     * @return os result parameters associados a este resultado
     * ({@link ResultParameterMetaData}). Os result parameters estão
     * ordenados pela ordem que foi especificada na pesquisa que
     * originou este reultado.
     */
    List getResultMetaData();

    /**
     * @return a instancia de ({@link ResultParameterMetaData}) para o
     * dado índice.
     */
    ResultParameterMetaData getResultMetaData(int index);

    /**
     * Este método e útil em situacões em que a hierarquia dos result 
     * params não é necessária.
     *
     * @return dos result parameters associados ao resultado.
     * Esta lista não contém os composite result params completos,
     * só as folhas. Por exemplo, se o composite 'A' tiver os filhos 
     * 'B' e 'C', só estes últimos serão considerados.
     */
    List getExpandedResultMetaData();

    /**
     * @return os required result parameters associados a este
     * resultado ({@link RequiredResultParameterMetaData}).
     */
    List getRequiredResultMetaData();
}
