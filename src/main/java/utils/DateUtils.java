package utils;


import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    public static String dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = simpleDateFormat.parse(s);
        return String.valueOf(date.getTime());
    }


    //将带年月日的日期转化为yyyy-MM-dd
    public String parseDate(String date) {
        String year;
        String month;
        String day;
        year = date.trim().split("年")[0];
        month = date.trim().split("年")[1].split("月")[0];
        day = date.trim().split("年")[1].split("月")[1].split("日")[0];
        return year + "-" + month + "-" + day;
    }


}
