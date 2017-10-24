package pt.maisis.search;

import java.util.List;

/**
 * A implementacão deste interface é útil em situacões em que
 * a metadata relativa aos critérios de pesquisa é variável.
 *
 * @version 1.0
 */
public interface DynamicSearchParameters {

    List getSearchParameters();
}
