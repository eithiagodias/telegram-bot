package telegram_bot.managers;
import telegram_bot.entity.Client;

import java.util.Map;
import java.util.HashMap;

public class ClientManager {

    private Map<String, Client> clients;

    public ClientManager() {
        this.setClients(new HashMap<String, Client>());
    }

    public Map<String, Client> getClients() {
        return clients;
    }
    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public void addNewClient(Client client) {
        String clientId = client.getClientId();
        if(!this.clients.containsKey(clientId)) {
            this.clients.put(clientId, client);
        }
    }

    public void updateClientState(String clientId, Client.StateCurrent state) {
        if(this.clients.containsKey(clientId)) {
            Client client = this.clients.get(clientId);
            client.setState(state);
            this.clients.replace(clientId, client);
        }
    }

    public void updateClientOption(String clientId, OptionManager.OPTIONS option) {
        if(this.clients.containsKey(clientId)) {
            Client client = this.clients.get(clientId);
            client.setOptionSelected(option);
            this.clients.replace(clientId, client);
        }
    }

    public Client.StateCurrent getClientState(String clientId) {
        return this.clients.get(clientId).getState();
    }

    public Client getClient(String clientId) {
       return this.clients.get(clientId);
    }
}
