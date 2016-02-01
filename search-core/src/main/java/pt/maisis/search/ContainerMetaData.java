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

import pt.maisis.search.validator.Validatable;
import pt.maisis.search.value.Value;
import pt.maisis.search.value.AjaxValue;

import java.io.Serializable;
import java.util.List;

/**
 * Os critérios de pesquisa são, normalmente, apresentados ao
 * utilizador através de componentes gráficos (<i>texts</i>,
 * <i>selects</i>, <i>textareas</i>, etc). Estes componentes
 * gráficos dependem do ambiente em uso (<i>web</i>, <i>swing</i>,
 * etc).
 * <p/>
 * De forma a simplificar a associacão destes componentes aos
 * respectivos critérios de pesquisa é utilizado o conceito de
 * <i>container</i>.
 * <br>
 * Foram disponibilizadas duas implementacões diferentes. Uma para
 * containers simples ({@link SimpleContainerMetaData}) e outra
 * para containers compostos ({@link CompositeContainerMetaData}).
 *
 * @version 1.0
 */
public interface ContainerMetaData extends Serializable {

    /** Identificador de um container do tipo hidden. */
    String TYPE_HIDDEN = "hidden";
    /** Identificador de um container do tipo radio. */
    String TYPE_RADIO = "radio";
    /** Identificador de um container do tipo text. */
    String TYPE_TEXT = "text";
    /** Identificador de um container do tipo select. */
    String TYPE_SELECT = "select";
    /** Identificador de um container do tipo checkbox. */
    String TYPE_CHECKBOX = "checkbox";
    /** Identificador de um container do tipo textarea. */
    String TYPE_TEXTAREA = "textarea";
    /** Identifica a colocacão do label em cima. */
    String TOP = "top";
    /** Identifica a posicão do label à esquerda. */
    String LEFT = "left";
    /** Identifica a posicão do label à direita. */
    String RIGHT = "rigth";
    /** Identifica a posicão do label em baixo. */
    String BOTTOM = "bottom";

    /**
     * Retorna o nome do container.
     */
    String getName();

    /**
     * Este método retorna uma implementacão do interface
     * {@link pt.maisis.search.validator.Validator}, mesmo quando
     * não é definido nenhum validator no descriptor da pesquisa.
     * Neste último caso é retornada uma instância de 
     * {@link pt.maisis.search.validator.NullValidator}.
     */
    Validatable getValidatable();

    /**
     * Label que identifica o container em termos de interface
     * gráfico.
     */
    String getLabel();

    /**
     * Retorna o tipo de orientacão do label {@link #getLabel()}.
     * <br/>
     * Tipos de orientacão:  {@link #TOP}, {@link #LEFT}, 
     * {@link #RIGHT} e {@link #BOTTOM}.
     */
    String getLabelOrientation();

    /**
     * Tipo de container.
     * Por exemplo, numa aplicacão web, o tipo de container pode 
     * ser um <i>text</i>, uma <i>select</i>, <i>textarea</i>, etc.
     */
    String getType();

    /**
     * Tamanho do container.
     */
    int getSize();

    /**
     * Número máximo de caracteres que podem ser introduzidos
     * no container.
     */
    int getInputSize();

    /**
     * Identifica se o container deve ser apresentado desactivado.
     */
    boolean isDisabled();

    /**
     * Identifica se o container deve ser só de leitura.
     */
    boolean isReadonly();

    boolean isChecked();

    /**
     * Retorna uma instânica de {@link EventMetaData} com os
     * eventos especificados no descriptor da pesquisa.
     */
    EventMetaData getEvent();

    /**
     * Retorna o valor, por defeito, do container.
     * <br/>
     * O tipo de valor por defeito é definido no ficheiro de
     * configuracão da pesquisa. Neste momento pode ser definida 
     * uma constante (<i>constant</i>) ou uma expressão 
     * (<i>expression</i>).
     */
    Value getDefaultValue();

    AjaxValue getAjaxValue();

    /**
     * Identifica se o container é do tipo composto 
     * {@link CompositeContainerMetaData}. Um container do tipo 
     * composto pode, internamente, conter outros containers.
     */
    boolean isComposite();

    /**
     * Retorna uma lista dos containers agregados, caso se trate
     * de um container composto {@link CompositeContainerMetaData}.
     * No caso do container ser um container simples
     * {@link SimpleContainerMetaData}, é retornada uma lista com
     * o próprio container. Desta forma, norma geral, só é útil 
     * invocar este método caso se trate de um cainter do tipo 
     * composto {@link #isComposite}.
     *
     * TODO: alterar o nome deste método.
     */
    List getChildren();

    /**
     * Retorna o <i>parent container</i> se o container tiver
     * um pai. Caso contrário retorna <code>null</code>.
     */
    ContainerMetaData getParent();

    /**
     * Retorna o critério de pesquisa (search parameter) ao qual
     * este container está associado.
     */
    SearchParameterMetaData getSearchParameterMetaData();
}
