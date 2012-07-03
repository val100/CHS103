/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.bl;

/**
 * Title: ErrorCode <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class ErrorCode {
    private byte errorCode;
    public static final byte OK    = 0x00;
    public static final byte ERROR = 0x01;
    /**
     * 
     * @param errorCode
     */
    public ErrorCode() {
        errorCode = 0;
    }
    /**
     * 
     * @param errorCode
     */
    public ErrorCode(byte errorCode) {
        this.errorCode = errorCode;
    }
    /**
     * 
     * @return
     */
    public byte getErrorCode() {
        return errorCode;
    }
    /**
     * 
     * @param buf
     * @return
     */
    public static ErrorCode parse(int[] buf) {
        if(buf == null)
            throw new NullPointerException();
        if(buf.length < 4)
            throw new ArrayIndexOutOfBoundsException();
        byte errorCode = (byte)buf[4];

        return new ErrorCode(errorCode);
    }
            
}
