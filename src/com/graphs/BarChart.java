package com.graphs;

import java.io.OutputStream;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart {
	public static void get3DBarChart(OutputStream out, ChartModel c,
			ArrayList<String> xAxisValues, ArrayList<Integer> bar1,
			ArrayList<Integer> bar2) {
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < xAxisValues.size(); i++) {
				dataset.setValue(bar1.get(i), c.getRow1(), xAxisValues.get(i)
						.toString());
				dataset.setValue(bar2.get(i), c.getRow2(), xAxisValues.get(i)
						.toString());
			}
			JFreeChart chart = ChartFactory.createBarChart3D(c.getGraphTitle(),
					c.getxAxisTitle(), c.getyAxisTitle(), dataset,
					PlotOrientation.VERTICAL, true, true, false);
			chart.setBackgroundPaint(c.chartBackground);
			chart.getTitle().setPaint(c.colorTitle);
			CategoryPlot p = chart.getCategoryPlot();
			p.setBackgroundPaint(c.graphColor);
			p.setRangeGridlinePaint(c.colorGrid);
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void get3DBarChart(OutputStream out,
			ArrayList<String> xAxisValues, ArrayList<Integer> bar1,
			ArrayList<Integer> bar2) {
		ChartModel c = new ChartModel();
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < xAxisValues.size(); i++) {
				dataset.setValue(bar1.get(i), c.getRow1(), xAxisValues.get(i)
						.toString());
				dataset.setValue(bar2.get(i), c.getRow2(), xAxisValues.get(i)
						.toString());
			}
			JFreeChart chart = ChartFactory.createBarChart3D(c.getGraphTitle(),
					c.getxAxisTitle(), c.getyAxisTitle(), dataset,
					PlotOrientation.VERTICAL, true, true, false);
			chart.setBackgroundPaint(c.chartBackground);
			chart.getTitle().setPaint(c.colorTitle);
			CategoryPlot p = chart.getCategoryPlot();
			p.setBackgroundPaint(c.graphColor);
			p.setRangeGridlinePaint(c.colorGrid);
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void get3DBarChart(OutputStream out, ChartModel c,
			ArrayList<String> xAxisValues, ArrayList<Integer> bar1) {
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < xAxisValues.size(); i++) {
				dataset.setValue(bar1.get(i), c.getRow1(), xAxisValues.get(i)
						.toString());
			}
			JFreeChart chart = ChartFactory.createBarChart3D(c.getGraphTitle(),
					c.getxAxisTitle(), c.getyAxisTitle(), dataset,
					PlotOrientation.VERTICAL, true, true, false);
			chart.setBackgroundPaint(c.chartBackground);
			chart.getTitle().setPaint(c.colorTitle);
			CategoryPlot p = chart.getCategoryPlot();
			p.setBackgroundPaint(c.graphColor);
			p.setRangeGridlinePaint(c.colorGrid);
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void get3DBarChart(OutputStream out,
			ArrayList<String> xAxisValues, ArrayList<Integer> bar1) {
		ChartModel c = new ChartModel();
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < xAxisValues.size(); i++) {
				dataset.setValue(bar1.get(i), c.getRow1(), xAxisValues.get(i)
						.toString());
			}
			JFreeChart chart = ChartFactory.createBarChart3D(c.getGraphTitle(),
					c.getxAxisTitle(), c.getyAxisTitle(), dataset,
					PlotOrientation.VERTICAL, true, true, false);
			chart.setBackgroundPaint(c.chartBackground);
			chart.getTitle().setPaint(c.colorTitle);
			CategoryPlot p = chart.getCategoryPlot();
			p.setBackgroundPaint(c.graphColor);
			p.setRangeGridlinePaint(c.colorGrid);
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
