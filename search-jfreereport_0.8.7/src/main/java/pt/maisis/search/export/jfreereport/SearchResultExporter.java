package pt.maisis.search.export.jfreereport;

import pt.maisis.search.Result;
import pt.maisis.search.SearchResult;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.export.SearchResultExportException;
import pt.maisis.search.template.VelocityTemplate;
import pt.maisis.search.util.ApplicationResources;

import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.PageDefinition;
import org.jfree.report.modules.output.table.xls.ExcelProcessor;
import org.jfree.report.modules.output.table.html.HtmlProcessor;
import org.jfree.report.modules.output.table.html.StreamHtmlFilesystem;
import org.jfree.report.modules.output.table.csv.CSVTableProcessor;
import org.jfree.report.modules.output.table.rtf.RTFProcessor;
import org.jfree.report.modules.output.pageable.base.PageableReportProcessor;
import org.jfree.report.modules.output.pageable.pdf.PDFOutputTarget;
import org.jfree.report.modules.output.pageable.graphics.G2OutputTarget;
import org.jfree.report.modules.parser.base.ReportGenerator;
import org.jfree.report.DefaultResourceBundleFactory;

import org.apache.velocity.VelocityContext;

import org.xml.sax.InputSource;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.keypoint.PngEncoder;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.PrintWriter;
import java.util.Map;
import javax.swing.table.TableModel;
import java.net.URL;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Classe responsável por exportar resultados das pesquisas.
 * Esta classe utiliza a framework JFreeReport para o efeito de
 * exportacão dos resultados das pesquisas.
 *
 * @version 1.0
 */
