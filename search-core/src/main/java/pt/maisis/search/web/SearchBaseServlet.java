/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.web;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.*;
import pt.maisis.search.config.WebSearchMetaDataConfig;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.value.Value;

/**
 * Servlet base...
 *
 * @version 1.0
 */
public class SearchBaseServlet extends HttpServlet {

    private static Log log = LogFactory.getLog(SearchBaseServlet.class);
    /**
     * Parâmetro que identifica o nome da pesquisa.
     * <br/>
     * Sempre que é feita uma pesquisa, é necessário que o parâmetro
     * <code>search</code> seja disponibilizado com o nome da 
     * pesquisa desejada.
     */
    public static final String SEARCH = "search";
    /**
     * Quando algum erro em termos de regras de negócio ocorre. 
     * Por exemplo, quando o utilizador introduz um valor inválido 
     * num dado search param, então esse erro é colocado numa 
     * instância de {@link ValidationErrors}, sendo, posteriormente, 
     * colocada no contexto do pedido HTTP com o nome "errors". 
     *
     * Pode ser utilizada a tag XPTO para verificar se existem erros 
     * e apresentar os erros ao utilizador. No entanto, se desejado, 
     * os erros estão acessíveis na resposta HTTP através deste nome.
     */
    public static final String ERRORS = "errors";
    /**
     * Constante que deve ser utilizada como parâmetro nos pedidos
     * HTTP, quando se pretende ter controlo sobre a forma como
     * os dados da form são manipulados.
     * <br>
     * Esta questão é especialmente importante no caso da form
     * seja colocada em sessão.
     * <br>
     * Se o parâmetro <code>reset</code> existir no request e tiver
     * o valor false, o reset dos valores da form não é feito.
     */
    public static final String RESET = "reset";
    /**
     * Constante que deve ser enviada nos pedidos HTTP, sempre que 
     * se desejar que uma pesquisa seja precedida por um 'prepare'.
     * <br/>
     * Quando chega um pedido com este parâmetro com o valor 'true' 
     * é feito o populate da form com os valores por defeito definidos 
     * no descriptor XML da pesquisa. De seguida é feito o populate da
     * form com os valores enviados no request e, finalmente, é feita
     * a pesquisa.
     */
    public static final String COUNT = "count";

    public static final String PREPARE = "prepare";
    public static final String LOCALE = "locale";
    private boolean searchShareReset = true;

    /** Captura a metadata das pesquisas web. */
    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected SearchForm createForm(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        return createForm(request, response, null);
    }

    /**
     * Método responssável por criar uma instância da
     * {@link SearchForm}.
     *
     * <p>Se o parâmetro <code>search</code> não existir no pedido, 
     * cria uma resposta de erro e retorna <code>null</code>.</p>
     *
     * <p>Se a ocorrer algum problema ao criar uma instância de
     * {@link SearchForm} o método cria uma mensagem de erro e
     * e retorna <code>null</code>.</p>
     */
    protected SearchForm createForm(HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> parameterMap)
            throws IOException, ServletException {

        // verificar se o param 'search' foi especificado no request
        String search = (String)request.getParameter(SEARCH);

        if (parameterMap != null && !parameterMap.isEmpty()) {
            search = (String)parameterMap.get("search");
        } 
        
        if (search == null) {
            String message = MessageResources.getInstance().getMessage("servlet.searchParamRequired");
            log.error(message);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
            return null;
        }

        // Verificar se existe alguma form no contexto da sessao.
        // Pensar melhor nesta questao. O facto de se permitir 
        // guardar as forms de pesquisa na sessao acarreta alguns 
        // problemas.
        // Por exemplo, se se abrir um novo browser e se fizer uma 
        // nova pesquisa (do mesmo tipo), a nova form irá substituir 
        // a form anterior em sessão.
        //
        // Uma forma de resolver o problema é o nome do atributo 
        // onde se guarda a pesquisa ser gerado dinamicamente e
        // colocado no request (inconveniente: muitas pesquisas,
        // muito lixo em sessão).
        //
        // Outra forma, mais radical, de resolver o problema é
        // não permitir colocar a form em sessão. (inconveniente:
        // um pouco mais de trabalho em situacões em que se deseje
        // repetir a pesquisa alterando so alguns params, por exemplo,
        // numa paginacão).

        WebSearch webSearch = getWebSearch(search);

        if (webSearch == null) {
            String message = MessageResources.getInstance().getMessage("servlet.invalidSearch");
            log.error(message);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            return null;
        }

        if (log.isTraceEnabled()) {
            log.trace("[web search]: " + webSearch);
        }

        if (WebSearch.SESSION_SCOPE.equals(webSearch.getSearchFormScope())) {
            HttpSession session = request.getSession();
            SearchForm form = (SearchForm) session.getAttribute(webSearch.getSearchFormName());

            if (form != null) {
                if (isPreparableSearch(request)) {
                    if (log.isDebugEnabled()) {
                        log.debug("[prepare search]");
                    }

                    form.reset();
                    form.setSearch(search);
                    prepareSearch(request, form);

                } else if (isResetableSearch(request)) {
                    if (log.isDebugEnabled()) {
                        log.debug("[reset search]");
                    }

                    form.reset();
                    form.setSearch(search);
                }

                form.setRequest(request);
                form.setResponse(response);
                return form;
            }
        }

        // criar instancia da search form
        try {
            Class clazz = Class.forName(webSearch.getSearchFormClass());
            SearchForm form = (SearchForm) clazz.newInstance();
            form.setRequest(request);
            form.setResponse(response);
            form.setSearch(search);

            if (isPreparableSearch(request)) {
                if (log.isDebugEnabled()) {
                    log.debug("[prepare search]");
                }
                prepareSearch(request, form);
            }

            if (WebSearch.SESSION_SCOPE.equals(webSearch.getSearchFormScope())) {
                HttpSession session = request.getSession();
                session.setAttribute(webSearch.getSearchFormName(), form);
            } else {
                request.setAttribute(webSearch.getSearchFormName(), form);
            }

            return form;

        } catch (Exception e) {
            String message = MessageResources.getInstance().getMessage("servlet.invalidSearchForm");
            log.error(message);
            log.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            return null;
        }
    }

