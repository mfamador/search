package pt.maisis.search;

/**
 * Classe que representa a <i>metadata</i> dos eventos que poderão 
 * ser definidos para um dado <i>container</i>.
 *
 * @version 1.0
 */
public interface EventMetaData {

    /**
     * O botão do rato é clicado em cima do <i>container</i>.
     */
    String getOnClick();

    /**
     * O botão do rato foi clicado duas vezes em cima do 
     * <i>container</i>.
     */
    String getOnDblClick();

    /**
     * O botão do rato foi carregado em cima do 
     * <i>container</i>.
     */
    String getOnMouseDown();

    /**
     * O botão do rato foi libertado em cima do 
     * <i>container</i>.
     */
    String getOnMouseUp();

    /**
     * O ponteiro do rato foi movido para cima do 
     * <i>container</i>.
     */
    String getOnMouseOver();

    /**
     * O ponteiro do rato foi movido dentro do 
     * <i>container</i>.
     */
    String getOnMouseMove();

    /**
     * O ponteiro do rato foi movido para fora do 
     * <i>container</i>.
     */
    String getOnMouseOut();

    /**
     * O <i>container</i> recebeu o <i>focus</i>.
     */
    String getOnFocus();

    /**
     * O <i>container</i> perdeu o <i>focus</i>.
     */
    String getOnBlur();

    /**
     * Uma tecla foi premida e libertada enquanto o 
     * <i>container</i> está seleccionado.
     */
    String getOnKeyPress();

    /**
     * Uma tecla foi premida enquanto o <i>container</i>
     * está seleccionado.
     */
    String getOnKeyDown();

    /**
     * Uma tecla foi libertada enquanto o <i>container</i>
     * estava seleccionado.
     */
    String getOnKeyUp();

    /**
     * Quando o valor do <i>container</i> é alterado.
     */
    String getOnChange();
}
