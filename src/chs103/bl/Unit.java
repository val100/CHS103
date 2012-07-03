/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.bl;

/**
 * Title: Unit <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public class Unit {
    private static byte unit;
    public static byte parse(int[] buf,String unitsrting,int version){
        
        byte firstBitMask = 0x01;
        byte unitByte = (byte)buf[BatchFormat.UNIT];
        byte unitFlag = (byte)(unitByte & firstBitMask);
        
        if(version == 1) {
            unit = (unitsrting.equals("gram")) ? Batch.GRAM:Batch.POUND;
            System.out.println("version 1");
        } else {
            unit = (unitFlag == 0) ? Batch.POUND:Batch.GRAM;
            System.out.println("version 2");
            String u = (unit==0)? "pound":"gram";
            System.out.println("unit : " + u);
        }
        
        return unit;
    }
}
