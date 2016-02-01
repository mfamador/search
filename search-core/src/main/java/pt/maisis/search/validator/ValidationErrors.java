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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe tem como objectivo armazenar todos erros que ocorrem
 * na validacão de uma pesquisa.
 * <br>
 * Sempre que ocorre um erro, é adicionada uma nova instância da 
 * classe {@link ValidationError} a este <i>container</i>.
 *
 * @version 1.0
 */
public class ValidationErrors implements Serializable {

    private List errors = new ArrayList();

    public ValidationErrors() {
    }

    /**
     * Permite adicionar um novo erro ao container.
     */
    public void add(ValidationError error) {
        if (error != null) {
            this.errors.add(error);
        }
    }

    /**
     * Permite verificar se existe algum erro no container.
     */
    public boolean isEmpty() {
        return errors.isEmpty();
    }

    /**
     * Retorna o número de erros.
     */
    public int size() {
        return errors.size();
    }

    public List getErrors() {
        return this.errors;
    }

    public String toString() {
        return new StringBuffer().append("ValidationErrors").append(this.errors).toString();
    }
}
