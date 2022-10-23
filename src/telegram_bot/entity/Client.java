package telegram_bot.entity;

import telegram_bot.managers.OptionManager;

public class Client {
    public enum StateCurrent {
        NOT_STARTED,
        STARTED,
        WAITING_FOR_CLIENT_TO_CHOOSE_OPTION,
        WAITING_FOR_SENT_PARAMS_BY_CLIENT,
        WAITING_FOR_SENT_RESPONSE_TO_CLIENT
    }
    private String clientId;
    private StateCurrent state;
    private OptionManager.OPTIONS optionSelected;

    public Client(String clientId) {
        this.setClientId(clientId);
        this.setState(StateCurrent.NOT_STARTED);
        this.setOptionSelected(OptionManager.OPTIONS.NOT_FOUND);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public StateCurrent getState() {
        return state;
    }

    public void setState(StateCurrent state) {
        this.state = state;
    }

    public OptionManager.OPTIONS getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(OptionManager.OPTIONS optionSelected) {
        this.optionSelected = optionSelected;
    }

    public boolean isStarted() {
        return this.state == StateCurrent.STARTED;
    }

    public boolean isNotStarted() {
        return this.state == StateCurrent.NOT_STARTED;
    }

    public boolean isWaitingChoose() {
        return this.state == StateCurrent.WAITING_FOR_CLIENT_TO_CHOOSE_OPTION;
    }

    public boolean isWaitingSentParams() {
        return this.state == StateCurrent.WAITING_FOR_SENT_PARAMS_BY_CLIENT;
    }

    public boolean isWaitingSentResponse() {
        return this.state == StateCurrent.WAITING_FOR_CLIENT_TO_CHOOSE_OPTION;
    }
}
