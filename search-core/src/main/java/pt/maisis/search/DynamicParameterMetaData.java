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

import java.util.Map;

/**
 * Interface que representa a <i>metadata</i> dinâmica de um dado
 * <i>parameter</i> ({@link SearchParameterMetaData} ou 
 * {@link ResultParameterMetaData}). <br/>
 *
 * Nos descriptors XML das pesquisas é possível definir, para além 
 * das propriedades disponíveis por defeito (name, field, etc), 
 * propriedades dinâmicas.
 *
 * @version 1.0
 */
public interface DynamicParameterMetaData
        extends ParameterMetaData {

    /**
     * Retorna todas as propriedades dinâmicas associadas
     * ao campo de retorno ou critério de pesquisa.
     */
    Map getProperties();

    /**
     * Retorna o valor da dada propriedade.
     */
    String getProperty(String name);
}
