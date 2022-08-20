package br.com.daciosoftware.degustlanches.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dácio Braga on 19/07/2016.
 */
public class MyDateUtil {

    private MyDateUtil() {
    }

    public static Calendar dateBrToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar dateTimeBrToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar dateUSToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public static Calendar dateTimeUSToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar dateShortBrToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String calendarToDateBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(data.getTime());
    }

    public static String calendarToDateTimeBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(data.getTime());
    }

    public static String calendarToDateUS(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(data.getTime());
    }

    public static String calendarToDateTimeUS(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return sdf.format(data.getTime());
    }

    public static String timeToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault());
        Calendar data = Calendar.getInstance();
        return sdf.format(data.getTime());
    }

    public static String calendarToShortDateBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.getDefault());
        return sdf.format(data.getTime());
    }

    public static String calendarToTimeBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(data.getTime());
    }

}
