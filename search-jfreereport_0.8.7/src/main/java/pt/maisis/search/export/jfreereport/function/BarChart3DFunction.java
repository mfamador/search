package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;

public class BarChart3DFunction extends AbstractCategoryBasedChartFunction {

    protected JFreeChart createChart() {
        return ChartFactory
            .createBarChart3D(getTitle(),
                              getDomainAxisLabel(),
                              getRangeAxisLabel(),
                              getDataset(),
                              getPlotOrientation(), 
                              isShowLegend(),
                              false,
                              false);
    }
}
