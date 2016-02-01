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

import pt.maisis.search.util.StringUtils;

import java.util.List;

/**
 * Classe que representa um result param.
 * Um result param pode mapear um ou mais campos na base
 * de dados (fields).
 *
 * @version 1.0
 */
public class ResultParameter extends Parameter {

    /**
     * Identifica se o result param e resultante de um required 
     * result param ou nao.  
     */
    private final boolean required;
    /** Colunas que o result param mapeia. */
    private final List fields;

    public ResultParameter(final String name,
            final List fields) {
        this(name, fields, false);
    }

    public ResultParameter(final String name,
            final List fields,
            final boolean required) {
        super(name);
        this.fields = fields;
        this.required = required;
    }

    /**
     * Retorna o número de campos associados a este result parameter.
     */
    public int size() {
        return this.fields.size();
    }

    /**
     * Retorna uma lista com todos os nomes dos campos associados
     * a este result param.
     */
    public List getFields() {
        return this.fields;
    }

    /**
     * Identifica se esta instância é resultante de um required
     * result param.
     */
    public boolean isRequired() {
        return this.required;
    }

    /** 
     * Retorna o nome do campo. 
     * Caso o result param mapear mais do que um campo, retorna 
     * uma string com os nomes dos campos separados por vírgulas.
     */
    public String getField() {
        return StringUtils.join(this.fields, ",");
    }

    public String toString() {
        return new StringBuffer("ResultParameter[").append("name=").append(getName()).append(", fields=").append(this.fields).append("]").toString();
    }
}
