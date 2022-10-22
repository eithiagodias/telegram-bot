package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

public class Cep {
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public static Cep get(String num) throws GetInfoException {
        Response res = Api.get("cep", num);
        Cep cep = (new Gson()).fromJson(res.getResult(), Cep.class);
        return cep;
    }

    @Override
    public String toString() {
        return "\nℹ INFO: "+ "\n" +
                "Cep: " + this.getCep() + "\n" +
                "Rua: " + this.getStreet() + "\n" +
                "Cidade: " + this.getCity() + "\n" +
                "Bairro/Localização: " + this.getNeighborhood() + "\n" +
                "Estado: " + this.getState() + "\n" +
                "Serviço: " + this.getService();
    }
}