public class SearchResultExporter
    extends pt.maisis.search.export.SearchResultExporter {

    /** Displayable search params. */
    public static final String SEARCH_PARAMS
        = "searchParams";

    /** Metadata da pesquisa. */
    public static final String SEARCH_METADATA
        = "searchMetaData";

    /** Resultado da pesquisa. */
    public static final String RESULT
        = "result";

    /** Metadata do resultado da pesquisa. */
    public static final String RESULTS_METADATA
        = "resultMetaData";

    /** Metadata expandida do resultado da pesquisa. */
    public static final String EXPANDED_RESULTS_METADATA
        = "expandedResultMetaData";

    public static final String PAGE_TOOL
        = "page";

    /** Formato dos resultados. */
    public static final String FORMAT 
        = "format";

    /** Locale. */
    public static final String LOCALE
        = "locale";

    /** Messages translator util. */
    public static final String MESSAGES 
        = "messages";

    private String template;
    private boolean useVelocityTemplate;

    static {
        // JFreeReport Booting
        //
        // Important: Do not forget to boot the library before you
        // use any of JFreeReport's classes. Booting should be done
        // at least before you first try to use JFreeReport's classes.
        // Booting initializes the library and all subsystems
        //
        // Without booting JFreeReport's components will not be
        // initialized properly and all kinds of funny things can and
        // will happen.
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
     * Permite fazer a exportacão do relatório para o formato
     * desejado ({@link #HTML}, {@link #HTML_FRAGMENT},
     * {@link #XLS}, {@link #PDF}, {@link #PNG}, {@link #RTF})
     * ou {@link #CSV}.
     *
     * @param result resultado de uma dada pesquisa.
     * @param out    onde deve ser escrito o relatório.
     * @param format formato do relatório.
     */
    public void export(final Map searchParams,
                       final Result result,
                       final OutputStream out,
                       final String format,
		       final Locale locale)
        throws SearchResultExportException {

        TableModel data = new SearchResultTableModel
            (result, JFreeReportConfig.getInstance().isFormatValues(), locale);

        JFreeReport report;
        if (this.useVelocityTemplate) {
            VelocityContext context = createContext(searchParams, result, format, locale);
            VelocityTemplate velocity = VelocityTemplate.getInstance();
            String parsedReport = velocity.merge(context, this.template);
	    
	    if (parsedReport == null || "".equals(parsedReport)) {
		new SearchResultExportException("The input has no data to be exported");
	    }

            InputSource input = new InputSource(new StringReader(parsedReport));
            report = parseReport(input);
        }
        else {
            report = parseReport();
        }

	report.setResourceBundleFactory(new DefaultResourceBundleFactory(locale));


        report.setData(data);

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

        throw new SearchResultExportException
            ("Param 'format' not supported: " + format);
    }


    /**
     * Exporta os resultados para o formato XLS.
     */
    public void exportXLS(final JFreeReport report,
                          final OutputStream out)
        throws SearchResultExportException {

        try {
            ExcelProcessor processor = new ExcelProcessor(report);
            processor.setOutputStream(out);
            processor.processReport();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato CSV.
     */
    public static void exportCSV(final JFreeReport report,
                                 final OutputStream out)
        throws SearchResultExportException {

        try {
            CSVTableProcessor processor = new CSVTableProcessor(report);
            processor.setStrictLayout(false);
            processor.setWriter(new PrintWriter(out));
            processor.processReport();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato PDF.
     */
    public void exportPDF(final JFreeReport report,
                          final OutputStream out)
        throws SearchResultExportException {

        try {
            PDFOutputTarget target = new PDFOutputTarget(out);
            target.configure(report.getReportConfiguration());
            target.open();

            PageableReportProcessor processor = new PageableReportProcessor(report);
            processor.setOutputTarget(target);
            processor.processReport();

            target.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato HTML.
     */
    public void exportHTML(final JFreeReport report,
                           final OutputStream out)
        throws SearchResultExportException {

        try {
            HtmlProcessor processor = new HtmlProcessor(report);
            processor.setFilesystem(new StreamHtmlFilesystem(out));
            processor.processReport();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato HTML Fragment.
     */
    public void exportHTMLFragment(final JFreeReport report,
                                   final OutputStream out)
        throws SearchResultExportException {

        try {
            HtmlProcessor processor = new HtmlProcessor(report);
            processor.setGenerateBodyFragment(true);
            processor.setFilesystem(new StreamHtmlFilesystem(out));
            processor.processReport();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato RTF.
     */
    public void exportRTF(final JFreeReport report,
                          final OutputStream out)
        throws SearchResultExportException {

        try {
            RTFProcessor processor = new RTFProcessor(report);
            processor.setStrictLayout(false);
            processor.setOutputStream(out);
            processor.processReport();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Exporta os resultados para o formato PNG.
     */
    public void exportPNG(final JFreeReport report,
                          final OutputStream out)
        throws SearchResultExportException {

        try {
            BufferedImage image = createImage(report);
            Graphics2D g2 = image.createGraphics();
            g2.setPaint(Color.white);
            g2.fillRect(0,0, image.getWidth(), image.getHeight());

            G2OutputTarget target = new G2OutputTarget(g2);

            PageableReportProcessor processor 
                = new PageableReportProcessor(report);
            processor.setOutputTarget(target);
            target.open();
            processor.processReport();
            target.close();
            g2.dispose();

            PngEncoder encoder = new PngEncoder(image, true, 0, 9);
            byte[] data = encoder.pngEncode();
            out.write(data);
            out.flush();

        } catch (Exception e) {
            throw new SearchResultExportException(e);
        }
    }

    /**
     * Create the empty image for the given page size.
     *
     * @param pd the page definition that defines the image bounds.
     * @return the generated image.
     */
    private BufferedImage createImage(final JFreeReport report) {
        PageDefinition pd = report.getPageDefinition();
        double width = pd.getWidth();
        double height = pd.getHeight();

        final BufferedImage image = new BufferedImage
            ((int) width, (int) height, BufferedImage.TYPE_BYTE_INDEXED);
        return image;
    }


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
        context.put(EXPANDED_RESULTS_METADATA, wrap(result.getExpandedResultMetaData()));
        context.put(FORMAT, format);
        context.put(LOCALE, locale);
        context.put(PAGE_TOOL, new PageTool(result.getExpandedResultMetaData()));
        context.put(MESSAGES, ApplicationResources.getInstance(locale));

        return context;
    }

    private List wrap(List rpmd) {
        List list = new ArrayList(rpmd.size());
        Iterator it = rpmd.iterator();
        while (it.hasNext()) {
            list.add(new ResultParameterMetaDataAdapter
                     ((ResultParameterMetaData) it.next()));
        }
        return list;
    }
    
    private JFreeReport parseReport(InputSource in)
        throws SearchResultExportException {

        try {
            ReportGenerator generator = ReportGenerator.getInstance();
            URL url = this.getClass().getResource("/");
	    return generator.parseReport(in, url);

        } catch(Exception e) {
            e.printStackTrace();
            throw new SearchResultExportException("Parsing failed" 
                                                  + e.getMessage());
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

        } catch(Exception e) {
            throw new SearchResultExportException("Parsing failed" 
                                                  + e.getMessage());
        }
    }
}
