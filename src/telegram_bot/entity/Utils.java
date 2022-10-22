package telegram_bot.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDate(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
