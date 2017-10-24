package pt.maisis.search;

import pt.maisis.search.util.ParameterMetaDataUtils;
import pt.maisis.search.config.SearchConfig;

import java.io.Serializable;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;

/**
 * Implementacão de <code>SearchMetaData</code>.
 *
 * @version 1.0
 */
public class SearchMetaDataImpl implements SearchMetaData, Serializable {

    /** Lista vazia. */
    private static final List EMPTY_LIST = new ArrayList(0);
    /** Search params index. */
    private final Map spmdsMap = new LinkedHashMap();
    /** Result params index. */
    private final Map rpmdsMap = new LinkedHashMap();
    /** Containers index. */
    private final Map containers = new HashMap();
    /** Search params metadata. */
    private List spmds = new ArrayList();
    /** Result params metadata. */
    private List rpmds = new ArrayList();
    /** Unselectable result params. */
    private List urpmds = new ArrayList();
    /** Required result params metadata. */
    private List rrpmds = new ArrayList();
    /** Selected result params metadata. */
    private List srpmds = new ArrayList();
    /** Result params metadata order. */
    private List srpmdos = new ArrayList();
    /** SQL fragment. */
    private SqlFragmentParameterMetaData sqlFragment;
    // result param names
    private String[] rpmdNames;
    private String[] srpmdNames;
    private String[] srpmdoNames;
    private String name;
    private String label;
    private String description;
    // Default datasource.
    // TODO: remover esta dependência.
    private String dataSource = SearchConfig.getInstance().getDataSourceName();
    private String tableSource;
    /** Map vazio a ser utilizado quando nenhuma propriedade dinamica e 
    declarada no descriptor xml da pesquisa. */
    private static final Map NO_PROPERTIES = new HashMap(0);
    private Map properties;
    private String sqlTemplate;
    private String searchEngineClass;

    public SearchMetaDataImpl() {
    }

    /**
     * Método invocado pelo configurador da framework imediatamente
     * depois de capturar a metadata dos ficheiros xml.
     * <br/>
     * Aqui temos a oportunidade de fazer o que é necessário antes
     * da metadata ser disponibilizada ao utilizador da API.
     */
    public void init() {
        /*
        this.rpmdNames = getParameterNames(this.rpmds);
        this.srpmdNames = getParameterNames(getSelectedResultParameters());
        this.srpmdoNames = getParameterNames(getSelectedResultParametersOrder());
         */

        this.spmds = Collections.unmodifiableList(this.spmds);
        this.rpmds = Collections.unmodifiableList(this.rpmds);
        this.urpmds = Collections.unmodifiableList(this.urpmds);
        this.rrpmds = Collections.unmodifiableList(this.rrpmds);
        this.srpmds = Collections.unmodifiableList(this.srpmds);
        this.srpmdos = Collections.unmodifiableList(this.srpmdos);
    }

