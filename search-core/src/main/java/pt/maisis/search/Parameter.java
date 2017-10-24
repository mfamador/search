package pt.maisis.search;

/**
 * Classe que todos os diferentes tipos de parâmetros devem
 * extender.<br/>
 *
 * Existem vários tipos de parâmetros: {@link SearchParameter}, 
 * {@link ResultParameter} e {@link OrderParameter}.
 *
 * @version 1.0
 */
public abstract class Parameter {

    private final String name;

    /**
     * Cria uma instância desta classe.
     * @param name  nome do parâmetro.
     * @param field o nome do campo que este parâmetro mapeia.
     */
    public Parameter(final String name) {
        this.name = name;
    }

    /**
     * Retorna o nome do parâmetro.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retorna o nome do(s) campo(s) que este parâmetro mapeia.
     */
    public abstract String getField();
}
