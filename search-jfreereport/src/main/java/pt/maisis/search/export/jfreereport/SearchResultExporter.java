package pt.maisis.search.export.jfreereport;

import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.TableModel;

import org.apache.velocity.VelocityContext;
import org.jfree.report.DefaultResourceBundleFactory;
import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.modules.output.pageable.base.PageableReportProcessor;
import org.jfree.report.modules.output.pageable.pdf.PdfOutputProcessor;
import org.jfree.report.modules.output.table.base.StreamReportProcessor;
import org.jfree.report.modules.output.table.csv.StreamCSVOutputProcessor;
import org.jfree.report.modules.output.table.html.HtmlReportUtil;
import org.jfree.report.modules.output.table.rtf.RTFReportUtil;
import org.jfree.report.modules.output.table.xls.ExcelReportUtil;
import org.jfree.report.modules.output.table.xls.PageableExcelOutputProcessor;
import org.jfree.report.modules.parser.base.ReportGenerator;
import org.xml.sax.InputSource;
import pt.maisis.search.Result;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.export.SearchResultExportException;
import pt.maisis.search.template.VelocityTemplate;
import pt.maisis.search.util.ApplicationResources;

/**
 * Classe responsavel por exportar resultados das pesquisas.
 * Esta classe utiliza a framework JFreeReport para o efeito de
 * exportacao dos resultados das pesquisas.
 *
 * @version 1.0
 */
