package pt.maisis.search.dao.db.util;

/**
 * Classe que representa um fragmento sql.
 *
 * @version 1.0
 */
public class SQLFragmentCriteria extends Criteria {

    /** Fragmento sql. */
    private final String sql;

    /**
     * Cria um novo fragmento sql.
     * @param sql fragmento sql.
     */
    public SQLFragmentCriteria(final String sql) {
        this.sql = sql;
    }

    /**
     * Adiciona o fragmento sql ao dado buffer.
     * @param sb buffer a escrever.
     */
    public final void write(final StringBuffer sb) {
        sb.append(sql);
    }
}
