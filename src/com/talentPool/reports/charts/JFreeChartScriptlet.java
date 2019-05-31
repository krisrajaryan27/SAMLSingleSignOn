package com.talentPool.reports.charts;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.renderers.JCommonDrawableRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import java.util.ArrayList;


public class JFreeChartScriptlet extends JRDefaultScriptlet {
	static ArrayList names = null;

	static ArrayList values = null;

	public void afterReportInit() throws JRScriptletException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		if (names != null) {
			for (int i = 0; i < names.size(); i++) {
				dataset.setValue((String) names.get(i), new Double((String) values.get(i)));
			}
		}
		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, false, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setForegroundAlpha(0.5f);
		plot.setNoDataMessage("No data to display");

		/*   */
		this.setVariableValue("Chart", new JCommonDrawableRenderer(chart));

	}

	public static void setPieValues(ArrayList n, ArrayList v) {
		names =  n;
		values = v;
	}
	

}
