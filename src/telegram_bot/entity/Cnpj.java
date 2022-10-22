package telegram_bot.entity;

import com.google.gson.Gson;
import telegram_bot.api.Api;
import telegram_bot.api.GetInfoException;
import telegram_bot.api.Response;

import java.util.ArrayList;

class CnaesSecundario{
    public int codigo;
    public String descricao;
}

class Qsa{
    public int identificador_de_socio;
    public String nome_socio;
    public String cnpj_cpf_do_socio;
    public int codigo_qualificacao_socio;
    public int percentual_capital_social;
    public String data_entrada_sociedade;
    public Object cpf_representante_legal;
    public Object nome_representante_legal;
    public Object codigo_qualificacao_representante_legal;
}

public class Cnpj{
    public String cnpj;
    public int identificador_matriz_filial;
    public String descricao_matriz_filial;
    public String razao_social;
    public String nome_fantasia;
    public int situacao_cadastral;
    public String descricao_situacao_cadastral;
    public Object nome_cidade_exterior;
    public int codigo_natureza_juridica;
    public String data_inicio_atividade;
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public int cep;
    public String uf;
    public String municipio;
    public String ddd_telefone_1;
    public Object ddd_telefone_2;
    public Object ddd_fax;
    public int capital_social;
    public String porte;

    public String data_situacao_cadastral;
    public int motivo_situacao_cadastral;
    public int cnae_fiscal;
    public String cnae_fiscal_descricao;
    public String descricao_tipo_logradouro;
    public int codigo_municipio;
    public int qualificacao_do_responsavel;
    public String descricao_porte;
    public boolean opcao_pelo_simples;
    public Object data_opcao_pelo_simples;
    public Object data_exclusao_do_simples;
    public boolean opcao_pelo_mei;
    public Object situacao_especial;
    public Object data_situacao_especial;
    public ArrayList<CnaesSecundario> cnaes_secundarios;
    public ArrayList<Qsa> qsa;

    public static Cnpj get(String num) throws GetInfoException {
        Response res = Api.get("cnpj", num);
        Cnpj cnpj = (new Gson()).fromJson(res.getResult(), Cnpj.class);
        return cnpj;
    }

    @Override
    public String toString() {
        return "\n\uD83D\uDCCB Info Básica:"+ "\n" +
                "CNPJ: " + this.cnpj + "\n" +
                "Data de Inicio Atividade: " + this.data_inicio_atividade + "\n" +
                "ID Matriz Filial: " + this.identificador_matriz_filial + "\n" +
                "Desc Matriz Filial: " + this.descricao_matriz_filial + "\n" +
                "Razão Social: " + this.razao_social + "\n" +
                "Nome Fantasia: " + this.nome_fantasia + "\n" +
                "Capital Social: " + this.capital_social + "\n" +
                "Porte: " + this.porte + "\n" +
                "Cod. Natureza Juridica: " + this.codigo_natureza_juridica + "\n" +
                "Situação Cadastral: " + this.situacao_cadastral + "\n" +
                "Desc. Situação Cadastral: " + this.descricao_situacao_cadastral + "\n\n" +

                "\uD83D\uDCEA Endereço: \n" +
                "Logradouro: " + this.logradouro + "\n" +
                "Numero: " + this.numero + "\n" +
                "Complemento: " + this.complemento + "\n" +
                "Bairro: " + this.bairro + "\n" +
                "Cep: " + this.cep + "\n" +
                "UF: " + this.uf + "\n" +
                "Municipio: " + this.municipio + "\n" +

                "\n☎ Contato: \n" +
                "Telefone 1: " + this.ddd_telefone_1 + "\n" +
                "Telefone 2: " + this.ddd_telefone_2 + "\n" +
                "Fax: " + this.ddd_fax;
    }

}
