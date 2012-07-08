/*
 * CS103App.java
 *
 * Created on July 20, 2008, 9:49 AM
 */
package cs103.gui;

import chs103.bl.Batch;
import chs103.bl.DateTime;
import chs103.bl.ErrorCode;
import chs103.bl.Record;
import chs103.bl.RecordVector;
import chs103.bl.Unit;
import chs103.bl.Version;
import chs103.exception.*;
import chs103.graph.XYBarChart;
import chs103.network.Commands;
import chs103.network.Creator;
import chs103.network.NetworkMode;
import chs103.network.SerialPortControl;
import chs103.physical.FileChooser;
import chs103.physical.PrintManager;
import chs103.physical.PropertyFileManager;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartPanel;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 * Title: CS103App <br> Description: <br> Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class CS103App extends javax.swing.JFrame implements Runnable, PropertyChangeListener {

    private SerialPortControl serialPortControl = null;
    private Task task;
    private ProgressMonitor progressMonitor;
    private Creator creator;
    private ChartPanel chartPanel[];
    private Version currVersion;
    private Version prevVersion;
    private DateTime dateTime;
    private byte unit;
    private ErrorCode errorCode;
    private Batch batchs[];
    private Color buttonsColor[];
    private RecordVector recordItems;
    private int graphIndex;
    private String comPort;
    private boolean connected;
    private boolean stopRunning;
    private boolean startLoader;
    private static final int NUM_OF_BATCHS = 9;
    private static final int ALL_GRAPHS = 9;
    private static ResourceBundle resourceMap = ResourceBundle.getBundle("resource/CS103App");
    public static final String FILENAME = "config/program.properties";
    public static final HashMap<String, Locale> languageMap = new HashMap<String, Locale>();
    private Locale currLocale;
    private HashMap localMenuMap = new HashMap();
    static {
        languageMap.put("English", new Locale("en", "US"));
        languageMap.put("Hebrew", new Locale("iw", "IL"));
    }

    enum WeightUnit {

        pound, gram
    }
    enum LoaderStates {

        IDLE,
        START, // after varifiyng version
        WAIT_SATRT_ACK, //
        WAIT_LOADER_VERSION, //
        WAIT_CONFIG_ACK, //
        SEND_FILE_CONFIG, //
        WRITE_PROGRAM, //
        WAIT_FILE_END_ACK       //
    }

    private LoaderStates loaderState = LoaderStates.IDLE;

    enum ConnectionStatus {

        CONNECTED, NOT_CONNECTED
    }
    enum BatchInCacheStates {

        EMPTY, FROM_FILE, FROM_CHS_UNSAVED, FROM_CHS_SAVED
    }

    private BatchInCacheStates batchInCache = BatchInCacheStates.EMPTY;

    /**
     * Creates new form CS103 Application.
     */
    public CS103App() throws ConfigurationFailedException {
        initComponents();
        Windows.setWindowsLAF(this);
        Windows.centerOnScreen(this);
        setCurrnetLocale(PropertyFileManager.getProperty(FILENAME, "language"));
        getJMenuBar().add(createShortLocaleMenu());
        changeLanguage();
        creator = new Creator();
        dateTime = new DateTime();
        currVersion = new Version();
        errorCode = new ErrorCode();
        batchs = new Batch[NUM_OF_BATCHS];
        buttonsColor = new Color[NUM_OF_BATCHS];
        recordItems = new RecordVector();
        comPort = null;
        connected = false;
        stopRunning = false;
        startLoader = false;
        graphIndex = ALL_GRAPHS;
        disableButtons();
        groupHistogramButtons();
        setDate(dateTime);

        // start main thread
        new Thread(this).start();

        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("cs103_win_icon.gif"));
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgComPortChooser = new javax.swing.JDialog();
        pnlComPorts = new javax.swing.JPanel();
        rdbComPort1 = new javax.swing.JRadioButton();
        rdbComPort2 = new javax.swing.JRadioButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnGrpHistogram = new javax.swing.ButtonGroup();
        pnlCommandBar = new javax.swing.JPanel();
        btnReadData = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnExcelExport = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        pnlHistograms = new javax.swing.JPanel();
        togBtnHist1 = new javax.swing.JToggleButton();
        togBtnHist2 = new javax.swing.JToggleButton();
        togBtnHist3 = new javax.swing.JToggleButton();
        togBtnHist4 = new javax.swing.JToggleButton();
        togBtnHist5 = new javax.swing.JToggleButton();
        togBtnHist6 = new javax.swing.JToggleButton();
        togBtnHist7 = new javax.swing.JToggleButton();
        togBtnHist8 = new javax.swing.JToggleButton();
        togBtnHist9 = new javax.swing.JToggleButton();
        togBtnShowAll = new javax.swing.JToggleButton();
        pnlDisplayGraphs = new javax.swing.JPanel();
        pnlStatus = new javax.swing.JPanel();
        lblVersion = new javax.swing.JLabel();
        lblStatusTitle = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        progressBar = new javax.swing.JProgressBar();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        lblDateTime = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        mnuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu(resourceMap.getString("CS103.Menu.File.text"));
        mnuItemExit = new javax.swing.JMenuItem();
        mnuTool = new javax.swing.JMenu();
        mnuItemSetting = new javax.swing.JMenuItem();
        mnuItemSetupDriver = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuItemPCManual = new javax.swing.JMenuItem();
        mnuItemCS103Manual = new javax.swing.JMenuItem();
        mnuItemAbout = new javax.swing.JMenuItem();
        mnuLanguage = new javax.swing.JMenu();

        dlgComPortChooser.setTitle("Comport Dialog Chooser");

        pnlComPorts.setBorder(javax.swing.BorderFactory.createTitledBorder("Choose Port"));

        rdbComPort1.setText("COM1");
        rdbComPort1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rdbComPort1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        rdbComPort2.setText("COM2");
        rdbComPort2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rdbComPort2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout pnlComPortsLayout = new javax.swing.GroupLayout(pnlComPorts);
        pnlComPorts.setLayout(pnlComPortsLayout);
        pnlComPortsLayout.setHorizontalGroup(
            pnlComPortsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComPortsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlComPortsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbComPort2)
                    .addComponent(rdbComPort1))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        pnlComPortsLayout.setVerticalGroup(
            pnlComPortsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComPortsLayout.createSequentialGroup()
                .addComponent(rdbComPort1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(rdbComPort2))
        );

        btnOk.setText("Ok");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout dlgComPortChooserLayout = new javax.swing.GroupLayout(dlgComPortChooser.getContentPane());
        dlgComPortChooser.getContentPane().setLayout(dlgComPortChooserLayout);
        dlgComPortChooserLayout.setHorizontalGroup(
            dlgComPortChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgComPortChooserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgComPortChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlComPorts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dlgComPortChooserLayout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dlgComPortChooserLayout.setVerticalGroup(
            dlgComPortChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgComPortChooserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlComPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgComPortChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("resource/CS103App"); // NOI18N
        setTitle(bundle.getString("CS103.title")); // NOI18N
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlCommandBar.setLayout(new java.awt.GridLayout(1, 0));

        btnReadData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/getall.GIF"))); // NOI18N
        btnReadData.setText(bundle.getString("CS103.Button.ReadData.text")); // NOI18N
        btnReadData.setToolTipText("Send All\n");
        btnReadData.setAutoscrolls(true);
        btnReadData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReadData.setOpaque(false);
        btnReadData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadDataActionPerformed(evt);
            }
        });
        pnlCommandBar.add(btnReadData);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/openfolder.GIF"))); // NOI18N
        btnOpen.setText(bundle.getString("CS103.Button.Open.text")); // NOI18N
        btnOpen.setToolTipText("Load form file\n");
        btnOpen.setAutoscrolls(true);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setOpaque(false);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        pnlCommandBar.add(btnOpen);

        btnExcelExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/excel.gif"))); // NOI18N
        btnExcelExport.setText(bundle.getString("CS103.Button.ExcelExport.text")); // NOI18N
        btnExcelExport.setToolTipText("Export excel file\n");
        btnExcelExport.setAutoscrolls(true);
        btnExcelExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcelExport.setOpaque(false);
        btnExcelExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcelExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelExportActionPerformed(evt);
            }
        });
        pnlCommandBar.add(btnExcelExport);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/save.gif"))); // NOI18N
        btnSave.setText(bundle.getString("CS103.Button.Save.text")); // NOI18N
        btnSave.setToolTipText("Save");
        btnSave.setAutoscrolls(true);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setOpaque(false);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnlCommandBar.add(btnSave);

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/print.gif"))); // NOI18N
        btnPrint.setText(bundle.getString("CS103.Button.Print.text")); // NOI18N
        btnPrint.setToolTipText("Print");
        btnPrint.setAutoscrolls(true);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setOpaque(false);
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        pnlCommandBar.add(btnPrint);

        togBtnHist1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist1.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist1.setText("1");
        togBtnHist1.setBorder(null);
        togBtnHist1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist1ActionPerformed(evt);
            }
        });

        togBtnHist2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist2.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist2.setText("2");
        togBtnHist2.setBorder(null);
        togBtnHist2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist2ActionPerformed(evt);
            }
        });

        togBtnHist3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist3.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist3.setText("3");
        togBtnHist3.setBorder(null);
        togBtnHist3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist3ActionPerformed(evt);
            }
        });

        togBtnHist4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist4.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist4.setText("4");
        togBtnHist4.setBorder(null);
        togBtnHist4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist4ActionPerformed(evt);
            }
        });

        togBtnHist5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist5.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist5.setText("5");
        togBtnHist5.setBorder(null);
        togBtnHist5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist5ActionPerformed(evt);
            }
        });

        togBtnHist6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist6.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist6.setText("6");
        togBtnHist6.setBorder(null);
        togBtnHist6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist6ActionPerformed(evt);
            }
        });

        togBtnHist7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist7.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist7.setText("7");
        togBtnHist7.setBorder(null);
        togBtnHist7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist7ActionPerformed(evt);
            }
        });

        togBtnHist8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist8.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist8.setText("8");
        togBtnHist8.setBorder(null);
        togBtnHist8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist8ActionPerformed(evt);
            }
        });

        togBtnHist9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnHist9.setForeground(new java.awt.Color(255, 255, 255));
        togBtnHist9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/hist.gif"))); // NOI18N
        togBtnHist9.setText("9");
        togBtnHist9.setBorder(null);
        togBtnHist9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnHist9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnHist9ActionPerformed(evt);
            }
        });

        togBtnShowAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        togBtnShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cs103/gui/histall.gif"))); // NOI18N
        togBtnShowAll.setText("ALL");
        togBtnShowAll.setBorder(null);
        togBtnShowAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        togBtnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                togBtnShowAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHistogramsLayout = new javax.swing.GroupLayout(pnlHistograms);
        pnlHistograms.setLayout(pnlHistogramsLayout);
        pnlHistogramsLayout.setHorizontalGroup(
            pnlHistogramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistogramsLayout.createSequentialGroup()
                .addComponent(togBtnHist1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnHist9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togBtnShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlHistogramsLayout.setVerticalGroup(
            pnlHistogramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistogramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(togBtnHist1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnHist9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(togBtnShowAll, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlDisplayGraphs.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout pnlDisplayGraphsLayout = new javax.swing.GroupLayout(pnlDisplayGraphs);
        pnlDisplayGraphs.setLayout(pnlDisplayGraphsLayout);
        pnlDisplayGraphsLayout.setHorizontalGroup(
            pnlDisplayGraphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 959, Short.MAX_VALUE)
        );
        pnlDisplayGraphsLayout.setVerticalGroup(
            pnlDisplayGraphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        pnlStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlStatus.setPreferredSize(new java.awt.Dimension(710, 25));
        pnlStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersion.setAlignmentX(1.5F);
        lblVersion.setAlignmentY(1.5F);
        pnlStatus.add(lblVersion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 2, 220, 20));

        lblStatusTitle.setText(bundle.getString("CS103.Label.Status.text")); // NOI18N
        lblStatusTitle.setAlignmentX(1.5F);
        lblStatusTitle.setAlignmentY(1.5F);
        pnlStatus.add(lblStatusTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 2, 50, 20));

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStatus.setAlignmentX(1.5F);
        lblStatus.setAlignmentY(1.5F);
        pnlStatus.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 2, 110, 20));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setAlignmentX(1.5F);
        jSeparator2.setAlignmentY(1.5F);
        pnlStatus.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 2, 20, 20));

        progressBar.setPreferredSize(new java.awt.Dimension(145, 19));
        pnlStatus.add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 3, 120, -1));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setAlignmentX(1.5F);
        jSeparator4.setAlignmentY(1.5F);
        pnlStatus.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 2, -1, 20));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setAlignmentX(1.5F);
        jSeparator5.setAlignmentY(1.5F);
        pnlStatus.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 2, -1, 20));

        lblDateTime.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDateTime.setText("12/09/2008");
        lblDateTime.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jScrollPane2.setWheelScrollingEnabled(false);

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {bundle.getString("CS103.Table.row.NumberOfBirds.text"), null, null, null, null, null, null, null, null, null},
                {bundle.getString("CS103.Table.row.Average.text"), null, null, null, null, null, null, null, null, null},
                {bundle.getString("CS103.Table.row.STD.text"), null, null, null, null, null, null, null, null, null},
                {bundle.getString("CS103.Table.row.CV.text"), null, null, null, null, null, null, null, null, null},
                {bundle.getString("CS103.Table.row.Percent.text"), null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                bundle.getString("CS103.Table.col.batch.text"),
                bundle.getString("CS103.Table.col.batch1.text"),
                bundle.getString("CS103.Table.col.batch2.text"),
                bundle.getString("CS103.Table.col.batch3.text"),
                bundle.getString("CS103.Table.col.batch4.text"),
                bundle.getString("CS103.Table.col.batch5.text"),
                bundle.getString("CS103.Table.col.batch6.text"),
                bundle.getString("CS103.Table.col.batch7.text"),
                bundle.getString("CS103.Table.col.batch8.text"),
                bundle.getString("CS103.Table.col.batch9.text")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblTable.setAutoscrolls(false);
        tblTable.setCellSelectionEnabled(true);
        tblTable.setGridColor(new java.awt.Color(0, 51, 255));
        tblTable.setRequestFocusEnabled(false);
        tblTable.getTableHeader().setResizingAllowed(false);
        tblTable.getTableHeader().setReorderingAllowed(false);
        TableCellRenderer renderer = new CellRenderer();
        tblTable.setDefaultRenderer(Object.class, renderer);
        jScrollPane2.setViewportView(tblTable);
        tblTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        mnuFile.setText(bundle.getString("CS103.Menu.File.text")); // NOI18N

        mnuItemExit.setText(bundle.getString("CS103.Menu.Exit.text")); // NOI18N
        mnuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemExit);

        mnuBar.add(mnuFile);

        mnuTool.setText(bundle.getString("CS103.Menu.Tool.text")); // NOI18N

        mnuItemSetting.setText(bundle.getString("CS103.Menu.Setting.text")); // NOI18N
        mnuItemSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSettingActionPerformed(evt);
            }
        });
        mnuTool.add(mnuItemSetting);

        mnuItemSetupDriver.setText(bundle.getString("CS103.Menu.Setup.Driver.text")); // NOI18N
        mnuItemSetupDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSetupDriverActionPerformed(evt);
            }
        });
        mnuTool.add(mnuItemSetupDriver);

        mnuBar.add(mnuTool);

        mnuHelp.setText(bundle.getString("CS103.Menu.Help.text")); // NOI18N
        mnuHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpActionPerformed(evt);
            }
        });

        mnuItemPCManual.setText(bundle.getString("CS103.Menu.PCManual.text")); // NOI18N
        mnuItemPCManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPCManualActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuItemPCManual);

        mnuItemCS103Manual.setText(bundle.getString("CS103.Menu.CS103Manual.text")); // NOI18N
        mnuItemCS103Manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCS103ManualActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuItemCS103Manual);

        mnuItemAbout.setText(bundle.getString("CS103.Menu.About.text")); // NOI18N
        mnuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuItemAbout);

        mnuBar.add(mnuHelp);

        mnuLanguage.setText(bundle.getString("CS103.Menu.Language.text")); // NOI18N
        mnuBar.add(mnuLanguage);

        setJMenuBar(mnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCommandBar, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlHistograms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(347, Short.MAX_VALUE))
            .addComponent(pnlStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
            .addComponent(pnlDisplayGraphs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlCommandBar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHistograms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDisplayGraphs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Set current locale .
     *
     * @param language the language.
     */
    private void setCurrnetLocale(final String language) throws ConfigurationFailedException {
        if (language == null || language.equals("")) {
            throw new ConfigurationFailedException();
        }
        currLocale = languageMap.get(language);
        Locale.setDefault(currLocale);
        PropertyFileManager.setProperty(FILENAME, "", "language", language);
    }

    private JMenu createShortLocaleMenu() {
        final Locale defaultLocale = currLocale;
        ActionListener listener = new LocaleActionListener();
        ButtonGroup group = new ButtonGroup();
        final Set<Entry<String, Locale>> set = languageMap.entrySet();
        for (Entry e : set) {
            JRadioButtonMenuItem rbItem = new JRadioButtonMenuItem((String) e.getKey());
            if (e.getValue().equals(defaultLocale)) {
                rbItem.setSelected(true);
            }
            localMenuMap.put(rbItem, e.getValue());
            rbItem.addActionListener(listener);
            group.add(rbItem);
            mnuLanguage.add(rbItem);
        }
        return mnuLanguage;


    }

    private class LocaleActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JRadioButtonMenuItem menu = (JRadioButtonMenuItem) e.getSource();
            Locale locale = (Locale) localMenuMap.get(menu);
            if (locale != null) {
                currLocale = locale;
                Locale.setDefault(currLocale);
                getWindow().setLocale(currLocale);
                changeLanguage();
                changeGraphLanguage();
                SwingUtilities.updateComponentTreeUI(getWindow());
            }
        }
    }

    private void changeLanguage() {

        PropertyFileManager.setProperty(FILENAME, "Language", "language", getLanguageString(currLocale));
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        setTitle(rb.getString("CS103.title"));
        btnCancel.setText(rb.getString("CS103.Button.Cancel.text"));
        btnExcelExport.setText(rb.getString("CS103.Button.ExcelExport.text"));
        btnOk.setText(rb.getString("CS103.Button.Ok.text"));
        btnOpen.setText(rb.getString("CS103.Button.Open.text"));
        btnPrint.setText(rb.getString("CS103.Button.Print.text"));
        btnReadData.setText(rb.getString("CS103.Button.ReadData.text"));
        btnSave.setText(rb.getString("CS103.Button.Save.text"));
        lblStatusTitle.setText(rb.getString("CS103.Label.Status.text"));
        if (connected) {
            setConnectionStatus(ConnectionStatus.CONNECTED);
        } else {
            setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
        }
        mnuFile.setText(rb.getString("CS103.Menu.File.text"));
        mnuItemExit.setText(rb.getString("CS103.Menu.Exit.text"));
        mnuTool.setText(rb.getString("CS103.Menu.Tool.text"));
        mnuItemSetting.setText(rb.getString("CS103.Menu.Setting.text"));
        mnuHelp.setText(rb.getString("CS103.Menu.Help.text"));
        mnuItemAbout.setText(rb.getString("CS103.Menu.About.text"));
        mnuItemPCManual.setText(rb.getString("CS103.Menu.PCManual.text"));
        mnuItemCS103Manual.setText(rb.getString("CS103.Menu.CS103Manual.text"));
        mnuItemSetupDriver.setText(rb.getString("CS103.Menu.Setup.Driver.text"));
        togBtnHist1.setText(rb.getString("CS103.Button.Hist1.text"));
        togBtnHist2.setText(rb.getString("CS103.Button.Hist2.text"));
        togBtnHist3.setText(rb.getString("CS103.Button.Hist3.text"));
        togBtnHist4.setText(rb.getString("CS103.Button.Hist4.text"));
        togBtnHist5.setText(rb.getString("CS103.Button.Hist5.text"));
        togBtnHist6.setText(rb.getString("CS103.Button.Hist6.text"));
        togBtnHist7.setText(rb.getString("CS103.Button.Hist7.text"));
        togBtnHist8.setText(rb.getString("CS103.Button.Hist8.text"));
        togBtnHist9.setText(rb.getString("CS103.Button.Hist9.text"));
        togBtnShowAll.setText(rb.getString("CS103.Button.ShowAll.text"));
        setVersionInTitle();
        changeTableLanguage(rb);
    }

    private void changeTableLanguage(ResourceBundle rb) {
        changeTableColName(tblTable, 0, rb.getString("CS103.Table.col.batch.text"));
        changeTableColName(tblTable, 1, rb.getString("CS103.Table.col.batch1.text"));
        changeTableColName(tblTable, 2, rb.getString("CS103.Table.col.batch2.text"));
        changeTableColName(tblTable, 3, rb.getString("CS103.Table.col.batch3.text"));
        changeTableColName(tblTable, 4, rb.getString("CS103.Table.col.batch4.text"));
        changeTableColName(tblTable, 5, rb.getString("CS103.Table.col.batch5.text"));
        changeTableColName(tblTable, 6, rb.getString("CS103.Table.col.batch6.text"));
        changeTableColName(tblTable, 7, rb.getString("CS103.Table.col.batch7.text"));
        changeTableColName(tblTable, 8, rb.getString("CS103.Table.col.batch8.text"));
        changeTableColName(tblTable, 9, rb.getString("CS103.Table.col.batch9.text"));
        changeTableCellData(tblTable, 0, 0, rb.getString("CS103.Table.row.NumberOfBirds.text"));
        changeTableCellData(tblTable, 1, 0, rb.getString("CS103.Table.row.Average.text"));
        changeTableCellData(tblTable, 2, 0, rb.getString("CS103.Table.row.STD.text"));
        changeTableCellData(tblTable, 3, 0, rb.getString("CS103.Table.row.CV.text"));
        changeTableCellData(tblTable, 4, 0, rb.getString("CS103.Table.row.Percent.text"));
    }

    public void changeTableColName(JTable table, int colIndex, String colName) {
        table.getColumnModel().getColumn(colIndex).setHeaderValue(colName);
    }

    public void changeTableCellData(JTable table, int rowIndex, int colIndex, String name) {
        TableModel tm = table.getModel();
        tm.setValueAt(name, rowIndex, colIndex);
    }

    private String getLanguageString(final Locale defaultLocale) {
        final Set<Entry<String, Locale>> set = languageMap.entrySet();
        for (Entry e : set) {
            if (e.getValue().equals(defaultLocale)) {
                return (String) e.getKey();
            }
        }
        return "English";
    }

