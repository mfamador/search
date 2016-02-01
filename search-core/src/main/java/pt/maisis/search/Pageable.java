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
 * Interface que marca um dado resultado como paginável.
 *
 * @version 1.0
 */
public interface Pageable {

    /**
     * Retorna <code>true</code> se o container contem mais 
     * páginas.
     */
    boolean hasNext();

    /**
     * Retorna <code>true</code> se a página actual não fôr
     * a primeira.
     */
    boolean hasPrevious();

    /**
     * Retorna o índice do início da próxima página.
     */
    int getStartOfNextPage();

    /**
     * Retorna o índice do início da página anterior.
     */
    int getStartOfPreviousPage();
}
