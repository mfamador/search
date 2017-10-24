package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.util.ApplicationResources;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import pt.maisis.search.SearchParameterMetaData;

/**
 * Abstract Converter que facilita a criacão de novos converters.
 *
 * @version 1.0
 */
public abstract class AbstractConverter implements Converter {

    protected Map properties = new HashMap();

    public void addProperty(String name, String value) {
        this.properties.put(name, value);
    }

    /**
     * Retorna um Map com as propriedades que foram especificadas
     * no descriptor da pesquisa para este converter.
     */
    protected Map getProperties() {
        return this.properties;
    }

    /**
     * Retorna o valor da propriedade identificada pelo dado
     * <code>name</code>.
     */
    protected String getProperty(String name) {
        return (String) properties.get(name);
    }

    /**
     * Retorna o label do container traduzido em funcão do dado 
     * locale.<br/>
     *
     * Se o label do container não for encontrado no resources bundle
     * especificado no ficheiro <code>search-config.xml</code> então
     * o label do container é retornado exactamente como está 
     * definido no descriptor da pesquisa.
     */
    public String getLabel(ContainerMetaData container,
            Locale locale) {
        ApplicationResources messages = ApplicationResources.getInstance(locale);
        return messages.getMessage(container.getLabel());
    }

    /**
     * Constroi uma chave tendo como base o valor do parâmetro
     * <code>key</code>.
     *
     * <p>Todas as mensagens de erro criadas pelos converters
     * devem utilizar este método de forma a que uma mensagem de erro 
     * possa ser especificada a nível global (para todas as pesquisas),
     * para uma dada pesquisa ou para um search param específico.</p>
     *
     * <p>Por exemplo, se a pesquisa em causa se chamar <b>search</b>, 
     * o search param tiver o nome <b>param</b>, e finalmente, o 
     * parâmetro <code>key</code> consistir na string 
     * <b>validation.error.invalid</b> então:</p>
     * <ol>
     *   <li>
     *     Em primeiro lugar é criada uma nova chave com o nome da 
     *     pesquisa e do search param adicionados como prefixo:
     *     <b>search.param.validation.error.invalid</b>.<br/>
     *     Antes de retornar esta nova chave, verifica se existe
     *     traducão no resources bundle para o dado locale.
     *     Se não existir executa o passo seguinte.
     *   </li>
     *   <li>
     *     O prefixo passa a ser só o nome do search param:
     *     <b>param.validation.error.invalid</b>.<br/>
     *     Mais uma vez, antes de devolver esta chave, verifica se 
     *     existe traducão. Se não existir executa o passo seguinte.
     *   </li>
     *   <li>
     *     Finalmente, devolve o valor do parâmetro <code>key</code> 
     *     inalterado. Neste caso o valor seria: 
     *     <b>validation.error.invalid</b>.
     *   </li>
     * </ol>
     */
    public String getKey(ContainerMetaData container,
            Locale locale,
            String key) {

        ApplicationResources messages =
                ApplicationResources.getInstance(locale);
        SearchParameterMetaData spmd = container.getSearchParameterMetaData();
        String search = spmd.getSearchMetaData().getName();
        String name = container.getName();
        return messages.getKey(search, name, key);
    }
}
