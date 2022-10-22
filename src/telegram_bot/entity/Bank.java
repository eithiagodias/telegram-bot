package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

public class Bank {
    private Integer ispb;
    private String name;
    private Integer code;
    private String fullName;

    public Integer getIspb() {
        return ispb;
    }

    public void setIspb(Integer ispb) {
        this.ispb = ispb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static Bank get(String code) throws GetInfoException {
        Response res = Api.get("banks", code);
        Bank bank = (new Gson()).fromJson(res.getResult(), Bank.class);
        return bank;
    }

    @Override
    public String toString() {
        return "\n\uD83D\uDCB0 BANCO: "+ "\n" +
                "Codigo: " + this.getCode() + "\n" +
                "Nome: " + this.getName() + "\n" +
                "Nome Completo: " + this.getFullName();
    }
}
