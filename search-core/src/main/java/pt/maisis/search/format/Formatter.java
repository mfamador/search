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
package pt.maisis.search.format;

import java.util.Locale;

/**
 * Interface que todos os formatadores (formatters) devem, directa
 * ou indirectamente, implementar.
 *
 * @version 1.0
 */
public interface Formatter {

    /**
     * Todos os formatadores podem usufruir da possibilidade de
     * especificar um padrão (pattern) no descriptor da pesquisa.
     */
    void setPattern(String pattern);

    /**
     * @return o padrão especificado no descriptor da pesquisa.
     */
    String getPattern();

    /**
     * String a utilizar caso o valor do result param for null.
     */
    void setNullValue(String nullValue);

    /**
     * Retorna o valor por defeito especificado através de
     * {@link #setNullValue()}.
     */
    String getNullValue();

    /**
     * Método que permite formatar um dado objecto utilizando
     * o locale por defeito da JVM.
     *
     * @param obj objecto a formaar.
     * @return String resultante da formatacão.
     */
    Object format(Object obj);

    /**
     * Método que permite formatar um dado objecto utilizando
     * o locale especificado.
     *
     * @param obj objecto a formatar.
     * @param locale intância de {@link Locale} a ter em conta
     *  na formatacão.
     * @return String resultante da formatacão.
     */
    Object format(Object obj, Locale locale);
}
