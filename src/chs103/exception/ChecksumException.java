/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.exception;

/**
 *
 * @author user
 */
public class ChecksumException extends Exception {
    public ChecksumException() {
    }
    @Override
    public String toString() {
        return "checksum error!";
    }
}
