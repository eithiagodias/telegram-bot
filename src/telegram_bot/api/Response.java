package telegram_bot.api;

public class Response {
    private String result;
    private Integer statusCode;

    public Response(String result, Integer statusCode){
        this.setResult(result);
        this.setStatusCode(statusCode);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
