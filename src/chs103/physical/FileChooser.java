/*
 * FileChooser.java
 *
 * Created on August 11, 2008, 3:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.physical;

import chs103.bl.Batch;
import chs103.bl.RecordVector;
import chs103.exception.ChecksumException;
import chs103.exception.ReadFileExeption;
import cs103.gui.CS103App;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Title: FileChooser <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * 
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class FileChooser extends JFileChooser {
    /**
     * Creates a new instance of FileChooser
     */
    public FileChooser(FilterType filterType, int dlgType, int mode) {
        super();
        super.setDialogType(dlgType);
        super.setFileSelectionMode(mode);
        super.setCurrentDirectory (new File ("."));
        setFilter(filterType);
    }
    
    /**
     * Use a JFileChooser in Open mode to select files
     * to open. Use a filter for FileFilter subclass to select
     * for "*.txt" files. If a file is selected then read the
     * file and place the data into the array of batchs.
     */
    public static Batch[] openFile (JFrame mainFrame) throws ReadFileExeption {

        FileChooser fileChooser = new FileChooser ( FilterType.CHS,
                                                    JFileChooser.OPEN_DIALOG,
                                                    JFileChooser.FILES_ONLY );
        // Now open chooser
        int result = fileChooser.showOpenDialog (mainFrame);

        if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        } 
        else if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile ();
            return ChsFileManager.readFromFile(file.getAbsolutePath());            
        }
          
        return null;
    }
    /**
     * Use a JFileChooser in Save mode to select files
     * to open. Use a filter for FileFilter subclass to select
     * for "*.chs" files. If a file is selected, then write the
     * batchs data from array of batchs into it.
     */
    public static int saveFile (JFrame mainFrame,Batch[] batchs) {

        FileChooser fileChooser = new FileChooser ( FilterType.CHS,
                                                    JFileChooser.SAVE_DIALOG,
                                                    JFileChooser.FILES_ONLY );
        // Now open chooser
        // Show dialog; this method does not return until dialog is closed
        int result = fileChooser.showSaveDialog(mainFrame);

        if (result == JFileChooser.CANCEL_OPTION) {
            return JFileChooser.CANCEL_OPTION;
        } 
        else if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // 
            if (file.exists ()) {
                int response = JOptionPane.showConfirmDialog (null,
                   "Overwrite existing file?","Confirm Overwrite",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                 if (response == JOptionPane.CANCEL_OPTION) return JFileChooser.CANCEL_OPTION;
            }
            // write batchs data to file
            boolean success = ChsFileManager.writeToFile (file.getAbsolutePath(), batchs);
            if(success) {
                return JFileChooser.APPROVE_OPTION;
            } else {
                return JFileChooser.ERROR_OPTION;
            }
        }
        else {
            return JFileChooser.ERROR_OPTION;
        }
    }
    /**
     * Use a JFileChooser in Save mode to select files
     * to open. Use a filter for FileFilter subclass to select
     * for "*.chs" files. If a file is selected, then write the
     * batchs data from array of batchs into it.
     */
    public static int exportToExcel(JFrame mainFrame,Batch[] batchs) {

        FileChooser fileChooser = new FileChooser ( FilterType.CSV,
                                                    JFileChooser.SAVE_DIALOG,
                                                    JFileChooser.FILES_ONLY );
        fileChooser.setDialogTitle ("Excel export");
        // Now open chooser
        // Show dialog; this method does not return until dialog is closed
        int result = fileChooser.showSaveDialog(mainFrame);

        if (result == JFileChooser.CANCEL_OPTION) {
            return JFileChooser.CANCEL_OPTION;
        } 
        else if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile ();
            if (file.exists ()) {
                int response = JOptionPane.showConfirmDialog (null,
                   "Overwrite existing file?","Confirm Overwrite",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                 if (response == JOptionPane.CANCEL_OPTION) return JFileChooser.CANCEL_OPTION;
            }
            // write batchs data to file
            boolean success = ExlFileManager.writeToFile(file.getAbsolutePath(),batchs);
            if(success) {
                return JFileChooser.APPROVE_OPTION;
            } else {
                return JFileChooser.ERROR_OPTION;
            }
        }
        else {
            return JFileChooser.ERROR_OPTION;
        }
    }
    /**
     * Use a JFileChooser in Save mode to select files
     * to open. Use a filter for FileFilter subclass to select
     * for "*.hex" files. If a file is selected, then read the
     * file and place the data records into RecordVector object.
     */
    public static RecordVector loadProgramFile(JFrame mainFrame) throws ChecksumException, ReadFileExeption {
        
        FileChooser fileChooser = new FileChooser ( FilterType.HEX,
                                                    JFileChooser.OPEN_DIALOG,
                                                    JFileChooser.FILES_ONLY );
        fileChooser.setDialogTitle("Load program");
        // Now open chooser
        // Show dialog; this method does not return until dialog is closed
        // Now open chooser
        int result = fileChooser.showOpenDialog (mainFrame);

        if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        } 
        else if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                HexFileManager hexFileManage = new HexFileManager(file);
                return hexFileManage.parseFile();
        }
        return null;
    }
    /**
     * Set file filter type
     * @param filterType the filter type 
     */
    public void setFilter(FilterType filterType) {
        switch(filterType) {
            default:
            case TXT:
                setFileFilter(new TxtFilter());
                break;
            case CSV:
                setFileFilter(new CsvFilter());
                break;
            case CHS:
                setFileFilter(new ChsFilter());
                break;
            case HEX:
                setFileFilter(new HexFilter());
                break;
        }
    }

    @Override
    public File getSelectedFile() {
        File file = super.getSelectedFile();
        if(file == null)
            return null;
        if (getFileExt(file) == null){
            if(getFileFilter() instanceof CsvFilter) {
                return (new File (file.getPath()+ "." + (new CsvFilter()).getExtension())); 
            }
            if(getFileFilter() instanceof ChsFilter) {
                return (new File (file.getPath()+ "." + (new ChsFilter()).getExtension()));
            }
        }
        return file;
    }
    /**
     * Get file extension of selected file if it exist
     * @param file          the selected file
     * @return extension    return extension if exist overwise
     */
    private String getFileExt(File file) {
            String filename = file.getName().toLowerCase();
            String extension = (filename.lastIndexOf(".")==-1) ? null
                   : filename.substring(filename.lastIndexOf(".")+1,filename.length());
            
            return  extension;
    }
    /**
     * Inner class 
     */
    class ChsFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept (File f) {
            return f.getName ().toLowerCase ().endsWith (".chs")
                  || f.isDirectory ();
        }

        @Override
        public String getDescription () {
            return "Chick scale files (*.chs)";
        }

        public String getExtension() {
            return "chs";
        }
    } // class ChsFilter
    /**
     * Inner class 
     */
    class CsvFilter extends javax.swing.filechooser.FileFilter {
        
        @Override
        public boolean accept (File f) {
            return f.getName ().toLowerCase ().endsWith (".csv")
                  || f.isDirectory ();
        }
        @Override
        public String getDescription () {
            return "Csv files (*.csv)";
        }
        
        public String getExtension() {
            return "csv";
        }
    } // class CsvFilter
    /**
     * Inner class 
     */
    class TxtFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept (File f) {
            return f.getName ().toLowerCase ().endsWith (".txt")
                 || f.isDirectory ();
        }

        @Override
        public String getDescription () {
            return "Text files (*.txt)";
        }

        public String getExtension() {
            return ".txt";
        }
    } // class TxtFilter
    /**
     * Inner class 
     */
    class HexFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept (File f) {
            return f.getName ().toLowerCase ().endsWith (".hex")
                 || f.isDirectory ();
        }

        @Override
        public String getDescription () {
            return "Hex files (*.hex)";
        }

        public String getExtension() {
            return ".hex";
        }
    } // class TxtFilter    
}
