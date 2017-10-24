package pt.maisis.search.web.taglib;

import pt.maisis.search.SearchMetaData;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.util.MessageResources;

import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;

/**
 * TODO: Actualizar a tag para a versão mais recente!
 *
 * @version 1.0
 */
public class SearchMetaDataTag extends SearchBaseTag {

    private static final Iterator EMPTY_ITERATOR = new ArrayList().iterator();
    private int numberColumns;
    private int startIndex;
    private int endIndex;
    private boolean allHidden;
    private Iterator spmds;
    private SearchParameterMetaData searchParam;
    private ContainerMetaData container;
    private ContainerMetaData compositeContainer;
    private Iterator containers;
    private int i;
    private boolean startRow;
    private boolean endRow;
    private boolean startColumn;
    private boolean endColumn;
    private boolean startComposite;
    private boolean endComposite;
    private SearchForm searchForm;

    /**
     * Atribui o número de critérios de pesquisa a apresentar
     * por linha.
     */
    public void setNumberColumns(final int numberColumns) {
        this.numberColumns = numberColumns;
    }

    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(final int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public int doStartTag() throws JspException {

        if (!isSearchAvailable()) {
            throw new JspException(MessageResources.getInstance().getMessage("servlet.searchParamRequired"));
        }

        SearchMetaData smd = getSearchMetaData();

        if (smd != null) {
            this.i = 0;
            this.containers = EMPTY_ITERATOR;

            if (this.startIndex == 0 && this.endIndex == 0) {
                this.spmds = smd.getSearchParameters().iterator();

            } else {
                int size = smd.getSearchParameters().size();

                if (this.startIndex < this.endIndex
                        && this.startIndex >= 0
                        && this.endIndex <= size) {

                    this.spmds = smd.getSearchParameters().subList(this.startIndex, this.endIndex).iterator();
                } else {
                    // TODO: lancar excepcao!!!!!
                    return SKIP_BODY;
                }
            }

            if (this.spmds.hasNext()) {
                setContainer();
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (this.containers.hasNext() || this.spmds.hasNext()) {
            setContainer();
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    ContainerMetaData getContainer() {
        return this.container;
    }

    SearchParameterMetaData getSearchParameter() {
        return this.searchParam;
    }

    boolean isComposite() {
        return ( this.compositeContainer != null );
    }

    ContainerMetaData getCompositeContainer() {
        return this.compositeContainer;
    }

    boolean handleStartComposite() {
        return this.startComposite;
    }

    boolean handleEndComposite() {
        return this.endComposite;
    }

    boolean handleStartRow() {
        return this.startRow;
    }

    boolean handleEndRow() {
        return this.endRow;
    }

    boolean handleStartColumn() {
        return this.startColumn;
    }

    boolean handleEndColumn() {
        return this.endColumn;
    }

    private void setContainer() {
        prepareIteration();

        // se o container actual tiver mais containers agregados
        // considerar o próximo, caso contrário considerar o
        // container do próximo searchparam
        if (this.containers.hasNext()) {
            setContainer((ContainerMetaData) this.containers.next());

            if (!containers.hasNext()) {
                this.endComposite = true;
            }
        } else if (this.spmds.hasNext()) {

            this.compositeContainer = null;

            SearchParameterMetaData spmd = (SearchParameterMetaData) spmds.next();

            ContainerMetaData cmd = spmd.getContainer();

            // se o container é do tipo hidden, ou, no caso de ser
            // um container composto, todos os containers agregados
            // são hidden
            this.allHidden = areAllHidden(cmd);

            if (this.allHidden) {
                this.i--;
            }

            this.startRow = this.i % this.numberColumns == 0;
            this.startColumn = !this.allHidden;

            setSearchParameter(spmd);
            setContainer(spmd.getContainer());
        }

        if (!this.containers.hasNext()) {
            this.i++;
            this.endColumn = !this.allHidden;
            this.endRow = this.i % this.numberColumns == 0;
        }
    }

    private void setSearchParameter(final SearchParameterMetaData searchParam) {
        this.searchParam = searchParam;
    }

    private void setContainer(final ContainerMetaData container) {
        if (container.isComposite()) {
            this.compositeContainer = container;
            this.containers = container.getChildren().iterator();
            this.container = (ContainerMetaData) this.containers.next();
            this.startComposite = true;
        } else {
            this.container = container;
        }
    }

    private boolean areAllHidden(final ContainerMetaData container) {
        if (container.isComposite()) {
            Iterator it = container.getChildren().iterator();
            while (it.hasNext()) {
                if (!areAllHidden((ContainerMetaData) it.next())) {
                    return false;
                }
            }
            return true;
        }
        return ( ContainerMetaData.TYPE_HIDDEN.equals(container.getType()) )
                ? true : false;
    }

    private void prepareIteration() {
        this.startRow = false;
        this.endRow = false;
        this.startColumn = false;
        this.endColumn = false;
        this.startComposite = false;
        this.endComposite = false;
    }
}
