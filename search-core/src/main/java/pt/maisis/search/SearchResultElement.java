package pt.maisis.search;

import java.io.Serializable;

/**
 * Classe que representa um único elemento do resultado de pesquisa
 * ({@link SearchResult}).
 *
 * @version 1.0
 */
public class SearchResultElement implements Serializable {

    private final Object[] values;
    private final int requiredStartIndex;
    private int counter = 0;

    /**
     * Cria um nova instância da classe com um dado tamanho.
     * @param length tamanho do elemento (numero de colunas).
     * @param requiredMark indice do inicio dos required result params.
     */
    public SearchResultElement(final int length, final int requiredStartIndex) {
        this.values = new Object[length];
        this.requiredStartIndex = requiredStartIndex;
    }

    /**
     * @return número de colunas de cada elemento.
     * Os required result params não são tidos em consideracão.
     */
    public int length() {
        return (this.requiredStartIndex == -1) ? values.length : this.requiredStartIndex;
    }

    /**
     * @return o valor do result param para o dado index.
     */
    public Object getValue(final int index) {
        return this.values[index];
    }

    /**
     * @return o valor do result param para o dado index.
     */
    public void setValue(final int index, Object value) {
        this.values[index] = value;
    }

    /**
     * @return número de colunas do result element
     * (inclui os required result params).
     */
    public int size() {
        return this.values.length;
    }

    /**
     * Permite adicionar um novo valor ao elemento.
     * @param value valor a adicionar.
     */
    public void addValue(final Object value) {
        this.values[this.counter++] = value;
    }

    @Override
    public String toString() {
        return new StringBuffer("SearchResultElement").append(this.values).toString();
    }
}
