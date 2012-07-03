/*
 * FileManager.java
 *
 * Created on July 22, 2008, 11:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.physical;

import chs103.bl.DateTime;
import chs103.bl.Batch;
import chs103.exception.ReadFileExeption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Title: FileManager <br>
 * Decription: Read batch data from file <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
  * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class ChsFileManager {
    private static final String COLUMN_TITLES[] = { "Batch Number           ",
                                                    "Birds                  ",
                                                    "Birds Percent          ",
                                                    "Average Weight         ",
                                                    "Average Weight Rounded ",
                                                    "Standart Dev.          ",
                                                    "Min.GraphIndex         ",
                                                    "Minimum Graph          ",
                                                    "C. V.                  ",
                                                    "Start Date             ",
                                                    "End Date               ",
                                                    "Histogramma            ",
                                                    "History Weights        "
                                                  };
    public static final int BATCH_NUMBER     = 0;
    public static final int BIRDS            = 1;
    public static final int BIRDS_PERCENT    = 2;
    public static final int AVG_WEIGHT       = 3;
    public static final int AVG_WEIGHT_ROUND = 4;
    public static final int STD_DEV          = 5;
    public static final int MIN_GRAPH_INDEX  = 6;
    public static final int MIN_GRAPH        = 7;
    public static final int CV               = 8;
    public static final int START_DATE       = 9;
    public static final int END_DATE         = 10;
    public static final int HISTOGRAMMA      = 11;
    public static final int HISTORY_WEIGTHS  = 12;    
    
    /**
     * Creates a new instance of FileManager
     */
    public ChsFileManager() {
    }
    /**
     * Create file input stream of giving filename
     * @param filename  the file name 
     * @return fis      the file input stream
     */
    private static FileInputStream loadFileToRead(String filename) {
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
        }
        return fis;
    }
    /**
     * Create PrintWriter of giving filename
     * @param filename  the file name 
     * @return fis      the file input stream
     */
    private static PrintWriter loadFileToWrite(String filename) throws IOException {
        PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (filename)));
        return pw;
    }
    /**
     * Read batch data from "*.CHS" file 
     * @param  filename  the file name
     * @return batchs    the array of batch that was read from file
     */
    public static Batch[] readFromFile(String filename) throws ReadFileExeption {
        FileInputStream fis = loadFileToRead(filename);

        Batch batchs[] = new Batch[9];        
        StreamTokenizer tokenizer= null;

        if(null != fis) {

            Batch batch = new Batch();
            tokenizer = new StreamTokenizer(fis);
            tokenizer.wordChars(' ',' ');
            String sval  = "";
            Object value = null;
            String key   = new String();

            while(tokenizer.ttype != StreamTokenizer.TT_EOF) {
                try {
                    if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                        sval = tokenizer.sval;
                        int index = findDigitIndex(sval, 0);
                        if (index != -1) {
                            key = sval.substring(0, index - 1);
                        } else {
                            key = sval;
                        }
                        key = key.trim();
                        if (key.equals((COLUMN_TITLES[BATCH_NUMBER]).trim())) {
                            value = sval.substring(index);
                            batch.setBatchNumber(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[BIRDS]).trim())) {
                            value = sval.substring(index);
                            batch.setNumOfBirds(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[BIRDS_PERCENT]).trim())) {
                            value = sval.substring(index);
                            batch.setNumOfBirdsPercent(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[AVG_WEIGHT]).trim())) {
                            value = sval.substring(index);
                            batch.setAvgWeight(Double.parseDouble((String) value));
                        } else if (key.equals((COLUMN_TITLES[AVG_WEIGHT_ROUND]).trim())) {
                            value = sval.substring(index);
                            batch.setAvgWeightRounded(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[STD_DEV]).trim())) {
                            value = sval.substring(index);
                            batch.setStdDiv(Double.parseDouble((String) value));
                        } else if (key.equals((COLUMN_TITLES[MIN_GRAPH_INDEX]).trim())) {
                            value = sval.substring(index);
                            batch.setMinGraphIndex(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[MIN_GRAPH]).trim())) {
                            value = sval.substring(index);
                            batch.setMinGraph(Double.parseDouble((String) value));
                        } else if (key.equals((COLUMN_TITLES[CV]).trim())) {
                            value = sval.substring(index);
                            batch.setCv(Integer.parseInt((String) value));
                        } else if (key.equals((COLUMN_TITLES[START_DATE]).trim())) {
                            value = sval.substring(index);
                            batch.setStartDate(DateTime.parse((String) value));
                        } else if (key.equals((COLUMN_TITLES[END_DATE]).trim())) {
                            value = sval.substring(index);
                            batch.setEndDate(DateTime.parse((String) value));
                        } else if (key.equals((COLUMN_TITLES[HISTOGRAMMA]).trim())) {
                            value = sval.substring(index);
                            batch.setHistogramma(stringToIntArray((String) value));
                        } else if(key.equals(COLUMN_TITLES[HISTORY_WEIGTHS].trim())) {
                            value = sval.substring(index);
                            batch.setHistoryWeight(stringToIntArray((String) value));
                            batchs[batch.getBatchNumber() - 1] = batch;
                            batch = new Batch();
                        }
                    }
                    tokenizer.nextToken();
                } catch (IOException ex) {
                    throw new ReadFileExeption("Error while read chick scale file");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    throw new ReadFileExeption("Error while read chick scale file");
                }
            }
        }
        return batchs;
    }    
    /**
     * Write batch data to file
     * @param filename  the file name
     * @param batchs    the array of batch
     * @return          if write tot file was successful return true otherwise false;
     */
    public static boolean writeToFile(String filename,Batch[] batchs) {
        PrintWriter pw;
        try {
            pw = loadFileToWrite(filename);
        } catch (IOException ex) {
            return false;
        }
        
        for(int i = 0; i < batchs.length; i++) {
            pw.print(COLUMN_TITLES[BATCH_NUMBER]);
            pw.print(batchs[i].getBatchNumber());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[BIRDS]);
            pw.print(batchs[i].getNumOfBirds());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[BIRDS_PERCENT]);
            pw.print(batchs[i].getNumOfBirdsPercent());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[AVG_WEIGHT]);
            pw.print(batchs[i].getAvgWeight());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[AVG_WEIGHT_ROUND]);            
            pw.print(batchs[i].getAvgWeightRounded());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[STD_DEV]);
            pw.print(batchs[i].getStdDev());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[MIN_GRAPH_INDEX]);
            pw.print(batchs[i].getMinGraphIndex());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[MIN_GRAPH]);
            pw.print(batchs[i].getMinGraph());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[CV]);
            pw.print(batchs[i].getCv());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[START_DATE]);
            pw.print(batchs[i].getStartDate());
            pw.print("\r\n");
            pw.print(COLUMN_TITLES[END_DATE]);
            pw.print(batchs[i].getEndDate());
            pw.print("\r\n");

            pw.print(COLUMN_TITLES[HISTOGRAMMA]);
            int[] hist = batchs[i].getHistogramma();
            for(int j = 0; j < hist.length; j++) {
                pw.print(hist[j]);
                pw.print(" ");
            }
            pw.print("\r\n");
            
            int[] weitghts = batchs[i].getHistoryWeight();
            pw.print(COLUMN_TITLES[HISTORY_WEIGTHS]);
            for(int j = 0; j < weitghts.length; j++) {
                pw.print(weitghts[j]);
                pw.print(" ");
            }
            pw.print("\r\n");
            pw.print("\r\n");
            pw.flush ();
        }
        pw.close ();
        // renaming file if user wrote file name without extension
        return true;
    }    
    /**
     * Find index of first digit in string
     * @param string    the string 
     * @param index     the index
     * @return index    if digit was found in string or -1 otherwise
     */
    static int findDigitIndex(String string,int index) {

        if(index < string.length()) {
            if(Character.isDigit(string.charAt(index))) {
                    return index;
            }
            else {
                index++;
                return findDigitIndex(string,index);
            }
        }
        else {
            return -1;
        }
    }
    /**
     * Returns array of val that string contains separated by spaces
     * @param string    the string of vals
     * @return arr      the array of vals
     */
    static int[] stringToIntArray(String string) {
        
        if(string.length() == 0) 
            return null;
        
        String[] result = string.split(" ");
        
        int[] arr =  new int[result.length];
        
        for(int i = 0; i < result.length; i++) {
            arr[i] = Integer.valueOf(result[i]);
        }
        
        return arr;
    }    
}