    /**
     * Retorna os nomes dos <code>params</code>.
     * @param params lista de {@link ParameterMetaData}s.
     */
    private String[] getParameterNames(final List params) {
        return ParameterMetaDataUtils.getNames(params);
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(final String dataSource) {
        this.dataSource = dataSource;
    }

    public String getTableSource() {
        return this.tableSource;
    }

    public void setTableSource(final String tableSource) {
        this.tableSource = tableSource;
    }

    public Map getProperties() {
        return (this.properties == null)
                ? NO_PROPERTIES
                : this.properties;
    }

    public String getProperty(final String name) {
        return (this.properties == null)
                ? null
                : (String) this.properties.get(name);
    }

    public void addProperty(final String name, final String value) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }
        properties.put(name, value);
    }

    public String getSqlTemplate() {
        return this.sqlTemplate;
    }

    public void setSqlTemplate(final String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSearchEngineClass() {
        return this.searchEngineClass;
    }

    public void setSearchEngineClass(final String searchEngineClass) {
        this.searchEngineClass = searchEngineClass;
    }

    public String[] getResultParameterNames() {
        return getParameterNames(this.rpmds);
    }

    public String[] getSelectedResultParameterNames() {
        return getParameterNames(getSelectedResultParameters());
    }

    public String[] getSelectedResultParameterOrderNames() {
        return getParameterNames(getSelectedResultParametersOrder());
    }

    public ResultParameterMetaData getResultParameter(final String name) {
        return (ResultParameterMetaData) this.rpmdsMap.get(name);
    }

    public List getResultParameters(final String[] names) {
        if (names == null || names.length == 0) {
            return EMPTY_LIST;
        }
        List list = new ArrayList();
        for (int i = 0; i < names.length; i++) {
            ResultParameterMetaData rpmd = getResultParameter(names[i]);
            if (rpmd != null) {
                list.add(rpmd);
            }
        }
        return list;
    }

    public List getResultParameters() {
        return this.rpmds;
    }

    public List getUnselectableResultParameters() {
        return this.urpmds;
    }

    public List getRequiredResultParameters() {
        return this.rrpmds;
    }

    public List filterRequiredResultParameters(final String[] names) {
        if (names == null || names.length == 0) {
            return EMPTY_LIST;
        }

        Set set = new HashSet(Arrays.asList(names));
        List filtered = new ArrayList();
        Iterator it = this.rrpmds.iterator();
        while (it.hasNext()) {
            RequiredResultParameterMetaData r = (RequiredResultParameterMetaData) it.next();

            if (r.getDepends() != null && set.contains(r.getDepends())) {
                filtered.add(r);
            } else if (r.getUnless() != null && !set.contains(r.getUnless())) {
                filtered.add(r);
            } else if (r.getDepends() == null && r.getUnless() == null) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    public List filterRequiredResultParameters(final List rpmd) {
        String[] names = ParameterMetaDataUtils.getNames(rpmd);
        return filterRequiredResultParameters(names);
    }

    public List filterRequiredResultParametersExceptUnless(final String[] names) {
        if (names == null || names.length == 0) {
            return EMPTY_LIST;
        }

        Set set = new HashSet(Arrays.asList(names));
        List filtered = new ArrayList();
        Iterator it = this.rrpmds.iterator();
        while (it.hasNext()) {
            RequiredResultParameterMetaData r = (RequiredResultParameterMetaData) it.next();

            if (r.getDepends() == null) {
                filtered.add(r);
            } else if (set.contains(r.getDepends())) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    public List filterRequiredResultParametersExceptUnless(final List rpmd) {
        String[] names = ParameterMetaDataUtils.getNames(rpmd);
        return filterRequiredResultParametersExceptUnless(names);
    }

    public SearchParameterMetaData getSearchParameter(final String name) {
        return (SearchParameterMetaData) this.spmdsMap.get(name);
    }

    public List getSearchParameters(final String[] names) {
        if (names == null || names.length == 0) {
            return EMPTY_LIST;
        }
        List list = new ArrayList();
        for (int i = 0; i < names.length; i++) {
            SearchParameterMetaData spmd = getSearchParameter(names[i]);
            if (spmd != null) {
                list.add(spmd);
            }
        }
        return list;
    }

    public List getSearchParameters() {
        return this.spmds;
    }

    public List getSelectedResultParameters() {
        return (this.srpmds.isEmpty())
                ? this.rpmds
                : this.srpmds;
    }

    public List getSelectedResultParametersOrder() {
        return (this.srpmdos.isEmpty())
                ? EMPTY_LIST
                : this.srpmdos;
    }

    /**
     * Adiciona um novo search param à pesquisa.
     *
     * @param spmd instância de <code>SearchParameterMetaData</code>.
     */
    public void addSearchParameter(final SearchParameterMetaDataImpl spmd) {

        // com excepcao dos composite containers, os containers tem o 
        // mesmo nome que os search params
        if (this.spmdsMap.get(spmd.getName()) != null) {
            throw new IllegalArgumentException("O searchparam '"
                    + spmd.getName()
                    + "' foi definido mais do que uma vez no descriptor");
        }
        if (spmd.getContainer().isComposite()) {
            List children = spmd.getContainer().getChildren();
            for (Iterator it = children.iterator(); it.hasNext();) {
                ContainerMetaData container = (ContainerMetaData) it.next();
                if (this.containers.get(container.getName()) != null) {
                    throw new IllegalArgumentException("O container '"
                            + container.getName()
                            + "' foi definido mais do que uma vez no descriptor");
                }
            }
            for (Iterator it = children.iterator(); it.hasNext();) {
                ContainerMetaData container = (ContainerMetaData) it.next();
                this.containers.put(container.getName(), container);
            }
        }

        spmd.setSearchMetaData(this);
        this.spmds.add(spmd);
        this.spmdsMap.put(spmd.getName(), spmd);
    }

    public void addSearchParameters(final List spmds) {
        Iterator i = spmds.iterator();
        while (i.hasNext()) {
            addSearchParameter((SearchParameterMetaDataImpl) i.next());
        }
    }

    /**
     * Adiciona um novo result param  à pesquisa.
     *
     * @param rpmd instância de <code>ResultParameterMetaData</code>.
     */
    public void addResultParameter(final ResultParameterMetaData rpmd) {

        validateResultParameter(rpmd);

        if (rpmd.isSelectable()) {
            this.rpmds.add(rpmd);
            addResultParameterToIndex(rpmd);

        } else {
            this.urpmds.add(rpmd);
        }

        this.rpmdNames = getParameterNames(this.rpmds);
    }

    public void addResultParameterToIndex(final ResultParameterMetaData rpmd) {
        validateResultParameter(rpmd);

        if (rpmd.isComposite()) {
            Iterator children = rpmd.getChildren().iterator();
            while (children.hasNext()) {
                ResultParameterMetaData child =
                        (ResultParameterMetaData) children.next();
                addResultParameterToIndex(child);
            }
        }

        this.rpmdsMap.put(rpmd.getName(), rpmd);
    }

    private void validateResultParameter(final ResultParameterMetaData rpmd) {
        if (this.rpmdsMap.get(rpmd.getName()) != null) {
            throw new IllegalArgumentException("O resultparam '"
                    + rpmd.getName()
                    + "' foi definido mais do que uma vez no descriptor XML");
        }
    }

    /**
     * Adiciona uma lista de result params.
     */
    public void addResultParameters(final List rpmds) {
        Iterator i = rpmds.iterator();
        while (i.hasNext()) {
            addResultParameter((ResultParameterMetaData) i.next());
        }
    }

    /**
     * Adiciona o identificador de um result param que deve ficar
     * seleccionado por defeito.
     * Se o parâmetro não corresponder a nenhum result param
     * previamente adicionado, a operacão é ignorada.
     *
     * @param name nome do result param.
     * @see ResultParameterMetaData
     */
    public void addSelectedResultParameter(final String name) {
        ResultParameterMetaData rpmd = getResultParameter(name);
        if (rpmd == null) {
            return;
        }

        if (rpmd.isSelectable()) {
            this.srpmds.add(rpmd);
        } else {
            this.urpmds.add(rpmd);
        }

        this.srpmdNames = getParameterNames(getSelectedResultParameters());
    }

    /**
     * Adiciona um novo required result param.
     */
    public void addRequiredResultParameter(final RequiredResultParameterMetaData rrpmd) {
        this.rrpmds.add(rrpmd);
    }

    /**
     * Adiciona uma lista de required result params.
     */
    public void addRequiredResultParameters(final List rrpmds) {
        Iterator i = rrpmds.iterator();
        while (i.hasNext()) {
            addRequiredResultParameter((RequiredResultParameterMetaData) i.next());
        }
    }

    /**
     * Adiciona uma lista de required result params.
     */
    public void addSelectedResultParameters(final List srpmds) {
        Iterator i = srpmds.iterator();
        while (i.hasNext()) {
            addSelectedResultParameter((String) i.next());
        }
    }

    public void addSelectedResultParameterOrder(final SelectedResultParameterMetaDataOrder srpmdo) {
        this.srpmdos.add(srpmdo);
        this.srpmdoNames = getParameterNames(getSelectedResultParametersOrder());
    }

    public void setSqlFragmentParameter(final SqlFragmentParameterMetaData sqlFragment) {
        this.sqlFragment = sqlFragment;
    }

    public SqlFragmentParameterMetaData getSqlFragmentParameter() {
        return this.sqlFragment;
    }

    /**
     * Retorna o container identificado pelo dado nome.
     * Com excepcão dos composite containers, os containers tem o 
     * mesmo nome que os search params.
     */
    public ContainerMetaData findContainer(String name) {
        SearchParameterMetaData spmd = getSearchParameter(name);
        if (spmd == null) {
            return (ContainerMetaData) this.containers.get(name);
        }
        return spmd.getContainer();
    }

    public String toString() {
        return new StringBuffer("SearchMetaData[").append("name=").append(name).append(", label=").
          append(label).append(", description=").append(description).append(", dataSource=").
          append(dataSource).append(", tableSource=").append(tableSource).append(", searchEngineClass=").
          append(searchEngineClass).append(", spmds=").append(spmds).append(", rpmds=").append(rpmds).
          append(", rrpmds=").append(rrpmds).append(", srpmds=").append(srpmds).append(", srpmdos=").
          append(srpmdos).append("]").toString();
    }
}
