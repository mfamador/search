/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.export.jfreereport.function;

import org.jfree.report.function.Expression;
import org.jfree.report.function.FunctionUtilities;
import org.jfree.report.event.ReportEvent;

import org.jfree.chart.JFreeChart;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.report.DataRow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractCategoryBasedChartFunction
    extends AbstractChartFunction {

    private static Log log 
        = LogFactory.getLog(AbstractCategoryBasedChartFunction.class);

    public static final String NUMBER_FORMAT = "number";
    public static final String DATE_FORMAT   = "date";

    private int currentIndex;
    private String group;
    private DefaultCategoryDataset dataset;
    private List datasets = new ArrayList();
    private String rangeAxisLabel;
    private String domainAxisLabel;

    private boolean dataRowSeries;
    private boolean dataRowCategories;
    private List categories = new ArrayList();
    private List series = new ArrayList();
    private List values = new ArrayList();

    private List seriesColors = new ArrayList();

    private boolean showDomainAxis;
    private boolean showRangeAxis;

    private String itemLabelFormat;
    private String itemLabelFormatterType;
    private String itemLabelFormatterPattern;


    public String getItemLabelFormat() {
        return this.itemLabelFormat;
    }

    public void setItemLabelFormat(final String itemLabelFormat) {
        this.itemLabelFormat = itemLabelFormat;
    }

    public String getItemLabelFormatterType() {
        return this.itemLabelFormatterType;
    }

    public void setItemLabelFormatterType
        (final String itemLabelFormatterType) {
        this.itemLabelFormatterType = itemLabelFormatterType;
    }

    public String getItemLabelFormatterPattern() {
        return this.itemLabelFormatterPattern;
    }

    public void setItemLabelFormatterPattern
        (final String itemLabelFormatterPattern) {
        this.itemLabelFormatterPattern = itemLabelFormatterPattern;
    }

    public boolean isShowDomainAxis() {
        return this.showDomainAxis;
    }

    public void setShowDomainAxis(final boolean showDomainAxis) {
        this.showDomainAxis = showDomainAxis;
    }

    public boolean isShowRangeAxis() {
        return this.showRangeAxis;
    }

    public void setShowRangeAxis(final boolean showRangeAxis) {
        this.showRangeAxis = showRangeAxis;
    }

    public void setSeriesColor(int index, String color) {
        if (this.seriesColors.size() == index) {
            this.seriesColors.add(color);
        } else {
            this.seriesColors.set(index, color);
        }
    }

    public List getSeriesColors() {
        return this.seriesColors;
    }

    public void setDataRowSeries(final boolean dataRowSeries) {
        this.dataRowSeries = dataRowSeries;
    }

    public boolean isDataRowSeries() {
        return this.dataRowSeries;
    }

    public void setDataRowCategories(final boolean dataRowCategories) {
        this.dataRowCategories = dataRowCategories;
    }

    public boolean isDataRowCategories() {
        return this.dataRowCategories;
    }

    public String getRangeAxisLabel() {
        return this.rangeAxisLabel;
    }

    public void setRangeAxisLabel(final String rangeAxisLabel) {
        this.rangeAxisLabel = rangeAxisLabel;
    }

    public String getDomainAxisLabel() {
        return this.domainAxisLabel;
    }

    public void setDomainAxisLabel(final String domainAxisLabel) {
        this.domainAxisLabel = domainAxisLabel;
    }

    protected DefaultCategoryDataset getDataset() {
        return this.dataset;
    }

    /**
     * Atribui o nome do grupo a considerar.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Retorna o nome do grupo a considerar.
     */
    public void setGroup (final String group) {
        this.group = group;
    }

    public String getCategory(final int index) {
        return (String) this.categories.get(index / this.series.size());
    }

    public void setCategory(final int index, final String category) {
        if (this.categories.size() == index) {
            this.categories.add(category);
        }
        else {
            this.categories.set(index, category);
        }
    }

    public String getValue(final int index) {
        return (String) this.values.get(index);
    }

    public void setValue(final int index, final String value) {
        if (this.values.size() == index) {
            this.values.add(value);
        }
        else {
            this.values.set(index, value);
        }
    }

    public String getSerie(final int index) {
        final int actualIndex = index % this.series.size();
        return (String) this.series.get(actualIndex);
    }

    public void setSerie(final int index, final String serie) {
        if (this.series.size() == index) {
            this.series.add(serie);
        }
        else {
            this.series.set(index, serie);
        }
    }


    /**
     * Notificacão que o relatório foi iniciado.
     */
    public void reportInitialized(final ReportEvent event) {
        this.currentIndex = -1;

        if (FunctionUtilities.isDefinedPrepareRunLevel(this, event)) {
            if (log.isTraceEnabled()) {
                log.trace("[report initialized]: pre-run level");
            }

            this.datasets.clear();

            // se for para considerar grupos, não é suposto
            // criar nenhum dataset
            if (this.group == null) {
                this.dataset = new DefaultCategoryDataset();
                this.datasets.add(this.dataset);
            }
        }
        else {
            if (log.isTraceEnabled()) {
                log.trace("[report initialized]: layout level");
            }
            if (this.group == null) {
                this.dataset = (DefaultCategoryDataset) this.datasets.get(0);
            }
        }
    }

    /**
     * Notificacão que o grupo foi iniciado.
     */
    public void groupStarted (final ReportEvent event) {
        // se nao for o grupo que se pretende tratar...
        if (!FunctionUtilities.isDefinedGroup(getGroup(), event)) {
            return;
        }

        // iniciar dataset
        if (FunctionUtilities.isDefinedPrepareRunLevel(this, event)) {
            if (log.isTraceEnabled()) {
                log.trace("[group started]: pre-run level, name=" + this.group);
            }
            this.dataset = new DefaultCategoryDataset();
            this.datasets.add(this.dataset);

        } else if (FunctionUtilities.isLayoutLevel(event)) {
            if (log.isTraceEnabled()) {
                log.trace("[group started]: layout level");
            }
            // capturar dados do pre-run
            this.currentIndex += 1;
            this.dataset 
                = (DefaultCategoryDataset) this.datasets.get(this.currentIndex);
        }
    }

    /**
     * Notificacão que o grupo foi iniciado.
     */
    public void groupFinished (final ReportEvent event) {
        if (!isSumItems()) {
            // se nao for o grupo que se pretende tratar...
            if (!FunctionUtilities.isDefinedGroup(getGroup(), event)) {
                return;
            }
             // iniciar dataset
            if (FunctionUtilities.isDefinedPrepareRunLevel(this, event)) {
                if (log.isTraceEnabled()) {
                    log.trace("[group finished]: pre-run level, name=" 
                              + this.group);
                }
                DataRow datarow = event.getDataRow();

                for (int i = 0; i < this.values.size(); i++) {
                    String category = getCategory(datarow, i);
                    String serie = getSerie(datarow, i);
                    double value = getValue(datarow, i);

                    if (log.isTraceEnabled()) {
                        log.trace("[group finished]: "
                                  + "dataset.addValue"
                                  + "(value=" + value
                                  + ", serie=" + serie
                                  + ", category=" + category
                                  + ")");
                    }

                    this.dataset.addValue(value, serie, category);
                }
            }
        }
    }

    /**
     * Notificacão que um item está a ser processado.
     */
    public void itemsAdvanced (final ReportEvent event) {
        if (isSumItems()) {
            // Verificar se é o nível da funcão correcto (level == 0)
            // e se é o prepare run.
            if (!FunctionUtilities.isDefinedPrepareRunLevel(this, event)) {
                if (log.isTraceEnabled()) {
                    log.trace("[items advanced]: layout level");
                }
                return;
            }

            if (log.isTraceEnabled()) {
                log.trace("[items advanced]: pre-run level");
            }

            DataRow datarow = event.getDataRow();

            for (int i = 0; i < this.values.size(); i++) {
                String category = getCategory(datarow, i);
                String serie = getSerie(datarow, i);
                double value = getSumValue(datarow, i);

                if (log.isTraceEnabled()) {
                    log.trace("[items advanced]: "
                              + "dataset.addValue"
                              + "(value=" + value
                              + ", serie=" + serie
                              + ", category=" + category
                              + ")");
                }

                if (category != null) {
                    this.dataset.addValue(value, serie, category);
                }
            }
        }
    }

    private String getCategory(final DataRow dataRow, final int index) {
        if (isDataRowCategories()) {
            return (String) dataRow.get(getCategory(index));
        }
        return getCategory(index);
    }

    private String getSerie(final DataRow dataRow, final int index) {
        if (isDataRowSeries()) {
            return (String) dataRow.get(getSerie(index));
        }
        return getSerie(index);
    }

    private double getSumValue(final DataRow dataRow, final int index) {
        Object o = dataRow.get(getValue(index));

        double value =
            (o instanceof Number)
            ? ((Number) o).doubleValue() : 0;


        // Verificar se já foi introduzido esta coluna
        // caso tenha sido, os valores devem ser somados
        Number previousValue = null;
        try {
            if (getCategory(dataRow, index) != null) {
                previousValue = this.dataset.getValue
                    (getSerie(dataRow, index), getCategory(dataRow, index));
            }

        } catch (UnknownKeyException e) {
            // Esta excepcão é lancada sempre que se faz um get
            // para uma chave inexistente (apesar de não ver isto
            // na documentacão da API).
        }

        if (previousValue == null) {
            return value;
        }
        return value + previousValue.doubleValue();
    }

    private double getValue(final DataRow dataRow, final int index) {
        Object o = dataRow.get(getValue(index));

        double value =
            (o instanceof Number)
            ? ((Number) o).doubleValue() : 0;

        return value;
    }


    /**
     * Return a completly separated copy of this function. The copy no longer
     * shares any changeable objects with the original function.
     *
     * @return a copy of this function.
     */
    public Expression getInstance() {
        AbstractCategoryBasedChartFunction f =
            (AbstractCategoryBasedChartFunction) super.getInstance();
        f.dataset = null;
        f.datasets = new ArrayList();
        return f;
    }

    private void readObject (final ObjectInputStream in)
        throws IOException, ClassNotFoundException {

        in.defaultReadObject();
        this.datasets = new ArrayList();
        this.dataset = null;
    }



    protected void setCustomProperties(final JFreeChart chart) {
        super.setCustomProperties(chart);

        CategoryItemRenderer renderer = getRenderer(chart);
        if (!this.seriesColors.isEmpty()) {
            for (int i = 0; i < seriesColors.size(); i++) {
                renderer.setSeriesPaint(i, Color.decode((String)this.seriesColors.get(i)));
            }
        }

        if (!this.showDomainAxis) {
            chart.getCategoryPlot().getDomainAxis().setVisible(false);
        }

        if (!this.showRangeAxis) {
            chart.getCategoryPlot().getRangeAxis().setVisible(false);
        }

        if (this.itemLabelFormat != null 
            && this.itemLabelFormatterType != null 
            && this.itemLabelFormatterPattern != null) {

            StandardCategoryItemLabelGenerator labelGenerator;
            if (DATE_FORMAT.equals(itemLabelFormatterType)) {
                labelGenerator = new StandardCategoryItemLabelGenerator
                    (this.itemLabelFormat,
                     new SimpleDateFormat(this.itemLabelFormatterPattern));

            } else {
                labelGenerator = new StandardCategoryItemLabelGenerator
                    (this.itemLabelFormat,
                     new DecimalFormat(this.itemLabelFormatterPattern));
            }
            getRenderer(chart).setItemLabelGenerator(labelGenerator);
        }
    }

    protected CategoryItemRenderer getRenderer(final JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        return renderer;
    }
}
