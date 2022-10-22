package telegram_bot.api;

public class GetInfoException extends Exception {
    public GetInfoException() {
    }

    // Constructor that accepts a message
    public GetInfoException(String message) {
        super(message);
    }
}
