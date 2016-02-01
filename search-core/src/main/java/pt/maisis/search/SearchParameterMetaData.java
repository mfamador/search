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

/**
 * Classe que representa um critério de pesquisa.
 *
 * @version 1.0
 */
public interface SearchParameterMetaData
        extends DynamicParameterMetaData {

    /**
     * Campo da persistência que este critério de pesquisa mapeia.
     */
    String getField();

    /**
     * Operador que deverá ser utilizado no processo de pesquisa para
     * se obter os resultados pretendidos.
     */
    String getOperator();

    /**
     * Retorna o <code>container</code> associado a este critério de 
     * pesquisa.
     */
    ContainerMetaData getContainer();

    /**
     * Retorna a metadata à qual este critério de pesquisa pertence.
     */
    SearchMetaData getSearchMetaData();
}
