/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.exception;

import java.io.IOException;

/**
 * Title: ReadFileExeption <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     AgroLogic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class ReadFileExeption extends IOException {
    public ReadFileExeption(String errorText) {
        super(errorText);
    }
}
