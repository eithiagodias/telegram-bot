package telegram_bot.managers;

import telegram_bot.api.GetInfoException;
import telegram_bot.entity.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OptionManager {

    public enum OPTIONS {

        BANK(new Option(1, "Banco", "codigo do banco (ex. 237)", "^(?i)(1)$|(?i)(banco)$|(?i)(bank)$|(?i)(bancos)$")),
        CEP(new Option(2, "Cep", "numero do CEP (ex. 09784435)", "^(?i)(2)$|(?i)(cep)$|(?i)(ceps)$")),
        DDD(new Option(3, "DDD", "codigo do DDD sem zeros (ex. 11)","^(?i)(3)$|(?i)(ddd)$|(?i)(discagem direta a distância)$|(?i)(ddds)$")),
        CNPJ(new Option(4, "CNPJ", "numero do CNPJ (ex. 19131243000197)", "^(?i)(4)$|(?i)(cnpj)$|(?i)(cnpjs)$")),
        NATIONAL_HOLIDAYS(new Option(5, "Feriados Nacionais", "o ano que deseja (ex. 2022)", "^(?i)(5)$|(?i)(feriado)$|(?i)(nacional)$|(?i)(feriados)$|(?i)(folga)$")),
        NOT_FOUND(new Option(0, "", "", ""));

        private Option opt;

        OPTIONS(Option opt) {
            this.opt = opt;
        }

        public Option get() {
            return opt;
        }

        public void set(Option opt) {
            this.opt = opt;
        }
    }

    public static String getMenu() {
        String menu = "\n⚪ MENU: \n\n";
        for (OPTIONS option : OPTIONS.values()) {
            if(option.get().getIndex() > 0) {
                menu += "["+(option.get().getIndex())+"] - Quero info de "+option.get().getResource()+"\n";
            }
        }

        menu += "\n\uD83D\uDEAA - Para parar digite: Sair";
        return menu;
    }

    public static String descriptionParams(OPTIONS option) {
        return "Por favor digite o "  +  option.get().getDescriptionParams()+ " \uD83D\uDE01";
    }

    public static OPTIONS getOption(String msg) {

        OPTIONS option = OPTIONS.NOT_FOUND;

        for (OPTIONS opt : OPTIONS.values()) {
            if(opt.get().getIndex() == 0) continue;

            Pattern p = Pattern.compile(opt.get().getRegex());
            Matcher m = p.matcher(msg.toLowerCase());
            if(m.find()) {
                option = opt;
                break;
            }
        }

        return option;
    }

    public static Boolean isValidOption(OPTIONS option) {
        return option.get().getIndex()  > 0;
    }

    public static String getResponseByOption(OPTIONS option, String param) throws GetInfoException {
        String res = "";

        if(option.get().getIndex() == OPTIONS.BANK.get().getIndex()) {
            res = Bank.get(param).toString();
        }

        if(option.get().getIndex() == OPTIONS.CEP.get().getIndex()) {
            res = Cep.get(param).toString();
        }

        if(option.get().getIndex() == OPTIONS.DDD.get().getIndex()) {
            res = DDD.get(param).toString();
        }

        if(option.get().getIndex() == OPTIONS.CNPJ.get().getIndex()) {
            res = Cnpj.get(param).toString();
        }

        if(option.get().getIndex() == OPTIONS.NATIONAL_HOLIDAYS.get().getIndex()) {
            res = Holiday.get(param).toString();
        }

        return "\uD83C\uDF89 Eba! Encontrei isso aqui: \n" +res;
    }
}
