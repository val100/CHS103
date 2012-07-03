/*
 * XYBarChart.java
 *
 * Created on August 7, 2008, 3:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package chs103.graph;

import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Title: XYBarChart <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class XYBarChart {

    private JFreeChart chart;
    private XYSeriesCollection dataset;
    private Color[] batchColor;
    public static final int SIZE = 80;
    public static final int startIndex = 0;
    public static final int endIndex = 79;

    /**
     * Creates a new XYBarChart instance.
     */
    public XYBarChart() {
    }

    /**
     * Creates a new barchart instance.
     * @param categories the catigories.
     * @param title the title.
     * @param info the info.
     * @param values the array of values.
     * @param minGraph the start graph.
     * @param minGraphIndex the step in graph.
     */
    public XYBarChart(String title, int batchNumber, String info, double values[], double minGraph, int minGraphIndex, double range, double interval) {
        batchColor = new Color[9];
        initColor();
        dataset = createDataset(values, minGraph, minGraphIndex, startIndex, endIndex, range, interval);
        chart = createXYBarChart(title, batchNumber, dataset, info);
    }

    /**
     * Creates a dataset
     * @param values the array of values.
     * @param minGraph the start graph.
     * @param minGraphIndex the step in graph.
     * @return dataset
     */
    private XYSeriesCollection createDataset(double[] values, double minGraph, int minGraphIndex, int startIndex, int endIndex, double range, double interval) {
        //Create a simple XY chart
        XYSeries series = new XYSeries("(weight,birds)");

        for (int i = startIndex; i < endIndex; i++) {
            //Add the series to your data set
            double cell = (i - minGraphIndex) * range + minGraph;
            if (cell >= 0) {
                series.add(cell, values[i]);
            }
        }
        dataset = new XYSeriesCollection();
        dataset.setIntervalWidth(interval);
        dataset.setIntervalPositionFactor(0);
        dataset.addSeries(series);

        return dataset;
    }

    /**
     * Create xybarchart
     * @param title     the title.
     * @param dataset   the dataset.
     * @param info      the info.
     * @return chart    the new JFreeChart object 
     */
    private JFreeChart createXYBarChart(String title, int batchNumber, XYSeriesCollection dataset, String info) {
        chart = ChartFactory.createXYBarChart(
                title + " " + batchNumber,// Title
                info, // x-axis Label
                false,
                "", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
                );

        chart.setBackgroundPaint(new Color(192, 192, 192));
        XYPlot plot = (XYPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final XYItemRenderer renderer = plot.getRenderer();

        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, batchColor[batchNumber - 1],
                0.0f, 0.0f, batchColor[batchNumber - 1]);
        renderer.setSeriesPaint(0, gp0);

        return chart;
    }

    /**
     * Return   chart
     * @return chart the chart
     */
    public JFreeChart getChart() {
        return chart;
    }

    private void initColor() {
        batchColor[0] = new Color(128, 128, 128);
        batchColor[1] = new Color(255, 255, 0);
        batchColor[2] = new Color(128, 0, 255);
        batchColor[3] = new Color(0, 128, 64);
        batchColor[4] = new Color(0, 128, 255);
        batchColor[5] = new Color(255, 0, 0);
        batchColor[6] = new Color(255, 0, 255);
        batchColor[7] = new Color(128, 64, 0);
        batchColor[8] = new Color(0, 0, 0);
    }
}
