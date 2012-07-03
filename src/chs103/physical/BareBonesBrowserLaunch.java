/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chs103.physical;

/**
 * Title: BareBonesBrowserLaunch <br>
 * Decription: 
 * Supports:    Mac OS X, GNU/Linux, Unix, Windows XP/Vista <br>
 * Example Usage:   String url = "http://www.centerkey.com/";
                    BareBonesBrowserLaunch.openURL(url);
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
//  Supports:                                          //
//     Mac OS X, GNU/Linux, Unix, Windows XP/Vista     //
//  Example Usage:                                     //
//     String url = "http://www.centerkey.com/";       //
//     BareBonesBrowserLaunch.openURL(url);            //

import java.lang.reflect.Method;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class BareBonesBrowserLaunch {

   static final String[] browsers = { "firefox", "opera", "konqueror", "epiphany",
      "seamonkey", "galeon", "kazehakase", "mozilla", "netscape" };

   public static void openURL(String url) {
      String osName = System.getProperty("os.name");
      try {
         if (osName.startsWith("Mac OS")) {
            Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL",
               new Class[] {String.class});
            openURL.invoke(null, new Object[] {url});
            }
         else if (osName.startsWith("Windows"))
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
         else { //assume Unix or Linux
            boolean found = false;
            for (String browser : browsers)
               if (!found) {
                  found = Runtime.getRuntime().exec(
                     new String[] {"which", browser}).waitFor() == 0;
                  if (found)
                     Runtime.getRuntime().exec(new String[] {browser, url});
                  }
            if (!found)
               throw new Exception(Arrays.toString(browsers));
            }
         }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null,
            "Error attempting to launch web browser\n" + e.toString());
         }
      }

   }
