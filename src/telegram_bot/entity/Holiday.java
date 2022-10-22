package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

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
                "Data: " + holiday.date+"\n" +
                "Tipo: " + holiday.type+"\n\n";
        }
        return "\n\uD83D\uDCC5 FERIADOS: \n\n" + h;
    }
}
