package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;


public class BarChartFunction extends AbstractCategoryBasedChartFunction {

    protected JFreeChart createChart() {
        return ChartFactory
            .createBarChart(getTitle(),
                            getDomainAxisLabel(),
                            getRangeAxisLabel(),
                            getDataset(),
                            getPlotOrientation(), 
                            isShowLegend(),
                            false,
                            false);
    }

    protected void setItemLabels(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryItemRenderer renderer = plot.getRenderer();

        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);
    }
}