    /**
     * Faz o forward do request para outro recurso.
     *
     * <p>Caso o dado <code>uri</code> for inválido, o método cria 
     * uma mensagem de erro e retorna <code>null</code>.</p>
     */
    protected void forward(HttpServletRequest request,
            HttpServletResponse response,
            String uri)
            throws IOException, ServletException {

        RequestDispatcher rd = request.getRequestDispatcher(uri);
        if (rd == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    MessageResources.getInstance().getMessage("servlet.invalid.uri", uri));
            return;
        }

        if ("false".equals(request.getParameter("forward"))) {
            rd.include(request, response);
        } else {
            rd.forward(request, response);
        }
    }

    /**
     * Faz o redirect do request para outro recurso.
     *
     * <p>Caso o dado <code>uri</code> for inválido, o método cria
     * uma mensagem de erro e retorna <code>null</code>.</p>
     */
    protected void redirect(HttpServletResponse response,
            String uri)
            throws IOException, ServletException {

        String urlWithSessionID = response.encodeRedirectURL(uri);

        response.sendRedirect(urlWithSessionID);
    }

    /**
     * Retorna a web search ({@link WebSearch}) identificada
     * pelo dado nome.
     */
    protected WebSearch getWebSearch(String name) {
        WebSearchMetaDataConfig config = WebSearchMetaDataConfig.getInstance();
        WebSearchMetaData wsmd = config.getWebSearchMetaData();
        return wsmd.getSearch(name);
    }

    /**
     * Guarda no contexto do pedido HTTP os erros ocorridos.
     */
    protected void saveErrors(HttpServletRequest request,
            ValidationErrors errors) {
        request.setAttribute(ERRORS, errors);
    }

    /**
     * Retorna true se o param 'prepare' foi enviado no request
     * com o valor 'true'.
     */
    protected boolean isPreparableSearch(HttpServletRequest request) {
        String prepare = request.getParameter(PREPARE);
        return prepare != null && prepare.equals("true");
    }

    /**
     * Retorna true se o param 'reset' não foi enviado no request, ou
     * se foi enviado com o valor 'true'.
     */
    protected boolean isResetableSearch(HttpServletRequest request) {
        String reset = request.getParameter(RESET);
        return reset == null || reset.equals("true");
    }

    private void prepareSearch(HttpServletRequest request, SearchForm form) {
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());

        if (smd != null) {
            Map context = new HashMap();
            context.put("request", request);
            context.put("session", request.getSession());

            setSearchParametersMetaData(smd, form, context);
            setResultParametersMetaData(smd, form);
        }
    }

    /**
     * Faz um populate da {@link SearchForm} com os valores por
     * defeitos, definidos no descriptor pesquisa.
     */
    private void setSearchParametersMetaData(final SearchMetaData smd,
            final SearchForm form,
            final Map context) {
        Iterator it = smd.getSearchParameters().iterator();
        while (it.hasNext()) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            setSearchParametersMetaData(spmd.getContainer(), form, context);
        }
    }

    private void setSearchParametersMetaData(final ContainerMetaData cmd,
            final SearchForm form,
            final Map context) {
        if (cmd.isComposite()) {
            Iterator it = cmd.getChildren().iterator();
            while (it.hasNext()) {
                setSearchParametersMetaData((ContainerMetaData) it.next(), form, context);
            }
        } else {
            Value defaultValue = cmd.getDefaultValue();
            
            if (defaultValue != null) {
                // É necessário fazer o set do valor por defeito nas radio boxes 
                // checked, caso contrário a última radio fica seleccionada.
                if (cmd.getType() != null) {
                    if (cmd.getType().equals(ContainerMetaData.TYPE_RADIO)
                            && !cmd.isChecked()) {
                        return;
                    }
                }
                
                form.setSearchParam(cmd.getName(), defaultValue.getValue(context));
            }
        }
    }

    private void setResultParametersMetaData(final SearchMetaData smd,
            final SearchForm form) {

        // selected result param names
        String[] srpmdNames = smd.getSelectedResultParameterNames();
        form.setSelectedResultParams(srpmdNames);

        // selected result params order
        //
        // para já, em termos de interface o tipo de ordenacão
        // considerado é global, isto é, não é possível definir
        // o tipo de ordenacão (ASC ou DESC) individualmente por
        // campo. 
        List srpmdos = smd.getSelectedResultParametersOrder();
        if (!srpmdos.isEmpty()) {
            SelectedResultParameterMetaDataOrder srpmdo = (SelectedResultParameterMetaDataOrder) srpmdos.get(0);
            form.setOrderType(srpmdo.getOrderType());
            form.setResultOrder(smd.getSelectedResultParameterOrderNames());
        }

        // result params
        String[] resultNames = smd.getResultParameterNames();
        
        form.setResultParams(subtract(resultNames, srpmdNames));
    }

    private String[] subtract(final String[] a, final String[] b) {
        Set result = new LinkedHashSet(Arrays.asList(a));
        Iterator it = Arrays.asList(b).iterator();
        while (it.hasNext()) {
            result.remove(it.next());
        }
        return (String[]) result.toArray(new String[result.size()]);
    }
}
