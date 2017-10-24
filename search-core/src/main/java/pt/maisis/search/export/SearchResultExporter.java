package pt.maisis.search.export;

import pt.maisis.search.Result;

import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

/**
 * 
 *
 * @version 1.0
 */
public abstract class SearchResultExporter {

    private static final Map NO_HEADERS = new HashMap(0);
    /** HTML export. */
    public static final String HTML = "html";
    /** HTML fragment export. */
    public static final String HTML_FRAGMENT = "htmlfragment";
    /** XLS export. */
    public static final String XLS = "xls";
    /** Paginated XLS export. */
    public static final String PXLS = "pxls";
    /** PDF export. */
    public static final String PDF = "pdf";
    /** PNG export. */
    public static final String PNG = "png";
    /** RTF export. */
    public static final String RTF = "rtf";
    /** CSV export. */
    public static final String CSV = "csv";
    /** CSV export. */
    public static final String TXT = "txt";

    /**
     * Método invocado pela framework de pesquisa, imediatamente
     * depois de criar a instÃ¢ncia desta classe.<br/>
     *
     * @param template nome completo do ficheiro
     */
    public abstract void setTemplate(String template)
            throws SearchResultExportException;

    /**
     * Identifica se a template deve ser pre-parsed pela framework
     * Velocity.<br/>
     * Este método e invocado pela framework de pesquisa, imediatamente
     * depois de invocar o metodo {@link setTemplate(String)}.
     *
     * TODO: tornar este método mais genérico de forma a ser possÃ­vel
     * utilizar outro tipo de template engine.
     */
    public abstract void useVelocityTemplate(boolean velocityTemplate)
            throws SearchResultExportException;

    /**
     * Método invocado imediatamente antes do método {@link export()}.
     * Este método é útil em ambiente web, e tem como objectivo permitir
     * especificar quais os headers desejados para a o tipo de 
     * exportacão especificada no parÃ¢mtro <code>format</code>.
     *
     * @param result resultado da pesquisa.
     * @param format formato do relatório.
     * @return <code>Map</code> com os headers desejados.
     */
    public Map getHeaders(Result result, String format, String filenamePrefix) {
        Map headers = new HashMap();
        if (HTML.equalsIgnoreCase(format) || HTML_FRAGMENT.equalsIgnoreCase(format)) {
            headers.put("Content-Type", "text/html");
            return headers;
        }
        if (XLS.equalsIgnoreCase(format) || PXLS.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".xls\"");
            headers.put("Content-Type", "application/vnd.ms-excel");
            return headers;
        }
        if (PDF.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".pdf\"");
            headers.put("Content-Type", "application/pdf");
            return headers;
        }
        if (PNG.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".png\"");
            headers.put("Content-Type", "image/png");
            return headers;
        }
        if (RTF.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".rtf\"");
            headers.put("Content-Type", "text/rtf");
            return headers;
        }
        if (CSV.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".csv\"");
            headers.put("Content-Type", "application/vnd.ms-excel");
            return headers;
        }
        if (TXT.equalsIgnoreCase(format)) {
            headers.put("Content-Disposition", "attachment; filename=\"" + filenamePrefix + ".txt\"");
            headers.put("Content-Type", "text/plain");
            return headers;
        }
        return NO_HEADERS;
    }

    public Map getHeaders(Result result, String format) {
        return getHeaders(result, format, "report");
    }

    /**
     * Permite fazer a exportacão do relatório para o formato
     * desejado ({@link #HTML}, {@link #HTML_FRAGMENT}, 
     * {@link #XLS}, {@link #PDF}, {@link #PNG}, {@link #RTF})
     * ou {@link #CSV}.
     *
     * @param result resultado de uma dada pesquisa.
     * @param out    onde deve ser escrito o relatório.
     * @param format formato do relatório.
     */
    public void export(Result result,
            OutputStream out,
            String format,
            Locale locale)
            throws SearchResultExportException {

        export(null, result, out, format, locale);
    }

    public abstract void export(Map searchParams,
            Result result,
            OutputStream out,
            String format,
            Locale locale)
            throws SearchResultExportException;
}
