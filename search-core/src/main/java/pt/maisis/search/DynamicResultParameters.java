package pt.maisis.search;

import java.util.List;

/**
 * A implementacão deste interface é útil em situacões em que 
 * a metadata relativa aos campos de retorno é variável.
 *
 * @version 1.0
 */
public interface DynamicResultParameters {

    List getResultParameters();
}
