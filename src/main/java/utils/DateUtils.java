package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static String getYearMonth(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        DateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        String value = null;
        try {
            value = dateFormat.format(Format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }
}
