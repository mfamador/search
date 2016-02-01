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
import java.util.Map;

/**
 * Interface que representa a metadata de uma pesquisa.
 *
 * @version 1.0
 */
public interface SearchMetaData {

    /**
     * Retorna o nome identificador da pesquisa.
     */
    String getName();

    /**
     * Retorna o label da pesquisa.
     */
    String getLabel();

    /**
     * Retorna todas as propriedades dinâmicas associadas
     * ao campo de retorno ou critério de pesquisa.
     */
    Map getProperties();

    /**
     * Retorna o valor da dada propriedade.
     */
    String getProperty(String name);

    /**
     * Retorna uma descricão da pesquisa.
     */
    String getDescription();

    /**
     * Retorna o nome (JNDI) da datasource específico para
     * esta pesquisa.
     */
    String getDataSource();

    /**
     * Retorna o nome da tabela/view a utilizar para
     * capturar a informacão desejada.
     */
    String getTableSource();

    /**
     * Retorna a template sql definida para a pesquisa.
     */
    String getSqlTemplate();

    /**
     * Retorna o nome da classe que deve ser utilizada nas
     * pesquisas. Esta classe deve implementar o interface
     * {@link pt.maisis.search.dao.SearchEngineDao}.
     */
    String getSearchEngineClass();

    /**
     * Retorna um array com os nomes de todos os campos de
     * retorno.
     */
    String[] getResultParameterNames();

    /**
     * Retorna um array com os nomes dos campos de retorno
     * pré-seleccionados.
     */
    String[] getSelectedResultParameterNames();

    /**
     * Retorna um array como os nomes dos campos a ordenar.
     */
    String[] getSelectedResultParameterOrderNames();

    /**
     * Retorna o {@link ResultParameterMetaData} identificado
     * pelo parâmetro <code>name</code>.
     */
    ResultParameterMetaData getResultParameter(String name);

    /**
     * Retorna uma lista com os {@link ResultParameterMetaData}s
     * identificados pelo dado array de <code>names</code>.
     */
    List getResultParameters(String[] names);

    /**
     * Retorna uma lista com os {@link ResultParameterMetaData}s
     * definidos para esta pesquisa.
     */
    List getResultParameters();

    /**
     * Retorna uma lista com os {@link ResultParameterMetaData}s
     * definidos para esta pesquisa e em que a propriedade
     * <code>selectable</code> é <code>false</code>.
     */
    List getUnselectableResultParameters();

    /**
     * Retorna uma lista com os campos de retorno necessários
     * ({@link RequiredResultParameterMetaData}) definidos para
     * esta pesquisa.
     */
    List getRequiredResultParameters();

    /**
     * Filtra os required result params tendo em conta os nomes
     * identificadores dos result params e as regras definidas 
     * no ficheiro de configuracão da pesquisa.
     *
     * @param names result params a apresentar no resultado da
     *  pesquisa.
     * @return uma lista com os required result params filtrados.
     */
    List filterRequiredResultParameters(String[] names);

    /**
     * Faz exactamente o mesmo que o método 
     * {@link #filterRequiredResultParameters(String[])}. 
     * No entanto, em vez de um array de strings com os nomes 
     * identificadores dos result params, recebe uma lista
     * com os result params.
     *
     * @param rpmd result params a apresentar no resultado da
     *  pesquisa.
     * @return uma lista com os required result params filtrados.
     */
    List filterRequiredResultParameters(List rpmd);

    /**
     * Retorna todos os required result params especificados no
     * descriptor da pesquisa, excepto os que dependem de result
     * params não contidos no parâmetro <code>names</code>.
     *
     * @param names nomes dos result params.
     * @return uma lista com os required result params filtrados.
     */
    List filterRequiredResultParametersExceptUnless(String[] names);

    /**
     * Faz exactamente o mesmo que o método 
     * {@link #filterRequiredResultParameters(String[])}. 
     *
     * @param rpmd result params a apresentar no resultado da
     *  pesquisa.
     * @return uma lista com os required result params filtrados.
     */
    List filterRequiredResultParametersExceptUnless(List rpmd);

    /**
     * Retorna o {@link SearchParameterMetaData} identificado
     * pelo parâmetro <code>name</code>.
     */
    SearchParameterMetaData getSearchParameter(String name);

    /**
     * Retorna uma lista com os critérios de pesquisa
     * ({@link SearchParameterMetaData}) identificados pelo
     * parâmetro <code>names</code>.
     */
    List getSearchParameters(String[] names);

    /**
     * Retorna uma lista com todos os critérios de pesquisa
     * ({@link SearchParameterMetaData}) especificados no
     * ficheiro de configuracão.
     */
    List getSearchParameters();

    /**
     * Retorna uma lista com todos os campos de retorno
     * ({@link ResultParameterMetaData}), pré-seleccionados,
     * especificados no ficheiro de configuracão.
     */
    List getSelectedResultParameters();

    /**
     * Retorna uma lista com todos os campos de ordenacão,
     * especificados no ficheiro de configuracão da pesquisa.
     */
    List getSelectedResultParametersOrder();

    SqlFragmentParameterMetaData getSqlFragmentParameter();

    /**
     * Retorna o container identificado pelo dado nome.
     * <br/>
     * Por defeito, o nome do container é o mesmo que o do search 
     * param. No entanto, no caso dos composite containers o mesmo 
     * não acontece. Este método é, principalmente, útil em situacões
     * em que se pretende encontrar um container pelo nome e sendo 
     * o container filho de um composite container.
     */
    ContainerMetaData findContainer(String name);
}