public class SearchResultExporter
        extends pt.maisis.search.export.SearchResultExporter {

    /** Displayable search params. */
    public static final String SEARCH_PARAMS = "searchParams";
    /** Metadata da pesquisa. */
    public static final String SEARCH_METADATA = "searchMetaData";
    /** Resultado da pesquisa. */
    public static final String RESULT = "result";
    /** Metadata do resultado da pesquisa. */
    public static final String RESULTS_METADATA = "resultMetaData";
    /** Metadata expandida do resultado da pesquisa. */
    public static final String EXPANDED_RESULTS_METADATA = "expandedResultMetaData";
    public static final String PAGE_TOOL = "page";
    /** Formato dos resultados. */
    public static final String FORMAT = "format";
    /** Locale. */
    public static final String LOCALE = "locale";
    /** Messages translator util. */
    public static final String MESSAGES = "messages";
    private String template;
    private boolean useVelocityTemplate;

    static {
        // JFreeReport Booting
        JFreeReportBoot.getInstance().start();
    }

    public SearchResultExporter() {
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public void useVelocityTemplate(final boolean useVelocityTemplate) {
        this.useVelocityTemplate = useVelocityTemplate;
    }

    /**
     * Permite fazer a exportacao do resultado de uma dada pesquisa
     * para o formato desejado ({@link #HTML}, {@link #HTML_FRAGMENT},
     * {@link #XLS}, {@link #PDF}, {@link #PNG}, {@link #RTF})
     * ou {@link #CSV}.
     *
     * @param result resultado de uma dada pesquisa.
     * @param out    onde deve ser escrito o relatorio.
     * @param format formato do relatorio.
     */
    public void export(final Map searchParams,
            final Result result,
            final OutputStream out,
            final String format,
            final Locale locale)
            throws SearchResultExportException {

        TableModel data = new SearchResultTableModel(result, JFreeReportConfig.getInstance().isFormatValues(), locale);

        JFreeReport report;
        if (this.useVelocityTemplate) {
            VelocityContext context = createContext(searchParams, result, format, locale);
            VelocityTemplate velocity = VelocityTemplate.getInstance();
            String parsedReport = velocity.merge(context, this.template);
            report = parseReport(parsedReport);
        } else {
            report = parseReport();
        }

        report.setResourceBundleFactory(new DefaultResourceBundleFactory(locale));

        report.setData(data);
        export(report, out, format);

    }

    private void export(final JFreeReport report,
            final OutputStream out,
            final String format)
            throws SearchResultExportException {

        try {
            if (HTML.equals(format)) {
                exportHTML(report, out);
                return;
            }
            if (HTML_FRAGMENT.equals(format)) {
                exportHTMLFragment(report, out);
                return;
            }
            if (XLS.equals(format)) {
                exportXLS(report, out);
                return;
            }
            if (PXLS.equals(format)) {
                exportPXLS(report, out);
                return;
            }
            if (PDF.equals(format)) {
                exportPDF(report, out);
                return;
            }
            if (PNG.equals(format)) {
                exportPNG(report, out);
                return;
            }
            if (RTF.equals(format)) {
                exportRTF(report, out);
                return;
            }
            if (CSV.equals(format)) {
                exportCSV(report, out);
                return;
            }
            if (TXT.equals(format)) {
                exportTXT(report, out);
                return;
            }

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }

        throw new SearchResultExportException("Param 'format' not supported: " + format);
    }

    /**
     * Exporta os resultados para o formato XLS.
     */
    public void exportXLS(final JFreeReport report,
            final OutputStream out) throws Exception {

        ExcelReportUtil.createXLS(report, out);
    }

    /**
     * Exporta os resultados para o formato XLS.
     */
    public void exportPXLS(final JFreeReport report,
            final OutputStream out) throws Exception {

        PageableExcelOutputProcessor outputProcessor = new PageableExcelOutputProcessor(report.getConfiguration(), out);
        PageableReportProcessor proc = new PageableReportProcessor(report, outputProcessor);
        proc.processReport();
    }

    /**
     * Exporta os resultados para o formato CSV.
     */
    public static void exportCSV(final JFreeReport report,
            final OutputStream out) throws Exception {

        StreamCSVOutputProcessor target =
                new StreamCSVOutputProcessor(report.getConfiguration(), out);
        StreamReportProcessor reportProcessor =
                new StreamReportProcessor(report, target);

        reportProcessor.processReport();
    }

    /**
     * Exporta os resultados para o formato CSV.
     */
    public static void exportTXT(final JFreeReport report,
            final OutputStream out) throws Exception {

        StreamCSVOutputProcessor target =
                new StreamCSVOutputProcessor(report.getConfiguration(), out);
        StreamReportProcessor reportProcessor =
                new StreamReportProcessor(report, target);

        reportProcessor.processReport();
    }

    /**
     * Exporta os resultados para o formato PDF.
     */
    public void exportPDF(final JFreeReport report,
            final OutputStream out) throws Exception {

        PdfOutputProcessor outputProcessor = new PdfOutputProcessor(report.getConfiguration(), out);
        PageableReportProcessor proc = new PageableReportProcessor(report, outputProcessor);
        proc.processReport();
        proc.close();
    }

    /**
     * Exporta os resultados para o formato HTML.
     */
    public void exportHTML(final JFreeReport report,
            final OutputStream out) throws Exception {

        HtmlReportUtil.createStreamHTML(report, out);
    }

    /**
     * Exporta os resultados para o formato HTML Fragment.
     */
    public void exportHTMLFragment(final JFreeReport report,
            final OutputStream out) throws Exception {
        exportHTML(report, out);
    }

    /**
     * Exporta os resultados para o formato RTF.
     */
    public void exportRTF(final JFreeReport report,
            final OutputStream out) throws Exception {

        RTFReportUtil.createRTF(report, out);
    }

    /**
     * Exporta os resultados para o formato PNG.
     */
    public void exportPNG(final JFreeReport report,
            final OutputStream out) throws Exception {
        //             BufferedImage image = createImage(report);
        //             Graphics2D g2 = image.createGraphics();
        //             g2.setPaint(Color.white);
        //             g2.fillRect(0,0, image.getWidth(), image.getHeight());
        //             G2OutputTarget target = new G2OutputTarget(g2);
        //             PageableReportProcessor processor
        //                 = new PageableReportProcessor(report);
        //             processor.setOutputTarget(target);
        //             target.open();
        //             processor.processReport();
        //             target.close();
        //             g2.dispose();
        //             PngEncoder encoder = new PngEncoder(image, true, 0, 9);
        //             byte[] data = encoder.pngEncode();
        //             out.write(data);
        //             out.flush();
    }

    /**
     * Create the empty image for the given page size.
     *
     * @param pd the page definition that defines the image bounds.
     * @return the generated image.
     */
    //     private BufferedImage createImage(final JFreeReport report) {
    //         PageDefinition pd = report.getPageDefinition();
    //         double width = pd.getWidth();
    //         double height = pd.getHeight();
    //         final BufferedImage image = new BufferedImage
    //             ((int) width, (int) height, BufferedImage.TYPE_BYTE_INDEXED);
    //         return image;
    //     }
    /**
     * Retorna uma instância de <code>VelocityContext</code> com
     * os valores relativos a: {@link #SEARCH_METADATA},
     * {@link #RESULT}, {@link #RESULT_METADATA} e
     * {@link #EXPANDED_RESULTS_METADATA}.
     */
    private VelocityContext createContext(final Map searchParams,
            final Result result,
            final String format,
            final Locale locale) {
        VelocityContext context = new VelocityContext();
        context.put(RESULT, result);
        context.put(SEARCH_PARAMS, searchParams);
        context.put(SEARCH_METADATA, result.getSearchMetaData());
        context.put(RESULTS_METADATA, result.getResultMetaData());
        context.put(EXPANDED_RESULTS_METADATA,
                wrap(result.getExpandedResultMetaData()));
        context.put(FORMAT, format);
        context.put(LOCALE, locale);
        context.put(PAGE_TOOL,
                new PageTool(result.getExpandedResultMetaData()));
        context.put(MESSAGES, ApplicationResources.getInstance(locale));

        return context;
    }

    private List wrap(List rpmd) {
        List list = new ArrayList(rpmd.size());
        Iterator it = rpmd.iterator();
        while (it.hasNext()) {
            list.add(new ResultParameterMetaDataAdapter((ResultParameterMetaData) it.next()));
        }
        return list;
    }

    private JFreeReport parseReport(String in)
            throws SearchResultExportException {

        try {
            InputSource input = new InputSource(new StringReader(in));
            ReportGenerator generator = ReportGenerator.getInstance();
            final URL url = this.getClass().getResource("/");
            return generator.parseReport(input, url);
        } catch (Exception e) {
            throw new SearchResultExportException("Parsing failed: " + e.getMessage());
        }
    }

    private JFreeReport parseReport()
            throws SearchResultExportException {

        URL in = this.getClass().getResource(this.template);
        if (in == null) {
            throw new SearchResultExportException("Invalid URL");
        }

        try {
            ReportGenerator generator = ReportGenerator.getInstance();
            return generator.parseReport(in);

        } catch (Exception e) {
            throw new SearchResultExportException("Parsing failed" + e.getMessage());
        }
    }
}
