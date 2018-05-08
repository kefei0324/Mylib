package org.paul.library.manager.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    /**
     * format long to simpleDateFormat ("MM/dd/yyyy hh:mm:ss")
     * @param time
     * @return
     */
    public static String formatDate(long time){
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

}
