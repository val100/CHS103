/*
 * BatchFormat.java
 *
 * Created on August 6, 2008, 1:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package chs103.bl;

/**
 * Title: BatchFormat interface <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2008 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.0 <br>
 */
public interface BatchFormat {

    public static int HEAD_OFFSET           = 6;
    public static int DATA_OFFSET           = 640;
    public static int HISTOGRM_NUMS         = 80;
    // HISTOGRAMMA
    public static int START_HISTOGRAM       = 0;
    public static int END_HISTOGRAM         = 79;
    // batch data
    public static int BATCH_NUM             = 80;
    public static int START_NUM_OF_BIRDS    = 81;
    public static int END_NUM_OF_BIRDS      = 82;
    public static int START_WEIGHT          = 83;
    public static int END_WEIGHT            = 86;
    public static int START_SUM_OF_SQUARE   = 87;
    public static int END_SUM_OF_SQUARE     = 90;
    public static int MIN_GRAPH_INDEX       = 91;
    public static int START_MIN_GRAPH       = 92;
    public static int END_MIN_GRAPH         = 93;
    public static int START_AVG_WEIGHT_RUND = 94;
    public static int END_AVG_WEIGHT_RUND   = 95;
    public static int START_AVG_WEIGHT      = 96;
    public static int END_AVG_WEIGHT        = 97;
    public static int START_CV              = 98;
    public static int END_CV                = 99;
    public static int START_NUM_OF_BIRDS_PRC= 100;
    public static int END_NUM_OF_BIRDS_PRC  = 101;
    public static int START_STD_DEVIATION   = 102;
    public static int END_STD_DEVIATION     = 103;
    // date time
    public static int START_START_YEAR      = 104;
    public static int END_START_YEAR        = 105;
    public static int START_MONTH           = 106;
    public static int START_DAY             = 107;
    public static int START_HOUR            = 108;
    public static int START_MINUTE          = 109;
    public static int START_END_YEAR        = 110;
    public static int END_END_YEAR          = 111;
    public static int END_MONTH             = 112;
    public static int END_DAY               = 113;
    public static int END_HOUR              = 114;
    public static int END_MINUTE            = 115;
    // history
    public static int START_HISTORY_WEIGHT  = 128;
    public static int END_HISTORY_WEIGHT    = 639;
    // unit flag
    public static int FLAG                  = 5769;
    public static int UNIT                  = 5773;
}
