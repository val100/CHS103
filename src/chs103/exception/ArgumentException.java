/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.exception;

/**
 * Title: ArgumentException <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class ArgumentException extends Exception {
    public ArgumentException(String errorText) {
        super(errorText);
    }
}
