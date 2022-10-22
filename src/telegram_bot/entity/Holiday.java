package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

class HolidayInfo {
    public String date;
    public String name;
    public String type;
}

public class Holiday {
    public HolidayInfo[] holidays;
    public static Holiday get(String year) throws GetInfoException {
        Response res = Api.get("feriados", year);
        Holiday holiday = (new Gson()).fromJson("{holidays: " + res.getResult() + "}", Holiday.class);
        return holiday;
    }

    @Override
    public String toString() {
        String h = "";
        for(HolidayInfo holiday : this.holidays) {
            h += "\uD83D\uDCCC " + holiday.name+"\n" +
                "Data: " + Utils.formatDate(holiday.date) +"\n" +
                "Tipo: nacional \n\n";
        }
        return "\n\uD83D\uDCC5 FERIADOS: \n\n" + h;
    }
}
