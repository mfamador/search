package pt.maisis.search;

import pt.maisis.search.format.Formatter;

import java.util.List;

/**
 * Classe que representa um campo de retorno.
 *
 * @version 1.0
 */
public interface ResultParameterMetaData
        extends DynamicParameterMetaData {

    /**
     * Retorna o label de pesquisa para este result parameter.
     */
    String getSearchLabel();

    /**
     * Retorna o label de de retorno para este result parameter.
     * Este label é útil em situacões em que o label de pesquisa
     * {@link #getSearchLabel()} é diferente do label de retorno.
     */
    String getResultLabel();

    /**
     * Retorna o comprimento do campo de retorno.
     */
    int getWidth();

    /**
     * Retorna a altura do campo de retorno.
     */
    int getHeight();

    /**
     * Retorna o alinhamento do campo de retorno.
     */
    String getAlign();

    /**
     * Retorna o formatador {@link pt.maisis.search.format.Formatter} 
     * a utilizar para formatar o valor deste campo de retorno.
     */
    Formatter getFormatter();

    /**
     * Retorna o formatador {@link pt.maisis.search.format.Formatter}
     * a utilizar para formatar o valor deste campo de retorno.
     */
    Formatter getExporterFormatter();

    /**
     * Retorna o nome do campo da persistência em uso.
     * Por exemplo, este campo pode representar a coluna de
     * uma dada tabela/view na base de dados.
     */
    FieldMetaData getFieldMetaData();

    /**
     * Identifica se este campo de retorno é composto por mais
     * campos de retorno.
     */
    boolean isComposite();

    /**
     * Se o campo de retorno for composto {@link #isComposite()} 
     * retorna uma lista com os campos de retorno agregados.
     * <br/>
     * Caso o campo de retorno não for composto, retorna uma lista
     * vazia.
     */
    List getChildren();

    /**
     * Identifica se o result param é ou não <i>selectable</i>.
     * Isto é, dependendo do interface em que a framework é 
     * utilizada, pode ser dado a escolher aos utilizadores que
     * result params ele deseja ver no resultado de uma pesquisa.
     * <br/>
     * Neste tipo de ambiente pode ser desejado que nem todos os
     * result params possam ser seleccionáveis.
     */
    boolean isSelectable();

    /**
     * Nome do estilo a aplicar ao header do result parameter.
     * Em ambiente web, este estilo pode ser o nome de um estilo
     * css.
     *
     * @see #getValueStyle
     */
    String getHeaderStyle();

    /**
     * Nome do estilo a aplicar ao header do result parameter.
     * Em ambiente web, este estilo pode ser o nome de um estilo
     * css.
     *
     * @see #getHeaderStyle
     */
    String getValueStyle();
}
