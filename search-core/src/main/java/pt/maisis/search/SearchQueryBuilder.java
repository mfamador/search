package pt.maisis.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Builder responsável por criar instâncias de {@link SearchQuery}.
 *
 * @version 1.0
 */
public class SearchQueryBuilder {

    private final SearchQueryImpl query;
    private final SearchMetaData smd;
    // guardar os result params porque vou precisar
    // deles mais tarde
    private final List rpmds = new ArrayList();

    /**
     * Cria uma nova instância.
     */
    public SearchQueryBuilder(final String search) {
        this.smd = SearchEngine.getInstance().getSearch(search);
        if (this.smd == null) {
            throw new IllegalArgumentException("Invalid search name: " + search);
        }

        this.query = new SearchQueryImpl();
        this.query.setDataSource(smd.getDataSource());
        this.query.setTableSource(smd.getTableSource());
        this.query.setSqlTemplate(smd.getSqlTemplate());

        // Aproveitar para adicionar já os unselectable 
        // result params.
        List urpmds = this.smd.getUnselectableResultParameters();
        if (urpmds != null) {
            for (Iterator it = urpmds.iterator(); it.hasNext();) {
                ResultParameterMetaData rpmd =
                        (ResultParameterMetaData) it.next();
                this.rpmds.add(rpmd);
                buildResultParam(rpmd);
            }
        }
    }

    /**
     * Adiciona à <i>search query</i>, um novo critério de pesquisa.
     */
    public void buildSearchParam(final String name,
            final Object value) {
        this.buildSearchParam(name, value, null);
    }

    /**
     * Adiciona à <i>search query</i>, um critério de pesquisa tendo 
     * em conta o operador dado e não o definido no ficheiro de 
     * configuracão da pesquisa.
     */
    public void buildSearchParam(final String name,
            final Object value,
            final String operator) {
        SearchParameterMetaData spmd = this.smd.getSearchParameter(name);
        if (spmd == null) {
            throw new IllegalArgumentException("Invalid search param name: " + name);
        }
        SearchParameter sp = new SearchParameter(spmd.getName(),
                spmd.getField(),
                (operator == null) ? spmd.getOperator() : operator,
                spmd.getContainer().getType(),
                value);
        this.query.addSearchParam(sp);
    }

    public void buildSearchParams(final Map params) {
        Iterator entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            buildSearchParam((String) entry.getKey(), entry.getValue());
        }
    }

    /**
     * Adiciona um campo de retorno à <i>search query</i>.
     */
    public void buildResultParam(final String name) {
        ResultParameterMetaData rpmd = this.smd.getResultParameter(name);
        if (rpmd == null) {
            throw new IllegalArgumentException("Invalid result param name: " + name);
        }

        this.rpmds.add(rpmd); // vou precisar disto mais tarde
        buildResultParam(rpmd);
    }

    /**
     * Adiciona um conjunto de campos de retorno à <i>search query</i>.
     */
    public void buildResultParams(final String[] names) {
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                buildResultParam(names[i]);
            }
        }
    }

    /**
     * Adiciona um tipo de ordenacão à <i>search query</i>.
     */
    public void buildOrderParam(final String name,
            final int order) {
        ResultParameterMetaData rpmd = this.smd.getResultParameter(name);
        if (rpmd == null) {
            throw new IllegalArgumentException("Invalid order param name: " + name);
        }

        OrderParameter op = new OrderParameter(rpmd.getName(), rpmd.getFieldMetaData().getFields(), order);
        this.query.addOrderParam(op);
    }

    /**
     * Adiciona um conjunto de tipos de ordenacão à <i>search query</i>,
     * com um tipo de ordenacão constante.
     */
    public void buildOrderParams(final String names[],
            final int order) {
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                buildOrderParam(names[i], order);
            }
        }
    }

    /**
     * Adiciona um conjunto de tipos de ordenacão à <i>search query</i>,
     * com um tipo de ordenacão variável.
     */
    public void buildOrderParams(final String names[], final int[] order) {
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                buildOrderParam(names[i], order[i]);
            }

        }
    }

    public void buildSqlFragmentParam(final String value) {
        SqlFragmentParameterMetaData sql = smd.getSqlFragmentParameter();

        if (sql == null || (value == null && sql.getValue() == null)) {
            return;
        }
        SqlFragmentParameter sqlParam =
                new SqlFragmentParameter(value == null ? sql.getValue() : value);

        this.query.setSqlFragmentParam(sqlParam);
    }

    /**
     * Retorna a instância da ({@link SearchQuery}) construida.
     */
    public SearchQueryMetaData getSearchQuery() {
        if (this.query.getResultParams().isEmpty()) {
            throw new IllegalStateException("result params not set");
        }

        List rrpmds = this.smd.filterRequiredResultParameters(rpmds);

        Iterator it = rrpmds.iterator();
        while (it.hasNext()) {
            RequiredResultParameterMetaData rrpmd =
                    (RequiredResultParameterMetaData) it.next();

            ResultParameter rp =
                    new ResultParameter(rrpmd.getName(), rrpmd.getFieldMetaData().getFields(), true);
            this.query.addResultParam(rp);
        }

        rrpmds = this.smd.filterRequiredResultParametersExceptUnless(rpmds);
        return new SearchQueryMetaData(this.query, this.smd, this.rpmds, rrpmds);
    }

    private void buildResultParam(ResultParameterMetaData rpmd) {
        if (rpmd.isComposite()) {
            Iterator children = rpmd.getChildren().iterator();
            while (children.hasNext()) {
                ResultParameterMetaData child =
                        (ResultParameterMetaData) children.next();
                buildResultParam(child);
            }
        } else {
            ResultParameter rp =
                    new ResultParameter(rpmd.getName(), rpmd.getFieldMetaData().getFields());
            this.query.addResultParam(rp);
        }
    }
}
