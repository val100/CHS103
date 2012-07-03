/*
 * DateTime.java
 *
 * Created on August 3, 2008, 11:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.bl;

import java.util.Calendar;


/**
 * Title: DateTime <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * 
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class DateTime {
    private int year;
    private int hiyear;
    private int loyear;
    private int day;
    private int date;
    private int month;
    private int hour;
    private int minute;
    /**
     * Creates a new instance of DateTime
     */
    public DateTime() {
        
        Calendar cal = Calendar.getInstance();     
        
        java.util.Date dateTime = new java.util.Date (System.currentTimeMillis()); 
        year    =  dateTime.getYear()+1900;
        hiyear  = year/256;
        loyear  = year%256;
        day     = dateTime.getDay();
        date    = dateTime.getDate();
        month   = dateTime.getMonth()+1;
        hour    = dateTime.getHours();
        minute  = dateTime.getMinutes();
        
    }
    public DateTime(byte ly, byte hy, byte mt, byte dy, byte hr, byte mn) {
        loyear = ly;
        hiyear = hy;    
        year   = (int)((int)(hy * 256) + ly);
        month  = mt;
        day    = dy;
        hour   = hr;
        minute = mn;
    }
    public DateTime(int yr, int mt, int dy, int hr, int mn) {
        year   = yr;
        month  = mt;
        day    = dy;
        hour   = hr;
        minute = mn;
    }
    public int[] getDateTime() {
        return new int[]{
                         getHiyear(), 
                         getLoyear(), 
                         getDay(), 
                         getMonth(), 
                         getHour(), 
                         getMinute()
                        };
    }
    public void setDateTime(int datetime[]) {
        setLoyear(datetime[0]);
        setHiyear(datetime[1]);
        setDay   (datetime[2]);
        setMonth (datetime[3]);
        setHour  (datetime[4]);
        setMinute(datetime[5]);
    }
    public int getHiyear() {
        return hiyear;
    }
    public void setHiyear(int hiyear) {
        this.hiyear = hiyear;
    }
    public int getLoyear() {
        return loyear;
    }
    public void setLoyear(int loyear) {
        this.loyear = loyear;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinute() {
        return minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public String getDate() {

        StringBuilder sb = new StringBuilder();
        
        sb.append(date).append("-").append(month).append("-").append(year);
        
        return sb.toString();
    }
    public String getDate(byte unit) {
       
        StringBuilder sb = new StringBuilder();
        if(unit == Batch.GRAM){
            sb.append(date).append("-").append(month).append("-").append(year);
        } else {
            sb.append(month).append("-").append(date).append("-").append(year);
        }
        
        return sb.toString();
    }
    public String getTime() {
       StringBuilder time = new StringBuilder();
       
       time.append(hour).append(":").append(minute);
       
       return time.toString();
    }
    /**
     * Parses the byte array argument as a new DateTime object. 
     *
     * @param buf  a <code>byte</code> array containing the array of <code>byte</code>
     *             representation to be parsed
     * @return     the DateTime object
     */
    public static DateTime parse(int[] buf) {
        byte ly = (byte)buf[DateTimeFormat.LOW_YEAR];
        byte hy = (byte)buf[DateTimeFormat.HIGH_YEAR];
        byte mt = (byte)buf[DateTimeFormat.MONTH];
        byte dy = (byte)buf[DateTimeFormat.DAY];
        byte hr = (byte)buf[DateTimeFormat.HOUR];
        byte mn = (byte)buf[DateTimeFormat.MINUTE];
        
        return new DateTime(ly,hy,mt,dy,hr,mn);
    }
    
    public static DateTime parse(String string) {
        String[] arr = string.split("-");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        return new DateTime(year,month,day,0,0);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date).append("-").append(month).append("-").append(year);

        return sb.toString();
    }
}
