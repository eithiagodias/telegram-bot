package telegram_bot.entity;

public class Option {
    private Integer index;
    private String resource;
    private String descriptionParams;

    private String regex;

    public Option(Integer index, String resource, String descriptionParams, String regex) {
        this.index = index;
        this.resource = resource;
        this.descriptionParams = descriptionParams;
        this.regex = regex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDescriptionParams() {
        return descriptionParams;
    }

    public void setDescriptionParams(String descriptionParams) {
        this.descriptionParams = descriptionParams;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
