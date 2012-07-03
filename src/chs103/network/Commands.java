/*
 * Commands.java
 *
 * Created on August 4, 2008, 2:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.network;

/**
 * Title: Commands <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public interface Commands {
    static final int START_TRASSMIT  = 0x55;    // 85
    static final int READ_VERSION    = 0x01;    // 1
    static final int READ_ALL        = 0x05;    // 5
    static final int READ_DATE_TIME  = 0x32;    // 50
    static final int WRITE_DATE_TIME = 0x80;    // 80
    // restart loader commands
    static final int RESTART_LOADER             = 0x5A;    // 90
    static final int READ_LOADER_VERSION        = 0x46;    // 70
    static final int WRITE_PROGRAM              = 0x5F;    // 95
    static final int WRITE_CONFIGURATION        = 0x64;    // 100
    static final int READ_CONFIGURATION         = 0x48;    // 75
    static final int END_PROGRAM                = 0x69;    // 105
}

