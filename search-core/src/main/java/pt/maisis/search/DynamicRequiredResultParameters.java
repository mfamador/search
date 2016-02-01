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
 * Interface cuja implementacão permite retornar toda a 
 * <i>metadata</i> relativa aos <i>required result params</i> 
 * para uma dada pesquisa.<br/>
 * 
 * Útil em situacões em que a <i>metadata</i> só é conhecida em 
 * <i>runtime</i>.
 *
 * <p>
 * Exemplo:
 * <pre>
 *   &lt;dynamic-required-result-params class="..."/&gt;
 * </pre>
 * </p>
 *
 *
 * @version 1.0
 *
 * @see pt.maisis.search.DynamicResultParameters
 * @see pt.maisis.search.DynamicSearchParameters
 */
public interface DynamicRequiredResultParameters {

    /**
     * @return uma lista de instâncias de
     * {@link RequiredResultParameterMetaData}.
     */
    List getRequiredResultParameters();
}
