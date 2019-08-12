package com.graphs;

import java.awt.Color;
import java.io.Serializable;

public class ChartModel implements Serializable{
	public static String row1 ="1st Value",row2 = "2nd Value";
	public static String xAxisTitle = "X",yAxisTitle = "Y",graphTitle = "GraphTitle";
	Color chartBackground = Color.yellow;
	Color graphColor = Color.BLACK;
	Color colorTitle = Color.blue;
	Color colorGrid = Color.red;
	int width = 1000,height =300;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getChartBackground() {
		return chartBackground;
	}

	public void setChartBackground(Color chartBackground) {
		this.chartBackground = chartBackground;
	}

	public Color getGraphColor() {
		return graphColor;
	}

	public void setGraphColor(Color graphColor) {
		this.graphColor = graphColor;
	}

	public Color getColorTitle() {
		return colorTitle;
	}

	public void setColorTitle(Color colorTitle) {
		this.colorTitle = colorTitle;
	}

	public Color getColorGrid() {
		return colorGrid;
	}

	public void setColorGrid(Color colorGrid) {
		this.colorGrid = colorGrid;
	}

	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	public String getyAxisTitle() {
		return yAxisTitle;
	}

	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		this.graphTitle = graphTitle;
	}

	public String getRow1() {
		return row1;
	}

	public void setRow1(String row1) {
		this.row1 = row1;
	}

	public String getRow2() {
		return row2;
	}

	public void setRow2(String row2) {
		this.row2 = row2;
	}
	
}
