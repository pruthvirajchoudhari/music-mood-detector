package com.graphs;

import neural.helper.FileHelper;
import com.helper.ServerConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart {

    public static void getLineChart(OutputStream out, ChartModel c, ArrayList<Integer> value1,
            ArrayList<Integer> value2) {
        XYSeries series = new XYSeries(c.getRow1());
        for (int i = 0; i < value1.size(); i++) {
            series.add(value1.get(i), value2.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(c.graphTitle,
                c.xAxisTitle, c.yAxisTitle, dataset, PlotOrientation.VERTICAL,
                true, true, false);

        try {
            ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(), c.getHeight());
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }

    public static void main(String[] args) {
        double[][] values = new double[][]{
            {10, 20},
            {12, 21},
            {13, 24},
            {10, 100}
        };
        String[] axisLabels = new String[]{"A", "B", "C", "D"};
        String[] dataLabels = new String[]{"Makrs", "Per"};
//        getLineChart(null, values, axisLabels, dataLabels);
//        getLineChart(null, values, dataLabels);
        getLineChartMFCC();
    }

    public static BufferedImage getLineChart(OutputStream out, int[][] values, String[] axisLabels, String[] dataLabels) {
        ChartModel c = new ChartModel();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < values.length; i++) {

            for (int j = 0; j < values[0].length; j++) {
                dataset.addValue(values[i][j], dataLabels[i], axisLabels[j]);
            }
        }
        JFreeChart chart = ChartFactory.createLineChart(c.graphTitle,
                c.xAxisTitle, c.yAxisTitle, dataset, PlotOrientation.VERTICAL,
                true, true, false);
        File lineChart = new File("LineChart.jpeg");
        int width = 640; /*
         * Width of the image
         */
        int height = 480; /*
         * Height of the image
         */
        Color[] colors = new Color[4];

        //Initialize the values of the array
        colors[0] = Color.red;
        colors[1] = Color.blue;
        colors[2] = Color.yellow;
        colors[3] = Color.green;
        final CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        for (int i = 0; i < values[0].length; i++) {
            renderer.setSeriesPaint(i, colors[i % 4]);
            renderer.setSeriesStroke(i, new BasicStroke(4.0f));
        }

        plot.setRenderer(renderer);

        try {
            ChartUtilities.saveChartAsJPEG(lineChart, chart, width, height);
//            ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(), c.getHeight());
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
        return chart.createBufferedImage(width, height);
    }

    public static BufferedImage getLineChartMFCC() {
        ArrayList<double[]> arr = FileHelper.parseFileDouble(ServerConstants.PROJECT_DIR + "svm\\training.csv");
        ChartModel c = new ChartModel();
        c.width = 700;
        c.height = 400;
        c.setGraphTitle("MFCC Overall Average Comparision");
        double[][] data = new double[arr.size() - 1][arr.get(0).length - 4];
        for (int i = 1; i < arr.size(); i++) {
            double[] ds = arr.get(i);
            if(ds.length<ds.length - 2){
                continue;
            }
            for (int j = 3; j < ds.length - 2; j++) {
                data[i - 1][j - 3] = ds[j];

            }

        }
        String[] xaxis = new String[data[0].length];
        for (int i = 0; i < xaxis.length; i++) {
            String string = xaxis[i];
            xaxis[i] = "MFCC " + (i + 1);
        }
        return getLineChart(null, c, data, xaxis);
    }

    public static BufferedImage getLineChart(OutputStream out, ChartModel c, double[][] values, String[] dataLabels) {


        XYSeriesCollection collection = new XYSeriesCollection();
        ArrayList<XYSeries> arr = new ArrayList<XYSeries>();
        for (int j = 0; j < values.length; j++) {

            XYSeries series = new XYSeries("Song " + j);
            arr.add(series);
            collection.addSeries(series);
        }

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                collection.getSeries(i).add(j + 1, values[i][j]);
//                dataset.addValue(values[j][i], dataLabels[i], axisLabels[j]);
            }
        }


        JFreeChart chart = ChartFactory.createXYLineChart(c.graphTitle,
                c.xAxisTitle, c.yAxisTitle, collection, PlotOrientation.VERTICAL,
                true, true, false);
        File lineChart = new File("LineChart2.jpeg");
        int width = 640; /*
         * Width of the image
         */

        int height = 480; /*
         * Height of the image
         */

        Color[] colors = new Color[4];
        //Initialize the values of the array
        colors[0] = Color.red;
        colors[1] = Color.blue;
        colors[2] = Color.yellow;
        colors[3] = Color.green;
        final XYPlot plot = chart.getXYPlot();
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        for (int i = 0;
                i < values[
    0].length;
                i++) {
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesLinesVisible(i, Boolean.TRUE);
            renderer.setSeriesShapesVisible(i, true);
            plot.setRenderer(renderer);
        }


        try {
            ChartUtilities.saveChartAsJPEG(lineChart, chart, width, height);
//            ChartUtilities.writeChartAsPNG(out, chart, c.getWidth(), c.getHeight());
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }

        return chart.createBufferedImage(width, height);
    }
}
