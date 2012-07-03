/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chs103.physical;

import cs103.gui.CS103App;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;

/**
 * Title: PrintManager <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class PrintManager {

    public PrintManager() {
    }

    public static void printAll(ChartPanel[] chartPanel) {
        // Obtain a PrinterJob 
        PrinterJob printJob = PrinterJob.getPrinterJob();
        // Create a landscape page format 
        PageFormat format = printJob.defaultPage();
        format.setOrientation(PageFormat.LANDSCAPE);

        try {
            for (int i = 0; i < chartPanel.length; i++) {
                printJob.setPrintable(chartPanel[i], format);
                printJob.print();
            }
        } catch (PrinterException ex) {
            Logger.getLogger(CS103App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printOne(ChartPanel[] chartPanel, int graphIndex) {
        // Obtain a PrinterJob 
        PrinterJob printJob = PrinterJob.getPrinterJob();
        // Create a landscape page format 
        PageFormat format = printJob.defaultPage();
        format.setOrientation(PageFormat.LANDSCAPE);

        try {
            printJob.setPrintable(chartPanel[graphIndex], format);
            if (printJob.printDialog() == true) {
                printJob.print();
            }
        } catch (PrinterException ex) {
            Logger.getLogger(CS103App.class.getName()).log(Level.SEVERE, null, ex);
            // error printing message
        }

    }
}
