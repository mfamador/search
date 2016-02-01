package pt.maisis.search.export.jfreereport.function;

import org.jfree.report.function.AbstractFunction;
import org.jfree.report.event.ReportEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractChartFunction 
    extends AbstractFunction {

    private static Log log = LogFactory.getLog(AbstractChartFunction.class);

    /** Orienta��o vertical do gr�fico. */
    public static final String VERTICAL = "vertical";

    /** Orienta��o horizonal do gr�fico. */
    public static final String HORIZONTAL = "horizontal";

    /** Largura do gr�fico. */
    private int width;

    /** Altura do gr�fico. */
    private int height;

    /** T�tulo do gr�fico. */
    private String title;

    /** Orienta��o do gr�fico {@link #VERTICAL} ou {@link #HORIZONTAL}. */
    private String orientation;

    /** Fonte do t�tulo (@see). */
    private String titleFont;

    /** Fonte das labels. */
    private String labelFont;

    /** Fonte da legenda. */
    private String legendFont;

    /** Deve-se ou n�o mostrar a legenda do gr�fico. */
    private boolean showLegend;

    /** Identifica se o gr�fico gerado deve ser uma imagem ou uma
        inst�ncia de Drawable. */
    private boolean drawable;

    /** Antialias. */
    private boolean antiAlias;

    /** Background do gr�fico. */
    private String backgroundColor;

    /** Background da �rea de plot do gr�fico. */
    private String plotBackgroundColor;

    /** Identifica se os valores dos items devem ou n�o ser somados. */
    private boolean sumItems;

    /** Nome da classe que permite customizar o gr�fico. */
    private String chartCustomizer;

    public AbstractChartFunction() {
    }

    public String getChartCustomizer() {
        return this.chartCustomizer;
    }

    public void setChartCustomizer(final String chartCustomizer) {
        this.chartCustomizer = chartCustomizer;
    }


    public boolean isSumItems() {
        return sumItems;
    }

    public void setSumItems(final boolean sumItems) {
        this.sumItems = sumItems;
    }



    /**
     * Retorna uma string representativa da fonte a utilizar para o
     * t�tulo do gr�fico.
     * @see #setTitleFont
     */
    public String getTitleFont() {
        return titleFont;
    }

    /**
     * Atribui uma string representativa da fonte a utilizar para o
     * t�tulo do gr�fico.
     * <p/>
     * O formato da string deve ser o seguinte:
     * <pre>
     *   "fontfamilyname-style-pointsize" ou
     *   "fontfamilyname style pointsize"
     * </pre>
     * Exemplo:
     * <pre>
     *   "Arial-BOLD-18"
     * </pre>
     *
     * @see http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Font.html#decode
     */
    public void setTitleFont(final String value) {
        this.titleFont = value;
    }

    public String getLegendFont() {
        return this.legendFont;
    }

    public void setLegendFont(final String legendFont) {
        this.legendFont = legendFont;
    }

    public String getLabelFont() {
        return this.labelFont;
    }

    public void setLabelFont(final String labelFont) {
        this.labelFont = labelFont;
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public void setAntiAlias(final boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getPlotBackgroundColor() {
        return this.plotBackgroundColor;
    }

    public void setPlotBackgroundColor(final String plotBackgroundColor) {
        this.plotBackgroundColor = plotBackgroundColor;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(final String orientation) {
        this.orientation = orientation;
    }

    public boolean isShowLegend() {
        return this.showLegend;
    }

    public void setShowLegend(final boolean showLegend) {
        this.showLegend = showLegend;
    }

    public boolean isDrawable() {
        return this.drawable;
    }

    public void setDrawable(final boolean drawable) {
        this.drawable = drawable;
    }


    /**
     * Retorna uma instância do gr�fico (<code>JFreeChart</code>),
     * ou seja, um objecto do tipo drawable, ou uma implementa��o
     * de <code>URLImageContainer</code>.
     */
    public Object getValue() {
	if (this.drawable) {
	    JFreeChart chart = createChart();
	    setCustomProperties(chart);
	    return chart;
	}
	return new ImageReference(this);
    }

    protected void setCustomProperties(JFreeChart chart) {

        if (this.backgroundColor != null) {
            chart.setBackgroundPaint(getColor(this.backgroundColor));
        }

        if (this.plotBackgroundColor != null) {
            Plot plot = chart.getPlot();
            plot.setBackgroundPaint(getColor(this.plotBackgroundColor));
        }

        if (this.titleFont != null) {
            chart.getTitle().setFont(getFont(this.titleFont));
        }

        chart.setAntiAlias(this.antiAlias);

        if (this.chartCustomizer != null) {
            try {
                ChartCustomizer customizer = (ChartCustomizer)
                    Class.forName(this.chartCustomizer).newInstance();

                customizer.customize(chart);

            } catch (Exception e) {} // ignorar customizer
        }
    }

    /**
     * Retorna uma inst�ncia de <code>PlotOrientation</code>
     * tendo em conta a propriedade <code>orientation</code>.
     */
    protected PlotOrientation getPlotOrientation() {
        return
            HORIZONTAL.equals(this.orientation)
            ? PlotOrientation.HORIZONTAL
            : PlotOrientation.VERTICAL;
    }

    /**
     * Converte a dada string numa inst�ncia de <code>Color</code>.
     * @see http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Color.html#decode
     */
    protected Color getColor(final String color) {
        return Color.decode(color);
    }

    /**
     * Converte a dada string numa inst�ncia de <code>Font</code>.
     * @see http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Font.html#decode
     */
    protected Font getFont(final String font) {
        return Font.decode(font);
    }

    protected abstract JFreeChart createChart();
}
