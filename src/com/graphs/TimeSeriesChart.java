package com.graphs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

public class TimeSeriesChart {
	public static void getTimeSeriesGraph(OutputStream out, ChartModel c,
			ArrayList<Date> days, ArrayList<Integer> value, MyDate m) {
		TimeSeries pop;
		if (m == MyDate.MONTH) {//same year but for different months
			pop = new TimeSeries(c.row1, Month.class); 
			for (int i = 0; i < days.size(); i++)
				pop.add(new Month(days.get(i)), value.get(i));
		} else if (m == MyDate.DAY) { //different days in same month or year
			pop = new TimeSeries(c.row1, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Day(days.get(i)), value.get(i));
		} else if (m == MyDate.YEAR) { //graph for different years
			pop = new TimeSeries(c.row1, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Year(days.get(i)), value.get(i));
		} else if (m == MyDate.HOUR) { //in same day different hours
			pop = new TimeSeries(c.row1, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Hour(days.get(i)), value.get(i));
		} else if (m == MyDate.MINUTE) { // in same day different minutes
			pop = new TimeSeries(c.row1, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Minute(days.get(i)), value.get(i));
		} else { // in same day different seconds
			pop = new TimeSeries(c.row1, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Second(days.get(i)), value.get(i));
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setDomainIsPointsInTime(true);
		dataset.addSeries(pop);
		XYDataset data1 = dataset;
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				c.getGraphTitle(), c.xAxisTitle, c.yAxisTitle, data1, true,
				true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(1, true);
		plot.setRenderer(renderer);

		ValueAxis xAxis = (ValueAxis) plot.getDomainAxis();
		xAxis.setVerticalTickLabels(true);
		try {
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}

	public static enum MyDate {
		MONTH, DAY, YEAR, HOUR, SECOND, MINUTE
	}

	public static void getTimeSeriesGraph(OutputStream out,
			ArrayList<Date> days, ArrayList<Integer> value, MyDate m) {
		ChartModel c = new ChartModel();
		TimeSeries pop;
		if (m == MyDate.MONTH) {//same year but for different months
			pop = new TimeSeries(c.row1, Month.class); 
			for (int i = 0; i < days.size(); i++)
				pop.add(new Month(days.get(i)), value.get(i));
		} else if (m == MyDate.DAY) { //different days in same month or year
			pop = new TimeSeries(c.row1, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Day(days.get(i)), value.get(i));
		} else if (m == MyDate.YEAR) { //graph for different years
			pop = new TimeSeries(c.row1, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Year(days.get(i)), value.get(i));
		} else if (m == MyDate.HOUR) { //in same day different hours
			pop = new TimeSeries(c.row1, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Hour(days.get(i)), value.get(i));
		} else if (m == MyDate.MINUTE) { // in same day different minutes
			pop = new TimeSeries(c.row1, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Minute(days.get(i)), value.get(i));
		} else { // in same day different seconds
			pop = new TimeSeries(c.row1, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Second(days.get(i)), value.get(i));
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setDomainIsPointsInTime(true);
		dataset.addSeries(pop);
		XYDataset data1 = dataset;
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				c.getGraphTitle(), c.xAxisTitle, c.yAxisTitle, data1, true,
				true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(1, true);
		plot.setRenderer(renderer);

		ValueAxis xAxis = (ValueAxis) plot.getDomainAxis();
		xAxis.setVerticalTickLabels(true);
		try {
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}

	public static void getTimeSeriesGraph(OutputStream out,
			ArrayList<Date> days, ArrayList<Date> days2,
			ArrayList<Integer> value, ArrayList<Integer> value2, MyDate m) {
		ChartModel c = new ChartModel();
		TimeSeries pop;
		TimeSeries pop2;
		if (m == MyDate.MONTH) {//same year but for different months
			pop = new TimeSeries(c.row1, Month.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Month(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Month.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Month(days2.get(i)), value2.get(i));
		} else if (m == MyDate.DAY) { //different days in same month or year
			pop = new TimeSeries(c.row1, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Day(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Day(days2.get(i)), value2.get(i));
		} else if (m == MyDate.YEAR) { //graph for different years
			pop = new TimeSeries(c.row1, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Year(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Year(days2.get(i)), value2.get(i));
		} else if (m == MyDate.HOUR) {//in same day different hours
			pop = new TimeSeries(c.row1, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Hour(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Hour(days2.get(i)), value2.get(i));
		} else if (m == MyDate.MINUTE) {// in same day different minutes
			pop = new TimeSeries(c.row1, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Minute(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Minute(days2.get(i)), value2.get(i));
		} else {// in same day different seconds
			pop = new TimeSeries(c.row1, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Second(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Second(days2.get(i)), value2.get(i));
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setDomainIsPointsInTime(true);
		dataset.addSeries(pop);
		dataset.addSeries(pop2);
		XYDataset data1 = dataset;
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				c.getGraphTitle(), c.xAxisTitle, c.yAxisTitle, data1, true,
				true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(1, true);
		plot.setRenderer(renderer);

		ValueAxis xAxis = (ValueAxis) plot.getDomainAxis();
		xAxis.setVerticalTickLabels(true);
		try {
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}

	public static void getTimeSeriesGraph(OutputStream out, ChartModel c,
			ArrayList<Date> days, ArrayList<Date> days2,
			ArrayList<Integer> value, ArrayList<Integer> value2, MyDate m) {
		TimeSeries pop;
		TimeSeries pop2;
		if (m == MyDate.MONTH) {//same year but for different months
			pop = new TimeSeries(c.row1, Month.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Month(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Month.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Month(days2.get(i)), value2.get(i));
		} else if (m == MyDate.DAY) { //different days in same month or year
			pop = new TimeSeries(c.row1, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Day(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Day.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Day(days2.get(i)), value2.get(i));
		} else if (m == MyDate.YEAR) { //graph for different years
			pop = new TimeSeries(c.row1, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Year(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Year.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Year(days2.get(i)), value2.get(i));
		} else if (m == MyDate.HOUR) {//in same day different hours
			pop = new TimeSeries(c.row1, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Hour(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Hour.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Hour(days2.get(i)), value2.get(i));
		} else if (m == MyDate.MINUTE) {// in same day different minutes
			pop = new TimeSeries(c.row1, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Minute(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Minute.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Minute(days2.get(i)), value2.get(i));
		} else {// in same day different seconds
			pop = new TimeSeries(c.row1, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop.add(new Second(days.get(i)), value.get(i));

			pop2 = new TimeSeries(c.row2, Second.class);
			for (int i = 0; i < days.size(); i++)
				pop2.add(new Second(days2.get(i)), value2.get(i));
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setDomainIsPointsInTime(true);
		dataset.addSeries(pop);
		dataset.addSeries(pop2);
		XYDataset data1 = dataset;
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				c.getGraphTitle(), c.xAxisTitle, c.yAxisTitle, data1, true,
				true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(1, true);
		plot.setRenderer(renderer);

		ValueAxis xAxis = (ValueAxis) plot.getDomainAxis();
		xAxis.setVerticalTickLabels(true);
		try {
			ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(),
					c.getHeight());
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
}
