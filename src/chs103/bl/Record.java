/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.bl;

import chs103.exception.ArgumentException;

/**
 *
 * @author user
 */
public class Record implements Comparable {
    private int[] data;
    private int address;
    private boolean empty;
    private final static int RECORD_LENGTH = 64;
    /**
     * Default constructor
     */
    public Record() {
        data  = new int[RECORD_LENGTH];
        for(int i = 0; i < RECORD_LENGTH; i++) {
            data[i] = 65535;
        }
        address = 0;
        empty = false;
    }
    /**
     * Constructor.
     * @param index     index that use for calculate address
     *                  for record
     */
    public Record(int index) {
        data = new int[RECORD_LENGTH];
        for(int i = 0; i < RECORD_LENGTH; i++) {
            data[i] = 65535;
        }
        address = index * RECORD_LENGTH;
        empty   = true;
    }
    /**
     * Constructor.
     * @param address       
     * @param data      
     */
    public Record(int address, int[] data) {
        this.address = address;
        this.data = data;
        this.empty = false;
    }
    /**
     * Return empty flag that represents if a record is empty or not
     * @return empty  the empty flag
     */
    public boolean isEmpty() {
        return empty;
    }
    /**
     * Return address of record
     * @return address  the address of record
     */
    public int getAddress() {
        return address;
    }
    /**
     * Return data representing the data of record
     * @return  data
     */
    public int[] getData() {
        return data;
    }
    /**
     * Put newData into data array at the offset address
     * @param newData   the new data
     * @param offset    the offset address of data
     * @param len       the lenght of data
     */
    public void put(int[] newData, int offset, int len) {
        if(offset < 0)
            throw new ArrayIndexOutOfBoundsException();
        if(len < 0 || (len+offset) > RECORD_LENGTH)
            throw new ArrayIndexOutOfBoundsException();
        for(int i = 0; i < len; i++) {
            data[i+offset] = newData[i];
        }
        empty = false;
    }
    /**
     * Parses the byte array argument as a record object. The 
     * bytes in the byte array must all be decimal or hex digits
     *
     * @param buf  a <code>byte</code> array containing two bytes of address (hi,lo)
     *             and remained bytes are data bytes representation to be parsed
     * @return     the record object represented by the argument in decimal.
     * @exception  ArgumnetException  if the byte array does not contain a
     *             parsable values.
     */
    public static Record parse(int[] buf) throws ArgumentException {
        
        if(buf == null)
            throw new NullPointerException();
        if(buf.length < 0 )
            throw new ArgumentException("buffer length less then 0");
        int zeroHighBytesMask = 0x00FF;
        int addrLo = (buf[4] & zeroHighBytesMask);
        int addrHi = (buf[5] & zeroHighBytesMask);
        int address = (addrHi*256)+addrLo;
        
        int []data = new int[RECORD_LENGTH];
        
        int offset = 6;
        for(int i = 0; i < RECORD_LENGTH; i++) {
            data[i] = buf[i + offset];
        }
        
        return new Record(address,data); 
    }
    /**
     * Compares this record with the specified record for order.  Returns a
     * negative integer or a positive integer as this record is not equal
     * to , or equal to the specified record.
     * @param   o the record to be compared.
     * @return  a negative integer or a positive integer as this record
     *		is not equal to, or equal to the specified record.
     */
    @Override
    public int compareTo(Object o) {
        if(((Record)o).getAddress() != this.address) {
            return -1;
        }
        
        int result = 1;     // by default equal
        int[] oData = ((Record)o).getData();
        
        int zeroHighBytesMask = 0x00FF;
        for(int i = 0; i < RECORD_LENGTH; i++) {
             oData[i] = ( oData[i] & zeroHighBytesMask );
            if(data[i] != oData[i]) {
                result = -1;
                break;
            }
        }
        return result;
    }
}
