package com.graphs;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;



public class ChartsUtil {

	public static void main(String arg[]) {
		ArrayList<String> xAxisValues = new ArrayList<String>();
		xAxisValues.add("Pavan");
		xAxisValues.add("Rajesh");
		xAxisValues.add("Shahadeo");
		xAxisValues.add("Ashok");
		ArrayList<Integer> bar1 = new ArrayList<Integer>();
		bar1.add(10);
		bar1.add(20);
		bar1.add(15);
		bar1.add(10);
		ArrayList<Integer> bar2 = new ArrayList<Integer>();
		bar2.add(12);
		bar2.add(18);
		bar2.add(13);
		bar2.add(8);
		ChartModel c = new ChartModel();
		c.setGraphTitle("Demo Graph");
		c.setxAxisTitle("Names");
		c.setyAxisTitle("Demo Values");
		c.setRow1("1st Value");
		c.setRow2("2nd Value");
		c.setGraphColor(Color.white);
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("D:\\barChartWrite.png"));
			BarChart.get3DBarChart(out, xAxisValues, bar1, bar2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(new File("D:\\barChartWrite1.png"));
			BarChart.get3DBarChart(out,  xAxisValues, bar1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Date> days = new ArrayList<Date>();
		days.add(new Date(2014, 1, 1,11,54,50 ));
		days.add(new Date(2014, 1, 1,11,54,40));
		days.add(new Date(2014, 1, 1,11,54,30));
		days.add(new Date(2014, 1, 1,11,54,20));
		try {
			out = new FileOutputStream(new File("D:\\TimeSeriesChart1.png"));
			TimeSeriesChart.getTimeSeriesGraph(out,  days, bar1,TimeSeriesChart.MyDate.SECOND);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(new File("D:\\TimeSeriesChart2.png"));
			TimeSeriesChart.getTimeSeriesGraph(out,  days, days, bar1, bar2,TimeSeriesChart.MyDate.SECOND);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(new File("D:\\PieChart1.png"));
			PieChart.getPieChart(out,  xAxisValues, bar1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new FileOutputStream(new File("D:\\LineChart1.png"));
//			LineChart.getLineChart(out, bar1, bar2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