//    private void resizeWindow(Window w) {
//        Dimension d = w.getPreferredSize();
//        Toolkit toolkit = w.getToolkit();
//        Dimension size = toolkit.getScreenSize();
//        d.width = Math.min(d.width, size.width);
//        d.height = Math.min(d.height, size.height);
//        w.setSize(d);
//        Windows.centerOnScreen(w);
//        w.invalidate();
//        w.validate();
//        w.paintComponents(w.getGraphics());
//    }
    private Window getWindow() {
        return CS103App.this;
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        stop();
        if (serialPortControl != null) {
            serialPortControl.close();
        }
    }//GEN-LAST:event_formWindowClosing
    /**
     * Show all histogramms
     *
     * @param evt
     */
    private void togBtnShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnShowAllActionPerformed
        showAllGraphs();
    }//GEN-LAST:event_togBtnShowAllActionPerformed
    /**
     * Show histogramma 1
     *
     * @param evt
     */
    private void togBtnHist1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist1ActionPerformed
        showGraph(0);
    }//GEN-LAST:event_togBtnHist1ActionPerformed
    /**
     * Show histogramma 2
     *
     * @param evt
     */
    private void togBtnHist2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist2ActionPerformed
        showGraph(1);
    }//GEN-LAST:event_togBtnHist2ActionPerformed
    /**
     * Show histogramma 3
     *
     * @param evt
     */
    private void togBtnHist3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist3ActionPerformed
        showGraph(2);
    }//GEN-LAST:event_togBtnHist3ActionPerformed

    /**
     * Show histogramma 4
     *
     * @param evt
     */
    private void togBtnHist4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist4ActionPerformed
        showGraph(3);
    }//GEN-LAST:event_togBtnHist4ActionPerformed
    /**
     * Show histogramma 5
     *
     * @param evt
     */
    private void togBtnHist5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist5ActionPerformed
        showGraph(4);
    }//GEN-LAST:event_togBtnHist5ActionPerformed
    /**
     * Show histogramma 6
     *
     * @param evt
     */
    private void togBtnHist6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist6ActionPerformed
        showGraph(5);
    }//GEN-LAST:event_togBtnHist6ActionPerformed

    /**
     * Show histogramma 7
     *
     * @param evt
     */
    private void togBtnHist7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist7ActionPerformed
        showGraph(6);
    }//GEN-LAST:event_togBtnHist7ActionPerformed
    /**
     * Show histogramma 8
     *
     * @param evt
     */
    private void togBtnHist8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist8ActionPerformed
        showGraph(7);
    }//GEN-LAST:event_togBtnHist8ActionPerformed
    /**
     * Show histogramma 9
     *
     * @param evt
     */
    private void togBtnHist9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togBtnHist9ActionPerformed
        showGraph(8);
    }//GEN-LAST:event_togBtnHist9ActionPerformed

    //********************* unused methods **************************************/
    /**
     *
     * Perform loader command action .Create send buffer according to command and then send it throw serialport.
     *
     * @param cmd the command
     * @return success the action successful
     */
    private boolean doLoaderCommand(int cmd) {
        boolean success = false;
        int sendBuf[] = null;
        try {
            switch (cmd) {
                default:
                case Commands.RESTART_LOADER:
                    sendBuf = Creator.packetToSend(cmd);
                    serialPortControl.write(sendBuf, (byte) cmd);
                    success = true;
                    break;
                case Commands.READ_LOADER_VERSION:
                    sendBuf = Creator.packetToSend(cmd);
                    serialPortControl.write(sendBuf, (byte) cmd);
                    success = true;
                    break;
                case Commands.WRITE_CONFIGURATION:
                    Record config = recordItems.getConfigRecord();
                    sendBuf = Creator.recordPacketToSend(cmd, config.getAddress(), config.getData());
                    serialPortControl.write(sendBuf, (byte) cmd);
                    success = true;
                    break;
                case Commands.WRITE_PROGRAM:
                    Record record = recordItems.getNextRecord();
                    if (record != null) {
                        sendBuf = Creator.recordPacketToSend(cmd, record.getAddress(), record.getData());
                        serialPortControl.write(sendBuf, (byte) cmd);
                        success = true;
                    }
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * @param buf
     */
    private void doLoader(int[] buf) throws IOException, ArgumentException {
        switch (loaderState) {
            default:
                loaderState = LoaderStates.IDLE;
                break;
            case START:
                currVersion = Version.parse(buf);
                int response = updateProgConfirmMsg();
                if (response == JOptionPane.OK_OPTION) {
                    doLoaderCommand(Commands.RESTART_LOADER);
                    loaderState = LoaderStates.WAIT_SATRT_ACK;
                } else {
                    loaderState = LoaderStates.IDLE;
                }
                break;
            case WAIT_SATRT_ACK:
                errorCode = ErrorCode.parse(buf);
                if (errorCode.getErrorCode() == ErrorCode.OK) {
                    doCommand(Commands.READ_LOADER_VERSION);
                    loaderState = LoaderStates.WAIT_LOADER_VERSION;
                }
                break;
            case WAIT_LOADER_VERSION:
                Version loaderVersion = Version.parse(buf);
                doLoaderCommand(Commands.WRITE_CONFIGURATION);
                loaderState = LoaderStates.WAIT_CONFIG_ACK;
                break;
            case WAIT_CONFIG_ACK:
                Record configReceived = Record.parse(buf);
                Record configSended = recordItems.getConfigRecord();
                // compare two record and acording to result perform loader command or
                // send once again if received record not equals to sended record
                if (configReceived.compareTo(configSended) == 1) {
                    doLoaderCommand(Commands.WRITE_PROGRAM);
                    loaderState = LoaderStates.WRITE_PROGRAM;
                }
                break;
            case WRITE_PROGRAM:
                // receved record
                Record recordReceved = Record.parse(buf);
                // sended record
                Record recordSended = recordItems.getPrevRecord();
                // compare two record and according to result perform loader command or
                // send once again if received record not equals to sended record
                if (recordReceved.compareTo(recordSended) == 1) {
                    boolean success = doLoaderCommand(Commands.WRITE_PROGRAM);
                    if (!success) {
                        doLoaderCommand(Commands.END_PROGRAM);
                        loaderState = LoaderStates.WAIT_FILE_END_ACK;
                    }
                } else {
                    boolean success2 = doLoaderCommand(Commands.WRITE_PROGRAM);
                    if (!success2) {
                        doLoaderCommand(Commands.END_PROGRAM);
                        loaderState = LoaderStates.WAIT_FILE_END_ACK;
                    }
//                    int[] sendBuf = Creator.recordPacketToSend(Commands.WRITE_PROGRAM,recordSended.getAddress(),recordSended.getData());
//                    serialPortControl.write(sendBuf,Commands.WRITE_PROGRAM);
                }
                break;
            case WAIT_FILE_END_ACK:
                errorCode = ErrorCode.parse(buf);
                if (errorCode.getErrorCode() == ErrorCode.OK) {
                    JOptionPane.showMessageDialog(null, "End loader program successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                    loaderState = LoaderStates.IDLE;
                } else {
                    loaderState = LoaderStates.IDLE;
                }
                break;
        }
    }

    /**
     */
    private int updateProgConfirmMsg() {
        String message;
        int result = currVersion.compareTo(prevVersion);
        // the version program is the same to the loader version
        if (result == 0) {
            message = new String("Current program is the same with download program.");
        } else if (result > 0) {
            message = new String("Current program is older then download program.");
        } else {
            message = new String("Current program is newer then download program.");
        }
        return JOptionPane.showConfirmDialog(null,
                message
                + "Do you really want to update program? ",
                "Confirm Load program",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }
    //********************* end unused methods **********************************/

    /**
     * Excelt export event
     *
     * @param evt
     */
    private void btnExcelExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelExportActionPerformed
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        int result = FileChooser.exportToExcel(this, batchs);
        if (result == JFileChooser.ERROR_OPTION) {
            // if opening file failed
            // show error open file message
            JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Export.Error.text"), rb.getString("CS103.Message.Warring.Title.text"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnExcelExportActionPerformed
    /**
     * Run send version command
     *
     * @param evt
     */
    /**
     * Run send date time command
     *
     * @param evt
     */
    /**
     * Run send all command
     *
     * @param evt
     */
    private void btnReadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadDataActionPerformed
        if (connected) {
            btnReadData.setEnabled(false);
            doCommand(Commands.READ_ALL);
            startProgressBar();
            batchInCache = batchInCache.FROM_CHS_UNSAVED;
        } else {
            connected = doConnect();
            if (connected) {
                btnReadData.setEnabled(false);
                doCommand(Commands.READ_ALL);
                startProgressBar();
                batchInCache = BatchInCacheStates.FROM_CHS_UNSAVED;
            }
        }
}//GEN-LAST:event_btnReadDataActionPerformed
    /**
     * Perform open file action . Load all batch data from file and show all graphs.
     *
     * @param evt event open file
     */
    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        try {
            if (batchInCache == BatchInCacheStates.FROM_CHS_UNSAVED) {
                int result = JOptionPane.showConfirmDialog(CS103App.this,
                        rb.getString("CS103.Message.Open.Not.Save.text"),
                        rb.getString("CS103.Message.Open.Title.text"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    return;
                }
            }
            // if opennig file successed
            if ((batchs = FileChooser.openFile(this)) != null) {
                pnlDisplayGraphs.removeAll();
                chartPanel = new ChartPanel[NUM_OF_BATCHS];
                pnlDisplayGraphs.setLayout(new GridLayout(0, 3));
                // Scan all batchs and create chart foreach batch
                for (int batchIdx = 0; batchIdx < NUM_OF_BATCHS; batchIdx++) {
                    Batch batch = batchs[batchIdx];
                    batch.setRb(rb);
                    chartPanel[batchIdx] = new ChartPanel(((XYBarChart) batch.createBarChart()).getChart());
                    chartPanel[batchIdx].setDisplayToolTips(true);
                    JToggleButton button = getButtonByIndex(batchIdx);
                    chartPanel[batchIdx].addMouseListener(new ShowGraphEvent(batchIdx, button) {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 2) {
                                showGraph(i);
                                button.doClick();
                            }
                        }
                    });
                    pnlDisplayGraphs.add(chartPanel[batchIdx]);
                }
                pnlDisplayGraphs.updateUI();
                initTable(batchs);
                enableButtons();
                batchInCache = batchInCache.FROM_FILE;
            } else {
                JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Open.Not.Save.text"), rb.getString("CS103.Message.Info.Title.text"), JOptionPane.WARNING_MESSAGE);
            }

        } catch (ReadFileExeption ex) {
            JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Open.Error.text"), rb.getString("CS103.Message.Warring.Title.text"), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOpenActionPerformed
    /**
     * Perform save file action . Save all batch data .
     *
     * @param evt event save file
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        int result = FileChooser.saveFile(this, batchs);
        if (result == JFileChooser.ERROR_OPTION) {
            // show error save file message
            JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Save.Error.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
        } else if (result == JFileChooser.APPROVE_OPTION) {
            // show save file succesful message
            JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Save.Success.text"), rb.getString("CS103.Message.Info.Title.text"), JOptionPane.INFORMATION_MESSAGE);
            switch (batchInCache) {
                default:
                case FROM_CHS_SAVED:
                case FROM_FILE:
                case FROM_CHS_UNSAVED:
                    batchInCache = BatchInCacheStates.FROM_CHS_SAVED;
                    break;
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    /**
     * Perform print
     *
     * @param evt event print
     */
    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if (graphIndex == ALL_GRAPHS) {
            PrintManager.printAll(chartPanel);
        } else {
            PrintManager.printOne(chartPanel, graphIndex);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    /**
     * Open user manual from menu.
     * @param evt the menu item event
     */
    private void mnuItemPCManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPCManualActionPerformed
        try {
            File file = new File("help/PCUserManual.pdf");
            Desktop.getDesktop().open(file);
            //try statement
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "help/PCUserManual.pdf");   //open the file chart.pdf
        } catch (Exception ex) {                  //catch any exceptions here
            ex.printStackTrace();  //print the error
        }
    }//GEN-LAST:event_mnuItemPCManualActionPerformed

    private void mnuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemAboutActionPerformed
        CS103AboutDialog dialog = new CS103AboutDialog(new java.awt.Frame(), true);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuItemAboutActionPerformed

    private void mnuItemSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSettingActionPerformed
        CS103SettingDialog settingDialog = new CS103SettingDialog(this, true);
        settingDialog.setVisible(true);
    }//GEN-LAST:event_mnuItemSettingActionPerformed

    private void mnuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemExitActionPerformed

    private void mnuHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuHelpActionPerformed

    private void mnuItemCS103ManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCS103ManualActionPerformed
        try {
            File file = new File("help/CS103UserManualV4.0.pdf");
            Desktop.getDesktop().open(file);
            //try statement
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "help/CS103UserManualV4.0.pdf");   //open the file chart.pdf
        } catch (Exception ex) {                  //catch any exceptions here
            ex.printStackTrace();  //print the error
        }
    }//GEN-LAST:event_mnuItemCS103ManualActionPerformed

    private void mnuItemSetupDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSetupDriverActionPerformed
        progressMonitor = new ProgressMonitor(CS103App.this, "Installing AG USB driver", "", 0, 100);
        progressMonitor.setProgress(0);
        task = new Task();
        task.addPropertyChangeListener(CS103App.this);
        task.execute();

    }//GEN-LAST:event_mnuItemSetupDriverActionPerformed

    class Task extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(500);
                Process p = Runtime.getRuntime().exec("CDM20814_Setup.exe");
                while (progress < 100 && !isCancelled()) {
                    //Sleep for up to one second.
                    Thread.sleep(random.nextInt(100));
                    //Make random progress.
                    p.waitFor();
                    System.out.println(p.exitValue());
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message = String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                }
            }
        }
    }

    /**
     * Show all graph method.
     */
    private void showAllGraphs() {
        graphIndex = ALL_GRAPHS;
        pnlDisplayGraphs.setLayout(new GridLayout(0, 3));
        pnlDisplayGraphs.removeAll();
        for (int i = 0; i < NUM_OF_BATCHS; i++) {
            pnlDisplayGraphs.add(chartPanel[i]);
        }
        pnlDisplayGraphs.updateUI();
    }

    /**
     * Show histogramma 1
     *
     * @param evt
     */
    private void showGraph(int index) {
        graphIndex = index;
        pnlDisplayGraphs.setLayout(new GridLayout(1, 0));
        pnlDisplayGraphs.removeAll();
        pnlDisplayGraphs.add(chartPanel[graphIndex]);
        pnlDisplayGraphs.updateUI();


    }

    private JToggleButton getButtonByIndex(int index) {
        if (index == 0) {
            return togBtnHist1;


        } else if (index == 1) {
            return togBtnHist2;


        } else if (index == 2) {
            return togBtnHist3;


        } else if (index == 3) {
            return togBtnHist4;


        } else if (index == 4) {
            return togBtnHist5;


        } else if (index == 5) {
            return togBtnHist6;


        } else if (index == 6) {
            return togBtnHist7;


        } else if (index == 7) {
            return togBtnHist8;


        } else {
            return togBtnHist9;


        }
    }

    /**
     * Creating new SerialPortControl object with specific comPort
     *
     * @return SerialPortControl object
     */
    private SerialPortControl createSerialPortCtrl(String comPort) throws PortInUseException, NoSuchPortException, DllNotfoundException {
        return new SerialPortControl(comPort);
    }

    /**
     * The method starts on program load Read property file
     */
    private void connectOnLoad() {
        // comport from property
        comPort = PropertyFileManager.getProperty(FILENAME, "comport");
        //comPort = PropertyFileManager.getComportFromPropFile();


        if (comPort != null) {
            try {
                serialPortControl = createSerialPortCtrl(comPort);
                connected = true;
                btnReadData.setEnabled(false);
                doCommand(
                        Commands.READ_VERSION);

            } catch (DllNotfoundException ex) {
                ex.printStackTrace();
                connected = false;
            } catch (PortInUseException ex) {
                setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
                //setConnectionStatus(" NOT CONNECTED ");
                // reset property file and comport
                PropertyFileManager.deleteProperty(FILENAME, "Propertie file for program", "comport");
//                PropertyFileManager.deleteComportFromPropFile();
                connected = false;


            } catch (NoSuchPortException ex) {
                setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
                //setConnectionStatus(" NOT CONNECTED ");
                // reset property file and comport
                PropertyFileManager.deleteProperty(FILENAME, "Propertie file for program", "comport");
                connected = false;
            }
        } else {
            setConnectionStatus(ConnectionStatus.NOT_CONNECTED);

            //setConnectionStatus(" NOT CONNECTED ");
            // reset property file and comport
            PropertyFileManager.deleteProperty(FILENAME, "Propertie file for program", "comport");
            connected = false;
        }
    }

    /**
     * @return
     */
    private boolean doConnect() {
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        // comport from property
        comPort = PropertyFileManager.getProperty(FILENAME, "comport");
        //comPort = PropertyFileManager.getComportFromPropFile();
        if (comPort != null && !comPort.equals("")) {
            try {
                serialPortControl = createSerialPortCtrl(comPort);
                return true;

            } catch (DllNotfoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, rb.getString("CS103.Message.RXTX.DLL.Not.Found.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                return false;
            } catch (PortInUseException ex) {
                //
            } catch (NoSuchPortException ex) {
                //
            }
        }
        // comport from input dialog
        comPort = chooseComportDlg((Component) this);


        if (comPort != null) {
            try {
                serialPortControl = createSerialPortCtrl(comPort);
                PropertyFileManager.setProperty(FILENAME, "Properties file for program", "comport", comPort);
                return true;
            } catch (DllNotfoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, rb.getString("CS103.Message.RXTX.DLL.Not.Found.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                return false;
            } catch (PortInUseException ex) {
                JOptionPane.showMessageDialog(null, rb.getString("CS103.Message.USB.Port.In.Use.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                return false;
            } catch (NoSuchPortException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, rb.getString("CS103.Message.USB.Port.Not.Exist.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;


        }
    }

    /**
     * Get all available comports and show to user inpt dialog with list of comports . Return comort that user choosed .
     *
     * @param source the mainframe
     * @return comport the comport that user choosed ,null otherwise
     */
    private String chooseComportDlg(Component source) {
        // get available comports
        Enumeration enm = CommPortIdentifier.getPortIdentifiers();
        Vector comPortTitlesVector = new Vector();


        while (enm.hasMoreElements()) {
            String comPortName = ((CommPortIdentifier) enm.nextElement()).getName();


            if (comPortName.indexOf("COM") != -1) {
                comPortTitlesVector.add(comPortName);


            }
        }
        // Create comport titles array for input dialog
        int len = comPortTitlesVector.size() + 1;
        String comPortTitles[] = new String[len];


        for (int i = 0; i
                < (len - 1); i++) {
            comPortTitles[i] = (String) comPortTitlesVector.get(i);


        }
        comPortTitles[len - 1] = " ";
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        Locale.setDefault(currLocale);
        String choosed = (String) JOptionPane.showInputDialog(
                source,
                rb.getString("CS103.InputDialog.Label.text"),
                rb.getString("CS103.InputDialog.Title.text"), JOptionPane.QUESTION_MESSAGE,
                null, comPortTitles, " ");
        // if user canceled input dialog


        if (choosed != null) {
            int i = 0;


            for (; i
                    < comPortTitles.length; i++) {
                if (comPortTitles[i].equals(choosed)) {
                    break;


                }
            }
            if (!comPortTitles[i].equals(" ")) {
                return comPortTitles[i];


            }
        }
        return null;


    }

    /**
     * Perform command action .Create send buffer according to command and then send it throw serialport.If any error
     * ocured while writing sendBuffer show error message.
     *
     * @param cmd the command
     */
    private void doCommand(int cmd) {
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);


        int sendBuffer[] = Creator.packetToSend(cmd);


        try {
            serialPortControl.write(sendBuffer, (byte) cmd);


        } catch (IOException ex) {
            serialPortControl.setNetMode(NetworkMode.IDLE);
            // error serialport writeing message
            JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.USB.Write.Error.text"), rb.getString("CS103.Message.Title.text"), JOptionPane.ERROR_MESSAGE);

            connected = false;
            serialPortControl.close();


            return;


        }
    }

    /**
     * Parse received buffer acording to command.
     *
     * @param buf the received buffer
     */
    private void parseData(int buf[]) throws IOException, ArgumentException, NullPointerException {

        if (buf == null || buf.length < 0) {
            throw new ArgumentException("");


        }

        int cmd = buf[1];


        if (loaderState != LoaderStates.IDLE) {
            doLoader(buf);


        } else {

            switch (cmd) {
                default:
                case Commands.READ_VERSION:
                    currVersion = Version.parse(buf);
                    setVersionLabel(currVersion.getVersion());
                    setConnectionStatus(ConnectionStatus.CONNECTED);
                    //setConnectionStatus(" CONNECTED ");
                    serialPortControl.setNetMode(NetworkMode.IDLE);     // return to IDLE
                    doCommand(Commands.READ_DATE_TIME);
                    break;


                case Commands.READ_DATE_TIME:
                    dateTime = DateTime.parse(buf);
                    serialPortControl.setNetMode(NetworkMode.IDLE);     // return to IDLE
                    break;


                case Commands.READ_ALL:
                    // parsing all batch data
                    currVersion = Version.parse(buf);
                    // parse sc103 unit
                    String unitString = PropertyFileManager.getProperty(FILENAME, "weightunit");
                    int version = currVersion.getVersion();
                    unit = Unit.parse(buf, unitString, version);
                    setVersionLabel(currVersion.getVersion());
                    setDate(dateTime, unit);
                    setConnectionStatus(ConnectionStatus.CONNECTED);
                    //setConnectionStatus(" CONNECTED ");
                    serialPortControl.setNetMode(NetworkMode.IDLE);     // return to IDLE

                    for (int batchIdx = 0; batchIdx < NUM_OF_BATCHS; batchIdx++) {
                        batchs[batchIdx] = Batch.parse(buf, batchIdx, unit);
                    }
                    // creating charts
                    chartPanel = new ChartPanel[NUM_OF_BATCHS];
                    pnlDisplayGraphs.setLayout(new GridLayout(0, 3));
                    pnlDisplayGraphs.removeAll();
                    for (int batchIdx = 0; batchIdx < NUM_OF_BATCHS; batchIdx++) {
                        batchs[batchIdx].setRb(ResourceBundle.getBundle("resource/CS103App", currLocale));
                        chartPanel[batchIdx] = new ChartPanel(((XYBarChart) batchs[batchIdx].createBarChart()).getChart());
                        chartPanel[batchIdx].setDisplayToolTips(true);
                        JToggleButton button = getButtonByIndex(batchIdx);
                        chartPanel[batchIdx].addMouseListener(new ShowGraphEvent(batchIdx, button) {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getClickCount() == 2) {
                                    showGraph(i);
                                    button.doClick();


                                }
                            }
                        });
                        pnlDisplayGraphs.add(chartPanel[batchIdx]);
                    }
                    pnlDisplayGraphs.updateUI();
                    initTable(batchs);
                    stopProgressBar();
                    break;


                case Commands.WRITE_DATE_TIME:
                    errorCode = ErrorCode.parse(buf);
                    serialPortControl.setNetMode(NetworkMode.IDLE);     // return to IDLE
                    break;
            }
        }
    }

    private void changeGraphLanguage() {
        if (batchs == null || batchs[0] == null) {
            return;


        }
        chartPanel = new ChartPanel[NUM_OF_BATCHS];
        pnlDisplayGraphs.setLayout(new GridLayout(0, 3));
        pnlDisplayGraphs.removeAll();



        for (int batchIdx = 0; batchIdx
                < NUM_OF_BATCHS; batchIdx++) {
            batchs[batchIdx].setRb(ResourceBundle.getBundle("resource/CS103App", currLocale));
            chartPanel[batchIdx] = new ChartPanel(((XYBarChart) batchs[batchIdx].recreateBarChart()).getChart());
            chartPanel[batchIdx].setDisplayToolTips(true);
            JToggleButton button = getButtonByIndex(batchIdx);
            chartPanel[batchIdx].addMouseListener(new ShowGraphEvent(batchIdx, button) {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        showGraph(i);
                        button.doClick();


                    }
                }
            });
            pnlDisplayGraphs.add(chartPanel[batchIdx]);


        }
        pnlDisplayGraphs.updateUI();


    }

    private void startProgressBar() {
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);


    }

    private void stopProgressBar() {
        progressBar.setVisible(false);
        progressBar.setIndeterminate(false);


    }

    /**
     * Set version of program
     */
    private void setPrevVersion() {
        prevVersion = (currVersion == null) ? new Version() : currVersion;


    }

    /**
     * Set date label.
     *
     * @param dateTime the DateTime object that contain system date&time
     */
    private void setDate(DateTime dateTime) {
        lblDateTime.setText(dateTime.getDate());


    }

    /**
     *
     * @param dateTime
     * @param unit
     */
    private void setDate(DateTime dateTime, byte unit) {
        lblDateTime.setText(dateTime.getDate(unit));


    }

    /**
     * Set version .
     */
    public void setVersionInTitle() {
        String vers = PropertyFileManager.getProperty(FILENAME, "version");
        String title = getTitle() + vers;
        setTitle(title);
    }

    /**
     * Set current program version field
     *
     * @param version the version number
     */
    public void setVersion(int version) {
        currVersion.setVersion(version);


    }

    /**
     * Set current program version label
     *
     * @param version the version number
     */
    public void setVersionLabel(int version) {
        lblVersion.setText("CS103 Ver.: " + version);


    }

    /**
     * Set connection to comport status label
     *
     * @param status the connection status flag .
     */
    public void setConnectionStatus(String status) {
        if (status.equals(" NOT CONNECTED ")) {
            lblStatus.setForeground(Color.red);
        } else if (status.equals(" CONNECTED ")) {
            lblStatus.setForeground(new Color(152, 156, 0));
        }
        lblStatus.setText(status);
    }

    public void setConnectionStatus(ConnectionStatus status) {
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);


        if (status == ConnectionStatus.NOT_CONNECTED) {
            lblStatus.setText(rb.getString("CS103.Label.Status.NotConnected.text"));
            lblStatus.setForeground(Color.red);
        } else {
            lblStatus.setText(rb.getString("CS103.Label.Status.Connected.text"));
            lblStatus.setForeground(new Color(0, 156, 0));
        }
    }

    /**
     * Set graphIndex to selected graph
     *
     * @param graphIndex selected index graph
     */
    public void setGraphIndex(int graphIndex) {
        this.graphIndex = graphIndex;
    }

    /**
     * Enable all buttons
     */
    public void enableButtons() {
        btnSave.setEnabled(true);
        btnExcelExport.setEnabled(true);
        btnPrint.setEnabled(true);
        togBtnHist1.setEnabled(true);
        togBtnHist2.setEnabled(true);
        togBtnHist3.setEnabled(true);
        togBtnHist4.setEnabled(true);
        togBtnHist5.setEnabled(true);
        togBtnHist6.setEnabled(true);
        togBtnHist7.setEnabled(true);
        togBtnHist8.setEnabled(true);
        togBtnHist9.setEnabled(true);
        togBtnShowAll.setEnabled(true);
        graphIndex = ALL_GRAPHS;
    }

    public final void disableButtons() {
        btnSave.setEnabled(false);
        btnExcelExport.setEnabled(false);
        btnPrint.setEnabled(false);
        togBtnHist1.setEnabled(false);
        togBtnHist2.setEnabled(false);
        togBtnHist3.setEnabled(false);
        togBtnHist4.setEnabled(false);
        togBtnHist5.setEnabled(false);
        togBtnHist6.setEnabled(false);
        togBtnHist7.setEnabled(false);
        togBtnHist8.setEnabled(false);
        togBtnHist9.setEnabled(false);
        togBtnShowAll.setEnabled(false);
        progressBar.setVisible(false);


    }

    /**
     * Initialize batch table
     *
     * @param batchs the array of batch object
     */
    private void initTable(Batch[] batchs) {
        TableModel tm = tblTable.getModel();
        int tableCol = 1;   // batch col
        for (int i = 0, tableRow = 0; i < 9; i++) {
            tm.setValueAt(batchs[i].getNumOfBirds(), tableRow++, tableCol);
            if (batchs[i].getUnitFlag() == Batch.GRAM) {
                tm.setValueAt((int) batchs[i].getAvgWeight(), tableRow++, tableCol);
            } else {
                tm.setValueAt(batchs[i].getAvgWeight(), tableRow++, tableCol);
            }
            if (batchs[i].getUnitFlag() == Batch.GRAM) {
                tm.setValueAt((int) batchs[i].getStdDev(), tableRow++, tableCol);
            } else {
                tm.setValueAt(batchs[i].getStdDev(), tableRow++, tableCol);
            }
            tm.setValueAt(batchs[i].getCv(), tableRow++, tableCol);
            tm.setValueAt(batchs[i].calcNumOfBirdPercent(), tableRow, tableCol);
            tableCol++;     // next batch col
            tableRow = 0;   // reset table row
        }
        enableButtons();
    }

    /**
     * Group all graph buttons
     */
    private void groupHistogramButtons() {
        btnGrpHistogram.add(togBtnHist1);
        btnGrpHistogram.add(togBtnHist2);
        btnGrpHistogram.add(togBtnHist3);
        btnGrpHistogram.add(togBtnHist4);
        btnGrpHistogram.add(togBtnHist5);
        btnGrpHistogram.add(togBtnHist6);
        btnGrpHistogram.add(togBtnHist7);
        btnGrpHistogram.add(togBtnHist8);
        btnGrpHistogram.add(togBtnHist9);
        btnGrpHistogram.add(togBtnShowAll);


    }

    @Override
    public void run() {
        NetworkMode mode = NetworkMode.IDLE;
        setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
        ResourceBundle rb = ResourceBundle.getBundle("resource/CS103App", currLocale);
        while (!stopRunning) {
            try {
                Thread.sleep(100);
                if (serialPortControl != null) {
                    mode = serialPortControl.getNetMode();
                }
                switch (mode) {
                    default:
                    case IDLE:
                        break;
                    case DATA_READY:
                        stopProgressBar();
                        btnReadData.setEnabled(true);
                        int buf[] = serialPortControl.read();
                        System.out.println("receive");
                        parseData(
                                buf);
                        serialPortControl.setNetMode(NetworkMode.IDLE);
                        mode = NetworkMode.IDLE;


                        break;


                    case CRC_ERROR:
                        stopProgressBar();
                        btnReadData.setEnabled(true);
                        System.out.println("receive crc error");
                        serialPortControl.setNetMode(NetworkMode.IDLE);
                        batchInCache = BatchInCacheStates.EMPTY;
                        mode = NetworkMode.IDLE;
                        JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Read.Data.Error.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                        break;


                    case TIME_OUT:
                        stopProgressBar();
                        btnReadData.setEnabled(true);
                        setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
                        PropertyFileManager.deleteProperty(FILENAME, "Property file for program ", "comport");
                        serialPortControl.setNetMode(NetworkMode.IDLE);
                        serialPortControl = null;
                        connected = false;
                        mode = NetworkMode.IDLE;
                        batchInCache = BatchInCacheStates.EMPTY;
                        JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.Read.Data.Error.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);
                        break;


                    case BUSY:
                        break;


                    case DISCONNECTED:
                        serialPortControl.setNetMode(NetworkMode.IDLE);
                        serialPortControl.close();
                        connected = false;
                        JOptionPane.showMessageDialog(CS103App.this, rb.getString("CS103.Message.USB.Cable.Disconnect.text"), rb.getString("CS103.Message.Error.Title.text"), JOptionPane.ERROR_MESSAGE);


                        break;



                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                System.out.println("interupt ex");


            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("io ex");


            } catch (ArgumentException ex) {
                ex.printStackTrace();
                System.out.println("argument ex");


            } catch (NullPointerException ex) {
                ex.printStackTrace();
                System.out.println("null pointer ex" + ex);


            }
        }
    }

    /**
     * Stop program loop
     */
    public void stop() {
        stopRunning = true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnExcelExport;
    private javax.swing.ButtonGroup btnGrpHistogram;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReadData;
    private javax.swing.JButton btnSave;
    private javax.swing.JDialog dlgComPortChooser;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusTitle;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuItemAbout;
    private javax.swing.JMenuItem mnuItemCS103Manual;
    private javax.swing.JMenuItem mnuItemExit;
    private javax.swing.JMenuItem mnuItemPCManual;
    private javax.swing.JMenuItem mnuItemSetting;
    private javax.swing.JMenuItem mnuItemSetupDriver;
    private javax.swing.JMenu mnuLanguage;
    private javax.swing.JMenu mnuTool;
    private javax.swing.JPanel pnlComPorts;
    private javax.swing.JPanel pnlCommandBar;
    private javax.swing.JPanel pnlDisplayGraphs;
    private javax.swing.JPanel pnlHistograms;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton rdbComPort1;
    private javax.swing.JRadioButton rdbComPort2;
    private javax.swing.JTable tblTable;
    private javax.swing.JToggleButton togBtnHist1;
    private javax.swing.JToggleButton togBtnHist2;
    private javax.swing.JToggleButton togBtnHist3;
    private javax.swing.JToggleButton togBtnHist4;
    private javax.swing.JToggleButton togBtnHist5;
    private javax.swing.JToggleButton togBtnHist6;
    private javax.swing.JToggleButton togBtnHist7;
    private javax.swing.JToggleButton togBtnHist8;
    private javax.swing.JToggleButton togBtnHist9;
    private javax.swing.JToggleButton togBtnShowAll;
    // End of variables declaration//GEN-END:variables

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new CS103App().setVisible(true);
                } catch (ConfigurationFailedException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
