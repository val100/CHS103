/*
 * Version.java
 *
 * Created on August 24, 2008, 11:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.bl;

import chs103.exception.ArgumentException;

/**
 * Title: Version <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class Version implements Comparable {
    private int version;
    /**
     * Creates a new instance of Version 
     */
    public Version() {
        version = -1;
    }
    /**
     * Create  a new instance of Version 
     * @param version   new version
     */
    public Version(int version) {
        this.version = version;
    }
    /**
     * Get version
     * @return  version  
     */
    public int getVersion() {
        return version;
    }
    /**
     * 
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }
    /**
     * Parses the byte array argument as a new Version object. 
     *
     * @param buf  a <code>byte</code> array containing the array of <code>byte</code>
     *             representation to be parsed
     * @return     the Version object
     */
    public static Version parse(int[] buf) throws ArgumentException {
        if(buf == null)
            throw new NullPointerException();
        if(buf.length < 5)
            throw new ArgumentException("error in array length");
        byte low  = (byte)buf[4];
        byte high = (byte)buf[5];
        int newVersion = ( high * 256) + low;
        
        return new Version(newVersion);
    }
    /**
     * Override toString method
     * @return  String object
     */
    @Override
    public String toString() {
        return String.valueOf(version);
    }
    /**
     * Compare this object to another 
     * @param o the Version object
     * @return  return 1 - if o > this, return 0 - if o == this , return -1 if o < this
     */
    @Override
    public int compareTo(Object o) {
        if(((Version)o).getVersion() > this.version) {
            return 1;
        }
        else if(((Version)o).getVersion() == this.version) {
            return 0;
        }
        else  {
            return -1;
        }
    }
}
