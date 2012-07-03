/*
 * SerialPortControl.java
 *
 * Created on July 29, 2008, 2:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package chs103.network;

import chs103.exception.DllNotfoundException;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

/**
 * Title: SerialPortControl <br> Description: <br> Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class SerialPortControl implements SerialPortEventListener {
    // comminication fields

    private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;
    //data fields
    private static byte[] buf;
    private byte currCommand;
    private static int crc;                // crc
    private int count;              // buffer counter
    private int numBytesLow, // num bytes low
            numBytesHigh, // num bytes high
            numBytesTotal;      // num bytes total = (nbh*256)+nbl
    //timer data
    private Timer timer;
    private int timeCounter;
    public static final int TIME_OUT = 500;
    // connection modes
    private NetworkMode netMode;

    // send receive states
    enum SendReceiveStates {

        IDL, SOT, CMD, NBL, NBH, DTA, CRC
    }
    private SendReceiveStates state;

    /**
     * Creates a new instance of SerialPortControl
     */
    public SerialPortControl(String com) throws PortInUseException, NoSuchPortException, DllNotfoundException {
        init(com);
        timer = new Timer();
        buf = new byte[6000];
        numBytesLow = numBytesHigh = numBytesTotal = 0;
        state = SendReceiveStates.IDL;
        netMode = NetworkMode.IDLE;
        timer.schedule(new TimerTick(), 0, TIME_OUT);
        resetCrc();
        resetCount();
    }

    public static void loadJniLib() throws UnsatisfiedLinkError {
        // loads the jnilib from the source folder "src/main/resources"
        URL url = SerialPortControl.class.getResource("./jni/rxtxSerial.dll");
        try {
            System.load(url.getPath());
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            // native code library failed to load.
            unsatisfiedLinkError.printStackTrace();
            throw new UnsatisfiedLinkError(unsatisfiedLinkError.getMessage());
        }
    }

    /**
     *
     */
    private void init(String comport) throws PortInUseException, NoSuchPortException, DllNotfoundException {
//        try {
//            loadJniLib();
//        } catch (UnsatisfiedLinkError err) {
//            throw new DllNotfoundException();
//        }
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(comport);
        serialPort = (SerialPort) portId.open("Terminal", 2000);
        try {
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();
        } catch (IOException e) {
        } catch (Exception e) {
        }

        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        }

        serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnOutputEmpty(true);
        try {
            serialPort.setSerialPortParams(115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException e) {
        }
    }

    /**
     * Write a buffer array of int over a serial port to CH103.
     *
     * @param buf int array is the request buffer from PC to CH103
     */
    public void write(int buf[], byte cmd) throws IOException {
        // save command
        currCommand = cmd;
        // reset counter
        resetCount();
        // convert int array to byte array and write
        out.write(intArrayToByteArray(buf));
        System.err.println("send bytes = " + buf.length);
        netMode = NetworkMode.BUSY;
        timeCounter = 50; // in 20 sec
    }

    /**
     * This returns the response buffer from the CH103 to pc.
     *
     * @return buf byte array is the response buffer from CH103 to PC.
     */
    public int[] read() {
        System.err.println("received bytes = " + (count));
        return byteArrayToIntArray(buf);
    }

    /**
     * Convert array of int to array of byte
     *
     * @param buf the array of int
     * @return array the array of byte
     */
    private byte[] intArrayToByteArray(int[] buf) {
        byte[] array = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            array[i] = (byte) buf[i];
        }
        return array;
    }

    /**
     * Convert array of byte to array of int
     *
     * @param buf the array of byte
     * @return array the array of int
     */
    private int[] byteArrayToIntArray(byte[] buf) {
        int[] array = new int[count];
        int zeroHiByteMask = 0x00FF;
        for (int i = 0; i < count; i++) {
            array[i] = (int) (buf[i] & zeroHiByteMask);
        }
        return array;

    }

    /**
     * Method declaration
     *
     * @param event A serial port event. See the JavaDoc for the Java CommAPI for details.
     */
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    int c;
                    while (in.available() > 0) {
                        c = in.read();
                        switch (state) {
                            default:
                            case IDL:
                                if (Commands.START_TRASSMIT == (byte) c) {
                                    buf[count++] = (byte) c;
                                    state = SendReceiveStates.CMD;
                                } else {
                                    netMode = NetworkMode.CRC_ERROR;
                                    state = SendReceiveStates.IDL;
                                }
                                break;
                            case CMD:
                                if (currCommand == (byte) c) {
                                    buf[count++] = (byte) c;
                                    crc((byte) c);
                                    state = SendReceiveStates.NBL;
                                } else {
                                    netMode = NetworkMode.CRC_ERROR;
                                    state = SendReceiveStates.IDL;
                                }
                                break;
                            case NBL:
                                numBytesLow = c;
                                buf[count++] = (byte) c;
                                crc((byte) c);
                                state = SendReceiveStates.NBH;
                                break;
                            case NBH:
                                numBytesHigh = c;
                                numBytesTotal = (numBytesHigh * 256) + numBytesLow;
                                buf[count++] = (byte) c;
                                crc((byte) c);
                                state = SendReceiveStates.DTA;
                                break;
                            case DTA:
                                if (count == 5765) {
                                    count = count + 1 - 1;
                                }
                                buf[count++] = (byte) c;
                                numBytesTotal--;
                                crc((byte) c);
                                if (numBytesTotal == 0) {
                                    state = SendReceiveStates.CRC;
                                }
                                break;
                            case CRC:
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                }
                                int low = c;
                                int high = in.read();
                                int recCRC = (high * 256) + low; //make 16((byte)low,(byte)high);
                                if (crc != recCRC) {
                                    System.err.println("error in crc\n" + recCRC + " != " + crc + "\n" + count);
                                    netMode = NetworkMode.CRC_ERROR;
                                } else {
                                    System.err.println(recCRC + " == " + crc + " number of bytes = " + count);
                                    netMode = NetworkMode.DATA_READY;
                                }
                                resetCrc();
                                state = SendReceiveStates.IDL;
                                break;
                        }
                    }
                } catch (IOException e) {
                    netMode = NetworkMode.DISCONNECTED;
                }
                break;
        }
    }

    /**
     * This method returns mode of serialportcontroler
     *
     * @return mode the mode
     */
    public NetworkMode getNetMode() {
        return netMode;
    }

    /**
     * Set netMode of serialportcontroler
     *
     * @param netMode the netMoode
     */
    public void setNetMode(NetworkMode netMode) {
        this.netMode = netMode;
    }

    /**
     * Calculate crc method
     *
     * @param c the byte parameter of buffer to calculate crc
     * @return crc the crc
     */
    private void crc(byte c) {
        byte f;
        int i = 8;

        do {
            f = (byte) (1 & (c ^ crc)); // calc in data xor
            crc >>= 1; // shift right crc
            c >>= 1; // and in data
            if (f > 0) {
                crc = (crc ^ 0xA001);  // CRC (x16 + x15 + x2 + 1),xor if in data xor is one
            }
        } while ((--i) > 0);
    }

    /**
     * Reset crc
     */
    private void resetCrc() {
        crc = 65535;
    }

    /**
     * Reset counter
     */
    private void resetCount() {
        count = 0;
    }

    /**
     * Close serialPort connection
     */
    public void close() {
        serialPort.close();
    }

    /**
     * Inner class
     */
    class TimerTick extends TimerTask {// runs every 100 ms

        @Override
        public void run() {
            if (timeCounter > 0) {
                timeCounter--;
            }
            if (netMode == NetworkMode.BUSY) {
                if (timeCounter == 0) {
                    System.out.println("timeout");
                    netMode = NetworkMode.TIME_OUT;
                }
            }
        }
    }
}
