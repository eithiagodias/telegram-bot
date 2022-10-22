package telegram_bot.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static Response get(String resource, String param) throws GetInfoException {

        Response response = new Response("Nenhum dado encontrado. Por favor, revise o parametro enviado.", 0);

        try {
            URL api = new URL("https://brasilapi.com.br/api/"+resource+"/v1/"+param);
            HttpURLConnection connection = (HttpURLConnection) api.openConnection();
            connection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.setRequestMethod("GET");

            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String chunk;
            StringBuffer content = new StringBuffer();
            while((chunk = buffer.readLine()) != null) {
                content.append(chunk);
            }
            buffer.close();
            connection.disconnect();

            response = (new Response(content.toString(), (int) connection.getResponseCode()));

        } catch (Exception e) {
            throw new GetInfoException("Erro ao tentar buscar infos. Tente novamente.");
        }

        return response;
    }
}
