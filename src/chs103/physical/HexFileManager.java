/*
 * HexFileManager.java
 *
 * Created on August 21, 2008, 1:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.physical;

import chs103.exception.ChecksumException;
import chs103.bl.RecordVector;
import chs103.exception.ArgumentException;
import chs103.exception.ReadFileExeption;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Title: HexFileManager <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class HexFileManager {
    File hexFile;
    int version;
    RecordVector recordsVector;

    public static final int START_RECORD    = 0x3A;     // start record  code
    public static final int END_RECORD      = 0x0D;     // end record code    
    public static final int ACKLOD          = 0x06;

    public static final int BUFFER_LEN_LOD  = 64;
    public static final int DATA_LEN        = 32;
    
    public static final int DATA_ADDRESS    = 64000;
    public static final int CONFIG_ADDRESS  = 3145728;
    /** 
     * Creates a new instance of HexFileManager 
     */
    public HexFileManager(File hexFile) {
        this.hexFile = hexFile;
        this.recordsVector = new RecordVector();
    }
    /**
     * 
     * @return  recordsVector   the vector with batch records 
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws ch102.exception.ChecksumException
     */
    public RecordVector parseFile() throws ChecksumException, ReadFileExeption {
        boolean  success, done=false;
        

        int dataIdx, i, count, recordCount = 0;
        int data[] = new int[DATA_LEN];
        
        char[] recordBuffer = new char[BUFFER_LEN_LOD];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(hexFile);
        } catch (FileNotFoundException ex) {
            throw new ReadFileExeption("Error while read hex file");
        }
        int bufferIdx;
        try {
            while (!done) {  // Loop until the entire program is downloaded
                bufferIdx = 0;  // Read into the recordBuffer until 0x0D ('\r') is received or the recordBuffer is full
                do {
                    recordBuffer[bufferIdx] = (char)fis.read();
                } while ( (recordBuffer[bufferIdx++] != END_RECORD) && (bufferIdx <= BUFFER_LEN_LOD) );

                fis.read();     // clear '\n' from fis    

                success = true;
                int addr,lowAddr,highAddr = 0;  // base address
                // Only process data blocks that start with ':'
                if (recordBuffer[0] == ':') {
                    count = atoi(recordBuffer,1);  // Get the number of bytes from the recordBuffer
                    // Get the lower 16 bits of address
                    lowAddr = make16(atoi(recordBuffer,3),atoi(recordBuffer,5));
                    addr = make32(highAddr,lowAddr);
                    // Get the line_type
                    int lineType = atoi(recordBuffer,7);
                    // If the line type is 1, then data is done being sent
                    if (lineType == 1) {
                        done = true;
                    }
                    else if (lineType == 0) {
                        // Loops through all of the data and stores it in data
                        // The last 2 bytes are the check sum, hence buffidx-3
                        for (i = 9,dataIdx=0; i < bufferIdx-3; i += 2)
                            data[dataIdx++] = atoi(recordBuffer,i);
                        //  write_program_memory(addr, data, count);
                        success = doCheckSum(recordBuffer,bufferIdx);
                        if(!success) {
                            throw new ChecksumException();
                        }
                        if(addr <= DATA_ADDRESS) {
                            recordCount++;
                            recordsVector.addRecordData(addr,data,count);
                        }
                        else if(addr >= CONFIG_ADDRESS) {
                            recordsVector.addConfigRecord(lowAddr,data,count);
                        }
                    }
                    else if (lineType == 4) {
                        highAddr = make16(atoi(recordBuffer,9), atoi(recordBuffer,11));
                        for (i = 9,dataIdx=0; i < bufferIdx-3; i += 2)
                            data[dataIdx++] = atoi(recordBuffer,i);
                        //  write_program_memory(addr, data, count);
                        success = doCheckSum(recordBuffer,bufferIdx);
                        if(!success) {
                            throw new ChecksumException();
                        }                
                    }
                }
            }
        } catch (ArgumentException ex) {
            throw new ReadFileExeption("Error while opening hex file"); 
        } catch (NullPointerException ex) {
            throw new ReadFileExeption("Error while opening hex file"); 
        } catch (IOException ex) {
            throw new ReadFileExeption("Error while opening hex file"); 
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(HexFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return recordsVector;
    }
    private boolean doCheckSum(char[] recordBuffer,int bufferIdx) throws NullPointerException, ArgumentException {
        if(recordBuffer == null)
            throw new NullPointerException();
        int checksum = 0;                   // Sum the bytes to find the check sum value
        for (int i=1; i<bufferIdx-1; i+=2) {
           checksum += atoi(recordBuffer,i);
        }
        checksum &= 0xFF;
        if (checksum != 0)
            return false;
        return true;
    }
    private int hex2bin(char c) {
        if(c >= 'A')
            return (c -'A' + 10);
        else
            return (c -'0');
    }
    private int atoi(char[] buffer, int index) throws ArgumentException, NullPointerException {
        if(index < 0 )           
            throw new ArgumentException("Illegal index number");
        if(buffer == null)
            throw new NullPointerException();
        int result = hex2bin(buffer[index++]);
            result = result * 16 + hex2bin(buffer[index]);
        
        return result;  
    }
    private int make16(int high, int low) throws ArgumentException {
        if(high < 0 || low < 0) {
            throw new ArgumentException("Illegal high or low number");
        }
        return (high*256)+low;
    }
    private int make32(int high, int low) throws ArgumentException {
        if(high < 0 || low < 0) {
            throw new ArgumentException("Illegal high or low number");
        }
        return (high*65536)+low;
    }
}

