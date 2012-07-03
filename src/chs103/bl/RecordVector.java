/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.bl;

import java.util.Vector;

/**
 *
 * @author user
 */
public class RecordVector extends Vector {
    private int nextRecordIndex;
    private int prevRecordIndex;
    private Record[] records;
    private Record config;
    public static final int NUM_RECORDS = 1000;
    /**
     * 
     */
    public RecordVector() {
        nextRecordIndex = 0;
        prevRecordIndex = 0;
        records = new Record[NUM_RECORDS];
        initRecords();
        config = new Record();
    }
    /**
     * Initialize records
     */
    private void initRecords() {
        for (int i = 0; i < NUM_RECORDS; i++) {
            records[i] = new Record(i);
        }
    }  
    /**
     * Add record data 
     * @param address
     * @param data
     * @param length
     * @return
     */
    public boolean addRecordData(int address, int[] data, int length) {
        int recordIdx = address/64;         // get the index of record. address/64
        int offset = address-(recordIdx*64);
        records[recordIdx].put(data, offset, length);
        return true;
    }
    /**
     * Add configuration record data
     * @param address
     * @param data
     * @param length
     * @return
     */
    public boolean addConfigRecord(int address, int[] data, int length) {
        int offset = address%64;
        config.put(data, offset, length);
        return true;
    }
    /**
     * Returns next unreaded record
     * @return  next record unreaded record
     */
    public Record getNextRecord() {
        if(nextRecordIndex < NUM_RECORDS) {
            while(nextRecordIndex < NUM_RECORDS && records[nextRecordIndex].isEmpty()) {
                nextRecordIndex++;
            }
        }
        // if no more records
        if(nextRecordIndex == NUM_RECORDS) {
            return null;
        }
        prevRecordIndex = nextRecordIndex;
        return records[nextRecordIndex++];
    }
    /**
     * Returns the previous record
     * @return  previous record
     */
    public Record getPrevRecord() {
        return records[prevRecordIndex];
    }
    /**
     * Returns the configuration record RecordsVector.
     * @return  configuration record
     */
    public Record getConfigRecord() {
        return config;
    }
    /**
     * 
     * @return
     */
    public int getRecordToSendIndex() {
        return nextRecordIndex;
    }
}
