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
package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Locale;

/**
 * Interface que todos os <i>converters</i> devem, directa ou 
 * indirectamente, implementar.</br>
 * 
 * Por questões de facilidade foi criada a classe 
 * {@link AbstractConverter} que facilita a criacão de novos 
 * converters.
 *
 * @version 1.0
 */
public interface Converter {

    /**
     * No descriptor XML da pesquisa é possível definir propriedades
     * que podem ser utilizadas pelo <code>converter</code>.<br/>
     * 
     * Este método é incovado para cada propriedade definida.
     */
    void addProperty(String name,
            String value);

    /**
     * Método invocado pelo {@link validator} na fase em que precisa
     * de converter os dados.
     *
     * @return true se a conversão for efectuada com sucesso ou 
     *  false, caso contrário.
     */
    boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale);
}
