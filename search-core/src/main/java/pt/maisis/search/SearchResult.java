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
 * Classe que representa o resultado de uma pesquisa.
 *
 * @version 1.0
 */
public interface SearchResult {

    /**
     * @return a lista de elementos ({@link SearchResultElement})
     * contidos no resultado.
     */
    List getElements();

    List getNames();

    /**
     * @return o {@link SearchResultElement} para o dado
     * <code>index</code>.
     */
    SearchResultElement getElement(int index);

    /**
     * @return o número de elementos contidos no resultado da
     * pesquisa.
     */
    int getResultCount();

    /**
     * @return <code>true</code> se o resultado da pesquisa estiver
     * vazio.
     */
    boolean isEmpty();

    /**
     * Permite verificar se o resultado contém o campo de retorno
     * identificado pelo parâmetro <code>name</code>.
     */
    boolean contains(String name);

    /**
     * @return o índice de um dado result parameter através
     * do nome.
     */
    int getIndex(String name);

    String getName(int index);
}
