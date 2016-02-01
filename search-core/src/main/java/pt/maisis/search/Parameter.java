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
 * Classe que todos os diferentes tipos de parâmetros devem
 * extender.<br/>
 *
 * Existem vários tipos de parâmetros: {@link SearchParameter}, 
 * {@link ResultParameter} e {@link OrderParameter}.
 *
 * @version 1.0
 */
public abstract class Parameter {

    private final String name;

    /**
     * Cria uma instância desta classe.
     * @param name  nome do parâmetro.
     * @param field o nome do campo que este parâmetro mapeia.
     */
    public Parameter(final String name) {
        this.name = name;
    }

    /**
     * Retorna o nome do parâmetro.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retorna o nome do(s) campo(s) que este parâmetro mapeia.
     */
    public abstract String getField();
}
