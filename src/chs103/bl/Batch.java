/*
 * Batch.java
 *
 * Created on August 7, 2008, 9:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package chs103.bl;

import chs103.graph.XYBarChart;
import chs103.physical.PropertyFileManager;
import cs103.gui.CS103App;
import java.util.Locale;
import java.util.ResourceBundle;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;

/**
 * Title: Batch <br> Description: <br> Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class Batch {

    private int batchNumber;
    private int numOfBirds;
    private int minGraphIndex;
    private double minGraph;
    private int avgWeightRounded;
    private double avgWeight;
    private int cv;
    private int numOfBirdsPercent;
    private double stdDev;
    private DateTime startDate;
    private DateTime endDate;
    private int[] histogramma;
    private int[] historyWeight;
    private byte unitFlag;   // 0 - libra,1 - gr
    private XYBarChart barchart;
    public static final byte POUND = 0;
    public static final byte GRAM = 1;
    public static final double GRAM_UNIT = 25;
    public static final double LIBRA_UNIT = 0.050;
    private Locale currLocale;
    private ResourceBundle rb;


    /**
     * Creates a new instance of Batch
     */
    public Batch() {
        this.batchNumber = 0;
        this.numOfBirds = 0;
        this.minGraphIndex = 0;
        this.minGraph = 0;
        this.avgWeightRounded = 0;
        this.avgWeight = 0;
        this.cv = 0;
        this.numOfBirdsPercent = 0;
        this.stdDev = 0;
        this.startDate = null;
        this.endDate = null;
        this.histogramma = null;
        this.historyWeight = null;
        this.barchart = null;
        String unitString = PropertyFileManager.getProperty(CS103App.FILENAME, "weightunit");
        this.unitFlag = (unitString.equals("pound")) ? (byte) 0 : (byte) 1;
    }

    public Batch(int batchNumber,
            int numOfBirds,
            int minGraphIndex,
            double minGraph,
            int avgWeightRounded,
            double avgWeight,
            int cv,
            int numOfBirdsPercent,
            double stdDev,
            DateTime startDate,
            DateTime endDate,
            int[] histogramma,
            int[] historyWeight,
            byte unitFlag) {
        this.batchNumber = batchNumber;
        this.numOfBirds = numOfBirds;
        this.minGraphIndex = minGraphIndex;
        this.minGraph = minGraph;
        this.avgWeightRounded = avgWeightRounded;
        this.avgWeight = (unitFlag == POUND) ? avgWeight / 100 : avgWeight;
        this.cv = cv;
        this.numOfBirdsPercent = numOfBirdsPercent;
        this.stdDev = (unitFlag == POUND) ? stdDev / 100 : stdDev;
        this.startDate = startDate;
        this.endDate = endDate;
        this.histogramma = histogramma;
        this.historyWeight = historyWeight;
        this.unitFlag = unitFlag;
        this.barchart = null;
    }

    /**
     * Reset all fileds of batch
     */
    public void reset() {
        this.numOfBirds = 0;
        this.minGraphIndex = 0;
        this.minGraph = 0;
        this.avgWeightRounded = 0;
        this.avgWeight = 0;
        this.cv = 0;
        this.numOfBirdsPercent = 0;
        this.stdDev = 0;
        this.startDate = null;
        this.endDate = null;
        this.histogramma = null;
        this.barchart = null;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getNumOfBirds() {
        return numOfBirds;
    }

    public void setNumOfBirds(int numOfBirds) {
        this.numOfBirds = numOfBirds;
    }

    public int getMinGraphIndex() {
        return minGraphIndex;
    }

    public void setMinGraphIndex(int minGraphIndex) {
        this.minGraphIndex = minGraphIndex;
    }

    public double getMinGraph() {
        return minGraph;
    }

    public void setMinGraph(double minGraph) {
        this.minGraph = minGraph;
    }

    public int getAvgWeightRounded() {
        return avgWeightRounded;
    }

    public void setAvgWeightRounded(int avgWeightRounded) {
        this.avgWeightRounded = avgWeightRounded;
    }

    public double getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(double avgWeight) {
        this.avgWeight = avgWeight;
    }

    public int getCv() {
        return cv;
    }

    public void setCv(int cv) {
        this.cv = cv;
    }

    public int getNumOfBirdsPercent() {
        return numOfBirdsPercent;
    }

    public int calcNumOfBirdPercent() {
        try {
            return ((numOfBirdsPercent * 100) + numOfBirds / 2) / numOfBirds;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public void setNumOfBirdsPercent(int numOfBirdsPercent) {
        this.numOfBirdsPercent = numOfBirdsPercent;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDiv(double stdDev) {
        this.stdDev = stdDev;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public int[] getHistogramma() {
        return histogramma;
    }

    public void setHistogramma(int[] histogramma) {
        this.histogramma = histogramma;
    }

    public int[] getHistoryWeight() {
        return historyWeight;
    }

    public void setHistoryWeight(int[] historyWeight) {
        this.historyWeight = historyWeight;
    }

    public XYBarChart getBarchart() {
        return barchart;
    }

    public void setBarchart(XYBarChart barchart) {
        this.barchart = barchart;
    }

    public byte getUnitFlag() {
        return unitFlag;
    }

    public void setUnitFlag(byte unitFlag) {
        this.unitFlag = unitFlag;
    }

    public Locale getCurrLocale() {
        return currLocale;
    }

    public void setCurrLocale(Locale currLocale) {
        this.currLocale = currLocale;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

    /**
     * Parses the byte array argument as a new Batch object.
     *
     * @param buf a
     * <code>byte</code> array containing the array of
     * <code>byte</code> representation to be parsed
     * @param i an
     * <code>int</code> containing the batch index
     * @return the Batch object
     */
    public static Batch parse(int[] buf, int i, byte unit) {

        int totalOffset = BatchFormat.HEAD_OFFSET + (BatchFormat.DATA_OFFSET * i);
        // init histogramma
        int start = BatchFormat.START_HISTOGRAM + totalOffset;
        int end = BatchFormat.END_HISTOGRAM + totalOffset;
        int arrHistograma[] = new int[80];
        for (int j = 0; start <= end && j < 80; j++, start++) {
            arrHistograma[j] = buf[start];
        }
        // init batch data

        // batch number
        int batchNum = buf[BatchFormat.BATCH_NUM + totalOffset];
        if (batchNum == 0 || batchNum == 255) {
            batchNum = i + 1;
        }
        // num. of birds
        int numOfBirds = (int) buf[BatchFormat.END_NUM_OF_BIRDS + totalOffset] * 256;
        numOfBirds += buf[BatchFormat.START_NUM_OF_BIRDS + totalOffset];
        // minimum graph index
        int minGraphIndex = buf[BatchFormat.MIN_GRAPH_INDEX + totalOffset];
        // minimum graph
        int minGraph = (int) buf[BatchFormat.END_MIN_GRAPH + totalOffset] * 256;
        minGraph += buf[BatchFormat.START_MIN_GRAPH + totalOffset];
        // average weight rounded
        int avgWeightRounded = (int) buf[BatchFormat.END_AVG_WEIGHT_RUND + totalOffset] * 256;
        avgWeightRounded += buf[BatchFormat.END_AVG_WEIGHT_RUND + totalOffset];
        // average weight
        int avgWeight = (int) buf[BatchFormat.END_AVG_WEIGHT + totalOffset] * 256;
        avgWeight += buf[BatchFormat.START_AVG_WEIGHT + totalOffset];
        // cv
        int cv = (int) buf[BatchFormat.END_CV + totalOffset] * 256;
        cv += buf[BatchFormat.START_CV + totalOffset];
        // number of birds percentage
        int numOfBirdsPercent = (int) buf[BatchFormat.END_NUM_OF_BIRDS_PRC + totalOffset] * 256;
        numOfBirdsPercent += buf[BatchFormat.START_NUM_OF_BIRDS_PRC + totalOffset];
        // standart deviation
        int stdDev = (int) buf[BatchFormat.END_STD_DEVIATION + totalOffset] * 256;
        stdDev = buf[BatchFormat.START_STD_DEVIATION + totalOffset];
        // start year
        int startYear = (int) buf[BatchFormat.END_START_YEAR + totalOffset] * 256;
        startYear = buf[BatchFormat.START_START_YEAR + totalOffset];
        // start month
        int startMonth = (int) buf[BatchFormat.START_MONTH + totalOffset];
        // start day
        int startDay = (int) buf[BatchFormat.START_DAY + totalOffset];
        // start hour
        int startHour = (int) buf[BatchFormat.START_HOUR + totalOffset];
        // start minute
        int startMinute = (int) buf[BatchFormat.START_MINUTE + totalOffset];

        DateTime startDate = new DateTime(startYear, startMonth, startDay, startHour, startMinute);
        // end year
        int endYear = (int) buf[BatchFormat.END_END_YEAR + totalOffset] * 256;
        endYear = buf[BatchFormat.START_END_YEAR + totalOffset];
        // end month
        int endMonth = (int) buf[BatchFormat.END_MONTH + totalOffset];
        // end day
        int endDay = (int) buf[BatchFormat.END_DAY + totalOffset];
        // end hour
        int endHour = (int) buf[BatchFormat.END_HOUR + totalOffset];
        // end minute
        int endMinute = (int) buf[BatchFormat.END_MINUTE + totalOffset];

        DateTime endDate = new DateTime(endYear, endMonth, endDay, endHour, endMinute);
        start = BatchFormat.START_HISTORY_WEIGHT + totalOffset;
        end = BatchFormat.END_HISTORY_WEIGHT + totalOffset;
        int arrHistoryWeigth[] = new int[256];
        for (int j = 0; start <= end && j < 256; j++) {
            arrHistoryWeigth[j] = buf[start];
            arrHistoryWeigth[j] = arrHistoryWeigth[j] + buf[start + 1] * 256;
            start += 2;
        }

        return new Batch(batchNum, numOfBirds,
                minGraphIndex, (double) minGraph,
                avgWeightRounded, (double) avgWeight,
                cv, numOfBirdsPercent,
                stdDev, startDate,
                endDate, arrHistograma,
                arrHistoryWeigth, unit);
    }

    /**
     * Creating barchart
     *
     * @return the XYBarChart object
     */
    public XYBarChart createBarChart() {
        double values[] = new double[80];
        for (int i = 0; i < 80; i++) {
            values[i] = (double) histogramma[i];
        }
        String graphTitle = rb.getString("CS103.Table.col.batch.text");
        if (unitFlag == POUND) {
            String unit = rb.getString("CS103App.Unit.Pound.text");
            double range = LIBRA_UNIT;
            double interval = LIBRA_UNIT;
            minGraph = minGraph / 100;

            String info = rb.getString("CS103.Graph.Average.text") + avgWeight + " " + unit + " "
                    + rb.getString("CS103.Graph.Birds.text")  + numOfBirds+ " "
                    + rb.getString("CS103.Graph.CV.text")  + cv+ " "
                    + rb.getString("CS103.Graph.Std.Dev.text") + stdDev;

            barchart = new XYBarChart(graphTitle, batchNumber, info, values, minGraph, minGraphIndex, range, interval);
        } else {
            String unit = rb.getString("CS103App.Unit.Gram.text");
            double range = GRAM_UNIT;
            double interval = GRAM_UNIT;

            String info = rb.getString("CS103.Graph.Average.text") + avgWeight + " " + unit + " "
                    + rb.getString("CS103.Graph.Birds.text") +  numOfBirds+ " "
                    + rb.getString("CS103.Graph.CV.text") +  cv+ " "
                    + rb.getString("CS103.Graph.Std.Dev.text") + " " + stdDev;
            barchart = new XYBarChart(graphTitle, batchNumber, info, values, minGraph, minGraphIndex, range, interval);
        }
        return barchart;
    }

    /**
     * Creating barchart
     *
     * @return the XYBarChart object
     */
    public XYBarChart recreateBarChart() {
        double values[] = new double[80];
        for (int i = 0; i < 80; i++) {
            values[i] = (double) histogramma[i];
        }
        String graphTitle = rb.getString("CS103.Table.col.batch.text") + " " + batchNumber;
        barchart.getChart().setTitle(graphTitle);
        String info = "";

        if (unitFlag == POUND) {
            String unit = rb.getString("CS103App.Unit.Pound.text");
            double range = LIBRA_UNIT;
            double interval = LIBRA_UNIT;
            minGraph = minGraph / 100;

            info = rb.getString("CS103.Graph.Average.text")  + avgWeight + " " + unit + " "
                    + rb.getString("CS103.Graph.Birds.text")  +  numOfBirds+ " "
                    + rb.getString("CS103.Graph.CV.text")  +  cv+ " "
                    + rb.getString("CS103.Graph.Std.Dev.text")  +  stdDev;
        } else {
            String unit = rb.getString("CS103App.Unit.Gram.text");
            double range = GRAM_UNIT;
            double interval = GRAM_UNIT;

            info = rb.getString("CS103.Graph.Average.text") + avgWeight + " " + unit + " "
                    + rb.getString("CS103.Graph.Birds.text") + numOfBirds + " "
                    + rb.getString("CS103.Graph.CV.text") + cv + " "
                    + rb.getString("CS103.Graph.Std.Dev.text") + stdDev;
        }

        Plot plot = barchart.getChart().getPlot();
        ((XYPlot) plot).getDomainAxis().setLabel(info);

        return barchart;
    }

    public int calcStartWeight(int i) {
        int start = getMinGraphIndex();
        double result = (((i+1) - start) < 0) ? 0:(((i+1) - start) + 1) * getUnit();
        return (int) result;
    }

    public double getUnit() {
        if (unitFlag == GRAM) {
            return GRAM_UNIT;
        } else {
            return LIBRA_UNIT;
        }
    }
}
