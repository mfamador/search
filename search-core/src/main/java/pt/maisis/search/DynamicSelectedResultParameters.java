package pt.maisis.search;

import java.util.List;

/**
 * Interface cuja implementacão permite retornar toda a 
 * <i>metadata</i> relativa aos <i>selected result params</i>
 * para uma dada pesquisa.<br/>
 * 
 * Útil em situacões em que a <i>metadata</i> só é conhecida em 
 * <i>runtime</i>.
 *
 * <p>
 * Exemplo:
 * <pre>
 *   &lt;dynamic-selected-result-params class="..."/&gt;
 * </pre>
 * </p>
 *
 *
 * @version 1.0
 *
 * @see pt.maisis.search.DynamicResultParameters
 * @see pt.maisis.search.DynamicSearchParameters
 */
public interface DynamicSelectedResultParameters {

    /**
     * @return uma lista de instâncias de
     * {@link String}.
     */
    List getSelectedResultParameters();
}
