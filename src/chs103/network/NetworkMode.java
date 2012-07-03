/*
 * NetworkMode.java
 *
 * Created on August 21, 2008, 3:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.network;

/**
 * This is a enum for network states
 */
public enum NetworkMode {
    IDLE,       
    TIME_OUT,
    DATA_READY,
    CRC_ERROR,
    BUSY,
    DISCONNECTED
};
