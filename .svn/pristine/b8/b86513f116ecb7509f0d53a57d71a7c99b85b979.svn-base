package com.talentPool.reports.charts;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.renderers.JCommonDrawableRenderer;

/**
 * @author shivprasad
 * 
 */
public class MonthlyJoiningChartScriptlet extends JRDefaultScriptlet {
	static ArrayList names = null;
	static ArrayList values = null;

	public MonthlyJoiningChartScriptlet() {
		
    }

    public void afterReportInit() throws JRScriptletException {
    	
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	if (names != null) {
    		
    		for (int i = 0; i < names.size(); i++) {
    			dataset.setValue(new Integer((String) values.get(i)),"", (String) names.get(i));
    			//empty string is for legend
    		}
    	}


    	JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Joining Chart",       // chart title
                "Month",                    // domain axis label
                "Joined",                   // range axis label
                dataset,                   // data
                PlotOrientation.VERTICAL,  // orientation
                false,                      // include legend
                true,                      // tooltips
                false                      // urls
            );
    	
    	CategoryPlot plot = chart.getCategoryPlot();
    	
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.BLUE);
        plot.setNoDataMessage("No Data To Display");
        this.setVariableValue("Chart", new JCommonDrawableRenderer(chart));


    }
    
    public static void setValues(ArrayList n, ArrayList v) {
		names =  n;
		values = v;
	}
	

}
