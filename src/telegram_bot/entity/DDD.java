package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

public class DDD {
    private String state;
    private String[] cities;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getCities() {
        return cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public static DDD get(String code) throws GetInfoException {
        Response res = Api.get("ddd", code);
        DDD ddd = (new Gson()).fromJson(res.getResult(), DDD.class);
        return ddd;
    }

    @Override
    public String toString() {
        String cities = "Cidades: ";
        for(String city : this.cities) {
            cities += city+"\n";
        }
        return "\nℹ INFO: "+ "\n" +
                "Estado: " + this.getState() + "\n" + cities;
    }
}
