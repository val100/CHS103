/*
 * DataCreator.java
 *
 * Created on August 24, 2008, 12:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.network;

import chs103.bl.DateTime;
import chs103.bl.DateTimeFormat;

/**
 * Title: DataCreator <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class Creator {
    private static final int START_TRANSSMIT_IDX    = 0;
    private static final int COMMAND_IDX            = 1;
    private static final int NUM_DATABYTES_LOW_IDX  = 2;
    private static final int NUM_DATABYTES_HIGH_IDX = 3;
    private static final int ADDR_LOW_IDX           = 4;
    private static final int ADDR_HIGH_IDX          = 5;
    private static final int CRC_LOW_IDX            = 4;
    private static final int CRC_HIGH_IDX           = 5;
    private static final int BUFFER_LEN_IDX         = 6;
    private static final int ADDR_BYTES_IDX         = 2;
    private static final int REC_DATA_LEN_IDX       = 64;
    /**
     * Create buffer according to command
     * @param cmd   the command from user interface
     * @return      the send buffer
     */
    public static int[] packetToSend(int cmd) {
        int[] buffer;
        int crc;
        switch(cmd) {
            default:
            case Commands.READ_ALL:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.READ_ALL;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num high
                break;
            case Commands.READ_DATE_TIME:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.READ_DATE_TIME;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num
                break;
            case Commands.READ_VERSION:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.READ_VERSION;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num
                break;
            case Commands.WRITE_DATE_TIME:
                DateTime dateTime = new DateTime();
                int[] dt          = dateTime.getDateTime();
                buffer            = new int[BUFFER_LEN_IDX + dt.length];
                
                buffer[START_TRANSSMIT_IDX]      = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]              = Commands.WRITE_DATE_TIME;
                buffer[NUM_DATABYTES_LOW_IDX]    = dt.length;
                buffer[NUM_DATABYTES_HIGH_IDX]   = 0;
                buffer[DateTimeFormat.LOW_YEAR]  = dt[0];
                buffer[DateTimeFormat.HIGH_YEAR] = dt[1];
                buffer[DateTimeFormat.DAY]       = dt[2];
                buffer[DateTimeFormat.MONTH]     = dt[3];
                buffer[DateTimeFormat.HOUR]      = dt[4];
                buffer[DateTimeFormat.MINUTE]    = dt[5];
                crc                              = doCrc(buffer,1,dt.length+4);
                buffer[CRC_LOW_IDX + dt.length]  = low(crc);      // num low
                buffer[CRC_HIGH_IDX + dt.length] = high(crc);     // num
                break;
            case Commands.RESTART_LOADER:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.RESTART_LOADER;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num high
                break;
            case Commands.READ_LOADER_VERSION:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.READ_LOADER_VERSION;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num high
                break;
            case Commands.END_PROGRAM:
                buffer = new int[BUFFER_LEN_IDX];
                
                buffer[START_TRANSSMIT_IDX]     = Commands.START_TRASSMIT;
                buffer[COMMAND_IDX]             = Commands.END_PROGRAM;
                buffer[NUM_DATABYTES_LOW_IDX]   = 0;
                buffer[NUM_DATABYTES_HIGH_IDX]  = 0;
                crc                             = doCrc(buffer,1,4);
                buffer[CRC_LOW_IDX]             = low(crc);      // num low
                buffer[CRC_HIGH_IDX]            = high(crc);     // num high
                break;
        }
        return buffer;
    }
    /**
     * Static method create record buffer
     * @param cmd
     * @param address
     * @param buf
     * @return
     */
    public static int[] recordPacketToSend(int cmd, int address, int[] buf) {

        int recordBufferLength = BUFFER_LEN_IDX + REC_DATA_LEN_IDX + ADDR_BYTES_IDX;
        
        int[] buffer = new int[recordBufferLength];
        
        buffer[START_TRANSSMIT_IDX]    = Commands.START_TRASSMIT;
        buffer[COMMAND_IDX]            = cmd;
        buffer[NUM_DATABYTES_LOW_IDX]  = REC_DATA_LEN_IDX + ADDR_BYTES_IDX;
        buffer[NUM_DATABYTES_HIGH_IDX] = 0;
        buffer[ADDR_LOW_IDX]        = low(address);
        buffer[ADDR_HIGH_IDX]       = high(address);
        
        int endIdx = REC_DATA_LEN_IDX + BUFFER_LEN_IDX; 
        for(int i = BUFFER_LEN_IDX; i < endIdx; i++) {
            buffer[i] = buf[i - BUFFER_LEN_IDX];
        }
        
        int from = COMMAND_IDX, to = REC_DATA_LEN_IDX + BUFFER_LEN_IDX;
        int crc = doCrc(buffer,from,to);

        int crcLoIdx = CRC_LOW_IDX   + REC_DATA_LEN_IDX + ADDR_BYTES_IDX;
        int crcHiIdx = CRC_HIGH_IDX  + REC_DATA_LEN_IDX + ADDR_BYTES_IDX;
        
        buffer[crcLoIdx] = low(crc);      // num low
        buffer[crcHiIdx] = high(crc);     // num high
        
        return buffer;
    }
    /**
     * Calculate crc
     * @param c
     * @param crc
     * @return crc
     */
    private static int crc(int c, int crc) {
        byte f;
        int i=8;

        do{
            f = (byte)(1 & (c ^ crc)); // calc in data xor
            crc >>= 1; // shift right num
            c  >>= 1; // and in data
            if (f>0)
                crc = (crc ^ 0xA001);  // CRC (x16 + x15 + x2 + 1),xor if in data xor is one
        } while ( (--i) > 0);
        return crc;
    }
    /**
     * Return high byte of num
     * @param num       the num
     * @return byte     the high num byte
     */
    private static int high(int num) {
        return  ((num>>8)&0x00FF) ;
    }
    /**
     * Return low byte of num
     * @param num       the num
     * @return byte     the low num byte
     */
    private static int low(int num) {
        return (num & 0x00FF);
    }
    /**
     * Reset num
     */
    private static int crcReset() {
        return 65535;    
    }
    /**
     * doCrc
     * @param buffer    the array of byte buffer
     * @param off       the offset index
     * @param len       the length of buffer
     * @return num      the num of buffer
     */
    private static int doCrc(int buffer[], int off,int len) {
        int i = off;
        int crc = crcReset();
        while(i < len){
            crc = crc(buffer[i++],crc);
        }
        return crc;
    }
}
